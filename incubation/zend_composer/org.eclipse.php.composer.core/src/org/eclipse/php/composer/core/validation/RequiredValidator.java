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

import java.text.MessageFormat;

import org.eclipse.php.composer.core.internal.Messages;
import org.eclipse.php.composer.core.model.IProperty;

/**
 * Implementation of {@link IPropertyValidator} responsible for validation of
 * required properties.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class RequiredValidator implements IPropertyValidator {

	private String propertyName;

	public RequiredValidator(String propertyName) {
		super();
		this.propertyName = propertyName;
	}

	@Override
	public String validate(IProperty property) {
		if (property.getValue() == null
				|| (property.getValue() != null && property.getValue()
						.isEmpty())) {
			return MessageFormat.format(Messages.RequiredValidator_Error,
					propertyName);
		}
		return null;
	}

}
