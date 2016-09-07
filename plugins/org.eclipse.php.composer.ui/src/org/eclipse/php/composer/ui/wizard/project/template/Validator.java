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
package org.eclipse.php.composer.ui.wizard.project.template;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.php.composer.ui.wizard.AbstractValidator;
import org.eclipse.php.composer.ui.wizard.AbstractWizardFirstPage;
import org.eclipse.php.composer.ui.wizard.ValidationException;
import org.eclipse.php.composer.ui.wizard.ValidationException.Severity;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
@SuppressWarnings("restriction")
public class Validator extends AbstractValidator {

	public Validator(AbstractWizardFirstPage composerProjectWizardFirstPage) {
		super(composerProjectWizardFirstPage);
	}

	@Override
	protected void beginValidation() throws ValidationException {

		IPath fileLocation = firstPage.PHPLocationGroup.getLocation();
		if (fileLocation.toPortableString().length() > 0) {
			final IFileHandle directory = environment.getFile(fileLocation);
			IPath futurepath = directory.getPath().append(firstPage.nameGroup.getName());
			File futureFile = futurepath.toFile();
			if ((futureFile.exists() && futureFile.isFile())
					|| (futureFile.exists() && futureFile.isDirectory() && futureFile.list().length > 0)) {
				throw new ValidationException(
						"The target directory is not empty. Unable to run \"create-project\" with a target directory containing files.",
						Severity.ERROR);
			}
		}
	}

	@Override
	protected void finishValidation() throws ValidationException {

	}
}
