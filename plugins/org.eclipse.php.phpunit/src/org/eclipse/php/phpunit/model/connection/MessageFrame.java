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
