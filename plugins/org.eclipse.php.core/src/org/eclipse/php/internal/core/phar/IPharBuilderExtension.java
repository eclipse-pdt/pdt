/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phar;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public interface IPharBuilderExtension {

	/**
	 * Add the given file to the archive at the given path
	 * 
	 * @param file
	 *            the file to be written. It is guaranteed, that the file is not
	 *            a directory.
	 * @param destinationPath
	 *            the path for the file inside the archive
	 * @throws CoreException
	 *             thrown when the file could not be written
	 */
	public void writeFile(File file, IPath destinationPath)
			throws CoreException;
}
