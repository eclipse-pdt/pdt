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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.core.phpModel.parser.php4.PHP4LanguageManager;
import org.eclipse.php.internal.core.phpModel.parser.php5.PHP5LanguageManager;


public class PHPLanguageManagerProvider {

	// singleton value 
	private static final PHPLanguageManagerProvider instance = new PHPLanguageManagerProvider(); 
	private PHPLanguageManagerProvider() { }
	
	// available models
	private final static Map<String, PHPLanguageManager> models = new HashMap<String, PHPLanguageManager>();
	
	/**
	 * @return language provider instance
	 */
	public static PHPLanguageManagerProvider instance() {
		return instance;
	}

	// get the relevant language model 
	public synchronized PHPLanguageManager getPHPLanguageManager(String key) {
		assert key.equals(PHPVersion.PHP4) || key.equals(PHPVersion.PHP5);
		
		if (!models.containsKey(key)) {
			if (key.equals(PHPVersion.PHP4)) {
				models.put(PHPVersion.PHP4, new PHP4LanguageManager());
			} else {
				models.put(PHPVersion.PHP5, new PHP5LanguageManager());
			}
		}
		
		return (PHPLanguageManager) models.get(key);
	}
}
