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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.internal.resources.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.php.internal.core.IncludePathContainerInitializer;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.project.IIncludePathContainer;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathEntry;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PHPProjectOptions {

	public static final String BUILDER_ID = PHPCorePlugin.ID + ".PhpIncrementalProjectBuilder";
	static final IIncludePathEntry[] EMPTY_INCLUDEPATH = {};

	public static final String FILE_NAME = ".projectOptions";
	private static final String TAG_OPTION = "projectOption";

	private static final String TAG_OPTIONS = "phpProjectOptions";
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

	IIncludePathEntry[] includePathEntries = {};

	private Map options = new HashMap();

	private final Map optionsChangeListenersMap = new HashMap();

	private IProject project;

	public PHPProjectOptions(final IProject project) {
		//		assert project != null;
		this.project = project;
		loadOptions();

		//		initializeArguments();
	}

	public void addOptionChangeListener(final String optionKey, final IPhpProjectOptionChangeListener optionChangeListener) {
		List optionChangeListeners = null;
		final Object object = optionsChangeListenersMap.get(optionKey);
		if (object == null) {
			optionChangeListeners = new ArrayList();
			optionsChangeListenersMap.put(optionKey, optionChangeListeners);
		} else
			optionChangeListeners = (List) object;

		optionChangeListeners.add(optionChangeListener);
	}

	public Object getOption(final String key) {
		return options.get(key);
	}

	public IProject getProject() {
		return project;
	}

	private void loadOptions() {

		final IFile optionsFile = project.getFile(FILE_NAME);
		if (!optionsFile.exists())
			return;

		final ArrayList paths = new ArrayList();
		includePathEntries = EMPTY_INCLUDEPATH;
		try {
			Element cpElement;
			final Reader reader = new InputStreamReader(optionsFile.getContents());

			try {
				final DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				cpElement = parser.parse(new InputSource(reader)).getDocumentElement();
			} catch (final SAXException e) {
				throw new IOException("Bad project options file format");
			} catch (final ParserConfigurationException e) {
				throw new IOException("Bad project options file format");
			} finally {
				reader.close();
			}

			options = new HashMap();
			if (!cpElement.getNodeName().equalsIgnoreCase(TAG_OPTIONS))
				throw new IOException("Bad project options file format");
			NodeList list = cpElement.getElementsByTagName(TAG_OPTION);
			int length = list.getLength();
			for (int i = 0; i < length; ++i) {
				final Element element = (Element) list.item(i);
				final String key = element.getAttribute("name");
				final String value = element.getFirstChild().getNodeValue().trim();
				options.put(key, value);
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

			}
		} catch (final IOException e) {
			PHPCorePlugin.log(e);
		} catch (final CoreException e) {
			PHPCorePlugin.log(e);
		}
		final int pathSize = paths.size();
		includePathEntries = new IIncludePathEntry[pathSize];
		paths.toArray(includePathEntries);

	}

	public void modifyIncludePathEntry(final IIncludePathEntry newEntry, final IProject jproject, final IPath containerPath, final IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("implement me");
	}

	public void notifyOptionChangeListeners(final String key, final Object oldValue, final Object newValue) {
		final Object object = optionsChangeListenersMap.get(key);
		if (object == null)
			return;
		final List OptionChangeListeners = (List) object;
		for (final Iterator optionChangeListenerIterator = OptionChangeListeners.iterator(); optionChangeListenerIterator.hasNext();) {
			final IPhpProjectOptionChangeListener phpProjectOptionChangeListener = (IPhpProjectOptionChangeListener) optionChangeListenerIterator.next();
			phpProjectOptionChangeListener.notifyOptionChanged(oldValue, newValue);
		}
	}

	public IIncludePathEntry[] readRawIncludePath() {
		return includePathEntries;
	}

	public Object removeOption(final String key) {
		final Object object = options.remove(key);
		if (object == null)
			return object;

		runSave();
		return object;
	}

	public void removeOptionChangeListener(final String optionKey, final IPhpProjectOptionChangeListener optionChangeListener) {
		final Object object = optionsChangeListenersMap.get(optionKey);
		if (object == null)
			return;
		final List optionChangeListeners = (List) object;
		optionChangeListeners.remove(optionChangeListener);
	}

	public Object removeOptionNotify(final String key) {
		final Object object = removeOption(key);
		notifyOptionChangeListeners(key, object, null);

		return object;
	}

	public void removeResourceFromIncludePath(final IResource resource) {
		if (includePathEntries.length > 0) {
			final IIncludePathEntry[] newIncludePathEntries = new IIncludePathEntry[includePathEntries.length - 1];
			for (int i = 0, j = 0; i < includePathEntries.length; ++i) {
				if (includePathEntries[i].getResource() == resource)
					continue;
				newIncludePathEntries[j++] = includePathEntries[i];
			}
			try {
				setRawIncludePath(newIncludePathEntries, null);
			} catch (final Exception e) {
				PHPCorePlugin.log(e);
			}
		}
	}

	public void runSave() {
		final WorkspaceJob job = new WorkspaceJob("Project save") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					saveChanges(monitor);
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(getProject());
		job.setUser(false);
		job.schedule();
	}

	public void saveChanges(final IProgressMonitor monitor) throws CoreException {
		try {

			final ByteArrayOutputStream s = new ByteArrayOutputStream();
			final XMLWriter xmlWriter = new XMLWriter(s);

			xmlWriter.startTag(TAG_OPTIONS, null);

			final HashMap attributes = new HashMap();
			for (final Iterator iter = options.keySet().iterator(); iter.hasNext();) {
				final String key = (String) iter.next();
				final Object value = options.get(key);
				attributes.put("name", key);
				xmlWriter.startTag(TAG_OPTION, attributes);
				xmlWriter.write(value.toString());
				xmlWriter.endTag(TAG_OPTION);
			}

			xmlWriter.startTag(IncludePathEntry.TAG_INCLUDEPATH, null);
			for (int i = 0; i < includePathEntries.length; ++i)
				((IncludePathEntry) includePathEntries[i]).elementEncode(xmlWriter, project.getFullPath(), true);

			xmlWriter.endTag(IncludePathEntry.TAG_INCLUDEPATH);

			xmlWriter.endTag(TAG_OPTIONS);

			xmlWriter.flush();
			xmlWriter.close();

			final InputStream inputStream = new ByteArrayInputStream(s.toByteArray());

			final IFile optionsFile = project.getFile(FILE_NAME);

			if (optionsFile.exists())
				optionsFile.setContents(inputStream, IResource.FORCE, monitor);
			else
				optionsFile.create(inputStream, IResource.FORCE, monitor);

		} catch (final IOException e) {
			PHPCorePlugin.log(e);
		}
	}

	public void setOption(final String key, final Object value) {
		assert value != null;
		final Object oldValue = options.get(key);
		if (oldValue != null) {
			if (value != null && value.equals(oldValue))
				return;
		} else if (value == null)
			return;
		options.put(key, value);
		runSave();
		notifyOptionChangeListeners(key, oldValue, value);

	}

	public void setRawIncludePath(final IIncludePathEntry[] newIncludePathEntries, final SubProgressMonitor subProgressMonitor) throws CoreException {
		final IIncludePathEntry[] oldValue = includePathEntries;
		includePathEntries = newIncludePathEntries;
		IncludePathEntry.updateProjectReferences(includePathEntries, oldValue, project, subProgressMonitor);

		saveChanges(subProgressMonitor);
		notifyOptionChangeListeners(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, oldValue, newIncludePathEntries);
	}
}
