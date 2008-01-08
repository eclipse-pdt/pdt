/*******************************************************************************
 * Copyright (c) 2006 Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.actions;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.internal.ui.actions.variables.ChangeVariableValueInputDialog;
import org.eclipse.debug.ui.actions.IVariableValueEditor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStringValue;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpVariable;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Node;

/**
 * A variable value editor that prompts the user to set a primitive's value.
 */
public class XDebugVariableValueEditor implements IVariableValueEditor {

	/**
	 * Creates a new editor for a variable with the given signature
	 * @param signature the signature of the primitive to be edited
	 */
	public XDebugVariableValueEditor() {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IVariableValueEditor#editVariable(org.eclipse.debug.core.model.IVariable, org.eclipse.swt.widgets.Shell)
	 */
	public boolean editVariable(IVariable variable, Shell shell) {
		try {
			String name = variable.getName();
			String title = "Change Value"; // PHPDebugUIMessages.PHPPrimitiveValueEditor_0
			// PHPDebugUIMessages.PHPPrimitiveValueEditor_1
			String message = MessageFormat.format("Enter a new value for {0}:", new Object[] { name }); //$NON-NLS-1$
			String initialValue = getValueString(variable);
			
			PrimitiveValidator validator = new PrimitiveValidator(variable);
			ChangeVariableValueInputDialog dialog = new ChangeVariableValueInputDialog(shell, title, message, initialValue, validator);
			if (dialog.open() == Window.OK) {
				String stringValue = dialog.getValue();
				variable.setValue(stringValue);
			}
		} catch (DebugException e) {
			IStatus status = e.getStatus();
			//         PHPDebugUIMessages.PHPPrimitiveValueEditor_2, // PHPDebugUIMessages.PHPPrimitiveValueEditor_3           
			ErrorDialog.openError(shell, "Error Changing Value", "An exception occurred attempting to change the variable value.", status);
		}
		return true;
	}

	private String getValueString(IVariable variable) throws DebugException {
		IValue value = variable.getValue();
		String initialValue = value.getValueString();
		
		if (value instanceof DBGpStringValue) {
			DBGpStringValue strValue = (DBGpStringValue)value;
			if (strValue.isComplete() == false) {
				DBGpTarget target = (DBGpTarget)value.getDebugTarget();
				DBGpVariable dbgpVar = (DBGpVariable)variable;
				String stackLevel = dbgpVar.getStackLevel();
				Node result = target.getCompleteString(dbgpVar.getFullName(), stackLevel, strValue.getRequiredBytes());
				if (result != null) {
					IVariable tempVar = new DBGpVariable(target, result, stackLevel);
					IValue valRes = null;
					try {
						valRes = tempVar.getValue();
						if (valRes != null) {
							initialValue = valRes.getValueString();
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return initialValue;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IVariableValueEditor#saveVariable(org.eclipse.debug.core.model.IVariable, java.lang.String, org.eclipse.swt.widgets.Shell)
	 */
	public boolean saveVariable(IVariable variable, String expression, Shell shell) {
		return false;
	}

	/**
	 * Input validator for primitive types
	 */
	protected class PrimitiveValidator implements IInputValidator {

		IVariable var;

		public PrimitiveValidator(IVariable var) {
			this.var = var;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
		 */
		public String isValid(String newText) {
			String errorMsg = null;
			try {
				if (!var.verifyValue(newText)) {
					errorMsg = "Invalid entry for variable";
				}
			} catch (DebugException e) {
				Logger.logException("DebugException", e);
				errorMsg = "unexpected error occurred, see log for details";
			}
			return errorMsg;
		}
	}

}
