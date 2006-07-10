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
package org.eclipse.php.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.php.ui.util.PHPPluginImages;
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
	
	private Text containerText;
	private Text fileText;
	private ISelection selection;
//	private Combo templatesCombo;
	//	private Combo encodingCombo;
//	EncodingSettings encodingSettings;
	
	protected static final String UTF_8 = "UTF 8";
	protected static final String NO_TEMPLATE = "-- none -- ";

	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public PHPFileCreationWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("New PHP file");
		setDescription("Create a new PHP file");
		setImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_PHP_FILE);
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("Source Folder");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 400;
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("File Name");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		//gd.widthHint = 300;
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
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
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			IContainer container = null;
			if (obj instanceof PHPCodeData)
				obj=PHPModelUtil.getResource(obj);
			else if (obj instanceof PHPNature) {
				obj = ((PHPNature) obj).getProject();
			} else if (obj instanceof PHPProjectModel) {
				obj = PHPWorkspaceModelManager.getInstance().getProjectForModel((PHPProjectModel) obj);
			}

			
			if (obj instanceof IResource) {
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();

			} else if (obj instanceof PHPProjectModel) {
				container = PHPWorkspaceModelManager.getInstance().getProjectForModel((PHPProjectModel) obj);
			}

			if (container != null) {
				containerText.setText(container.getFullPath().toString());
//				IProject project = container.getProject();
//				PHPProjectOptions options = PHPProjectOptions.forProject(project);
//				if (options != null) {
//					String defaultEncoding = (String) options.getOption(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING);
//					encodingSettings.setIANATag(defaultEncoding);
//				}
			}
		}
		setInitialFileName("newfile.php");
	}

	protected void setInitialFileName(String fileName) {
		fileText.setText(fileName);
	}
	
	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, "Select New File Folder");
		dialog.showClosedProjects(false);
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toOSString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		String container = getContainerName();
		String fileName = getFileName();

		if (container.length() == 0) {
			updateStatus("Folder must be specified");
			return;
		}
		IResource resource = null;

		IContainer containerFolder = getContainer(container);
		if (containerFolder==null || !containerFolder.exists()) {
			updateStatus("Selected folder does not exist");
			return;
		}
		if (!containerFolder.getProject().isOpen()) {
			updateStatus("Selected folder is in closed project");
			return;
		}
		if (fileName != null && !fileName.equals("") && containerFolder.getFile(new Path(fileName)).exists()) {
			updateStatus("Specified file already exists");
			return;
		}
		
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		IContentType contentType = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		if (!contentType.isAssociatedWith(fileName)) {
			updateStatus("File extension must be \"php\"");
			return;
		}

		updateStatus(null);
	}

	IContainer getContainer(String text ) {
		Path path = new Path(text);
 
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
			return (resource instanceof IContainer)? (IContainer)resource:null;
 
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFileName() {
		return fileText.getText();
	}
}