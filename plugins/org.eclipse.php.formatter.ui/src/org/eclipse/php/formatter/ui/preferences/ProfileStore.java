/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/

package org.eclipse.php.formatter.ui.preferences;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.formatter.core.FormatterCorePlugin;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.php.formatter.ui.FormatterUIPlugin;
import org.eclipse.php.formatter.ui.Logger;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.CustomProfile;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.Profile;
import org.osgi.service.prefs.BackingStoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ProfileStore {

	/**
	 * A SAX event handler to parse the xml format for profiles.
	 */
	private final static class ProfileDefaultHandler extends DefaultHandler {

		private List fProfiles;

		private String fName;
		private Map fSettings;

		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {

			if (qName.equals(XML_NODE_SETTING)) {

				final String key = attributes.getValue(XML_ATTRIBUTE_ID);
				final String value = attributes.getValue(XML_ATTRIBUTE_VALUE);
				fSettings.put(key, value);

			} else if (qName.equals(XML_NODE_PROFILE)) {

				fName = attributes.getValue(XML_ATTRIBUTE_NAME);
				fSettings = new HashMap(200);

			} else if (qName.equals(XML_NODE_ROOT)) {
				fProfiles = new ArrayList();
			}
		}

		public void endElement(String uri, String localName, String qName) {
			if (qName.equals(XML_NODE_PROFILE)) {
				fProfiles.add(new CustomProfile(fName, fSettings));
				fName = null;
				fSettings = null;
			}
		}

		public List getProfiles() {
			return fProfiles;
		}

	}

	/**
	 * Preference key where all profiles are stored
	 */
	private static final String PREF_FORMATTER_PROFILES = "org.eclipse.php.ui.formatterprofiles"; //$NON-NLS-1$

	/**
	 * Identifiers for the XML file.
	 */
	private final static String XML_NODE_ROOT = "profiles"; //$NON-NLS-1$
	private final static String XML_NODE_PROFILE = "profile"; //$NON-NLS-1$
	private final static String XML_NODE_SETTING = "setting"; //$NON-NLS-1$

	private final static String XML_ATTRIBUTE_ID = "id"; //$NON-NLS-1$
	private final static String XML_ATTRIBUTE_NAME = "name"; //$NON-NLS-1$
	private final static String XML_ATTRIBUTE_VALUE = "value"; //$NON-NLS-1$

	private ProfileStore() {
	}

	/**
	 * @return Returns the collection of profiles currently stored in the
	 *         preference store or <code>null</code> if the loading failed. The
	 *         elements are of type {@link CustomProfile} and are all updated to
	 *         the latest version.
	 * @throws CoreException
	 */
	public static List readProfiles(IScopeContext scope) throws CoreException {
		List res = readProfilesFromPreferences(scope);
		if (res == null) {
			return readOldForCompatibility(scope);
		}
		return res;
	}

	public static void writeProfiles(Collection profiles,
			IScopeContext instanceScope) throws CoreException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream(2000);
		try {
			writeProfilesToStream(profiles, stream);
			String val;
			try {
				val = stream.toString("UTF-8"); //$NON-NLS-1$
			} catch (UnsupportedEncodingException e) {
				val = stream.toString();
			}
			IEclipsePreferences uiPreferences = instanceScope
					.getNode(FormatterCorePlugin.PLUGIN_ID);
			uiPreferences.put(PREF_FORMATTER_PROFILES, val);
			uiPreferences.flush();
		} catch (BackingStoreException e) { /* ignore */
		} finally {
			try {
				stream.close();
			} catch (IOException e) { /* ignore */
			}
		}
	}

	public static List readProfilesFromPreferences(IScopeContext scope)
			throws CoreException {
		String string = scope.getNode(FormatterCorePlugin.PLUGIN_ID).get(
				PREF_FORMATTER_PROFILES, null);
		if (string != null && string.length() > 0) {
			byte[] bytes;
			try {
				bytes = string.getBytes("UTF-8"); //$NON-NLS-1$
			} catch (UnsupportedEncodingException e) {
				bytes = string.getBytes();
			}
			InputStream is = new ByteArrayInputStream(bytes);
			try {
				List res = readProfilesFromStream(new InputSource(is));
				return res;
			} finally {
				try {
					is.close();
				} catch (IOException e) { /* ignore */
				}
			}
		}
		return null;
	}

	/**
	 * Read the available profiles from the internal XML file and return them as
	 * collection.
	 * 
	 * @return returns a list of <code>CustomProfile</code> or <code>null</code>
	 */
	private static List readOldForCompatibility(IScopeContext instanceScope) {

		// in 3.0 M9 and less the profiles were stored in a file in the plugin's
		// meta data
		final String STORE_FILE = "code_formatter_profiles.xml"; //$NON-NLS-1$

		File file = FormatterUIPlugin.getDefault().getStateLocation()
				.append(STORE_FILE).toFile();
		if (!file.exists())
			return null;

		try {
			// note that it's wrong to use a file reader when XML declares
			// UTF-8: Kept for compatibility
			final FileReader reader = new FileReader(file);
			try {
				List res = readProfilesFromStream(new InputSource(reader));
				if (res != null) {
					writeProfiles(res, instanceScope);
				}
				file.delete(); // remove after successful write
				return res;
			} finally {
				reader.close();
			}
		} catch (CoreException e) {
			Logger.logException(e);// log but ignore
		} catch (IOException e) {
			Logger.logException(e); // log but ignore
		}
		return null;
	}

	/**
	 * Read the available profiles from the internal XML file and return them as
	 * collection or <code>null</code> if the file is not a profile file.
	 * 
	 * @param file
	 *            The file to read from
	 * @return returns a list of <code>CustomProfile</code> or <code>null</code>
	 * @throws CoreException
	 */
	public static List readProfilesFromFile(File file) throws CoreException {
		try {
			final FileInputStream reader = new FileInputStream(file);
			try {
				return readProfilesFromStream(new InputSource(reader));
			} finally {
				try {
					reader.close();
				} catch (IOException e) { /* ignore */
				}
			}
		} catch (IOException e) {
			throw createException(
					e,
					FormatterMessages.CodingStyleConfigurationBlock_error_reading_xml_message);
		}
	}

	/**
	 * Load profiles from a XML stream and add them to a map or
	 * <code>null</code> if the source is not a profile store.
	 * 
	 * @param inputSource
	 *            The input stream
	 * @return returns a list of <code>CustomProfile</code> or <code>null</code>
	 * @throws CoreException
	 */
	private static List readProfilesFromStream(InputSource inputSource)
			throws CoreException {

		final ProfileDefaultHandler handler = new ProfileDefaultHandler();
		try {
			final SAXParserFactory factory = SAXParserFactory.newInstance();
			final SAXParser parser = factory.newSAXParser();
			parser.parse(inputSource, handler);
		} catch (SAXException e) {
			throw createException(
					e,
					FormatterMessages.CodingStyleConfigurationBlock_error_reading_xml_message);
		} catch (IOException e) {
			throw createException(
					e,
					FormatterMessages.CodingStyleConfigurationBlock_error_reading_xml_message);
		} catch (ParserConfigurationException e) {
			throw createException(
					e,
					FormatterMessages.CodingStyleConfigurationBlock_error_reading_xml_message);
		}
		return handler.getProfiles();
	}

	/**
	 * Write the available profiles to the internal XML file.
	 * 
	 * @param profiles
	 *            List of <code>CustomProfile</code>
	 * @param file
	 *            File to write
	 * @throws CoreException
	 */
	public static void writeProfilesToFile(Collection profiles, File file)
			throws CoreException {
		final OutputStream writer;
		try {
			writer = new FileOutputStream(file);
			try {
				writeProfilesToStream(profiles, writer);
			} finally {
				try {
					writer.close();
				} catch (IOException e) { /* ignore */
				}
			}
		} catch (IOException e) {
			throw createException(
					e,
					FormatterMessages.CodingStyleConfigurationBlock_error_serializing_xml_message);
		}
	}

	/**
	 * Save profiles to an XML stream
	 * 
	 * @param profiles
	 *            List of <code>CustomProfile</code>
	 * @param stream
	 *            Stream to write
	 * @throws CoreException
	 */
	private static void writeProfilesToStream(Collection profiles,
			OutputStream stream) throws CoreException {

		try {
			final DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.newDocument();

			final Element rootElement = document.createElement(XML_NODE_ROOT);

			document.appendChild(rootElement);

			for (final Iterator iter = profiles.iterator(); iter.hasNext();) {
				final Profile profile = (Profile) iter.next();
				if (profile.isProfileToSave()) {
					final Element profileElement = createProfileElement(
							profile, document);
					rootElement.appendChild(profileElement);
				}
			}

			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml"); //$NON-NLS-1$
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			transformer.transform(new DOMSource(document), new StreamResult(
					stream));
		} catch (TransformerException e) {
			throw createException(
					e,
					FormatterMessages.CodingStyleConfigurationBlock_error_serializing_xml_message);
		} catch (ParserConfigurationException e) {
			throw createException(
					e,
					FormatterMessages.CodingStyleConfigurationBlock_error_serializing_xml_message);
		}
	}

	/*
	 * Create a new profile element in the specified document. The profile is
	 * not added to the document by this method.
	 */
	private static Element createProfileElement(Profile profile,
			Document document) {
		final Element element = document.createElement(XML_NODE_PROFILE);
		element.setAttribute(XML_ATTRIBUTE_NAME, profile.getName());

		final Iterator keyIter = ProfileManager.getKeys().iterator();

		while (keyIter.hasNext()) {
			final String key = (String) keyIter.next();
			final String value = (String) profile.getSettings().get(key);
			if (value != null) {
				final Element setting = document
						.createElement(XML_NODE_SETTING);
				setting.setAttribute(XML_ATTRIBUTE_ID, key);
				setting.setAttribute(XML_ATTRIBUTE_VALUE, value);
				element.appendChild(setting);
			} else {
				Logger.log(
						Logger.ERROR,
						"ProfileStore: Profile does not contain value for key " + key); //$NON-NLS-1$
			}
		}
		return element;
	}

	public static void checkCurrentOptionsVersion() {
		IScopeContext instanceScope = new InstanceScope();
		try {
			List profiles = ProfileStore.readProfiles(instanceScope);
			if (profiles == null) {
				profiles = Collections.EMPTY_LIST;
			}
			ProfileManager manager = new ProfileManager(profiles, instanceScope);
			if (manager.getSelected() instanceof CustomProfile) {
				manager.commitChanges(instanceScope); // updates JavaCore
														// options
			}
			savePreferences(instanceScope);

			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
					.getProjects();
			for (int i = 0; i < projects.length; i++) {
				IScopeContext scope = new ProjectScope(projects[i]);
				if (ProfileManager.hasProjectSpecificSettings(scope)) {
					manager = new ProfileManager(profiles, scope);
					manager.commitChanges(scope); // updates JavaCore project
													// options
					savePreferences(scope);
				}
			}
		} catch (CoreException e) {
			Logger.logException(e);
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}

	private static void savePreferences(final IScopeContext context)
			throws BackingStoreException {
		context.getNode(FormatterCorePlugin.PLUGIN_ID).flush();
	}

	/*
	 * Creates a UI exception for logging purposes
	 */
	private static CoreException createException(Throwable t, String message) {
		return new CoreException(new Status(IStatus.ERROR,
				FormatterUIPlugin.PLUGIN_ID, IStatus.ERROR, message, t));
	}
}
