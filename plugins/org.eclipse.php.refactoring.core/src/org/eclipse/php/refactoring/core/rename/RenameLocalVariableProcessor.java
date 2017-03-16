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
package org.eclipse.php.refactoring.core.rename;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.rename.logic.RenameLocalVariable;

/**
 * Local Variable processor refactoring processor
 * 
 * @author Roy, 2007
 */
public class RenameLocalVariableProcessor extends
		AbstractRenameProcessor<IFile> implements ITextUpdating {

	private static final String RENAME_IS_PROCESSING = PhpRefactoringCoreMessages
			.getString("RenameLocalVariableProcessor.0"); //$NON-NLS-1$
	private static final String CREATING_MODIFICATIONS_LABEL = PhpRefactoringCoreMessages
			.getString("RenameLocalVariableProcessor.1"); //$NON-NLS-1$
	private static final String LOCAL_VARIABLE_IS_USED = PhpRefactoringCoreMessages
			.getString("RenameLocalVariableProcessor.2"); //$NON-NLS-1$
	private static final String ID_RENAME_FUNCTION = "php.refactoring.ui.rename.localVariable"; //$NON-NLS-1$
	protected static final String ATTRIBUTE_TEXTUAL_MATCHES = "textual"; //$NON-NLS-1$
	public static final String RENAME_FUNCTION_PROCESSOR_NAME = PhpRefactoringCoreMessages
			.getString("RenameLocalVariableProcessor.3"); //$NON-NLS-1$

	/**
	 * The original identifier node we want to rename
	 */
	private final Variable identifier;

	/**
	 * holds weather or not we want to change also the in-line text
	 */
	private boolean isUpdateTextualMatches;
	private final FunctionDeclaration functionDeclaration;

	public RenameLocalVariableProcessor(IFile operatedFile, ASTNode locateNode) {
		super(operatedFile);

		if (locateNode instanceof Identifier) {
			this.identifier = (Variable) locateNode.getParent();
		} else {
			this.identifier = (Variable) locateNode;
		}

		ASTNode parent = identifier.getParent();
		while (parent.getType() != ASTNode.FUNCTION_DECLARATION) {
			parent = parent.getParent();
		}

		assert parent.getType() == ASTNode.FUNCTION_DECLARATION;
		this.functionDeclaration = (FunctionDeclaration) parent;
	}

	/**
	 * Derive the change
	 */
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		try {
			pm.beginTask(RenameLocalVariableProcessor.RENAME_IS_PROCESSING,
					participantFiles.size());
			pm.setTaskName(RenameLocalVariableProcessor.CREATING_MODIFICATIONS_LABEL);

			if (pm.isCanceled())
				throw new OperationCanceledException();

			// get target parameters
			final String newElementName = getNewElementName();
			final RenameLocalVariable rename = new RenameLocalVariable(
					resource, ((Identifier) identifier.getName()).getName(),
					newElementName, getUpdateTextualMatches());

			// aggregate the changes identifiers
			this.functionDeclaration.accept(rename);

			if (pm.isCanceled())
				throw new OperationCanceledException();

			pm.worked(1);
			TextFileChange change = null;
			if (rename.hasChanges()) {
				// create the change
				change = acquireChange(resource,
						getProgram(functionDeclaration));
				rename.updateChange(change);
			}
			return change;

		} finally {
			pm.done();
		}
	}

	private Program getProgram(ASTNode node) {
		while (node.getParent() != null) {
			node = node.getParent();
		}
		return node.getType() == ASTNode.PROGRAM ? (Program) node : null;
	}

	public Object[] getElements() {
		return new Object[] { identifier };
	}

	public String getIdentifier() {
		return ID_RENAME_FUNCTION;
	}

	public String getProcessorName() {
		return RENAME_FUNCTION_PROCESSOR_NAME;
	}

	public Object getNewElement() {
		return getNewElementName();
	}

	public String getCurrentElementName() {
		return ((Identifier) identifier.getName()).getName();
	}

	public boolean canEnableTextUpdating() {
		return true;
	}

	public String getCurrentElementQualifier() {
		return ((Identifier) identifier.getName()).getName();
	}

	public boolean getUpdateTextualMatches() {
		return isUpdateTextualMatches;
	}

	public void setUpdateTextualMatches(boolean update) {
		isUpdateTextualMatches = update;
	}

	public RefactoringStatus getRefactoringStatus(IFile key, Program program) {
		if (PhpElementConciliator.localVariableAlreadyExists(
				this.functionDeclaration, getNewElementName())) {
			final String message = MessageFormat.format(
					RenameLocalVariableProcessor.LOCAL_VARIABLE_IS_USED,
					new Object[] { resource.getName() });
			return RefactoringStatus.createWarningStatus(message);
		}
		return null;
	}
}
