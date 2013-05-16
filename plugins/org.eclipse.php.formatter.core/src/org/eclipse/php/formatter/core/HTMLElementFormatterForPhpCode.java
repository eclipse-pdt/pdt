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

import org.eclipse.wst.html.core.internal.format.HTMLElementFormatter;
import org.eclipse.wst.html.core.internal.format.HTMLFormatter;
import org.eclipse.wst.html.core.internal.provisional.HTMLFormatContraints;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Override HTMLElementFormatter to fixed bug on the HTML formatter
 * 
 * @author moshe, 2007
 */
@SuppressWarnings("deprecation")
public class HTMLElementFormatterForPhpCode extends HTMLElementFormatter {

	@Override
	protected boolean canInsertBreakAfter(Node node) {
		if ("PHP".equals(node.getNodeName())) { //$NON-NLS-1$
			return false;
		}
		Node next = node.getNextSibling();
		if (next != null && "PHP".equals(next.getNodeName())) { //$NON-NLS-1$
			return false;
		}
		return super.canInsertBreakAfter(node);
	}

	@Override
	protected boolean canInsertBreakBefore(Node node) {
		if ("PHP".equals(node.getNodeName())) { //$NON-NLS-1$
			return false;
		}
		Node prev = node.getPreviousSibling();
		if (prev != null && "PHP".equals(prev.getNodeName())) { //$NON-NLS-1$
			return false;
		}
		return super.canInsertBreakBefore(node);
	}

	/**
	 */
	protected void formatChildNodes(IDOMNode node,
			HTMLFormatContraints contraints) {
		if (node == null)
			return;
		if (!node.hasChildNodes())
			return;

		// concat adjacent texts
		node.normalize();

		// disable sibling indent during formatting all the children
		boolean indent = false;
		if (contraints != null) {
			indent = contraints.getFormatWithSiblingIndent();
			contraints.setFormatWithSiblingIndent(false);
		}

		boolean insertBreak = true;
		IDOMNode child = (IDOMNode) node.getFirstChild();
		while (child != null) {
			if (child.getParentNode() != node)
				break;
			IDOMNode next = (IDOMNode) child.getNextSibling();

			if (insertBreak && canInsertBreakBefore(child)) {
				insertBreakBefore(child, contraints);
			}

			IStructuredFormatter formatter = HTMLFormatterFactoryForPhpCode
					.getInstance().createFormatter(child,
							getFormatPreferences());
			if (formatter != null) {
				if (formatter instanceof HTMLFormatter) {
					HTMLFormatter htmlFormatter = (HTMLFormatter) formatter;
					htmlFormatter.format(child, contraints);
				} else {
					formatter.format(child);
				}
			}

			if (canInsertBreakAfter(child)) {
				insertBreakAfter(child, contraints);
				insertBreak = false; // not to insert twice
			} else {
				insertBreak = true;
			}

			child = next;
		}

		if (contraints != null)
			contraints.setFormatWithSiblingIndent(indent);
	}

	protected void formatNode(IDOMNode node, HTMLFormatContraints contraints) {
		// fixed bug 198901 - prevent the HTML formatter to format the value of
		// style attribute
		// skip the format start tag and end tag
		Attr attr = null;
		if (node instanceof Element) {
			attr = ((Element) node).getAttributeNode("style");//$NON-NLS-1$
		}
		if (attr == null || attr.getValue().indexOf("<?") == -1) { //$NON-NLS-1$
			super.formatNode(node, contraints);
		} else {
			formatChildNodes(node, contraints);
		}
	}

}
