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
import org.eclipse.wst.html.core.internal.format.HTMLTextFormatter;
import org.eclipse.wst.html.core.internal.provisional.HTMLFormatContraints;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.eclipse.wst.xml.core.internal.document.TextImpl;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;

/**
 * Look for documentation at HTMLFormatterNoPHP
 * 
 * @author guy.g
 *
 */
public class HTMLFormatterNoPHPBase {

	/**
	 */
	public static void formatChildNodes(IHTMLFormatterNoPHPWrapper f, IDOMNode node, HTMLFormatContraints contraints) {
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

			if(child instanceof PHPElementImpl){
				child = next;
				continue;
			}
			if(child instanceof TextImpl){
					TextImpl text = (TextImpl) child;
					if(text.getFirstStructuredDocumentRegion().getType().indexOf("PHP") != -1){
						child = next;
						continue;
					}
			}

			if (insertBreak && f.runCanInsertBreakBefore(child)) {
				f.runInsertBreakBefore(child, contraints);
			}

			IStructuredFormatter formatter = HTMLFormatterNoPHPFactory.getInstance().createFormatter(child, f.getFormatPreferences());
			if (formatter != null) {
				if (formatter instanceof IHTMLFormatterNoPHPWrapper) {
					IHTMLFormatterNoPHPWrapper htmlFormatter = (IHTMLFormatterNoPHPWrapper) formatter;
					htmlFormatter.runFormatNode(child, contraints);
				}
				else {
					formatter.format(child);
				}
			}

			if (f.runCanInsertBreakAfter(child)) {
				f.runInsertBreakAfter(child, contraints);
				insertBreak = false; // not to insert twice
			}
			else {
				insertBreak = true;
			}

			child = next;
		}

		if (contraints != null)
			contraints.setFormatWithSiblingIndent(indent);
	}

	public static void insertBreakAfter(IHTMLFormatterNoPHPWrapper f, IDOMNode node, HTMLFormatContraints contraints) {
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
			spaces = f.runGetBreakSpaces(parent);
		}
		else if (next.getNodeType() == Node.TEXT_NODE) {
			if (contraints != null && contraints.getFormatWithSiblingIndent()) {
				IDOMNode text = (IDOMNode) next;
				IStructuredFormatter formatter = HTMLFormatterNoPHPFactory.getInstance().createFormatter(text, f.getFormatPreferences());
				if (formatter instanceof HTMLTextFormatterNoPHP) {
					HTMLTextFormatterNoPHP textFormatter = (HTMLTextFormatterNoPHP) formatter;
					textFormatter.runFormatText(text, contraints, HTMLTextFormatter.FORMAT_HEAD);
				}
			}
			return;
		}
		else {
			spaces = f.runGetBreakSpaces(node);
		}
		if (spaces == null || spaces.length() == 0)
			return;

		f.runReplaceSource(node.getModel(), node.getEndOffset(), 0, spaces);
		f.runSetWidth(contraints, spaces);
	}

	/**
	 */
	public static void insertBreakBefore(IHTMLFormatterNoPHPWrapper f, IDOMNode node, HTMLFormatContraints contraints) {
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
				IStructuredFormatter formatter = HTMLFormatterNoPHPFactory.getInstance().createFormatter(text, f.getFormatPreferences());
				if (formatter instanceof HTMLTextFormatterNoPHP) {
					HTMLTextFormatterNoPHP textFormatter = (HTMLTextFormatterNoPHP) formatter;
					textFormatter.runFormatText(text, contraints, HTMLTextFormatter.FORMAT_TAIL);
				}
			}
			return;
		}
		else {
			spaces = f.runGetBreakSpaces(node);
		}
		if (spaces == null || spaces.length() == 0)
			return;

		f.runReplaceSource(node.getModel(), node.getStartOffset(), 0, spaces);
		f.runSetWidth(contraints, spaces);
	}

}
