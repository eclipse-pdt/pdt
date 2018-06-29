/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard.project.template;

import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.composer.ui.wizard.AbstractComposerWizard;
import org.eclipse.php.composer.ui.wizard.AbstractWizardFirstPage;
import org.eclipse.php.composer.ui.wizard.AbstractWizardSecondPage;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class PackageProjectCreationWizard extends AbstractComposerWizard {

	public PackageProjectCreationWizard() {

		setDefaultPageImageDescriptor(ComposerUIPluginImages.CREATE_PROJECT_FROM_PACKAGE);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		setWindowTitle(Messages.PackageProjectCreationWizard_Title);

	}

	@Override
	protected AbstractWizardFirstPage getFirstPage() {
		return new PackageProjectWizardFirstPage();
	}

	@Override
	protected AbstractWizardSecondPage getSecondPage() {
		return new PackageProjectWizardSecondPage(firstPage, Messages.PackageProjectCreationWizard_SecondPageTitle);
	}
}
