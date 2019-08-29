/*******************************************************************************
 * Copyright (c) 2009-2019 IBM Corporation and others.
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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PHPFieldDeclaration extends FieldDeclaration implements IPHPDocAwareDeclaration {

	private SimpleReference fieldType;
	private int declStart;
	private Expression initializer;
	private PHPDocBlock phpDoc;

	public PHPFieldDeclaration(VariableReference variable, SimpleReference variableType, Expression initializer,
			int start, int end, int modifiers, int declStart, PHPDocBlock phpDoc) {
		super(variable.getName(), variable.sourceStart(), variable.sourceEnd(), start, end);

		if ((modifiers & Modifiers.AccPrivate) == 0 && (modifiers & Modifiers.AccProtected) == 0) {
			modifiers |= Modifiers.AccPublic;
		}
		setModifiers(modifiers);

		this.fieldType = variableType;
		this.initializer = initializer;
		this.declStart = declStart;
		this.phpDoc = phpDoc;
	}

	/**
	 * @since PHP 7.4
	 */
	public PHPFieldDeclaration(VariableReference variable, Expression initializer, int start, int end, int modifiers,
			int declStart, PHPDocBlock phpDoc) {
		this(variable, null, initializer, start, end, modifiers, declStart, phpDoc);
	}

	@Override
	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			if (fieldType != null) {
				fieldType.traverse(visitor);
			}
			getRef().traverse(visitor);
			if (initializer != null) {
				initializer.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.FIELD_DECLARATION;
	}

	public Expression getVariableValue() {
		return initializer;
	}

	public int getDeclarationStart() {
		return declStart;
	}

	public SimpleReference getFieldType() {
		return fieldType;
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
