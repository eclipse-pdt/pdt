/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.refactoring.core.move.PHPProjectMoveProcessor;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.dialogs.ProjectContentsLocationArea;
import org.eclipse.ui.internal.ide.dialogs.ProjectContentsLocationArea.IErrorMessageReporter;

/**
 * Move wizard page. Responsible for the creation of the wizard content.
 * 
 * @author Eden K., 2007
 * 
 */
public class RefactoringProjectMoveWizardPage extends UserInputWizardPage {

	private IProject project;

	private static String PROJECT_LOCATION_SELECTION_TITLE = IDEWorkbenchMessages.ProjectLocationSelectionDialog_selectionTitle;

	private ProjectContentsLocationArea locationArea;
	private Button fReferenceCheckbox;

	public RefactoringProjectMoveWizardPage(IProject existingProject) {
		super(PHPRefactoringUIMessages.getString("RefactoringMoveWizardPage.0")); //$NON-NLS-1$
		this.project = existingProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets .Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite result = new Composite(parent, SWT.NONE);
		setControl(result);
		result.setLayout(new GridLayout());

		locationArea = new ProjectContentsLocationArea(getErrorReporter(), result, this.project);

		// Scale the button based on the rest of the dialog
		setButtonLayoutData(locationArea.getBrowseButton());

		setPageComplete(false);

		addUpdateReferenceComponent(result);

		Dialog.applyDialogFont(result);

	}

	/**
	 * Get an error reporter for the receiver.
	 * 
	 * @return IErrorMessageReporter
	 */
	private IErrorMessageReporter getErrorReporter() {
		return new IErrorMessageReporter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.ui.internal.ide.dialogs.ProjectContentsLocationArea
			 * .IErrorMessageReporter#reportError(java.lang.String)
			 */
			@Override
			public void reportError(String errorMessage, boolean notError) {
				if (errorMessage != null) {
					if (notError) {
						setMessage(errorMessage, IMessageProvider.WARNING);
					} else {
						setMessage(errorMessage, IMessageProvider.ERROR);
					}
					setPageComplete(false);
				} else {
					verifyDestination(new Path(locationArea.getProjectLocation()).append(project.getName()).toString(),
							false);
				}
			}
		};
	}

	private final void verifyDestination(String selected, boolean initialVerification) {
		try {
			RefactoringStatus status = verifyDestination(selected);
			if (initialVerification) {
				setPageComplete(status.isOK());
			} else {
				setPageComplete(status);
			}
		} catch (Exception e) {
			Logger.logException(e);
			setPageComplete(false);
		}
	}

	protected RefactoringStatus verifyDestination(String selected) throws Exception {
		PHPProjectMoveProcessor processor = getPHPMoveProcessor();
		final RefactoringStatus refactoringStatus;

		refactoringStatus = processor.setDestination(selected);

		return refactoringStatus;
	}

	private PHPProjectMoveProcessor getPHPMoveProcessor() {
		return getRefactoring().getAdapter(PHPProjectMoveProcessor.class);
	}

	private void addUpdateReferenceComponent(Composite result) {
		final PHPProjectMoveProcessor processor = getPHPMoveProcessor();

		fReferenceCheckbox = new Button(result, SWT.CHECK);
		fReferenceCheckbox.setText(PHPRefactoringUIMessages.getString("RefactoringMoveWizardPage.3")); //$NON-NLS-1$
		fReferenceCheckbox.setSelection(processor.getUpdateReferences());
		fReferenceCheckbox.setEnabled(true);
		fReferenceCheckbox.setSelection(true);
		processor.setUpdateReferences(true);
		// getRefactoringWizard().setForcePreviewReview(true);

		fReferenceCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				processor.setUpdateReferences(((Button) e.widget).getSelection());
				getRefactoringWizard().setForcePreviewReview(processor.getUpdateReferences());
			}
		});
	}

}
