/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.IBinding;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;

/**
 * Find all nodes connected to a given binding or node. e.g. Declaration of a
 * field and all references. For types this includes also the constructor
 * declaration, for methods also overridden methods or methods overriding (if
 * existing in the same AST), for constructors also the type and all other
 * constructors.
 */
public class LinkedNodeFinder {

	private LinkedNodeFinder() {
	}

	/**
	 * Find all nodes connected to the given binding. e.g. Declaration of a field
	 * and all references. For types this includes also the constructor declaration,
	 * for methods also overridden methods or methods overriding (if existing in the
	 * same AST)
	 * 
	 * @param root
	 *            The root of the AST tree to search
	 * @param binding
	 *            The binding of the searched nodes
	 * @return Return
	 */
	public static Identifier[] findByBinding(ASTNode root, IBinding binding) {
		List<Identifier> res = new ArrayList<>();
		BindingFinder nodeFinder = new BindingFinder(binding, res);
		root.accept(nodeFinder);
		return res.toArray(new Identifier[res.size()]);
	}

	/**
	 * Find all nodes connected to the given name node. If the node has a binding
	 * then all nodes connected to this binding are returned. If the node has no
	 * binding, then all nodes that also miss a binding and have the same name are
	 * returned.
	 * 
	 * @param root
	 *            The root of the AST tree to search
	 * @param name
	 *            The node to find linked nodes for
	 * @return Return
	 */
	public static Identifier[] findByNode(ASTNode root, Identifier name) {
		IBinding binding = name.resolveBinding();
		if (binding != null) {
			return findByBinding(root, binding);
		}
		return new Identifier[] { name };
	}

	private static class BindingFinder extends AbstractVisitor {

		private IModelElement fBinding;
		private List<Identifier> fResult;

		public BindingFinder(IBinding binding, List<Identifier> result) {
			fBinding = getDeclaration(binding);
			fResult = result;
		}

		@Override
		public boolean visit(Identifier node) {
			IBinding binding = node.resolveBinding();
			if (binding == null || fBinding == null) {
				return false;
			}

			if (fBinding.equals(binding.getPHPElement())) {
				fResult.add(node);
			}
			return false;
		}

		private static IModelElement getDeclaration(IBinding binding) {
			return binding.getPHPElement();
		}
	}
}
