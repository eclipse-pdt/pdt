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
 * Keywords initializer for PHP 5.1/5.2
 */
public class KeywordInitializerPHP_5 extends KeywordInitializerPHP_4 {

	public void initialize(Collection<KeywordData> list) {
		super.initialize(list);

		list.add(new KeywordData("abstract", WHITESPACE_SUFFIX, 1, //$NON-NLS-1$
				PHPKeywords.CLASS_BODY | PHPKeywords.GLOBAL));
		list.add(new KeywordData("catch", WHITESPACE_PARENTESES_SUFFIX, 2)); //$NON-NLS-1$
		list.add(new KeywordData("clone", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		list.add(new KeywordData("final", WHITESPACE_SUFFIX, 1, //$NON-NLS-1$
				PHPKeywords.CLASS_BODY | PHPKeywords.GLOBAL));
		list.add(new KeywordData("implements", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		list.add(new KeywordData("instanceof", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		list.add(new KeywordData("interface", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		list.add(new KeywordData("private", WHITESPACE_SUFFIX, 1, //$NON-NLS-1$
				PHPKeywords.CLASS_BODY));
		list.add(new KeywordData("protected", WHITESPACE_SUFFIX, 1, //$NON-NLS-1$
				PHPKeywords.CLASS_BODY));
		list.add(new KeywordData("public", WHITESPACE_SUFFIX, 1, //$NON-NLS-1$
				PHPKeywords.CLASS_BODY));
		list.add(new KeywordData("self", PAAMAYIM_NEKUDOTAYIM_SUFFIX, 2, //$NON-NLS-1$
				PHPKeywords.METHOD_BODY | PHPKeywords.METHOD_PARAM));
		list.add(new KeywordData("throw", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		list.add(new KeywordData("try", OPEN_BLOCK_SUFFIX, 2)); //$NON-NLS-1$
	}

	public void initializeSpecific(Collection<KeywordData> list) {
	}
}
