package org.eclipse.emf.cdo.server.internal.mongodb;

import org.eclipse.emf.cdo.server.CDOServerBrowser;
import org.eclipse.emf.cdo.server.CDOServerBrowser.AbstractPage;
import org.eclipse.emf.cdo.server.mongodb.IMongoDBStore;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import org.eclipse.net4j.util.factory.ProductCreationException;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.io.PrintStream;
import java.util.Set;

/**
 * @author Eike Stepper
 * @since 4.0
 */
public class MongoDBBrowserPage extends AbstractPage
{
  public MongoDBBrowserPage()
  {
    super("collections", "MongoDB Collections");
  }

  public boolean canDisplay(InternalRepository repository)
  {
    return repository.getStore() instanceof IMongoDBStore;
  }

  public void display(CDOServerBrowser browser, InternalRepository repository, PrintStream out)
  {
    IMongoDBStore store = (IMongoDBStore)repository.getStore();
    DB db = store.getDB();

    out.print("<table border=\"0\">\r\n");
    out.print("<tr>\r\n");

    out.print("<td valign=\"top\">\r\n");
    String collection = showCollections(browser, out, db, repository.getName());
    out.print("</td>\r\n");

    if (collection != null)
    {
      out.print("<td valign=\"top\">\r\n");
      showCollection(browser, out, db, collection);
      out.print("</td>\r\n");
    }

    out.print("</tr>\r\n");
    out.print("</table>\r\n");
  }

  protected String showCollections(CDOServerBrowser browser, PrintStream pout, DB db, String repo)
  {
    String collection = browser.getParam("collection");

    Set<String> allCollectionNames = db.getCollectionNames();
    for (String collectionName : allCollectionNames)
    {
      if (collection == null)
      {
        collection = collectionName;
      }

      String label = browser.escape(collectionName)/* .toLowerCase() */;
      if (collectionName.equals(collection))
      {
        pout.print("<b>" + label + "</b><br>\r\n");
      }
      else
      {
        pout.print(browser.href(label, getName(), "collection", collectionName) + "<br>\r\n");
      }
    }

    return collection;
  }

  protected void showCollection(CDOServerBrowser browser, PrintStream pout, DB db, String collection)
  {
    DBCursor cursor = null;

    try
    {
      pout.print("<ol>\r\n");
      pout.print("<ol>\r\n");
      cursor = db.getCollection(collection).find();
      while (cursor.hasNext())
      {
        DBObject doc = cursor.next();
        pout.print("<li>");
        showDocument(browser, pout, doc, "");
        pout.print("<p>\r\n");
      }

      pout.print("</ol>\r\n");
    }
    finally
    {
      if (cursor != null)
      {
        cursor.close();
      }
    }
  }

  protected void showDocument(CDOServerBrowser browser, PrintStream pout, DBObject doc, String level)
  {
    for (String key : doc.keySet())
    {
      pout.print(level);
      pout.print("<b>");
      pout.print(key);
      pout.print("</b> = ");

      Object value = doc.get(key);
      if (value instanceof DBObject)
      {
        DBObject child = (DBObject)value;
        pout.print("<br>");
        showDocument(browser, pout, child, level + "&nbsp;&nbsp;");
      }
      else
      {
        pout.print(value);
        pout.print("<em>&nbsp;&nbsp;(");
        pout.print(value.getClass().getSimpleName().toLowerCase());
        pout.print(")</em><br>");
      }
    }
  }

  /**
   * @author Eike Stepper
   */
  public static class Factory extends org.eclipse.net4j.util.factory.Factory
  {
    public static final String TYPE = "default";

    public Factory()
    {
      super(PRODUCT_GROUP, TYPE);
    }

    public MongoDBBrowserPage create(String description) throws ProductCreationException
    {
      return new MongoDBBrowserPage();
    }
  }
}
