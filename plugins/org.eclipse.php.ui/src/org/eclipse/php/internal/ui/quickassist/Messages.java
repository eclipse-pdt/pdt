/*******************************************************************************
 * Copyright (c) 2006, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Kaloyan Raev - Bug 485550 - Improve insert variable comment quick assist
 *******************************************************************************/
package org.eclipse.php.internal.ui.quickassist;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.quickassist.messages"; //$NON-NLS-1$
	public static String VarCommentQuickAssistProcessor_AdditionalProposalInfo;
	public static String VarCommentQuickAssistProcessor_name;

	public static String VarCommentQuickAssistProcessor_OpenTypeAction_dialogMessage;
	public static String VarCommentQuickAssistProcessor_OpenTypeAction_dialogTitle;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
