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

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.parser.regions.XMLParserRegionFactory;

/**
 * Description: Creates XML or PHP text regions
 * TODO: maybe we can move this small class into the php tokenizer   
 * @author Roy, 2006
 */
public class PHPRegionFactory extends XMLParserRegionFactory {

	private final static PHPRegionFactory instance = new PHPRegionFactory();
	private PHPRegionFactory() { }
	
	public static final PHPRegionFactory getInstance() {
		return instance;
	}

	/**
	 * Creates a PHP content token if needed
	 * else creates the as XML content
	 */
	public ITextRegion createToken(String context, int start, int textLength, int length, IProject project, String script) {
		assert (context == PHPRegionContext.PHP_CONTENT || context == PHPRegionContext.PHP_ASP_CONTENT);
		return new PhpScriptRegion(context, start, textLength, length, script, project);
	}
}
