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
import org.eclipse.php.core.ast.nodes.Scalar;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;

/**
 * Rename visitor for constants
 * 
 * @author Roy, 2007
 */
public class RenameGlobalConstant extends AbstractRename {

	private static final String RENAME_CONSTANT = PhpRefactoringCoreMessages
			.getString("RenameDefinedName.0"); //$NON-NLS-1$
	private boolean isCaseSenstive = true;

	public RenameGlobalConstant(IFile file, String oldName, String newName,
			boolean searchTextual) {
		super(file, oldName, newName, searchTextual);
	}

	// /**
	// * finds the define declaration
	// */
	// public boolean visit(FunctionInvocation functionInvocation) {
	// final Expression functionName =
	// functionInvocation.getFunctionName().getFunctionName();
	// if (functionName.getType() == ASTNode.IDENTIFIER &&
	// functionInvocation.getParent().getType() == ASTNode.EXPRESSION_STATEMENT)
	// {
	// final Identifier identifier = (Identifier) functionName;
	//			if ("define".equalsIgnoreCase(identifier.getName()) && functionInvocation.getParameters() != null && functionInvocation.getParameters().length != 0) { //$NON-NLS-1$
	// final Expression expression = functionInvocation.getParameters()[0];
	// Expression caseSensitive = null;
	//
	// if (functionInvocation.getParameters().length == 3) {
	// caseSensitive = functionInvocation.getParameters()[2];
	// if (expression.getType() == ASTNode.SCALAR) {
	// String value = ((Scalar) caseSensitive).getStringValue();
	//						if ("true".equalsIgnoreCase(value)) { //$NON-NLS-1$
	// isCaseSenstive = false;
	// }
	// }
	// }
	//
	// if (expression.getType() == ASTNode.SCALAR) {
	// Scalar scalar = (Scalar) expression;
	// if (scalar.getStringValue().length() > 2 &&
	// scalar.getStringValue().charAt(0) == '"') {
	// if (isCaseSenstive) {
	// if (oldName.equals(scalar.getStringValue().substring(1,
	// scalar.getStringValue().length() - 1))) {
	// addChange(scalar);
	// }
	// } else {
	// if (oldName.equalsIgnoreCase(scalar.getStringValue().substring(1,
	// scalar.getStringValue().length() - 1))) {
	// addChange(scalar);
	// }
	// }
	// }
	// }
	// }
	// }
	// return true;
	// }

	/**
	 * Search for the constant
	 */
	/**
	 * Search for the constant
	 */
	public boolean visit(Scalar scalar) {
		String stringValue = scalar.getStringValue();
		if (stringValue.length() > 2
				&& (stringValue.charAt(0) == '\'' || stringValue.charAt(0) == '"')) {
			stringValue = stringValue.substring(1, stringValue.length() - 1);
		}
		if (isCaseSenstive) {
			if (scalar.getScalarType() == Scalar.TYPE_STRING
					&& stringValue != null && stringValue.equals(oldName)) {
				addChange(scalar);
			}
		} else {
			if (scalar.getScalarType() == Scalar.TYPE_STRING
					&& stringValue != null
					&& stringValue.equalsIgnoreCase(oldName)) {
				addChange(scalar);
			}
		}
		return false;
	}

	public String getRenameDescription() {
		return RenameGlobalConstant.RENAME_CONSTANT;
	}
}
