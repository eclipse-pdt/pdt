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

import org.eclipse.php.composer.core.PackageVersion;
import org.eclipse.php.composer.core.internal.Messages;
import org.eclipse.php.composer.core.model.IProperty;

/**
 * Implementation of {@link IPropertyValidator} responsible for validation of
 * version property.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class VersionValidator implements IPropertyValidator {

	@Override
	public String validate(IProperty property) {
		String val = property.getValue();
		if (val != null) {
			PackageVersion version = PackageVersion.byName(val);
			if (version == PackageVersion.UNKNOWN || version.getMajor() == -1
					|| version.getMinor() == -1 || version.getBuild() == -1) {
				return Messages.VersionValidator_Error;
			}
		}
		return null;
	}

}
