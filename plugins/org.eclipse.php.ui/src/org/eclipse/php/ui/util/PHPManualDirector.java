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
package org.eclipse.php.ui.util;

import java.util.HashMap;
import java.util.Map;

public class PHPManualDirector {

	private final static String DEFAULT_KEY = "*";
	private String label;
	private Map map = new HashMap(50); 

	public PHPManualDirector (String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public void addPath (String name, String path) {
		map.put(name.toLowerCase(), path);
	}
	
	/**
	 * Returns path to the PHP element
	 * 
	 * @param name of the PHP element
	 * @param site file extension
	 * @return String
	 */
	public String getPath (String name, String extension) {
		name = name.toLowerCase();
		name = name.replace('_', '-');
		
		String value = map.containsKey(name) ? (String)map.get(name) : (String)map.get(DEFAULT_KEY);
		if (value == null) {
			value = "";
		}
		
 		value = value.replaceAll("%NAME", name);
		value = value.replaceAll("%EXT", extension);
		return value;
	}
}
