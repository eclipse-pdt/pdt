/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.extract.function;

import java.util.*;

import org.eclipse.core.runtime.Assert;
import org.eclipse.php.core.ast.match.ASTMatcher;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;

/* package */class SnippetFinder extends AbstractVisitor {

	public static class Match {
		private List fNodes;
		private Map fLocalMappings;

		public Match() {
			fNodes = new ArrayList(10);
			fLocalMappings = new HashMap();
		}

		public void add(ASTNode node) {
			fNodes.add(node);
		}

		public boolean hasCorrectNesting(ASTNode node) {
			if (fNodes.size() == 0)
				return true;
			ASTNode parent = node.getParent();
			if (((ASTNode) fNodes.get(0)).getParent() != parent)
				return false;
			// Here we know that we have two elements. In this case the
			// parent must be a block or a switch statement. Otherwise a
			// snippet like "if (true) foo(); else foo();" would match
			// the pattern "foo(); foo();"
			int nodeType = parent.getType();
			return nodeType == ASTNode.BLOCK || nodeType == ASTNode.SWITCH_STATEMENT;
		}

		public ASTNode[] getNodes() {
			return (ASTNode[]) fNodes.toArray(new ASTNode[fNodes.size()]);
		}

		public void addLocal(IVariableBinding org, Identifier local) {
			fLocalMappings.put(org, local);
		}

		public Identifier getMappedName(IVariableBinding org) {
			return (Identifier) fLocalMappings.get(org);
		}

		public IVariableBinding getMappedBinding(IVariableBinding org) {
			// Identifier name = (Identifier) fLocalMappings.get(org);
			// TODO:???
			return null;
		}

		public boolean isEmpty() {
			return fNodes.isEmpty() && fLocalMappings.isEmpty();
		}

		/**
		 * Tests if the whole duplicate is the full body of a method. If so
		 * don't replace it since we would replace a method body with a new
		 * method body which doesn't make to much sense.
		 * 
		 * @return whether the duplicte is the whole method body
		 */
		public boolean isMethodBody() {
			ASTNode first = (ASTNode) fNodes.get(0);
			if (first.getParent() == null)
				return false;
			ASTNode candidate = first.getParent().getParent();
			if (candidate == null || candidate.getType() != ASTNode.METHOD_DECLARATION)
				return false;
			MethodDeclaration method = (MethodDeclaration) candidate;
			return method.getFunction().getBody().statements().size() == fNodes.size();
		}

		public MethodDeclaration getEnclosingMethod() {
			ASTNode first = (ASTNode) fNodes.get(0);
			return (MethodDeclaration) ASTNodes.getParent(first, ASTNode.METHOD_DECLARATION);
		}
	}

	private class Matcher extends ASTMatcher {
		public boolean match(Identifier candidate, Object s) {
			if (!(s instanceof Identifier))
				return false;

			Identifier snippet = (Identifier) s;

			if (!(candidate.getParent() instanceof Variable) || !(snippet.getParent() instanceof Variable)) {
				return false;
			}

			Variable cv = (Variable) candidate.getParent();
			Variable sv = (Variable) snippet.getParent();

			IVariableBinding cb = cv.resolveVariableBinding();
			IVariableBinding sb = sv.resolveVariableBinding();
			if (cb == null || sb == null)
				return false;

			if (!cb.isField() && !sb.isField()) {
				Identifier mapped = fMatch.getMappedName(sb);
				if (mapped != null) {
					Variable parent = (Variable) mapped.getParent();
					IVariableBinding mappedBinding = parent.resolveVariableBinding();
					if (!cb.equals(mappedBinding))
						return false;
				}
				fMatch.addLocal(sb, candidate);
				return true;
			}
			return cb.equals(sb);
		}
	}

	private List fResult = new ArrayList(2);
	private Match fMatch;
	private ASTNode[] fSnippet;
	private int fIndex;
	private Matcher fMatcher;
	private int fTypes;

	private SnippetFinder(ASTNode[] snippet) {
		super();
		fSnippet = snippet;
		fMatcher = new Matcher();
		reset();
	}

	public static Match[] perform(ASTNode start, ASTNode[] snippet) {
		Assert.isTrue(
				start instanceof ClassDeclaration || start instanceof FunctionDeclaration || start instanceof Program);
		SnippetFinder finder = new SnippetFinder(snippet);
		start.accept(finder);
		for (Iterator iter = finder.fResult.iterator(); iter.hasNext();) {
			Match match = (Match) iter.next();
			ASTNode[] nodes = match.getNodes();
			// doesn't match if the candidate is the left hand side of an
			// assignment and the snippet consists of a single node.
			// Otherwise y= i; i= z; results in y= e(); e()= z;
			if (nodes.length == 1 && isLeftHandSideOfAssignment(nodes[0])) {
				iter.remove();
			}
		}
		return (Match[]) finder.fResult.toArray(new Match[finder.fResult.size()]);
	}

	private static boolean isLeftHandSideOfAssignment(ASTNode node) {
		ASTNode parent = node.getParent();
		return parent != null && parent.getType() == ASTNode.ASSIGNMENT
				&& ((Assignment) parent).getLeftHandSide() == node;
	}

	// public boolean visit(ClassDeclaration node) {
	// if (++fTypes > 1)
	// return false;
	// return visitNode(node);
	// }

	public boolean visit(ExpressionStatement node) {
		return visitNode(node);
	}

	// public void endVisit(ClassDeclaration node) {
	// --fTypes;
	// super.endVisit(node);
	// }

	public boolean visit(FunctionDeclaration node) {
		if (++fTypes > 1)
			return false;
		return super.visit(node);
	}

	public void endVisit(FunctionDeclaration node) {
		--fTypes;
		super.endVisit(node);
	}

	protected boolean visitNode(ASTNode node) {
		if (matches(node)) {
			return false;
		} else if (!isResetted()) {
			reset();
			if (matches(node))
				return false;
		}
		return true;
	}

	private boolean matches(ASTNode node) {
		if (isSnippetNode(node))
			return false;
		if (node.subtreeMatch(fMatcher, fSnippet[fIndex]) && fMatch.hasCorrectNesting(node)) {
			fMatch.add(node);
			fIndex++;
			if (fIndex == fSnippet.length) {
				fResult.add(fMatch);
				reset();
			}
			return true;
		}
		return false;
	}

	private boolean isResetted() {
		return fIndex == 0 && fMatch.isEmpty();
	}

	private void reset() {
		fIndex = 0;
		fMatch = new Match();
	}

	private boolean isSnippetNode(ASTNode node) {
		for (int i = 0; i < fSnippet.length; i++) {
			if (node == fSnippet[i])
				return true;
		}
		return false;
	}
}
