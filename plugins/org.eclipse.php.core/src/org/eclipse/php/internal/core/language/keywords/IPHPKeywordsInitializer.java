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
package org.eclipse.php.internal.core.language.keywords;

import java.util.Collection;

import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

/**
 * This interface initializes a PHP keywords database with keywords for specific
 * PHP language
 */
public interface IPHPKeywordsInitializer {

	public static final String OPEN_BLOCK_SUFFIX = " {"; //$NON-NLS-1$
	public static final String PAAMAYIM_NEKUDOTAYIM_SUFFIX = "::"; //$NON-NLS-1$
	public static final String WS_QUOTES_SEMICOLON_SUFFIX = " '';"; //$NON-NLS-1$
	public static final String EMPTY_SUFFIX = ""; //$NON-NLS-1$
	public static final String COLON_SUFFIX = ":"; //$NON-NLS-1$
	public static final String WHITESPACE_COLON_SUFFIX = " :"; //$NON-NLS-1$
	public static final String SEMICOLON_SUFFIX = ";"; //$NON-NLS-1$
	public static final String WHITESPACE_PARENTESES_SUFFIX = " ()"; //$NON-NLS-1$
	public static final String WHITESPACE_SUFFIX = " "; //$NON-NLS-1$
	public static final String PARENTESES_SUFFIX = "()"; //$NON-NLS-1$

	/**
	 * Initialize the given list with keywords data compatible with this PHP
	 * version.
	 * 
	 * @param keywordData
	 */
	public void initialize(Collection<KeywordData> keywordData);

	/**
	 * Initialize the given list with keywords data compatible with this PHP
	 * version, and not compatible with greater version of PHP.
	 */
	public void initializeSpecific(Collection<KeywordData> list);
}
