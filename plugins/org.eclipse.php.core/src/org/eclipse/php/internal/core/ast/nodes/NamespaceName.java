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
 * Represents namespace name:
 * 
 * <pre>e.g.
 * 
 * <pre>
 * MyNamespace;
 * MyProject\Sub\Level;
 * namespace\MyProject\Sub\Level;
 */
public class NamespaceName extends Identifier {

	protected ASTNode.NodeList<Identifier> segments = new ASTNode.NodeList<Identifier>(
			ELEMENTS_PROPERTY);

	/**
	 * Whether the namespace name has '\' prefix, which means it relates to the
	 * global scope
	 */
	private boolean global;

	/**
	 * Whether the namespace name has 'namespace' prefix, which means it relates
	 * to the current namespace scope
	 */
	private boolean current;

	/**
	 * The "namespace" structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor ELEMENTS_PROPERTY = new ChildListPropertyDescriptor(
			NamespaceName.class, "segments", Identifier.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final SimplePropertyDescriptor GLOBAL_PROPERTY = new SimplePropertyDescriptor(
			UseStatementPart.class, "global", Boolean.class, MANDATORY); //$NON-NLS-1$
	public static final SimplePropertyDescriptor CURRENT_PROPERTY = new SimplePropertyDescriptor(
			UseStatementPart.class, "current", Boolean.class, MANDATORY); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(
				2);
		properyList.add(NAME_PROPERTY);
		properyList.add(ELEMENTS_PROPERTY);
		properyList.add(GLOBAL_PROPERTY);
		properyList.add(CURRENT_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public NamespaceName(AST ast) {
		super(ast);
	}

	public NamespaceName(int start, int end, AST ast, Identifier[] segments,
			boolean global, boolean current) {
		super(start, end, ast, buildName(segments, global, current));

		if (segments == null) {
			throw new IllegalArgumentException();
		}
		for (Identifier name : segments) {
			this.segments.add(name);
		}

		this.global = global;
		this.current = current;
	}

	public NamespaceName(int start, int end, AST ast, List segments,
			boolean global, boolean current) {
		super(start, end, ast, buildName((Identifier[]) segments
				.toArray(new Identifier[segments.size()]), global, current));

		if (segments == null) {
			throw new IllegalArgumentException();
		}
		Iterator<Identifier> it = segments.iterator();
		while (it.hasNext()) {
			this.segments.add(it.next());
		}

		this.global = global;
		this.current = current;
	}

	protected static String buildName(Identifier[] segments, boolean global,
			boolean current) {
		StringBuilder buf = new StringBuilder();
		if (global) {
			buf.append('\\');
		} else if (current) {
			buf.append("namespace\\"); //$NON-NLS-1$
		}
		for (int i = 0; i < segments.length; ++i) {
			if (i > 0) {
				buf.append('\\');
			}
			buf.append(segments[i].getName());
		}
		return buf.toString();
	}

	public void childrenAccept(Visitor visitor) {
		for (ASTNode node : this.segments) {
			node.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.segments) {
			node.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.segments) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<NamespaceName"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" global='").append(global).append('\''); //$NON-NLS-1$
		buffer.append(" current='").append(current).append('\''); //$NON-NLS-1$
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.segments) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</NamespaceName>"); //$NON-NLS-1$
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public int getType() {
		return ASTNode.NAMESPACE_NAME;
	}

	/**
	 * Returns whether this namespace name has global context (starts with '\')
	 * 
	 * @return
	 */
	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		preValueChange(GLOBAL_PROPERTY);
		this.global = global;
		postValueChange(GLOBAL_PROPERTY);
	}

	/**
	 * Returns whether this namespace name has current namespace context (starts
	 * with 'namespace')
	 * 
	 * @return
	 */
	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		preValueChange(CURRENT_PROPERTY);
		this.current = current;
		postValueChange(CURRENT_PROPERTY);
	}

	/**
	 * Retrieves names parts of the namespace
	 * 
	 * @return segments. If names list is empty, that means that this namespace
	 *         is global.
	 */
	public List<Identifier> segments() {
		return this.segments;
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
	protected ASTNode clone0(AST target) {
		final List segments = ASTNode.copySubtrees(target, segments());
		final boolean global = isGlobal();
		final boolean current = isCurrent();
		final NamespaceName result = new NamespaceName(this.getStart(), this
				.getEnd(), target, segments, global, current);
		return result;
	}

	@Override
	protected List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	boolean internalGetSetBooleanProperty(SimplePropertyDescriptor property,
			boolean get, boolean value) {
		if (property == GLOBAL_PROPERTY) {
			if (get) {
				return isGlobal();
			} else {
				setGlobal(value);
				return false;
			}
		}
		if (property == CURRENT_PROPERTY) {
			if (get) {
				return isCurrent();
			} else {
				setCurrent(value);
				return false;
			}
		}
		return super.internalGetSetBooleanProperty(property, get, value);
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == ELEMENTS_PROPERTY) {
			return segments();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}
}
