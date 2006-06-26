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

	// singleton value 
	private static final PHPLanguageManagerProvider instance = new PHPLanguageManagerProvider(); 
	private PHPLanguageManagerProvider() { }
	
	// avaliable models
	private final static Map models = new HashMap();
	static {
		models.put(PHPVersion.PHP4, new PHP4LanguageManager());
		models.put(PHPVersion.PHP5, new PHP5LanguageManager());
	}
	
	/**
	 * @return language provider instance
	 */
	public static PHPLanguageManagerProvider instance() {
		return instance;
	}

	// get the relevant language model 
	public PHPLanguageManager getPHPLanguageManager(String key) {
		assert key.equals(PHPVersion.PHP4) || key.equals(PHPVersion.PHP5);
		
		return (PHPLanguageManager) models.get(key);
	}
}
