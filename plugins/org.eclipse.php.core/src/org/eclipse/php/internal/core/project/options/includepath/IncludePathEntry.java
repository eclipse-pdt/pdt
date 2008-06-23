/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.project.options.includepath;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.internal.resources.XMLWriter;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesReader;
import org.w3c.dom.Element;

public class IncludePathEntry implements IIncludePathEntry {

	private static final String SELF_PROJECT = "|S.E.L.F|"; //$NON-NLS-1$

	public static final String TAG_INCLUDEPATH = "includepath"; //$NON-NLS-1$
	public static final String TAG_INCLUDEPATHENTRY = "includepathentry"; //$NON-NLS-1$
	public static final String TAG_ENTRY_KIND = "kind"; //$NON-NLS-1$
	public static final String TAG_CONTENT_KIND = "contentKind"; //$NON-NLS-1$
	public static final String TAG_PATH = "path"; //$NON-NLS-1$
	public static final String TAG_RESOURCE = "resource"; //$NON-NLS-1$
	public static final String TAG_ROOTPATH = "rootpath"; //$NON-NLS-1$
	public static final String TAG_EXPORTED = "exported"; //$NON-NLS-1$
	public static final String TAG_CREATEDREFERENCE = "createdReference"; //$NON-NLS-1$

	public int entryKind;
	public int contentKind;
	public IPath path;
	public IResource resource;
	public boolean isExported;
	private boolean createdReference;

	/**
	 * Creates a class path entry of the specified kind with the given path.
	 */
	public IncludePathEntry(int contentKind, int entryKind, IPath path, IResource resource, boolean isExported) {

		this.contentKind = contentKind;
		this.entryKind = entryKind;
		this.path = path;
		this.resource = resource;
		this.isExported = isExported;
	}

	/**
	 * This method gets the include path entries for a given project as a string and returns a "decoded" List of IIncludePathEntrys
	 * @param String representing the entries they way they are saved into the preferences
	 * @param project
	 * @return List of IIncludePathEntrys for a given project
	 */
	public static List getIncludePathEntriesFromPreferences(String entriesString, IProject project) {

		final ArrayList entries = new ArrayList();

		HashMap[] maps = XMLPreferencesReader.getHashFromStoredValue(entriesString);
		if (maps.length > 0) {
			for (int entryCount = 0; entryCount < maps.length; ++entryCount) {
				IncludePathEntryDescriptor descriptor = new IncludePathEntryDescriptor();
				descriptor.restoreFromMap(maps[entryCount]);
				entries.add(IncludePathEntry.elementDecode(descriptor, project.getFullPath()));
			}
		}
		return entries;
	}

	public int getContentKind() {
		return contentKind;
	}

	public int getEntryKind() {
		return entryKind;
	}

	public IPath getPath() {
		return this.path;
	}

	public IResource getResource() {
		return this.resource;
	}

	public boolean isExported() {
		return isExported;
	}

	public static IIncludePathEntry elementDecode(Element element, PHPProjectOptions options) {

		IPath projectPath = options.getProject().getFullPath();
		String entryKindAttr = element.getAttribute(TAG_ENTRY_KIND);
		String contentKindAttr = element.getAttribute(TAG_CONTENT_KIND);
		String pathAttr = element.getAttribute(TAG_PATH);
		String resourceAttr = element.getAttribute(TAG_RESOURCE);
		IPath resourcePath = new Path(resourceAttr);
		if (resourcePath.segmentCount() > 0 && resourcePath.segment(0).equals(SELF_PROJECT)) {
			resourcePath = new Path("/" + projectPath.segment(0)).append(resourcePath.removeFirstSegments(1)); //$NON-NLS-1$
		}

		// exported flag (optional)
		boolean isExported = element.getAttribute(TAG_EXPORTED).equals("true"); //$NON-NLS-1$

		IIncludePathEntry entry = getEntry(pathAttr, entryKindAttr, contentKindAttr, resourcePath.toString(), isExported, projectPath);
		return entry;
	}

	public static IIncludePathEntry elementDecode(IncludePathEntryDescriptor descriptor, IPath projectPath) {

		IIncludePathEntry entry = getEntry(descriptor.getPath(), descriptor.getEntryKind(), descriptor.getContentKind(), descriptor.getResourceName(), descriptor.isExported(), projectPath);
		return entry;
	}

	public static IIncludePathEntry getEntry(String sPath, String sEntryKind, String sContentKind, String sResource, boolean isExported, IPath projectPath) {
		//		 ensure path is absolute
		IPath path = new Path(sPath);
		int entryKind = entryKindFromString(sEntryKind);
		if (entryKind != IIncludePathEntry.IPE_VARIABLE && entryKind != IIncludePathEntry.IPE_CONTAINER && !path.isAbsolute()) {
			path = projectPath.append(path);
		}
		IResource resource = null;

		// recreate the CP entry
		IIncludePathEntry entry = null;
		switch (entryKind) {

			case IIncludePathEntry.IPE_PROJECT:
				try {
					resource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(sResource));
				} catch (Exception e) {
					// Do nothing
				}
				entry = newProjectEntry(path, resource, isExported);
				break;
			case IIncludePathEntry.IPE_LIBRARY:
			case IIncludePathEntry.IPE_JRE:
				entry = new IncludePathEntry(contentKindFromString(sContentKind), IIncludePathEntry.IPE_LIBRARY, path, resource, isExported);
				break;
			case IIncludePathEntry.IPE_SOURCE:
				// must be an entry in this project or specify another project
				entry = newSourceEntry(path, resource);
				break;
			case IIncludePathEntry.IPE_VARIABLE:
				entry = newVariableEntry(path, resource, isExported);
				break;
			case IIncludePathEntry.IPE_CONTAINER:
				entry = newContainerEntry(path, resource, isExported);
				break;
			default:
				throw new AssertionError(NLS.bind(CoreMessages.getString("includePath_unknownKind"), sEntryKind));
		}
		return entry;
	}

	public static IIncludePathEntry newProjectEntry(IPath path, IResource resource, boolean isExported) {

		if (!path.isAbsolute())
			throw new IllegalArgumentException("Path for IIncludePathEntry must be absolute"); //$NON-NLS-1$

		return new IncludePathEntry(K_SOURCE, IIncludePathEntry.IPE_PROJECT, path, resource, isExported);

	}

	public static IIncludePathEntry newContainerEntry(IPath containerPath, IResource containerResource, boolean isExported) {

		if (containerPath == null)
			throw new IllegalArgumentException("Container path cannot be null"); //$NON-NLS-1$
		if (containerPath.segmentCount() < 1) {
			throw new IllegalArgumentException("Illegal include path container path: \'" + containerPath.makeRelative().toString() + "\', must have at least one segment (containerID+hints)"); //$NON-NLS-1$//$NON-NLS-2$
		}
		return new IncludePathEntry(K_SOURCE, IIncludePathEntry.IPE_CONTAINER, containerPath, containerResource, isExported);
	}

	public static IIncludePathEntry newVariableEntry(IPath variablePath, IResource variableResource, boolean isExported) {

		if (variablePath == null)
			throw new IllegalArgumentException("Variable path cannot be null"); //$NON-NLS-1$
		if (variablePath.segmentCount() < 1) {
			throw new IllegalArgumentException("Illegal classpath variable path: \'" + variablePath.makeRelative().toString() + "\', must have at least one segment"); //$NON-NLS-1$//$NON-NLS-2$
		}

		return new IncludePathEntry(K_SOURCE, IIncludePathEntry.IPE_VARIABLE, variablePath, variableResource, isExported);
	}

	public static IIncludePathEntry newSourceEntry(IPath path, IResource resource) {

		if (path == null)
			throw new IllegalArgumentException("Source path cannot be null"); //$NON-NLS-1$
		if (!path.isAbsolute())
			throw new IllegalArgumentException("Path for IIncludePathEntry must be absolute"); //$NON-NLS-1$

		return new IncludePathEntry(K_SOURCE, IIncludePathEntry.IPE_SOURCE, path, resource, false);
	}

	public void elementEncode(XMLWriter writer, IPath projectPath, boolean newLine) {
		HashMap parameters = new HashMap();

		parameters.put(TAG_ENTRY_KIND, IncludePathEntry.entryKindToString(this.entryKind));
		parameters.put(TAG_CONTENT_KIND, IncludePathEntry.contentKindToString(this.contentKind));
		parameters.put(TAG_CREATEDREFERENCE, createdReference ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$

		IPath xmlPath = this.path;
		if (this.entryKind != IIncludePathEntry.IPE_VARIABLE && this.entryKind != IIncludePathEntry.IPE_CONTAINER) {
			// translate to project relative from absolute (unless a device path)
			if (projectPath != null && projectPath.isPrefixOf(xmlPath)) {
				if (xmlPath.segment(0).equals(projectPath.segment(0))) {
					xmlPath = xmlPath.removeFirstSegments(1);
					xmlPath = xmlPath.makeRelative();
				} else {
					xmlPath = xmlPath.makeAbsolute();
				}
			}
		}
		parameters.put(TAG_PATH, String.valueOf(xmlPath));
		if (resource != null) {
			IPath resourcePath = resource.getFullPath();
			if (projectPath.isPrefixOf(resourcePath)) {
				resourcePath = new Path(SELF_PROJECT).append(resourcePath.removeFirstSegments(1));
			}
			parameters.put(TAG_RESOURCE, resourcePath.toString());
		}
		if (this.isExported) {
			parameters.put(TAG_EXPORTED, "true");//$NON-NLS-1$
		}

		writer.printTag(TAG_INCLUDEPATHENTRY, parameters);
		writer.endTag(TAG_INCLUDEPATHENTRY);
	}

	public String elementEncode(IPath projectPath) {
		IncludePathEntryDescriptor descriptor = new IncludePathEntryDescriptor(this, projectPath);
		return descriptor.toString();
	}

	public static void updateProjectReferences(IIncludePathEntry[] newEntries, IIncludePathEntry[] oldEntries, final IProject project, SubProgressMonitor monitor) {
		try {
			boolean changedReferences = false;
			if (!project.isAccessible()) {
				return;
			}
			final IProjectDescription projectDescription = project.getDescription();
			List<IProject> referenced = new ArrayList<IProject>();
			List<String> referencedNames = new ArrayList<String>();
			IProject[] referencedProjects = projectDescription.getReferencedProjects();
			for (IProject element : referencedProjects) {
				referenced.add(element);
				referencedNames.add(element.getName());
			}

			for (IIncludePathEntry element : oldEntries) {
				if (element.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
					String projectName = element.getPath().segment(0);
					if (projectName.equals(project.getName())) {
						continue;
					}
					if (!containsProject(newEntries, projectName)) {
						if (((IncludePathEntry) element).createdReference) {
							int index = referencedNames.indexOf(projectName);
							if (index >= 0) {
								changedReferences = true;
								referencedNames.remove(index);
								referenced.remove(index);
							}
						}
					}
				}
			}

			for (int i = 0; i < newEntries.length; i++) {
				if (newEntries[i].getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
					String projectName = newEntries[i].getPath().segment(0);
					if (projectName.equals(project.getName())) {
						continue;
					}
					if (!containsProject(oldEntries, projectName)) {
						if (!referencedNames.contains(projectName)) {
							changedReferences = true;
							((IncludePathEntry) newEntries[i]).createdReference = true;
							referenced.add(ResourcesPlugin.getWorkspace().getRoot().getProject(projectName));
							referencedNames.add(projectName);
						}
					}
				}
			}
			if (changedReferences) {
				IProject[] referenceProjects = referenced.toArray(new IProject[referenced.size()]);
				projectDescription.setReferencedProjects(referenceProjects);
				WorkspaceJob job = new WorkspaceJob(CoreMessages.getString("IncludePathEntry_2")) {
					public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
						project.setDescription(projectDescription, monitor);
						return Status.OK_STATUS;
					}
				};
				job.setRule(project.getParent());
				job.schedule();
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean containsProject(IIncludePathEntry[] entries, String projectName) {
		for (IIncludePathEntry element : entries) {
			if (element.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
				if (element.getPath().segment(0).equals(projectName))
					return true;
			}
		}
		return false;
	}

	/**
	 * Returns the entry kind of a <code>PackageFragmentRoot</code> from its <code>String</code> form.
	 */
	static int entryKindFromString(String kindStr) {

		if (kindStr.equalsIgnoreCase("prj")) //$NON-NLS-1$
			return IIncludePathEntry.IPE_PROJECT;
		if (kindStr.equalsIgnoreCase("var")) //$NON-NLS-1$
			return IIncludePathEntry.IPE_VARIABLE;
		if (kindStr.equalsIgnoreCase("con")) //$NON-NLS-1$
			return IIncludePathEntry.IPE_CONTAINER;
		if (kindStr.equalsIgnoreCase("src")) //$NON-NLS-1$
			return IIncludePathEntry.IPE_SOURCE;
		if (kindStr.equalsIgnoreCase("lib")) //$NON-NLS-1$
			return IIncludePathEntry.IPE_LIBRARY;
		if (kindStr.equalsIgnoreCase("jre")) //$NON-NLS-1$
			return IIncludePathEntry.IPE_JRE;
		return -1;
	}

	/**
	 * Returns a <code>String</code> for the entry kind of a class path entry.
	 */
	static String entryKindToString(int kind) {

		switch (kind) {
			case IIncludePathEntry.IPE_PROJECT:
				return "prj"; //$NON-NLS-1$
			case IIncludePathEntry.IPE_SOURCE:
				return "src"; //$NON-NLS-1$
			case IIncludePathEntry.IPE_LIBRARY:
				return "lib"; //$NON-NLS-1$
			case IIncludePathEntry.IPE_JRE:
				return "jre"; //$NON-NLS-1$
			case IIncludePathEntry.IPE_VARIABLE:
				return "var"; //$NON-NLS-1$
			case IIncludePathEntry.IPE_CONTAINER:
				return "con"; //$NON-NLS-1$
			default:
				return "unknown"; //$NON-NLS-1$
		}
	}

	/**
	 * Returns the content kind of a <code>PackageFragmentRoot</code> from its <code>String</code> form.
	 */
	static int contentKindFromString(String kindStr) {

		if (kindStr.equalsIgnoreCase("binary")) //$NON-NLS-1$
			return IIncludePathEntry.K_BINARY;
		if (kindStr.equalsIgnoreCase("source")) //$NON-NLS-1$
			return IIncludePathEntry.K_SOURCE;
		return -1;
	}

	/**
	 * Returns a <code>String</code> for the content kind of a class path entry.
	 */
	static String contentKindToString(int kind) {

		switch (kind) {
			case IIncludePathEntry.K_BINARY:
				return "binary"; //$NON-NLS-1$
			case IIncludePathEntry.K_SOURCE:
				return "source"; //$NON-NLS-1$
			default:
				return "unknown"; //$NON-NLS-1$
		}
	}

	public String validate() {
		String message = null;

		switch (entryKind) {

			case IIncludePathEntry.IPE_PROJECT:
				if (resource == null || !resource.exists())
					message = CoreMessages.getString("IncludePathEntry_4") + path.toOSString();
				break;
			case IIncludePathEntry.IPE_LIBRARY:
			case IIncludePathEntry.IPE_JRE:
				if (resource == null || !resource.exists()) {
					File file = new File(path.toOSString());
					if (!file.exists())
						message = CoreMessages.getString("IncludePathEntry_5") + path.toOSString();
				}
				break;
			case IIncludePathEntry.IPE_SOURCE:
				if (resource == null || !resource.exists())
					message = CoreMessages.getString("IncludePathEntry_6") + path.toOSString();
				break;
			case IIncludePathEntry.IPE_VARIABLE:
				//				if (resource == null || !resource.exists())
				//					message = "included variable not found: " + path.toOSString();
				break;
			case IIncludePathEntry.IPE_CONTAINER:
				break;
			default:
				throw new AssertionError(NLS.bind(CoreMessages.getString("includePath_unknownKind"), "")); //$NON-NLS-1$
		}
		return message;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.project.IIncludePathEntry#setResource(org.eclipse.core.resources.IResource)
	 */
	public void setResource(IResource resource) {
		this.resource = resource;

	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + contentKind;
		result = PRIME * result + entryKind;
		result = PRIME * result + (path == null ? 0 : path.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final IncludePathEntry other = (IncludePathEntry) obj;
		if (contentKind != other.contentKind)
			return false;
		if (entryKind != other.entryKind)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

}
