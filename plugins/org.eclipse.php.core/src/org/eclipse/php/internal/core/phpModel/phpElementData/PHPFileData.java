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

import org.eclipse.php.internal.core.phpModel.parser.ComparableName;
import org.eclipse.php.internal.core.util.ICachable;

public interface PHPFileData extends PHPCodeData, ComparableName, ICachable {

	/**
	 * Returns all contained classes as ClassData objects.
	 */
	public PHPClassData[] getClasses();

	/**
	 * Returns all contained functions which aren't containd in classes as
	 * FunctionData objects.
	 */
	public PHPFunctionData[] getFunctions();

	/**
	 * Returns all of the variables information for the file
	 */
	public PHPVariablesTypeManager getVariableTypeManager();

	/**
	 * Returns all the include files
	 */
	public PHPIncludeFileData[] getIncludeFiles();

	/**
	 * Returns all the markers in th file.
	 */
	public IPHPMarker[] getMarkers();

	/**
	 * Returns all the PHP blocks in th file.
	 */
	public PHPBlock[] getPHPBlocks();

	/**
	 * Returns all the Constans.
	 */
	public PHPConstantData[] getConstants();

	/**
	 * Returns this PHPFileData's file last modified time apon parsing.
	 */
	public long getCreationTimeLastModified();

}