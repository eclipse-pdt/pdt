package org.eclipse.php.phpunit.debug;

import org.eclipse.php.internal.debug.core.zend.model.ServerDebugHandler;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;

public class PHPUnitDebugHandler extends ServerDebugHandler {

	public PHPUnitDebugHandler() {
		super();
	}

	@Override
	public void handleScriptEnded() {
		super.handleScriptEnded();
		PHPUnitView.getDefault().showCodeCoverage(getLastCodeCoverageData());

	}

}
