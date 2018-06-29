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
package org.eclipse.php.internal.debug.ui.launching;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.wizards.DebuggerCompositeFragment;
import org.eclipse.php.internal.debug.ui.wizards.PHPExeEditDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Debugger settings tab for PHP executable launch configurations.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPExeLaunchConfigurationDebuggerTab extends AbstractPHPLaunchConfigurationDebuggerTab {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#handleConfigureDebugger()
	 */
	@Override
	protected void handleConfigureDebugger() {
		PHPexeItem phpExe = getPHPExe();
		if (phpExe != null) {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			NullProgressMonitor monitor = new NullProgressMonitor();
			PHPExeEditDialog dialog = new PHPExeEditDialog(shell, phpExe, PHPexes.getInstance().getAllItems(),
					DebuggerCompositeFragment.ID);
			if (dialog.open() == Window.CANCEL) {
				monitor.setCanceled(true);
				return;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#getCurrentDebuggerId()
	 */
	@Override
	protected String getCurrentDebuggerId() {
		PHPexeItem phpExe = getPHPExe();
		if (phpExe == null) {
			return PHPDebuggersRegistry.NONE_DEBUGGER_ID;
		}
		return phpExe.getDebuggerID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#getNoDebuggerMessage()
	 */
	@Override
	protected String getNoDebuggerMessage() {
		return MessageFormat.format(
				Messages.PHPExeLaunchConfigurationDebuggerTab_No_debugger_is_attached_to_configuration,
				getPHPExe() != null ? getPHPExe().getName() : Messages.PHPExeLaunchConfigurationDebuggerTab_none);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#updateDebugTest()
	 */
	@Override
	protected void updateDebugTest() {
		validateDebuggerBtn.setVisible(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab#performDebugTest()
	 */
	@Override
	protected void performDebugTest() {
		// No tests for CLI
	}

	private PHPexeItem getPHPExe() {
		PHPexeItem phpExe = null;
		try {
			phpExe = PHPLaunchUtilities.getPHPExe(getConfiguration());
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return phpExe;
	}

}
