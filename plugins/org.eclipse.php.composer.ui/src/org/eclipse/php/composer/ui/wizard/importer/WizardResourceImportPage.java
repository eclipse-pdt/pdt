/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard.importer;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.internal.resources.ProjectDescription;
import org.eclipse.core.internal.resources.ProjectDescriptionReader;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.composer.api.ComposerConstants;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.json.ParseException;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.wizard.ValidationException;
import org.eclipse.php.composer.ui.wizard.ValidationException.Severity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardDataTransferPage;

/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class WizardResourceImportPage extends WizardDataTransferPage {

	private String sourceFromDialog;
	private String source;
	private String target;
	private String projectName;
	private StringButtonDialogField sourcePath;
	private IPath json;
	private ComposerPackage composerPackage;
	private StringDialogField projectNameField;
	private IWorkspace workspace;
	private StringButtonDialogField targetPath;
	private Button useWorkspaceLocation;
	private static final String defaultMessage = Messages.WizardResourceImportPage_Description;

	private boolean useWorkspace = true;

	public WizardResourceImportPage(IWorkbench aWorkbench, IStructuredSelection selection, String[] strings) {
		super(Messages.WizardResourceImportPage_Name);
		setTitle(Messages.WizardResourceImportPage_Title);
		setDescription(defaultMessage);
	}

	@Override
	public void handleEvent(Event event) {

	}

	public String getProjectName() {
		return projectName;
	}

	public String getSourcePath() {
		return source;
	}

	public String getTargetPath() {
		return target;
	}

	public boolean doUseWorkspace() {
		return useWorkspace;
	}

	@Override
	public void createControl(Composite parent) {

		Composite control = new Composite(parent, SWT.NONE);

		int numColumns = 3;
		GridLayoutFactory.fillDefaults().numColumns(numColumns).applyTo(control);

		projectNameField = new StringDialogField();
		projectNameField.setLabelText(Messages.WizardResourceImportPage_ProjectNameLabel);
		projectNameField.doFillIntoGrid(control, numColumns);
		LayoutUtil.setHorizontalGrabbing(projectNameField.getTextControl(null));

		projectNameField.setDialogFieldListener(field -> {
			projectName = projectNameField.getText();
			updatePageCompletion();
		});

		sourcePath = new StringButtonDialogField(field -> {
			DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
			dialog.setMessage(Messages.WizardResourceImportPage_BrowseDialogMessage);
			sourceFromDialog = dialog.open();
			try {
				handleSourcePathChange();
			} catch (Exception e) {
				Logger.logException(e);
			}
		});

		sourcePath.setLabelText(Messages.WizardResourceImportPage_SourcePathLabel);
		sourcePath.setButtonLabel(Messages.WizardResourceImportPage_BrowseButton);
		sourcePath.doFillIntoGrid(control, numColumns);
		sourcePath.getTextControl(null).setEnabled(false);

		useWorkspaceLocation = new Button(control, SWT.CHECK);
		useWorkspaceLocation.setText(Messages.WizardResourceImportPage_WorkspaceLocationCheckbox);
		useWorkspaceLocation.setSelection(true);

		GridDataFactory.fillDefaults().grab(true, false).span(3, 1).applyTo(useWorkspaceLocation);

		useWorkspaceLocation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				targetPath.setEnabled(!useWorkspaceLocation.getSelection());
				useWorkspace = useWorkspaceLocation.getSelection();
				updatePageCompletion();
			}
		});

		// don't know if we can use this in the future. for now, simply use the
		// source location as the project location
		useWorkspaceLocation.setVisible(false);

		LayoutUtil.setHorizontalGrabbing(sourcePath.getTextControl(null));
		setControl(control);

		workspace = ResourcesPlugin.getWorkspace();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control,
				ComposerUIPlugin.PLUGIN_ID + "." + "help_context_wizard_importer"); //$NON-NLS-1$ //$NON-NLS-2$
		setPageComplete(false);
	}

	protected void handleTargetPathChange() throws IOException {
		updatePageCompletion();
	}

	protected void handleSourcePathChange() throws IOException, ParseException {

		if (sourceFromDialog != null) {
			source = sourceFromDialog;
			sourcePath.setText(source);
			json = new Path(source).append(ComposerConstants.COMPOSER_JSON);

			if (json != null && json.toFile().exists()) {
				composerPackage = new ComposerPackage(json.toFile());
			} else {
				composerPackage = null;
			}
			setProjectNameFromJson();
		}

		updatePageCompletion();
	}

	protected void setProjectNameFromJson() {

		if (projectNameField.getText().length() > 0 || composerPackage == null) {
			return;
		}

		String name = composerPackage.getName();
		if (name != null) {
			if (name.contains("/")) { //$NON-NLS-1$
				String[] split = name.split("/"); //$NON-NLS-1$
				if (split.length == 2) {
					name = split[1];
				}
			}

			try {
				validateProjectName(name);
			} catch (ValidationException e) {
				setErrorMessage(e.getMessage());
				updatePageCompletion();
				return;
			}
			projectNameField.setText(name);
		}
	}

	protected void validateProjectName(String name) throws ValidationException {

		IProject project = workspace.getRoot().getProject(name);
		if (project != null && project.exists()) {
			throw new ValidationException(Messages.WizardResourceImportPage_ProjectAlreadyExistsError, Severity.ERROR);
		}
	}

	@Override
	protected boolean validateSourceGroup() {

		projectNameField.getTextControl(null).setEnabled(true);

		if (source == null || json == null || !json.toFile().exists()) {
			setErrorMessage(Messages.WizardResourceImportPage_NoComposerJsonError);
			return false;
		}

		IPath sourceProject = new Path(source).append(".project"); //$NON-NLS-1$

		if (sourceProject.toFile().exists()) {
			try {
				ProjectDescriptionReader reader = new ProjectDescriptionReader(ResourcesPlugin.getWorkspace());
				ProjectDescription projectDescription = reader.read(sourceProject);

				if (projectDescription == null) {
					setErrorMessage(Messages.WizardResourceImportPage_CannotReadProjectError);
					return false;
				}

				projectName = projectDescription.getName();
				projectNameField.setTextWithoutUpdate(projectName);
				projectNameField.getTextControl(null).setEnabled(false);

				setMessage(Messages.WizardResourceImportPage_EclipseProjectAvailableMessage);
				return true;

			} catch (IOException e) {
				Logger.logException(e);
				setErrorMessage(Messages.WizardResourceImportPage_ErrorReadingProject);
				return false;
			}
		}

		projectNameField.getTextControl(null).setEnabled(true);

		setMessage(defaultMessage);

		return true;
	}

	@Override
	protected boolean validateDestinationGroup() {

		if (projectName == null || projectName.length() == 0) {
			setErrorMessage(Messages.WizardResourceImportPage_18);
			return false;
		}

		try {
			validateProjectName(projectName);
		} catch (ValidationException e) {
			setErrorMessage(e.getMessage());
			return false;
		}

		if (useWorkspaceLocation.getSelection() == true) {
			return true;
		}

		if (target == null) {
			setMessage(Messages.WizardResourceImportPage_19);
			return false;
		}

		IPath targetPath = new Path(target);
		File file = targetPath.toFile();

		if (file == null || !file.exists() || !file.isDirectory()) {
			setErrorMessage(Messages.WizardResourceImportPage_20);
			return false;
		}

		IPath targetProjectPath = targetPath.append(projectName);

		File targetProject = targetProjectPath.toFile();

		if (targetProject == null || targetProject.exists()) {
			setErrorMessage(Messages.WizardResourceImportPage_21);
			return false;
		}

		setMessage(defaultMessage);

		return true;
	}

	@Override
	protected boolean allowNewContainerName() {
		return false;
	}

}
