/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.internal.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.composer.core.ComposerCorePlugin;
import org.eclipse.php.composer.core.model.ComposerRoot;
import org.eclipse.php.composer.core.model.IPackage;
import org.eclipse.php.composer.core.model.IPackages;
import org.eclipse.php.composer.core.validation.VersionConstraintValidator;
import org.eclipse.wst.validation.AbstractValidator;
import org.eclipse.wst.validation.ValidationResult;
import org.eclipse.wst.validation.ValidationState;
import org.eclipse.wst.validation.ValidatorMessage;

public class ComposerValidator extends AbstractValidator {

	private VersionConstraintValidator constraintValidator = new VersionConstraintValidator();

	public ComposerValidator() {
		super();
	}

	@Override
	public ValidationResult validate(IResource resource, int kind, ValidationState state, IProgressMonitor monitor) {
		ValidationResult result = new ValidationResult();
		if (resource.getType() != IResource.FILE) {
			return result;
		}
		IFile file = (IFile) resource;
		ComposerJsonValidator validator = new ComposerJsonValidator(file);
		validator.validate();
		try {
			// file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);
			if (!validator.isValid()) {
				int start = (int) validator.getCharOffset();
				long byteOffset = validator.getByteOffset();
				ValidatorMessage problem = ValidatorMessage.create(validator.getMessage(), resource);
				problem.setAttribute(IMarker.LINE_NUMBER, validator.getLine());
				problem.setAttribute(IMarker.CHAR_START, (int) validator.getCharOffset());
				problem.setAttribute(IMarker.LINE_NUMBER, validator.getLine());
				problem.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				problem.setAttribute(IMarker.CHAR_START, start);
				if (byteOffset <= 0) {
					problem.setAttribute(IMarker.CHAR_END, start + 1);
				} else {
					problem.setAttribute(IMarker.CHAR_END, start + (int) byteOffset);
				}

				result.add(problem);
			} else {
				ComposerRoot root = validator.getObject();
				if (root != null) {
					validatePackages(file, root.getRequire(), result);
					validatePackages(file, root.getRequireDev(), result);
					validatePackages(file, root.getConflict(), result);
					validatePackages(file, root.getReplace(), result);
					validatePackages(file, root.getProvide(), result);
				}
			}
		} catch (CoreException e) {
			ComposerCorePlugin.logError(e);
		}
		return result;
	}

	private void validatePackages(IFile file, IPackages packages, ValidationResult validationResult)
			throws CoreException {
		if (packages == null) {
			return;
		}
		for (IPackage p : packages.getPackages()) {
			String result = constraintValidator.validate(p);
			if (result != null) {
				ValidatorMessage problem = ValidatorMessage.create(String.format("%s: %s", p.getName(), result), file);
				problem.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
				problem.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				problem.setAttribute(IMarker.LINE_NUMBER, 1);
				validationResult.add(problem);
			}
		}
	}
}
