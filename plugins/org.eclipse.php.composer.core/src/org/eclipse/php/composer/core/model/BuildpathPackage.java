/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.compiler.util.GenericXMLWriter;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.dltk.internal.core.util.Messages;
import org.eclipse.dltk.internal.core.util.Util;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@SuppressWarnings("restriction")
public class BuildpathPackage {

	private static final String CURRENT_VERSION = "1"; //$NON-NLS-1$
	private static final String TAG_VERSION = "version"; //$NON-NLS-1$
	private static final String TAG_USERLIBRARY = "composerPackage"; //$NON-NLS-1$
	private static final String TAG_PATH = "path"; //$NON-NLS-1$
	private static final String TAG_ARCHIVE = "archive"; //$NON-NLS-1$
	private static final String TAG_SYSTEMLIBRARY = "systemlibrary"; //$NON-NLS-1$

	public BuildpathPackage(IBuildpathEntry[] entries, boolean isSystem) {
		// TODO Auto-generated constructor stub
	}

	public static String serialize(IBuildpathEntry[] entries, boolean isSystemLibrary) throws IOException {

		ByteArrayOutputStream s = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(s, StandardCharsets.UTF_8); // $NON-NLS-1$

		XMLWriter xmlWriter = new XMLWriter(writer, null, true);

		HashMap<String, String> library = new HashMap<String, String>();
		library.put(TAG_VERSION, String.valueOf(CURRENT_VERSION));
		library.put(TAG_SYSTEMLIBRARY, String.valueOf(isSystemLibrary));
		xmlWriter.printTag(TAG_USERLIBRARY, library, true, true, false);

		for (int i = 0, length = entries.length; i < length; ++i) {
			BuildpathEntry cpEntry = (BuildpathEntry) entries[i];

			HashMap<String, String> archive = new HashMap<String, String>();
			archive.put(TAG_PATH, cpEntry.getPath().toString());

			// boolean hasExtraAttributes = cpEntry.extraAttributes != null
			// && cpEntry.extraAttributes.length != 0;
			boolean hasExtraAttributes = false;
			boolean hasRestrictions = cpEntry.getAccessRuleSet() != null; // access
			// rule
			// set
			// is
			// null
			// if
			// no
			// access
			// rules
			xmlWriter.printTag(TAG_ARCHIVE, archive, true, true, !(hasExtraAttributes || hasRestrictions));

			// write extra attributes if necessary
			if (hasExtraAttributes) {
				// cpEntry.encodeExtraAttributes(xmlWriter, true, true);
			}

			// write extra attributes and restriction if necessary
			if (hasRestrictions) {
				// cpEntry.encodeAccessRules(xmlWriter, true, true);
			}

			// write archive end tag if necessary
			if (hasExtraAttributes || hasRestrictions) {
				xmlWriter.endTag(TAG_ARCHIVE, true/* insert tab */,
						true/*
							 * insert new line
							 */);
			}
		}
		xmlWriter.endTag(TAG_USERLIBRARY, true/* insert tab */,
				true/*
					 * insert new line
					 */);
		writer.flush();
		writer.close();
		xmlWriter.close();
		return s.toString(StandardCharsets.UTF_8.name());

	}

	public static BuildpathPackage createFromString(Reader reader) throws IOException {
		Element cpElement;
		try {
			DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			cpElement = parser.parse(new InputSource(reader)).getDocumentElement();
		} catch (SAXException e) {
			throw new IOException(Messages.file_badFormat);
		} catch (ParserConfigurationException e) {
			throw new IOException(Messages.file_badFormat);
		} finally {
			reader.close();
		}

		if (!cpElement.getNodeName().equalsIgnoreCase(TAG_USERLIBRARY)) {
			throw new IOException(Messages.file_badFormat);
		}
		// String version= cpElement.getAttribute(TAG_VERSION);
		// in case we update the format: add code to read older versions

		boolean isSystem = Boolean.valueOf(cpElement.getAttribute(TAG_SYSTEMLIBRARY)).booleanValue();

		NodeList list = cpElement.getChildNodes();
		int length = list.getLength();

		List<IBuildpathEntry> res = new ArrayList<IBuildpathEntry>(length);
		for (int i = 0; i < length; ++i) {
			Node node = list.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (element.getNodeName().equals(TAG_ARCHIVE)) {
					String path = element.getAttribute(TAG_PATH);
					NodeList children = element.getElementsByTagName("*"); //$NON-NLS-1$
					boolean[] foundChildren = new boolean[children.getLength()];
					NodeList attributeList = BuildpathEntry.getChildAttributes(BuildpathEntry.TAG_ATTRIBUTES, children,
							foundChildren);

					/*
					 * IBuildpathAttribute[] extraAttributes = BuildpathEntry
					 * .decodeExtraAttributes(attributeList); attributeList =
					 * BuildpathEntry.getChildAttributes(
					 * BuildpathEntry.TAG_ACCESS_RULES, children,
					 * foundChildren); IAccessRule[] accessRules =
					 * BuildpathEntry .decodeAccessRules(attributeList);
					 */

					IBuildpathEntry entry = DLTKCore.newLibraryEntry(Path.fromPortableString(path), new IAccessRule[0],
							new IBuildpathAttribute[0], false, true);
					res.add(entry);
				}
			}
		}

		IBuildpathEntry[] entries = (IBuildpathEntry[]) res.toArray(new IBuildpathEntry[res.size()]);

		return new BuildpathPackage(entries, isSystem);
	}

	private static class XMLWriter extends GenericXMLWriter {

		public XMLWriter(Writer writer, IScriptProject project, boolean printXmlVersion) {
			super(writer, Util.getLineSeparator((String) null, project), printXmlVersion);
		}
	}
}
