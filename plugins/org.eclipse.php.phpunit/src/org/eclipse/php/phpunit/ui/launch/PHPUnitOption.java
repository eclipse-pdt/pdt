/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

public enum PHPUnitOption {

	INCLUDE_PATH("--include-path"), PRINTER("--printer"), LOG_JUNIT( //$NON-NLS-1$ //$NON-NLS-2$
			"--log-junit"), CONFIGURATION("--configuration"), //$NON-NLS-1$ //$NON-NLS-2$
	FILTER("--filter"), TEST_SUITE("--testsuite"); //$NON-NLS-1$ //$NON-NLS-2$

	private String optionName;

	private PHPUnitOption(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionName() {
		return optionName;
	}

}
