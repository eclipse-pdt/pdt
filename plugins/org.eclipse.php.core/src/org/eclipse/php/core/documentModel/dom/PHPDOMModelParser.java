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

import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.document.DOMModelImpl;
import org.eclipse.wst.xml.core.internal.document.XMLModelParser;

public class PHPDOMModelParser extends XMLModelParser {

	public PHPDOMModelParser(DOMModelImpl model) {
		super(model);
	}
	
	protected boolean isNestedContent(String regionType) {
		return regionType.startsWith("PHP"); 
	}

	protected boolean isNestedTag(String regionType) {
		return regionType == PHPRegionTypes.PHP_OPENTAG || regionType == PHPRegionTypes.PHP_CLOSETAG;
	}

	protected boolean isNestedTagOpen(String regionType) {
		return regionType == PHPRegionTypes.PHP_OPENTAG;
	}

	protected String computeNestedTag(String regionType, String tagName, IStructuredDocumentRegion structuredDocumentRegion, ITextRegion region) {
		return PHPTagNames.PHP_SCRIPTLET;
	}
	
	protected boolean isNestedTagClose(String regionType) {
		return regionType == PHPRegionTypes.PHP_CLOSETAG;
	}
}
