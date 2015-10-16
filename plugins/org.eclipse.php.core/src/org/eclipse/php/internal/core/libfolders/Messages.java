/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.libfolders;

import org.eclipse.osgi.util.NLS;

/**
 * @author Kaloyan Raev
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.core.libfolders.messages"; //$NON-NLS-1$
	public static String AutoDetectLibraryFolderJob_Name;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
