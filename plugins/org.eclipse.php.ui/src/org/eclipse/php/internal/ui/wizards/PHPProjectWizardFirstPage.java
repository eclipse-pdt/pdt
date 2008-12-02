/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards;

import java.net.URI;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.*;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.environment.IEnvironmentUI;
import org.eclipse.dltk.ui.wizards.ProjectWizardFirstPage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.PHPBuildPreferencePage;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * The first page of the <code>SimpleProjectWizard</code>.
 */
public class PHPProjectWizardFirstPage extends ProjectWizardFirstPage {
	/**
	 * Validate this page and show appropriate warnings and error
	 * NewWizardMessages.
	 */
	public final class PdtValidator implements Observer {
		public void update(Observable o, Object arg) {
			final IWorkspace workspace = DLTKUIPlugin.getWorkspace();
			final String name = getNameGroup().getName();
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
			if (handle.exists()) {
				setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_projectAlreadyExists);
				setPageComplete(false);
				return;
			}

			setPageComplete(true);
			setErrorMessage(null);
			setMessage(null);
		}
	}
	/**
	 * GUI for controlling whether a new PHP project should include JavaScript support or not 
	 * @author alon
	 *
	 */
	public class JavaScriptSupportGroup {

		private final Group fGroup;
		protected Button fEnableJavaScriptSupport;

		public JavaScriptSupportGroup(Composite composite, WizardPage projectWizardFirstPage) {

			fGroup = new Group(composite, SWT.NONE);
			fGroup.setFont(composite.getFont());
			GridLayout layout = new GridLayout();

			fGroup.setLayout(layout);
			GridData data = new GridData(GridData.FILL_BOTH);
			fGroup.setLayoutData(data);
			fGroup.setText(PHPUIMessages.getString("JavaScriptSupportGroup_OptionBlockTitle"));

			Composite checkLinkComposite = new Composite(fGroup, SWT.NONE);
			checkLinkComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			checkLinkComposite.setLayout(new GridLayout(2, false));

			fEnableJavaScriptSupport = new Button(checkLinkComposite, SWT.CHECK | SWT.RIGHT);
			fEnableJavaScriptSupport.setText(PHPUIMessages.getString("JavaScriptSupportGroup_EnableSupport"));
			fEnableJavaScriptSupport.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
			//FIXME	fEnableJavaScriptSupport.setSelection(model.getBooleanProperty(PHPCoreConstants.ADD_JS_NATURE));
		}

	}
	/**
	 * Request a project layout.
	 */
	private final class LayoutGroup implements Observer, SelectionListener, IDialogFieldListener {

		private final SelectionButtonDialogField fStdRadio, fSrcBinRadio;
		private Group fGroup;
		private Link fPreferenceLink;

		public LayoutGroup(Composite composite) {
			fStdRadio = new SelectionButtonDialogField(SWT.RADIO);
			fStdRadio.setLabelText(PHPUIMessages.getString("LayoutGroup_OptionBlock_ProjectSrc"));
			fStdRadio.setDialogFieldListener(this);

			fSrcBinRadio = new SelectionButtonDialogField(SWT.RADIO);
			fSrcBinRadio.setLabelText(PHPUIMessages.getString("LayoutGroup_OptionBlock_SrcResources"));
			fSrcBinRadio.setDialogFieldListener(this);

			//getting Preferences default choice
			boolean useSrcBin = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ);

			fSrcBinRadio.setSelection(useSrcBin);
			fStdRadio.setSelection(!useSrcBin);

			//createContent
			fGroup = new Group(composite, SWT.NONE);
			fGroup.setFont(composite.getFont());

			fGroup.setLayout(initGridLayout(new GridLayout(3, false), true));
			fGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
			fGroup.setText(PHPUIMessages.getString("LayoutGroup_OptionBlock_Title"));

			fStdRadio.doFillIntoGrid(fGroup, 3);
			LayoutUtil.setHorizontalGrabbing(fStdRadio.getSelectionButton(null));

			fSrcBinRadio.doFillIntoGrid(fGroup, 2);

			fPreferenceLink = new Link(fGroup, SWT.NONE);
			fPreferenceLink.setText(PHPUIMessages.getString("ToggleLinkingAction_link_description"));
			//fPreferenceLink.setLayoutData(new GridData(GridData.END, GridData.END, false, false));
			fPreferenceLink.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, true, false));
			fPreferenceLink.addSelectionListener(this);
			fPreferenceLink.setEnabled(true);

			updateEnableState();
		}

		//		public Control createContent(Composite composite) {
		//			fGroup = new Group(composite, SWT.NONE);
		//			fGroup.setFont(composite.getFont());
		//
		//			fGroup.setLayout(initGridLayout(new GridLayout(3, false), true));
		//			fGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		//			fGroup.setText(PHPUIMessages.getString("LayoutGroup_OptionBlock_Title"));
		//
		//			fStdRadio.doFillIntoGrid(fGroup, 3);
		//			LayoutUtil.setHorizontalGrabbing(fStdRadio.getSelectionButton(null));
		//
		//			fSrcBinRadio.doFillIntoGrid(fGroup, 2);
		//
		//			fPreferenceLink = new Link(fGroup, SWT.NONE);
		//			fPreferenceLink.setText(PHPUIMessages.getString("ToggleLinkingAction_link_description"));
		//			//fPreferenceLink.setLayoutData(new GridData(GridData.END, GridData.END, false, false));
		//			fPreferenceLink.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, true, false));
		//			fPreferenceLink.addSelectionListener(this);
		//			fPreferenceLink.setEnabled(true);
		//
		//			updateEnableState();
		//			return fGroup;
		//		}

		/* (non-Javadoc)
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
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
		 * Return <code>true</code> if the user specified to create 'source' and 'bin' folders.
		 *
		 * @return returns <code>true</code> if the user specified to create 'source' and 'bin' folders.
		 */
		public boolean isSrcBin() {
			return fSrcBinRadio.isSelected();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}

		/*
		 * @see org.eclipse.jdt.internal.ui.wizards.dialogfields.IDialogFieldListener#dialogFieldChanged(org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField)
		 * @since 3.5
		 */
		public void dialogFieldChanged(DialogField field) {
			updateEnableState();
		}

		public void widgetDefaultSelected(SelectionEvent e) {

			String prefID = PHPBuildPreferencePage.PREF_ID;
			Map data = null;
			PreferencesUtil.createPreferenceDialogOn(getShell(), prefID, new String[] { prefID }, data).open();
			//			if (!fEnableProjectSettings.getSelection()) {
			//				fConfigurationBlock.performRevert();
			//			}
		}
	}

	protected PdtValidator fPdtValidator;

	public NameGroup getNameGroup() {
		return fNameGroup;
	}

	protected PHPVersionGroup fVersionGroup;
	protected JavaScriptSupportGroup fJavaScriptSupportGroup;
	protected LayoutGroup fLayoutGroup;
	protected PHPLocationGroup fPHPLocationGroup;

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());
		composite.setLayout(initGridLayout(new GridLayout(1, false), true));
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		// create UI elements
		fNameGroup = new NameGroup(composite, fInitialName);
		fPHPLocationGroup = new PHPLocationGroup(composite);

		fVersionGroup = new PHPVersionGroup(composite, this);
		fLayoutGroup = new LayoutGroup(composite);
		fJavaScriptSupportGroup = new JavaScriptSupportGroup(composite, this);

		createCustomGroups(composite);
		fDetectGroup = new DetectGroup(composite);

		// establish connections
		fNameGroup.addObserver(fPHPLocationGroup);
		fDetectGroup.addObserver(fLayoutGroup);
		// fDetectGroup.addObserver(fInterpreterEnvironmentGroup);

		fPHPLocationGroup.addObserver(fDetectGroup);
		// initialize all elements
		fNameGroup.notifyObservers();
		// create and connect validator
		fPdtValidator = new PdtValidator();

		fNameGroup.addObserver(fPdtValidator);
		fPHPLocationGroup.addObserver(fPdtValidator);

		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

	public URI getLocationURI() {
		IEnvironment environment = getEnvironment();
		return environment.getURI(fPHPLocationGroup.getLocation());
	}

	@Override
	protected void createInterpreterGroup(Composite parent) {
		// TODO Auto-generated method stub
	}

	@Override
	protected IInterpreterInstall getInterpreter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInWorkspace() {
		return fPHPLocationGroup.isInWorkspace();
	}

	@Override
	protected Observable getInterpreterGroupObservable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handlePossibleInterpreterChange() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean interpeterRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean supportInterpreter() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Request a location. Fires an event whenever the checkbox or the location
	 * field is changed, regardless of whether the change originates from the
	 * user or has been invoked programmatically.
	 */
	public class PHPLocationGroup extends Observable implements Observer, IStringButtonAdapter, IDialogFieldListener {
		protected final SelectionButtonDialogField fWorkspaceRadio;
		protected final SelectionButtonDialogField fExternalRadio;
		protected final StringButtonDialogField fLocation;
		//		protected final ComboDialogField fEnvironment;
		private IEnvironment[] environments;

		private String fPreviousExternalLocation;
		private int localEnv;
		private static final String DIALOGSTORE_LAST_EXTERNAL_LOC = DLTKUIPlugin.PLUGIN_ID + ".last.external.project"; //$NON-NLS-1$

		public PHPLocationGroup(Composite composite) {
			final int numColumns = 3;
			final Group group = new Group(composite, SWT.NONE);
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setLayout(initGridLayout(new GridLayout(numColumns, false), true));
			group.setText(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_title);
			fWorkspaceRadio = new SelectionButtonDialogField(SWT.RADIO);
			fWorkspaceRadio.setDialogFieldListener(this);
			fWorkspaceRadio.setLabelText(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_workspace_desc);
			fExternalRadio = new SelectionButtonDialogField(SWT.RADIO);
			fExternalRadio.setDialogFieldListener(this);
			fExternalRadio.setLabelText(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_external_desc);
			fLocation = new StringButtonDialogField(this);
			fLocation.setDialogFieldListener(this);
			fLocation.setLabelText(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_locationLabel_desc);
			fLocation.setButtonLabel(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_browseButton_desc);
			// fExternalRadio.attachDialogField(fLocation);
			fWorkspaceRadio.setSelection(true);
			fExternalRadio.setSelection(false);
			fPreviousExternalLocation = ""; //$NON-NLS-1$
			fWorkspaceRadio.doFillIntoGrid(group, numColumns);
			fExternalRadio.doFillIntoGrid(group, numColumns);
			fLocation.doFillIntoGrid(group, numColumns);
			LayoutUtil.setHorizontalGrabbing(fLocation.getTextControl(null));

			//			fEnvironment = new ComboDialogField(SWT.DROP_DOWN | SWT.READ_ONLY);
			//			fEnvironment.setLabelText(NewWizardMessages.ProjectWizardFirstPage_host);
			//			fEnvironment.setDialogFieldListener(this);
			//			
			environments = EnvironmentManager.getEnvironments();
			String[] items = new String[environments.length];
			localEnv = 0;
			for (int i = 0; i < items.length; i++) {
				items[i] = environments[i].getName();
				if (environments[i].isLocal()) {
					localEnv = i;
				}
			}
			////			fEnvironment.setItems(items);
			////			fEnvironment.selectItem(local);
			//			fEnvironment.doFillIntoGrid(group, numColumns);
			//			LayoutUtil.setHorizontalGrabbing(fEnvironment.getComboControl(null));

			//			fEnvironment.setEnabled(false);
			fExternalRadio.attachDialogFields(new DialogField[] { fLocation });

		}

		protected void fireEvent() {
			setChanged();
			notifyObservers();
		}

		private void updateInterpreters() {
			Observable observable = PHPProjectWizardFirstPage.this.getInterpreterGroupObservable();
			if (observable != null && observable instanceof AbstractInterpreterGroup) {
				((AbstractInterpreterGroup) observable).handlePossibleInterpreterChange();
			}
		}

		protected String getDefaultPath(String name) {
			IEnvironment environment = this.getEnvironment();
			if (environment != null && environment.isLocal()) {
				final IPath path = Platform.getLocation().append(name);
				return path.toOSString();
			} else {
				return ""; //$NON-NLS-1$
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Observer#update(java.util.Observable,
		 * java.lang.Object)
		 */
		public void update(Observable o, Object arg) {
			if (isInWorkspace()) {
				fLocation.setText(getDefaultPath(fNameGroup.getName()));
			}
			fireEvent();
		}

		public IPath getLocation() {
			if (isInWorkspace()) {
				return Platform.getLocation();
			}
			return new Path(fLocation.getText().trim());
		}

		public boolean isInWorkspace() {
			return fWorkspaceRadio.isSelected();
		}

		public IEnvironment getEnvironment() {
			if (fWorkspaceRadio.isSelected()) {
				return EnvironmentManager.getEnvironmentById(LocalEnvironment.ENVIRONMENT_ID);
			}
			//			return environments[fEnvironment.getSelectionIndex()];
			return environments[localEnv];
		}

		public void changeControlPressed(DialogField field) {
			IEnvironment environment = getEnvironment();
			IEnvironmentUI environmentUI = (IEnvironmentUI) environment.getAdapter(IEnvironmentUI.class);
			if (environmentUI != null) {
				String selectedDirectory = environmentUI.selectFolder(getShell());

				if (selectedDirectory != null) {
					fLocation.setText(selectedDirectory);
					DLTKUIPlugin.getDefault().getDialogSettings().put(DIALOGSTORE_LAST_EXTERNAL_LOC, selectedDirectory);
				}
			}
		}

		public void dialogFieldChanged(DialogField field) {
			if (field == fWorkspaceRadio) {
				final boolean checked = fWorkspaceRadio.isSelected();
				if (checked) {
					fPreviousExternalLocation = fLocation.getText();
					fLocation.setText(getDefaultPath(fNameGroup.getName()));
				} else {
					IEnvironment environment = this.getEnvironment();
					if (environment != null && environment.isLocal()) {
						fLocation.setText(fPreviousExternalLocation);
					} else {
						fLocation.setText(""); //$NON-NLS-1$
					}
				}
			}

			//			if (field == fExternalRadio) {
			//				updateInterpreters();
			//			}

			fireEvent();
		}

	}

}
