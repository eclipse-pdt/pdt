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

package org.eclipse.php.internal.core.documentModel.dom;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.validate.ValidationAdapter;
import org.eclipse.wst.xml.core.internal.document.ElementImpl;
import org.eclipse.wst.xml.core.internal.validate.ValidationComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Represents elements in the dom model {@link DOMModelForPHP}
 * 
 * @author Roy, 2007
 */
public class ElementImplForPHP extends ElementStyleImpl implements IAdaptable, IImplForPHP {

	private static final String WORKBENCH_ADAPTER = "org.eclipse.ui.model.IWorkbenchAdapter"; //$NON-NLS-1$
	private IModelElement modelElement;

	public ElementImplForPHP() {
		super();
	}

	public Object getAdapter(Class adapter) {
		if (adapter != null && adapter.getName().equals(WORKBENCH_ADAPTER)) {
			return null;
		}
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	public ElementImplForPHP(ElementStyleImpl that) {
		super(that);
	}

	protected boolean isNestedClosed(String regionType) {
		return regionType == PHPRegionContext.PHP_CLOSE;
	}

	public Node cloneNode(boolean deep) {
		ElementImpl cloned = new ElementImplForPHP(this);
		if (deep)
			cloneChildNodes(cloned, deep);
		return cloned;
	}

	/**
	 * @see ElementStyleImpl#setOwnerDocument(Document) make this method package
	 *      visible
	 */
	protected void setOwnerDocument(Document ownerDocument) {
		super.setOwnerDocument(ownerDocument);
	}

	/**
	 * @see setTagName(String) make this method package visible
	 */
	protected void setTagName(String tagName) {
		super.setTagName(tagName);
	}

	public boolean isGlobalTag() {
		return isPHPTag() ? false : super.isGlobalTag();
	}

	/**
	 * @return true if it is a php element
	 */
	public boolean isPHPTag() {
		return PHPDOMModelParser.PHP_TAG_NAME.equals(getNodeName());
	}

	public INodeAdapter getExistingAdapter(Object type) {

		// no validation or validation propagation for PHP tags
		if (isPHPTag() && type instanceof Class && ValidationAdapter.class.isAssignableFrom((Class) type)) {
			return nullValidator;
		}
		return super.getExistingAdapter(type);
	}

	private final static ValidationComponent nullValidator = new NullValidator();

	public String getPrefix() {
		final String prefix = super.getPrefix();
		if (prefix == null && isPHPTag()) {
			return ""; //$NON-NLS-1$
		}
		return prefix;
	}

	public IModelElement getModelElement() {
		return modelElement;
	}

	public void setModelElement(IModelElement modelElement) {
		this.modelElement = modelElement;
	}

	@Override
	public boolean isStartTagClosed() {
		return isPHPTag() ? true : super.isStartTagClosed();
	}
}
