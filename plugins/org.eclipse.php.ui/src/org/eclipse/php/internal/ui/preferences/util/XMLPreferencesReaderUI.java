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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesReader;

/**
 * XML preferences reader for reading XML structures from the prefernces store.
 * This class works in combination with IXMLPreferencesStorable.
 */
@Deprecated
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
		StringTokenizer st = new StringTokenizer(store.getString(prefsKey), String.valueOf(DELIMITER));
		while (st.hasMoreTokens()) {
			maps.add(read(st.nextToken(), false));
		}
		return (HashMap[]) maps.toArray(new HashMap[maps.size()]);
	}

}
