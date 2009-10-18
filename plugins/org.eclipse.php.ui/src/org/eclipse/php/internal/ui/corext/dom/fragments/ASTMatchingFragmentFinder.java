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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.visitor.ApplyAll;

class ASTMatchingFragmentFinder extends ApplyAll {

	private static boolean isProgramScope = false;

	public static IASTFragment[] findMatchingFragments(ASTNode scope,
			ASTFragment toMatch) {
		isProgramScope = scope.getType() == ASTNode.PROGRAM ? true : false;

		return new ASTMatchingFragmentFinder(toMatch).findMatches(scope);
	}

	private ASTFragment fFragmentToMatch;
	private Set<IASTFragment> fMatches = new HashSet<IASTFragment>();

	private ASTMatchingFragmentFinder(ASTFragment toMatch) {
		fFragmentToMatch = toMatch;
	}

	private IASTFragment[] findMatches(ASTNode scope) {
		fMatches.clear();
		scope.accept(this);
		return getMatches();
	}

	private IASTFragment[] getMatches() {
		return (IASTFragment[]) fMatches.toArray(new IASTFragment[fMatches
				.size()]);
	}

	protected boolean apply(ASTNode node) {
		// if the change scope is the program scope, we don't want it to affect
		// the function scope
		if (node.getType() == ASTNode.FUNCTION_DECLARATION && isProgramScope)
			return false;

		IASTFragment[] localMatches = fFragmentToMatch
				.getMatchingFragmentsWithNode(node);
		for (int i = 0; i < localMatches.length; i++) {
			fMatches.add(localMatches[i]);
		}
		return true;
	}

}
