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
package org.eclipse.php.internal.core.phpModel.parser;

/**
 * A PHPIncludePathModel. 
 * The include path model is a user model for the include path that also holds a type identifier
 * for its source (e.g. library or variable).
 */
public class PHPIncludePathModel extends PHPUserModel {

	private final String id;
	private final IncludePathModelType type;

	/**
	 * Constructs a new PHPIncludePathModel.
	 * 
	 * @param root
	 * @param type The type of the model.
	 */
	public PHPIncludePathModel(String root, IncludePathModelType type) {
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
		this(root, IncludePathModelType.UNKNOWN); 
	}

	public String getID() {
		return id;
	}

	public void dispose() {
	}

	public IncludePathModelType getType() {
		return type;
	}
	
	/**
	 * Define Includepath types - library, variable or unknown
	 */
	public enum IncludePathModelType {

		LIBRARY("Library"), VARIABLE("Variable"), UNKNOWN("Unknown");

		private final String label;
		private IncludePathModelType(String label) {
			this.label = label;
		}
		public String toString() {
			return label;
		}		
	}	
}