package org.eclipse.php.internal.core;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * TODO description  
 * TODO: rename this class, to have all utilities for backward compatibility in here
 * TODO move this class to the util package
 */
public class IncludePathToBuildpathImporter {
	public static final String TAG_INCLUDEPATHENTRY = "includepathentry"; //$NON-NLS-1$
	public static final String TAG_ENTRY_KIND = "kind"; //$NON-NLS-1$
	public static final String TAG_PATH = "path"; //$NON-NLS-1$
	public static final String TAG_RESOURCE = "resource"; //$NON-NLS-1$
	public static final String TAG_EXPORTED = "exported"; //$NON-NLS-1$
	private static final String PREF_QUALIFIER = PHPCorePlugin.ID + ".projectOptions"; //$NON-NLS-1$

	private static IBuildpathEntry[] buildpathEntries = {};

	/*
	 * Reads the project include paths and returns the corresponding build paths
	 */
	public static IBuildpathEntry[] convertIncludePathForProject(IProject project) {
		try {
			ProjectScope projectScope = new ProjectScope(project);
			// reads the project options created by the old model
			IEclipsePreferences preferences = projectScope.getNode(PREF_QUALIFIER);
			String includePathXml = preferences.get(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, null);

			if (includePathXml == null) {
				return buildpathEntries;
			}

			// parse the includes xml 
			Element cpElement;
			final Reader reader = new StringReader(includePathXml);

			try {
				final DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				cpElement = parser.parse(new InputSource(reader)).getDocumentElement();
			} catch (final Exception e) {
				throw new IOException(CoreMessages.getString("PHPProjectOptions_1"));
			} finally {
				reader.close();
			}

			// convert each node in the xml into a build path entry 
			final List<IBuildpathEntry> paths = new ArrayList<IBuildpathEntry>();
			NodeList list = cpElement.getElementsByTagName(TAG_INCLUDEPATHENTRY);
			for (int i = 0; i < list.getLength(); ++i) {
				final Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					final IBuildpathEntry entry = elementDecode((Element) node);
					if (entry != null) {
						paths.add(entry);
					}
				}
			}
			final int pathSize = paths.size();
			buildpathEntries = new IBuildpathEntry[pathSize];
			return paths.toArray(buildpathEntries);
		} catch (IOException e) {
			PHPCorePlugin.log(e);
		} finally {

		}
		return buildpathEntries;
	}

	/*
	 * constructs a build entry from an element 
	 */
	private static IBuildpathEntry elementDecode(Element element) {

		String entryKindAttr = element.getAttribute(TAG_ENTRY_KIND);
		String pathAttr = element.getAttribute(TAG_PATH);
		String resourceAttr = element.getAttribute(TAG_RESOURCE);

		// exported flag (optional)
		boolean isExported = element.getAttribute(TAG_EXPORTED).equals("true"); //$NON-NLS-1$
		IBuildpathEntry entry = null;
		IPath path = null;

		int entryKind = entryKindFromString(entryKindAttr);
		switch (entryKind) {
			case IBuildpathEntry.BPE_PROJECT:
				path = ResourcesPlugin.getWorkspace().getRoot().getProject(resourceAttr).getFullPath();
				if (path != null) {
					entry = DLTKCore.newProjectEntry(path, isExported);
				}
				break;
			case IBuildpathEntry.BPE_LIBRARY:
				if ("var".equalsIgnoreCase(entryKindAttr)) {
					path = IncludePathVariableManager.instance().resolveVariablePath(pathAttr);
					if (path != null) {
						entry = DLTKCore.newExtLibraryEntry(path.makeAbsolute());
					}
				} else {
					entry = DLTKCore.newLibraryEntry(new Path(pathAttr).makeAbsolute());
				}
			case IBuildpathEntry.BPE_CONTAINER:
				break;
			case IBuildpathEntry.BPE_SOURCE:
				entry = DLTKCore.newSourceEntry(path);
				break;
		}

		return entry;
	}

	/**
	 * Returns the entry kind of a <code>PackageFragmentRoot</code> from its <code>String</code> form.
	 */
	static int entryKindFromString(String kindStr) {
		if (kindStr.equalsIgnoreCase("prj")) //$NON-NLS-1$
			return IBuildpathEntry.BPE_PROJECT;
		// TODO: add better variable support
		if (kindStr.equalsIgnoreCase("var")) //$NON-NLS-1$
			return IBuildpathEntry.BPE_LIBRARY;
		if (kindStr.equalsIgnoreCase("con")) //$NON-NLS-1$
			return IBuildpathEntry.BPE_CONTAINER;
		if (kindStr.equalsIgnoreCase("src")) //$NON-NLS-1$
			return IBuildpathEntry.BPE_SOURCE;
		if (kindStr.equalsIgnoreCase("lib")) //$NON-NLS-1$
			return IBuildpathEntry.BPE_LIBRARY;
		return -1;
	}

	/*
	 * Class from the 1.0.x branch - used for trying to resolve include path variables from the old model
	 */
	static class IncludePathVariableManager {

		private static IncludePathVariableManager instance;

		public static IncludePathVariableManager instance() {
			if (instance == null) {
				instance = new IncludePathVariableManager();
			}
			return instance;
		}

		Preferences preferenceStore = PHPCorePlugin.getDefault().getPluginPreferences();

		HashMap<String, Path> variables = new HashMap<String, Path>();

		private IncludePathVariableManager() {
			startUp();
		}

		public IPath getIncludePathVariable(String variableName) {
			IPath varPath = null;
			IPath path = new Path(variableName);
			if (path.segmentCount() == 1) {
				varPath = variables.get(variableName);
			} else {
				varPath = variables.get(path.segment(0));
				if (varPath != null) {
					varPath = varPath.append(path.removeFirstSegments(1));
				}
			}
			return varPath;
		}

		public String[] getIncludePathVariableNames() {
			ArrayList<String> list = new ArrayList<String>();
			list.addAll(variables.keySet());
			return list.toArray(new String[list.size()]);

		}

		public void startUp() {
			String namesString = preferenceStore.getString(PHPCoreConstants.INCLUDE_PATH_VARIABLE_NAMES);
			String pathsString = preferenceStore.getString(PHPCoreConstants.INCLUDE_PATH_VARIABLE_PATHS);
			String[] names = {};
			if (namesString.length() > 0)
				names = namesString.split(","); //$NON-NLS-1$
			String[] paths = {};
			if (pathsString.length() > 0)
				paths = pathsString.split(","); //$NON-NLS-1$
			// Not good since empty paths are allowed!!!
			// assert (names.length == paths.length);
			for (int i = 0; i < names.length; i++) {
				String path;
				if (i < paths.length) {
					path = paths[i];
				} else {
					path = ""; //$NON-NLS-1$
				}
				variables.put(names[i], new Path(path));
			}

		}

		/**
		 * Returns resolved IPath from the given path string that starts from include path variable
		 * @param path Path string
		 * @return resolved IPath or <code>null</code> if it couldn't be resolved
		 */
		public IPath resolveVariablePath(String path) {
			int index = path.indexOf('/');
			if (index != -1) {
				String var = path.substring(0, index);
				IPath varPath = getIncludePathVariable(var);
				if (varPath != null && index + 1 < path.length()) {
					varPath = varPath.append(path.substring(index + 1));
				}
				return varPath;
			}
			return getIncludePathVariable(path);
		}
	}

}
