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
package org.eclipse.php.internal.server.ui.launching;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.server.ui.launching.Messages"; //$NON-NLS-1$
	public static String launch_failure_msg_title;
	public static String launch_failure_no_target;
	public static String launch_failure_server_msg_text;
	public static String PHPWebPageLaunchConfigurationDebuggerTab_No_debugger_is_attached_to_server_configuration;
	public static String PHPWebPageLaunchShortcut_0;
	public static String PHPWebPageLaunchShortcut_1;
	public static String PHPWebPageLaunchShortcut_10;
	public static String PHPWebPageLaunchShortcut_2;
	public static String PHPWebPageLaunchShortcut_3;
	public static String PHPWebPageLaunchShortcut_4;
	public static String PHPWebPageLaunchShortcut_9;
	public static String DebugServerConnectionTest_some_IPs_seems_to_be_redundant;
	public static String DebugServerConnectionTest_test_result;
	public static String DebugServerConnectionTest_test_successfull;
	public static String DefaultServerTestMessageDialog_0;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
