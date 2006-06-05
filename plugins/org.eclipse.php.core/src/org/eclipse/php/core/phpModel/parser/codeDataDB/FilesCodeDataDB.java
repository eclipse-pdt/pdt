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

import java.io.File;
import java.util.*;

import org.eclipse.php.core.phpModel.phpElementData.CodeData;

public class FilesCodeDataDB implements CodeDataDB {

	private Hashtable hashtable;
	private ArrayList sortedData;
	private boolean needToSort;
	private Comparator comparator;

	public FilesCodeDataDB() {
		hashtable = new Hashtable(1000);
		sortedData = new ArrayList(1000);
		comparator = new Comparator() {
			public int compare(Object o, Object o1) {
				String name1 = new File(((CodeData) o).getName()).getName();
				String name2 = new File(((CodeData) o1).getName()).getName();
				return name1.compareToIgnoreCase(name2);
			}
		};
	}

	public synchronized void clear() {
		hashtable.clear();
		sortedData.clear();
		needToSort = false;
	}

	public synchronized List getCodeData(String name) {
		return null;
	}

	public synchronized CodeData getUniqCodeData(String name) {
		if (name == null) {
			return null;
		}
		return (CodeData) hashtable.get(name);
	}

	public synchronized void addCodeData(CodeData codeData) {
		String name = codeData.getName();
		hashtable.put(name, codeData);
		sortedData.add(codeData);
		needToSort = true;
	}

	public synchronized void removeCodeData(CodeData codeData) {
		String name = codeData.getName();
		hashtable.remove(name);
		sortedData.remove(codeData);
	}

	public synchronized List asList() {
		if (needToSort) {
			Collections.sort(sortedData, comparator);
			needToSort = false;
		}
		return sortedData;
	}

}