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
 * AST visitor for finding use statements by namespace.
 * 
 * @author Kaloyan Raev
 */
public class FindUseStatementByNamespaceASTVisitor extends AbstractUseStatementASTVisitor {

	/**
	 * Current use statement, used to detect group use statements.
	 */
	private UseStatement currentUseStatement;

	/**
	 * The namespace to look for.
	 */
	private String namespace;

	/**
	 * The found {@link UsePart} node to return as result.
	 */
	private UsePart result;

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
	 * @return a <code>UsePart</code> node, or <code>null<code> if there is not
	 *         use statement for the specified namespace.
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

		boolean isGroupStatement = currentUseStatement != null && currentUseStatement.getNamespace() != null;
		if (isGroupStatement) {
			String curentFQN = currentUseStatement.getNamespace().getFullyQualifiedName();
			ns = PHPModelUtils.concatFullyQualifiedNames(curentFQN, ns);
		}

		if (namespace.equalsIgnoreCase(ns)) {
			if (isGroupStatement) {
				FullyQualifiedReference fqn = createCombinedFQN(usePart);

				result = new UsePart(fqn, usePart.getAlias(),
						Math.max(currentUseStatement.getStatementType(), usePart.getStatementType()));
			} else {
				result = usePart;
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
