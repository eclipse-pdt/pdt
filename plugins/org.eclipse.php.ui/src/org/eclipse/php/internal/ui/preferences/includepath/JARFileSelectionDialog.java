/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * Selection dialog to select a JAR on the file system. Set input to a
 * java.io.File that point to folder.
 */
public class JARFileSelectionDialog extends FilteredElementTreeSelectionDialog {

	/**
	 * Constructor for JARFileSelectionDialog.
	 * 
	 * @param parent
	 *            parent shell
	 * @param multiSelect
	 *            specifies if selecting multiple elements is allowed
	 * @param acceptFolders
	 *            specifies if folders can be selected as well
	 */
	public JARFileSelectionDialog(Shell parent, boolean multiSelect,
			boolean acceptFolders) {
		this(parent, multiSelect, acceptFolders, false);
	}

	/**
	 * Constructor for JARFileSelectionDialog.
	 * 
	 * @param parent
	 *            parent shell
	 * @param multiSelect
	 *            specifies if selecting multiple elements is allowed
	 * @param acceptFolders
	 *            specifies if folders can be selected as well
	 * @param acceptAllArchives
	 *            specifies if all archives (not just jar and zip) can be
	 *            selected
	 */
	public JARFileSelectionDialog(Shell parent, boolean multiSelect,
			boolean acceptFolders, boolean acceptAllArchives) {
		super(parent, new FileLabelProvider(), new FileContentProvider(), false);
		setComparator(new FileViewerComparator());
		if (!acceptAllArchives) {
			addFilter(new JARZipFileFilter(acceptFolders));
		} else {
			// TODO
			// setInitialFilter("*.phar;*.zip;*.tar;*.tar.gz;*.tgz;*.tar.bz2");
		}
		setValidator(new FileSelectionValidator(multiSelect, acceptFolders));
		setHelpAvailable(false);
	}

	private static class FileLabelProvider extends LabelProvider {
		private final Image IMG_FOLDER = PlatformUI.getWorkbench()
				.getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		private final Image IMG_JAR = PHPPluginImages
				.get(PHPPluginImages.IMG_OBJS_EXTJAR);

		public Image getImage(Object element) {
			if (element instanceof File) {
				File curr = (File) element;
				if (curr.isDirectory()) {
					return IMG_FOLDER;
				} else {
					// FIXME here we should the right icon for files
					return IMG_JAR;
				}
			}
			return null;
		}

		public String getText(Object element) {
			if (element instanceof File) {
				return BasicElementLabels.getResourceName(((File) element)
						.getName());
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

	private static class JARZipFileFilter extends ViewerFilter {
		private final boolean fAcceptFolders;

		public JARZipFileFilter(boolean acceptFolders) {
			fAcceptFolders = acceptFolders;
		}

		public boolean select(Viewer viewer, Object parent, Object element) {
			if (element instanceof File) {
				File file = (File) element;
				if (file.isFile()) {
					return isArchive(file);
				} else if (fAcceptFolders) {
					return true;
				} else {
					File[] listFiles = file.listFiles();
					if (listFiles != null) {
						for (int i = 0; i < listFiles.length; i++) {
							if (select(viewer, file, listFiles[i])) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}

		private static boolean isArchive(File file) {
			String name = file.getName();
			int detIndex = name.lastIndexOf('.');
			return (detIndex != -1 && PHPToolkitUtil.isPharExtention(name
					.substring(detIndex + 1)));
		}
	}

	private static class FileViewerComparator extends ViewerComparator {
		public int category(Object element) {
			if (element instanceof File) {
				if (((File) element).isFile()) {
					return 1;
				}
			}
			return 0;
		}
	}

	private static class FileSelectionValidator implements
			ISelectionStatusValidator {
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
