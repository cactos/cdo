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
package org.eclipse.net4j.signal;

import org.eclipse.net4j.buffer.BufferInputStream;
import org.eclipse.net4j.buffer.BufferOutputStream;
import org.eclipse.net4j.signal.monitor.ISignalMonitor;
import org.eclipse.net4j.signal.monitor.MonitorCanceledException;
import org.eclipse.net4j.signal.monitor.SignalMonitor;
import org.eclipse.net4j.util.concurrent.ConcurrencyUtil;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.io.ExtendedDataOutputStream;

import org.eclipse.internal.net4j.bundle.OM;

import java.util.concurrent.ExecutorService;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public abstract class IndicationWithMonitoring extends IndicationWithResponse
{
  private SignalMonitor monitor;

  private long lastMonitorAccess;

  /**
   * @since 2.0
   */
  public IndicationWithMonitoring(SignalProtocol<?> protocol, short id, String name)
  {
    super(protocol, id, name);
  }

  /**
   * @since 2.0
   */
  public IndicationWithMonitoring(SignalProtocol<?> protocol, short signalID)
  {
    super(protocol, signalID);
  }

  /**
   * @since 2.0
   */
  public IndicationWithMonitoring(SignalProtocol<?> protocol, Enum<?> literal)
  {
    super(protocol, literal);
  }

  @Override
  protected void execute(BufferInputStream in, BufferOutputStream out) throws Exception
  {
    try
    {
      super.execute(in, out);
    }
    finally
    {
      monitor = null;
    }
  }

  @Override
  protected final void indicating(ExtendedDataInputStream in) throws Exception
  {
    final long monitorProgressInterval = in.readLong();
    ExecutorService executorService = getMonitoringExecutorService();
    if (executorService != null)
    {
      monitor = new LastAccessMonitor();
      setLastMonitorAccess();
      executorService.execute(new Runnable()
      {
        public void run()
        {
          while (monitor != null)
          {
            if (System.currentTimeMillis() - lastMonitorAccess > monitorProgressInterval)
            {
              setMonitorCanceled();
              break;
            }

            sendProgress(monitor.getTotalWork(), monitor.getWork());
            ConcurrencyUtil.sleep(monitorProgressInterval);
          }
        }

        private void sendProgress(int totalWork, int work)
        {
          try
          {
            new MonitorProgressRequest(getProtocol(), -getCorrelationID(), totalWork, work).sendAsync();
          }
          catch (Exception ex)
          {
            OM.LOG.error(ex);
          }
        }
      });
    }

    indicating(in, monitor.fork(getIndicatingWorkPercent()));
  }

  @Override
  protected final void responding(ExtendedDataOutputStream out) throws Exception
  {
    responding(out, monitor.fork(getRespondingWorkPercent()));
  }

  protected abstract void indicating(ExtendedDataInputStream in, ISignalMonitor monitor) throws Exception;

  protected abstract void responding(ExtendedDataOutputStream out, ISignalMonitor monitor) throws Exception;

  /**
   * @since 2.0
   */
  protected ExecutorService getMonitoringExecutorService()
  {
    return getProtocol().getExecutorService();
  }

  protected int getIndicatingWorkPercent()
  {
    return 50;
  }

  protected int getRespondingWorkPercent()
  {
    return 50;
  }

  void setMonitorCanceled()
  {
    if (monitor != null)
    {
      monitor.cancel();
    }
  }

  void setLastMonitorAccess()
  {
    lastMonitorAccess = System.currentTimeMillis();
  }

  /**
   * @author Eike Stepper
   */
  private final class LastAccessMonitor extends SignalMonitor
  {
    @Override
    public synchronized void begin(int totalWork)
    {
      setLastMonitorAccess();
      super.begin(totalWork);
    }

    @Override
    public synchronized void checkCanceled() throws MonitorCanceledException
    {
      setLastMonitorAccess();
      super.checkCanceled();
    }

    @Override
    public synchronized boolean isCanceled()
    {
      setLastMonitorAccess();
      return super.isCanceled();
    }

    @Override
    public synchronized void worked(int work)
    {
      setLastMonitorAccess();
      super.worked(work);
    }
  }
}
