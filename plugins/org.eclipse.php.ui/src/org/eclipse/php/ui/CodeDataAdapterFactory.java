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
package org.eclipse.php.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.ui.IContributorResourceAdapter;

/**
 * Adapter factory for PHPCodeData. 
 * This factory is accepting requests for retrieving IResource (IFile) from the PHPCodeData.
 * 
 * @author shalom
 */
public class CodeDataAdapterFactory implements IAdapterFactory, IContributorResourceAdapter {

	private static Class[] PROPERTIES= new Class[] {
		IResource.class,
	};
	
	public Class[] getAdapterList() {
		return PROPERTIES;
	}
	
	public Object getAdapter(Object element, Class key) {
		CodeData codeData = getCodeData(element);
		if (IResource.class.equals(key)) {
			return getResource(codeData);
		}
		return null; 
	}
	
	private IResource getResource(CodeData element) {
		if (element == null) {
			return null;
		}
		UserData userData = element.getUserData();
		if (userData != null) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(userData.getFileName()));
		}
		return null;
    }

    public IResource getAdaptedResource(IAdaptable adaptable) {
    	CodeData codeData = getCodeData(adaptable);
    	if (codeData != null)
    		return getResource(codeData);

    	return null;
    }
    
	private CodeData getCodeData(Object element) {
		if (element instanceof CodeData) {
			return (CodeData)element;
		}
		return null;
	}
	
}
