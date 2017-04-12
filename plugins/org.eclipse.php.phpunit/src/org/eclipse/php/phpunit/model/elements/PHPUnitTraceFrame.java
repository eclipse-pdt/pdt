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

import java.util.Map;

import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.phpunit.model.connection.PHPUnitMessageParser;

public class PHPUnitTraceFrame extends PHPUnitElement {

	private String traceFunction = ""; //$NON-NLS-1$
	private String traceClass = ""; //$NON-NLS-1$
	private String traceType = PHPUnitMessageParser.CALL_DYNAMIC;

	public PHPUnitTraceFrame(final Map<?, ?> frame, final PHPUnitTestEvent parent, RemoteDebugger remoteDebugger) {
		super(frame, parent, remoteDebugger);
		traceFunction = (String) frame.get("function"); //$NON-NLS-1$
		traceClass = (String) frame.get("class"); //$NON-NLS-1$
		traceType = (String) frame.get("type"); //$NON-NLS-1$
	}

	public String getFunction() {
		return traceFunction;
	}

	public String getClassName() {
		return traceClass;
	}

	public String getTraceType() {
		return traceType;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder(1);
		if (traceClass != null)
			result.append(traceClass);
		if (traceType != null)
			result.append(traceType);
		result.append(traceFunction);
		return result.toString();
	}
}