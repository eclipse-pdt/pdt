package org.eclipse.php.internal.debug.ui.actions;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.debug.ui.actions.messages"; //$NON-NLS-1$
	public static String AddPHPExceptionBreakpointHandler_Dialog_title;
	public static String AddPHPExceptionBreakpointDialog_Choose_an_exception_filter_message;
	public static String AddPHPExceptionBreakpointDialog_Exception_description;
	public static String AddPHPExceptionBreakpointDialog_Matching_items;
	public static String AddPHPExceptionBreakpointDialog_Note;
	public static String AddPHPExceptionBreakpointDialog_Suspend_on_deprecated;
	public static String AddPHPExceptionBreakpointDialog_Suspend_on_error;
	public static String AddPHPExceptionBreakpointDialog_Suspend_on_notice;
	public static String SortByNameAction_1;
	public static String SortByNameAction_2;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
