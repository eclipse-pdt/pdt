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

import com.google.gson.annotations.SerializedName;

public class MessageException extends MessageElement {

	private String message;

	private String diff;

	@SerializedName("class")
	private String clazz;

	private String code;

	private Map<Integer, MessageFrame> trace;

	public MessageException() {
	}

	public MessageException(String clazz, String message) {
		this.clazz = clazz;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getDiff() {
		return diff;
	}

	public String getClazz() {
		return clazz;
	}

	public String getCode() {
		return code;
	}

	public Map<Integer, MessageFrame> getTrace() {
		return trace;
	}

}
