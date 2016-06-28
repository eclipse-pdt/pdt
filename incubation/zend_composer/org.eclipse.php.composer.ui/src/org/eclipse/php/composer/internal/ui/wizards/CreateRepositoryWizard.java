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

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.composer.core.model.IRepositories;
import org.eclipse.php.composer.core.model.IRepository;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class CreateRepositoryWizard extends Wizard {

	private IRepository repository;
	private CreateRepositoryPage page;

	public CreateRepositoryWizard(IRepository repository,
			IRepositories repositories) {
		setWindowTitle(Messages.CreateRepositoryWizard_Title);
		this.repository = repository;
		page = new CreateRepositoryPage(repository, repositories);
	}

	@Override
	public void addPages() {
		addPage(page);
	}

	public IRepository getRepository() {
		return repository;
	}

	@Override
	public boolean performFinish() {
		repository = page.getRepository();
		return true;
	}

	public void setRepository(IRepository repository) {
		this.repository = repository;
	}

}
