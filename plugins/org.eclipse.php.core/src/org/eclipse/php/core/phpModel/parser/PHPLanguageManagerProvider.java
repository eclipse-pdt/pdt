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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.core.phpModel.parser.php4.PHP4LanguageManager;
import org.eclipse.php.core.phpModel.parser.php5.PHP5LanguageManager;


public class PHPLanguageManagerProvider {

	private static PHPLanguageManagerProvider instance;

	public static PHPLanguageManagerProvider instance() {
		if (instance == null) {
			instance = new PHPLanguageManagerProvider();
		}
		return instance;
	}

	private Map models;

	private PHPLanguageManagerProvider() {
		models = new HashMap();
		initModels();
	}

	public PHPLanguageManager getPHPLanguageManager(String key) {
		return (PHPLanguageManager) models.get(key);
	}

	private void initModels() {
		models.put(PHPVersion.PHP4, new PHP4LanguageManager());
		models.put(PHPVersion.PHP5, new PHP5LanguageManager());
	}

}
