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
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.xml.core.internal.document.TextImpl;
import org.w3c.dom.Document;

/**
 * Represents attributes implementation in php dom model
 * 
 * @author Roy, 2007
 */
public class TextImplForPhp extends TextImpl implements IAdaptable, IImplForPhp {

	private IModelElement modelElement;

	protected TextImplForPhp() {
		super();
	}

	protected TextImplForPhp(Document doc, String data) {
		super();
		setOwnerDocument(doc);
		setData(data);
	}

	protected boolean isNotNestedContent(String regionType) {
		return regionType != PHPRegionContext.PHP_CONTENT;
	}

	protected void setOwnerDocument(Document ownerDocument) {
		super.setOwnerDocument(ownerDocument);
	}

	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	public IModelElement getModelElement() {
		return modelElement;
	}

	public void setModelElement(IModelElement modelElement) {
		this.modelElement = modelElement;
	}
}