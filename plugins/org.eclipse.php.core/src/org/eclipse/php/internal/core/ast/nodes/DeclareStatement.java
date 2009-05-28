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
import java.util.Iterator;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a declare statement
 * <pre>e.g.<pre> declare(ticks=1) { }
 * declare(ticks=2) { for ($x = 1; $x < 50; ++$x) {  }  }
 */
public class DeclareStatement extends Statement {

	private final ASTNode.NodeList<Identifier> directiveNames = new ASTNode.NodeList<Identifier>(DIRECTIVE_NAMES_PROPERTY);
	private final ASTNode.NodeList<Expression> directiveValues = new ASTNode.NodeList<Expression>(DIRECTIVE_VALUES_PROPERTY);
	private Statement body;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor DIRECTIVE_NAMES_PROPERTY 
		= new ChildListPropertyDescriptor(DeclareStatement.class, "directiveNames", Identifier.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor DIRECTIVE_VALUES_PROPERTY 
		= new ChildListPropertyDescriptor(DeclareStatement.class, "directiveValues", Expression.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor BODY_PROPERTY 
		= new ChildPropertyDescriptor(DeclareStatement.class, "action", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(3);
		properyList.add(DIRECTIVE_NAMES_PROPERTY);
		properyList.add(DIRECTIVE_VALUES_PROPERTY);
		properyList.add(BODY_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	private DeclareStatement(int start, int end, AST ast, Identifier[] directiveNames, Expression[] directiveValues, Statement action) {
		super(start, end, ast);

		if (directiveNames == null || directiveValues == null || directiveNames.length != directiveValues.length) {
			throw new IllegalArgumentException();
		}
		for (Identifier identifier : directiveNames) {
			this.directiveNames.add(identifier);
		}
		for (Expression expression : directiveValues) {
			this.directiveValues.add(expression);
		}
		setBody(action);
	}

	public DeclareStatement(int start, int end, AST ast, List directiveNames, List directiveValues, Statement action) {
		this(start, end, ast, directiveNames == null ? null : (Identifier[]) directiveNames.toArray(new Identifier[directiveNames.size()]), directiveValues == null ? null : (Expression[]) directiveValues.toArray(new Expression[directiveValues.size()]), action);
	}

	public DeclareStatement(AST ast) {
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
		final Iterator<Identifier> itId = directiveNames.iterator();
		final Iterator<Expression> itExpr = directiveValues.iterator();
		while (itId.hasNext()) {
			final Identifier name = itId.next();
			final Expression value = itExpr.next();
			name.accept(visitor);
			value.accept(visitor);
		}
		body.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		final Iterator<Identifier> itId = directiveNames.iterator();
		final Iterator<Expression> itExpr = directiveValues.iterator();
		while (itId.hasNext()) {
			final Identifier name = itId.next();
			final Expression value = itExpr.next();
			name.traverseTopDown(visitor);
			value.traverseTopDown(visitor);
		}
		body.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		final Iterator<Identifier> itId = directiveNames.iterator();
		final Iterator<Expression> itExpr = directiveValues.iterator();
		while (itId.hasNext()) {
			final Identifier name = itId.next();
			final Expression value = itExpr.next();
			name.traverseBottomUp(visitor);
			value.traverseBottomUp(visitor);
		}
		body.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<DeclareStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("<Directives>\n"); //$NON-NLS-1$
		final Iterator<Identifier> itId = directiveNames.iterator();
		final Iterator<Expression> itExpr = directiveValues.iterator();
		while (itId.hasNext()) {
			final Identifier name = itId.next();
			final Expression value = itExpr.next();
			buffer.append(tab).append(TAB).append(TAB).append("<Name>\n"); //$NON-NLS-1$
			name.toString(buffer, TAB + TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append(TAB).append("</Name>\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append(TAB).append("<Value>\n"); //$NON-NLS-1$
			value.toString(buffer, TAB + TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append(TAB).append("</Value>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</Directives>\n"); //$NON-NLS-1$
		body.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</DeclareStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.DECLARE_STATEMENT;
	}

	/**
	 * @deprecated use #getBody()
	 */
	public Statement getAction() {
		return body;
	}

	/**
	 * @deprecated use {@link #directiveNames()}  
	 */
	public Identifier[] getDirectiveNames() {
		return directiveNames.toArray(new Identifier[directiveNames.size()]);
	}

	/**
	 * @deprecated {@link #directiveValues()}  
	 */
	public Expression[] getDirectiveValues() {
		return directiveValues.toArray(new Expression[directiveValues.size()]);
	}
	
	/**
	 * The list of directive names
	 * 
	 * @return List of directive names
	 */
	public List<Identifier> directiveNames() {
		return directiveNames;
	}

	/**
	 * The list of directive values
	 * 
	 * @return List of directive values
	 */
	public List<Expression> directiveValues() {
		return directiveValues;
	}

	/**
	 * The body of this declare statement
	 * 
	 * @return body of this this declare statement
	 */
	public Statement getBody() {
		return this.body;
	}

	/**
	 * Sets the expression of this unary operation.
	 * 
	 * @param body the new expression node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setBody(Statement body) {
		if (body == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.body;
		preReplaceChild(oldChild, body, BODY_PROPERTY);
		this.body = body;
		postReplaceChild(oldChild, body, BODY_PROPERTY);
	}
	
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == BODY_PROPERTY) {
			if (get) {
				return getBody();
			} else {
				setBody((Statement) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == DIRECTIVE_NAMES_PROPERTY) {
			return directiveNames();
		}
		if (property == DIRECTIVE_VALUES_PROPERTY) {
			return directiveValues();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
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
		final List names = ASTNode.copySubtrees(target, this.directiveNames());
		final List values = ASTNode.copySubtrees(target, this.directiveValues());
		final Statement body = ASTNode.copySubtree(target, getBody());
		
		final DeclareStatement echoSt = new DeclareStatement(this.getStart(), this.getEnd(), target, names, values, body);
		return echoSt;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
