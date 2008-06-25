/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core;

import org.eclipse.php.internal.core.language.PHPVersion;

public interface PHPCoreConstants {
	
	public static final String PLUGIN_ID = PHPCorePlugin.ID;
	public static final String IP_VARIABLE_INITIALIZER_EXTPOINT_ID = "includePathVariables"; //$NON-NLS-1$

	//
	// Project Option names
	//
	public static final String PHPOPTION_DEFAULT_ENCODING = PLUGIN_ID + ".defaultEncoding"; //$NON-NLS-1$
	public static final String PHPOPTION_CONTEXT_ROOT = PLUGIN_ID + ".contextRoot"; //$NON-NLS-1$
	public static final String PHPOPTION_INCLUDE_PATH = PLUGIN_ID + ".includePath"; //$NON-NLS-1$
	//
	// Project Option values
	//
	public static final String PHP4 = PHPVersion.PHP4;
	public static final String PHP5 = PHPVersion.PHP5;

	public static final String TASK_PRIORITIES = PLUGIN_ID + ".taskPriorities"; //$NON-NLS-1$
	public static final String TASK_PRIORITY_HIGH = "HIGH"; //$NON-NLS-1$
	public static final String TASK_PRIORITY_LOW = "LOW"; //$NON-NLS-1$
	public static final String TASK_PRIORITY_NORMAL = "NORMAL"; //$NON-NLS-1$
	public static final String TASK_TAGS = PLUGIN_ID + ".taskTags"; //$NON-NLS-1$
	public static final String TASK_CASE_SENSITIVE = PLUGIN_ID + ".taskCaseSensitive"; //$NON-NLS-1$
	public static final String DEFAULT_TASK_TAGS = "TODO,FIXME,XXX,@todo"; //$NON-NLS-1$
	public static final String DEFAULT_TASK_PRIORITIES = "NORMAL,HIGH,NORMAL,NORMAL"; //$NON-NLS-1$
	public static final String ENABLED = "enabled"; //$NON-NLS-1$
	public static final String DISABLED = "disabled"; //$NON-NLS-1$
	public static final String DEFAULT_INDENTATION_SIZE = "1"; //$NON-NLS-1$

	public static final String INCLUDE_PATH_VARIABLE_NAMES = PLUGIN_ID + ".includePathVariableNames"; //$NON-NLS-1$
	public static final String INCLUDE_PATH_VARIABLE_PATHS = PLUGIN_ID + ".includePathVariablePaths"; //$NON-NLS-1$

	public static final String RESERVED_INCLUDE_PATH_VARIABLE_NAMES = PLUGIN_ID + ".includePathReservedVariableNames"; //$NON-NLS-1$
	public static final String RESERVED_INCLUDE_PATH_VARIABLE_PATHS = PLUGIN_ID + ".includePathReservedVariablePaths"; //$NON-NLS-1$

	public static final String PHP_OPTIONS_PHP_VERSION = "phpVersion"; //$NON-NLS-1$
	public static final String PHP_OPTIONS_PHP_ROOT_CONTEXT = "phpRootContext"; //$NON-NLS-1$

	public static final String FORMATTER_USE_TABS = PLUGIN_ID + ".phpForamtterUseTabs"; //$NON-NLS-1$
	public static final String FORMATTER_INDENTATION_SIZE = PLUGIN_ID + ".phpForamtterIndentationSize"; //$NON-NLS-1$

	// workspace locale and default local preferences identifiers
	public final static String WORKSPACE_LOCALE = PLUGIN_ID + ".workspaceLocale"; //$NON-NLS-1$
	public final static String WORKSPACE_DEFAULT_LOCALE = PLUGIN_ID + ".workspaceDefaultLocale"; //$NON-NLS-1$

	public static final String RSE_TEMP_PROJECT_NATURE_ID = "org.eclipse.rse.ui.remoteSystemsTempNature"; //$NON-NLS-1$
	
	/**
	 * A named preference that controls if the PHP code assist adds import
	 * statements.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_ADDIMPORT = "contentAssistAddImport"; //$NON-NLS-1$

	/**
	 * A named preference that controls if the PHP code assist gets auto activated.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_AUTOACTIVATION = "contentAssistAutoactivation"; //$NON-NLS-1$

	/**
	 * A name preference that holds the auto activation delay time in milliseconds.
	 * <p>
	 * Value is of type <code>Integer</code>.
	 * </p>
	 */
	public final static String CODEASSIST_AUTOACTIVATION_DELAY = "contentAssistAutoactivationDelay"; //$NON-NLS-1$

	/**
	 * A name preference that holds the auto activation triggers for PHP code
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public final static String CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP = "contentAssistAutoactivationTriggersPHP"; //$NON-NLS-1$

	/**
	 * A name preference that holds the auto activation triggers for PHPDOC
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public final static String CODEASSIST_AUTOACTIVATION_TRIGGERS_PHPDOC = "contentAssistAutoactivationTriggersPHPDoc"; //$NON-NLS-1$

	
	/**
	 * A named preference that controls if the php code assist inserts a
	 * proposal automatically if only one proposal is available.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_AUTOINSERT = "contentAssistAutoinsert"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether code assist proposals filtering is case sensitive or not.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_CASE_SENSITIVITY = "contentAssistCaseSensitivity"; //$NON-NLS-1$

	/**
	 * A named preference that controls if code assist for constants should be case sensitive
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>true<code> code assist for constants should be case sensitive. If
	 * <code>false</code> case insensitive.
	 * </p>
	 */
	public final static String CODEASSIST_CONSTANTS_CASE_SENSITIVE = "contentAssistConstantsCaseSensitive"; //$NON-NLS-1$

	/**
	 * A named preference that controls if code assist determines object type from other files
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>true<code> code assist will try to determine object type
	 * from other files. If <code>false</code> it will determine object type only from current scope.
	 * </p>
	 */
	public final static String CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES = "contentAssistDetermineObjTypeFromOtherFiles"; //$NON-NLS-1$

	/**
	 * A named preference that controls if argument names are filled in when a method is selected from as list
	 * of code assist proposal.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_FILL_ARGUMENT_NAMES = "contentAssistFillMethodArguments"; //$NON-NLS-1$

	/**
	 * A named preference that controls if method arguments are guessed when a
	 * method is selected from as list of code assist proposal.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_GUESS_METHOD_ARGUMENTS = "contentAssistGuessMethodArguments"; //$NON-NLS-1$

	/**
	 * A named preference that controls if the PHP code assist only inserts
	 * completions. If set to false the proposals can also _replace_ code.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_INSERT_COMPLETION = "contentAssistInsertCompletion"; //$NON-NLS-1$

	/**
	 * A named preference that defines if code assist proposals are sorted in alphabetical order.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> that are sorted in alphabetical
	 * order. If <code>false</code> that are unsorted.
	 * </p>
	 */
	public final static String CODEASSIST_ORDER_PROPOSALS = "contentAssistOrderProposals"; //$NON-NLS-1$

	public final static String CODEASSIST_PREFIX_COMPLETION = "contentAssistPrefixCompletion"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether to show class names in Global Completion list
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>true<code> class names in Global Completion list will be shown
	 * <code>false</code> they will not be shown.
	 * </p>
	 */
	public final static String CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION = "contentAssistShowClassNamesInGlobalCompletion"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether to show constants assist
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>true<code> constant assist will be visible
	 * <code>false</code> invisible.
	 * </p>
	 */
	public final static String CODEASSIST_SHOW_CONSTANTS_ASSIST = "contentAssistShowConstantsAssist"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether to show options that are restricted by PHP
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>true<code> constant assist will be visible
	 * <code>false</code> invisible.
	 * </p>
	 */
	public final static String CODEASSIST_SHOW_NON_STRICT_OPTIONS = "contentAssistShowNonStrictOptions"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether to group options
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>true<code> constant assist will be visible
	 * <code>false</code> invisible.
	 * </p>
	 */
	public final static String CODEASSIST_GROUP_OPTIONS = "contentAssistGroupOptions"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether to cut common prefix
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>true<code> constant assist will be visible
	 * <code>false</code> invisible.
	 * </p>
	 */
	public final static String CODEASSIST_CUT_COMMON_PREFIX = "contentAssistCutPrefix"; //$NON-NLS-1$

	/**
	 * A named preference that controls if code assist also contains proposals from other files
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>false<code> code assist only contains visible members. If
	 * <code>true</code> all members are included.
	 * </p>
	 */
	public final static String CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES = "contentAssistShowVariablesFromOtherFiles"; //$NON-NLS-1$
	
}
