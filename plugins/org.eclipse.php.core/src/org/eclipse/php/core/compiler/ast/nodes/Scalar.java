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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a scalar
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * 'string', 1, 1.3, __CLASS__ "string"[0] `
 */
public class Scalar extends StringLiteral implements Dereferencable {
	// 'int'
	public static final int TYPE_INT = 0;
	// 'real'
	public static final int TYPE_REAL = 1;
	// 'string'
	public static final int TYPE_STRING = 2;
	// unknown scalar in quote expression
	public static final int TYPE_UNKNOWN = 3;
	// system scalars (__CLASS__ / ...)
	public static final int TYPE_SYSTEM = 4;
	// 'binary' starts with "0b",e.g "0b"[01]+
	public static final int TYPE_BIN = 5;

	private final int scalarType;

	public Scalar(int start, int end, String value, int type) {
		super(start, end, value);
		this.scalarType = type;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			visitor.endvisit(this);
		}
	}

	public String getType() {
		switch (getScalarType()) {
		case TYPE_INT:
			return "int"; //$NON-NLS-1$
		case TYPE_REAL:
			return "real"; //$NON-NLS-1$
		case TYPE_STRING:
			return "string"; //$NON-NLS-1$
		case TYPE_UNKNOWN:
			return "unknown"; //$NON-NLS-1$
		case TYPE_SYSTEM:
			return "system"; //$NON-NLS-1$
		case TYPE_BIN:
			return "bin"; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.SCALAR;
	}

	public int getScalarType() {
		return scalarType;
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
