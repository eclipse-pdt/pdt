/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import org.eclipse.jface.viewers.IBasicPropertyConstants;

import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;


public class PHPElementProperties implements IPropertySource {

	private PHPCodeData fSource;

	// Property Descriptors
	static private IPropertyDescriptor[] fgPropertyDescriptors = new IPropertyDescriptor[2];
	{
		PropertyDescriptor descriptor;

		// resource name
		descriptor = new PropertyDescriptor(IBasicPropertyConstants.P_TEXT, "Name");
		descriptor.setAlwaysIncompatible(true);
		fgPropertyDescriptors[0] = descriptor;
	}

	public PHPElementProperties(PHPCodeData source) {
		fSource = source;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return fgPropertyDescriptors;
	}

	public Object getPropertyValue(Object name) {
		if (name.equals(IBasicPropertyConstants.P_TEXT)) {
			return fSource.getName();
		}
		return null;
	}

	public void setPropertyValue(Object name, Object value) {
	}

	public Object getEditableValue() {
		return this;
	}

	public boolean isPropertySet(Object property) {
		return false;
	}

	public void resetPropertyValue(Object property) {
	}
}
