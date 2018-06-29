/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Dawid Pakuła - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.test;

import org.eclipse.php.core.tests.TestAllSuiteWatcher;
import org.eclipse.php.profile.core.test.cachegrind.CacheGrindParserTest;
import org.junit.ClassRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CacheGrindParserTest.class })
public class AllTests {

	@ClassRule
	public static TestWatcher watcher = new TestAllSuiteWatcher();

}
