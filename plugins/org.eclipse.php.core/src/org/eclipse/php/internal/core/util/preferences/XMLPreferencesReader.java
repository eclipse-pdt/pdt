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

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.internal.core.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML preferences reader for reading XML structures from the preferences store.
 * This class works in combination with IXMLPreferencesStorable.
 */
public class XMLPreferencesReader {

	public static final char DELIMITER = (char) 5;
	protected static final Pattern LT_PATTERN = Pattern.compile("&lt;"); //$NON-NLS-1$
	protected static final Pattern GT_PATTERN = Pattern.compile("&gt;"); //$NON-NLS-1$
	protected static final Pattern QUOT_PATTERN = Pattern.compile("&quot;"); //$NON-NLS-1$
	protected static final Pattern APOS_PATTERN = Pattern.compile("&apos;"); //$NON-NLS-1$
	protected static final Pattern AMP_PATTERN = Pattern.compile("&amp;"); //$NON-NLS-1$
	public static final String STRING_DEFAULT = ""; //$NON-NLS-1$

	public static String getUnEscaped(String s) {
		s = LT_PATTERN.matcher(s).replaceAll("<"); //$NON-NLS-1$
		s = GT_PATTERN.matcher(s).replaceAll(">"); //$NON-NLS-1$
		s = QUOT_PATTERN.matcher(s).replaceAll("\""); //$NON-NLS-1$
		s = APOS_PATTERN.matcher(s).replaceAll("'"); //$NON-NLS-1$
		s = AMP_PATTERN.matcher(s).replaceAll("&"); //$NON-NLS-1$
		return s;
	}

	/**
	 * Read XML nodes to values map
	 * 
	 * @param nodeList
	 * @param skipEmptyNodes
	 * @return values map
	 */
	private static Map<String, Object> read(NodeList nodeList, boolean skipEmptyNodes) {
		Map<String, Object> map = new HashMap<String, Object>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); ++i) {
			Node n = nodeList.item(i);
			if (n.hasChildNodes()) {
				if (n.getFirstChild().getNodeType() == Node.TEXT_NODE) {
					map.put(n.getNodeName(), getUnEscaped(n.getFirstChild().getNodeValue()));
				} else {
					map.put(n.getNodeName(), read(n.getChildNodes(), skipEmptyNodes));
				}
			} else if (!skipEmptyNodes) {
				map.put(n.getNodeName(), ""); //$NON-NLS-1$
			}
		}
		return map;
	}

	/**
	 * Converts given XML string to values map
	 * 
	 * @param str
	 * @param skipEmptyNodes
	 * @return values map
	 */
	protected static Map<String, Object> read(String str, boolean skipEmptyNodes) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new ByteArrayInputStream(str.getBytes()));
			return read(doc.getChildNodes(), skipEmptyNodes);
		} catch (Exception e) {
			Logger.logException(e);
		}
		return null;
	}

	/**
	 * Reads a map of elements from the preferences by a given key.
	 * 
	 * @param store
	 * @param prefsKey
	 * @param skipEmptyNodes
	 * @return a map of elements from the preferences by a given key
	 */
	public static List<Map<String, Object>> read(IEclipsePreferences store, String prefsKey, boolean skipEmptyNodes) {
		String storedValue = store.get(prefsKey, null);
		if (storedValue == null)
			return new ArrayList<Map<String, Object>>();
		return getMapsFromValue(storedValue, skipEmptyNodes);
	}

	/**
	 * Returns array of maps with stored values.
	 * 
	 * @param storedValue
	 * @param skipEmptyNodes
	 * @return array of maps with stored values
	 */
	public static List<Map<String, Object>> getMapsFromValue(String storedValue, boolean skipEmptyNodes) {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		StringTokenizer st = new StringTokenizer(storedValue, new String(new char[] { DELIMITER }));
		while (st.hasMoreTokens()) {
			maps.add(read(st.nextToken(), skipEmptyNodes));
		}
		return maps;
	}

}
