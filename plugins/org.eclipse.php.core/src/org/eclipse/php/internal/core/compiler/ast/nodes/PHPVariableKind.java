/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.references.VariableKind;

/**
 * PHP variable kinds
 * @author michael
 */
public interface PHPVariableKind extends VariableKind {

	public class PHPImplementation extends Implementation implements PHPVariableKind {

		public PHPImplementation(VariableKind kind) {
			super(kind.getId());
		}
	}

	/**
	 * Variable, like: <code>$name</code> anywhere in function scope
	 */
	public static final PHPVariableKind LOCAL = new PHPImplementation(VariableKind.LOCAL);

	/**
	 * Variable, like: <code>$name</code> anywhere in global scope
	 */
	public static final PHPVariableKind GLOBAL = new PHPImplementation(VariableKind.GLOBAL);

	/**
	 * Instance variable, like: <code>$obj->var</code>
	 */
	public static final PHPVariableKind INSTANCE = new PHPImplementation(VariableKind.INSTANCE);

	/**
	 * Class variable, like: <code>DAO::$instance</code>
	 */
	public static final PHPVariableKind CLASS = new PHPImplementation(VariableKind.CLASS);

	/**
	 * Constant, like: <code>E_ALL</code>
	 */
	public static final PHPVariableKind CONSTANT = new PHPImplementation(VariableKind.GLOBAL);

}
