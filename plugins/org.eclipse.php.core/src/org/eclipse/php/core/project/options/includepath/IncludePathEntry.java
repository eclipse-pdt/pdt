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
package org.eclipse.php.core.project.options.includepath;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.internal.resources.XMLWriter;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.php.core.util.Messages;
import org.eclipse.php.core.util.preferences.Key;
import org.eclipse.php.core.util.preferences.XMLPreferencesReader;
import org.eclipse.ui.preferences.IWorkingCopyManager;
import org.w3c.dom.Element;

public class IncludePathEntry implements IIncludePathEntry {
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
	
	public static ArrayList getIncludePathEntriesFromPreferences (Key preferenceKey, IProject project, ProjectScope projectScope, IWorkingCopyManager workingCopyManager){
		
		final ArrayList entries = new ArrayList();
		
		HashMap[] maps = XMLPreferencesReader.read(preferenceKey, projectScope, workingCopyManager);
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

		// not in use: comment out
//		String createdReferenceAttr = element.getAttribute(TAG_CREATEDREFERENCE);
//		boolean createdReference = "true".equalsIgnoreCase(createdReferenceAttr);


		// exported flag (optional)
		boolean isExported = element.getAttribute(TAG_EXPORTED).equals("true"); //$NON-NLS-1$

		IIncludePathEntry entry = getEntry(pathAttr, entryKindAttr, contentKindAttr, resourceAttr, isExported, projectPath);
		return entry;
	}
	
	public static IIncludePathEntry elementDecode(IncludePathEntryDescriptor descriptor, IPath projectPath ){
		
		IIncludePathEntry entry = getEntry(descriptor.getPath(), descriptor.getEntryKind(), descriptor.getContentKind(), descriptor.getResourceName(),descriptor.isExported(), projectPath);	
		return entry;
	}
	
	public static IIncludePathEntry getEntry(String sPath, String sEntryKind, String sContentKind, String sResource, boolean isExported, IPath projectPath ){
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
				resource = ResourcesPlugin.getWorkspace().getRoot().getProject(sResource);
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
				throw new AssertionError(Messages.bind(Messages.includePath_unknownKind, sEntryKind));
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
		parameters.put(TAG_CREATEDREFERENCE, createdReference ? "true" : "false");

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
			parameters.put(TAG_RESOURCE, resource.getName());
		}
		if (this.isExported) {
			parameters.put(TAG_EXPORTED, "true");//$NON-NLS-1$
		}

		writer.printTag(TAG_INCLUDEPATHENTRY, parameters);
		writer.endTag(TAG_INCLUDEPATHENTRY);
	}
	
	public String elementEncode(IPath projectPath){
		IncludePathEntryDescriptor descriptor = new IncludePathEntryDescriptor(this,projectPath);
		return descriptor.toString();
	}

	public static void updateProjectReferences(IIncludePathEntry[] newEntries, IIncludePathEntry[] oldEntries, IProject project, SubProgressMonitor monitor) {
		try {
			boolean changedReferences = false;
			IProjectDescription projectDescription = project.getDescription();
			ArrayList referenced = new ArrayList();
			ArrayList referencedNames = new ArrayList();
			IProject[] referencedProjects = projectDescription.getReferencedProjects();
			for (int i = 0; i < referencedProjects.length; i++) {
				referenced.add(referencedProjects[i]);
				referencedNames.add(referencedProjects[i].getName());
			}

			for (int i = 0; i < oldEntries.length; i++) {
				if (oldEntries[i].getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
					String projectName = oldEntries[i].getPath().lastSegment();
					if (!containsProject(newEntries, projectName)) {
						if (((IncludePathEntry) oldEntries[i]).createdReference) {
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
					String projectName = newEntries[i].getPath().lastSegment();
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
				IProject[] referenceProjects = (IProject[]) referenced.toArray(new IProject[referenced.size()]);
				projectDescription.setReferencedProjects(referenceProjects);
				project.setDescription(projectDescription, monitor);	
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean containsProject(IIncludePathEntry[] entries, String projectName) {
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
				if (entries[i].getPath().lastSegment().equals(projectName))
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
				return "prj";
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

	public boolean equals(Object other) {
		if (other != null && other.getClass().equals(getClass())) {
			IncludePathEntry otherEntry = (IncludePathEntry) other;
			if (otherEntry.entryKind != entryKind || otherEntry.contentKind != contentKind)
				return false;
			if (!path.equals(otherEntry.path))
				return false;
			return true;
		}
		return false;
	}

	public String validate() {
		String message = null;

		switch (entryKind) {

			case IIncludePathEntry.IPE_PROJECT:
				if (resource == null || !resource.exists())
					message = "included project not found: " + path.toOSString();
				break;
			case IIncludePathEntry.IPE_LIBRARY:
			case IIncludePathEntry.IPE_JRE:
				if (resource == null || !resource.exists()) {
					File file = new File(path.toOSString());
					if (!file.exists())
						message = "included library not found: " + path.toOSString();
				}
				break;
			case IIncludePathEntry.IPE_SOURCE:
				if (resource == null || !resource.exists())
					message = "included source not found: " + path.toOSString();
				break;
			case IIncludePathEntry.IPE_VARIABLE:
//				if (resource == null || !resource.exists())
//					message = "included variable not found: " + path.toOSString();
				break;
			case IIncludePathEntry.IPE_CONTAINER:
				break;
			default:
				throw new AssertionError(Messages.bind(Messages.includePath_unknownKind, ""));
		}
		return message;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.core.project.IIncludePathEntry#setResource(org.eclipse.core.resources.IResource)
	 */
	public void setResource(IResource resource) {
		this.resource = resource;
		
	}

}
