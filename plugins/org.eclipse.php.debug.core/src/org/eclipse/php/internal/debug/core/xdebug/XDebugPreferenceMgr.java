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
package org.eclipse.php.internal.debug.core.xdebug;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpPreferences;

public class XDebugPreferenceMgr {

	// general
	public static final String XDEBUG_PREF_PORT = PHPDebugPlugin.ID + ".xdebug_port"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_SHOWSUPERGLOBALS = PHPDebugPlugin.ID + ".xdebug_showSuperGlobals"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_ARRAYDEPTH = PHPDebugPlugin.ID + ".xdebug_arrayDepth"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_CHILDREN = PHPDebugPlugin.ID + ".xdebug_children"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_DATA = PHPDebugPlugin.ID + ".xdebug_data"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_MULTISESSION = PHPDebugPlugin.ID + ".xdebug_multisession"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_REMOTESESSION = PHPDebugPlugin.ID + ".xdebug_remotesession"; //$NON-NLS-1$
	// capture output
	public static final String XDEBUG_PREF_CAPTURESTDOUT = PHPDebugPlugin.ID + ".xdebug_capturestdout"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_CAPTURESTDERR = PHPDebugPlugin.ID + ".xdebug_capturestderr"; //$NON-NLS-1$
	// proxy
	public static final String XDEBUG_PREF_USEPROXY = PHPDebugPlugin.ID + ".xdebug_useproxy"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_IDEKEY = PHPDebugPlugin.ID + ".xdebug_idekey"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_PROXY = PHPDebugPlugin.ID + ".xdebug_proxy"; //$NON-NLS-1$
	public static final String XDEBUG_PREF_WARN_NO_LOCALHOST_SESSION = PHPDebugPlugin.ID
			+ ".no_localhost_remote_session"; //$NON-NLS-1$

	public static enum AcceptRemoteSession {
		off, localhost, any, prompt
	}

	public static final String[] remoteSessionOptions = {
			PHPDebugCoreMessages.XDebugConfigurationDialog_remoteSessionOption_off,
			PHPDebugCoreMessages.XDebugConfigurationDialog_remoteSessionOption_localhost,
			PHPDebugCoreMessages.XDebugConfigurationDialog_remoteSessionOption_any,
			PHPDebugCoreMessages.XDebugConfigurationDialog_remoteSessionOption_prompt };

	// just happens to match DBGp values so no conversion to DBGp Preferences
	// required.
	public static enum CaptureOutput {
		off, copy, redirect
	}

	public static final String[] captureOutputOptions = { PHPDebugCoreMessages.XDebugConfigurationDialog_capture_off,
			PHPDebugCoreMessages.XDebugConfigurationDialog_capture_copy,
			PHPDebugCoreMessages.XDebugConfigurationDialog_capture_redirect };

	public static void setDefaults() {
		IEclipsePreferences prefs = PHPDebugPlugin.getDefaultPreferences();
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_PORT, getPortDefault());
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS, showSuperGlobalsDefault());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH, getDepthDefault());
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION, useMultiSessionDefault());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN, getChildrenDefault());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_DATA, getDataDefault());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_REMOTESESSION, getAcceptRemoteSessionDefault());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDOUT, getCaptureDefault());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDERR, getCaptureDefault());
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_WARN_NO_LOCALHOST_SESSION, getWarnNoLocalhostSessionDefault());
		// Proxy config doesn't need its default values set here.
	}

	public static void applyDefaults(IEclipsePreferences prefs) {
		IEclipsePreferences defaults = PHPDebugPlugin.getDefaultPreferences();
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_PORT, defaults.getInt(XDebugPreferenceMgr.XDEBUG_PREF_PORT, 0));
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS,
				defaults.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS, false));
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH,
				defaults.getInt(XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH, 0));
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN,
				defaults.getInt(XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN, 0));
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_DATA, defaults.getInt(XDebugPreferenceMgr.XDEBUG_PREF_DATA, 0));
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION,
				defaults.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION, false));
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_REMOTESESSION,
				defaults.getInt(XDebugPreferenceMgr.XDEBUG_PREF_REMOTESESSION, 0));
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDOUT,
				defaults.getInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDOUT, 0));
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDERR,
				defaults.getInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDERR, 0));
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY,
				defaults.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY, false));
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY,
				defaults.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY, false));
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_PROXY,
				defaults.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_PROXY, false));
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_WARN_NO_LOCALHOST_SESSION,
				defaults.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_WARN_NO_LOCALHOST_SESSION, false));
	}

	/**
	 * create the DBGp preferences from the UI preferences.
	 * <ul>
	 * <li>Array Depth
	 * <li>Children
	 * <li>show super globals
	 * 
	 * @return
	 */
	public static DBGpPreferences createSessionPreferences() {
		DBGpPreferences sessionPrefs = new DBGpPreferences();
		int maxDepth = getInt(XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH);
		if (1 == maxDepth) {
			XDebugPreferenceMgr.setDefaults();
			maxDepth = XDebugPreferenceMgr.getDepthDefault();
		}
		sessionPrefs.setValue(DBGpPreferences.DBGP_MAX_DEPTH_PROPERTY, maxDepth);

		int maxChildren = getInt(XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN);
		sessionPrefs.setValue(DBGpPreferences.DBGP_MAX_CHILDREN_PROPERTY, maxChildren);

		int maxData = getInt(XDebugPreferenceMgr.XDEBUG_PREF_DATA);
		sessionPrefs.setValue(DBGpPreferences.DBGP_MAX_DATA_PROPERTY, maxData);

		boolean getSuperGlobals = getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS);
		sessionPrefs.setValue(DBGpPreferences.DBGP_SHOW_GLOBALS_PROPERTY, getSuperGlobals);

		// ui stored values are identical to DBGp expected values so no need to
		// convert
		int captureStdout = getInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDOUT);
		sessionPrefs.setValue(DBGpPreferences.DBGP_CAPTURE_STDOUT_PROPERTY, captureStdout);

		int captureStderr = getInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDERR);
		sessionPrefs.setValue(DBGpPreferences.DBGP_CAPTURE_STDERR_PROPERTY, captureStderr);

		return sessionPrefs;

	}

	// provide easy access to the preferences which are not DBGp Session
	// preferences.
	public static int getPort() {
		return getInt(XDebugPreferenceMgr.XDEBUG_PREF_PORT);
	}

	public static void setPort(int port) {
		setPort(PHPDebugPlugin.getInstancePreferences(), port);
	}

	public static void setPort(IEclipsePreferences preferences, int port) {
		preferences.putInt(XDebugPreferenceMgr.XDEBUG_PREF_PORT, port);
	}

	public static boolean useMultiSession() {
		return getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION);
	}

	public static boolean useProxy() {
		return getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY);
	}

	public static void setUseProxy(boolean newState) {
		PHPDebugPlugin.getInstancePreferences().putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_REMOTESESSION, newState);
	}

	public static AcceptRemoteSession getAcceptRemoteSession() {
		return AcceptRemoteSession.values()[getInt(XDebugPreferenceMgr.XDEBUG_PREF_REMOTESESSION)];
	}

	public static boolean getWarnNoLocalhostSession() {
		return getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_WARN_NO_LOCALHOST_SESSION);
	}

	private static boolean getBoolean(String key) {
		return Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID, key, false, null);
	}

	private static int getInt(String key) {
		return Platform.getPreferencesService().getInt(PHPDebugPlugin.ID, key, 0, null);
	}

	// the defaults for the UI preferences
	private static int getDepthDefault() {
		return DBGpPreferences.DBGP_MAX_DEPTH_DEFAULT;
	}

	private static int getChildrenDefault() {
		return DBGpPreferences.DBGP_MAX_CHILDREN_DEFAULT;
	}

	private static int getDataDefault() {
		return DBGpPreferences.DBGP_MAX_DATA_DEFAULT;
	}

	private static int getPortDefault() {
		return DBGpPreferences.DBGP_PORT_DEFAULT;
	}

	private static boolean showSuperGlobalsDefault() {
		return DBGpPreferences.DBGP_SHOW_GLOBALS_DEFAULT;
	}

	private static int getCaptureDefault() {
		// we use the UI definition here as it would be mapped
		// if required to the appropriate DBGp Value.
		return CaptureOutput.copy.ordinal();
	}

	private static boolean useMultiSessionDefault() {
		// this is not a DBGp property.
		return true;
	}

	private static int getAcceptRemoteSessionDefault() {
		// this is not a DBGp property
		return AcceptRemoteSession.localhost.ordinal();
	}

	private static boolean getWarnNoLocalhostSessionDefault() {
		return DBGpPreferences.WARN_NO_LOCALHOST_SESSION;
	}

}
