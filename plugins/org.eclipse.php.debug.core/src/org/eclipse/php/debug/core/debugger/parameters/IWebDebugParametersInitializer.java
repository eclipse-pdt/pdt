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

import java.util.Hashtable;

import org.eclipse.debug.core.ILaunch;

/**
 * This interface allows passing additional data to the debug session, e.g: GET,
 * POST, RAW data, COOKIES, HEADERS
 */
public interface IWebDebugParametersInitializer extends
		IDebugParametersInitializer {

	public static final String GET_METHOD = "GET"; //$NON-NLS-1$
	public static final String POST_METHOD = "POST"; //$NON-NLS-1$

	/**
	 * This method returns request method ({@link #GET_METHOD}|
	 * {@link #POST_METHOD})
	 */
	public String getRequestMethod(ILaunch launch);

	/**
	 * This method returns additional GET or POST parameters to be passed to the
	 * debug session request.
	 */
	public Hashtable<String, String> getRequestParameters(ILaunch launch);

	/**
	 * This method returns cookies to be passed to the debug session request
	 */
	public Hashtable<String, String> getRequestCookies(ILaunch launch);

	/**
	 * This method returns headers to be added to the debug session request
	 */
	public Hashtable<String, String> getRequestHeaders(ILaunch launch);

	/**
	 * This method returns additional RAW data. This data will not replace
	 * additional POST parameters - it will be appended.
	 */
	public String getRequestRawData(ILaunch launch);
}
