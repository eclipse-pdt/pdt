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
package org.eclipse.php.composer.core.validation;

import org.eclipse.php.composer.core.model.IProperty;

/**
 * Implementation of {@link IPropertyValidator} responsible for validation of
 * name property.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class NameValidator extends RequiredValidator {

	public NameValidator(String propertyName) {
		super(propertyName);
	}

	@Override
	public String validate(IProperty property) {
		String result = super.validate(property);
		if (result == null && "php".equalsIgnoreCase(property.getValue())) { //$NON-NLS-1$
			return "'php' is not a valid name"; //$NON-NLS-1$
		}
		return null;
	}

}
