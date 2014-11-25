/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - extract to API
 *******************************************************************************/
package org.eclipse.php.core.tests;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;

public class PDTTUtils {
	public static String[] getPDTTFiles(String testsDirectory) {
		return getPDTTFiles(testsDirectory, PHPCoreTests.getDefault()
				.getBundle());
	}

	public static String[] getPDTTFiles(String testsDirectory, Bundle bundle) {
		return getFiles(testsDirectory, bundle, ".pdtt");
	}

	public static String[] getFiles(String testsDirectory, String ext) {
		return getFiles(testsDirectory, PHPCoreTests.getDefault().getBundle(),
				ext);
	}

	public static String[] getFiles(String testsDirectory, Bundle bundle,
			String ext) {
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
				if (!name.endsWith(ext)) { // check fhe file extention
					continue;
				}
				files.add(path);
			}
		}
		return (String[]) files.toArray(new String[files.size()]);
	}

	public static void assertContents(String expected, String actual) {
		String diff = PHPCoreTests.compareContents(expected, actual);
		if (diff != null) {
			fail(diff);
		}
	}
}
