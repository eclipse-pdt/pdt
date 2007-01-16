/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/

package org.eclipse.php.core.documentModel.dom;

import org.eclipse.php.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.eclipse.wst.xml.core.internal.document.ElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Represents the element implementation in php dom model
 * @author Roy, 2007
 */
public class ElementImplForPhp extends ElementStyleImpl {
	
	public ElementImplForPhp() {
		super();
	}

	public ElementImplForPhp(ElementStyleImpl that) {
		super(that);
	}

	protected boolean isNestedClosed(String regionType) {
		return regionType == PHPRegionContext.PHP_CLOSE;
	}

	public Node cloneNode(boolean deep) {
		ElementImpl cloned = new ElementImplForPhp(this);
		if (deep)
			cloneChildNodes(cloned, deep);
		return cloned;
	}

	protected void setOwnerDocument(Document ownerDocument) {
		super.setOwnerDocument(ownerDocument);
	}
	
	protected void setTagName(String tagName) {
		super.setTagName(tagName);
	}
	
}
