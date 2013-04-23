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

import java.io.IOException;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.DefaultIndentationStrategy;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.core.format.IIndentationStrategy;
import org.eclipse.php.internal.core.format.IIndentationStrategyExtension1;
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

public class IndentLineAutoEditStrategy extends DefaultIndentationStrategy
		implements IAutoEditStrategy {

	private final CurlyCloseAutoEditStrategy curlyCloseAutoEditStrategy = new CurlyCloseAutoEditStrategy();
	private final ParenthesesCloseAutoEditStrategy parenCloseAutoEditStrategy = new ParenthesesCloseAutoEditStrategy();

	private final StringBuffer helpBuffer = new StringBuffer();

	IAfterNewLineAutoEditStrategy pairCurlyBracketAutoEditStrategy = new PairCurlyBracketAutoEditStrategy();
	IAfterNewLineAutoEditStrategy pairParenthesesAutoEditStrategy = new PairParenthesesAutoEditStrategy();

	private IndentationExtensionRegistry registry = IndentationExtensionRegistry
			.getInstance();

	private void autoIndentAfterNewLine(final IStructuredDocument document,
			final DocumentCommand command) {
		try {

			helpBuffer.setLength(0);
			helpBuffer.append(command.text);

			final int currentOffset = command.offset;

			final int lineNumber = document.getLineOfOffset(currentOffset);

			placeMatchingBlanks(document, helpBuffer, lineNumber, command);

			int futureCaretPosition = -1;

			if (currentOffset > 0) {
				final IAfterNewLineAutoEditStrategy autoEditStrategy = getAfterNewLineAutoEditStrategy(
						document, command);
				if (autoEditStrategy != null)
					futureCaretPosition = autoEditStrategy
							.autoEditAfterNewLine(document, command, helpBuffer);
			}

			final IRegion lineInfo = document.getLineInformation(lineNumber);

			final int startOffset = lineInfo.getOffset();
			final int length = lineInfo.getLength();

			final String lineText = document.get(startOffset, length);

			// find the first non blank char of the element.
			int i;
			for (i = 0; i < length
					&& (lineText.charAt(i) == ' ' || lineText.charAt(i) == '\t'); i++)
				;

			// if we are in the blaks at the start of the element then select
			// them
			// so they will be replaced with the MatchingBlanks.
			if (currentOffset < startOffset + i) {
				// if (command.length != 0) { // comment out in order to fix bug
				// # 139437
				command.offset = Math.min(command.offset, startOffset);
				// }
				command.length = Math.max(i, command.length);
			}
			command.text = helpBuffer.toString();

			// if we need to put the caret at a position differnt then the end
			// of the text
			if (DefaultIndentationStrategy.getPairArrayParen()) {
				futureCaretPosition = DefaultIndentationStrategy
						.getPairArrayOffset();
				DefaultIndentationStrategy.unsetPairArrayParen();
			}
			if (futureCaretPosition != -1) {
				// runing the command ourselfs
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

	public void customizeDocumentCommand(final IDocument document,
			final DocumentCommand command) {
		if (command.text != null
				&& TextUtilities.endsWith(document.getLegalLineDelimiters(),
						command.text) != -1)
			autoIndentAfterNewLine((IStructuredDocument) document, command);
	}

	private IAfterNewLineAutoEditStrategy getAfterNewLineAutoEditStrategy(
			final IStructuredDocument document, final DocumentCommand command)
			throws BadLocationException {
		if (command.length > 0)
			return null;
		final int offset = command.offset;

		String currentState = FormatterUtils.getPartitionType(document, offset,
				true);

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

		if (TypingPreferences.closeBrackets
				&& (prevChar == '(' || prevChar == '[')) {
			if (currentState != PHPPartitionTypes.PHP_DEFAULT) {
				if (document.getLength() == offset) {
					currentState = FormatterUtils.getPartitionType(document,
							offset - 1);
				}
			}
			if (currentState == PHPPartitionTypes.PHP_DEFAULT) {
				return pairParenthesesAutoEditStrategy;
			}
		}

		if (TypingPreferences.closeCurlyBracket && prevChar == '{') {
			if (currentState != PHPPartitionTypes.PHP_DEFAULT)
				if (document.getLength() == offset)
					currentState = FormatterUtils.getPartitionType(document,
							offset - 1);
			if (currentState == PHPPartitionTypes.PHP_DEFAULT)
				return pairCurlyBracketAutoEditStrategy;

		}
		return null;
	}

	private IIndentationStrategy getAutoEditStrategy(
			final char insertionStrtegyKey) {
		switch (insertionStrtegyKey) {
		case '}':
			return curlyCloseAutoEditStrategy;
		case ')':
		case ']':
			return parenCloseAutoEditStrategy;
		default:
			if (registry.hasExtensions()) {
				return registry.getExtensions().get(0);
			}
			return this;
		}
	}

	public void placeMatchingBlanks(final IStructuredDocument document,
			final StringBuffer result, final int lineNumber,
			final DocumentCommand command) throws BadLocationException {
		final int forOffset = command.offset;
		final IRegion lineInfo = document.getLineInformation(lineNumber);
		// read the rest of the line
		final String lineText = document.get(forOffset + command.length,
				lineInfo.getOffset() + lineInfo.getLength()
						- (forOffset + command.length));
		final String trimedText = lineText.trim();

		final char insertionStrategyKey = trimedText.length() == 0 ? '{'
				: trimedText.charAt(0);
		final IIndentationStrategy indentationStrategy = getAutoEditStrategy(insertionStrategyKey);

		if (indentationStrategy instanceof IIndentationStrategyExtension1) {
			((IIndentationStrategyExtension1) indentationStrategy)
					.placeMatchingBlanks(document, result, lineNumber,
							forOffset, getCurrentProgram(document));
		} else {
			indentationStrategy.placeMatchingBlanks(document, result,
					lineNumber, forOffset);
		}
	}

	private Program getCurrentProgram(final IStructuredDocument document) {
		final ISourceModule[] sourceModules = new ISourceModule[1];
		// resolve current sourceModule
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PHPUiPlugin.getActivePage();
				if (page != null) {
					IEditorPart editor = page.getActiveEditor();
					if (editor instanceof PHPStructuredEditor) {
						PHPStructuredEditor phpStructuredEditor = (PHPStructuredEditor) editor;
						if (phpStructuredEditor.getTextViewer() != null
								&& phpStructuredEditor != null
								&& phpStructuredEditor.getDocument() == document) {
							if (phpStructuredEditor != null
									&& phpStructuredEditor.getTextViewer() != null) {
								sourceModules[0] = (ISourceModule) phpStructuredEditor
										.getModelElement();
							}
						}
					}
				}
			}
		});

		// resolve AST
		Program program = null;
		if (sourceModules[0] != null) {
			try {
				program = SharedASTProvider.getAST(sourceModules[0],
						SharedASTProvider.WAIT_YES, null);
			} catch (ModelException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return program;
	}
}
