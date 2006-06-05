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
package org.eclipse.php.core.phpModel.phpElementData;

import java.io.Serializable;

public interface PHPDocTag extends Serializable {

	public static final int ABSTRACT = 0;

	public static final int AUTHOR = 1;

	public static final int DEPRECATED = 2;

	public static final int FINAL = 3;

	public static final int GLOBAL = 4;

	public static final int NAME = 5;

	public static final int RETURN = 6;

	public static final int PARAM = 7;

	public static final int SEE = 8;

	public static final int STATIC = 9;

	public static final int STATICVAR = 10;

	public static final int TODO = 11;

	public static final int VAR = 12;

	public static final int PACKAGE = 13;

	public static final int ACCESS = 14;

	public static final int CATEGORY = 15;

	public static final int COPYRIGHT = 16;

	public static final int DESC = 17;

	public static final int EXAMPLE = 18;

	public static final int FILESOURCE = 19;

	public static final int IGNORE = 20;

	public static final int INTERNAL = 21;

	public static final int LICENSE = 22;

	public static final int LINK = 23;

	public static final int SINCE = 24;

	public static final int SUBPACKAGE = 25;

	public static final int TUTORIAL = 26;

	public static final int USES = 27;

	public static final int VERSION = 28;

	public int getID();

	public String getValue();

}
