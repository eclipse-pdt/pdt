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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.DefaultIndentationStrategy;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.core.format.IIndentationStrategy;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * @author guy.g
 *
 */

public class IndentLineAutoEditStrategy extends DefaultIndentationStrategy implements IAutoEditStrategy {

	private final CurlyCloseAutoEditStrategy curlyCloseAutoEditStrategy = new CurlyCloseAutoEditStrategy();

	private final StringBuffer helpBuffer = new StringBuffer();

	IAfterNewLineAutoEditStrategy pairCurlyBracketAutoEditStrategy = new PairCurlyBracketAutoEditStrategy();

	private void autoIndentAfterNewLine(final IStructuredDocument document, final DocumentCommand command) {
		try {
			helpBuffer.setLength(0);
			helpBuffer.append(command.text);

			final int currentOffset = command.offset;

			final int lineNumber = document.getLineOfOffset(currentOffset);

			placeMatchingBlanks(document, helpBuffer, lineNumber, command);

			int futureCaretPosition = -1;

			if (currentOffset > 0) {
				final IAfterNewLineAutoEditStrategy autoEditStrategy = getAfterNewLineAutoEditStrategy(document, command);
				if (autoEditStrategy != null)
					futureCaretPosition = autoEditStrategy.autoEditAfterNewLine(document, command, helpBuffer);
			}

			final IRegion lineInfo = document.getLineInformation(lineNumber);

			final int startOffset = lineInfo.getOffset();
			final int length = lineInfo.getLength();

			final String lineText = document.get(startOffset, length);

			// find the first non blank char of the element.
			int i;
			for (i = 0; i < length && (lineText.charAt(i) == ' ' || lineText.charAt(i) == '\t'); i++)
				;

			// if we are in the blaks at the start of the element then select them
			// so they will be replaced with the MatchingBlanks.
			if (currentOffset < startOffset + i) {
				//if (command.length != 0) { // comment out in order to fix bug # 139437
				command.offset = Math.min(command.offset, startOffset);
				//}
				command.length = Math.max(i, command.length);
			}
			command.text = helpBuffer.toString();

			//if we need to put the caret at a position differnt then the end of the text
			if (futureCaretPosition != -1) {
				//runing the command ourselfs
				document.replace(command.offset, command.length, command.text);
				//consuming the command
				command.length = 0;
				command.text = "";
				command.offset = futureCaretPosition;
				//moving the caret position
				document.getUndoManager().disableUndoManagement();
				document.replace(command.offset, command.length, command.text);
				document.getUndoManager().enableUndoManagement();
			}
		} catch (final BadLocationException exp) {
			Logger.logException(exp);
		}
	}

	public void customizeDocumentCommand(final IDocument document, final DocumentCommand command) {
		if (command.text != null && TextUtilities.endsWith(document.getLegalLineDelimiters(), command.text) != -1)
			autoIndentAfterNewLine((IStructuredDocument) document, command);
	}

	private IAfterNewLineAutoEditStrategy getAfterNewLineAutoEditStrategy(final IStructuredDocument document, final DocumentCommand command) throws BadLocationException {
		if (command.length > 0)
			return null;
		final int offset = command.offset;

		String currentState = FormatterUtils.getPartitionType(document, offset, true);

		final char prevChar = document.getChar(offset - 1);
		if (TypingPreferences.closeCurlyBracket && prevChar == '{') {
			if (currentState != PHPPartitionTypes.PHP_DEFAULT)
				if (document.getLength() == offset)
					currentState = FormatterUtils.getPartitionType(document, offset - 1);
			if (currentState == PHPPartitionTypes.PHP_DEFAULT)
				return pairCurlyBracketAutoEditStrategy;

		}
		return null;
	}

	private IIndentationStrategy getAutoEditStrategy(final char insertionStrtegyKey) {
		switch (insertionStrtegyKey) {
			case '}':
				return curlyCloseAutoEditStrategy;
			default:
				return this;
		}
	}

	public void placeMatchingBlanks(final IStructuredDocument document, final StringBuffer result, final int lineNumber, final DocumentCommand command) throws BadLocationException {
		final int forOffset = command.offset;
		final IRegion lineInfo = document.getLineInformation(lineNumber);
		// read the rest of the line
		final String lineText = document.get(forOffset + command.length, lineInfo.getOffset() + lineInfo.getLength() - (forOffset + command.length));
		final String trimedText = lineText.trim();

		final char insertionStrategyKey = trimedText.length() == 0 ? '{' : trimedText.charAt(0);
		final IIndentationStrategy indentationStrategy = getAutoEditStrategy(insertionStrategyKey);

		indentationStrategy.placeMatchingBlanks(document, result, lineNumber, forOffset);
	}

}
