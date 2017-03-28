/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.internal.core.search.IOccurrencesFinder;
import org.eclipse.php.internal.core.search.IOccurrencesFinder.OccurrenceLocation;
import org.eclipse.php.internal.core.search.OccurrencesFinderFactory;

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
	 * Find all nodes connected to the given name node. If the node has a
	 * binding then all nodes connected to this binding are returned. If the
	 * node has no binding, then all nodes that also miss a binding and have the
	 * same name are returned.
	 * 
	 * @param root
	 *            The root of the AST tree to search
	 * @param name
	 *            The node to find linked nodes for
	 * @return Return
	 */
	public static OccurrenceLocation[] findByNode(Program root, ASTNode node) {
		ASTNode selectedNode = node;
		if (selectedNode != null && selectedNode.getType() == ASTNode.VARIABLE) {
			final Expression name = ((Variable) selectedNode).getName();
			if (name instanceof Identifier) {
				selectedNode = name;
			}
		}

		OccurrenceLocation[] locations = null;
		int type = PhpElementConciliator.concile(selectedNode);

		IOccurrencesFinder finder = OccurrencesFinderFactory
				.getOccurrencesFinder(type);
		if (finder != null) {
			if (finder.initialize(root, selectedNode) == null) {
				locations = finder.getOccurrences();
			}
		}

		return locations;
	}

	/**
	 * Find all nodes connected to the given name node. If the node has a
	 * binding then all nodes connected to this binding are returned. If the
	 * node has no binding, then all nodes that also miss a binding and have the
	 * same name are returned.
	 * 
	 * @param root
	 *            The root of the AST tree to search
	 * @param name
	 *            The node to find linked nodes for
	 * @return Return
	 */
	public static OccurrenceLocation[] findByNode(Program root, ASTNode[] nodes) {

		List<OccurrenceLocation> locationList = new ArrayList<OccurrenceLocation>();
		for (ASTNode selectedNode : nodes) {
			OccurrenceLocation[] locations = findByNode(root, selectedNode);
			if (locations != null) {
				locationList.addAll(Arrays.asList(locations));
			}
		}

		return locationList
				.toArray(new OccurrenceLocation[locationList.size()]);
	}

	public static Identifier[] findByBinding(ASTNode root,
			IVariableBinding binding) {
		ArrayList<Identifier> res = new ArrayList<Identifier>();
		BindingFinder nodeFinder = new BindingFinder(binding, res);
		root.accept(nodeFinder);
		return res.toArray(new Identifier[res.size()]);
	}

	private static class BindingFinder extends AbstractVisitor {

		private IBinding fBinding;
		private ArrayList<Identifier> fResult;

		public BindingFinder(IBinding binding, ArrayList<Identifier> result) {
			fBinding = binding;
			fResult = result;
		}

		public boolean visit(Identifier node) {
			IBinding binding = node.resolveBinding();
			if (binding == null) {
				return false;
			}

			if (fBinding.equals(binding)) {
				fResult.add(node);
			} else if (binding.getKind() != fBinding.getKind()) {
				return false;
			} else if (binding.getKind() == IBinding.METHOD) {
				IMethodBinding curr = (IMethodBinding) binding;
				IMethodBinding methodBinding = (IMethodBinding) fBinding;
				if (methodBinding.overrides(curr)
						|| curr.overrides(methodBinding)) {
					fResult.add(node);
				}
			}
			return false;
		}

		// private static IBinding getDeclaration(IBinding binding) {
		// if (binding instanceof ITypeBinding) {
		// return ((ITypeBinding) binding).getTypeDeclaration();
		// } else if (binding instanceof IMethodBinding) {
		// IMethodBinding methodBinding = (IMethodBinding) binding;
		// if (methodBinding.isConstructor()) { // link all constructors with
		// their type
		// return methodBinding.getDeclaringClass().getTypeDeclaration();
		// } else {
		// return methodBinding.getMethodDeclaration();
		// }
		// } else if (binding instanceof IVariableBinding) {
		// return ((IVariableBinding) binding).getVariableDeclaration();
		// }
		// return binding;
		// }
	}

}
