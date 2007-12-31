/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

import java.util.List;

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents array creation
 * <pre>e.g.<pre> array(1,2,3,),
 * array('Dodo'=>'Golo','Dafna'=>'Dodidu')
 * array($a, $b=>foo(), 1=>$myClass->getFirst())
 */
public class ArrayCreation extends Expression {

	private final ArrayElement[] elements;

	private ArrayCreation(int start, int end, ArrayElement[] elements) {
		super(start, end);

		assert elements != null;
		this.elements = elements;

		// set the child nodes' parent
		for (int i = 0; i < elements.length; i++) {
			elements[i].setParent(this);
		}
	}

	public ArrayCreation(int start, int end, List elements) {
		this(start, end, elements == null ? null : (ArrayElement[]) elements.toArray(new ArrayElement[elements.size()]));
	}

	public void childrenAccept(Visitor visitor) {
		for (int i = 0; i < elements.length; i++) {
			elements[i].accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (int i = 0; i < elements.length; i++) {
			elements[i].traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (int i = 0; i < elements.length; i++) {
			elements[i].traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ArrayCreation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (int i = 0; i < elements.length; i++) {
			elements[i].toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</ArrayCreation>"); //$NON-NLS-1$
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	
	
	public int getType() {
		return ASTNode.ARRAY_CREATION;
	}

	public ArrayElement[] getElements() {
		return elements;
	}
}
