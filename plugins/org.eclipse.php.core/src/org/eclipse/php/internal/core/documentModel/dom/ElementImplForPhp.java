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

package org.eclipse.php.internal.core.documentModel.dom;

import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.core.internal.validate.ValidationAdapter;
import org.eclipse.wst.xml.core.internal.document.ElementImpl;
import org.eclipse.wst.xml.core.internal.validate.ValidationComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Represents elements in the dom model {@link DOMModelForPHP}
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

	/**
	 */
	public boolean isGlobalTag() {
		return isPhpTag() ? false : super.isGlobalTag();
	}

	/**
	 * @return true if it is a php element
	 */
	public boolean isPhpTag() {
		return getNodeName() == PHPDOMModelParser.PHP_TAG_NAME;
	}
	
	public INodeAdapter getExistingAdapter(Object type) {
		String className = ((Class)type).getName();
		if((className.equals("org.eclipse.wst.html.core.internal.validate.ElementPropagatingValidator") || type == ValidationAdapter.class) && isPhpTag()){
			return validator;
		}
		return super.getExistingAdapter(type);
	}
	
	private ValidationComponent validator = new PHPValidationComponent();
	
	private class PHPValidationComponent extends ValidationComponent{
		public void validate(IndexedRegion node) {	
		}
	}
}
