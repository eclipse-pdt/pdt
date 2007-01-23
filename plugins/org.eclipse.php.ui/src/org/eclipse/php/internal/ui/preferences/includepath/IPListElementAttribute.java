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
package org.eclipse.php.internal.ui.preferences.includepath;

/**
 */
public class IPListElementAttribute {

	private IPListElement fParent;
	private String fKey;
	private Object fValue;
	private final boolean fBuiltIn;

	public IPListElementAttribute(IPListElement parent, String key, Object value, boolean builtIn) {
		fKey = key;
		fValue = value;
		fParent = parent;
		fBuiltIn = builtIn;
		if (!builtIn) {
			if (!(value instanceof String || value == null))
				throw new IllegalArgumentException();
		}
	}

	public IPListElement getParent() {
		return fParent;
	}

	/**
	 * @return Returns <code>true</code> if the attribute is a built in attribute.
	 */
	public boolean isBuiltIn() {
		return fBuiltIn;
	}

	/**
	 * @return Returns <code>true</code> if the attribute is in a non-modifiable includepath container
	 */
	public boolean isInNonModifiableContainer() {
		return fParent.isInNonModifiableContainer();
	}

	/**
	 * Returns the key.
	 * @return String
	 */
	public String getKey() {
		return fKey;
	}

	/**
	 * Returns the value.
	 * @return Object
	 */
	public Object getValue() {
		return fValue;
	}

	/**
	 * Returns the value.
	 */
	public void setValue(Object value) {
		fValue = value;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof IPListElementAttribute))
			return false;
		IPListElementAttribute attrib = (IPListElementAttribute) obj;
		return attrib.fKey == this.fKey && attrib.getParent().getPath().equals(fParent.getPath());
	}
}
