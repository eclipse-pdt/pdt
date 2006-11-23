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
package org.eclipse.php.ui.preferences.includepath;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.internal.ui.actions.StatusInfo;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * Selection dialog to select a Zip on the file system.
 * Set input to a java.io.File that point to folder.
 */
public class ZipFileSelectionDialog extends ElementTreeSelectionDialog {

	/**
	 * Constructor for ZipFileSelectionDialog.
	 */
	public ZipFileSelectionDialog(Shell parent, boolean multiSelect, boolean acceptFolders) {
		super(parent, new FileLabelProvider(), new FileContentProvider());
		setSorter(new FileViewerSorter());
		addFilter(new FileArchiveFileFilter());
		setValidator(new FileSelectionValidator(multiSelect, acceptFolders));
	}

	private static boolean isArchive(File file) {
		String name = file.getName();
		int detIndex = name.lastIndexOf('.');
		return (detIndex != -1 && ArchieveFileFilter.isArchiveFileExtension(name.substring(detIndex + 1)));
	}

	private static class FileLabelProvider extends LabelProvider {
		private final Image IMG_FOLDER = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		private final Image IMG_ZIP = PHPUiPlugin.getDefault().getImageRegistry().get(PHPPluginImages.IMG_OBJS_EXTZIP);

		public Image getImage(Object element) {
			if (element instanceof File) {
				File curr = (File) element;
				if (curr.isDirectory()) {
					return IMG_FOLDER;
				} else {
					return IMG_ZIP;
				}
			}
			return null;
		}

		public String getText(Object element) {
			if (element instanceof File) {
				return ((File) element).getName();
			}
			return super.getText(element);
		}
	}

	private static class FileContentProvider implements ITreeContentProvider {

		private final Object[] EMPTY = new Object[0];

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof File) {
				File[] children = ((File) parentElement).listFiles();
				if (children != null) {
					return children;
				}
			}
			return EMPTY;
		}

		public Object getParent(Object element) {
			if (element instanceof File) {
				return ((File) element).getParentFile();
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		public Object[] getElements(Object element) {
			return getChildren(element);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private static class FileArchiveFileFilter extends ViewerFilter {
		public boolean select(Viewer viewer, Object parent, Object element) {
			if (element instanceof File) {
				File file = (File) element;
				if (file.isFile()) {
					return isArchive(file);
				} else {
					return true;
				}
			}
			return false;
		}
	}

	private static class FileViewerSorter extends ViewerSorter {
		public int category(Object element) {
			if (element instanceof File) {
				if (((File) element).isFile()) {
					return 1;
				}
			}
			return 0;
		}
	}

	private static class FileSelectionValidator implements ISelectionStatusValidator {
		private boolean fMultiSelect;
		private boolean fAcceptFolders;

		public FileSelectionValidator(boolean multiSelect, boolean acceptFolders) {
			fMultiSelect = multiSelect;
			fAcceptFolders = acceptFolders;
		}

		public IStatus validate(Object[] selection) {
			int nSelected = selection.length;
			if (nSelected == 0 || (nSelected > 1 && !fMultiSelect)) {
				return new StatusInfo(IStatus.ERROR, ""); //$NON-NLS-1$
			}
			for (int i = 0; i < selection.length; i++) {
				Object curr = selection[i];
				if (curr instanceof File) {
					File file = (File) curr;
					if (!fAcceptFolders && !file.isFile()) {
						return new StatusInfo(IStatus.ERROR, ""); //$NON-NLS-1$
					}
				}
			}
			return new StatusInfo();
		}
	}

}
