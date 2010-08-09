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

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.preferences.PHPInterpreterPreferencePage;
import org.eclipse.php.internal.ui.preferences.PHPVersionConfigurationBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class PHPVersionGroup implements SelectionListener {

	private final Group fGroup;
	private final Link fPreferenceLink;
	private final WizardPage fPage;
	protected Button fEnableProjectSettings;
	protected PHPVersionConfigurationBlock fConfigurationBlock;

	public PHPVersionGroup(Composite composite, WizardPage wizardBasePage) {

		fPage = wizardBasePage;
		fGroup = new Group(composite, SWT.NONE);
		fGroup.setFont(composite.getFont());
		GridLayout layout = new GridLayout();

		fGroup.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		fGroup.setLayoutData(data);
		fGroup.setText(PHPUIMessages.PHPVersionGroup_OptionBlockTitle);

		Composite checkLinkComposite = new Composite(fGroup, SWT.NONE);
		checkLinkComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		checkLinkComposite.setLayout(new GridLayout(2, false));

		fEnableProjectSettings = new Button(checkLinkComposite, SWT.CHECK
				| SWT.RIGHT);
		fEnableProjectSettings
				.setText(PHPUIMessages.PHPVersionGroup_EnableProjectSettings);
		fEnableProjectSettings.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.CENTER, false, false));

		fEnableProjectSettings.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateEnableState();
			}
		});

		fPreferenceLink = new Link(checkLinkComposite, SWT.NONE);
		fPreferenceLink.setFont(fGroup.getFont());
		fPreferenceLink.setLayoutData(new GridData(SWT.END, SWT.BEGINNING,
				true, false));
		fPreferenceLink
				.setText(PHPUIMessages.PHPVersionGroup_ConfigWorkspaceSettings);
		fPreferenceLink.addSelectionListener(this);

		Composite versionComposite = new Composite(fGroup, SWT.NONE);
		versionComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		versionComposite.setLayout(new GridLayout(2, false));

		fConfigurationBlock = createConfigurationBlock(
				getNewStatusChangedListener(), getProject(), null);
		fConfigurationBlock.createContents(versionComposite);
		fConfigurationBlock.setEnabled(false);
	}

	private void updateEnableState() {
		if (fEnableProjectSettings.getSelection()) {
			fConfigurationBlock.setEnabled(true);
		} else {
			fConfigurationBlock.performRevert();
			fConfigurationBlock.setEnabled(false);
		}
	}

	public void widgetSelected(SelectionEvent e) {
		widgetDefaultSelected(e);
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		String prefID = PHPInterpreterPreferencePage.PREF_ID;
		Map data = null;
		PreferencesUtil.createPreferenceDialogOn(fPage.getShell(), prefID,
				new String[] { prefID }, data).open();
		if (!fEnableProjectSettings.getSelection()) {
			fConfigurationBlock.performRevert();
		}
	}

	protected IProject getProject() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(
				"DUMMY______________Project"); //$NON-NLS-1$
	}

	protected IStatusChangeListener getNewStatusChangedListener() {
		return new IStatusChangeListener() {
			public void statusChanged(IStatus status) {
			}
		};
	}

	// FIXME : remove this redundant method
	public void setPropertiesInDataModel(IDataModel dataModel) {
		if (fEnableProjectSettings.getSelection()) {
			PHPVersion version = fConfigurationBlock.getPHPVersionValue();
			boolean useASPTags = fConfigurationBlock.getUseAspTagsValue();
			dataModel.setBooleanProperty(Keys.EDITOR_USE_ASP_TAGS, useASPTags);
			dataModel.setStringProperty(Keys.PHP_VERSION, version.getAlias());
		}
	}

	public void setPropertiesInDataModel(IProject project) {
		if (fEnableProjectSettings.getSelection()) {
			PHPVersion version = fConfigurationBlock.getPHPVersionValue();
			boolean useASPTags = fConfigurationBlock.getUseAspTagsValue();

			// FIXME : update project with values
			// dataModel.setBooleanProperty(Keys.EDITOR_USE_ASP_TAGS,
			// useASPTags);
			// dataModel.setStringProperty(Keys.PHP_VERSION, version);
		}
	}

	protected PHPVersionConfigurationBlock createConfigurationBlock(
			IStatusChangeListener listener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new PHPVersionConfigurationBlock(listener, project, container,
				true);
	}

	public PHPVersionConfigurationBlock getVersionBlock() {
		return fConfigurationBlock;
	}

	public void setVisible(boolean visible) {
		fGroup.setVisible(visible);
	}
}
