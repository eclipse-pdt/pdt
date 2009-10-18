/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesReader;
import org.eclipse.ui.preferences.IWorkingCopyManager;

/**
 * XML preferences reader for reading XML structures from the prefernces store.
 * This class works in combination with IXMLPreferencesStorable.
 */
public class XMLPreferencesReaderUI extends XMLPreferencesReader {

	/**
	 * Reads a map of elements from the IPreferenceStore by a given key.
	 * 
	 * @param store
	 * @param prefsKey
	 * @return
	 */
	public static HashMap[] read(IPreferenceStore store, String prefsKey) {
		ArrayList maps = new ArrayList();
		StringTokenizer st = new StringTokenizer(store.getString(prefsKey),
				new String(new char[] { DELIMITER }));
		while (st.hasMoreTokens()) {
			maps.add(read(st.nextToken()));
		}
		return (HashMap[]) maps.toArray(new HashMap[maps.size()]);
	}

	/**
	 * Reads a map of elements from the project Properties by a given key.
	 * 
	 * @param prefsKey
	 *            The key to store by.
	 * @param projectScope
	 *            The context for the project Scope
	 * @param workingCopyManager
	 * @return
	 */
	public static HashMap[] read(Key prefKey, ProjectScope projectScope,
			IWorkingCopyManager workingCopyManager) {

		String storedValue = prefKey.getStoredValue(projectScope,
				workingCopyManager);
		if (storedValue == null)
			storedValue = STRING_DEFAULT;
		return getHashFromStoredValue(storedValue);

	}
}
