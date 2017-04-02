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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;

/**
 * This visitor locates the identifiers we need to change given a global
 * variable name
 * 
 * @author Roy, 2007
 */
public class RenameGlobalVariable extends AbstractRename {

	private static final String RENAME_GLOBAL_VARIABLE = PhpRefactoringCoreMessages
			.getString("RenameGlobalVariableName.0"); //$NON-NLS-1$

	/**
	 * Holds if the context was changed and we should not change the
	 * identifier's name
	 */
	private boolean isGlobalScope = true;

	public RenameGlobalVariable(IFile file, String oldName, String newName, boolean searchTextual) {
		super(file, oldName, newName, searchTextual);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.refactoring.core.rename.logic.AbstractRename#getTextualSearchPattern()
	 */
	@Override
	protected String getTextualSearchPattern() {
		return "(\\$" + oldName + "|'" + oldName + "'|\"" + oldName + "\")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	/**
	 * class declaration
	 */
	public boolean visit(ClassDeclaration classDeclaration) {
		setNonGlobalScope();
		return true;
	}

	public void endVisit(ClassDeclaration classDeclaration) {
		setGlobalScope();
	}

	public boolean visit(TraitDeclaration traitDeclaration) {
		setNonGlobalScope();
		return true;
	}

	public void endVisit(TraitDeclaration traitDeclaration) {
		setGlobalScope();
	}

	/**
	 * change the name of the function
	 */
	public boolean visit(FunctionDeclaration functionDeclaration) {
		setNonGlobalScope();
		return true;

	}

	public void endVisit(FunctionDeclaration functionDeclaration) {
		setGlobalScope();
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		setNonGlobalScope();
		return true;
	}

	public void endVisit(InterfaceDeclaration interfaceDeclaration) {
		setGlobalScope();
	}

	public boolean visit(FieldsDeclaration fieldDeclaration) {
		setNonGlobalScope();
		return true;
	}

	public void endVisit(FieldsDeclaration fieldsDeclaration) {
		setGlobalScope();
	}

	/**
	 * Rename $a on global references: $a = 5;
	 */
	public boolean visit(Variable variable) {
		if ((variable.isDollared() || org.eclipse.php.internal.core.corext.ASTNodes.isQuotedDollaredCurlied(variable))
				&& variable.getName() instanceof Identifier) {
			Identifier identifier = (Identifier) variable.getName();
			if (oldName.equals(identifier.getName()) && isGlobalScope) {
				addChange(identifier);
			}
		}
		return true;
	}

	/**
	 * Rename $a on global references (on function/methods) : ...global $a;$a =
	 * 5;...
	 */
	public boolean visit(GlobalStatement globalStatement) {
		final List<Variable> variables = globalStatement.variables();
		for (final Variable variable : variables) {
			if (variable.isDollared()) {
				Identifier identifier = (Identifier) variable.getName();
				if (oldName.equals(identifier.getName())) {
					addChange(identifier);
					setGlobalScope();
				}
			}
		}
		return false;
	}

	/**
	 * Rename $GLOBALS['variableName'] occurrences
	 */
	public boolean visit(ArrayAccess arrayAccess) {
		// check the case of $GLOBALS['var']
		final Expression variableName = arrayAccess.getName();
		if (variableName.getType() == ASTNode.VARIABLE) {
			final Variable var = (Variable) variableName;
			if ((var.isDollared() || org.eclipse.php.internal.core.corext.ASTNodes.isQuotedDollaredCurlied(var))
					&& var.getName() instanceof Identifier) {
				final Identifier id = (Identifier) var.getName();
				if (id.getName().equals("GLOBALS") || id.getName().equals("_GLOBALS")) { //$NON-NLS-1$ //$NON-NLS-2$
					final Expression index = arrayAccess.getIndex();
					if (index.getType() == ASTNode.SCALAR) {
						Scalar scalar = (Scalar) index;
						final String stringValue = scalar.getStringValue();
						if (isScalarNeedChange(scalar, stringValue)) {
							addChange(scalar);
							return false;
						}
					}
				}
			}
		}

		// else - just do the regular refactoring
		return true;
	}

	private boolean isScalarNeedChange(Scalar scalar, final String stringValue) {
		return scalar.getScalarType() == Scalar.TYPE_STRING && (stringValue.equals(oldName)
				|| stringValue.length() > 2 && stringValue.substring(1, stringValue.length() - 1).equals(oldName));
	}

	public String getRenameDescription() {
		return RenameGlobalVariable.RENAME_GLOBAL_VARIABLE;
	}

	private void setNonGlobalScope() {
		isGlobalScope = false;
	}

	private void setGlobalScope() {
		isGlobalScope = true;
	}

}
