package org.eclipse.php.internal.core;

import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public class EarlyStartup implements IStartup {

	public void earlyStartup() {
		// Dirty hack! Fix Subversive issue: listening to resource changes before plugin was loaded.
		Bundle svnBundle = Platform.getBundle("org.polarion.team.svn.core");
		if (svnBundle != null) {
			try {
				svnBundle.start();
			} catch (BundleException e) {
				Logger.logException(e);
			}
		}

		PHPWorkspaceModelManager.getInstance().startup();
		IncludePathVariableManager.instance().startUp();
	}
}
