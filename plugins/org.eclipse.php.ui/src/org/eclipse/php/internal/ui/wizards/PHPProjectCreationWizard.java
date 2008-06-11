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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.wizards.operations.PHPCreationDataModelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard;
import org.eclipse.wst.common.frameworks.internal.operations.IProjectCreationPropertiesNew;
import org.osgi.service.prefs.BackingStoreException;

public class PHPProjectCreationWizard extends DataModelWizard implements IExecutableExtension, INewWizard {

	private static final String ID = "org.eclipse.php.ui.wizards.PHPProjectCreationWizard"; //$NON-NLS-1$

	protected PHPIncludePathPage includePathPage;
	protected PHPProjectWizardBasePage basePage;

	protected final ArrayList wizardPagesList = new ArrayList();
	private IProject createdProject = null;
	protected IConfigurationElement configElement;
	private List /** WizardPageFactory */
	wizardPageFactories = new ArrayList();

	public PHPProjectCreationWizard(IDataModel model) {
		super(model);
		populateWizardFactoryList();
	}

	public PHPProjectCreationWizard() {
		super();
		populateWizardFactoryList();
	}

	/**
	 * This operation is called after the Wizard  is created (and before the doAddPages)
	 * and it is used to define all the properties that the wizard pages will set
	 * (not necessarly store as properties).
	 * All the wizard pages may access these properties.
	 */
	protected IDataModelProvider getDefaultProvider() {
		return new PHPCreationDataModelProvider(wizardPageFactories);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void doAddPages() {
		// if we succeeded adding the default pages, add the contributed pages
		if (addDeafaultPages()) {
			addContributedPages();
		}

	}

	protected void addContributedPages() {
		// generates the pages added trough the phpWizardPages extention point
		// and add them to the wizard
		for (Iterator iter = wizardPageFactories.iterator(); iter.hasNext();) {
			WizardPageFactory pageFactory = (WizardPageFactory) iter.next();
			IWizardPage currentPage = pageFactory.createPage(getDataModel());
			addPage(currentPage);
		}
	}

	protected boolean addDeafaultPages() {
		addPage(basePage = new PHPProjectWizardBasePage(getDataModel(), "page1")); //$NON-NLS-1$
		addPage(includePathPage = new PHPIncludePathPage(getDataModel(), "page2")); //$NON-NLS-1$
		return true;
	}

	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		configElement = config;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(PHPUIMessages.getString("PHPProjectCreationWizard_PageTile")); //$NON-NLS-1$
		setDefaultPageImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_PHP_PROJECT);
	}

	protected boolean prePerformFinish() {
		createdProject = (IProject) getDataModel().getProperty(IProjectCreationPropertiesNew.PROJECT);
		
		String location = (String) getDataModel().getProperty(IProjectCreationPropertiesNew.USER_DEFINED_LOCATION);
		File projectLocation = new File(location);
		if (projectLocation.exists() && projectLocation.isDirectory() && projectLocation.listFiles().length != 0) {
			LocationVerificationDialog dialog = new LocationVerificationDialog(getShell(), (String)getDataModel().getProperty(IProjectCreationPropertiesNew.PROJECT_NAME), location);
			dialog.open();
			if(dialog.getCreateInNewLocation()){
				getDataModel().setProperty(IProjectCreationPropertiesNew.USER_DEFINED_LOCATION, location + System.getProperty("file.separator") + createdProject.getName()); //$NON-NLS-1$
			}
		}

		getDataModel().setProperty(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, includePathPage.getIncludePathsBlock().getRawBuildPath());
		basePage.setProjectOptionInModel(getDataModel());

		return super.prePerformFinish();
	}

	static class LocationVerificationDialog extends MessageDialog {

		private String projectName;
		private String location;

		private boolean createInNewLocation = true;

		private Button radio1;

		private Button radio2;

		LocationVerificationDialog(Shell parentShell, String project, String location) {
			super(parentShell, PHPUIMessages.getString("PHPProjectCreationWizard.title"), null, NLS.bind(PHPUIMessages.getString("PHPProjectCreationWizard.message"),location), MessageDialog.QUESTION, new String[] { IDialogConstants.OK_LABEL}, 0); //$NON-NLS-1$ //$NON-NLS-2$
			this.projectName = project;
			this.location = location;
		}

		
		/*
		 * (non-Javadoc) Method declared on Window.
		 */
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, IIDEHelpContextIds.DELETE_PROJECT_DIALOG);
		}

		protected Control createCustomArea(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);
			composite.setLayout(new GridLayout());
			radio1 = new Button(composite, SWT.RADIO);
			radio1.addSelectionListener(selectionListener);

			radio1.setText(PHPUIMessages.getString("PHPProjectCreationWizard.createProjectIn") + location + "."); //$NON-NLS-1$ //$NON-NLS-2$
			radio1.setFont(parent.getFont());

			// Add explanatory label that the action cannot be undone.
			// We can't put multi-line formatted text in a radio button,
			// so we have to create a separate label.
			Label detailsLabel = new Label(composite, SWT.LEFT);
			detailsLabel.setText(PHPUIMessages.getString("PHPProjectCreationWizard.details") + location + PHPUIMessages.getString("PHPProjectCreationWizard.folder")); //$NON-NLS-1$ //$NON-NLS-2$
			detailsLabel.setFont(parent.getFont());
			// indent the explanatory label
			GC gc = new GC(detailsLabel);
			gc.setFont(detailsLabel.getParent().getFont());
			FontMetrics fontMetrics = gc.getFontMetrics();
			gc.dispose();
			GridData data = new GridData();
			data.horizontalIndent = Dialog.convertHorizontalDLUsToPixels(fontMetrics, IDialogConstants.INDENT);
			detailsLabel.setLayoutData(data);
			// add a listener so that clicking on the label selects the
			// corresponding radio button.
			// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=172574
			detailsLabel.addMouseListener(new MouseAdapter() {
				public void mouseUp(MouseEvent e) {
					createInNewLocation = false;
					radio1.setSelection(!createInNewLocation);
					radio2.setSelection(createInNewLocation);
				}
			});
			// Add a spacer label
			new Label(composite, SWT.LEFT);

			radio2 = new Button(composite, SWT.RADIO);
			radio2.addSelectionListener(selectionListener);
			radio2.setText(PHPUIMessages.getString("PHPProjectCreationWizard.createProjectIn") + location + System.getProperty("file.separator") + projectName + "."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			radio2.setFont(parent.getFont());

			// set initial state
			radio1.setSelection(!createInNewLocation);
			radio2.setSelection(createInNewLocation);

			return composite;
		}

		private SelectionListener selectionListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				if (button.getSelection()) {
					createInNewLocation = (button == radio2);
				}
			}
		};

		boolean getCreateInNewLocation() {
			return createInNewLocation;
		}
		
		protected Control createButtonBar(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(0) // this is incremented
															// by createButton
					.equalWidth(true).applyTo(composite);

			GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).span(
					2, 1).applyTo(composite);
			composite.setFont(parent.getFont());
			// Add the buttons to the button bar.
			createButtonsForButtonBar(composite);
			return composite;
		}
	}

	protected void postPerformFinish() throws InvocationTargetException {
		if (createdProject != null) {
			// Save any project-specific data (Fix Bug# 143406)
			try {
				new ProjectScope(createdProject).getNode(PHPCorePlugin.ID).flush();
			} catch (BackingStoreException e) {
				Logger.logException(e);
			}
		}

		UIJob j = new UIJob("") { //$NON-NLS-1$
			public IStatus runInUIThread(IProgressMonitor monitor) {
				BasicNewProjectResourceWizard.updatePerspective(configElement);
				return Status.OK_STATUS;
			}
		};
		j.setUser(false);
		j.schedule();
	}

	private void populateWizardFactoryList() {
		IWizardPage[] pageGenerators = PHPWizardPagesRegistry.getPageFactories(ID);
		if (pageGenerators != null) {
			for (IWizardPage element : pageGenerators) {
				WizardPageFactory pageFactory = (WizardPageFactory) element;
				wizardPageFactories.add(pageFactory);
			}
		}
	}

}