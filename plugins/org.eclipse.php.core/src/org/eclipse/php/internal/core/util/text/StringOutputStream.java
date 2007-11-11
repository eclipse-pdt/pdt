/**
 *
 */
package org.eclipse.php.internal.core.util.text;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author seva, 2007
 * A hook to catch System.err and System.out
 */
public class StringOutputStream extends OutputStream {

	protected List<StringBuffer> buffers = new ArrayList<StringBuffer>();
	protected StringBuffer buffer = new StringBuffer();

	public StringOutputStream() {
	}

	public void close() {
	}

	public void flush() {
		buffers.add(buffer);
		buffer = new StringBuffer();
	}

	public void write(byte[] b) {
		String str = new String(b);
		buffer.append(str);
	}

	public void write(byte[] b, int off, int len) {
		String str = new String(b, off, len);
		buffer.append(str);
	}

	public void write(int b) {
		String str = Integer.toString(b);
		buffer.append(str);
	}

	public String toString() {
		return buffer.toString();
	}

	public String getString(int i) {
		return buffers.get(i).toString();
	}

	public String[] getStrings() {
		List<String> strings = new ArrayList<String>(buffers.size());
		for (StringBuffer buffer : buffers) {
			strings.add(buffer.toString());
		}
		return strings.toArray(new String[strings.size()]);
	}
}