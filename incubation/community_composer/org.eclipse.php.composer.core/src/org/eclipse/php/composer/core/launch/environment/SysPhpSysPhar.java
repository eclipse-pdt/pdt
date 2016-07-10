/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.launch.environment;

import org.apache.commons.exec.CommandLine;
import org.eclipse.core.resources.IProject;

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

	public boolean isAvailable() {
		return php != null && phar != null;
	}

	public void setUp(IProject project) {

	}

	public CommandLine getCommand() {
		CommandLine cmd = new CommandLine(php.trim());
		cmd.addArgument(phar.trim());

		return cmd;
	}
}
