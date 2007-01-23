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
package org.eclipse.php.core.documentModel.parser;

public interface PHPRegionContext {
	public static final String PHP_OPEN = "PHP_OPEN"; //$NON-NLS-1$
	public static final String PHP_CLOSE = "PHP_CLOSE"; //$NON-NLS-1$
	public static final String PHP_CONTENT = "PHP_CONTENT"; //$NON-NLS-1$
	public static final String PHP_ASP_CONTENT = "PHP_ASP_CONTENT"; //$NON-NLS-1$

	public static final String XML_TAG_ATTRIBUTE_VALUE_DQUOTE = "XML_TAG_ATTRIBUTE_VALUE_DQUOTE"; //$NON-NLS-1$
	public static final String XML_TAG_ATTRIBUTE_VALUE_SQUOTE = "XML_TAG_ATTRIBUTE_VALUE_SQUOTE"; //$NON-NLS-1$
	
}
