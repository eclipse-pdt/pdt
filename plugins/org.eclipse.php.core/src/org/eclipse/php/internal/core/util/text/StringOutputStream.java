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

	protected List<String> strings = new ArrayList<String>();
	protected StringBuffer buffer = new StringBuffer();

	public void flush() {
		strings.add(buffer.toString());
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
		return strings.get(i);
	}

	public String[] getStrings() {
		return strings.toArray(new String[strings.size()]);
	}
}