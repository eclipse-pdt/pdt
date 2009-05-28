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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableKind;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Holds a variable and an index that point to array or hashtable
 * <pre>e.g.<pre> $a[],
 * $a[1],
 * $a[$b],
 * $a{'name'}
 */
public class ArrayVariableReference extends VariableReference {

	public static final int VARIABLE_ARRAY = 1;
	public static final int VARIABLE_HASHTABLE = 2;

	/**
	 * In case of array / hashtable variable, the index expression is added
	 */
	private final Expression index;
	private final int arrayType;

	public ArrayVariableReference(DLTKToken token, Expression index, int arrayType) {
		super(token);
		this.index = index;
		this.arrayType = arrayType;
	}

	public ArrayVariableReference(int start, int end, String name, VariableKind kind, Expression index, int arrayType) {
		super(start, end, name, kind);
		this.index = index;
		this.arrayType = arrayType;
	}

	public ArrayVariableReference(int start, int end, String name, Expression index, int arrayType) {
		super(start, end, name);
		this.index = index;
		this.arrayType = arrayType;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (index != null) {
				index.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public static String getArrayType(int type) {
		switch (type) {
			case VARIABLE_ARRAY:
				return "array"; //$NON-NLS-1$
			case VARIABLE_HASHTABLE:
				return "hashtable"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public Expression getIndex() {
		return index;
	}

	public int getArrayType() {
		return arrayType;
	}

	public int getKind() {
		return ASTNodeKinds.ARRAY_ACCESS;
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
