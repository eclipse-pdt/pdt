/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorExtension2;
import org.eclipse.ui.texteditor.TextEditorAction;


/**
 * Common block comment code.
 * 
 * @since 3.0
 */
public abstract class ToBeRemoved_BlockCommentAction extends TextEditorAction {
	
	/** The document partitioning */
	public String fDocumentPartitioning;
	
	/**
	 * Creates a new instance.
	 * @param bundle
	 * @param prefix
	 * @param editor
	 */
	public ToBeRemoved_BlockCommentAction(ResourceBundle bundle, String prefix, ITextEditor editor) {
		super(bundle, prefix, editor);
	}

	public void run() {
		if (!isEnabled())
			return;
			
		ITextEditor editor= getTextEditor();
		if (editor == null || !ensureEditable(editor))
			return;
		
		IDocumentProvider docProvider= editor.getDocumentProvider();
		IEditorInput input= editor.getEditorInput();
		if (docProvider == null || input == null)
			return;
			
		IDocument document= docProvider.getDocument(input);
		if (document == null)
			return;
			
		IDocumentExtension3 docExtension;
		if (document instanceof IDocumentExtension3)
			docExtension= (IDocumentExtension3) document;
		else
			return;
			
		ITextSelection selection= getCurrentSelection();
		if (!isValidSelection(selection, docExtension))
			return;
		
		if (!validateEditorInputState())
			return;
		
		IRewriteTarget target= (IRewriteTarget)editor.getAdapter(IRewriteTarget.class);
		if (target != null) {
			target.beginCompoundChange();
		}
		
		try {
			runInternal(selection, docExtension);
	
		} catch (BadLocationException e) {
			// can happen on concurrent modification, deletion etc. of the document 
			// -> don't complain, just bail out
		} catch (BadPartitioningException e) {
			// should not happen
			Assert.isTrue(false, "bad partitioning");  //$NON-NLS-1$
		} finally {
			if (target != null) {
				target.endCompoundChange();
			}
		}
	}

	/**
	 * Ensures that the editor is modifyable. If the editor is an instance of
	 * <code>ITextEditorExtension2</code>, its <code>validateEditorInputState</code> method 
	 * is called, otherwise, the result of <code>isEditable</code> is returned.
	 * 
	 * @param editor the editor to be checked
	 * @return <code>true</code> if the editor is editable, <code>false</code> otherwise
	 */
	protected boolean ensureEditable(ITextEditor editor) {
		Assert.isNotNull(editor);
	
		if (editor instanceof ITextEditorExtension2) {
			ITextEditorExtension2 ext= (ITextEditorExtension2) editor;
			return ext.validateEditorInputState();
		}
		
		return editor.isEditable();
	}

	/*
	 * @see org.eclipse.ui.texteditor.IUpdate#update()
	 */
	public void update() {
		super.update();
		
		if (isEnabled()) {
			if (!canModifyEditor()) {
				setEnabled(false);
				return;
			}
			ITextEditor editor= getTextEditor();
			if (editor != null) {
				IDocumentProvider docProvider= editor.getDocumentProvider();
				IEditorInput input= editor.getEditorInput();
				if (docProvider != null && input != null) {
					IDocument document= docProvider.getDocument(input);
					if (document != null) {
						ITextSelection selection = getCurrentSelection();
						if (selection != null && !selection.isEmpty()) {
							if (document instanceof IDocumentExtension3) {
								IDocumentExtension3 docExtension = (IDocumentExtension3) document;
								if (!isValidSelection(selection, docExtension)) {
									setEnabled(false);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Returns the editor's selection, or <code>null</code> if no selection can be obtained or the 
	 * editor is <code>null</code>.
	 * 
	 * @return the selection of the action's editor, or <code>null</code>
	 */
	protected ITextSelection getCurrentSelection() {
		ITextEditor editor= getTextEditor();
		if (editor != null) {
			ISelectionProvider provider= editor.getSelectionProvider();
			if (provider != null) {
				ISelection selection= provider.getSelection();
				if (selection instanceof ITextSelection) 
					return (ITextSelection) selection;
			}
		}
		return null;
	}

	/**
	 * Runs the real command once all the editor, document, and selection checks have succeeded.
	 * 
	 * @param selection the current selection we are being called for
	 * @param docExtension the document extension where we get the partitioning from
	 * @throws BadLocationException if an edition fails
	 * @throws BadPartitioningException if a partitioning call fails
	 */
	protected abstract void runInternal(ITextSelection selection, IDocumentExtension3 docExtension) throws BadLocationException, BadPartitioningException;

	/**
	 * Checks whether <code>selection</code> is valid.
	 * 
	 * @param selection the selection to check
	 * @param docExtension the document extension where we get the partitioning from
	 * @return <code>true</code> if the selection is valid, <code>false</code> otherwise
	 */
	protected abstract boolean isValidSelection(ITextSelection selection, IDocumentExtension3 docExtension);

	/**
	 * Returns the text to be inserted at the selection start.
	 * 
	 * @return the text to be inserted at the selection start
	 */
	protected String getCommentStart() {
		// for now: no space story
		return "/*"; //$NON-NLS-1$
	}

	/**
	 * Returns the text to be inserted at the selection end.
	 * 
	 * @return the text to be inserted at the selection end
	 */
	protected String getCommentEnd() {
		// for now: no space story
		return "*/"; //$NON-NLS-1$
	}

	public void configure(ISourceViewer sourceViewer, SourceViewerConfiguration configuration) {
		fDocumentPartitioning= configuration.getConfiguredDocumentPartitioning(sourceViewer);
	}
}
