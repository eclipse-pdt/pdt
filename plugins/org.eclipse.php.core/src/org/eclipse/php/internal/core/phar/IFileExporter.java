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

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

/**
 * Interface for file exporters of different file formats. Used by the zip and
 * tar.gz exporters.
 * 
 * @since 3.1
 */
public interface IFileExporter {

	/**
	 * Do all required cleanup now that we are finished with the currently-open
	 * file.
	 * 
	 * @throws IOException
	 */
	public void finished() throws IOException;

	/**
	 * Write the passed resource to the current archive
	 * 
	 * @param resource
	 * @param destinationPath
	 * @throws IOException
	 * @throws CoreException
	 */
	public void write(IFile resource, String destinationPath)
			throws IOException, CoreException;

	public void writeStub(IStub stub) throws IOException, CoreException;

	public void write(IFolder resource, String destinationPath)
			throws IOException, CoreException;

	public void writeSignature() throws IOException;

}
