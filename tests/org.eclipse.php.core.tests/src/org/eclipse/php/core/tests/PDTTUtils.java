/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - extract to API
 *******************************************************************************/
package org.eclipse.php.core.tests;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;

public class PDTTUtils {

	public static String[] getPDTTFiles(String testsDirectory) {
		return getPDTTFiles(testsDirectory, PHPCoreTests.getDefault().getBundle());
	}

	public static String[] getPDTTFiles(String testsDirectory, Bundle bundle) {
		return getFiles(testsDirectory, bundle, ".pdtt");
	}

	public static String[] getFiles(String testsDirectory, String ext) {
		return getFiles(testsDirectory, PHPCoreTests.getDefault().getBundle(), ext);
	}

	public static String[] getFiles(String testsDirectory, Bundle bundle, String ext) {
		List<String> files = new LinkedList<>();
		Enumeration<String> entryPaths = bundle.getEntryPaths(testsDirectory);
		if (entryPaths != null) {
			while (entryPaths.hasMoreElements()) {
				final String path = entryPaths.nextElement();
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
		Collections.sort(files);
		return files.toArray(new String[files.size()]);
	}

	public static void assertContents(String expected, String actual) {
		String diff = TestUtils.compareContents(expected, actual);
		if (diff != null) {
			fail(diff);
		}
	}

}
