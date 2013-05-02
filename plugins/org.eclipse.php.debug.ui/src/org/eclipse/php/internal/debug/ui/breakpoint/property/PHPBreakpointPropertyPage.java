/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.breakpoint.property;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

import com.ibm.icu.text.MessageFormat;

public class PHPBreakpointPropertyPage extends PropertyPage implements
		IWorkbenchPropertyPage {

	private boolean conditionEnabled;
	private Text text;
	private PHPConditionalBreakpoint breakpoint;

	public PHPBreakpointPropertyPage() {
		noDefaultAndApplyButton();
	}

	protected Control createContents(Composite parent) {
		breakpoint = (PHPConditionalBreakpoint) getElement().getAdapter(
				PHPConditionalBreakpoint.class);
		if (breakpoint == null) {
			Logger.log(Logger.ERROR,
					"Could not adapt to PHPConditionalBreakpoint"); //$NON-NLS-1$
			return null;
		}
		conditionEnabled = breakpoint.isConditionEnabled();
		String currentCondition = breakpoint.getCondition();
		if (currentCondition.equals("")) { //$NON-NLS-1$
			conditionEnabled = true;
		}
		Label label = new Label(parent, SWT.WRAP);
		label.setText(MessageFormat.format(PHPDebugUIMessages.EnterCondition_1,
				new Object[] {}));
		GridData data = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_CENTER);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
		label.setLayoutData(data);
		label.setFont(parent.getFont());
		text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));
		text.setText(currentCondition);
		final Button checkbox = new Button(parent, SWT.CHECK);
		data = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
		checkbox.setLayoutData(data);
		checkbox.setFont(parent.getFont());
		checkbox.setText(MessageFormat.format(
				PHPDebugUIMessages.EnableSetCondition_1, new Object[] {}));
		checkbox.setSelection(conditionEnabled);
		text.setEnabled(conditionEnabled);

		checkbox.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				conditionEnabled = checkbox.getSelection();
				text.setEnabled(conditionEnabled);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});
		// HELP - waiting for keren
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
		// IStudioHelpContextIds.???);
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		if (breakpoint != null) {
			String condition = text.getText().trim();
			if (condition.equals("")) //$NON-NLS-1$
				conditionEnabled = false;
			try {
				breakpoint.setConditionWithEnable(conditionEnabled, condition);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		return super.performOk();
	}

}
