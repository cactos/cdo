/**
 * <copyright>
 * </copyright>
 *
 * $Id: HTTPConnectorDef.java,v 1.1 2008-12-28 18:07:29 estepper Exp $
 */
package org.eclipse.net4j.net4jdefs;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>HTTP Connector Def</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.net4j.net4jdefs.HTTPConnectorDef#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.net4j.net4jdefs.Net4jDefsPackage#getHTTPConnectorDef()
 * @model
 * @generated
 */
public interface HTTPConnectorDef extends ConnectorDef {

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see org.eclipse.net4j.net4jdefs.Net4jDefsPackage#getHTTPConnectorDef_Url()
	 * @model
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link org.eclipse.net4j.net4jdefs.HTTPConnectorDef#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);
} // HTTPConnectorDef
