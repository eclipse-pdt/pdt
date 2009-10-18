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

package org.eclipse.php.internal.ui.editor;

import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.IBinding;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.wst.jsdt.core.dom.SimpleName;

/**
 * Semantic token
 */
public final class SemanticToken {

	/** AST node */
	private Identifier fNode;
	private Expression fLiteral;

	/** Binding */
	private IBinding fBinding;
	/** Is the binding resolved? */
	private boolean fIsBindingResolved = false;

	/** AST root */
	private Program fRoot;
	private boolean fIsRootResolved = false;

	/**
	 * @return Returns the binding, can be <code>null</code>.
	 */
	public IBinding getBinding() {
		if (!fIsBindingResolved) {
			fIsBindingResolved = true;
			if (fNode != null)
				fBinding = fNode.resolveBinding();
		}

		return fBinding;
	}

	/**
	 * @return the AST node (a {@link SimpleName})
	 */
	public Identifier getNode() {
		return fNode;
	}

	/**
	 * @return the AST node (a
	 *         <code>Boolean-, Character- or NumberLiteral</code>)
	 */
	public Expression getLiteral() {
		return fLiteral;
	}

	/**
	 * @return the AST root
	 */
	public Program getRoot() {
		if (!fIsRootResolved) {
			fIsRootResolved = true;
			fRoot = (Program) (fNode != null ? fNode : fLiteral).getRoot();
		}

		return fRoot;
	}

	/**
	 * Update this token with the given AST node.
	 * <p>
	 * NOTE: Allowed to be used by {@link SemanticHighlightingReconciler} only.
	 * </p>
	 * 
	 * @param node
	 *            the AST simple name
	 */
	void update(Identifier node) {
		clear();
		fNode = node;
	}

	/**
	 * Update this token with the given AST node.
	 * <p>
	 * NOTE: Allowed to be used by {@link SemanticHighlightingReconciler} only.
	 * </p>
	 * 
	 * @param literal
	 *            the AST literal
	 */
	void update(Expression literal) {
		clear();
		fLiteral = literal;
	}

	/**
	 * Clears this token.
	 * <p>
	 * NOTE: Allowed to be used by {@link SemanticHighlightingReconciler} only.
	 * </p>
	 */
	void clear() {
		fNode = null;
		fLiteral = null;
		fBinding = null;
		fIsBindingResolved = false;
		fRoot = null;
		fIsRootResolved = false;
	}
}
