/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.pathmapper;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
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
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping.MappingSource;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.debug.ui.pathmapper.PathMapperEntryDialog.WorkspaceBrowseDialog.IPFile;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class PathMapperEntryDialog extends TitleAreaDialog {

	private Mapping fEditData;

	private Text fRemotePathText;
	private Button fWorkspacePathBtn;
	private Button fExternalPathBtn;
	private Text fWorkspacePathText;
	private Text fExternalPathText;
	private Button fWorkspacePathBrowseBtn;
	private Button fExternalPathBrowseBtn;
	private Button ignoreMappingBtn;
	private Text ignorePathText;

	private IDialogSettings fDialogSettings;

	public PathMapperEntryDialog(Shell parent) {
		this(parent, null);
	}

	public PathMapperEntryDialog(Shell parent, Mapping editData) {
		super(parent);
		if (editData != null) {
			fEditData = editData.clone();
		}

	}

	public Mapping getResult() {
		return fEditData;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);

		Composite mainComp = new Composite(parent, SWT.None);
		mainComp.setLayout(new GridLayout());
		mainComp.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Remote path text field:
		Label label = new Label(mainComp, SWT.NONE);
		label.setText(Messages.PathMapperEntryDialog_2);

		fRemotePathText = new Text(mainComp, SWT.BORDER);
		fRemotePathText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				ignorePathText.setText(fRemotePathText.getText());
				validate();
			}
		});
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		fRemotePathText.setLayoutData(layoutData);

		// Radio buttons group:
		Composite typeSelectionGroup = new Composite(mainComp, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.marginTop = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		typeSelectionGroup.setLayout(layout);
		typeSelectionGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label divider = new Label(typeSelectionGroup, SWT.HORIZONTAL | SWT.SEPARATOR);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 2;
		divider.setLayoutData(layoutData);

		// Workspace file:
		fWorkspacePathBtn = new Button(typeSelectionGroup, SWT.RADIO);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 2;
		fWorkspacePathBtn.setLayoutData(layoutData);
		fWorkspacePathBtn.setText(Messages.PathMapperEntryDialog_3);
		fWorkspacePathBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = fWorkspacePathBtn.getSelection();
				fWorkspacePathText.setEnabled(enabled);
				fWorkspacePathBrowseBtn.setEnabled(enabled);
				fExternalPathText.setEnabled(!enabled);
				fExternalPathBrowseBtn.setEnabled(!enabled);
				ignorePathText.setEnabled(!enabled);
				validate();
			}
		});

		fWorkspacePathText = new Text(typeSelectionGroup, SWT.BORDER);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		fWorkspacePathText.setLayoutData(layoutData);
		fWorkspacePathText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				fWorkspacePathText.setData(Type.WORKSPACE);
				validate();
			}
		});

		fWorkspacePathBrowseBtn = new Button(typeSelectionGroup, SWT.NONE);
		setButtonLayoutData(fWorkspacePathBrowseBtn);
		fWorkspacePathBrowseBtn.setText(Messages.PathMapperEntryDialog_4);
		fWorkspacePathBrowseBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WorkspaceBrowseDialog dialog = new WorkspaceBrowseDialog(getShell());
				if (dialog.open() == Window.OK) {
					Object selectedElement = dialog.getSelectedElement();
					fWorkspacePathText.setData(null);
					if (selectedElement instanceof IResource) {
						IResource resource = (IResource) selectedElement;
						fWorkspacePathText.setData(Type.WORKSPACE);
						fWorkspacePathText.setText(resource.getFullPath().toString());
					} else if (selectedElement instanceof IBuildpathEntry) {
						IBuildpathEntry includePathEntry = (IBuildpathEntry) selectedElement;
						fWorkspacePathText.setData(
								includePathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE ? Type.INCLUDE_VAR
										: Type.INCLUDE_FOLDER);
						if (includePathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
							IPath incPath = DLTKCore.getResolvedVariablePath(includePathEntry.getPath());
							if (incPath != null) {
								fWorkspacePathText.setText(incPath.toOSString());
							}
						} else {
							fWorkspacePathText.setText(
									EnvironmentPathUtils.getLocalPath(includePathEntry.getPath()).toOSString());
						}
					} else if (selectedElement instanceof IPFile) {
						IPFile ipFile = (IPFile) selectedElement;
						IBuildpathEntry includePathEntry = ipFile.includePathEntry;
						fWorkspacePathText.setData(
								includePathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE ? Type.INCLUDE_VAR
										: Type.INCLUDE_FOLDER);
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
		fExternalPathBtn.setText(Messages.PathMapperEntryDialog_5);
		fExternalPathBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = fExternalPathBtn.getSelection();
				fExternalPathText.setEnabled(enabled);
				fExternalPathBrowseBtn.setEnabled(enabled);
				fWorkspacePathText.setEnabled(!enabled);
				fWorkspacePathBrowseBtn.setEnabled(!enabled);
				ignorePathText.setEnabled(!enabled);
				validate();
			}
		});

		fExternalPathText = new Text(typeSelectionGroup, SWT.BORDER);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		fExternalPathText.setLayoutData(layoutData);
		fExternalPathText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		fExternalPathBrowseBtn = new Button(typeSelectionGroup, SWT.NONE);
		setButtonLayoutData(fExternalPathBrowseBtn);
		fExternalPathBrowseBtn.setText(Messages.PathMapperEntryDialog_4);
		fExternalPathBrowseBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setMessage("Select local folder"); //$NON-NLS-1$
				String path = dialog.open();
				if (path != null) {
					fExternalPathText.setText(path);
				}
			}
		});

		ignoreMappingBtn = new Button(typeSelectionGroup, SWT.RADIO);
		ignoreMappingBtn.setText(Messages.PathMapperEntryDialog_18);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 2;
		ignoreMappingBtn.setLayoutData(layoutData);
		ignoreMappingBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = ignoreMappingBtn.getSelection();
				fWorkspacePathText.setEnabled(!enabled);
				fWorkspacePathBrowseBtn.setEnabled(!enabled);
				fExternalPathText.setEnabled(!enabled);
				fExternalPathBrowseBtn.setEnabled(!enabled);
				ignorePathText.setEnabled(enabled);
				validate();
			}
		});

		ignorePathText = new Text(typeSelectionGroup, SWT.BORDER | SWT.READ_ONLY);
		ignorePathText.setEnabled(false);
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		ignorePathText.setLayoutData(layoutData);
		ignorePathText.setText(""); //$NON-NLS-1$

		applyDialogFont(mainComp);
		initializeValues();
		return parent;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);

		setTitleImage(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_WIZBAN_MAPPING_SERVER).createImage());
		if (fEditData != null) {
			getShell().setText(Messages.PathMapperEntryDialog_0);
			setTitle(Messages.PathMapperEntryDialog_6);
			setMessage(Messages.PathMapperEntryDialog_7);
		} else {
			getShell().setText(Messages.PathMapperEntryDialog_1);
			setTitle(Messages.PathMapperEntryDialog_8);
			setMessage(Messages.PathMapperEntryDialog_19);
		}
		// proper OK button state will be set with next validation
		disableOkButton();

		return control;
	}

	protected void initializeValues() {
		fWorkspacePathBtn.setSelection(true);
		fExternalPathBtn.setSelection(false);

		if (fEditData != null) {
			enableOkButton();
			fRemotePathText.setText(fEditData.remotePath.toString());

			if (fEditData.type == Type.SERVER) {
				fExternalPathBtn.setSelection(false);
				fWorkspacePathBtn.setSelection(false);
				ignoreMappingBtn.setSelection(true);
			} else if (fEditData.type == Type.EXTERNAL) {
				fExternalPathBtn.setSelection(true);
				fWorkspacePathBtn.setSelection(false);
				fExternalPathText.setText(fEditData.localPath.toString());
			} else {
				fWorkspacePathText.setData(fEditData.type);
				fWorkspacePathText.setText(fEditData.localPath.toString());
			}
		}
	}

	protected void setError(String error) {
		if (error == null) {
			enableOkButton();
		} else {
			disableOkButton();
		}
		setErrorMessage(error);
	}

	private void enableOkButton() {
		Button btn = getButton(IDialogConstants.OK_ID);
		if (btn != null) {
			btn.setEnabled(true);
		}
	}

	private void disableOkButton() {
		Button btn = getButton(IDialogConstants.OK_ID);
		if (btn != null) {
			btn.setEnabled(false);
		}
	}

	protected void validate() {
		Mapping mapping = new Mapping();
		mapping.source = MappingSource.USER;

		String remotePathStr = fRemotePathText.getText().trim();
		if (remotePathStr.length() == 0) {
			setError(Messages.PathMapperEntryDialog_9);
			return;
		}
		// Workaround bug 542652 - Path Mapping for PHP Server does not accept
		// UNC paths
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=542652
		if (!VirtualPath.isLocal(remotePathStr)) {
			setError(Messages.PathMapperEntryDialog_10);
			return;
		}
		try {
			mapping.remotePath = new VirtualPath(remotePathStr);
		} catch (IllegalArgumentException e) {
			setError(Messages.PathMapperEntryDialog_10);
			return;
		}

		// Workspace file:
		if (fWorkspacePathBtn.getSelection()) {
			String workspacePath = fWorkspacePathText.getText().trim();
			if (workspacePath.length() == 0) {
				setError(Messages.PathMapperEntryDialog_11);
				return;
			}

			// Workaround bug 542652 - Path Mapping for PHP Server does not
			// accept UNC paths
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=542652
			if (!VirtualPath.isLocal(workspacePath)) {
				setError(Messages.PathMapperEntryDialog_13);
				return;
			}
			try {
				mapping.localPath = new VirtualPath(workspacePath);
			} catch (IllegalArgumentException e) {
				setError(Messages.PathMapperEntryDialog_13);
				return;
			}
			boolean pathExistsInWorkspace = false;
			mapping.type = (Type) fWorkspacePathText.getData();
			if (mapping.type == Type.INCLUDE_FOLDER || mapping.type == Type.INCLUDE_VAR) {
				pathExistsInWorkspace = new File(mapping.localPath.toString()).exists();
			} else {
				pathExistsInWorkspace = (ResourcesPlugin.getWorkspace().getRoot()
						.findMember(mapping.localPath.toString()) != null);
			}
			if (!pathExistsInWorkspace) {
				setError(NLS.bind(Messages.PathMapperEntryDialog_12, workspacePath));
				return;
			}
		} else if (ignoreMappingBtn.getSelection()) {
			mapping.localPath = mapping.remotePath.clone();
			mapping.type = Type.SERVER;
		} else { // External file:
			String externalPath = fExternalPathText.getText().trim();
			if (externalPath.length() == 0) {
				setError(Messages.PathMapperEntryDialog_14);
				return;
			}

			// Workaround bug 542652 - Path Mapping for PHP Server does not
			// accept UNC paths
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=542652
			if (!VirtualPath.isLocal(externalPath)) {
				setError(Messages.PathMapperEntryDialog_16);
				return;
			}
			try {
				mapping.localPath = new VirtualPath(externalPath);
			} catch (IllegalArgumentException e) {
				setError(Messages.PathMapperEntryDialog_16);
				return;
			}
			mapping.type = Type.EXTERNAL;
			if (!new File(mapping.localPath.toString()).exists()) {
				setError(NLS.bind(Messages.PathMapperEntryDialog_15, externalPath));
				return;
			}
		}

		fEditData = mapping;

		setError(null);
	}

	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		if (fDialogSettings == null) {
			fDialogSettings = DialogSettings.getOrCreateSection(PHPDebugUIPlugin.getDefault().getDialogSettings(),
					this.getClass().getSimpleName());
		}
		return fDialogSettings;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.addControlListener(new ControlAdapter() {

			private int initialHeight = -1;

			@Override
			public void controlResized(ControlEvent e) {
				// http://stackoverflow.com/questions/4384691/preventing-a-swt-window-from-being-resized-horizontally
				Shell shell = getShell();
				Rectangle rect = shell.getBounds();
				if (initialHeight == -1) {
					initialHeight = rect.height;
					return;
				}

				if (rect.height != initialHeight) {
					shell.setBounds(rect.x, rect.y, rect.width, initialHeight);
				}
			}
		});
	}

	@Override
	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE);
	}

	class WorkspaceBrowseDialog extends StatusDialog {
		private TreeViewer fViewer;
		private Object selectedElement;

		public WorkspaceBrowseDialog(Shell parent) {
			super(parent);
			setTitle(Messages.PathMapperEntryDialog_17);
		}

		public Object getSelectedElement() {
			return selectedElement;
		}

		@Override
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
				@Override
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
			// TODO: buildpath entry selection
			if (element == null/* || element instanceof IncludeNode */) {
				updateStatus(new StatusInfo(IStatus.ERROR, "")); //$NON-NLS-1$
				return;
			}
			selectedElement = element;
			updateStatus(Status.OK_STATUS);
		}

		class IPFile {
			IBuildpathEntry includePathEntry;
			File file;

			IPFile(IBuildpathEntry includePathEntry, File file) {
				this.includePathEntry = includePathEntry;
				this.file = file;
			}

			@Override
			public int hashCode() {
				return file.hashCode() + 13 * includePathEntry.hashCode();
			}

			@Override
			public boolean equals(Object obj) {
				if (!(obj instanceof IPFile)) {
					return false;
				}
				IPFile other = (IPFile) obj;
				return other.file.equals(file) && other.includePathEntry.equals(includePathEntry);
			}
		}

		class ContentProvider implements ITreeContentProvider {

			@Override
			public Object[] getChildren(Object parentElement) {
				try {
					if (parentElement instanceof IContainer) {
						List<Object> r = new LinkedList<>();
						// Add all members:
						IContainer container = (IContainer) parentElement;
						IResource[] members = container.members();
						for (IResource member : members) {
							if (member instanceof IContainer && member.isAccessible()) {
								if (member instanceof IProject) { // show only
									// PHP
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
							IncludePath[] includePath = IncludePathManager.getInstance().getIncludePaths(project);
							for (IncludePath path : includePath) {
								if (path.isBuildpath()) {
									IBuildpathEntry buildpathEntry = (IBuildpathEntry) path.getEntry();
									if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY
											|| buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
										r.add(buildpathEntry);
									}
								}
							}
						}
						return r.toArray();
					} else if (parentElement instanceof IBuildpathEntry) {
						IBuildpathEntry includePathEntry = (IBuildpathEntry) parentElement;
						IPath path = EnvironmentPathUtils.getLocalPath(includePathEntry.getPath());
						File file = null;
						if (includePathEntry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
							file = path.toFile();
						} else if (includePathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
							path = DLTKCore.getResolvedVariablePath(path);
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
								@Override
								public boolean accept(File pathname) {
									return pathname.isDirectory();
								}
							});
							List<Object> r = new ArrayList<>(dirs.length);
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

			@Override
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

			@Override
			public boolean hasChildren(Object element) {
				return getChildren(element).length > 0;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		}

		class LabelProvider extends ScriptUILabelProvider {

			@Override
			public Image getImage(Object element) {
				if (element instanceof IBuildpathEntry) {
					IBuildpathEntry includePathEntry = (IBuildpathEntry) element;
					if (includePathEntry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
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

			@Override
			public String getText(Object element) {
				if (element instanceof IBuildpathEntry) {
					IBuildpathEntry includePathEntry = (IBuildpathEntry) element;
					return EnvironmentPathUtils.getLocalPath(includePathEntry.getPath()).toOSString();
				}
				if (element instanceof IPFile) {
					return ((IPFile) element).file.getName();
				}
				return super.getText(element);
			}
		}
	}
}
