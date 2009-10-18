/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.preferences.stepFilters;

/**
 * This file contains all the available types of the Debug Step Filters
 * 
 * @author yaronm
 * 
 */
public interface IStepFilterTypes {
	/**
	 * A Filter Type representation of a filter by pattern
	 */
	public final static int PATH_PATTERN = 9001;

	/**
	 * A Filter Type representation of a PHP Project filter
	 */
	public final static int PHP_PROJECT = 9002;

	/**
	 * A Filter Type representation of a PHP Project File filter
	 */
	public final static int PHP_PROJECT_FILE = 9003;

	/**
	 * A Filter Type representation of a PHP Project Folder filter
	 */
	public final static int PHP_PROJECT_FOLDER = 9004;

	/**
	 * A Filter Type representation of a PHP Include Path Variable Entry
	 */
	public final static int PHP_INCLUDE_PATH_VAR = 9005;

	/**
	 * A Filter Type representation of a File within a PHP Include Path Variable
	 * Entry
	 */
	public final static int PHP_INCLUDE_PATH_VAR_FILE = 9006;

	/**
	 * A Filter Type representation of a Folder within PHP Include Path Variable
	 * Entry
	 */
	public final static int PHP_INCLUDE_PATH_VAR_FOLDER = 9007;

	/**
	 * A Filter Type representation of a PHP Include Path Library Entry
	 */
	public final static int PHP_INCLUDE_PATH_LIBRARY = 9008;

	/**
	 * A Filter Type representation of a Folder within a PHP Include Path
	 * Library Entry
	 */
	public final static int PHP_INCLUDE_PATH_LIBRARY_FOLDER = 9009;

	/**
	 * A Filter Type representation of a File PHP Include Path Library Entry
	 */
	public final static int PHP_INCLUDE_PATH_LIBRARY_FILE = 9010;
}
