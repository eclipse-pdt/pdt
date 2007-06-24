/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.analysis;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;


public class AnalysisRunnerForPDT implements IApplication {

	public Object start(IApplicationContext context) throws Exception {
		final String[] commandLineArgs = Platform.getCommandLineArgs();
		
		String root = null;
		List<String> projectNames = new ArrayList<String>();
		String rulesFilename = null;
		String outputFilename = null;
		
		for (int i = 0; i < commandLineArgs.length; i++) {
			String string = commandLineArgs[i];
			if (string.equals("-root")) {
				root = commandLineArgs[++i];
			}
			if (string.equals("-projectName")) {
				projectNames.add(commandLineArgs[++i]);
			}
			if (string.equals("-rules")) {
				rulesFilename = commandLineArgs[++i];
			}
			if (string.equals("-output")) {
				outputFilename = commandLineArgs[++i];
			}			
		}
		
		final AnalysisLaunchConfigurationDelegate analysisLaunchConfigurationDelegate = new AnalysisLaunchConfigurationDelegate(rulesFilename, outputFilename);
		analysisLaunchConfigurationDelegate.launch(new LaunchConfigurationStub(root, projectNames), "analyze", new AnalysisLaunch(), new NullProgressMonitor());
		return null;
	}

	public void stop() {
	}
}
