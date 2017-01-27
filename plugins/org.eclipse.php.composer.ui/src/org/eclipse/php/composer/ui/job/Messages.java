/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Rogue Wave Software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.job;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.ui.job.messages"; //$NON-NLS-1$
	public static String ComposerJob_DownloadDialogMessage;
	public static String ComposerJob_DownloadDialogTitle;
	public static String ComposerJob_ErrorMessage;
	public static String CreateProjectJob_Name;
	public static String DownloadJob_ErrorMessage;
	public static String DownloadJob_Name;
	public static String DownloadJob_TaskName;
	public static String InstallDevJob_Name;
	public static String InstallJob_Name;
	public static String SelfUpdateJob_Name;
	public static String UpdateDevJob_Name;
	public static String UpdateJob_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
