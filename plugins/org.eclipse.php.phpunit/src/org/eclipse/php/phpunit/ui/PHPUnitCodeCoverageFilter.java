package org.eclipse.php.phpunit.ui;

import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.ui.views.coverage.ICodeCoverageFilter;
import org.eclipse.php.phpunit.ui.launch.PHPUnitLaunchConfigurationDelegate;

public class PHPUnitCodeCoverageFilter implements ICodeCoverageFilter {

	private static final String PHAR_PREFIX = "phar://"; //$NON-NLS-1$
	private static final String PRINTER = PHPUnitLaunchConfigurationDelegate.PRINTER_NAME + ".php"; //$NON-NLS-1$

	@Override
	public boolean isFiltered(VirtualPath path) {
		if (path.toString().startsWith(PHAR_PREFIX) || path.toString().endsWith(PRINTER)) {
			return true;
		}

		return false;
	}
}
