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
package org.eclipse.php.internal.core.tar;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream for writing ustar archive files (tar) compatible with the
 * specification in IEEE Std 1003.1-2001.
 * 
 * @since 3.1
 */
public class TarOutputStream extends FilterOutputStream {

	private int byteswritten = 0;
	private int datapos = 0;
	private long cursize = 0;

	/**
	 * Creates a new tar output stream.
	 * 
	 * @param out
	 *            the stream to write to
	 */
	public TarOutputStream(OutputStream out) {
		super(out);
	}

	/**
	 * Close the output stream and write any necessary padding.
	 */
	public void close() throws IOException {
		// Spec says to write 1024 bytes of zeros at the end.
		byte[] zeros = new byte[1024];
		cursize = 1024;
		write(zeros, 0, 1024);

		// Default block size for tar files is 10240, so we have to
		// pad the end of the file to be a multiple of this size.
		if ((byteswritten % 10240) != 0) {
			int length = 10240 - (byteswritten % 10240);
			cursize = length;
			zeros = new byte[length];
			write(zeros, 0, length);
		}
		super.close();
	}

	/**
	 * Close the current entry in the tar file. Must be called after each entry
	 * is completed.
	 * 
	 * @throws IOException
	 */
	public void closeEntry() throws IOException {
		byte[] data = new byte[512];
		int len = 512 - datapos;
		if (len > 0 && datapos > 0) {
			cursize = len;
			write(data, 0, len);
		}
	}

	/**
	 * The checksum of a tar file header is simply the sum of the bytes in the
	 * header.
	 * 
	 * @param header
	 * @return checksum
	 */
	private long headerChecksum(byte[] header) {
		long sum = 0;
		for (int i = 0; i < 512; i++) {
			sum += header[i] & 0xff;
		}
		return sum;
	}

	/**
	 * Adds an entry for a new file in the tar archive.
	 * 
	 * @param e
	 *            TarEntry describing the file
	 * @throws IOException
	 */
	public void putNextEntry(TarEntry e) throws IOException {
		byte[] header = new byte[512];
		String filename = e.getName();
		String prefix = null;
		int pos, i;

		/* Split filename into name and prefix if necessary. */
		byte[] filenameBytes = filename.getBytes("UTF8"); //$NON-NLS-1$
		if (filenameBytes.length > 99) {
			int seppos = filename.lastIndexOf('/');
			if (seppos == -1) {
				throw new IOException("filename too long"); //$NON-NLS-1$
			}
			prefix = filename.substring(0, seppos);
			filename = filename.substring(seppos + 1);
			filenameBytes = filename.getBytes("UTF8"); //$NON-NLS-1$
			if (filenameBytes.length > 99) {
				throw new IOException("filename too long"); //$NON-NLS-1$
			}
		}

		/* Filename. */
		pos = 0;
		System.arraycopy(filenameBytes, 0, header, 0, filenameBytes.length);
		pos += 100;

		/* File mode. */
		StringBuffer mode = new StringBuffer(Long.toOctalString(e.getMode()));
		while (mode.length() < 7) {
			mode.insert(0, '0');
		}
		for (i = 0; i < 7; i++) {
			header[pos + i] = (byte) mode.charAt(i);
		}
		pos += 8;

		/* UID. */
		header[pos] = '0';
		pos += 8;

		/* GID. */
		header[pos] = '0';
		pos += 8;

		/* Length of the file. */
		String length = Long.toOctalString(e.getSize());
		for (i = 0; i < length.length(); i++) {
			header[pos + i] = (byte) length.charAt(i);
		}
		pos += 12;

		/* mtime */
		String mtime = Long.toOctalString(e.getTime());
		for (i = 0; i < mtime.length(); i++) {
			header[pos + i] = (byte) mtime.charAt(i);
		}
		pos += 12;

		/* "Blank" out the checksum. */
		for (i = 0; i < 8; i++) {
			header[pos + i] = ' ';
		}
		pos += 8;

		/* Link flag. */
		header[pos] = (byte) e.getFileType();
		pos += 1;

		/* Link destination. */
		pos += 100;

		/* Add ustar header. */
		String ustar = "ustar 00"; //$NON-NLS-1$
		for (i = 0; i < ustar.length(); i++) {
			header[pos + i] = (byte) ustar.charAt(i);
		}
		header[pos + 5] = 0;
		pos += 8;

		/* Username. */
		String uname = "nobody"; //$NON-NLS-1$
		for (i = 0; i < uname.length(); i++) {
			header[pos + i] = (byte) uname.charAt(i);
		}
		pos += 32;

		/* Group name. */
		String gname = "nobody"; //$NON-NLS-1$
		for (i = 0; i < gname.length(); i++) {
			header[pos + i] = (byte) gname.charAt(i);
		}
		pos += 32;

		/* Device major. */
		pos += 8;

		/* Device minor. */
		pos += 8;

		/* File prefix. */
		if (prefix != null) {
			byte[] prefixBytes = prefix.getBytes("UTF8"); //$NON-NLS-1$
			if (prefixBytes.length > 155) {
				throw new IOException("prefix too large"); //$NON-NLS-1$
			}
			System.arraycopy(prefixBytes, 0, header, pos, prefixBytes.length);
		}

		long sum = headerChecksum(header);
		pos = 100 + 8 + 8 + 8 + 12 + 12;
		String sumval = Long.toOctalString(sum);
		for (i = 0; i < sumval.length(); i++) {
			header[pos + i] = (byte) sumval.charAt(i);
		}

		cursize = 512;
		write(header, 0, 512);

		cursize = e.getSize();
	}

	/**
	 * Writes data for the current file into the archive.
	 */
	public void write(byte[] b, int off, int len) throws IOException {
		super.write(b, off, len);
		datapos = (datapos + len) % 512;
		byteswritten += len;
		cursize -= len;
		if (cursize < 0) {
			throw new IOException("too much data written for current file"); //$NON-NLS-1$
		}
	}
}
