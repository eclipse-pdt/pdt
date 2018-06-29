/*******************************************************************************
 * Copyright (c) 2009, 2015, 2017 IBM Corporation and others.
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
 *     Dawid Pakuła [459462]
 *******************************************************************************/
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.format.IndentationObject;
import org.eclipse.php.internal.core.format.IndentationUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * @author guy.g
 * 
 * @deprecated
 */

@Deprecated
public class CurlyOpenAutoEditStrategy extends IndentLineAutoEditStrategy implements IAutoEditStrategy {

	public CurlyOpenAutoEditStrategy() {
	}

	/**
	 * 
	 * @param indentationObject
	 *            basic indentation preferences, can be null
	 */
	public CurlyOpenAutoEditStrategy(IndentationObject indentationObject) {
		setIndentationObject(indentationObject);
	}

	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		if (command.text != null && command.text.endsWith("{")) { //$NON-NLS-1$
			setIndentationObject(null); // reset
			autoIndentBeforeCurlyOpen((IStructuredDocument) document, command);
		}
	}

	private StringBuilder helpBuffer = new StringBuilder();

	private void autoIndentBeforeCurlyOpen(IStructuredDocument document, DocumentCommand command) {

		int startOffset = command.offset;
		int endOffset = startOffset + command.length;
		helpBuffer.setLength(0);
		try {
			IRegion startlineInfo = document.getLineInformationOfOffset(startOffset);

			int lineNumber = document.getLineOfOffset(startOffset);

			if (IndentationUtils.isBlanks(document, startlineInfo.getOffset(), startOffset)) {
				placeMatchingBlanks(document, helpBuffer, lineNumber, startOffset);
				int endSelection = command.offset + command.length;
				command.offset = startlineInfo.getOffset();
				command.length = (command.length == 0) ? endOffset - command.offset : endSelection - command.offset;
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}

		command.text = helpBuffer.toString() + command.text;

	}

	@Override
	protected String getCommandText() {
		return "{"; //$NON-NLS-1$
	}
}
