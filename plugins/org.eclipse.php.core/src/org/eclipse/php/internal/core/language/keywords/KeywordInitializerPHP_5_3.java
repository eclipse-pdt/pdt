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
 * Keywords initializer for PHP 5.3
 */
public class KeywordInitializerPHP_5_3 extends KeywordInitializerPHP_5 {

	public void initialize(Collection<KeywordData> list) {
		super.initialize(list);

		// update "const" keyword
		list.remove(new KeywordData("const", "", 0)); //$NON-NLS-1$ //$NON-NLS-2$
		list.add(new KeywordData("const", WHITESPACE_SUFFIX, 1, //$NON-NLS-1$
				PHPKeywords.CLASS_BODY | PHPKeywords.GLOBAL));

		list.add(new KeywordData("goto", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		list.add(new KeywordData("namespace", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		if (this.getClass().getName()
				.equals(KeywordInitializerPHP_5_3.class.getName())) {
			list.add(new KeywordData("use", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		}

	}

	public void initializeSpecific(Collection<KeywordData> list) {
	}
}
