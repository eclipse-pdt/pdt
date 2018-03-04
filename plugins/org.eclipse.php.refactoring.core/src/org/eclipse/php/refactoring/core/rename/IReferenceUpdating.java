/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename;

public interface IReferenceUpdating extends ITextUpdating {

	public static final String UPDATECLASSNAME = "org.eclipse.php.refactoring.core.rename.resource.classname"; //$NON-NLS-1$
	public static final String NEEDUPDATECLASSNAME = "org.eclipse.php.refactoring.core.rename.resource.needclassname"; //$NON-NLS-1$

	/**
	 * This method is used to ask the refactoring object whether references to
	 * refactored files should be updated.
	 */
	public boolean getUpdateReferences();

	/**
	 * This method is used to inform the refactoring object whether references to
	 * the refactored file should be updated.
	 */
	public void setUpdateRefernces(boolean update);

	/**
	 * Set the attribute value
	 * 
	 * @param attribute
	 * @param value
	 */
	public void setAttribute(String attribute, String value);

	/**
	 * Gets the value corresponding to the key.
	 * 
	 * @param attribute
	 * @return the value.
	 */
	public String getAttribute(String attribute);
}
