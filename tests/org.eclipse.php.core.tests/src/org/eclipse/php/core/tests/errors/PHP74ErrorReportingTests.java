/*******************************************************************************
 * Copyright (c) 20019 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests.errors;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class PHP74ErrorReportingTests extends AbstractErrorReportingTests {

	@Parameters
	public static final String[] TEST_DIRS = { "/workspace/errors/php54", "/workspace/errors/php55",
			"/workspace/errors/php56", "/workspace/errors/php7", "/workspace/errors/php71", "/workspace/errors/php72",
			"/workspace/errors/php73", "/workspace/errors/php74" };

	public PHP74ErrorReportingTests(String[] fileNames) {
		super(fileNames);
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP7_4;
	}
}
