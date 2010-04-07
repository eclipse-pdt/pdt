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
package org.eclipse.php.internal.core.documentModel;

import org.eclipse.php.internal.core.documentModel.dom.DOMDocumentForPHP;
import org.eclipse.php.internal.core.documentModel.dom.PHPDOMModelParser;
import org.eclipse.php.internal.core.documentModel.dom.PHPDOMModelUpdater;
import org.eclipse.wst.html.core.internal.document.DOMStyleModelImpl;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.xml.core.internal.document.XMLModelParser;
import org.eclipse.wst.xml.core.internal.document.XMLModelUpdater;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Document;

/*
 * The PHPModel will support both the DOM style interface and PHP specific API's.
 */
public class DOMModelForPHP extends DOMStyleModelImpl {

	/*
	 * This is modeled after what is done for JSP
	 */
	protected Document internalCreateDocument() {
		DOMDocumentForPHP document = new DOMDocumentForPHP();
		document.setModel(this);
		return document;
	}

	protected XMLModelParser createModelParser() {
		return new PHPDOMModelParser(this);
	}

	protected XMLModelUpdater createModelUpdater() {
		return new PHPDOMModelUpdater(this);
	}
	@Override
	public IndexedRegion getIndexedRegion(int offset) {
		IndexedRegion result = super.getIndexedRegion(offset);
		if(result == null && offset == getDocument().getEndOffset()){
			return (IDOMNode) getDocument().getLastChild();
		}
		return super.getIndexedRegion(offset);
	}
}
