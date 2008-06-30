/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.adapter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.php.internal.ui.actions.filters.GenericActionFilter;
import org.eclipse.ui.IActionFilter;

/**
 * This adapter factory class is used to create a GenericActionFilter
 * when performing a right-click within the editor
 * @author yaronm
 */
public class PhpElementAdapterFactory implements IAdapterFactory {

	private static Map<Class<?>, Object> adapterType2Object = new HashMap<Class<?>, Object>(4);
	static {
		adapterType2Object.put(IActionFilter.class, new GenericActionFilter());
	}

	public PhpElementAdapterFactory() {
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		return adapterType2Object.get(adapterType);
	}

	@SuppressWarnings("unchecked")
	public Class[] getAdapterList() {
		Class[] classArray = new Class[adapterType2Object.size()];
		adapterType2Object.entrySet().toArray(classArray);
		return classArray;
	}
}
