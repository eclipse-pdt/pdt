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
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.rename.logic.RenameGlobalConstant;

/**
 * Rename a constant processor
 * 
 * @author Roy, 2007
 */
public class RenameGlobalConstantProcessor extends
		AbstractRenameProcessor<IFile> implements ITextUpdating {

	private static final String RENAME_IS_PROCESSING = PhpRefactoringCoreMessages
			.getString("RenameDefinedProcessor.0"); //$NON-NLS-1$
	private static final String CREATING_MODIFICATIONS_LABEL = PhpRefactoringCoreMessages
			.getString("RenameDefinedProcessor.1"); //$NON-NLS-1$
	private static final String CONSTANT_IS_USED = PhpRefactoringCoreMessages
			.getString("RenameDefinedProcessor.2"); //$NON-NLS-1$
	private static final String ID_RENAME_CONSTANT = "php.refactoring.ui.rename.constant"; //$NON-NLS-1$
	protected static final String ATTRIBUTE_TEXTUAL_MATCHES = "textual"; //$NON-NLS-1$
	public static final String RENAME_CONSTANT_PROCESSOR_NAME = PhpRefactoringCoreMessages
			.getString("RenameDefinedProcessor.3"); //$NON-NLS-1$

	/**
	 * The original identifier node we want to rename
	 */
	private final Scalar scalar;
	private final String scalarName;

	/**
	 * holds wether or not we want to change also the inlined text
	 */
	private boolean isUpdateTextualMatches;

	public RenameGlobalConstantProcessor(IFile operatedFile, ASTNode locateNode) {
		super(operatedFile); //$NON-NLS-1$

		this.scalar = getScalar(locateNode);

		final String stringValue = scalar.getStringValue();
		final char charAt = stringValue.charAt(0);
		this.scalarName = charAt != '"' && charAt != '\'' ? stringValue
				: stringValue.substring(1, stringValue.length() - 1);
	}

	private Scalar getScalar(ASTNode locateNode) {
		if (locateNode.getType() != ASTNode.SCALAR) {
			if (locateNode instanceof Identifier
					&& "define".equals(((Identifier) locateNode).getName())) { //$NON-NLS-1$
				FunctionInvocation inv = (FunctionInvocation) locateNode
						.getParent().getParent();
				List<Expression> parameters = inv.parameters();
				if (parameters != null && parameters.size() > 0) {
					return (Scalar) parameters.get(0);
				}
			} else {
				return null;
			}
		} else {
			return (Scalar) locateNode;
		}
		return null;
	}

	/**
	 * Derive the change
	 */
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange rootChange = new CompositeChange(
				PhpRefactoringCoreMessages
						.getString("RenameDefinedProcessor.4")); //$NON-NLS-1$
		rootChange.markAsSynthetic();
		try {
			pm.beginTask(RenameGlobalConstantProcessor.RENAME_IS_PROCESSING,
					participantFiles.size());
			pm.setTaskName(RenameGlobalConstantProcessor.CREATING_MODIFICATIONS_LABEL);

			if (pm.isCanceled())
				throw new OperationCanceledException();

			// get target parameters
			final String newElementName = getNewElementName();

			// go over the files and check for global variable usage
			for (Entry<IFile, Program> entry : participantFiles.entrySet()) {
				final IFile file = entry.getKey();
				final Program program = entry.getValue();
				final RenameGlobalConstant rename = new RenameGlobalConstant(
						file, scalarName, newElementName,
						getUpdateTextualMatches());

				// aggregate the changes identifiers
				program.accept(rename);

				if (pm.isCanceled())
					throw new OperationCanceledException();

				pm.worked(1);

				if (rename.hasChanges()) {
					// create the change
					TextFileChange change = acquireChange(file, program);
					rename.updateChange(change);
					rootChange.add(change);
				}
			}
			return rootChange;

		} finally {
			pm.done();
		}
	}

	public Object[] getElements() {
		return new Object[] { scalar };
	}

	public String getIdentifier() {
		return ID_RENAME_CONSTANT;
	}

	public String getProcessorName() {
		return RENAME_CONSTANT_PROCESSOR_NAME;
	}

	public Object getNewElement() {
		return getNewElementName();
	}

	public String getCurrentElementName() {
		return scalarName;
	}

	public boolean canEnableTextUpdating() {
		return true;
	}

	public String getCurrentElementQualifier() {
		return scalarName;
	}

	public boolean getUpdateTextualMatches() {
		return isUpdateTextualMatches;
	}

	public void setUpdateTextualMatches(boolean update) {
		isUpdateTextualMatches = update;
	}

	public RefactoringStatus getRefactoringStatus(IFile key, Program program) {
		if (PhpElementConciliator.constantAlreadyExists(program,
				getNewElementName())) {
			final String message = MessageFormat.format(
					RenameGlobalConstantProcessor.CONSTANT_IS_USED,
					new Object[] { key.getName() });
			return RefactoringStatus.createWarningStatus(message);
		}
		return null;
	}
}
