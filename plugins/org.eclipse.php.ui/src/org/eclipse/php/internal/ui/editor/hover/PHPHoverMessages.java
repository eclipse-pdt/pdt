package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to get NLSed messages.
 */
final class PHPHoverMessages extends NLS {

	private static final String BUNDLE_NAME = PHPHoverMessages.class.getName();

	private PHPHoverMessages() {
		// Do not instantiate
	}

	public static String AbstractAnnotationHover_action_configureAnnotationPreferences;
	public static String AbstractAnnotationHover_message_singleQuickFix;
	public static String AbstractAnnotationHover_message_multipleQuickFix;

	public static String JavadocHover_back;
	public static String JavadocHover_back_toElement_toolTip;
	public static String JavadocHover_back_toolTip;
	public static String JavadocHover_noAttachments;
	public static String JavadocHover_noAttachedJavadoc;
	public static String JavadocHover_noAttachedSource;
	public static String JavadocHover_noInformation;
	public static String JavadocHover_constantValue_hexValue;
	public static String JavadocHover_error_gettingJavadoc;
	public static String JavadocHover_forward;
	public static String JavadocHover_forward_toElement_toolTip;
	public static String JavadocHover_forward_toolTip;
	public static String JavadocHover_openDeclaration;
	public static String JavadocHover_showInJavadoc;

	public static String JavaTextHover_createTextHover;

	public static String NoBreakpointAnnotation_addBreakpoint;

	public static String NLSStringHover_NLSStringHover_missingKeyWarning;
	public static String NLSStringHover_NLSStringHover_PropertiesFileNotDetectedWarning;
	public static String NLSStringHover_open_in_properties_file;
	public static String ProblemHover_action_configureProblemSeverity;

	public static String ProblemHover_chooseSettingsTypeDialog_button_cancel;
	public static String ProblemHover_chooseSettingsTypeDialog_button_project;
	public static String ProblemHover_chooseSettingsTypeDialog_button_workspace;
	public static String ProblemHover_chooseSettingsTypeDialog_checkBox_dontShowAgain;
	public static String ProblemHover_chooseSettingsTypeDialog_message;
	public static String ProblemHover_chooseSettingsTypeDialog_title;

	static {
		NLS.initializeMessages(BUNDLE_NAME, PHPHoverMessages.class);
	}
}
