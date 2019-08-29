/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
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
 *     Dawid Pakuła [426054]
 *******************************************************************************/
package org.eclipse.php.internal.core.language.keywords;

import java.util.Collection;

import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

/**
 * Keywords initializer for PHP 7.4
 */
public class KeywordInitializerPHP_7_4 extends KeywordInitializerPHP_7_3 {

	@Override
	public void initialize(Collection<KeywordData> list) {
		super.initialize(list);

		list.add(new KeywordData("fn", WHITESPACE_PARENTHESES_SUFFIX, 2)); //$NON-NLS-1$
	}

	@Override
	public void initializeSpecific(Collection<KeywordData> list) {
	}
}
