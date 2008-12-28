/**
 * <copyright>
 * </copyright>
 *
 * $Id: TCPAcceptorDef.java,v 1.1 2008-12-28 18:07:29 estepper Exp $
 */
package org.eclipse.net4j.net4jdefs;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TCP Acceptor Def</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.net4j.net4jdefs.TCPAcceptorDef#getHost <em>Host</em>}</li>
 *   <li>{@link org.eclipse.net4j.net4jdefs.TCPAcceptorDef#getPort <em>Port</em>}</li>
 *   <li>{@link org.eclipse.net4j.net4jdefs.TCPAcceptorDef#getTcpSelectorDef <em>Tcp Selector Def</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.net4j.net4jdefs.Net4jDefsPackage#getTCPAcceptorDef()
 * @model
 * @generated
 */
public interface TCPAcceptorDef extends AcceptorDef {

	/**
	 * Returns the value of the '<em><b>Host</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host</em>' attribute.
	 * @see #setHost(String)
	 * @see org.eclipse.net4j.net4jdefs.Net4jDefsPackage#getTCPAcceptorDef_Host()
	 * @model required="true"
	 * @generated
	 */
	String getHost();

	/**
	 * Sets the value of the '{@link org.eclipse.net4j.net4jdefs.TCPAcceptorDef#getHost <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host</em>' attribute.
	 * @see #getHost()
	 * @generated
	 */
	void setHost(String value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' attribute.
	 * @see #setPort(int)
	 * @see org.eclipse.net4j.net4jdefs.Net4jDefsPackage#getTCPAcceptorDef_Port()
	 * @model required="true"
	 * @generated
	 */
	int getPort();

	/**
	 * Sets the value of the '{@link org.eclipse.net4j.net4jdefs.TCPAcceptorDef#getPort <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' attribute.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(int value);

	/**
	 * Returns the value of the '<em><b>Tcp Selector Def</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tcp Selector Def</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tcp Selector Def</em>' reference.
	 * @see #setTcpSelectorDef(TCPSelectorDef)
	 * @see org.eclipse.net4j.net4jdefs.Net4jDefsPackage#getTCPAcceptorDef_TcpSelectorDef()
	 * @model required="true"
	 * @generated
	 */
	TCPSelectorDef getTcpSelectorDef();

	/**
	 * Sets the value of the '{@link org.eclipse.net4j.net4jdefs.TCPAcceptorDef#getTcpSelectorDef <em>Tcp Selector Def</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tcp Selector Def</em>' reference.
	 * @see #getTcpSelectorDef()
	 * @generated
	 */
	void setTcpSelectorDef(TCPSelectorDef value);
} // TCPAcceptorDef
