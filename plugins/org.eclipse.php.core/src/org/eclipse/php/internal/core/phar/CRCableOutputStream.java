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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;

import org.eclipse.php.internal.core.tar.CRCable;

/**
 * just for getting the file's crc32
 * 
 * @author zhaozw
 * 
 */
public class CRCableOutputStream extends OutputStream implements CRCable {

	private BufferedOutputStream innerOutputStream;
	private CRC32 crc = new CRC32();
	private long crcValue = 0;

	// MessageDigest digest;
	public CRCableOutputStream(OutputStream out) {
		innerOutputStream = new BufferedOutputStream(out);
		crc.reset();
	}

	@Override
	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	@Override
	public synchronized void write(byte[] b, int off, int len)
			throws IOException {
		innerOutputStream.write(b, off, len);
		crc.update(b, off, len);
	}

	@Override
	public synchronized void write(int b) throws IOException {
		innerOutputStream.write(b);
		crc.update(b);
	}

	@Override
	public void close() throws IOException {
		innerOutputStream.close();
		crcValue = crc.getValue();
		crc.reset();
	}

	@Override
	public void flush() throws IOException {
		innerOutputStream.flush();
	}

	public long getCrc() {
		return crcValue;
	}
}
