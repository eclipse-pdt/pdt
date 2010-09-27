/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests.performance.zf;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.php.core.tests.performance.ProjectSuite;
import org.eclipse.php.internal.core.PHPVersion;

/**
 * Various performance tests performed on Zend Framework project.
 * 
 * @author Michael
 * 
 */
public class ZFPerformanceTests {

	private static final String PROJECT = "ZendFramework";
	private static final String URL = "http://framework.zend.com/releases/ZendFramework-1.9.5/ZendFramework-1.9.5.zip";
	private static final String TYPE = "Zend_Exception";

	public static Test suite() {
		TestSuite suite = new TestSuite(PROJECT + " Performance Tests");
		Map map = new HashMap();
		map.put(ProjectSuite.PROJECT, PROJECT);
		map.put(ProjectSuite.URL, URL);
		map.put(ProjectSuite.TYPE, TYPE);
		map.put(ProjectSuite.PHP_VERSION, PHPVersion.PHP5);
		map.put(ProjectSuite.CHANGE_INCLUDE_PATH, Boolean.TRUE);
		map.put(ProjectSuite.INCLUDE_PATH, new String[] { "library" });
		map.put(ProjectSuite.REFERENCED_FILE,
				new String[] { "library/Zend/View/Helper/Abstract.php" });
		map.put(ProjectSuite.REFERENCING_FILE,
				new String[] { "library/Zend/View/Helper/Action.php" });
		Test test = new ProjectSuite().suite(map);
		suite.addTest(test);

		map = new HashMap();
		map.put(ProjectSuite.PROJECT, PROJECT);
		map.put(ProjectSuite.URL, URL);
		map.put(ProjectSuite.TYPE, TYPE);
		map.put(ProjectSuite.PHP_VERSION, PHPVersion.PHP5_3);
		map.put(ProjectSuite.CHANGE_INCLUDE_PATH, Boolean.TRUE);
		map.put(ProjectSuite.INCLUDE_PATH, new String[] { "library" });
		test = new ProjectSuite().suite(map);
		suite.addTest(test);

		return suite;
	}

}
