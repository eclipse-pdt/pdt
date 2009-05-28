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
package org.eclipse.php.internal.core.documentModel.parser;

import org.eclipse.wst.sse.core.internal.provisional.events.RegionChangedEvent;
import org.eclipse.wst.sse.core.internal.provisional.events.StructuredDocumentEvent;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredTextReParser;
import org.eclipse.wst.xml.core.internal.parser.XMLStructuredDocumentReParser;

/**
 * Handles the php region when reparsing an XML/PHP structured document
 * @author Roy, 2006
 */
public class PhpStructuredDocumentReParser extends XMLStructuredDocumentReParser {

	public PhpStructuredDocumentReParser() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.core.internal.parser.XMLStructuredDocumentReParser#newInstance()
	 */
	public IStructuredTextReParser newInstance() {
		return new PhpStructuredDocumentReParser();
	}

	/**
	 * Adding the support to php comments
	 */
	protected StructuredDocumentEvent checkForComments() {
		StructuredDocumentEvent result = checkForCriticalKey("/*"); //$NON-NLS-1$
		if (result == null) {
			result = checkForCriticalKey("*/"); //$NON-NLS-1$
		}
		return result != null ? result : super.checkForComments();
	}

	/**
	 * This function was added in order to support asp tags in PHP (bug fix #150363)
	 */
	protected StructuredDocumentEvent checkForCrossStructuredDocumentRegionSyntax() {
		StructuredDocumentEvent result = super.checkForCrossStructuredDocumentRegionSyntax();
		if (result == null) {
			result = checkForCriticalKey("<%"); //$NON-NLS-1$
			if (result == null)
				result = checkForCriticalKey("%>"); //$NON-NLS-1$

		}
		return result;
	}

	/**
	 * Change PHP Script Regions nodes...
	 */
	protected StructuredDocumentEvent regionCheck(IStructuredDocumentRegion oldNode, IStructuredDocumentRegion newNode) {
		final StructuredDocumentEvent event = super.regionCheck(oldNode, newNode);
		
		if (event instanceof RegionChangedEvent) {
			RegionChangedEvent changedEvent = (RegionChangedEvent) event;
			
			if (changedEvent.getRegion().getType() == PHPRegionContext.PHP_CONTENT) {
				oldNode.setRegions(newNode.getRegions());
			}
		}		
		return event;
	}

	/**
	 * This implementation updates the php tokens model after updating WST editor model
	 */
	public StructuredDocumentEvent reparse() {
		final StructuredDocumentEvent documentEvent = super.reparse();
		return documentEvent;
	}
}
