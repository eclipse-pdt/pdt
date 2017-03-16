/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies - adapt for PHP refactoring
 *******************************************************************************/
package org.eclipse.php.refactoring.core.code.flow;

import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.IVariableBinding;
import org.eclipse.php.core.ast.nodes.ReturnStatement;
import org.eclipse.php.core.ast.nodes.Variable;

public class InOutFlowAnalyzer extends FlowAnalyzer {

	public InOutFlowAnalyzer(FlowContext context) {
		super(context);
	}

	public FlowInfo perform(ASTNode[] selectedNodes) {
		FlowContext context = getFlowContext();
		GenericSequentialFlowInfo result = createSequential();
		for (int i = 0; i < selectedNodes.length; i++) {
			ASTNode node = selectedNodes[i];
			node.accept(this);
			result.merge(getFlowInfo(node), context);
		}
		return result;
	}

	protected boolean traverseNode(ASTNode node) {
		// we are only traversing the selected nodes.
		return true;
	}

	protected boolean createReturnFlowInfo(ReturnStatement node) {
		// we are only traversing selected nodes.
		return true;
	}

	// TODO - verify that we don't need the remarked methods
	// public void endVisit(Block node) {
	// super.endVisit(node);
	// clearAccessMode(accessFlowInfo(node), node.statements());
	// }
	//
	// public void endVisit(CatchClause node) {
	// super.endVisit(node);
	// clearAccessMode(accessFlowInfo(node), node.getVariable());
	// }
	//
	//
	// public void endVisit(ForStatement node) {
	// super.endVisit(node);
	// clearAccessMode(accessFlowInfo(node), node.initializers());
	// }
	//
	// public void endVisit(ForEachStatement node) {
	// super.endVisit(node);
	// clearAccessMode(accessFlowInfo(node), node.getExpression());
	// }
	//
	// public void endVisit(FunctionDeclaration node) {
	// super.endVisit(node);
	// FlowInfo info= accessFlowInfo(node);
	// for (Iterator iter= node.formalParameters().iterator(); iter.hasNext();)
	// {
	// clearAccessMode(info, (FormalParameter)iter.next());
	// }
	// }
	//
	// public void endVisit(MethodDeclaration node) {
	// super.endVisit(node);
	// FlowInfo info= accessFlowInfo(node);
	// for (Iterator iter= node.getFunction().formalParameters().iterator();
	// iter.hasNext();) {
	// clearAccessMode(info, (FormalParameter)iter.next());
	// }
	// }

	public void endVisit(Variable variable) {
		super.endVisit(variable);
		// FlowInfo info= accessFlowInfo(variable);
		// clearAccessMode(info, variable);
	}

	private void clearAccessMode(FlowInfo info, Variable variable) {
		IVariableBinding binding = variable.resolveVariableBinding();
		if (binding != null && !binding.isField())
			info.clearAccessMode(binding, fFlowContext);
	}

	// private void clearAccessMode(FlowInfo info, List nodes) {
	// if (nodes== null || nodes.isEmpty() || info == null)
	// return;
	// for (Iterator iter= nodes.iterator(); iter.hasNext(); ) {
	// Object node= iter.next();
	// Iterator fragments= null;
	// if (node instanceof Variable) {
	// clearAccessMode(info, (Variable) node);
	// }
	// }
	// }
}
