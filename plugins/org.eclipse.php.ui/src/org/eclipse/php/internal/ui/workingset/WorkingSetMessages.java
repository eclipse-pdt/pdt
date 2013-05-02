/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.workingset;

import org.eclipse.osgi.util.NLS;

public final class WorkingSetMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.workingset.WorkingSetMessages"; //$NON-NLS-1$

	private WorkingSetMessages() {
		// Do not instantiate
	}

	public static String PhpWorkingSetPage_title;
	public static String PhpWorkingSetPage_workingSet_name;
	public static String PhpWorkingSetPage_workingSet_description;
	public static String PhpWorkingSetPage_workingSet_content;
	public static String PhpWorkingSetPage_warning_nameMustNotBeEmpty;
	public static String PhpWorkingSetPage_warning_workingSetExists;
	public static String PhpWorkingSetPage_warning_resourceMustBeChecked;
	public static String PhpWorkingSetPage_warning_nameWhitespace;
	public static String PhpWorkingSetPage_selectAll_label;
	public static String PhpWorkingSetPage_selectAll_toolTip;
	public static String PhpWorkingSetPage_deselectAll_label;
	public static String PhpWorkingSetPage_deselectAll_toolTip;

	static {
		NLS.initializeMessages(BUNDLE_NAME, WorkingSetMessages.class);
	}
}
