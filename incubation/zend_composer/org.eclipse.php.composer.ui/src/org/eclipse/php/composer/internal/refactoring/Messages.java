/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.refactoring;

import org.eclipse.osgi.util.NLS;

/**
 * @author Michal Niewrzal, 2014
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.internal.refactoring.messages"; //$NON-NLS-1$
	public static String RenameComposerProjectChange_ChangeName;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
