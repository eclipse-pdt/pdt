/*******************************************************************************
 * Copyright (c) 2013-2020 Zend Techologies Ltd.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.formatter.core;

import org.eclipse.wst.html.core.internal.format.HTMLFormatter;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatPreferences;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.eclipse.wst.xml.core.internal.provisional.format.StructuredFormatPreferencesXML;
import org.w3c.dom.Node;

class HTMLFormatterFactoryForPHPCode {
	private static HTMLFormatterFactoryForPHPCode fInstance = null;
	protected StructuredFormatPreferencesXML fFormatPreferences = null;

	static synchronized HTMLFormatterFactoryForPHPCode getInstance() {
		if (fInstance == null) {
			fInstance = new HTMLFormatterFactoryForPHPCode();
		}
		return fInstance;
	}

	protected IStructuredFormatter createFormatter(Node node, IStructuredFormatPreferences formatPreferences) {
		IStructuredFormatter formatter = null;

		switch (node.getNodeType()) {
		case Node.ELEMENT_NODE:
			formatter = new HTMLElementFormatterForPHPCode();
			break;
		case Node.TEXT_NODE:
			if (isEmbeddedCSS(node)) {
				formatter = new EmbeddedCSSFormatterForPHPCode();
			} else {
				formatter = new HTMLTextFormatterForPHPCode();
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
		if (node == null) {
			return false;
		}
		Node parent = node.getParentNode();
		if (parent == null) {
			return false;
		}
		if (parent.getNodeType() != Node.ELEMENT_NODE) {
			return false;
		}
		String name = parent.getNodeName();
		if (name == null) {
			return false;
		}
		boolean isStyleNode = name.equalsIgnoreCase("STYLE");//$NON-NLS-1$
		if (isStyleNode && node.getTextContent().indexOf("<?") != -1) { //$NON-NLS-1$
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=528379
			// Do not use the CSS formatter when a "style" node contains PHP
			// code.
			return false;
		}
		return isStyleNode;
	}

	private HTMLFormatterFactoryForPHPCode() {
		super();
	}

}