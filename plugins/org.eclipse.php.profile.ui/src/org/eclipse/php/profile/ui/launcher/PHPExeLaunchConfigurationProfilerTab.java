/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.launcher;

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
 * Profiler settings tab for PHP executable launch configurations.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPExeLaunchConfigurationProfilerTab extends AbstractPHPLaunchConfigurationProfilerTab {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab#handleConfigureProfiler()
	 */
	@Override
	protected void handleConfigureProfiler() {
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
	 * @see org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab#getCurrentProfilerId()
	 */
	@Override
	protected String getCurrentProfilerId() {
		PHPexeItem phpExe = getPHPExe();
		if (phpExe == null) {
			return PHPDebuggersRegistry.NONE_DEBUGGER_ID;
		}
		return phpExe.getDebuggerID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab#getNoProfilerMessage()
	 */
	@Override
	protected String getNoProfilerMessage() {
		return MessageFormat.format(Messages.PHPExeLaunchConfigurationProfilerTab_No_profiler_is_attached,
				getPHPExe() != null ? getPHPExe().getName() : Messages.PHPExeLaunchConfigurationProfilerTab_None);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab#updateProfileTest()
	 */
	@Override
	protected void updateProfileTest() {
		validateProfilerBtn.setVisible(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab#performProfileTest()
	 */
	@Override
	protected void performProfileTest() {
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
