/**
 *
 */
package org.eclipse.php.internal.core.util.text;

import java.io.OutputStream;

/**
 * @author seva, 2007
 * A hook to catch System.err and System.out
 */
public class StringOutputStream extends OutputStream {

	protected StringBuffer buf = new StringBuffer();

	public StringOutputStream() {
	}

	public void close() {
	}

	public void flush() {
	}

	public void write(byte[] b) {
		String str = new String(b);
		this.buf.append(str);
	}

	public void write(byte[] b, int off, int len) {
		String str = new String(b, off, len);
		this.buf.append(str);
	}

	public void write(int b) {
		String str = Integer.toString(b);
		this.buf.append(str);
	}

	public String toString() {
		return buf.toString();
	}
}