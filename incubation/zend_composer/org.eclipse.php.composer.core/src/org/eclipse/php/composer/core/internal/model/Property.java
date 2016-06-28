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
package org.eclipse.php.composer.core.internal.model;

import org.eclipse.php.composer.core.model.IProperty;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class Property extends ModelElement implements IProperty {

	private String value;

	@Override
	public String getValue() {
		return value != null && !value.isEmpty() ? value : null;
	}

	@Override
	public void setValue(String value, boolean update) {
		if ((value != null && !value.equals(this.value))
				|| (this.value != null && value == null)) {
			if (value != null && value.isEmpty() && this.value == null) {
				return;
			}
			this.value = value;
			if (update) {
				updateModel();
			}
		}
	}

}
