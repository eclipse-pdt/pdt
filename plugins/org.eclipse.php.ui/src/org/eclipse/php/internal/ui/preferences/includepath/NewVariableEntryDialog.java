/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPVariableElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPVariableElementLabelProvider;
import org.eclipse.dltk.ui.preferences.BuildpathVariablesPreferencePage;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class NewVariableEntryDialog extends StatusDialog {

	private class VariablesAdapter implements IDialogFieldListener,
			IListAdapter {

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

	private CLabel fWarning;
	IEnvironment environment;

	public NewVariableEntryDialog(Shell parent, IEnvironment environment) {
		super(parent);
		setTitle(NewWizardMessages.NewVariableEntryDialog_title);
		this.environment = environment;
		updateStatus(new StatusInfo(IStatus.ERROR, "")); //$NON-NLS-1$ 

		String[] buttonLabels = new String[] { NewWizardMessages.NewVariableEntryDialog_vars_extend, };

		VariablesAdapter adapter = new VariablesAdapter();

		BPVariableElementLabelProvider labelProvider = new BPVariableElementLabelProvider(
				false);

		fVariablesList = new ListDialogField(adapter, buttonLabels,
				labelProvider);
		fVariablesList.setDialogFieldListener(adapter);
		fVariablesList
				.setLabelText(NewWizardMessages.NewVariableEntryDialog_vars_label);

		// TODO now disable Extend button
		fVariablesList.enableButton(IDX_EXTEND, false);

		// fVariablesList.setViewerComparator(new ViewerComparator() {
		// public int compare(Viewer viewer, Object e1, Object e2) {
		// if (e1 instanceof BPVariableElement
		// && e2 instanceof BPVariableElement) {
		// return getComparator().compare(
		// ((BPVariableElement) e1).getName(),
		// ((BPVariableElement) e2).getName());
		// }
		// return super.compare(viewer, e1, e2);
		// }
		// });

		fConfigButton = new SelectionButtonDialogField(SWT.PUSH);
		fConfigButton
				.setLabelText(NewWizardMessages.NewVariableEntryDialog_configbutton_label);
		fConfigButton.setDialogFieldListener(adapter);

		initializeElements();

		fCanExtend = false;
		fIsValidSelection = false;
		fResultPaths = null;

		fVariablesList.selectFirstElement();
	}

	/*
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 * 
	 * @since 3.4
	 */
	protected boolean isResizable() {
		return true;
	}

	private void initializeElements() {
		String[] entries = DLTKCore.getBuildpathVariableNames();
		ArrayList elements = new ArrayList(entries.length);
		for (int i = 0; i < entries.length; i++) {
			String name = entries[i];
			IPath entryPath = DLTKCore.getBuildpathVariable(name);
			if (entryPath != null) {
				elements.add(new BPVariableElement(name, entryPath));
			}
		}

		fVariablesList.setElements(elements);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Window#configureShell(Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		// TODO add help for this dialog
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
		// IJavaHelpContextIds.NEW_VARIABLE_ENTRY_DIALOG);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
	 */
	protected IDialogSettings getDialogBoundsSettings() {
		return PHPUiPlugin.getDefault().getDialogSettings()/*
															 * getDialogSettingsSection
															 * ( getClass
															 * ().getName())
															 */;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout layout = (GridLayout) composite.getLayout();
		layout.numColumns = 2;

		fVariablesList.doFillIntoGrid(composite, 3);

		LayoutUtil.setHorizontalSpan(fVariablesList.getLabelControl(null), 2);

		GridData listData = (GridData) fVariablesList.getListControl(null)
				.getLayoutData();
		listData.grabExcessHorizontalSpace = true;
		listData.heightHint = convertHeightInCharsToPixels(10);
		listData.widthHint = convertWidthInCharsToPixels(70);

		fWarning = new CLabel(composite, SWT.NONE);
		fWarning.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false,
				fVariablesList.getNumberOfControls() - 1, 1));

		Composite lowerComposite = new Composite(composite, SWT.NONE);
		lowerComposite.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL));

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
				BPVariableElement curr = (BPVariableElement) selected.get(i);
				fResultPaths[i] = new Path(curr.getName());
				File file = curr.getPath().toFile();
				if (!file.exists()) {
					status.setError(NewWizardMessages.NewVariableEntryDialog_info_notexists);
					isValidSelection = false;
					break;
				}
				if (file.isDirectory()) {
					canExtend = true;
				}
			}
		} else {
			isValidSelection = false;
			status.setInfo(NewWizardMessages.NewVariableEntryDialog_info_noselection);
		}
		if (isValidSelection && nSelected > 1) {
			String str = Messages.format(
					NewWizardMessages.NewVariableEntryDialog_info_selected,
					String.valueOf(nSelected));
			status.setInfo(str);
		}
		// TODO now disable Extend button
		fCanExtend = nSelected == 1 && canExtend;
		fVariablesList.enableButton(0, fCanExtend);

		updateStatus(status);
		fIsValidSelection = isValidSelection;
		Button okButton = getButton(IDialogConstants.OK_ID);
		if (okButton != null && !okButton.isDisposed()) {
			okButton.setEnabled(isValidSelection);
		}
		updateDeprecationWarning();
	}

	private void updateDeprecationWarning() {
		// TODO
		// if (fWarning == null || fWarning.isDisposed())
		// return;
		//
		// for (Iterator iter= fVariablesList.getSelectedElements().iterator();
		// iter.hasNext();) {
		// BPVariableElement element= (BPVariableElement) iter.next();
		// String deprecationMessage= element.getDeprecationMessage();
		// if (deprecationMessage != null) {
		// fWarning.setText(deprecationMessage);
		// fWarning.setImage(JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING));
		// return;
		// }
		// }
		// fWarning.setText(null);
		// fWarning.setImage(null);
	}

	private IPath[] chooseExtensions(BPVariableElement elem) {
		File file = elem.getPath().toFile();
		// IPath[] selected = PHPBuildpathDialogAccess
		// .chooseExternalArchiveEntries(getShell(), environment);
		JARFileSelectionDialog dialog = new JARFileSelectionDialog(getShell(),
				true, true, true);
		dialog.setTitle(NewWizardMessages.NewVariableEntryDialog_ExtensionDialog_title);
		dialog.setMessage(Messages
				.format(NewWizardMessages.NewVariableEntryDialog_ExtensionDialog_description,
						elem.getName()));
		dialog.setInput(file);
		if (dialog.open() == Window.OK) {
			Object[] selected = dialog.getResult();
			IPath[] paths = new IPath[selected.length];
			for (int i = 0; i < selected.length; i++) {
				IPath filePath = Path.fromOSString(((File) selected[i])
						.getPath());
				IPath resPath = new Path(elem.getName());
				for (int k = elem.getPath().segmentCount(); k < filePath
						.segmentCount(); k++) {
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
			IPath[] extendedPaths = chooseExtensions((BPVariableElement) selected
					.get(0));
			if (extendedPaths != null && extendedPaths.length > 0) {
				fResultPaths = extendedPaths;
				super.buttonPressed(IDialogConstants.OK_ID);
			}
		}
	}

	protected final void configButtonPressed() {
		String id = BuildpathVariablesPreferencePage.ID;
		Map options = new HashMap();
		List selected = fVariablesList.getSelectedElements();
		if (!selected.isEmpty()) {
			String varName = ((BPVariableElement) selected.get(0)).getName();
			options.put(BuildpathVariablesPreferencePage.DATA_SELECT_VARIABLE,
					varName);
		}
		PreferencesUtil.createPreferenceDialogOn(getShell(), id,
				new String[] { id }, options).open();

		List oldElements = fVariablesList.getElements();
		initializeElements();
		List newElements = fVariablesList.getElements();
		newElements.removeAll(oldElements);
		if (!newElements.isEmpty()) {
			fVariablesList.selectElements(new StructuredSelection(newElements));
		} else if (fVariablesList.getSelectedElements().isEmpty()) {
			fVariablesList.selectFirstElement();
		}
	}

}
