/*******************************************************************************
 * Copyright (c) 2020 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.provisional.registry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.wst.jsdt.web.core.internal.validation.JsValidator;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;

public class JSDTValidator implements IValidator, IExecutableExtension {
	private IValidator validator;

	public JSDTValidator() {
		try {
			validator = new JsValidator();
		} catch (NoClassDefFoundError e) {
			validator = null;
		}
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		if (validator != null) {
			((IExecutableExtension) validator).setInitializationData(config, propertyName, data);
		}

	}

	@Override
	public void cleanup(IReporter reporter) {
		if (validator != null) {
			validator.cleanup(reporter);
		}
	}

	@Override
	public void validate(IValidationContext helper, IReporter reporter) throws ValidationException {
		if (validator != null) {
			validator.validate(helper, reporter);
		}
	}

}
