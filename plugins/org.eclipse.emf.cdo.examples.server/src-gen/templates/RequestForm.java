package templates;

public class RequestForm
{
  protected static String nl;
  public static synchronized RequestForm create(String lineSeparator)
  {
    nl = lineSeparator;
    RequestForm result = new RequestForm();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "<html>" + NL + "  <header>" + NL + "\t  <title>" + NL + "\t\t\tDemo Request Form" + NL + "\t  </title>" + NL + "\t\t<link media=\"screen\" href=\"demo-server.css\" type=\"text/css\" rel=\"stylesheet\">" + NL + "\t<header>" + NL + "<body>" + NL + "" + NL + "<h1>Demo Request Form</h1>" + NL + "<hr>" + NL + "" + NL + "<form method=\"post\" action=\"configOverview.html\">" + NL + "\t<table border=\"0\">" + NL + "\t\t<tr><td>Mode:</td><td><select name=\"mode\"><option>NORMAL</option><option>AUDITING</option><option>BRANCHING</option></select></td></tr>" + NL + "\t\t<!--<tr><td>User IDs:</td><td><input name=\"userIDs\" type=\"input\"/></td></tr>-->" + NL + "\t\t<tr><td>&nbsp;</td><td><input name=\"submit\" type=\"submit\"/></td></tr>" + NL + "\t</table>" + NL + "</form>" + NL + "" + NL + "</body>" + NL + "</html>";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
