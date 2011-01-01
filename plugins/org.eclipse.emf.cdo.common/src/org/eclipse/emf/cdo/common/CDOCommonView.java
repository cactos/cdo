/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.common;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.revision.CDORevisionProvider;

import org.eclipse.net4j.util.collection.Closeable;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public interface CDOCommonView extends CDOBranchPoint, CDORevisionProvider, Closeable
{
  public int getViewID();

  /**
   * @since 3.0
   */
  public boolean isReadOnly();

  public CDOCommonSession getSession();
}
