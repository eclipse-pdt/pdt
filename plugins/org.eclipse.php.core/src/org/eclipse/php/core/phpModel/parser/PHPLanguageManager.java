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
package org.eclipse.php.core.phpModel.parser;

public interface PHPLanguageManager {

	public PHPParserManager createPHPParserManager();

	public String getPHPFunctionPath();

	public ParserClient createParserClient(PHPUserModel userModel);
	
	public IPhpModel getModel();

}
