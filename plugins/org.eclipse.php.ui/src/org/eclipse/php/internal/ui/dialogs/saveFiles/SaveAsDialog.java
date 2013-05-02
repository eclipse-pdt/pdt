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
package org.eclipse.php.internal.ui.dialogs.saveFiles;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.wizards.PHPProjectCreationWizardProxy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IDEInternalWorkbenchImages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;

/**
 * A "Save As" dialog which solicits a path from the user. The
 * <code>getResult</code> method returns the path. Note that the folder at the
 * specified path might not exist and might need to be created. This dialog is
 * also able to save a file in a new PHP project
 * 
 * @see org.eclipse.ui.dialogs.SaveAsDialog
 */
public class SaveAsDialog extends TitleAreaDialog {

	private static final String DIALOG_SETTINGS_SECTION = "SaveAsDialogSettings"; //$NON-NLS-1$

	private static final int NEW_PROJ_ID = IDialogConstants.CLIENT_ID + 1;

	private IFile originalFile = null;

	private String originalName = null;

	private IPath result;

	// widgets
	private ResourceAndContainerGroup resourceGroup;

	private Button okButton;

	private Button newProjectButton;

	/**
	 * Image for title area
	 */
	private Image dlgTitleImage = null;

	private IWorkbenchWizard newProjectWizard;

	/**
	 * Creates a new Save As dialog for no specific file.
	 * 
	 * @param parentShell
	 *            the parent shell
	 */
	public SaveAsDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/*
	 * (non-Javadoc) Method declared in Window.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(""); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(shell, IIDEHelpContextIds.SAVE_AS_DIALOG);
	}

	/*
	 * (non-Javadoc) Method declared in Window.
	 */
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);

		initializeControls();
		validatePage();
		resourceGroup.setFocus();
		setTitle(""); //$NON-NLS-1$
		dlgTitleImage = IDEInternalWorkbenchImages.getImageDescriptor(
				IDEInternalWorkbenchImages.IMG_DLGBAN_SAVEAS_DLG).createImage();
		setTitleImage(PHPUiPlugin.getImageDescriptorRegistry().get(
				PHPPluginImages.DESC_WIZBAN_ADD_PHP_FILE));
		setMessage(""); //$NON-NLS-1$

		return contents;
	}

	/**
	 * The <code>SaveAsDialog</code> implementation of this <code>Window</code>
	 * method disposes of the banner image when the dialog is closed.
	 */
	public boolean close() {
		if (dlgTitleImage != null) {
			dlgTitleImage.dispose();
		}
		return super.close();
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout parentLayout = (GridLayout) parent.getLayout();
		parentLayout.makeColumnsEqualWidth = false;

		newProjectButton = createButton(parent, NEW_PROJ_ID,
				PHPUIMessages.SaveAsDialog_createNewProject, false); 
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected Control createDialogArea(Composite parent) {
		// top level composite
		Composite parentComposite = (Composite) super.createDialogArea(parent);

		// create a composite with standard margins and spacing
		Composite composite = new Composite(parentComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parentComposite.getFont());

		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				setDialogComplete(validatePage());
			}
		};

		resourceGroup = new ResourceAndContainerGroup(composite, listener,
				"", ""); //$NON-NLS-1$ //$NON-NLS-2$
		resourceGroup.setAllowExistingResources(true);

		return parentComposite;
	}

	/**
	 * Returns the full path entered by the user.
	 * <p>
	 * Note that the file and container might not exist and would need to be
	 * created. See the <code>IFile.create</code> method and the
	 * <code>ContainerGenerator</code> class.
	 * </p>
	 * 
	 * @return the path, or <code>null</code> if Cancel was pressed
	 */
	public IPath getResult() {
		return result;
	}

	/**
	 * Initializes the controls of this dialog.
	 */
	private void initializeControls() {
		if (originalFile != null) {
			resourceGroup.setContainerFullPath(originalFile.getParent()
					.getFullPath());
			resourceGroup.setResource(originalFile.getName());
		} else if (originalName != null) {
			resourceGroup.setResource(originalName);
		}
		setDialogComplete(validatePage());
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected void okPressed() {
		IPath path = resourceGroup.getContainerFullPath().append(
				resourceGroup.getResource());

		// If the user does not supply a file extension and if the save
		// as dialog was provided a default file name append the extension
		// of the default filename to the new name
		if (path.getFileExtension() == null) {
			if (originalFile != null && originalFile.getFileExtension() != null) {
				path = path.addFileExtension(originalFile.getFileExtension());
			} else if (originalName != null) {
				int pos = originalName.lastIndexOf('.');
				if (++pos > 0 && pos < originalName.length()) {
					path = path.addFileExtension(originalName.substring(pos));
				}
			}
		}

		// If the path already exists then confirm overwrite.
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		if (file.exists()) {
			String[] buttons = new String[] { IDialogConstants.YES_LABEL,
					IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };
			String question = PHPUIMessages.SaveAsDialog_6 + file.getFullPath().toString() + PHPUIMessages.SaveAsDialog_7; 
			MessageDialog d = new MessageDialog(getShell(),
					PHPUIMessages.SaveAsDialog_saveFileMessage, null, question,
					MessageDialog.QUESTION, buttons, 0); 
			int overwrite = d.open();
			switch (overwrite) {
			case 0: // Yes
				break;
			case 1: // No
				return;
			case 2: // Cancel
			default:
				cancelPressed();
				return;
			}
		}

		// Store path and close.
		result = path;
		close();
	}

	/**
	 * Sets the completion state of this dialog and adjusts the enable state of
	 * the Ok button accordingly.
	 * 
	 * @param value
	 *            <code>true</code> if this dialog is compelete, and
	 *            <code>false</code> otherwise
	 */
	protected void setDialogComplete(boolean value) {
		okButton.setEnabled(value);
	}

	/**
	 * Sets the original file to use.
	 * 
	 * @param originalFile
	 *            the original file
	 */
	public void setOriginalFile(IFile originalFile) {
		this.originalFile = originalFile;
	}

	/**
	 * Set the original file name to use. Used instead of
	 * <code>setOriginalFile</code> when the original resource is not an IFile.
	 * Must be called before <code>create</code>.
	 * 
	 * @param originalName
	 *            default file name
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	/**
	 * Returns whether this page's visual components all contain valid values.
	 * 
	 * @return <code>true</code> if valid, and <code>false</code> otherwise
	 */
	private boolean validatePage() {
		if (!resourceGroup.areAllValuesValid()) {
			if (!resourceGroup.getResource().equals("")) { //$NON-NLS-1$
				setErrorMessage(resourceGroup.getProblemMessage());
			} else {
				setErrorMessage(null);
			}
			return false;
		}

		String resourceName = resourceGroup.getResource();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// Do not allow a closed project to be selected
		IPath fullPath = resourceGroup.getContainerFullPath();
		if (fullPath != null) {
			String projectName = fullPath.segment(0);
			IStatus isValidProjectName = workspace.validateName(projectName,
					IResource.PROJECT);
			if (isValidProjectName.isOK()) {
				IProject project = workspace.getRoot().getProject(projectName);
				if (!project.isOpen()) {
					setErrorMessage(""); //$NON-NLS-1$
					return false;
				}
			}
		}

		IStatus result = workspace.validateName(resourceName, IResource.FILE);
		if (!result.isOK()) {
			setErrorMessage(result.getMessage());
			return false;
		}

		setErrorMessage(null);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Dialog#getDialogBoundsSettings()
	 * 
	 * @since 3.2
	 */
	protected IDialogSettings getDialogBoundsSettings() {
		IDialogSettings settings = IDEWorkbenchPlugin.getDefault()
				.getDialogSettings();
		IDialogSettings section = settings.getSection(DIALOG_SETTINGS_SECTION);
		if (section == null) {
			section = settings.addNewSection(DIALOG_SETTINGS_SECTION);
		}
		return section;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			okPressed();
		} else if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		} else if (NEW_PROJ_ID == buttonId) {
			newProjectWizard = PHPProjectCreationWizardProxy.getProjectWizard();
			newProjectWizard.init(PlatformUI.getWorkbench(),
					StructuredSelection.EMPTY);
			WizardDialog dialog = new WizardDialog(getShell(), newProjectWizard);
			if (dialog.open() == Window.OK) {
				resourceGroup.refresh();
			}
		}
	}
}