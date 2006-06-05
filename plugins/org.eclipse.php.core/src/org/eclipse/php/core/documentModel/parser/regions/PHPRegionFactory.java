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

import org.eclipse.php.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.parser.regions.XMLParserRegionFactory;

public class PHPRegionFactory extends XMLParserRegionFactory {

	private static final PHPRegionFactory instance = new PHPRegionFactory();

	public static PHPRegionFactory getInstance() {
		return instance;
	}

	private PHPRegionFactory() {
	}

	public ITextRegion createToken(String context, int start, int textLength, int length, String lang, String surroundingTag, String tokenText) {
		ITextRegion newRegion = null;
		if (context == PHPRegionContext.PHP_OPEN) {
			int trailingWhitespacesSize = this.getTrailingWhitespacesSize(tokenText);
			newRegion = new OpenPHPRegion(start, textLength - trailingWhitespacesSize, length);
		} else if (context == PHPRegionContext.PHP_CLOSE) {
			newRegion = new ClosePHPRegion(start, textLength, length);
		} else if (context == PHPRegionContext.PHP_CONTENT || context == PHPRegionContext.PHP_ASP_CONTENT) {
			newRegion = new PHPContentRegion(start, textLength, length);
		} else
			newRegion = super.createToken(context, start, textLength, length, lang, surroundingTag);

		return newRegion;
	}

	private int getTrailingWhitespacesSize(String tokenText) {
		//assert !tokenText.equals("");

		for (int trailingWhitespacesLocation = tokenText.length() - 1; trailingWhitespacesLocation != -1; --trailingWhitespacesLocation) {
			char c = tokenText.charAt(trailingWhitespacesLocation);
			if (!Character.isWhitespace(c)) {
				return tokenText.length() - trailingWhitespacesLocation - 1;
			}
		}

		return 0;
	}

}
