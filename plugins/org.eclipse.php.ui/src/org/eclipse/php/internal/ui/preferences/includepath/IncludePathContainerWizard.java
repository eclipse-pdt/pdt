/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.ExceptionHandler;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.swt.widgets.Shell;

/**
 */
public class IncludePathContainerWizard extends Wizard {

	private IncludePathContainerDescriptor fPageDesc;
	private IIncludePathEntry fEntryToEdit;

	private IIncludePathEntry[] fNewEntries;
	private IIncludePathContainerPage fContainerPage;
	private IProject fCurrProject;
	private PHPProjectOptions fCurrOptions;
	private IIncludePathEntry[] fCurrIncludePath;

	private IncludePathContainerSelectionPage fSelectionWizardPage;

	/**
	 * Constructor for IncludePathContainerWizard.
	 */
	public IncludePathContainerWizard(IIncludePathEntry entryToEdit, IProject currProject, IIncludePathEntry[] currEntries) {
		this(entryToEdit, null, currProject, currEntries);
	}

	/**
	 * Constructor for IncludePathContainerWizard.
	 */
	public IncludePathContainerWizard(IncludePathContainerDescriptor pageDesc, IProject currProject, IIncludePathEntry[] currEntries) {
		this(null, pageDesc, currProject, currEntries);
	}

	private IncludePathContainerWizard(IIncludePathEntry entryToEdit, IncludePathContainerDescriptor pageDesc, IProject currProject, IIncludePathEntry[] currEntries) {
		fEntryToEdit = entryToEdit;
		fPageDesc = pageDesc;
		fNewEntries = null;

		fCurrProject = currProject;
		fCurrIncludePath = currEntries;

		String title;
		if (entryToEdit == null) {
			title = PHPUIMessages.IncludePathContainerWizard_new_title;
		} else {
			title = PHPUIMessages.IncludePathContainerWizard_edit_title;
		}
		setWindowTitle(title);
	}

	/**
	 * @deprecated use getNewEntries()
	 */
	public IIncludePathEntry getNewEntry() {
		IIncludePathEntry[] entries = getNewEntries();
		if (entries != null) {
			return entries[0];
		}
		return null;
	}

	public IIncludePathEntry[] getNewEntries() {
		return fNewEntries;
	}

	/* (non-Javadoc)
	 * @see IWizard#performFinish()
	 */
	public boolean performFinish() {
		if (fContainerPage != null) {
			if (fContainerPage.finish()) {
				if (fEntryToEdit == null && fContainerPage instanceof IIncludePathContainerPageExtension2) {
					fNewEntries = ((IIncludePathContainerPageExtension2) fContainerPage).getNewContainers();
				} else {
					IIncludePathEntry entry = fContainerPage.getSelection();
					fNewEntries = (entry != null) ? new IIncludePathEntry[] { entry } : null;
				}
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see IWizard#addPages()
	 */
	public void addPages() {
		if (fPageDesc != null) {
			fContainerPage = getContainerPage(fPageDesc);
			addPage(fContainerPage);
		} else if (fEntryToEdit == null) { // new entry: show selection page as first page
			IncludePathContainerDescriptor[] containers = IncludePathContainerDescriptor.getDescriptors();

			fSelectionWizardPage = new IncludePathContainerSelectionPage(containers);
			addPage(fSelectionWizardPage);

			// add as dummy, will not be shown
			fContainerPage = new IncludePathContainerDefaultPage();
			addPage(fContainerPage);
		} else { // fPageDesc == null && fEntryToEdit != null
			IncludePathContainerDescriptor[] containers = IncludePathContainerDescriptor.getDescriptors();
			IncludePathContainerDescriptor descriptor = findDescriptorPage(containers, fEntryToEdit);
			fContainerPage = getContainerPage(descriptor);
			addPage(fContainerPage);
		}
		super.addPages();
	}

	private IIncludePathContainerPage getContainerPage(IncludePathContainerDescriptor pageDesc) {
		IIncludePathContainerPage containerPage = null;
		if (pageDesc != null) {
			IIncludePathContainerPage page = pageDesc.getPage();
			if (page != null) {
				return page; // if page is already created, avoid double initialization
			}
			try {
				containerPage = pageDesc.createPage();
			} catch (CoreException e) {
				handlePageCreationFailed(e);
				containerPage = null;
			}
		}

		if (containerPage == null) {
			containerPage = new IncludePathContainerDefaultPage();
			if (pageDesc != null) {
				pageDesc.setPage(containerPage); // avoid creation next time
			}
		}

		if (containerPage instanceof IIncludePathContainerPageExtension) {
			((IIncludePathContainerPageExtension) containerPage).initialize(fCurrProject, fCurrIncludePath);
		}

		containerPage.setSelection(fEntryToEdit);
		containerPage.setWizard(this);
		return containerPage;
	}

	/* (non-Javadoc)
	 * @see IWizard#getNextPage(IWizardPage)
	 */
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == fSelectionWizardPage) {

			IncludePathContainerDescriptor selected = fSelectionWizardPage.getSelected();
			fContainerPage = getContainerPage(selected);

			return fContainerPage;
		}
		return super.getNextPage(page);
	}

	private void handlePageCreationFailed(CoreException e) {
		String title = PHPUIMessages.IncludePathContainerWizard_pagecreationerror_title;
		String message = PHPUIMessages.IncludePathContainerWizard_pagecreationerror_message;
		ExceptionHandler.handle(e, getShell(), title, message);
	}

	private IncludePathContainerDescriptor findDescriptorPage(IncludePathContainerDescriptor[] containers, IIncludePathEntry entry) {
		for (int i = 0; i < containers.length; i++) {
			if (containers[i].canEdit(entry)) {
				return containers[i];
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#dispose()
	 */
	public void dispose() {
		if (fSelectionWizardPage != null) {
			IncludePathContainerDescriptor[] descriptors = fSelectionWizardPage.getContainers();
			for (int i = 0; i < descriptors.length; i++) {
				descriptors[i].dispose();
			}
		}
		super.dispose();
	}

	/* (non-Javadoc)
	 * @see IWizard#canFinish()
	 */
	public boolean canFinish() {
		if (fSelectionWizardPage != null) {
			if (!fContainerPage.isPageComplete()) {
				return false;
			}
		}
		if (fContainerPage != null) {
			return fContainerPage.isPageComplete();
		}
		return false;
	}

	public static int openWizard(Shell shell, IncludePathContainerWizard wizard) {
		WizardDialog dialog = new WizardDialog(shell, wizard);
		PixelConverter converter = new PixelConverter(shell);
		dialog.setMinimumPageSize(converter.convertWidthInCharsToPixels(70), converter.convertHeightInCharsToPixels(20));
		dialog.create();
		return dialog.open();
	}

}
