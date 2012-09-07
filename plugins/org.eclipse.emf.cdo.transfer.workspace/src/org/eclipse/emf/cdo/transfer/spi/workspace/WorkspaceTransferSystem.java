/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.transfer.spi.workspace;

import org.eclipse.emf.cdo.transfer.CDOTransferElement;
import org.eclipse.emf.cdo.transfer.CDOTransferSystem;

import org.eclipse.net4j.util.WrappedException;

import org.eclipse.emf.common.util.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Eike Stepper
 * @since 4.2
 */
public class WorkspaceTransferSystem extends CDOTransferSystem
{
  public static final String TYPE = "workspace";

  private static final IWorkspaceRoot ROOT = ResourcesPlugin.getWorkspace().getRoot();

  public WorkspaceTransferSystem()
  {
    super(false);
  }

  @Override
  public String getType()
  {
    return TYPE;
  }

  public String getDefaultEncoding()
  {
    try
    {
      return ROOT.getDefaultCharset();
    }
    catch (CoreException ex)
    {
      throw WrappedException.wrap(ex);
    }
  }

  @Override
  public URI getURI(IPath path)
  {
    return URI.createPlatformResourceURI(path.toString(), true);
  }

  @Override
  public CDOTransferElement getElement(IPath path)
  {
    IResource resource = ROOT.findMember(path);
    if (resource.exists())
    {
      return new Element(this, resource);
    }

    return null;
  }

  @Override
  public void createFolder(IPath path) throws IOException
  {
    try
    {
      IFolder folder = ROOT.getFolder(path);
      folder.create(true, true, null);
    }
    catch (CoreException ex)
    {
      throw new IOException(ex);
    }
  }

  @Override
  public void createBinary(IPath path, InputStream source) throws IOException
  {
    try
    {
      IFile file = ROOT.getFile(path);
      file.create(source, true, null);
    }
    catch (CoreException ex)
    {
      throw new IOException(ex);
    }
  }

  @Override
  public void createText(IPath path, InputStream source, String encoding) throws IOException
  {
    try
    {
      IFile file = ROOT.getFile(path);
      file.create(source, true, null);
      file.setCharset(encoding, null);
    }
    catch (CoreException ex)
    {
      throw new IOException(ex);
    }
  }

  @Override
  public String toString()
  {
    return "Workspace";
  }

  /**
   * @author Eike Stepper
   */
  private static class Element extends CDOTransferElement
  {
    private IResource resource;

    public Element(CDOTransferSystem system, IResource resource)
    {
      super(system);
      this.resource = resource;
    }

    @Override
    public Object getNativeObject()
    {
      return resource;
    }

    @Override
    public IPath getPath()
    {
      return resource.getFullPath();
    }

    @Override
    public boolean isDirectory()
    {
      return resource instanceof IContainer;
    }

    @Override
    protected CDOTransferElement[] doGetChildren() throws IOException
    {
      try
      {
        IResource[] children = ((IContainer)resource).members();
        CDOTransferElement[] result = new Element[children.length];

        for (int i = 0; i < children.length; i++)
        {
          IResource child = children[i];
          result[i] = new Element(getSystem(), child);
        }

        return result;
      }
      catch (CoreException ex)
      {
        throw new IOException(ex);
      }
    }

    @Override
    protected InputStream doOpenInputStream() throws IOException
    {
      try
      {
        return ((IFile)resource).getContents();
      }
      catch (CoreException ex)
      {
        throw new IOException(ex);
      }
    }

    // @Override
    // protected OutputStream doOpenOutputStream() throws IOException
    // {
    // return new ByteArrayOutputStream()
    // {
    // @Override
    // public void close() throws IOException
    // {
    // if (resource.exists())
    // {
    // try
    // {
    // ((IFile)resource).setContents(new ByteArrayInputStream(toByteArray()), true, true, null);
    // }
    // catch (CoreException ex)
    // {
    // throw new IOException(ex);
    // }
    // }
    // }
    // };
    // }
  }
}