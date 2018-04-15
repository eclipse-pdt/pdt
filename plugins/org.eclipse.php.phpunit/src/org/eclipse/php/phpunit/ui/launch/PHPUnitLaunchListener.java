/*******************************************************************************
 * Copyright (c) 2018 Michał Niewrzał and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Michał Niewrzał - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.php.phpunit.model.connection.PHPUnitMessageParser;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;

public class PHPUnitLaunchListener implements ILaunchesListener2 {

	private ILaunch launch;

	public PHPUnitLaunchListener(ILaunch launch) {
		this.launch = launch;
	}

	@Override
	public void launchesTerminated(ILaunch[] launches) {
		for (ILaunch launche : launches) {
			if (launche == launch && launche.isTerminated()) {
				if (!PHPUnitMessageParser.getInstance().isInProgress()) {
					PHPUnitView.getDefault().stopRunning(true);
				}
				DebugPlugin.getDefault().getLaunchManager().removeLaunchListener(this);
			}
		}
	}

	@Override
	public void launchesAdded(ILaunch[] arg0) {
		// skip
	}

	@Override
	public void launchesChanged(ILaunch[] arg0) {
		// skip
	}

	@Override
	public void launchesRemoved(ILaunch[] arg0) {
		// skip
	}

}
