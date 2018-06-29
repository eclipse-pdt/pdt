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
package org.eclipse.php.composer.ui.wizard.project;

import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.composer.ui.wizard.AbstractComposerWizard;
import org.eclipse.php.composer.ui.wizard.AbstractWizardFirstPage;
import org.eclipse.php.composer.ui.wizard.AbstractWizardSecondPage;

public class ComposerProjectCreationWizard extends AbstractComposerWizard {

	public ComposerProjectCreationWizard() {
		setDefaultPageImageDescriptor(ComposerUIPluginImages.CREATE_PROJECT);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		setWindowTitle(Messages.ComposerProjectCreationWizard_Title);
	}

	@Override
	protected AbstractWizardFirstPage getFirstPage() {
		return new ComposerProjectWizardFirstPage();
	}

	@Override
	protected AbstractWizardSecondPage getSecondPage() {
		return new ComposerProjectWizardSecondPage(firstPage);
	}
}
