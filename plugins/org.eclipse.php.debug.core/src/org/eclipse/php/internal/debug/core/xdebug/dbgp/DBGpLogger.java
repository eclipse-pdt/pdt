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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

/**
 * A set of useful utilities 
 */
public class DBGpLogger {

	private static boolean debugOn = true;

	public static boolean debugState() {
		return true;
	}

	public static boolean debugCmd() {
		return true;
	}

	public static boolean debugResp() {
		return true;
	}

	public static boolean debugBP() {
		return true;
	}

	public static boolean debugSession() {
		return true;
	}

	/**
	 * output a debug message
	 * @param info the string to output
	 */
	public static void debug(String info) {
		if (debugOn) {
			System.out.println("-->DBGp: " + info);
		}
	}

	/**
	 * output an exception, does not output embedded exceptions
	 * @param exc the exception to output
	 */
	public static void debugException(Throwable exc) {
		debug(exc.getClass().toString() + ":" + exc.getMessage());
		if (exc != null) {
			StackTraceElement[] els = exc.getStackTrace();
			for (int i = 0; i < els.length; i++) {
				debug(els[i].toString());
			}
		}
	}

	public static void logException(String info, Object obj, Throwable exc) {
		ILog theLog = PHPDebugPlugin.getDefault().getLog();
		StringBuffer msg = new StringBuffer();
		if (obj != null) {
			msg.append(obj.getClass().toString());
			msg.append(" : ");
		}
		if (info != null) {
			msg.append(info);
		}
		debug(msg.toString());
		if (exc != null) {
			debugException(exc);
		}
		/*
		IStatus stat = new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, msg.toString(), exc);
		theLog.log(stat);
		*/
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bs);
		exc.printStackTrace(ps);
		IStatus stat = new Status(IStatus.ERROR, PHPDebugPlugin.ID, IStatus.ERROR, msg + "\n" + bs.toString(), null);
		theLog.log(stat);
	}

	/**
	 * Log an Error Message, message and exception sent to debug output as well.
	 * @param pluginId symbolic string name of the plug-in id eg. "getDefault().getBundle().getSymbolicName()"
	 * @param obj The object the exception occurred in (usually this)
	 * @param info Additional message information
	 * @param exc The exception
	 */
	public static void logError(String info, Object obj, Throwable exc) {
		doLog(info, obj, exc, IStatus.ERROR);
	}

	/**
	 * Log a Warning Message, message and exception sent to debug output as well.
	 * @param pluginId symbolic string name of the plug-in id eg. "getDefault().getBundle().getSymbolicName()"
	 * @param obj The object the exception occurred in (usually this)
	 * @param info Additional message information
	 * @param exc The exception
	 */
	public static void logWarning(String info, Object obj, Throwable exc) {
		doLog(info, obj, exc, IStatus.WARNING);
	}

	/**
	 * Log an Information Message, message sent to debug output as well.
	 * @param pluginId symbolic string name of the plug-in id eg. "getDefault().getBundle().getSymbolicName()"
	 * @param obj The object the exception occurred in (usually this)
	 * @param info Additional message information
	 */
	public static void logInfo(String info, Object obj) {
		doLog(info, obj, null, IStatus.INFO);
	}

	/*
	 * send the message to the Error log.
	 * 
	 * @param pluginId symbolic string name of the plug-in id eg. "getDefault().getBundle().getSymbolicName()"
	 * @param obj The object the exception occurred in (usually this)
	 * @param info Additional message information
	 * @param exc The exception
	 * @param type type of log message, ERROR, WARNING,INFO
	 */
	private static void doLog(String info, Object obj, Throwable exc, int type) {
		ILog theLog = PHPDebugPlugin.getDefault().getLog();
		StringBuffer msg = new StringBuffer();
		if (obj != null) {
			msg.append(obj.getClass().toString());
			msg.append(" : ");
		}
		if (info != null) {
			msg.append(info);
		}
		if (exc != null) {
			msg.append("Exception:");
			msg.append(exc.getClass().toString());
			msg.append(" msg: ");
			msg.append(exc.getMessage());
		}
		IStatus stat = new Status(type, PHPDebugPlugin.ID, type, msg.toString(), exc);
		debug(msg.toString());
		if (exc != null) {
			debugException(exc);
		}
		theLog.log(stat);
	}
}
