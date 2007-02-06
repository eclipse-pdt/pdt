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

import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public class FilesCodeDataDB implements CodeDataDB {

	private Map files;
	private List sortedFiles;
	private boolean needsResort;
	private Comparator comparator;

	public FilesCodeDataDB() {
		files = new Hashtable(1000);
		sortedFiles = new ArrayList(1000);
		comparator = new Comparator() {
			public int compare(Object o, Object o1) {
				String name1 = new Path(((CodeData) o).getName()).lastSegment();
				String name2 = new Path(((CodeData) o1).getName()).lastSegment();
				return name1.compareToIgnoreCase(name2);
			}
		};
	}

	public synchronized void clear() {
		files.clear();
		sortedFiles.clear();
		needsResort = false;
	}

	public synchronized List getCodeData(String name) {
		return null;
	}

	public synchronized CodeData getUniqCodeData(String name) {
		if (name == null) {
			return null;
		}
		return (CodeData) files.get(name);
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

	public synchronized List asList() {
		if (needsResort) {
			Collections.sort(sortedFiles, comparator);
			needsResort = false;
		}
		return sortedFiles;
	}

}