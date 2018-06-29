/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DBGpUtils {

	private static final String ENCODING_BASE64 = "base64"; //$NON-NLS-1$
	private static Map<String, String> encoded;

	static {
		encoded = Collections.synchronizedMap(new HashMap<String, String>());
	}

	/**
	 * Retrieves node's content, using encodingCharset to decode text content when
	 * node's "encoding" attribute is set to ENCODING_BASE64.
	 * 
	 * @param node
	 * @param encodingCharset
	 * @return
	 */
	public static String getEncodedStringValue(@NonNull Node node, @NonNull String encodingCharset) {
		String valueString = ""; //$NON-NLS-1$
		Node child = node.getFirstChild();
		if (child != null) {
			String valueData = child.getNodeValue();
			valueString = valueData;
			if (node.hasAttributes()) {
				NamedNodeMap attrs = node.getAttributes();
				Node attribute = attrs.getNamedItem("encoding"); //$NON-NLS-1$
				String nodeEncoding = null;
				if (attribute != null) {
					nodeEncoding = attribute.getNodeValue();
				}
				if (nodeEncoding != null && nodeEncoding.equalsIgnoreCase(ENCODING_BASE64)) {
					if (valueData != null && valueData.trim().length() != 0) {
						byte[] valueBytes = Base64.decode(valueData.trim());
						try {
							valueString = new String(valueBytes, encodingCharset);
						} catch (UnsupportedEncodingException e) {
							DBGpLogger.logException("Unexpected encoding problem", //$NON-NLS-1$
									node, e);
							valueString = new String(valueBytes);
						}
					}
				}
			}
		}
		return valueString;
	}

	/**
	 * convert a file name to a URI
	 * 
	 * @param fileName
	 * @return the file URI
	 */
	public static String getFileURIString(String fileName) {
		if (encoded.containsKey(fileName)) {
			return encoded.get(fileName);
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
