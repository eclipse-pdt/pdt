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
package org.eclipse.php.internal.core.phpModel.parser.codeDataDB;

import java.util.*;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public class FilesCodeDataDB implements CodeDataDB {

	private Map<String, CodeData> files;
	private List<CodeData> sortedFiles;
	private boolean needsResort;
	private Comparator<CodeData> comparator;

	public FilesCodeDataDB() {
		files = new HashMap<String, CodeData>(1000);
		sortedFiles = new ArrayList<CodeData>(1000);
		comparator = new Comparator<CodeData>() {
			public int compare(CodeData o1, CodeData o2) {
				String name1 = o1.getName();
				String name2 = o2.getName();
				return name1.compareToIgnoreCase(name2);
			}
		};
	}

	public synchronized void clear() {
		files.clear();
		sortedFiles.clear();
		needsResort = false;
	}

	public synchronized Collection<CodeData> getCodeData(String name) {
		return null;
	}

	public synchronized CodeData getUniqCodeData(String name) {
		if (name == null) {
			return null;
		}
		return files.get(name);
	}

	public synchronized void addCodeData(CodeData codeData) {
		String name = codeData.getName();
		files.put(name, codeData);
		sortedFiles.add(codeData);
		needsResort = true;
	}

	public synchronized void removeCodeData(CodeData codeData) {
		String name = codeData.getName();
		files.remove(name);
		sortedFiles.remove(codeData);
	}

	public synchronized List<CodeData> asList() {
		if (needsResort) {
			Collections.sort(sortedFiles, comparator);
			needsResort = false;
		}
		return sortedFiles;
	}

}