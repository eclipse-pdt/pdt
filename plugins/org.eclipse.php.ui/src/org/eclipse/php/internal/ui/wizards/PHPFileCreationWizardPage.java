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
package org.eclipse.php.internal.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * This class allows for the creation of a PHP file.
 */
public class PHPFileCreationWizardPage extends WizardPage {

	protected Text containerText;
	protected Text fileText;
	private ISelection selection;
	protected IProject project;
	//	private Combo templatesCombo;
	//	private Combo encodingCombo;
	//	EncodingSettings encodingSettings;

	protected static final String UTF_8 = "UTF 8"; //$NON-NLS-1$
	protected static final String NO_TEMPLATE = "-- none -- "; //$NON-NLS-1$
	protected Label targetResourceLabel;

	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public PHPFileCreationWizardPage(final ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(PHPUIMessages.getString("PHPFileCreationWizardPage.3")); //$NON-NLS-1$
		setDescription(PHPUIMessages.getString("PHPFileCreationWizardPage.4")); //$NON-NLS-1$
		setImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_PHP_FILE);
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(PHPUIMessages.getString("PHPFileCreationWizardPage.5")); //$NON-NLS-1$

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 400;
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				dialogChanged();
			}
		});

		final Button button = new Button(container, SWT.PUSH);
		button.setText(PHPUIMessages.getString("PHPFileCreationWizardPage.6")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				handleBrowse();
			}
		});

		targetResourceLabel = new Label(container, SWT.NULL);
		targetResourceLabel.setText(PHPUIMessages.getString("PHPFileCreationWizardPage.7")); //$NON-NLS-1$

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileText.setFocus();
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		//gd.widthHint = 300;
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				dialogChanged();
			}
		});

		//		label = new Label(container, SWT.NULL);
		//		label.setText("Templates :");
		//
		//		templatesCombo = new Combo(container, SWT.READ_ONLY);
		//		templatesCombo.setItems(new String[] { NO_TEMPLATE });
		//		templatesCombo.setText(NO_TEMPLATE);
		//		gd = new GridData();
		//		gd.horizontalSpan = 2;
		//		gd.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
		//		templatesCombo.setLayoutData(gd);
		//		templatesCombo.addModifyListener(new ModifyListener() {
		//			public void modifyText(ModifyEvent e) {
		//				dialogChanged();
		//			}
		//		});

		//		label = new Label(container, SWT.NULL);
		//		label.setText("Encoding :");

		//		encodingSettings = new PhpEncodingSettings(container, "Encoding");
		//		gd = new GridData(GridData.FILL_HORIZONTAL);
		//		gd.horizontalSpan = 3;
		//		encodingSettings.setLayoutData(gd);
		//		encodingSettings.setEncoding();

		//		encodingCombo = new Combo(container, SWT.READ_ONLY);
		//		encodingCombo.setItems(new String[]{UTF_8});
		//		encodingCombo.setText(UTF_8);
		//		gd = new GridData();
		//		gd.horizontalSpan = 2;
		//		gd.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
		//		templatesCombo.setLayoutData(gd);
		//		templatesCombo.addModifyListener(new ModifyListener() {
		//			public void modifyText(ModifyEvent e) {
		//				dialogChanged();
		//			}
		//		});

		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable
	 * container to use.
	 */
	private void initialize() {
		if (selection != null && selection.isEmpty() == false && selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			IContainer container = null;
			if (obj instanceof PHPCodeData)
				obj = PHPModelUtil.getResource(obj);
			else if (obj instanceof PHPNature)
				obj = ((PHPNature) obj).getProject();
			else if (obj instanceof PHPProjectModel)
				obj = PHPWorkspaceModelManager.getInstance().getProjectForModel((PHPProjectModel) obj);

			if (obj instanceof IResource) {
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();

			} else if (obj instanceof PHPProjectModel)
				container = PHPWorkspaceModelManager.getInstance().getProjectForModel((PHPProjectModel) obj);

			if (container != null) {
				containerText.setText(container.getFullPath().toString());
				this.project = container.getProject();
			}
			
			//				IProject project = container.getProject();
			//				PHPProjectOptions options = PHPProjectOptions.forProject(project);
			//				if (options != null) {
			//					String defaultEncoding = (String) options.getOption(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING);
			//					encodingSettings.setIANATag(defaultEncoding);
			//				}
		}
		setInitialFileName(PHPUIMessages.getString("PHPFileCreationWizardPage.8")); //$NON-NLS-1$
	}

	protected void setInitialFileName(final String fileName) {
		fileText.setText(fileName);
		// fixed bug 157145 - highlight the newfile word in the file name input
		fileText.setSelection(0, 7);
	}

	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */

	private void handleBrowse() {
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, PHPUIMessages.getString("PHPFileCreationWizardPage.9")); //$NON-NLS-1$
		dialog.showClosedProjects(false);
		if (dialog.open() == Window.OK) {
			final Object[] result = dialog.getResult();
			if (result.length == 1)
				containerText.setText(((Path) result[0]).toOSString());
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */
	protected void dialogChanged() {
		final String container = getContainerName();
		final String fileName = getFileName();

		if (container.length() == 0) {
			updateStatus(PHPUIMessages.getString("PHPFileCreationWizardPage.10")); //$NON-NLS-1$
			return;
		}
		final IContainer containerFolder = getContainer(container);
		if (containerFolder == null || !containerFolder.exists()) {
			updateStatus(PHPUIMessages.getString("PHPFileCreationWizardPage.11")); //$NON-NLS-1$
			return;
		}
		if (!containerFolder.getProject().isOpen()) {
			updateStatus(PHPUIMessages.getString("PHPFileCreationWizardPage.12")); //$NON-NLS-1$
			return;
		}
		if (fileName != null && !fileName.equals("") && containerFolder.getFile(new Path(fileName)).exists()) { //$NON-NLS-1$
			updateStatus(PHPUIMessages.getString("PHPFileCreationWizardPage.14")); //$NON-NLS-1$
			return;
		}

		int dotIndex = fileName.lastIndexOf('.');
		if (fileName.length() == 0 || dotIndex == 0) {
			updateStatus(PHPUIMessages.getString("PHPFileCreationWizardPage.15")); //$NON-NLS-1$
			return;
		}

		if (dotIndex != -1) {
			String fileNameWithoutExtention = fileName.substring(0, dotIndex);
			for (int i = 0; i < fileNameWithoutExtention.length(); i++) {
				char ch = fileNameWithoutExtention.charAt(i);
				if (!(Character.isJavaIdentifierPart(ch) || ch == '.' || ch == '-')) {
					updateStatus(PHPUIMessages.getString("PHPFileCreationWizardPage.16")); //$NON-NLS-1$
					return;
				}
			}
		}

		final IContentType contentType = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		if (!contentType.isAssociatedWith(fileName)) {
			// fixed bug 195274
			// get the extensions from content type
			final String[] fileExtensions = contentType.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
			StringBuffer buffer = new StringBuffer(PHPUIMessages.getString("PHPFileCreationWizardPage.17")); //$NON-NLS-1$
			buffer.append(fileExtensions[0]);
			for (String extension : fileExtensions) {
				buffer.append(", ").append(extension); //$NON-NLS-1$
			}
			buffer.append("]"); //$NON-NLS-1$
			updateStatus(buffer.toString());
			return;
		}

		updateStatus(null);
	}

	protected IContainer getContainer(final String text) {
		final Path path = new Path(text);

		final IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
		return resource instanceof IContainer ? (IContainer) resource : null;

	}

	protected void updateStatus(final String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}
	
	public void setContainerName(String containerPath) {
		containerText.setText(containerPath);
	}

	public String getFileName() {
		return fileText.getText();
	}
}