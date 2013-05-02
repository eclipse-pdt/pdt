/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import java.util.Locale;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.SemanticHighlightingManager;
import org.eclipse.php.internal.ui.folding.IStructuredTextFoldingProvider;
import org.eclipse.php.internal.ui.outline.PHPContentOutlineConfiguration;
import org.eclipse.php.internal.ui.util.PHPManualSiteDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.spelling.SpellingService;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;
import org.eclipse.wst.sse.ui.internal.projection.AbstractStructuredFoldingStrategy;

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
	 * A named preference that defines how member elements are ordered by
	 * visibility in the PHP views using the <code>PHPElementSorter</code>.
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
	 * 
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
	 * A named preference that controls return type rendering of methods in the
	 * UI.
	 * <p>
	 * Value is of type <code>Boolean</code>: if <code>true</code> return types
	 * are rendered
	 * </p>
	 */
	public static final String APPEARANCE_METHOD_RETURNTYPE = "methodreturntype"; //$NON-NLS-1$

	/**
	 * A named preference that controls type parameter rendering of methods in
	 * the UI.
	 * <p>
	 * Value is of type <code>Boolean</code>: if <code>true</code> return types
	 * are rendered
	 * </p>
	 */
	public static final String APPEARANCE_METHOD_TYPEPARAMETERS = "methodtypeparametesr"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether annotation roll over is used or
	 * not.
	 * <p>
	 * Value is of type <code>Boolean</code>. If
	 * <code>true<code> the annotation ruler column
	 * uses a roll over to display multiple annotations
	 * </p>
	 */
	public static final String EDITOR_ANNOTATION_ROLL_OVER = "editor_annotation_roll_over"; //$NON-NLS-1$

	/**
	 * A named preference that holds the color for the PHP boundary makers
	 * (open/close tags)
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_BOUNDARYMARKER_COLOR = "editorColorBoundarymaker"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the PHP boundary
	 * makers (open/close tags)
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_BOUNDARYMARKER_DEFAULT_COLOR = ColorHelper
			.getColorString(255, 0, 0);

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
	 * A named preference that controls whether the 'close phpdoc and comments'
	 * feature is enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_CLOSE_PHPDOCS_AND_COMMENTS = "closePhpDocsAndComments"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the 'close phpdoc and comments'
	 * feature is enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_ADD_PHPDOC_TAGS = "autoAddPhpDocTags"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the 'close php close tag'
	 * feature is enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_ADD_PHPCLOSE_TAGS = "autoAddPhpCloseTags"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the 'Add "php" after PHP start
	 * tag (<?)' feature is enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_ADD_PHP_FOR_PHPSTART_TAGS = "autoAddPhpForPhpStartTags"; //$NON-NLS-1$

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
	public static final String EDITOR_COMMENT_DEFAULT_COLOR = ColorHelper
			.getColorString(85, 127, 95);

	/**
	 * A named preference that holds the color for the PHP comments
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_LINE_COMMENT_COLOR = "editorColorLineComment"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the PHP comments
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_LINE_COMMENT_DEFAULT_COLOR = ColorHelper
			.getColorString(85, 127, 95);

	/**
	 * A named preference that holds the color for the PHP comments
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_PHPDOC_COMMENT_COLOR = "editorColorPHPDocComment"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the PHP comments
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_PHPDOC_COMMENT_DEFAULT_COLOR = ColorHelper
			.getColorString(63, 85, 191);

	/**
	 * A named preference that holds the color for the TASK tag inside the
	 * comment
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_TASK_COLOR = "editorColorTask"; //$NON-NLS-1$

	/**
	 * A named preference that holds the default color for the TASK tag inside
	 * the comment
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 */
	public static final String EDITOR_TASK_DEFAULT_COLOR = ColorHelper
			.getColorString(124, 165, 213) + " | | true"; //$NON-NLS-1$

	/**
	 * A named preference that controls if correction indicators are shown in
	 * the UI.
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
	public static final String EDITOR_HEREDOC_DEFAULT_COLOR = ColorHelper
			.getColorString(0, 130, 130);

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
	public static final String EDITOR_KEYWORD_DEFAULT_COLOR = ColorHelper
			.packStylePreferences(new String[] {
					ColorHelper.getColorString(127, 0, 85), null, "true" }); //$NON-NLS-1$

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
	public static final String EDITOR_NORMAL_DEFAULT_COLOR = ColorHelper
			.getColorString(0, 0, 0);

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
	public static final String EDITOR_NUMBER_DEFAULT_COLOR = ColorHelper
			.getColorString(0, 0, 0);

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
	 * 
	 * @return Foo
	 */
	public static final String EDITOR_PHPDOC_DEFAULT_COLOR = ColorHelper
			.getColorString(127, 159, 191) + " | | true"; //$NON-NLS-1$

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
	public static final String EDITOR_STRING_DEFAULT_COLOR = ColorHelper
			.getColorString(0, 0, 192);

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
	public static final String EDITOR_VARIABLE_DEFAULT_COLOR = ColorHelper
			.getColorString(0, 0, 0);

	/**
	 * A named preference that controls the smart tab behavior.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * 
	 */
	public static final String EDITOR_SMART_TAB = "smart_tab"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether occurrences are marked in the
	 * editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_MARK_OCCURRENCES = "markOccurrences"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether occurrences are sticky in the
	 * editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_STICKY_OCCURRENCES = "stickyOccurrences"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether type occurrences are marked.
	 * Only valid if {@link #EDITOR_MARK_OCCURRENCES} is <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_MARK_TYPE_OCCURRENCES = "markTypeOccurrences"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether method occurrences are marked.
	 * Only valid if {@link #EDITOR_MARK_OCCURRENCES} is <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_MARK_METHOD_OCCURRENCES = "markMethodOccurrences"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether function occurrences are marked.
	 * Only valid if {@link #EDITOR_MARK_OCCURRENCES} is <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.4
	 */
	public static final String EDITOR_MARK_FUNCTION_OCCURRENCES = "markFunctionOccurrences"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether constant (static final)
	 * occurrences are marked. Only valid if {@link #EDITOR_MARK_OCCURRENCES} is
	 * <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_MARK_CONSTANT_OCCURRENCES = "markConstantOccurrences"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether local variable occurrences are
	 * marked. Only valid if {@link #EDITOR_MARK_OCCURRENCES} is
	 * <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_MARK_LOCAL_VARIABLE_OCCURRENCES = "markLocalVariableOccurrences"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether global variable occurrences are
	 * marked. Only valid if {@link #EDITOR_MARK_OCCURRENCES} is
	 * <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.4
	 */
	public static final String EDITOR_MARK_GLOBAL_VARIABLE_OCCURRENCES = "markGlobalVariableOccurrences"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether exception occurrences are
	 * marked. Only valid if {@link #EDITOR_MARK_OCCURRENCES} is
	 * <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_MARK_EXCEPTION_OCCURRENCES = "markExceptionOccurrences"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether method exit points are marked.
	 * Only valid if {@link #EDITOR_MARK_OCCURRENCES} is <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_MARK_METHOD_EXIT_POINTS = "markMethodExitPoints"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether targets for of
	 * <code>break</code> and <code>continue</code> statements are marked. Only
	 * valid if {@link #EDITOR_MARK_OCCURRENCES} is <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.2
	 */
	public static final String EDITOR_MARK_BREAK_CONTINUE_TARGETS = "markBreakContinueTargets"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether method exit points are marked.
	 * Only valid if {@link #EDITOR_MARK_OCCURRENCES} is <code>true</code>.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.1
	 */
	public static final String EDITOR_MARK_IMPLEMENTORS = "markImplementors"; //$NON-NLS-1$

	/**
	 * A named preference prefix for semantic highlighting preferences.
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX = "semanticHighlighting."; //$NON-NLS-1$

	/**
	 * A named preference that controls if semantic highlighting is enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>:<code>true</code> if enabled.
	 * </p>
	 * 
	 * @since 3.0
	 * @deprecated As of 3.1, this preference is not used or set any longer; see
	 *             {@link SemanticHighlightingManager#affectsEnablement(IPreferenceStore, org.eclipse.jface.util.PropertyChangeEvent)}
	 */
	public static final String EDITOR_SEMANTIC_HIGHLIGHTING_ENABLED = EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
			+ "enabled"; //$NON-NLS-1$

	/**
	 * A named preference suffix that controls a semantic highlighting's color.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 * 
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 * @since 3.0
	 */
	public static final String EDITOR_SEMANTIC_HIGHLIGHTING_COLOR_SUFFIX = ".color"; //$NON-NLS-1$

	public static final String EDITOR_SEMANTIC_HIGHLIGHTING_BGCOLOR_SUFFIX = ".bgcolor"; //$NON-NLS-1$

	/**
	 * A named preference suffix that controls if semantic highlighting has the
	 * text attribute bold.
	 * <p>
	 * Value is of type <code>Boolean</code>: <code>true</code> if bold.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_SEMANTIC_HIGHLIGHTING_BOLD_SUFFIX = ".bold"; //$NON-NLS-1$

	/**
	 * A named preference suffix that controls if semantic highlighting has the
	 * text attribute italic.
	 * <p>
	 * Value is of type <code>Boolean</code>: <code>true</code> if italic.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_SEMANTIC_HIGHLIGHTING_ITALIC_SUFFIX = ".italic"; //$NON-NLS-1$

	/**
	 * A named preference suffix that controls if semantic highlighting has the
	 * text attribute strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>: <code>true</code> if
	 * strikethrough.
	 * </p>
	 * 
	 * @since 3.1
	 */
	public static final String EDITOR_SEMANTIC_HIGHLIGHTING_STRIKETHROUGH_SUFFIX = ".strikethrough"; //$NON-NLS-1$

	/**
	 * A named preference suffix that controls if semantic highlighting has the
	 * text attribute underline.
	 * <p>
	 * Value is of type <code>Boolean</code>: <code>true</code> if underline.
	 * </p>
	 * 
	 * @since 3.1
	 */
	public static final String EDITOR_SEMANTIC_HIGHLIGHTING_UNDERLINE_SUFFIX = ".underline"; //$NON-NLS-1$

	/**
	 * A named preference suffix that controls if semantic highlighting is
	 * enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>: <code>true</code> if enabled.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_SEMANTIC_HIGHLIGHTING_ENABLED_SUFFIX = ".enabled"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether blank lines are cleared during
	 * formatting.
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
	 * Value is of type <code>Integer</code>. The value must be at least 4 for
	 * reasonable formatting.
	 * </p>
	 * 
	 */
	public final static String FORMATTER_COMMENT_LINELENGTH = "commentLineLength"; //$NON-NLS-1$

	/**
	 * A named preference that controls which profile is used by the code
	 * formatter.
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
	 * A named preference that controls whether the php explorer's selection is
	 * linked to the active editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String LINK_EXPLORER_TO_EDITOR = "explorerLinktoeditor"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the explorer's selection is
	 * linked to the active editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String LINK_FOLDERS_TO_EDITOR = "foldersLinktoeditor"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether all dirty editors are
	 * automatically saved before a refactoring is executed.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String REFACTOR_SAVE_ALL_EDITORS = "RefactoringSavealleditors"; //$NON-NLS-1$

	/**
	 * A named preference that specifies whether children of a PHP file are
	 * shown in the php explorer.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String SHOW_CU_CHILDREN = "explorerCuchildren"; //$NON-NLS-1$

	/**
	 * A named preference that specifies whether children of a php file are
	 * shown in the explorer.
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
	//	public static final String EDITOR_FOLDING_INCLUDES = "foldIncludes"; 
	public static final String EDITOR_FOLDING_HEADER_COMMENTS = "foldHeaderComment"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether smart Home/End in PHP code is
	 * enabled
	 */
	public static final String USE_SMART_HOME_END = "useSmartHomeEnd"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether smart navigation in PHP code is
	 * enabled on sub-words
	 */
	public static final String USE_SUB_WORD_NAVIGATION = "editorSubWordNavigation"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether folding is enabled in the PHP
	 * editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.1
	 * @see IStructuredTextFoldingProvider#FOLDING_ENABLED
	 */
	//	public static final String EDITOR_FOLDING_ENABLED= "editor_folding_enabled"; 
	public static final String EDITOR_FOLDING_ENABLED = AbstractStructuredFoldingStrategy.FOLDING_ENABLED;

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

	public static final String CODE_TEMPLATES_KEY = "org.eclipse.php.ui.text.custom_code_templates"; //$NON-NLS-1$

	public static final String NEW_PHP_FILE_TEMPLATE = "newFileTemplateName"; //$NON-NLS-1$

	public static final String ALLOW_MULTIPLE_LAUNCHES = "allowMultipleLaunches"; //$NON-NLS-1$

	/**
	 * The id of the best match hover contributed for extension point
	 * <code>javaEditorTextHovers</code>.
	 */
	public static final String ID_BESTMATCH_HOVER = "org.eclipse.php.ui.editor.hover.BestMatchHover"; //$NON-NLS-1$

	/**
	 * A named preference that defines the key for the hover modifiers.
	 */
	public static final String EDITOR_TEXT_HOVER_MODIFIERS = PHPUiPlugin.ID
			+ "hoverModifiers"; //$NON-NLS-1$

	/**
	 * A named preference that defines the key for the hover modifier state
	 * masks. The value is only used if the value of
	 * <code>EDITOR_TEXT_HOVER_MODIFIERS</code> cannot be resolved to valid SWT
	 * modifier bits.
	 * 
	 * @see #EDITOR_TEXT_HOVER_MODIFIERS
	 */
	public static final String EDITOR_TEXT_HOVER_MODIFIER_MASKS = PHPUiPlugin.ID
			+ "hoverModifierMasks"; //$NON-NLS-1$

	/**
	 * A named preference that defines the key for the default PHP Manual site.
	 */
	public static final String PHP_MANUAL_SITE = "phpManualSite"; //$NON-NLS-1$

	/**
	 * A named preference that defines the key for the stored PHP Manual sites
	 */
	public static final String PHP_MANUAL_SITES = "phpManualSites"; //$NON-NLS-1$

	public static final String PHP_MANUAL_OPEN_IN_NEW_BROWSER = "phpManualOpenInNewBrowser"; //$NON-NLS-1$

	public static final String SWITCH_BACK_TO_PHP_PERSPECTIVE = "switchBackToPHPPerspective"; //$NON-NLS-1$

	/**
	 * A named preference that controls a reduced search menu is used in the php
	 * editors.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String SEARCH_USE_REDUCED_MENU = "Search.usereducemenu"; //$NON-NLS-1$

	/**
	 * A named preference that holds the background color used for parameter
	 * hints.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 * 
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_PARAMETERS_BACKGROUND = "contentAssistParametersBackground"; //$NON-NLS-1$

	/**
	 * A named preference that holds the foreground color used in the code
	 * assist selection dialog.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 * 
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_PARAMETERS_FOREGROUND = "contentAssistParametersForeground"; //$NON-NLS-1$

	/**
	 * A named preference that holds the background color used in the code
	 * assist selection dialog.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 * 
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_PROPOSALS_BACKGROUND = "contentAssistProposalsBackground"; //$NON-NLS-1$

	/**
	 * A named preference that holds the foreground color used in the code
	 * assist selection dialog.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
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
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
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
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 * 
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String CODEASSIST_REPLACEMENT_FOREGROUND = "contentAssistCompletionReplacementForeground"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether new projects are generated using
	 * source and output folder.
	 * <p>
	 * Value is of type <code>Boolean</code>. if <code>true</code> new projects
	 * are created with a source and output folder. If <code>false</code> source
	 * and output folder equals to the project.
	 * </p>
	 */
	public static final String SRCBIN_FOLDERS_IN_NEWPROJ = "org.eclipse.php.ui.wizards.srcBinFoldersInNewProjects"; //$NON-NLS-1$

	/**
	 * A named preference that specifies the source folder name used when
	 * creating a new Java project. Value is inactive if
	 * <code>SRCBIN_FOLDERS_IN_NEWPROJ</code> is set to <code>false</code>.
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 * 
	 * @see #SRCBIN_FOLDERS_IN_NEWPROJ
	 */
	//public static final String SRCBIN_SRCNAME = "org.eclipse.php.ui.wizards.srcBinFoldersSrcName"; 
	public static final String SRCBIN_SRCNAME = "com.xore.dltk.ui.wizards.srcFoldersSrcName"; //$NON-NLS-1$

	/**
	 * A named preference that controls if quick assist light bulbs are shown.
	 * <p>
	 * Value is of type <code>Boolean</code>: if <code>true</code> light bulbs
	 * are shown for quick assists.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String EDITOR_QUICKASSIST_LIGHTBULB = "org.eclipse.php.quickassist.lightbulb"; //$NON-NLS-1$

	public static final String JavaScriptSupportEnable = "org.eclipse.php.ui.wizards.JSsupport"; //$NON-NLS-1$

	/**
	 * A named preference that specifies the output folder name used when
	 * creating a new Java project. Value is inactive if
	 * <code>SRCBIN_FOLDERS_IN_NEWPROJ</code> is set to <code>false</code>.
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 * 
	 * @see #SRCBIN_FOLDERS_IN_NEWPROJ
	 */
	public static final String SRCBIN_BINNAME = "org.eclipse.php.ui.wizards.srcBinFoldersBinName"; //$NON-NLS-1$

	/**
	 * some constants for auto-ident Smart Tab
	 */
	public static final String TAB = "tab"; //$NON-NLS-1$
	public static final String FORMATTER_TAB_CHAR = PHPUiPlugin.ID
			+ ".smart_tab.char"; //$NON-NLS-1$

	public static final String FORMAT_REMOVE_TRAILING_WHITESPACES = "cleanup.remove_trailing_whitespaces"; //$NON-NLS-1$
	public static final String FORMAT_REMOVE_TRAILING_WHITESPACES_ALL = "cleanup.remove_trailing_whitespaces_all"; //$NON-NLS-1$
	public static final String FORMAT_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY = "cleanup.remove_trailing_whitespaces_ignore_empty"; //$NON-NLS-1$
	public static final String PREF_OUTLINEMODE = "ChangeOutlineModeAction.selectedMode"; //$NON-NLS-1$

	/**
	 * This setting controls whether to group elements by namespaces in PHP
	 * Explorer
	 */
	public static final String EXPLORER_GROUP_BY_NAMESPACES = "PHPExplorerPart.groupByNamespaces"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the 'smart paste' feature is
	 * enabled.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * 
	 * @since 2.1
	 */
	public final static String EDITOR_SMART_PASTE = "smartPaste"; //$NON-NLS-1$

	public static IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	/**
	 * Initializes the given preference store with the default values.
	 */
	public static void initializeDefaultValues() {

		// Override Editor Preference defaults:
		IPreferenceStore editorStore = EditorsPlugin.getDefault()
				.getPreferenceStore();

		// Show current line:
		editorStore
				.setDefault(
						AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE,
						true);

		// Show line numbers:
		editorStore
				.setDefault(
						AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER,
						true);

		// disabling the spelling detection till we find a way to refine it the
		// run only on strings and comments.
		editorStore.setDefault(SpellingService.PREFERENCE_SPELLING_ENABLED,
				false);

		IPreferenceStore store = getPreferenceStore();

		store.setDefault(JavaScriptSupportEnable, false);
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
		store.setDefault(EDITOR_ADD_PHPCLOSE_TAGS, true);
		store.setDefault(EDITOR_ADD_PHP_FOR_PHPSTART_TAGS, true);
		store.setDefault(EDITOR_SMART_PASTE, true);

		// mark occurrences
		store.setDefault(PreferenceConstants.EDITOR_MARK_OCCURRENCES, true);
		store.setDefault(PreferenceConstants.EDITOR_STICKY_OCCURRENCES, true);
		store.setDefault(PreferenceConstants.EDITOR_MARK_TYPE_OCCURRENCES, true);
		store.setDefault(PreferenceConstants.EDITOR_MARK_METHOD_OCCURRENCES,
				true);
		store.setDefault(PreferenceConstants.EDITOR_MARK_CONSTANT_OCCURRENCES,
				true);
		store.setDefault(PreferenceConstants.EDITOR_MARK_FUNCTION_OCCURRENCES,
				true);
		store.setDefault(
				PreferenceConstants.EDITOR_MARK_LOCAL_VARIABLE_OCCURRENCES,
				true);
		store.setDefault(
				PreferenceConstants.EDITOR_MARK_GLOBAL_VARIABLE_OCCURRENCES,
				true);
		store.setDefault(PreferenceConstants.EDITOR_MARK_EXCEPTION_OCCURRENCES,
				true);
		store.setDefault(PreferenceConstants.EDITOR_MARK_METHOD_EXIT_POINTS,
				true);
		store.setDefault(
				PreferenceConstants.EDITOR_MARK_BREAK_CONTINUE_TARGETS, true);
		store.setDefault(PreferenceConstants.EDITOR_MARK_IMPLEMENTORS, true);

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
		store.setDefault(
				org.eclipse.dltk.ui.PreferenceConstants.EDITOR_CORRECTION_INDICATION,
				true);
		store.setDefault(EDITOR_SYNC_OUTLINE_ON_CURSOR_MOVE, true);
		store.setDefault(EDITOR_ANNOTATION_ROLL_OVER, false);

		// CodeAssistPreferencePage
		PreferenceConverter.setDefault(store, CODEASSIST_PROPOSALS_BACKGROUND,
				new RGB(255, 255, 255));
		PreferenceConverter.setDefault(store, CODEASSIST_PROPOSALS_FOREGROUND,
				new RGB(0, 0, 0));
		PreferenceConverter.setDefault(store, CODEASSIST_PARAMETERS_BACKGROUND,
				new RGB(255, 255, 255));
		PreferenceConverter.setDefault(store, CODEASSIST_PARAMETERS_FOREGROUND,
				new RGB(0, 0, 0));
		PreferenceConverter.setDefault(store,
				CODEASSIST_REPLACEMENT_BACKGROUND, new RGB(255, 255, 0));
		PreferenceConverter.setDefault(store,
				CODEASSIST_REPLACEMENT_FOREGROUND, new RGB(255, 0, 0));

		// SyntaxColoringPage
		store.setDefault(EDITOR_NORMAL_COLOR, EDITOR_NORMAL_DEFAULT_COLOR);
		store.setDefault(EDITOR_BOUNDARYMARKER_COLOR,
				EDITOR_BOUNDARYMARKER_DEFAULT_COLOR);
		store.setDefault(EDITOR_KEYWORD_COLOR, EDITOR_KEYWORD_DEFAULT_COLOR);
		store.setDefault(EDITOR_VARIABLE_COLOR, EDITOR_VARIABLE_DEFAULT_COLOR);
		store.setDefault(EDITOR_STRING_COLOR, EDITOR_STRING_DEFAULT_COLOR);
		store.setDefault(EDITOR_NUMBER_COLOR, EDITOR_NUMBER_DEFAULT_COLOR);
		store.setDefault(EDITOR_HEREDOC_COLOR, EDITOR_HEREDOC_DEFAULT_COLOR);
		store.setDefault(EDITOR_COMMENT_COLOR, EDITOR_COMMENT_DEFAULT_COLOR);
		store.setDefault(EDITOR_LINE_COMMENT_COLOR,
				EDITOR_LINE_COMMENT_DEFAULT_COLOR);
		store.setDefault(EDITOR_PHPDOC_COMMENT_COLOR,
				EDITOR_PHPDOC_COMMENT_DEFAULT_COLOR);
		store.setDefault(EDITOR_PHPDOC_COLOR, EDITOR_PHPDOC_DEFAULT_COLOR);
		store.setDefault(EDITOR_TASK_COLOR, EDITOR_TASK_DEFAULT_COLOR);

		// SyntaxColoringPage enable
		store.setDefault(getEnabledPreferenceKey(EDITOR_NORMAL_COLOR), true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_BOUNDARYMARKER_COLOR),
				true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_KEYWORD_COLOR), true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_VARIABLE_COLOR), true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_STRING_COLOR), true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_NUMBER_COLOR), true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_HEREDOC_COLOR), true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_COMMENT_COLOR), true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_LINE_COMMENT_COLOR),
				true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_PHPDOC_COMMENT_COLOR),
				true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_PHPDOC_COLOR), true);
		store.setDefault(getEnabledPreferenceKey(EDITOR_TASK_COLOR), true);
		// PHP options
		store.setDefault(PHPCoreConstants.PHP_OPTIONS_PHP_VERSION,
				PHPVersion.PHP5_3.toString());
		store.setDefault(PHPCoreConstants.PHP_OPTIONS_PHP_ROOT_CONTEXT, ""); //$NON-NLS-1$

		// Smart home/end
		store.setDefault(USE_SMART_HOME_END, true);

		// smart php code sub-word navigation
		store.setDefault(USE_SUB_WORD_NAVIGATION, true);

		// Folding options
		store.setDefault(EDITOR_FOLDING_ENABLED, true);
		store.setDefault(EDITOR_FOLDING_PROVIDER,
				"org.eclipse.php.ui.defaultFoldingProvider"); //$NON-NLS-1$
		store.setDefault(EDITOR_FOLDING_PHPDOC, false);
		store.setDefault(EDITOR_FOLDING_CLASSES, false);
		store.setDefault(EDITOR_FOLDING_FUNCTIONS, false);

		// store.setDefault(EDITOR_FOLDING_INCLUDES, false);
		store.setDefault(TYPING_AUTO_CLOSE_STRING, true);
		store.setDefault(TYPING_AUTO_CLOSE_BRACKETS, true);
		store.setDefault(TYPING_AUTO_CLOSE_BRACES, true);

		store.setDefault(NEW_PHP_FILE_TEMPLATE, "New simple PHP file"); //$NON-NLS-1$

		store.setDefault(ALLOW_MULTIPLE_LAUNCHES,
				MessageDialogWithToggle.PROMPT);

		String mod1Name = Action.findModifierString(SWT.MOD1); // SWT.COMMAND on
		// Mac;
		// SWT.CONTROL
		// elsewhere
		store.setDefault(
				EDITOR_TEXT_HOVER_MODIFIERS,
				"org.eclipse.php.ui.editor.hover.BestMatchHover;0;org.eclipse.php.ui.editor.hover.PHPSourceTextHover;" + mod1Name); //$NON-NLS-1$
		store.setDefault(
				EDITOR_TEXT_HOVER_MODIFIER_MASKS,
				"org.eclipse.php.ui.editor.hover.BestMatchHover;0;org.eclipse.php.ui.editor.hover.PHPSourceTextHover;" + SWT.MOD1); 		 //$NON-NLS-1$

		store.setDefault(PHP_MANUAL_SITE, PHPManualConfigSerializer
				.toString(new PHPManualConfig(
						PHPManualSiteDescriptor.DEFAULT_PHP_MANUAL_LABEL,
						PHPManualSiteDescriptor.DEFAULT_PHP_MANUAL_SITE,
						PHPManualSiteDescriptor.DEFAULT_PHP_MANUAL_EXTENSION,
						false)));
		store.setDefault(PHP_MANUAL_OPEN_IN_NEW_BROWSER, true);

		store.setDefault(PreferenceConstants.SEARCH_USE_REDUCED_MENU, true);

		store.setDefault(SWITCH_BACK_TO_PHP_PERSPECTIVE,
				MessageDialogWithToggle.NEVER);

		// default locale
		if (store.getString(PHPCoreConstants.WORKSPACE_DEFAULT_LOCALE).equals(
				"")) { //$NON-NLS-1$
			store.setValue(PHPCoreConstants.WORKSPACE_DEFAULT_LOCALE, Locale
					.getDefault().toString());
			store.setDefault(PHPCoreConstants.WORKSPACE_LOCALE, Locale
					.getDefault().toString());
		}

		// save actions
		store.setDefault(FORMAT_REMOVE_TRAILING_WHITESPACES, false);
		store.setDefault(FORMAT_REMOVE_TRAILING_WHITESPACES_ALL, true);
		store.setDefault(FORMAT_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY, false);

		// default php outline mode
		store.setDefault(PREF_OUTLINEMODE,
				PHPContentOutlineConfiguration.MODE_PHP);

		store.setDefault(EXPLORER_GROUP_BY_NAMESPACES, false);

		// PHP Semantic Highlighting
		SemanticHighlightingManager.getInstance().initDefaults(store);

		// do more complicated stuff
		PHPProjectLayoutPreferencePage.initDefaults(store);
	}

	public static String getEnabledPreferenceKey(String preferenceKey) {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ preferenceKey
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_ENABLED_SUFFIX;
	}

	// Don't instantiate
	private PreferenceConstants() {
	}
}
