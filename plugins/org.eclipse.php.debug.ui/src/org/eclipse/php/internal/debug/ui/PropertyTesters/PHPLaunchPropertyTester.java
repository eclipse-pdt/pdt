/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.PropertyTesters;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.ui.IEditorInput;

/**
 * A property tester for the launch shortcuts.
 * 
 * @author shalom
 */
public class PHPLaunchPropertyTester extends PropertyTester {
	
	private static final String PROPERTY = "launchablePHP"; //$NON-NLS-1$

	/**
	 * Executes the property test determined by the parameter <code>property</code>. 
	 * 
	 * @param receiver the receiver of the property test
	 * @param property the property to test
	 * @param args additional arguments to evaluate the property. If no arguments
	 *  are specified in the <code>test</code> expression an array of length 0
	 *  is passed
	 * @param expectedValue the expected value of the property. The value is either 
	 *  of type <code>java.lang.String</code> or a boxed base type. If no value was
	 *  specified in the <code>test</code> expressions then <code>null</code> is passed
	 * 
	 * @return returns <code>true<code> if the property is equal to the expected value; 
	 *  otherwise <code>false</code> is returned
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof List) {
			List<?> list = (List<?>) receiver;
			if (list.size() > 0) {
				Object obj = list.get(0);
				
				if (PROPERTY.equals(property)) {
					if (obj instanceof IEditorInput) {
						IModelElement modelElement = DLTKUIPlugin.getEditorInputModelElement((IEditorInput) obj);
						if (modelElement != null) {
							return PHPModelUtil.isPhpElement(modelElement);
						}
					}
					else if (obj instanceof IAdaptable) {
						IResource resource = (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
						if (resource instanceof IFile) {
							return PHPModelUtil.isPhpFile((IFile)resource);
						}
						IModelElement modelElement = (IModelElement) ((IAdaptable) obj).getAdapter(IModelElement.class);
						if (modelElement != null) {
							return PHPModelUtil.isPhpElement(modelElement);
						}
					}
				}
			}
		}
		return false;
	}
}
