/***************************************************************************
 * Copyright (c) 2004 - 2008 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.emf.cdo;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.model.CDOClass;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.eresource.CDOResource;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Eike Stepper
 */
public interface CDOObject extends EObject
{
  public CDOID cdoID();

  public CDOClass cdoClass();

  public CDOState cdoState();

  /**
   * @since 2.0
   */
  public boolean cdoConflict();

  /**
   * @since 2.0
   */
  public boolean cdoInvalid();

  public CDOView cdoView();

  public CDORevision cdoRevision();

  public CDOResource cdoResource();

  /**
   * @since 2.0
   */
  public CDOResource cdoDirectResource();

  /**
   * Returns read lock associate with this object.
   * 
   * @since 2.0
   */
  public CDOLock cdoReadLock();

  /**
   * Returns write lock associate with this object.
   * 
   * @since 2.0
   */
  public CDOLock cdoWriteLock();

  public void cdoReload();
}
