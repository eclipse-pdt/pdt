package org.eclipse.php.core.util.preferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;

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

	private static void appendEscapedChar(StringBuffer buffer, char c) {
		String replacement = getReplacement(c);
		if (replacement != null) {
			buffer.append('&');
			buffer.append(replacement);
			buffer.append(';');
		} else {
			buffer.append(c);
		}
	}

	private static String getReplacement(char c) {
		// Encode special XML characters into the equivalent character references.
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

	private static void write(StringBuffer sb, HashMap map) {
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
				sb.append(getEscaped(object.toString()));
			}
			sb.append("</"); //$NON-NLS-1$
			sb.append(key);
			sb.append(">"); //$NON-NLS-1$
		}
	}

	public static void write(IPreferenceStore store, String prefsKey, IXMLPreferencesStorable object) {
		StringBuffer sb = new StringBuffer();
		write(sb, object.storeToMap());
		store.setValue(prefsKey, sb.toString());
	}

	public static void write(IPreferenceStore store, String prefsKey, IXMLPreferencesStorable[] objects) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objects.length; ++i) {
			write(sb, objects[i].storeToMap());
			if (i < objects.length - 1) {
				sb.append(DELIMITER);
			}
		}
		store.setValue(prefsKey, sb.toString());
	}
}
