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
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a single element of array. Holds the key and the value both can be
 * any expression The key can be null
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * 1, 'Dodo'=>'Golo', $a, $b=>foo(), 1=>$myClass->getFirst() *
 */
public class ArrayElement extends Expression {

	private final Expression key;
	private final Expression value;

	public ArrayElement(int start, int end, Expression key, Expression value) {
		super(start, end);

		assert value != null;
		this.key = key;
		this.value = value;
	}

	public ArrayElement(int start, int end, Expression value) {
		this(start, end, null, value);
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (key != null) {
				key.traverse(visitor);
			}
			value.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.ARRAY_ELEMENT;
	}

	public Expression getKey() {
		return key;
	}

	public Expression getValue() {
		return value;
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
