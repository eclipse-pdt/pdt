/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.core.phpModel.parser;

/**
 * @author guy.g
 *
 */
public abstract class IncludingType {

	public static final String REQUIRE = "require";
	public static final String REQUIRE_ONCE = "require_once";
	public static final String INCLUDE = "include";
	public static final String INCLUDE_ONCE = "include_once";
}
