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
package org.eclipse.php.internal.debug.core.zend.debugger;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.debug.core.zend.debugger.messages"; //$NON-NLS-1$
	public static String DebugParametersInitializersRegistry_12;
	public static String OldDebuggerWarningDialog_0;
	public static String OldDebuggerWarningDialog_1;
	public static String OldDebuggerWarningDialog_4;
	public static String OldDebuggerWarningDialog_6;
	public static String OldDebuggerWarningDialog_9;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
