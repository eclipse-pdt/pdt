/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phar;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.internal.core.phar.Messages";//$NON-NLS-1$

	public static String Phar_Signature_Corrupted;

	public static String Phar_Signature_Unsupported;

	public static String Phar_Signature_End;

	public static String Phar_No_Stub_End;

	public static String Phar_Corrupted;
	public static String Stub_Invalid;
	public static String PharEntry_Too_Long;
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// Do not instantiate
	}
}
