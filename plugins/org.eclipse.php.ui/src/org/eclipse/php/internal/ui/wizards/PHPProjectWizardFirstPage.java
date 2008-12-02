/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.wizards.ProjectWizardFirstPage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.PHPInterpreterPreferencePage;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.wizards.fields.DialogField;
import org.eclipse.php.internal.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.internal.ui.wizards.fields.LayoutUtil;
import org.eclipse.php.internal.ui.wizards.fields.SelectionButtonDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
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
			final IStatus nameStatus = workspace.validateName(name,
					IResource.PROJECT);
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

		public LayoutGroup() {
			fStdRadio= new SelectionButtonDialogField(SWT.RADIO);
			fStdRadio.setLabelText(PHPUIMessages.getString("LayoutGroup_OptionBlock_ProjectSrc"));
			fStdRadio.setDialogFieldListener(this);

			fSrcBinRadio= new SelectionButtonDialogField(SWT.RADIO);
			fSrcBinRadio.setLabelText(PHPUIMessages.getString("LayoutGroup_OptionBlock_SrcResources"));
			fSrcBinRadio.setDialogFieldListener(this);

			//boolean useSrcBin= PreferenceConstants.getPreferenceStore().getBoolean("test3");
			boolean useSrcBin=false ;//FIXME : need to get default from pref. store
			fSrcBinRadio.setSelection(useSrcBin);
			fStdRadio.setSelection(!useSrcBin);
		}

		public Control createContent(Composite composite) {
			fGroup= new Group(composite, SWT.NONE);
			fGroup.setFont(composite.getFont());

			fGroup.setLayout(initGridLayout(new GridLayout(3, false), true));
			fGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
			fGroup.setText(PHPUIMessages.getString("LayoutGroup_OptionBlock_Title"));

			fStdRadio.doFillIntoGrid(fGroup, 3);
			LayoutUtil.setHorizontalGrabbing(fStdRadio.getSelectionButton(null));

			fSrcBinRadio.doFillIntoGrid(fGroup, 2);

			fPreferenceLink= new Link(fGroup, SWT.NONE);
			fPreferenceLink.setText(PHPUIMessages.getString("ToggleLinkingAction_link_description"));
			//fPreferenceLink.setLayoutData(new GridData(GridData.END, GridData.END, false, false));
			fPreferenceLink.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, true, false));
			fPreferenceLink.addSelectionListener(this);

			updateEnableState();
			return fGroup;
		}


		/* (non-Javadoc)
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
		public void update(Observable o, Object arg) {
			updateEnableState();
		}

		private void updateEnableState() {
			if (fDetectGroup == null)
				return;
			
			final boolean detect= fDetectGroup.mustDetect();
			fStdRadio.setEnabled(!detect);
			fSrcBinRadio.setEnabled(!detect);
			if (fPreferenceLink != null) {
				fPreferenceLink.setEnabled(!detect && fSrcBinRadio.isSelected());
			}
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
			//FIXME : 1. need to create PHP->Build pref. page
			//FIXME : 2. link it ...
			
			String prefID = PHPInterpreterPreferencePage.PREF_ID;
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

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());
		composite.setLayout(initGridLayout(new GridLayout(1, false), true));
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		// create UI elements
		fNameGroup = new NameGroup(composite, fInitialName);
		fVersionGroup = new PHPVersionGroup(composite, this);
		fLayoutGroup = new LayoutGroup();
		fLayoutGroup.createContent(composite);//FIXME
		fJavaScriptSupportGroup = new JavaScriptSupportGroup(composite, this);

		createCustomGroups(composite);
		// fLayoutGroup= new LayoutGroup(composite);
		fDetectGroup = new DetectGroup(composite);
		// establish connections
		//fNameGroup.addObserver(fLocationGroup);
		// fDetectGroup.addObserver(fLayoutGroup);
		// fDetectGroup.addObserver(fInterpreterEnvironmentGroup);

		//fLocationGroup.addObserver(fDetectGroup);
		// initialize all elements
		fNameGroup.notifyObservers();
		// create and connect validator
		fPdtValidator = new PdtValidator();

		fNameGroup.addObserver(fPdtValidator);
		//fLocationGroup.addObserver(fValidator);

		setControl(composite);
		Dialog.applyDialogFont(composite);
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

	@Override
	public boolean isInWorkspace() {
		// always in workspace
		return true;
	}
	
}
