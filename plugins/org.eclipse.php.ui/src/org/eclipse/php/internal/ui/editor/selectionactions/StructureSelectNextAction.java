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

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ISourceReference;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.visitor.ApplyAll;
import org.eclipse.php.internal.core.corext.dom.SelectionAnalyzer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;

public class StructureSelectNextAction extends StructureSelectionAction {

	private static class NextNodeAnalyzer extends ApplyAll {
		private final int fOffset;
		private ASTNode fNextNode;

		private NextNodeAnalyzer(int offset) {
			// super(true);
			fOffset = offset;
		}

		public static ASTNode perform(int offset, ASTNode lastCoveringNode) {
			NextNodeAnalyzer analyzer = new NextNodeAnalyzer(offset);
			lastCoveringNode.accept(analyzer);
			return analyzer.fNextNode;
		}

		@Override
		public boolean visit(ASTNode node) {
			int start = node.getStart();
			int end = node.getEnd();
			if (start == fOffset) {
				fNextNode = node;
				return true;
			} else {
				return (start < fOffset && fOffset < end);
			}
		}

		@Override
		protected boolean apply(ASTNode node) {
			int start = node.getStart();
			int end = node.getEnd();
			if (start == fOffset) {
				fNextNode = node;
				return true;
			} else {
				return (start < fOffset && fOffset < end);
			}
		}
	}

	public StructureSelectNextAction(PHPStructuredEditor editor,
			SelectionHistory history) {
		super(Messages.StructureSelectNextAction_3, editor, history);
		setToolTipText(Messages.StructureSelectNextAction_4);
		setDescription(Messages.StructureSelectNextAction_5);
		// PlatformUI
		// .getWorkbench()
		// .getHelpSystem()
		// .setHelp(this,
		// IJavaHelpContextIds.STRUCTURED_SELECT_NEXT_ACTION);
	}

	/*
	 * This constructor is for testing purpose only.
	 */
	public StructureSelectNextAction() {
	}

	/*
	 * non java doc
	 * 
	 * @see StructureSelectionAction#internalGetNewSelectionRange(ISourceRange,
	 * ICompilationUnit, SelectionAnalyzer)
	 */
	public ISourceRange internalGetNewSelectionRange(
			ISourceRange oldSourceRange, ISourceReference sr,
			SelectionAnalyzer selAnalyzer) throws ModelException {
		if (oldSourceRange.getLength() == 0
				&& selAnalyzer.getLastCoveringNode() != null) {
			ASTNode previousNode = NextNodeAnalyzer.perform(
					oldSourceRange.getOffset(),
					selAnalyzer.getLastCoveringNode());
			if (previousNode != null)
				return getSelectedNodeSourceRange(sr, previousNode);
		}
		org.eclipse.php.internal.core.ast.nodes.ASTNode first = selAnalyzer
				.getFirstSelectedNode();
		if (first == null)
			return getLastCoveringNodeRange(oldSourceRange, sr, selAnalyzer);

		org.eclipse.php.internal.core.ast.nodes.ASTNode parent = first
				.getParent();
		if (parent == null)
			return getLastCoveringNodeRange(oldSourceRange, sr, selAnalyzer);

		org.eclipse.php.internal.core.ast.nodes.ASTNode lastSelectedNode = selAnalyzer
				.getSelectedNodes()[selAnalyzer.getSelectedNodes().length - 1];
		org.eclipse.php.internal.core.ast.nodes.ASTNode nextNode = getNextNode(
				parent, lastSelectedNode);
		if (nextNode == parent)
			return getSelectedNodeSourceRange(sr, first.getParent());
		int offset = oldSourceRange.getOffset();
		int end = Math.min(sr.getSourceRange().getLength(), nextNode.getStart()
				+ nextNode.getLength() - 1);
		return createSourceRange(offset, end);
	}

	// -- helper methods for this class and subclasses

	private static org.eclipse.php.internal.core.ast.nodes.ASTNode getNextNode(
			org.eclipse.php.internal.core.ast.nodes.ASTNode parent,
			org.eclipse.php.internal.core.ast.nodes.ASTNode node) {
		org.eclipse.php.internal.core.ast.nodes.ASTNode[] siblingNodes = getSiblingNodes(node);
		if (siblingNodes == null || siblingNodes.length == 0)
			return parent;
		if (node == siblingNodes[siblingNodes.length - 1])
			return parent;
		else
			return siblingNodes[findIndex(siblingNodes, node) + 1];
	}

}
