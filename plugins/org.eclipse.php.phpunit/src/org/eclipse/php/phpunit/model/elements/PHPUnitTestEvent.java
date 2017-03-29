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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.phpunit.model.connection.PHPUnitMessageParser;

public abstract class PHPUnitTestEvent extends PHPUnitElement {

	protected String message;
	protected String diff;
	protected List<PHPUnitTraceFrame> trace;

	public PHPUnitTestEvent(final Map<?, ?> event, final PHPUnitElement parent, RemoteDebugger remoteDebugger) {
		super(event, parent, remoteDebugger);
		message = (String) event.get(PHPUnitMessageParser.PROPERTY_MESSAGE);
		diff = (String) event.get(PHPUnitMessageParser.PROPERTY_DIFF);
		final Map<?, ?> mTrace = (Map<?, ?>) event.get(PHPUnitMessageParser.PROPERTY_TRACE);
		if (mTrace == null || mTrace.isEmpty())
			return;
		trace = new ArrayList<PHPUnitTraceFrame>(mTrace.size());
		for (int i = 0; i < mTrace.size(); ++i) {
			trace.add(new PHPUnitTraceFrame((Map<?, ?>) mTrace.get(String.valueOf(i)), this, remoteDebugger));
		}
	}

	public String getMessage() {
		return message;
	}

	public String getDiff() {
		return diff;
	}

	public List<PHPUnitTraceFrame> getTrace() {
		return trace;
	}
}