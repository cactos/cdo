/*
 * Copyright (c) 2008, 2009, 2011, 2012, 2015 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.buddies.internal.common;

import org.eclipse.net4j.buddies.common.IBuddy;
import org.eclipse.net4j.buddies.common.ICollaboration;
import org.eclipse.net4j.buddies.common.IMembership;
import org.eclipse.net4j.buddies.common.IMembershipContainer;
import org.eclipse.net4j.buddies.common.IMembershipKey;
import org.eclipse.net4j.util.container.IContainerDelta;
import org.eclipse.net4j.util.container.SingleDeltaContainerEvent;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.lifecycle.ILifecycleEvent;
import org.eclipse.net4j.util.lifecycle.Lifecycle;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Eike Stepper
 */
public class MembershipContainer extends Lifecycle implements IMembershipContainer, IListener
{
  private ConcurrentMap<IMembershipKey, IMembership> memberships = new ConcurrentHashMap<IMembershipKey, IMembership>();

  public MembershipContainer()
  {
  }

  public void addMembership(IMembership membership)
  {
    if (memberships.putIfAbsent(membership, membership) == null)
    {
      IListener[] listeners = getListeners();
      if (listeners != null)
      {
        fireEvent(new SingleDeltaContainerEvent<IMembership>(this, membership, IContainerDelta.Kind.ADDED), listeners);
      }

      membership.addListener(this);
    }
  }

  public IMembership removeMembership(IBuddy buddy, ICollaboration collaboration)
  {
    return removeMembership(new MembershipKey(buddy, collaboration));
  }

  public IMembership removeMembership(IMembershipKey key)
  {
    // for (IMembership membership : memberships.values())
    // {
    //
    // }
    //
    IMembership membership = memberships.remove(key);
    if (membership != null)
    {
      membership.removeListener(this);
      IListener[] listeners = getListeners();
      if (listeners != null)
      {
        fireEvent(new SingleDeltaContainerEvent<IMembership>(this, membership, IContainerDelta.Kind.REMOVED),
            listeners);
      }
    }

    return membership;
  }

  public IMembership[] getMemberships()
  {
    return memberships.values().toArray(new IMembership[memberships.size()]);
  }

  public IMembership getMembership(IBuddy buddy, ICollaboration collaboration)
  {
    return memberships.get(new MembershipKey(buddy, collaboration));
  }

  public IMembership[] getElements()
  {
    return getMemberships();
  }

  public boolean isEmpty()
  {
    return memberships.isEmpty();
  }

  public void notifyEvent(IEvent event)
  {
    if (event.getSource() instanceof IMembership)
    {
      IMembership membership = (IMembership)event.getSource();
      notifyMembershipEvent(event);
      if (event instanceof ILifecycleEvent)
      {
        ILifecycleEvent e = (ILifecycleEvent)event;
        if (e.getKind() == ILifecycleEvent.Kind.DEACTIVATED)
        {
          removeMembership(membership);
        }
      }
    }
  }

  protected void notifyMembershipEvent(IEvent event)
  {
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    for (IMembership membership : getMemberships())
    {
      membership.removeListener(this);
    }

    super.doDeactivate();
  }
}
