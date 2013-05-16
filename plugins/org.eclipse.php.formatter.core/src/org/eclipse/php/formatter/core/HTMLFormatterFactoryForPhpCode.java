/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.wst.html.core.internal.HTMLCorePlugin;
import org.eclipse.wst.html.core.internal.format.HTMLFormatter;
import org.eclipse.wst.html.core.internal.preferences.HTMLCorePreferenceNames;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatPreferences;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.eclipse.wst.xml.core.internal.provisional.format.StructuredFormatPreferencesXML;
import org.w3c.dom.Node;

class HTMLFormatterFactoryForPhpCode {
	private static HTMLFormatterFactoryForPhpCode fInstance = null;
	protected StructuredFormatPreferencesXML fFormatPreferences = null;

	static synchronized HTMLFormatterFactoryForPhpCode getInstance() {
		if (fInstance == null) {
			fInstance = new HTMLFormatterFactoryForPhpCode();
		}
		return fInstance;
	}

	protected IStructuredFormatter createFormatter(Node node,
			IStructuredFormatPreferences formatPreferences) {
		IStructuredFormatter formatter = null;

		switch (node.getNodeType()) {
		case Node.ELEMENT_NODE:
			formatter = new HTMLElementFormatterForPhpCode();
			break;
		case Node.TEXT_NODE:
			if (isEmbeddedCSS(node)) {
				formatter = new EmbeddedCSSFormatterForPhpCode();
			} else {
				formatter = new HTMLTextFormatterForPhpCode();
			}
			break;
		default:
			formatter = new HTMLFormatter();
			break;
		}

		// init FormatPreferences
		formatter.setFormatPreferences(formatPreferences);

		return formatter;
	}

	/**
	 */
	private boolean isEmbeddedCSS(Node node) {
		if (node == null)
			return false;
		Node parent = node.getParentNode();
		if (parent == null)
			return false;
		if (parent.getNodeType() != Node.ELEMENT_NODE)
			return false;
		String name = parent.getNodeName();
		if (name == null)
			return false;
		return name.equalsIgnoreCase("STYLE");//$NON-NLS-1$
	}

	private HTMLFormatterFactoryForPhpCode() {
		super();
	}

	protected StructuredFormatPreferencesXML getFormatPreferences() {
		if (fFormatPreferences == null) {
			fFormatPreferences = new StructuredFormatPreferencesXML();

			Preferences preferences = HTMLCorePlugin.getDefault()
					.getPluginPreferences();
			if (preferences != null) {
				fFormatPreferences.setLineWidth(preferences
						.getInt(HTMLCorePreferenceNames.LINE_WIDTH));
				fFormatPreferences.setSplitMultiAttrs(preferences
						.getBoolean(HTMLCorePreferenceNames.SPLIT_MULTI_ATTRS));
				fFormatPreferences.setAlignEndBracket(preferences
						.getBoolean(HTMLCorePreferenceNames.ALIGN_END_BRACKET));
				fFormatPreferences
						.setClearAllBlankLines(preferences
								.getBoolean(HTMLCorePreferenceNames.CLEAR_ALL_BLANK_LINES));

				char indentChar = ' ';
				String indentCharPref = preferences
						.getString(HTMLCorePreferenceNames.INDENTATION_CHAR);
				if (HTMLCorePreferenceNames.TAB.equals(indentCharPref)) {
					indentChar = '\t';
				}
				int indentationWidth = preferences
						.getInt(HTMLCorePreferenceNames.INDENTATION_SIZE);

				StringBuffer indent = new StringBuffer();
				for (int i = 0; i < indentationWidth; i++) {
					indent.append(indentChar);
				}
				fFormatPreferences.setIndent(indent.toString());
			}
		}

		return fFormatPreferences;
	}
}