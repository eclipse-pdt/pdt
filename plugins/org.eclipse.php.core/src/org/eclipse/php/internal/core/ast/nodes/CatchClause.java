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
 * Represents a catch clause (as part of a try statement)
 * <pre>e.g.<pre> catch (ExceptionClassName $variable) { body; },
 * 
 */
public class CatchClause extends Statement {

	private Expression className;
	private Variable variable;
	private Block body;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildPropertyDescriptor CLASS_NAME_PROPERTY = 
		new ChildPropertyDescriptor(CatchClause.class, "className", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor VARIABLE_PROPERTY = 
		new ChildPropertyDescriptor(CatchClause.class, "variable", Variable.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor BODY_PROPERTY = 
		new ChildPropertyDescriptor(CatchClause.class, "statement", Block.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(3);
		properyList.add(CLASS_NAME_PROPERTY);
		properyList.add(VARIABLE_PROPERTY);
		properyList.add(BODY_PROPERTY);		
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public CatchClause(int start, int end, AST ast, Expression className, Variable variable, Block statement) {
		super(start, end, ast);

		if (variable == null || statement == null) {
			throw new IllegalArgumentException();
		}
		if (!(className instanceof Identifier) && !(className instanceof NamespaceName)) {
			throw new IllegalArgumentException();
		}
		
		this.className = className;
		this.variable = variable;
		this.body = statement;

		className.setParent(this, CLASS_NAME_PROPERTY);
		variable.setParent(this, VARIABLE_PROPERTY);
		statement.setParent(this, BODY_PROPERTY);
	}

	public CatchClause(AST ast) {
		super(ast);
	}
	
	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		className.accept(visitor);
		variable.accept(visitor);
		body.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		className.traverseTopDown(visitor);
		variable.traverseTopDown(visitor);
		body.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		className.traverseBottomUp(visitor);
		variable.traverseBottomUp(visitor);
		body.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<CatchClause"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<ClassName>\n"); //$NON-NLS-1$
		className.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</ClassName>\n"); //$NON-NLS-1$
		variable.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		body.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</CatchClause>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.CATCH_CLAUSE;
	}

	/**
	 * @deprecated use #getBody()
	 */
	public Block getStatement() {
		return body;
	}

	/**
	 * Returns the class name of this catch clause.
	 * 
	 * @return the exception variable declaration node
	 */ 
	public Expression getClassName() {
		return this.className;
	}
		
	/**
	 * Sets the variable declaration of this catch clause.
	 * 
	 * @param exception the exception variable declaration node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setClassName(Expression className) {
		if (!(className instanceof Identifier) && !(className instanceof NamespaceName)) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.className;
		preReplaceChild(oldChild, className, CLASS_NAME_PROPERTY);
		this.className= className;
		postReplaceChild(oldChild, className, CLASS_NAME_PROPERTY);
	}
	
	
	/**
	 * Returns the exception variable declaration of this catch clause.
	 * 
	 * @return the exception variable declaration node
	 */ 
	public Variable getVariable() {
		return this.variable;
	}
		
	/**
	 * Sets the variable declaration of this catch clause.
	 * 
	 * @param exception the exception variable declaration node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setVariable(Variable variable) {
		if (variable == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.variable;
		preReplaceChild(oldChild, variable, VARIABLE_PROPERTY);
		this.variable = variable;
		postReplaceChild(oldChild, variable, VARIABLE_PROPERTY);
	}
	
	/**
	 * Returns the body of this catch clause.
	 * 
	 * @return the catch clause body
	 */ 
	public Block getBody() {
		return this.body;
	}
	
	/**
	 * Sets the body of this catch clause.
	 * 
	 * @param body the catch clause block node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setBody(Block body) {
		if (body == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.body;
		preReplaceChild(oldChild, body, BODY_PROPERTY);
		this.body = body;
		postReplaceChild(oldChild, body, BODY_PROPERTY);
	}
	
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	ASTNode clone0(AST target) {
		final Block body = ASTNode.copySubtree(target, getBody());
		final Expression className = ASTNode.copySubtree(target, getClassName());
		final Variable variable = ASTNode.copySubtree(target, getVariable());
		
		CatchClause result = new CatchClause(this.getStart(), this.getEnd(), target, className, variable, body);
		return result;
	}


	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
	
	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == CLASS_NAME_PROPERTY) {
			if (get) {
				return getClassName();
			} else {
				setClassName((Expression) child);
				return null;
			}
		}
		if (property == VARIABLE_PROPERTY) {
			if (get) {
				return getVariable();
			} else {
				setVariable((Variable) child);
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
}
