/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
public class KeywordInitializerPHP_5_4 extends KeywordInitializerPHP_5_3 {

	@Override
	public void initialize(Collection<KeywordData> list) {
		super.initialize(list);

		list.add(new KeywordData("trait", WHITESPACE_SUFFIX, 1, PHPKeywords.GLOBAL)); //$NON-NLS-1$
		list.add(new KeywordData("insteadof", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		list.add(new KeywordData("callable", WHITESPACE_SUFFIX, 1)); //$NON-NLS-1$
		list.add(new KeywordData("use", WHITESPACE_SUFFIX, 1, //$NON-NLS-1$
				PHPKeywords.CLASS_BODY | PHPKeywords.GLOBAL));
	}

	@Override
	public void initializeSpecific(Collection<KeywordData> list) {
	}
}
