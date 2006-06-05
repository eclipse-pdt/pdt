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
package org.eclipse.php.debug.core.debugger.parameters;

/**
 * A debug parameters initializer is used to generate debug query strings that will be delivered to the 
 * debug server when a debug session is initialized.
 */
public interface IDebugParametersInitializer {

	/**
	 * Generate and return a debug query.
	 * @return A debug query string
	 */
	public String generateQuery();
	
	/**
	 * Adds a debug parameter to the initializer.
	 * @param key	The parameter identifier.
	 * @param value	The parameter value.
	 */
	public void addParameter(String key, Object value);
	
	/**
	 * Returns ID of the corresponding Debug handler
	 */
	public String getDebugHandler();
	
	/**
	 * Sets the ID of the corresponding Debug handler
	 * @param id
	 */
	public void setDebugHandler(String id);
}
