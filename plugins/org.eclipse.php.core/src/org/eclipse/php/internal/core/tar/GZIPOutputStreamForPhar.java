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
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class GZIPOutputStreamForPhar extends DeflaterOutputStream implements
		CRCable {
	/**
	 * CRC-32 of uncompressed data.
	 */
	protected CRC32 crc = new CRC32();

	private boolean closed = false;

	/**
	 * Creates a new output stream with the specified buffer size.
	 * 
	 * @param out
	 *            the output stream
	 * @param size
	 *            the output buffer size
	 * @exception IOException
	 *                If an I/O error has occurred.
	 * @exception IllegalArgumentException
	 *                if size is <= 0
	 */
	public GZIPOutputStreamForPhar(OutputStream out, int size)
			throws IOException {
		super(out, new Deflater(Deflater.DEFAULT_COMPRESSION, true), size);
		crc.reset();
	}

	/**
	 * Creates a new output stream with a default buffer size.
	 * 
	 * @param out
	 *            the output stream
	 * @exception IOException
	 *                If an I/O error has occurred.
	 */
	public GZIPOutputStreamForPhar(OutputStream out) throws IOException {
		this(out, 512);
	}

	/**
	 * Writes array of bytes to the compressed output stream. This method will
	 * block until all the bytes are written.
	 * 
	 * @param buf
	 *            the data to be written
	 * @param off
	 *            the start offset of the data
	 * @param len
	 *            the length of the data
	 * @exception IOException
	 *                If an I/O error has occurred.
	 */
	public synchronized void write(byte[] buf, int off, int len)
			throws IOException {
		super.write(buf, off, len);
		crc.update(buf, off, len);
	}

	/**
	 * Finishes writing compressed data to the output stream without closing the
	 * underlying stream. Use this method when applying multiple filters in
	 * succession to the same output stream.
	 * 
	 * @exception IOException
	 *                if an I/O error has occurred
	 */
	public void finish() throws IOException {
		if (!def.finished()) {
			def.finish();
			while (!def.finished()) {
				int len = def.deflate(buf, 0, buf.length);
				if (len > 0)
					out.write(buf, 0, len);
			}
		}
	}

	/**
	 * Writes remaining compressed data to the output stream and closes the
	 * underlying stream.
	 * 
	 * @exception IOException
	 *                if an I/O error has occurred
	 */
	public void close() throws IOException {
		if (!closed) {
			finish();
			def.end();
			out.close();
			closed = true;
		}
	}

	public long getCrc() {
		return crc.getValue();
	}

}
