/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
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
package org.eclipse.php.internal.core.ast.locator;

import org.eclipse.php.core.PHPVersion;
import org.junit.BeforeClass;

public class PHPElementConciliatorV5_6Test extends PHPElementConciliatorV5_5Test {

	static {
		phpVersion = PHPVersion.PHP5_6;
	}

	@BeforeClass
	public static void setUpSuite() throws Exception {
		AbstractConciliatorTest.setUpSuite();
	}
}
