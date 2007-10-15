/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.project.options;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.internal.resources.XMLWriter;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.internal.core.*;
import org.eclipse.php.internal.core.project.IIncludePathContainer;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathEntry;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.osgi.service.prefs.BackingStoreException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class PHPProjectOptions {

	private static final String LOCATION_INCLUDE_PATH = "Include Path"; //$NON-NLS-1$
	private static final String OWNER_PHP_INCLUDE_PATH = "phpIncludePath"; //$NON-NLS-1$
	private static final String OWNER_ATTRIBUTE = "Owner"; //$NON-NLS-1$

	public static final String BUILDER_ID = PHPCorePlugin.ID + ".PhpIncrementalProjectBuilder"; //$NON-NLS-1$
	static final IIncludePathEntry[] EMPTY_INCLUDEPATH = {};

	private static final String PREF_QUALIFIER = PHPCorePlugin.ID + ".projectOptions"; //$NON-NLS-1$
	private static final String OLD_FILE_NAME = ".projectOptions"; //$NON-NLS-1$

	private static final String TAG_OPTION = "projectOption"; //$NON-NLS-1$
	private static final String TAG_OPTIONS = "phpProjectOptions"; //$NON-NLS-1$
	/**
	 * Name of the User Library Container id.
	 */
	public static final String USER_LIBRARY_CONTAINER_ID = "org.eclipse.php.USER_LIBRARY"; //$NON-NLS-1$

	public static PHPProjectOptions forProject(final IProject project) {
		if (!project.exists() || !project.isAccessible()) {
			return null;
		}
		PHPNature nature = null;
		try {
			nature = (PHPNature) project.getNature(PHPNature.ID);
		} catch (final CoreException e) {
			PHPCorePlugin.log(e);
		}
		if (nature != null)
			return nature.getOptions();
		return null;
	}

	public static IIncludePathContainer getIncludePathContainer(final IPath path, final IProject project2) {
		return null;
	}

	public static IncludePathContainerInitializer getIncludePathContainerInitializer(final String string) {
		return null;
	}

	public static IPath getIncludePathVariable(final String variableName) {
		return IncludePathVariableManager.instance().getIncludePathVariable(variableName);
	}

	public static String[] getIncludePathVariableNames() {
		return IncludePathVariableManager.instance().getIncludePathVariableNames();
	}

	public static IPath getResolvedVariablePath(final IPath path) {
		return IncludePathVariableManager.instance().getIncludePathVariable(path.toString());
	}

	public static void setIncludePathVariables(final String[] names, final IPath[] paths, final SubProgressMonitor monitor) {
		IncludePathVariableManager.instance().setIncludePathVariables(names, paths, monitor);
	}

	private IIncludePathEntry[] includePathEntries = {};

	private final Map<String, List<IPhpProjectOptionChangeListener>> optionsChangeListenersMap = new HashMap<String, List<IPhpProjectOptionChangeListener>>();

	private IProject project;
	private IEclipsePreferences preferences;

	public PHPProjectOptions(final IProject project) {
		//		assert project != null;
		this.project = project;
		ProjectScope projectScope = new ProjectScope(project);
		preferences = projectScope.getNode(PREF_QUALIFIER);
		loadIncludePath();

		// backward compatible
		loadOldConfiguration();
	}

	public void addOptionChangeListener(final String optionKey, final IPhpProjectOptionChangeListener optionChangeListener) {
		List<IPhpProjectOptionChangeListener> optionChangeListeners = optionsChangeListenersMap.get(optionKey);
		if (optionChangeListeners == null) {
			optionChangeListeners = new ArrayList<IPhpProjectOptionChangeListener>();
			optionsChangeListenersMap.put(optionKey, optionChangeListeners);
		}
		if (!optionChangeListeners.contains(optionChangeListener))
			optionChangeListeners.add(optionChangeListener);
	}

	public void removeOptionChangeListener(final String optionKey, final IPhpProjectOptionChangeListener optionChangeListener) {
		List<IPhpProjectOptionChangeListener> optionChangeListeners = optionsChangeListenersMap.get(optionKey);
		if (optionChangeListeners != null) {
			optionChangeListeners.remove(optionChangeListener);
		}
	}

	public void notifyOptionChangeListeners(final String key, final Object oldValue, final Object newValue) {
		List<IPhpProjectOptionChangeListener> optionChangeListeners = optionsChangeListenersMap.get(key);
		if (optionChangeListeners == null)
			return;
		for (IPhpProjectOptionChangeListener phpProjectOptionChangeListener : optionChangeListeners) {
			phpProjectOptionChangeListener.notifyOptionChanged(oldValue, newValue);
		}
	}

	public IProject getProject() {
		return project;
	}

	public Object getOption(final String key) {
		return preferences.get(key, null);
	}

	public void setOption(final String key, final Object value) {
		final Object oldValue = getOption(key);
		if (oldValue != null) {
			if (value != null && value.equals(oldValue)) {
				return;
			}
		} else if (value == null) {
			return;
		}
		preferences.put(key, value.toString());
		flushPrefs();
		notifyOptionChangeListeners(key, oldValue, value);
	}

	public Object removeOption(final String key) {
		final Object object = getOption(key);
		if (object != null) {
			preferences.remove(key);
			flushPrefs();
		}
		return object;
	}

	private void flushPrefs() {
		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}

	public Object removeOptionNotify(final String key) {
		final Object object = removeOption(key);
		notifyOptionChangeListeners(key, object, null);

		return object;
	}

	public void modifyIncludePathEntry(final IIncludePathEntry newEntry, final IProject jproject, final IPath containerPath, final IProgressMonitor monitor) {
		throw new RuntimeException("implement me"); //$NON-NLS-1$
	}

	public IIncludePathEntry[] readRawIncludePath() {
		return includePathEntries;
	}

	public void removeResourceFromIncludePath(final IResource resource) {
		if (includePathEntries.length == 0)
			return;
		List<IIncludePathEntry> newIncludePathEntries = new ArrayList<IIncludePathEntry>(includePathEntries.length);
		for (int i = 0; i < includePathEntries.length; ++i) {
			if (includePathEntries[i].getResource() == resource) {
				continue;
			}
			newIncludePathEntries.add(includePathEntries[i]);
		}
		try {
			setRawIncludePath(newIncludePathEntries.toArray(new IIncludePathEntry[newIncludePathEntries.size()]), null);
			return;
		} catch (final Exception e) {
			PHPCorePlugin.log(e);
		}
	}

	public void renameResourceAtIncludePath(final IResource from, final IResource to) {
		if (includePathEntries.length == 0)
			return;
		List<IIncludePathEntry> newIncludePathEntries = new ArrayList<IIncludePathEntry>(includePathEntries.length);
		for (int i = 0; i < includePathEntries.length; ++i) {
			if (includePathEntries[i].getResource() == from) {
				IIncludePathEntry newSourceEntry = IncludePathEntry.newProjectEntry(to.getFullPath(), to, false);
				newIncludePathEntries.add(newSourceEntry);
			} else {
				newIncludePathEntries.add(includePathEntries[i]);
			}
		}
		try {
			setRawIncludePath(newIncludePathEntries.toArray(new IIncludePathEntry[newIncludePathEntries.size()]), null);
		} catch (final Exception e) {
			PHPCorePlugin.log(e);
		}
	}

	public void setRawIncludePath(final IIncludePathEntry[] newIncludePathEntries, final SubProgressMonitor subProgressMonitor) {
		final IIncludePathEntry[] oldValue = includePathEntries;
		includePathEntries = newIncludePathEntries;
		IncludePathEntry.updateProjectReferences(includePathEntries, oldValue, project, subProgressMonitor);

		saveIncludePath();
		notifyOptionChangeListeners(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, oldValue, newIncludePathEntries);
	}

	private void saveIncludePath() {
		try {
			final ByteArrayOutputStream s = new ByteArrayOutputStream();
			final XMLWriter xmlWriter = new XMLWriter(s);

			xmlWriter.startTag(IncludePathEntry.TAG_INCLUDEPATH, null);
			for (int i = 0; i < includePathEntries.length; ++i) {
				((IncludePathEntry) includePathEntries[i]).elementEncode(xmlWriter, project.getFullPath(), true);
			}
			xmlWriter.endTag(IncludePathEntry.TAG_INCLUDEPATH);
			xmlWriter.flush();
			xmlWriter.close();

			preferences.put(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, new String(s.toByteArray()));
			flushPrefs();
			validateIncludePath();
		} catch (IOException e) {
			PHPCorePlugin.log(e);
		} finally {

		}
	}

	private void loadIncludePath() {
		try {
			String includePathXml = preferences.get(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, null);

			if (includePathXml == null) {
				return;
			}

			Element cpElement;
			final Reader reader = new StringReader(includePathXml);

			try {
				final DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				cpElement = parser.parse(new InputSource(reader)).getDocumentElement();
			} catch (final Exception e) {
				throw new IOException(CoreMessages.PHPProjectOptions_1);
			} finally {
				reader.close();
			}

			final List<IIncludePathEntry> paths = new ArrayList<IIncludePathEntry>();
			NodeList list = cpElement.getElementsByTagName(IncludePathEntry.TAG_INCLUDEPATHENTRY);
			for (int i = 0; i < list.getLength(); ++i) {
				final Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					final IIncludePathEntry entry = IncludePathEntry.elementDecode((Element) node, this);
					paths.add(entry);
				}
			}
			final int pathSize = paths.size();
			includePathEntries = new IIncludePathEntry[pathSize];
			paths.toArray(includePathEntries);
		} catch (IOException e) {
			PHPCorePlugin.log(e);
		} finally {

		}
	}

	private void loadOldConfiguration() {
		final IFile optionsFile = project.getFile(OLD_FILE_NAME);
		if (!optionsFile.exists()) {
			return;
		}

		final List<IIncludePathEntry> paths = new ArrayList<IIncludePathEntry>();
		includePathEntries = EMPTY_INCLUDEPATH;
		try {
			Element cpElement;
			final Reader reader = new InputStreamReader(optionsFile.getContents());

			try {
				final DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				cpElement = parser.parse(new InputSource(reader)).getDocumentElement();
			} catch (final Exception e) {
				throw new IOException(CoreMessages.PHPProjectOptions_1);
			} finally {
				reader.close();
			}

			if (!cpElement.getNodeName().equalsIgnoreCase(TAG_OPTIONS))
				throw new IOException(CoreMessages.PHPProjectOptions_1);
			NodeList list = cpElement.getElementsByTagName(TAG_OPTION);
			int length = list.getLength();
			for (int i = 0; i < length; ++i) {
				final Element element = (Element) list.item(i);
				final String key = element.getAttribute("name"); //$NON-NLS-1$
				final String value = element.getFirstChild().getNodeValue().trim();
				preferences.put(key, value);
			}

			list = cpElement.getElementsByTagName(IncludePathEntry.TAG_INCLUDEPATH);
			if (list.getLength() > 0) {
				final Element includePathElement = (Element) list.item(0);
				list = includePathElement.getElementsByTagName(IncludePathEntry.TAG_INCLUDEPATHENTRY);
				length = list.getLength();

				for (int i = 0; i < length; ++i) {
					final Node node = list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						final IIncludePathEntry entry = IncludePathEntry.elementDecode((Element) node, this);
						paths.add(entry);
					}
				}

				final int pathSize = paths.size();
				includePathEntries = new IIncludePathEntry[pathSize];
				paths.toArray(includePathEntries);
			}

			saveIncludePath();

			optionsFile.delete(true, new NullProgressMonitor());
		} catch (final IOException e) {
			PHPCorePlugin.log(e);
		} catch (final CoreException e) {
			PHPCorePlugin.log(e);
		}
	}

	public void validateIncludePath() {
		clearMarkers();
		for (IIncludePathEntry element : includePathEntries) {
			String message = element.validate();
			if (message != null) {
				addError(message);
			}
		}
	}

	private void addError(String message) {
		try {
			IMarker marker = project.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.LOCATION, LOCATION_INCLUDE_PATH);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			marker.setAttribute(OWNER_ATTRIBUTE, OWNER_PHP_INCLUDE_PATH);
		} catch (CoreException e) {
		}
	}

	private void clearMarkers() {
		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			for (IMarker element : markers) {
				if (OWNER_PHP_INCLUDE_PATH.equals(element.getAttribute(OWNER_ATTRIBUTE))) {
					element.delete();
				}
			}
		} catch (CoreException e) {
		}
	}
}
