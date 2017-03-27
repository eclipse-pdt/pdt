/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

import static org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType.PHP_VIRTUAL_CLASS;
import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.KIND_CONSTANT;
import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.KIND_OBJECT_MEMBER;
import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.MOD_STATIC;
import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.VIRTUAL_CLASS;

import java.text.MessageFormat;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType;
import org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet;
import org.eclipse.php.internal.debug.core.model.VariablesUtil;

/**
 * Utility class for Zend Debugger expressions.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ExpressionsUtil {

	private static final String CHECK_CLASS_METHOD_EXISTS = "eval(''if (class_exists(\\''ReflectionClass\\'')) return (new ReflectionClass({0}))->hasMethod(\\''{1}\\''); else return false;'');"; //$NON-NLS-1$

	private static final class FetchStaticsExpression extends DefaultExpression {

		static final String FETCH_STATIC_MEMBERS = "eval(''if (class_exists(\\''ReflectionClass\\'') && class_exists(\\''{0}\\'')) return (new ReflectionClass(\\''{0}\\''))->getStaticProperties(); else return array();'');"; //$NON-NLS-1$

		public FetchStaticsExpression(String className) {
			super(MessageFormat.format(FETCH_STATIC_MEMBERS, className), className);
		}

		@Override
		public Expression createChildExpression(String endName, String endRepresentation, Facet... facets) {
			return new DefaultExpression(this, endName, "::$" + endName, KIND_OBJECT_MEMBER, MOD_STATIC); //$NON-NLS-1$
		}

	}

	private static final class FetchClassConstantsExpression extends DefaultExpression {

		static final String FETCH_CONSTANTS = "eval(''if (class_exists(\\''ReflectionClass\\'') && class_exists(\\''{0}\\'')) return (new ReflectionClass(\\''{0}\\''))->getConstants(); else return array();'');"; //$NON-NLS-1$

		public FetchClassConstantsExpression(String className) {
			super(MessageFormat.format(FETCH_CONSTANTS, className), className);
		}

		@Override
		public Expression createChildExpression(String endName, String endRepresentation, Facet... facets) {
			return new DefaultExpression(this, endName, "::" + endName, KIND_CONSTANT); //$NON-NLS-1$
		}

	}

	private static final class FetchConstantExpression extends DefaultExpression {

		static final String FETCH_CONSTANT = "eval(''return constant(\\''{0}\\'');'');"; //$NON-NLS-1$

		public FetchConstantExpression(String constantName) {
			super(MessageFormat.format(FETCH_CONSTANT, constantName), constantName);
		}

	}

	private static final class FetchStaticsVisibilityExpression extends DefaultExpression {

		static final String FETCH_STATICS_MODIFIERS = "eval(''if (class_exists(\\''ReflectionProperty\\'')) return array({0}); else return array();'');"; //$NON-NLS-1$
		static final String TUPLE_ELEMENT = "(new ReflectionProperty(\\''{0}\\'', \\''{1}\\''))->getModifiers()"; //$NON-NLS-1$

		public FetchStaticsVisibilityExpression(String tuple) {
			super(MessageFormat.format(FETCH_STATICS_MODIFIERS, tuple));
		}

	}

	private static final int PROP_MOD_PUBLIC = 1 << 8;
	private static final int PROP_MOD_PROTECTED = 1 << 9;
	private static final int PROP_MOD_PRIVATE = 1 << 10;

	private static final Map<Expression, String> staticMemberClassNames = new WeakHashMap<Expression, String>();

	private static Map<ExpressionsManager, ExpressionsUtil> fInstance = new WeakHashMap<>();

	private ExpressionsManager fExpressionsManager;

	/**
	 * 
	 */
	private ExpressionsUtil(ExpressionsManager expressionsManager) {
		fExpressionsManager = expressionsManager;
	}

	public static ExpressionsUtil getInstance(ExpressionsManager expressionsManager) {
		if (fInstance.get(expressionsManager) == null) {
			fInstance.put(expressionsManager, new ExpressionsUtil(expressionsManager));
		}
		return fInstance.get(expressionsManager);
	}

	/**
	 * Returns expressions of static members for given class.
	 * 
	 * @param className
	 * @param expressionsManager
	 * @return expressions of static members for given class
	 */
	public Expression[] fetchStaticMembers(String className) {
		Expression staticMembers = new FetchStaticsExpression(className);
		fExpressionsManager.update(staticMembers, 1);
		Expression[] members = staticMembers.getValue().getChildren();
		// Possibly interrupted, crash, etc.
		if (members == null)
			return new Expression[0];
		int[] mods = fetchStaticMembersVisibility(className, members);
		// Possibly interrupted, crash, etc.
		if (mods == null)
			return new Expression[0];
		if (members.length > 0) {
			for (int i = 0; i < members.length; i++) {
				Expression member = members[i];
				staticMemberClassNames.put(member, className);
				if ((mods[i] & PROP_MOD_PRIVATE) > 0)
					member.addFacets(Facet.MOD_PRIVATE);
				else if ((mods[i] & PROP_MOD_PROTECTED) > 0)
					member.addFacets(Facet.MOD_PROTECTED);
				else if ((mods[i] & PROP_MOD_PUBLIC) > 0)
					member.addFacets(Facet.MOD_PUBLIC);
			}
			return members;
		}
		return new Expression[0];
	}

	public Expression[] fetchClassConstants(String className) {
		Expression constants = new FetchClassConstantsExpression(className);
		fExpressionsManager.update(constants, 1);
		Expression[] members = constants.getValue().getChildren();

		if (members == null)
			return new Expression[0];
		for (Expression e : members) {
			e.addFacets(Facet.MOD_PUBLIC);
		}
		return members;
	}

	public Expression fetchConstant(String constantName) {
		Expression constant = new FetchConstantExpression(constantName);
		fExpressionsManager.update(constant, 1);
		Expression newExpression = new DefaultExpression(constantName, KIND_CONSTANT, Facet.MOD_PUBLIC);
		newExpression.setValue(constant.getValue());
		return newExpression;
	}

	private boolean invokeMethod(String object, String method, StringBuffer result) {
		Expression e = new DefaultExpression(MessageFormat.format(CHECK_CLASS_METHOD_EXISTS, object, method));
		fExpressionsManager.update(e, 1);
		if (e.getValue().getValue() != null && e.getValue().getValue().equals("1")) {
			String expression = object + "->" + method + "()"; //$NON-NLS-1$ //$NON-NLS-2$
			e = new DefaultExpression(expression);
			fExpressionsManager.getExpressionValue(e, 1);
			fExpressionsManager.update(e, 1);
			result.append(e.getValue().getValue());
			return true;
		}
		return false;
	}

	/**
	 * Return class name for corresponding static member expression.
	 * 
	 * @param staticMember
	 * @return class name for corresponding static member expression
	 */
	public static String fetchStaticMemberClassName(Expression staticMember) {
		return staticMemberClassNames.get(staticMember);
	}

	/**
	 * Returns "virtual class" expression with child static members.
	 * 
	 * @param className
	 * @param expressionsManager
	 * @return "virtual class" expression with child static members
	 */
	public Expression fetchStaticContext(String className) {
		Expression[] staticMembers = fetchStaticMembers(className);
		if (staticMembers.length == 0)
			return null;
		Expression classStaticContext = new DefaultExpression(VariablesUtil.CLASS_INDICATOR, VIRTUAL_CLASS);
		ExpressionValue classStaticContextValue = new ExpressionValue(PHP_VIRTUAL_CLASS, className, "Class of: " //$NON-NLS-1$
				+ className, staticMembers, staticMembers.length);
		classStaticContext.setValue(classStaticContextValue);
		return classStaticContext;
	}

	private int[] fetchStaticMembersVisibility(String className, Expression[] members) {
		StringBuilder tuple = new StringBuilder();
		for (int i = 0; i < members.length; i++) {
			tuple.append(MessageFormat.format(FetchStaticsVisibilityExpression.TUPLE_ELEMENT, className,
					members[i].getLastName()));
			if (i < members.length - 1)
				tuple.append(',');
		}
		Expression fetchModifiersExpression = new FetchStaticsVisibilityExpression(tuple.toString());
		fExpressionsManager.update(fetchModifiersExpression, 1);
		Expression[] computed = fetchModifiersExpression.getValue().getOriChildren();
		if (computed == null)
			return null;
		int[] mods = new int[computed.length];
		for (int i = 0; i < computed.length; i++)
			mods[i] = Integer.valueOf((String) computed[i].getValue().getValue());
		return mods;
	}

	public Expression fetchClassContext(String className) {
		Expression classContext = new DefaultExpression(VariablesUtil.CLASS_INDICATOR, VIRTUAL_CLASS);
		ExpressionValue classStaticContextValue = new ExpressionValue(PHP_VIRTUAL_CLASS, className, className, null);
		classContext.setValue(classStaticContextValue);
		return classContext;
	}

	public String getValueDetail(Expression expression) {
		ExpressionValue value = expression.getValue();
		if (value.getDataType() == DataType.PHP_OBJECT) {
			StringBuffer result = new StringBuffer();
			boolean exists = invokeMethod(expression.getFullName(), "__toString", result); //$NON-NLS-1$
			if (exists)
				return result.toString();
		} else if (value.getDataType() == DataType.PHP_ARRAY) {
			fExpressionsManager.update(expression, 1);
			StringBuffer result = new StringBuffer("["); //$NON-NLS-1$
			for (int i = 0; i < expression.getValue().getChildren().length; i++) {
				Expression child = expression.getValue().getChildren()[i];
				if (i > 0) {
					result.append(","); //$NON-NLS-1$
					result.append(" "); //$NON-NLS-1$
				}
				result.append(child.getLastName());
				result.append(" => "); //$NON-NLS-1$
				result.append(getValueDetail(child));
			}
			result.append("]"); //$NON-NLS-1$
			return result.toString();
		} else if (value.getDataType() == DataType.PHP_STRING) {
			String result = expression.getValue().getValueAsString();
			int length = result.length();
			return result.substring(1, length - 1);
		}
		return expression.getValue().getValueAsString();
	}

	/**
	 * Returns the variable value.
	 * 
	 * @param variable
	 *            The variable name
	 * @return
	 */
	public Expression buildExpression(String variable) {
		Expression expression = fExpressionsManager.buildExpression(variable);
		fExpressionsManager.getExpressionValue(expression, 1);
		fExpressionsManager.update(expression, 1);
		return expression;
	}

}
