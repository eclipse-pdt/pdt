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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.internal.core.Logger;
import org.osgi.service.prefs.BackingStoreException;

/**
 * XML preferences writer for writing XML structures into the prefernces store.
 * This class works in combination with IXMLPreferencesStorable.
 */
public class XMLPreferencesWriter {

	public static final char DELIMITER = (char) 5;

	public static String getEscaped(String s) {
		StringBuilder result = new StringBuilder(s.length() + 10);
		for (int i = 0; i < s.length(); ++i)
			appendEscapedChar(result, s.charAt(i));
		return result.toString();
	}

	protected static void appendEscapedChar(StringBuilder buffer, char c) {
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

	@SuppressWarnings("unchecked")
	protected static void write(StringBuilder sb, Map<String, Object> map) {
		for (Entry<?, ?> entry : map.entrySet()) {
			String key = (String) entry.getKey();
			sb.append("<"); //$NON-NLS-1$
			sb.append(key);
			sb.append(">"); //$NON-NLS-1$
			Object object = entry.getValue();
			if (object instanceof Map) {
				write(sb, (Map<String, Object>) object);
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
	 * Writes a group of IXMLPreferencesStorables to the given plug-in
	 * preferences.
	 * 
	 * @param pluginPreferences
	 *            A Preferences instance
	 * @param prefsKey
	 *            The key to store by.
	 * @param objects
	 *            The IXMLPreferencesStorables to store.
	 */
	public static void write(IEclipsePreferences pluginPreferences, String prefsKey,
			List<IXMLPreferencesStorable> objects) {
		StringBuilder sb = new StringBuilder();
		appendDelimitedString(sb, objects.toArray(new IXMLPreferencesStorable[objects.size()]));
		pluginPreferences.put(prefsKey, sb.toString());
		try {
			pluginPreferences.flush();
		} catch (BackingStoreException e) {
			Logger.logException("Could not write XML preferences.", e); //$NON-NLS-1$
		}
	}

	/**
	 * Writes an IXMLPreferencesStorable to the given plug-in preferences.
	 * 
	 * @param pluginPreferences
	 *            A Preferences instance
	 * @param prefsKey
	 *            The key to store by.
	 * @param object
	 *            The IXMLPreferencesStorable to store.
	 */
	public static void write(IEclipsePreferences pluginPreferences, String prefsKey, IXMLPreferencesStorable object) {
		StringBuilder sb = new StringBuilder();
		write(sb, object.storeToMap());
		pluginPreferences.put(prefsKey, sb.toString());
		try {
			pluginPreferences.flush();
		} catch (BackingStoreException e) {
			Logger.logException("Could not write XML preferences.", e); //$NON-NLS-1$
		}
	}

	// Append the elements one by one into the given StringBuffer.
	protected static void appendDelimitedString(StringBuilder buffer, IXMLPreferencesStorable[] elements) {
		if (elements != null) {
			for (int i = 0; i < elements.length; ++i) {
				write(buffer, elements[i].storeToMap());
				if (i < elements.length - 1) {
					buffer.append(DELIMITER);
				}
			}
		}
	}

	public static String storableElementsToString(IXMLPreferencesStorable[] elements) {
		StringBuilder sb = new StringBuilder();
		appendDelimitedString(sb, elements);
		return sb.toString();
	}

}
