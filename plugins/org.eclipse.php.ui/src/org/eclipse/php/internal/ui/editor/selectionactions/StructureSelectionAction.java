/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.selectionactions;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.StructuralPropertyDescriptor;
import org.eclipse.php.internal.core.corext.dom.Selection;
import org.eclipse.php.internal.core.corext.dom.SelectionAnalyzer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.editor.SharedASTProvider;

public abstract class StructureSelectionAction extends Action {

	public static final String NEXT = "SelectNextElement"; //$NON-NLS-1$
	public static final String PREVIOUS = "SelectPreviousElement"; //$NON-NLS-1$
	public static final String ENCLOSING = "SelectEnclosingElement"; //$NON-NLS-1$
	public static final String HISTORY = "RestoreLastSelection"; //$NON-NLS-1$

	private PHPStructuredEditor fEditor;
	private SelectionHistory fSelectionHistory;

	protected StructureSelectionAction(String text, PHPStructuredEditor editor, SelectionHistory history) {
		super(text);
		Assert.isNotNull(editor);
		Assert.isNotNull(history);
		fEditor = editor;
		fSelectionHistory = history;
	}

	/*
	 * This constructor is for testing purpose only.
	 */
	protected StructureSelectionAction() {
		super(""); //$NON-NLS-1$
	}

	/*
	 * Method declared in IAction.
	 */
	@Override
	public final void run() {
		ISourceModule inputElement = EditorUtility.getEditorInputModelElement(fEditor, false);
		if (!(inputElement instanceof ISourceModule && inputElement.exists()))
			return;

		// ITypeRoot typeRoot= (ITypeRoot) inputElement;
		ISourceRange sourceRange;
		try {
			sourceRange = inputElement.getSourceRange();
			if (sourceRange == null || sourceRange.getLength() == 0) {
				MessageDialog.openInformation(fEditor.getEditorSite().getShell(), Messages.StructureSelectionAction_0,
						Messages.StructureSelectionAction_1);
				return;
			}
		} catch (ModelException e) {
		}
		ITextSelection selection = getTextSelection();
		ISourceRange newRange = getNewSelectionRange(createSourceRange(selection), inputElement);
		// Check if new selection differs from current selection
		if (selection.getOffset() == newRange.getOffset() && selection.getLength() == newRange.getLength())
			return;
		fSelectionHistory.remember(new SourceRange(selection.getOffset(), selection.getLength()));
		try {
			fSelectionHistory.ignoreSelectionChanges();
			fEditor.selectAndReveal(newRange.getOffset(), newRange.getLength());
		} finally {
			fSelectionHistory.listenToSelectionChanges();
		}
	}

	public final ISourceRange getNewSelectionRange(ISourceRange oldSourceRange, ISourceModule typeRoot) {
		try {
			Program root = getAST(typeRoot);
			if (root == null)
				return oldSourceRange;
			Selection selection = Selection.createFromStartLength(oldSourceRange.getOffset(),
					oldSourceRange.getLength());
			SelectionAnalyzer selAnalyzer = new SelectionAnalyzer(selection, true);
			root.accept(selAnalyzer);
			return internalGetNewSelectionRange(oldSourceRange, typeRoot, selAnalyzer);
		} catch (ModelException e) {
			return new SourceRange(oldSourceRange.getOffset(), oldSourceRange.getLength());
		}
	}

	/**
	 * Subclasses determine the actual new selection.
	 * 
	 * @param oldSourceRange
	 *            the selected range
	 * @param sr
	 *            the current type root
	 * @param selAnalyzer
	 *            the selection analyzer
	 * @return return the new selection range
	 * @throws JavaModelException
	 *             if getting the source range fails
	 */
	abstract ISourceRange internalGetNewSelectionRange(ISourceRange oldSourceRange, ISourceReference sr,
			SelectionAnalyzer selAnalyzer) throws ModelException;

	protected final ITextSelection getTextSelection() {
		return (ITextSelection) fEditor.getSelectionProvider().getSelection();
	}

	// -- helper methods for subclasses to fit a node range into the source
	// range

	protected static ISourceRange getLastCoveringNodeRange(ISourceRange oldSourceRange, ISourceReference sr,
			SelectionAnalyzer selAnalyzer) throws ModelException {
		if (selAnalyzer.getLastCoveringNode() == null)
			return oldSourceRange;
		else
			return getSelectedNodeSourceRange(sr, selAnalyzer.getLastCoveringNode());
	}

	protected static ISourceRange getSelectedNodeSourceRange(ISourceReference sr, ASTNode nodeToSelect)
			throws ModelException {
		int offset = nodeToSelect.getStart();
		int end = Math.min(sr.getSourceRange().getLength(), nodeToSelect.getEnd() - 1);
		return createSourceRange(offset, end);
	}

	// -- private helper methods

	private static ISourceRange createSourceRange(ITextSelection ts) {
		return new SourceRange(ts.getOffset(), ts.getLength());
	}

	private static Program getAST(ISourceModule sr) {
		try {
			return SharedASTProvider.getAST(sr, SharedASTProvider.WAIT_YES, null);
		} catch (ModelException e) {
		} catch (IOException e) {
		}
		return null;
	}

	// -- helper methods for this class and subclasses

	static ISourceRange createSourceRange(int offset, int end) {
		int length = end - offset + 1;
		if (length == 0) // to allow 0-length selection
			length = 1;
		return new SourceRange(Math.max(0, offset), length);
	}

	static ASTNode[] getSiblingNodes(ASTNode node) {
		ASTNode parent = node.getParent();
		StructuralPropertyDescriptor locationInParent = node.getLocationInParent();
		if (locationInParent.isChildListProperty()) {
			List<? extends ASTNode> siblings = (List<? extends ASTNode>) parent.getStructuralProperty(locationInParent);
			return siblings.toArray(new ASTNode[siblings.size()]);
		}
		return null;
	}

	static int findIndex(Object[] array, Object o) {
		for (int i = 0; i < array.length; i++) {
			Object object = array[i];
			if (object == o)
				return i;
		}
		return -1;
	}

}
