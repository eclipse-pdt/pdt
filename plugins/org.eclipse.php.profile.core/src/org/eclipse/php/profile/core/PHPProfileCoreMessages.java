/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core;

import org.eclipse.osgi.util.NLS;

public class PHPProfileCoreMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.profile.core.PHPProfileCoreMessages"; //$NON-NLS-1$

	private PHPProfileCoreMessages() {
	}

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, PHPProfileCoreMessages.class);
	}

	public static String PHPLaunchListener_0;
	public static String PHPLaunchListener_1;
	public static String PHPLaunchListener_2;
	public static String PHPLaunchListener_3;
	public static String PHPLaunchListener_4;
	public static String PHPLaunchListener_5;
	public static String PHPLaunchListener_6;
	public static String PHPLaunchListener_7;
	public static String PHPProfilePerspectiveOpener_confirmDialog_0;
	public static String PHPProfilePerspectiveOpener_confirmDialog_1;
	public static String PHPProfilePerspectiveOpener_confirmDialog_2;
	public static String ProfilerGlobalData_0;
	public static String ZProfiler_0;

}
