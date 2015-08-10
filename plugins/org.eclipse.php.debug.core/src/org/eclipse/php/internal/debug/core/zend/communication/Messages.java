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
package org.eclipse.php.internal.debug.core.zend.communication;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.debug.core.zend.communication.messages"; //$NON-NLS-1$
	public static String BroadcastConnection_Broadcast_connection_handler_name;
	public static String BroadcastConnection_HTML_content_message;

	public static String OpenRemoteFileRequestor_No_match_could_be_found;
	public static String OpenRemoteFileRequestor_Open_remote_file_request;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
