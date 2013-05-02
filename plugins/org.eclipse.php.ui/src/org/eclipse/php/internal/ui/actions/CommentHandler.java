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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;

/**
 * Handler class for the Comment Handlers
 * 
 * @author NirC, 2008
 */

public abstract class CommentHandler extends AbstractHandler implements
		IHandler {
	static final String SINGLE_LINE_COMMENT = "//"; //$NON-NLS-1$
	static final String OPEN_COMMENT = "/*"; //$NON-NLS-1$
	static final String CLOSE_COMMENT = "*/"; //$NON-NLS-1$

	public CommentHandler() {
		super();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		ITextEditor textEditor = null;
		if (editor instanceof ITextEditor)
			textEditor = (ITextEditor) editor;
		else {
			Object o = editor.getAdapter(ITextEditor.class);
			if (o != null)
				textEditor = (ITextEditor) o;
		}
		if (textEditor != null) {
			IDocument document = textEditor.getDocumentProvider().getDocument(
					textEditor.getEditorInput());
			if (document != null) {
				// get current text selection
				ITextSelection textSelection = getCurrentSelection(textEditor);
				if (textSelection.isEmpty()) {
					return null;
				}

				processAction(textEditor, document, textSelection);
			}
		}

		return null;
	}

	protected ITextSelection getCurrentSelection(ITextEditor textEditor) {
		ISelectionProvider provider = textEditor.getSelectionProvider();
		if (provider != null) {
			ISelection selection = provider.getSelection();
			if (selection instanceof ITextSelection) {
				return (ITextSelection) selection;
			}
		}
		return TextSelection.emptySelection();
	}

	void processAction(ITextEditor textEditor, IDocument document,
			ITextSelection textSelection) {
		// Implementations to override.
	}

	protected void removeOpenCloseComments(IDocument document, int offset,
			int endOffset) {
		try {
			int adjusted_length = endOffset - offset;

			// remove open comments
			String string = document.get(offset, adjusted_length);
			int index = string.lastIndexOf(OPEN_COMMENT);
			while (index != -1) {
				document.replace(offset + index, OPEN_COMMENT.length(), ""); //$NON-NLS-1$
				index = string.lastIndexOf(OPEN_COMMENT, index - 1);
				adjusted_length -= OPEN_COMMENT.length();
			}

			// remove close comments
			string = document.get(offset, adjusted_length);
			index = string.lastIndexOf(CLOSE_COMMENT);
			while (index != -1) {
				document.replace(offset + index, CLOSE_COMMENT.length(), ""); //$NON-NLS-1$
				index = string.lastIndexOf(CLOSE_COMMENT, index - 1);
				adjusted_length -= CLOSE_COMMENT.length();
			}

		} catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}
	}

	protected boolean isCommentLine(IDocument document, int line) {
		boolean isComment = false;

		try {
			IRegion region = document.getLineInformation(line);
			String string = document
					.get(region.getOffset(), region.getLength()).trim();
			isComment = (string.length() >= OPEN_COMMENT.length() && string
					.startsWith(OPEN_COMMENT))
					|| (string.length() >= SINGLE_LINE_COMMENT.length() && string
							.startsWith(SINGLE_LINE_COMMENT));
		} catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}
		return isComment;
	}

	protected void commentSingleLine(IDocument document, int openCommentOffset) {
		try {
			document.replace(openCommentOffset, 0, SINGLE_LINE_COMMENT);
		} catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}
	}

	protected void uncommentSingleLine(IDocument document, int openCommentOffset) {
		try {
			document.replace(openCommentOffset, SINGLE_LINE_COMMENT.length(),
					""); //$NON-NLS-1$
		} catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}
	}

	protected void commentMultiLine(IDocument document, int selectionStartLine,
			int selectionEndLine) {
		StringBuffer sb = new StringBuffer(SINGLE_LINE_COMMENT);
		try {
			for (int i = selectionStartLine; i < selectionEndLine; i++) {
				if (document.getLineLength(i) > 0) {
					int openCommentOffset = document.getLineOffset(i);
					int nextLineOffset = document.getLineOffset(i + 1);
					sb.append(
							document.get(openCommentOffset, nextLineOffset
									- openCommentOffset)).append(
							SINGLE_LINE_COMMENT);
				}
			}
			document.replace(document.getLineOffset(selectionStartLine),
					document.getLineOffset(selectionEndLine)
							- document.getLineOffset(selectionStartLine), sb
							.toString());
		} catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}
	}

	protected void uncommentMultiLine(IDocument document,
			int selectionStartLine, int selectionEndLine) {
		StringBuffer sb = new StringBuffer();
		try {
			for (int i = selectionStartLine; i < selectionEndLine; i++) {
				if (document.getLineLength(i) > 0) {
					int openCommentOffset = document.getLineOffset(i);
					int nextLineOffset = document.getLineOffset(i + 1);
					sb.append(document.get(openCommentOffset,
							nextLineOffset - openCommentOffset).substring(2));
				}
			}
			document.replace(document.getLineOffset(selectionStartLine),
					document.getLineOffset(selectionEndLine)
							+ SINGLE_LINE_COMMENT.length()
							- document.getLineOffset(selectionStartLine), sb
							.toString());
		} catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}
	}

	protected boolean isMoreThanOneContextBlockSelected(IDocument document,
			ITextSelection textSelection) {
		if (document == null) {
			assert false;
			return true;
		}
		IStructuredDocument structuredDocument = null;
		if (document instanceof IStructuredDocument) {
			structuredDocument = (IStructuredDocument) document;
		} else {
			return true;
		}

		IStructuredDocumentRegion[] structuredDocumentRegions = structuredDocument
				.getStructuredDocumentRegions(textSelection.getOffset(),
						textSelection.getLength());
		if (structuredDocumentRegions == null
				|| structuredDocumentRegions.length == 0) {
			assert false;
			return true;
		}

		if (structuredDocumentRegions.length == 1
				&& isPhpDocumentRegion(structuredDocumentRegions[0])) {
			// single PHP element and the selection is inside the element
			// boundaries
			return false;
		}

		// Handling case there is more then 1 region within the selection,
		// if we encounter PHP open/close Tag - it means we are not only within
		// HTML context
		for (IStructuredDocumentRegion structuredDocumentRegion : structuredDocumentRegions) {
			if (isPhpDocumentRegion(structuredDocumentRegion)) {
				return true;
			}
		}

		// all regions are !PHP
		return false;
	}

	private boolean isPhpDocumentRegion(
			IStructuredDocumentRegion structuredDocumentRegion) {
		return structuredDocumentRegion.getFirstRegion().getType() == PHPRegionContext.PHP_OPEN;
	}

	protected void displayCommentActinosErrorDialog(IEditorPart editor) {
		MessageDialog.openError(editor.getSite().getShell(),
				PHPUIMessages.AddBlockComment_error_title,
				PHPUIMessages.AddBlockComment_error_messageBadSelection); 
	}
}
