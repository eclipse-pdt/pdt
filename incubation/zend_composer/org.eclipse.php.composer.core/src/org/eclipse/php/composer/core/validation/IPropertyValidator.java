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
 * Class responsible for property validation.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public interface IPropertyValidator {

	/**
	 * Validates value of specified property.
	 * 
	 * @param property
	 * @return error message if validation failed; otherwise return
	 *         <code>null</code>
	 */
	String validate(IProperty property);

}
