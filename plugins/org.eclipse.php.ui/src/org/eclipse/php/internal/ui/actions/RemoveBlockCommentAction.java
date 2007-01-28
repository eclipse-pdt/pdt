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

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPartitioningException;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.editor.contentassist.PHPTextSequenceUtilities;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

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

	/*
	 * @see org.eclipse.jdt.internal.ui.actions.AddBlockCommentAction#runInternal(org.eclipse.jface.text.ITextSelection, org.eclipse.jface.text.IDocumentExtension3, org.eclipse.jdt.internal.ui.actions.AddBlockCommentAction.Edit.EditFactory)
	 */
	protected void runInternal(ITextSelection selection, IDocumentExtension3 docExtension, Edit.EditFactory factory) throws BadPartitioningException, BadLocationException {
		List edits = new LinkedList();
		int offset = selection.getOffset();
		int endOffset = offset + selection.getLength();

		do {
			if (docExtension instanceof IStructuredDocument) {
				IStructuredDocument sDoc = (IStructuredDocument)docExtension;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(offset);
				ITextRegion region = sdRegion.getRegionAtCharacterOffset(offset);
				if (region.getType() == PHPRegionContext.PHP_CONTENT) {
					PhpScriptRegion phpScriptRegion = (PhpScriptRegion)region;
					region = phpScriptRegion.getPhpToken(offset - sdRegion.getStartOffset() - phpScriptRegion.getStart());
					if (PHPPartitionTypes.isPHPMultiLineCommentState(region.getType())) {
						ITextRegion startRegion = PHPTextSequenceUtilities.getMultilineCommentStartRegion(sdRegion, offset);
						ITextRegion endRegion = PHPTextSequenceUtilities.getMultilineCommentEndRegion(sdRegion, offset);
						if (startRegion != null && endRegion != null) {
							int startCommentOffset = sdRegion.getStart() + phpScriptRegion.getStart() + startRegion.getStart();
							int endCommentOffset = sdRegion.getStart() + phpScriptRegion.getStart() + endRegion.getStart();
							edits.add(factory.createEdit(startCommentOffset, startRegion.getLength(), "")); //$NON-NLS-1$
							edits.add(factory.createEdit(endCommentOffset, getCommentEnd().length(), "")); //$NON-NLS-1$
							offset = endCommentOffset + endRegion.getLength();
						} else {
							break; // comment is not opened or not closed
						}
					} else {
						int endPartitionOffset = PHPPartitionTypes.getPartitionStart(phpScriptRegion, offset - sdRegion.getStartOffset() - phpScriptRegion.getStart());
						offset = sdRegion.getStart() + phpScriptRegion.getStart() + endPartitionOffset;
					}
				}
			}
		} while (offset < endOffset);

		executeEdits(edits);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.actions.BlockCommentAction#isValidSelection(org.eclipse.jface.text.ITextSelection, org.eclipse.jface.text.IDocumentExtension3)
	 */
	protected boolean isValidSelection(ITextSelection selection, IDocumentExtension3 docExtension) {
		int offset = selection.getOffset();
		try {
			if (docExtension instanceof IStructuredDocument) {
				IStructuredDocument sDoc = (IStructuredDocument)docExtension;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(offset);
				ITextRegion region = sdRegion.getRegionAtCharacterOffset(offset);
				if (region.getType() == PHPRegionContext.PHP_CONTENT) {
					PhpScriptRegion phpScriptRegion = (PhpScriptRegion)region;
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
