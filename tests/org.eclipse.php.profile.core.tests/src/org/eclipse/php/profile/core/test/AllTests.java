/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
