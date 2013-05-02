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
 * Represents namespace declaration:
 * 
 * <pre>e.g.
 * 
 * <pre>
 * namespace MyNamespace;
 *namespace MyProject\Sub\Level;
 */
public class NamespaceDeclaration extends Statement {

	private NamespaceName name;
	private Block body;
	private boolean bracketed = true;

	/**
	 * The "namespace" structural property of this node type.
	 */
	public static final ChildPropertyDescriptor NAME_PROPERTY = new ChildPropertyDescriptor(
			NamespaceDeclaration.class,
			"name", NamespaceName.class, OPTIONAL, NO_CYCLE_RISK); //$NON-NLS-1$

	public static final ChildPropertyDescriptor BODY_PROPERTY = new ChildPropertyDescriptor(
			NamespaceDeclaration.class,
			"body", Block.class, OPTIONAL, CYCLE_RISK); //$NON-NLS-1$

	public static final SimplePropertyDescriptor BRACKETED_PROPERTY = new SimplePropertyDescriptor(
			NamespaceDeclaration.class, "bracketed", Boolean.class, MANDATORY); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(
				2);
		properyList.add(NAME_PROPERTY);
		properyList.add(BODY_PROPERTY);
		properyList.add(BRACKETED_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public NamespaceDeclaration(AST ast) {
		super(ast);
	}

	public NamespaceDeclaration(int start, int end, AST ast,
			NamespaceName name, Block body, boolean bracketed) {
		super(start, end, ast);

		if (!bracketed && name == null) {
			throw new IllegalArgumentException(
					"Namespace name must not be null in an un-bracketed statement"); //$NON-NLS-1$
		}

		this.bracketed = bracketed;

		if (body == null) {
			body = new Block(end + 1, end + 1, ast, new ArrayList(), false);
		}
		body.setParent(this, BODY_PROPERTY);

		if (name != null) {
			name.setParent(this, NAME_PROPERTY);
		}

		this.name = name;
		this.body = body;
	}

	/**
	 * Returns whether this namespace declaration has a bracketed syntax
	 * 
	 * @return
	 */
	public boolean isBracketed() {
		return bracketed;
	}

	public void setBracketed(boolean bracketed) {
		preValueChange(BRACKETED_PROPERTY);
		this.bracketed = bracketed;
		postValueChange(BRACKETED_PROPERTY);
	}

	public void addStatement(Statement statement) {
		Block body = getBody();
		body.statements().add(statement);

		int statementEnd = statement.getEnd();
		int bodyStart = body.getStart();
		body.setSourceRange(bodyStart, statementEnd - bodyStart + 1);

		int namespaceStart = getStart();
		setSourceRange(namespaceStart, statementEnd - namespaceStart);
	}

	/**
	 * The body component of this namespace declaration node
	 * 
	 * @return body component of this namespace declaration node
	 */
	public Block getBody() {
		return body;
	}

	/**
	 * Sets the name of this parameter
	 * 
	 * @param name
	 *            of this type declaration.
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setBody(Block block) {
		// an Assignment may occur inside a Expression - must check cycles
		ASTNode oldChild = this.body;
		preReplaceChild(oldChild, block, BODY_PROPERTY);
		this.body = block;
		postReplaceChild(oldChild, block, BODY_PROPERTY);
	}

	/**
	 * The name component of this namespace declaration node
	 * 
	 * @return name component of this namespace declaration node
	 */
	public NamespaceName getName() {
		return name;
	}

	/**
	 * Sets the name of this parameter
	 * 
	 * @param name
	 *            of this type declaration.
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setName(NamespaceName name) {
		// an Assignment may occur inside a Expression - must check cycles
		ASTNode oldChild = this.name;
		preReplaceChild(oldChild, name, NAME_PROPERTY);
		this.name = name;
		postReplaceChild(oldChild, name, NAME_PROPERTY);
	}

	public void childrenAccept(Visitor visitor) {
		NamespaceName name = getName();
		if (name != null) {
			name.accept(visitor);
		}
		Block body = getBody();
		body.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		NamespaceName name = getName();
		if (name != null) {
			name.accept(visitor);
		}
		Block body = getBody();
		body.accept(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		NamespaceName name = getName();
		if (name != null) {
			name.accept(visitor);
		}
		Block body = getBody();
		body.accept(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<NamespaceDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isBracketed='").append(bracketed).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$

		NamespaceName name = getName();
		if (name != null) {
			name.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}

		Block body = getBody();
		body.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$

		buffer.append(tab).append("</NamespaceDeclaration>"); //$NON-NLS-1$
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
		final NamespaceName name = ASTNode.copySubtree(target, getName());
		final Block body = ASTNode.copySubtree(target, getBody());
		final boolean bracketed = isBracketed();
		final NamespaceDeclaration result = new NamespaceDeclaration(this
				.getStart(), this.getEnd(), target, name, body, bracketed);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	boolean internalGetSetBooleanProperty(SimplePropertyDescriptor property,
			boolean get, boolean value) {
		if (property == BRACKETED_PROPERTY) {
			if (get) {
				return isBracketed();
			} else {
				setBracketed(value);
				return false;
			}
		}
		return super.internalGetSetBooleanProperty(property, get, value);
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property,
			boolean get, ASTNode child) {
		if (property == BODY_PROPERTY) {
			if (get) {
				return getBody();
			} else {
				setBody((Block) child);
				return null;
			}
		}
		if (property == NAME_PROPERTY) {
			if (get) {
				return getName();
			} else {
				setName((NamespaceName) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}
}
