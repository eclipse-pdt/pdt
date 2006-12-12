/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.core.documentModel.dom;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.wst.xml.core.internal.document.TextImpl;
import org.w3c.dom.Document;

public class PHPTextImpl extends TextImpl implements IAdaptable {
	protected PHPTextImpl(Document doc, String data) {
		super();
		setOwnerDocument(doc);
		setData(data);
	}

	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}
}