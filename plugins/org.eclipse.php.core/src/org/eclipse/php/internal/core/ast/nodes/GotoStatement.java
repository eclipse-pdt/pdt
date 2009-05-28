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
 * Holds a goto statement. 
 * <pre>e.g.<pre> 
 *goto START;
 */
public class GotoStatement extends Statement {

	private Identifier label;

	/**
	 * The "expression" structural property of this node type.
	 */
	public static final ChildPropertyDescriptor LABEL_PROPERTY = 
		new ChildPropertyDescriptor(GotoStatement.class, "label", Identifier.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	
	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(1);
		propertyList.add(LABEL_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}	

	public GotoStatement(int start, int end, AST ast, Identifier label) {
		super(start, end, ast);

		if (label == null) {
			throw new IllegalArgumentException();
		}
		setLabel(label);
	}

	public GotoStatement(AST ast) {
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
		label.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		label.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		label.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<GotoStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		label.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</GotoStatement>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.GOTO_STATEMENT;
	}

	/**
	 * Returns the label of this goto label
	 * 
	 * @return the label label
	 */ 
	public Identifier getLabel() {
		return this.label;
	}
		
	/**
	 * Sets the label of this goto label
	 * 
	 * @param label of this goto label
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setLabel(Identifier label) {
		if (label == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.label;
		preReplaceChild(oldChild, label, LABEL_PROPERTY);
		this.label = label;
		postReplaceChild(oldChild, label, LABEL_PROPERTY);
	}
	
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == LABEL_PROPERTY) {
			if (get) {
				return getLabel();
			} else {
				setLabel((Identifier) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
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
		final Identifier label = ASTNode.copySubtree(target, getLabel());
		final GotoStatement result = new GotoStatement(this.getStart(), this.getEnd(), target, label);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
