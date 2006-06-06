package org.eclipse.php.debug.daemon;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.debug.daemon.communication.CommunicationDaemonRegistry;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
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
	private ICommunicationDaemon daemon;

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
		daemon = CommunicationDaemonRegistry.getBestMatchCommunicationDaemon();
		if (daemon != null) {
			daemon.init();
			daemon.startListen();
		}
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
		if (daemon != null) {
			daemon.stopListen();
			daemon = null;
		}
	}

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
