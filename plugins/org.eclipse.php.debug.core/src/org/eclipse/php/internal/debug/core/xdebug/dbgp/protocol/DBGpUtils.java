/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;

public class DBGpUtils {

	private static Map encoded;

	static {
		encoded = Collections.synchronizedMap(new HashMap());
	}

	/**
	 * convert a file name to a URI
	 * 
	 * @param fileName
	 * @return the file URI
	 */
	public static String getFileURIString(String fileName) {
		if (encoded.containsKey(fileName)) {
			return (String) encoded.get(fileName);
		}
		String fileURIStr = ""; //$NON-NLS-1$
		if (fileName == null || fileName.length() == 0) {
			return fileURIStr;
		}
		// make the fileName absolute in URI terms if it isn't
		if (fileName.charAt(0) != '/') {
			fileName = "/" + fileName; //$NON-NLS-1$
		}
		try {
			URI uri = new URI("file", "", fileName, null, null); //$NON-NLS-1$ //$NON-NLS-2$
			fileURIStr = adjustURIWhiteSpaces(uri.toASCIIString());
			encoded.put(fileName, fileURIStr);
		} catch (URISyntaxException e) {
			DBGpLogger.logException("URISyntaxException - 1", null, e); //$NON-NLS-1$
		}
		return fileURIStr;
	}

	/**
	 * convert a file URI to standard file name
	 * 
	 * @param fileURIStr
	 * @return the file name
	 */
	public static String getFilenameFromURIString(String fileURIStr) {
		String filePath = ""; //$NON-NLS-1$
		try {
			fileURIStr = adjustURIWhiteSpaces(fileURIStr);
			URI uri = new URI(fileURIStr);
			filePath = uri.getPath();
			if (filePath != null && filePath.length() > 2 && filePath.charAt(2) == ':') {
				filePath = filePath.substring(1);
			}
		} catch (URISyntaxException e) {
			DBGpLogger.logException("URISyntaxException - 2", null, e); //$NON-NLS-1$
		}
		return filePath;
	}

	/**
	 * check for a good dbgp response
	 * 
	 * @param caller
	 * @param resp
	 * @return
	 */
	public static boolean isGoodDBGpResponse(Object caller, DBGpResponse resp) {
		// TODO: Improvement: Support adding own messages, different error level
		// TODO: Improvement: Error_cant_get_property test to be optional
		if (resp == null) {
			return false;
		}

		if (resp.getType() == DBGpResponse.RESPONSE || resp.getType() == DBGpResponse.STREAM) {

			// ok, or cannot get property are good responses really.
			if (resp.getErrorCode() == DBGpResponse.ERROR_OK
					|| resp.getErrorCode() == DBGpResponse.ERROR_CANT_GET_PROPERTY) {
				return true;
			}
			// log it if it is not a problem with performing eval
			else if (resp.getErrorCode() != DBGpResponse.ERROR_CANT_PERFORM_EVAL) {
				DBGpLogger.logError("DBGp Response Error: " + resp.getCommand() //$NON-NLS-1$
						+ ":=" + resp.getErrorCode() + " msg:" //$NON-NLS-1$ //$NON-NLS-2$
						+ resp.getErrorMessage(), caller, null);
			}
		} else {
			// init, parser failure, unknown type, proxyError. Callers of this
			// are not
			// expecting init either
			DBGpLogger.logError("Unexpected XML or parser failure: " //$NON-NLS-1$
					+ resp.getRawXML(), caller, null);
		}
		return false;
	}

	private static String adjustURIWhiteSpaces(String fileName) {
		// Ensure that all white spaces are converted
		return fileName.replaceAll(" ", "%20"); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
