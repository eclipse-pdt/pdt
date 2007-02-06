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

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.util.collections.BucketMap;

public class TreeCodeDataDB implements CodeDataDB {

	private List cachedList = null;;
	private final Set sortedData = new TreeSet();
	private final BucketMap elements = new BucketMap(1000);

	public synchronized void addCodeData(final CodeData codeData) {
		cachedList = null;
		final String key = getCodeDataIdentifier(codeData.getName());
		elements.add(key, codeData); // O(N)
		sortedData.add(codeData); // O(lgN)
	}

	/*
	 returns an unmodifiable instance of the (cached) list
	 */
	public synchronized List asList() {
		if (cachedList != null)
			return cachedList;

		cachedList = Arrays.asList(sortedData.toArray());
		return cachedList;
	}

	public synchronized void clear() {
		cachedList = null;
		elements.clear();
		sortedData.clear();
	}

	public synchronized List getCodeData(final String name) {
		if (name == null)
			return null;
		return (List) elements.get(getCodeDataIdentifier(name));
	}

	private String getCodeDataIdentifier(final String name) {
		return name.trim().toLowerCase();
	}

	public synchronized void removeCodeData(final CodeData codeData) {
		cachedList = null;
		final String key = getCodeDataIdentifier(codeData.getName());
		elements.remove(key, codeData);
		sortedData.remove(codeData);
	}

}