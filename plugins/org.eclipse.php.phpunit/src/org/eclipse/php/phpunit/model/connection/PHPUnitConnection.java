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
package org.eclipse.php.phpunit.model.connection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;
import org.eclipse.swt.widgets.Display;

public class PHPUnitConnection {
	protected static PHPUnitConnection instance = null;
	private PHPUnitConnectionListener listener;

	public static PHPUnitConnection getInstance() {
		if (instance == null) {
			instance = new PHPUnitConnection();
		}
		return instance;
	}

	private PHPUnitConnection() {
	}

	public boolean listen(final int port, final ILaunch launch) {
		PHPUnitView.activateView(true);
		PHPUnitMessageParser.getInstance().clean();
		if (PHPUnitView.getDefault().isRunning()) {
			Display.getDefault()
					.syncExec(() -> ErrorDialog.openError(Display.getCurrent().getActiveShell(),
							PHPUnitMessages.PHPUnitConnection_Launching,
							PHPUnitMessages.PHPUnitConnection_Unable_to_run, new Status(IStatus.ERROR, PHPUnitPlugin.ID,
									0, PHPUnitMessages.PHPUnitConnection_Previous_session_exists, null)));
			return false;
		}
		listener = new PHPUnitConnectionListener(port, launch);
		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(listener);
		PHPUnitView.getDefault().startRunning(launch, listener);

		listener.start();
		return true;
	}

	public PHPUnitConnectionListener getListener() {
		return listener;
	}

}
