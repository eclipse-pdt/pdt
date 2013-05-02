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
package org.eclipse.php.internal.debug.ui.pathmapper;

import java.util.*;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.pathmapper.BestMatchPathComparator;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class PathEntrySelectionDialog extends TrayDialog {

	private static final Object[] EMPTY = new Object[0];
	private static final Object EXTERNAL_CONTAINER = new Object();

	private VirtualPath path;
	private PathEntry[] pathEntries;
	private PathEntry result;
	private VirtualPath ignorePathResult;
	private Font boldFont;
	protected Button selectMappingBtn;
	private TreeViewer entriesViewer;
	protected Button ignoreMappingBtn;
	private Text ignorePathText;
	private Button configurePathBtn;

	/**
	 * Constructs new path entry selection dialog
	 * 
	 * @param shell
	 *            Window shell
	 * @param path
	 *            Abstract path of the file to match
	 * @param files
	 *            Files to choose
	 */
	public PathEntrySelectionDialog(Shell shell, VirtualPath path,
			PathEntry[] pathEntries) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);

		this.path = path;
		this.pathEntries = pathEntries;
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);

		newShell.setText(Messages.PathEntrySelectionDialog_0);
		newShell.setImage(PHPDebugUIImages
				.get(PHPDebugUIImages.IMG_OBJ_PATH_MAPPING));

		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell,
				IPHPHelpContextIds.PATH_MAPPING);
	}

	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);
		GridLayout layout = (GridLayout) parent.getLayout();
		layout.numColumns = 2;

		initializeDialogUnits(parent);

		FontData[] fontData = parent.getFont().getFontData();
		for (FontData d : fontData) {
			d.setStyle(SWT.BOLD);
		}
		boldFont = new Font(parent.getFont().getDevice(), fontData);

		selectMappingBtn = new Button(parent, SWT.RADIO);
		selectMappingBtn.setSelection(true);
		selectMappingBtn
				.setText(Messages.PathEntrySelectionDialog_1);
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		selectMappingBtn.setLayoutData(layoutData);
		selectMappingBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = selectMappingBtn.getSelection();
				entriesViewer.getControl().setEnabled(enabled);
				ignorePathText.setEnabled(!enabled);
				configurePathBtn.setEnabled(!enabled);
				validate();
			}
		});

		Label label = new Label(parent, SWT.NONE);
		layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		layoutData.horizontalIndent = convertWidthInCharsToPixels(4);
		layoutData.verticalAlignment = SWT.BEGINNING;
		label.setLayoutData(layoutData);

		label.setFont(boldFont);
		label.setText(path.toString());

		entriesViewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.horizontalSpan = 2;
		layoutData.verticalIndent = convertHeightInCharsToPixels(1);
		layoutData.horizontalIndent = convertWidthInCharsToPixels(2);
		layoutData.heightHint = convertHeightInCharsToPixels(20);
		layoutData.widthHint = convertWidthInCharsToPixels(70);
		entriesViewer.getControl().setLayoutData(layoutData);
		entriesViewer.setContentProvider(new ContentProvider());
		entriesViewer.setLabelProvider(new LabelProvider());
		entriesViewer.setSorter(new Sorter());
		entriesViewer.setInput(this);
		entriesViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						validate();
					}
				});
		entriesViewer.expandAll();

		ignoreMappingBtn = new Button(parent, SWT.RADIO);
		ignoreMappingBtn
				.setText(Messages.PathEntrySelectionDialog_2);
		layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		ignoreMappingBtn.setLayoutData(layoutData);
		ignoreMappingBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = ignoreMappingBtn.getSelection();
				entriesViewer.getControl().setEnabled(!enabled);
				ignorePathText.setEnabled(enabled);
				configurePathBtn.setEnabled(enabled);
				validate();
			}
		});

		ignorePathText = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		ignorePathText.setEnabled(false);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalIndent = convertWidthInCharsToPixels(2);
		layoutData.widthHint = convertWidthInCharsToPixels(70);
		ignorePathText.setLayoutData(layoutData);
		ignorePathResult = path.clone();
		ignorePathText.setText(getIgnorePathString(ignorePathResult));

		configurePathBtn = new Button(parent, SWT.PUSH);
		configurePathBtn.setEnabled(false);
		configurePathBtn.setText(Messages.PathEntrySelectionDialog_3);
		configurePathBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ConfigurePathDialog dialog = new ConfigurePathDialog(
						ignorePathResult);
				if (dialog.open() == Window.OK) {
					ignorePathResult = dialog.getResult();
					ignorePathText
							.setText(getIgnorePathString(ignorePathResult));
					validate();
				}
			}
		});

		return parent;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		Button okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);

		createButton(parent, IDialogConstants.CANCEL_ID, Messages.PathEntrySelectionDialog_4,
				false);
	}

	private String getIgnorePathString(VirtualPath path) {
		if (path.getSegmentsCount() == PathEntrySelectionDialog.this.path
				.getSegmentsCount()) {
			return path.toString();
		}
		return new StringBuilder(path.toString()).append(
				path.getSeparatorChar()).append('*').toString();
	}

	/**
	 * Returns selected path entry
	 * 
	 * @return path entry
	 */
	public PathEntry getResult() {
		return result;
	}

	/**
	 * Returns ignored path result
	 */
	public VirtualPath getIgnoreResult() {
		return ignorePathResult;
	}

	protected void validate() {

		Button okButton = getButton(IDialogConstants.OK_ID);
		okButton.setEnabled(false);
		result = null;

		if (selectMappingBtn.getSelection()) {

			Object selectedElement = ((IStructuredSelection) entriesViewer
					.getSelection()).getFirstElement();
			if (selectedElement instanceof PathEntry) {
				okButton.setEnabled(true);
				result = (PathEntry) selectedElement;
			}
		} else {
			okButton.setEnabled(true);
		}
	}

	/**
	 * Ignored paths configuration dialog
	 */
	class ConfigurePathDialog extends Dialog {

		private VirtualPath result;
		private Text pathText;

		protected ConfigurePathDialog(VirtualPath path) {

			super(PathEntrySelectionDialog.this.getShell());

			setShellStyle(getShellStyle() | SWT.RESIZE);

			this.result = path;
		}

		public VirtualPath getResult() {
			return result;
		}

		protected Control createDialogArea(Composite parent) {
			parent = (Composite) super.createDialogArea(parent);

			initializeDialogUnits(parent);

			getShell().setText(Messages.PathEntrySelectionDialog_5);
			getShell()
					.setImage(
							PHPDebugUIImages
									.get(PHPDebugUIImages.IMG_OBJ_PATH_MAPPING));

			TreeViewer treeViewer = new TreeViewer(parent, SWT.SINGLE
					| SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			GridData layoutData = new GridData(GridData.FILL_BOTH);
			layoutData.heightHint = convertHeightInCharsToPixels(15);
			layoutData.widthHint = convertWidthInCharsToPixels(60);
			treeViewer.getControl().setLayoutData(layoutData);

			treeViewer.setContentProvider(new ContentProvider());
			treeViewer.setLabelProvider(new LabelProvider());
			treeViewer.setInput(Integer.valueOf(0));
			treeViewer
					.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							Integer segmentNum = (Integer) ((IStructuredSelection) event
									.getSelection()).getFirstElement();
							if (segmentNum != null) {
								result = path.clone();
								for (int i = path.getSegmentsCount(); i > segmentNum; --i) {
									result.removeLastSegment();
								}
								pathText.setText(getIgnorePathString(result));
							}
						}
					});

			pathText = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
			layoutData = new GridData(GridData.FILL_HORIZONTAL);
			pathText.setLayoutData(layoutData);

			treeViewer.setSelection(new StructuredSelection(result
					.getSegmentsCount()));

			treeViewer.expandAll();

			return parent;
		}

		class ContentProvider implements ITreeContentProvider {

			public Object[] getChildren(Object parentElement) {
				Integer segmentNum = (Integer) parentElement;
				if (segmentNum < path.getSegmentsCount()) {
					return new Object[] { segmentNum + 1 };
				}
				return EMPTY;
			}

			public Object getParent(Object element) {
				Integer segmentNum = (Integer) element;
				if (segmentNum > 0) {
					return segmentNum - 1;
				}
				return null;
			}

			public boolean hasChildren(Object element) {
				Integer segmentNum = (Integer) element;
				return (segmentNum < path.getSegmentsCount());
			}

			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		}

		class LabelProvider extends org.eclipse.jface.viewers.LabelProvider {

			private Map<String, Image> images = new HashMap<String, Image>();

			public Image getImage(Object element) {
				Integer segmentNum = (Integer) element;
				if (segmentNum < path.getSegmentsCount()) {
					return PlatformUI.getWorkbench().getSharedImages()
							.getImage(ISharedImages.IMG_OBJ_FOLDER);
				}
				String lastSegment = path.getLastSegment();
				int idx = lastSegment.lastIndexOf('.');
				if (idx != -1) {
					String extension = lastSegment.substring(idx);
					if (images.containsKey(extension)) {
						return images.get(extension);
					}
					Program p = Program.findProgram(extension);
					if (p != null) {
						ImageData data = p.getImageData();
						if (data != null) {
							Image image = new Image(Display.getDefault(), data);
							images.put(extension, image);
							return image;
						}
					}
				}
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_FILE);
			}

			public String getText(Object element) {
				Integer segmentNum = (Integer) element;
				return path.getSegments()[segmentNum - 1];
			}

			public void dispose() {
				Iterator<Image> i = images.values().iterator();
				while (i.hasNext()) {
					i.next().dispose();
				}
			}
		}
	}

	/**
	 * Sorter for path entries by relevancy
	 */
	class Sorter extends ViewerSorter {

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

	/**
	 * Path entries label provider
	 */
	class LabelProvider extends ScriptUILabelProvider {

		public Image getImage(Object element) {

			if (element instanceof IBuildpathEntry) {
				IBuildpathEntry includePathEntry = (IBuildpathEntry) element;
				if (includePathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
					return PHPPluginImages
							.get(PHPPluginImages.IMG_OBJS_ENV_VAR);
				} else {
					return PHPPluginImages
							.get(PHPPluginImages.IMG_OBJS_LIBRARY);
				}
			}

			if (element instanceof PathEntry) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_CUNIT);
			}

			return super.getImage(element);
		}

		public String getText(Object element) {
			if (element == EXTERNAL_CONTAINER) {
				return Messages.PathEntrySelectionDialog_6;
			}

			if (element instanceof IBuildpathEntry) {
				IBuildpathEntry includePathEntry = (IBuildpathEntry) element;
				return EnvironmentPathUtils.getLocalPathString(includePathEntry
						.getPath());
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
			if (entry.getType() == Type.INCLUDE_FOLDER
					|| entry.getType() == Type.INCLUDE_VAR) {
				IBuildpathEntry includePathEntry = (IBuildpathEntry) entry
						.getContainer();
				String includePath = EnvironmentPathUtils
						.getLocalPathString(includePathEntry.getPath());
				if (includePathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
					IPath p = DLTKCore.getResolvedVariablePath(includePathEntry
							.getPath());
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
	class ContentProvider implements ITreeContentProvider {

		public Object[] getChildren(Object parentElement) {

			if (parentElement == PathEntrySelectionDialog.this) {
				Set<Object> containers = new HashSet<Object>();
				for (PathEntry entry : pathEntries) {
					if (entry.getType() == Type.EXTERNAL) {
						containers.add(EXTERNAL_CONTAINER);
					} else if (entry.getType() == Type.INCLUDE_VAR
							|| entry.getType() == Type.INCLUDE_FOLDER) {
						containers.add(entry.getContainer());
					} else if (entry.getType() == Type.WORKSPACE) {
						containers.add(((IResource) entry.getContainer())
								.getProject());
					}
				}
				return containers.toArray();
			}

			if (parentElement instanceof PathEntry) {
				return EMPTY;
			}

			Set<PathEntry> entries = new HashSet<PathEntry>();
			for (PathEntry entry : pathEntries) {
				if (entry.getType() == Type.EXTERNAL
						&& parentElement == EXTERNAL_CONTAINER) {
					entries.add(entry);
				} else if ((entry.getType() == Type.INCLUDE_VAR || entry
						.getType() == Type.INCLUDE_FOLDER)
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

			if (element == PathEntrySelectionDialog.this) {
				return null;
			}

			if (element instanceof PathEntry) {
				PathEntry entry = (PathEntry) element;

				if (entry.getType() == Type.EXTERNAL) {
					return EXTERNAL_CONTAINER;
				} else if (entry.getType() == Type.INCLUDE_VAR
						|| entry.getType() == Type.INCLUDE_FOLDER) {
					return entry.getContainer();
				} else if (entry.getType() == Type.WORKSPACE) {
					return ((IResource) entry.getContainer()).getProject();
				}
			}

			for (PathEntry entry : pathEntries) {
				if (entry.getType() == Type.EXTERNAL
						&& element == EXTERNAL_CONTAINER) {
					return entry;
				} else if (entry.getType() == Type.INCLUDE_VAR
						|| entry.getType() == Type.INCLUDE_FOLDER
						&& entry.getContainer() == element) {
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
}
