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

import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.phpunit.model.connection.MessageEventType;
import org.eclipse.php.phpunit.model.connection.MessageTest;

public class PHPUnitTest extends PHPUnitElement {

	public static final int STATUS_STARTED = 0;
	public static final int STATUS_PASS = 1;
	public static final int STATUS_SKIP = 2;
	public static final int STATUS_INCOMPLETE = 3;
	public static final int STATUS_FAIL = 4;
	public static final int STATUS_ERROR = 5;
	public static final String METHOD_SEPARATOR = "::"; //$NON-NLS-1$

	protected PHPUnitTestException exception = null;
	protected List<PHPUnitElement> warnings = null;
	protected String name = ""; //$NON-NLS-1$
	protected int status = 0;

	public PHPUnitTest(MessageTest test, final PHPUnitTestGroup parent, RemoteDebugger remoteDebugger) {
		super(test, parent, remoteDebugger);
		if (test != null) {
			processName(test.getName());
		}
	}

	protected void processName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getRunCount() {
		return 1;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	/**
	 * Map and save parser status to model status
	 * 
	 * @param sStatus
	 */
	public void setStatus(MessageEventType sStatus) {
		if (sStatus == MessageEventType.pass) {
			setStatus(STATUS_PASS);
		} else if (sStatus == MessageEventType.skip) {
			setStatus(STATUS_SKIP);
		} else if (sStatus == MessageEventType.incomplete) {
			setStatus(STATUS_INCOMPLETE);
		} else if (sStatus == MessageEventType.fail) {
			setStatus(STATUS_FAIL);
		} else if (sStatus == MessageEventType.error) {
			setStatus(STATUS_ERROR);
		} else if (sStatus == MessageEventType.start) {
			setStatus(STATUS_STARTED);
		}
	}

	public String getFilterName() {
		return this.getName();
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

	@Override
	public String toString() {
		return file + SEPARATOR_LINE + String.valueOf(line) + SEPARATOR_NAME + name;
	}

}