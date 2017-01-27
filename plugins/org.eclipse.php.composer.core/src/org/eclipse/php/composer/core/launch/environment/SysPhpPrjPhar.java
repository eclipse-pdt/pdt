/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.launch.environment;

import java.io.File;

import org.apache.commons.exec.CommandLine;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;

public class SysPhpPrjPhar extends PrjPharEnvironment {

	private String php;

	public SysPhpPrjPhar(String executable) {
		php = executable;
	}

	public boolean isAvailable() {
		return php != null;
	}

	public CommandLine getCommand() {
		CommandLine cmd = new CommandLine(php.trim());

		// specify php.ini location
		File iniFile = PHPINIUtil.findPHPIni(php);
		if (iniFile != null) {
			cmd.addArgument("-c"); //$NON-NLS-1$
			cmd.addArgument(iniFile.getAbsolutePath());
		}

		// specify composer.phar location
		cmd.addArgument(phar.trim());
		return cmd;
	}

	@Override
	protected IResource getScript(IProject project) {
		return project.findMember("composer.phar"); //$NON-NLS-1$
	}
}
