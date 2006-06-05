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
package org.eclipse.php.core.project;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

public interface IIncludePathEntry {

	
	int K_SOURCE = 1;
	/**
	 * Kind constant for a binary path root. Indicates this
	 * root only contains binary files.
	 */
	int K_BINARY = 2;
	
	int IPE_LIBRARY = 1;

	/**
	 * Entry kind constant describing a includepath entry identifying a
	 * required project.
	 */
	int IPE_PROJECT = 2;

	/**
	 * Entry kind constant describing a includepath entry identifying a
	 * folder containing package fragments with source code
	 * to be compiled.
	 */
	int IPE_SOURCE = 3;

	/**
	 * Entry kind constant describing a includepath entry defined using
	 * a path that begins with a includepath variable reference.
	 */
	int IPE_VARIABLE = 4;

	/**
	 * Entry kind constant describing a includepath entry representing
	 * a name includepath container.
	 * 
	 * @since 2.0
	 */
	int IPE_CONTAINER = 5;
	
	int getContentKind();
	int getEntryKind();
	
	IPath getPath();
	IResource getResource();
	boolean isExported();
	String validate();
	
}
