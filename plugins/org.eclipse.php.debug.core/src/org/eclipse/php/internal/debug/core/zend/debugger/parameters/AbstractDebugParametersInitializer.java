/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.parameters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;

public abstract class AbstractDebugParametersInitializer implements IDebugParametersInitializer {

	// Parameters
	public static final String START_DEBUG = "start_debug="; //$NON-NLS-1$
	public static final String DEBUG_PORT = "debug_port="; //$NON-NLS-1$
	public static final String DEBUG_PASSIVE = "debug_passive="; //$NON-NLS-1$
	public static final String DEBUG_HOST = "debug_host="; //$NON-NLS-1$
	public static final String SEND_SESS_END = "send_sess_end="; //$NON-NLS-1$
	public static final String DEBUG_NO_CACHE = "debug_no_cache="; //$NON-NLS-1$
	public static final String DEBUG_STOP = "debug_stop="; //$NON-NLS-1$
	public static final String ORIGINAL_URL = "original_url="; //$NON-NLS-1$
	public static final String DEBUG_SESSION_ID = "debug_session_id="; //$NON-NLS-1$
	public static final String DEBUG_FIRST_PAGE = "debug_new_session="; //$NON-NLS-1$
	public static final String DEBUG_ALL_PAGES = "debug_start_session="; //$NON-NLS-1$
	public static final String DEBUG_START_URL = "debug_start_url="; //$NON-NLS-1$
	public static final String DEBUG_CONTINUE = "debug_cont_session="; //$NON-NLS-1$

	private String id = null;

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.debugger.parameters.IDebugParametersInitializer#getDebugHandler()
	 */
	public String getDebugHandler() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.debugger.parameters.IDebugParametersInitializer#setDebugHandler(java.lang.String)
	 */
	public void setDebugHandler(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.debugger.parameters.IDebugParametersInitializer#generateQuery(org.eclipse.debug.core.ILaunch)
	 */
	public String generateQuery(ILaunch launch) {
		StringBuffer buf = new StringBuffer();

		Hashtable<String, String> parameters = generateQueryParameters(launch);
		Enumeration<String> e = parameters.keys();

		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			buf.append(key);
			try {
				buf.append(URLEncoder.encode((String) parameters.get(key), "UTF-8"));
			} catch (UnsupportedEncodingException exc) {
			}
			if (e.hasMoreElements()) {
				buf.append('&'); //$NON-NLS-1$
			}
		}
		return buf.toString();
	}
}
