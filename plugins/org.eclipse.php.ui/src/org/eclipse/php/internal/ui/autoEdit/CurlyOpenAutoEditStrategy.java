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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.*;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * @author guy.g
 * 
 */

public class CurlyOpenAutoEditStrategy extends IndentLineAutoEditStrategy
		implements IAutoEditStrategy {

	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		if (command.text != null && command.text.endsWith("{")) { //$NON-NLS-1$
			autoIndentBeforeCurlyOpen((IStructuredDocument) document, command);
		}
	}

	private StringBuffer helpBuffer = new StringBuffer();

	private void autoIndentBeforeCurlyOpen(IStructuredDocument document,
			DocumentCommand command) {

		int startOffset = command.offset;
		int endOffset = startOffset + command.length;
		helpBuffer.setLength(0);
		try {
			IRegion startlineInfo = document
					.getLineInformationOfOffset(startOffset);
			IRegion endlineInfo = null;

			if (startlineInfo.getOffset() + startlineInfo.getLength() < endOffset) {
				// meaning the begin and the end of the selection is not in the
				// same line.
				endlineInfo = document.getLineInformationOfOffset(endOffset);
			} else {
				endlineInfo = startlineInfo;
			}
			int lineNumber = document.getLineOfOffset(startOffset);

			if (isBlanks(document, startlineInfo.getOffset(),
					endlineInfo.getOffset() + endlineInfo.getLength(),
					startOffset)) {
				placeMatchingBlanks(document, helpBuffer, lineNumber,
						startOffset);
				int endSelection = command.offset + command.length;
				command.offset = startlineInfo.getOffset();
				command.length = (command.length == 0) ? endOffset
						- command.offset : endSelection - command.offset;
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}

		command.text = helpBuffer.toString() + command.text;

	}

	protected String getCommandText() {
		return "{"; //$NON-NLS-1$
	}
}
