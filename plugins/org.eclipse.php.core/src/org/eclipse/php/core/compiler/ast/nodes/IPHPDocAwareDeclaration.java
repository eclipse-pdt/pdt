/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

/**
 * This interface used by all AST declaration nodes that can hold a PHPDoc
 * description above the title. For example:
 * 
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
	 * 
	 * @return PHPDoc block
	 */
	public PHPDocBlock getPHPDoc();
}
