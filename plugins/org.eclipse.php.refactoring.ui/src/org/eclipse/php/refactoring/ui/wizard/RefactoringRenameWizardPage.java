/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.php.refactoring.core.rename.INameUpdating;
import org.eclipse.php.refactoring.core.rename.ITextUpdating;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.help.WorkbenchHelpSystem;

abstract class RenameInputWizardPage extends TextInputWizardPage {

	private String fHelpContextID;
	private Button fUpdateTextualMatches;
	private Button fUpdateQualifiedNames;
	private QualifiedNameComponent fQualifiedNameComponent;

	private static final String UPDATE_TEXTUAL_MATCHES = "updateTextualMatches"; //$NON-NLS-1$
	private static final String UPDATE_QUALIFIED_NAMES = "updateQualifiedNames"; //$NON-NLS-1$

	/**
	 * Creates a new text input page.
	 * 
	 * @param isLastUserPage
	 *            <code>true</code> if this page is the wizard's last user input
	 *            page. Otherwise <code>false</code>.
	 * @param initialValue
	 *            the initial value
	 */
	public RenameInputWizardPage(String description, String contextHelpId, boolean isLastUserPage,
			String initialValue) {
		super(description, isLastUserPage, initialValue);
		fHelpContextID = contextHelpId;
	}

	@Override
	public void createControl(Composite parent) {
		Composite superComposite = new Composite(parent, SWT.NONE);
		setControl(superComposite);
		initializeDialogUnits(superComposite);
		superComposite.setLayout(new GridLayout());
		Composite composite = new Composite(superComposite, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		composite.setLayout(layout);
		RowLayouter layouter = new RowLayouter(2);

		Label label = new Label(composite, SWT.NONE);
		label.setText(getLabelText());

		Text text = createTextInputField(composite);
		text.selectAll();
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = convertWidthInCharsToPixels(25);
		text.setLayoutData(gd);

		layouter.perform(label, text, 1);

		Label separator = new Label(composite, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridData.heightHint = 2;
		separator.setLayoutData(gridData);

		int indent = convertWidthInCharsToPixels(2);

		addAdditionalOptions(composite, layouter);
		addOptionalUpdateTextualMatches(composite, layouter);
		addOptionalUpdateQualifiedNameComponent(composite, layouter, indent);
		updateForcePreview();

		Dialog.applyDialogFont(superComposite);

		getControl().setData(WorkbenchHelpSystem.HELP_KEY, fHelpContextID);
		getControl().addHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent arg0) {
				org.eclipse.swt.program.Program.launch(fHelpContextID);
			}
		});
	}

	/**
	 * Clients can override this method to provide more UI elements. By default,
	 * does nothing
	 * 
	 * @param composite
	 *            the parent composite
	 * @param layouter
	 *            the row layouter to use
	 */
	protected void addAdditionalOptions(Composite composite, RowLayouter layouter) {
		// none by default
	}

	@Override
	public void setVisible(boolean visible) {
		// visible

		if (visible) {
			INameUpdating nameUpdating = getRefactoring().getAdapter(INameUpdating.class);
			if (nameUpdating != null) {
				String newName = getNewName(nameUpdating);
				if (newName != null && newName.length() > 0 && !newName.equals(getInitialValue())) {
					Text textField = getTextField();
					textField.setText(newName);
					textField.setSelection(0, newName.length());
				}
			}
		}
		super.setVisible(visible);
	}

	/**
	 * Returns the new name for the Java element or <code>null</code> if no new name
	 * is provided
	 * 
	 * @return the new name or <code>null</code>
	 */
	protected String getNewName(INameUpdating nameUpdating) {
		return nameUpdating.getNewElementName();
	}

	protected boolean saveSettings() {
		if (getContainer() instanceof Dialog) {
			return ((Dialog) getContainer()).getReturnCode() == IDialogConstants.OK_ID;
		}
		return true;
	}

	@Override
	public void dispose() {
		if (saveSettings()) {
			saveBooleanSetting(UPDATE_TEXTUAL_MATCHES, fUpdateTextualMatches);
			saveBooleanSetting(UPDATE_QUALIFIED_NAMES, fUpdateQualifiedNames);
			if (fQualifiedNameComponent != null) {
				fQualifiedNameComponent.savePatterns(getRefactoringSettings());
				// TODO : helps insert here
				// DelegateUIHelper.saveLeaveDelegateSetting(fLeaveDelegateCheckBox);
				// DelegateUIHelper.saveDeprecateDelegateSetting(fDeprecateDelegateCheckBox);
			}
		}
		super.dispose();
	}

	private void addOptionalUpdateTextualMatches(Composite result, RowLayouter layouter) {
		final ITextUpdating refactoring = getRefactoring().getAdapter(ITextUpdating.class);
		if (refactoring == null || !refactoring.canEnableTextUpdating()) {
			return;
		}
		String title = PHPRefactoringUIMessages.getString("RenameInputWizardPage_update_textual_matches"); //$NON-NLS-1$
		boolean defaultValue = getBooleanSetting(UPDATE_TEXTUAL_MATCHES, refactoring.getUpdateTextualMatches());
		fUpdateTextualMatches = createCheckbox(result, title, defaultValue, layouter);
		refactoring.setUpdateTextualMatches(fUpdateTextualMatches.getSelection());
		fUpdateTextualMatches.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refactoring.setUpdateTextualMatches(fUpdateTextualMatches.getSelection());
				updateForcePreview();
			}
		});
	}

	private void addOptionalUpdateQualifiedNameComponent(Composite parent, RowLayouter layouter, int marginWidth) {
		final IQualifiedNameUpdating ref = getRefactoring().getAdapter(IQualifiedNameUpdating.class);
		if (ref == null || !ref.canEnableQualifiedNameUpdating()) {
			return;
		}
		fUpdateQualifiedNames = new Button(parent, SWT.CHECK);
		int indent = marginWidth + fUpdateQualifiedNames.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		fUpdateQualifiedNames
				.setText(PHPRefactoringUIMessages.getString("RenameInputWizardPage_update_qualified_names")); //$NON-NLS-1$
		layouter.perform(fUpdateQualifiedNames);

		fQualifiedNameComponent = new QualifiedNameComponent(parent, SWT.NONE, ref, getRefactoringSettings());
		layouter.perform(fQualifiedNameComponent);
		GridData gd = (GridData) fQualifiedNameComponent.getLayoutData();
		gd.horizontalAlignment = GridData.FILL;
		gd.horizontalIndent = indent;

		boolean defaultSelection = getBooleanSetting(UPDATE_QUALIFIED_NAMES, ref.getUpdateQualifiedNames());
		fUpdateQualifiedNames.setSelection(defaultSelection);
		updateQulifiedNameUpdating(ref, defaultSelection);

		fUpdateQualifiedNames.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = ((Button) e.widget).getSelection();
				updateQulifiedNameUpdating(ref, enabled);
			}
		});
	}

	private void updateQulifiedNameUpdating(final IQualifiedNameUpdating ref, boolean enabled) {
		fQualifiedNameComponent.setEnabled(enabled);
		ref.setUpdateQualifiedNames(enabled);
		updateForcePreview();
	}

	protected String getLabelText() {
		return PHPRefactoringUIMessages.getString("RenameInputWizardPage_new_name"); //$NON-NLS-1$
	}

	protected boolean getBooleanSetting(String key, boolean defaultValue) {
		String update = getRefactoringSettings().get(key);
		if (update != null) {
			return Boolean.valueOf(update).booleanValue();
		} else {
			return defaultValue;
		}
	}

	protected void saveBooleanSetting(String key, Button checkBox) {
		if (checkBox != null) {
			getRefactoringSettings().put(key, checkBox.getSelection());
		}
	}

	protected static Button createCheckbox(Composite parent, String title, boolean value, RowLayouter layouter) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(title);
		checkBox.setSelection(value);
		layouter.perform(checkBox);
		return checkBox;
	}

	protected void updateForcePreview() {
		boolean forcePreview = false;
		Refactoring refactoring = getRefactoring();
		ITextUpdating tu = refactoring.getAdapter(ITextUpdating.class);
		IQualifiedNameUpdating qu = refactoring.getAdapter(IQualifiedNameUpdating.class);
		if (tu != null) {
			forcePreview = tu.getUpdateTextualMatches();
		}
		if (qu != null) {
			forcePreview |= qu.getUpdateQualifiedNames();
		}
		getRefactoringWizard().setForcePreviewReview(forcePreview);
	}
}
