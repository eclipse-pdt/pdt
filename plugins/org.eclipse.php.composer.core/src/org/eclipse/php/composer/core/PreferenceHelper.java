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
package org.eclipse.php.composer.core;

import java.util.StringTokenizer;

@Deprecated
public class PreferenceHelper {

	private static final String DELIMITER = ";"; //$NON-NLS-1$

	public static String serialize(String[] elements) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < elements.length; i++) {
			buffer.append(elements[i]);
			buffer.append(DELIMITER);
		}
		return buffer.toString();
	}

	public static String[] deserialize(String value) {
		StringTokenizer tokenizer = new StringTokenizer(value, DELIMITER);
		int tokenCount = tokenizer.countTokens();
		String[] elements = new String[tokenCount];
		for (int i = 0; i < tokenCount; i++) {
			elements[i] = tokenizer.nextToken();
		}

		return elements;
	}
}
