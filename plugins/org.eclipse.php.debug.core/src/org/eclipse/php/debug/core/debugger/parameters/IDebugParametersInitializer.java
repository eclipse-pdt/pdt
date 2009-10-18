/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.debug.core.debugger.parameters;

import java.net.URL;
import java.util.Hashtable;

import org.eclipse.debug.core.ILaunch;

/**
 * A debug parameters initializer is used to generate debug query strings that
 * will be delivered to the debug server when a debug session is initialized.
 */
public interface IDebugParametersInitializer {

	/**
	 * Returns the request URL for the given launch. The request URL holds the
	 * URL's protocol, domain and path (without the query parameters).
	 * 
	 * @param launch
	 *            The {@link ILaunch}
	 * @return The request url (e.g. http://www.eclipse.org/pdt/main.php).
	 */
	public URL getRequestURL(ILaunch launch);

	/**
	 * Generate and returns a debug query parameters
	 * 
	 * @param ILaunch
	 *            launch
	 * @return A hastable containing debug query parameters
	 */
	public Hashtable<String, String> getDebugParameters(ILaunch launch);

	/**
	 * Returns ID of the corresponding Debug handler
	 */
	public String getDebugHandler();

	/**
	 * Sets the ID of the corresponding Debug handler
	 * 
	 * @param id
	 */
	public void setDebugHandler(String id);
}
