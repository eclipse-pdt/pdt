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

import com.google.gson.annotations.SerializedName;

public class MessageFrame extends MessageElement {

	private String function;

	@SerializedName("class")
	private String clazz;

	private String type;

	public String getFunction() {
		return function;
	}

	public String getClazz() {
		return clazz;
	}

	public String getType() {
		return type;
	}
}
