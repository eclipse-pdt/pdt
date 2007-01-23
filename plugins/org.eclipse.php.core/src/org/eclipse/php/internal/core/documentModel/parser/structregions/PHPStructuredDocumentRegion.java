/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.documentModel.parser.structregions;

import org.eclipse.php.internal.core.documentModel.parser.Scanner.LexerState;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocumentRegion;

public class PHPStructuredDocumentRegion extends BasicStructuredDocumentRegion {

	/**
	 * Holds the lex state at the begining of this structured document
	 */
	public final LexerState lexerState;
	
	/**
	 * Holds true if the regions are already in a php state
	 */
	public final boolean inPhpState;

	public PHPStructuredDocumentRegion(LexerState lexerState, boolean inPhpState) {
		super();
		this.lexerState = lexerState;
		this.inPhpState = inPhpState;
	}

	public String getType() {

		return PHPRegionTypes.PHP_CONTENT;
	}
}
