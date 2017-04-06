/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin.xml;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * An XML element.
 */
public class XMLElement {
	private Element xmlElement;
	protected Factory factory;

	public XMLElement() {
		// do nothing
	}

	public Element getElementNode() {
		return xmlElement;
	}

	public Attr addAttribute(String s, String s1) {
		Attr attr = factory.createAttribute(s, xmlElement);
		attr.setValue(s1);
		return attr;
	}

	public XMLElement createElement(int index, String s) {
		return factory.createElement(index, s, xmlElement);
	}

	public XMLElement createElement(String s) {
		return factory.createElement(s, xmlElement);
	}

	public XMLElement findElement(String s) {
		NodeList nodelist = xmlElement.getElementsByTagName(s);
		int i = nodelist == null ? 0 : nodelist.getLength();
		for (int j = 0; j < i; j++) {
			Node node = nodelist.item(j);
			String s1 = node.getNodeName().trim();
			if (s1.equals(s))
				return factory.newInstance((Element) node);
		}

		return createElement(s);
	}

	public XMLElement findElement(String s, int i) {
		NodeList nodelist = xmlElement.getElementsByTagName(s);
		int j = nodelist == null ? 0 : nodelist.getLength();
		for (int k = 0; k < j; k++) {
			Node node = nodelist.item(k);
			String s1 = node.getNodeName().trim();
			if (s1.equals(s) && k == i)
				return factory.newInstance((Element) node);
		}

		return createElement(s);
	}

	public String getAttributeValue(String s) {
		Attr attr = xmlElement.getAttributeNode(s);
		if (attr != null)
			return attr.getValue();

		return null;
	}

	public Map getAttributes() {
		Map<String, String> attributes = new LinkedHashMap<String, String>();
		NamedNodeMap attrs = xmlElement.getAttributes();
		if (null != attrs) {
			for (int i = 0; i < attrs.getLength(); i++) {
				Node attr = attrs.item(i);
				String name = attr.getNodeName();
				String value = attr.getNodeValue();
				attributes.put(name, value);
			}
		}
		return attributes;
	}

	public String getElementName() {
		return xmlElement.getNodeName();
	}

	public String getElementValue() {
		return getElementValue(xmlElement);
	}

	protected static String getElementValue(Element element) {
		String s = element.getNodeValue();
		if (s != null)
			return s;
		NodeList nodelist = element.getChildNodes();
		for (int i = 0; i < nodelist.getLength(); i++)
			if (nodelist.item(i) instanceof Text)
				return ((Text) nodelist.item(i)).getData();

		return null;
	}

	public Element getSubElement(String s) {
		NodeList nodelist = xmlElement.getElementsByTagName(s);
		int i = nodelist == null ? 0 : nodelist.getLength();
		for (int j = 0; j < i; j++) {
			Node node = nodelist.item(j);
			String s1 = node.getNodeName().trim();
			if (s1.equals(s))
				return (Element) node;
		}

		return null;
	}

	public String getSubElementValue(String s) {
		Element element = getSubElement(s);
		if (element == null)
			return null;

		String value = getElementValue(element);
		if (value == null)
			return null;

		return value.trim();
	}

	public boolean removeAttribute(String s) {
		try {
			xmlElement.removeAttribute(s);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean removeElement(String s, int i) {
		NodeList nodelist = xmlElement.getElementsByTagName(s);
		int j = nodelist == null ? 0 : nodelist.getLength();
		for (int k = 0; k < j; k++) {
			Node node = nodelist.item(k);
			String s1 = node.getNodeName().trim();
			if (s1.equals(s) && k == i) {
				xmlElement.removeChild(node);
				return true;
			}
		}

		return false;
	}

	public void setAttributeValue(String s, String s1) {
		Attr attr = xmlElement.getAttributeNode(s);
		if (attr == null)
			attr = addAttribute(s, s1);
		else
			attr.setValue(s1);
	}

	void setElement(Element element) {
		xmlElement = element;
	}

	protected static void setElementValue(Element element, String value) {
		String s = element.getNodeValue();
		if (s != null) {
			element.setNodeValue(value);
			return;
		}
		NodeList nodelist = element.getChildNodes();
		for (int i = 0; i < nodelist.getLength(); i++)
			if (nodelist.item(i) instanceof Text) {
				Text text = (Text) nodelist.item(i);
				text.setData(value);
				return;
			}

		return;
	}

	void setFactory(Factory factory1) {
		factory = factory1;
	}

	public void setSubElementValue(String s, String value) {
		Element element = getSubElement(s);
		if (element == null) {
			element = factory.document.createElement(s);
			element.appendChild(factory.document.createTextNode("temp")); //$NON-NLS-1$
			xmlElement.appendChild(element);
		}
		setElementValue(element, value);
	}

	public int sizeOfElement(String s) {
		NodeList nodelist = xmlElement.getElementsByTagName(s);
		int i = nodelist == null ? 0 : nodelist.getLength();
		return i;
	}

	public void updateElementValue(String s) {
		try {
			xmlElement.setNodeValue(s);
		} catch (DOMException ex) {
			NodeList nodelist = xmlElement.getChildNodes();
			int i = nodelist == null ? 0 : nodelist.getLength();
			if (i > 0) {
				for (int j = 0; j < i; j++)
					if (nodelist.item(j) instanceof Text) {
						((Text) nodelist.item(j)).setData(s);
						return;
					}
			} else {
				xmlElement.appendChild(factory.document.createTextNode(s));
			}
		}
	}

	public boolean hasChildNodes() {
		return xmlElement.hasChildNodes();
	}

	public void removeChildren() {
		while (xmlElement.hasChildNodes()) {
			xmlElement.removeChild(xmlElement.getFirstChild());
		}
	}

	public void copyChildrenTo(XMLElement destination) {
		NodeList nodelist = xmlElement.getChildNodes();
		int len = nodelist == null ? 0 : nodelist.getLength();
		for (int i = 0; i < len; i++) {
			Node node = nodelist.item(i);
			destination.importNode(node, true);
		}
	}

	public void importNode(Node node, boolean deep) {
		xmlElement.appendChild(xmlElement.getOwnerDocument().importNode(node, deep));
	}

	public boolean isEquivalent(XMLElement obj) {
		if (obj != null) {
			try {
				return elementsAreEquivalent(xmlElement, obj.getElementNode());
			} catch (Exception e) {
				// Catch and ignore just to be safe
			}
		}
		return false;
	}

	/**
	 * Same as isEquivalent() but doesn't ignore exceptions for test purposes.
	 * This avoids hiding an expected mismatch behind an unexpected exception.
	 * 
	 * @param obj
	 *            XMLElement to compare
	 * @return true if the elements are equivalent
	 */
	public boolean isEquivalentTest(XMLElement obj) {
		if (obj != null) {
			return elementsAreEquivalent(xmlElement, obj.getElementNode());
		}
		return false;
	}

	private static boolean elementsAreEquivalent(Element element, Element otherElement) {
		if (element == otherElement)
			return true;

		if (!element.getNodeName().equals(otherElement.getNodeName()))
			return false;

		if (element.hasChildNodes()) {
			if (otherElement.hasChildNodes() && attributesAreEqual(element, otherElement)) {
				// Compare child nodes
				NodeList nodelist = element.getChildNodes();
				NodeList otherNodelist = otherElement.getChildNodes();
				if (nodelist.getLength() == otherNodelist.getLength()) {
					Node node = nextNonTextNode(element.getFirstChild());
					Node otherNode = nextNonTextNode(otherElement.getFirstChild());
					while (node != null) {
						if (otherNode == null)
							return false;
						short nextNodeType = node.getNodeType();
						if (nextNodeType != otherNode.getNodeType())
							return false;
						// If elements, compare
						if (nextNodeType == Node.ELEMENT_NODE) {
							if (!elementsAreEquivalent((Element) node, (Element) otherNode))
								return false;
						}
						// Else if comment, compare
						else if (nextNodeType == Node.COMMENT_NODE) {
							if (!nodeValuesAreEqual(node, otherNode))
								return false;
						}
						// Else punt on other node types
						else {
							return false;
						}
						node = nextNonTextNode(node.getNextSibling());
						otherNode = nextNonTextNode(otherNode.getNextSibling());
					}
					// If also at end of other children, return equal
					if (otherNode == null)
						return true;
				}
			}
		} else if (!otherElement.hasChildNodes()) {
			return attributesAreEqual(element, otherElement);
		}
		return false;
	}

	private static Node nextNonTextNode(Node node) {
		while (node != null && node.getNodeType() == Node.TEXT_NODE)
			node = node.getNextSibling();
		return node;
	}

	private static boolean attributesAreEqual(Element element, Element otherElement) {
		NamedNodeMap attrs = element.getAttributes();
		NamedNodeMap otherAttrs = otherElement.getAttributes();
		if (attrs == null && otherAttrs == null) {
			// Return comparison of element values if there are no attributes
			return nodeValuesAreEqual(element, otherElement);
		}

		if (attrs.getLength() == otherAttrs.getLength()) {
			if (attrs.getLength() == 0)
				// Return comparison of element values if there are no
				// attributes
				return nodeValuesAreEqual(element, otherElement);

			for (int i = 0; i < attrs.getLength(); i++) {
				Node attr = attrs.item(i);
				Node otherAttr = otherAttrs.getNamedItem(attr.getNodeName());
				if (!nodeValuesAreEqual(attr, otherAttr))
					return false;
			}
			return true;
		}
		return false;
	}

	private static boolean nodeValuesAreEqual(Node node, Node otherNode) {
		String value = node.getNodeValue();
		String otherValue = otherNode.getNodeValue();
		if (value != null && otherValue != null) {
			if (value.equals(otherValue))
				return true;
		} else if (value == null && otherValue == null)
			return true;
		return false;
	}
}
