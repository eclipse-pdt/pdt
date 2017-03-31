/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.formatter.core;

import java.io.IOException;
import java.io.Reader;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

public class DocumentReader extends Reader {

	private int index;
	private int size;
	private IDocument phpDocument;

	public DocumentReader(IDocument document, int offset, int length) {
		super();
		this.index = offset;
		this.size = offset + length;
		this.phpDocument = document;
	}

	@Override
	public int read() throws IOException {
		try {
			if (index < size) {
				return phpDocument.getChar(index++);
			}
			return -1;
		} catch (BadLocationException e) {
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public int read(char[] b, int off, int len) throws IOException {

		if (b == null) {
			throw new NullPointerException();
		} else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return 0;
		}

		int c = read();
		if (c == -1) {
			return -1;
		}
		b[off] = (char) c;

		int i = 1;
		try {
			for (; i < len; i++) {
				c = read();
				if (c == -1) {
					break;
				}
				b[off + i] = (char) c;
			}
		} catch (IOException ee) {
		}
		return i;
	}

	@Override
	public void close() throws IOException {
	}
}
