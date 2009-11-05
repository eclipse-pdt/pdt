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

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class PharFileExporter extends AbstractFileExporter {
	PharBufferedOutputStream outputStream;
	Set<File> exportedField;

	public PharFileExporter(PharPackage pharPackage) throws IOException {
		super(pharPackage);
		outputStream = new PharBufferedOutputStream(fileContentStream,
				pharPackage);
		exportedField = new HashSet<File>();
	}

	public void finished() throws IOException {
		outputStream.close();
	}

	/**
	 * Write the passed resource to the current archive.
	 * 
	 * @param resource
	 *            org.eclipse.core.resources.IFile
	 * @param destinationPath
	 *            java.lang.String
	 * @exception java.io.IOException
	 * @exception org.eclipse.core.runtime.CoreException
	 */
	public void write(IFile resource, String destinationPath)
			throws IOException, CoreException {

		write(resource.getLocation().toFile(), destinationPath);
	}

	public void write(File file, String destinationPath) throws IOException,
			CoreException {

		PharAchiveOutputEntry newEntry = new PharAchiveOutputEntry(
				destinationPath);
		newEntry.setMethod(pharPackage.getCompressType());
		if (file.lastModified() != IResource.NULL_STAMP) {
			newEntry.setTime(file.lastModified() / 1000);
		}

		write(newEntry, file);
	}

	private void write(PharAchiveOutputEntry entry, File file)
			throws IOException, CoreException {
		if (exportedField.contains(file))
			return;
		exportedField.add(file);
		// final URI location = contents.getLocationURI();
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}

		InputStream contentStream = new BufferedInputStream(
				new FileInputStream(file));
		entry.setSize(file.length());
		outputStream.putNextEntry(entry);
		try {
			int n;
			byte[] readBuffer = new byte[4096];
			while ((n = contentStream.read(readBuffer)) > 0) {
				outputStream.write(readBuffer, 0, n);
			}
		} finally {
			if (contentStream != null) {
				contentStream.close();
			}
		}

		outputStream.closeEntry();
	}

	public void writeStub(IStub stub) throws IOException, CoreException {
		outputStream.writeStub(stub);
	}

	public void write(IFolder resource, String destinationPath)
			throws IOException, CoreException {

		write(resource.getLocation().toFile(), destinationPath);
	}

	public void writeSignature() throws IOException {
		outputStream.writeSignature();
	}

	@Override
	public void doWriteSignature() throws IOException {

	}
}
