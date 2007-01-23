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
package org.eclipse.php.internal.core.phpModel.phpElementData;

public interface PHPClassData extends PHPCodeData {

	public static final String CONSTRUCTOR = "__construct";
	public static final String DESCRUCTOR = "__destruct";

	/**
	 * returns the super class.
	 */
	public PHPSuperClassNameData getSuperClassData();

	/**
	 * return the interfaces names.
	 */
	public PHPInterfaceNameData[] getInterfacesNamesData();

	/**
	 * returns all class vars.
	 */
	public PHPClassVarData[] getVars();

	/**
	 * returns all class consts.
	 */
	public PHPClassConstData[] getConsts();

	/**
	 * Returns all class functions as FunctionData objects.
	 */
	public PHPFunctionData[] getFunctions();

	/**
	 * Returns if the class have a constructor.
	 */
	public boolean hasConstructor();

	/**
	 * Returns the constructor or if it does not have return a default constructor.
	 */
	public PHPFunctionData getConstructor();

	public int getModifiers();

	public static interface PHPSuperClassNameData extends PHPCodeData {
	}

	public static interface PHPInterfaceNameData extends PHPCodeData {
	}

}