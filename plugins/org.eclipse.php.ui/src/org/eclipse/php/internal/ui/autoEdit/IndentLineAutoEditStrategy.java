/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [459462]
 *     Thierry Blind [464039]
 *******************************************************************************/
package org.eclipse.php.internal.ui.autoEdit;

import java.io.IOException;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.*;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.*;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * @author guy.g
 * 
 */

public class IndentLineAutoEditStrategy extends DefaultIndentationStrategy implements IAutoEditStrategy {

	private final CurlyCloseAutoEditStrategy curlyCloseAutoEditStrategy = new CurlyCloseAutoEditStrategy();
	private final StringBuilder helpBuffer = new StringBuilder();

	IAfterNewLineAutoEditStrategy pairCurlyBracketAutoEditStrategy = new PairCurlyBracketAutoEditStrategy();

	private IndentationExtensionRegistry registry = IndentationExtensionRegistry.getInstance();

	public IndentLineAutoEditStrategy() {
	}

	/**
	 * 
	 * @param indentationObject
	 *            basic indentation preferences, can be null
	 */
	public IndentLineAutoEditStrategy(IndentationObject indentationObject) {
		setIndentationObject(indentationObject);
	}

	private void autoIndentAfterNewLine(final IStructuredDocument document, final DocumentCommand command) {
		try {

			helpBuffer.setLength(0);
			helpBuffer.append(command.text);

			placeMatchingBlanks(document, helpBuffer, document.getLineOfOffset(command.offset), command);

			int futureCaretPosition = -1;

			if (command.offset > 0) {
				final IAfterNewLineAutoEditStrategy autoEditStrategy = getAfterNewLineAutoEditStrategy(document,
						command);
				if (autoEditStrategy != null)
					futureCaretPosition = autoEditStrategy.autoEditAfterNewLine(document, command, helpBuffer);
			}

			final int startOffset = command.offset;
			final int endOffset = startOffset + command.length;

			IRegion firstLineInfo = document.getLineInformationOfOffset(startOffset);
			int firstLineOffset = firstLineInfo.getOffset();
			int firstLineLength = firstLineInfo.getLength();
			String firstLineText = document.get(firstLineOffset, firstLineLength);

			IRegion lastLineInfo;
			int lastLineOffset;
			int lastLineLength;
			String lastLineText;

			// In most case, selections are empty or single-line selections.
			if (firstLineOffset <= endOffset && endOffset <= firstLineOffset + firstLineLength) {
				lastLineInfo = firstLineInfo;
				lastLineOffset = firstLineOffset;
				lastLineLength = firstLineLength;
				lastLineText = firstLineText;
			} else {
				lastLineInfo = document.getLineInformationOfOffset(endOffset);
				lastLineOffset = lastLineInfo.getOffset();
				lastLineLength = lastLineInfo.getLength();
				lastLineText = document.get(lastLineOffset, lastLineLength);
			}

			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=464039
			// How it works: remove blank chars in front of first and last line
			// of selection, because "helpBuffer" already contains (new &
			// correct) indentation for those lines.
			//
			// Start by removing blank chars BEFORE actual selection.
			// Only valid if all chars between firstLineOffset and
			// startOffset - 1 are blank chars.
			if (startOffset > firstLineOffset) {
				int pos = 0;
				for (; pos < startOffset - firstLineOffset
						&& (firstLineText.charAt(pos) == ' ' || firstLineText.charAt(pos) == '\t'); pos++)
					;
				if (pos == startOffset - firstLineOffset) {
					// Tweak: also try to keep previous indentation when
					// "helpBuffer" starts with a newline char. It's mainly
					// cosmetic, first line of selection should keep its
					// blank chars (between firstLineOffset and startOffset - 1)
					// if its content (starting from startOffset) is put/pushed
					// on a new line.
					if (TextUtilities.startsWith(document.getLegalLineDelimiters(), helpBuffer.toString()) != -1) {
						helpBuffer.insert(0, firstLineText.substring(0, startOffset - firstLineOffset));
					}
					command.length += startOffset - firstLineOffset;
					command.offset = firstLineOffset;
				}
			}
			// Continue by removing all blank chars AFTER end of
			// selection.
			for (int i = endOffset - lastLineOffset; i < lastLineLength
					&& (lastLineText.charAt(i) == ' ' || lastLineText.charAt(i) == '\t'); i++, command.length++)
				;

			command.text = helpBuffer.toString();

			// if we need to put the caret at a position different then the end
			// of the text
			if (DefaultIndentationStrategy.getPairArrayParen()) {
				futureCaretPosition = DefaultIndentationStrategy.getPairArrayOffset();
				DefaultIndentationStrategy.unsetPairArrayParen();
			}
			if (futureCaretPosition != -1) {
				// running the command ourself
				document.replace(command.offset, command.length, command.text);
				// consuming the command
				command.length = 0;
				command.text = ""; //$NON-NLS-1$
				command.offset = futureCaretPosition;
				// moving the caret position
				document.getUndoManager().disableUndoManagement();
				document.replace(command.offset, command.length, command.text);
				document.getUndoManager().enableUndoManagement();
			}
		} catch (final BadLocationException exp) {
			Logger.logException(exp);
		}
	}

	@Override
	public void customizeDocumentCommand(final IDocument document, final DocumentCommand command) {
		if (command.text != null && TextUtilities.endsWith(document.getLegalLineDelimiters(), command.text) != -1) {
			setIndentationObject(null); // reset

			autoIndentAfterNewLine((IStructuredDocument) document, command);
		}
	}

	private IAfterNewLineAutoEditStrategy getAfterNewLineAutoEditStrategy(final IStructuredDocument document,
			final DocumentCommand command) throws BadLocationException {
		if (command.length > 0)
			return null;

		final int offset = command.offset;

		String currentState = FormatterUtils.getPartitionType(document, offset);

		// fixed bug 186710
		// scan for the first char that not equals to space or tab
		int charPosition = offset;
		char prevChar = '\0';
		while (charPosition-- >= 0) {
			prevChar = document.getChar(charPosition);
			if (prevChar != ' ' && prevChar != '\t') {
				break;
			}
		}

		if (TypingPreferences.closeCurlyBracket && prevChar == '{') {
			if (currentState == PHPPartitionTypes.PHP_DEFAULT)
				return pairCurlyBracketAutoEditStrategy;

		}
		return null;
	}

	private IIndentationStrategy getAutoEditStrategy(final char insertionStrategyKey) {
		switch (insertionStrategyKey) {
		case '}':
			return curlyCloseAutoEditStrategy;
		default:
			if (registry.hasExtensions()) {
				return registry.getExtensions().get(0);
			}
			return this;
		}
	}

	public void placeMatchingBlanks(final IStructuredDocument document, final StringBuilder result,
			final int lineNumber, final DocumentCommand command) throws BadLocationException {
		final int forOffset = command.offset;
		final IRegion endLineInfo = document.getLineInformationOfOffset(forOffset + command.length);
		// read the rest of the line
		final String lineText = document.get(forOffset + command.length,
				endLineInfo.getOffset() + endLineInfo.getLength() - (forOffset + command.length));
		final String trimedText = lineText.trim();

		final char insertionStrategyKey = trimedText.length() == 0 ? '{' : trimedText.charAt(0);
		final IIndentationStrategy indentationStrategy = getAutoEditStrategy(insertionStrategyKey);

		if (indentationStrategy instanceof IIndentationStrategyExtension1) {
			((IIndentationStrategyExtension1) indentationStrategy).placeMatchingBlanks(document, result, lineNumber,
					forOffset, getCurrentProgram(document));
		} else {
			indentationStrategy.placeMatchingBlanks(document, result, lineNumber, forOffset);
		}
	}

	private Program getCurrentProgram(final IStructuredDocument document) {
		final ISourceModule[] sourceModules = new ISourceModule[1];
		// resolve current sourceModule
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				IWorkbenchPage page = PHPUiPlugin.getActivePage();
				if (page != null) {
					IEditorPart editor = page.getActiveEditor();
					if (editor instanceof PHPStructuredEditor) {
						PHPStructuredEditor phpStructuredEditor = (PHPStructuredEditor) editor;
						if (phpStructuredEditor.getTextViewer() != null
								&& phpStructuredEditor.getDocument() == document) {
							sourceModules[0] = (ISourceModule) phpStructuredEditor.getModelElement();
						}
					}
				}
			}
		});

		// resolve AST
		Program program = null;
		if (sourceModules[0] != null) {
			try {
				program = SharedASTProvider.getAST(sourceModules[0], SharedASTProvider.WAIT_YES, null);
			} catch (ModelException e) {
				Logger.logException(e);
			} catch (IOException e) {
				Logger.logException(e);
			}
		}
		return program;
	}
}
