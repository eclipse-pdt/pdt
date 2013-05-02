package org.eclipse.php.internal.server.ui.launching;

import java.security.MessageDigest;

import org.eclipse.php.internal.server.ui.Logger;

/**
 * MD5 utility class.
 * 
 * @author Shalom Gibly
 */
public class MD5 {

	/**
	 * Returns a MD5 digest in a hex format for the given string.
	 * 
	 * @param str
	 *            The string to digest
	 * @return MD5 digested string in a hex format; Null, in case of an error or
	 *         a null input
	 */
	public static String digest(String str) {
		if (str == null) {
			return null;
		}
		if (str.length() == 0) {
			return ""; //$NON-NLS-1$
		}
		String passwordDigest = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
			md5.reset();
			md5.update(str.getBytes());
			byte digest[] = md5.digest();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < digest.length; i++) {
				String hex = Integer.toHexString(0xff & digest[i]);
				if (hex.length() == 1) {
					buffer.append('0');
				}
				buffer.append(hex);
			}
			passwordDigest = buffer.toString();
		} catch (Exception e) {
			Logger.logException("Message digest error", e); //$NON-NLS-1$
		}
		if (passwordDigest == null) {
			return null;
		}
		return passwordDigest;
	}
}
