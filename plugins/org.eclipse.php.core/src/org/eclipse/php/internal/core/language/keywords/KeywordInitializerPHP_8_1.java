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
 * Keywords initializer for PHP 8.0
 */
public class KeywordInitializerPHP_8_1 extends KeywordInitializerPHP_8_0 {

	@Override
	public void initialize(Collection<KeywordData> list) {
		super.initialize(list);

		list.add(new KeywordData("readonly", WHITESPACE_SUFFIX, 2)); //$NON-NLS-1$
		list.add(new KeywordData("enum", WHITESPACE_SUFFIX, 2)); //$NON-NLS-1$
		list.add(new KeywordData("never", WHITESPACE_SUFFIX, 2)); //$NON-NLS-1$
	}

	@Override
	public void initializeSpecific(Collection<KeywordData> list) {
	}
}
