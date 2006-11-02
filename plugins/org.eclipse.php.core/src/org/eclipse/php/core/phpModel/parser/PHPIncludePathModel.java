/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.phpModel.parser;

/**
 * A PHPIncludePathModel. 
 * The include path model is a user model for the include path that also holds a type identifier
 * for its source (e.g. Zip, library or variable).
 */
public class PHPIncludePathModel extends PHPUserModel {

	public static final int TYPE_ZIP = 0;
	public static final int TYPE_LIBRARY = 1;
	public static final int TYPE_VARIABLE = 2;
	public static final int TYPE_UNKNOWN = 3;

	private final String id;
	private final int type;

	/**
	 * Constructs a new PHPIncludePathModel.
	 * 
	 * @param root
	 * @param type The type of the model.
	 */
	public PHPIncludePathModel(String root, int type) {
		this.id = root;
		this.type = type;
	}

	/**
	 * Constructs a new PHPIncludePathModel.
	 * Note that the type of the model is set to TYPE_UNKNOWN.
	 * 
	 * @param root
	 */
	public PHPIncludePathModel(String root) {
		this(root, TYPE_UNKNOWN);
	}
	
	public String getID() {
		return id;
	}

	public void dispose() {
	}

	public int getType() {
		return type;
	}
}