/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.util.StringTokenizer;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;
import org.eclipse.wst.sse.ui.internal.util.EditorUtility;

public class PHPColorHelper extends ColorHelper {
	protected final static String STYLE_SEPARATOR = "|"; //$NON-NLS-1$

	public final static String NOCOLOR = " | null";
	public final static String NOBACKGROUND = " | null";

	/*
	 * This function produces style descriptor string
	 * @param boolean Background
	 * @param boolean Bold
	 * @param boolean Italic
	 * @param boolean Underline
	 */
	public static String getStyleString(boolean bold, boolean italic, boolean underline) {
		String style = "";
		style += (bold ? " | true" : " | false");
		style += (italic ? " | true" : " | false");
		style += (underline ? " | true" : " | false");
		return style;
	}

	/*
	 * In this function style string may contain variable number of style descriptors
	 * @see org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper#packStylePreferences(java.lang.String[])
	 */
	public static String packStylePreferences(String[] stylePrefs) {
		StringBuffer styleString = new StringBuffer();

		for (int i = 0; i < stylePrefs.length; ++i) {
			String s = stylePrefs[i];
			styleString.append(s);

			// add in the separator (except on last iteration)
			if (i < stylePrefs.length - 1) {
				styleString.append(" " + STYLE_SEPARATOR + " "); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return styleString.toString();
	}

	/*
	 * In this function style string may contain variable number of descriptors
	 * @see org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper#unpackStylePreferences(java.lang.String)
	 */
	public static String[] unpackStylePreferences(String preference) {
		String[] stylePrefs = null;
		if (preference != null) {
			StringTokenizer st = new StringTokenizer(preference, STYLE_SEPARATOR);
			stylePrefs = new String[st.countTokens()];
			int index = 0;
			while (st.hasMoreTokens()) {
				stylePrefs[index++] = st.nextToken().trim();
			}
		}
		return stylePrefs;
	}

	/*
	 * Creates TextAttribute from the given style description array string
	 */
	public static TextAttribute createTextAttribute(String[] stylePrefs) {
		int fontModifier = SWT.NORMAL;
		if (Boolean.valueOf(stylePrefs[2]).booleanValue()) { // bold
			fontModifier |= SWT.BOLD;
		}
		if (Boolean.valueOf(stylePrefs[3]).booleanValue()) { // italic
			fontModifier |= SWT.ITALIC;
		}
		if (Boolean.valueOf(stylePrefs[4]).booleanValue()) { // underline
			fontModifier |= TextAttribute.UNDERLINE;
		}
		return new TextAttribute(EditorUtility.getColor(toRGB(stylePrefs[0])), EditorUtility.getColor(toRGB(stylePrefs[1])), fontModifier);
	}
}
