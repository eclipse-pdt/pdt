/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.parser.codeDataDB;

import java.util.*;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public class GlobalVariablesCodeDataDB implements CodeDataDB {

	private Map<String, Integer> codeDatas;
	private Set<CodeData> sortedData;
	private List<CodeData> cacheList;

	public GlobalVariablesCodeDataDB() {
		codeDatas = new HashMap<String, Integer>(1000);
		sortedData = new TreeSet<CodeData>();
	}

	public synchronized void clear() {
		cacheList = null;
		codeDatas.clear();
		sortedData.clear();
	}

	public synchronized Collection<CodeData> getCodeData(String name) {
		return null;
	}

	public synchronized void addCodeData(CodeData codeData) {
		cacheList = null;
		String name = codeData.getName().trim().toLowerCase();
		Integer count = codeDatas.get(name);
		if (count == null) {
			count = new Integer(1);
			sortedData.add(codeData);
		} else {
			count = new Integer(count.intValue() + 1);
		}
		codeDatas.put(name, count);
	}

	public synchronized void removeCodeData(CodeData codeData) {
		cacheList = null;
		String name = codeData.getName().trim().toLowerCase();
		Integer count = codeDatas.get(name);
		if (count == null) {
			sortedData.remove(codeData);
			return;
		}
		int intCount = count.intValue() - 1;
		if (intCount <= 0) {
			sortedData.remove(codeData);
			codeDatas.remove(name);
		} else {
			codeDatas.put(name, new Integer(intCount));
		}
	}

	public synchronized List<CodeData> asList() {
		if (cacheList == null) {
			cacheList = new ArrayList<CodeData>(sortedData);
		}
		return cacheList;
	}

}