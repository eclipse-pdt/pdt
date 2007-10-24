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
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.debug.core.pathmapper.AbstractPath;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping;
import org.eclipse.php.internal.debug.ui.pathmapper.PathMapperEntryDialog.WorkspaceBrowseDialog.IPFile;
import org.eclipse.php.internal.ui.treecontent.IncludesNode;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.util.PHPUILabelProvider;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class PathMapperEntryDialog extends StatusDialog {

	private Mapping fEditData;
	private Text fRemotePathText;
	private Button fWorkspacePathBtn;
	private Button fExternalPathBtn;
	private Text fWorkspacePathText;
	private Text fExternalPathText;
	private Button fWorkspacePathBrowseBtn;
	private Button fExternalPathBrowseBtn;

	public PathMapperEntryDialog(Shell parent) {
		this(parent, null);
	}

	public PathMapperEntryDialog(Shell parent, Mapping editData) {
		super(parent);
		if (editData != null) {
			fEditData = editData.clone();
			setTitle("Edit Path Mapping");
		} else {
			setTitle("Add new Path Mapping");
		}
	}

	public Mapping getResult() {
		return fEditData;
	}

	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);
		PixelConverter pixelConverter = new PixelConverter(parent);

		// Remote path text field:
		Label label = new Label(parent, SWT.NONE);
		label.setText("Path on Server:");

		fRemotePathText = new Text(parent, SWT.BORDER);
		fRemotePathText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.widthHint = pixelConverter.convertWidthInCharsToPixels(50);
		fRemotePathText.setLayoutData(layoutData);

		// Radio buttons group:
		Composite typeSelectionGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		typeSelectionGroup.setLayout(layout);
		typeSelectionGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Workspace file:
		fWorkspacePathBtn = new Button(typeSelectionGroup, SWT.RADIO);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 2;
		fWorkspacePathBtn.setLayoutData(layoutData);
		fWorkspacePathBtn.setText("Path in &Workspace");
		fWorkspacePathBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = fWorkspacePathBtn.getSelection();
				fWorkspacePathText.setEnabled(enabled);
				fWorkspacePathBrowseBtn.setEnabled(enabled);
				fExternalPathText.setEnabled(!enabled);
				fExternalPathBrowseBtn.setEnabled(!enabled);
			}
		});

		fWorkspacePathText = new Text(typeSelectionGroup, SWT.BORDER);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalIndent = pixelConverter.convertWidthInCharsToPixels(1);
		fWorkspacePathText.setLayoutData(layoutData);
		fWorkspacePathText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		fWorkspacePathBrowseBtn = new Button(typeSelectionGroup, SWT.NONE);
		fWorkspacePathBrowseBtn.setText("&Browse");
		fWorkspacePathBrowseBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				WorkspaceBrowseDialog dialog = new WorkspaceBrowseDialog(getShell());
				if (dialog.open() == Window.OK) {
					Object selectedElement = dialog.getSelectedElement();
					fWorkspacePathText.setData(null);
					if (selectedElement instanceof IResource) {
						IResource resource = (IResource) selectedElement;
						fWorkspacePathText.setText(resource.getFullPath().toString());
						fWorkspacePathText.setData(Type.WORKSPACE);
					} else if (selectedElement instanceof IIncludePathEntry) {
						IIncludePathEntry includePathEntry = (IIncludePathEntry) selectedElement;
						fWorkspacePathText.setData(includePathEntry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE ? Type.INCLUDE_VAR : Type.INCLUDE_FOLDER);
						if (includePathEntry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
							IPath incPath = IncludePathVariableManager.instance().resolveVariablePath(includePathEntry.getPath().toString());
							if (incPath != null) {
								fWorkspacePathText.setText(incPath.toOSString());
							}
						} else {
							fWorkspacePathText.setText(includePathEntry.getPath().toOSString());
						}
					} else if (selectedElement instanceof IPFile) {
						IPFile ipFile = (IPFile) selectedElement;
						IIncludePathEntry includePathEntry = ipFile.includePathEntry;
						fWorkspacePathText.setData(includePathEntry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE ? Type.INCLUDE_VAR : Type.INCLUDE_FOLDER);
						fWorkspacePathText.setText(ipFile.file.getAbsolutePath());
					}
				}
			}
		});

		// External file:
		fExternalPathBtn = new Button(typeSelectionGroup, SWT.RADIO);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 2;
		fExternalPathBtn.setLayoutData(layoutData);
		fExternalPathBtn.setText("Path in &File System");
		fExternalPathBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = fExternalPathBtn.getSelection();
				fExternalPathText.setEnabled(enabled);
				fExternalPathBrowseBtn.setEnabled(enabled);
				fWorkspacePathText.setEnabled(!enabled);
				fWorkspacePathBrowseBtn.setEnabled(!enabled);
			}
		});

		fExternalPathText = new Text(typeSelectionGroup, SWT.BORDER);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalIndent = pixelConverter.convertWidthInCharsToPixels(1);
		fExternalPathText.setLayoutData(layoutData);
		fExternalPathText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		fExternalPathBrowseBtn = new Button(typeSelectionGroup, SWT.NONE);
		fExternalPathBrowseBtn.setText("&Browse");
		fExternalPathBrowseBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				String path = dialog.open();
				if (path != null) {
					fExternalPathText.setText(path);
				}
			}
		});

		applyDialogFont(parent);
		initializeValues();

		return parent;
	}

	protected void initializeValues() {
		fWorkspacePathBtn.setSelection(true);
		fExternalPathBtn.setSelection(false);

		if (fEditData != null) {
			fRemotePathText.setText(fEditData.remotePath.toString());

			if (fEditData.type == Type.EXTERNAL) {
				fExternalPathBtn.setSelection(true);
				fWorkspacePathBtn.setSelection(false);
				fExternalPathText.setText(fEditData.localPath.toString());
			} else {
				fWorkspacePathText.setText(fEditData.localPath.toString());
				fWorkspacePathText.setData(fEditData.type);
			}
		}
		fWorkspacePathBtn.notifyListeners(SWT.Selection, new Event());
		fExternalPathBtn.notifyListeners(SWT.Selection, new Event());
	}

	protected void setError(String error) {
		updateStatus(new StatusInfo(IStatus.ERROR, error));
	}

	protected void validate() {
		Mapping mapping = new Mapping();

		String remotePathStr = fRemotePathText.getText().trim();
		if (remotePathStr.length() == 0) {
			setError("Path on server must not be empty!");
			return;
		}
		try {
			mapping.remotePath = new AbstractPath(remotePathStr);
		} catch (IllegalArgumentException e) {
			setError("Path on server is illegal or not absolute!");
			return;
		}

		// Workspace file:
		if (fWorkspacePathBtn.getSelection()) {
			String workspacePath = fWorkspacePathText.getText().trim();
			if (workspacePath.length() == 0) {
				setError("Path in workspace must not be empty!");
				return;
			}

			boolean pathExistsInWorkspace = false;
			mapping.type = (Type) fWorkspacePathText.getData();
			if (mapping.type == Type.INCLUDE_FOLDER  || mapping.type == Type.INCLUDE_VAR) {
				pathExistsInWorkspace = new File(workspacePath).exists();
			} else {
				pathExistsInWorkspace = (ResourcesPlugin.getWorkspace().getRoot().findMember(workspacePath) != null);
			}
			if (!pathExistsInWorkspace) {
				setError(NLS.bind("Path ''{0}'' doesn't exist in workspace!", workspacePath));
				return;
			}
			try {
				mapping.localPath = new AbstractPath(workspacePath);
			} catch (IllegalArgumentException e) {
				setError("Path in workspace is illegal or not absolute!");
				return;
			}
		} else { // External file:
			String externalPath = fExternalPathText.getText().trim();
			if (externalPath.length() == 0) {
				setError("Path in file system must not be empty!");
				return;
			}
			if (!new File(externalPath).exists()) {
				setError(NLS.bind("Path ''{0}'' doesn't exist in file system!", externalPath));
				return;
			}
			try {
				mapping.type = Type.EXTERNAL;
				mapping.localPath = new AbstractPath(externalPath);
			} catch (IllegalArgumentException e) {
				setError("Path in file system is illegal or not absolute!");
				return;
			}
		}

		fEditData = mapping;

		updateStatus(Status.OK_STATUS);
	}

	class WorkspaceBrowseDialog extends StatusDialog {
		private TreeViewer fViewer;
		private Object selectedElement;

		public WorkspaceBrowseDialog(Shell parent) {
			super(parent);
			setTitle("Select Workspace Resource");
		}

		public Object getSelectedElement() {
			return selectedElement;
		}

		protected Control createDialogArea(Composite parent) {
			parent = (Composite) super.createDialogArea(parent);
			parent.setLayoutData(new GridData(GridData.FILL_BOTH));

			PixelConverter pixelConverter = new PixelConverter(parent);

			fViewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			GridData layoutData = new GridData(GridData.FILL_BOTH);
			layoutData.widthHint = pixelConverter.convertWidthInCharsToPixels(70);
			layoutData.heightHint = pixelConverter.convertHeightInCharsToPixels(20);
			fViewer.getControl().setLayoutData(layoutData);

			fViewer.setContentProvider(new ContentProvider());
			fViewer.setLabelProvider(new LabelProvider());

			fViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					validate();
				}
			});

			fViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());

			return parent;
		}

		protected void validate() {
			IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
			Object element = selection.getFirstElement();
			if (element == null || element instanceof IncludesNode) {
				updateStatus(new StatusInfo(IStatus.ERROR, ""));
				return;
			}
			selectedElement = element;
			updateStatus(Status.OK_STATUS);
		}

		class IPFile {
			IIncludePathEntry includePathEntry;
			File file;

			IPFile(IIncludePathEntry includePathEntry, File file) {
				this.includePathEntry = includePathEntry;
				this.file = file;
			}

			public int hashCode() {
				return file.hashCode() + 13 * includePathEntry.hashCode();
			}

			public boolean equals(Object obj) {
				if (!(obj instanceof IPFile)) {
					return false;
				}
				IPFile other = (IPFile) obj;
				return other.file.equals(file) && other.includePathEntry.equals(includePathEntry);
			}
		}

		class ContentProvider implements ITreeContentProvider {

			public Object[] getChildren(Object parentElement) {
				try {
					if (parentElement instanceof IContainer) {
						List<Object> r = new LinkedList<Object>();
						// Add all members:
						IContainer container = (IContainer) parentElement;
						IResource[] members = container.members();
						for (IResource member : members) {
							if (member instanceof IContainer && member.isAccessible()) {
								r.add(member);
							}
						}
						// Add include paths:
						if (parentElement instanceof IProject) {
							IProject project = (IProject) parentElement;
							PHPProjectOptions options = PHPProjectOptions.forProject(project);
							if (options != null) {
								IIncludePathEntry[] includePath = options.readRawIncludePath();
								r.addAll(Arrays.asList(includePath));
							}
						}
						return r.toArray();
					} else if (parentElement instanceof IIncludePathEntry) {
						IIncludePathEntry includePathEntry = (IIncludePathEntry) parentElement;
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
							return getChildren(new IPFile(includePathEntry, file));
						}
					} else if (parentElement instanceof IPFile) {
						IPFile ipFile = (IPFile) parentElement;
						File file = ipFile.file;
						if (file.isDirectory()) {
							File dirs[] = file.listFiles(new FileFilter() {
								public boolean accept(File pathname) {
									return pathname.isDirectory();
								}
							});
							List<Object> r = new ArrayList<Object>(dirs.length);
							for (File dir : dirs) {
								r.add(new IPFile(ipFile.includePathEntry, dir));
							}
							return r.toArray();
						}
					}
				} catch (CoreException e) {
				}
				return new Object[0];
			}

			public Object getParent(Object element) {
				if (element instanceof IResource) {
					return ((IResource) element).getParent();
				}
				if (element instanceof IPFile) {
					IPFile ipFile = (IPFile) element;
					return new IPFile(ipFile.includePathEntry, ipFile.file.getParentFile());
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

		class LabelProvider extends PHPUILabelProvider {

			public Image getImage(Object element) {
				if (element instanceof IIncludePathEntry) {
					IIncludePathEntry includePathEntry = (IIncludePathEntry) element;
					if (includePathEntry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
						return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_ENV_VAR);
					} else {
						return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
					}
				}
				if (element instanceof IPFile) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
				}
				return super.getImage(element);
			}

			public String getText(Object element) {
				if (element instanceof IIncludePathEntry) {
					IIncludePathEntry includePathEntry = (IIncludePathEntry) element;
					return includePathEntry.getPath().toOSString();
				}
				if (element instanceof IPFile) {
					return ((IPFile) element).file.getName();
				}
				return super.getText(element);
			}
		}
	}
}
