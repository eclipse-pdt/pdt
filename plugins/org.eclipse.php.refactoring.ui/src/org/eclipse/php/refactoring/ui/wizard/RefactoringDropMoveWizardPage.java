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

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.refactoring.core.move.PHPMoveProcessor;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Move wizard page. Responsible for the creation of the wizard content.
 * 
 * @author Eden K., 2007
 * 
 */
public class RefactoringDropMoveWizardPage extends UserInputWizardPage {

	private Button fReferenceCheckbox;
	private PHPMoveProcessor processor;

	public RefactoringDropMoveWizardPage() {
		super(PHPRefactoringUIMessages.getString("RefactoringMoveWizardPage.0")); //$NON-NLS-1$

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
		processor = getPHPMoveProcessor();
		Composite result = new Composite(parent, SWT.NONE);
		setControl(result);
		result.setLayout(new GridLayout());

		// IResource[] initialSelections = getPHPMoveProcessor()
		// .getSourceSelection();

		// verifyDestination(initialSelections, true);
		verifyDestination(processor.getDestination(), true);

		addUpdateReferenceComponent(result);
		// TODO add fully qualified names checkbox (? enhancement)

		Dialog.applyDialogFont(result);
	}

	private final void verifyDestination(Object selected, boolean initialVerification) {
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

	protected RefactoringStatus verifyDestination(Object selected) throws Exception {

		final RefactoringStatus refactoringStatus;

		if (selected instanceof IContainer) {
			refactoringStatus = processor.setDestination((IContainer) selected);
		} else {
			refactoringStatus = RefactoringStatus
					.createFatalErrorStatus(PHPRefactoringUIMessages.getString("RefactoringMoveWizardPage.2")); //$NON-NLS-1$
		}

		return refactoringStatus;
	}

	private PHPMoveProcessor getPHPMoveProcessor() {
		return getRefactoring().getAdapter(PHPMoveProcessor.class);
	}

	private void addUpdateReferenceComponent(Composite result) {
		final PHPMoveProcessor processor = getPHPMoveProcessor();

		fReferenceCheckbox = new Button(result, SWT.CHECK);
		fReferenceCheckbox.setText(PHPRefactoringUIMessages.getString("RefactoringMoveWizardPage.3")); //$NON-NLS-1$
		fReferenceCheckbox.setSelection(processor.getUpdateReferences());
		fReferenceCheckbox.setEnabled(true);
		fReferenceCheckbox.setSelection(true);
		processor.setUpdateReferences(true);
		getRefactoringWizard().setForcePreviewReview(true);

		fReferenceCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				processor.setUpdateReferences(((Button) e.widget).getSelection());
				getRefactoringWizard().setForcePreviewReview(processor.getUpdateReferences());
			}
		});
	}

}
