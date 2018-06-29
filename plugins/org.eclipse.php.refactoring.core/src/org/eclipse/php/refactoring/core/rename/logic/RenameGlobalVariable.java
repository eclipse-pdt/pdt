/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename.logic;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.refactoring.core.PHPRefactoringCoreMessages;

/**
 * This visitor locates the identifiers we need to change given a global
 * variable name
 * 
 * @author Roy, 2007
 */
public class RenameGlobalVariable extends AbstractRename {

	private static final String RENAME_GLOBAL_VARIABLE = PHPRefactoringCoreMessages
			.getString("RenameGlobalVariableName.0"); //$NON-NLS-1$

	/**
	 * Holds if the context was changed and we should not change the identifier's
	 * name
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
	@Override
	public boolean visit(ClassDeclaration classDeclaration) {
		setNonGlobalScope();
		return true;
	}

	@Override
	public void endVisit(ClassDeclaration classDeclaration) {
		setGlobalScope();
	}

	@Override
	public boolean visit(TraitDeclaration traitDeclaration) {
		setNonGlobalScope();
		return true;
	}

	@Override
	public void endVisit(TraitDeclaration traitDeclaration) {
		setGlobalScope();
	}

	/**
	 * change the name of the function
	 */
	@Override
	public boolean visit(FunctionDeclaration functionDeclaration) {
		setNonGlobalScope();
		return true;

	}

	@Override
	public void endVisit(FunctionDeclaration functionDeclaration) {
		setGlobalScope();
	}

	@Override
	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		setNonGlobalScope();
		return true;
	}

	@Override
	public void endVisit(InterfaceDeclaration interfaceDeclaration) {
		setGlobalScope();
	}

	@Override
	public boolean visit(FieldsDeclaration fieldDeclaration) {
		setNonGlobalScope();
		return true;
	}

	@Override
	public void endVisit(FieldsDeclaration fieldsDeclaration) {
		setGlobalScope();
	}

	/**
	 * Rename $a on global references: $a = 5;
	 */
	@Override
	public boolean visit(Variable variable) {
		if ((variable.isDollared() || ASTNodes.isQuotedDollaredCurlied(variable))
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
	@Override
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
	@Override
	public boolean visit(ArrayAccess arrayAccess) {
		// check the case of $GLOBALS['var']
		final Expression variableName = arrayAccess.getName();
		if (variableName.getType() == ASTNode.VARIABLE) {
			final Variable var = (Variable) variableName;
			if ((var.isDollared() || ASTNodes.isQuotedDollaredCurlied(var)) && var.getName() instanceof Identifier) {
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
		if (stringValue.length() == 0) {
			return false;
		}
		return scalar.getScalarType() == Scalar.TYPE_STRING && (stringValue.equals(oldName)
				|| stringValue.length() > 2 && stringValue.substring(1, stringValue.length() - 1).equals(oldName));
	}

	@Override
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
