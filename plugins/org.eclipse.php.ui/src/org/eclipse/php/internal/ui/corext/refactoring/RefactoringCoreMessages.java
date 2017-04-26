/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.refactoring;

import org.eclipse.osgi.util.NLS;

public final class RefactoringCoreMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.corext.refactoring.refactoring";//$NON-NLS-1$

	public static String AbstractRenameChange_Renaming;
	public static String CreatePackageChange_Create_package;
	public static String CreatePackageChange_Creating_package;
	public static String MoveSourceModuleChange_global_namespace;
	public static String MoveSourceModuleChange_name;
	public static String RenameSourceModuleChange_name;

	static {
		NLS.initializeMessages(BUNDLE_NAME, RefactoringCoreMessages.class);
	}

	private RefactoringCoreMessages() {
		// Do not instantiate
	}
}