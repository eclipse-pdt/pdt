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

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.php.internal.server.core.builtin.Trace;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Utility class to create and read XML documents.
 */
public class XMLUtil {
	private static DocumentBuilder documentBuilder;

	/**
	 * XMLUtil constructor comment.
	 */
	public XMLUtil() {
		super();
	}

	public static DocumentBuilder getDocumentBuilder() {
		if (documentBuilder == null)
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setValidating(false);
				factory.setNamespaceAware(false);
				factory.setExpandEntityReferences(false);
				// In case we happen to have a Xerces parser, try to set the
				// feature that allows Java encodings to be used
				try {
					factory.setFeature("http://apache.org/xml/features/allow-java-encodings", true); //$NON-NLS-1$
				} catch (ParserConfigurationException e) {
					// Ignore if feature isn't supported
				}
				// factory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd",
				// new Boolean(false));
				documentBuilder = factory.newDocumentBuilder();
				documentBuilder.setEntityResolver(new EntityResolver() {
					@Override
					public InputSource resolveEntity(String publicId, String systemId)
							throws SAXException, IOException {
						return new InputSource(new ByteArrayInputStream(new byte[0]));
					}
				});
			} catch (Exception e) {
				Trace.trace(Trace.SEVERE, "Rrror creating document builder"); //$NON-NLS-1$
			}

		return documentBuilder;
	}

	/**
	 * Create a child of the given node at the given index.
	 *
	 * @param doc
	 *            a document
	 * @param element
	 *            an element
	 * @param index
	 *            an index
	 * @param nodeName
	 *            a node name
	 * @return org.w3c.dom.Element
	 */
	public static Element createChildElement(Document doc, Element element, int index, String nodeName) {
		Element element2 = doc.createElement(nodeName);
		try {
			NodeList childList = element.getElementsByTagName(nodeName);
			Node child = childList.item(index);
			element.insertBefore(element2, child);
		} catch (Exception e) {
			element.appendChild(element2);
		}
		return element2;
	}

	/**
	 * Create a child of the given node.
	 *
	 * @param doc
	 *            a document
	 * @param node
	 *            a node
	 * @param nodeName
	 *            a node name
	 * @return org.w3c.dom.Element
	 */
	public static Element createChildElement(Document doc, Node node, String nodeName) {
		Element element = doc.createElement(nodeName);
		node.appendChild(element);
		return element;
	}

	/*
	 * Set the value of the given node to the given text.
	 */
	public static void createTextChildElement(Document doc, Node node, String name, String value) {
		Element element = createChildElement(doc, node, name);
		element.appendChild(doc.createTextNode(value));
	}

	/**
	 * Return the attribute value.
	 * 
	 * @return java.lang.String
	 * @param element
	 *            org.w3c.dom.Element
	 * @param attr
	 *            java.lang.String
	 */
	public static String getAttributeValue(Element element, String attr) {
		return element.getAttributeNode(attr).getValue();
	}

	public static byte[] getContents(Document document) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			print(new PrintStream(out, true, "UTF-8"), document); //$NON-NLS-1$
			return out.toByteArray();
		} catch (Exception ex) {
			throw new IOException(ex.getLocalizedMessage());
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
					// ignore
				}
		}
	}

	protected static String getDocumentTypeData(DocumentType doctype) {
		String data = doctype.getName();
		if (doctype.getPublicId() != null) {
			data += " PUBLIC \"" + doctype.getPublicId() + "\""; //$NON-NLS-1$ //$NON-NLS-2$
			String systemId = doctype.getSystemId();
			if (systemId == null)
				systemId = ""; //$NON-NLS-1$
			data += " \"" + systemId + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		} else
			data += " SYSTEM \"" + doctype.getSystemId() + "\""; //$NON-NLS-1$ //$NON-NLS-2$

		return data;
	}

	/**
	 * Return an iterator for the subelements.
	 * 
	 * @return java.util.Iterator
	 * @param element
	 *            org.w3c.dom.Element
	 * @param name
	 *            java.lang.String
	 */
	public static Iterator getNodeIterator(Element element, String name) {
		List<Node> list = new ArrayList<Node>();
		NodeList nodeList = element.getElementsByTagName(name);

		int length = nodeList.getLength();
		for (int i = 0; i < length; i++)
			list.add(nodeList.item(i));

		return list.iterator();
	}

	/**
	 * Get the value of this node. Will return "" instead of null.
	 * 
	 * @return java.lang.String
	 * @param node
	 *            org.w3c.dom.Node
	 */
	public static String getNodeValue(Node node) {
		NodeList nodeList = node.getChildNodes();

		int length = nodeList.getLength();
		for (int i = 0; i < length; i++) {
			Node n = nodeList.item(i);
			if (n instanceof Text) {
				Text t = (Text) n;
				return t.getNodeValue();
			}
		}
		return ""; //$NON-NLS-1$
	}

	/*
	 * Get the value of a subnode.
	 * 
	 * @return java.lang.String
	 */
	public static String getSubNodeValue(Element element, String name) {
		NodeList nodeList = element.getElementsByTagName(name);
		return getNodeValue(nodeList.item(0)).trim();
	}

	/*
	 * Insert the given text.
	 */
	public static void insertText(Document doc, Node node, String text) {
		node.appendChild(doc.createCDATASection(text));
	}

	protected static String normalize(String s) {
		StringBuffer stringbuffer = new StringBuffer();
		int i = s == null ? 0 : s.length();
		for (int j = 0; j < i; j++) {
			char c = s.charAt(j);
			switch (c) {
			case 60: /* '<' */
				stringbuffer.append("&lt;"); //$NON-NLS-1$
				break;

			case 62: /* '>' */
				stringbuffer.append("&gt;"); //$NON-NLS-1$
				break;

			case 38: /* '&' */
				stringbuffer.append("&amp;"); //$NON-NLS-1$
				break;

			case 34: /* '"' */
				stringbuffer.append("&quot;"); //$NON-NLS-1$
				break;

			case 10: /* '\n' */
			case 13: /* '\r' */
			default:
				stringbuffer.append(c);
				break;

			}
		}

		return stringbuffer.toString();
	}

	protected static void print(PrintStream out, Node node) {
		if (node == null)
			return;
		short type = node.getNodeType();
		switch (type) {
		case Node.DOCUMENT_NODE: {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //$NON-NLS-1$
			// out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
			NodeList nodelist = node.getChildNodes();
			int size = nodelist.getLength();
			for (int i = 0; i < size; i++)
				print(out, nodelist.item(i));
			break;
		}

		case Node.DOCUMENT_TYPE_NODE: {
			DocumentType docType = (DocumentType) node;
			out.print("<!DOCTYPE " + getDocumentTypeData(docType) + ">\n"); //$NON-NLS-1$ //$NON-NLS-2$
			break;
		}

		case Node.ELEMENT_NODE: {
			out.print('<');
			out.print(node.getNodeName());
			NamedNodeMap map = node.getAttributes();
			if (map != null) {
				int size = map.getLength();
				for (int i = 0; i < size; i++) {
					Attr attr = (Attr) map.item(i);
					out.print(' ');
					out.print(attr.getNodeName());
					out.print("=\""); //$NON-NLS-1$
					out.print(normalize(attr.getNodeValue()));
					out.print('"');
				}
			}

			if (!node.hasChildNodes())
				out.print("/>"); //$NON-NLS-1$
			else {
				out.print('>');
				NodeList nodelist = node.getChildNodes();
				int numChildren = nodelist.getLength();
				for (int i = 0; i < numChildren; i++)
					print(out, nodelist.item(i));

				out.print("</"); //$NON-NLS-1$
				out.print(node.getNodeName());
				out.print('>');
			}
			break;
		}

		case Node.ENTITY_REFERENCE_NODE: {
			NodeList nodelist = node.getChildNodes();
			if (nodelist != null) {
				int size = nodelist.getLength();
				for (int i = 0; i < size; i++)
					print(out, nodelist.item(i));

			}
			break;
		}

		case Node.CDATA_SECTION_NODE: {
			out.print(normalize(node.getNodeValue()));
			break;
		}

		case Node.TEXT_NODE: {
			out.print(normalize(node.getNodeValue()));
			break;
		}

		case Node.PROCESSING_INSTRUCTION_NODE: {
			out.print("<?"); //$NON-NLS-1$
			out.print(node.getNodeName());
			String s = node.getNodeValue();
			if (s != null && s.length() > 0) {
				out.print(' ');
				out.print(s);
			}
			out.print("?>"); //$NON-NLS-1$
			break;
		}

		case Node.COMMENT_NODE: {
			out.print("<!--"); //$NON-NLS-1$
			out.print(node.getNodeValue());
			out.print("-->"); //$NON-NLS-1$
			break;
		}

		default: {
			out.print(normalize(node.getNodeValue()));
			break;
		}
		}
		out.flush();
	}

	public static void save(String filename, Document document) throws IOException {
		PrintStream out = null;
		try {
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream(filename)), true, "UTF-8"); //$NON-NLS-1$
			// traceNode(document, "");
			print(out, document);
		} catch (Exception ex) {
			throw new IOException(ex.getLocalizedMessage());
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
					// ignore
				}
		}
	}

	public static void save(String filename, Node node) throws IOException {
		PrintStream out = null;
		try {
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream(filename)), true, "UTF-8"); //$NON-NLS-1$
			print(out, node);
		} catch (Exception ex) {
			throw new IOException(ex.getLocalizedMessage());
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
					// ignore
				}
		}
	}

	/*
	 * Set the value of the subnode
	 *
	 * @param name java.lang.String
	 * 
	 * @param value java.lang.String
	 */
	public static void setNodeValue(Node node, String name, String value) {
		String s = node.getNodeValue();
		if (s != null) {
			node.setNodeValue(value);
			return;
		}
		NodeList nodelist = node.getChildNodes();
		for (int i = 0; i < nodelist.getLength(); i++) {
			if (nodelist.item(i) instanceof Text) {
				Text text = (Text) nodelist.item(i);
				text.setData(value);
				return;
			}
		}
		return;
	}

	public static String toString(Document document) {
		PrintStream out = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
			out = new PrintStream(baos);
			print(out, document);
			return new String(baos.toByteArray(), "UTF-8"); //$NON-NLS-1$
		} catch (Exception ex) {
			// ignore
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
					// ignore
				}
		}
		return null;
	}
}
