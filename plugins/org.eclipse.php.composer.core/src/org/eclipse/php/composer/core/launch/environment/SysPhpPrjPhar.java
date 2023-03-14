/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.launch.environment;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;

public class SysPhpPrjPhar extends PrjPharEnvironment {

	private String php;

	public SysPhpPrjPhar(String executable) {
		php = executable;
	}

	@Override
	public boolean isAvailable() {
		return php != null;
	}

	@Override
	public ProcessBuilder getCommand() throws CoreException {
		ProcessBuilder pb = new ProcessBuilder(php);

		// specify php.ini location
		File iniFile = PHPINIUtil.findPHPIni(php);
		if (iniFile != null) {
			pb.command().add("-c");//$NON-NLS-1$
			pb.command().add(iniFile.getAbsolutePath());
		}
		pb.command().add(phar);

		return pb;
	}

	@Override
	protected IResource getScript(IProject project) {
		return project.findMember("composer.phar"); //$NON-NLS-1$
	}
}
