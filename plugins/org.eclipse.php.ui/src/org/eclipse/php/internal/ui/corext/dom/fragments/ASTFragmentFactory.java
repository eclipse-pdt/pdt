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
package org.eclipse.php.internal.ui.corext.dom.fragments;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.InfixExpression;
import org.eclipse.php.internal.core.ast.visitor.HierarchicalVisitor;
import org.eclipse.php.internal.core.corext.SourceRange;
import org.eclipse.php.internal.core.corext.dom.Selection;
import org.eclipse.php.internal.core.corext.dom.SelectionAnalyzer;

/**
 * Creates various differing kinds of IASTFragments, all through a very narrow
 * interface. The kind of IASTFragment produced will depend on properties of the
 * parameters supplied to the factory methods, such as the types and
 * characteristics of AST nodes, or the location of source ranges.
 * 
 * In general, the client will not be aware of exactly what kind of fragment is
 * obtained from these methods. Beyond the functionality provided by the
 * IASTFragment interface, the client can know, however, based on the parameters
 * passed, some things about the created fragment. See the documentation of the
 * factory methods.
 * 
 * @see IASTFragment
 * 
 */
public class ASTFragmentFactory {

	/**
	 * If possible, this method creates a fragment whose source code range is
	 * <code>range</code> within compilation unit <code>cu</code>, and which
	 * resides somewhere within the subtree identified by <code>scope</code>.
	 * 
	 * XXX: more doc (current assertions about input vs. output)
	 * 
	 * @param range
	 *            The source range which the create fragment must have.
	 * @param scope
	 *            A node identifying the AST subtree in which the fragment must
	 *            lie.
	 * @param cu
	 *            The compilation unit to which the source range applies, and to
	 *            which the AST corresponds.
	 * @return IASTFragment A fragment whose source range is <code>range</code>
	 *         within compilation unit <code>cu</code>, residing somewhere
	 *         within the AST subtree identified by <code>scope</code>.
	 * @throws JavaModelException
	 */
	public static IASTFragment createFragmentForSourceRange(SourceRange range,
			ASTNode scope, IDocument document) throws Exception {
		SelectionAnalyzer sa = new SelectionAnalyzer(
				Selection.createFromStartLength(range.getOffset(),
						range.getLength()), false);
		scope.accept(sa);
		if (isSingleNodeSelected(sa, range, document, scope))
			return ASTFragmentFactory.createFragmentForFullSubtree(
					sa.getFirstSelectedNode(), null);
		if (isEmptySelectionCoveredByANode(range, sa))
			return ASTFragmentFactory.createFragmentForFullSubtree(
					sa.getLastCoveringNode(), null);
		return ASTFragmentFactory.createFragmentForSubPartBySourceRange(
				sa.getLastCoveringNode(), range, scope, document);
	}

	/**
	 * Returns <code>null</code> if the indices, taken with respect to the node,
	 * do not correspond to a valid node-sub-part fragment.
	 * 
	 * @param document
	 * @throws Exception
	 */
	private static IASTFragment createFragmentForSubPartBySourceRange(
			ASTNode node, SourceRange range, ASTNode scope, IDocument document)
			throws Exception {
		return FragmentForSubPartBySourceRangeFactory.createFragmentFor(node,
				range, scope, document);
	}

	private static boolean isEmptySelectionCoveredByANode(SourceRange range,
			SelectionAnalyzer sa) {
		return range.getLength() == 0 && sa.getFirstSelectedNode() == null
				&& sa.getLastCoveringNode() != null;
	}

	private static boolean isSingleNodeSelected(SelectionAnalyzer sa,
			SourceRange range, IDocument document, ASTNode scope)
			throws BadLocationException {
		return sa.getSelectedNodes().length == 1
				&& !rangeIncludesNonWhitespaceOutsideNode(range,
						sa.getFirstSelectedNode(), document, scope);
	}

	private static boolean rangeIncludesNonWhitespaceOutsideNode(
			SourceRange range, ASTNode node, IDocument document, ASTNode scope)
			throws BadLocationException {
		return Util.rangeIncludesNonWhitespaceOutsideRange(range,
				new SourceRange(node), document);
	}

	/**
	 * Creates and returns a fragment representing the entire subtree rooted at
	 * <code>node</code>. It is not true in general that the node to which the
	 * produced IASTFragment maps (see {@link IASTFragment IASTFragment}) will
	 * be <code>node</code>.
	 * 
	 * XXX: more doc (current assertions about input vs. output)
	 * 
	 * @param astFragment
	 */
	public static IASTFragment createFragmentForFullSubtree(ASTNode node,
			IASTFragment astFragment) {
		IASTFragment result = FragmentForFullSubtreeFactory.createFragmentFor(
				node, astFragment);
		Assert.isNotNull(result);
		return result;
	}

	private static class FragmentForSubPartBySourceRangeFactory extends
			FragmentFactory {
		private SourceRange fRange;
		private ASTNode fScope;

		private Exception modelException = null;
		private IDocument fDocument;

		public static IASTFragment createFragmentFor(ASTNode node,
				SourceRange range, ASTNode scope, IDocument document)
				throws Exception {
			return new FragmentForSubPartBySourceRangeFactory().createFragment(
					node, range, scope, document);
		}

		public boolean visit(InfixExpression node) {
			try {
				setFragment(createInfixExpressionSubPartFragmentBySourceRange(
						node, fRange, fScope, fDocument));
			} catch (Exception e) {
				modelException = e;
			}
			return false;
		}

		public boolean visit(ASTNode node) {
			// let fragment be null
			return false;
		}

		protected IASTFragment createFragment(ASTNode node, SourceRange range,
				ASTNode scope, IDocument document) throws Exception {
			fRange = range;
			fScope = scope;
			fDocument = document;
			IASTFragment result = createFragment(node, null);
			if (modelException != null)
				throw modelException;
			return result;
		}

		private static IExpressionFragment createInfixExpressionSubPartFragmentBySourceRange(
				InfixExpression node, SourceRange range, ASTNode scope,
				IDocument document) throws Exception {
			return AssociativeInfixExpressionFragment
					.createSubPartFragmentBySourceRange(node, range, document);
		}
	}

	private static class FragmentForFullSubtreeFactory extends FragmentFactory {

		public static IASTFragment createFragmentFor(ASTNode node,
				IASTFragment astFragment) {
			return new FragmentForFullSubtreeFactory().createFragment(node,
					astFragment);
		}

		public boolean visit(InfixExpression node) {
			/*
			 * Try creating an associative infix expression fragment /* for the
			 * full subtree. If this is not applicable, try something more
			 * generic.
			 */
			IASTFragment fragment = AssociativeInfixExpressionFragment
					.createFragmentForFullSubtree(node);
			if (fragment == null)
				return visit((Expression) node);
			if (oldFragment instanceof AssociativeInfixExpressionFragment
					&& fragment instanceof AssociativeInfixExpressionFragment) {
				AssociativeInfixExpressionFragment f1 = (AssociativeInfixExpressionFragment) oldFragment;
				AssociativeInfixExpressionFragment f2 = (AssociativeInfixExpressionFragment) fragment;
				if (f1.getOperands().size() != f2.getOperands().size()) {
					return visit((Expression) node);
				}
			}

			setFragment(fragment);
			return false;
		}

		public boolean visit(Expression node) {
			setFragment(new SimpleExpressionFragment(node));
			return false;
		}

		public boolean visit(ASTNode node) {
			setFragment(new SimpleFragment(node));
			return false;
		}
	}

	private static abstract class FragmentFactory extends HierarchicalVisitor {
		private IASTFragment fFragment;
		protected IASTFragment oldFragment;

		protected IASTFragment createFragment(ASTNode node,
				IASTFragment astFragment) {
			oldFragment = astFragment;
			fFragment = null;
			node.accept(this);
			return fFragment;
		}

		protected final IASTFragment getFragment() {
			return fFragment;
		}

		protected final void setFragment(IASTFragment fragment) {
			Assert.isTrue(!isFragmentSet());
			fFragment = fragment;
		}

		protected final void clearFragment() {
			fFragment = null;
		}

		protected final boolean isFragmentSet() {
			return getFragment() != null;
		}
	}

	public static IASTFragment createFragmentForFullSubtree(ASTNode node) {
		return createFragmentForFullSubtree(node, null);
	}

}
