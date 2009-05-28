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
 * Represents a global statement 
 * <pre>e.g.<pre> global $a
 * global $a, $b
 * global ${foo()->bar()},
 * global $$a 
 */
public class GlobalStatement extends Statement {

	private final ASTNode.NodeList<Variable> variables = new ASTNode.NodeList<Variable>(VARIABLES_PROPERTY);
	
	/**
	 * The "variables" structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor VARIABLES_PROPERTY = 
		new ChildListPropertyDescriptor(GlobalStatement.class, "variables", Variable.class, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(1);
		properyList.add(VARIABLES_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	private GlobalStatement(int start, int end, AST ast, Variable[] variables) {
		super(start, end, ast);

		if (variables == null) {
			throw new IllegalArgumentException();
		}
		for (Variable variable : variables) {
			this.variables.add(variable);
		}
	}

	public GlobalStatement(int start, int end, AST ast, List variables) {
		this(start, end, ast, variables == null ? null : (Variable[]) variables.toArray(new Variable[variables.size()]));
	}

	public GlobalStatement(AST ast) {
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
		for (ASTNode node : this.variables) {
			node.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (ASTNode node : this.variables) {
				node.traverseTopDown(visitor);
			}
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.variables) {
			node.traverseBottomUp(visitor);
		}
		visitor.visit(this);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<GlobalStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.variables) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</GlobalStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.GLOBAL_STATEMENT;
	}

	/**
	 * @deprecated use {@link #variables()}
	 */
	public Variable[] getVariables() {
		return variables.toArray(new Variable[this.variables.size()]);
	}
	
	/**
	 * @return the variables component of the global statement
	 */
	public List<Variable> variables() {
		return this.variables;
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
		final List variables = ASTNode.copySubtrees(target, variables());
		GlobalStatement result = new GlobalStatement(this.getStart(), this.getEnd(), target, variables);
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
		if (property == VARIABLES_PROPERTY) {
			return variables();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}	
}
