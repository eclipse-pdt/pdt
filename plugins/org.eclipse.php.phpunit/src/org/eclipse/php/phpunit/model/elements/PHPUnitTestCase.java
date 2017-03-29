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
package org.eclipse.php.phpunit.model.elements;

import java.util.List;
import java.util.Map;

import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.phpunit.model.connection.PHPUnitMessageParser;

public class PHPUnitTestCase extends PHPUnitTest {

	protected PHPUnitTestException exception = null;
	protected List<PHPUnitElement> warnings = null;

	public PHPUnitTestCase(final Map<?, ?> test, final PHPUnitTestGroup parent, RemoteDebugger remoteDebugger) {
		super(test, parent, remoteDebugger);
	}

	public PHPUnitTestCase(final Map<?, ?> test, final PHPUnitTestGroup parent, final String sStatus,
			RemoteDebugger remoteDebugger) {
		this(test, parent, remoteDebugger);
		updateStatus(sStatus);
	}

	public void updateStatus(String sStatus) {
		if (sStatus.equals(PHPUnitMessageParser.STATUS_PASS))
			status = STATUS_PASS;
		else if (sStatus.equals(PHPUnitMessageParser.STATUS_SKIP))
			status = STATUS_SKIP;
		else if (sStatus.equals(PHPUnitMessageParser.STATUS_INCOMPLETE))
			status = STATUS_INCOMPLETE;
		else if (sStatus.equals(PHPUnitMessageParser.STATUS_FAIL))
			status = STATUS_FAIL;
		else if (sStatus.equals(PHPUnitMessageParser.STATUS_ERROR))
			status = STATUS_ERROR;
		else if (sStatus.equals(PHPUnitMessageParser.TAG_START))
			status = STATUS_STARTED;
	}

	public PHPUnitTestException getException() {
		return exception;
	}

	public List<PHPUnitElement> getWarnings() {
		return warnings;
	}

	public void setException(final PHPUnitTestException exception) {
		this.exception = exception;

	}

	public void setWarnings(final List<PHPUnitElement> warnings) {
		this.warnings = warnings;
	}

}