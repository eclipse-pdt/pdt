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
package org.eclipse.php.debug.ui.actions;

import java.text.MessageFormat;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.actions.IVariableValueEditor;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.php.debug.core.model.PHPValue;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.widgets.Shell;

/**
 * A variable value editor that prompts the user to set a primitive's value.
 */
public class PHPVariableValueEditor implements IVariableValueEditor {

    /**
     * Creates a new editor for a variable with the given signature
     * @param signature the signature of the primitive to be edited
     */
    public PHPVariableValueEditor() {

    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.ui.actions.IVariableValueEditor#editVariable(org.eclipse.debug.core.model.IVariable, org.eclipse.swt.widgets.Shell)
     */
    public boolean editVariable(IVariable variable, Shell shell) {
        try {
            String name = variable.getName();
            String title = PHPDebugUIMessages.PHPPrimitiveValueEditor_0; //$NON-NLS-1$
            String message = MessageFormat.format(PHPDebugUIMessages.PHPPrimitiveValueEditor_1, new String[] { name }); //$NON-NLS-1$
            PHPValue value = (PHPValue) variable.getValue();
            String initialValue = value.getValue();
            PrimitiveValidator validator = new PrimitiveValidator();
            InputDialog dialog = new InputDialog(shell, title, message, initialValue, validator);
            if (dialog.open() == Window.OK) {
                String stringValue = dialog.getValue();
                variable.setValue(stringValue);
            }
        } catch (DebugException e) {
            DebugUIPlugin.errorDialog(shell, PHPDebugUIMessages.PHPPrimitiveValueEditor_2, PHPDebugUIMessages.PHPPrimitiveValueEditor_3, e); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return true;
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
        /* (non-Javadoc)
         * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
         */
        public String isValid(String newText) {
            // TODO Add some error checking code
            return null;
        }
    }

}
