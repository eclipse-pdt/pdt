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

import org.eclipse.php.internal.debug.core.model.VariablesUtil;

/**
 * Expression for fetching top-level context variables.
 * 
 * @author Bartlomiej Laczkowski
 */
public class CurrentContextExpression extends DefaultExpression {

	private final static String GET_CURRENT_CONTEXT = "eval('if (isset($this)) {$this;}; return array_merge(get_defined_vars(), array(constant(\\'__CLASS__\\')));')"; //$NON-NLS-1$

	/**
	 * Creates new current context expression.
	 */
	public CurrentContextExpression() {
		super(GET_CURRENT_CONTEXT);
	}

	@Override
	public Expression createChildExpression(String endName,
			String endRepresentation, Facet... facets) {
		endName = '$' + endName;
		if (VariablesUtil.isThis(endName))
			return new DefaultExpression(endName, KIND_THIS);
		else if (VariablesUtil.isSuperGlobal(endName))
			return new DefaultExpression(endName, KIND_SUPER_GLOBAL);
		else
			return new DefaultExpression(endName, KIND_LOCAL);
	}

}
