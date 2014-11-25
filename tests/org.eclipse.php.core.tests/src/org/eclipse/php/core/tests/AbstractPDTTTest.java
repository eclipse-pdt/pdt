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
package org.eclipse.php.core.tests;

import junit.framework.TestCase;

import org.osgi.framework.Bundle;

/**
 * This is an abstract test for .pdtt tests
 * 
 * @author michael
 */
@Deprecated
abstract public class AbstractPDTTTest extends TestCase {

	public AbstractPDTTTest() {
		super();
	}

	public AbstractPDTTTest(String name) {
		super(name);
	}

	protected static String[] getPDTTFiles(String testsDirectory) {
		return PDTTUtils.getPDTTFiles(testsDirectory, PHPCoreTests.getDefault()
				.getBundle());
	}

	protected static String[] getPDTTFiles(String testsDirectory, Bundle bundle) {
		return PDTTUtils.getFiles(testsDirectory, bundle, ".pdtt");
	}

	protected static String[] getFiles(String testsDirectory, String ext) {
		return PDTTUtils.getFiles(testsDirectory, PHPCoreTests.getDefault()
				.getBundle(), ext);
	}

	protected static String[] getFiles(String testsDirectory, Bundle bundle,
			String ext) {
		return PDTTUtils.getFiles(testsDirectory, bundle, ext);
	}

	protected void assertContents(String expected, String actual) {
		String diff = PHPCoreTests.compareContents(expected, actual);
		if (diff != null) {
			fail(diff);
		}
	}
}
