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
package org.eclipse.php.core.phpModel.parser.codeDataDB;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;

import org.eclipse.php.core.phpModel.phpElementData.CodeData;

public class GlobalVariablesCodeDataDB implements CodeDataDB {

	private Hashtable hashtable;
	private TreeSet sortedData;
	private ArrayList cacheList;

	public GlobalVariablesCodeDataDB() {
		hashtable = new Hashtable(1000);
		sortedData = new TreeSet();
	}

	public synchronized void clear() {
		cacheList = null;
		hashtable.clear();
		sortedData.clear();
	}

	public synchronized List getCodeData(String name) {
		return null;
	}

	public synchronized void addCodeData(CodeData codeData) {
		cacheList = null;
		String name = codeData.getName().trim().toLowerCase();
		Integer count = (Integer) hashtable.get(name);
		if (count == null) {
			count = new Integer(1);
			sortedData.add(codeData);
		} else {
			count = new Integer(count.intValue() + 1);
		}
		hashtable.put(name, count);
	}

	public synchronized void removeCodeData(CodeData codeData) {
		cacheList = null;
		String name = codeData.getName().trim().toLowerCase();
		Integer count = (Integer) hashtable.get(name);
		if (count == null) {
			sortedData.remove(codeData);
			return;
		}
		int intCount = count.intValue() - 1;
		if (intCount <= 0) {
			sortedData.remove(codeData);
			hashtable.remove(name);
		} else {
			hashtable.put(name, new Integer(intCount));
		}
	}

	public synchronized List asList() {
		if (cacheList == null) {
			cacheList = new ArrayList(sortedData);
		}
		return cacheList;
	}

}