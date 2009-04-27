/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to get NLSed messages.
 */
public final class CorrectionMessages extends NLS {

	private static final String BUNDLE_NAME= CorrectionMessages.class.getName();

	private CorrectionMessages() {
		// Do not instantiate
	}
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, CorrectionMessages.class);
	}

	public static String ChangeCorrectionProposal_error_title;
	public static String ChangeCorrectionProposal_error_message;
	public static String NoCorrectionProposal_description;
	public static String JavaCorrectionProcessor_error_quickfix_message;
	public static String JavaCorrectionProcessor_error_status;
	public static String JavaCorrectionProcessor_error_quickassist_message;
	public static String JavaCorrectionProcessor_go_to_closest_using_menu;
	public static String JavaCorrectionProcessor_go_to_closest_using_key;
	public static String JavaCorrectionProcessor_go_to_original_using_menu;
	public static String JavaCorrectionProcessor_go_to_original_using_key;
}
