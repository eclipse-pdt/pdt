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
import org.eclipse.php.phpunit.model.connection.MessageException;

public class PHPUnitTestWarning extends PHPUnitTestEvent {

	private String code = ""; //$NON-NLS-1$

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