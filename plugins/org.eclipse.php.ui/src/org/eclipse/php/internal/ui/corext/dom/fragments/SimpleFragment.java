/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.dom.fragments;

import org.eclipse.core.runtime.Assert;
import org.eclipse.php.internal.core.ast.match.PHPASTMatcher;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;

class SimpleFragment extends ASTFragment {
	private final ASTNode fNode;

	SimpleFragment(ASTNode node) {
		Assert.isNotNull(node);
		fNode= node;
	}

	public IASTFragment[] getMatchingFragmentsWithNode(ASTNode node) {
		if (! PHPASTMatcher.doNodesMatch(getAssociatedNode(), node))
			return new IASTFragment[0];

		IASTFragment match= ASTFragmentFactory.createFragmentForFullSubtree(node);
		Assert.isTrue(match.matches(this) || this.matches(match));
		return new IASTFragment[] { match };
	}

	public boolean matches(IASTFragment other) {
		return other.getClass().equals(getClass()) && PHPASTMatcher.doNodesMatch(other.getAssociatedNode(), getAssociatedNode());
	}

	public IASTFragment[] getSubFragmentsMatching(IASTFragment toMatch) {
		return ASTMatchingFragmentFinder.findMatchingFragments(getAssociatedNode(), (ASTFragment) toMatch);

	}

	public int getStartPosition() {
		return fNode.getStart();
	}

	public int getLength() {
		return fNode.getLength();
	}

	public ASTNode getAssociatedNode() {
		return fNode;
	}
	
//	public void replace(ASTRewrite rewrite, ASTNode replacement, TextEditGroup textEditGroup) {
//		rewrite.replace(fNode, replacement, textEditGroup);
//	}

	public int hashCode() {
		return fNode.hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleFragment other= (SimpleFragment) obj;
		return fNode.equals(other.fNode);
	}
	
}
