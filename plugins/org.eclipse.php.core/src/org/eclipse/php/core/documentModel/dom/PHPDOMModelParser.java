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

	private boolean isPhpBranch = false; 
	
	public PHPDOMModelParser(DOMModelImpl model) {
		super(model);
	}

	protected boolean isNestedCommentOpen(String regionType) {
		boolean result = false;
		return result;
	}

	protected boolean isNestedCommentText(String regionType) {
		boolean result = false;
		return result;
	}

	protected boolean isNestedContent(String regionType) {
		boolean result = regionType == PHPRegionTypes.PHP_CONTENT;
		return result;
	}

	/**
	 * We need to start the nested tag if the PHP open tag is presented firstly
	 */
	protected boolean isNestedTag(String regionType) {
		isPhpBranch = isPhpBranch || regionType == PHPRegionTypes.PHP_OPENTAG;
		return isPhpBranch;
	}

	protected boolean isNestedTagName(String regionType) {
		return false;
	}

	protected boolean isNestedTagOpen(String regionType) {
		return regionType == PHPRegionTypes.PHP_OPENTAG;
	}

	protected String computeNestedTag(String regionType, String tagName, IStructuredDocumentRegion structuredDocumentRegion, ITextRegion region) {
		String resultTagName = tagName;
		if (regionType == PHPRegionTypes.PHP_OPENTAG) {
			resultTagName = PHPTagNames.PHP_SCRIPTLET;
		}
		return resultTagName;
	}

	protected boolean isNestedTagClose(String regionType) {
		isPhpBranch = regionType == PHPRegionTypes.PHP_CLOSETAG;
		return isPhpBranch;
	}
}
