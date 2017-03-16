/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename.logic;

import org.eclipse.core.resources.IFile;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;

/**
 * This visitor locates the identifiers to change given a global function name
 * 
 * @author Roy, 2007
 */
public class RenameFunction extends AbstractRename {

	private static final String RENAME_FUNCTION = PhpRefactoringCoreMessages
			.getString("RenameFunctionName.0"); //$NON-NLS-1$

	public RenameFunction(IFile file, String oldName, String newName,
			boolean searchTextual) {
		super(file, oldName, newName, searchTextual);
	}

	/**
	 * on class method declaration, we skip the function declaration name
	 */
	public boolean visit(MethodDeclaration methodDeclaration) {
		Block body = methodDeclaration.getFunction().getBody();
		if (body != null) {
			body.accept(this);
		}
		return false;
	}

	/**
	 * change the name of the function
	 */
	public boolean visit(FunctionDeclaration functionDeclaration) {
		assert functionDeclaration.getParent().getType() != ASTNode.METHOD_DECLARATION;

		// check the function name
		if (oldName.equalsIgnoreCase(functionDeclaration.getFunctionName()
				.getName())) {
			addChange(functionDeclaration.getFunctionName());
		}

		return true;
	}

	/**
	 * skip static call invocation, and add to changes list the global calls
	 */
	public boolean visit(FunctionInvocation functionInvocation) {
		final Expression functionName = functionInvocation.getFunctionName()
				.getName();
		final int invocationParent = functionInvocation.getParent().getType();
		if (functionName instanceof Identifier
				&& invocationParent != ASTNode.METHOD_INVOCATION
				&& invocationParent != ASTNode.STATIC_METHOD_INVOCATION) {
			final Identifier identifier = (Identifier) functionName;
			if (oldName.equalsIgnoreCase(identifier.getName())) {
				addChange(identifier);
			}
		}

		return true;
	}

	public String getRenameDescription() {
		return RenameFunction.RENAME_FUNCTION;
	}
}
