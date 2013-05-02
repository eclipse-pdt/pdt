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
package org.eclipse.php.internal.debug.daemon;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.daemon.communication.CommunicationDaemonRegistry;
import org.osgi.framework.BundleContext;

/**
 * The main plug-in class to be used in the desktop. Note that since PDT 1.0 the
 * daemon plug-in waits for an outside invocation to start the daemons listening
 * (See startDaemons()).
 * 
 * @author Shalom Gibly
 */
public class DaemonPlugin extends Plugin {

	public static final String ID = "org.eclipse.php.debug.daemon"; //$NON-NLS-1$
	private static final int INTERNAL_ERROR = 10001;
	public static final boolean isDebugMode;
	static {
		String value = Platform
				.getDebugOption("org.eclipse.php.debug.daemon/debug"); //$NON-NLS-1$
		isDebugMode = value != null && value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	// The shared instance.
	private static DaemonPlugin plugin;
	// Hold an array of possible daemons.
	private ICommunicationDaemon[] daemons;

	/**
	 * The constructor.
	 */
	public DaemonPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		initializeAfterStart(context);
	}

	/**
	 * This method is used for later initialization. This trick should release
	 * plug-in start-up.
	 * 
	 * @param context
	 */
	void initializeAfterStart(final BundleContext context) {
		Job job = new Job("") { //$NON-NLS-1$
			protected IStatus run(IProgressMonitor monitor) {
				startDaemons(null);
				return Status.OK_STATUS;
			}
		};
		job.schedule(Job.LONG);
	}

	/**
	 * Initializes and starts the daemons that has the given daemonID. In case
	 * that the give id is null, starts all the registered daemons.
	 * 
	 * @param debuggerID
	 *            The debugger id, or null.
	 * @since PDT 1.0
	 */
	public void startDaemons(String debuggerID) {
		if (daemons == null) {
			daemons = CommunicationDaemonRegistry
					.getBestMatchCommunicationDaemons();
		}
		if (daemons != null) {
			for (int i = 0; i < daemons.length; i++) {
				if (debuggerID == null
						|| (daemons[i].isDebuggerDaemon() && debuggerID
								.equals(daemons[i].getDebuggerID()))) {
					daemons[i].init();
					daemons[i].startListen();
				}
			}
		}
	}

	public boolean isInitialized(String debuggerID) {
		if (daemons != null) {
			for (int i = 0; i < daemons.length; i++) {
				if (debuggerID == null
						|| (daemons[i].isDebuggerDaemon() && debuggerID
								.equals(daemons[i].getDebuggerID()))) {
					if (!daemons[i].isInitialized()) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public void makeSureDebuggerInitialized(String debuggerID) {
		while (true) {
			if (!isInitialized(debuggerID)) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			} else {
				break;
			}
		}
	}

	/**
	 * Stop the daemons that has the given daemonID. In case that the give id is
	 * null, stop all the registered daemons.
	 * 
	 * @param debuggerID
	 *            The debugger id, or null.
	 * @since PDT 1.0
	 */
	public void stopDaemons(String debuggerID) {
		if (daemons != null) {
			for (int i = 0; i < daemons.length; i++) {
				if (debuggerID == null
						|| (daemons[i].isDebuggerDaemon() && debuggerID
								.equals(daemons[i].getDebuggerID()))) {
					daemons[i].stopListen();
				}
			}
		}
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
		stopDaemons(null);
		daemons = null;
	}

	/**
	 * Make sure that the communication daemons are alive and listening. This
	 * method can be called before a communication session is requested in order
	 * to make sure that the requested communication daemon is up and running.
	 * 
	 * The method goes over the registered daemons and reset the socket for any
	 * communication daemon that is not listening.
	 * 
	 * The validation will be made on the daemons that have the given debuggerID
	 * or on all the daemons in case the id is null.
	 * 
	 * @param debuggerID
	 *            The debugger id, or null.
	 * @return True, if all the communication daemons passed the validation;
	 *         False, otherwise.
	 * @since PDT 1.0
	 */
	public boolean validateCommunicationDaemons(String debuggerID) {
		boolean validated = true;
		if (daemons != null) {
			for (int i = 0; i < daemons.length; i++) {
				if (debuggerID == null
						|| (daemons[i].isDebuggerDaemon() && debuggerID
								.equals(daemons[i].getDebuggerID()))) {
					if (!daemons[i].isListening()) {
						validated &= daemons[i].resetSocket();
					}
				}
			}
		}
		return validated;
	}

	/**
	 * Returns the DaemonPlugin ID string (e.g. org.eclipse.php.debug.daemon).
	 */
	public static String getID() {
		return ID;
	}

	/**
	 * Returns the shared instance.
	 */
	public static DaemonPlugin getDefault() {
		return plugin;
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR,
				"Debug Daemon plugin internal error", e)); //$NON-NLS-1$
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, message, null));
	}
}
