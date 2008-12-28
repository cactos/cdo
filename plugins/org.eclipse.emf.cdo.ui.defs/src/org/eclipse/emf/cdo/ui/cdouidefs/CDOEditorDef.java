/**
 * <copyright>
 * </copyright>
 *
 * $Id: CDOEditorDef.java,v 1.1 2008-12-28 18:05:24 estepper Exp $
 */
package org.eclipse.emf.cdo.ui.cdouidefs;

import org.eclipse.emf.cdo.cdodefs.CDOViewDef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CDO Editor Def</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.cdo.ui.cdouidefs.CDOEditorDef#getCdoView <em>Cdo View</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.ui.cdouidefs.CDOEditorDef#getResourcePath <em>Resource Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.cdo.ui.cdouidefs.CDOUIDefsPackage#getCDOEditorDef()
 * @model
 * @generated
 */
public interface CDOEditorDef extends EditorDef {
	/**
   * Returns the value of the '<em><b>Cdo View</b></em>' reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cdo View</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Cdo View</em>' reference.
   * @see #setCdoView(CDOViewDef)
   * @see org.eclipse.emf.cdo.ui.cdouidefs.CDOUIDefsPackage#getCDOEditorDef_CdoView()
   * @model required="true"
   * @generated
   */
	CDOViewDef getCdoView();

	/**
   * Sets the value of the '{@link org.eclipse.emf.cdo.ui.cdouidefs.CDOEditorDef#getCdoView <em>Cdo View</em>}' reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Cdo View</em>' reference.
   * @see #getCdoView()
   * @generated
   */
	void setCdoView(CDOViewDef value);

	/**
   * Returns the value of the '<em><b>Resource Path</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Resource Path</em>' attribute.
   * @see #setResourcePath(String)
   * @see org.eclipse.emf.cdo.ui.cdouidefs.CDOUIDefsPackage#getCDOEditorDef_ResourcePath()
   * @model required="true"
   * @generated
   */
	String getResourcePath();

	/**
   * Sets the value of the '{@link org.eclipse.emf.cdo.ui.cdouidefs.CDOEditorDef#getResourcePath <em>Resource Path</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Resource Path</em>' attribute.
   * @see #getResourcePath()
   * @generated
   */
	void setResourcePath(String value);

} // CDOEditorDef
