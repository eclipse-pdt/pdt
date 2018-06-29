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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents array creation
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * array(1,2,3,), array('Dodo'=>'Golo','Dafna'=>'Dodidu') array($a, $b=>foo(),
 * 1=>$myClass->getFirst()), array($a, $b=>foo(), 1=>$myClass->getFirst())[0]
 */
public class ArrayCreation extends Expression implements Dereferencable {

	private final List<ArrayElement> elements;

	public ArrayCreation(int start, int end, List<ArrayElement> elements) {
		super(start, end);

		assert elements != null;
		this.elements = new ArrayList<>();
		for (Expression el : elements) {
			if (el instanceof ArrayElement) {
				this.elements.add((ArrayElement) el);
			} else {
				this.elements.add(new ArrayElement(el.start(), el.end(), el));
			}
		}
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			for (ArrayElement element : elements) {
				element.traverse(visitor);
			}
		}

		visitor.endvisit(this);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.ARRAY_CREATION;
	}

	public Collection<ArrayElement> getElements() {
		return elements;
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
