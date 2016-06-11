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
package org.eclipse.php.internal.server.ui.launching.zend;

import org.eclipse.osgi.util.NLS;

public class JFaceResources extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.server.ui.launching.zend.messages"; //$NON-NLS-1$
	public static String DefaultServerTestMessageDialog_0;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, JFaceResources.class);
	}

	private JFaceResources() {
	}
}
