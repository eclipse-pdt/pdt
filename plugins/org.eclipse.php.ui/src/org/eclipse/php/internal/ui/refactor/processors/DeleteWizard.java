/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.refactor.processors;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.corext.refactoring.util.ModelElementUtil;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.dltk.internal.ui.refactoring.MessageWizardPage;
import org.eclipse.dltk.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.dltk.internal.ui.refactoring.reorg.ReorgQueries;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.participants.DeleteRefactoring;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class DeleteWizard extends RefactoringWizard {

	public DeleteWizard(Refactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE | YES_NO_BUTTON_STYLE
				| NO_PREVIEW_PAGE | NO_BACK_BUTTON_ON_STATUS_DIALOG);
		setDefaultPageTitle(RefactoringMessages.DeleteWizard_1);
		((ScriptDeleteProcessor) ((DeleteRefactoring) getRefactoring())
				.getProcessor()).setQueries(new ReorgQueries(this));
	}

	protected void addUserInputPages() {
		addPage(new DeleteInputPage());
	}

	public int getMessageLineWidthInChars() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#needsProgressMonitor()
	 */
	public boolean needsProgressMonitor() {
		DeleteRefactoring refactoring = (DeleteRefactoring) getRefactoring();
		RefactoringProcessor processor = refactoring.getProcessor();
		if (processor instanceof ScriptDeleteProcessor) {
			return ((ScriptDeleteProcessor) processor).needsProgressMonitor();
		}
		return super.needsProgressMonitor();
	}

	private static class DeleteInputPage extends MessageWizardPage {
		private static final String PAGE_NAME = "DeleteInputPage"; //$NON-NLS-1$ 
		private static final String DIALOG_SETTINGS_DELETE_SUB_PACKAGES = "deleteSubPackages"; //$NON-NLS-1$ 
		private Button fDeleteSubPackagesCheckBox;

		public DeleteInputPage() {
			super(PAGE_NAME, true, MessageWizardPage.STYLE_QUESTION);
		}

		protected String getMessageString() {
			try {
				if (1 == numberOfSelectedElements()) {
					String pattern = createConfirmationStringForOneElement();
					String name = getNameOfSingleSelectedElement();
					return Messages.format(pattern, new String[] { name });
				} else {
					String pattern = createConfirmationStringForManyElements();
					return Messages.format(pattern, new String[] { String
							.valueOf(numberOfSelectedElements()) });
				}
			} catch (ModelException e) {
				// http://bugs.eclipse.org/bugs/show_bug.cgi?id=19253
				if (ScriptModelUtil.isExceptionToBeLogged(e))
					DLTKUIPlugin.log(e);
				setPageComplete(false);
				if (e.isDoesNotExist())
					return RefactoringMessages.DeleteWizard_12;
				return RefactoringMessages.DeleteWizard_2;
			}
		}

		public void createControl(Composite parent) {
			super.createControl(parent);

			if (getDeleteProcessor().hasSubPackagesToDelete())
				addDeleteSubPackagesCheckBox();
		}

		/**
		 * Adds the "delete subpackages" checkbox to the composite. Note that
		 * this code assumes that the control of the parent is a Composite with
		 * GridLayout and a horizontal span of 2.
		 * 
		 * @see MessageWizardPage#createControl(Composite)
		 */
		private void addDeleteSubPackagesCheckBox() {

			Composite c = new Composite((Composite) getControl(), SWT.NONE);
			GridLayout gd = new GridLayout();
			gd.horizontalSpacing = 10;
			c.setLayout(gd);

			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;
			c.setLayoutData(data);

			final boolean selection = getRefactoringSettings().getBoolean(
					DIALOG_SETTINGS_DELETE_SUB_PACKAGES);

			fDeleteSubPackagesCheckBox = new Button(c, SWT.CHECK);
			fDeleteSubPackagesCheckBox
					.setText(org.eclipse.php.internal.ui.refactor.processors.Messages.DeleteFolderAndSubFolder);
			fDeleteSubPackagesCheckBox.setSelection(selection);

			fDeleteSubPackagesCheckBox
					.addSelectionListener(new SelectionAdapter() {

						public void widgetSelected(SelectionEvent event) {
							getDeleteProcessor().setDeleteSubPackages(
									fDeleteSubPackagesCheckBox.getSelection());
						}
					});

			getDeleteProcessor().setDeleteSubPackages(
					fDeleteSubPackagesCheckBox.getSelection());
		}

		private String getNameOfSingleSelectedElement() throws ModelException {
			if (getSingleSelectedResource() != null)
				return ReorgUtils.getName(getSingleSelectedResource());
			else
				return ReorgUtils.getName(getSingleSelectedScriptElement());
		}

		private IModelElement getSingleSelectedScriptElement() {
			IModelElement[] elements = getSelectedScriptElements();
			return elements.length == 1 ? elements[0] : null;
		}

		private IResource getSingleSelectedResource() {
			IResource[] resources = getSelectedResources();
			return resources.length == 1 ? resources[0] : null;
		}

		private int numberOfSelectedElements() {
			return getSelectedScriptElements().length
					+ getSelectedResources().length;
		}

		protected boolean performFinish() {
			return super.performFinish() || getDeleteProcessor().wasCanceled(); // close
			// the
			// dialog
			// if
			// canceled
		}

		protected boolean saveSettings() {
			if (getContainer() instanceof Dialog)
				return ((Dialog) getContainer()).getReturnCode() == IDialogConstants.OK_ID;
			return true;
		}

		public void dispose() {
			if (fDeleteSubPackagesCheckBox != null && saveSettings())
				getRefactoringSettings().put(
						DIALOG_SETTINGS_DELETE_SUB_PACKAGES,
						fDeleteSubPackagesCheckBox.getSelection());
			super.dispose();
		}

		private String createConfirmationStringForOneElement()
				throws ModelException {
			IModelElement[] elements = getSelectedScriptElements();
			if (elements.length == 1) {
				IModelElement element = elements[0];
				if (isDefaultPackageWithLinkedFiles(element))
					return RefactoringMessages.DeleteWizard_3;

				if (!isLinkedResource(element))
					return RefactoringMessages.DeleteWizard_4;

				if (isLinkedPackageOrProjectFragment(element))
					// XXX workaround for jcore bugs 31998 and 31456 - linked
					// packages or source folders cannot be deleted properly
					return RefactoringMessages.DeleteWizard_6;

				return RefactoringMessages.DeleteWizard_5;
			} else {
				if (isLinked(getSelectedResources()[0])) // checked before that
					// this will work
					return RefactoringMessages.DeleteWizard_7;
				else
					return RefactoringMessages.DeleteWizard_8;
			}
		}

		private String createConfirmationStringForManyElements()
				throws ModelException {
			IResource[] resources = getSelectedResources();
			IModelElement[] modelElements = getSelectedScriptElements();
			if (!containsLinkedResources(resources, modelElements))
				return RefactoringMessages.DeleteWizard_9;

			if (!containsLinkedPackagesOrProjectFragments(modelElements))
				return RefactoringMessages.DeleteWizard_10;

			// XXX workaround for jcore bugs - linked packages or source folders
			// cannot be deleted properly
			return RefactoringMessages.DeleteWizard_11;
		}

		private static boolean isLinkedPackageOrProjectFragment(
				IModelElement element) {
			if ((element instanceof IScriptFolder)
					|| (element instanceof IProjectFragment))
				return isLinkedResource(element);
			else
				return false;
		}

		private static boolean containsLinkedPackagesOrProjectFragments(
				IModelElement[] modelElements) {
			for (int i = 0; i < modelElements.length; i++) {
				IModelElement element = modelElements[i];
				if (isLinkedPackageOrProjectFragment(element))
					return true;
			}
			return false;
		}

		private static boolean containsLinkedResources(IResource[] resources,
				IModelElement[] modelElements) throws ModelException {
			for (int i = 0; i < modelElements.length; i++) {
				IModelElement element = modelElements[i];
				if (isLinkedResource(element))
					return true;
				if (isDefaultPackageWithLinkedFiles(element))
					return true;
			}
			for (int i = 0; i < resources.length; i++) {
				IResource resource = resources[i];
				if (isLinked(resource))
					return true;
			}
			return false;
		}

		private static boolean isDefaultPackageWithLinkedFiles(
				Object firstElement) throws ModelException {
			if (!ModelElementUtil.isDefaultPackage(firstElement))
				return false;
			IScriptFolder defaultPackage = (IScriptFolder) firstElement;
			ISourceModule[] cus = defaultPackage.getSourceModules();
			for (int i = 0; i < cus.length; i++) {
				if (isLinkedResource(cus[i]))
					return true;
			}
			return false;
		}

		private static boolean isLinkedResource(IModelElement element) {
			return isLinked(ReorgUtils.getResource(element));
		}

		private static boolean isLinked(IResource resource) {
			return resource != null && resource.isLinked();
		}

		private IModelElement[] getSelectedScriptElements() {
			return getDeleteProcessor().getScriptElementsToDelete();
		}

		private IResource[] getSelectedResources() {
			return getDeleteProcessor().getResourcesToDelete();
		}

		private ScriptDeleteProcessor getDeleteProcessor() {
			return (ScriptDeleteProcessor) ((DeleteRefactoring) getRefactoring())
					.getProcessor();
		}

	}
}
