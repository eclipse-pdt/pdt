/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.php.internal.core.keywords.PHPKeywords.Context;
import org.eclipse.php.internal.core.keywords.PHPKeywords.KeywordData;

/**
 * This strategy completes keywords that can be shown in a class body 
 * @author michael
 */
public class GlobalKeywordsStrategy extends KeywordsStrategy {

	protected boolean filterKeyword(KeywordData keyword) {
		return keyword.context != Context.GLOBAL;
	}
	
}
