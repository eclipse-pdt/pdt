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

import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.model.connection.MessageException;
import org.eclipse.php.phpunit.model.connection.PHPUnitMessageParser;

public class PHPUnitTestException extends PHPUnitTestEvent {

	private static final String TOP_CLASS = "Exception"; //$NON-NLS-1$
	private String exceptionClass = TOP_CLASS;

	public PHPUnitTestException(MessageException exception, PHPUnitTest parent, RemoteDebugger remoteDebugger) {
		super(exception, parent, remoteDebugger);
		exceptionClass = (String) exception.getClazz();
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	@Override
	public String toString() {
		return file + SEPARATOR_LINE + String.valueOf(line) + SEPARATOR_NAME + exceptionClass + SEPARATOR_MESSAGE
				+ message;
	}

	public static void addAbnormalException(PHPUnitTestCase testCase) {
		// if(ABNORMAL_EXCEPTION == null) {
		MessageException exception = new MessageException(PHPUnitMessages.PHPUnitTestException_0,
				PHPUnitMessages.PHPUnitTestException_1);
		PHPUnitTestException abnormalException = new PHPUnitTestException(exception, null, null);
		abnormalException.setParent(testCase);
		testCase.setException(abnormalException);
		testCase.setStatus(PHPUnitTest.STATUS_ERROR);
		PHPUnitTestGroup parent = (PHPUnitTestGroup) testCase.getParent();
		if (parent != null) {
			parent.setStatus(testCase.getStatus());
		}
		PHPUnitMessageParser.getInstance().mapException(testCase, exception);
	}
}