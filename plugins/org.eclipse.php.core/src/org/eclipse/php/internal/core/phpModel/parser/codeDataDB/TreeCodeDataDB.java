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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public class TreeCodeDataDB implements CodeDataDB {

	private List chachedList = null;;
	private final Set sortedData = new TreeSet();
	private final Map treeDatabase = new HashMap(1000);

	public synchronized void addCodeData(final CodeData codeData) {
		chachedList = null;
		final String codeDataIdentifier = getCodeDataIdentifier(codeData.getName());
		List list = (ArrayList) treeDatabase.get(codeDataIdentifier);
		if (list == null) {
			list = new ArrayList();
			treeDatabase.put(codeDataIdentifier, list);
		} else
			list.remove(codeData);
		list.add(codeData);
		sortedData.add(codeData);
	}

	/*
	 returns an unmodifiable instance of the (chached) list
	 */
	public synchronized List asList() {
		if (chachedList != null)
			return chachedList;

		chachedList = Arrays.asList(sortedData.toArray());
		return chachedList;
	}

	public synchronized void clear() {
		chachedList = null;
		treeDatabase.clear();
		sortedData.clear();
	}

	public synchronized List getCodeData(final String name) {
		if (name == null)
			return null;
		return (List) treeDatabase.get(getCodeDataIdentifier(name));
	}

	private String getCodeDataIdentifier(final String name) {
		return name.trim().toLowerCase();
	}

	public synchronized void removeCodeData(final CodeData codeData) {
		chachedList = null;
		final String codeDataIdentifier = getCodeDataIdentifier(codeData.getName());
		final ArrayList list = (ArrayList) treeDatabase.get(codeDataIdentifier);
		if (list == null)
			return;
		list.remove(codeData);
		if (list.isEmpty())
			treeDatabase.remove(list);
		sortedData.remove(codeData);
	}

}