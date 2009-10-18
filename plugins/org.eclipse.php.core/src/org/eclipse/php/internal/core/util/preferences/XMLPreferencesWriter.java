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
package org.eclipse.php.internal.core.util.preferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Preferences;

/**
 * XML preferences writer for writing XML structures into the prefernces store.
 * This class works in combination with IXMLPreferencesStorable.
 */
public class XMLPreferencesWriter {

	public static final char DELIMITER = (char) 5;

	public static String getEscaped(String s) {
		StringBuffer result = new StringBuffer(s.length() + 10);
		for (int i = 0; i < s.length(); ++i)
			appendEscapedChar(result, s.charAt(i));
		return result.toString();
	}

	protected static void appendEscapedChar(StringBuffer buffer, char c) {
		String replacement = getReplacement(c);
		if (replacement != null) {
			buffer.append('&');
			buffer.append(replacement);
			buffer.append(';');
		} else {
			buffer.append(c);
		}
	}

	protected static String getReplacement(char c) {
		// Encode special XML characters into the equivalent character
		// references.
		// These five are defined by default for all XML documents.
		switch (c) {
		case '<':
			return "lt"; //$NON-NLS-1$
		case '>':
			return "gt"; //$NON-NLS-1$
		case '"':
			return "quot"; //$NON-NLS-1$
		case '\'':
			return "apos"; //$NON-NLS-1$
		case '&':
			return "amp"; //$NON-NLS-1$
		}
		return null;
	}

	protected static void write(StringBuffer sb, HashMap map) {
		Set keys = map.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
			String key = (String) i.next();
			sb.append("<"); //$NON-NLS-1$
			sb.append(key);
			sb.append(">"); //$NON-NLS-1$
			Object object = map.get(key);
			if (object instanceof HashMap) {
				write(sb, (HashMap) object);
			} else {
				if (object != null) {
					sb.append(getEscaped(object.toString()));
				} else {
					sb.append(""); //$NON-NLS-1$
				}
			}
			sb.append("</"); //$NON-NLS-1$
			sb.append(key);
			sb.append(">"); //$NON-NLS-1$
		}
	}

	/**
	 * Writes a group of IXMLPreferencesStorables to the given plugin
	 * preferences. The caller to this method should also make sure that
	 * {@link Plugin#savePluginPreferences()} is called in order to really store
	 * the changes.
	 * 
	 * @param pluginPreferences
	 *            A Preferences instance
	 * @param prefsKey
	 *            The key to store by.
	 * @param objects
	 *            The IXMLPreferencesStorables to store.
	 */
	public static void write(Preferences pluginPreferences, String prefsKey,
			IXMLPreferencesStorable[] objects) {
		StringBuffer sb = new StringBuffer();
		appendDelimitedString(sb, objects);
		pluginPreferences.setValue(prefsKey, sb.toString());
	}

	/**
	 * Writes an IXMLPreferencesStorable to the given plugin preferences. The
	 * caller to this method should also make sure that
	 * {@link Plugin#savePluginPreferences()} is called in order to really store
	 * the changes.
	 * 
	 * @param pluginPreferences
	 *            A Preferences instance
	 * @param prefsKey
	 *            The key to store by.
	 * @param object
	 *            The IXMLPreferencesStorable to store.
	 */
	public static void write(Preferences pluginPreferences, String prefsKey,
			IXMLPreferencesStorable object) {
		StringBuffer sb = new StringBuffer();
		write(sb, object.storeToMap());
		pluginPreferences.setValue(prefsKey, sb.toString());
	}

	// Append the elements one by one into the given StringBuffer.
	protected static void appendDelimitedString(StringBuffer buffer,
			IXMLPreferencesStorable[] elements) {
		if (elements != null) {
			for (int i = 0; i < elements.length; ++i) {
				write(buffer, elements[i].storeToMap());
				if (i < elements.length - 1) {
					buffer.append(DELIMITER);
				}
			}
		}
	}

	public static String storableElementsToString(
			IXMLPreferencesStorable[] elements) {
		StringBuffer sb = new StringBuffer();
		appendDelimitedString(sb, elements);
		return sb.toString();
	}
}
