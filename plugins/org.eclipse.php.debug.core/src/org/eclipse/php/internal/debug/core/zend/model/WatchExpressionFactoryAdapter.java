package org.eclipse.php.internal.debug.core.zend.model;

import java.util.Stack;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.actions.IWatchExpressionFactoryAdapter;

public class WatchExpressionFactoryAdapter implements IWatchExpressionFactoryAdapter {

	private static final String TYPE_ARRAY = "ARRAY";
	private static final String TYPE_OBJECT = "OBJECT";

	public String createWatchExpression(IVariable variable) throws CoreException {
		if (variable instanceof PHPVariable) {
			PHPVariable phpVar = (PHPVariable) variable;
			PHPDebugTarget debugTarget = (PHPDebugTarget) phpVar.getDebugTarget();
			IVariable[] variables = debugTarget.getVariables();
			Stack<IVariable> stack = new Stack<IVariable>();
			if (findVariable(phpVar, variables, stack)) {
				return createExpression(stack);
			}
		}
		return variable.getName();
	}

	private boolean findVariable( PHPVariable phpVar, IVariable[] variables,
			Stack<IVariable> stack) throws DebugException {
		for (IVariable variable : variables) {
			if (variable.equals(phpVar)) {
				stack.push(variable);
				return true;
			}
			IValue value = variable.getValue();
			if (value.hasVariables()) {
				IVariable[] vars = value.getVariables();
				if (findVariable(phpVar, vars, stack)) {
					stack.push(variable);
					return true;
				}
			}
		}
		return false;
	}

	private String createExpression(Stack<IVariable> stack) throws DebugException {
		StringBuffer expr = new StringBuffer();
		String parentType = "";
		while (!stack.isEmpty()) {
			IVariable variable = stack.pop();
			String name = variable.getName();
			if (parentType.equals(TYPE_ARRAY)) {
				expr.append("['").append(name).append("']");
			} else if (parentType.equals(TYPE_OBJECT)) {
				expr.append("->").append(name);
			} else {
				expr.append(name);
			}
			parentType = variable.getReferenceTypeName();
		}
		return expr.toString();
	}
}