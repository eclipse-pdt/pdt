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

import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a namespace declaration
 * 
 * <pre>
 * 
 * e.g.
 * 
 * namespace A; namespace A { } namespace { }
 * </pre>
 */
public class NamespaceDeclaration extends TypeDeclaration implements IPHPDocAwareDeclaration {

	public static final String GLOBAL = "__global__namespace__"; //$NON-NLS-1$
	private PHPDocBlock phpDoc;
	private boolean braketed = true;

	public NamespaceDeclaration(int start, int end, int nameStart, int nameEnd, String className, Block body,
			PHPDocBlock phpDoc) {
		super(className, nameStart, nameEnd, start, end);
		this.phpDoc = phpDoc;
		if (body == null) {
			body = new Block(start, end);
		}
		setBody(body);
	}

	public void setBracketed(boolean bracketed) {
		this.braketed = bracketed;
	}

	public boolean isBracketed() {
		return braketed;
	}

	public boolean isGlobal() {
		return getName() == GLOBAL;
	}

	@Override
	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	public void addStatement(Statement statement) {
		// If there's no body - create one that will hold the namespace
		// statements
		getBody().addStatement(statement);

		getBody().setEnd(statement.sourceEnd());
		setEnd(statement.sourceEnd());
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.NAMESPACE_DECLARATION;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	@Override
	public final void printNode(CorePrinter output) {
	}

	@Override
	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
