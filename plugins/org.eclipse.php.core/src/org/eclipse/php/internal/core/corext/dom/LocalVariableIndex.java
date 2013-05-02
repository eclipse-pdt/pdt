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
package org.eclipse.php.internal.core.corext.dom;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;

public class LocalVariableIndex extends AbstractVisitor {

	private int fTopIndex;
	// in case we are in the program scope
	// we don't want to descend into function/class/interface scope
	private static boolean isProgramScope = false;
	private final Set<String> variablesSet = new HashSet<String>();

	/**
	 * Computes the maximum number of local variable declarations in the given
	 * body declaration.
	 * 
	 * @param node
	 *            the body declaration. Must either be a method declaration or
	 *            an initializer.
	 * @return the maximum number of local variables
	 */
	public static int perform(ASTNode node) {
		Assert.isTrue(node != null);
		switch (node.getType()) {
		case ASTNode.METHOD_DECLARATION:
			isProgramScope = false;
			return internalPerform(((MethodDeclaration) node).getFunction());
		case ASTNode.FUNCTION_DECLARATION:
			isProgramScope = false;
			return internalPerform((FunctionDeclaration) node);
		case ASTNode.PROGRAM:
			isProgramScope = true;
			return internalPerform((Program) node);
		default:
			Assert.isTrue(false);
		}
		return -1;
	}

	private static int internalPerform(ASTNode node) {
		LocalVariableIndex counter = new LocalVariableIndex();
		node.accept(counter);
		return counter.fTopIndex;
	}

	/**
	 * Insert to the variables Name set each variable that is first encountered
	 * in the flow
	 */
	public boolean visit(Variable variable) {
		Expression name = variable.getName();
		if (variable.isDollared() && name.getType() == ASTNode.IDENTIFIER) {
			String variableName = ((Identifier) name).getName();
			if (!variableName.equalsIgnoreCase("this") //$NON-NLS-1$
					&& !variablesSet.contains(variableName)) {
				variablesSet.add(variableName);
				handleVariableBinding();
			}
		}
		return true;

	}

	public boolean visit(FunctionDeclaration function) {
		return !isProgramScope;
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		return !isProgramScope;
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		return !isProgramScope;
	}

	private void handleVariableBinding() {
		// TODO - check if the workaround works properly
		// fTopIndex = Math.max(fTopIndex, binding.getVariableId());
		fTopIndex = variablesSet.size();
	}
}
