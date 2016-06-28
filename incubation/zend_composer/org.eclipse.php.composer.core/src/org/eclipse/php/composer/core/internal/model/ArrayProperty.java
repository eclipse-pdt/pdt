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

import org.eclipse.php.composer.core.model.IArrayProperty;

/**
 * @author Wojciech Galanciak, 2013
 *
 */
public class ArrayProperty extends ModelElement implements IArrayProperty {

	private String[] value;

	@Override
	public String[] getValue() {
		return value;
	}

	@Override
	public void setValue(String[] value, boolean update) {
		this.value = value;
		if (update) {
			updateModel();
		}
	}
}
