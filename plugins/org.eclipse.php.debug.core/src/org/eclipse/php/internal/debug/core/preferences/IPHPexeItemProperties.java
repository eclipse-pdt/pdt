/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.preferences;

/**
 * PHP exe item property keys.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IPHPexeItemProperties {

	/**
	 * 'Name' property key.
	 */
	public static final String PROP_NAME = "name"; //$NON-NLS-1$

	/**
	 * 'Version' property key.
	 */
	public static final String PROP_VERSION = "version"; //$NON-NLS-1$

	/**
	 * 'Executable location' property key.
	 */
	public static final String PROP_EXE_LOCATION = "exe-location"; //$NON-NLS-1$

	/**
	 * 'INI file location' property key.
	 */
	public static final String PROP_INI_LOCATION = "ini-location"; //$NON-NLS-1$

	/**
	 * 'Use system default INI' property key.
	 */
	public static final String PROP_USE_DEFAULT_INI = "use-default-ini"; //$NON-NLS-1$

	/**
	 * 'SAPI type' property key.
	 */
	public static final String PROP_SAPI_TYPE = "sapi-type"; //$NON-NLS-1$

	/**
	 * 'Debugger type ID' property key.
	 */
	public static final String PROP_DEBUGGER_ID = "debugger-id"; //$NON-NLS-1$

}
