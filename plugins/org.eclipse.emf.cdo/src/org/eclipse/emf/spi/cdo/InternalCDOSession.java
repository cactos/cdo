/*
 * Copyright (c) 2009-2013, 2015, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.spi.cdo;

import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOChangeSet;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDGenerator;
import org.eclipse.emf.cdo.common.lob.CDOLobStore;
import org.eclipse.emf.cdo.common.lock.CDOLockChangeInfo;
import org.eclipse.emf.cdo.common.protocol.CDOProtocol.CommitNotificationInfo;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.security.CDOPermission;
import org.eclipse.emf.cdo.session.CDORepositoryInfo;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranch;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;
import org.eclipse.emf.cdo.spi.common.commit.CDORevisionAvailabilityInfo;
import org.eclipse.emf.cdo.spi.common.commit.InternalCDOCommitInfoManager;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry.PackageLoader;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry.PackageProcessor;
import org.eclipse.emf.cdo.spi.common.revision.CDORevisionUnchunker;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager.RevisionLocker;
import org.eclipse.emf.cdo.view.CDOFetchRuleManager;

import org.eclipse.net4j.util.lifecycle.ILifecycle;
import org.eclipse.net4j.util.security.IPasswordCredentialsProvider;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.spi.cdo.CDOSessionProtocol.RefreshSessionResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 2.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalCDOSession
    extends CDOSession, PackageProcessor, PackageLoader, RevisionLocker, CDORevisionUnchunker, ILifecycle
{
  public CDOSessionProtocol getSessionProtocol();

  /**
   * @since 3.0
   */
  public void setSessionProtocol(CDOSessionProtocol sessionProtocol);

  /**
   * @since 4.0
   * @deprecated As of 4.2 use {@link #getCredentialsProvider()}
   */
  @Deprecated
  public org.eclipse.emf.cdo.common.protocol.CDOAuthenticator getAuthenticator();

  /**
   * @since 4.0
   * @deprecated As of 4.2 use {@link #setCredentialsProvider(IPasswordCredentialsProvider)}
   */
  @Deprecated
  public void setAuthenticator(org.eclipse.emf.cdo.common.protocol.CDOAuthenticator authenticator);

  /**
   * @since 4.2
   */
  public void setCredentialsProvider(IPasswordCredentialsProvider credentialsProvider);

  public InternalCDOPackageRegistry getPackageRegistry();

  /**
   * @since 4.0
   */
  public void setPackageRegistry(InternalCDOPackageRegistry packageRegistry);

  /**
   * @since 3.0
   */
  public InternalCDOBranchManager getBranchManager();

  /**
   * @since 4.0
   */
  public void setBranchManager(InternalCDOBranchManager branchManager);

  /**
   * @since 3.0
   */
  public InternalCDORevisionManager getRevisionManager();

  /**
   * @since 4.0
   */
  public void setRevisionManager(InternalCDORevisionManager revisionManager);

  /**
   * @since 3.0
   */
  public InternalCDOCommitInfoManager getCommitInfoManager();

  /**
   * @since 4.0
   */
  public void setCommitInfoManager(InternalCDOCommitInfoManager commitInfoManager);

  /**
   * @since 3.0
   */
  public InternalCDORemoteSessionManager getRemoteSessionManager();

  /**
   * @since 3.0
   */
  public void setRemoteSessionManager(InternalCDORemoteSessionManager remoteSessionManager);

  /**
   * @since 4.0
   */
  public CDOLobStore getLobStore();

  public void setExceptionHandler(CDOSession.ExceptionHandler exceptionHandler);

  /**
   * @since 4.1
   */
  public void setIDGenerator(CDOIDGenerator idGenerator);

  /**
   * @since 3.0
   */
  public void setFetchRuleManager(CDOFetchRuleManager fetchRuleManager);

  /**
   * @since 3.0
   */
  public void setRepositoryInfo(CDORepositoryInfo repositoryInfo);

  /**
   * @since 3.0
   */
  public void setSessionID(int sessionID);

  public void setUserID(String userID);

  /**
   * @since 3.0
   */
  public void setLastUpdateTime(long lastUpdateTime);

  /**
   * Initiates (possibly interactive) reset of credentials for the specified user.
   * This is an optional operation of the session and only available for administrators.
   *
   * @param userID the ID of the user for which to reset credentials
   * @throws UnsupportedOperationException if the session implementation does not permit resetting user credentials
   *
   * @since 4.3
   * @see #getCredentialsProvider()
   */
  public void resetCredentials(String userID);

  public void viewDetached(InternalCDOView view);

  /**
   * @since 3.0
   */
  public Object resolveElementProxy(CDORevision revision, EStructuralFeature feature, int accessIndex, int serverIndex);

  /**
   * @since 4.0
   */
  public void resolveAllElementProxies(CDORevision revision);

  /**
   * @since 3.0
   */
  public void handleRepositoryTypeChanged(CDOCommonRepository.Type oldType, CDOCommonRepository.Type newType);

  /**
   * @since 3.0
   */
  public void handleRepositoryStateChanged(CDOCommonRepository.State oldState, CDOCommonRepository.State newState);

  /**
   * @since 3.0
   * @deprecated As of 4.3 no longer supported.
   */
  @Deprecated
  public void handleBranchNotification(InternalCDOBranch branch);

  /**
   * @since 3.0
   * @deprecated As of 4.2 use {@link #handleCommitNotification(CDOCommitInfo, boolean)}.
   */
  @Deprecated
  public void handleCommitNotification(CDOCommitInfo commitInfo);

  /**
   * @since 4.2
   * @deprecated As of 4.3 use {@link #handleCommitNotification(CommitNotificationInfo)}.
   */
  @Deprecated
  public void handleCommitNotification(CDOCommitInfo commitInfo, boolean clearResourcePathCache);

  /**
   * @since 4.3
   */
  public void handleCommitNotification(CommitNotificationInfo info);

  /**
   * @since 4.1
   */
  public void handleLockNotification(CDOLockChangeInfo lockChangeInfo, InternalCDOView sender);

  /**
   * @since 4.3
   */
  public Object startLocalCommit();

  /**
   * @since 4.3
   */
  public void endLocalCommit(Object token);

  /**
   * @since 3.0
   * @deprecated As of 4.2 use {@link #invalidate(CDOCommitInfo, InternalCDOTransaction, boolean)}.
   */
  @Deprecated
  public void invalidate(CDOCommitInfo commitInfo, InternalCDOTransaction sender);

  /**
   * @since 4.2
   * @deprecated As of 4.3 use {@link #invalidate(CDOCommitInfo, InternalCDOTransaction, boolean, byte, Map)}.
   */
  @Deprecated
  public void invalidate(CDOCommitInfo commitInfo, InternalCDOTransaction sender, boolean clearResourcePathCache);

  /**
   * @since 4.3
   * @deprecated As of 4.6 use {@link #invalidate(InvalidationData)}.
   */
  @Deprecated
  public void invalidate(CDOCommitInfo commitInfo, InternalCDOTransaction sender, boolean clearResourcePathCache,
      byte securityImpact, Map<CDOID, CDOPermission> newPermissions);

  /**
   * @since 4.6
   */
  public void invalidate(InvalidationData invalidationData);

  /**
   * @since 3.0
   */
  public void processRefreshSessionResult(RefreshSessionResult result, CDOBranch branch,
      List<InternalCDOView> branchViews, Map<CDOBranch, Map<CDOID, InternalCDORevision>> viewedRevisions);

  /**
   * @since 4.0
   */
  public boolean isSticky();

  /**
   * @since 4.0
   */
  public CDOBranchPoint getCommittedSinceLastRefresh(CDOID id);

  /**
   * @since 4.0
   */
  public void setCommittedSinceLastRefresh(CDOID id, CDOBranchPoint branchPoint);

  /**
   * @since 4.0
   */
  public void clearCommittedSinceLastRefresh();

  /**
   * @since 4.0
   */
  public void setMainBranchLocal(boolean mainBranchLocal);

  /**
   * @since 4.0
   * @deprecated As of 4.2 not used anymore.
   */
  @Deprecated
  public CDORevisionAvailabilityInfo createRevisionAvailabilityInfo(CDOBranchPoint branchPoint);

  /**
   * @since 4.0
   * @deprecated As of 4.2 not used anymore.
   */
  @Deprecated
  public void cacheRevisions(CDORevisionAvailabilityInfo info);

  /**
   * @since 4.2
   */
  public MergeData getMergeData(CDOBranchPoint target, CDOBranchPoint source, CDOBranchPoint sourceBase,
      boolean computeChangeSets);

  /**
   * @since 4.6
   */
  public MergeData getMergeData(CDOBranchPoint target, CDOBranchPoint source, CDOBranchPoint targetBase,
      CDOBranchPoint sourceBase, boolean computeChangeSets);

  /**
   * @author Eike Stepper
   * @since 4.5
   */
  public static final class CommitToken
  {
    private final int commitNumber;

    private final String threadName;

    public CommitToken(int commitNumber, String threadName)
    {
      this.commitNumber = commitNumber;
      this.threadName = threadName;
    }

    public int getCommitNumber()
    {
      return commitNumber;
    }

    @Override
    public boolean equals(Object obj)
    {
      return commitNumber == ((CommitToken)obj).commitNumber;
    }

    @Override
    public int hashCode()
    {
      return commitNumber;
    }

    @Override
    public String toString()
    {
      return "LocalCommit[" + getCommitNumber() + (threadName == null ? "" : ", " + threadName) + "]";
    }
  }

  /**
   * A data structure that holds all input values of {@link InternalCDOSession#invalidate(InvalidationData) InternalCDOSession#invalidate()}.
   *
   * @author Eike Stepper
   * @since 4.6
   */
  public static final class InvalidationData
  {
    private CDOCommitInfo commitInfo;

    private InternalCDOTransaction sender;

    private boolean clearResourcePathCache;

    private byte securityImpact;

    private Map<CDOID, CDOPermission> newPermissions;

    private CDOLockChangeInfo lockChangeInfo;

    public InvalidationData()
    {
    }

    public CDOCommitInfo getCommitInfo()
    {
      return commitInfo;
    }

    public void setCommitInfo(CDOCommitInfo commitInfo)
    {
      this.commitInfo = commitInfo;
    }

    public InternalCDOTransaction getSender()
    {
      return sender;
    }

    public void setSender(InternalCDOTransaction sender)
    {
      this.sender = sender;
    }

    public boolean isClearResourcePathCache()
    {
      return clearResourcePathCache;
    }

    public void setClearResourcePathCache(boolean clearResourcePathCache)
    {
      this.clearResourcePathCache = clearResourcePathCache;
    }

    public byte getSecurityImpact()
    {
      return securityImpact;
    }

    public void setSecurityImpact(byte securityImpact)
    {
      this.securityImpact = securityImpact;
    }

    public Map<CDOID, CDOPermission> getNewPermissions()
    {
      return newPermissions;
    }

    public void setNewPermissions(Map<CDOID, CDOPermission> newPermissions)
    {
      this.newPermissions = newPermissions;
    }

    public CDOLockChangeInfo getLockChangeInfo()
    {
      return lockChangeInfo;
    }

    public void setLockChangeInfo(CDOLockChangeInfo lockChangeInfo)
    {
      this.lockChangeInfo = lockChangeInfo;
    }

    @Override
    public String toString()
    {
      StringBuilder builder = new StringBuilder();
      builder.append("InvalidationData[commitInfo=");
      builder.append(commitInfo);
      builder.append(", sender=");
      builder.append(sender);
      builder.append(", clearResourcePathCache=");
      builder.append(clearResourcePathCache);
      builder.append(", securityImpact=");
      builder.append(securityImpact);
      builder.append(", newPermissions=");
      builder.append(newPermissions);
      builder.append(", lockChangeInfo=");
      builder.append(lockChangeInfo);
      builder.append("]");
      return builder.toString();
    }
  }

  /**
   * A data structure that holds all input and output values of {@link InternalCDOSession#getMergeData(CDOBranchPoint, CDOBranchPoint, CDOBranchPoint, boolean) InternalCDOSession.getMergeData()}.
   *
   * @author Eike Stepper
   * @since 4.2
   */
  public static final class MergeData
  {
    private final CDOBranchPoint target;

    private final CDOBranchPoint source;

    private final CDOBranchPoint sourceBase;

    private final CDOBranchPoint targetBase;

    private final CDORevisionAvailabilityInfo targetInfo;

    private final CDORevisionAvailabilityInfo sourceInfo;

    private final CDORevisionAvailabilityInfo sourceBaseInfo;

    private final CDORevisionAvailabilityInfo targetBaseInfo;

    private final Set<CDOID> ids;

    private final CDOChangeSet targetChanges;

    private final CDOChangeSet sourceChanges;

    public MergeData(CDOBranchPoint target, CDOBranchPoint source, CDOBranchPoint sourceBase, CDOBranchPoint targetBase,
        CDORevisionAvailabilityInfo targetInfo, CDORevisionAvailabilityInfo sourceInfo,
        CDORevisionAvailabilityInfo sourceBaseInfo, CDORevisionAvailabilityInfo targetBaseInfo, Set<CDOID> ids,
        CDOChangeSet targetChanges, CDOChangeSet sourceChanges)
    {
      this.target = target;
      this.source = source;
      this.sourceBase = sourceBase;
      this.targetBase = targetBase;
      this.targetInfo = targetInfo;
      this.sourceInfo = sourceInfo;
      this.sourceBaseInfo = sourceBaseInfo;
      this.targetBaseInfo = targetBaseInfo;
      this.ids = ids;
      this.targetChanges = targetChanges;
      this.sourceChanges = sourceChanges;
    }

    public CDOBranchPoint getTarget()
    {
      return target;
    }

    public CDOBranchPoint getSource()
    {
      return source;
    }

    public CDOBranchPoint getSourceBase()
    {
      return sourceBase;
    }

    /**
     * @since 4.6
     */
    public CDOBranchPoint getTargetBase()
    {
      return targetBase;
    }

    public CDORevisionAvailabilityInfo getTargetInfo()
    {
      return targetInfo;
    }

    public CDORevisionAvailabilityInfo getSourceInfo()
    {
      return sourceInfo;
    }

    /**
     * @since 4.6
     */
    public CDORevisionAvailabilityInfo getSourceBaseInfo()
    {
      return sourceBaseInfo;
    }

    /**
     * @since 4.6
     */
    public CDORevisionAvailabilityInfo getTargetBaseInfo()
    {
      return targetBaseInfo;
    }

    public Set<CDOID> getIDs()
    {
      return ids;
    }

    public CDOChangeSet getTargetChanges()
    {
      return targetChanges;
    }

    public CDOChangeSet getSourceChanges()
    {
      return sourceChanges;
    }

    /**
     * @deprecated As of 4.6 use {@link #getTargetBase()}.
     */
    @Deprecated
    public CDOBranchPoint getAncestor()
    {
      return getTargetBase();
    }

    /**
     * @deprecated As of 4.6 use {@link #getSourceBaseInfo()}.
     */
    @Deprecated
    public CDORevisionAvailabilityInfo getBaseInfo()
    {
      return getSourceBaseInfo();
    }

    /**
     * @deprecated As of 4.6 use {@link #getTargetBaseInfo()}.
     */
    @Deprecated
    public CDORevisionAvailabilityInfo getAncestorInfo()
    {
      return getTargetBaseInfo();
    }
  }
}
