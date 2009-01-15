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
	private Block body;
	
	/**
	 * The "namespace" structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor ELEMENTS_PROPERTY = 
		new ChildListPropertyDescriptor(Namespace.class, "names", Identifier.class, NO_CYCLE_RISK); //$NON-NLS-1$
	
	public static final ChildPropertyDescriptor BODY_PROPERTY = 
		new ChildPropertyDescriptor(Namespace.class, "body", Block.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

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

	public Namespace(int start, int end, AST ast, Identifier[] names, Block body) {
		super(start, end, ast);

		if (names == null) {
			throw new IllegalArgumentException();
		}
		for (Identifier name : names) {
			this.names.add(name);
		}
		// Body can be null
		this.body = body;
	}
	
	/**
	 * Creates global namespace node (without name)
	 * @param start
	 * @param end
	 * @param ast
	 * @param body
	 */
	public Namespace(int start, int end, AST ast, Block body) {
		super(start, end, ast);

		// Body must be set in this case
		if (body == null) {
			throw new IllegalArgumentException();
		}
	}
	
	public Namespace(int start, int end, AST ast, List names) {
		this(start, end, ast, names, null);
	}
	
	public Namespace(int start, int end, AST ast, List names, Block body) {
		this(start, end, ast, names == null ? null : (Identifier[]) names.toArray(new Identifier[names.size()]), body);
	}

	/**
	 * The body component of this type declaration node 
	 * @return body component of this type declaration node
	 */
	public Block getBody() {
		return body;
	}

	/**
	 * Sets the name of this parameter
	 * 
	 * @param name of this type declaration.
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setBody(Block block) {
		if (block == null) {
			throw new IllegalArgumentException();
		}
		// an Assignment may occur inside a Expression - must check cycles
		ASTNode oldChild = this.body;
		preReplaceChild(oldChild, block, BODY_PROPERTY);
		this.body = block;
		postReplaceChild(oldChild, block, BODY_PROPERTY);
	}
	
	public void childrenAccept(Visitor visitor) {
		for (ASTNode node : this.names) {
			node.accept(visitor);
		}
		getBody().accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.names) {
			node.traverseTopDown(visitor);
		}
		getBody().traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.names) {
			node.traverseBottomUp(visitor);
		}
		getBody().traverseTopDown(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Namespace"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n").append(tab).append(TAB).append("<Name>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (ASTNode node : this.names) {
			node.toString(buffer, TAB + tab + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</Name>\n");
		
		Block body = getBody();
		if (body != null) {
			body.toString(buffer, TAB + tab);
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
	 * Retrieves names parts of the namespace
	 * @return names. If names list is empty, that means that this namespace is global.
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
		final List names = ASTNode.copySubtrees(target, names());
		final Block body = ASTNode.copySubtree(target, getBody());
		final Namespace result = new Namespace(this.getStart(), this.getEnd(), target, names, body);
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
	
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == BODY_PROPERTY) {
			if (get) {
				return getBody();
			} else {
				setBody((Block) body);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}
}
