/**
 * 
 */
package org.eclipse.php.internal.core.util;

/**
 * This class gives workaround of the fact Path cannot handle relative paths (./ and ../).
 * It allows to replace .. & . with || & . and backwards.
 * 
 * @author seva
 *
 */
public class IncludeStringHack {

	public static String hack(String includeString) {
		includeString = includeString.replaceAll("\\.{2}([\\/])", "||$1");
		includeString = includeString.replaceAll("\\.{1}([\\/])", "|$1");
		
//		if (includeString.length() != 0 && includeString.charAt(0) == '.') {
//			if (includeString.length() != 1 && includeString.charAt(1) == '.') {
//				includeString = "||" + includeString.substring(2);
//			} else {
//				includeString = "|" + includeString.substring(1);
//			}
//		}
		return includeString;
	}

	public static String unhack(String includeString) {
		includeString = includeString.replaceAll("\\|{2}([\\/])", "..$1");
		includeString = includeString.replaceAll("\\|{1}([\\/])", ".$1");

//		if (includeString.length() != 0 && includeString.charAt(0) == '|') {
//			if (includeString.length() != 1 && includeString.charAt(1) == '|') {
//				includeString = ".." + includeString.substring(2);
//			} else {
//				includeString = "." + includeString.substring(1);
//			}
//		}
		return includeString;
	}

	public static boolean isHacked(String includeString) {
		if (includeString.length() != 0 && includeString.charAt(0) == '|') {
			return true;
		}
		return false;
	}

}
