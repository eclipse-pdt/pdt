package org.eclipse.php.internal.ui.documentation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class BuiltinDoc {
	
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.documentation.BuiltinDoc"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	private BuiltinDoc() {
	}
	
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return ""; //$NON-NLS-1$
		}
	}
}
