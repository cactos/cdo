/**
 * <copyright>
 * </copyright>
 *
 * $Id: ResponseNegotiatorDefImpl.java,v 1.1 2008-12-28 18:07:28 estepper Exp $
 */
package org.eclipse.net4j.net4jdefs.impl;

import org.eclipse.net4j.net4jdefs.CredentialsProviderDef;
import org.eclipse.net4j.net4jdefs.Net4jDefsPackage;
import org.eclipse.net4j.net4jdefs.ResponseNegotiatorDef;
import org.eclipse.net4j.util.security.IPasswordCredentialsProvider;
import org.eclipse.net4j.util.security.ResponseNegotiator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Response Negotiator Def</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.net4j.net4jdefs.impl.ResponseNegotiatorDefImpl#getCredentialsProvider <em>Credentials Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResponseNegotiatorDefImpl extends NegotiatorDefImpl implements
		ResponseNegotiatorDef {
	/**
	 * The cached value of the '{@link #getCredentialsProvider() <em>Credentials Provider</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getCredentialsProvider()
	 * @generated
	 * @ordered
	 */
	protected CredentialsProviderDef credentialsProvider;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ResponseNegotiatorDefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Net4jDefsPackage.Literals.RESPONSE_NEGOTIATOR_DEF;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CredentialsProviderDef getCredentialsProvider() {
		if (credentialsProvider != null && credentialsProvider.eIsProxy()) {
			InternalEObject oldCredentialsProvider = (InternalEObject)credentialsProvider;
			credentialsProvider = (CredentialsProviderDef)eResolveProxy(oldCredentialsProvider);
			if (credentialsProvider != oldCredentialsProvider) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, Net4jDefsPackage.RESPONSE_NEGOTIATOR_DEF__CREDENTIALS_PROVIDER, oldCredentialsProvider, credentialsProvider));
			}
		}
		return credentialsProvider;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CredentialsProviderDef basicGetCredentialsProvider() {
		return credentialsProvider;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setCredentialsProvider(
			CredentialsProviderDef newCredentialsProvider) {
		CredentialsProviderDef oldCredentialsProvider = credentialsProvider;
		credentialsProvider = newCredentialsProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Net4jDefsPackage.RESPONSE_NEGOTIATOR_DEF__CREDENTIALS_PROVIDER, oldCredentialsProvider, credentialsProvider));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Net4jDefsPackage.RESPONSE_NEGOTIATOR_DEF__CREDENTIALS_PROVIDER:
				if (resolve) return getCredentialsProvider();
				return basicGetCredentialsProvider();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case Net4jDefsPackage.RESPONSE_NEGOTIATOR_DEF__CREDENTIALS_PROVIDER:
				setCredentialsProvider((CredentialsProviderDef)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case Net4jDefsPackage.RESPONSE_NEGOTIATOR_DEF__CREDENTIALS_PROVIDER:
				setCredentialsProvider((CredentialsProviderDef)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case Net4jDefsPackage.RESPONSE_NEGOTIATOR_DEF__CREDENTIALS_PROVIDER:
				return credentialsProvider != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	protected Object createInstance() {
		ResponseNegotiator responseNegotiator = new ResponseNegotiator();
		responseNegotiator
				.setCredentialsProvider((IPasswordCredentialsProvider) getCredentialsProvider()
						.getInstance());
		return responseNegotiator;
	}

} // ResponseNegotiatorDefImpl
