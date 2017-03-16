/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies - adapt for PHP refactoring
 *******************************************************************************/
package org.eclipse.php.refactoring.core.visitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.TokenScanner;
import org.eclipse.php.internal.core.corext.dom.Selection;
import org.eclipse.php.internal.core.corext.dom.SelectionAnalyzer;
import org.eclipse.php.refactoring.core.SourceModuleSourceContext;

/**
 * Analyzer to check if a selection covers a valid set of statements of an
 * abstract syntax tree. The selection is valid iff
 * <ul>
 * <li>it does not start or end in the middle of a comment.</li>
 * <li>no extract characters except the empty statement ";" is included in the
 * selection.</li>
 * </ul>
 */
public class StatementAnalyzer extends SelectionAnalyzer {

	protected Program fCUnit;
	private IDocument fDocument;
	private ISourceModule fFile;
	private TokenScanner fScanner;
	private RefactoringStatus fStatus;

	public StatementAnalyzer(Program cunit, ISourceModule sourceModule, IDocument document, Selection selection,
			boolean traverseSelectedNode) throws CoreException, IOException {
		super(selection, traverseSelectedNode);
		Assert.isNotNull(cunit);
		fCUnit = cunit;
		fDocument = document;
		fFile = sourceModule;
		fStatus = new RefactoringStatus();
		fScanner = new TokenScanner(fCUnit.getAST().lexer(), document.get().toCharArray());
	}

	protected void checkSelectedNodes() {
		ASTNode[] nodes = getSelectedNodes();
		if (nodes.length == 0)
			return;

		ASTNode node = nodes[0];
		int selectionOffset = getSelection().getOffset();
		try {
			int pos = fScanner.getNextStartOffset(selectionOffset);
			if (pos == node.getStart()) {
				int lastNodeEnd = nodes[nodes.length - 1].getEnd();

				try {
					pos = fScanner.getNextStartOffset(lastNodeEnd);
				} catch (IllegalArgumentException e) {
					// The last statement;
					pos = -1;
				} catch (CoreException e) {
					pos = -1;
				}

				int selectionEnd = getSelection().getInclusiveEnd();
				if (pos > 0 && pos <= selectionEnd) {
					ISourceRange range = new SourceRange(lastNodeEnd, pos - lastNodeEnd);
					invalidSelection("The end of the selection contains characters that do not belong to a statement.", //$NON-NLS-1$
							new SourceModuleSourceContext(fFile,
									new org.eclipse.dltk.corext.SourceRange(range.getOffset(), range.getLength())));
				}
				return; // success
			}
		} catch (CoreException e) {
			// fall through
		}
		ISourceRange range = new SourceRange(selectionOffset, node.getStart() - selectionOffset + 1);
		invalidSelection("The beginning of the selection contains characters that do not belong to a statement.", //$NON-NLS-1$
				new SourceModuleSourceContext(fFile,
						new org.eclipse.dltk.corext.SourceRange(range.getOffset(), range.getLength())));
	}

	public RefactoringStatus getStatus() {
		return fStatus;
	}

	protected Program getCompilationUnit() {
		return fCUnit;
	}

	protected TokenScanner getTokenScanner() {
		return fScanner;
	}

	/*
	 * (non-Javadoc) Method declared in ASTVisitor
	 */
	public void endVisit(Program program) {
		if (!hasSelectedNodes()) {
			super.endVisit(program);
			return;
		}
		// ASTNode selectedNode = getFirstSelectedNode();
		// if (program != selectedNode) {
		// ASTNode parent = selectedNode.getParent();
		// TODO - add comment analyzer
		// fStatus.merge(CommentAnalyzer.perform(selection,
		// fScanner.getScanner(), parent.getStart(), parent.getLength()));
		// }
		if (!fStatus.hasFatalError())
			checkSelectedNodes();
		super.endVisit(program);
	}

	/*
	 * (non-Javadoc) Method declared in ASTVisitor
	 */
	public void endVisit(DoStatement doStatement) {
		ASTNode[] selectedNodes = getSelectedNodes();
		if (doAfterValidation(doStatement, selectedNodes)) {
			if (contains(selectedNodes, doStatement.getBody()) && contains(selectedNodes, doStatement.getCondition())) {
				invalidSelection("Operation not applicable to a 'do' statement's body and expression."); //$NON-NLS-1$
			}
		}

		super.endVisit(doStatement);
	}

	/*
	 * (non-Javadoc) Method declared in ASTVisitor
	 */
	public void endVisit(ForStatement node) {
		ASTNode[] selectedNodes = getSelectedNodes();
		if (doAfterValidation(node, selectedNodes)) {
			boolean containsExpression = contains(selectedNodes, node.conditions());
			boolean containsUpdaters = contains(selectedNodes, node.updaters());
			if (contains(selectedNodes, node.initializers()) && containsExpression) {
				invalidSelection("Operation not applicable to a 'for' statement's initializer and expression part."); //$NON-NLS-1$
			} else if (containsExpression && containsUpdaters) {
				invalidSelection("Operation not applicable to a 'for' statement's expression and updater part."); //$NON-NLS-1$
			} else if (containsUpdaters && contains(selectedNodes, node.getBody())) {
				invalidSelection("Operation not applicable to a 'for' statement's updater and body part."); //$NON-NLS-1$
			}
		}
		super.endVisit(node);
	}

	/*
	 * (non-Javadoc) Method declared in ASTVisitor
	 */
	public void endVisit(SwitchStatement node) {
		ASTNode[] selectedNodes = getSelectedNodes();
		if (doAfterValidation(node, selectedNodes)) {
			List cases = getSwitchCases(node);
			for (int i = 0; i < selectedNodes.length; i++) {
				ASTNode topNode = selectedNodes[i];
				if (cases.contains(topNode)) {
					invalidSelection(
							"Selection must either cover whole switch statement or parts of a single case block."); //$NON-NLS-1$
					break;
				}
			}
		}
		super.endVisit(node);
	}

	/*
	 * (non-Javadoc) Method declared in ASTVisitor
	 */
	public void endVisit(TryStatement node) {
		ASTNode firstSelectedNode = getFirstSelectedNode();
		if (getSelection().getEndVisitSelectionMode(node) == Selection.AFTER) {
			if (firstSelectedNode == node.getBody()) {
				invalidSelection("Selection must either cover whole try statement or parts of try or catch block."); //$NON-NLS-1$
			} else {
				List catchClauses = node.catchClauses();
				for (Iterator iterator = catchClauses.iterator(); iterator.hasNext();) {
					CatchClause element = (CatchClause) iterator.next();
					if (element == firstSelectedNode || element.getBody() == firstSelectedNode) {
						invalidSelection(
								"Selection must either cover whole try statement or parts of try or catch block."); //$NON-NLS-1$
						// TODO - make sure this is the right condition
					} else if (element.getClassName() == firstSelectedNode) {
						invalidSelection("Operation is not applicable to a catch block's argument declaration."); //$NON-NLS-1$
					}
				}
			}
		}

		try {
			throw new Exception(""); //$NON-NLS-1$
		} catch (Exception e) {

		}
		super.endVisit(node);
	}

	/*
	 * (non-Javadoc) Method declared in ASTVisitor
	 */
	public void endVisit(WhileStatement node) {
		ASTNode[] selectedNodes = getSelectedNodes();
		if (doAfterValidation(node, selectedNodes)) {
			if (contains(selectedNodes, node.getCondition()) && contains(selectedNodes, node.getBody())) {
				invalidSelection("Operation not applicable to a while statement's expression and body."); //$NON-NLS-1$
			}
		}
		super.endVisit(node);
	}

	private boolean doAfterValidation(ASTNode node, ASTNode[] selectedNodes) {
		return selectedNodes.length > 0 && node == selectedNodes[0].getParent()
				&& getSelection().getEndVisitSelectionMode(node) == Selection.AFTER;
	}

	protected void invalidSelection(String message) {
		fStatus.addFatalError(message);
		reset();
	}

	protected void invalidSelection(String message, RefactoringStatusContext context) {
		fStatus.addFatalError(message, context);
		reset();
	}

	private static List getSwitchCases(SwitchStatement node) {
		List result = new ArrayList();
		for (Iterator iter = node.getBody().statements().iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof SwitchCase)
				result.add(element);
		}
		return result;
	}

	protected static boolean contains(ASTNode[] nodes, ASTNode node) {
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] == node)
				return true;
		}
		return false;
	}

	protected static boolean contains(ASTNode[] nodes, List list) {
		for (int i = 0; i < nodes.length; i++) {
			if (list.contains(nodes[i]))
				return true;
		}
		return false;
	}

}
