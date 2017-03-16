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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;

/**
 * Represents a fragment (@see IASTFragment) for which the node to which the
 * fragment maps is an Expression.
 */
public interface IExpressionFragment extends IASTFragment {

	/**
	 * Every IASTFragment maps to an ASTNode, although this mapping may not be
	 * straightforward, and more than one fragment may map to the same node. An
	 * IExpressionFragment maps, specifically, to an Expression.
	 * 
	 * @return Expression The node to which this fragment maps.
	 */
	public Expression getAssociatedExpression();

	/**
	 * Creates a copy of this IExpressionFragment.
	 * 
	 * @param rewrite
	 *            an ASTRewrite
	 * @param removeSurroundingParenthesis
	 *            if set to <code>true</true>, a surrounding
	 *            ParenthesizedExpression will not be part of the copied node.
	 * @return a copy of this IExpressionFragment, ready for use in the given
	 *         rewrite
	 * @throws CoreException
	 */
	public Expression createCopyTarget(ASTRewrite rewrite, boolean removeSurroundingParenthesis) throws CoreException;
}
