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

import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.ParenthesisExpression;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;

class SimpleExpressionFragment extends SimpleFragment implements
		IExpressionFragment {
	SimpleExpressionFragment(Expression node) {
		super(node);
	}

	public Expression getAssociatedExpression() {
		return (Expression) getAssociatedNode();
	}

	public Expression createCopyTarget(ASTRewrite rewrite,
			boolean removeSurroundingParenthesis) {
		Expression node = getAssociatedExpression();
		if (removeSurroundingParenthesis
				&& node instanceof ParenthesisExpression) {
			node = ((ParenthesisExpression) node).getExpression();
		}
		return (Expression) rewrite.createCopyTarget(node);
	}
}
