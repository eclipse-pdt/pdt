/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.pathmapper;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.debug.core.pathmapper.BestMatchPathComparator;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.help.WorkbenchHelpSystem;

/**
 * Basic map remote to local file dialog.
 * 
 * @author Bartlomiej Laczkowski
 */
public class MapLocalFileDialog extends TrayDialog {

	/**
	 * Path entries label provider
	 */
	private class LabelProvider extends ScriptUILabelProvider {

		public Image getImage(Object element) {
			if (element instanceof IBuildpathEntry) {
				IBuildpathEntry includePathEntry = (IBuildpathEntry) element;
				if (includePathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_ENV_VAR);
				} else {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
				}
			}
			if (element instanceof PathEntry) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_CUNIT);
			}
			return super.getImage(element);
		}

		public String getText(Object element) {
			if (element == EXTERNAL_CONTAINER) {
				return Messages.OpenLocalFileSearchFilter_External_files;
			}
			if (element instanceof IBuildpathEntry) {
				IBuildpathEntry includePathEntry = (IBuildpathEntry) element;
				return EnvironmentPathUtils.getLocalPathString(includePathEntry.getPath());
			}
			if (!(element instanceof PathEntry)) {
				return super.getText(element);
			}
			PathEntry entry = (PathEntry) element;
			String path = entry.getResolvedPath();
			if (entry.getType() == Type.WORKSPACE) {
				VirtualPath tmpPath = entry.getAbstractPath().clone();
				tmpPath.removeFirstSegment();
				path = tmpPath.toString();
				if (path.startsWith("/")) { //$NON-NLS-1$
					path = path.substring(1);
				}
			}
			if (entry.getType() == Type.INCLUDE_FOLDER || entry.getType() == Type.INCLUDE_VAR) {
				IBuildpathEntry includePathEntry = (IBuildpathEntry) entry.getContainer();
				String includePath = EnvironmentPathUtils.getLocalPathString(includePathEntry.getPath());
				if (includePathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
					IPath p = DLTKCore.getResolvedVariablePath(includePathEntry.getPath());
					if (p != null) {
						includePath = p.toOSString();
					}
				}
				if (includePath != null && path.startsWith(includePath)) {
					path = path.substring(includePath.length());
				}
				if (path.startsWith("/")) { //$NON-NLS-1$
					path = path.substring(1);
				}
			}
			return path;
		}

	}

	/**
	 * Path entries content provider
	 */
	private class ContentProvider implements ITreeContentProvider {

		public Object[] getChildren(Object parentElement) {
			if (parentElement == MapLocalFileDialog.this) {
				Set<Object> containers = new HashSet<Object>();
				for (PathEntry entry : pathEntries) {
					if (entry.getType() == Type.EXTERNAL) {
						containers.add(EXTERNAL_CONTAINER);
					} else if (entry.getType() == Type.INCLUDE_VAR || entry.getType() == Type.INCLUDE_FOLDER) {
						containers.add(entry.getContainer());
					} else if (entry.getType() == Type.WORKSPACE) {
						containers.add(((IResource) entry.getContainer()).getProject());
					}
				}
				return containers.toArray();
			}

			if (parentElement instanceof PathEntry) {
				return EMPTY;
			}

			Set<PathEntry> entries = new HashSet<PathEntry>();
			for (PathEntry entry : pathEntries) {
				if (entry.getType() == Type.EXTERNAL && parentElement == EXTERNAL_CONTAINER) {
					entries.add(entry);
				} else if ((entry.getType() == Type.INCLUDE_VAR || entry.getType() == Type.INCLUDE_FOLDER)
						&& entry.getContainer() == parentElement) {
					entries.add(entry);
				} else if (entry.getType() == Type.WORKSPACE
						&& ((IResource) entry.getContainer()).getProject() == parentElement) {
					entries.add(entry);
				}
			}
			return entries.toArray();
		}

		public Object getParent(Object element) {
			if (element == MapLocalFileDialog.this) {
				return null;
			}
			if (element instanceof PathEntry) {
				PathEntry entry = (PathEntry) element;

				if (entry.getType() == Type.EXTERNAL) {
					return EXTERNAL_CONTAINER;
				} else if (entry.getType() == Type.INCLUDE_VAR || entry.getType() == Type.INCLUDE_FOLDER) {
					return entry.getContainer();
				} else if (entry.getType() == Type.WORKSPACE) {
					return ((IResource) entry.getContainer()).getProject();
				}
			}
			for (PathEntry entry : pathEntries) {
				if (entry.getType() == Type.EXTERNAL && element == EXTERNAL_CONTAINER) {
					return entry;
				} else if (entry.getType() == Type.INCLUDE_VAR
						|| entry.getType() == Type.INCLUDE_FOLDER && entry.getContainer() == element) {
					return entry;
				} else if (entry.getType() == Type.WORKSPACE
						&& ((IResource) entry.getContainer()).getProject() == element) {
					return entry;
				}
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof PathEntry) {
				return false;
			}
			return true;
		}

		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	/**
	 * Sorter for path entries by relevancy
	 */
	private class Sorter extends ViewerSorter {

		private BestMatchPathComparator comparator;

		public Sorter() {
			comparator = new BestMatchPathComparator(path);
		}

		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof PathEntry && e2 instanceof PathEntry) {
				return comparator.compare((PathEntry) e1, (PathEntry) e2);
			}
			if (e1 == EXTERNAL_CONTAINER) {
				return 1;
			}
			if (e1 instanceof IResource) {
				return -1;
			}
			return 0;
		}

	}

	private static final Object[] EMPTY = new Object[0];
	private static final Object EXTERNAL_CONTAINER = new Object();

	private VirtualPath path;
	private PathEntry[] pathEntries;
	private PathEntry result;
	private TreeViewer entriesViewer;
	private Button selectMappingRadio;
	private Button selectRemoteRadio;

	public MapLocalFileDialog(Shell shell, VirtualPath path, PathEntry[] pathEntries) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.path = path;
		this.pathEntries = pathEntries;
	}

	/**
	 * Returns result entry.
	 * 
	 * @return result entry
	 */
	public PathEntry getResult() {
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		getShell().forceActive();
		getShell().setActive();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.OpenLocalFileSearchFilter_Open_file_request);
		newShell.setData(WorkbenchHelpSystem.HELP_KEY, IPHPHelpContextIds.PATH_MAPPING);
		newShell.addHelpListener(new HelpListener() {
			public void helpRequested(HelpEvent arg0) {
				org.eclipse.swt.program.Program.launch(IPHPHelpContextIds.PATH_MAPPING);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);
		GridLayout layout = (GridLayout) parent.getLayout();
		layout.numColumns = 2;
		initializeDialogUnits(parent);
		FontData[] fontData = parent.getFont().getFontData();
		for (FontData d : fontData) {
			d.setStyle(SWT.BOLD);
		}
		GridData layoutData;
		final Font boldFont = new Font(parent.getFont().getDevice(), fontData);
		Label remoteLabel = new Label(parent, SWT.NONE);
		remoteLabel.setText(Messages.OpenLocalFileSearchFilter_Remote_file_is_requested);
		layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		remoteLabel.setLayoutData(layoutData);
		Label label = new Label(parent, SWT.NONE);
		label.setFont(boldFont);
		label.setText(path.toString());
		layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		layoutData.horizontalIndent = convertVerticalDLUsToPixels(10);
		label.setLayoutData(layoutData);

		selectRemoteRadio = new Button(parent, SWT.RADIO);
		selectRemoteRadio.setSelection(false);
		selectRemoteRadio.setText(Messages.OpenLocalFileSearchFilter_Open_remote);
		layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		selectRemoteRadio.setLayoutData(layoutData);

		selectMappingRadio = new Button(parent, SWT.RADIO);
		selectMappingRadio.setSelection(true);
		selectMappingRadio.setText(Messages.OpenLocalFileSearchFilter_Map_and_open_local);
		layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		selectMappingRadio.setLayoutData(layoutData);

		selectRemoteRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectMappingRadio.setSelection(!selectRemoteRadio.getSelection());
			}
		});
		selectMappingRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectRemoteRadio.setSelection(!selectMappingRadio.getSelection());
				entriesViewer.getTree().setEnabled(selectMappingRadio.getSelection());
				validate();
			}
		});

		entriesViewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.horizontalSpan = 2;
		layoutData.minimumHeight = convertVerticalDLUsToPixels(80);
		entriesViewer.getControl().setLayoutData(layoutData);
		entriesViewer.setContentProvider(new ContentProvider());
		entriesViewer.setLabelProvider(new LabelProvider());
		entriesViewer.setSorter(new Sorter());
		entriesViewer.setInput(this);
		entriesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				validate();
			}
		});
		entriesViewer.expandAll();
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.
	 * Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		validate();
		return control;
	}

	protected void validate() {
		Button okButton = getButton(IDialogConstants.OK_ID);
		okButton.setEnabled(false);
		result = null;
		if (selectMappingRadio.getSelection()) {
			Object selectedElement = ((IStructuredSelection) entriesViewer.getSelection()).getFirstElement();
			if (selectedElement instanceof PathEntry) {
				okButton.setEnabled(true);
				result = (PathEntry) selectedElement;
			}
		} else {
			okButton.setEnabled(true);
		}
	}

}