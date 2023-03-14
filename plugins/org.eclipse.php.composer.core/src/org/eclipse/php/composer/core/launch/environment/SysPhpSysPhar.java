/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.launch.environment;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * An environment which uses a PHP executable selected by the user and a script
 * to global script to launch (also selected by the user).
 * 
 */
public class SysPhpSysPhar implements Environment {

	private String php;
	private String phar;

	public SysPhpSysPhar(String executable, String composerPhar) {
		php = executable;
		phar = composerPhar;
	}

	@Override
	public boolean isAvailable() {
		return php != null && phar != null;
	}

	@Override
	public void setUp(IProject project) {

	}

	@Override
	public ProcessBuilder getCommand() throws CoreException {
		return new ProcessBuilder(php, phar);
	}
}
