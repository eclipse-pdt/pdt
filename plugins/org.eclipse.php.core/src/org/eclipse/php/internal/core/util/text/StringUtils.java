/**
 *
 */
package org.eclipse.php.internal.core.util.text;

import java.util.Iterator;

/**
 * @author seva, 2007
 *
 */
public class StringUtils {

	private StringUtils() {
	}

	public static String implodeStrings(Iterable<String> strings, String glue) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Iterator<String> i = strings.iterator(); i.hasNext();) {
			String varClassName = i.next();
			stringBuffer.append(varClassName);
			if (i.hasNext()) {
				stringBuffer.append(glue);
			}
		}
		return stringBuffer.toString();
	}

}
