package org.eclipse.php.core.util.preferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.IWorkingCopyManager;

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

	
	/**
	 * Writes a group of IXMLPreferencesStorables to the given the project properties.
	 * 
	 * @param prefsKey The key to store by.
	 * @param objects The IXMLPreferencesStorables to store.
	 * @param projectScope The project Scope
	 * @param workingCopyManager
	 */
	public static void write(Key prefsKey, IXMLPreferencesStorable[] objects, ProjectScope projectScope, IWorkingCopyManager workingCopyManager){
		StringBuffer sb = new StringBuffer();
		appendDelimitedString(sb, objects);
		prefsKey.setStoredValue(projectScope, sb.toString(), workingCopyManager);
		
	}	
	
	
	/**
	 * Writes an IXMLPreferencesStorables to the given IPreferenceStore.
	 * 
	 * @param store An IPreferenceStore instance
	 * @param prefsKey The key to store by.
	 * @param object The IXMLPreferencesStorables to store.
	 */
	public static void write(IPreferenceStore store, String prefsKey, IXMLPreferencesStorable object) {
		StringBuffer sb = new StringBuffer();
		write(sb, object.storeToMap());
		store.setValue(prefsKey, sb.toString());
	}

	/**
	 * Writes a group of IXMLPreferencesStorables to the given IPreferenceStore.
	 * 
	 * @param store An IPreferenceStore instance
	 * @param prefsKey The key to store by.
	 * @param objects The IXMLPreferencesStorables to store.
	 */
	public static void write(IPreferenceStore store, String prefsKey, IXMLPreferencesStorable[] objects) {
		StringBuffer sb = new StringBuffer();
		appendDelimitedString(sb, objects);
		store.setValue(prefsKey, sb.toString());
	}

	/**
	 * Writes a group of IXMLPreferencesStorables to the given plugin preferences.
	 * The caller to this method should also make sure that {@link Plugin#savePluginPreferences()} is called
	 * in order to really store the changes.
	 * 
	 * @param pluginPreferences A Preferences instance
	 * @param prefsKey The key to store by.
	 * @param objects The IXMLPreferencesStorables to store.
	 */
	public static void write(Preferences pluginPreferences, String prefsKey, IXMLPreferencesStorable[] objects) {
		StringBuffer sb = new StringBuffer();
		appendDelimitedString(sb, objects);
		pluginPreferences.setValue(prefsKey, sb.toString());
	}

	/**
	 * Writes an IXMLPreferencesStorable to the given plugin preferences.
	 * The caller to this method should also make sure that {@link Plugin#savePluginPreferences()} is called
	 * in order to really store the changes.
	 * 
	 * @param pluginPreferences A Preferences instance
	 * @param prefsKey The key to store by.
	 * @param object The IXMLPreferencesStorable to store.
	 */
	public static void write(Preferences pluginPreferences, String prefsKey, IXMLPreferencesStorable object) {
		StringBuffer sb = new StringBuffer();
		write(sb, object.storeToMap());
		pluginPreferences.setValue(prefsKey, sb.toString());
	}

	// Append the elements one by one into the given StringBuffer.
	private static void appendDelimitedString(StringBuffer buffer, IXMLPreferencesStorable[] elements) {
		if (elements != null){
			for (int i = 0; i < elements.length; ++i) {
				write(buffer, elements[i].storeToMap());
				if (i < elements.length - 1) {
					buffer.append(DELIMITER);
				}
			}
		}
	}
	
	public static String storableElementsToString(IXMLPreferencesStorable[] elements){
		StringBuffer sb = new StringBuffer();
		appendDelimitedString(sb, elements);
		return sb.toString();
	}
}
