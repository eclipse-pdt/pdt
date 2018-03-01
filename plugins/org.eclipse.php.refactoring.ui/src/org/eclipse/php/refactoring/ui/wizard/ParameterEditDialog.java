/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.internal.ui.dialogs.TextFieldNavigationHandler;
import org.eclipse.dltk.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.refactoring.core.extract.function.ParameterInfo;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.php.refactoring.ui.utils.PHPConventionsUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ParameterEditDialog extends StatusDialog {

	private final ParameterInfo fParameter;

	private final boolean fEditDefault;
	private final StubTypeContext fContext;
	private Text fType; // XXX: never set
	private Text fName;
	private Text fDefaultValue;

	/**
	 * @param parentShell
	 * @param parameter
	 * @param canEditType
	 * @param canEditDefault
	 * @param context
	 *            the <code>IPackageFragment</code> for type ContentAssist. Can
	 *            be <code>null</code> if <code>canEditType</code> is
	 *            <code>false</code>.
	 */
	public ParameterEditDialog(Shell parentShell, ParameterInfo parameter, boolean canEditDefault,
			StubTypeContext context) {
		super(parentShell);
		fParameter = parameter;

		fEditDefault = canEditDefault;
		fContext = context;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(RefactoringMessages.ParameterEditDialog_title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite result = (Composite) super.createDialogArea(parent);
		GridLayout layout = (GridLayout) result.getLayout();
		layout.numColumns = 2;
		Label label;
		GridData gd;

		label = new Label(result, SWT.NONE);
		String newName = fParameter.getNewDisplayName();
		if (newName.length() == 0) {
			label.setText(RefactoringMessages.ParameterEditDialog_message_new);
		} else {
			label.setText(NLS.bind(RefactoringMessages.ParameterEditDialog_message, newName));
		}
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		label = new Label(result, SWT.NONE);
		fName = new Text(result, SWT.BORDER);
		initializeDialogUnits(fName);
		label.setText(RefactoringMessages.ParameterEditDialog_name);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = convertWidthInCharsToPixels(45);
		fName.setLayoutData(gd);
		fName.setText(newName);
		fName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validate((Text) e.widget);
			}
		});
		TextFieldNavigationHandler.install(fName);

		if (fEditDefault && fParameter.isAdded()) {
			label = new Label(result, SWT.NONE);
			label.setText(RefactoringMessages.ParameterEditDialog_defaultValue);
			fDefaultValue = new Text(result, SWT.BORDER);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			fDefaultValue.setLayoutData(gd);
			fDefaultValue.setText(fParameter.getDefaultValue());
			fDefaultValue.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					validate((Text) e.widget);
				}
			});
			TextFieldNavigationHandler.install(fDefaultValue);
		}
		applyDialogFont(result);
		return result;
	}

	@Override
	protected void okPressed() {
		if (fType != null) {
			fParameter.setNewTypeName(fType.getText());
		}
		fParameter.setNewName(fName.getText());
		if (fDefaultValue != null) {
			fParameter.setDefaultValue(fDefaultValue.getText());
		}
		super.okPressed();
	}

	private void validate(Text first) {
		IStatus[] result = new IStatus[3];
		if (first == fType) {
			result[0] = validateType();
			result[1] = validateName();
			result[2] = validateDefaultValue();
		} else if (first == fName) {
			result[0] = validateName();
			result[1] = validateType();
			result[2] = validateDefaultValue();
		} else {
			result[0] = validateDefaultValue();
			result[1] = validateName();
			result[2] = validateType();
		}
		for (int i = 0; i < result.length; i++) {
			IStatus status = result[i];
			if (status != null && !status.isOK()) {
				updateStatus(status);
				return;
			}
		}
		updateStatus(createOkStatus());
	}

	private IStatus validateType() {
		if (fType == null) {
			return null;
		}
		String type = fType.getText();

		RefactoringStatus status = PHPConventionsUtil.checkParameterTypeSyntax(type,
				fContext.getCuHandle().getSourceModule().getScriptProject());
		if (status == null || status.isOK()) {
			return createOkStatus();
		}
		if (status.hasError()) {
			return createErrorStatus(status.getEntryWithHighestSeverity().getMessage());
		} else {
			return createWarningStatus(status.getEntryWithHighestSeverity().getMessage());
		}
	}

	private IStatus validateName() {
		if (fName == null) {
			return null;
		}
		String text = fName.getText();
		if (text.length() == 0) {
			return createErrorStatus(RefactoringMessages.ParameterEditDialog_name_error);
		}
		IStatus status = PHPConventionsUtil.validateFieldName(text);

		if (status.matches(IStatus.ERROR)) {
			return status;
		}
		if (!PHPConventionsUtil.startsWithLowerCase(text)) {
			return createWarningStatus(RefactoringCoreMessages.ExtractTempRefactoring_convention);
		}
		return createOkStatus();
	}

	private IStatus validateDefaultValue() {
		if (fDefaultValue == null) {
			return null;
		}
		String defaultValue = fDefaultValue.getText();
		if (defaultValue.length() == 0) {
			return createErrorStatus(RefactoringMessages.ParameterEditDialog_defaultValue_error);
		}
		// if (ChangeSignatureProcessor.isValidExpression(defaultValue))
		// return createOkStatus();
		String msg = RefactoringMessages.bind(RefactoringMessages.ParameterEditDialog_defaultValue_invalid,
				new String[] { defaultValue });
		return createErrorStatus(msg);

	}

	private Status createOkStatus() {
		return new Status(IStatus.OK, RefactoringUIPlugin.PLUGIN_ID, IStatus.OK, "", null); //$NON-NLS-1$
	}

	private Status createWarningStatus(String message) {
		return new Status(IStatus.WARNING, RefactoringUIPlugin.PLUGIN_ID, IStatus.WARNING, message, null);
	}

	private Status createErrorStatus(String message) {
		return new Status(IStatus.ERROR, RefactoringUIPlugin.PLUGIN_ID, IStatus.ERROR, message, null);
	}
}
