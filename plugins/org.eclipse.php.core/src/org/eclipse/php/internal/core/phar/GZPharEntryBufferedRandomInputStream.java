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

public class GZPharEntryBufferedRandomInputStream extends
		PharEntryBufferedRandomInputStream {
	byte[] header = new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0 };
	byte[] tailer;
	protected int headerIndex;
	protected int tailerIndex;

	public GZPharEntryBufferedRandomInputStream(File file, PharEntry pharEntry)
			throws IOException {
		super(file, pharEntry);
		tailer = comcat(pharEntry.getCrcByte(), pharEntry.getSizeByte());
		headerIndex = 0;
		tailerIndex = 0;
	}

	private static byte[] comcat(byte[] bs, byte[] content) {
		byte[] tmp = new byte[bs.length + content.length];
		System.arraycopy(bs, 0, tmp, 0, bs.length);
		System.arraycopy(content, 0, tmp, bs.length, content.length);
		return tmp;
	}

	@Override
	public int read() throws IOException {
		if (headerIndex < header.length) {
			return (header[headerIndex++] + 256) % 256;
		} else {
			return super.read();
		}
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int result = 0;
		if (headerIndex < header.length) {
			int j = 0;
			for (; headerIndex < header.length && j < len; headerIndex++, j++) {
				b[off + j] = (byte) ((header[headerIndex] + 256) % 256);
			}
			// all header have been read
			if (j < len) {
				result = super.read(b, off + j, len - j);
				result = j + result;
			} else {
				result = j;
			}
		} else {
			result = super.read(b, off, len);
		}
		// if reach the end,we read from tailer
		if (result < len && tailerIndex < tailer.length) {
			int j = 0;
			for (; tailerIndex < tailer.length && result + j < len; tailerIndex++, j++) {
				b[result + j] = (byte) ((tailer[tailerIndex] + 256) % 256);
				;
			}
			result = j + result;
		}
		return result;
	}
}
