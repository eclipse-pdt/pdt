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

import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.php.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * AST visitor for finding use statements by alias.
 * 
 * @author Kaloyan Raev
 */
public class FindUseStatementByAliasASTVisitor extends AbstractUseStatementASTVisitor {

	/**
	 * Current use statement, used to detect group use statements.
	 */
	private UseStatement currentUseStatement;

	/**
	 * The alias to look for.
	 */
	private String aliasName;

	/**
	 * The found {@link UsePart} node to return as result.
	 */
	private UsePart result;

	/**
	 * Constructor of the visitor.
	 * 
	 * @param aliasName
	 *            the alias to look for
	 * @param offset
	 *            the position in the AST tree after which the search stops
	 */
	public FindUseStatementByAliasASTVisitor(String aliasName, int offset) {
		super(offset);
		this.aliasName = aliasName;
	}

	/**
	 * Returns the found {@link UsePart} node that corresponds to the specified
	 * alias name.
	 * 
	 * @return a <code>UsePart</code> node, or <code>null<code> if there is not
	 *         use statement for the specified alias name.
	 */
	public UsePart getResult() {
		return result;
	}

	@Override
	protected void visit(UseStatement s) throws Exception {
		this.currentUseStatement = s;
		super.visit(s);
	}

	@Override
	public boolean endvisit(Statement s) throws Exception {
		if (s instanceof UseStatement) {
			currentUseStatement = null;
		}
		return super.endvisit(s);
	}

	/**
	 * Compares the alias of the {@link UsePart} node being visited with the
	 * alias name that the visitor is looking for.
	 */
	@Override
	protected boolean visit(UsePart usePart) {
		String alias;
		if (usePart.getAlias() != null) {
			alias = usePart.getAlias().getName();
		} else {
			// In case there's no alias - the alias is the
			// last segment of the namespace name:
			alias = usePart.getNamespace().getName();
		}

		if (aliasName.equalsIgnoreCase(alias)) {
			if (currentUseStatement == null || currentUseStatement.getNamespace() == null) {
				result = usePart;
			} else {
				FullyQualifiedReference fqn = createCombinedFQN(usePart);

				result = new UsePart(fqn, usePart.getAlias(),
						Math.max(currentUseStatement.getStatementType(), usePart.getStatementType()));
			}
			return false;
		}

		return true;
	}

	/**
	 * Creates fake FQN hat combines use statement namespace and use part
	 * namespace.
	 * 
	 * @param usePart
	 * @return
	 */
	private FullyQualifiedReference createCombinedFQN(UsePart usePart) {
		String firstNamespace = currentUseStatement.getNamespace().getFullyQualifiedName();
		String name = PHPModelUtils.extractElementName(usePart.getNamespace().getFullyQualifiedName());
		String secondNamespace = PHPModelUtils.extractNameSpaceName(usePart.getNamespace().getFullyQualifiedName());

		String fqn = PHPModelUtils.concatFullyQualifiedNames(firstNamespace, secondNamespace);
		return new FullyQualifiedReference(0, 0, name, new NamespaceReference(0, 0, fqn));
	}

}
