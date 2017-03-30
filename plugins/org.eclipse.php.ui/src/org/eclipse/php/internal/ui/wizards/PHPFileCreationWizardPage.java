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
package org.eclipse.php.internal.ui.wizards;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * This class allows for the creation of a PHP file.
 */
public class PHPFileCreationWizardPage extends WizardNewFileCreationPage {

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public PHPFileCreationWizardPage(final ISelection selection) {
		super("PHPWizardNewFileCreationPage", //$NON-NLS-1$
				selection instanceof IStructuredSelection ? (IStructuredSelection) selection : null);
		setTitle(PHPUIMessages.PHPFileCreationWizardPage_3);
		setDescription(PHPUIMessages.PHPFileCreationWizardPage_4);
		setImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_PHP_FILE);
	}

	private String computeDefaultFileName() {
		String fileName = addDefaultExtension(PHPUIMessages.PHPFileCreationWizardPage_8);
		IPath containerFullPath = getContainerFullPath();
		if (containerFullPath != null) {
			int count = 0;
			while (true) {
				IPath path = containerFullPath.append(fileName);
				if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
					count++;
					fileName = addDefaultExtension(PHPUIMessages.PHPFileCreationWizardPage_8 + count);
				} else {
					break;
				}
			}
		}
		return fileName;
	}

	private String addDefaultExtension(String fName) {
		return fName + ".php"; //$NON-NLS-1$
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		setFileName(computeDefaultFileName());

		setPageComplete(validatePage());
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IPHPHelpContextIds.CREATING_A_PHP_FILE_WITHIN_A_PROJECT);
		super.performHelp();
	}

	/**
	 * This method is overridden to set additional validation specific to html
	 * files.
	 */
	@Override
	protected boolean validatePage() {
		setMessage(null);
		setErrorMessage(null);

		if (!super.validatePage()) {
			return false;
		}
		IPath fullPath = getContainerFullPath();
		final String fileName = getFileName();

		if (fullPath == null) {
			setErrorMessage(PHPUIMessages.PHPFileCreationWizardPage_10);
			return false;
		}
		final IContainer containerFolder = getContainer(fullPath);
		if (containerFolder == null || !containerFolder.exists()) {
			setErrorMessage(PHPUIMessages.PHPFileCreationWizardPage_11);
			return false;
		}
		if (!containerFolder.getProject().isOpen()) {
			setErrorMessage(PHPUIMessages.PHPFileCreationWizardPage_12);
			return false;
		}
		if (fileName == null) {
			setErrorMessage(PHPUIMessages.PHPFileCreationWizardPage_15);
			return false;
		}
		if (!fileName.isEmpty() && containerFolder.getFile(new Path(fileName)).exists()) { // $NON-NLS-1$
			setErrorMessage(PHPUIMessages.PHPFileCreationWizardPage_14);
			return false;
		}

		int dotIndex = fileName.lastIndexOf('.');
		if (fileName.length() == 0 || dotIndex == 0) {
			setErrorMessage(PHPUIMessages.PHPFileCreationWizardPage_15);
			return false;
		}

		if (dotIndex != -1) {
			String fileNameWithoutExtention = fileName.substring(0, dotIndex);
			for (int i = 0; i < fileNameWithoutExtention.length(); i++) {
				char ch = fileNameWithoutExtention.charAt(i);
				if (!(Character.isJavaIdentifierPart(ch) || ch == '.' || ch == '-')) {
					setErrorMessage(PHPUIMessages.PHPFileCreationWizardPage_16);
					return false;
				}
			}
		}

		final IContentType contentType = Platform.getContentTypeManager()
				.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		if (!contentType.isAssociatedWith(fileName)) {
			// fixed bug 195274
			// get the extensions from content type
			final String[] fileExtensions = contentType.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
			StringBuilder buffer = new StringBuilder(PHPUIMessages.PHPFileCreationWizardPage_17);
			buffer.append(fileExtensions[0]);
			for (String extension : fileExtensions) {
				buffer.append(", ").append(extension); //$NON-NLS-1$
			}
			buffer.append("]"); //$NON-NLS-1$
			setErrorMessage(buffer.toString());
			return false;
		}

		return true;
	}

	protected IContainer getContainer(final IPath path) {

		final IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
		return resource instanceof IContainer ? (IContainer) resource : null;

	}

	public IProject getProject() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(getContainerFullPath());
		IProject project = null;
		if (resource instanceof IProject) {
			project = (IProject) resource;
		} else if (resource != null) {
			project = resource.getProject();
		}
		return project;
	}
}