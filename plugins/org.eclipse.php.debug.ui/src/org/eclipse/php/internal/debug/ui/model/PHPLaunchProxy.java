/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.model;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ModelDelta;
import org.eclipse.debug.internal.ui.viewers.update.LaunchProxy;
import org.eclipse.php.internal.debug.core.launching.PHPLaunch;

/**
 * PHP launch element proxy.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPLaunchProxy extends LaunchProxy {

	private PHPLaunch fLaunch;

	public PHPLaunchProxy(PHPLaunch launch) {
		super(launch);
		this.fLaunch = launch;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.LaunchProxy#dispose()
	 */
	@Override
	public void dispose() {
		this.fLaunch = null;
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.LaunchProxy#
	 * launchesTerminated (org.eclipse.debug.core.ILaunch[])
	 */
	@Override
	public void launchesTerminated(ILaunch[] launches) {
		// Do not react on termination
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.LaunchProxy#launchesRemoved
	 * (org.eclipse.debug.core.ILaunch[])
	 */
	@Override
	public void launchesRemoved(ILaunch[] launches) {
		uninstallModelProxies(launches);
		super.launchesRemoved(launches);
	}

	/**
	 * This method will uninstall all sub-proxies related to this launch proxy.
	 * 
	 * @param launches
	 */
	protected void uninstallModelProxies(ILaunch[] launches) {
		for (ILaunch launch : launches) {
			if (this.fLaunch == launch) {
				ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
				ModelDelta delta = new ModelDelta(manager, 0, IModelDelta.NO_CHANGE, manager.getLaunches().length);
				for (Object element : fLaunch.getChildren()) {
					delta.addNode(element, IModelDelta.UNINSTALL);
				}
				fireModelChanged(delta);
			}
		}
	}

}
