/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *     Dawid Pakuła - 457924
 *******************************************************************************/
package org.eclipse.php.refactoring.core.test;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.internal.core.Logger;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ org.eclipse.php.refactoring.core.rename.AllTests.class,
		org.eclipse.php.refactoring.core.move.AllTests.class,
		org.eclipse.php.refactoring.core.extract.function.AllTests.class,
		org.eclipse.php.refactoring.core.extract.variable.AllTests.class,
		org.eclipse.php.refactoring.ui.actions.AllTests.class,
		org.eclipse.php.refactoring.core.changes.AllTests.class })
public class AllTests {

	@AfterClass
	public static void tearDown() {
		// Save workspace to avoid errors after finishing tests.
		try {
			ResourcesPlugin.getWorkspace().save(true, new NullProgressMonitor());
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

}
