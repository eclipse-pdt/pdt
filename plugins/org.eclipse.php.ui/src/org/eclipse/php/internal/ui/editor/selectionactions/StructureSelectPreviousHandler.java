/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dawid Pakuła - PHP Adaptation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.selectionactions;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.visitor.ApplyAll;
import org.eclipse.php.internal.core.corext.dom.Selection;
import org.eclipse.php.internal.core.corext.dom.SelectionAnalyzer;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.xml.ui.internal.handlers.StructuredSelectPreviousXMLHandler;

public class StructureSelectPreviousHandler extends StructuredSelectPreviousXMLHandler {
	private ISourceModule sourceModule = null;

	private static class PreviousNodeAnalyzer extends ApplyAll {
		private final int fOffset;
		private ASTNode fPreviousNode;

		private PreviousNodeAnalyzer(int offset) {
			fOffset = offset;
		}

		public static ASTNode perform(int offset, ASTNode lastCoveringNode) {
			PreviousNodeAnalyzer analyzer = new PreviousNodeAnalyzer(offset);
			lastCoveringNode.accept(analyzer);
			return analyzer.fPreviousNode;
		}

		@Override
		protected boolean apply(ASTNode node) {
			int start = node.getStart();
			int end = start + node.getLength();
			if (end == fOffset) {
				fPreviousNode = node;
				return true;
			} else {
				return (start < fOffset && fOffset < end);
			}
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		sourceModule = StructureSelectUtil.getSourceModule(event);
		super.execute(event);
		sourceModule = null;

		return null;
	}

	protected Region getNewSelectionRegion(IndexedRegion indexedRegion, ITextSelection textSelection) {
		if (sourceModule != null && StructureSelectUtil.isPHP(indexedRegion)) {
			Selection selection = Selection.createFromStartLength(textSelection.getOffset(), textSelection.getLength());
			SelectionAnalyzer selAnalyzer = new SelectionAnalyzer(selection, true);
			try {
				Program ast = StructureSelectUtil.getAST(sourceModule);
				ast.accept(selAnalyzer);
				Region oldSourceRange = new Region(textSelection.getOffset(), textSelection.getLength());
				if (oldSourceRange.getLength() == 0 && selAnalyzer.getLastCoveringNode() != null) {
					ASTNode previousNode = PreviousNodeAnalyzer.perform(oldSourceRange.getOffset(),
							selAnalyzer.getLastCoveringNode());
					if (previousNode != null)
						return StructureSelectUtil.getSelectedNodeSourceRange(sourceModule, previousNode);
				}
				ASTNode first = selAnalyzer.getFirstSelectedNode();
				if (first == null)
					return StructureSelectUtil.getLastCoveringNodeRange(oldSourceRange, sourceModule, selAnalyzer);

				ASTNode parent = first.getParent();
				if (parent == null)
					return StructureSelectUtil.getLastCoveringNodeRange(oldSourceRange, sourceModule, selAnalyzer);

				ASTNode previousNode = getPreviousNode(parent, selAnalyzer.getSelectedNodes()[0]);
				if (previousNode == parent)
					return StructureSelectUtil.getSelectedNodeSourceRange(sourceModule, parent);

				int offset = previousNode.getStart();
				int end = oldSourceRange.getOffset() + oldSourceRange.getLength() - 1;
				return StructureSelectUtil.createSourceRange(offset, end);
			} catch (ModelException e) {
			} catch (IOException e) {
			}
		}
		return super.getNewSelectionRegion(indexedRegion, textSelection);
	}

	private static ASTNode getPreviousNode(ASTNode parent, ASTNode node) {
		ASTNode[] siblingNodes = StructureSelectUtil.getSiblingNodes(node);
		if (siblingNodes == null || siblingNodes.length == 0)
			return parent;
		if (node == siblingNodes[0]) {
			return parent;
		} else {
			int index = StructureSelectUtil.findIndex(siblingNodes, node);
			if (index < 1)
				return parent;
			return siblingNodes[index - 1];
		}
	}
}
