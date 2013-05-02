package org.eclipse.php.internal.debug.ui.console;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.debug.ui.console.messages"; //$NON-NLS-1$
	public static String PHPFileLink_0;
	public static String PHPFileLink_1;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
