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
import org.eclipse.php.phpunit.model.connection.MessageException;
import org.eclipse.php.phpunit.model.connection.MessageFrame;

public abstract class PHPUnitTestEvent extends PHPUnitElement {

	protected String message;
	protected String diff;
	protected List<PHPUnitTraceFrame> trace;

	public PHPUnitTestEvent(MessageException exception, final PHPUnitElement parent, RemoteDebugger remoteDebugger) {
		super(exception, parent, remoteDebugger);
		message = exception.getMessage();
		diff = exception.getDiff();
		final Map<Integer, MessageFrame> frames = exception.getTrace();
		if (frames == null || frames.isEmpty()) {
			return;
		}
		trace = new ArrayList<>(frames.size());
		for (Integer key : frames.keySet()) {
			trace.add(new PHPUnitTraceFrame(frames.get(key), this, remoteDebugger));
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