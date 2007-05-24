package org.eclipse.php.internal.debug.daemon;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.daemon.communication.CommunicationDaemonRegistry;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class DaemonPlugin extends Plugin {

	public static final String ID = "org.eclipse.php.debug.daemon"; //$NON-NLS-1$
	private static final int INTERNAL_ERROR = 10001;
	public static final boolean isDebugMode;
	static {
		String value = Platform.getDebugOption("org.eclipse.php.debug.daemon/debug"); //$NON-NLS-1$
		isDebugMode = value != null && value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	//The shared instance.
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
		daemons = CommunicationDaemonRegistry.getBestMatchCommunicationDaemons();
		if (daemons != null) {
			for (int i = 0; i < daemons.length; i++) {
				daemons[i].init();
				daemons[i].startListen();
			}
		}
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
		if (daemons != null) {
			for (int i = 0; i < daemons.length; i++) {
				daemons[i].stopListen();
			}
		}
		daemons = null;
	}

	/**
	 * Make sure that the communication daemons are alive and listening. 
	 * This method can be called before a communication session is requested in order
	 * to make sure that the requested communication daemon is up and running.
	 * 
	 * The method goes over the registered daemons and reset the socket for any communication 
	 * daemon that is not listening.
	 * 
	 * @return True, if all the communication daemons passed the validation; False, otherwise.
	 */
	public boolean validateCommunicationDaemons() {
		boolean validated = true;
		if (daemons != null) {
			for (int i = 0; i < daemons.length; i++) {
				if (!daemons[i].isListening()) {
					validated &= daemons[i].resetSocket();
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
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, "Debug Daemon plugin internal error", e)); //$NON-NLS-1$
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, message, null));
	}

}
