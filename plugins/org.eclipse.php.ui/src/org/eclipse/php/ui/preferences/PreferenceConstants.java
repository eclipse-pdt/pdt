/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.preferences;

import java.util.Locale;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.phpModel.parser.PHPVersion;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.PHPColorHelper;
import org.eclipse.php.ui.util.PHPManualSiteDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.wst.sse.ui.internal.projection.IStructuredTextFoldingProvider;

public class PreferenceConstants {

	/**
	 * A named preferences that controls if PHP elements are also sorted by
	 * visibility.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String APPEARANCE_ENABLE_VISIBILITY_SORT_ORDER = "enableVisibilityOrder"; //$NON-NLS-1$

	/**
	 * A named preference that defines how member elements are ordered by visibility in the
	 * PHP views using the <code>PHPElementSorter</code>.
	 * <p>
	 * Value is of type <code>String</code>: A comma separated list of the
	 * following entries. Each entry must be in the list, no duplication. List
	 * order defines the sort order.
	 * <ul>
	 * <li><b>B</b>: Public</li>
	 * <li><b>V</b>: Private</li>
	 * <li><b>R</b>: Protected</li>
	 * <li><b>D</b>: Default</li>
	 * </ul>
	 * </p>
	 * @since 3.0
	 */
	public static final String APPEARANCE_VISIBILITY_SORT_ORDER = "org.eclipse.php.ui.visibility.order"; //$NON-NLS-1$

	/**
	 * A named preference that defines how member elements are ordered by the
	 * PHP views using the <code>PHPElementSorter</code>.
	 * <p>
	 * Value is of type <code>String</code>: A comma separated list of the
	 * following entries. Each entry must be in the list, no duplication. List
	 * order defines the sort order.
	 * <ul>
	 * <li><b>T</b>: Types</li>
	 * <li><b>M</b>: Methods</li>
	 * <li><b>F</b>: Fields</li>
	 * </ul>
	 * </p>
	 */
	public static final String APPEARANCE_MEMBER_SORT_ORDER = "outlinesortoption"; //$NON-NLS-1$

	/**
	 * A named preference that controls return type rendering of methods in the UI.
	 * <p>
	 * Value is of type <code>Boolean</code>: if <code>true</code> return types
	 * are rendered
	 * </p>
	 */
	public static final String APPEARANCE_METHOD_RETURNTYPE = "methodreturntype";//$NON-NLS-1$

	/**
	 * A named preference that controls type parameter rendering of methods in the UI.
	 * <p>
	 * Value is of type <code>Boolean</code>: if <code>true</code> return types
	 * are rendered
	 * </p>
	 */
	public static final String APPEARANCE_METHOD_TYPEPARAMETERS = "methodtypeparametesr";//$NON-NLS-1$

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
	 * A named preference that holds the autoactivation proposals limit.
	 * <p>
	 * Value is of type <code>Integer</code>.
	 * </p>
	 */
	public final static String CODEASSIST_AUTOACTIVATION_SIZE_LIMIT = "contentAssistAutoactivationSizeLimit"; //$NON-NLS-1$

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
	 * A named preference that controls whether to start actiactivation on class names
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES = "contentAssistAutoactivationForClassNames"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether to start actiactivation on class names
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_AUTOACTIVATION_FOR_VARIABLES = "contentAssistAutoactivationForVariables"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether to start actiactivation on class names
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS = "contentAssistAutoactivationForFunctionsKeyWordsConstants"; //$NON-NLS-1$

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

	/**
	 * A named preference that holds the background color used for parameter hints.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a string
	 * using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_PARAMETERS_BACKGROUND = "contentAssistParametersBackground"; //$NON-NLS-1$

	/**
	 * A named preference that holds the foreground color used in the code assist selection dialog.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a string
	 * using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_PARAMETERS_FOREGROUND = "contentAssistParametersForeground"; //$NON-NLS-1$

	public final static String CODEASSIST_PREFIX_COMPLETION = "contentAssistPrefixCompletion"; //$NON-NLS-1$

	/**
	 * A named preference that holds the background color used in the code assist selection dialog.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a string
	 * using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_PROPOSALS_BACKGROUND = "contentAssistProposalsBackground"; //$NON-NLS-1$

	/**
	 * A named preference that holds the foreground color used in the code assist selection dialog.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a string
	 * using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_PROPOSALS_FOREGROUND = "contentAssistProposalsForeground"; //$NON-NLS-1$

	/**
	 * A named preference that holds the background color used in the code
	 * assist selection dialog to mark replaced code.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a string
	 * using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_REPLACEMENT_BACKGROUND = "contentAssistCompletionReplacementBackground"; //$NON-NLS-1$

	/**
	 * A named preference that holds the foreground color used in the code
	 * assist selection dialog to mark replaced code.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a string
	 * using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_REPLACEMENT_FOREGROUND = "contentAssistCompletionReplacementForeground"; //$NON-NLS-1$

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
	 * A named preference that controls if code assist also contains proposals from other files
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>false<code> code assist only contains visible members. If
	 * <code>true</code> all members are included.
	 * </p>
	 */
	public final static String CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES = "contentAssistShowVariablesFromOtherFiles"; //$NON-NLS-1$

	/**
	 * A named preference that controls the behavior when double clicking on a container in the folders view.
	 * <p>
	 * Value is of type <code>String</code>: possible values are <code>
	 * DOUBLE_CLICK_GOES_INTO</code> or <code>
	 * DOUBLE_CLICK_EXPANDS</code>.
	 * </p>
	 *
	 * @see #DOUBLE_CLICK_EXPANDS
	 * @see #DOUBLE_CLICK_GOES_INTO
	 */
	public static final String DOUBLE_CLICK = "phpviewDoubleclick"; //$NON-NLS-1$

	/**
	 * A string value used by the named preference <code>DOUBLE_CLICK</code>.
	 *
	 * @see #DOUBLE_CLICK
	 */
	public static final String DOUBLE_CLICK_EXPANDS = "phpviewDoubleclickExpands"; //$NON-NLS-1$

	/**
	 * A string value used by the named preference <code>DOUBLE_CLICK</code>.
	 *
	 * @see #DOUBLE_CLICK
	 */
	public static final String DOUBLE_CLICK_GOES_INTO = "phpviewGointo"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether annotation roll over is used or not.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true<code> the annotation ruler column
	 * uses a roll over to display multiple annotations
	 * </p>
	 */
	public static final String EDITOR_ANNOTATION_ROLL_OVER = "editor_annotation_roll_over"; //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the PHP boundary makers (open/close tags)
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_BOUNDARYMARKER_COLOR = "editorColorBoundarymaker"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the PHP boundary makers (open/close tags)
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_BOUNDARYMARKER_DEFAULT_COLOR = PHPColorHelper.getColorString(255, 0, 0) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(false, false, false); //$NON-NLS-1$

	/**
	 * A named preference that controls whether the 'close braces' feature is
	 * enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_CLOSE_BRACES = "closeBraces"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the 'close brackets' feature is
	 * enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_CLOSE_BRACKETS = "closeBrackets"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the 'close strings' feature is
	 * enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_CLOSE_STRINGS = "closeStrings"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the 'close phpdoc and comments' feature is
	 * enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_CLOSE_PHPDOCS_AND_COMMENTS = "closePhpDocsAndComments"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the 'close phpdoc and comments' feature is
	 * enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_ADD_PHPDOC_TAGS = "autoAddPhpDocTags"; //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the PHP comments
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_COMMENT_COLOR = "editorColorComment"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the PHP comments
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_COMMENT_DEFAULT_COLOR = PHPColorHelper.getColorString(128, 128, 128) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(false, false, false); //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the TASK tag inside the comment
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_TASK_COLOR = "editorColorTask"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the TASK tag inside the comment
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_TASK_DEFAULT_COLOR = PHPColorHelper.getColorString(124, 165, 213) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(true, false, false); //$NON-NLS-1$

	/**
	 * A named preference that controls if correction indicators are shown in the UI.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_CORRECTION_INDICATION = "PHPEditorShowTemporaryProblem"; //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the heredoc
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_HEREDOC_COLOR = "editorColorHeredoc"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the heredoc
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_HEREDOC_DEFAULT_COLOR = PHPColorHelper.getColorString(0, 130, 130) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(false, false, false); //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the PHP keyword
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_KEYWORD_COLOR = "editorColorKeyword"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the PHP keyword
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_KEYWORD_DEFAULT_COLOR = PHPColorHelper.getColorString(0, 0, 255) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(false, false, false); //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the normal PHP text
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_NORMAL_COLOR = "codeStyleNormal"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the normal PHP text
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_NORMAL_DEFAULT_COLOR = PHPColorHelper.getColorString(0, 0, 0) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(false, false, false); //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the numbers
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_NUMBER_COLOR = "editorColorNumber"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the numbers
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_NUMBER_DEFAULT_COLOR = PHPColorHelper.getColorString(255, 0, 0) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(false, false, false); //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the PHPDoc comments
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_PHPDOC_COLOR = "editorColorPhpdoc"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the PHPDoc comments
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_PHPDOC_DEFAULT_COLOR = PHPColorHelper.getColorString(128, 128, 128) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(true, false, false); //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the PHP string
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_STRING_COLOR = "editorColorString"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the PHP string
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_STRING_DEFAULT_COLOR = PHPColorHelper.getColorString(0, 130, 0) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(false, false, false); //$NON-NLS-1$

	/**
	 * A named preference that controls whether the outline view selection
	 * should stay in sync with with the element at the current cursor position.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_SYNC_OUTLINE_ON_CURSOR_MOVE = "PHPEditorSyncOutlineOnCursorMove"; //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the PHP variable
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_VARIABLE_COLOR = "editorColorVariable"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the PHP variable
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_VARIABLE_DEFAULT_COLOR = PHPColorHelper.getColorString(102, 0, 0) + PHPColorHelper.NOBACKGROUND + PHPColorHelper.getStyleString(false, false, false); //$NON-NLS-1$

	/**
	 * A named preference that controls the smart tab behavior.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 *
	 */
	public static final String EDITOR_SMART_TAB = "smart_tab"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether blank lines are cleared during formatting.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public final static String FORMATTER_COMMENT_CLEARBLANKLINES = "commentClearBlankLines"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether HTML tags are formatted.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public final static String FORMATTER_COMMENT_FORMATHTML = "commentFormatHtml"; //$NON-NLS-1$

	/**
	 * A named preference that controls the line length of comments.
	 * <p>
	 * Value is of type <code>Integer</code>. The value must be at least 4 for reasonable formatting.
	 * </p>
	 *
	 */
	public final static String FORMATTER_COMMENT_LINELENGTH = "commentLineLength"; //$NON-NLS-1$

	/**
	 * A named preference that controls which profile is used by the code formatter.
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 *
	 */
	public static final String FORMATTER_PROFILE = "formatterProfile"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the folders view's selection is
	 * linked to the active editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String LINK_BROWSING_FOLDERS_TO_EDITOR = "browsingLinktoeditor"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the projects view's selection is
	 * linked to the active editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String LINK_BROWSING_PROJECTS_TO_EDITOR = "browsingProjectstoeditor"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the php explorer's selection is linked to the active editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String LINK_EXPLORER_TO_EDITOR = "explorerLinktoeditor"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the  explorer's selection is linked to the active editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String LINK_FOLDERS_TO_EDITOR = "foldersLinktoeditor"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether all dirty editors are automatically saved before a refactoring is
	 * executed.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String REFACTOR_SAVE_ALL_EDITORS = "RefactoringSavealleditors"; //$NON-NLS-1$

	/**
	 * A named preference that specifies whether children of a PHP file are shown in the php explorer.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String SHOW_CU_CHILDREN = "explorerCuchildren"; //$NON-NLS-1$

	/**
	 * A named preference that specifies whether children of a php file are shown in the  explorer.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String SHOW_PHP_CHILDREN = "foldersPhpchildren"; //$NON-NLS-1$

	/**
	 * A named preference that controls if templates are formatted when applied.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String TEMPLATES_USE_CODEFORMATTER = "templateFormat"; //$NON-NLS-1$

	public static final String EDITOR_FOLDING_PHPDOC = "foldPHPDoc"; //$NON-NLS-1$
	public static final String EDITOR_FOLDING_CLASSES = "foldClasses"; //$NON-NLS-1$
	public static final String EDITOR_FOLDING_FUNCTIONS = "foldFunctions"; //$NON-NLS-1$
	//	public static final String EDITOR_FOLDING_INCLUDES = "foldIncludes"; //$NON-NLS-1$
	/**
	 * A named preference that controls whether folding is enabled in the PHP editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.1
	 * @see IStructuredTextFoldingProvider#FOLDING_ENABLED
	 */
	//	public static final String EDITOR_FOLDING_ENABLED= "editor_folding_enabled"; //$NON-NLS-1$
	public static final String EDITOR_FOLDING_ENABLED = IStructuredTextFoldingProvider.FOLDING_ENABLED;

	/**
	 * A named preference that stores the configured folding provider.
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 * 
	 * @since 3.1
	 */
	public static final String EDITOR_FOLDING_PROVIDER = "editor_folding_provider"; //$NON-NLS-1$

	public static final String TYPING_AUTO_CLOSE_STRING = "typing_autoclose_string"; //$NON-NLS-1$

	public static final String TYPING_AUTO_CLOSE_BRACKETS = "typing_autoclose_brackets"; //$NON-NLS-1$

	public static final String TYPING_AUTO_CLOSE_BRACES = "typing_autoclose_braces"; //$NON-NLS-1$

	public static final String TEMPLATES_KEY = "org.eclipse.php.ui.editor.templates"; //$NON-NLS-1$

	public static final String NEW_PHP_FILE_TEMPLATE = "newFileTemplateName"; //$NON-NLS-1$

	/**
	 * The id of the best match hover contributed for extension point
	 * <code>javaEditorTextHovers</code>.
	 */
	public static final String ID_BESTMATCH_HOVER = "org.eclipse.php.ui.editor.hover.BestMatchHover"; //$NON-NLS-1$

	/**
	 * A named preference that defines whether the hint to make hover sticky should be shown.
	 */
	public static final String EDITOR_SHOW_TEXT_HOVER_AFFORDANCE = "PreferenceConstants.EDITOR_SHOW_TEXT_HOVER_AFFORDANCE"; //$NON-NLS-1$

	/**
	 * A named preference that defines the key for the hover modifiers.
	 */
	public static final String EDITOR_TEXT_HOVER_MODIFIERS = "hoverModifiers"; //$NON-NLS-1$

	/**
	 * A named preference that defines the key for the hover modifier state masks.
	 * The value is only used if the value of <code>EDITOR_TEXT_HOVER_MODIFIERS</code>
	 * cannot be resolved to valid SWT modifier bits.
	 * 
	 * @see #EDITOR_TEXT_HOVER_MODIFIERS
	 */
	public static final String EDITOR_TEXT_HOVER_MODIFIER_MASKS = "hoverModifierMasks"; //$NON-NLS-1$

	/**
	 * A named preference that defines the key for the default PHP Manual site.
	 */
	public static final String PHP_MANUAL_SITE = "phpManualSite";

	/**
	 * A named preference that defines the key for the stored PHP Manual sites 
	 */
	public static final String PHP_MANUAL_SITES = "phpManualSites";

	public static final String PHP_MANUAL_OPEN_IN_NEW_BROWSER = "phpManualOpenInNewBrowser"; //$NON-NLS-1$

	/**
	 * some constants for auto-ident Smart Tab
	 */
	public static final String TAB = "tab"; //$NON-NLS-1$
	public static final String FORMATTER_TAB_CHAR = PHPUiPlugin.ID + ".smart_tab.char"; //$NON-NLS-1$

	public static IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	/**
	 * Initializes the given preference store with the default values.
	 */
	public static void initializeDefaultValues() {

		// override default line highlight color:
		
		IPreferenceStore editorStore = EditorsPlugin.getDefault().getPreferenceStore(); 
		editorStore.setDefault(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE, true);
		PreferenceConverter.setDefault(editorStore, AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE_COLOR, new RGB(255, 255, 206));

		IPreferenceStore store = getPreferenceStore();
		
		store.setDefault(DOUBLE_CLICK, DOUBLE_CLICK_EXPANDS);

		store.setDefault(LINK_FOLDERS_TO_EDITOR, false);
		store.setDefault(LINK_BROWSING_PROJECTS_TO_EDITOR, true);
		store.setDefault(LINK_BROWSING_FOLDERS_TO_EDITOR, true);
		store.setDefault(LINK_EXPLORER_TO_EDITOR, true);

		store.setDefault(SHOW_PHP_CHILDREN, true);
		store.setDefault(SHOW_CU_CHILDREN, true);

		store.setDefault(APPEARANCE_METHOD_RETURNTYPE, false);
		store.setDefault(APPEARANCE_METHOD_TYPEPARAMETERS, true);
		store.setDefault(APPEARANCE_ENABLE_VISIBILITY_SORT_ORDER, false);

		store.setDefault(EDITOR_CLOSE_BRACKETS, true);
		store.setDefault(EDITOR_CLOSE_BRACES, true);
		store.setDefault(EDITOR_CLOSE_STRINGS, true);
		store.setDefault(EDITOR_CLOSE_PHPDOCS_AND_COMMENTS, true);
		store.setDefault(EDITOR_ADD_PHPDOC_TAGS, true);

		store.setDefault(FORMATTER_COMMENT_CLEARBLANKLINES, false);
		store.setDefault(FORMATTER_COMMENT_FORMATHTML, true);
		store.setDefault(FORMATTER_COMMENT_LINELENGTH, 80);

		// RefactoringPreferencePage
		store.setDefault(REFACTOR_SAVE_ALL_EDITORS, false);

		// TemplatePreferencePage
		store.setDefault(TEMPLATES_USE_CODEFORMATTER, true);

		// MembersOrderPreferencePage
		store.setDefault(APPEARANCE_MEMBER_SORT_ORDER, "I,S,T,C,SV,SF,V,F"); //$NON-NLS-1$

		store.setDefault(EDITOR_CORRECTION_INDICATION, true);
		store.setDefault(EDITOR_SYNC_OUTLINE_ON_CURSOR_MOVE, true);
		store.setDefault(EDITOR_ANNOTATION_ROLL_OVER, false);

		// CodeAssistPreferencePage
		PreferenceConverter.setDefault(store, CODEASSIST_PROPOSALS_BACKGROUND, new RGB(255, 255, 255));
		PreferenceConverter.setDefault(store, CODEASSIST_PROPOSALS_FOREGROUND, new RGB(0, 0, 0));
		PreferenceConverter.setDefault(store, CODEASSIST_PARAMETERS_BACKGROUND, new RGB(255, 255, 255));
		PreferenceConverter.setDefault(store, CODEASSIST_PARAMETERS_FOREGROUND, new RGB(0, 0, 0));
		PreferenceConverter.setDefault(store, CODEASSIST_REPLACEMENT_BACKGROUND, new RGB(255, 255, 0));
		PreferenceConverter.setDefault(store, CODEASSIST_REPLACEMENT_FOREGROUND, new RGB(255, 0, 0));
		store.setDefault(CODEASSIST_CASE_SENSITIVITY, false);
		store.setDefault(CODEASSIST_ORDER_PROPOSALS, false);
		store.setDefault(CODEASSIST_ADDIMPORT, true);
		store.setDefault(CODEASSIST_FILL_ARGUMENT_NAMES, false);
		store.setDefault(CODEASSIST_GUESS_METHOD_ARGUMENTS, true);
		store.setDefault(CODEASSIST_PREFIX_COMPLETION, false);
		// implemented:
		store.setDefault(CODEASSIST_AUTOINSERT, true);
		store.setDefault(CODEASSIST_INSERT_COMPLETION, true);
		store.setDefault(CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES, true);
		store.setDefault(CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES, true);
		store.setDefault(CODEASSIST_SHOW_CONSTANTS_ASSIST, true);
		store.setDefault(CODEASSIST_CONSTANTS_CASE_SENSITIVE, false);
		store.setDefault(CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION, true);
		store.setDefault(CODEASSIST_AUTOACTIVATION, true);
		store.setDefault(CODEASSIST_AUTOACTIVATION_SIZE_LIMIT, 500);
		store.setDefault(CODEASSIST_AUTOACTIVATION_DELAY, 200);
		store.setDefault(CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP, "$:>");
		store.setDefault(CODEASSIST_AUTOACTIVATION_TRIGGERS_PHPDOC, "@");
		store.setDefault(CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES, true); //$NON-NLS-1$
		store.setDefault(CODEASSIST_AUTOACTIVATION_FOR_VARIABLES, true); //$NON-NLS-1$
		store.setDefault(CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS, true); //$NON-NLS-1$

		// SyntaxColoringPage
		store.setDefault(EDITOR_NORMAL_COLOR, EDITOR_NORMAL_DEFAULT_COLOR);
		store.setDefault(EDITOR_BOUNDARYMARKER_COLOR, EDITOR_BOUNDARYMARKER_DEFAULT_COLOR);
		store.setDefault(EDITOR_KEYWORD_COLOR, EDITOR_KEYWORD_DEFAULT_COLOR);
		store.setDefault(EDITOR_VARIABLE_COLOR, EDITOR_VARIABLE_DEFAULT_COLOR);
		store.setDefault(EDITOR_STRING_COLOR, EDITOR_STRING_DEFAULT_COLOR);
		store.setDefault(EDITOR_NUMBER_COLOR, EDITOR_NUMBER_DEFAULT_COLOR);
		store.setDefault(EDITOR_HEREDOC_COLOR, EDITOR_HEREDOC_DEFAULT_COLOR);
		store.setDefault(EDITOR_COMMENT_COLOR, EDITOR_COMMENT_DEFAULT_COLOR);
		store.setDefault(EDITOR_PHPDOC_COLOR, EDITOR_PHPDOC_DEFAULT_COLOR);
		store.setDefault(EDITOR_TASK_COLOR, EDITOR_TASK_DEFAULT_COLOR);

		// PHP options
		store.setDefault(PHPCoreConstants.PHP_OPTIONS_PHP_VERSION, PHPVersion.PHP5);
		store.setDefault(PHPCoreConstants.PHP_OPTIONS_PHP_ROOT_CONTEXT, "");

		// Folding options
		store.setDefault(EDITOR_FOLDING_ENABLED, false);
		store.setDefault(EDITOR_FOLDING_PROVIDER, "org.eclipse.php.ui.defaultFoldingProvider"); //$NON-NLS-1$
		store.setDefault(EDITOR_FOLDING_PHPDOC, true);
		store.setDefault(EDITOR_FOLDING_CLASSES, false);
		store.setDefault(EDITOR_FOLDING_FUNCTIONS, false);
		//		store.setDefault(EDITOR_FOLDING_INCLUDES, false);
		store.setDefault(TYPING_AUTO_CLOSE_STRING, true);
		store.setDefault(TYPING_AUTO_CLOSE_BRACKETS, true);
		store.setDefault(TYPING_AUTO_CLOSE_BRACES, true);

		store.setDefault(NEW_PHP_FILE_TEMPLATE, "New simple PHP file"); //$NON-NLS-1$

		String mod1Name = Action.findModifierString(SWT.MOD1); // SWT.COMMAND on Mac; SWT.CONTROL elsewhere
		store.setDefault(EDITOR_TEXT_HOVER_MODIFIERS, "org.eclipse.php.ui.editor.hover.BestMatchHover;0;org.eclipse.php.ui.editor.hover.PHPSourceTextHover;" + mod1Name); //$NON-NLS-1$
		store.setDefault(EDITOR_TEXT_HOVER_MODIFIER_MASKS, "org.eclipse.php.ui.editor.hover.BestMatchHover;0;org.eclipse.php.ui.editor.hover.PHPSourceTextHover;" + SWT.MOD1); //$NON-NLS-1$
		store.setDefault(EDITOR_SHOW_TEXT_HOVER_AFFORDANCE, true);

		store.setDefault(PHP_MANUAL_SITE, PHPManualSiteDescriptor.DEFAULT_PHP_MANUAL_SITE);
		store.setDefault(PHP_MANUAL_OPEN_IN_NEW_BROWSER, true);

		// default locale
		if ((store.getString(PHPCoreConstants.WORKSPACE_DEFAULT_LOCALE)).equals("")) {
			store.setValue(PHPCoreConstants.WORKSPACE_DEFAULT_LOCALE, Locale.getDefault().toString());
			store.setDefault(PHPCoreConstants.WORKSPACE_LOCALE, Locale.getDefault().toString());
		}
	}

	// Don't instantiate
	private PreferenceConstants() {
	}
}
