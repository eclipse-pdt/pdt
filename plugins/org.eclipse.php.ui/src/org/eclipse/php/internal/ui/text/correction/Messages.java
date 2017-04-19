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
package org.eclipse.php.internal.ui.text.correction;

import org.eclipse.osgi.util.NLS;

import com.ibm.icu.text.MessageFormat;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.text.correction.messages"; //$NON-NLS-1$
	public static String ContributedProcessorDescriptor_4;
	public static String ContributedProcessorDescriptor_5;
	public static String ContributedProcessorDescriptor_8;
	public static String ContributedProcessorDescriptor_9;
	public static String ProblemLocation_0;
	public static String ProblemLocation_2;
	public static String ProblemLocation_3;
	public static String ProblemLocation_4;
	public static String ProblemLocation_5;
	public static String ProblemLocation_6;
	public static String ProblemLocation_7;
	public static String ProblemLocation_8;
	public static String ProblemLocation_9;

	public static String format(String message, Object object) {
		return MessageFormat.format(message, new Object[] { object });
	}

	public static String format(String message, Object[] objects) {
		return MessageFormat.format(message, objects);
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
