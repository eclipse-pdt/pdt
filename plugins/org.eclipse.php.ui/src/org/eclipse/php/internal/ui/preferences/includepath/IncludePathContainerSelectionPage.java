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

import java.util.Arrays;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.ListContentProvider;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 */
public class IncludePathContainerSelectionPage extends WizardPage {

	private static final String DIALOGSTORE_SECTION = "IncludePathContainerSelectionPage"; //$NON-NLS-1$
	private static final String DIALOGSTORE_CONTAINER_IDX = "index"; //$NON-NLS-1$

	private static class IncludePathContainerLabelProvider extends LabelProvider {
		public String getText(Object element) {
			return ((IncludePathContainerDescriptor) element).getName();
		}
	}

	private static class IncludePathContainerSorter extends ViewerSorter {
	}

	private ListViewer fListViewer;
	private IncludePathContainerDescriptor[] fContainers;
	private IDialogSettings fDialogSettings;

	/**
	 * Constructor for IncludePathContainerWizardPage.
	 * @param containerPages
	 */
	protected IncludePathContainerSelectionPage(IncludePathContainerDescriptor[] containerPages) {
		super("IncludePathContainerWizardPage"); //$NON-NLS-1$
		setTitle(PHPUIMessages.getString("IncludePathContainerSelectionPage_title"));
		setDescription(PHPUIMessages.getString("IncludePathContainerSelectionPage_description"));
		setImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_LIBRARY);

		fContainers = containerPages;

		IDialogSettings settings = PHPUiPlugin.getDefault().getDialogSettings();
		fDialogSettings = settings.getSection(DIALOGSTORE_SECTION);
		if (fDialogSettings == null) {
			fDialogSettings = settings.addNewSection(DIALOGSTORE_SECTION);
			fDialogSettings.put(DIALOGSTORE_CONTAINER_IDX, 0);
		}
		validatePage();
	}

	/* (non-Javadoc)
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		fListViewer = new ListViewer(parent, SWT.SINGLE | SWT.BORDER);
		fListViewer.setLabelProvider(new IncludePathContainerLabelProvider());
		fListViewer.setContentProvider(new ListContentProvider());
		fListViewer.setSorter(new IncludePathContainerSorter());
		fListViewer.setInput(Arrays.asList(fContainers));
		fListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				validatePage();
			}
		});
		fListViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doDoubleClick();
			}
		});

		int selectionIndex = fDialogSettings.getInt(DIALOGSTORE_CONTAINER_IDX);
		if (selectionIndex >= fContainers.length) {
			selectionIndex = 0;
		}
		fListViewer.getList().select(selectionIndex);
		validatePage();
		setControl(fListViewer.getList());
		Dialog.applyDialogFont(fListViewer.getList());
	}

	/**
	 * Method validatePage.
	 */
	private void validatePage() {
		setPageComplete(getSelected() != null);
	}

	public IncludePathContainerDescriptor getSelected() {
		if (fListViewer != null) {
			ISelection selection = fListViewer.getSelection();
			return (IncludePathContainerDescriptor) getSingleElement(selection);
		}
		return null;
	}

	public static Object getSingleElement(ISelection s) {
		if (!(s instanceof IStructuredSelection))
			return null;
		IStructuredSelection selection = (IStructuredSelection) s;
		if (selection.size() != 1)
			return null;

		return selection.getFirstElement();
	}

	public IncludePathContainerDescriptor[] getContainers() {
		return fContainers;
	}

	protected void doDoubleClick() {
		if (canFlipToNextPage()) {
			getContainer().showPage(getNextPage());
		}
	}

	/* (non-Javadoc)
	 * @see IWizardPage#canFlipToNextPage()
	 */
	public boolean canFlipToNextPage() {
		return isPageComplete(); // avoid the getNextPage call to prevent potential plugin load
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		if (!visible && fListViewer != null) {
			fDialogSettings.put(DIALOGSTORE_CONTAINER_IDX, fListViewer.getList().getSelectionIndex());
		}
		super.setVisible(visible);
	}

}
