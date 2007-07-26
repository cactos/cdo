package org.eclipse.emf.cdo.internal.ui.actions;

import org.eclipse.emf.cdo.CDOSession;
import org.eclipse.emf.cdo.CDOView;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.util.CDOPackageRegistry;

import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eike Stepper
 * @ADDED
 */
public class ImportResourceAction extends EditingDomainAction
{
  private static final String TITLE = "Import Resource";

  private CDOResource targetResource;

  private transient List<URI> uris;

  public ImportResourceAction()
  {
    super("Import Resource...");
  }

  public CDOResource getTargetResource()
  {
    return targetResource;
  }

  public void setTargetResource(CDOResource targetResource)
  {
    this.targetResource = targetResource;
  }

  @Override
  public boolean isEnabled()
  {
    return targetResource != null && super.isEnabled();
  }

  @Override
  protected void preRun() throws Exception
  {
    ResourceDialog dialog = new ResourceDialog(getShell(), TITLE, SWT.OPEN | SWT.MULTI)
    {
      @Override
      protected boolean processResources()
      {
        return true;
      }
    };

    if (dialog.open() == ResourceDialog.OK)
    {
      uris = dialog.getURIs();
    }
    else
    {
      cancel();
    }
  }

  @Override
  protected void doRun(IProgressMonitor monitor) throws Exception
  {
    EList<EObject> targetContents = targetResource.getContents();
    List<Resource> resources = getSourceResources();
    for (Resource resource : resources)
    {
      List<EObject> contents = new ArrayList(resource.getContents());
      for (EObject root : contents)
      {
        targetContents.add(root);
      }
    }
  }

  protected List<Resource> getSourceResources()
  {
    ResourceSetImpl resourceSet = createSourceResourceSet();
    List<Resource> resources = new ArrayList(uris.size());
    for (URI uri : uris)
    {
      Resource resource = resourceSet.getResource(uri, true);
      resources.add(resource);
    }

    return resources;
  }

  protected ResourceSetImpl createSourceResourceSet()
  {
    CDOView view = targetResource.cdoView();
    CDOSession session = view.getSession();
    CDOPackageRegistry packageRegistry = session.getPackageRegistry();

    ResourceSetImpl resourceSet = new ResourceSetImpl();
    resourceSet.setPackageRegistry(packageRegistry);
    return resourceSet;
  }
}