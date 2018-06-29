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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint;
import org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint.Type;
import org.eclipse.php.internal.debug.core.model.PHPExceptionBreakpoint;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.actions.AddPHPExceptionBreakpointDialog.ErrorType;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Add PHP exception breakpoint command handler.
 * 
 * @author Bartlomiej Laczkowski
 */
public class AddPHPExceptionBreakpointHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SelectionDialog dialog = createDialog();
		int result = dialog.open();
		if (result != IDialogConstants.OK_ID) {
			return null;
		}
		Object[] types = dialog.getResult();
		addBreakpoints(types);
		return null;
	}

	protected SelectionDialog createDialog() {
		AddPHPExceptionBreakpointDialog dialog = new AddPHPExceptionBreakpointDialog();
		dialog.setTitle(Messages.AddPHPExceptionBreakpointHandler_Dialog_title);
		return dialog;
	}

	protected void addBreakpoints(Object[] exceptions) {
		for (Object exception : exceptions) {
			IType type = (IType) exception;
			String exceptionName = type.getElementName();
			if (hasBreakpoint(exceptionName)) {
				continue;
			}
			Breakpoint exceptionBreakpoint;
			if (type instanceof ErrorType) {
				exceptionBreakpoint = new PHPExceptionBreakpoint(exceptionName, Type.ERROR);
			} else {
				exceptionBreakpoint = new PHPExceptionBreakpoint(exceptionName, Type.EXCEPTION);
			}
			try {
				DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(exceptionBreakpoint);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
	}

	protected boolean hasBreakpoint(String exceptionName) {
		for (IBreakpoint breakpoint : DebugPlugin.getDefault().getBreakpointManager()
				.getBreakpoints(IPHPDebugConstants.ID_PHP_DEBUG_CORE)) {
			if (breakpoint instanceof IPHPExceptionBreakpoint) {
				IPHPExceptionBreakpoint exceptionBreakpoint = (IPHPExceptionBreakpoint) breakpoint;
				if (exceptionBreakpoint.getExceptionName().equals(exceptionName)) {
					return true;
				}
			}
		}
		return false;
	}

}
