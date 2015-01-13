/*******************************************************************************
 * Copyright (c) 2013, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.changes;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.refactoring.core.changes.messages"; //$NON-NLS-1$
	public static String PHPProjectMoveChange_0;
	public static String RenameBreackpointChange_0;
	public static String RenameBuildAndIcludePathChange_0;
	public static String RenameConfigurationChange_0;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
