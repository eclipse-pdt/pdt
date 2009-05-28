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
 * Represents an indirect reference to a variable.
 * <pre>e.g.<pre> $$a
 * $$foo()
 */
public class ReflectionVariable extends Variable {

	/**
	 * The structural property of this node type.
	 */
	public static final ChildPropertyDescriptor NAME_PROPERTY = 
		new ChildPropertyDescriptor(ReflectionVariable.class, "name", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final SimplePropertyDescriptor DOLLARED_PROPERTY = 
		new SimplePropertyDescriptor(ReflectionVariable.class, "isDollared", Boolean.class, OPTIONAL); //$NON-NLS-1$
	
	/**
	 * @return the name PROPERTY
	 */
	public ChildPropertyDescriptor getNameProperty() {
		return ReflectionVariable.NAME_PROPERTY;
	}

	/**
	 * @return the DOLLARED property
	 */
	public SimplePropertyDescriptor getDollaredProperty() {
		return ReflectionVariable.DOLLARED_PROPERTY;
	}
	
	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(2);
		propertyList.add(NAME_PROPERTY);
		propertyList.add(DOLLARED_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}
	
	public ReflectionVariable(int start, int end, AST ast, Expression variable) {
		super(start, end, ast, variable);
	}

	public ReflectionVariable(AST ast) {
		super(ast);
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	
	
	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ReflectionVariable"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		getName().toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</ReflectionVariable>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.REFLECTION_VARIABLE;
	}
	
	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
  
	@Override
	ASTNode clone0(AST target) {
		final Expression expr = ASTNode.copySubtree(target, getName());
		final ReflectionVariable result = new ReflectionVariable(getStart(), getEnd(), target, expr);
		return result;
	}
	
	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
	
}
