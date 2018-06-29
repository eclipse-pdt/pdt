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
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.php.refactoring.core.rename.INameUpdating;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;

/**
 * Base wizard for all rename
 * 
 * @author Roy, 2007
 */
public class RenameRefactoringWizard extends RefactoringWizard {

	private final String fInputPageDescription;
	private final String fPageContextHelpId;
	private final ImageDescriptor fInputPageImageDescriptor;

	public RenameRefactoringWizard(Refactoring refactoring, String defaultPageTitle, String inputPageDescription,
			ImageDescriptor inputPageImageDescriptor, String pageContextHelpId) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE);
		setDefaultPageTitle(defaultPageTitle);
		fInputPageDescription = inputPageDescription;
		fInputPageImageDescriptor = inputPageImageDescriptor;
		fPageContextHelpId = pageContextHelpId;
	}

	/*
	 * non java-doc
	 * 
	 * @see RefactoringWizard#addUserInputPages
	 */
	@Override
	protected void addUserInputPages() {
		String initialSetting = getNameUpdating().getCurrentElementName();
		RenameInputWizardPage inputPage = createInputPage(fInputPageDescription, initialSetting);
		inputPage.setImageDescriptor(fInputPageImageDescriptor);
		addPage(inputPage);
	}

	protected INameUpdating getNameUpdating() {
		return getRefactoring().getAdapter(INameUpdating.class);
	}

	protected RenameInputWizardPage createInputPage(String message, String initialSetting) {
		return new RenameInputWizardPage(message, fPageContextHelpId, true, initialSetting) {
			@Override
			protected RefactoringStatus validateTextField(String text) {
				return validateNewName(text);
			}
		};
	}

	protected RefactoringStatus validateNewName(String newName) {
		INameUpdating ref = getNameUpdating();
		ref.setNewElementName(newName);
		try {
			return ref.checkNewElementName(newName);
		} catch (CoreException e) {
			RefactoringUIPlugin.log(e);
			return RefactoringStatus.createFatalErrorStatus(
					PHPRefactoringUIMessages.getString("RenameRefactoringWizard_internal_error")); //$NON-NLS-1$
		}
	}

}
