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

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			for (ArrayElement element : elements) {
				element.traverse(visitor);
			}
		}

		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.ARRAY_CREATION;
	}

	public Collection<ArrayElement> getElements() {
		return elements;
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
