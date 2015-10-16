/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [426054]
 *******************************************************************************/
package org.eclipse.php.internal.core.language.keywords;

import java.util.Collection;

import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

/**
 * Keywords initializer for PHP 5.5
 */
public class KeywordInitializerPHP_5_5 extends KeywordInitializerPHP_5_4 {

	public void initialize(Collection<KeywordData> list) {
		super.initialize(list);

		list.add(new KeywordData("yield", WHITESPACE_SUFFIX, 1, PHPKeywords.METHOD_BODY)); //$NON-NLS-1$
		list.add(new KeywordData("finally", OPEN_BLOCK_SUFFIX, 2)); //$NON-NLS-1$

		for (KeywordData k : list) {
			if (k.name.equals("self") || k.name.equals("parent") //$NON-NLS-1$ //$NON-NLS-2$
					|| k.name.equals("static")) { //$NON-NLS-1$
				k.ignoreCase = true;
			}
		}
	}

	public void initializeSpecific(Collection<KeywordData> list) {
	}
}
