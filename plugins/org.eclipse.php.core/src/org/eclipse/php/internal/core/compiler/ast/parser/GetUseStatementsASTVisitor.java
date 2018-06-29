/*******************************************************************************
 * Copyright (c) 2013 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.compiler.ast.nodes.UseStatement;

/**
 * AST visitor for finding all use statements in a PHP source module.
 * 
 * @author Kaloyan Raev
 */
public class GetUseStatementsASTVisitor extends AbstractUseStatementASTVisitor {

	/**
	 * The result list of all found {@link UseStatement}s.
	 */
	private List<UseStatement> result = new LinkedList<>();

	/**
	 * Constructor of the visitor.
	 * 
	 * @param offset
	 *            the position in the AST tree after which the search stops
	 */
	public GetUseStatementsASTVisitor(int offset) {
		super(offset);
	}

	/**
	 * Returns all found {@link UseStatement}s.
	 * 
	 * <p>
	 * This method never returns <code>null</code>.
	 * </p>
	 * 
	 * @return an array of {@link UseStatement} nodes.
	 */
	public UseStatement[] getResult() {
		return result.toArray(new UseStatement[result.size()]);
	}

	/**
	 * Just add every {@link UseStatement} node visited to the result.
	 * 
	 * <p>
	 * The base AST visitor ensures that only nodes before the <code>offset</code>
	 * are visited.
	 * </p>
	 */
	@Override
	protected void visit(UseStatement s) {
		result.add(s);
	}

}
