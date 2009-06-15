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

import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.osgi.framework.Bundle;

/**
 * This is an abstract test for .pdtt tests
 * @author michael
 */
public class AbstractPDTTTest extends TestCase {

	public AbstractPDTTTest() {
		super();
	}

	public AbstractPDTTTest(String name) {
		super(name);
	}

	protected static String[] getPDTTFiles(String testsDirectory) {
		return getPDTTFiles(testsDirectory, PHPCoreTests.getDefault().getBundle());
	}

	@SuppressWarnings("unchecked")
	protected static String[] getPDTTFiles(String testsDirectory, Bundle bundle) {
		List<String> files = new LinkedList<String>();
		Enumeration<String> entryPaths = bundle.getEntryPaths(testsDirectory);
		if (entryPaths != null) {
			while (entryPaths.hasMoreElements()) {
				final String path = (String) entryPaths.nextElement();
				URL entry = bundle.getEntry(path);
				// check whether the file is readable:
				try {
					entry.openStream().close();
				} catch (Exception e) {
					continue;
				}
				int pos = path.lastIndexOf('/');
				final String name = (pos >= 0 ? path.substring(pos + 1) : path);
				if (!name.endsWith(".pdtt")) { // check fhe file extention
					continue;
				}
				files.add(path);
			}
		}
		return (String[]) files.toArray(new String[files.size()]);
	}

	protected void assertContents(String expected, String actual) {
		String diff = PHPCoreTests.compareContents(expected, actual);
		if (diff != null) {
			fail(diff);
		}
	}
}
