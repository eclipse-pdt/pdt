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
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a function declaration
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * function foo() {}
 * 
 * function &amp;foo() {}
 * 
 * function foo($a, int $b, $c = 5, int $d = 6) {}
 * 
 * function foo(); -abstract function in class declaration
 * 
 * function foo() : MyClass;
 */
public class PHPMethodDeclaration extends MethodDeclaration implements IPHPDocAwareDeclaration {

	private static final List<FormalParameter> EMPTY_PARAMETERS = new LinkedList<FormalParameter>();
	private final boolean isReference;
	private PHPDocBlock phpDoc;
	private TypeReference returnType;

	public PHPMethodDeclaration(int start, int end, int nameStart, int nameEnd, String functionName,
			List<FormalParameter> formalParameters, Block body, final boolean isReference, PHPDocBlock phpDoc) {
		this(start, end, nameStart, nameEnd, functionName, Modifiers.AccPublic, formalParameters, body, isReference,
				phpDoc);
	}

	public PHPMethodDeclaration(int start, int end, int nameStart, int nameEnd, String functionName, int modifiers,
			List<FormalParameter> formalParameters, Block body, final boolean isReference, PHPDocBlock phpDoc) {
		this(start, end, nameStart, nameEnd, functionName, modifiers, formalParameters, body, isReference, phpDoc,
				null);
	}

	public PHPMethodDeclaration(int start, int end, int nameStart, int nameEnd, String functionName, int modifiers,
			List<FormalParameter> formalParameters, Block body, final boolean isReference, PHPDocBlock phpDoc,
			TypeReference returnType) {
		super(functionName, nameStart, nameEnd, start, end);

		setModifiers(modifiers);
		acceptArguments(formalParameters == null ? EMPTY_PARAMETERS : formalParameters);
		acceptBody(body);

		this.isReference = isReference;
		this.phpDoc = phpDoc;
		this.returnType = returnType;
	}

	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	public boolean isReference() {
		return isReference;
	}

	public TypeReference getReturnType() {
		return returnType;
	}

	public void setReturnType(TypeReference returnType) {
		this.returnType = returnType;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
