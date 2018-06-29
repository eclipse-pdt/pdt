/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
 *******************************************************************************/
package org.eclipse.php.internal.core.documentModel.dom;

import org.eclipse.wst.html.core.internal.document.DocumentStyleImpl;
import org.eclipse.wst.xml.core.internal.document.DocumentImpl;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.w3c.dom.*;

public class DOMDocumentForPHP extends DocumentStyleImpl {

	public DOMDocumentForPHP() {
		super();
	}

	protected DOMDocumentForPHP(DocumentImpl that) {
		super(that);
	}

	@Override
	public Node cloneNode(boolean deep) {
		DOMDocumentForPHP cloned = new DOMDocumentForPHP(this);
		if (deep) {
			cloned.importChildNodes(this, true);
		}
		return cloned;
	}

	/**
	 * createElement method
	 * 
	 * @return org.w3c.dom.Element
	 * @param tagName
	 *            java.lang.String
	 */
	@Override
	public Element createElement(String tagName) throws DOMException {
		checkTagNameValidity(tagName);

		ElementImplForPHP element = new ElementImplForPHP();
		element.setOwnerDocument(this);
		element.setTagName(tagName);
		return element;
	}

	/**
	 * createAttribute method
	 * 
	 * @return org.w3c.dom.Attr
	 * @param name
	 *            java.lang.String
	 */
	@Override
	public Attr createAttribute(String name) throws DOMException {
		AttrImplForPHP attr = new AttrImplForPHP();
		attr.setOwnerDocument(this);
		attr.setName(name);
		return attr;
	}

	/**
	 * createTextNode method
	 * 
	 * @return org.w3c.dom.Text
	 * @param data
	 *            java.lang.String
	 */
	@Override
	public Text createTextNode(String data) {
		TextImplForPHP text = new TextImplForPHP();
		text.setOwnerDocument(this);
		text.setData(data);
		return text;
	}

	@Override
	public void setModel(IDOMModel model) {
		super.setModel(model);
	}
}
