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
package org.eclipse.php.internal.core.search;

import org.eclipse.dltk.core.ISearchPatternProcessor;
import org.eclipse.dltk.core.search.AbstractSearchFactory;
import org.eclipse.dltk.core.search.IMatchLocatorParser;
import org.eclipse.dltk.core.search.matching.MatchLocator;

public class PHPSearchFactory extends AbstractSearchFactory {

	private PHPSearchPatternProcessor phpSearchPatternProcessor = new PHPSearchPatternProcessor();

	@Override
	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		return new PHPMatchLocatorParser(locator);
	}

	@Override
	public ISearchPatternProcessor createSearchPatternProcessor() {
		return phpSearchPatternProcessor;
	}
}
