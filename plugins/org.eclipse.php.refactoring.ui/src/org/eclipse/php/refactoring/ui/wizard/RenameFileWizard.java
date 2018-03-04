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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.refactoring.core.rename.INameUpdating;
import org.eclipse.php.refactoring.core.rename.IReferenceUpdating;
import org.eclipse.php.refactoring.core.rename.RenameFileProcessor;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author shachar
 * 
 */
public class RenameFileWizard extends RenameRefactoringWizard {

	public RenameFileWizard(Refactoring refactoring) {
		super(refactoring, PHPRefactoringUIMessages.getString("RenameFileWizard.0"), //$NON-NLS-1$
				PHPRefactoringUIMessages.getString("RenameGlobalVariableWizard_inputPageDescription"), null, null); //$NON-NLS-1$
	}

	@Override
	protected RenameInputWizardPage createInputPage(String message, String initialSetting) {
		return new RenameInputWizardPage(message, null, true, initialSetting) {

			private static final String UPDATE_REFERENCES = "updateReferences"; //$NON-NLS-1$
			private Button fUpdateReferences;
			private Button fUPdateClassName;

			@Override
			protected RefactoringStatus validateTextField(String text) {
				return validateNewName(text);
			}

			@Override
			protected void addAdditionalOptions(Composite composite, RowLayouter layouter) {
				final IReferenceUpdating refactoring = getRefactoring().getAdapter(IReferenceUpdating.class);
				String title = PHPRefactoringUIMessages.getString("RenameFileWizard.1"); //$NON-NLS-1$
				boolean defaultValue = getBooleanSetting(UPDATE_REFERENCES, refactoring.getUpdateReferences());
				fUpdateReferences = createCheckbox(composite, title, defaultValue, layouter);
				// the default is update references
				fUpdateReferences.setSelection(true);
				refactoring.setUpdateRefernces(true);
				getRefactoringWizard().setForcePreviewReview(true);
				fUpdateReferences.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						refactoring.setUpdateRefernces(fUpdateReferences.getSelection());
						updateForcePreview();
					}
				});

				String updateClass = refactoring.getAttribute(RenameFileProcessor.NEEDUPDATECLASSNAME);

				if (updateClass != null && Boolean.valueOf(updateClass)) {
					defaultValue = true;
					fUPdateClassName = createCheckbox(composite,
							PHPRefactoringUIMessages.getString("RenameFileWizard.3"), defaultValue, layouter); //$NON-NLS-1$
					// the default is update references
					fUPdateClassName.setSelection(true);
					refactoring.setAttribute(RenameFileProcessor.UPDATECLASSNAME,
							Boolean.toString((fUPdateClassName.getSelection())));
					getRefactoringWizard().setForcePreviewReview(true);
					fUPdateClassName.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							refactoring.setAttribute(RenameFileProcessor.UPDATECLASSNAME,
									Boolean.toString(fUPdateClassName.getSelection()));
							updateForcePreview();
						}
					});
				}
			}

			@Override
			protected void updateForcePreview() {
				super.updateForcePreview();
				boolean forcePreview = false;
				Refactoring refactoring = getRefactoring();
				IReferenceUpdating refUpdate = refactoring.getAdapter(IReferenceUpdating.class);
				if (refUpdate != null) {
					forcePreview = refUpdate.getUpdateReferences();
				}
				getRefactoringWizard().setForcePreviewReview(forcePreview);
			}

			@Override
			public void dispose() {
				if (saveSettings()) {
					saveBooleanSetting(UPDATE_REFERENCES, fUpdateReferences);
				}
				super.dispose();
			}
		};
	}

	@Override
	protected RefactoringStatus validateNewName(String newName) {
		INameUpdating ref = getNameUpdating();
		ref.setNewElementName(newName);
		ResourcesPlugin.getWorkspace().validateName(newName, IResource.FILE);
		// check if the name is a valid file name
		IStatus nameStatus = ResourcesPlugin.getWorkspace().validateName(newName, IResource.FILE);
		if (!nameStatus.isOK()) {
			return RefactoringStatus.createFatalErrorStatus(nameStatus.getMessage());
		}
		return new RefactoringStatus();
	}
}
