/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import org.eclipse.php.internal.debug.core.Logger;

/**
 * Interface for variables/values that are responsible for handling the
 * particular PHP data type.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IPHPDataType {

	/**
	 * PHP data type enum.
	 */
	public enum DataType {

		PHP_BOOL("bool"), //$NON-NLS-1$
		PHP_INT("int"), //$NON-NLS-1$
		PHP_FLOAT("float"), //$NON-NLS-1$
		PHP_STRING("string"), //$NON-NLS-1$
		PHP_NULL("null"), //$NON-NLS-1$
		PHP_ARRAY("array"), //$NON-NLS-1$
		PHP_OBJECT("object"), //$NON-NLS-1$
		PHP_RESOURCE("resource"), //$NON-NLS-1$
		PHP_VIRTUAL_CLASS("class"), //$NON-NLS-1$
		PHP_UNINITIALIZED("<uninitialized>"), //$NON-NLS-1$
		UNKNOWN("<?>"); //$NON-NLS-1$

		private static final String[] TYPE_UNINITIALIZED = new String[] { "uninitialised", "uninitialized" }; //$NON-NLS-1$ //$NON-NLS-2$

		private String type;

		private DataType(String type) {
			this.type = type;
		}

		public String getText() {
			return type;
		}

		/**
		 * Finds data type enum element by corresponding string value.
		 * 
		 * @param type
		 * @return data type enum element
		 */
		public static DataType find(String type) {
			for (DataType t : values()) {
				if (t.getText().equalsIgnoreCase(type))
					return t;
			}
			for (String t : TYPE_UNINITIALIZED) {
				if (t.equalsIgnoreCase(type))
					return PHP_UNINITIALIZED;
			}
			// Log the info if something like this will ever happen...
			Logger.log(Logger.WARNING, "Unknown PHP data type: " + type);
			return UNKNOWN;
		}

	}

	/**
	 * Returns related PHP data type.
	 * 
	 * @return related PHP data type
	 */
	public DataType getDataType();

}
