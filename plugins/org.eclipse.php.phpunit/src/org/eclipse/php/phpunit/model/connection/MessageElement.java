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

public class MessageElement {

	private String file;

	private Integer line;

	private String filtered;

	public String getFile() {
		return file;
	}

	public Integer getLine() {
		return line;
	}

	public String getFiltered() {
		return filtered;
	}
}
