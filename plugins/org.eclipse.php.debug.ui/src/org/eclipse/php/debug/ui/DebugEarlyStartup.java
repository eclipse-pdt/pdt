/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.debug.ui;

import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.zend.communication.RemoteFileContentRequestorsRegistry;
import org.eclipse.php.internal.debug.ui.launching.OpenRemoteFileExternalRequestor;
import org.eclipse.php.internal.debug.ui.preferences.phps.PHPExeVerifier;
import org.eclipse.ui.IStartup;

/**
 * This class is intended to perform early startup of a debugger plug-ins. The
 * main goal of PDT debugger plug-ins early startup is to trigger the background
 * thread that performs all of the heavy weight operations right after the
 * workbench startup.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DebugEarlyStartup implements IStartup {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	@Override
	public void earlyStartup() {
		RemoteFileContentRequestorsRegistry.getInstance()
				.addExternalRequestHandler(new OpenRemoteFileExternalRequestor());
		// Verify all of the available PHP executables
		PHPExeVerifier.verify(PHPexes.getInstance().getAllItems());
	}

}
