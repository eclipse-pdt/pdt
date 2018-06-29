/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.util;

public class StringUtil {

	public static final String LINK_PATTERN = "(?i)(<http)(.+?)(>)"; //$NON-NLS-1$

	public static String replaceLinksInComposerMessage(String message) {
		return message.replaceAll(LINK_PATTERN, "<a>http$2</a>"); //$NON-NLS-1$
	}
}
