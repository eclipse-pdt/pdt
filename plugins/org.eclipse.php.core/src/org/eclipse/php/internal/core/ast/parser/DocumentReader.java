package org.eclipse.php.internal.core.ast.parser;

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
		} else if ((off < 0) || (off > b.length) || (len < 0)
				|| ((off + len) > b.length) || ((off + len) < 0)) {
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
				if (b != null) {
					b[off + i] = (char) c;
				}
			}
		} catch (IOException ee) {
		}
		return i;
	}

	@Override
	public void close() throws IOException {
	}
}
