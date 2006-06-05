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
package org.eclipse.php.core.phpModel.parser.php5;

import org.eclipse.php.core.phpModel.parser.*;


public class PHP5LanguageManager implements PHPLanguageManager {

	private static final String PHP5_FUNCTIONS_PATH = "Resources/phpFunctions5.php";

	private PHPLanguageModel languageModel;

	public PHP5LanguageManager() {
		languageModel = new PHP5LanguageModel(this);
	}

	public IPhpModel getModel() {
		return languageModel;
	}

	public PHPParserManager createPHPParserManager() {
		return new PHP5ParserManager();
	}

	public String getPHPFunctionPath() {
		return PHP5_FUNCTIONS_PATH;
	}

	public ParserClient createParserClient(PHPUserModel userModel) {
		return new PHP5DefaultParserClient(userModel);
	}

}
