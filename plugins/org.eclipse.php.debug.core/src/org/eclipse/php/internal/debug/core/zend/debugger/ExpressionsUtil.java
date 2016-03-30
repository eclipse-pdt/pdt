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

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.WeakHashMap;

import static org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType.*;
import org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet;
import org.eclipse.php.internal.debug.core.model.VariablesUtil;

/**
 * Utility class for Zend Debugger expressions.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ExpressionsUtil {

	private static final class FetchStaticsExpression extends DefaultExpression {

		static final String FETCH_STATIC_MEMBERS = "eval(''if (class_exists(\"ReflectionClass\") && class_exists(\"{0}\")) return (new ReflectionClass(\"{0}\"))->getStaticProperties(); else return array();'');"; //$NON-NLS-1$

		public FetchStaticsExpression(String className) {
			super(MessageFormat.format(FETCH_STATIC_MEMBERS, className));
		}

		@Override
		public Expression createChildExpression(String endName, String endRepresentation, Facet... facets) {
			return new DefaultExpression(this, endName, endRepresentation, KIND_OBJECT_MEMBER, MOD_STATIC);
		}

	}

	private static final class FetchStaticsVisibilityExpression extends DefaultExpression {

		static final String FETCH_STATICS_MODIFIERS = "eval(''if (class_exists(\"ReflectionProperty\")) return array({0}); else return array();'');"; //$NON-NLS-1$
		static final String TUPLE_ELEMENT = "(new ReflectionProperty(\"{0}\", \"{1}\"))->getModifiers()"; //$NON-NLS-1$

		public FetchStaticsVisibilityExpression(String tuple) {
			super(MessageFormat.format(FETCH_STATICS_MODIFIERS, tuple));
		}

	}

	private static final int PROP_MOD_PUBLIC = 1 << 8;
	private static final int PROP_MOD_PROTECTED = 1 << 9;
	private static final int PROP_MOD_PRIVATE = 1 << 10;

	private static final Map<Expression, String> staticMemberClassNames = new WeakHashMap<Expression, String>();

	/**
	 * 
	 */
	private ExpressionsUtil() {
		// Utility class - no public constructor
	}

	/**
	 * Returns expressions of static members for given class.
	 * 
	 * @param className
	 * @param expressionsManager
	 * @return expressions of static members for given class
	 */
	public static Expression[] fetchStaticMembers(String className, ExpressionsManager expressionsManager) {
		Expression staticMembers = new FetchStaticsExpression(className);
		expressionsManager.update(staticMembers, 1);
		Expression[] members = staticMembers.getValue().getChildren();
		// Possibly interrupted, crash, etc.
		if (members == null)
			return new Expression[0];
		int[] mods = fetchStaticMembersVisibility(className, members, expressionsManager);
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
	public static Expression fetchStaticContext(String className, ExpressionsManager expressionsManager) {
		Expression[] staticMembers = fetchStaticMembers(className, expressionsManager);
		if (staticMembers.length == 0)
			return null;
		Expression classStaticContext = new DefaultExpression(VariablesUtil.CLASS_INDICATOR, VIRTUAL_CLASS);
		ExpressionValue classStaticContextValue = new ExpressionValue(PHP_VIRTUAL_CLASS, className,
				"Class of: " //$NON-NLS-1$
						+ className,
				staticMembers, staticMembers.length);
		classStaticContext.setValue(classStaticContextValue);
		return classStaticContext;
	}

	private static int[] fetchStaticMembersVisibility(String className, Expression[] members,
			ExpressionsManager expressionsManager) {
		StringBuffer tuple = new StringBuffer();
		for (int i = 0; i < members.length; i++) {
			tuple.append(MessageFormat.format(FetchStaticsVisibilityExpression.TUPLE_ELEMENT, className,
					members[i].getLastName()));
			if (i < members.length - 1)
				tuple.append(',');
		}
		Expression fetchModifiersExpression = new FetchStaticsVisibilityExpression(tuple.toString());
		expressionsManager.update(fetchModifiersExpression, 1);
		Expression[] computed = fetchModifiersExpression.getValue().getOriChildren();
		if (computed == null)
			return null;
		int[] mods = new int[computed.length];
		for (int i = 0; i < computed.length; i++)
			mods[i] = Integer.valueOf((String) computed[i].getValue().getValue());
		return mods;
	}

}
