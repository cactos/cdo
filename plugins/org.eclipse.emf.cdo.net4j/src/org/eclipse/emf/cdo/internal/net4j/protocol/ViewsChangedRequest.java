/***************************************************************************
 * Copyright (c) 2004 - 2009 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.CDOCommonView;
import org.eclipse.emf.cdo.common.io.CDODataInput;
import org.eclipse.emf.cdo.common.io.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;

import java.io.IOException;

/**
 * @author Eike Stepper
 */
public class ViewsChangedRequest extends CDOClientRequest<Boolean>
{
  private int viewID;

  private CDOCommonView.Type viewType;

  private long timeStamp;

  public ViewsChangedRequest(CDOClientProtocol protocol, int viewID, CDOCommonView.Type viewType, long timeStamp)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_VIEWS_CHANGED);
    this.viewID = viewID;
    this.viewType = viewType;
    this.timeStamp = timeStamp;
  }

  public ViewsChangedRequest(CDOClientProtocol protocol, int viewID)
  {
    this(protocol, viewID, null, CDOCommonView.UNSPECIFIED_DATE);
  }

  @Override
  protected void requesting(CDODataOutput out) throws IOException
  {
    out.writeInt(viewID);
    out.writeByte(viewType == null ? CDOProtocolConstants.VIEW_CLOSED : (byte)viewType.ordinal());
    if (viewType == CDOCommonView.Type.AUDIT)
    {
      out.writeLong(timeStamp);
    }
  }

  @Override
  protected Boolean confirming(CDODataInput in) throws IOException
  {
    return in.readBoolean();
  }
}
