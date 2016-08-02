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

import org.eclipse.php.internal.core.util.VersionUtils;
import org.eclipse.php.internal.debug.core.model.VariablesUtil;

/**
 * Expression for fetching top-level context variables.
 * 
 * @author Bartlomiej Laczkowski
 */
public class CurrentContextExpression extends DefaultExpression {

	private final static String GET_EXTENDED_CONTEXT = "eval('if (isset($this)) {$this;}; " //$NON-NLS-1$
			+ "if (array_key_exists(\\'class\\', debug_backtrace(0, 1)[0])) " //$NON-NLS-1$
			+ "{ return array_merge(get_defined_vars(), array(get_called_class())); } " //$NON-NLS-1$
			+ "else " //$NON-NLS-1$
			+ "{ return array_merge(get_defined_vars(), array(false)); }')"; //$NON-NLS-1$

	private final static String GET_CONTEXT = "eval('if (isset($this)) {$this;}; " // $NON-NLS-1$
			+ "return array_merge(get_defined_vars(), array(false));')"; // $NON-NLS-1$

	/**
	 * Creates new current context expression.
	 */
	private CurrentContextExpression(String contextExpression) {
		super(contextExpression);
	}

	@Override
	public Expression createChildExpression(String endName, String endRepresentation, Facet... facets) {
		endName = '$' + endName;
		if (VariablesUtil.isThis(endName))
			return new DefaultExpression(endName, KIND_THIS);
		else if (VariablesUtil.isSuperGlobal(endName))
			return new DefaultExpression(endName, KIND_SUPER_GLOBAL);
		else
			return new DefaultExpression(endName, KIND_LOCAL);
	}

	/**
	 * Builds current context expression with the use of PHP version info taken
	 * from provided debugger.
	 * 
	 * @param debugger
	 * @return context expression
	 */
	public static Expression build(Debugger debugger) {
		if (supportsStaticContext(debugger)) {
			return new CurrentContextExpression(GET_EXTENDED_CONTEXT);
		}
		return new CurrentContextExpression(GET_CONTEXT);
	}

	/**
	 * Checks if given debugger supports static (extended) context. Static
	 * context is supported for PHP version >= 5.4.x.
	 * 
	 * @param debugger
	 * @return <code>true</code> if given debugger supports static context,
	 *         <code>false</code> otherwise
	 */
	public static boolean supportsStaticContext(Debugger debugger) {
		if (VersionUtils.greater(debugger.getPHPVersion(), "5.3", 2)) { // $NON-NLS-1$
			return true;
		}
		return false;
	}

}
