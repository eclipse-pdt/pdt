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
package org.eclipse.php.internal.debug.ui.preferences.stepFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathEntry;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.util.PHPUILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * A content provider to be used for Resource selection dialog
 * This special content provider will put the projects and their inlcude paths
 * at the same tree level
 * @author yaronm
 */
public class PHPResourceContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement) {
		try {
			if (parentElement instanceof IContainer) {
				List<Object> r = new LinkedList<Object>();
				// Add all members:
				IContainer container = (IContainer) parentElement;
				IResource[] members = container.members();
				for (IResource member : members) {
					if (member.isAccessible() && !isResourceFiltered(member)) {
						r.add(member);
					}
				}
				return r.toArray();
			} else if (parentElement instanceof IncludePathEntry) {
				IncludePathEntry includePathEntry = (IncludePathEntry) parentElement;
				IPath path = includePathEntry.getPath();
				File file = null;
				if (includePathEntry.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
					file = path.toFile();
				} else if (includePathEntry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
					path = IncludePathVariableManager.instance().resolveVariablePath(path.toString());
					if (path != null) {
						file = path.toFile();
					}
				}
				if (file != null) {
					return getChildren(new IncPathFile(includePathEntry, file));
				}
			} else if (parentElement instanceof IncPathFile) {
				IncPathFile ipFile = (IncPathFile) parentElement;
				File file = ipFile.file;
				if (file.isDirectory()) {
					File files[] = file.listFiles();
					List<Object> r = new ArrayList<Object>(files.length);
					for (File currentFile : files) {
						r.add(new IncPathFile(ipFile.includePathEntry, currentFile));
					}
					return r.toArray();
				}
			}
		} catch (CoreException e) {
		}
		return new Object[0];
	}

	private Object[] getIncludePathChildren(Object parentElement) {
		ArrayList<Object> r = new ArrayList<Object>();
		// Add include paths:
		if (parentElement instanceof IProject) {
			IProject project = (IProject) parentElement;
			PHPProjectOptions options = PHPProjectOptions.forProject(project);
			if (options != null) {
				IIncludePathEntry[] includePaths = options.readRawIncludePath();
				for (IIncludePathEntry entry : includePaths) {
					if (entry.getEntryKind() != IncludePathEntry.IPE_PROJECT) {
						r.add(entry);
					}
				}
			}
		}
		return r.toArray();
	}

	//filter out non PHP files
	private boolean isResourceFiltered(IResource member) {
		if (member instanceof IFile) {
			return !PHPModelUtil.isPhpFile((IFile) member);
		}
		return false;
	}

	public Object getParent(Object element) {
		if (element instanceof IResource) {
			return ((IResource) element).getParent();
		}
		if (element instanceof IncPathFile) {
			IncPathFile ipFile = (IncPathFile) element;
			return new IncPathFile(ipFile.includePathEntry, ipFile.file.getParentFile());
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	private ArrayList<Object> includePathElements = null;

	public Object[] getElements(Object inputElement) {
		ArrayList<Object> list = new ArrayList<Object>();
		includePathElements = new ArrayList<Object>();
		Object[] projects = getChildren(inputElement);
		for (Object proj : projects) {
			list.add(proj);
			Object[] includePathsChildren = getIncludePathChildren(proj);
			for (Object includePath : includePathsChildren) {
				includePathElements.add(includePath);
			}
		}

		list.addAll(includePathElements);
		Object[] result = new Object[list.size()];
		list.toArray(result);
		return result;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}

class IncPathFile {
	IncludePathEntry includePathEntry;
	File file;

	IncPathFile(IncludePathEntry includePathEntry, File file) {
		this.includePathEntry = includePathEntry;
		this.file = file;
	}

	public IncludePathEntry getIncludePathEntry() {
		return includePathEntry;
	}

	public int hashCode() {
		return file.hashCode() + 13 * includePathEntry.hashCode();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof IncPathFile)) {
			return false;
		}
		IncPathFile other = (IncPathFile) obj;
		return other.file.equals(file) && other.includePathEntry.equals(includePathEntry);
	}
}

class PHPResLabelProvider extends PHPUILabelProvider {

	public Image getImage(Object element) {
		if (element instanceof IIncludePathEntry) {
			IIncludePathEntry includePathEntry = (IIncludePathEntry) element;
			if (includePathEntry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_ENV_VAR);
			} else {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
			}
		}
		if (element instanceof IncPathFile) {
			IncPathFile currentFile = (IncPathFile) element;
			if (currentFile.file.isDirectory()) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
			} else {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_FILE);
			}
		}
		return super.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof IIncludePathEntry) {
			IIncludePathEntry includePathEntry = (IIncludePathEntry) element;
			return includePathEntry.getPath().toOSString();
		}
		if (element instanceof IncPathFile) {
			return ((IncPathFile) element).file.getName();
		}
		return super.getText(element);
	}
}
