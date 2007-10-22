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
package org.eclipse.php.internal.debug.ui.pathmapper;

import java.io.File;
import java.util.Comparator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.AbstractPath;
import org.eclipse.php.internal.debug.core.pathmapper.BestMatchPathComparator;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.util.PHPUILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.dialogs.SearchPattern;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

public class PathEntrySelectionDialog extends FilteredItemsSelectionDialog {

	private static final String DIALOG_SETTINGS = "org.eclipse.php.internal.debug.core.pathmapper.PathEntrySelectionDialogSettings"; //$NON-NLS-1$
	private PathEntry[] pathEntries;
	private BestMatchPathComparator comparator;
	private DetailsLabelProvider detailsLabelProvider;

	/**
	 * Constructor
	 * @param shell
	 * @param path Abstract path of the file to match
	 * @param files Files to choose
	 */
	public PathEntrySelectionDialog(Shell shell, AbstractPath path, PathEntry[] pathEntries) {
		super(shell);
		this.pathEntries = pathEntries;
		this.comparator = new BestMatchPathComparator(path);

		setTitle("Select local resource that matches remote file");
		setMessage(NLS.bind("Select local resource that matches remote file ''{0}''\n\nUse pattern to filter results (? = any character, * = any string):", path.toString()));
		setListLabelProvider(new LabelProvider());
		setDetailsLabelProvider(detailsLabelProvider = new DetailsLabelProvider());
	}

	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	class EntriesFilter extends ItemsFilter {
		public EntriesFilter() {
			super(new SearchPattern() {
				public void setPattern(String stringPattern) {
					if (!stringPattern.startsWith("*")) {
						stringPattern = "*" + stringPattern;
					}
					super.setPattern(stringPattern);
				}
			});
		}

		public boolean isConsistentItem(Object item) {
			return true;
		}

		public boolean matchItem(Object item) {
			return matches(((PathEntry) item).getAbstractPath().toString());
		}
	}

	protected ItemsFilter createFilter() {
		return new EntriesFilter();
	}

	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter, IProgressMonitor progressMonitor) throws CoreException {
		for (int i = 0; i < pathEntries.length; ++i) {
			contentProvider.add(pathEntries[i], itemsFilter);
		}
		if (progressMonitor != null) {
			progressMonitor.done();
		}
	}

	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = IDEWorkbenchPlugin.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS);
		if (settings == null) {
			settings = IDEWorkbenchPlugin.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS);
		}
		return settings;
	}

	public String getElementName(Object item) {
		if (item instanceof PathEntry) {
			return ((PathEntry) item).getAbstractPath().getLastSegment();
		}
		return null;
	}

	protected Comparator<PathEntry> getItemsComparator() {
		return comparator;
	}

	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

	private class LabelProvider extends org.eclipse.jface.viewers.LabelProvider {
		public Image getImage(Object element) {
			if (!(element instanceof PathEntry)) {
				return super.getImage(element);
			}
			return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_CUNIT);
		}

		public String getText(Object element) {
			if (!(element instanceof PathEntry)) {
				return super.getText(element);
			}
			PathEntry pathEntry = (PathEntry) element;
			return NLS.bind("{0} - {1}", pathEntry.getAbstractPath().getLastSegment(), detailsLabelProvider.getText(element));
		}
	}

	private class DetailsLabelProvider extends org.eclipse.jface.viewers.LabelProvider {
		private PHPUILabelProvider phpLabelProvider = new PHPUILabelProvider();

		public Image getImage(Object element) {
			if (!(element instanceof PathEntry)) {
				return super.getImage(element);
			}
			PathEntry pathEntry = (PathEntry) element;
			if (pathEntry.getType() == Type.EXTERNAL) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
			}
			if (pathEntry.getContainer() instanceof IncludePathEntry) {
				if (pathEntry.getType() == Type.INCLUDE_VAR) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_ENV_VAR);
				} else {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
				}
			}
			return phpLabelProvider.getImage(pathEntry.getContainer());
		}

		public String getText(Object element) {
			if (!(element instanceof PathEntry)) {
				return super.getText(element);
			}
			PathEntry pathEntry = (PathEntry) element;
			if (pathEntry.getType() == Type.EXTERNAL) {
				return ((File) pathEntry.getContainer()).getPath();
			}
			if (pathEntry.getContainer() instanceof IncludePathEntry) {
				IncludePathEntry entry = (IncludePathEntry) pathEntry.getContainer();
				return entry.getPath().toOSString();
			}
			if (pathEntry.getContainer() instanceof IResource) {
				String path = ((IResource) pathEntry.getContainer()).getFullPath().toPortableString();
				if (path.startsWith("/")) {
					path = path.substring(1);
				}
				return path;
			}
			return phpLabelProvider.getText(pathEntry.getContainer());
		}
	}
}
