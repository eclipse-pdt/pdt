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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.debug.core.debugger.parameters.IWebDebugParametersInitializer;
import org.eclipse.php.internal.debug.core.*;
import org.eclipse.php.internal.debug.core.launching.PHPLaunch;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.daemon.DaemonPlugin;

/**
 * A debug session initializer. There are two ways to initialize debug session
 * (configurable in launch configuration dialog):
 * <ul>
 * <li>Open an internal Web browser that will issue a request to the debug
 * server</li>
 * <li>Use URL connection to send the request to the the debug server directly
 * </li>
 * </ul>
 */
@SuppressWarnings("restriction")
public class PHPWebServerDebuggerInitializer implements IDebuggerInitializer {

	private static final String URL_ENCODING = "UTF-8"; //$NON-NLS-1$

	public void debug(ILaunch launch) throws DebugException {
		DaemonPlugin.getDefault().makeSureDebuggerInitialized(null);
		IDebugParametersInitializer parametersInitializer = DebugParametersInitializersRegistry
				.getBestMatchDebugParametersInitializer(launch);
		if (launch instanceof PHPLaunch) {
			((PHPLaunch) launch).pretendRunning(true);
		}
		boolean openInBrowser = false;
		try {
			openInBrowser = launch.getLaunchConfiguration().getAttribute(IPHPDebugConstants.OPEN_IN_BROWSER, false);
		} catch (CoreException e) {
			// Should not happened
		}
		try {
			if (openInBrowser) {
				openBrowser(launch, parametersInitializer);
			} else {
				openUrlConnection(launch, parametersInitializer);
			}
		} finally {
			if (launch instanceof PHPLaunch)
				((PHPLaunch) launch).pretendRunning(false);
		}
	}

	/**
	 * Start the debug session by opening a browser that will actually trigger
	 * the URL connection to the debug server (with Zend Debugger installed).
	 * 
	 * @param launch
	 * @param parametersInitializer
	 * @throws DebugException
	 */
	protected void openBrowser(ILaunch launch, IDebugParametersInitializer parametersInitializer)
			throws DebugException {
		boolean runWithDebug = true;
		try {
			runWithDebug = launch.getLaunchConfiguration().getAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true);
		} catch (CoreException e) {
			// Should not happen
		}
		URL requestURL = parametersInitializer.getRequestURL(launch);
		String launchURL = requestURL.toString();
		if (runWithDebug && !ILaunchManager.RUN_MODE.equals(launch.getLaunchMode())) {
			String query = PHPLaunchUtilities.generateQuery(launch, parametersInitializer);
			if (launchURL.indexOf('?') == -1) {
				launchURL = launchURL + '?' + query;
			} else {
				launchURL = launchURL + '&' + query;
			}
		}
		PHPDebugUtil.openLaunchURL(launchURL);
	}

	/**
	 * Issue the request to the debug server using URL connection mechanism
	 * 
	 * @param launch
	 * @param parametersInitializer
	 * @throws DebugException
	 */
	protected void openUrlConnection(ILaunch launch, IDebugParametersInitializer parametersInitializer)
			throws DebugException {
		URL requestURL = parametersInitializer.getRequestURL(launch);
		try {
			// We only support this kind of debug session initializer here:
			if (parametersInitializer instanceof IWebDebugParametersInitializer) {
				IWebDebugParametersInitializer webParametersInitializer = (IWebDebugParametersInitializer) parametersInitializer;
				StringBuilder getParams = new StringBuilder("?"); //$NON-NLS-1$
				// Initialize debug parameters (using cookies):
				Hashtable<String, String> debugParameters = parametersInitializer.getDebugParameters(launch);
				if (debugParameters != null) {
					Enumeration<String> k = debugParameters.keys();
					while (k.hasMoreElements()) {
						String key = k.nextElement();
						String value = debugParameters.get(key);
						getParams.append(URLEncoder.encode(key, URL_ENCODING)).append('=')
								.append(URLEncoder.encode(value, URL_ENCODING));
						if (k.hasMoreElements()) {
							getParams.append('&');
						}
					}
				}
				// Initialize with additional GET parameters
				String requestMethod = webParametersInitializer.getRequestMethod(launch);
				if (IWebDebugParametersInitializer.GET_METHOD.equals(requestMethod)) {
					Hashtable<String, String> requestParameters = webParametersInitializer.getRequestParameters(launch);
					if (requestParameters != null) {
						Enumeration<String> k = requestParameters.keys();
						while (k.hasMoreElements()) {
							String key = k.nextElement();
							String value = requestParameters.get(key);
							getParams.append('&');
							getParams.append(URLEncoder.encode(key, URL_ENCODING)).append('=')
									.append(URLEncoder.encode(value, URL_ENCODING));
						}
					}
				}
				requestURL = new URL(requestURL.getProtocol(), requestURL.getHost(), requestURL.getPort(),
						requestURL.getPath() + getParams.toString());

				// Open the connection:
				if (PHPDebugPlugin.DEBUG) {
					System.out.println("Opening URL connection: " //$NON-NLS-1$
							+ requestURL.toString());
				}
				HttpURLConnection urlConection = (HttpURLConnection) requestURL.openConnection();
				urlConection.setDoInput(true);
				urlConection.setDoOutput(true);
				if (requestMethod != null) {
					urlConection.setRequestMethod(requestMethod);
				}
				// Add additional headers
				Hashtable<String, String> headers = webParametersInitializer.getRequestHeaders(launch);
				if (headers != null) {
					Enumeration<String> k = headers.keys();
					while (k.hasMoreElements()) {
						String key = k.nextElement();
						String value = URLEncoder.encode(headers.get(key), URL_ENCODING);
						if (PHPDebugPlugin.DEBUG) {
							System.out.println("Adding HTTP header: " + key //$NON-NLS-1$
									+ "=" + value); //$NON-NLS-1$
						}
						urlConection.addRequestProperty(key, value);
					}
				}
				// Set cookies
				Hashtable<String, String> cookies = webParametersInitializer.getRequestCookies(launch);
				if (cookies != null) {
					StringBuilder cookieBuf = new StringBuilder();
					Enumeration<String> k = cookies.keys();
					while (k.hasMoreElements()) {
						String key = k.nextElement();
						String value = cookies.get(key);
						cookieBuf.append(URLEncoder.encode(key, URL_ENCODING)).append('=')
								.append(URLEncoder.encode(value, URL_ENCODING));
						if (k.hasMoreElements()) {
							cookieBuf.append("; "); //$NON-NLS-1$
						}
					}
					if (PHPDebugPlugin.DEBUG) {
						System.out.println("Setting cookies: " //$NON-NLS-1$
								+ cookieBuf.toString());
					}
					urlConection.addRequestProperty("Cookie", //$NON-NLS-1$
							cookieBuf.toString());
				}
				DataOutputStream outputStream = new DataOutputStream(urlConection.getOutputStream());
				try {
					// Initialize with additional POST parameters
					if (requestMethod == IWebDebugParametersInitializer.POST_METHOD) {
						Hashtable<String, String> requestParameters = webParametersInitializer
								.getRequestParameters(launch);
						if (requestParameters != null) {
							StringBuilder postParams = new StringBuilder();
							Enumeration<String> k = requestParameters.keys();
							while (k.hasMoreElements()) {
								String key = k.nextElement();
								String value = requestParameters.get(key);
								postParams.append(URLEncoder.encode(key, URL_ENCODING)).append('=')
										.append(URLEncoder.encode(value, URL_ENCODING));
								if (k.hasMoreElements()) {
									postParams.append('&');
								}
							}
							outputStream.writeBytes(postParams.toString());
						}
					}
					// Add raw data
					String rawData = webParametersInitializer.getRequestRawData(launch);
					if (rawData != null) {
						outputStream.writeBytes(rawData);
					}
				} finally {
					outputStream.flush();
					outputStream.close();
				}
				String headerKey = urlConection.getHeaderFieldKey(1);
				if (headerKey == null) {
					Logger.log(Logger.WARNING, "No HeaderKey returned by server. Most likely not started"); //$NON-NLS-1$
					String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_1;
					throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
							IPHPDebugConstants.INTERNAL_ERROR, errorMessage, null));
				}
				for (int i = 1; (headerKey = urlConection.getHeaderFieldKey(i)) != null; i++) {
					if (headerKey.equals("X-Zend-Debug-Server")) { //$NON-NLS-1$
						String headerValue = urlConection.getHeaderField(headerKey);
						if (!headerValue.equals("OK")) { //$NON-NLS-1$
							Logger.log(Logger.WARNING, "Unexpected Header Value returned by Server. " //$NON-NLS-1$
									+ headerValue);
							String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_2 + " - " //$NON-NLS-1$
									+ headerValue;
							throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
									IPHPDebugConstants.INTERNAL_ERROR, errorMessage, null));
						}
						break;
					}
				}
				InputStream inputStream = urlConection.getInputStream();
				while (inputStream.read() != -1) {
					// do nothing on the content returned by standard stream
				}
				inputStream.close();
			}
		} catch (UnknownHostException e) {
			String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_4 + requestURL.getHost();
			Logger.logException(errorMessage, e);
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
					IPHPDebugConstants.INTERNAL_ERROR, errorMessage, e));
		} catch (ConnectException e) {
			String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_5
					+ requestURL.toString().substring(0, requestURL.toString().indexOf('?'));
			Logger.logException(errorMessage, e);
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
					IPHPDebugConstants.INTERNAL_ERROR, errorMessage, e));
		} catch (IOException e) {
			String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_5
					+ requestURL.toString().substring(0, requestURL.toString().indexOf('?'));
			Logger.logException(errorMessage, e);
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
					IPHPDebugConstants.INTERNAL_ERROR, errorMessage, e));
		} catch (Exception e) {
			Logger.logException("Unexpected exception communicating with Web server", e); //$NON-NLS-1$
			String errorMessage = e.getMessage();
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
					IPHPDebugConstants.INTERNAL_ERROR, errorMessage, e));
		}
	}

}
