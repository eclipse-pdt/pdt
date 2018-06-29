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
package org.eclipse.php.composer.core.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

	protected static final Pattern NS_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\\\]+$"); //$NON-NLS-1$

	public static boolean validateNamespace(String namespace) {
		Matcher matcher = NS_PATTERN.matcher(namespace);
		return matcher.matches();
	}
}
