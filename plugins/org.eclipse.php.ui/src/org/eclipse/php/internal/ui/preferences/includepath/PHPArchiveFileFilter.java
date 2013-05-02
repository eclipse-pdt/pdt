package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.PHPToolkitUtil;

public class PHPArchiveFileFilter extends ViewerFilter {

	public static final String[] FILTER_EXTENSIONS = new String[] {
			"*.phar", "*.tar", "*.bz2", "*.gz", "*.zip" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	//	private static final String[] fgArchiveExtensions = { "phar","zip" }; 

	private List fExcludes;

	private boolean fRecursive;

	/**
	 * @param excludedFiles
	 *            Excluded files will not pass the filter. <code>null</code> is
	 *            allowed if no files should be excluded.
	 * @param recusive
	 *            Folders are only shown if, searched recursively, contain an
	 *            archive
	 */
	public PHPArchiveFileFilter(IFile[] excludedFiles, boolean recusive) {

		if (excludedFiles != null) {
			fExcludes = Arrays.asList(excludedFiles);
		} else {
			fExcludes = null;
		}
		fRecursive = recusive;
	}

	public PHPArchiveFileFilter(List excludedFiles, boolean recusive) {

		fExcludes = excludedFiles;
		fRecursive = recusive;
	}

	/*
	 * @see ViewerFilter#select
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {

		if (element instanceof IFile) {
			if (fExcludes != null && fExcludes.contains(element)) {
				return false;
			}
			return isArchivePath(((IFile) element).getFullPath());
		} else if (element instanceof IContainer) { // IProject, IFolder
			if (!fRecursive) {
				return true;
			}
			// Ignore closed projects
			if (element instanceof IProject && !((IProject) element).isOpen())
				return false;
			try {
				IResource[] resources = ((IContainer) element).members();
				for (int i = 0; i < resources.length; i++) {
					// recursive! Only show containers that contain an archive
					if (select(viewer, parent, resources[i])) {
						return true;
					}
				}
			} catch (CoreException e) {
				DLTKUIPlugin.log(e.getStatus());
			}
		}
		return false;
	}

	public static boolean isArchivePath(IPath path) {

		String ext = path.getFileExtension();
		if (ext != null && ext.length() != 0) {
			return isArchiveFileExtension(ext);
		}
		return false;
	}

	public static boolean isArchiveFileExtension(String ext) {

		for (int i = 0; i < PHPToolkitUtil.PHAR_EXTENSTIONS.length; i++) {
			if (ext.equalsIgnoreCase(PHPToolkitUtil.PHAR_EXTENSTIONS[i])) {
				return true;
			}
		}
		return false;
	}

}
