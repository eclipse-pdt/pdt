package org.eclipse.php.internal.ui.compare;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.compare.messages"; //$NON-NLS-1$
	public static String PHPTextMergeViewer_Viewer_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
