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

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.dltk.internal.ui.util.CoreUtility;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.*;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.environment.IEnvironmentUI;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.dltk.ui.wizards.NewElementWizard;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.*;
import org.eclipse.php.internal.ui.workingset.IWorkingSetIds;
import org.eclipse.php.ui.util.PHPProjectUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.dialogs.WorkingSetConfigurationBlock;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * The first page of the <code>SimpleProjectWizard</code>.
 */
public class PHPProjectWizardFirstPage extends WizardPage implements IPHPProjectCreateWizardPage {

	public PHPProjectWizardFirstPage() {
		super(PAGE_NAME);
		setPageComplete(false);
		setTitle(NewWizardMessages.ScriptProjectWizardFirstPage_page_title);
		setDescription(NewWizardMessages.ScriptProjectWizardFirstPage_page_description);
		fInitialName = ""; //$NON-NLS-1$
	}

	private static final String FILENAME_PROJECT = ".project"; //$NON-NLS-1$
	protected static final String FILENAME_BUILDPATH = ".buildpath"; //$NON-NLS-1$
	protected URI fCurrProjectLocation; // null if location is platform location

	private boolean fKeepContent;
	private boolean fProjectCreated = false;

	private File fDotProjectBackup;
	private File fDotBuildpathBackup;
	private Boolean fIsAutobuild;

	private static final String PAGE_NAME = NewWizardMessages.ScriptProjectWizardFirstPage_page_title;
	public static final String ERROR_MESSAGE = "ErrorMessage"; //$NON-NLS-1$

	protected Validator fPdtValidator;
	protected String fInitialName;
	protected NameGroup fNameGroup;
	protected DetectGroup fDetectGroup;
	protected VersionGroup fVersionGroup;
	protected JavaScriptSupportGroup fJavaScriptSupportGroup;
	protected LayoutGroup fLayoutGroup;
	protected LocationGroup fPHPLocationGroup;
	protected WorkingSetGroup fWorkingSetGroup;
	protected WizardFragment fragment;

	@Override
	public void createControl(Composite parent) {

		initializeDialogUnits(parent);

		Composite main = new Composite(parent, SWT.NONE);
		main.setLayout(new GridLayout());

		ScrolledComposite sc = new ScrolledComposite(main, SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, SWT.DEFAULT).create());

		final Composite composite = new Composite(sc, SWT.NULL);
		composite.setFont(parent.getFont());
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		// create UI elements
		fNameGroup = new NameGroup(composite, fInitialName, getShell());
		fPHPLocationGroup = new LocationGroup(composite, fNameGroup, getShell());

		CompositeData data = new CompositeData();
		data.setParetnt(composite);
		data.setSettings(getDialogSettings());
		data.setObserver(fPHPLocationGroup);
		fragment = (WizardFragment) Platform.getAdapterManager().loadAdapter(data,
				PHPProjectWizardFirstPage.class.getName());

		fVersionGroup = new VersionGroup(this, composite, PHPVersion.PHP5) {
			@Override
			public IEnvironment getEnvironment() {
				return PHPProjectWizardFirstPage.this.getEnvironment();
			}
		};
		fLayoutGroup = new LayoutGroup(composite);
		fJavaScriptSupportGroup = new JavaScriptSupportGroup(composite, this);

		createWorkingSetGroup(composite, ((PHPProjectCreationWizard) getWizard()).getSelection(),
				new String[] { IWorkingSetIds.PHP_ID, IWorkingSetIds.RESOURCE_ID, IWorkingSetIds.TASK_ID });

		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setContent(composite);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);

		fDetectGroup = new DetectGroup(composite, fPHPLocationGroup, fNameGroup);

		// establish connections
		fNameGroup.addObserver(fPHPLocationGroup);
		fDetectGroup.addObserver(fLayoutGroup);

		fPHPLocationGroup.addObserver(fDetectGroup);
		// initialize all elements
		fNameGroup.notifyObservers();
		// create and connect validator
		fPdtValidator = new Validator();

		fNameGroup.addObserver(fPdtValidator);
		fPHPLocationGroup.addObserver(fPdtValidator);

		setControl(main);
		Dialog.applyDialogFont(composite);

		// set the focus to the project name
		fNameGroup.postSetFocus();

		setHelpContext(composite);
	}

	public WorkingSetGroup createWorkingSetGroup(Composite composite, IStructuredSelection selection,
			String[] supportedWorkingSetTypes) {
		if (fWorkingSetGroup != null)
			return fWorkingSetGroup;
		fWorkingSetGroup = new WorkingSetGroup(composite, selection, supportedWorkingSetTypes);
		return fWorkingSetGroup;
	}

	public boolean isExistingLocation() {
		return fPHPLocationGroup.isExistingLocation();
	}

	protected void setHelpContext(Composite parent) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.CREATING_PHP_PROJECTS);
	}

	public URI getLocationURI() {
		IEnvironment environment = getEnvironment();
		return environment.getURI(fPHPLocationGroup.getLocation());
	}

	public IEnvironment getEnvironment() {
		return fPHPLocationGroup.getEnvironment();
	}

	/**
	 * Creates a project resource handle for the current project name field
	 * value.
	 * <p>
	 * This method does not create the project resource; this is the
	 * responsibility of <code>IProject::create</code> invoked by the new
	 * project resource wizard.
	 * </p>
	 * 
	 * @return the new project resource handle
	 */
	public IProject getProjectHandle() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(fNameGroup.getName());
	}

	public String getProjectName() {
		return fNameGroup.getName();
	}

	public boolean isInWorkspace() {
		return fPHPLocationGroup.isInWorkspace();
	}

	public boolean isInLocalServer() {
		return fPHPLocationGroup.isInLocalServer();
	}

	public boolean getDetect() {
		return fDetectGroup.mustDetect();
	}

	/**
	 * returns whether this project layout is "detailed" - meaning tree
	 * structure - one folder for source, one for resources
	 * 
	 * @return
	 */
	public boolean hasPhpSourceFolder() {
		return fLayoutGroup != null && fLayoutGroup.isDetailedLayout();
	}

	/**
	 * Initialize a grid layout with the default Dialog settings.
	 */
	public GridLayout initGridLayout(GridLayout layout, boolean margins) {
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		if (margins) {
			layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
			layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		} else {
			layout.marginWidth = 0;
			layout.marginHeight = 0;
		}
		return layout;
	}

	/**
	 * Validate this page and show appropriate warnings and error
	 * NewWizardMessages.
	 */
	public final class Validator implements Observer {
		@Override
		public void update(Observable o, Object arg) {
			final IWorkspace workspace = DLTKUIPlugin.getWorkspace();
			final String name = fNameGroup.getName();
			// check whether the project name field is empty
			if (name.length() == 0) {
				setErrorMessage(null);
				setMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_enterProjectName);
				setPageComplete(false);
				return;
			}
			// check whether the project name is valid
			final IStatus nameStatus = workspace.validateName(name, IResource.PROJECT);
			if (!nameStatus.isOK()) {
				setErrorMessage(nameStatus.getMessage());
				setPageComplete(false);
				return;
			}
			// check whether project already exists
			final IProject handle = getProjectHandle();

			if (!isInLocalServer() && handle.exists()) {
				setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_projectAlreadyExists);
				setPageComplete(false);
				return;
			}

			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			String newProjectNameLowerCase = name.toLowerCase();
			for (IProject currentProject : projects) {
				String existingProjectName = currentProject.getName();
				if (existingProjectName.toLowerCase().equals(newProjectNameLowerCase)) {
					setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_projectAlreadyExists);
					setPageComplete(false);
					return;
				}
			}

			final String location = fPHPLocationGroup.getLocation().toOSString();
			// check whether location is empty
			if (location.length() == 0) {
				setErrorMessage(null);
				setMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_enterLocation);
				setPageComplete(false);
				return;
			}
			// check whether the location is a syntactically correct path
			if (!Path.EMPTY.isValidPath(location)) {
				setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_invalidDirectory);
				setPageComplete(false);
				return;
			}
			// check whether the location has the workspace as prefix
			IPath projectPath = Path.fromOSString(location);
			if (!fPHPLocationGroup.isInWorkspace() && Platform.getLocation().isPrefixOf(projectPath)) {
				setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_cannotCreateInWorkspace);
				setPageComplete(false);
				return;
			}
			// If we do not place the contents in the workspace validate the
			// location.
			if (!fPHPLocationGroup.isInWorkspace()) {
				IEnvironment environment = getEnvironment();
				if (EnvironmentManager.isLocal(environment)) {
					final IStatus locationStatus = workspace.validateProjectLocation(handle, projectPath);
					if (!locationStatus.isOK()) {
						setErrorMessage(locationStatus.getMessage());
						setPageComplete(false);
						return;
					}

					if (!canCreate(projectPath.toFile())) {
						setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_invalidDirectory);
						setPageComplete(false);
						return;
					}
				}
			}

			if (fragment != null) {
				fragment.getWizardModel().putObject("ProjectName", //$NON-NLS-1$
						fNameGroup.getName());
				if (!fragment.isComplete()) {
					setErrorMessage((String) fragment.getWizardModel().getObject(ERROR_MESSAGE));
					setPageComplete(false);
					return;
				}
			}

			setPageComplete(true);
			setErrorMessage(null);
			setMessage(null);
		}
	}

	private boolean canCreate(File file) {
		while (!file.exists()) {
			file = file.getParentFile();
			if (file == null)
				return false;
		}

		return file.canWrite();
	}

	/**
	 * GUI for controlling whether a new PHP project should include JavaScript
	 * support or not
	 * 
	 * @author alon
	 * 
	 */
	public class JavaScriptSupportGroup implements SelectionListener {

		private final Group fGroup;
		protected Button fEnableJavaScriptSupport;

		public boolean shouldSupportJavaScript() {
			return PHPUiPlugin.getDefault().getPreferenceStore()
					.getBoolean((PreferenceConstants.JavaScriptSupportEnable));
		}

		public JavaScriptSupportGroup(Composite composite, WizardPage projectWizardFirstPage) {
			final int numColumns = 3;
			fGroup = new Group(composite, SWT.NONE);
			fGroup.setFont(composite.getFont());

			fGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			fGroup.setLayout(new GridLayout(numColumns, false));
			fGroup.setText(PHPUIMessages.JavaScriptSupportGroup_OptionBlockTitle);

			fEnableJavaScriptSupport = new Button(fGroup, SWT.CHECK | SWT.RIGHT);
			fEnableJavaScriptSupport.setText(PHPUIMessages.JavaScriptSupportGroup_EnableSupport);
			fEnableJavaScriptSupport.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
			fEnableJavaScriptSupport.addSelectionListener(this);
			fEnableJavaScriptSupport.setSelection(PHPUiPlugin.getDefault().getPreferenceStore()
					.getBoolean((PreferenceConstants.JavaScriptSupportEnable)));
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
		}

		public boolean getSelection() {
			return fEnableJavaScriptSupport.getSelection();
		}

	}

	/**
	 * Request a project layout.
	 */
	public class LayoutGroup implements Observer, SelectionListener, IDialogFieldListener {

		private final SelectionButtonDialogField fStdRadio, fSrcBinRadio;
		private Group fGroup;
		private Link fPreferenceLink;

		public LayoutGroup(Composite composite) {
			final int numColumns = 3;

			fStdRadio = new SelectionButtonDialogField(SWT.RADIO);
			fStdRadio.setLabelText(PHPUIMessages.LayoutGroup_OptionBlock_ProjectSrc);
			fStdRadio.setDialogFieldListener(this);

			fSrcBinRadio = new SelectionButtonDialogField(SWT.RADIO);
			fSrcBinRadio.setLabelText(PHPUIMessages.LayoutGroup_OptionBlock_SrcResources);
			fSrcBinRadio.setDialogFieldListener(this);

			// getting Preferences default choice
			boolean useSrcBin = PreferenceConstants.getPreferenceStore()
					.getBoolean(PreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ);

			fSrcBinRadio.setSelection(useSrcBin);
			fStdRadio.setSelection(!useSrcBin);

			// createContent
			fGroup = new Group(composite, SWT.NONE);
			fGroup.setFont(composite.getFont());
			fGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			fGroup.setLayout(new GridLayout(numColumns, false));
			fGroup.setText(PHPUIMessages.LayoutGroup_OptionBlock_Title);

			fStdRadio.doFillIntoGrid(fGroup, 3);
			LayoutUtil.setHorizontalGrabbing(fStdRadio.getSelectionButton(null));

			fSrcBinRadio.doFillIntoGrid(fGroup, 2);

			fPreferenceLink = new Link(fGroup, SWT.NONE);
			fPreferenceLink.setText(PHPUIMessages.ToggleLinkingAction_link_description);
			fPreferenceLink.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, true, false));
			fPreferenceLink.addSelectionListener(this);
			fPreferenceLink.setEnabled(true);

			updateEnableState();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Observer#update(java.util.Observable,
		 * java.lang.Object)
		 */
		@Override
		public void update(Observable o, Object arg) {
			updateEnableState();
		}

		private void updateEnableState() {
			if (fDetectGroup == null)
				return;

			final boolean detect = fDetectGroup.mustDetect();
			fStdRadio.setEnabled(!detect);
			fSrcBinRadio.setEnabled(!detect);

			if (fGroup != null) {
				fGroup.setEnabled(!detect);
			}
		}

		/**
		 * Return <code>true</code> if the user specified to create
		 * 'application' and 'public' folders.
		 * 
		 * @return returns <code>true</code> if the user specified to create
		 *         'source' and 'bin' folders.
		 */
		public boolean isDetailedLayout() {
			return fSrcBinRadio.isSelected();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
		 * .swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}

		/*
		 * @see
		 * org.eclipse.jdt.internal.ui.wizards.dialogfields.IDialogFieldListener
		 * #dialogFieldChanged(org.eclipse.jdt.internal.ui.wizards.dialogfields.
		 * DialogField)
		 * 
		 * @since 3.5
		 */
		@Override
		public void dialogFieldChanged(DialogField field) {
			updateEnableState();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

			String prefID = PHPProjectLayoutPreferencePage.PREF_ID;

			PreferencesUtil.createPreferenceDialogOn(getShell(), prefID, new String[] { prefID }, null).open();
		}
	}

	/**
	 * Request a location. Fires an event whenever the checkbox or the location
	 * field is changed, regardless of whether the change originates from the
	 * user or has been invoked programmatically.
	 */
	public abstract static class VersionGroup extends Observable
			implements Observer, IStringButtonAdapter, IDialogFieldListener, SelectionListener {
		private WizardPage page;
		public final SelectionButtonDialogField fDefaultValues;
		protected final SelectionButtonDialogField fCustomValues;

		public PHPVersionConfigurationBlock fConfigurationBlock;

		private static final String DIALOGSTORE_LAST_EXTERNAL_LOC = DLTKUIPlugin.PLUGIN_ID + ".last.external.project"; //$NON-NLS-1$

		public VersionGroup(WizardPage page, Composite composite, PHPVersion minimumVersion) {
			this.page = page;
			final int numColumns = 3;
			final Group group = new Group(composite, SWT.NONE);
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setLayout(new GridLayout(numColumns, false));
			group.setText(PHPUIMessages.VersionGroup_OptionBlock_Title);
			fDefaultValues = new SelectionButtonDialogField(SWT.RADIO);
			fDefaultValues.setDialogFieldListener(this);
			fDefaultValues.setLabelText(PHPUIMessages.VersionGroup_OptionBlock_fDefaultValues);
			fCustomValues = new SelectionButtonDialogField(SWT.RADIO);
			fCustomValues.setDialogFieldListener(this);
			fCustomValues.setLabelText(PHPUIMessages.VersionGroup_OptionBlock_fCustomValues);

			fDefaultValues.setSelection(true);
			fCustomValues.setSelection(false);

			fDefaultValues.doFillIntoGrid(group, numColumns);
			fCustomValues.doFillIntoGrid(group, 2);

			fConfigurationBlock = createConfigurationBlock(status -> {
			}, (IProject) null, null);
			fConfigurationBlock.setMinimumVersion(minimumVersion);
			fConfigurationBlock.createContents(group);
			fConfigurationBlock.setEnabled(false);
		}

		protected PHPVersionConfigurationBlock createConfigurationBlock(IStatusChangeListener listener,
				IProject project, IWorkbenchPreferenceContainer container) {
			return new PHPVersionConfigurationBlock(listener, project, container, true);
		}

		protected void fireEvent() {
			setChanged();
			notifyObservers();
		}

		//

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Observer#update(java.util.Observable,
		 * java.lang.Object)
		 */
		@Override
		public void update(Observable o, Object arg) {
			fireEvent();
		}

		@Override
		public void changeControlPressed(DialogField field) {
			IEnvironment environment = getEnvironment();
			IEnvironmentUI environmentUI = (IEnvironmentUI) environment.getAdapter(IEnvironmentUI.class);
			if (environmentUI != null) {
				String selectedDirectory = environmentUI.selectFolder(page.getShell());

				if (selectedDirectory != null) {
					DLTKUIPlugin.getDefault().getDialogSettings().put(DIALOGSTORE_LAST_EXTERNAL_LOC, selectedDirectory);
				}
			}
		}

		public abstract IEnvironment getEnvironment();

		@Override
		public void dialogFieldChanged(DialogField field) {
			if (field == fDefaultValues) {
				final boolean checked = fDefaultValues.isSelected();
				if (null != fConfigurationBlock)
					this.fConfigurationBlock.setEnabled(!checked);
			}

			fireEvent();
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			String prefID = PHPInterpreterPreferencePage.PREF_ID;
			PreferencesUtil.createPreferenceDialogOn(page.getShell(), prefID, new String[] { prefID }, null).open();
			if (!fCustomValues.isSelected()) {
				fConfigurationBlock.performRevert();
			}
		}

	}

	private static final class WorkingSetGroup {

		private WorkingSetConfigurationBlock workingSetBlock;

		public WorkingSetGroup(Composite composite, IStructuredSelection currentSelection, String[] workingSetTypes) {
			Group workingSetGroup = new Group(composite, SWT.NONE);
			workingSetGroup.setFont(composite.getFont());
			workingSetGroup.setText(WorkbenchMessages.WorkingSetGroup_WorkingSets_group);
			workingSetGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

			GridLayout layout = new GridLayout(1, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			workingSetGroup.setLayout(layout);

			workingSetBlock = new WorkingSetConfigurationBlock(workingSetTypes,
					PHPUiPlugin.getDefault().getDialogSettings());
			workingSetBlock.setWorkingSets(workingSetBlock.findApplicableWorkingSets(currentSelection));
			workingSetBlock.createContent(workingSetGroup);
		}

		public IWorkingSet[] getSelectedWorkingSets() {
			return workingSetBlock.getSelectedWorkingSets();
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		fNameGroup.setFocus();

		if (visible) {
			// if there is a project we are going from 2nd to 1st
			// remove the project
			removeProject();
		} else {
			IWizardPage currentPage = getContainer().getCurrentPage();
			if (currentPage instanceof IPHPProjectCreateWizardPage) {
				// going forward from 1st page to 2nd one
				changeToNewProject();
				((IPHPProjectCreateWizardPage) currentPage).initPage();
			}
		}

	}

	private void removeProject() {
		if (fNameGroup == null || fNameGroup.getName() == null || fNameGroup.getName().length() == 0) {
			return;
		}
		if (getProjectHandle() == null || !getProjectHandle().exists()) {
			return;
		}

		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				doRemoveProject(monitor);
			}
		};

		try {
			getContainer().run(true, true, new WorkspaceModifyDelegatingOperation(op));
		} catch (InvocationTargetException e) {
			final String title = NewWizardMessages.ScriptProjectWizardSecondPage_error_remove_title;
			final String message = NewWizardMessages.ScriptProjectWizardSecondPage_error_remove_message;
			ExceptionHandler.handle(e, getShell(), title, message);
		} catch (InterruptedException e) {
			// cancel pressed
		}
	}

	final void doRemoveProject(IProgressMonitor monitor) throws InvocationTargetException {
		final boolean noProgressMonitor = (fCurrProjectLocation == null); // inside
		// workspace
		if (monitor == null || noProgressMonitor) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask(NewWizardMessages.ScriptProjectWizardSecondPage_operation_remove, 3);
		try {
			try {
				boolean removeContent = !fKeepContent && getProjectHandle().isSynchronized(IResource.DEPTH_INFINITE);
				getProjectHandle().delete(removeContent, false, new SubProgressMonitor(monitor, 2));

			} finally {
				CoreUtility.enableAutoBuild(fIsAutobuild.booleanValue()); // fIsAutobuild
				// must
				// be
				// set
				fIsAutobuild = null;
			}
		} catch (CoreException e) {
			throw new InvocationTargetException(e);
		} finally {
			monitor.done();
			fKeepContent = false;
		}
	}

	private void changeToNewProject() {
		fKeepContent = this.getDetect();

		final IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					if (fIsAutobuild == null) {
						fIsAutobuild = Boolean.valueOf(CoreUtility.enableAutoBuild(false));
					}
					updateProject(monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} catch (OperationCanceledException e) {
					throw new InterruptedException();
				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, false, new WorkspaceModifyDelegatingOperation(op));
		} catch (InvocationTargetException e) {
			final String title = NewWizardMessages.ScriptProjectWizardSecondPage_error_title;
			final String message = NewWizardMessages.ScriptProjectWizardSecondPage_error_message;
			ExceptionHandler.handle(e, getShell(), title, message);
		} catch (InterruptedException e) {
			// cancel pressed
		}
	}

	/**
	 * Called from the wizard on cancel.
	 */
	public void performCancel() {
		if (fProjectCreated) {
			removeProject();
		}
	}

	/**
	 * Helper method to create and open a IProject. The project location is
	 * configured. No natures are added.
	 * 
	 * @param project
	 *            The handle of the project to create.
	 * @param locationURI
	 *            The location of the project or <code>null</code> to create the
	 *            project in the workspace
	 * @param monitor
	 *            a progress monitor to report progress or <code>null</code> if
	 *            progress reporting is not desired
	 * @throws CoreException
	 *             if the project couldn't be created
	 * @see org.eclipse.core.resources.IProjectDescription#setLocationURI(java.net.URI)
	 * 
	 */
	public void createProject(IProject project, URI locationURI, IProgressMonitor monitor) throws CoreException {
		PHPProjectUtils.createProjectAt(project, locationURI, monitor);
		fProjectCreated = true;
	}

	protected void rememberExistingFiles(URI projectLocation) throws CoreException {
		fDotProjectBackup = null;
		fDotBuildpathBackup = null;

		IFileStore file = EFS.getStore(projectLocation);
		if (file.fetchInfo().exists()) {
			IFileStore projectFile = file.getChild(FILENAME_PROJECT);
			if (projectFile.fetchInfo().exists()) {
				fDotProjectBackup = createBackup(projectFile, "project-desc"); //$NON-NLS-1$
			}
			IFileStore buildpathFile = file.getChild(FILENAME_BUILDPATH);
			if (buildpathFile.fetchInfo().exists()) {
				fDotBuildpathBackup = createBackup(buildpathFile, "buildpath-desc"); //$NON-NLS-1$
			}
		}
	}

	private File createBackup(IFileStore source, String name) throws CoreException {
		try {
			File bak = File.createTempFile("eclipse-" + name, ".bak"); //$NON-NLS-1$ //$NON-NLS-2$
			copyFile(source, bak);
			return bak;
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, DLTKUIPlugin.PLUGIN_ID, IStatus.ERROR,
					Messages.format(NewWizardMessages.ScriptProjectWizardSecondPage_problem_backup, name), e);
			throw new CoreException(status);
		}
	}

	private void copyFile(IFileStore source, File target) throws IOException, CoreException {
		InputStream is = source.openInputStream(EFS.NONE, null);
		FileOutputStream os = new FileOutputStream(target);
		copyFile(is, os);
	}

	private void copyFile(InputStream is, OutputStream os) throws IOException {
		try {
			byte[] buffer = new byte[8192];
			while (true) {
				int bytesRead = is.read(buffer);
				if (bytesRead == -1)
					break;

				os.write(buffer, 0, bytesRead);
			}
		} finally {
			try {
				is.close();
			} finally {
				os.close();
			}
		}
	}

	protected URI getProjectLocationURI() throws CoreException {
		if (this.isInWorkspace()) {
			return null;
		}
		return this.getLocationURI();
	}

	protected void updateProject(IProgressMonitor monitor) throws CoreException, InterruptedException {

		IProject projectHandle = this.getProjectHandle();
		DLTKCore.create(projectHandle);
		fCurrProjectLocation = getProjectLocationURI();

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			monitor.beginTask(NewWizardMessages.ScriptProjectWizardSecondPage_operation_initialize, 70);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			URI realLocation = fCurrProjectLocation;
			if (fCurrProjectLocation == null) { // inside workspace
				try {
					URI rootLocation = ResourcesPlugin.getWorkspace().getRoot().getLocationURI();
					realLocation = new URI(rootLocation.getScheme(), null,
							Path.fromPortableString(rootLocation.getPath()).append(projectHandle.getName()).toString(),
							null);
				} catch (URISyntaxException e) {
					Assert.isTrue(false, "Can't happen"); //$NON-NLS-1$
				}
			}

			rememberExistingFiles(realLocation);

			createProject(projectHandle, fCurrProjectLocation, new SubProgressMonitor(monitor, 20));
		} finally {
			monitor.done();
		}
	}

	@Override
	public void initPage() {
	}

	public WizardModel getWizardData() {
		if (fragment != null) {
			return fragment.getWizardModel();
		}
		return null;
	}

	public void performFinish(IProgressMonitor monitor) {
		Display.getDefault().asyncExec(() -> {
			PHPUiPlugin.getDefault().getPreferenceStore().setValue((PreferenceConstants.JavaScriptSupportEnable),
					fJavaScriptSupportGroup.getSelection());
			IWorkingSet[] workingSets = fWorkingSetGroup.getSelectedWorkingSets();
			((NewElementWizard) getWizard()).getWorkbench().getWorkingSetManager().addToWorkingSets(getProjectHandle(),
					workingSets);

		});
	}

	public boolean shouldSupportJavaScript() {

		return fJavaScriptSupportGroup != null && fJavaScriptSupportGroup.shouldSupportJavaScript();
	}

	public boolean isDefaultVersionSelected() {
		return fVersionGroup != null && fVersionGroup.fDefaultValues.isSelected();
	}

	public boolean getUseAspTagsValue() {
		return fVersionGroup != null && fVersionGroup.fConfigurationBlock.getUseAspTagsValue();
	}

	public PHPVersion getPHPVersionValue() {
		if (fVersionGroup != null) {
			return fVersionGroup.fConfigurationBlock.getPHPVersionValue();
		}
		return null;
	}

	protected Boolean getIsAutobuild() {
		return fIsAutobuild;
	}
}
