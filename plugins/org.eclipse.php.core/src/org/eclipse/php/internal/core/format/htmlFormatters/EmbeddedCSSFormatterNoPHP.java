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

package org.eclipse.php.internal.core.format.htmlFormatters;

import org.eclipse.wst.html.core.internal.format.EmbeddedCSSFormatter;
import org.eclipse.wst.html.core.internal.format.HTMLTextFormatter;
import org.eclipse.wst.html.core.internal.provisional.HTMLFormatContraints;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;

/**
 * Look for documentation at HTMLFormatterNoPHP
 * 
 * @author guy.g
 * 
 */
public class EmbeddedCSSFormatterNoPHP extends EmbeddedCSSFormatter {

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

			IStructuredFormatter formatter = HTMLFormatterNoPHPFactory
					.getInstance().createFormatter(child,
							getFormatPreferences());
			if (formatter != null) {
				if (formatter instanceof HTMLFormatterNoPHP) {
					HTMLFormatterNoPHP htmlFormatter = (HTMLFormatterNoPHP) formatter;
					htmlFormatter.formatNode(child, contraints);
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

	/**
	 */
	protected void insertBreakAfter(IDOMNode node,
			HTMLFormatContraints contraints) {
		if (node == null)
			return;
		if (node.getNodeType() == Node.TEXT_NODE)
			return;
		Node parent = node.getParentNode();
		if (parent == null)
			return;
		Node next = node.getNextSibling();

		String spaces = null;
		if (next == null) { // last spaces
			// use parent indent for the end tag
			spaces = getBreakSpaces(parent);
		} else if (next.getNodeType() == Node.TEXT_NODE) {
			if (contraints != null && contraints.getFormatWithSiblingIndent()) {
				IDOMNode text = (IDOMNode) next;
				IStructuredFormatter formatter = HTMLFormatterNoPHPFactory
						.getInstance().createFormatter(text,
								getFormatPreferences());
				if (formatter instanceof HTMLTextFormatterNoPHP) {
					HTMLTextFormatterNoPHP textFormatter = (HTMLTextFormatterNoPHP) formatter;
					textFormatter.formatText(text, contraints,
							HTMLTextFormatter.FORMAT_HEAD);
				}
			}
			return;
		} else {
			spaces = getBreakSpaces(node);
		}
		if (spaces == null || spaces.length() == 0)
			return;

		replaceSource(node.getModel(), node.getEndOffset(), 0, spaces);
		setWidth(contraints, spaces);
	}

	/**
	 */
	protected void insertBreakBefore(IDOMNode node,
			HTMLFormatContraints contraints) {
		if (node == null)
			return;
		if (node.getNodeType() == Node.TEXT_NODE)
			return;
		Node parent = node.getParentNode();
		if (parent == null)
			return;
		Node prev = node.getPreviousSibling();

		String spaces = null;
		if (prev != null && prev.getNodeType() == Node.TEXT_NODE) {
			if (contraints != null && contraints.getFormatWithSiblingIndent()) {
				IDOMNode text = (IDOMNode) prev;
				IStructuredFormatter formatter = HTMLFormatterNoPHPFactory
						.getInstance().createFormatter(text,
								getFormatPreferences());
				if (formatter instanceof HTMLTextFormatterNoPHP) {
					HTMLTextFormatterNoPHP textFormatter = (HTMLTextFormatterNoPHP) formatter;
					textFormatter.formatText(text, contraints,
							HTMLTextFormatter.FORMAT_TAIL);

					if (node == null)
						return;

					if (node.hasChildNodes()) { // container
						formatChildNodes(node, contraints);
					} else { // leaf
						IStructuredDocumentRegion flatNode = node
								.getStartStructuredDocumentRegion();
						if (flatNode != null) {
							String source = flatNode.getText();
							if (source != null && source.length() > 0) {
								setWidth(contraints, source);
							}
						}
					}

				}
			}
			return;
		} else {
			spaces = getBreakSpaces(node);
		}
		if (spaces == null || spaces.length() == 0)
			return;

		replaceSource(node.getModel(), node.getStartOffset(), 0, spaces);
		setWidth(contraints, spaces);
	}
}
