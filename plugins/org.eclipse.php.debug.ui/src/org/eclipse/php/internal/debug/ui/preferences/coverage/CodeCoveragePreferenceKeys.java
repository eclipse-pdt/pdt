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
package org.eclipse.php.internal.debug.ui.preferences.coverage;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;

/**
 * Code coverage UI preferences keys.
 */
public class CodeCoveragePreferenceKeys {

	public static final String CODE_COVERAGE_COLUMNS = PHPDebugUIPlugin.ID + "code_coverage_column_widths"; //$NON-NLS-1$
	private static final int defaultWidth[] = { 150, 100 };

	public static IPreferenceStore getPreferenceStore() {
		return PHPDebugUIPlugin.getDefault().getPreferenceStore();
	}

	public static void initializeDefaultValues() {
		final IPreferenceStore store = getPreferenceStore();

		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < defaultWidth.length; i++) {
			buf.append(defaultWidth[i]);
			if (i < defaultWidth.length - 1) {
				buf.append(","); //$NON-NLS-1$
			}
		}
		store.setDefault(CODE_COVERAGE_COLUMNS, buf.toString()); // $NON-NLS-1$
	}

	private CodeCoveragePreferenceKeys() {
		// don't instantiate
	}

	public static int[] getCodeCoverageColumnWidths() {
		String widthsString = getPreferenceStore().getString(CODE_COVERAGE_COLUMNS);
		String[] widthsArray = widthsString.split(","); //$NON-NLS-1$
		int[] widths = new int[widthsArray.length];
		for (int i = 0; i < widthsArray.length; ++i) {
			widths[i] = Integer.parseInt(widthsArray[i]);
			if (widths[i] < 1) {
				widths[i] = defaultWidth[i];
			}
		}
		return widths;
	}

	public static void setCodeCoverageColumnWidths(int[] widths) {
		StringBuffer widthsBuffer = new StringBuffer();
		for (int i = 0; i < widths.length; ++i) {
			if (widthsBuffer.length() > 0) {
				widthsBuffer.append(","); //$NON-NLS-1$
			}
			widthsBuffer.append(String.valueOf(widths[i]));
		}
		getPreferenceStore().setValue(CODE_COVERAGE_COLUMNS, widthsBuffer.toString());
	}
	
}
