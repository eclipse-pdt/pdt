/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.phpElementData;


import java.io.Serializable;


public class PHPMarker implements IPHPMarker, Serializable {

	private String type;
	private String description;
	private UserData userData;

	public PHPMarker(String type, String description, UserData userData) {
		this.type = type;
		this.description = description;
		this.userData = userData;
	}

	public String getDescription() {
		return description;
	}

	public UserData getUserData() {
		return userData;
	}

	public String getType() {
		return type;
	}

}