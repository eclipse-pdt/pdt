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
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

/**
 * Exports resources to a .zip file
 */
public class ZipFileExporter extends AbstractFileExporter {
	private ZipOutputStream outputStream;

	// private boolean useCompression = true;

	/**
	 * Create an instance of this class.
	 * 
	 * @param filename
	 *            java.lang.String
	 * @param compress
	 *            boolean
	 * @exception java.io.IOException
	 */
	public ZipFileExporter(PharPackage pharPackage) throws IOException {
		super(pharPackage);
		outputStream = new ZipOutputStream(fileContentStream);
	}

	/**
	 * Do all required cleanup now that we're finished with the currently-open
	 * .zip
	 * 
	 * @exception java.io.IOException
	 */
	public void finished() throws IOException {
		// super.finished();

		outputStream.close();
	}

	/**
	 * Write the contents of the file to the tar archive.
	 * 
	 * @param entry
	 * @param contents
	 * @exception java.io.IOException
	 * @exception org.eclipse.core.runtime.CoreException
	 */
	private void write(ZipEntry entry, InputStream contentStream)
			throws IOException {
		byte[] readBuffer = new byte[4096];

		outputStream.putNextEntry(entry);
		try {
			int n;
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

	private void completeEntry(ZipEntry entry, InputStream contentStream)
			throws IOException {
		byte[] readBuffer = new byte[4096];

		// If the contents are being compressed then we get the below for free.
		if (pharPackage.getCompressType() == PharConstants.NONE_COMPRESSED) {
			entry.setMethod(ZipEntry.STORED);
			int length = 0;
			CRC32 checksumCalculator = new CRC32();
			try {
				int n;
				while ((n = contentStream.read(readBuffer)) > 0) {
					checksumCalculator.update(readBuffer, 0, n);
					length += n;
				}
			} finally {
				if (contentStream != null) {
					contentStream.close();
				}
			}

			entry.setSize(length);
			entry.setCrc(checksumCalculator.getValue());
		}

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
		ZipEntry newEntry = new ZipEntry(destinationPath);
		completeEntry(newEntry, resource.getContents(false));
		write(newEntry, resource.getContents(false));
	}

	public void writeStub(IStub stub) throws IOException, CoreException {
		ZipEntry newEntry = new ZipEntry(PharConstants.STUB_PATH);
		completeEntry(newEntry, PharUtil.getStubInputStream(stub));
		write(newEntry, PharUtil.getStubInputStream(stub));
	}

	public void write(IFolder resource, String destinationPath)
			throws IOException, CoreException {
		ZipEntry newEntry = new ZipEntry(destinationPath);

		outputStream.putNextEntry(newEntry);
		outputStream.closeEntry();
	}

	public void doWriteSignature() throws IOException {

		byte[] signature = fileContentStream.getSignature();

		if (signature != null) {
			signature = PharUtil.getWholeSignature(signature, pharPackage);
			ZipEntry newEntry = new ZipEntry(PharConstants.SIGNATURE_PATH);
			completeEntry(newEntry, PharUtil.getInputStream(signature));
			write(newEntry, PharUtil.getInputStream(signature));
		}

	}
}
