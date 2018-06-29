/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.dom.fragments;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.visitor.ApplyAll;

class ASTMatchingFragmentFinder extends ApplyAll {

	private static boolean isProgramScope = false;

	public static IASTFragment[] findMatchingFragments(ASTNode scope, ASTFragment toMatch) {
		isProgramScope = scope.getType() == ASTNode.PROGRAM ? true : false;

		return new ASTMatchingFragmentFinder(toMatch).findMatches(scope);
	}

	private ASTFragment fFragmentToMatch;
	private Set<IASTFragment> fMatches = new HashSet<>();

	private ASTMatchingFragmentFinder(ASTFragment toMatch) {
		fFragmentToMatch = toMatch;
	}

	private IASTFragment[] findMatches(ASTNode scope) {
		fMatches.clear();
		scope.accept(this);
		return getMatches();
	}

	private IASTFragment[] getMatches() {
		return fMatches.toArray(new IASTFragment[fMatches.size()]);
	}

	@Override
	protected boolean apply(ASTNode node) {
		// if the change scope is the program scope, we don't want it to affect
		// the function scope
		if (node.getType() == ASTNode.FUNCTION_DECLARATION && isProgramScope) {
			return false;
		}

		IASTFragment[] localMatches = fFragmentToMatch.getMatchingFragmentsWithNode(node);
		for (int i = 0; i < localMatches.length; i++) {
			fMatches.add(localMatches[i]);
		}
		return true;
	}

}
