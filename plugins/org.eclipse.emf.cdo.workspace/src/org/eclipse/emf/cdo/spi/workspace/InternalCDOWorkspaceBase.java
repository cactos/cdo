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
package org.eclipse.emf.cdo.spi.workspace;

import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.workspace.CDOWorkspaceBase;

/**
 * @author Eike Stepper
 */
public interface InternalCDOWorkspaceBase extends CDOWorkspaceBase
{
  public static final String PRODUCT_GROUP = "org.eclipse.emf.cdo.workspace.bases";

  public InternalCDOWorkspace getWorkspace();

  public void init(InternalCDOWorkspace workspace);

  public void updateAfterCommit(CDOTransaction transaction);

  public void clear();
}
