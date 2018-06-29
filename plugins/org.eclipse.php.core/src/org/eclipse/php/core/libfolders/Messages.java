/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.libfolders;

import org.eclipse.osgi.util.NLS;

/**
 * @author Kaloyan Raev
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.core.libfolders.messages"; //$NON-NLS-1$
	public static String RenameLibraryFolderChange_name;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
