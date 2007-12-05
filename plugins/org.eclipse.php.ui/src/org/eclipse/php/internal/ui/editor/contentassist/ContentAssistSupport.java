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
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.dom.Utils;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.*;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.util.Visitor;
import org.eclipse.php.internal.core.util.WeakPropertyChangeListener;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateCompletionProcessor;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateContextTypeIds;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.editor.contentassist.IContentAssistSupport;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.eclipse.wst.sse.ui.internal.contentassist.ContentAssistUtils;

public class ContentAssistSupport implements IContentAssistSupport {

	/**
	 *
	 */
	private static final String PHPDOC_CLASS_NAME_SEPARATOR = "\\|"; //$NON-NLS-1$
	protected static final char[] phpDelimiters = new char[] { '?', ':', ';', '|', '^', '&', '<', '>', '+', '-', '.', '*', '/', '%', '!', '~', '[', ']', '(', ')', '{', '}', '@', '\n', '\t', ' ', ',', '$', '\'', '\"' };
	protected static final String CLASS_FUNCTIONS_TRIGGER = "::"; //$NON-NLS-1$
	protected static final String OBJECT_FUNCTIONS_TRIGGER = "->"; //$NON-NLS-1$

	private static final Pattern extendsPattern = Pattern.compile("\\Wextends\\W", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final Pattern implementsPattern = Pattern.compile("\\Wimplements", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final Pattern catchPattern = Pattern.compile("catch\\s[^{]*", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final Pattern globalPattern = Pattern.compile("\\$GLOBALS[\\s]*\\[[\\s]*[\\'\\\"][\\w]+[\\'\\\"][\\s]*\\]"); //$NON-NLS-1$

	public static final ICompletionProposal[] EMPTY_CompletionProposal_ARRAY = new ICompletionProposal[0];
	public static final CodeDataCompletionProposal[] EMPTY_CodeDataCompletionProposal_ARRAY = new CodeDataCompletionProposal[0];
	private static final PHPProposalComperator proposalsComperator = new PHPProposalComperator();

	private static String[] CLASS_KEYWORDS = { "abstract", "const", "function", "private", "protected", "public", "static", "var" }; // must be ordered!

	protected boolean showVariablesFromOtherFiles;
	protected boolean groupCompletionOptions;
	protected boolean cutCommonPrefix;
	protected boolean determineObjectTypeFromOtherFile;
	protected boolean disableConstants;
	protected boolean showClassNamesInGlobalList;
	protected boolean showNonStrictOptions;
	protected boolean constantCaseSensitive;
	protected boolean autoShowVariables;
	protected boolean autoShowFunctionsKeywordsConstants;
	protected boolean autoShowClassNames;
	protected char[] autoActivationTriggers;

	private PHPTemplateCompletionProcessor templateCompletionProcessor;

	protected IPropertyChangeListener prefChangeListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			if (event != null) {
				initPreferences(event.getProperty());
			}
		}
	};

	protected void initPreferences(String prefKey) {
		if (prefKey == null || PreferenceConstants.CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES.equals(prefKey) || PreferenceConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST.equals(prefKey) || PreferenceConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS.equals(prefKey)
			|| PreferenceConstants.CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION.equals(prefKey) || PreferenceConstants.CODEASSIST_CONSTANTS_CASE_SENSITIVE.equals(prefKey) || PreferenceConstants.CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES.equals(prefKey)
			|| PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES.equals(prefKey) || PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS.equals(prefKey) || PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_VARIABLES.equals(prefKey)
			|| PreferenceConstants.CODEASSIST_CUT_COMMON_PREFIX.equals(prefKey) || PreferenceConstants.CODEASSIST_GROUP_OPTIONS.equals(prefKey) || PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP.equals(prefKey)) {

			showVariablesFromOtherFiles = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES);
			groupCompletionOptions = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_GROUP_OPTIONS);
			cutCommonPrefix = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_CUT_COMMON_PREFIX);
			disableConstants = !PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST);
			showClassNamesInGlobalList = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION);
			showNonStrictOptions = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS);
			constantCaseSensitive = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_CONSTANTS_CASE_SENSITIVE);
			determineObjectTypeFromOtherFile = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES);
			autoShowClassNames = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES);
			autoShowFunctionsKeywordsConstants = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS);
			autoShowVariables = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_VARIABLES);
			autoActivationTriggers = PreferenceConstants.getPreferenceStore().getString(PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP).trim().toCharArray();
		}
	}

	protected CompletionProposalGroup completionProposalGroup;
	protected ICompletionProposal[] templateProposals;

	protected CompletionProposalGroup phpCompletionProposalGroup = new PHPCompletionProposalGroup();
	protected CompletionProposalGroup regularPHPCompletionProposalGroup = new RegularPHPCompletionProposalGroup();
	protected CompletionProposalGroup classConstructorCompletionProposalGroup = new ClassConstructorCompletionProposalGroup();
	protected CompletionProposalGroup newStatementCompletionProposalGroup = new NewStatementCompletionProposalGroup();
	protected CompletionProposalGroup arrayCompletionProposalGroup = new ArrayCompletionProposalGroup();
	protected CompletionProposalGroup classStaticCallCompletionProposalGroup = new ClassStaticCallCompletionProposalGroup();
	protected CompletionProposalGroup classVariableCallCompletionProposalGroup = new ClassVariableCallCompletionProposalGroup();

	public ContentAssistSupport() {
		// Initialize all preferences
		initPreferences(null);

		// Listen to preferences changes
		PreferenceConstants.getPreferenceStore().addPropertyChangeListener(WeakPropertyChangeListener.create(prefChangeListener, PreferenceConstants.getPreferenceStore()));
	}

	public ICompletionProposal[] getCompletionOption(ITextViewer viewer, DOMModelForPHP phpDOMModel, int offset, boolean explicit) throws BadLocationException {
		ICompletionProposal[] codeCompletionOptions = getCodeCompletionOptions(viewer, phpDOMModel, offset, explicit);
		if (codeCompletionOptions == null) {
			return new ICompletionProposal[0];
		}
		return codeCompletionOptions;
	}

	private ICompletionProposal[] getCodeCompletionOptions(ITextViewer viewer, DOMModelForPHP phpEditorModel, int offset, boolean explicit) throws BadLocationException {
		completionProposalGroup = null;
		templateProposals = null;
		calcCompletionOption(phpEditorModel, offset, viewer, explicit);
		if (completionProposalGroup == null) {
			return templateProposals;
		}
		completionProposalGroup.setGroupOptions(groupCompletionOptions);
		completionProposalGroup.setCutCommonPrefix(cutCommonPrefix);
		return merg(completionProposalGroup.getCompletionProposals(getProjectModel(phpEditorModel)), templateProposals);
	}

	protected ICompletionProposal[] getTemplates(ITextViewer viewer, int offset) {
		PHPTemplateCompletionProcessor templateCompletionProcessor = getTemplateCompletionProcessor();
		ICompletionProposal[] templatesCompletionProposals = templateCompletionProcessor.computeCompletionProposals(viewer, offset);
		return templatesCompletionProposals;
	}

	private PHPTemplateCompletionProcessor getTemplateCompletionProcessor() {
		if (templateCompletionProcessor == null) {
			templateCompletionProcessor = new PHPTemplateCompletionProcessor();
			String context = getTemplateContext();
			templateCompletionProcessor.setContextTypeId(context);
		}
		return templateCompletionProcessor;
	}

	protected String getTemplateContext() {
		return PHPTemplateContextTypeIds.PHP;
	}

	public char[] getAutoactivationTriggers() {
		return autoActivationTriggers;
	}

	protected void calcCompletionOption(DOMModelForPHP editorModel, int offset, ITextViewer viewer, boolean explicit) throws BadLocationException {

		final int originalOffset = viewer.getSelectedRange().x;
		final boolean isStrict = originalOffset != offset ? true : false;

		PHPProjectModel projectModel = getProjectModel(editorModel);

		String fileName = null;
		PHPFileData fileData = editorModel.getFileData(true);
		if (fileData == null) {
			return;
		}
		fileName = fileData.getName();

		int selectionLength = ((TextSelection) viewer.getSelectionProvider().getSelection()).getLength();

		IStructuredDocumentRegion sdRegion = ContentAssistUtils.getStructuredDocumentRegion(viewer, offset);
		ITextRegion textRegion = null;
		// 	in case we are at the end of the document, asking for completion
		if (offset == editorModel.getStructuredDocument().getLength()) {
			textRegion = sdRegion.getLastRegion();
		} else {
			textRegion = sdRegion.getRegionAtCharacterOffset(offset);
		}

		if (textRegion == null)
			return;

		ITextRegionCollection container = sdRegion;

		if (textRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) textRegion;
			textRegion = container.getRegionAtCharacterOffset(offset);
		}

		if (textRegion.getType() == PHPRegionContext.PHP_OPEN) {
			return;
		}
		if (textRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			if (container.getStartOffset(textRegion) == offset) {
				ITextRegion regionBefore = container.getRegionAtCharacterOffset(offset - 1);
				if (regionBefore instanceof IPhpScriptRegion) {
					textRegion = regionBefore;
				}
			} else {
				return;
			}
		}

		// find the start String for completion
		int startOffset = container.getStartOffset(textRegion);

		//in case we are standing at the beginning of a word and asking for completion
		//should not take into account the found region
		//find the previous region and update the start offset
		if (startOffset == offset) {
			ITextRegion preTextRegion = container.getRegionAtCharacterOffset(offset - 1);
			IStructuredDocumentRegion preSdRegion = null;
			if (preTextRegion != null || (preSdRegion = sdRegion.getPrevious()) != null && (preTextRegion = preSdRegion.getRegionAtCharacterOffset(offset - 1)) != null) {
				if (preTextRegion.getType() == "") { //$NON-NLS-1$
					// FIXME needs to be fixed. The problem is what to do if the cursor is exatly between problematic regions, e.g. single line comment and quoted string??
				}
			}
			startOffset = sdRegion.getStartOffset(textRegion);
		}

		IPhpScriptRegion phpScriptRegion = null;
		String partitionType = null;
		int internalOffset = 0;
		ContextRegion internalPHPRegion = null;
		if (textRegion instanceof IPhpScriptRegion) {
			phpScriptRegion = (IPhpScriptRegion) textRegion;
			internalOffset = offset - container.getStartOffset() - phpScriptRegion.getStart();

			partitionType = phpScriptRegion.getPartition(internalOffset);
			//if we are at the begining of multi-line comment or docBlock then we should get completion.
			if (partitionType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT || partitionType == PHPPartitionTypes.PHP_DOC) {
				String regionType = phpScriptRegion.getPhpToken(internalOffset).getType();
				if (regionType == PHPRegionTypes.PHP_COMMENT_START || regionType == PHPRegionTypes.PHPDOC_COMMENT_START) {
					if (phpScriptRegion.getPhpToken(internalOffset).getStart() == internalOffset) {
						partitionType = phpScriptRegion.getPartition(internalOffset - 1);
					}
				}
			}
			if (partitionType == PHPPartitionTypes.PHP_DEFAULT || partitionType == PHPPartitionTypes.PHP_QUOTED_STRING || partitionType == PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT) {
			} else {
				return;
			}
			internalPHPRegion = (ContextRegion) phpScriptRegion.getPhpToken(internalOffset);
		}

		IStructuredDocument document = sdRegion.getParentDocument();
		// if there is no project model (the file is not part of a project)
		// complete with language model only
		if (fileData == null || phpScriptRegion == null) {
			getRegularCompletion(viewer, projectModel, "", "", offset, selectionLength, explicit, container, phpScriptRegion, internalPHPRegion, document, isStrict); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		TextSequence statementText = PHPTextSequenceUtilities.getStatement(offset, sdRegion, true);

		String type = internalPHPRegion.getType();
		if (isInArrayOptionQuotes(projectModel, fileName, type, offset, selectionLength, statementText)) {
			// the current position is inside quotes as a parameter for an array.
			return;
		}

		if (isPHPSingleQuote(container, phpScriptRegion, internalPHPRegion, document, offset) || isLineComment(container, phpScriptRegion, offset)) {
			// we dont have code completion inside single quotes.
			return;
		}

		if (isInFunctionDeclaration(projectModel, fileName, statementText, offset, selectionLength, explicit)) {
			// the current position is inside function declaration.
			return;
		}

		if (isInCatchStatement(projectModel, statementText, offset, selectionLength, explicit)) {
			// the current position is inside catch statement.
			return;
		}

		int totalLength = statementText.length();

		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, totalLength); // read whitespace
		int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, endPosition, true);
		String lastWord = statementText.subSequence(startPosition, endPosition).toString();
		boolean haveSpacesAtEnd = totalLength != endPosition;

		if (haveSpacesAtEnd && isNewOrInstanceofStatement(projectModel, lastWord, "", offset, selectionLength, explicit, type)) { //$NON-NLS-1$
			// the current position is inside new or instanceof statement.
			return;
		}

		int line = document.getLineOfOffset(offset);
		if (isClassFunctionCompletion(projectModel, fileName, statementText, offset, line, selectionLength, lastWord, startPosition, haveSpacesAtEnd, explicit, isStrict)) {
			// the current position is in class function.
			return;
		}

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, startPosition); // read whitespace
		startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, endPosition, true);
		String firstWord = statementText.subSequence(startPosition, endPosition).toString();

		if (!haveSpacesAtEnd && isNewOrInstanceofStatement(projectModel, firstWord, lastWord, offset, selectionLength, explicit, type)) {
			// the current position is inside new or instanceof statement.
			if (lastWord.startsWith("$")) {
				if (haveSpacesAtEnd) {
					getRegularCompletion(viewer, projectModel, fileName, "", offset, selectionLength, explicit, container, phpScriptRegion, internalPHPRegion, document, isStrict); //$NON-NLS-1$
				} else {
					getRegularCompletion(viewer, projectModel, fileName, lastWord, offset, selectionLength, explicit, container, phpScriptRegion, internalPHPRegion, document, isStrict);
				}
			}
			return;
		}

		if (haveSpacesAtEnd && isFunctionCall(projectModel, lastWord)) {
			// the current position is between the end of a function call and open bracket.
			return;
		}

		if (isInArrayOption(projectModel, fileName, haveSpacesAtEnd, firstWord, lastWord, startPosition, offset, selectionLength, statementText, type)) {
			// the current position is after '[' sign show special completion.
			return;
		}

		if (isInClassDeclaration(projectModel, statementText, offset, selectionLength, explicit)) {
			// the current position is inside class declaration.
			return;
		}

		if (haveSpacesAtEnd) {
			getRegularCompletion(viewer, projectModel, fileName, "", offset, selectionLength, explicit, container, phpScriptRegion, internalPHPRegion, document, isStrict); //$NON-NLS-1$
		} else {
			getRegularCompletion(viewer, projectModel, fileName, lastWord, offset, selectionLength, explicit, container, phpScriptRegion, internalPHPRegion, document, isStrict);
		}

		return;
	}

	private PHPProjectModel getProjectModel(DOMModelForPHP editorModel) {
		PHPProjectModel projectModel = editorModel.getProjectModel();

		// if there is no project model (the file is not part of a project)
		// get the default project model
		if (projectModel == null) {
			projectModel = PHPWorkspaceModelManager.getDefaultPHPProjectModel();
		}
		return projectModel;
	}

	protected static boolean isFunctionCall(PHPProjectModel projectModel, String functionName) {
		CodeData[] functionsData = projectModel.getFunction(functionName);
		return functionsData != null && functionsData.length > 0;
	}

	protected boolean isLineComment(ITextRegionCollection sdRegion, IPhpScriptRegion phpScriptRegion, int offset) {
		int relativeOffset = offset - sdRegion.getStartOffset(phpScriptRegion);
		try {
			return phpScriptRegion.isLineComment(relativeOffset);
		} catch (BadLocationException e) {
			Logger.logException(e);
			return false;
		}
	}

	protected boolean isPHPSingleQuote(ITextRegionCollection sdRegion, IPhpScriptRegion phpScriptRegion, ContextRegion internalRegion, IStructuredDocument document, int documentOffset) {
		if (PHPPartitionTypes.isPHPQuotesState(internalRegion.getType())) {
			char firstChar;
			int startOffset;
			int endOffset;
			try {
				startOffset = internalRegion.getStart() + sdRegion.getStartOffset(phpScriptRegion);
				endOffset = startOffset + internalRegion.getTextLength();
				firstChar = document.get(startOffset, internalRegion.getTextLength()).charAt(0);
			} catch (BadLocationException e) {
				Logger.logException(e);
				return false;
			}
			return firstChar == '\'' && documentOffset <= endOffset - 1 && startOffset < documentOffset;
		}
		return false;
	}

	protected boolean isInArrayOptionQuotes(PHPProjectModel projectModel, String fileName, String type, int offset, int selectionLength, TextSequence text) {
		if (!PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}
		int length = text.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, length);
		int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(text, endPosition, false);
		if (endPosition != length && startPosition != endPosition) {
			return false;
		}

		String startWith = text.subSequence(startPosition, endPosition).toString();
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, startPosition);
		if (endPosition == 0) {
			return false;
		}
		char c = text.charAt(endPosition - 1);
		if (c != '\"' && c != '\'') {
			return false;
		}
		endPosition--;
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, endPosition);
		if (endPosition == 0 || text.charAt(endPosition - 1) != '[') {
			return false;
		}
		endPosition--;
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, endPosition);
		startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(text, endPosition, true);
		String variableName = text.subSequence(startPosition, endPosition).toString();

		if (variableName.startsWith("$")) { //$NON-NLS-1$
			variableName = variableName.substring(1);
		}
		CodeData[] result = projectModel.getArrayVariables(fileName, variableName, startWith, determineObjectTypeFromOtherFile);

		completionProposalGroup = arrayCompletionProposalGroup;
		completionProposalGroup.setData(offset, result, startWith, selectionLength);
		return true;
	}

	protected void getRegularCompletion(final ITextViewer viewer, final PHPProjectModel projectModel, final String fileName, String startsWith, final int offset, final int selectionLength, boolean explicit, final ITextRegionCollection sdRegion, final ITextRegion tRegion,
			final ContextRegion internalPhpRegion, IStructuredDocument document, boolean isStrict) {
		if (!explicit && startsWith.length() == 0)
			return;

		boolean inClass = false;

		PHPCodeData codeData = Utils.getCodeData(projectModel.getFileData(fileName), offset);

		if (codeData.getUserData().getStopPosition() > offset) {
			codeData = codeData.getContainer();
		}

		if (codeData instanceof PHPClassData) {
			inClass = true;
		}

		if (internalPhpRegion != null) {
			final String type = internalPhpRegion.getType();

			if (startsWith.startsWith("$") && !inClass) { //$NON-NLS-1$
				if (!explicit && !autoShowVariables)
					return;
				try {
					//if we're right next to a letter, in an implicit scenario, we don't want it to complete the variables name.
					if (!explicit && startsWith.equals("$") && document.getLength() != offset && Character.isLetter(document.getChar(offset))) { //$NON-NLS-1$
						return;
					}
				} catch (BadLocationException e) {
				}
				if (PHPPartitionTypes.isPHPQuotesState(type)) {
					final IStructuredDocument doc = document;
					try {
						final char charBefore = doc.get(offset - 2, 1).charAt(0);
						if (charBefore == '\\')
							return;
					} catch (final BadLocationException badLocationException) {
						Logger.logException(badLocationException);
					}
				}
				final PHPCodeContext context = getContext(projectModel, fileName, offset - startsWith.length());

				startsWith = startsWith.substring(1);
				CodeData[] variables = projectModel.getVariables(fileName, context, startsWith, showVariablesFromOtherFiles);
				completionProposalGroup = phpCompletionProposalGroup;
				completionProposalGroup.setData(offset, variables, startsWith, selectionLength, isStrict);
				return;
			}

			if (PHPPartitionTypes.isPHPQuotesState(type) || type.equals(PHPRegionTypes.PHP_HEREDOC_TAG) && sdRegion.getStartOffset(tRegion) + tRegion.getLength() <= offset) {
				completionProposalGroup = regularPHPCompletionProposalGroup;
				completionProposalGroup.setData(offset, new CodeData[0], startsWith, selectionLength, isStrict);
				return;
			}
		}

		CodeData[] functions = null;
		CodeData[] constants = null;
		CodeData[] keywords = null;

		if ((explicit || autoShowFunctionsKeywordsConstants) && !inClass) {
			if (startsWith.length() == 0)
				functions = projectModel.getFunctions();
			else {
				functions = projectModel.getFunctions(startsWith);
			}

			if (!disableConstants)
				if (startsWith.length() == 0)
					constants = projectModel.getConstants();
				else {
					constants = projectModel.getConstants(startsWith, constantCaseSensitive);
				}
		}

		keywords = projectModel.getKeywordData();
		if (inClass) {
			keywords = filterClassKeywords(keywords);
		}

		CodeData[] classes = null;
		if (!inClass) {
			if (showClassNamesInGlobalList)
				if (explicit || autoShowClassNames)
					classes = projectModel.getClasses();

		}
		CodeData[] mergeData = null;
		if (shouldAddPHPTag(document, offset, startsWith))
			mergeData = phpTagDataArray;

		mergeData = ModelSupport.merge(keywords, mergeData);
		mergeData = ModelSupport.merge(classes, mergeData);
		mergeData = ModelSupport.merge(constants, mergeData);
		mergeData = ModelSupport.merge(functions, mergeData);

		completionProposalGroup = regularPHPCompletionProposalGroup;
		completionProposalGroup.setData(offset, mergeData, startsWith, selectionLength, isStrict);

		templateProposals = getTemplates(viewer, offset);

		return;
	}

	/**
	 * @param keywords
	 * @return
	 */
	private CodeData[] filterClassKeywords(CodeData[] keywords) {
		List<CodeData> filteredKeywords = new ArrayList<CodeData>();
		for (int i = 0, j = 0; i < keywords.length && j < CLASS_KEYWORDS.length;) {
			int compared = keywords[i].getName().compareTo(CLASS_KEYWORDS[j]);
			if (compared < 0) {
				i++;
			} else if (compared > 0) {
				j++;
			} else {
				filteredKeywords.add(keywords[i]);
				i++;
				j++;
			}
		}
		return filteredKeywords.toArray(new CodeData[filteredKeywords.size()]);
	}

	private boolean shouldAddPHPTag(IStructuredDocument doc, int offset, String startsWith) {
		offset -= startsWith.length() + 2;
		try {
			String text = doc.get(offset, 2);
			if (text.equals("<?")) { //$NON-NLS-1$
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	private static final PHPTagData[] phpTagDataArray = { new PHPTagData() };

	static class PHPTagData extends PHPCodeDataFactory.PHPFunctionDataImp {

		PHPTagData() {
			super("php", 0, null, null, PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, ""); //$NON-NLS-1$ //$NON-NLS-2$
		}

		@Override
		public void accept(Visitor v) {
			if (v instanceof PHPCompletionRendererVisitor) {
				((PHPCompletionRendererVisitor) v).visit(this);
			} else {
				super.accept(v);
			}
		}

	}

	protected boolean isClassFunctionCompletion(PHPProjectModel projectModel, String fileName, TextSequence statementText, int offset, int line, int selectionLength, String functionName, int startFunctionPosition, boolean haveSpacesAtEnd, boolean explicit, boolean isStrict) {
		startFunctionPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, startFunctionPosition);
		if (startFunctionPosition <= 2) {
			return false;
		}
		boolean isClassTriger = false;
		boolean isParent = false;
		String triggerText = statementText.subSequence(startFunctionPosition - 2, startFunctionPosition).toString();
		if (triggerText.equals(OBJECT_FUNCTIONS_TRIGGER)) {
		} else if (triggerText.equals(CLASS_FUNCTIONS_TRIGGER)) {
			isClassTriger = true;
			if (startFunctionPosition >= 8) {
				String parentText = statementText.subSequence(startFunctionPosition - 8, startFunctionPosition - 2).toString();
				if (parentText.equals("parent")) { //$NON-NLS-1$
					isParent = true;
				}
			}
		} else {
			return false;
		}

		String className = getClassName(projectModel, fileName, statementText, startFunctionPosition, offset, line);

		if (className == null) {
			className = ""; //$NON-NLS-1$
		}

		if (haveSpacesAtEnd && functionName.length() > 0) {
			// check if current position is between the end of a function call and open bracket.
			return isClassFunctionCall(projectModel, fileName, className, functionName);
		}

		if (isClassTriger) {
			if (isParent) {
				if (className != "") { //$NON-NLS-1$
					showClassStaticCall(projectModel, fileName, offset, className, functionName, selectionLength, explicit);
				}
			} else {
				showClassStaticCall(projectModel, fileName, offset, className, functionName, selectionLength, explicit);
			}
		} else {
			String parent = statementText.toString().substring(0, statementText.toString().lastIndexOf(OBJECT_FUNCTIONS_TRIGGER)).trim();
			boolean isInstanceOf = !parent.equals("$this"); //$NON-NLS-1$
			//boolean addVariableDollar = parent.endsWith("()");
			boolean addVariableDollar = false;
			showClassCall(projectModel, fileName, offset, className, functionName, selectionLength, isInstanceOf, addVariableDollar, explicit, isStrict);
		}
		return true;
	}

	/**
	 * returns the type of the variable in the sequence.
	 *
	 * @param statementText
	 * @param endPosition  - the end offset in the sequence
	 * @param offset       - the offset in the document
	 */

	protected String getClassName(PHPProjectModel projectModel, String fileName, TextSequence statementText, int endPosition, int offset, int line) {
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, endPosition); // read whitespace

		boolean isClassTriger = false;

		String triggerText = statementText.subSequence(endPosition - 2, endPosition).toString();
		if (triggerText.equals(OBJECT_FUNCTIONS_TRIGGER)) {
		} else if (triggerText.equals(CLASS_FUNCTIONS_TRIGGER)) {
			isClassTriger = true;
		} else {
			return null;
		}

		int propertyEndPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, endPosition - 2);
		int lastObjectOperator = PHPTextSequenceUtilities.getPrivousTriggerIndex(statementText, propertyEndPosition);

		if (lastObjectOperator == -1) {
			// if there is no "->" or "::" in the left sequence then we need to calc the object type
			return innerGetClassName(projectModel, fileName, statementText, propertyEndPosition, isClassTriger, offset, line);
		}

		int propertyStartPosition = PHPTextSequenceUtilities.readForwardSpaces(statementText, lastObjectOperator + 2);
		String propertyName = statementText.subSequence(propertyStartPosition, propertyEndPosition).toString();
		String className = getClassName(projectModel, fileName, statementText, propertyStartPosition, offset, line);

		int bracketIndex = propertyName.indexOf('(');

		if (bracketIndex == -1) {
			//meaning its a class variable and not a function
			return getVarType(projectModel, fileName, className, propertyName, offset, line);
		}

		String functionName = propertyName.substring(0, bracketIndex).trim();
		return getFunctionReturnType(projectModel, fileName, className, functionName);
	}

	/**
	 * getting an instance and finding its type.
	 */
	protected String innerGetClassName(PHPProjectModel projectModel, String fileName, TextSequence statementText, int propertyEndPosition, boolean isClassTriger, int offset, int line) {

		int classNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, propertyEndPosition, true);
		String className = statementText.subSequence(classNameStart, propertyEndPosition).toString();
		if (isClassTriger) {
			if (className.equals("self")) { //$NON-NLS-1$
				PHPClassData classData = getContainerClassData(projectModel, fileName, offset - 6); //the offset before self::
				if (classData != null) {
					return classData.getName();
				}
			} else if (className.equals("parent")) { //$NON-NLS-1$
				PHPClassData classData = getContainerClassData(projectModel, fileName, offset - 8); //the offset before parent::
				if (classData != null) {
					return projectModel.getSuperClassName(fileName, classData.getName());
				}
			}
			return className;
		}
		//check for $GLOBALS['myVar'] scenario
		if (className.length() == 0) {
			//this can happen if the first char before the property is ']'
			String testedVar = statementText.subSequence(0, propertyEndPosition).toString().trim();
			Matcher m = globalPattern.matcher(testedVar);
			if (m.matches()) {
				// $GLOBALS['myVar'] => 'myVar'
				String quotedVarName = testedVar.substring(testedVar.indexOf('[') + 1, testedVar.indexOf(']')).trim();
				// 'myVar' => $myVar
				className = "$" + quotedVarName.substring(1, quotedVarName.length() - 1); //$NON-NLS-1$
			}
		}
		// if its object call calc the object type.
		if (className.length() > 0 && className.charAt(0) == '$') {
			int statementStart = offset - statementText.length();
			return PHPFileDataUtilities.getVariableType(fileName, className, statementStart, line, projectModel.getPHPUserModel(), determineObjectTypeFromOtherFile);
		}
		// if its function call calc the return type.
		if (statementText.charAt(propertyEndPosition - 1) == ')') {
			int functionNameEnd = getFunctionNameEndOffset(statementText, propertyEndPosition - 1);
			int functionNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, functionNameEnd, false);

			String functionName = statementText.subSequence(functionNameStart, functionNameEnd).toString();
			PHPClassData classData = getContainerClassData(projectModel, fileName, offset);
			if (classData != null) { //if its a clss function
				return getFunctionReturnType(projectModel, fileName, classData.getName(), functionName);
			}
			// if its a non class function
			PHPFileData fileData = projectModel.getFileData(fileName);
			PHPFunctionData[] functions = fileData.getFunctions();
			for (PHPFunctionData function : functions) {
				if (function.getName().equals(functionName)) {
					return function.getReturnType();
				}
			}
		}
		return null;
	}

	protected boolean isClassFunctionCall(PHPProjectModel projectModel, String fileName, String className, String functionName) {
		CodeData functionData = projectModel.getClassFunctionData(fileName, className, functionName);
		return functionData != null;
	}

	/**
	 * finding the type of the class variable.
	 */
	protected String getVarType(PHPProjectModel projectModel, String fileName, String className, String varName, int statementStart, int line) {
		String tempType = PHPFileDataUtilities.getVariableType(fileName, "this;*" + varName, statementStart, line, projectModel.getPHPUserModel(), determineObjectTypeFromOtherFile); //$NON-NLS-1$
		if (tempType != null) {
			return tempType;
		}

		// process multiple classes variables and compile their types for recursive multiple resolution
		String[] realClassNames = className.split(PHPDOC_CLASS_NAME_SEPARATOR);
		Set<String> varClassNames = new LinkedHashSet<String>();
		for (String realClassName : realClassNames) {
			realClassName = realClassName.trim();
			CodeData classVar = projectModel.getClassVariablesData(fileName, realClassName, varName);
			if (classVar != null) {
				if (classVar instanceof PHPClassVarData) {
					varClassNames.add(((PHPClassVarData) classVar).getClassType());
				}
				continue;
			}
			// checking if the variable belongs to one of the class's ancestor
			PHPClassData classData = projectModel.getClass(fileName, realClassName);

			if (classData == null) {
				continue;
			}
			PHPClassData.PHPSuperClassNameData superClassNameData = classData.getSuperClassData();
			if (superClassNameData == null) {
				continue;
			}
			String classVarClassName = getVarType(projectModel, fileName, superClassNameData.getName(), varName, statementStart, line);
			if (classVarClassName != null) {
				varClassNames.add(classVarClassName);
			}
		}
		StringBuffer compositeVarClassName = new StringBuffer();
		for (Iterator<String> i = varClassNames.iterator(); i.hasNext();) {
			String varClassName = i.next();
			compositeVarClassName.append(varClassName);
			if (i.hasNext()) {
				compositeVarClassName.append("|"); //$NON-NLS-1$
			}
		}
		return compositeVarClassName.toString();
	}

	/**
	 * finding the return type of the function.
	 */
	protected String getFunctionReturnType(PHPProjectModel projectModel, String fileName, String className, String functionName) {
		CodeData classFunction = projectModel.getClassFunctionData(fileName, className, functionName);
		if (classFunction != null) {
			if (classFunction instanceof PHPFunctionData) {
				return ((PHPFunctionData) classFunction).getReturnType();
			}
			return null;
		}

		// checking if the function belongs to one of the class's ancestor
		PHPClassData classData = projectModel.getClass(fileName, className);

		if (classData == null) {
			return null;
		}
		String rv = null;
		PHPClassData.PHPSuperClassNameData superClassNameData = classData.getSuperClassData();
		if (superClassNameData != null) {
			rv = getFunctionReturnType(projectModel, fileName, superClassNameData.getName(), functionName);
		}

		// checking if its a non-class function from within the file
		if (rv == null) {
			PHPFileData fileData = projectModel.getFileData(fileName);
			CodeData[] functions = fileData.getFunctions();
			for (CodeData function : functions) {
				if (function.getName().equals(functionName)) {
					if (function instanceof PHPFunctionData) {
						rv = ((PHPFunctionData) function).getReturnType();
					}
				}
			}
		}

		// checking if its a non-class function from within the project
		if (rv == null) {
			CodeData[] functions = projectModel.getFunctions();
			for (CodeData function : functions) {
				if (function.getName().equals(functionName)) {
					if (function instanceof PHPFunctionData) {
						rv = ((PHPFunctionData) function).getReturnType();
					}
				}
			}
		}
		return rv;
	}

	protected void showClassCall(PHPProjectModel projectModel, String fileName, int offset, String className, String startWith, int selectionLength, boolean isInstanceOf, boolean addVariableDollar, boolean explicit, boolean isStrict) {
		CodeData[] allFunctions = null;
		CodeData[] allClassVariables = null;

		// collecting multiple classes in case class name has string separated by "|", which may be used in doc-block
		String[] classNames = className.split(PHPDOC_CLASS_NAME_SEPARATOR);
		for (String realClassName : classNames) {
			realClassName = realClassName.trim();
			if (explicit || autoShowFunctionsKeywordsConstants) {
				CodeData[] functions = projectModel.getClassFunctions(fileName, realClassName, startWith.length() == 0 ? "" : startWith); //$NON-NLS-1$
				allFunctions = ModelSupport.merge(allFunctions, functions);
			}
			if (explicit || autoShowVariables) {
				CodeData[] classVariables = ModelSupport.getFilteredCodeData(projectModel.getClassVariables(fileName, realClassName, ""), ModelSupport.NOT_STATIC_VARIABLES_FILTER); //$NON-NLS-1$
				allClassVariables = ModelSupport.merge(allClassVariables, classVariables);
			}
		}
		CodeData[] result = ModelSupport.getFilteredCodeData(ModelSupport.merge(allFunctions, allClassVariables), getAccessLevelFilter(projectModel, fileName, className, offset, isInstanceOf));

		if (addVariableDollar) {
			completionProposalGroup = classVariableCallCompletionProposalGroup;
		} else {
			completionProposalGroup = phpCompletionProposalGroup;
		}
		completionProposalGroup.setData(offset, result, startWith, selectionLength, isStrict);
	}

	protected void showClassStaticCall(PHPProjectModel projectModel, String fileName, int offset, String className, String startWith, int selectionLength, boolean explicit) {
		CodeData[] functions = null;
		if (explicit || autoShowFunctionsKeywordsConstants) {
			functions = projectModel.getClassFunctions(fileName, className, ""); //$NON-NLS-1$
			String phpVersion = projectModel.getPHPLanguageModel().getPHPVersion();
			boolean isPHP5 = phpVersion.equals(PHPVersion.PHP5);
			if (isPHP5 && !showNonStrictOptions) {
				functions = ModelSupport.getFilteredCodeData(functions, ModelSupport.STATIC_FUNCTIONS_FILTER);
			}
		}
		CodeData[] classVariables = null;
		if (explicit || autoShowVariables) {
			classVariables = ModelSupport.merge(projectModel.getClassVariables(fileName, className, ""), projectModel.getClassConsts(fileName, className, "")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		CodeData[] result = ModelSupport.getFilteredCodeData(ModelSupport.merge(functions, classVariables), getAccessLevelFilter(projectModel, fileName, className, offset, false));
		completionProposalGroup = classStaticCallCompletionProposalGroup;
		completionProposalGroup.setData(offset, result, startWith, selectionLength);
	}

	protected void showParentCall(PHPProjectModel projectModel, String fileName, int offset, String className, String startWith, int selectionLength, boolean explicit, boolean isStrict) {
		CodeData[] functions = null;
		if (explicit || autoShowFunctionsKeywordsConstants) {
			functions = projectModel.getClassFunctions(fileName, className, startWith.length() == 0 ? "" : startWith); //$NON-NLS-1$
		}
		PHPClassData classData = projectModel.getClass(fileName, className);

		functions = ModelSupport.getFilteredCodeData(functions, ModelSupport.PROTECTED_ACCESS_LEVEL_FILTER_EXCLUDE_VARS_NOT_STATIC);
		completionProposalGroup = phpCompletionProposalGroup;
		completionProposalGroup.setData(offset, functions, startWith, selectionLength, isStrict);
	}

	protected PHPClassData getContainerClassData(PHPProjectModel projectModel, String fileName, int offset) {
		PHPFileData fileData = projectModel.getFileData(fileName);
		return PHPFileDataUtilities.getContainerClassData(fileData, offset);
	}

	/**
	 * this function searches the sequence from the right closing bracket ")" and finding
	 * the position of the left "("
	 * the offset has to be the offset of the "("
	 */
	protected int getFunctionNameEndOffset(TextSequence statementText, int offset) {
		if (statementText.charAt(offset) != ')') {
			return 0;
		}
		int currChar = offset;
		int bracketsNum = 1;
		char inStringMode = 0;
		while (bracketsNum != 0 && currChar >= 0) {
			currChar--;
			// get the current char
			final char charAt = statementText.charAt(currChar);
			// if it is string close / open - update state
			if (charAt == '\'' || charAt == '"') {
				inStringMode = inStringMode == 0 ? charAt : inStringMode == charAt ? 0 : inStringMode;
			}

			if (inStringMode != 0)
				continue;

			if (charAt == ')') {
				bracketsNum++;
			} else if (charAt == '(') {
				bracketsNum--;
			}
		}
		return currChar;
	}

	/**
	 * Returns the security filter we can use
	 * @param isInstanceOf - true if we are dealing with instance - not $this-> or MyClass::
	 * @return
	 * ModelSupport.PIRVATE_ACCESS_LEVEL_FILTER - if we can see private fields
	 * ModelSupport.PROTECTED_ACCESS_LEVEL_FILTER - if we can see protected fields
	 * ModelSupport.PUBLIC_ACCESS_LEVEL_FILTER - if we can see public fields
	 */
	protected CodeDataFilter getAccessLevelFilter(PHPProjectModel projectModel, String fileName, String className, int offset, boolean isInstanceOf) {
		PHPCodeContext context = getContext(projectModel, fileName, offset);
		String contextClassName = context.getContainerClassName();
		// if the name of the context class is the same as the className itself then we are
		// inside the same class - meaning we can use private methodes.
		if (contextClassName.equals(className)) {
			return ModelSupport.PIRVATE_ACCESS_LEVEL_FILTER;
		}

		//		if this is an instance of a class and not $this
		if (isInstanceOf) {
			return ModelSupport.PUBLIC_ACCESS_LEVEL_FILTER;
		}

		// if we are out side of a class
		if (contextClassName.equals("")) { //$NON-NLS-1$
			return ModelSupport.PUBLIC_ACCESS_LEVEL_FILTER_EXCLUDE_VARS_NOT_STATIC;
		}

		PHPClassData classData = projectModel.getClass(fileName, contextClassName);
		String superClassName = classData.getSuperClassData().getName();
		while (superClassName != null) {
			if (superClassName.equals(className)) {
				return ModelSupport.PROTECTED_ACCESS_LEVEL_FILTER_EXCLUDE_VARS_NOT_STATIC;//exclude non static vars !!!
			}
			classData = projectModel.getClass(fileName, superClassName);
			if (classData == null) {
				break;
			}
			superClassName = classData.getSuperClassData().getName();
		}

		//inside a class with no inheritence
		if (superClassName == null && !contextClassName.equals("")) { //$NON-NLS-1$
			return ModelSupport.PUBLIC_ACCESS_LEVEL_FILTER_EXCLUDE_VARS_NOT_STATIC;
		}

		return ModelSupport.PUBLIC_ACCESS_LEVEL_FILTER;
	}

	protected PHPCodeContext getContext(PHPProjectModel projectModel, String fileName, int offset) {
		PHPFileData fileData = projectModel.getFileData(fileName);
		return ModelSupport.createContext(fileData, offset);
	}

	protected boolean isInFunctionDeclaration(PHPProjectModel projectModel, String fileName, TextSequence text, int offset, int selectionLength, boolean explicit) {
		// are we inside function declaration statement
		int functionStart = PHPTextSequenceUtilities.isInFunctionDeclaration(text);
		if (functionStart == -1) {
			return false;
		}

		// are we inside parameters part in function declaration statement
		for (int i = text.length() - 1; i >= functionStart; i--) {
			if (text.charAt(i) == '(') {
				boolean showClassCompletion = true;
				for (int j = text.length() - 1; j > i; j--) {
					// fixed bug 178032 - check if the cursor is after type means no '$' sign between cursor to '(' sign or ',' sign
					if (text.charAt(j) == '$') {
						showClassCompletion = false;
						break;
					}
					if (text.charAt(j) == ',') {
						break;
					}
				}
				if (showClassCompletion) {
					CodeData[] classes = projectModel.getClasses();
					completionProposalGroup = phpCompletionProposalGroup;
					String prefix = text.subTextSequence(i + 1, text.length()).toString();
					completionProposalGroup.setData(offset, classes, prefix, selectionLength, false);
				}
				return true;
			}
		}

		PHPClassData classData = getContainerClassData(projectModel, fileName, text.getOriginalOffset(functionStart));
		// We look for the container class data in function start offset.

		if (classData == null) {
			// We are not inside class function.
			return true;
		}

		int wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(text, text.length());
		int wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(text, wordEnd, false);
		String word = text.subSequence(wordStart, wordEnd).toString();

		String functionNameStart;
		if (word.equals("function")) { //$NON-NLS-1$
			functionNameStart = ""; //$NON-NLS-1$
		} else if (wordEnd == text.length()) {
			functionNameStart = word;
		} else {
			return true;
		}

		/*if (!explicit && functionNameStart.length() == 0) {
			return true;
		}*/

		CodeData[] data;
		String phpVersion = projectModel.getPHPLanguageModel().getPHPVersion();
		boolean isPHP5 = phpVersion.equals(PHPVersion.PHP5);

		CodeData[] magicMethods = PHPCodeDataFactory.createMagicMethods(classData, isPHP5);
		CodeData[] constructors = PHPCodeDataFactory.createConstructors(classData, isPHP5);

		data = new CodeData[magicMethods.length + constructors.length];
		System.arraycopy(magicMethods, 0, data, 0, magicMethods.length);
		System.arraycopy(constructors, 0, data, magicMethods.length, constructors.length);

		Arrays.sort(data);

		completionProposalGroup = classConstructorCompletionProposalGroup;
		completionProposalGroup.setData(offset, data, functionNameStart, selectionLength);
		return true;
	}

	protected boolean isInClassDeclaration(PHPProjectModel projectModel, TextSequence text, int offset, int selectionLength, boolean explicit) {
		int classEnd = PHPTextSequenceUtilities.isInClassDeclaration(text);
		if (classEnd == -1) {
			return false;
		}
		boolean isClassDeclaration = true;
		if (classEnd >= 6) {
			String classString = text.subSequence(classEnd - 6, classEnd - 1).toString();
			isClassDeclaration = classString.equals("class"); //$NON-NLS-1$
		}
		text = text.subTextSequence(classEnd, text.length());

		// check if we are in the class identifier part.
		int classIdentifierEndPosition = 0;
		for (; classIdentifierEndPosition < text.length(); classIdentifierEndPosition++) {
			if (!Character.isLetterOrDigit(text.charAt(classIdentifierEndPosition))) {
				break;
			}
		}
		// we are in class identifier part.
		if (classIdentifierEndPosition == text.length()) {
			return true;
		}
		text = text.subTextSequence(classIdentifierEndPosition, text.length());

		int endPosition = text.length();
		int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(text, endPosition, false);
		String lastWord = text.subSequence(startPosition, endPosition).toString();

		Matcher extendsMatcher = extendsPattern.matcher(text);
		Matcher implementsMatcher = implementsPattern.matcher(text);
		boolean foundExtends = extendsMatcher.find();
		boolean foundImplements = implementsMatcher.find();
		if (!foundExtends && !foundImplements) {
			if (explicit || lastWord.length() > 0) {
				if (isClassDeclaration) {
					showExtendsImplementsList(projectModel, lastWord, offset, selectionLength, explicit);
				} else {
					showExtendsList(projectModel, lastWord, offset, selectionLength, explicit);
				}
			}
			return true;
		}

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, startPosition);
		startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(text, endPosition, true);
		String firstWord = text.subSequence(startPosition, endPosition).toString();

		if (firstWord.equalsIgnoreCase("extends")) { //$NON-NLS-1$
			showBaseClassList(projectModel, lastWord, offset, selectionLength, isClassDeclaration, explicit);
			return true;
		}

		if (firstWord.equalsIgnoreCase("implements")) { //$NON-NLS-1$
			showInterfaceList(projectModel, lastWord, offset, selectionLength, explicit);
			return true;
		}

		if (foundExtends && foundImplements) {
			if (explicit || lastWord.length() > 0) {
				if (extendsMatcher.start() < implementsMatcher.start()) {
					showInterfaceList(projectModel, lastWord, offset, selectionLength, explicit);
				} else {
					showBaseClassList(projectModel, lastWord, offset, selectionLength, isClassDeclaration, explicit);
				}
			}
			return true;
		}

		if (foundImplements || !isClassDeclaration) {
			if (explicit) {
				showInterfaceList(projectModel, lastWord, offset, selectionLength, explicit);
			}
			return true;
		}
		if ((explicit || lastWord.length() > 0) && isClassDeclaration) {
			showImplementsList(projectModel, lastWord, offset, selectionLength, explicit);
		}
		return true;
	}

	protected void showInterfaceList(PHPProjectModel projectModel, String startWith, int offset, int selectionLength, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}
		CodeData[] classes = projectModel.getClasses();
		ArrayList interfaces = new ArrayList(classes.length / 10);

		for (CodeData element : classes) {
			if (PHPModifier.isInterface(((PHPClassData) element).getModifiers())) {
				interfaces.add(element);
			}
		}
		CodeData[] interfacesArray = new CodeData[interfaces.size()];
		Iterator iter = interfaces.iterator();
		for (int i = 0; i < interfacesArray.length; i++) {
			interfacesArray[i] = (CodeData) iter.next();
		}
		completionProposalGroup = phpCompletionProposalGroup;
		completionProposalGroup.setData(offset, interfacesArray, startWith, selectionLength);
	}

	protected void showExtendsImplementsList(PHPProjectModel projectModel, String startWith, int offset, int selectionLength, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}
		CodeData[] result = getExtendsImplementsCodeData(projectModel);
		completionProposalGroup = phpCompletionProposalGroup;
		completionProposalGroup.setData(offset, result, startWith, selectionLength);
	}

	protected CodeData[] extendedImplementCodeData;

	protected CodeData[] getExtendsImplementsCodeData(PHPProjectModel projectModel) {
		if (extendedImplementCodeData == null) {
			CodeData extendsCodeData = null;
			CodeData implementsCodeData = null;
			CodeData[] keywords = projectModel.getKeywordData();
			for (CodeData element : keywords) {
				if (element.getName().equals("extends")) { //$NON-NLS-1$
					extendsCodeData = element;
				}
				if (element.getName().equals("implements")) { //$NON-NLS-1$
					implementsCodeData = element;
				}
			}
			String phpVersion = projectModel.getPHPLanguageModel().getPHPVersion();
			boolean isPHP5 = phpVersion.equals(PHPVersion.PHP5);
			if (isPHP5) {
				extendedImplementCodeData = new CodeData[] { extendsCodeData, implementsCodeData };
			} else {
				extendedImplementCodeData = new CodeData[] { extendsCodeData };
			}
		}
		return extendedImplementCodeData;
	}

	protected void showImplementsList(PHPProjectModel projectModel, String startWith, int offset, int selectionLength, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}
		CodeData[] result = getImplementsCodeData(projectModel);
		completionProposalGroup = phpCompletionProposalGroup;
		completionProposalGroup.setData(offset, result, startWith, selectionLength);
	}

	protected static CodeData[] implementCodeData;

	protected CodeData[] getImplementsCodeData(PHPProjectModel projectModel) {
		String phpVersion = projectModel.getPHPLanguageModel().getPHPVersion();
		boolean isPHP5 = phpVersion.equals(PHPVersion.PHP5);
		if (!isPHP5) {
			return null;
		}
		if (implementCodeData == null) {
			CodeData implementsCodeData = null;
			CodeData[] keywords = projectModel.getKeywordData();
			for (CodeData element : keywords) {
				if (element.getName().equals("implements")) { //$NON-NLS-1$
					implementsCodeData = element;
					break;
				}
			}
			implementCodeData = new CodeData[] { implementsCodeData };
		}
		return implementCodeData;
	}

	private void showExtendsList(PHPProjectModel projectModel, String startWith, int offset, int selectionLength, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}
		CodeData[] result = getExtendsCodeData(projectModel);

		completionProposalGroup = phpCompletionProposalGroup;
		completionProposalGroup.setData(offset, result, startWith, selectionLength);
	}

	private static CodeData[] extendsCodeData;

	private static CodeData[] getExtendsCodeData(PHPProjectModel projectModel) {

		if (extendsCodeData == null) {
			CodeData extendCodeData = null;
			CodeData[] keywords = projectModel.getKeywordData();
			for (CodeData element : keywords) {
				if (element.getName().equals("extends")) { //$NON-NLS-1$
					extendCodeData = element;
					break;
				}
			}
			extendsCodeData = new CodeData[] { extendCodeData };
		}
		return extendsCodeData;
	}

	private void showBaseClassList(PHPProjectModel projectModel, String startWith, int offset, int selectionLength, boolean isClassDecleration, boolean explicit) {
		if (!isClassDecleration) {
			showInterfaceList(projectModel, startWith, offset, selectionLength, explicit);
			return;
		}
		if (!explicit && !autoShowClassNames) {
			return;
		}

		CodeData[] classes = getOnlyClasses(projectModel);

		completionProposalGroup = phpCompletionProposalGroup;
		completionProposalGroup.setData(offset, classes, startWith, selectionLength);
	}

	private CodeData[] getOnlyClasses(PHPProjectModel projectModel) {
		CodeData[] classes = projectModel.getClasses();
		int numOfInterfaces = 0;
		for (CodeData element : classes) {
			if (PHPModifier.isInterface(((PHPClassData) element).getModifiers())) {
				numOfInterfaces++;
			}
		}
		if (numOfInterfaces == 0) {
			return classes;
		}
		CodeData[] newClasses = new CodeData[classes.length - numOfInterfaces];
		int j = 0;
		for (int i = 0; i < classes.length; i++) {
			if (!PHPModifier.isInterface(((PHPClassData) classes[i]).getModifiers())) {
				newClasses[j] = classes[i];
				j++;
			}
		}
		return newClasses;
	}

	protected boolean isInCatchStatement(PHPProjectModel projectModel, TextSequence text, int offset, int selectionLength, boolean explicit) {
		Matcher matcher = catchPattern.matcher(text);
		int catchStart = text.length();
		while (matcher.find()) {
			if (text.length() == matcher.end()) {
				catchStart = matcher.start() + 1; // for the white space before the 'class'
				break;
			}
		}
		if (catchStart == text.length()) {
			return false;
		}
		int classEnd = catchStart + 5;
		text = text.subTextSequence(classEnd, text.length());

		int startPosition = 0;
		for (; startPosition < text.length(); startPosition++) {
			if (text.charAt(startPosition) == '(') {
				break;
			}
		}
		if (startPosition == text.length()) {
			// the current position is before the '('
			return true;
		}

		startPosition = PHPTextSequenceUtilities.readForwardSpaces(text, startPosition + 1); // + 1 for the '('
		int endPosition = PHPTextSequenceUtilities.readIdentifierEndIndex(text, startPosition, false);
		String className = text.subSequence(startPosition, endPosition).toString();

		if (endPosition == text.length()) {
			showClassList(projectModel, className, offset, selectionLength, false, explicit);
		}
		return true;
	}

	protected void showClassList(PHPProjectModel projectModel, String startWith, int offset, int selectionLength, boolean isNewStatement, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}

		CodeData[] classes;
		if (isNewStatement) {
			completionProposalGroup = newStatementCompletionProposalGroup;
			classes = getOnlyClasses(projectModel);
		} else {
			completionProposalGroup = phpCompletionProposalGroup;
			classes = projectModel.getClasses();
		}

		completionProposalGroup.setData(offset, classes, startWith, selectionLength);
	}

	protected boolean isNewOrInstanceofStatement(PHPProjectModel projectModel, String keyword, String startWith, int offset, int selectionLength, boolean explicit, String type) {
		if (PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}

		if (keyword.equalsIgnoreCase("instanceof")) { //$NON-NLS-1$
			showClassList(projectModel, startWith, offset, selectionLength, false, explicit);
			return true;
		}

		if (keyword.equalsIgnoreCase("new")) { //$NON-NLS-1$
			showClassList(projectModel, startWith, offset, selectionLength, true, explicit);
			return true;
		}

		return false;
	}

	protected boolean isInArrayOption(PHPProjectModel projectModel, String fileName, boolean haveSpacesAtEnd, String firstWord, String lastWord, int startPosition, int offset, int selectionLength, TextSequence text, String type) {
		if (PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}
		boolean isArrayOption = false;
		if (startPosition > 0 && !lastWord.startsWith("$")) { //$NON-NLS-1$
			if (haveSpacesAtEnd) {
				if (lastWord.length() == 0 && firstWord.length() == 0) {
					if (text.charAt(startPosition - 1) == '[') {
						isArrayOption = true;
					}
				}
			} else {
				if (firstWord.length() == 0) {
					if (text.charAt(startPosition - 1) == '[') {
						isArrayOption = true;
					}
				}
			}
		}
		if (!isArrayOption) {
			return false;
		}
		int endPosition = startPosition - 1;

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, endPosition);
		startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(text, endPosition, true);
		String variableName = text.subSequence(startPosition, endPosition).toString();

		if (variableName.startsWith("$")) { //$NON-NLS-1$
			variableName = variableName.substring(1);
		}
		CodeData[] arrayResult = projectModel.getArrayVariables(fileName, variableName, lastWord, determineObjectTypeFromOtherFile);
		CodeData[] functions;
		CodeData[] constans;
		if (lastWord.length() == 0) {
			functions = projectModel.getFunctions();
			constans = disableConstants ? null : projectModel.getConstants();
		} else {
			functions = projectModel.getFunctions(lastWord);
			constans = disableConstants ? null : projectModel.getConstants(lastWord, constantCaseSensitive);
		}
		CodeData[] result = ModelSupport.merge(functions, ModelSupport.merge(arrayResult, constans));

		completionProposalGroup = arrayCompletionProposalGroup;
		completionProposalGroup.setData(offset, result, lastWord, selectionLength);

		return true;
	}

	protected ICompletionProposal[] merg(ICompletionProposal[] sortedArray1, ICompletionProposal[] sortedArray2) {

		// gets the arrays size
		int firstLength = sortedArray1 == null ? 0 : sortedArray1.length;
		int secondLength = sortedArray2 == null ? 0 : sortedArray2.length;

		// creates a united empty one
		ICompletionProposal[] mergedAndSorted = new ICompletionProposal[firstLength + secondLength];

		// copy the two arrays to a new one
		int position = 0;
		if (sortedArray1 != null) {
			System.arraycopy(sortedArray1, 0, mergedAndSorted, 0, firstLength);
			position = firstLength;
		}
		if (sortedArray2 != null) {
			System.arraycopy(sortedArray2, 0, mergedAndSorted, position, secondLength);
		}

		// then sort them
		Arrays.sort(mergedAndSorted, proposalsComperator);

		return mergedAndSorted;
	}

	protected class PHPCompletionProposalGroup extends CompletionProposalGroup {

		@Override
		protected CodeDataCompletionProposal createProposal(PHPProjectModel projectModel, CodeData codeData) {
			String suffix = " "; //$NON-NLS-1$
			int caretOffsetInSuffix = 1;
			boolean showTypeHints = false;

			if (codeData instanceof PHPKeywordData) {
				PHPKeywordData phpKeywordData = (PHPKeywordData) codeData;
				suffix = phpKeywordData.getSuffix();
				caretOffsetInSuffix = phpKeywordData.getSuffixOffset();
			} else if (codeData instanceof PHPTagData) {
				suffix = ""; //$NON-NLS-1$
				caretOffsetInSuffix = 0;
			} else if (codeData instanceof PHPFunctionData) {
				PHPFunctionData phpFunctionData = (PHPFunctionData) codeData;
				suffix = "()"; //$NON-NLS-1$
				showTypeHints = true;
				caretOffsetInSuffix = 2;
				boolean hasArgs = phpFunctionData.getParameters().length > 0;
				if (hasArgs) {
					caretOffsetInSuffix--; /* put the cursor between the parentheses */
				}
				String returnType = phpFunctionData.getReturnType();
				if (returnType != null && returnType.compareToIgnoreCase("void") == 0) { //$NON-NLS-1$
					suffix += ";"; //$NON-NLS-1$
					if (!hasArgs) {
						caretOffsetInSuffix++;
					}
				}
			} else if (codeData instanceof PHPVariableData || codeData instanceof PHPConstantData || codeData instanceof PHPClassConstData) {
				suffix = ""; //$NON-NLS-1$
				caretOffsetInSuffix = 0;
			}
			return new CodeDataCompletionProposal(projectModel, codeData, getOffset() - key.length(), key.length(), selectionLength, "", suffix, caretOffsetInSuffix, showTypeHints); //$NON-NLS-1$
		}
	}

	private class RegularPHPCompletionProposalGroup extends PHPCompletionProposalGroup {
		@Override
		protected CodeDataCompletionProposal createProposal(PHPProjectModel projectModel, CodeData codeData) {
			if (!(codeData instanceof PHPClassData)) {
				return super.createProposal(projectModel, codeData);
			}
			return new CodeDataCompletionProposal(projectModel, codeData, getOffset() - key.length(), key.length(), selectionLength, "", "::", 2, false); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private class ClassConstructorCompletionProposalGroup extends CompletionProposalGroup {
		@Override
		protected CodeDataCompletionProposal createProposal(PHPProjectModel projectModel, CodeData codeData) {
			return new CodeDataCompletionProposal(projectModel, codeData, getOffset() - key.length(), key.length(), selectionLength, "", "()", 1, true); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private class NewStatementCompletionProposalGroup extends CompletionProposalGroup {
		@Override
		protected CodeDataCompletionProposal createProposal(PHPProjectModel projectModel, CodeData codeData) {
			PHPClassData classData = (PHPClassData) codeData;
			PHPFunctionData constructor = classData.getUserData() != null ? PHPModelUtil.getRealConstructor(projectModel, classData.getUserData().getFileName(), classData) : null;
			int suffixOffset = constructor != null && constructor.getParameters().length > 0 ? 1 : 2;

			return new CodeDataCompletionProposal(projectModel, classData, getOffset() - key.length(), key.length(), selectionLength, "", "()", suffixOffset, true); //$NON-NLS-1$ //$NON-NLS-2$
		}

	}

	private class ArrayCompletionProposalGroup extends PHPCompletionProposalGroup {
		@Override
		protected CodeDataCompletionProposal createProposal(PHPProjectModel projectModel, CodeData codeData) {
			if (!(codeData instanceof PHPVariableData)) {
				return super.createProposal(projectModel, codeData);
			}
			CodeDataCompletionProposal proposal = new ArrayCompletionProposal(codeData, getOffset() - key.length(), key.length(), selectionLength, "'", "'", 1); //$NON-NLS-1$ //$NON-NLS-2$
			return proposal;
		}

		private class ArrayCompletionProposal extends CodeDataCompletionProposal {

			public ArrayCompletionProposal(CodeData codeData, int offset, int length, int selectionLength, String prefix, String suffix, int caretOffsetInSuffix) {
				super(projectModel, codeData, offset, length, selectionLength, prefix, suffix, caretOffsetInSuffix, false);
			}

			@Override
			public void apply(IDocument document) {
				try {
					boolean insertCompletion = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_INSERT_COMPLETION);
					if (!insertCompletion) { //need to override the text after the cursor
						removeTrailingCharacters(document);
					}
					char ch = document.getChar(replacementOffset - 1);
					if (ch == '\'' || ch == '\"') {
						replacementOffset--;
						replacementLength++;

						int position = replacementOffset + replacementLength + selectionLength;
						int length = document.getLength();

						int spacesToRemove = 0;
						boolean removeSpaces = false;
						while (position < length) {
							char ch2 = document.getChar(position);
							if (ch2 == ch) {
								spacesToRemove++;
								removeSpaces = true;
								break;
							}
							if (!Character.isWhitespace(ch2)) {
								break;
							}
							spacesToRemove++;
							position++;
						}
						if (removeSpaces) {
							replacementLength += spacesToRemove;
						}
					}

					document.replace(replacementOffset, replacementLength + selectionLength, getReplacementString());
				} catch (BadLocationException e) {
					super.apply(document);
				}
			}

		}
	}

	private class ClassVariableCallCompletionProposalGroup extends PHPCompletionProposalGroup {
		@Override
		protected CodeDataCompletionProposal createProposal(PHPProjectModel projectModel, CodeData codeData) {
			if (codeData instanceof PHPClassVarData) {
				return new CodeDataCompletionProposal(projectModel, codeData, getOffset() - key.length(), key.length(), selectionLength, "$", "", 0, false); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return super.createProposal(projectModel, codeData);
		}
	}

	private class ClassStaticCallCompletionProposalGroup extends PHPCompletionProposalGroup {

		@Override
		protected CodeDataCompletionProposal createProposal(PHPProjectModel projectModel, CodeData codeData) {
			if (codeData instanceof PHPClassVarData) {
				return new CodeDataCompletionProposal(projectModel, codeData, getOffset() - key.length(), key.length(), selectionLength, "$", "", 0, false); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return super.createProposal(projectModel, codeData);
		}

		@Override
		protected ICompletionProposal[] calcCompletionProposals(PHPProjectModel projectModel) {

			if (key.length() == 0) {
				return super.calcCompletionProposals(projectModel);
			}

			CodeData[] tmp;
			if (key.charAt(0) == '$') {
				tmp = ModelSupport.getFilteredCodeData(codeDataProposals, ModelSupport.STATIC_VARIABLES_FILTER);
				tmp = ModelSupport.getCodeDataStartingWith(tmp, key.substring(1));
			} else {
				tmp = ModelSupport.getCodeDataStartingWith(codeDataProposals, key);
				tmp = ModelSupport.getFilteredCodeData(tmp, ModelSupport.NOT_STATIC_VARIABLES_FILTER);
			}

			CodeDataCompletionProposal[] result = new CodeDataCompletionProposal[tmp.length];
			for (int i = 0; i < tmp.length; i++) {
				result[i] = createProposal(projectModel, tmp[i]);
			}
			return result;
		}
	}

}
