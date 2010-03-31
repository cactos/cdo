/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ibrahim Sallam - initial API and implementation
 */
package org.eclipse.emf.cdo.server.internal.objectivity.schema;

import com.objy.db.app.ooObj;

public class OoProperty extends ooObj
{
  protected String name;

  protected String value;

  public OoProperty(String name, String value)
  {
    this.name = name;
    this.value = value;
  }

  public String getKey()
  {
    fetch();
    return name;
  }

  public void setName(String name)
  {
    markModified();
    this.name = name;
  }

  public String getValue()
  {
    fetch();
    return value;
  }

  public void setValue(String value)
  {
    markModified();
    this.value = value;
  }
}
