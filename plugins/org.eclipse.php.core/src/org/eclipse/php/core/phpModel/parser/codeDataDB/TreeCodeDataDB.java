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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.php.core.phpModel.phpElementData.CodeData;

public class TreeCodeDataDB implements CodeDataDB {

	private final Map treeDatabase = new HashMap(1000);;
	private final List flattenData = new ArrayList();
	private List chachedList = null;

	public synchronized void clear() {
		chachedList = null;
		treeDatabase.clear();
		flattenData.clear();
	}

	public synchronized List getCodeData(String name) {
		if (name == null) {
			return null;
		}
		return (List) treeDatabase.get(getCodeDataIdentifier(name));
	}

	public synchronized void addCodeData(CodeData codeData) {
		chachedList = null;
		String codeDataIdentifier = getCodeDataIdentifier(codeData.getName());
		List list = (ArrayList) treeDatabase.get(codeDataIdentifier);
		if (list == null) {
			list = new ArrayList();
			treeDatabase.put(codeDataIdentifier, list);
		} else {
			list.remove(codeData);
		}
		list.add(codeData);
		flattenData.add(codeData);
	}

	public synchronized void removeCodeData(CodeData codeData) {
		chachedList = null;
		String codeDataIdentifier = getCodeDataIdentifier(codeData.getName());
		ArrayList list = (ArrayList) treeDatabase.get(codeDataIdentifier);
		if (list == null) {
			return;
		}
		list.remove(codeData);
		if (list.isEmpty()) {
			treeDatabase.remove(list);
		}
		flattenData.remove(codeData);
	}

	/*
		returns an unmodifiable instance of the (chached) list
	*/
	public synchronized List asList() {
		if (chachedList != null)
			return chachedList;

		chachedList = Collections.unmodifiableList(flattenData);
		return chachedList;
	}

	private String getCodeDataIdentifier(String name) {
		return name.trim().toLowerCase();
	}

}