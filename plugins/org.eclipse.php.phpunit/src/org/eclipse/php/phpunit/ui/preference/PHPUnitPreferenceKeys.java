/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.preference;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.phpunit.PHPUnitPlugin;

public class PHPUnitPreferenceKeys {

	public static final String CODE_COVERAGE = "PHPUnit_CodeCoverage"; //$NON-NLS-1$
	public static final String REPORTING = "PHPUnit_Reporting"; //$NON-NLS-1$
	public static final String FILTER = "PHPUnit_Filter"; //$NON-NLS-1$
	public static final String PORT = "PHPUnit_Port"; //$NON-NLS-1$
	public static final String REPORT_PATH = "PHPUnit_ReportPath"; //$NON-NLS-1$
	public static final String PHPUNITXML_PATH = "PHPUnitXML_Path"; //$NON-NLS-1$
	public static final String PHPUNIT_PATH = "PHPUnit_Path"; //$NON-NLS-1$
	public static final String PHPUNIT_PHAR_PATH = "PHPUnit_Phar_Path"; //$NON-NLS-1$

	public static final String SHOW_EXECUTION_TIME = "show_execution_time"; //$NON-NLS-1$

	public static boolean getCodeCoverage() {
		return getPreferenceStore().getBoolean(CODE_COVERAGE);
	}

	public static boolean getReporting() {
		return getPreferenceStore().getBoolean(REPORTING);
	}

	public static boolean getFilterStack() {
		return getPreferenceStore().getBoolean(FILTER);
	}

	public static String getReportPath() {
		return getPreferenceStore().getString(REPORT_PATH);
	}

	public static int getPort() {
		return getPreferenceStore().getInt(PORT);
	}

	public static String getPHPUnitPharPath() {
		return getPreferenceStore().getString(PHPUNIT_PHAR_PATH);
	}

	public static void setReportPath(String reportPath) {
		getPreferenceStore().setValue(REPORT_PATH, reportPath);
	}

	private static IPreferenceStore getPreferenceStore() {
		return PHPUnitPlugin.getDefault().getPreferenceStore();
	}

	public static void initializeDefaultValues() {
		final IPreferenceStore store = getPreferenceStore();
		store.setDefault(PORT, "7478"); //$NON-NLS-1$
		store.setDefault(FILTER, true);
		store.setDefault(CODE_COVERAGE, false);
		store.setDefault(REPORTING, false);
		store.setDefault(REPORT_PATH, getWritableDir());
	}

	public static void setFilterStack(final boolean filter) {
		getPreferenceStore().setValue(FILTER, filter);
	}

	private PHPUnitPreferenceKeys() {
		// don't instantiate
	}

	/**
	 * @return temporary directory location
	 */
	private static String getWritableDir() {
		// first try workspace root
		String path = getWritable(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString());
		if (path == null) {
			// and if failed - temp dir
			path = getWritable(null);
		}
		return path;
	}

	public static String getWritable(String path) {
		try {
			File tempFile;
			if (path != null) {
				tempFile = File.createTempFile("___", "", new File(path)); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				tempFile = File.createTempFile("___", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			String parent = tempFile.getParent();
			tempFile.delete();
			return parent;
		} catch (IOException e) {
			PHPUnitPlugin.log(e);
			return null;
		}
	}
}
