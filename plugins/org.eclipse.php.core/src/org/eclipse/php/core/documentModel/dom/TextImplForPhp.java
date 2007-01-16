/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.core.documentModel.dom;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.xml.core.internal.document.TextImpl;
import org.w3c.dom.Document;

/**
 * Represents attributes implementation in php dom model
 * @author Roy, 2007
 */
public class TextImplForPhp extends TextImpl implements IAdaptable {

	protected  TextImplForPhp() {
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
}