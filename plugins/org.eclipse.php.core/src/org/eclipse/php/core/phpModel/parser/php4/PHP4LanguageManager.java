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
package org.eclipse.php.core.phpModel.parser.php4;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.phpModel.parser.*;

public class PHP4LanguageManager implements PHPLanguageManager {

	private static final String PHP4_FUNCTIONS_PATH = "Resources/phpFunctions4.php";

	private PHPLanguageModel languageModel;

	public PHP4LanguageManager() {
		languageModel = new PHP4LanguageModel(this);
	}

	public IPhpModel getModel() {
		return languageModel;
	}

	public PHPParserManager createPHPParserManager() {
		return new PHP4ParserManager();
	}

	public String getPHPFunctionPath() {
		return PHP4_FUNCTIONS_PATH;
	}

	public ParserClient createParserClient(PHPUserModel userModel, IProject project) {
		return new PHP4DefaultParserClient(userModel, project);
	}

}