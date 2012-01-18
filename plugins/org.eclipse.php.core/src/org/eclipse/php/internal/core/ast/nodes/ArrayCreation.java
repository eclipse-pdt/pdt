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
package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents array creation
 * 
 * <pre>e.g.
 * 
 * <pre>
 * array(1,2,3,),
 * array('Dodo'=>'Golo','Dafna'=>'Dodidu')
 * array($a, $b=>foo(), 1=>$myClass->getFirst())
 */
public class ArrayCreation extends Expression {

	private final ASTNode.NodeList<ArrayElement> elements = new ASTNode.NodeList<ArrayElement>(
			ELEMENTS_PROPERTY);
	private boolean hasArrayKey;
	/**
	 * The "statements" structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor ELEMENTS_PROPERTY = new ChildListPropertyDescriptor(
			ArrayCreation.class, "elements", ArrayElement.class, CYCLE_RISK); //$NON-NLS-1$

	public static final SimplePropertyDescriptor HAS_ARRAY_KEY = new SimplePropertyDescriptor(
			LambdaFunctionDeclaration.class,
			"hasArrayKey", Boolean.class, OPTIONAL); //$NON-NLS-1$
	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(
				2);
		properyList.add(ELEMENTS_PROPERTY);
		properyList.add(HAS_ARRAY_KEY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public ArrayCreation(AST ast) {
		super(ast);
	}

	private ArrayCreation(int start, int end, AST ast, ArrayElement[] elements,
			boolean hasArrayKey) {
		super(start, end, ast);

		if (elements == null) {
			throw new IllegalArgumentException();
		}

		for (ArrayElement arrayElement : elements) {
			this.elements.add(arrayElement);
		}
		setHasArrayKey(hasArrayKey);
	}

	public ArrayCreation(int start, int end, AST ast, List elements) {
		this(start, end, ast, elements == null ? null
				: (ArrayElement[]) elements.toArray(new ArrayElement[elements
						.size()]), true);
	}

	public ArrayCreation(int start, int end, AST ast, List elements,
			boolean hasArrayKey) {
		this(start, end, ast, elements == null ? null
				: (ArrayElement[]) elements.toArray(new ArrayElement[elements
						.size()]), hasArrayKey);
	}

	public boolean isHasArrayKey() {
		return hasArrayKey;
	}

	public void setHasArrayKey(boolean hasArrayKey) {
		preValueChange(HAS_ARRAY_KEY);
		this.hasArrayKey = hasArrayKey;
		postValueChange(HAS_ARRAY_KEY);
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	final boolean internalGetSetBooleanProperty(
			SimplePropertyDescriptor property, boolean get, boolean value) {
		if (property == HAS_ARRAY_KEY) {
			if (get) {
				return isHasArrayKey();
			} else {
				setHasArrayKey(value);
				return false;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetBooleanProperty(property, get, value);
	}

	public void childrenAccept(Visitor visitor) {
		for (ASTNode node : this.elements) {
			node.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.elements) {
			node.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.elements) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ArrayCreation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.elements) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</ArrayCreation>"); //$NON-NLS-1$
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public int getType() {
		return ASTNode.ARRAY_CREATION;
	}

	/**
	 * @deprecated use elements()
	 */
	public ArrayElement[] getElements() {
		return elements.toArray(new ArrayElement[elements.size()]);
	}

	/**
	 * Retrieves elements parts of array creation
	 * 
	 * @return elements
	 */
	public List<ArrayElement> elements() {
		return this.elements;
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	ASTNode clone0(AST target) {
		final List elements = ASTNode.copySubtrees(target, elements());
		final ArrayCreation result = new ArrayCreation(this.getStart(),
				this.getEnd(), target, elements);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == ELEMENTS_PROPERTY) {
			return elements();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

}
