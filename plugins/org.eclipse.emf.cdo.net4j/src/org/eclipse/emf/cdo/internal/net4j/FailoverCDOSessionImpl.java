/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.net4j;

/**
 * @author Eike Stepper
 */
public class FailoverCDOSessionImpl extends CDONet4jSessionImpl
{
  public FailoverCDOSessionImpl(FailoverCDOSessionConfigurationImpl configuration)
  {
    super(configuration);
  }

  @Override
  public FailoverCDOSessionConfigurationImpl getConfiguration()
  {
    return (FailoverCDOSessionConfigurationImpl)super.getConfiguration();
  }

  @Override
  protected void sessionProtocolDeactivated()
  {
    getConfiguration().sessionProtocolDeactivated(this);
  }
}
