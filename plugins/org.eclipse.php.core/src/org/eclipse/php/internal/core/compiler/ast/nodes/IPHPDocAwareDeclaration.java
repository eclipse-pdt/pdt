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

/**
 * This interface used by all AST declaration nodes that can
 * hold a PHPDoc description above the title. For example:
 * <pre>
 * /**
 *  * This PHPDoc section is related to the class declaration
 *  * /
 *  class A {
 *  }
 * </pre>
 */
public interface IPHPDocAwareDeclaration {

	/**
	 * Returns PHPDoc block relevant to this node
	 * @return PHPDoc block
	 */
	public PHPDocBlock getPHPDoc();
}
