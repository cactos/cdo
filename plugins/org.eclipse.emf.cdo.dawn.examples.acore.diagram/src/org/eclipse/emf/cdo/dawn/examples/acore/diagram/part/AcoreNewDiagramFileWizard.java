/*
 * Copyright (c) 2010, 2012, 2015 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Martin Fluegge - initial API and implementation
 *
 */
package org.eclipse.emf.cdo.dawn.examples.acore.diagram.part;

import org.eclipse.emf.cdo.dawn.examples.acore.diagram.edit.parts.ACoreRootEditPart;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @generated
 */
public class AcoreNewDiagramFileWizard extends Wizard
{

  /**
   * @generated
   */
  private WizardNewFileCreationPage myFileCreationPage;

  /**
   * @generated
   */
  private ModelElementSelectionPage diagramRootElementSelectionPage;

  /**
   * @generated
   */
  private TransactionalEditingDomain myEditingDomain;

  /**
   * @generated
   */
  public AcoreNewDiagramFileWizard(URI domainModelURI, EObject diagramRoot, TransactionalEditingDomain editingDomain)
  {
    assert domainModelURI != null : "Domain model uri must be specified"; //$NON-NLS-1$
    assert diagramRoot != null : "Doagram root element must be specified"; //$NON-NLS-1$
    assert editingDomain != null : "Editing domain must be specified"; //$NON-NLS-1$

    myFileCreationPage = new WizardNewFileCreationPage(Messages.AcoreNewDiagramFileWizard_CreationPageName,
        StructuredSelection.EMPTY);
    myFileCreationPage.setTitle(Messages.AcoreNewDiagramFileWizard_CreationPageTitle);
    myFileCreationPage.setDescription(
        NLS.bind(Messages.AcoreNewDiagramFileWizard_CreationPageDescription, ACoreRootEditPart.MODEL_ID));
    IPath filePath;
    String fileName = URI.decode(domainModelURI.trimFileExtension().lastSegment());
    if (domainModelURI.isPlatformResource())
    {
      filePath = new Path(domainModelURI.trimSegments(1).toPlatformString(true));
    }
    else if (domainModelURI.isFile())
    {
      filePath = new Path(domainModelURI.trimSegments(1).toFileString());
    }
    else
    {
      // TODO : use some default path
      throw new IllegalArgumentException("Unsupported URI: " + domainModelURI); //$NON-NLS-1$
    }
    myFileCreationPage.setContainerFullPath(filePath);
    myFileCreationPage.setFileName(AcoreDiagramEditorUtil.getUniqueFileName(filePath, fileName, "acore_diagram")); //$NON-NLS-1$

    diagramRootElementSelectionPage = new DiagramRootElementSelectionPage(
        Messages.AcoreNewDiagramFileWizard_RootSelectionPageName);
    diagramRootElementSelectionPage.setTitle(Messages.AcoreNewDiagramFileWizard_RootSelectionPageTitle);
    diagramRootElementSelectionPage.setDescription(Messages.AcoreNewDiagramFileWizard_RootSelectionPageDescription);
    diagramRootElementSelectionPage.setModelElement(diagramRoot);

    myEditingDomain = editingDomain;
  }

  /**
   * @generated
   */
  @Override
  public void addPages()
  {
    addPage(myFileCreationPage);
    addPage(diagramRootElementSelectionPage);
  }

  /**
   * @generated
   */
  @Override
  public boolean performFinish()
  {
    List affectedFiles = new LinkedList();
    IFile diagramFile = myFileCreationPage.createNewFile();
    AcoreDiagramEditorUtil.setCharset(diagramFile);
    affectedFiles.add(diagramFile);
    URI diagramModelURI = URI.createPlatformResourceURI(diagramFile.getFullPath().toString(), true);
    ResourceSet resourceSet = myEditingDomain.getResourceSet();
    final Resource diagramResource = resourceSet.createResource(diagramModelURI);
    AbstractTransactionalCommand command = new AbstractTransactionalCommand(myEditingDomain,
        Messages.AcoreNewDiagramFileWizard_InitDiagramCommand, affectedFiles)
    {

      @Override
      protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException
      {
        int diagramVID = AcoreVisualIDRegistry.getDiagramVisualID(diagramRootElementSelectionPage.getModelElement());
        if (diagramVID != ACoreRootEditPart.VISUAL_ID)
        {
          return CommandResult.newErrorCommandResult(Messages.AcoreNewDiagramFileWizard_IncorrectRootError);
        }
        Diagram diagram = ViewService.createDiagram(diagramRootElementSelectionPage.getModelElement(),
            ACoreRootEditPart.MODEL_ID, AcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        diagramResource.getContents().add(diagram);
        return CommandResult.newOKCommandResult();
      }
    };
    try
    {
      OperationHistoryFactory.getOperationHistory().execute(command, new NullProgressMonitor(), null);
      diagramResource.save(AcoreDiagramEditorUtil.getSaveOptions());
      AcoreDiagramEditorUtil.openDiagram(diagramResource);
    }
    catch (ExecutionException e)
    {
      AcoreDiagramEditorPlugin.getInstance().logError("Unable to create model and diagram", e); //$NON-NLS-1$
    }
    catch (IOException ex)
    {
      AcoreDiagramEditorPlugin.getInstance().logError("Save operation failed for: " + diagramModelURI, ex); //$NON-NLS-1$
    }
    catch (PartInitException ex)
    {
      AcoreDiagramEditorPlugin.getInstance().logError("Unable to open editor", ex); //$NON-NLS-1$
    }
    return true;
  }

  /**
   * @generated
   */
  private static class DiagramRootElementSelectionPage extends ModelElementSelectionPage
  {

    /**
     * @generated
     */
    protected DiagramRootElementSelectionPage(String pageName)
    {
      super(pageName);
    }

    /**
     * @generated
     */
    @Override
    protected String getSelectionTitle()
    {
      return Messages.AcoreNewDiagramFileWizard_RootSelectionPageSelectionTitle;
    }

    /**
     * @generated
     */
    @Override
    protected boolean validatePage()
    {
      if (selectedModelElement == null)
      {
        setErrorMessage(Messages.AcoreNewDiagramFileWizard_RootSelectionPageNoSelectionMessage);
        return false;
      }
      boolean result = ViewService.getInstance()
          .provides(new CreateDiagramViewOperation(new EObjectAdapter(selectedModelElement), ACoreRootEditPart.MODEL_ID,
              AcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT));
      setErrorMessage(result ? null : Messages.AcoreNewDiagramFileWizard_RootSelectionPageInvalidSelectionMessage);
      return result;
    }
  }
}
