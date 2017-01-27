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
package org.eclipse.php.composer.ui.wizard.project;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.php.composer.core.validation.ValidationUtils;

public final class AutoloadValidator implements Observer {

	private ComposerProjectWizardSecondPage secondPage;

	AutoloadValidator(ComposerProjectWizardSecondPage secondPage) {
		this.secondPage = secondPage;
	}

	@Override
	public void update(Observable observable, Object object) {

		String namespace = secondPage.autoloadGroup.getNamespace();

		if (!ValidationUtils.validateNamespace(namespace)) {
			secondPage.setErrorMessage(Messages.AutoloadValidator_ErrorMessage);
			secondPage.setMessage(Messages.AutoloadValidator_Message);
			secondPage.setPageComplete(false);
			return;
		}

		secondPage.setPageComplete(true);
		secondPage.setErrorMessage(null);
		secondPage.setMessage(null);

	}
}
