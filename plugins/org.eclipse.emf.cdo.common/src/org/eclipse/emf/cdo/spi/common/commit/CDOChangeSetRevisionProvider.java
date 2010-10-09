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
package org.eclipse.emf.cdo.spi.common.commit;

import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.revision.CDORevisionProvider;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDeltaProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eike Stepper
 * @since 4.0
 */
public class CDOChangeSetRevisionProvider implements CDORevisionProvider
{
  private static final CDOIDAndVersion DETACHED = new CDOIDAndVersion()
  {
    public CDOID getID()
    {
      return CDOID.NULL;
    }

    public int getVersion()
    {
      return Integer.MIN_VALUE;
    }

    @Override
    public String toString()
    {
      return "DETACHED";
    }
  };

  private CDORevisionProvider delegate;

  private CDOChangeSetData changeSet;

  private CDORevisionProvider revisionCallback;

  private CDORevisionDeltaProvider revisionDeltaCallback;

  private Map<CDOID, CDOIDAndVersion> cachedRevisions;

  public CDOChangeSetRevisionProvider(CDORevisionProvider delegate, CDOChangeSetData changeSet,
      CDORevisionProvider revisionCallback, CDORevisionDeltaProvider revisionDeltaCallback)
  {
    this.delegate = delegate;
    this.changeSet = changeSet;
    this.revisionCallback = revisionCallback;
    this.revisionDeltaCallback = revisionDeltaCallback;
  }

  public CDOChangeSetRevisionProvider(CDORevisionProvider delegate, CDOChangeSetData changeSet)
  {
    this(delegate, changeSet, null, null);
  }

  public synchronized CDORevision getRevision(CDOID id)
  {
    if (cachedRevisions == null)
    {
      cachedRevisions = cacheRevisions();
    }

    CDOIDAndVersion key = cachedRevisions.get(id);
    if (key == DETACHED)
    {
      return null;
    }

    if (key instanceof CDORevision) // New object (eager)
    {
      return (CDORevision)key;
    }

    if (key instanceof CDORevisionDelta) // Changed object (eager)
    {
      CDORevisionDelta revisionDelta = (CDORevisionDelta)key;
      return applyDelta(revisionDelta);
    }

    if (key instanceof CDORevisionKey) // Changed object (lazy)
    {
      CDORevisionDelta revisionDelta = revisionDeltaCallback.getRevisionDelta(id);
      return applyDelta(revisionDelta);
    }

    if (key != null) // New object (lazy)
    {
      CDORevision revision = revisionCallback.getRevision(id);
      cachedRevisions.put(id, revision);
      return revision;
    }

    return delegate.getRevision(id);
  }

  private Map<CDOID, CDOIDAndVersion> cacheRevisions()
  {
    Map<CDOID, CDOIDAndVersion> cache = new HashMap<CDOID, CDOIDAndVersion>();
  
    for (CDOIDAndVersion key : changeSet.getNewObjects())
    {
      if (revisionCallback == null && !(key instanceof CDORevision))
      {
        throw new IllegalStateException("No callback installed to lazily obtain revision " + key);
      }
  
      cache.put(key.getID(), key);
    }
  
    for (CDORevisionKey key : changeSet.getChangedObjects())
    {
      if (revisionDeltaCallback == null && !(key instanceof CDORevisionDelta))
      {
        throw new IllegalStateException("No callback installed to lazily obtain revision delta " + key);
      }
  
      cache.put(key.getID(), key);
    }
  
    for (CDOIDAndVersion key : changeSet.getDetachedObjects())
    {
      cache.put(key.getID(), DETACHED);
    }
  
    return cache;
  }

  private CDORevision applyDelta(CDORevisionDelta revisionDelta)
  {
    CDOID id = revisionDelta.getID();
    CDORevision changedObject = delegate.getRevision(id);
    revisionDelta.apply(changedObject);
    cachedRevisions.put(id, changedObject);
    return changedObject;
  }
}
