/*
 * Copyright (c) 2008, 2009, 2011, 2012, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package base.util;

import base.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import base.BaseClass;
import base.BasePackage;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 * @see base.BasePackage
 * @generated
 */
public class BaseAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected static BasePackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public BaseAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = BasePackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc --> This implementation
   * returns <code>true</code> if the object is either the model's package or is an instance object of the model. <!--
   * end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected BaseSwitch<Adapter> modelSwitch = new BaseSwitch<Adapter>()
  {
    @Override
    public Adapter caseBaseClass(BaseClass object)
    {
      return createBaseClassAdapter();
    }

    @Override
    public Adapter caseDocument(Document object)
    {
      return createDocumentAdapter();
    }

    @Override
    public Adapter caseElement(Element object)
    {
      return createElementAdapter();
    }

    @Override
    public Adapter defaultCase(EObject object)
    {
      return createEObjectAdapter();
    }
  };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }

  /**
   * Creates a new adapter for an object of class '{@link base.BaseClass <em>Class</em>}'.
   * <!-- begin-user-doc --> This
   * default implementation returns null so that we can easily ignore cases; it's useful to ignore a case when
   * inheritance will catch all the cases anyway. <!-- end-user-doc -->
   * @return the new adapter.
   * @see base.BaseClass
   * @generated
   */
  public Adapter createBaseClassAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link base.Document <em>Document</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see base.Document
   * @generated
   */
  public Adapter createDocumentAdapter()
  {
    return null;
  }

  /**
  	 * Creates a new adapter for an object of class '{@link base.Element <em>Element</em>}'.
  	 * <!-- begin-user-doc -->
  	 * This default implementation returns null so that we can easily ignore cases;
  	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
  	 * <!-- end-user-doc -->
  	 * @return the new adapter.
  	 * @see base.Element
  	 * @generated
  	 */
  public Adapter createElementAdapter()
  {
    return null;
  }

  /**
  	 * Creates a new adapter for the default case.
  	 * <!-- begin-user-doc --> This default implementation returns null. <!--
   * end-user-doc -->
  	 * @return the new adapter.
  	 * @generated
  	 */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} // BaseAdapterFactory
