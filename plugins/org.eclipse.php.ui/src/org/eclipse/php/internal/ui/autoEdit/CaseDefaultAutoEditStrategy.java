/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.CaseDefaultIndentationStrategy;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * 
 * @author guy.g
 * 
 */
public class CaseDefaultAutoEditStrategy extends CaseDefaultIndentationStrategy implements IAppliedAutoEditStrategy {

	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		applied = false;
		if (command.text == null) {
			return;
		}
		String checkWord = null;
		String addedWord = null;
		if (command.text.endsWith("t")) { //$NON-NLS-1$
			checkWord = "defaul"; //$NON-NLS-1$
			addedWord = "default"; //$NON-NLS-1$
		} else if (command.text.endsWith("e")) { //$NON-NLS-1$
			checkWord = "cas"; //$NON-NLS-1$
			addedWord = "case"; //$NON-NLS-1$
		} else {
			return;
		}
		IStructuredDocument sDocument = (IStructuredDocument) document;
		if (FormatterUtils.getPartitionType(sDocument, command.offset) != PHPPartitionTypes.PHP_DEFAULT) {
			return;
		}
		int length = checkWord.length();
		if (command.offset < length + 1) {
			return;
		}
		try {
			if (document.get(command.offset - length, length).equals(checkWord)) {
				autoIdentCaseDefault(sDocument, command, addedWord);
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
	}

	private StringBuilder buffer = new StringBuilder();
	private boolean applied = false;

	@Override
	public boolean wasApplied() {
		return applied;
	}

	private void autoIdentCaseDefault(IStructuredDocument document, DocumentCommand command, String addedWord)
			throws BadLocationException {
		int startOffset = command.offset - addedWord.length() + 1; // the +1 is
																	// because
																	// the last
																	// latter
																	// was not
																	// added yet
		int lineNumber = document.getLineOfOffset(command.offset);

		IRegion lineInfo = document.getLineInformation(lineNumber);
		int lineOffset = lineInfo.getOffset();
		String lineStart = document.get(lineOffset, startOffset - lineOffset);
		if (StringUtils.isBlank(lineStart)) {
			// making sure that the work "case"/"default" is the first word in
			// the line
			buffer.setLength(0);
			placeMatchingBlanks(document, buffer, lineNumber, startOffset);
			String bufferString = buffer.toString();
			if (!bufferString.equals(lineStart)) {
				// meaning we need to change the whitespaces before the word.
				command.length += (command.offset - lineOffset);
				command.offset = lineOffset;
				command.text = bufferString + addedWord;
			}
			applied = true;
		}
	}

}
