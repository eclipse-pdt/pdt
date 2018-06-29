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
public class TextImplForPHP extends TextImpl implements IAdaptable, IImplForPHP {

	private IModelElement modelElement;

	protected TextImplForPHP() {
		super();
	}

	protected TextImplForPHP(Document doc, String data) {
		super();
		setOwnerDocument(doc);
		setData(data);
	}

	@Override
	protected boolean isNotNestedContent(String regionType) {
		return regionType != PHPRegionContext.PHP_CONTENT;
	}

	@Override
	protected void setOwnerDocument(Document ownerDocument) {
		super.setOwnerDocument(ownerDocument);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	@Override
	public IModelElement getModelElement() {
		return modelElement;
	}

	@Override
	public void setModelElement(IModelElement modelElement) {
		this.modelElement = modelElement;
	}
}