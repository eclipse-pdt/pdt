/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.server.core.types;

/**
 * Interface for server type.
 */
public interface IServerType {

	/**
	 * Server type attribute identifier.
	 */
	public static final String TYPE = "serverType"; //$NON-NLS-1$

	/**
	 * Returns server type unique ID.
	 * 
	 * @return server type unique ID
	 */
	String getId();

	/**
	 * Returns server type name.
	 * 
	 * @return server type name
	 */
	String getName();

	/**
	 * Returns server type description.
	 * 
	 * @return server type description
	 */
	String getDescription();

}
