/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies - adapt for PHP refactoring
 *******************************************************************************/
package org.eclipse.php.refactoring.core.code.flow;

import org.eclipse.php.core.ast.nodes.Expression;
import org.eclipse.php.core.ast.nodes.ReturnStatement;

class ReturnFlowInfo extends FlowInfo {

	public ReturnFlowInfo(ReturnStatement node) {
		super(getReturnFlag(node));
	}

	public void merge(FlowInfo info, FlowContext context) {
		if (info == null) {
			return;
		}

		assignAccessMode(info);
	}

	private static int getReturnFlag(ReturnStatement node) {
		Expression expression = node.getExpression();

		// TODO need to check the type of the return value -
		// .resolveTypeBinding() == node.getAST().resolveWellKnownType("void")
		if (expression == null || expression.isNullExpression()) {
			return VOID_RETURN;
		}
		return VALUE_RETURN;
	}
}
