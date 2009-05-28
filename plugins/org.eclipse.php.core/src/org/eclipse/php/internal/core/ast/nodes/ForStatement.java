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
 * Represents a for statement
 * <pre>e.g.<pre>
 * for (expr1; expr2; expr3)
 * 	 statement;
 * 
 * for (expr1; expr2; expr3):
 * 	 statement
 * 	 ...
 * endfor;
 */
public class ForStatement extends Statement {

	private final ASTNode.NodeList<Expression> initializers = new ASTNode.NodeList<Expression>(INITIALIZERS_PROPERTY);
	private final ASTNode.NodeList<Expression> conditions = new ASTNode.NodeList<Expression>(EXPRESSION_PROPERTY);
	private final ASTNode.NodeList<Expression> updaters = new ASTNode.NodeList<Expression>(UPDATERS_PROPERTY);
	private Statement body;

	/**
	 * The "initializers" structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor INITIALIZERS_PROPERTY = 
		new ChildListPropertyDescriptor(ForStatement.class, "initializers", Expression.class, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor EXPRESSION_PROPERTY =
		new ChildListPropertyDescriptor(ForStatement.class, "conditions", Expression.class, CYCLE_RISK); //$NON-NLS-1$		
	public static final ChildListPropertyDescriptor UPDATERS_PROPERTY = 
		new ChildListPropertyDescriptor(ForStatement.class, "updaters", Expression.class, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor BODY_PROPERTY = 
		new ChildPropertyDescriptor(ForStatement.class, "body", Statement.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(4);
		properyList.add(INITIALIZERS_PROPERTY);
		properyList.add(EXPRESSION_PROPERTY);
		properyList.add(UPDATERS_PROPERTY);
		properyList.add(BODY_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	/**
	 * Returns a list of structural property descriptors for this node type.
	 * Clients must not modify the result.
	 * 
	 * @param apiLevel the API level; one of the
	 * <code>AST.JLS*</code> constants

	 * @return a list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor})
	 * @since 3.0
	 */
	public static List<StructuralPropertyDescriptor> propertyDescriptors(int apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}	
	
	private ForStatement(int start, int end, AST ast, Expression[] initializations, Expression[] conditions, Expression[] increasements, Statement action) {
		super(start, end, ast);

		if (initializations == null || conditions == null || increasements == null || action == null) {
			throw new IllegalArgumentException();
		}
		for (Expression init : initializations) {
			this.initializers.add(init);
		}
		for (Expression cond : conditions) {
			this.conditions.add(cond);
		}
		for (Expression inc : increasements) {
			this.updaters.add(inc);
		}
		setBody(action);
	}

	public ForStatement(int start, int end, AST ast, List initializations, List conditions, List increasements, Statement action) {
		this(start, end, ast, initializations == null ? null : (Expression[]) initializations.toArray(new Expression[initializations.size()]), conditions == null ? null : (Expression[]) conditions.toArray(new Expression[conditions.size()]), increasements == null ? null : (Expression[]) increasements
			.toArray(new Expression[increasements.size()]), action);
	}

	public ForStatement(AST ast) {
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
		for (ASTNode node : this.initializers) {
			node.accept(visitor);
		}
		for (ASTNode node : this.conditions) {
			node.accept(visitor);
		}
		for (ASTNode node : this.updaters) {
			node.accept(visitor);
		}
		body.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		for (ASTNode node : this.initializers) {
			node.traverseTopDown(visitor);
		}
		for (ASTNode node : this.conditions) {
			node.traverseTopDown(visitor);
		}
		for (ASTNode node : this.updaters) {
			node.traverseTopDown(visitor);
		}
		body.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.initializers) {
			node.traverseBottomUp(visitor);
		}
		for (ASTNode node : this.conditions) {
			node.traverseBottomUp(visitor);
		}
		for (ASTNode node : this.updaters) {
			node.traverseBottomUp(visitor);
		}		
		body.traverseBottomUp(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ForStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Initializations>\n"); //$NON-NLS-1$
		for (ASTNode node : this.initializers) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}		
		buffer.append(TAB).append(tab).append("</Initializations>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Conditions>\n"); //$NON-NLS-1$
		for (ASTNode node : this.conditions) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}		
		buffer.append(TAB).append(tab).append("</Conditions>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Increasements>\n"); //$NON-NLS-1$
		for (ASTNode node : this.updaters) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}		
		buffer.append(TAB).append(tab).append("</Increasements>\n"); //$NON-NLS-1$
		body.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ForStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FOR_STATEMENT;
	}

	/**
	 * Returns the live ordered list of initializer expressions in this for
	 * statement.
	 * <p>
	 * The list should consist of either a list of so called statement 
	 * expressions (JLS2, 14.8), or a single <code>VariableDeclarationExpression</code>. 
	 * Otherwise, the for statement would have no Java source equivalent.
	 * </p>
	 * 
	 * @return the live list of initializer expressions 
	 *    (element type: <code>Expression</code>)
	 */ 
	public List<Expression> initializers() {
		return this.initializers;
	}
	
	/**
	 * Returns the condition expression of this for statement, or 
	 * <code>null</code> if there is none.
	 * 
	 * @return the condition expression node, or <code>null</code> if 
	 *     there is none
	 */ 
	public List<Expression> conditions() {
		return this.conditions;
	}
	
	/**
	 * Returns the live ordered list of update expressions in this for
	 * statement.
	 * <p>
	 * The list should consist of so called statement expressions. Otherwise,
	 * the for statement would have no Java source equivalent.
	 * </p>
	 * 
	 * @return the live list of update expressions 
	 *    (element type: <code>Expression</code>)
	 */ 
	public List<Expression> updaters() {
		return this.updaters;
	}
	
	/**
	 * Returns the body of this for statement.
	 * 
	 * @return the body statement node
	 */ 
	public Statement getBody() {
		return this.body;
	}
	
	/**
	 * Sets the body of this for statement.
	 * <p>
	 * Special note: The Java language does not allow a local variable declaration
	 * to appear as the body of a for statement (they may only appear within a
	 * block). However, the AST will allow a <code>VariableDeclarationStatement</code>
	 * as the body of a <code>ForStatement</code>. To get something that will
	 * compile, be sure to embed the <code>VariableDeclarationStatement</code>
	 * inside a <code>Block</code>.
	 * </p>
	 * 
	 * @param statement the body statement node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setBody(Statement statement) {
		if (statement == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.body;
		preReplaceChild(oldChild, statement, BODY_PROPERTY);
		this.body = statement;
		postReplaceChild(oldChild, statement, BODY_PROPERTY);
	}
	
	/**
	 * @deprecated use {@link #getBody()} 
	 */
	public Statement getAction() {
		return body;
	}

	/**
	 * @deprecated use {@link #conditions()} 
	 */
	public Expression[] getConditions() {
		return (Expression[]) conditions.toArray(new Expression[conditions.size()]);
	}

	/**
	 * @deprecated use {@link #updaters()} 
	 */
	public Expression[] getIncreasements() {
		return (Expression[]) updaters.toArray(new Expression[conditions.size()]);
	}

	/**
	 * @deprecated use {@link #initializers()} 
	 */
	public Expression[] getInitializations() {
		return (Expression[]) initializers.toArray(new Expression[conditions.size()]);
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
		final List inits = ASTNode.copySubtrees(target, initializers());
		final List conds = ASTNode.copySubtrees(target, conditions());
		final List updtaters= ASTNode.copySubtrees(target, updaters());
		final Statement body = ASTNode.copySubtree(target, getBody());
		ForStatement result = new ForStatement(this.getStart(), this.getEnd(), target, inits, conds, updtaters, body);
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
		if (property == INITIALIZERS_PROPERTY) {
			return initializers();
		}
		if (property == EXPRESSION_PROPERTY) {
			return conditions();
		}
		if (property == UPDATERS_PROPERTY) {
			return updaters();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
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
