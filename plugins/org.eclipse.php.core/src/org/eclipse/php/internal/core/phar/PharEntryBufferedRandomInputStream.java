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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.internal.registry.BufferedRandomInputStream;

/**
 * Wrapper for phar input tream
 * 
 * @author Zhao
 * 
 */
public class PharEntryBufferedRandomInputStream extends InputStream {

	private PharEntry pharEntry;
	protected int currentIndex;
	private int totalLength;
	private BufferedRandomInputStream bufferedRandomInputStream;

	public PharEntryBufferedRandomInputStream(File file, PharEntry pharEntry)
			throws IOException {
		// super(file);
		bufferedRandomInputStream = new BufferedRandomInputStream(file);
		this.pharEntry = pharEntry;
		totalLength = pharEntry.getCsize();
		if (bufferedRandomInputStream.skip(pharEntry.getPosition()) != pharEntry
				.getPosition()) {
			throw new IOException(Messages.PharEntry_Too_Long);
		}
	}

	@Override
	public int read() throws IOException {
		currentIndex++;
		if (currentIndex == totalLength) {
			return -1;
		}
		return bufferedRandomInputStream.read();
	}

	@Override
	public long skip(long n) throws IOException {
		return bufferedRandomInputStream.skip(n);
	}

	@Override
	public int read(byte[] b) throws IOException {
		return super.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		// if(flag){
		int readLength = totalLength - currentIndex;
		if (readLength <= 0)
			return -1;
		if (readLength < len) {
			len = readLength;
		} else {
			readLength = len;
		}
		currentIndex = currentIndex + readLength;
		int result = bufferedRandomInputStream.read(b, off, len);
		return result;
	}

	@Override
	public void close() throws IOException {
		bufferedRandomInputStream.close();
	}

	/**
	 * this method is called in GZIPInputStreamForPhar see
	 * GZIPInputStreamForPhar
	 * 
	 * @return
	 */
	public boolean isEnd() {
		return totalLength == currentIndex;
	}
}
