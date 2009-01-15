/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents namespace declaration:
 * <pre>e.g.<pre>namespace MyNamespace;
 *namespace MyProject\Sub\Level;
 */
public class Namespace extends Statement {

	protected ASTNode.NodeList<Identifier> names = new ASTNode.NodeList<Identifier>(ELEMENTS_PROPERTY);
	
	/**
	 * The "namespace" structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor ELEMENTS_PROPERTY = 
		new ChildListPropertyDescriptor(Namespace.class, "names", Namespace.class, NO_CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(2);
		properyList.add(ELEMENTS_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public Namespace(int start, int end, AST ast, Identifier[] names) {
		super(start, end, ast);
		
		if (names == null) {
			throw new IllegalArgumentException();
		}

		for (Identifier name : names) {
			this.names.add(name);
		}
	}
	
	public Namespace(int start, int end, AST ast, List names) {
		this(start, end, ast, names == null ? null : (Identifier[]) names.toArray(new Identifier[names.size()]));
	}

	public void childrenAccept(Visitor visitor) {
		for (ASTNode node : this.names) {
			node.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.names) {
			node.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.names) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Namespace"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.names) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}				
		buffer.append(tab).append("</Namespace>"); //$NON-NLS-1$
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	
	
	public int getType() {
		return ASTNode.NAMESPACE;
	}

	/**
	 * @deprecated use elements()
	 */
	public ArrayElement[] getElements() {
		return names.toArray(new ArrayElement[names.size()]);
	}
	
	/**
	 * Retrieves names parts of the namespace
	 * @return names
	 */
	public List<Identifier> names() {
		return this.names;
	}
	
	
	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	ASTNode clone0(AST target) {
		final List elements = ASTNode.copySubtrees(target, names());
		final ArrayCreation result = new ArrayCreation(this.getStart(), this.getEnd(), target, elements);
		return result;
	}
	
	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
	
	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == ELEMENTS_PROPERTY) {
			return names();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}
}
