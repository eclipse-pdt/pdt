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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.core.tar.CBZip2OutputStreamForPhar;
import org.eclipse.php.internal.core.tar.CRCable;
import org.eclipse.php.internal.core.tar.GZIPOutputStreamForPhar;

/**
 * the mean class to write a pure phar
 * 
 * @author zhaozw
 * 
 */
public class PharBufferedOutputStream implements IAchiveOutputStream {
	// private static final byte[] DEFAULT_STUB =
	// "<?php\r\n__HALT_COMPILER();\r\n".getBytes();

	List<PharAchiveOutputEntry> entries = new ArrayList<PharAchiveOutputEntry>();
	PharAchiveOutputEntry currentEntry;
	boolean currentStart;
	SignatureBufferedOutputStream os;
	OutputStream currentOutputStream;
	OutputStream manifestOutputStream;
	OutputStream fileDescriptionOutputStream;
	NumberedBufferedOutputStream fileContentStream;
	int currentIndex = 0;
	private File fileContent;
	private File manifest;
	private File fileDescription;
	PharPackage pharPackage;
	int fileNumber = 0;

	public PharBufferedOutputStream(
			SignatureBufferedOutputStream fileContentStream2,
			PharPackage pharPackage) throws IOException {
		this.os = fileContentStream2;
		manifest = File.createTempFile("temp1", ".tmp"); //$NON-NLS-1$ //$NON-NLS-2$
		manifestOutputStream = new BufferedOutputStream(new FileOutputStream(
				manifest));

		fileDescription = File.createTempFile("temp2", ".tmp"); //$NON-NLS-1$ //$NON-NLS-2$
		fileDescriptionOutputStream = new BufferedOutputStream(
				new FileOutputStream(fileDescription));

		fileContent = File.createTempFile("temp3", ".tmp"); //$NON-NLS-1$ //$NON-NLS-2$
		fileContentStream = new NumberedBufferedOutputStream(
				new FileOutputStream(fileContent));
		this.pharPackage = pharPackage;
	}

	public void writeStub(IStub stub) throws IOException, CoreException {
		stub.write(os);
	}

	public void close() throws IOException {
		os.close();
	}

	private void mergeFiles() throws IOException {
		fileDescriptionOutputStream.close();

		writeManifest();
		manifestOutputStream.close();

		fileContentStream.getInnerOutputStream().close();

		writeTempFileToStream(manifest);
		writeTempFileToStream(fileDescription);
		writeTempFileToStream(fileContent);
		manifest.delete();
		fileDescription.delete();
		fileContent.delete();
	}

	private void writeManifest() throws IOException {

		int aliasLength = 0;
		if (pharPackage.getAlias() != null) {
			aliasLength = pharPackage.getAlias().length();
		}
		// fileNumber4 + version2 + bitmap4 + aliaslength4 + metadatalength4 =
		// 18
		int manifestLength = (int) (18 + aliasLength + fileDescription.length());
		writeInt(manifestOutputStream, manifestLength);
		writeInt(manifestOutputStream, fileNumber);

		// write Version
		manifestOutputStream.write(PharUtil.getStubVersionBytes(pharPackage
				.getStubVersion()));
		// manifestOutputStream.write(new byte[] { 17, 0 });
		// writeString(manifestOutputStream,pharData.getStubVersion());
		// write bitmap
		manifestOutputStream.write(PharUtil.getGlobalBitmap(pharPackage));

		writeInt(manifestOutputStream, aliasLength);
		if (aliasLength != 0) {
			writeString(manifestOutputStream, pharPackage.getAlias());
		}
		writeInt(manifestOutputStream, 0);
	}

	private void writeTempFileToStream(File file) throws IOException {

		InputStream contentStream = new BufferedInputStream(
				new FileInputStream(file));
		try {
			int n;
			byte[] readBuffer = new byte[4096];
			while ((n = contentStream.read(readBuffer)) > 0) {
				os.write(readBuffer, 0, n);
			}
		} finally {
			if (contentStream != null) {
				contentStream.close();
			}
		}

	}

	public void putNextEntry(IAchiveOutputEntry output) throws IOException {

		assert output instanceof PharAchiveOutputEntry;
		fileNumber++;

		currentEntry = (PharAchiveOutputEntry) output;
		entries.add(currentEntry);
		currentStart = true;
	}

	private void setEntryInfo() throws IOException {
		// if(currentEntry != null){
		// fileContentStream.flush();
		currentEntry.setCompressedSize(fileContentStream.getCurrent()
				- currentIndex);
		currentEntry.setCrc(getCrc(currentOutputStream));
		currentIndex = fileContentStream.getCurrent();
		// }
	}

	private long getCrc(OutputStream currentOutputStream2) {
		return ((CRCable) currentOutputStream2).getCrc();
	}

	public void write(byte[] b, int off, int len) throws IOException {

		// if currentStart is true,it should initialize the currentOutputStream
		// depends on the method of currentEntry
		if (currentStart) {
			currentStart = false;
			if (currentEntry.getMethod() == PharConstants.NONE_COMPRESSED) {
				currentOutputStream = fileContentStream;
			} else if (currentEntry.getMethod() == PharConstants.BZ2_COMPRESSED) {
				currentOutputStream = new CBZip2OutputStreamForPhar(
						fileContentStream);
			} else if (currentEntry.getMethod() == PharConstants.GZ_COMPRESSED) {
				currentOutputStream = new GZIPOutputStreamForPhar(
						fileContentStream);
			}
			currentOutputStream = new CRCableOutputStream(currentOutputStream);
		}

		currentOutputStream.write(b, off, len);
	}

	public void closeEntry() throws IOException {
		// the close method is called to force to flush the cache when the
		// phar's content is compressed
		currentOutputStream.close();
		setEntryInfo();
		writeCurrentEntry();
	}

	// write the entry info to fileDescriptionOutputStream
	private void writeCurrentEntry() throws IOException {

		writeInt(fileDescriptionOutputStream, currentEntry.getName().length());
		writeString(fileDescriptionOutputStream, currentEntry.getName());
		writeInt(fileDescriptionOutputStream, (int) currentEntry.getSize());
		writeInt(fileDescriptionOutputStream, (int) currentEntry.getTime());
		writeInt(fileDescriptionOutputStream, (int) currentEntry
				.getCompressedSize());
		writeInt(fileDescriptionOutputStream, (int) currentEntry.getCrc());
		fileDescriptionOutputStream
				.write(PharUtil.getBitmapBytes(currentEntry));
		// writeInt(fileDescriptionOutputStream, (int)
		// currentEntry.getMethod());
		writeInt(fileDescriptionOutputStream, 0);
	}

	private void writeString(OutputStream os, String name) throws IOException {
		os.write(name.getBytes());
	}

	/*
	 * Writes integer in Intel byte order to a byte array, starting at a given
	 * offset.
	 */
	private void writeInt(OutputStream os, int i) throws IOException {
		writeShort(os, i & 0xffff);
		writeShort(os, (i >> 16) & 0xffff);
	}

	/*
	 * Writes short integer in Intel byte order to a byte array, starting at a
	 * given offset
	 */
	private void writeShort(OutputStream os, int s) throws IOException {
		os.write((byte) (s & 0xff));
		os.write((byte) ((s >> 8) & 0xff));
	}

	public void writeSignature() throws IOException {
		mergeFiles();
		os.flush();
		byte[] signature = os.getSignature();

		if (signature != null) {

			os.write(PharUtil.getWholeSignature(signature, pharPackage));
			// os
			// .write(Digest.DIGEST_MAP.get(pharData.getSignature())
			// .getBitMap());
			// os.write("GBMB".getBytes());
		}

	}
}
