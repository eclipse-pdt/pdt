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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.php.internal.core.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML preferences reader for reading XML structures from the prefernces store.
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

	private static HashMap read(NodeList nl) {
		HashMap map = new HashMap(nl.getLength());
		for (int i = 0; i < nl.getLength(); ++i) {
			Node n = nl.item(i);
			if (n.hasChildNodes()) {
				if (n.getFirstChild().getNodeType() == Node.TEXT_NODE) {
					map.put(n.getNodeName(), getUnEscaped(n.getFirstChild()
							.getNodeValue()));
				} else {
					map.put(n.getNodeName(), read(n.getChildNodes()));
				}
			}
		}
		return map;
	}

	protected static HashMap read(String str) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			// docBuilderFactory.setValidating(true);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new ByteArrayInputStream(str
					.getBytes()));

			return read(doc.getChildNodes());

		} catch (Exception e) {
			Logger.logException(e);
		}
		return null;
	}

	/**
	 * Reads a map of elements from the Preferences by a given key.
	 * 
	 * @param store
	 * @param prefsKey
	 * @return
	 */
	public static HashMap[] read(Preferences store, String prefsKey) {
		String storedValue = store.getString(prefsKey);
		return getHashFromStoredValue(storedValue);
	}

	public static HashMap[] getHashFromStoredValue(String storedValue) {

		ArrayList maps = new ArrayList();
		StringTokenizer st = new StringTokenizer(storedValue, new String(
				new char[] { DELIMITER }));
		while (st.hasMoreTokens()) {
			maps.add(read(st.nextToken()));
		}
		return (HashMap[]) maps.toArray(new HashMap[maps.size()]);

	}
}
