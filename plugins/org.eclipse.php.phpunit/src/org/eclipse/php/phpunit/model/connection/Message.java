/*******************************************************************************
 * Copyright (c) 2018 Michał Niewrzał and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Michał Niewrzał - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.model.connection;

import java.util.Map;

public class Message {

	private MessageEventType event;

	private String target;

	private Double time;

	private MessageTest test;

	private MessageException exception;

	private Map<Integer, MessageException> warnings;

	public MessageEventType getEvent() {
		return event;
	}

	public String getTarget() {
		return target;
	}

	public Double getTime() {
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
