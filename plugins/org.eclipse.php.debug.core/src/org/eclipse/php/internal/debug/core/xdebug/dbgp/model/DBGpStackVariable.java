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
 * DBGp variable for handling stack variables.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpStackVariable extends DBGpVariable {

	/**
	 * Creates new DBGp stack variable.
	 * 
	 * @param target
	 * @param descriptor
	 * @param stackLevel
	 * @param facets
	 */
	public DBGpStackVariable(IDebugTarget target, Node descriptor, int stackLevel, Facet... facets) {
		super(target, descriptor, stackLevel, facets);
	}

	@Override
	protected Kind getKind() {
		return Kind.STACK;
	}

	@Override
	protected Node getNode(int page) {
		DBGpTarget target = (DBGpTarget) getDebugTarget();
		return target.getProperty(getFullName(), String.valueOf(getStackLevel()), page);
	}

}
