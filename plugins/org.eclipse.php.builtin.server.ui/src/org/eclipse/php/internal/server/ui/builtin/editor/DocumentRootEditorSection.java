/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.builtin.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.server.core.builtin.IPHPServer;
import org.eclipse.php.internal.server.core.builtin.PHPServer;
import org.eclipse.php.internal.server.core.builtin.command.SetDocumentRootDirectoryCommand;
import org.eclipse.php.internal.server.core.builtin.command.SetTestEnvironmentCommand;
import org.eclipse.php.internal.server.ui.builtin.Messages;
import org.eclipse.php.internal.server.ui.builtin.PHPServerUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.wst.server.core.IPublishListener;
import org.eclipse.wst.server.core.IRuntime;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.util.PublishAdapter;
import org.eclipse.wst.server.ui.editor.ServerEditorSection;

public class DocumentRootEditorSection extends ServerEditorSection {
	protected Section section;
	protected PHPServer phpServer;

	protected Button serverDirMetadata;
	protected Button serverDirCustom;

	protected Text serverDir;
	protected Button serverDirBrowse;
	protected boolean updating;

	protected PropertyChangeListener listener;
	protected IPublishListener publishListener;
	protected IPath workspacePath;

	protected boolean allowRestrictedEditing;
	protected IPath tempDirPath;
	protected IPath installDirPath;

	// Avoid hardcoding this at some point
	private final static String METADATADIR = ".metadata"; //$NON-NLS-1$

	/**
	 * ServerGeneralEditorPart constructor comment.
	 */
	public DocumentRootEditorSection() {
	}

	/**
	 * Add listeners to detect undo changes and publishing of the server.
	 */
	protected void addChangeListeners() {
		listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (updating)
					return;
				updating = true;
				if (IPHPServer.PROPERTY_DOCUMENT_ROOT_DIR.equals(event.getPropertyName())) {
					updateServerDirButtons();
					updateServerDirFields();
					validate();
				}
				updating = false;
			}
		};
		server.addPropertyChangeListener(listener);

		publishListener = new PublishAdapter() {
			@Override
			public void publishFinished(IServer server2, IStatus status) {
				boolean flag = false;
				if (status.isOK() && server2.getModules().length == 0)
					flag = true;
				if (flag != allowRestrictedEditing) {
					allowRestrictedEditing = flag;
					// Update the state of the fields
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							boolean customServerDir = false;
							if (!DocumentRootEditorSection.this.serverDirCustom.isDisposed())
								customServerDir = DocumentRootEditorSection.this.serverDirCustom.getSelection();
							if (!DocumentRootEditorSection.this.serverDirMetadata.isDisposed())
								DocumentRootEditorSection.this.serverDirMetadata.setEnabled(allowRestrictedEditing);
							if (!DocumentRootEditorSection.this.serverDirCustom.isDisposed())
								DocumentRootEditorSection.this.serverDirCustom.setEnabled(allowRestrictedEditing);
							if (!DocumentRootEditorSection.this.serverDir.isDisposed())
								DocumentRootEditorSection.this.serverDir
										.setEnabled(allowRestrictedEditing && customServerDir);
							if (!DocumentRootEditorSection.this.serverDirBrowse.isDisposed())
								DocumentRootEditorSection.this.serverDirBrowse
										.setEnabled(allowRestrictedEditing && customServerDir);
						}
					});
				}
			}
		};
		server.getOriginal().addPublishListener(publishListener);
	}

	/**
	 * Creates the SWT controls for this workbench part.
	 *
	 * @param parent
	 *            the parent control
	 */
	@Override
	public void createSection(Composite parent) {
		super.createSection(parent);
		FormToolkit toolkit = getFormToolkit(parent.getDisplay());

		section = toolkit.createSection(parent, ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED
				| ExpandableComposite.TITLE_BAR | Section.DESCRIPTION | ExpandableComposite.FOCUS_TITLE);
		section.setText(Messages.serverEditorLocationsSection);
		section.setDescription(Messages.serverEditorLocationsDescription);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL));

		Composite composite = toolkit.createComposite(section);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginHeight = 5;
		layout.marginWidth = 10;
		layout.verticalSpacing = 5;
		layout.horizontalSpacing = 15;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL));
		IWorkbenchHelpSystem whs = PlatformUI.getWorkbench().getHelpSystem();
		// whs.setHelp(composite, ContextIds.SERVER_EDITOR);
		// whs.setHelp(section, ContextIds.SERVER_EDITOR);
		toolkit.paintBordersFor(composite);
		section.setClient(composite);

		serverDirMetadata = toolkit.createButton(composite, Messages.serverEditorServerDirMetadata, SWT.RADIO);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 3;
		serverDirMetadata.setLayoutData(data);
		serverDirMetadata.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (updating || !serverDirMetadata.getSelection())
					return;
				updating = true;
				execute(new SetTestEnvironmentCommand(phpServer));
				updateServerDirFields();
				updating = false;
				validate();
			}
		});

		serverDirCustom = toolkit.createButton(composite, Messages.serverEditorServerDirCustom, SWT.RADIO);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 3;
		serverDirCustom.setLayoutData(data);
		serverDirCustom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (updating || !serverDirCustom.getSelection())
					return;
				updating = true;
				execute(new SetTestEnvironmentCommand(phpServer));
				updateServerDirFields();
				updating = false;
				validate();
			}
		});

		// server directory
		Label label = createLabel(toolkit, composite, Messages.serverEditorServerDir);
		data = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		label.setLayoutData(data);

		serverDir = toolkit.createText(composite, null, SWT.SINGLE);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.widthHint = 75;
		serverDir.setLayoutData(data);
		serverDir.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (updating)
					return;
				updating = true;
				execute(new SetDocumentRootDirectoryCommand(phpServer, getServerDir()));
				updating = false;
				validate();
			}
		});

		serverDirBrowse = toolkit.createButton(composite, Messages.editorBrowse, SWT.PUSH);
		serverDirBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent se) {
				DirectoryDialog dialog = new DirectoryDialog(serverDir.getShell());
				dialog.setMessage(Messages.serverEditorBrowseDeployMessage);
				dialog.setFilterPath(serverDir.getText());
				String selectedDirectory = dialog.open();
				if (selectedDirectory != null && !selectedDirectory.equals(serverDir.getText())) {
					updating = true;
					// Make relative if relative to the workspace
					IPath path = new Path(selectedDirectory);
					if (workspacePath.isPrefixOf(path)) {
						int cnt = path.matchingFirstSegments(workspacePath);
						path = path.removeFirstSegments(cnt).setDevice(null);
						selectedDirectory = path.toOSString();
					}
					execute(new SetDocumentRootDirectoryCommand(phpServer, selectedDirectory));
					updateServerDirButtons();
					updateServerDirFields();
					updating = false;
					validate();
				}
			}
		});
		serverDirBrowse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		initialize();
	}

	protected Label createLabel(FormToolkit toolkit, Composite parent, String text) {
		Label label = toolkit.createLabel(parent, text);
		label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		return label;
	}

	/**
	 * @see ServerEditorSection#dispose()
	 */
	@Override
	public void dispose() {
		if (server != null) {
			server.removePropertyChangeListener(listener);
			if (server.getOriginal() != null)
				server.getOriginal().removePublishListener(publishListener);
		}
	}

	/**
	 * @see ServerEditorSection#init(IEditorSite, IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);

		// Cache workspace and default deploy paths
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		workspacePath = root.getLocation();

		if (server != null) {
			phpServer = (PHPServer) server.loadAdapter(PHPServer.class, null);
			addChangeListeners();
		}
		initialize();
	}

	/**
	 * Initialize the fields in this editor.
	 */
	protected void initialize() {
		if (serverDir == null || phpServer == null)
			return;
		updating = true;

		IRuntime runtime = server.getRuntime();
		if (runtime != null)
			installDirPath = runtime.getLocation();

		// determine if editing of locations is allowed
		allowRestrictedEditing = false;
		IPath basePath = phpServer.getRuntimeBaseDirectory();
		if (!readOnly) {
			// If server has not been published, or server is published with no
			// modules, allow editing
			// TODO Find better way to determine if server hasn't been published
			if ((basePath != null && !basePath.append("conf").toFile().exists()) //$NON-NLS-1$
					|| (server.getOriginal().getServerPublishState() == IServer.PUBLISH_STATE_NONE
							&& server.getOriginal().getModules().length == 0)) {
				allowRestrictedEditing = true;
			}
		}

		// Update server related fields
		updateServerDirButtons();
		updateServerDirFields();

		serverDirMetadata.setEnabled(allowRestrictedEditing);
		serverDirCustom.setEnabled(allowRestrictedEditing);

		updating = false;
		validate();
	}

	protected String getServerDir() {
		String dir = null;
		if (serverDir != null) {
			dir = serverDir.getText().trim();
			IPath path = new Path(dir);
			// Adjust if the temp dir is known and has been entered
			if (tempDirPath != null && tempDirPath.equals(path))
				dir = null;
			// If under the workspace, make relative
			else if (workspacePath.isPrefixOf(path)) {
				int cnt = path.matchingFirstSegments(workspacePath);
				path = path.removeFirstSegments(cnt).setDevice(null);
				dir = path.toOSString();
			}
		}
		return dir;
	}

	protected void updateServerDirButtons() {
		if (phpServer.getDocumentRootDirectory() == null) {
			IPath path = phpServer.getRuntimeBaseDirectory();
			if (path != null && path.equals(installDirPath)) {
				serverDirMetadata.setSelection(false);
				serverDirCustom.setSelection(false);
			} else {
				serverDirMetadata.setSelection(true);
				serverDirCustom.setSelection(false);
			}
		} else {
			serverDirCustom.setSelection(true);
			serverDirMetadata.setSelection(false);
		}
	}

	protected void updateServerDirFields() {
		updateServerDir();
		boolean customServerDir = serverDirCustom.getSelection();
		serverDir.setEnabled(allowRestrictedEditing && customServerDir);
		serverDirBrowse.setEnabled(allowRestrictedEditing && customServerDir);
	}

	protected void updateServerDir() {
		IPath path = phpServer.getRuntimeBaseDirectory();
		if (path == null)
			serverDir.setText(""); //$NON-NLS-1$
		else if (workspacePath.isPrefixOf(path)) {
			int cnt = path.matchingFirstSegments(workspacePath);
			path = path.removeFirstSegments(cnt).setDevice(null);
			serverDir.setText(path.toOSString());
			// cache the relative temp dir path if that is what we have
			if (tempDirPath == null) {
				if (phpServer.getDocumentRootDirectory() == null)
					tempDirPath = path;
			}
		} else
			serverDir.setText(path.toOSString());
	}

	/**
	 * @see ServerEditorSection#getSaveStatus()
	 */
	@Override
	public IStatus[] getSaveStatus() {
		if (phpServer != null) {
			// Check the instance directory
			String dir = phpServer.getDocumentRootDirectory();
			if (dir != null) {
				IPath path = new Path(dir);
				// Must not be the same as the workspace location
				if (dir.length() == 0 || workspacePath.equals(path)) {
					return new IStatus[] {
							new Status(IStatus.ERROR, PHPServerUIPlugin.PLUGIN_ID, Messages.errorServerDirIsRoot) };
				}
				// User specified value may not be under the ".metadata" folder
				// of the workspace
				else if (workspacePath.isPrefixOf(path)
						|| (!path.isAbsolute() && METADATADIR.equals(path.segment(0)))) {
					int cnt = path.matchingFirstSegments(workspacePath);
					if (METADATADIR.equals(path.segment(cnt))) {
						return new IStatus[] { new Status(IStatus.ERROR, PHPServerUIPlugin.PLUGIN_ID,
								NLS.bind(Messages.errorServerDirUnderRoot, METADATADIR)) };
					}
				} else if (path.equals(installDirPath))
					return new IStatus[] { new Status(IStatus.ERROR, PHPServerUIPlugin.PLUGIN_ID,
							NLS.bind(Messages.errorServerDirCustomNotInstall,
									NLS.bind(Messages.serverEditorServerDirInstall, "").trim())) }; //$NON-NLS-1$
			} else {
				IPath path = phpServer.getRuntimeBaseDirectory();
				// If non-custom instance dir is not the install and metadata
				// isn't the selection, return error
				if (!path.equals(installDirPath) && !serverDirMetadata.getSelection()) {
					return new IStatus[] { new Status(IStatus.ERROR, PHPServerUIPlugin.PLUGIN_ID,
							NLS.bind(Messages.errorServerDirCustomNotMetadata,
									NLS.bind(Messages.serverEditorServerDirMetadata, "").trim())) }; //$NON-NLS-1$
				}
			}

		}
		// use default implementation to return success
		return super.getSaveStatus();
	}

	protected void validate() {
		if (phpServer != null) {
			// Validate instance directory
			String dir = phpServer.getDocumentRootDirectory();
			if (dir != null) {
				IPath path = new Path(dir);
				// Must not be the same as the workspace location
				if (dir.length() == 0 || workspacePath.equals(path)) {
					setErrorMessage(Messages.errorServerDirIsRoot);
					return;
				}
				// User specified value may not be under the ".metadata" folder
				// of the workspace
				else if (workspacePath.isPrefixOf(path)
						|| (!path.isAbsolute() && METADATADIR.equals(path.segment(0)))) {
					int cnt = path.matchingFirstSegments(workspacePath);
					if (METADATADIR.equals(path.segment(cnt))) {
						setErrorMessage(NLS.bind(Messages.errorServerDirUnderRoot, METADATADIR));
						return;
					}
				} else if (path.equals(installDirPath)) {
					setErrorMessage(NLS.bind(Messages.errorServerDirCustomNotInstall,
							NLS.bind(Messages.serverEditorServerDirInstall, "").trim())); //$NON-NLS-1$
					return;
				}
			} else {
				IPath path = phpServer.getRuntimeBaseDirectory();
				// If non-custom instance dir is not the install and metadata
				// isn't the selection, return error
				if (path != null && !path.equals(installDirPath) && !serverDirMetadata.getSelection()) {
					setErrorMessage(NLS.bind(Messages.errorServerDirCustomNotMetadata,
							NLS.bind(Messages.serverEditorServerDirMetadata, "").trim())); //$NON-NLS-1$
				}
			}

		}
		// All is okay, clear any previous error
		setErrorMessage(null);
	}
}
