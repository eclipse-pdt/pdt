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
package org.eclipse.php.internal.core.search;

import org.eclipse.dltk.core.ISearchPatternProcessor;
import org.eclipse.dltk.core.search.AbstractSearchFactory;
import org.eclipse.dltk.core.search.IMatchLocatorParser;
import org.eclipse.dltk.core.search.matching.MatchLocator;

public class PHPSearchFactory extends AbstractSearchFactory {

	private PHPSearchPatternProcessor phpSearchPatternProcessor = new PHPSearchPatternProcessor();

	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		return new PHPMatchLocatorParser(locator);
	}

	public ISearchPatternProcessor createSearchPatternProcessor() {
		return phpSearchPatternProcessor;
	}
}
