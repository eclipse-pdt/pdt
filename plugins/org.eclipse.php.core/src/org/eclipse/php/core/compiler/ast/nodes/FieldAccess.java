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
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represents a field access
 * 
 * <pre>
 * e.g.
 * 
 * $a->$b
 * $a?->$b
 * </pre>
 */
public class FieldAccess extends Dispatch {

	private final boolean nullSafe;
	private final Expression field;

	public FieldAccess(int start, int end, Expression dispatcher, Expression field) {
		this(start, end, dispatcher, false, field);
	}

	public FieldAccess(int start, int end, Expression dispatcher, boolean nullSafe, Expression field) {
		super(start, end, dispatcher);

		assert field != null;
		this.field = field;
		this.nullSafe = nullSafe;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			getDispatcher().traverse(visitor);
			field.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.FIELD_ACCESS;
	}

	public Expression getField() {
		return field;
	}

	public boolean isNullSafe() {
		return nullSafe;
	}
}
