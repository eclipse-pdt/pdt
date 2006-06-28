package org.eclipse.php.ui;

import org.eclipse.osgi.util.NLS;

public class PHPUiPluginMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.ui.PHPUiPluginMessages"; //$NON-NLS-1$
	public static String PHPTemplateStore_error_message_nameEmpty;
	public static String PHPTemplateStore_error_title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, PHPUiPluginMessages.class);
	}

	private PHPUiPluginMessages() {
	}
}
