/*******************************************************************************
 * Copyright (c) 2018 Michał Niewrzał and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Michał Niewrzał - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.model.connection;

import java.util.Map;

public class Message {

	private MessageEventType event;

	private String target;

	private String time;

	private MessageTest test;

	private MessageException exception;

	private Map<Integer, MessageException> warnings;

	public MessageEventType getEvent() {
		return event;
	}

	public String getTarget() {
		return target;
	}

	public String getTime() {
		return time;
	}

	public MessageTest getTest() {
		return test;
	}

	public MessageException getException() {
		return exception;
	}

	public Map<Integer, MessageException> getWarnings() {
		return warnings;
	}

}
