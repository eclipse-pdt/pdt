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
package org.eclipse.php.core.documentModel.dom;

import org.eclipse.wst.html.core.internal.document.DocumentStyleImpl;
import org.eclipse.wst.xml.core.internal.document.DocumentImpl;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PHPDOMDocument extends DocumentStyleImpl {

	public PHPDOMDocument() {
		super();
	}

	protected PHPDOMDocument(DocumentImpl that) {
		super(that);
	}

	public Node cloneNode(boolean deep) {
		PHPDOMDocument cloned = new PHPDOMDocument(this);
		if (deep)
			cloned.importChildNodes(this, true);
		return cloned;
	}

	public void setModel(IDOMModel model) {
		super.setModel(model);
	}

	public Element createElement(String tagName) throws DOMException {
		checkTagNameValidity(tagName);

		Element element = null;
		if (tagName == PHPTagNames.PHP_SCRIPTLET) {
			element = new PHPElementImpl();
			((PHPElementImpl) element).setOwnerDocument(this);
			((PHPElementImpl) element).setTagName(tagName);
		} else
			element = super.createElement(tagName);

		return element;
	}
}
