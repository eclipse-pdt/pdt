/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

/**
 * Qualified names updateing
 * 
 * @author Roy, 2007
 */
public interface IQualifiedNameUpdating {

	/**
	 * Performs a dynamic check whether this refactoring object is capable of
	 * updating qualified names in non Java files. The return value of this
	 * method may change according to the state of the refactoring.
	 */
	public boolean canEnableQualifiedNameUpdating();

	/**
	 * If <code>canEnableQualifiedNameUpdating</code> returns <code>true</code>,
	 * then this method is used to ask the refactoring object whether references
	 * in non Java files should be updated. This call can be ignored if
	 * <code>canEnableQualifiedNameUpdating</code> returns <code>false</code>.
	 */
	public boolean getUpdateQualifiedNames();

	/**
	 * If <code>canEnableQualifiedNameUpdating</code> returns <code>true</code>,
	 * then this method is used to inform the refactoring object whether
	 * references in non Java files should be updated. This call can be ignored
	 * if <code>canEnableQualifiedNameUpdating</code> returns <code>false</code>
	 * .
	 */
	public void setUpdateQualifiedNames(boolean update);

	public String getFilePatterns();

	public void setFilePatterns(String patterns);

}
