/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
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
package org.eclipse.php.internal.debug.ui.actions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.internal.ui.actions.variables.ChangeVariableValueInputDialog;
import org.eclipse.debug.ui.actions.IVariableValueEditor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.widgets.Shell;

import com.ibm.icu.text.MessageFormat;

/**
 * Abstract implementation of PHP variable value editor.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractVariableValueEditor implements IVariableValueEditor {

	protected class ValueValidator implements IInputValidator {

		IVariable variable;

		public ValueValidator(IVariable variable) {
			this.variable = variable;
		}

		@Override
		public String isValid(String value) {
			String errorMsg = null;
			try {
				if (!variable.verifyValue(value)) {
					errorMsg = PHPDebugUIMessages.AbstractVariableValueEditor_InvalidValue;
				}
			} catch (DebugException e) {
				Logger.logException(e);
				errorMsg = PHPDebugUIMessages.AbstractVariableValueEditor_UnexpectedError;
			}
			return errorMsg;
		}

	}

	/**
	 * Returns input value string.
	 * 
	 * @param variable
	 * @return input value string
	 * @throws DebugException
	 */
	protected abstract String getValueString(IVariable variable) throws DebugException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.actions.IVariableValueEditor#editVariable(org.
	 * eclipse .debug.core.model.IVariable, org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public boolean editVariable(IVariable variable, Shell shell) {
		try {
			String name = variable.getName();
			String title = PHPDebugUIMessages.AbstractVariableValueEditor_ChangeValueTitle;
			String message = MessageFormat.format(PHPDebugUIMessages.AbstractVariableValueEditor_EnterValue,
					new Object[] { name });
			String initialValue = getValueString(variable);
			ValueValidator validator = new ValueValidator(variable);
			ChangeVariableValueInputDialog dialog = new ChangeVariableValueInputDialog(shell, title, message,
					initialValue, validator);
			if (dialog.open() == Window.OK) {
				String stringValue = dialog.getValue();
				variable.setValue(stringValue);
			} else {

			}
		} catch (DebugException e) {
			IStatus status = e.getStatus();
			ErrorDialog.openError(shell, PHPDebugUIMessages.AbstractVariableValueEditor_ErrorChangingValue,
					PHPDebugUIMessages.AbstractVariableValueEditor_UnexpectedErrorWhileValueChange, status);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.actions.IVariableValueEditor#saveVariable(org.
	 * eclipse .debug.core.model.IVariable, java.lang.String,
	 * org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public boolean saveVariable(IVariable variable, String expression, Shell shell) {
		return false;
	}

}
