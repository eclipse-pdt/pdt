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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class PharFileExporter extends AbstractFileExporter {
	PharBufferedOutputStream outputStream;
	// PharData pharData;
	Set<IResource> exportedField;

	public PharFileExporter(PharPackage pharPackage) throws IOException {
		super(pharPackage);
		outputStream = new PharBufferedOutputStream(fileContentStream,
				pharPackage);
		exportedField = new HashSet<IResource>();
	}

	public void finished() throws IOException {

		outputStream.close();
		// super.finished();
	}

	/**
	 * Write the contents of the file to the tar archive.
	 * 
	 * @param entry
	 * @param contents
	 * @exception java.io.IOException
	 * @exception org.eclipse.core.runtime.CoreException
	 */
	private void write(PharAchiveOutputEntry entry, IFile contents)
			throws IOException, CoreException {
		if (exportedField.contains(contents))
			return;
		exportedField.add(contents);
		final URI location = contents.getLocationURI();
		if (location == null) {
			throw new FileNotFoundException(contents.getFullPath().toOSString());
		}

		InputStream contentStream = contents.getContents(false);
		entry.setSize(EFS.getStore(location).fetchInfo().getLength());
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

		PharAchiveOutputEntry newEntry = new PharAchiveOutputEntry(
				destinationPath);
		newEntry.setMethod(pharPackage.getCompressType());
		if (resource.getLocalTimeStamp() != IResource.NULL_STAMP) {
			newEntry.setTime(resource.getLocalTimeStamp() / 1000);
		}

		write(newEntry, resource);
	}

	public void writeStub(IStub stub) throws IOException, CoreException {
		outputStream.writeStub(stub);
	}

	public void write(IFolder resource, String destinationPath)
			throws IOException, CoreException {

		PharAchiveOutputEntry newEntry = new PharAchiveOutputEntry(
				destinationPath);
		newEntry.setMethod(pharPackage.getCompressType());
		if (resource.getLocalTimeStamp() != IResource.NULL_STAMP) {
			newEntry.setTime(resource.getLocalTimeStamp() / 1000);
		}

		write(newEntry, resource);
	}

	private void write(PharAchiveOutputEntry entry, IFolder contents)
			throws IOException {
		if (exportedField.contains(contents))
			return;
		exportedField.add(contents);
		final URI location = contents.getLocationURI();
		if (location == null) {
			throw new FileNotFoundException(contents.getFullPath().toOSString());
		}

		entry.setSize(0);
		outputStream.putNextEntry(entry);

		outputStream.closeEntry();
	}

	public void writeSignature() throws IOException {
		outputStream.writeSignature();
	}

	@Override
	public void doWriteSignature() throws IOException {

	}
}
