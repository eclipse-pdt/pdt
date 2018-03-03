/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.test;

import java.util.HashMap;
import java.util.Map;

public class FileInfo {
	private String name;
	private StringBuilder contents = new StringBuilder();
	private Map<String, String> config = new HashMap<>();

	public FileInfo(String fileName) {
		setName(fileName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContents() {
		return contents.toString();
	}

	public void appendContents(String contents) {
		this.contents.append(contents);
	}

	/**
	 * Returns the configuration entries (--CONFIG-- section contents in format
	 * key:value)
	 * 
	 * @return
	 */
	public Map<String, String> getConfig() {
		return config;
	}

}
