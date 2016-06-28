/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.wizards;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.composer.core.IPackageSearchService;
import org.eclipse.php.composer.core.model.IPackage;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class AddDependencyWizard extends Wizard {

	private AddDependencyPage page;

	private IPackage selectedPackage;

	public AddDependencyWizard(IPackageSearchService searchService,
			List<IPackage> existingPackages) {
		this(searchService, existingPackages, null);
		setDefaultPageImageDescriptor(ComposerUIPlugin
				.getImageDescriptor(ComposerUIPlugin.IMAGE_DEPENDENCY_WIZ));
	}

	public AddDependencyWizard(IPackageSearchService searchService,
			List<IPackage> existingPackages, IPackage selectedPackage) {
		if (selectedPackage == null) {
			setWindowTitle(Messages.AddDependencyWizard_Title);
		} else {
			setWindowTitle(Messages.AddDependencyWizard_ModifyTitle);
		}
		setNeedsProgressMonitor(true);
		this.page = new AddDependencyPage(searchService, existingPackages,
				selectedPackage);
	}

	@Override
	public void addPages() {
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		selectedPackage = page.getPackage();
		return true;
	}

	public IPackage getPackage() {
		return selectedPackage;
	}

}
