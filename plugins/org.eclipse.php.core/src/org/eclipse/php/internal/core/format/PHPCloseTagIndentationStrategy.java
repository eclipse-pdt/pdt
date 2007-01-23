/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.internal.core.format;

import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * @author seva
 *
 */
public class PHPCloseTagIndentationStrategy extends DefaultIndentationStrategy {
	public void placeMatchingBlanks(final IStructuredDocument document, final StringBuffer result, final int lineNumber, final int forOffset) {
		// Ignore default behavior (don't add previous line's blanks)
	}

}
