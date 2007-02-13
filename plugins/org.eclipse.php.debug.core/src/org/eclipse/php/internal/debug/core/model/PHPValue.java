/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import java.util.StringTokenizer;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.debugger.DefaultExpression;
import org.eclipse.php.internal.debug.core.debugger.Expression;
import org.eclipse.php.internal.debug.core.debugger.ExpressionValue;
import org.eclipse.php.internal.debug.core.debugger.ExpressionsManager;

/**
 * Value of a PHP variable.
 */
public class PHPValue extends PHPDebugElement implements IValue {

	private ExpressionValue fValue;
	private boolean fHasChildren = false;
	private IVariable[] fChildren = new IVariable[0];
	private Expression fVariable;
	private boolean fGlobal;

	private String[] types = { "NULL", "INT", "STRING", "BOOLEAN", "DOUBLE", "ARRAY", "OBJECT", "RESOURCE" };

	public PHPValue(PHPDebugTarget target, Expression var) {
		super(target);
		fValue = var.getValue();
		fVariable = var;
		fGlobal = false;
		processChildren(fValue);

	}

	public PHPValue(PHPDebugTarget target, Expression var, boolean global) {
		super(target);
		fValue = var.getValue();
		fVariable = var;
		fGlobal = global;
		processChildren(fValue);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return types[fValue.getType()];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getValueString()
	 */
	public String getValueString() throws DebugException {
		StringBuffer string = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(fValue.getValueAsString(), "\\", true);
		String token;
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (token.length() > 1) {
				string.append(token);
			} else {
				string.append(token);
				if (tokenizer.hasMoreTokens())
					tokenizer.nextToken();
			}
		}
		String rString = string.toString();
		return rString;
	}

	public String getValue() throws DebugException {
		return (String) (fValue.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#isAllocated()
	 */
	public boolean isAllocated() throws DebugException {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getVariables()
	 */
	public IVariable[] getVariables() throws DebugException {
		if (fChildren.length == 0) {
			PHPDebugTarget debugTarget = (PHPDebugTarget) getDebugTarget();
			ExpressionsManager expressionManager = debugTarget.getExpressionManager();
			Expression var = fVariable;
			if (fGlobal) {
				String exp = "$GLOBALS[\"" + fVariable.getFullName().substring(1) + "\"]";
				var = new DefaultExpression(exp);
			}
			expressionManager.update(var, 1);
			fValue = var.getValue();
			processChildren(fValue);
		}
		return fChildren;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		return fHasChildren;
	}

	public void updateValue(ExpressionValue value) {
		fValue = value;
		processChildren(fValue);
	}

	public boolean isPrimative() {
		return fValue.isPrimitive();
	}

	private void processChildren(ExpressionValue value) {
		Expression[] eChildren = value.getChildren();
		if (eChildren == null)
			return;
		if (eChildren.length == 0) {
			fHasChildren = true;
			fChildren = new IVariable[0];
			return;
		}

		fHasChildren = true;
		fChildren = new PHPVariable[eChildren.length];
		for (int i = 0; i < eChildren.length; i++) {
			fChildren[i] = new PHPVariable((PHPDebugTarget) getDebugTarget(), eChildren[i], fGlobal);
		}

	}

	public boolean equals(Object obj) {
		// we restrict the recursion (checking descendants equality) to 5 levels 
		// to avoid OutOfMemoryError caused endles loop when the following type of PHP code
		// is used
		//   class Parent { $child }
		//   class Child { $parent}
		// in which case the child->getChildVariables returns the parent as a variable) 
		// [seva + almaz]
		return equals(obj, 5);
	}

	protected boolean equals(Object obj, int recurseDepth) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof PHPValue)) {
			return false;
		}

		PHPValue otherValue = (PHPValue) obj;
		// System.out.println("Compare " + fVariable.getFullName() + " with " + otherValue.fVariable.getFullName());

		// the "other" values were NOT necessarily loaded yet (if that is the case then we have to load them - other.getVariables) 
		boolean otherValueNull = otherValue.fValue.getValue() == null;

		if (fVariable.getFullName().equals(otherValue.fVariable.getFullName()) && (otherValueNull || fValue.getValueAsString().equals(otherValue.fValue.getValueAsString()))) {
			// if i don't have childrens means that I am a scalar
			if (!fHasChildren) {
				return !otherValue.fHasChildren;
			}

			// i have children but i did not retrieve them yet
			if (fChildren.length == 0) {
				try {
					getVariables();
				} catch (DebugException e) {
				}
			}

			if (otherValue.fChildren.length == 0) {
				try {
					otherValue.getVariables();
				} catch (DebugException e) {
				}
			}

			if (fChildren.length != otherValue.fChildren.length) {
				return false;
			}

			if (recurseDepth <= 0) {
				return true;
			}

			try {
				for (int i = 0; i < fChildren.length; i++) {
					IVariable myChild = fChildren[i];
					IVariable otherChild = otherValue.fChildren[i];

					if (!((PHPValue) myChild.getValue()).equals(otherChild.getValue(), --recurseDepth)) {
						return false;
					}
				}
			} catch (DebugException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	public int hashCode() {
		//		return fVariable.getFullName().hashCode();
		return super.hashCode(); // fVariable.getFullName().hashCode() + fValue.getValueAsString().hashCode();
	}
}
