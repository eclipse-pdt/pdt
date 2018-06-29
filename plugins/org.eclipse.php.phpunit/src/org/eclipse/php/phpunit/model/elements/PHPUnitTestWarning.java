/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.model.elements;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.phpunit.model.connection.MessageException;

public class PHPUnitTestWarning extends PHPUnitTestEvent {

	private String code = StringUtils.EMPTY;

	public PHPUnitTestWarning(MessageException warning, final PHPUnitElement parent, RemoteDebugger remoteDebugger) {
		super(warning, parent, remoteDebugger);
		code = warning.getCode();
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return file + SEPARATOR_LINE + String.valueOf(line) + SEPARATOR_NAME + code + SEPARATOR_MESSAGE + message;
	}
}