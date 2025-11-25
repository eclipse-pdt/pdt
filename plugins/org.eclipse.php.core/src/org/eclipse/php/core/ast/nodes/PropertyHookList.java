/*******************************************************************************
 * Copyright (c) 2023 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.match.ASTMatcher;
import org.eclipse.php.core.ast.visitor.Visitor;

/**
 * Represents a list of parameter list (in PropertyHook) if exists
 * 
 * e.g.
 * 
 * <pre>
 * ($param1, $param2, )
 * </pre>
 */
public class PropertyHookList extends Statement {

	private final ASTNode.NodeList<PropertyHook> hooks = new ASTNode.NodeList<>(HOOKS_PROPERTY);

	/**
	 * The structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor HOOKS_PROPERTY = new ChildListPropertyDescriptor(
			PropertyHookList.class, "hooks", PropertyHook.class, NO_CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<>(1);
		properyList.add(HOOKS_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public PropertyHookList(AST ast) {
		super(ast);
	}

	public PropertyHookList(int start, int end, AST ast, List<PropertyHook> hooks) {
		super(start, end, ast);

		for (PropertyHook a : hooks) {
			this.hooks.add(a);
		}

	}

	@Override
	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	@Override
	public void childrenAccept(Visitor visitor) {
		for (PropertyHook node : this.hooks) {
			node.accept(visitor);
		}
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (PropertyHook node : this.hooks) {
			node.traverseTopDown(visitor);
		}
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {
		for (PropertyHook node : this.hooks) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<PropertyHookList"); //$NON-NLS-1$
		appendInterval(buffer);

		buffer.append(">\n"); //$NON-NLS-1$

		for (PropertyHook part : this.hooks) {
			part.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</PropertyHookList>\n"); //$NON-NLS-1$
	}

	@Override
	public int getType() {
		return ASTNode.PROPERTY_HOOK_LIST;
	}

	/**
	 * The list of single parts of this 'use' statement
	 * 
	 * @return List of this statement parts
	 */
	public List<PropertyHook> hooks() {
		return this.hooks;
	}

	@Override
	final List<? extends ASTNode> internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == HOOKS_PROPERTY) {
			return hooks();
		}
		return super.internalGetChildListProperty(property);
	}

	/*
	 * Method declared on ASTNode.
	 */
	@Override
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	ASTNode clone0(AST target) {
		final List<PropertyHook> parts = ASTNode.copySubtrees(target, hooks());
		return new PropertyHookList(getStart(), getEnd(), target, parts);
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
