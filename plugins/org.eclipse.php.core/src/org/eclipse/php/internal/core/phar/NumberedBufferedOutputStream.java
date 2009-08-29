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

/**
 * just for getting the file's size after it has been compressed. the 'current'
 * field presents the size of this OutputStream always updates the 'current'
 * field when something are writen.
 * 
 * @author zhaozw
 * 
 */
public class NumberedBufferedOutputStream extends OutputStream {

	// actual size of the OutputStream
	private int current = 0;
	private BufferedOutputStream innerOutputStream;

	// private CRC32 crc = new CRC32();
	// private long crcValue = 0;

	// MessageDigest digest;
	public NumberedBufferedOutputStream(OutputStream out) {
		innerOutputStream = new BufferedOutputStream(out);
		// crc.reset();
	}

	public NumberedBufferedOutputStream(OutputStream out, int size) {
		innerOutputStream = new BufferedOutputStream(out, size);
		// crc.reset();
	}

	@Override
	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	@Override
	public synchronized void write(byte[] b, int off, int len)
			throws IOException {
		innerOutputStream.write(b, off, len);
		current = current + len;
		// crc.update(b, off, len);
	}

	@Override
	public synchronized void write(int b) throws IOException {
		innerOutputStream.write(b);
		current++;
		// crc.update(b);
	}

	@Override
	public void close() throws IOException {
		// innerOutputStream.close();
		// crcValue = crc.getValue();
		// crc.reset();
	}

	@Override
	public void flush() throws IOException {
		innerOutputStream.flush();
	}

	public BufferedOutputStream getInnerOutputStream() {
		return innerOutputStream;
	}

	public int getCurrent() {
		return current;
	}

	// public long getCrc() {
	// return crcValue;
	// }
}
