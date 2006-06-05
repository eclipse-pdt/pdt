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
package org.eclipse.php.ui.phpCodeData;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IActionFilter;

public class PhpCodeDataAdapterFactory implements IAdapterFactory {

	private static Map adapterType2Object = new HashMap(4);
	static {
		adapterType2Object.put(IActionFilter.class, new PhpCodeDataActionFilter());
	}
	
	public PhpCodeDataAdapterFactory() {}
	
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		return adapterType2Object.get(adapterType);
	}

	public Class[] getAdapterList() {
		Class[] classArray = new Class[adapterType2Object.size()];
		adapterType2Object.entrySet().toArray(classArray);
		return classArray;
	}
}
