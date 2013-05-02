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

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.eclipse.php.internal.core.phar.PharEntryBufferedRandomInputStream;

/**
 * This class implements a stream filter for reading compressed data in the GZIP
 * file format.
 * 
 * @see InflaterInputStream
 * @version 1.33, 04/07/06
 * @author David Connelly
 * 
 */
public class GZIPInputStreamForPhar extends InflaterInputStream {
	/**
	 * CRC-32 for uncompressed data.
	 */
	protected CRC32 crc = new CRC32();

	/**
	 * Indicates end of input stream.
	 */
	protected boolean eos;

	private boolean closed = false;

	/**
	 * Check to make sure that this stream has not been closed
	 */
	private void ensureOpen() throws IOException {
		if (closed) {
			throw new IOException("Stream closed"); //$NON-NLS-1$
		}
	}

	/**
	 * Creates a new input stream with the specified buffer size.
	 * 
	 * @param in
	 *            the input stream
	 * @param size
	 *            the input buffer size
	 * @exception IOException
	 *                if an I/O error has occurred
	 * @exception IllegalArgumentException
	 *                if size is <= 0
	 */
	public GZIPInputStreamForPhar(InputStream in, int size) throws IOException {
		super(in, new Inflater(true), size);
		// usesDefaultInflater = true;
		// readHeader();
		crc.reset();
	}

	/**
	 * Creates a new input stream with a default buffer size.
	 * 
	 * @param in
	 *            the input stream
	 * @exception IOException
	 *                if an I/O error has occurred
	 */
	public GZIPInputStreamForPhar(InputStream in) throws IOException {
		this(in, 512);
	}

	/**
	 * Reads uncompressed data into an array of bytes. If <code>len</code> is
	 * not zero, the method will block until some input can be decompressed;
	 * otherwise, no bytes are read and <code>0</code> is returned.
	 * 
	 * @param buf
	 *            the buffer into which the data is read
	 * @param off
	 *            the start offset in the destination array <code>b</code>
	 * @param len
	 *            the maximum number of bytes read
	 * @return the actual number of bytes read, or -1 if the end of the
	 *         compressed input stream is reached
	 * @exception NullPointerException
	 *                If <code>buf</code> is <code>null</code>.
	 * @exception IndexOutOfBoundsException
	 *                If <code>off</code> is negative, <code>len</code> is
	 *                negative, or <code>len</code> is greater than
	 *                <code>buf.length - off</code>
	 * @exception IOException
	 *                if an I/O error has occurred or the compressed input data
	 *                is corrupt
	 */
	public int read(byte[] buf, int off, int len) throws IOException {
		ensureOpen();
		if (eos) {
			return -1;
		} 
		//if reach the end and still read here will throw an exception
		//nomally this will not happen,but it happens when run unit tests
		//so add an isEnd method to class PharEntryBufferedRandomInputStream
		if(in instanceof PharEntryBufferedRandomInputStream
				&& ((PharEntryBufferedRandomInputStream)in).isEnd()){
				len = -1;
		}else{
			len = super.read(buf, off, len);
		}
		if (len == -1) {
			// readTrailer();
			eos = true;
		} else {
			crc.update(buf, off, len);
		}
		return len;
	}

	/**
	 * Closes this input stream and releases any system resources associated
	 * with the stream.
	 * 
	 * @exception IOException
	 *                if an I/O error has occurred
	 */
	public void close() throws IOException {
		if (!closed) {
			inf.end();
			super.close();
			eos = true;
			closed = true;
		}
	}

}
