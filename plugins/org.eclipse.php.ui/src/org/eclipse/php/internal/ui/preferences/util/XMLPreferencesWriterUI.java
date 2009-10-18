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

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesWriter;
import org.eclipse.ui.preferences.IWorkingCopyManager;

/**
 * XML preferences writer for writing XML structures into the prefernces store.
 * This class works in combination with IXMLPreferencesStorable.
 */
public class XMLPreferencesWriterUI extends XMLPreferencesWriter {

	/**
	 * Writes a group of IXMLPreferencesStorables to the given the project
	 * properties.
	 * 
	 * @param prefsKey
	 *            The key to store by.
	 * @param objects
	 *            The IXMLPreferencesStorables to store.
	 * @param projectScope
	 *            The project Scope
	 * @param workingCopyManager
	 */
	public static void write(Key prefsKey, IXMLPreferencesStorable[] objects,
			ProjectScope projectScope, IWorkingCopyManager workingCopyManager) {
		StringBuffer sb = new StringBuffer();
		appendDelimitedString(sb, objects);
		prefsKey
				.setStoredValue(projectScope, sb.toString(), workingCopyManager);

	}

	/**
	 * Writes an IXMLPreferencesStorables to the given IPreferenceStore.
	 * 
	 * @param store
	 *            An IPreferenceStore instance
	 * @param prefsKey
	 *            The key to store by.
	 * @param object
	 *            The IXMLPreferencesStorables to store.
	 */
	public static void write(IPreferenceStore store, String prefsKey,
			IXMLPreferencesStorable object) {
		StringBuffer sb = new StringBuffer();
		write(sb, object.storeToMap());
		store.setValue(prefsKey, sb.toString());
	}

	/**
	 * Writes a group of IXMLPreferencesStorables to the given IPreferenceStore.
	 * 
	 * @param store
	 *            An IPreferenceStore instance
	 * @param prefsKey
	 *            The key to store by.
	 * @param objects
	 *            The IXMLPreferencesStorables to store.
	 */
	public static void write(IPreferenceStore store, String prefsKey,
			IXMLPreferencesStorable[] objects) {
		StringBuffer sb = new StringBuffer();
		appendDelimitedString(sb, objects);
		store.setValue(prefsKey, sb.toString());
	}
}
