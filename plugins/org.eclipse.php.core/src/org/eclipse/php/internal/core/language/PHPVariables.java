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
package org.eclipse.php.internal.core.language;

import org.eclipse.php.internal.core.PHPVersion;

/**
 * This is a container for predefined PHP variables
 * @author michael
 */
public class PHPVariables {

	private final static String[] PHP_VARIABLES = {"$_COOKIE", "$_ENV", "$_FILES", "$_GET", "$_POST", "$_REQUEST", "$_SERVER", "$_SESSION", "$GLOBALS", "$HTTP_COOKIE_VARS", "$HTTP_ENV_VARS", "$HTTP_GET_VARS", "$HTTP_POST_FILES", "$HTTP_POST_VARS", "$HTTP_SERVER_VARS", "$HTTP_SESSION_VARS", };
	
	public static String[] getVariables(PHPVersion phpVersion) {
		return PHP_VARIABLES; // for now the variables set is the same for all PHP versions
	}
}
