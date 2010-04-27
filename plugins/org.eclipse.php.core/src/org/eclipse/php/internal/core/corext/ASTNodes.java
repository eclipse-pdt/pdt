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
package org.eclipse.php.internal.core.corext;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewriteFlattener;
import org.eclipse.php.internal.core.ast.visitor.ApplyAll;

public class ASTNodes {

	public static final int NODE_ONLY = 0;
	public static final int INCLUDE_FIRST_PARENT = 1;
	public static final int INCLUDE_ALL_PARENTS = 2;

	public static final int WARNING = 1 << 0;
	public static final int ERROR = 1 << 1;
	public static final int PROBLEMS = WARNING | ERROR;

	private static final int CLEAR_VISIBILITY = ~(Modifiers.AccPublic
			| Modifiers.AccProtected | Modifiers.AccPrivate);

	private ASTNodes() {
		// no instance;
	}

	public static String asString(ASTNode node) {
		ASTRewriteFlattener flattener = new ASTRewriteFlattener(null);
		node.accept(flattener);
		return flattener.getResult();
	}

	public static String getQualifier(Identifier name) {
		// if (name.isQualifiedName()) {
		// return ((QualifiedName) name).getQualifier().getFullyQualifiedName();
		// }
		return ""; //$NON-NLS-1$
	}

	/**
	 * Returns true if a node at a given location is a body of a control
	 * statement. Such body nodes are interesting as when replacing them, it has
	 * to be evaluates if a Block is needed instead. E.g.
	 * <code> if (x) do(); -> if (x) { do1(); do2() } </code>
	 * 
	 * @param locationInParent
	 *            Location of the body node
	 * @return Returns true if the location is a body node location of a control
	 *         statement.
	 */
	public static boolean isControlStatementBody(
			StructuralPropertyDescriptor locationInParent) {
		return locationInParent == IfStatement.TRUE_STATEMENT_PROPERTY
				|| locationInParent == IfStatement.FALSE_STATEMENT_PROPERTY
				|| locationInParent == ForStatement.BODY_PROPERTY
				|| locationInParent == WhileStatement.BODY_PROPERTY
				|| locationInParent == DoStatement.BODY_PROPERTY;
	}

	public static boolean needsParentheses(Expression expression) {
		int type = expression.getType();
		return type == ASTNode.INFIX_EXPRESSION
				|| type == ASTNode.CONDITIONAL_EXPRESSION
				|| type == ASTNode.PREFIX_EXPRESSION
				|| type == ASTNode.POSTFIX_EXPRESSION
				|| type == ASTNode.CAST_EXPRESSION
				|| type == ASTNode.INSTANCE_OF_EXPRESSION;
	}

	public static ASTNode getParent(ASTNode node, Class parentClass) {
		do {
			node = node.getParent();
		} while (node != null && !parentClass.isInstance(node));
		return node;
	}

	public static ASTNode getParent(ASTNode node, int nodeType) {
		do {
			node = node.getParent();
		} while (node != null && node.getType() != nodeType);
		return node;
	}

	public static ASTNode findParent(ASTNode node,
			StructuralPropertyDescriptor[][] pathes) {
		for (int p = 0; p < pathes.length; p++) {
			StructuralPropertyDescriptor[] path = pathes[p];
			ASTNode current = node;
			int d = path.length - 1;
			for (; d >= 0 && current != null; d--) {
				StructuralPropertyDescriptor descriptor = path[d];
				if (!descriptor.equals(current.getLocationInParent()))
					break;
				current = current.getParent();
			}
			if (d < 0)
				return current;
		}
		return null;
	}

	public static boolean isParent(ASTNode node, ASTNode parent) {
		Assert.isNotNull(parent);
		do {
			node = node.getParent();
			if (node == parent)
				return true;
		} while (node != null);
		return false;
	}

	public static int getExclusiveEnd(ASTNode node) {
		return node.getStart() + node.getLength();
	}

	public static int getInclusiveEnd(ASTNode node) {
		return node.getStart() + node.getLength() - 1;
	}

	private static int computeIterations(int flags) {
		switch (flags) {
		case NODE_ONLY:
			return 1;
		case INCLUDE_ALL_PARENTS:
			return Integer.MAX_VALUE;
		case INCLUDE_FIRST_PARENT:
			return 2;
		default:
			return 1;
		}
	}

	public static int changeVisibility(int modifiers, int visibility) {
		return (modifiers & CLEAR_VISIBILITY) | visibility;
	}

	/**
	 * Adds flags to the given node and all its descendants.
	 * 
	 * @param root
	 *            The root node
	 * @param flags
	 *            The flags to set
	 */
	public static void setFlagsToAST(ASTNode root, final int flags) {
		if (root == null) {
			return;
		}
		root.accept(new ApplyAll() {
			protected boolean apply(ASTNode node) {
				node.setFlags(node.getFlags() | flags);
				return true;
			}
		});
	}
}
