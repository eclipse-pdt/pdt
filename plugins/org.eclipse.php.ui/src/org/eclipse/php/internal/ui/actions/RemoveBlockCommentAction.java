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
package org.eclipse.php.internal.ui.actions;

import java.util.ResourceBundle;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPartitioningException;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.core.internal.undo.IStructuredTextUndoManager;

/**
 * Action that removes the enclosing comment marks from a Java block comment.
 * 
 * @since 3.0
 */
public class RemoveBlockCommentAction extends BlockCommentAction {

	/**
	 * Creates a new instance.
	 * 
	 * @param bundle the resource bundle
	 * @param prefix a prefix to be prepended to the various resource keys
	 *   (described in <code>ResourceAction</code> constructor), or 
	 *   <code>null</code> if none
	 * @param editor the text editor
	 */
	public RemoveBlockCommentAction(ResourceBundle bundle, String prefix, ITextEditor editor) {
		super(bundle, prefix, editor);
	}

	protected void runInternal(ITextSelection selection, IDocumentExtension3 docExtension) throws BadPartitioningException, BadLocationException {
		int offset = selection.getOffset();
		int endOffset = offset + selection.getLength();
		if (!(docExtension instanceof IStructuredDocument)) {
			return;
		}
		
		MultiTextEdit multiEdit = new MultiTextEdit();
		IStructuredDocument sDoc = (IStructuredDocument) docExtension;
		do {
			IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(offset);
			ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(offset);

			ITextRegionCollection container = sdRegion;

			if (textRegion instanceof ITextRegionContainer) {
				container = (ITextRegionContainer) textRegion;
				textRegion = container.getRegionAtCharacterOffset(offset);
			}

			if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
				PhpScriptRegion phpScriptRegion = (PhpScriptRegion) textRegion;
				textRegion = phpScriptRegion.getPhpToken(offset - container.getStartOffset() - phpScriptRegion.getStart());
				if (PHPPartitionTypes.isPHPMultiLineCommentState(textRegion.getType())) {
					ITextRegion startRegion = PHPPartitionTypes.getPartitionStartRegion(phpScriptRegion, textRegion.getStart());
					ITextRegion endRegion = PHPPartitionTypes.getPartitionEndRegion(phpScriptRegion, textRegion.getStart());
					if (startRegion.getType() == PHPRegionTypes.PHP_COMMENT_START && endRegion.getType() == PHPRegionTypes.PHP_COMMENT_END) {
						int startCommentOffset = container.getStart() + phpScriptRegion.getStart() + startRegion.getStart();
						int endCommentOffset = container.getStart() + phpScriptRegion.getStart() + endRegion.getStart();
						
						multiEdit.addChild(new DeleteEdit(startCommentOffset, startRegion.getLength()));
						multiEdit.addChild(new DeleteEdit(endCommentOffset, getCommentEnd().length()));
						
						offset = endCommentOffset + endRegion.getLength();
					} else {
						break; // comment is not opened or not closed
					}
				} else {
					int endPartitionOffset = PHPPartitionTypes.getPartitionStart(phpScriptRegion, offset - container.getStartOffset() - phpScriptRegion.getStart());
					offset = container.getStart() + phpScriptRegion.getStart() + endPartitionOffset;
				}
			} else {
				break;
			}
		} while (offset < endOffset);

		IStructuredTextUndoManager undoManager = sDoc.getUndoManager();
		undoManager.beginRecording(this);
		multiEdit.apply(sDoc);
		undoManager.endRecording(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.actions.BlockCommentAction#isValidSelection(org.eclipse.jface.text.ITextSelection, org.eclipse.jface.text.IDocumentExtension3)
	 */
	protected boolean isValidSelection(ITextSelection selection, IDocumentExtension3 docExtension) {
		int offset = selection.getOffset();
		try {
			if (docExtension instanceof IStructuredDocument) {
				IStructuredDocument sDoc = (IStructuredDocument) docExtension;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(offset);
				ITextRegion region = sdRegion.getRegionAtCharacterOffset(offset);
				if (region != null && region.getType() == PHPRegionContext.PHP_CONTENT) {
					PhpScriptRegion phpScriptRegion = (PhpScriptRegion) region;
					region = phpScriptRegion.getPhpToken(offset - sdRegion.getStartOffset() - phpScriptRegion.getStart());
					if (PHPPartitionTypes.isPHPMultiLineCommentState(region.getType())) {
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}
}
