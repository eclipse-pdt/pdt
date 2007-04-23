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
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathEntry;

public class IPListElement {

	private IProject fProject;

	private int fEntryKind;
	private int fContentKind;
	private IPath fPath;
	private IResource fResource;
	private boolean fIsExported;
	private boolean fIsMissing;

	private Object fParentContainer;

	private IIncludePathEntry fCachedEntry;
	private ArrayList fChildren;

	public IPListElement(IProject project, int entryKind, int contentKind, IPath path, IResource res) {
		this(null, project, entryKind, contentKind, path, res);
	}

	public IPListElement(Object parent, IProject project, int entryKind, int contentKind, IPath path, IResource res) {
		fProject = project;

		fEntryKind = entryKind;
		fContentKind = contentKind;
		fPath = path;
		fChildren = new ArrayList();
		fResource = res;
		fIsExported = false;

		fIsMissing = false;
		fCachedEntry = null;
		fParentContainer = parent;

		switch (entryKind) {
			case IIncludePathEntry.IPE_SOURCE:
				break;
			case IIncludePathEntry.IPE_LIBRARY:
			case IIncludePathEntry.IPE_VARIABLE:
			case IIncludePathEntry.IPE_JRE:
				break;
			case IIncludePathEntry.IPE_PROJECT:
				break;
			case IIncludePathEntry.IPE_CONTAINER:
				//				try {
				//					IIncludePathContainer container= JavaCore.getIncludePathContainer(fPath, fProject);
				//					if (container != null) {
				//						IIncludePathEntry[] entries= container.getIncludePathEntries();
				//						for (int i= 0; i < entries.length; i++) {
				//							IIncludePathEntry entry= entries[i];
				//							if (entry != null) {
				//								CPListElement curr= createFromExisting(this, entry, fProject);
				//								fChildren.add(curr);
				//							} else {
				//								PHPCorePlugin.logErrorMessage("Null entry in container '" + fPath + "'");  //$NON-NLS-1$//$NON-NLS-2$
				//							}
				//						}						
				//					}
				//				} catch (PHPModelException e) {
				//				}		
				//				break;
				throw new RuntimeException("implement me");
			default:
		}

	}

	public IIncludePathEntry getIncludePathEntry() {
		if (fCachedEntry == null) {
			fCachedEntry = newIncludePathEntry();
		}
		return fCachedEntry;
	}

	private IIncludePathEntry newIncludePathEntry() {

		switch (fEntryKind) {
			case IIncludePathEntry.IPE_SOURCE:
				return IncludePathEntry.newSourceEntry(fPath, fResource);
			case IIncludePathEntry.IPE_LIBRARY: 			
			{
				return new IncludePathEntry(fContentKind, IIncludePathEntry.IPE_LIBRARY, fPath, fResource, isExported());
			}
			case IIncludePathEntry.IPE_JRE:
			{
				return new IncludePathEntry(fContentKind, IIncludePathEntry.IPE_JRE, fPath, fResource, isExported());
			}
			case IIncludePathEntry.IPE_PROJECT: {
				return IncludePathEntry.newProjectEntry(fPath, fResource, isExported());
			}
			case IIncludePathEntry.IPE_CONTAINER: {
				return IncludePathEntry.newContainerEntry(fPath, fResource, isExported());
			}
			case IIncludePathEntry.IPE_VARIABLE: {
				return IncludePathEntry.newVariableEntry(fPath, fResource, isExported());
			}
			default:
				return null;
		}
	}

	/**
	 * Gets the include path entry path.
	 * @see IIncludePathEntry#getPath()
	 */
	public IPath getPath() {
		return fPath;
	}

	/**
	 * Gets the include path entry kind.
	 * @see IIncludePathEntry#getEntryKind()
	 */
	public int getEntryKind() {
		return fEntryKind;
	}

	/**
	 * Gets the include path content kind.
	 * @see IIncludePathEntry#getEntryKind()
	 */
	public int getContentKind() {
		return fContentKind;
	}
	
	/**
	 * Entries without resource are either non existing or a variable entry
	 * External zips do not have a resource
	 */
	public IResource getResource() {
		return fResource;
	}

	public IPListElementAttribute setAttribute(String key, Object value) {
		IPListElementAttribute attribute = findAttributeElement(key);
		if (attribute == null) {
			return null;
		}

		attribute.setValue(value);
		attributeChanged(key);
		return attribute;
	}

	public IPListElementAttribute findAttributeElement(String key) {
		for (int i = 0; i < fChildren.size(); i++) {
			Object curr = fChildren.get(i);
			if (curr instanceof IPListElementAttribute) {
				IPListElementAttribute elem = (IPListElementAttribute) curr;
				if (key.equals(elem.getKey())) {
					return elem;
				}
			}
		}
		return null;
	}

	public Object getAttribute(String key) {
		IPListElementAttribute attrib = findAttributeElement(key);
		if (attrib != null) {
			return attrib.getValue();
		}
		return null;
	}

	private void createAttributeElement(String key, Object value, boolean builtIn) {
		fChildren.add(new IPListElementAttribute(this, key, value, builtIn));
	}

	private static boolean isFiltered(Object entry, String[] filteredKeys) {
		if (entry instanceof IPListElementAttribute) {
			String key = ((IPListElementAttribute) entry).getKey();
			for (int i = 0; i < filteredKeys.length; i++) {
				if (key.equals(filteredKeys[i])) {
					return true;
				}
			}
		}
		return false;
	}

	private Object[] getFilteredChildren(String[] filteredKeys) {
		int nChildren = fChildren.size();
		ArrayList res = new ArrayList(nChildren);

		for (int i = 0; i < nChildren; i++) {
			Object curr = fChildren.get(i);
			if (!isFiltered(curr, filteredKeys)) {
				res.add(curr);
			}
		}
		return res.toArray();
	}

	public Object[] getChildren(boolean hideOutputFolder) {
		if (hideOutputFolder && fEntryKind == IIncludePathEntry.IPE_SOURCE) {
			return getFilteredChildren(new String[] {});
		}

		if (fEntryKind == IIncludePathEntry.IPE_PROJECT) {
			return getFilteredChildren(new String[] {});
		}
		return fChildren.toArray();
	}

	public Object getParentContainer() {
		return fParentContainer;
	}

	private void attributeChanged(String key) {
		fCachedEntry = null;
	}

	private boolean canUpdateContainer() {
		//		if (fEntryKind == IIncludePathEntry.IPE_CONTAINER && fProject != null) {
		//			IncludePathContainerInitializer initializer= JavaCore.getIncludePathContainerInitializer(fPath.segment(0));
		//			return (initializer != null && initializer.canUpdateIncludePathContainer(fPath, fProject));
		//		}
		//		return false;
		throw new RuntimeException("implement me");
	}

	public boolean isInNonModifiableContainer() {
		if (fParentContainer instanceof IPListElement) {
			return !((IPListElement) fParentContainer).canUpdateContainer();
		}
		return false;
	}

	/*
	 * @see Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other==this)
			return true;
		if (other != null && other.getClass().equals(getClass())) {
			IPListElement elem = (IPListElement) other;
			return getIncludePathEntry().equals(elem.getIncludePathEntry());
		}
		return false;
	}

	/*
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return fPath.hashCode() + fEntryKind + fContentKind;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getIncludePathEntry().toString();
	}

	/**
	 * Returns if a entry is missing.
	 * @return Returns a boolean
	 */
	public boolean isMissing() {
		return fIsMissing;
	}

	/**
	 * Sets the 'missing' state of the entry.
	 */
	public void setIsMissing(boolean isMissing) {
		fIsMissing = isMissing;
	}

	/**
	 * Returns if a entry is exported (only applies to libraries)
	 * @return Returns a boolean
	 */
	public boolean isExported() {
		return fIsExported;
	}

	/**
	 * Sets the export state of the entry.
	 */
	public void setExported(boolean isExported) {
		if (isExported != fIsExported) {
			fIsExported = isExported;

			attributeChanged(null);
		}
	}

	/**
	 * Gets the project.
	 * @return Returns a IProject
	 */
	public IProject getProject() {
		return fProject;
	}

	public static IPListElement createFromExisting(IIncludePathEntry curr, IProject project) {
		return createFromExisting(null, curr, project);
	}

	public static IPListElement createFromExisting(Object parent, IIncludePathEntry curr, IProject project) {
		IPath path = curr.getPath();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		// get the resource
		IResource res = null;
		boolean isMissing = false;

		switch (curr.getEntryKind()) {
			case IIncludePathEntry.IPE_CONTAINER:
				res = null;
				isMissing = project != null && (PHPProjectOptions.getIncludePathContainer(path, project) == null);
				break;
			case IIncludePathEntry.IPE_VARIABLE:
				IPath resolvedPath = PHPProjectOptions.getResolvedVariablePath(path);
				res = null;
				isMissing = root.findMember(resolvedPath) == null && !resolvedPath.toFile().exists();
				break;
			case IIncludePathEntry.IPE_LIBRARY:
			case IIncludePathEntry.IPE_JRE:
				
				res = root.findMember(path);
				if (res == null) {
					if (!ArchieveFileFilter.isZipPath(path)) {
						if (root.getWorkspace().validatePath(path.toString(), IResource.FOLDER).isOK() && root.getProject(path.segment(0)).exists()) {
							res = root.getFolder(path);
						}
					}
					isMissing = !path.toFile().exists(); 
				}
				break;
			case IIncludePathEntry.IPE_SOURCE:
				path = path.removeTrailingSeparator();
				res = root.findMember(path);
				if (res == null) {
					if (root.getWorkspace().validatePath(path.toString(), IResource.FOLDER).isOK()) {
						res = root.getFolder(path);
					}
					isMissing = true;
				}
				break;
			case IIncludePathEntry.IPE_PROJECT:
				res = root.findMember(path);
				isMissing = (res == null);
				break;
		}
		IPListElement elem = new IPListElement(parent, project, curr.getEntryKind(), curr.getContentKind(), path, res);
		elem.setExported(curr.isExported());

		if (project != null && project.exists()) {
			elem.setIsMissing(isMissing);
		}
		return elem;
	}

	public static StringBuffer appendEncodePath(IPath path, StringBuffer buf) {
		if (path != null) {
			String str = path.toString();
			buf.append('[').append(str.length()).append(']').append(str);
		} else {
			buf.append('[').append(']');
		}
		return buf;
	}

	public static StringBuffer appendEncodedString(String str, StringBuffer buf) {
		if (str != null) {
			buf.append('[').append(str.length()).append(']').append(str);
		} else {
			buf.append('[').append(']');
		}
		return buf;
	}

	public static StringBuffer appendEncodedFilter(IPath[] filters, StringBuffer buf) {
		if (filters != null) {
			buf.append('[').append(filters.length).append(']');
			for (int i = 0; i < filters.length; i++) {
				appendEncodePath(filters[i], buf).append(';');
			}
		} else {
			buf.append('[').append(']');
		}
		return buf;
	}

	public StringBuffer appendEncodedSettings(StringBuffer buf) {
		buf.append(fEntryKind).append(';');
		buf.append(fContentKind).append(';');
		appendEncodePath(fPath, buf).append(';');
		buf.append(Boolean.valueOf(fIsExported)).append(';');
		for (int i = 0; i < fChildren.size(); i++) {
			Object curr = fChildren.get(i);
			if (curr instanceof IPListElementAttribute) {
				IPListElementAttribute elem = (IPListElementAttribute) curr;
				if (elem.isBuiltIn()) {
					String key = elem.getKey();
				} else {
					appendEncodedString((String) elem.getValue(), buf);
				}
			}
		}
		return buf;
	}

}
