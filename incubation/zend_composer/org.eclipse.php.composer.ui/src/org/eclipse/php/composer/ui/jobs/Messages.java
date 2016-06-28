/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.jobs;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.ui.jobs.messages"; //$NON-NLS-1$
	public static String AddComposerJob_Name;
	public static String AddComposerJob_Title;
	public static String DumpAutoloadJob_Error;
	public static String DumpAutoloadJob_JobName;
	public static String DumpAutoloadJob_JobTitle;
	public static String InstallDependenciesJob_Error;
	public static String InstallDependenciesJob_ErrorTitle;
	public static String ResolveDependenciesJob_Name;
	public static String ResolveDependenciesJob_Title;
	public static String UpdateDependenciesJob_Error;
	public static String UpdateDependenciesJob_Name;
	public static String UpdateDependenciesJob_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
