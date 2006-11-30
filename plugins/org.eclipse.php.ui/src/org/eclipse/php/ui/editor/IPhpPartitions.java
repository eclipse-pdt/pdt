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

package org.eclipse.php.ui.editor;
// taken from package org.eclipse.jdt.ui.text;

/**
 * Definition of PHP partitioning and its partitions.
 * 
 * @see Eclipse 3.1
 */
interface IPhpPartitions {

	/**
	 * The identifier of the php partitioning.
	 */
	String PHP_PARTITIONING = "___php_partitioning"; //$NON-NLS-1$

	/**
	 * The identifier of the single-line (JLS2: EndOfLineComment) end comment
	 * partition content type.
	 */
	String PHP_SINGLE_LINE_COMMENT = "__php_singleline_comment"; //$NON-NLS-1$

	/**
	 * The identifier multi-line (JLS2: TraditionalComment) comment partition
	 * content type.
	 */
	String PHP_MULTI_LINE_COMMENT = "__php_multiline_comment"; //$NON-NLS-1$

	/**
	 * The identifier of the PHPdoc (JLS2: DocumentationComment) partition
	 * content type.
	 */
	String PHP_DOC = "__php_phpdoc"; //$NON-NLS-1$

	/**
	 * The identifier of the PHP string partition content type.
	 */
	String PHP_STRING = "__php_string"; //$NON-NLS-1$

	/**
	 * The identifier of the PHP character partition content type.
	 */
	String PHP_CHARACTER = "__php_character"; //$NON-NLS-1$
}

