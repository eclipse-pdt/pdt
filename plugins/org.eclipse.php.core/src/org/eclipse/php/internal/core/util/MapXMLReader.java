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
package org.eclipse.php.internal.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The MapXMLReader does the reading of Maps saved by the MapXMLWriter. For
 * example, this XML output can be parsed by this reader into a Map:
 * 
 * <pre>
 * 		&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * 	&lt;map&gt;
 * 		&lt;key name=&quot;first&quot;&gt;
 * 			&lt;value&gt;good &gt; bye&lt;/value&gt;
 * 		&lt;/key&gt;
 * 		&lt;key name=&quot;second&quot;&gt;
 * 			&lt;value&gt;hello&lt;/value&gt;
 * 		&lt;/key&gt;
 * 		&lt;key name=&quot;list of elements&quot;&gt;
 * 			&lt;value&gt;A&lt;/value&gt;
 * 			&lt;value&gt;B&lt;/value&gt;
 * 			&lt;value&gt;C&lt;/value&gt;
 * 		&lt;/key&gt;
 * 	&lt;/map&gt;
 * </pre>
 * 
 */
public class MapXMLReader {

	/**
	 * Read a Map from the given input stream . The returned Map will always
	 * have a String key and a List of one or more mapped values (also Strings).
	 * 
	 * @param input
	 *            An InputStream.
	 * @return A Map read from the input stream.
	 * @throws IOException
	 *             If any I/O error occures.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static Map readMap(InputSource input) throws IOException,
			ParserConfigurationException, SAXException {
		Map map = new HashMap();
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document dom = builder.parse(input);
		NodeList keys = dom.getElementsByTagName(MapXMLWriter.KEY_TAG);
		for (int i = 0; i < keys.getLength(); i++) {
			Node node = keys.item(i);
			NamedNodeMap attributes = node.getAttributes();
			if (attributes != null) {
				Node keyName = attributes.getNamedItem(MapXMLWriter.NAME_TAG);
				if (keyName != null) {
					String key = keyName.getNodeValue();
					NodeList values = node.getChildNodes();
					List valuesList = new ArrayList(values.getLength());
					for (int j = 0; j < values.getLength(); j++) {
						Node valueNode = values.item(j);
						if (valueNode.getNodeType() == Node.ELEMENT_NODE) {
							valueNode = valueNode.getFirstChild();
							if (valueNode != null) {
								valuesList.add(valueNode.getNodeValue());
							}
						}
					}
					map.put(key, valuesList);
				}
			}
		}
		return map;
	}
}
