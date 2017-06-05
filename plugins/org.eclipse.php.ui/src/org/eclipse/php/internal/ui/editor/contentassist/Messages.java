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
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.osgi.util.NLS;

import com.ibm.icu.text.MessageFormat;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.editor.contentassist.messages"; //$NON-NLS-1$
	public static String AutoActivationTrigger_0;
	public static String ParameterGuessingProposal_0;
	public static String PHPContentAssistant_0;
	public static String AssignToLocalCompletionProposal_name;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String format(String message, Object object) {
		return MessageFormat.format(message, new Object[] { object });
	}
}
