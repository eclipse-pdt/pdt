/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard.project;

import java.util.Observable;

import org.apache.commons.lang3.text.WordUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.core.ComposerPluginConstants;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.wizard.AbstractWizardFirstPage;
import org.eclipse.php.composer.ui.wizard.AbstractWizardSecondPage;
import org.eclipse.php.internal.ui.wizards.NameGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

public class ComposerProjectWizardSecondPage extends AbstractWizardSecondPage {

	protected AutoloadGroup autoloadGroup;
	private AutoloadValidator validator;

	public ComposerProjectWizardSecondPage(AbstractWizardFirstPage mainPage) {
		super(mainPage, "Dependencies");
	}

	@Override
	public void createControl(Composite parent) {

		int numColumns = 1;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		composite.setLayout(new GridLayout(numColumns, false));

		final Group group = new Group(composite, SWT.None);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(numColumns, false));
		group.setText("PSR-4");

		autoloadGroup = new AutoloadGroup(group, getShell());
		autoloadGroup.addObserver(this);

		validator = new AutoloadValidator(this);
		autoloadGroup.addObserver(validator);

		Dialog.applyDialogFont(composite);
		setControl(composite);
		((ComposerProjectWizardFirstPage) firstPage).settingsGroup.addObserver(this);
		((ComposerProjectWizardFirstPage) firstPage).nameGroup.addObserver(this);

		setHelpContext(composite);

	}

	@Override
	public void update(Observable observable, Object object) {

		if (observable instanceof BasicSettingsGroup || observable instanceof NameGroup) {
			ComposerProjectWizardFirstPage fPage = (ComposerProjectWizardFirstPage) firstPage;
			autoloadGroup.setNamespace(WordUtils.capitalize(fPage.settingsGroup.getVendor()) + "\\"
					+ WordUtils.capitalize(fPage.nameGroup.getName()) + "\\");
			return;
		}

		updateNamespace(autoloadGroup.getNamespace());
	}

	protected void updateNamespace(String namespace) {

		Namespace ns = new Namespace();
		ns.setNamespace(namespace);
		ns.add(ComposerPluginConstants.DEFAULT_SRC_FOLDER);

		firstPage.getPackage().getAutoload().getPsr4().clear();
		firstPage.getPackage().getAutoload().getPsr4().add(ns);
	}

	@Override
	protected String getPageTitle() {
		return "Autoloading settings";
	}

	@Override
	protected String getPageDescription() {
		return "Setup autoloading for your project.";
	}

	@Override
	protected void finishPage(IProgressMonitor monitor) throws Exception {

		monitor.setTaskName("Creating project structure");
		addComposerJson(monitor);
		monitor.worked(4);

		monitor.setTaskName("Installing composer.phar");
		installComposer(monitor);
		monitor.worked(4);

		monitor.setTaskName("Dumping autoloader");
		dumpAutoload(monitor);
		monitor.worked(2);
	}

	@Override
	protected void beforeFinish(IProgressMonitor monitor) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setHelpContext(Control control) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control,
				ComposerUIPlugin.PLUGIN_ID + "." + "help_project_wizard_autoload");
	}
}
