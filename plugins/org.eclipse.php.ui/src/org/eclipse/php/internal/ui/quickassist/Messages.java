package org.eclipse.php.internal.ui.quickassist;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.quickassist.messages"; //$NON-NLS-1$
	public static String VarCommentQuickAssistProcessor_name;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
