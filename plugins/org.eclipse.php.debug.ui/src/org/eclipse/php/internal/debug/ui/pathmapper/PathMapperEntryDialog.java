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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.debug.core.pathmapper.AbstractPath;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping;
import org.eclipse.php.internal.ui.explorer.ExplorerContentProvider;
import org.eclipse.php.internal.ui.explorer.PHPTreeViewer;
import org.eclipse.php.internal.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.internal.ui.util.PHPElementImageProvider;
import org.eclipse.php.internal.ui.util.PHPElementLabels;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
		this (parent, null);
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
				dialog.open();
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
			Object data = fWorkspacePathText.getData();

			if (data instanceof IIncludePathEntry) {
				IIncludePathEntry includePathEntry = (IIncludePathEntry) data;
				if (includePathEntry.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
					mapping.type = Type.INCLUDE_VAR;
					IPath path = IncludePathVariableManager.instance().resolveVariablePath(workspacePath);
					pathExistsInWorkspace = path.toFile().exists();
				} else {
					mapping.type = Type.INCLUDE_FOLDER;
					pathExistsInWorkspace = new File(workspacePath).exists();
				}
			} else {
				mapping.type = Type.WORKSPACE;
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

		updateStatus(StatusInfo.OK_STATUS);
	}

	class WorkspaceBrowseDialog extends StatusDialog {
		private PHPTreeViewer fViewer;

		public WorkspaceBrowseDialog(Shell parent) {
			super(parent);
			setTitle("Select Workspace Resource");
		}

		protected Control createDialogArea(Composite parent) {
			parent = (Composite) super.createDialogArea(parent);
			parent.setLayoutData(new GridData(GridData.FILL_BOTH));

			PixelConverter pixelConverter = new PixelConverter(parent);

			fViewer = new PHPTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			GridData layoutData = new GridData(GridData.FILL_BOTH);
			layoutData.widthHint = pixelConverter.convertWidthInCharsToPixels(50);
			layoutData.heightHint = pixelConverter.convertHeightInCharsToPixels(20);
			fViewer.getControl().setLayoutData(layoutData);

			ExplorerContentProvider contentProvider = new ExplorerContentProvider(null, false);
			fViewer.setContentProvider(contentProvider);

			AppearanceAwareLabelProvider labelProvider = new AppearanceAwareLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS | PHPElementLabels.P_COMPRESSED, AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS | PHPElementImageProvider.SMALL_ICONS | PHPElementImageProvider.OVERLAY_ICONS);
			fViewer.setLabelProvider(labelProvider);

			fViewer.setInput(PHPWorkspaceModelManager.getInstance());

			return parent;
		}

		protected void validate() {
		}
	}
}
