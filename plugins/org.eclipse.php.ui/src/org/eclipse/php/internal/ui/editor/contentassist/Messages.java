package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.editor.contentassist.messages"; //$NON-NLS-1$
	public static String AutoActivationTrigger_0;
	public static String ParameterGuessingProposal_0;
	public static String PHPContentAssistant_0;
	public static String AssignToLocalCompletionProposal_name;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
