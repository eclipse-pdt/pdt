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

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;

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
