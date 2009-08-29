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
import java.net.URI;
import java.util.zip.GZIPOutputStream;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.core.tar.CBZip2OutputStreamForPhar;
import org.eclipse.php.internal.core.tar.TarEntry;
import org.eclipse.php.internal.core.tar.TarOutputStream;

/**
 * Exports resources to a .tar.gz file.
 * 
 * @since 3.1
 */
public class TarFileExporter extends AbstractFileExporter {
	private TarOutputStream outputStream;

	// private GZIPOutputStream gzipOutputStream;

	/**
	 * Create an instance of this class.
	 * 
	 * @param filename
	 *            java.lang.String
	 * @param compress
	 *            boolean
	 * @exception java.io.IOException
	 */
	public TarFileExporter(PharPackage pharPackage) throws IOException {
		super(pharPackage);
		OutputStream os = fileContentStream;
		if (PharConstants.BZ2_COMPRESSED == pharPackage.getCompressType()) {
			// i think here should be CBZip2OutputStream,but it does not work
			// and CBZip2OutputStreamForPhar works well.
			// i do not know why
			os = new CBZip2OutputStreamForPhar(os);
		} else if (PharConstants.GZ_COMPRESSED == pharPackage.getCompressType()) {
			os = new GZIPOutputStream(os);
		}
		outputStream = new TarOutputStream(os);
	}

	/**
	 * Do all required cleanup now that we're finished with the currently-open
	 * .tar.gz
	 * 
	 * @exception java.io.IOException
	 */
	public void finished() throws IOException {
		// super.finished();
		outputStream.close();
		// if(gzipOutputStream != null) {
		// gzipOutputStream.close();
		// }
	}

	/**
	 * Write the contents of the file to the tar archive.
	 * 
	 * @param entry
	 * @param contents
	 * @exception java.io.IOException
	 * @exception org.eclipse.core.runtime.CoreException
	 */
	// private void write(TarEntry entry, IFile contents) throws IOException,
	// CoreException {
	//		
	// write(entry, contents.getContents(false));
	// }

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

		TarEntry newEntry = new TarEntry(destinationPath);
		if (resource.getLocalTimeStamp() != IResource.NULL_STAMP) {
			newEntry.setTime(resource.getLocalTimeStamp() / 1000);
		}
		ResourceAttributes attributes = resource.getResourceAttributes();
		if (attributes != null && attributes.isExecutable()) {
			newEntry.setMode(newEntry.getMode() | 0111);
		}
		if (attributes != null && attributes.isReadOnly()) {
			newEntry.setMode(newEntry.getMode() & ~0222);
		}

		final URI location = resource.getLocationURI();
		if (location == null) {
			throw new FileNotFoundException(resource.getFullPath().toOSString());
		}

		newEntry.setSize(EFS.getStore(location).fetchInfo().getLength());

		write(newEntry, resource.getContents(false));
	}

	public void writeStub(IStub stub) throws IOException, CoreException {
		ByteArrayInputStream stubInput = PharUtil.getStubInputStream(stub);
		TarEntry newEntry = new TarEntry(PharConstants.STUB_PATH);
		newEntry.setSize(stubInput.available());
		write(newEntry, stubInput);
	}

	private void write(TarEntry entry, InputStream contentStream)
			throws IOException {

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

	public void write(IFolder resource, String destinationPath)
			throws IOException, CoreException {
		TarEntry newEntry = new TarEntry(destinationPath);
		if (resource.getLocalTimeStamp() != IResource.NULL_STAMP) {
			newEntry.setTime(resource.getLocalTimeStamp() / 1000);
		}
		ResourceAttributes attributes = resource.getResourceAttributes();
		if (attributes != null && attributes.isExecutable()) {
			newEntry.setMode(newEntry.getMode() | 0111);
		}
		if (attributes != null && attributes.isReadOnly()) {
			newEntry.setMode(newEntry.getMode() & ~0222);
		}

		final URI location = resource.getLocationURI();
		if (location == null) {
			throw new FileNotFoundException(resource.getFullPath().toOSString());
		}

		outputStream.putNextEntry(newEntry);

		outputStream.closeEntry();
	}

	public void doWriteSignature() throws IOException {
		byte[] signature = fileContentStream.getSignature();

		if (signature != null) {
			signature = PharUtil.getWholeSignature(signature, pharPackage);
			TarEntry newEntry = new TarEntry(PharConstants.SIGNATURE_PATH);
			newEntry.setSize(signature.length);
			write(newEntry, PharUtil.getInputStream(signature));
		}

	}
}
