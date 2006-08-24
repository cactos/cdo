/***************************************************************************
 * Copyright (c) 2004, 2005, 2006 Eike Stepper, Fuggerstr. 39, 10777 Berlin, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.emf.cdo.tests;


import org.eclipse.emf.cdo.tests.topology.ITopologyConstants;
import org.eclipse.emf.cdo.tests.topology.TopologySuite;

import junit.framework.Test;
import junit.framework.TestSuite;


public class AllSuites extends TestSuite
{
  public static Test suite()
  {
    return new AllSuites("CDO Build JUnit Test Suite");
  }

  public AllSuites()
  {
    super();
    populateSuite();
  }

  public AllSuites(Class theClass)
  {
    super(theClass);
    populateSuite();
  }

  public AllSuites(String name)
  {
    super(name);
    populateSuite();
  }

  protected void populateSuite()
  {
    for (String mode : ITopologyConstants.ALL_SELF_CONTAINED_MODES)
    {
      TopologySuite topologySuite = new TopologySuite(mode);
      Test[] suites = createPackageSuites();
      for (int i = 0; i < suites.length; i++)
      {
        topologySuite.addTest(suites[i]);
      }

      addTest(topologySuite);
    }
  }

  protected Test[] createPackageSuites()
  {
    return new Test[] { //
    org.eclipse.emf.cdo.tests.AllTests.suite(), //
        org.eclipse.emf.cdo.tests.model1.AllTests.suite() //
    };
  }
}
