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

import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.document.DOMModelImpl;
import org.eclipse.wst.xml.core.internal.document.XMLModelParser;

public class PHPDOMModelParser extends XMLModelParser {

	public static final String PHP_TAG_NAME = "PHP"; //$NON-NLS-1$

	public PHPDOMModelParser(DOMModelImpl model) {
		super(model);
	}

	@Override
	protected boolean isNestedContent(String regionType) {
		return regionType == PHPRegionContext.PHP_CONTENT;
	}

	@Override
	protected boolean isNestedTag(String regionType) {
		return regionType == PHPRegionContext.PHP_OPEN || regionType == PHPRegionContext.PHP_CLOSE;
	}

	@Override
	protected boolean isNestedTagOpen(String regionType) {
		return regionType == PHPRegionContext.PHP_OPEN;
	}

	@Override
	protected String computeNestedTag(String regionType, String tagName,
			IStructuredDocumentRegion structuredDocumentRegion, ITextRegion region) {
		return PHPDOMModelParser.PHP_TAG_NAME;
	}

	@Override
	protected boolean isNestedTagClose(String regionType) {
		return regionType == PHPRegionContext.PHP_CLOSE;
	}
}
