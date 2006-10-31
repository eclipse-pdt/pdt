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

package org.eclipse.php.core.format.htmlFormatters;

import org.eclipse.php.core.documentModel.dom.PHPElementImpl;
import org.eclipse.php.core.format.PhpFormatter;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatPreferences;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.w3c.dom.Node;

/**
 * Look for documentation at HTMLFormatterNoPHP
 * 
 * @author guy.g
 *
 */
public class HTMLFormatterNoPHPFactory {

	private static HTMLFormatterNoPHPFactory fInstance = null;

	static synchronized HTMLFormatterNoPHPFactory getInstance() {
		if (fInstance == null) {
			fInstance = new HTMLFormatterNoPHPFactory();
		}
		return fInstance;
	}

	IStructuredFormatter createFormatter(Node node, IStructuredFormatPreferences formatPreferences) {
		IStructuredFormatter formatter = null;

		switch (node.getNodeType()) {
			case Node.ELEMENT_NODE :
				formatter = new HTMLElementFormatterNoPHP();
				break;
			case Node.TEXT_NODE :
				if (isEmbeddedCSS(node)) {
					formatter = new EmbeddedCSSFormatterNoPHP();
				}
				else if ((node.getParentNode() != null) && (node.getParentNode() instanceof PHPElementImpl)){
					formatter = new PhpFormatter();
				}
				else {
					formatter = new HTMLTextFormatterNoPHP();
				}
				break;
			default :
				formatter = new HTMLFormatterNoPHP();
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


	private HTMLFormatterNoPHPFactory() {
		super();
	}

}
