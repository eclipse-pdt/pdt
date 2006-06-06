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
package org.eclipse.php.core.project.options;

import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.internal.resources.XMLWriter;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.php.core.IncludePathContainerInitializer;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.project.IIncludePathContainer;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.php.core.project.options.includepath.IncludePathEntry;
import org.eclipse.php.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PHPProjectOptions {

	private static final String TAG_OPTIONS = "phpProjectOptions";
	private static final String TAG_OPTION = "projectOption";

	public static final String BUILDER_ID = PHPCorePlugin.ID + ".PhpIncrementalProjectBuilder";
	/**
	 * Name of the User Library Container id.
	 */
	public static final String USER_LIBRARY_CONTAINER_ID = "org.eclipse.php.USER_LIBRARY"; //$NON-NLS-1$

	public static final String FILE_NAME = ".projectOptions";
	private IProject project;
	private Map options = new HashMap();
	private Map optionsChangeListenersMap = new HashMap();

	IIncludePathEntry[] includePathEntries = {};

	static final IIncludePathEntry[] EMPTY_INCLUDEPATH = {};

	public static PHPProjectOptions forProject(IProject project) {
		if (!project.isAccessible()) {
			return null;
		}
		PHPNature nature = null;
		try {
			nature = (PHPNature) project.getNature(PHPNature.ID);
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
		}
		if (nature != null)
			return nature.getOptions();
		return null;
	}

	public PHPProjectOptions(final IProject project) {
		//		assert project != null;
		this.project = project;
		loadOptions();

		//		initializeArguments();
	}

	public void setOption(String key, Object value) {
		assert value != null;
		Object oldValue = options.get(key);
		if (oldValue != null) {
			if (value != null && value.equals(oldValue))
				return;
		} else if (value == null)
			return;
		options.put(key, value);
		runSave();
		notifyOptionChangeListeners(key, oldValue, value);

	}

	private void runSave() {
		WorkspaceJob job = new WorkspaceJob("Project save") {
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

	public void notifyOptionChangeListeners(String key, Object oldValue, Object newValue) {
		Object object = this.optionsChangeListenersMap.get(key);
		if (object == null) {
			return;
		}
		List OptionChangeListeners = (List) object;
		for (Iterator optionChangeListenerIterator = OptionChangeListeners.iterator(); optionChangeListenerIterator.hasNext();) {
			IPhpProjectOptionChangeListener phpProjectOptionChangeListener = (IPhpProjectOptionChangeListener) optionChangeListenerIterator.next();
			phpProjectOptionChangeListener.notifyOptionChanged(oldValue, newValue);
		}
	}

	public Object getOption(String key) {
		return options.get(key);
	}

	private void initializeArguments() {
		try {
			IProjectDescription desc = project.getDescription();
			ICommand[] commands = desc.getBuildSpec();
			for (int i = 0; i < commands.length; i++) {
				if (commands[i].getBuilderName().equals(BUILDER_ID)) {
					options = commands[i].getArguments();
					break;
				}
			}
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
		}

	}

	private void loadOptions() {

		IFile optionsFile = project.getFile(FILE_NAME);
		if (!optionsFile.exists())
			return;

		ArrayList paths = new ArrayList();
		IIncludePathEntry defaultOutput = null;
		includePathEntries = EMPTY_INCLUDEPATH;
		try {
			Element cpElement;
			Reader reader = new InputStreamReader(optionsFile.getContents());

			try {
				DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				cpElement = parser.parse(new InputSource(reader)).getDocumentElement();
			} catch (SAXException e) {
				throw new IOException("Bad project options file format");
			} catch (ParserConfigurationException e) {
				throw new IOException("Bad project options file format");
			} finally {
				reader.close();
			}

			options = new HashMap();
			if (!cpElement.getNodeName().equalsIgnoreCase(TAG_OPTIONS)) { //$NON-NLS-1$
				throw new IOException("Bad project options file format");
			}
			NodeList list = cpElement.getElementsByTagName(TAG_OPTION); //$NON-NLS-1$
			int length = list.getLength();
			for (int i = 0; i < length; ++i) {
				Element element = (Element) list.item(i);
				String key = element.getAttribute("name");
				String value = element.getFirstChild().getNodeValue().trim();
				options.put(key, value);
			}

			list = cpElement.getElementsByTagName(IncludePathEntry.TAG_INCLUDEPATH); //$NON-NLS-1$
			if (list.getLength() > 0) {
				Element includePathElement = (Element) list.item(0);
				list = includePathElement.getElementsByTagName(IncludePathEntry.TAG_INCLUDEPATHENTRY); //$NON-NLS-1$
				length = list.getLength();

				for (int i = 0; i < length; ++i) {
					Node node = list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						IIncludePathEntry entry = IncludePathEntry.elementDecode((Element) node, this);
						paths.add(entry);
					}
				}

			}
		} catch (IOException e) {
			PHPCorePlugin.log(e);
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
		}
		int pathSize = paths.size();
		includePathEntries = new IIncludePathEntry[pathSize];
		paths.toArray(includePathEntries);

	}

	public void saveChanges(IProgressMonitor monitor) throws CoreException {
		try {

			ByteArrayOutputStream s = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(s);

			xmlWriter.startTag(TAG_OPTIONS, null);

			HashMap attributes = new HashMap();
			for (Iterator iter = options.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Object value = options.get(key);
				attributes.put("name", key);
				xmlWriter.startTag(TAG_OPTION, attributes);
				xmlWriter.write(value.toString());
				xmlWriter.endTag(TAG_OPTION);
			}

			xmlWriter.startTag(IncludePathEntry.TAG_INCLUDEPATH, null);
			for (int i = 0; i < includePathEntries.length; ++i) {
				((IncludePathEntry) includePathEntries[i]).elementEncode(xmlWriter, project.getFullPath(), true);
			}

			xmlWriter.endTag(IncludePathEntry.TAG_INCLUDEPATH);

			xmlWriter.endTag(TAG_OPTIONS);

			xmlWriter.flush();
			xmlWriter.close();

			InputStream inputStream = new ByteArrayInputStream(s.toByteArray());

			IFile optionsFile = project.getFile(FILE_NAME);

			if (optionsFile.exists()) {

				optionsFile.setContents(inputStream, IResource.FORCE, monitor);
			} else {
				optionsFile.create(inputStream, IResource.FORCE, monitor);
			}

		} catch (IOException e) {
			PHPCorePlugin.log(e);
		}
	}

	public static IncludePathContainerInitializer getIncludePathContainerInitializer(String string) {
		return null;
	}

	public static IIncludePathContainer getIncludePathContainer(IPath path, IProject project2) {
		return null;
	}

	public static IPath getIncludePathVariable(String variableName) {
		return IncludePathVariableManager.instance().getIncludePathVariable(variableName);
	}

	public void modifyIncludePathEntry(IIncludePathEntry newEntry, IProject jproject, IPath containerPath, IProgressMonitor monitor) throws CoreException {
		throw new RuntimeException("implement me");
	}

	public IIncludePathEntry[] readRawIncludePath() {
		return includePathEntries;
	}

	public void setRawIncludePath(IIncludePathEntry[] newIncludePathEntries, SubProgressMonitor subProgressMonitor) throws CoreException {
		IIncludePathEntry[] oldValue = includePathEntries;
		includePathEntries = newIncludePathEntries;
		IncludePathEntry.updateProjectReferences (includePathEntries, oldValue, project, subProgressMonitor);
		
		saveChanges(subProgressMonitor);
		notifyOptionChangeListeners(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, oldValue, newIncludePathEntries);
	}

	public static IPath getResolvedVariablePath(IPath path) {
		return IncludePathVariableManager.instance().getIncludePathVariable(path.toString());
	}

	public static String[] getIncludePathVariableNames() {
		return IncludePathVariableManager.instance().getIncludePathVariableNames();
	}

	public static void setIncludePathVariables(String[] names, IPath[] paths, SubProgressMonitor monitor) {
		IncludePathVariableManager.instance().setIncludePathVariables(names, paths, monitor);
	}

	public IProject getProject() {
		return project;
	}

	public void addOptionChangeListener(String optionKey, IPhpProjectOptionChangeListener optionChangeListener) {
		List optionChangeListeners = null;
		Object object = optionsChangeListenersMap.get(optionKey);
		if (object == null) {
			optionChangeListeners = new ArrayList();
			optionsChangeListenersMap.put(optionKey, optionChangeListeners);
		} else {
			optionChangeListeners = (List) object;
		}

		optionChangeListeners.add(optionChangeListener);
	}

	public void removeOptionChangeListener(String optionKey, IPhpProjectOptionChangeListener optionChangeListener) {
		Object object = optionsChangeListenersMap.get(optionKey);
		if (object == null) {
			return;
		}
		List optionChangeListeners = (List) object;
		optionChangeListeners.remove(optionChangeListener);
	}

	public Object removeOption(String key) {
		Object object = this.options.remove(key);
		if (object == null) {
			return object;
		}

		runSave();
		return object;
	}

	public Object removeOptionNotify(String key) {
		Object object = removeOption(key);
		notifyOptionChangeListeners(key, object, null);

		return object;
	}
	
	public void removeResourceFromIncludePath(IResource resource) {
		if (includePathEntries.length > 0) {
			IIncludePathEntry[] newIncludePathEntries = new IIncludePathEntry[includePathEntries.length - 1];
			for (int i = 0, j = 0; i < includePathEntries.length; ++i) {
				if (includePathEntries[i].getResource() == resource) {
					continue;
				}
				newIncludePathEntries[j++] = includePathEntries[i];
			}
			try {
				setRawIncludePath(newIncludePathEntries, null);
			} catch (Exception e) {
				PHPCorePlugin.log(e);
			}
		}
	}
}
