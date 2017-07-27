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
package org.eclipse.php.internal.core.index;

import org.eclipse.dltk.core.index2.IElementResolver;
import org.eclipse.dltk.core.index2.IIndexerParticipant;
import org.eclipse.dltk.core.index2.IIndexingParser;

/**
 * H2 Database indexer participant for PHP.
 * 
 * @author michael
 * 
 */
public class PHPIndexerParticipant implements IIndexerParticipant {

	private static final String QUALIFIER_SEP = "\\"; //$NON-NLS-1$
	private PHPElementResolver elementResolver;
	private PHPIndexingParser parser;

	@Override
	public IElementResolver getElementResolver() {
		if (elementResolver == null) {
			elementResolver = new PHPElementResolver();
		}
		return elementResolver;
	}

	@Override
	public IIndexingParser getIndexingParser() {
		if (parser == null) {
			parser = new PHPIndexingParser();
		}
		return parser;
	}

	public String getQualifierSeparator() {
		return QUALIFIER_SEP;
	}
}
