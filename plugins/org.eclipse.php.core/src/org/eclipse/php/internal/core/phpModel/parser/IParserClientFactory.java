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
package org.eclipse.php.internal.core.phpModel.parser;



public interface IParserClientFactory {
	
	public static final int fileAdded = 1;
	public static final int fileChanged = 2;

	public boolean isParsable(String fileName, int parsingReason);
	
	/**
	 * 
	 * @param fileName
	 * @param parsingReason
	 * @return null if the parsing is not neededd for the given file with the given reason
	 */
	public ParserClient create();
	
	public void dispose();
}
