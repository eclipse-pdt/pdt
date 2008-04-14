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

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.internal.ui.actions.StatusInfo;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class NewVariableEntryDialog extends StatusDialog {

	private class VariablesAdapter implements IDialogFieldListener, IListAdapter {

		// -------- IListAdapter --------

		public void customButtonPressed(ListDialogField field, int index) {
			switch (index) {
				case IDX_EXTEND: /* extend */
					extendButtonPressed();
					break;
			}
		}

		public void selectionChanged(ListDialogField field) {
			doSelectionChanged();
		}

		public void doubleClicked(ListDialogField field) {
			doDoubleClick();
		}

		// ---------- IDialogFieldListener --------

		public void dialogFieldChanged(DialogField field) {
			if (field == fConfigButton) {
				configButtonPressed();
			}

		}

	}

	private final int IDX_EXTEND = 0;

	private ListDialogField fVariablesList;
	private boolean fCanExtend;
	private boolean fIsValidSelection;

	private IPath[] fResultPaths;

	private SelectionButtonDialogField fConfigButton;

	public NewVariableEntryDialog(Shell parent) {
		super(parent);
		setTitle(PHPUIMessages.getString("NewVariableEntryDialog_title"));

		int shellStyle = getShellStyle();
		setShellStyle(shellStyle | SWT.MAX | SWT.RESIZE);
		updateStatus(new StatusInfo(IStatus.ERROR, "")); //$NON-NLS-1$

		String[] buttonLabels = new String[] { PHPUIMessages.getString("NewVariableEntryDialog_vars_extend"), };

		VariablesAdapter adapter = new VariablesAdapter();

		IPVariableElementLabelProvider labelProvider = new IPVariableElementLabelProvider(false);

		fVariablesList = new ListDialogField(adapter, buttonLabels, labelProvider);
		fVariablesList.setDialogFieldListener(adapter);
		fVariablesList.setLabelText(PHPUIMessages.getString("NewVariableEntryDialog_vars_label"));

		fVariablesList.enableButton(IDX_EXTEND, false);

		fVariablesList.setViewerSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof IPVariableElement && e2 instanceof IPVariableElement) {
					return ((IPVariableElement) e1).getName().compareTo(((IPVariableElement) e2).getName());
				}
				return super.compare(viewer, e1, e2);
			}
		});

		fConfigButton = new SelectionButtonDialogField(SWT.PUSH);
		fConfigButton.setLabelText(PHPUIMessages.getString("NewVariableEntryDialog_configbutton_label"));
		fConfigButton.setDialogFieldListener(adapter);

		initializeElements();

		fCanExtend = false;
		fIsValidSelection = false;
		fResultPaths = null;
	}

	private void initializeElements() {
		String[] entries = PHPProjectOptions.getIncludePathVariableNames();
		ArrayList elements = new ArrayList(entries.length);
		for (String name : entries) {
			IPath entryPath = PHPProjectOptions.getIncludePathVariable(name);
			if (entryPath != null) {
				elements.add(new IPVariableElement(name, entryPath, IncludePathVariableManager.instance().isReserved(name)));
			}
		}

		fVariablesList.setElements(elements);
	}

	/* (non-Javadoc)
	 * @see Window#configureShell(Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(shell, IPHPHelpContextIds.ADDING_ELEMENTS_TO_A_PROJECT_S_INCLUDE_PATH);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout layout = (GridLayout) composite.getLayout();
		layout.numColumns = 2;

		fVariablesList.doFillIntoGrid(composite, 3);

		LayoutUtil.setHorizontalSpan(fVariablesList.getLabelControl(null), 2);

		GridData listData = (GridData) fVariablesList.getListControl(null).getLayoutData();
		listData.grabExcessHorizontalSpace = true;
		listData.heightHint = convertHeightInCharsToPixels(10);

		Composite lowerComposite = new Composite(composite, SWT.NONE);
		lowerComposite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		lowerComposite.setLayout(layout);

		fConfigButton.doFillIntoGrid(lowerComposite, 1);

		applyDialogFont(composite);
		return composite;
	}

	public IPath[] getResult() {
		return fResultPaths;
	}

	/*
	 * @see IDoubleClickListener#doubleClick(DoubleClickEvent)
	 */
	private void doDoubleClick() {
		if (fIsValidSelection) {
			okPressed();
		} else if (fCanExtend) {
			extendButtonPressed();
		}
	}

	private void doSelectionChanged() {
		boolean isValidSelection = true;
		boolean canExtend = false;
		StatusInfo status = new StatusInfo();

		List selected = fVariablesList.getSelectedElements();
		int nSelected = selected.size();

		if (nSelected > 0) {
			fResultPaths = new Path[nSelected];
			for (int i = 0; i < nSelected; i++) {
				IPVariableElement curr = (IPVariableElement) selected.get(i);
				fResultPaths[i] = new Path(curr.getName());
				// Seva: this is not true - we only work with folders.
				/*
				if (!curr.getPath().toFile().exists()) {
					status.setError(PHPUIMessages.getString("NewVariableEntryDialog_variable_non_existent_location")); //$NON-NLS-1$
					isValidSelection = false;
				}
				else if (!curr.getPath().toFile().isFile()) {
					status.setInfo(PHPUIMessages.getString("NewVariableEntryDialog_info_isfolder"));
					canExtend = true;
				}
				*/
			}
		} else {
			isValidSelection = false;
			status.setInfo(PHPUIMessages.getString("NewVariableEntryDialog_info_noselection"));
		}
		if (isValidSelection && nSelected > 1) {
			String str = MessageFormat.format(PHPUIMessages.getString("NewVariableEntryDialog_info_selected"), new String[] { String.valueOf(nSelected) });
			status.setInfo(str);
		}
		fCanExtend = nSelected == 1 && canExtend;
		fVariablesList.enableButton(0, fCanExtend);

		updateStatus(status);
		fIsValidSelection = isValidSelection;
		Button okButton = getButton(IDialogConstants.OK_ID);
		if (okButton != null && !okButton.isDisposed()) {
			okButton.setEnabled(isValidSelection);
		}
	}

	private IPath[] chooseExtensions(IPVariableElement elem) {
		File file = elem.getPath().toFile();

		ZipFileSelectionDialog dialog = new ZipFileSelectionDialog(getShell(), true, true);
		dialog.setTitle(PHPUIMessages.getString("NewVariableEntryDialog_ExtensionDialog_title"));
		dialog.setMessage(MessageFormat.format(PHPUIMessages.getString("NewVariableEntryDialog_ExtensionDialog_description"), new String[] { elem.getName() }));
		dialog.setInput(file);
		if (dialog.open() == Window.OK) {
			Object[] selected = dialog.getResult();
			IPath[] paths = new IPath[selected.length];
			for (int i = 0; i < selected.length; i++) {
				IPath filePath = Path.fromOSString(((File) selected[i]).getPath());
				IPath resPath = new Path(elem.getName());
				for (int k = elem.getPath().segmentCount(); k < filePath.segmentCount(); k++) {
					resPath = resPath.append(filePath.segment(k));
				}
				paths[i] = resPath;
			}
			return paths;
		}
		return null;
	}

	protected final void extendButtonPressed() {
		List selected = fVariablesList.getSelectedElements();
		if (selected.size() == 1) {
			IPath[] extendedPaths = chooseExtensions((IPVariableElement) selected.get(0));
			if (extendedPaths != null) {
				fResultPaths = extendedPaths;
				super.buttonPressed(IDialogConstants.OK_ID);
			}
		}
	}

	protected final void configButtonPressed() {
		String id = IncludePathVarsPreferencePage.ID;
		PreferencesUtil.createPreferenceDialogOn(getShell(), id, new String[] { id }, null).open();
		initializeElements();
	}

}
