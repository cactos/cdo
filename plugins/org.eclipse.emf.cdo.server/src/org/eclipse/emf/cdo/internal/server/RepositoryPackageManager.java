/***************************************************************************
 * Copyright (c) 2004 - 2007 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.internal.protocol.model.CDOPackageImpl;
import org.eclipse.emf.cdo.internal.protocol.model.CDOPackageManagerImpl;
import org.eclipse.emf.cdo.protocol.util.ImplementationError;

/**
 * @author Eike Stepper
 */
public class RepositoryPackageManager extends CDOPackageManagerImpl
{
  private Repository repository;

  public RepositoryPackageManager(Repository repository)
  {
    this.repository = repository;
  }

  public Repository getRepository()
  {
    return repository;
  }

  @Override
  protected void resolve(CDOPackageImpl cdoPackage)
  {
    // TODO Implement method RepositoryPackageManager.resolve()
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  protected String provideEcore(CDOPackageImpl cdoPackage)
  {
    throw new ImplementationError("No generated model on server side");
  }
}
