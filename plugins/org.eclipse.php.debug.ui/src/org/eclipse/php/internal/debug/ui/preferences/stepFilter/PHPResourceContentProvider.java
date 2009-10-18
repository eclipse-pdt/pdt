/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
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
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * A content provider to be used for Resource selection dialog This special
 * content provider will put the projects and their inlcude paths at the same
 * tree level
 * 
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
					if (member instanceof IContainer && member.isAccessible()
							&& !isResourceFiltered(member)) {
						if (member instanceof IProject) { // show only PHP
															// projects
							IProject project = (IProject) member;
							if (project.hasNature(PHPNature.ID)) {
								r.add(member);
							}
						} else {
							r.add(member);
						}
					}
				}
				// Add include paths:
				if (parentElement instanceof IProject) {
					IProject project = (IProject) parentElement;
					IncludePath[] includePath = IncludePathManager
							.getInstance().getIncludePaths(project);
					for (IncludePath path : includePath) {
						if (path.isBuildpath()) {
							IBuildpathEntry buildpathEntry = (IBuildpathEntry) path
									.getEntry();
							if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY
									|| buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
								r.add(buildpathEntry);
							}
						}
					}
				}
				return r.toArray();
			} else if (parentElement instanceof IBuildpathEntry) {
				IBuildpathEntry buildpathEntry = (IBuildpathEntry) parentElement;
				IPath path = buildpathEntry.getPath();
				File file = null;
				if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
					file = path.toFile();
				} else if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
					path = DLTKCore.getResolvedVariablePath(path);
					if (path != null) {
						file = path.toFile();
					}
				}
				if (file != null) {
					return getChildren(new IncPathFile(buildpathEntry, file));
				}
			} else if (parentElement instanceof IncPathFile) {
				IncPathFile ipFile = (IncPathFile) parentElement;
				File file = ipFile.file;
				if (file.isDirectory()) {
					File files[] = file.listFiles();
					List<Object> r = new ArrayList<Object>(files.length);
					for (File currentFile : files) {
						r.add(new IncPathFile(ipFile.IBuildpathEntry,
								currentFile));
					}
					return r.toArray();
				}
			}
		} catch (CoreException e) {
		}
		return new Object[0];
	}

	// filter out non PHP files
	private boolean isResourceFiltered(IResource member) {
		if (member instanceof IFile) {
			return !PHPToolkitUtil.isPhpFile((IFile) member);
		}
		return false;
	}

	public Object getParent(Object element) {
		if (element instanceof IResource) {
			return ((IResource) element).getParent();
		}
		if (element instanceof IncPathFile) {
			IncPathFile ipFile = (IncPathFile) element;
			return new IncPathFile(ipFile.IBuildpathEntry, ipFile.file
					.getParentFile());
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}

class IncPathFile {
	IBuildpathEntry IBuildpathEntry;
	File file;

	IncPathFile(IBuildpathEntry IBuildpathEntry, File file) {
		this.IBuildpathEntry = IBuildpathEntry;
		this.file = file;
	}

	public IBuildpathEntry getBuildpathEntry() {
		return IBuildpathEntry;
	}

	public int hashCode() {
		return file.hashCode() + 13 * IBuildpathEntry.hashCode();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof IncPathFile)) {
			return false;
		}
		IncPathFile other = (IncPathFile) obj;
		return other.file.equals(file)
				&& other.IBuildpathEntry.equals(IBuildpathEntry);
	}
}

class PHPResLabelProvider extends ScriptUILabelProvider {

	public Image getImage(Object element) {
		if (element instanceof IBuildpathEntry) {
			IBuildpathEntry buildpathEntry = (IBuildpathEntry) element;
			if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_ENV_VAR);
			} else {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
			}
		}
		if (element instanceof IncPathFile) {
			IncPathFile currentFile = (IncPathFile) element;
			if (currentFile.file.isDirectory()) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_FOLDER);
			} else {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_FILE);
			}
		}
		return super.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof IBuildpathEntry) {
			IBuildpathEntry includePathEntry = (IBuildpathEntry) element;
			return EnvironmentPathUtils
					.getLocalPath(includePathEntry.getPath()).toOSString();
		}
		if (element instanceof IncPathFile) {
			return ((IncPathFile) element).file.getName();
		}
		return super.getText(element);
	}
}
