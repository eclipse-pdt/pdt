/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.wst.server.core.model.RuntimeDelegate;

@SuppressWarnings("restriction")
public class PHPRuntime extends RuntimeDelegate implements IPHPRuntimeWorkingCopy {

	protected static final String PROP_EXECUTABLE_INSTALL_NAME = "executable-install-name"; //$NON-NLS-1$
	private PHPexeItem exeItem;

	@Override
	public IStatus validate() {
		IStatus status = super.validate();
		if (!status.isOK())
			return status;

		String id = getRuntime().getRuntimeType().getId();

		File f = getRuntime().getLocation().toFile();
		if (!f.canRead())
			return new Status(IStatus.WARNING, PHPServerPlugin.PLUGIN_ID, 0, Messages.warningCantReadDirectory, null);
		PHPexeItem installedItem = getExecutableInstall();

		if (!installedItem.getSapiType().equals("CLI")) { //$NON-NLS-1$
			return new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					"Only the CLI SAPI provides a built-in web server", null); //$NON-NLS-1$
		}

		String[] splitVersion = installedItem.getVersion().split("\\.", 3); //$NON-NLS-1$
		String mainVersion = splitVersion[0] + splitVersion[1];
		if (!id.endsWith(mainVersion)) {
			return new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					"Selected PHP executable does not match the target PHP version", null); //$NON-NLS-1$
		}

		return Status.OK_STATUS;
	}

	@Override
	public void setExecutableInstall(PHPexeItem item) {
		if (item == null) {
			internalSetExecutableInstall(null);
		} else
			internalSetExecutableInstall(item.getName());
		exeItem = item;
	}

	private void internalSetExecutableInstall(String name) {
		if (name == null)
			setAttribute(PROP_EXECUTABLE_INSTALL_NAME, (String) null);
		else
			setAttribute(PROP_EXECUTABLE_INSTALL_NAME, name);
	}

	@Override
	public PHPexeItem getExecutableInstall() {
		if (exeItem != null)
			return exeItem;
		if (getExecutableInstallName() == null)
			return null;
		try {
			String name = getExecutableInstallName();
			return PHPexes.getInstance().getItem(name);
		} catch (Exception e) {
			// ignore
		}
		return null;
	}

	protected String getExecutableInstallName() {
		return getAttribute(PROP_EXECUTABLE_INSTALL_NAME, (String) null);
	}

}
