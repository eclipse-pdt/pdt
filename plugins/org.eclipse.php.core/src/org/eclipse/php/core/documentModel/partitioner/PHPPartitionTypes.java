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
package org.eclipse.php.core.documentModel.partitioner;

public interface PHPPartitionTypes {
	public static final String PHP_DEFAULT = "org.eclipse.php.PHP_DEFAULT";
	public static final String PHP_SINGLE_LINE_COMMENT = "org.eclipse.php.PHP_SINGLE_LINE_COMMENT";
	public static final String PHP_MULTI_LINE_COMMENT = "org.eclipse.php.PHP_MULTI_LINE_COMMENT";
	public static final String PHP_DOC = "org.eclipse.php.PHP_DOC";
	public static final String PHP_QUOTED_STRING = "org.eclipse.php.PHP_QUOTED_STRING";
}
