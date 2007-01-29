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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.parser.*;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.util.WeakPropertyChangeListener;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateCompletionProcessor;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateContextTypeIds;
import org.eclipse.php.internal.ui.editor.util.TextSequence;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.editor.contentassist.IContentAssistSupport;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.sse.ui.internal.contentassist.ContentAssistUtils;

public class ContentAssistSupport implements IContentAssistSupport {

	protected static final char[] phpDelimiters = new char[] { '?', ':', ';', '|', '^', '&', '<', '>', '+', '-', '.', '*', '/', '%', '!', '~', '[', ']', '(', ')', '{', '}', '@', '\n', '\t', ' ', ',', '$', '\'', '\"' };
	protected static final String CLASS_FUNCTIONS_TRIGGER = "::";
	protected static final String OBJECT_FUNCTIONS_TRIGGER = "->";

	private static final Pattern extendsPattern = Pattern.compile("\\Wextends\\W", Pattern.CASE_INSENSITIVE);
	private static final Pattern implementsPattern = Pattern.compile("\\Wimplements", Pattern.CASE_INSENSITIVE);
	private static final Pattern catchPattern = Pattern.compile("catch\\s[^{]*", Pattern.CASE_INSENSITIVE);

	public static final ICompletionProposal[] EMPTY_CompletionProposal_ARRAY = new ICompletionProposal[0];
	public static final CodeDataCompletionProposal[] EMPTY_CodeDataCompletionProposal_ARRAY = new CodeDataCompletionProposal[0];
	private static final PHPProposalComperator proposalsComperator = new PHPProposalComperator();

	protected boolean showVariablesFromOtherFiles;
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
			|| PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP.equals(prefKey)) {

			showVariablesFromOtherFiles = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES);
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
	private CompletionProposalGroup regularPHPCompletionProposalGroup = new RegularPHPCompletionProposalGroup();
	private CompletionProposalGroup classConstructorCompletionProposalGroup = new ClassConstructorCompletionProposalGroup();
	private CompletionProposalGroup newStatmentCompletionProposalGroup = new NewStatmentCompletionProposalGroup();
	private CompletionProposalGroup arrayCompletionProposalGroup = new ArrayCompletionProposalGroup();
	private CompletionProposalGroup classStaticCallCompletionProposalGroup = new ClassStaticCallCompletionProposalGroup();
	protected CompletionProposalGroup classVariableCallCompletionProposalGroup = new ClassVariableCallCompletionProposalGroup();

	public ContentAssistSupport() {
		// Initialize all preferences
		initPreferences(null);

		// Listen to preferences changes
		PreferenceConstants.getPreferenceStore().addPropertyChangeListener(WeakPropertyChangeListener.create(prefChangeListener, PreferenceConstants.getPreferenceStore()));
	}

	public ICompletionProposal[] getCompletionOption(ITextViewer viewer, DOMModelForPHP phpDOMModel, int offset) throws BadLocationException {
		ICompletionProposal[] codeCompletionOptions = getCodeCompletionOptions(viewer, phpDOMModel, offset);
		if (codeCompletionOptions == null) {
			return new ICompletionProposal[0];
		}
		return codeCompletionOptions;
	}

	private ICompletionProposal[] getCodeCompletionOptions(ITextViewer viewer, DOMModelForPHP phpEditorModel, int offset) throws BadLocationException {
		completionProposalGroup = null;
		templateProposals = null;
		calcCompletionOption(phpEditorModel, offset, viewer);
		if (completionProposalGroup == null) {
			return templateProposals;
		}
		return merg(completionProposalGroup.getCompletionProposals(), templateProposals);
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

	protected void calcCompletionOption(DOMModelForPHP editorModel, int offset, ITextViewer viewer) throws BadLocationException {

		final int originalOffset = viewer.getSelectedRange().x;
		final boolean isStrict = originalOffset != offset ? true : false;

		PHPProjectModel projectModel = editorModel.getProjectModel();

		// if there is no project model (the file is not part of a project)
		// get the default project model
		if (projectModel == null) {
			projectModel = PHPWorkspaceModelManager.getDefaultPHPProjectModel();
		}

		String fileName = null;
		PHPFileData fileData = editorModel.getFileData(true);
		if (fileData != null) {
			fileName = fileData.getName();
		}
		boolean explicit = true;
		int selectionLength = ((TextSelection) viewer.getSelectionProvider().getSelection()).getLength();

		IStructuredDocumentRegion sdRegion = ContentAssistUtils.getStructuredDocumentRegion((StructuredTextViewer) viewer, offset);
		ITextRegion textRegion = null;
		// 	in case we are at the end of the document, asking for completion
		if (offset == editorModel.getStructuredDocument().getLength()) {
			textRegion = sdRegion.getLastRegion();
		} else {
			textRegion = sdRegion.getRegionAtCharacterOffset(offset);
		}

		if (textRegion == null)
			return;

		if (textRegion.getType() == PHPRegionContext.PHP_OPEN) {
			return;
		}
		if (textRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			if (sdRegion.getStartOffset(textRegion) == offset) {
				ITextRegion regionBefore = sdRegion.getRegionAtCharacterOffset(offset - 1);
				if (regionBefore instanceof PhpScriptRegion) {
					textRegion = regionBefore;
				}
			} else {
				return;
			}
		}

		// find the start String for completion
		int startOffset = sdRegion.getStartOffset(textRegion);

		//in case we are standing at the beginning of a word and asking for completion 
		//should not take into account the found region
		//find the previous region and update the start offset
		if (startOffset == offset) {
			ITextRegion preTextRegion = sdRegion.getRegionAtCharacterOffset(offset - 1);
			IStructuredDocumentRegion preSdRegion = null;
			if (preTextRegion != null || ((preSdRegion = sdRegion.getPrevious()) != null && (preTextRegion = preSdRegion.getRegionAtCharacterOffset(offset - 1)) != null)) {
				if (preTextRegion.getType() == "") {
					// TODO needs to be fixed. The problem is what to do if the cursor is exatly between problematic regions, e.g. single line comment and quoted string?? 
				}
			}
			startOffset = sdRegion.getStartOffset(textRegion);
		}

		PhpScriptRegion phpScriptRegion = null;
		String partitionType = null;
		int internalOffset = 0;
		ContextRegion internalPHPRegion = null;
		if (textRegion instanceof PhpScriptRegion) {
			phpScriptRegion = (PhpScriptRegion) textRegion;
			internalOffset = offset - sdRegion.getStartOffset() - phpScriptRegion.getStart();

			partitionType = phpScriptRegion.getPartition(internalOffset);
			//if we are at the begining of multi-line comment or docBlock then we should get completion.
			if(partitionType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT || partitionType == PHPPartitionTypes.PHP_DOC){
				String regionType = phpScriptRegion.getPhpToken(internalOffset).getType();
				if(regionType == PHPRegionTypes.PHP_COMMENT_START || regionType == PHPRegionTypes.PHPDOC_COMMENT_START ){
					if(phpScriptRegion.getPhpToken(internalOffset).getStart() == internalOffset){
						partitionType = phpScriptRegion.getPartition(internalOffset - 1);
					}
				}
			}
			if ((partitionType == PHPPartitionTypes.PHP_DEFAULT) || (partitionType == PHPPartitionTypes.PHP_QUOTED_STRING) || (partitionType == PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT)) {
			} else {
				return;
			}
			internalPHPRegion = (ContextRegion) phpScriptRegion.getPhpToken(internalOffset);
		}

		// if there is no project model (the file is not part of a project)
		// complete with language model only 
		if (fileData == null || phpScriptRegion == null) {
			getRegularCompletion(viewer, projectModel, "", "", offset, selectionLength, explicit, sdRegion, phpScriptRegion, internalPHPRegion, isStrict);
			return;
		}

		TextSequence statmentText = PHPTextSequenceUtilities.getStatment(offset, sdRegion, true);

		String type = internalPHPRegion.getType();
		if (isInArrayOptionQuotes(projectModel, fileName, type, offset, selectionLength, statmentText)) {
			// the current position is inside quotes as a parameter for an array.
			return;
		}

		if (isPHPSingleQuote(sdRegion, phpScriptRegion, internalPHPRegion, offset) || isLineComment(sdRegion, phpScriptRegion, offset)) {
			// we dont have code completion inside single quotes.
			return;
		}

		if (isInFunctionDeclaretion(projectModel, fileName, statmentText, offset, selectionLength, explicit)) {
			// the current position is inside function declaretion.
			return;
		}

		if (isInClassDeclaretion(projectModel, statmentText, offset, selectionLength, explicit)) {
			// the current position is inside class declaretion.
			return;
		}

		if (isInCatchStatment(projectModel, statmentText, offset, selectionLength, explicit)) {
			// the current position is inside catch statment.
			return;
		}

		int totalLength = statmentText.length();

		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statmentText, totalLength); // read whitespace
		int startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(statmentText, endPosition, true);
		String lastWord = statmentText.subSequence(startPosition, endPosition).toString();
		boolean haveSpacesAtEnd = totalLength != endPosition;

		if (haveSpacesAtEnd && isNewOrInstanceofStatment(projectModel, lastWord, "", offset, selectionLength, explicit, type)) {
			// the current position is inside new or instanceof statment.
			return;
		}

		int line = sdRegion.getParentDocument().getLineOfOffset(offset);
		if (isClassFunctionCompletion(projectModel, fileName, statmentText, offset, line, selectionLength, lastWord, startPosition, haveSpacesAtEnd, explicit, isStrict)) {
			// the current position is in class function.
			return;
		}

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statmentText, startPosition); // read whitespace
		startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(statmentText, endPosition, true);
		String firstWord = statmentText.subSequence(startPosition, endPosition).toString();

		if (!haveSpacesAtEnd && isNewOrInstanceofStatment(projectModel, firstWord, lastWord, offset, selectionLength, explicit, type)) {
			// the current position is inside new or instanceof statment.
			return;
		}

		if (haveSpacesAtEnd && isFunctionCall(projectModel, lastWord)) {
			// the current position is between the end of a function call and open bracket.
			return;
		}

		if (isInArrayOption(projectModel, fileName, haveSpacesAtEnd, firstWord, lastWord, startPosition, offset, selectionLength, statmentText)) {
			// the current position is after '[' sign show special completion.
			return;
		}

		if (haveSpacesAtEnd) {
			getRegularCompletion(viewer, projectModel, fileName, "", offset, selectionLength, explicit, sdRegion, phpScriptRegion, internalPHPRegion, isStrict);
		} else {
			getRegularCompletion(viewer, projectModel, fileName, lastWord, offset, selectionLength, explicit, sdRegion, phpScriptRegion, internalPHPRegion, isStrict);
		}

		return;
	}

	protected static boolean isFunctionCall(PHPProjectModel projectModel, String functionName) {
		CodeData[] functionsData = projectModel.getFunction(functionName);
		return functionsData != null && functionsData.length > 0;
	}

	protected boolean isLineComment(IStructuredDocumentRegion sdRegion, PhpScriptRegion phpScriptRegion, int offset) {
		int relativeOffset = offset - sdRegion.getStartOffset(phpScriptRegion);
		try {
			return phpScriptRegion.isLineComment(relativeOffset);
		} catch (BadLocationException e) {
			Logger.logException(e);
			return false;
		}
	}

	protected boolean isPHPSingleQuote(IStructuredDocumentRegion sdRegion, PhpScriptRegion phpScriptRegion, ContextRegion internalRegion, int documentOffset) {
		if (PHPPartitionTypes.isPHPQuotesState(internalRegion.getType())) {
			char firstChar;
			int startOffset;
			int endOffset;
			try {
				startOffset = internalRegion.getStart() + sdRegion.getStartOffset(phpScriptRegion);
				endOffset = startOffset + internalRegion.getTextLength();
				firstChar = sdRegion.getParentDocument().get(startOffset, internalRegion.getTextLength()).charAt(0);
			} catch (BadLocationException e) {
				Logger.logException(e);
				return false;
			}
			return (firstChar == '\'') && (documentOffset <= endOffset - 1) && (startOffset < documentOffset);
		}
		return false;
	}

	protected boolean isInArrayOptionQuotes(PHPProjectModel projectModel, String fileName, String type, int offset, int selectionLength, TextSequence text) {
		if (!PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}
		int length = text.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, length);
		int startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, false);
		if (endPosition != length && startPosition != endPosition) {
			return false;
		}

		String startWith = text.subSequence(startPosition, endPosition).toString();
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, startPosition);
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
		startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, true);
		String variableName = text.subSequence(startPosition, endPosition).toString();

		if (variableName.startsWith("$")) {
			variableName = variableName.substring(1);
		}
		CodeData[] result = projectModel.getArrayVariables(fileName, variableName, startWith, determineObjectTypeFromOtherFile);

		completionProposalGroup = arrayCompletionProposalGroup;
		completionProposalGroup.setData(offset, result, startWith, selectionLength);
		return true;
	}

	protected void getRegularCompletion(final ITextViewer viewer, final PHPProjectModel projectModel, final String fileName, String startsWith, final int offset, final int selectionLength, boolean explicit, final IStructuredDocumentRegion sdRegion, final ITextRegion tRegion,
			final ContextRegion internalPhpRegion, boolean isStrict) {
		if (!explicit && startsWith.length() == 0)
			return;

		if (internalPhpRegion != null) {
			final String type = internalPhpRegion.getType();

			if (startsWith.startsWith("$")) {
				if (!explicit && !autoShowVariables)
					return;
				if (PHPPartitionTypes.isPHPQuotesState(type)) {
					final IStructuredDocument doc = sdRegion.getParentDocument();
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
				completionProposalGroup.setData(offset, null, startsWith, selectionLength, isStrict);
				return;
			}
		}

		CodeData[] functions = null;
		CodeData[] constans = null;
		CodeData[] keywords = null;

		if (explicit || autoShowFunctionsKeywordsConstants) {
			if (startsWith.length() == 0)
				functions = projectModel.getFunctions();
			else {
				functions = projectModel.getFunctions(startsWith);
			}

			if (!disableConstants)
				if (startsWith.length() == 0)
					constans = projectModel.getConstants();
				else {
					constans = projectModel.getConstants(startsWith, constantCaseSensitive);
				}

			keywords = projectModel.getKeywordData();
		}

		CodeData[] classes = null;
		if (showClassNamesInGlobalList)
			if (explicit || autoShowClassNames)
				classes = projectModel.getClasses();

		CodeData[] mergeData = null;
		if (shouldAddPHPTag(sdRegion.getParentDocument(), offset, startsWith))
			mergeData = phpTagDataArray;

		mergeData = ModelSupport.merge(keywords, mergeData);
		mergeData = ModelSupport.merge(classes, mergeData);
		mergeData = ModelSupport.merge(constans, mergeData);
		mergeData = ModelSupport.merge(functions, mergeData);

		completionProposalGroup = regularPHPCompletionProposalGroup;
		completionProposalGroup.setData(offset, mergeData, startsWith, selectionLength, isStrict);

		templateProposals = getTemplates(viewer, offset);

		return;
	}

	private boolean shouldAddPHPTag(IStructuredDocument doc, int offset, String startsWith) {
		offset -= startsWith.length() + 2;
		try {
			String text = doc.get(offset, 2);
			if (text.equals("<?")) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	private static final PHPTagData[] phpTagDataArray = { new PHPTagData() };

	private static class PHPTagData extends PHPCodeDataFactory.PHPFunctionDataImp {

		PHPTagData() {
			super("php", 0, null, null, PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, "");
		}

	}

	protected boolean isClassFunctionCompletion(PHPProjectModel projectModel, String fileName, TextSequence statmentText, int offset, int line, int selectionLength, String functionName, int startFunctionPosition, boolean haveSpacesAtEnd, boolean explicit, boolean isStrict) {
		startFunctionPosition = PHPTextSequenceUtilities.readBackwardSpaces(statmentText, startFunctionPosition);
		if (startFunctionPosition <= 2) {
			return false;
		}
		boolean isClassTriger = false;
		boolean isParent = false;
		String triggerText = statmentText.subSequence(startFunctionPosition - 2, startFunctionPosition).toString();
		if (triggerText.equals(OBJECT_FUNCTIONS_TRIGGER)) {
		} else if (triggerText.equals(CLASS_FUNCTIONS_TRIGGER)) {
			isClassTriger = true;
			if (startFunctionPosition >= 8) {
				String parentText = statmentText.subSequence(startFunctionPosition - 8, startFunctionPosition - 2).toString();
				if (parentText.equals("parent")) {
					isParent = true;
				}
			}
		} else {
			return false;
		}

		String className = getClassName(projectModel, fileName, statmentText, startFunctionPosition, offset, line);

		if (className == null) {
			className = "";
		}

		if (haveSpacesAtEnd && functionName.length() > 0) {
			// check if current position is between the end of a function call and open bracket.
			return isClassFunctionCall(projectModel, fileName, className, functionName);
		}

		if (isClassTriger) {
			if (isParent) {
				showParentCall(projectModel, fileName, offset, className, functionName, selectionLength, explicit, isStrict);
			} else {
				showClassStaticCall(projectModel, fileName, offset, className, functionName, selectionLength, explicit);
			}
		} else {
			String parent = statmentText.toString().substring(0, statmentText.toString().lastIndexOf(OBJECT_FUNCTIONS_TRIGGER)).trim();
			boolean isInstanceOf = !parent.equals("$this");
			//boolean addVariableDollar = parent.endsWith("()");
			boolean addVariableDollar = false;
			showClassCall(projectModel, fileName, offset, className, functionName, selectionLength, isInstanceOf, addVariableDollar, explicit, isStrict);
		}
		return true;
	}

	/**
	 * returns the type of the variable in the sequence.
	 *
	 * @param statmentText
	 * @param endPosition  - the end offset in the sequence
	 * @param offset       - the offset in the document
	 */

	protected String getClassName(PHPProjectModel projectModel, String fileName, TextSequence statmentText, int endPosition, int offset, int line) {
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statmentText, endPosition); // read whitespace

		boolean isClassTriger = false;

		String triggerText = statmentText.subSequence(endPosition - 2, endPosition).toString();
		if (triggerText.equals(OBJECT_FUNCTIONS_TRIGGER)) {
		} else if (triggerText.equals(CLASS_FUNCTIONS_TRIGGER)) {
			isClassTriger = true;
		} else {
			return null;
		}

		int propertyEndPosition = PHPTextSequenceUtilities.readBackwardSpaces(statmentText, endPosition - 2);
		int lastObjectOperator = PHPTextSequenceUtilities.getPrivousTriggerIndex(statmentText, propertyEndPosition);

		if (lastObjectOperator == -1) {
			// if there is no "->" or "::" in the left sequence then we need to calc the object type
			return innerGetClassName(projectModel, fileName, statmentText, propertyEndPosition, isClassTriger, offset, line);
		}

		int propertyStartPosition = PHPTextSequenceUtilities.readForwardSpaces(statmentText, lastObjectOperator + 2);
		String propertyName = statmentText.subSequence(propertyStartPosition, propertyEndPosition).toString();
		String className = getClassName(projectModel, fileName, statmentText, propertyStartPosition, offset, line);

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
	protected String innerGetClassName(PHPProjectModel projectModel, String fileName, TextSequence statmentText, int propertyEndPosition, boolean isClassTriger, int offset, int line) {

		int classNameStart = PHPTextSequenceUtilities.readIdentifiarStartIndex(statmentText, propertyEndPosition, true);
		String className = statmentText.subSequence(classNameStart, propertyEndPosition).toString();
		if (isClassTriger) {
			if (className.equals("self")) {
				PHPClassData classData = getContainerClassData(projectModel, fileName, offset - 6); //the offset before self::
				if (classData != null) {
					return classData.getName();
				}
			} else if (className.equals("parent")) {
				PHPClassData classData = getContainerClassData(projectModel, fileName, offset - 8); //the offset before parent::
				if (classData != null) {
					return projectModel.getSuperClassName(fileName, classData.getName());
				}
			}
			return className;
		}
		// if its object call calc the object type.
		if (className.length() > 0 && className.charAt(0) == '$') {
			int statmentStart = offset - statmentText.length();
			return PHPFileDataUtilities.getVariableType(fileName, className, statmentStart, line, projectModel.getPHPUserModel(), determineObjectTypeFromOtherFile);
		}
		// if its function call calc the return type.
		if (statmentText.charAt(propertyEndPosition - 1) == ')') {
			int functionNameEnd = getFunctionNameEndOffset(statmentText, propertyEndPosition - 1);
			int functionNameStart = PHPTextSequenceUtilities.readIdentifiarStartIndex(statmentText, functionNameEnd, false);

			String functionName = statmentText.subSequence(functionNameStart, functionNameEnd).toString();
			PHPClassData classData = getContainerClassData(projectModel, fileName, offset);
			if (classData != null) { //if its a clss function
				return getFunctionReturnType(projectModel, fileName, classData.getName(), functionName);
			}
			// if its a non class function
			PHPFileData fileData = projectModel.getFileData(fileName);
			PHPFunctionData[] functions = fileData.getFunctions();
			for (int i = 0; i < functions.length; i++) {
				PHPFunctionData function = functions[i];
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
	protected String getVarType(PHPProjectModel projectModel, String fileName, String className, String varName, int statmentStart, int line) {
		String tempType = PHPFileDataUtilities.getVariableType(fileName, "this;*" + varName, statmentStart, line, projectModel.getPHPUserModel(), determineObjectTypeFromOtherFile);
		if (tempType != null) {
			return tempType;
		}
		CodeData classVar = projectModel.getClassVariablesData(fileName, className, varName);
		if (classVar != null) {
			if (classVar instanceof PHPClassVarData) {
				return ((PHPClassVarData) classVar).getClassType();
			}
			return null;
		}

		// checking if the var bellongs to one of the class's ancestor

		PHPClassData classData = projectModel.getClass(fileName, className);

		if (classData == null) {
			return null;
		}
		PHPClassData.PHPSuperClassNameData superClassNameData = classData.getSuperClassData();
		if (superClassNameData == null) {
			return null;
		}
		return getVarType(projectModel, fileName, superClassNameData.getName(), varName, statmentStart, line);
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

		// checking if the function bellongs to one of the class's ancestor
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
			for (int i = 0; i < functions.length; i++) {
				CodeData function = functions[i];
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
			for (int i = 0; i < functions.length; i++) {
				CodeData function = functions[i];
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
		CodeData[] functions = null;
		if (explicit || autoShowFunctionsKeywordsConstants) {
			functions = projectModel.getClassFunctions(fileName, className, startWith.length() == 0 ? "" : startWith);
		}
		CodeData[] classVariables = null;
		if (explicit || autoShowVariables) {
			classVariables = ModelSupport.getFilteredCodeData(projectModel.getClassVariables(fileName, className, ""), ModelSupport.NOT_STATIC_VARIABLES_FILTER);
		}
		CodeData[] result = ModelSupport.getFilteredCodeData(ModelSupport.merge(functions, classVariables), getAccessLevelFilter(projectModel, fileName, className, offset, isInstanceOf));

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
			functions = projectModel.getClassFunctions(fileName, className, "");
			String phpVersion = projectModel.getPHPLanguageModel().getPHPVersion();
			boolean isPHP5 = phpVersion.equals(PHPVersion.PHP5);
			if (isPHP5 && !showNonStrictOptions) {
				functions = ModelSupport.getFilteredCodeData(functions, ModelSupport.STATIC_FUNCTIONS_FILTER);
			}
		}
		CodeData[] classVariables = null;
		if (explicit || autoShowVariables) {
			classVariables = ModelSupport.merge(projectModel.getClassVariables(fileName, className, ""), projectModel.getClassConsts(fileName, className, ""));
		}
		CodeData[] result = ModelSupport.getFilteredCodeData(ModelSupport.merge(functions, classVariables), getAccessLevelFilter(projectModel, fileName, className, offset, false));
		completionProposalGroup = classStaticCallCompletionProposalGroup;
		completionProposalGroup.setData(offset, result, startWith, selectionLength);
	}

	protected void showParentCall(PHPProjectModel projectModel, String fileName, int offset, String className, String startWith, int selectionLength, boolean explicit, boolean isStrict) {
		CodeData[] functions = null;
		if (explicit || autoShowFunctionsKeywordsConstants) {
			functions = projectModel.getClassFunctions(fileName, className, startWith.length() == 0 ? "" : startWith);
		}
		PHPClassData classData = projectModel.getClass(fileName, className);
		//adding the default C'tor and D'tor if they don't exist
		boolean ctorExists = false;
		boolean dtorExists = false;
		for (int i = 0; i < functions.length; i++) {
			CodeData data = functions[i];
			if (data.getName().equals(PHPClassData.CONSTRUCTOR)) {
				ctorExists = true;
			}
			if (data.getName().equals(PHPClassData.DESCRUCTOR)) {
				dtorExists = true;
			}
		}
		int addedFunctions = 0;
		if (!ctorExists) {
			addedFunctions++;
		}
		if (!dtorExists) {
			addedFunctions++;
		}
		CodeData[] result = functions;
		if (addedFunctions > 0) {
			result = new CodeData[functions.length + addedFunctions];
			System.arraycopy(functions, 0, result, 0, functions.length);

			if (ctorExists) {
				if (!dtorExists) {
					result[functions.length] = PHPCodeDataFactory.createPHPFuctionData(PHPClassData.DESCRUCTOR, PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null);
				}
			} else {
				result[functions.length] = PHPCodeDataFactory.createPHPFuctionData(PHPClassData.CONSTRUCTOR, PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null);
				if (!dtorExists) {
					result[functions.length + 1] = PHPCodeDataFactory.createPHPFuctionData(PHPClassData.DESCRUCTOR, PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null);
				}
			}
		}
		result = ModelSupport.getFilteredCodeData(result, ModelSupport.PROTECTED_ACCESS_LEVEL_FILTER_EXCLUDE_VARS_NOT_STATIC);
		completionProposalGroup = phpCompletionProposalGroup;
		completionProposalGroup.setData(offset, result, startWith, selectionLength, isStrict);
	}

	protected PHPClassData getContainerClassData(PHPProjectModel projectModel, String fileName, int offset) {
		PHPFileData fileData = projectModel.getFileData(fileName);
		return PHPFileDataUtilities.getContainerClassDada(fileData, offset);
	}

	/**
	 * this function searches the sequence from the right closing bracket ")" and finding
	 * the position of the left "("
	 * the offset has to be the offset of the "("
	 */
	protected int getFunctionNameEndOffset(TextSequence statmentText, int offset) {
		if (statmentText.charAt(offset) != ')') {
			return 0;
		}
		int currChar = offset;
		int bracketsNum = 1;
		while (bracketsNum != 0 && currChar >= 0) {
			currChar--;
			if (statmentText.charAt(currChar) == ')') {
				bracketsNum++;
			} else if (statmentText.charAt(currChar) == '(') {
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
		if (contextClassName.equals("")) {
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
		if (superClassName == null && !contextClassName.equals("")) {
			return ModelSupport.PUBLIC_ACCESS_LEVEL_FILTER_EXCLUDE_VARS_NOT_STATIC;
		}

		return ModelSupport.PUBLIC_ACCESS_LEVEL_FILTER;
	}

	protected PHPCodeContext getContext(PHPProjectModel projectModel, String fileName, int offset) {
		PHPFileData fileData = projectModel.getFileData(fileName);
		return ModelSupport.createContext(fileData, offset);
	}

	protected boolean isInFunctionDeclaretion(PHPProjectModel projectModel, String fileName, TextSequence text, int offset, int selectionLength, boolean explicit) {
		// are we inside function declaretion statment
		int functionStart = PHPTextSequenceUtilities.isInFunctionDeclaretion(text);
		if (functionStart == -1) {
			return false;
		}
		PHPClassData classData = getContainerClassData(projectModel, fileName, text.getOriginalOffset(functionStart));
		// We look for the container class data in function start offset.

		if (classData == null) {
			// We are not inside class function.
			return true;
		}

		// are we inside parameters part in function declaretion statment
		for (int i = text.length() - 1; i >= functionStart; i--) {
			if (text.charAt(i) == '(') {
				return true;
			}
		}

		int wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(text, text.length());
		int wordStart = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, wordEnd, false);
		String word = text.subSequence(wordStart, wordEnd).toString();

		String functionNameStart;
		if (word.equals("function")) {
			functionNameStart = "";
		} else if (wordEnd == text.length()) {
			functionNameStart = word;
		} else {
			return true;
		}

		if (!explicit && functionNameStart.length() == 0) {
			return true;
		}

		CodeData[] data;
		String phpVersion = projectModel.getPHPLanguageModel().getPHPVersion();
		boolean isPHP5 = phpVersion.equals(PHPVersion.PHP5);
		if (isPHP5) {
			data = new CodeData[] { PHPCodeDataFactory.createPHPFuctionData(PHPClassData.CONSTRUCTOR, PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null),
				PHPCodeDataFactory.createPHPFuctionData(PHPClassData.DESCRUCTOR, PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null),
				PHPCodeDataFactory.createPHPFuctionData(classData.getName(), PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null), };
		} else {
			data = new CodeData[] { PHPCodeDataFactory.createPHPFuctionData(classData.getName(), PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null), };
		}
		completionProposalGroup = classConstructorCompletionProposalGroup;
		completionProposalGroup.setData(offset, data, functionNameStart, selectionLength);
		return true;
	}

	protected boolean isInClassDeclaretion(PHPProjectModel projectModel, TextSequence text, int offset, int selectionLength, boolean explicit) {
		int classEnd = PHPTextSequenceUtilities.isInClassDeclaretion(text);
		if (classEnd == -1) {
			return false;
		}
		boolean isClassDeclaration = true;
		if (classEnd >= 6) {
			String classString = text.subSequence(classEnd - 6, classEnd - 1).toString();
			isClassDeclaration = classString.equals("class");
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
		int startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, false);
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
		startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, true);
		String firstWord = text.subSequence(startPosition, endPosition).toString();

		if (firstWord.equalsIgnoreCase("extends")) {
			showBaseClassList(projectModel, lastWord, offset, selectionLength, isClassDeclaration, explicit);
			return true;
		}

		if (firstWord.equalsIgnoreCase("implements")) {
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

		if (foundImplements) {
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
		CodeData[] classes = projectModel.getClasses("");
		ArrayList interfaces = new ArrayList(classes.length / 10);

		for (int i = 0; i < classes.length; i++) {
			if (PHPModifier.isInterface(((PHPClassData) classes[i]).getModifiers())) {
				interfaces.add(classes[i]);
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
			for (int i = 0; i < keywords.length; i++) {
				if (keywords[i].getName().equals("extends")) {
					extendsCodeData = keywords[i];
				}
				if (keywords[i].getName().equals("implements")) {
					implementsCodeData = keywords[i];
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
			for (int i = 0; i < keywords.length; i++) {
				if (keywords[i].getName().equals("implements")) {
					implementsCodeData = keywords[i];
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
			for (int i = 0; i < keywords.length; i++) {
				if (keywords[i].getName().equals("extends")) {
					extendCodeData = keywords[i];
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
		CodeData[] classes = projectModel.getClasses("");
		int numOfInterfaces = 0;
		for (int i = 0; i < classes.length; i++) {
			if (PHPModifier.isInterface(((PHPClassData) classes[i]).getModifiers())) {
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

	protected boolean isInCatchStatment(PHPProjectModel projectModel, TextSequence text, int offset, int selectionLength, boolean explicit) {
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
		int endPosition = PHPTextSequenceUtilities.readIdentifiarEndIndex(text, startPosition, false);
		String className = text.subSequence(startPosition, endPosition).toString();

		if (endPosition == text.length()) {
			showClassList(projectModel, className, offset, selectionLength, false, explicit);
		}
		return true;
	}

	protected void showClassList(PHPProjectModel projectModel, String startWith, int offset, int selectionLength, boolean isNewStatment, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}

		CodeData[] classes;
		if (isNewStatment) {
			completionProposalGroup = newStatmentCompletionProposalGroup;
			classes = getOnlyClasses(projectModel);
		} else {
			completionProposalGroup = phpCompletionProposalGroup;
			classes = projectModel.getClasses("");
		}

		completionProposalGroup.setData(offset, classes, startWith, selectionLength);
	}

	protected boolean isNewOrInstanceofStatment(PHPProjectModel projectModel, String keyword, String startWith, int offset, int selectionLength, boolean explicit, String type) {
		if (PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}

		if (keyword.equalsIgnoreCase("instanceof")) {
			showClassList(projectModel, startWith, offset, selectionLength, false, explicit);
			return true;
		}

		if (keyword.equalsIgnoreCase("new")) {
			showClassList(projectModel, startWith, offset, selectionLength, true, explicit);
			return true;
		}

		return false;
	}

	protected boolean isInArrayOption(PHPProjectModel projectModel, String fileName, boolean haveSpacesAtEnd, String firstWord, String lastWord, int startPosition, int offset, int selectionLength, TextSequence text) {
		boolean isArrayOption = false;
		if (startPosition > 0 && !lastWord.startsWith("$")) {
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
		startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, true);
		String variableName = text.subSequence(startPosition, endPosition).toString();

		if (variableName.startsWith("$")) {
			variableName = variableName.substring(1);
		}
		CodeData[] arrayResult = projectModel.getArrayVariables(fileName, variableName, lastWord, determineObjectTypeFromOtherFile);
		CodeData[] functions;
		CodeData[] constans;
		if (lastWord.length() == 0) {
			functions = projectModel.getFunctions();
			constans = (disableConstants) ? null : projectModel.getConstants();
		} else {
			functions = projectModel.getFunctions(lastWord);
			constans = (disableConstants) ? null : projectModel.getConstants(lastWord, constantCaseSensitive);
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

		protected CodeDataCompletionProposal createProposal(CodeData codeData) {
			String suffix = " ";
			int caretOffsetInSuffix = 1;
			boolean showTypeHints = false;

			if (codeData instanceof PHPKeywordData) {
				PHPKeywordData phpKeywordData = (PHPKeywordData) codeData;
				suffix = phpKeywordData.getSuffix();
				caretOffsetInSuffix = phpKeywordData.getSuffixOffset();
			} else if (codeData instanceof PHPFunctionData) {
				PHPFunctionData phpFunctionData = (PHPFunctionData) codeData;
				suffix = "()";
				showTypeHints = true;
				caretOffsetInSuffix = 2;
				boolean hasArgs = phpFunctionData.getParameters().length > 0;
				if (hasArgs) {
					caretOffsetInSuffix--; /* put the cursor between the parentheses */
				}
				String returnType = phpFunctionData.getReturnType();
				if (returnType != null && returnType.compareToIgnoreCase("void") == 0) {
					suffix += ";";
					if (!hasArgs) {
						caretOffsetInSuffix++;
					}
				}
			} else if (codeData instanceof PHPVariableData || codeData instanceof PHPConstantData || codeData instanceof PHPClassConstData) {
				suffix = "";
				caretOffsetInSuffix = 0;
			}
			return new CodeDataCompletionProposal(codeData, getOffset() - key.length(), key.length(), selectionLength, "", suffix, caretOffsetInSuffix, showTypeHints);
		}
	}

	private class RegularPHPCompletionProposalGroup extends PHPCompletionProposalGroup {
		protected CodeDataCompletionProposal createProposal(CodeData codeData) {
			if (!(codeData instanceof PHPClassData)) {
				return super.createProposal(codeData);
			}
			return new CodeDataCompletionProposal(codeData, getOffset() - key.length(), key.length(), selectionLength, "", "::", 2, false);
		}
	}

	private class ClassConstructorCompletionProposalGroup extends CompletionProposalGroup {
		protected CodeDataCompletionProposal createProposal(CodeData codeData) {
			return new CodeDataCompletionProposal(codeData, getOffset() - key.length(), key.length(), selectionLength, "", "()", 1, true);
		}
	}

	private class NewStatmentCompletionProposalGroup extends CompletionProposalGroup {
		protected CodeDataCompletionProposal createProposal(CodeData codeData) {
			PHPClassData classData = (PHPClassData) codeData;
			int suffixOffset = (classData.getConstructor().getParameters().length > 0) ? 1 : 2;

			return new CodeDataCompletionProposal(codeData, getOffset() - key.length(), key.length(), selectionLength, "", "()", suffixOffset, true);
		}
	}

	private class ArrayCompletionProposalGroup extends PHPCompletionProposalGroup {
		protected CodeDataCompletionProposal createProposal(CodeData codeData) {
			if (!(codeData instanceof PHPVariableData)) {
				return super.createProposal(codeData);
			}
			CodeDataCompletionProposal proposal = new ArrayCompletionProposal(codeData, getOffset() - key.length(), key.length(), selectionLength, "'", "'", 1);
			return proposal;
		}

		private class ArrayCompletionProposal extends CodeDataCompletionProposal {

			public ArrayCompletionProposal(CodeData codeData, int offset, int length, int selectionLength, String prefix, String suffix, int caretOffsetInSuffix) {
				super(codeData, offset, length, selectionLength, prefix, suffix, caretOffsetInSuffix, false);
			}

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
		protected CodeDataCompletionProposal createProposal(CodeData codeData) {
			if (codeData instanceof PHPClassVarData) {
				return new CodeDataCompletionProposal(codeData, getOffset() - key.length(), key.length(), selectionLength, "$", "", 0, false);
			}
			return super.createProposal(codeData);
		}
	}

	private class ClassStaticCallCompletionProposalGroup extends PHPCompletionProposalGroup {

		protected CodeDataCompletionProposal createProposal(CodeData codeData) {
			if (codeData instanceof PHPClassVarData) {
				return new CodeDataCompletionProposal(codeData, getOffset() - key.length(), key.length(), selectionLength, "$", "", 0, false);
			}
			return super.createProposal(codeData);
		}

		protected CodeDataCompletionProposal[] calcCompletionProposals() {

			if (key.length() == 0) {
				return super.calcCompletionProposals();
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
				result[i] = createProposal(tmp[i]);
			}
			return result;
		}
	}

}
