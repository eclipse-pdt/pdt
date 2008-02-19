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

/**
 * Represents base class for class declaration and interface declaration  
 */
public abstract class TypeDeclaration extends Statement {

	private Identifier name;
	private ASTNode.NodeList<Identifier> interfaces = new ASTNode.NodeList<Identifier>(INTERFACES_PROPERTY);
	private Block body;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildPropertyDescriptor NAME_PROPERTY = 
		new ChildPropertyDescriptor(TypeDeclaration.class, "name", Identifier.class, MANDATORY, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor INTERFACES_PROPERTY = 
		new ChildListPropertyDescriptor(TypeDeclaration.class, "interfaces", Identifier.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor BODY_PROPERTY = 
		new ChildPropertyDescriptor(TypeDeclaration.class, "body", Block.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	
	
	public TypeDeclaration(int start, int end, AST ast, final Identifier name, final Identifier[] interfaces, final Block body) {
		super(start, end, ast);

		if (name == null || body == null) {
			throw new IllegalArgumentException();
		}
		
		setName(name);
		setBody(body);
		if (interfaces != null) {
			for (Identifier identifier : interfaces) {
				this.interfaces.add(identifier);
			}
		}
	}

	public TypeDeclaration(AST ast) {
		super(ast);
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
	
	/**
	 * @deprecated use interfaces()
	 */
	public Identifier[] getInterfaces() {
		return this.interfaces.toArray(new Identifier[interfaces.size()]);
	}

	/**
	 * List of interfaces that this type implements / extends
	 */
	public List<Identifier> interfaces() {
		return this.interfaces;
	}
	
	/**
	 * The name of the type declaration node
	 * @return name of the type declaration node
	 */
	public Identifier getName() {
		return this.name;
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
	public void setName(Identifier id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		// an Assignment may occur inside a Expression - must check cycles
		ASTNode oldChild = this.name;
		preReplaceChild(oldChild, id, NAME_PROPERTY);
		this.name = id;
		postReplaceChild(oldChild, id, NAME_PROPERTY);
	}	
	
	ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == NAME_PROPERTY) {
			if (get) {
				return getName();
			} else {
				setName((Identifier) child);
				return null;
			}
		}
		if (property == BODY_PROPERTY) {
			if (get) {
				return getBody();
			} else {
				setBody((Block) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == INTERFACES_PROPERTY) {
			return interfaces();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}
	
	
}
