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
package org.eclipse.php.core.documentModel.parser.regions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;

/**
 * Description: Holds the tokens extracted from the script   
 * @author Roy, 2006
 */
public class PhpTokenContainer {

	// holds the tokens and states
	private final List phpTokens;
	private final List lexerStates;

	private final static int DIV_DENOMINATOR = 10;

	public PhpTokenContainer() {
		this.phpTokens = new ArrayList();
		this.lexerStates = new ArrayList();
	}

	/**
	 * Push region to the end of the tokens list
	 * @param region
	 * @param lexerState
	 */
	public void pushToken(ITextRegion region, int lexerState) {
		assert region != null;

		if (phpTokens.size() % DIV_DENOMINATOR == 0) {
			lexerStates.add(new Integer(lexerState));
		}

		phpTokens.add(region);
	}

	/**
	 * find token for a given location 
	 * Naive implementation (TODO should fix to get better complexity)   
	 * @param offset
	 * @return
	 */
	public ITextRegion getTokenForOffset(int offset) {
		int index = 0;
		
		ITextRegion region = (ITextRegion) phpTokens.get(index);
		
		// go over the tokens
		while (index < region.getStart() || index > region.getEnd()) {
			region = (ITextRegion) phpTokens.get(index++);
		}
		
		if (index > region.getStart() && index < region.getEnd()) {
			return region;
		}
		return null;		
	}

	/**
	 * @return an unmodifiable list of php tokens that contained in the region
	 */
	public List getTokens() {
		assert phpTokens != null;
		
		return Collections.unmodifiableList(phpTokens);
	}

	/**
	 * Clears the containers
	 */
	public void clear() {
		this.phpTokens.clear();
		this.lexerStates.clear();
	}
}
