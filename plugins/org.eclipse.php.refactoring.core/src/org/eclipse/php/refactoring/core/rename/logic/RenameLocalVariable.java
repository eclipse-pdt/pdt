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
import org.eclipse.dltk.core.IMethod;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;

/**
 * This visitor locates the identifiers to change given a local variable name
 * 
 * @author Roy, 2007
 */
public class RenameLocalVariable extends AbstractRename {

	private static final String RENAME_LOCAL_VARIABLE = PhpRefactoringCoreMessages
			.getString("RenameLocalVariableName.0"); //$NON-NLS-1$

	private boolean isGlobalScope = false;

	private boolean beforeIfStatement;

	private boolean searchTextual;

	public RenameLocalVariable(IFile file, String oldName, String newName, boolean searchTextual) {
		super(file, oldName, newName, searchTextual);
		this.searchTextual = searchTextual;
	}

	public boolean visit(FunctionDeclaration function) {
		boolean result = super.visit(function);
		// Searching for matching text at the program level.
		if (this.searchTextual) {
			searchTextualOccurrences(function.getProgramRoot());
			return result;
		}
		IMethod method = ModelUtils.getFunctionMethod(function);
		if (method != null) {
			PHPDocBlock doc = ModelUtils.getPHPDoc(method);
			if (doc != null) {
				PHPDocTag[] tags = doc.getTags();
				for (PHPDocTag tag : tags) {
					if (tag.isValidMethodDescriptorTag()
							&& tag.getVariableReference().getName().equals("$" + oldName)) { //$NON-NLS-1$
						// add all variable references, even if they are
						// duplicated in this PHPDocBlock
						addChange(tag.getVariableReference().sourceStart() + 1, getRenameDescription());
					}
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.
	 * core.ast.nodes.Variable)
	 */
	public boolean visit(Variable variable) {
		if (isGlobalVariable(variable, true)) {
			addChange((Identifier) variable.getName());
		}
		return true;
	}

	/**
	 * @param variable
	 */
	private boolean isGlobalVariable(Variable variable, boolean checkInQuotes) {
		if (variable.isDollared()
				|| (checkInQuotes && org.eclipse.php.internal.core.corext.ASTNodes.isQuotedDollaredCurlied(variable))) {
			final Expression variableName = variable.getName();
			if (variableName instanceof Identifier) {
				Identifier identifier = (Identifier) variableName;
				if (identifier.getName().equals(oldName) && !isGlobalScope) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean visit(IfStatement ifStatement) {
		beforeIfStatement = isGlobalScope;
		return true;
	}

	public void endVisit(IfStatement ifStatement) {
		boolean afterIfStatement = isGlobalScope; // then at the end of the if
		// statement

		// if the scope was changed, give the conservative result. i.e. the
		// variable is still local
		if (beforeIfStatement != afterIfStatement) {
			isGlobalScope = false;
		}
	}

	public boolean visit(GlobalStatement globalStatement) {
		final List<Variable> variables = globalStatement.variables();
		for (Variable variable : variables) {
			if (isGlobalVariable(variable, false)) {
				isGlobalScope = true;
				break;
			}
		}
		return true;
	}

	public String getRenameDescription() {
		return RenameLocalVariable.RENAME_LOCAL_VARIABLE;
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
}
