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

import org.eclipse.php.core.ast.nodes.Expression;
import org.eclipse.php.core.ast.nodes.ParenthesisExpression;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;

class SimpleExpressionFragment extends SimpleFragment implements IExpressionFragment {
	SimpleExpressionFragment(Expression node) {
		super(node);
	}

	@Override
	public Expression getAssociatedExpression() {
		return (Expression) getAssociatedNode();
	}

	@Override
	public Expression createCopyTarget(ASTRewrite rewrite, boolean removeSurroundingParenthesis) {
		Expression node = getAssociatedExpression();
		if (removeSurroundingParenthesis && node instanceof ParenthesisExpression) {
			node = ((ParenthesisExpression) node).getExpression();
		}
		return (Expression) rewrite.createCopyTarget(node);
	}
}
