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

import org.eclipse.php.composer.ui.wizard.AbstractValidator;
import org.eclipse.php.composer.ui.wizard.AbstractWizardFirstPage;
import org.eclipse.php.composer.ui.wizard.ValidationException;
import org.eclipse.php.composer.ui.wizard.ValidationException.Severity;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public final class Validator extends AbstractValidator {

	public Validator(AbstractWizardFirstPage composerProjectWizardFirstPage) {
		super(composerProjectWizardFirstPage);
	}

	@Override
	protected void finishValidation() throws ValidationException {
		ComposerProjectWizardFirstPage first = (ComposerProjectWizardFirstPage) firstPage;
		final String vendor = first.settingsGroup.getVendor();
		if (vendor == null || vendor.length() == 0) {
			throw new ValidationException(Messages.Validator_EnterVendorName, Severity.WARNING);
		}
	}

	@Override
	protected void beginValidation() throws ValidationException {

	}
}