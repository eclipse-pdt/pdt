/*******************************************************************************
 * Copyright (c) 2013 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;

/**
 * AST visitor for finding use statements by namespace.
 * 
 * @author Kaloyan Raev
 */
public class FindUseStatementByNamespaceASTVisitor extends
		AbstractUseStatementASTVisitor {

	/**
	 * The namespace to look for.
	 */
	private String namespace;

	/**
	 * The found {@link UsePart} node to return as result.
	 */
	private UsePart result;

	/**
	 * Constructor of the visitor.
	 * 
	 * @param namespace
	 *            the namespace to look for
	 * @param offset
	 *            the position in the AST tree after which the search stops
	 */
	public FindUseStatementByNamespaceASTVisitor(String namespace, int offset) {
		super(offset);
		this.namespace = namespace;
	}

	/**
	 * Returns the found {@link UsePart} node that corresponds to the specified
	 * namespace.
	 * 
	 * @return a <code>UsePart</code> node, or
	 *         <code>null<code> if there is not use statement for the specified namespace.
	 */
	public UsePart getResult() {
		return result;
	}

	/**
	 * Compares the namespace of the {@link UsePart} node being visited with the
	 * namespace that the visitor is looking for.
	 */
	@Override
	protected boolean visit(UsePart usePart) {
		String ns = usePart.getNamespace().getFullyQualifiedName();

		if (namespace.equalsIgnoreCase(ns)) {
			result = usePart;
			return false;
		}

		return true;
	}

}
