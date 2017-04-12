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

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream for reading files in ustar format (tar) compatible with the
 * specification in IEEE Std 1003.1-2001. Also supports long filenames encoded
 * using the GNU @LongLink extension.
 * 
 * @since 3.1
 */
public class TarInputStream extends FilterInputStream {
	private int nextEntry = 0;
	private int nextEOF = 0;
	private int filepos = 0;
	private int bytesread = 0;
	private TarEntry firstEntry = null;
	private String longLinkName = null;

	/**
	 * Creates a new tar input stream on the given input stream.
	 * 
	 * @param in
	 *            input stream
	 * @throws TarException
	 * @throws IOException
	 */
	public TarInputStream(InputStream in) throws TarException, IOException {
		super(in);

		// Read in the first TarEntry to make sure
		// the input is a valid tar file stream.
		firstEntry = getNextEntry();
	}

	/**
	 * Create a new tar input stream, skipping ahead to the given entry in the
	 * file.
	 * 
	 * @param in
	 *            input stream
	 * @param entry
	 *            skips to this entry in the file
	 * @throws TarException
	 * @throws IOException
	 */
	TarInputStream(InputStream in, TarEntry entry) throws TarException, IOException {
		super(in);
		skipToEntry(entry);
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
	 * Skips ahead to the position of the given entry in the file.
	 * 
	 * @param entry
	 * @returns false if the entry has already been passed
	 * @throws TarException
	 * @throws IOException
	 */
	boolean skipToEntry(TarEntry entry) throws TarException, IOException {
		int bytestoskip = entry.filepos - bytesread;
		if (bytestoskip < 0) {
			return false;
		}
		while (bytestoskip > 0) {
			long ret = in.skip(bytestoskip);
			if (ret < 0) {
				throw new IOException("early end of stream"); //$NON-NLS-1$
			}
			bytestoskip -= ret;
			bytesread += ret;
		}
		filepos = entry.filepos;
		nextEntry = 0;
		nextEOF = 0;
		// Read next header to seek to file data.
		getNextEntry();
		return true;
	}

	/**
	 * Returns true if the header checksum is correct.
	 * 
	 * @param header
	 * @return true if this header has a valid checksum
	 */
	private boolean isValidTarHeader(byte[] header) {
		long fileChecksum, calculatedChecksum;
		int pos, i;

		pos = 148;
		StringBuilder checksumString = new StringBuilder();
		for (i = 0; i < 8; i++) {
			if (header[pos + i] == ' ') {
				continue;
			}
			if (header[pos + i] == 0 || !Character.isDigit((char) header[pos + i])) {
				break;
			}
			checksumString.append((char) header[pos + i]);
		}
		if (checksumString.length() == 0) {
			return false;
		}
		if (checksumString.charAt(0) != '0') {
			checksumString.insert(0, '0');
		}
		try {
			fileChecksum = Long.decode(checksumString.toString()).longValue();
		} catch (NumberFormatException exception) {
			// This is not valid if it cannot be parsed
			return false;
		}

		// Blank out the checksum.
		for (i = 0; i < 8; i++) {
			header[pos + i] = ' ';
		}
		calculatedChecksum = headerChecksum(header);

		return (fileChecksum == calculatedChecksum);
	}

	/**
	 * Returns the next entry in the tar file. Does not handle GNU @LongLink
	 * extensions.
	 * 
	 * @return the next entry in the tar file
	 * @throws TarException
	 * @throws IOException
	 */
	TarEntry getNextEntryInternal() throws TarException, IOException {
		byte[] header = new byte[512];
		int pos = 0;
		int i;

		if (firstEntry != null) {
			TarEntry entryReturn = firstEntry;
			firstEntry = null;
			return entryReturn;
		}

		while (nextEntry > 0) {
			long ret = in.skip(nextEntry);
			if (ret < 0) {
				throw new IOException("early end of stream"); //$NON-NLS-1$
			}
			nextEntry -= ret;
			bytesread += ret;
		}

		int bytestoread = 512;
		while (bytestoread > 0) {
			int ret = super.read(header, 512 - bytestoread, bytestoread);
			if (ret < 0) {
				throw new IOException("early end of stream"); //$NON-NLS-1$
				// return null;
			}
			bytestoread -= ret;
			bytesread += ret;
		}

		// If we have a header of all zeros, this marks the end of the file.
		if (headerChecksum(header) == 0) {
			// We are at the end of the file.
			if (filepos > 0) {
				return null;
			}

			// Invalid stream.
			throw new TarException("not in tar format"); //$NON-NLS-1$
		}

		// Validate checksum.
		if (!isValidTarHeader(header)) {
			throw new TarException("not in tar format"); //$NON-NLS-1$
		}

		while (pos < 100 && header[pos] != 0) {
			pos++;
		}
		String name = new String(header, 0, pos, "UTF8"); //$NON-NLS-1$
		// Prepend the prefix here.
		pos = 345;
		if (header[pos] != 0) {
			while (pos < 500 && header[pos] != 0) {
				pos++;
			}
			String prefix = new String(header, 345, pos - 345, "UTF8"); //$NON-NLS-1$
			name = prefix + "/" + name; //$NON-NLS-1$
		}

		TarEntry entry;
		if (longLinkName != null) {
			entry = new TarEntry(longLinkName, filepos);
			longLinkName = null;
		} else {
			entry = new TarEntry(name, filepos);
		}
		if (header[156] != 0) {
			entry.setFileType(header[156]);
		}

		pos = 100;
		StringBuilder mode = new StringBuilder();
		for (i = 0; i < 8; i++) {
			if (header[pos + i] == 0) {
				break;
			}
			if (header[pos + i] == ' ') {
				continue;
			}
			mode.append((char) header[pos + i]);
		}
		if (mode.length() > 0 && mode.charAt(0) != '0') {
			mode.insert(0, '0');
		}
		try {
			long fileMode = Long.decode(mode.toString()).longValue();
			entry.setMode(fileMode);
		} catch (NumberFormatException nfe) {
			throw new TarException("", nfe); //$NON-NLS-1$
		}

		pos = 100 + 24;
		StringBuilder size = new StringBuilder();
		for (i = 0; i < 12; i++) {
			if (header[pos + i] == 0) {
				break;
			}
			if (header[pos + i] == ' ') {
				continue;
			}
			size.append((char) header[pos + i]);
		}
		if (size.charAt(0) != '0') {
			size.insert(0, '0');
		}
		int fileSize;
		try {
			fileSize = Integer.decode(size.toString()).intValue();
		} catch (NumberFormatException nfe) {
			throw new TarException("", nfe); //$NON-NLS-1$
		}

		entry.setSize(fileSize);
		nextEOF = fileSize;
		if (fileSize % 512 > 0) {
			nextEntry = fileSize + (512 - (fileSize % 512));
		} else {
			nextEntry = fileSize;
		}
		filepos += (nextEntry + 512);
		return entry;
	}

	/**
	 * Moves ahead to the next file in the tar archive and returns a TarEntry
	 * object describing it.
	 * 
	 * @return the next entry in the tar file
	 * @throws TarException
	 * @throws IOException
	 */
	public TarEntry getNextEntry() throws TarException, IOException {
		TarEntry entry = getNextEntryInternal();

		if (entry != null && entry.getName().equals("././@LongLink")) { //$NON-NLS-1$
			// This is a GNU extension for doing long filenames.
			// We get a file called ././@LongLink which just contains
			// the real pathname.
			byte[] longNameData = new byte[(int) entry.getSize()];
			int bytesread = 0;
			while (bytesread < longNameData.length) {
				int cur = read(longNameData, bytesread, longNameData.length - bytesread);
				if (cur < 0) {
					throw new IOException("early end of stream"); //$NON-NLS-1$
				}
				bytesread += cur;
			}

			int pos = 0;
			while (pos < longNameData.length && longNameData[pos] != 0) {
				pos++;
			}
			longLinkName = new String(longNameData, 0, pos, "UTF8"); //$NON-NLS-1$
			return getNextEntryInternal();
		}
		return entry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilterInputStream#read(byte[], int, int)
	 */
	public int read(byte[] b, int off, int len) throws IOException {
		if (nextEOF == 0) {
			return -1;
		}
		if (len > nextEOF) {
			len = nextEOF;
		}
		int size = super.read(b, off, len);
		nextEntry -= size;
		nextEOF -= size;
		bytesread += size;
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilterInputStream#read()
	 */
	public int read() throws IOException {
		byte[] data = new byte[1];
		int size = read(data, 0, 1);
		if (size < 0) {
			return size;
		}
		return data[0];
	}
}
