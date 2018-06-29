/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.rename;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.link.LinkedPosition;

/**
 * A <code>Position</code> on a document that knows which document it is
 * registered with and has a sequence number for tab stops.
 * <p>
 * Clients may extend this class.
 * </p>
 */
public class PHPElementLinkedPosition extends LinkedPosition {

	/**
	 * Creates a new instance.
	 * 
	 * @param document
	 *            the document
	 * @param offset
	 *            the offset of the position
	 * @param length
	 *            the length of the position
	 * @param sequence
	 *            the iteration sequence rank
	 */
	public PHPElementLinkedPosition(IDocument document, int offset, int length, int sequence) {
		super(document, offset, length, sequence);
		if (isDollared()) {
			setOffset(offset + 1);
			setLength(length - 1);
		}
		if (isDoubleQuoted()) {
			setOffset(offset + 1);
			setLength(length - 2);
		}
		if (isSingleQuoted()) {
			setOffset(offset + 1);
			setLength(length - 2);
		}

	}

	private boolean isDollared() {
		String stringValue = null;
		try {
			stringValue = super.getContent();
		} catch (BadLocationException e) {

		}

		return stringValue != null && stringValue.indexOf("$") == 0; //$NON-NLS-1$
	}

	private boolean isDoubleQuoted() {
		String stringValue = null;
		try {
			stringValue = super.getContent();
		} catch (BadLocationException e) {

		}

		return stringValue != null && stringValue.indexOf("\"") == 0 //$NON-NLS-1$
				&& stringValue.lastIndexOf("\"") == stringValue.length() - 1; //$NON-NLS-1$
	}

	private boolean isSingleQuoted() {
		String stringValue = null;
		try {
			stringValue = super.getContent();
		} catch (BadLocationException e) {

		}

		return stringValue != null && stringValue.indexOf("\'") == 0 //$NON-NLS-1$
				&& stringValue.lastIndexOf("\'") == stringValue.length() - 1; //$NON-NLS-1$
	}

}
