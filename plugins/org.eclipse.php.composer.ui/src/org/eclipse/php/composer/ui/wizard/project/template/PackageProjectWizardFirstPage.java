/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard.project.template;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.converter.String2KeywordsConverter;
import org.eclipse.php.composer.ui.wizard.LocationGroup;
import org.eclipse.php.composer.ui.wizard.project.BasicSettingsGroup;
import org.eclipse.php.composer.ui.wizard.project.ComposerProjectWizardFirstPage;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.ui.wizards.CompositeData;
import org.eclipse.php.internal.ui.wizards.NameGroup;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardFirstPage.VersionGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class PackageProjectWizardFirstPage extends ComposerProjectWizardFirstPage implements IShellProvider {

	private Validator projectTemplateValidator;

	private Button overrideComposer;
	private boolean doesOverride = false;

	public PackageProjectWizardFirstPage() {
		super();
		setPageComplete(false);
		setTitle(Messages.PackageProjectWizardFirstPage_Title);
		setDescription(Messages.PackageProjectWizardFirstPage_Description);
	}

	@Override
	public void createControl(Composite parent) {

		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());
		composite.setLayout(initGridLayout(new GridLayout(1, false), false));
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		initialName = ""; //$NON-NLS-1$
		// create UI elements
		nameGroup = new NameGroup(composite, initialName, getShell());
		nameGroup.addObserver(this);
		PHPLocationGroup = new LocationGroup(composite, nameGroup, getShell());

		overrideComposer = new Button(composite, SWT.CHECK);
		overrideComposer.setText(Messages.PackageProjectWizardFirstPage_OverrideComposerJsonLabel);
		overrideComposer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doesOverride = overrideComposer.getSelection();
				settingsGroup.setEnabled(overrideComposer.getSelection());
			}
		});

		final Group group = new Group(composite, SWT.None);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(3, false));
		group.setText(""); //$NON-NLS-1$

		settingsGroup = new BasicSettingsGroup(group, getShell());
		settingsGroup.setEnabled(false);
		settingsGroup.addObserver(this);

		CompositeData data = new CompositeData();
		data.setParetnt(composite);
		data.setSettings(getDialogSettings());
		data.setObserver(PHPLocationGroup);

		versionGroup = new VersionGroup(this, composite, PHPVersion.PHP5_3) {
			@Override
			public IEnvironment getEnvironment() {
				return PackageProjectWizardFirstPage.this.getEnvironment();
			}

		};
		nameGroup.addObserver(PHPLocationGroup);

		// initialize all elements
		nameGroup.notifyObservers();
		// create and connect validator
		projectTemplateValidator = new Validator(this);
		nameGroup.addObserver(projectTemplateValidator);
		PHPLocationGroup.addObserver(projectTemplateValidator);

		Dialog.applyDialogFont(composite);

		setControl(composite);
		composerPackage = new ComposerPackage();
		keywordConverter = new String2KeywordsConverter(composerPackage);
		setHelpContext(composite);
	}

	@Override
	public void performFinish(final IProgressMonitor monitor) {

	}

	@Override
	protected void setHelpContext(Control container) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(container,
				ComposerUIPlugin.PLUGIN_ID + "." + "help_context_wizard_template_firstpage"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean doesOverrideComposer() {
		return doesOverride;
	}
}
