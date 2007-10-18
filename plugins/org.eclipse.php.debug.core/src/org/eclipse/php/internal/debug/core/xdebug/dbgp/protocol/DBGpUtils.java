/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.browser.WebBrowserView;

public class DBGpUtils {

	private static Map encoded;

	static {
		encoded = Collections.synchronizedMap(new HashMap());
	}

	/**
	 * convert a file name to a URI
	 * @param fileName
	 * @return the file URI
	 */
	public static String getFileURIString(String fileName) {
		if (encoded.containsKey(fileName)) {
			return (String) encoded.get(fileName);
		}

		String fileURIStr = "";
		if (fileName == null || fileName.length() == 0) {
			return fileURIStr;
		}
		// make the fileName absolute in URI terms if it isn't
		if (fileName.charAt(0) != '/') {
			fileName = "/" + fileName;
		}

		try {
			URI uri = new URI("file", "", fileName, null, null);
			fileURIStr = uri.toASCIIString();
			encoded.put(fileName, fileURIStr);
		} catch (URISyntaxException e) {
			DBGpLogger.logException("URISyntaxException - 1", null, e);
		}
		return fileURIStr;
	}

	/**
	 * convert a file URI to standard file name
	 * @param fileURIStr
	 * @return the file name
	 */
	public static String getFilenameFromURIString(String fileURIStr) {
		String filePath = "";
		try {
			URI uri = new URI(fileURIStr);
			filePath = uri.getPath();
			if (filePath != null && filePath.length() > 2 && filePath.charAt(2) == ':') {
				filePath = filePath.substring(1);
			}
		} catch (URISyntaxException e) {
			DBGpLogger.logException("URISyntaxException - 2", null, e);
		}

		return filePath;
	}

	/**
	 * check for a good dbgp response
	 * @param caller
	 * @param resp
	 * @return
	 */
	public static boolean isGoodDBGpResponse(Object caller, DBGpResponse resp) {
		//TODO: Improvement: Support adding own messages, different error level
		//TODO: Improvement: Error_cant_get_property test to be optional
		if (resp == null) {
			return false;
		}
		
		if (resp.getType() == DBGpResponse.RESPONSE) {

			// ok, or cannot get property are good responses really.
			if (resp.getErrorCode() == DBGpResponse.ERROR_OK || resp.getErrorCode() == DBGpResponse.ERROR_CANT_GET_PROPERTY) {
				return true;
			} else {
				DBGpLogger.logError("DBGp Response Error: " + resp.getCommand() + ":=" + resp.getErrorCode() + " msg:" + resp.getErrorMessage(), caller, null);
			}
		} else {
			// init, parser failure, unknown type. Callers of this are not
			// expecting init either
			DBGpLogger.logError("Unexpected XML or parser failure: " + resp.getRawXML(), caller, null);
		}
		return false;
	}

	/**
	 * open the internal browser view if you can
	 * @param url
	 */
	public static void openInternalBrowserView(final String url) {

		// can't invoke this on the UI Thread
		final String viewId = "org.eclipse.ui.browser.view";
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				try {
					IViewPart viewPart = window.getActivePage().showView(viewId);
					if (viewPart instanceof WebBrowserView)
						((WebBrowserView) viewPart).setURL(url);
				} catch (PartInitException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
