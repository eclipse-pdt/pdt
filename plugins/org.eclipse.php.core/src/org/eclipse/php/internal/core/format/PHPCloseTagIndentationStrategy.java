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
package org.eclipse.php.internal.core.format;

import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * @author seva
 * 
 */
public class PHPCloseTagIndentationStrategy extends DefaultIndentationStrategy {

	public PHPCloseTagIndentationStrategy() {
	}

	/**
	 * 
	 * @param indentationObject
	 *            basic indentation preferences, can be null
	 */
	public PHPCloseTagIndentationStrategy(IndentationObject indentationObject) {
		setIndentationObject(indentationObject);
	}

	@Override
	public void placeMatchingBlanks(final IStructuredDocument document, final StringBuilder result,
			final int lineNumber, final int forOffset) {
		// Ignore default behavior (don't add previous line's blanks)
	}

}
