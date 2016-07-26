/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.debug.core.model.IDebugTarget;
import org.w3c.dom.Node;

/**
 * DBGp variable for handling results of resolving expression with the use of
 * XdDebug 'eval' call.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpEvalVariable extends DBGpVariable {

	/**
	 * Creates new variable with some expression as an entry point.
	 * 
	 * @param target
	 * @param expression
	 * @param descriptor
	 * @param facets
	 */
	public DBGpEvalVariable(IDebugTarget target, String expression, Node descriptor, Facet... facets) {
		super(target, descriptor, -1, facets);
		fName = expression;
		fFullName = expression;
	}

	/**
	 * Creates new variable that is a child element of some eval call.
	 * 
	 * @param target
	 * @param descriptor
	 * @param facets
	 */
	public DBGpEvalVariable(IDebugTarget target, Node descriptor, Facet... facets) {
		super(target, descriptor, -1, facets);
	}

	@Override
	protected Kind getKind() {
		return Kind.EVAL;
	}

	@Override
	protected Node getNode(int page) {
		DBGpTarget target = (DBGpTarget) getDebugTarget();
		return target.eval(fFullName, page);
	}

}
