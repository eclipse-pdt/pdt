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

import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPartitioningException;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

/**
 * Action that encloses the editor's current selection with PHP block comment terminators
 * (<code>&#47;&#42;</code> and <code>&#42;&#47;</code>).
 */
public class AddBlockCommentAction extends BlockCommentAction {

	/**
	 * Creates a new instance.
	 * 
	 * @param bundle the resource bundle
	 * @param prefix a prefix to be prepended to the various resource keys
	 *   (described in <code>ResourceAction</code> constructor), or 
	 *   <code>null</code> if none
	 * @param editor the text editor
	 */
	public AddBlockCommentAction(ResourceBundle bundle, String prefix, ITextEditor editor) {
		super(bundle, prefix, editor);
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.actions.BlockCommentAction#runInternal(org.eclipse.jface.text.ITextSelection, org.eclipse.jface.text.IDocumentExtension3, org.eclipse.jdt.internal.ui.actions.BlockCommentAction.Edit.EditFactory)
	 */
	protected void runInternal(ITextSelection selection, IDocumentExtension3 docExtension, Edit.EditFactory factory) throws BadLocationException, BadPartitioningException {
		int selectionOffset = selection.getOffset();
		int selectionEndOffset = selectionOffset + selection.getLength();
		List edits = new LinkedList();

		if (docExtension instanceof IStructuredDocument) {
			IStructuredDocument sDoc = (IStructuredDocument) docExtension;
			IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(selectionOffset);
			ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(selectionOffset);
			
			ITextRegionCollection container = sdRegion;
			
			if(textRegion instanceof ITextRegionContainer){
				container = (ITextRegionContainer)textRegion;
				textRegion = container.getRegionAtCharacterOffset(selectionOffset);
			}

			if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
				PhpScriptRegion phpScriptRegion = (PhpScriptRegion) textRegion;
				ITypedRegion partition = PHPPartitionTypes.getPartition(phpScriptRegion, selectionOffset - container.getStartOffset() - phpScriptRegion.getStart());

				int phpRegionStart = container.getStartOffset(phpScriptRegion);

				handleFirstPartition(partition, edits, factory, selectionOffset, phpRegionStart);

				ITypedRegion lastPartition = partition;
				while (partition != null && phpRegionStart + partition.getOffset() + partition.getLength() < selectionEndOffset) {
					lastPartition = partition;
					partition = handleInteriorPartition(partition, edits, factory, docExtension, phpRegionStart);
				}
				if(partition == null){
					partition = lastPartition;
				}
				handleLastPartition(partition, edits, factory, selectionEndOffset, phpRegionStart);
			}
		}

		executeEdits(edits);
	}

	/**
	 * Handle the partition under the start offset of the selection.
	 * 
	 * @param partition the partition under the start of the selection
	 * @param edits the list of edits to later execute
	 * @param factory the factory for edits
	 * @param offset the start of the selection, which must lie inside <code>partition</code>
	 * @param offset where the PHP script region starts
	 */
	private void handleFirstPartition(ITypedRegion partition, List edits, Edit.EditFactory factory, int offset, int phpRegionStart) throws BadLocationException {

		int partOffset = partition.getOffset() + phpRegionStart;
		String partType = partition.getType();

		Assert.isTrue(partOffset <= offset, "illegal partition"); //$NON-NLS-1$

		// first partition: mark start of comment
		if (partType == PHPPartitionTypes.PHP_DEFAULT) {
			// PHP code: right where selection starts
			edits.add(factory.createEdit(offset, 0, getCommentStart()));
		} else if (isSpecialPartition(partType)) {
			// special types: include the entire partition
			edits.add(factory.createEdit(partOffset, 0, getCommentStart()));
		} // PHPDoc: no mark, will only start after comment
	}

	/**
	 * Handles partition boundaries within the selection. The end of the current
	 * partition and the start of the next partition are examined for whether
	 * they contain comment tokens that interfere with the created comment.
	 * <p>
	 * Comment tokens are removed from interior multi-line comments. PHPdoc
	 * comments are left as is; instead, multi-line comment tokens are inserted
	 * before and after PHPdoc partitions to ensure that the entire selected
	 * area is commented.
	 * </p>
	 * <p>
	 * The next partition is returned.
	 * </p>
	 * 
	 * @param partition the current partition
	 * @param edits the list of edits to add to
	 * @param factory the edit factory
	 * @param docExtension the document to get the partitions from
	 * @return the next partition after the current
	 * @param offset where the PHP script region starts
	 * @throws BadLocationException if accessing the document fails - this can only happen if the document gets modified concurrently
	 * @throws BadPartitioningException if the document does not have a PHP partitioning
	 */
	private ITypedRegion handleInteriorPartition(ITypedRegion partition, List edits, Edit.EditFactory factory, IDocumentExtension3 docExtension, int phpRegionStart) throws BadPartitioningException, BadLocationException {

		// end of previous partition
		String partType = partition.getType();
		int partEndOffset = phpRegionStart + partition.getOffset() + partition.getLength();
		int tokenLength = getCommentStart().length();

		boolean wasPHPDoc = false; // true if the previous partition is PHPDoc

		if (partType == PHPPartitionTypes.PHP_DOC) {
			wasPHPDoc = true;
		} else if (partType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT) {
			// already in a comment - remove ending mark
			edits.add(factory.createEdit(partEndOffset - tokenLength, tokenLength, "")); //$NON-NLS-1$
		}

		// advance to next partition
		IStructuredDocument sDoc = (IStructuredDocument) docExtension;
		IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(partEndOffset);
		ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(partEndOffset);
		
		ITextRegionCollection container = sdRegion;
		
		if(textRegion instanceof ITextRegionContainer){
			container = (ITextRegionContainer)textRegion;
			textRegion = container.getRegionAtCharacterOffset(partEndOffset);
		}

		if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			PhpScriptRegion phpScriptRegion = (PhpScriptRegion) textRegion;
			partition = PHPPartitionTypes.getPartition(phpScriptRegion, partEndOffset - container.getStartOffset() - phpScriptRegion.getStart());
			partType = partition.getType();
			phpRegionStart = container.getStartOffset() + phpScriptRegion.getStart();
		}
		if (textRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			return null;
		}

		// start of next partition
		if (wasPHPDoc) {
			// if previous was PHPDoc, and the current one is not, then add block comment start
			if (partType == PHPPartitionTypes.PHP_DEFAULT || isSpecialPartition(partType)) {
				edits.add(factory.createEdit(phpRegionStart + partition.getOffset(), 0, getCommentStart()));
			}
		} else { // !wasPHPDoc
			if (partType == PHPPartitionTypes.PHP_DOC) {
				// if next is PHPDoc, end block comment before
				edits.add(factory.createEdit(phpRegionStart + partition.getOffset(), 0, getCommentEnd()));
			} else if (partType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT) {
				// already in a comment - remove startToken
				edits.add(factory.createEdit(phpRegionStart + partition.getOffset(), getCommentStart().length(), "")); //$NON-NLS-1$
			}
		}
		return partition;
	}

	/**
	 * Handles the partition under the end of the selection. For normal java
	 * code, the comment end token is inserted at the selection end; if the
	 * selection ends inside a special (i.e. string, character, line comment)
	 * partition, the entire partition is included inside the comment.
	 * 
	 * @param partition the partition under the selection end offset
	 * @param edits the list of edits to add to
	 * @param factory the edit factory
	 * @param endOffset the end offset of the selection
	 * @param offset where the PHP script region starts
	 */
	private void handleLastPartition(ITypedRegion partition, List edits, Edit.EditFactory factory, int endOffset, int phpRegionStart) throws BadLocationException {
		String partType = partition.getType();

		if (partType == PHPPartitionTypes.PHP_DEFAULT) {
			// normal PHP: end comment where selection ends
			edits.add(factory.createEdit(endOffset, 0, getCommentEnd()));
		} else if (isSpecialPartition(partType)) {
			// special types: consume entire partition
			edits.add(factory.createEdit(phpRegionStart + partition.getOffset() + partition.getLength(), 0, getCommentEnd()));
		}
	}

	/**
	 * Returns whether <code>partType</code> is special, i.e. a PHP
	 * <code>String</code>,<code>Character</code>, or
	 * <code>Line End Comment</code> partition.
	 * 
	 * @param partType the partition type to check
	 * @return <code>true</code> if <code>partType</code> is special,
	 *         <code>false</code> otherwise
	 */
	private boolean isSpecialPartition(String partType) {
		return partType == PHPPartitionTypes.PHP_QUOTED_STRING || partType == PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT;
	}

	/* (non-javadoc)
	 * @see org.eclipse.php.internal.ui.actions.BlockCommentAction#isValidSelection(org.eclipse.jface.text.ITextSelection, org.eclipse.jface.text.IDocumentExtension3)
	 */
	protected boolean isValidSelection(ITextSelection selection, IDocumentExtension3 docExtension) {
		int offset = selection.getOffset();
		try {
			if (docExtension instanceof IStructuredDocument) {
				IStructuredDocument sDoc = (IStructuredDocument) docExtension;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(offset);
				ITextRegion region = sdRegion.getRegionAtCharacterOffset(offset);
				if (region.getType() == PHPRegionContext.PHP_CONTENT) {
					PhpScriptRegion phpScriptRegion = (PhpScriptRegion) region;
					region = phpScriptRegion.getPhpToken(offset - sdRegion.getStartOffset() - phpScriptRegion.getStart());
					if (!PHPPartitionTypes.isPHPMultiLineCommentState(region.getType())) {
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}
}
