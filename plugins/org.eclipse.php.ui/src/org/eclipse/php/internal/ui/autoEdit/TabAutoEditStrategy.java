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
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.core.format.IFormatterCommonPrferences;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * This class handles the events coming from the Tab key. It also handles cases
 * in which Tab suppose to insert indentation to the line
 * 
 * @author yaronm
 * 
 */

public class TabAutoEditStrategy implements IAutoEditStrategy {

	private int lastIndentSize = 0;
	private String lastIndentString = ""; //$NON-NLS-1$
	private StringBuffer helpBuffer = new StringBuffer();
	private IStructuredDocument document;
	private DocumentCommand command;
	private IndentLineAutoEditStrategy autoIndentLineStrategy = new IndentLineAutoEditStrategy();

	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		if ((command.text != null) && command.text.equals("\t")) { //$NON-NLS-1$
			// override original tab command
			command.text = ""; //$NON-NLS-1$

			this.command = command;
			this.document = (IStructuredDocument) document;

			boolean isAutoIndent = PHPUiPlugin.getDefault()
					.getPreferenceStore()
					.getBoolean(PreferenceConstants.EDITOR_SMART_TAB);
			if (!isAutoIndent) {
				applyTabRule();
				return;
			}

			if (command.length != 0) {
				return;
			}
			tabPressed();
		}
	}

	private void tabPressed() {
		try {

			// get original line information
			final int lineNumber = document.getLineOfOffset(command.offset);
			final IRegion originalLineInfo = document
					.getLineInformation(lineNumber);
			final int originalLineStart = document.getLineOffset(lineNumber);
			int originalIndentSize = 0;
			int autoIndentSize = 0;

			boolean caretInBegining = (command.offset == originalLineStart);

			// if caret is either :
			// - located in line's begining
			// OR
			// - there's no alphabetical text before it
			if (caretInBegining || !hasTextBeforeCaret(originalLineInfo)) {
				// the current "visual" indentation size (Tab = 4 x space)
				originalIndentSize = calculateOriginalIndentSize(lineNumber,
						originalLineInfo);
				// the automatic needed total "visual" indentation size (Tab = 4
				// x space)
				autoIndentSize = calculateAutoIndentSize(lineNumber);

				// calculateAutoIndentSize(lineNumber) had a runtime Exception
				// and returns -1
				if (autoIndentSize == -1) {
					applyTabRule();
					return;
				}
				// checks whether caret is before a space OR Tab character
				boolean emptyCharAfterOldCaretPos = hasEmptyCharAfterCaret(
						lineNumber, originalLineStart);
				if (autoIndentSize >= originalIndentSize) {
					applyIndent(lineNumber, originalLineStart);

					// if the caret "Visual" position did not change and it is
					// exactly in the begining
					// of an alphabetical text
					if ((autoIndentSize == originalIndentSize)
							&& !emptyCharAfterOldCaretPos) {
						applyTabRule();
					}
				}
				// autoIndentSize < originalIndentSize, means that indentation
				// will shorten the line
				else {
					if (hasEmptyCharAfterCaret(lineNumber, originalLineStart)) {
						// this indentation will shorten the line
						applyIndent(lineNumber, originalLineStart);
					} else {
						// this will not do auto indentation, but will apply a
						// Tab
						applyTabRule();
					}
				}
			}

			// caret is located in the middle of line and there's text before it
			else {
				applyTabRule();
			}

		} catch (BadLocationException exp) {
			Logger.logException(exp);
		}
	}

	// This method check if there's an alphabetical text before the caret's
	// location
	private boolean hasTextBeforeCaret(IRegion originalLineInfo)
			throws BadLocationException {
		int length = originalLineInfo.getLength();
		int lineOffset = originalLineInfo.getOffset();
		String lineText = document.get(lineOffset, length);
		int caretIndexInLine = (command.offset - lineOffset);

		for (int i = 0; i < caretIndexInLine; i++) {
			if ((lineText.charAt(i) != ' ') && (lineText.charAt(i) != '\t'))
				return true;
		}
		return false;
	}

	// This method checks if there's an empty character (' ' OR '\t')
	private boolean hasEmptyCharAfterCaret(int lineNumber, int originalLineStart)
			throws BadLocationException {
		boolean result = false;
		IRegion lineInfo = document.getLineInformation(lineNumber);
		int length = lineInfo.getLength();
		if (length == 0)
			return false;
		String lineText = document.get(originalLineStart, length);
		int caretIndexInString = command.offset - originalLineStart;

		// in case the caret is located at the line's end
		if (caretIndexInString == lineText.length())
			return false;

		char charAfterCaret = lineText.charAt(caretIndexInString);
		if (charAfterCaret == ' ' || charAfterCaret == '\t')
			result = true;
		return result;

	}

	// This method calculates the current indentation size of the given line
	// Note : it considers Tab as 4 spaces
	private int calculateOriginalIndentSize(int lineNumber, IRegion lineInfo)
			throws BadLocationException {

		int startOffset = lineInfo.getOffset();
		int length = lineInfo.getLength();

		String lineText = document.get(startOffset, length);
		int result = 0;
		for (int i = 0; i < lineText.length(); i++) {
			switch (lineText.charAt(i)) {
			default:
				i = lineText.length();
				break;
			case ' ':
				result += 1;
				break;
			case '\t':
				result += 4;
				break;
			}
		}
		return result;
	}

	// This method applies the appropriate indentation on the given line
	private void applyIndent(int lineNumber, int offset)
			throws BadLocationException {
		command.offset = offset;
		command.length = getCurrentIndentLength(lineNumber);
		command.text = helpBuffer.toString();
	}

	// This method returns the length of the line's indentation.
	// Note : Tab in this case is considered as 1 character.
	private int getCurrentIndentLength(int lineNumber)
			throws BadLocationException {
		IRegion lineInfo = document.getLineInformation(lineNumber);

		int startOffset = lineInfo.getOffset();
		int length = lineInfo.getLength();

		String lineText = document.get(startOffset, length);

		// find the first non blank char of the element.
		int i;
		for (i = 0; i < length
				&& (lineText.charAt(i) == ' ' || lineText.charAt(i) == '\t'); i++)
			;
		return i;
	}

	// this method returns the auto indentation size. (calculates tab as X4
	// spaces)
	private int calculateAutoIndentSize(int lineNumber) {
		helpBuffer.setLength(0);
		try {
			autoIndentLineStrategy.placeMatchingBlanks(document, helpBuffer,
					lineNumber, command);
		} catch (Exception e) {
			Logger.logException(e);
			return -1;
		}
		int result = 0;
		String str = helpBuffer.toString();
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
			case ' ':
				result += 1;
				break;
			case '\t':
				result += 4;
				break;
			}
		}
		return result;
	}

	// This method applies the standard Tab rule and will perform a regular tab
	private void applyTabRule() {
		IFormatterCommonPrferences formatterCommonPrferences = FormatterUtils
				.getFormatterCommonPrferences();
		char indentChar = formatterCommonPrferences
				.getIndentationChar(document);

		if (indentChar == ' ') {
			// determine where in line this command begins
			int lineOffset = -1;
			try {
				IRegion lineInfo = document
						.getLineInformationOfOffset(command.offset);
				lineOffset = command.offset - lineInfo.getOffset();
			} catch (BadLocationException e) {
				Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
			}

			int indentSize = formatterCommonPrferences
					.getIndentationSize(document);

			if (lineOffset > 0) {
				lineOffset %= indentSize;
				indentSize -= lineOffset;
			}

			if (indentSize == 0) {
				indentSize = formatterCommonPrferences
						.getIndentationSize(document);
			}

			command.text += getIndentationString(indentSize);
		} else {
			command.text += "\t"; //$NON-NLS-1$
		}
	}

	// This method creates a string with spaces
	private String getIndentationString(int indentSize) {
		if (indentSize == lastIndentSize) {
			return lastIndentString;
		}
		lastIndentSize = indentSize;
		char[] result = new char[indentSize];
		for (int i = 0; i < result.length; i++) {
			result[i] = ' ';
		}
		lastIndentString = String.valueOf(result);
		return lastIndentString;
	}
}
