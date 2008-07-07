/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.codeassist;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.codeassist.IAssistParser;
import org.eclipse.dltk.codeassist.ScriptCompletionEngine;
import org.eclipse.dltk.compiler.env.ISourceModule;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.internal.core.util.WeakHashSet;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPModifiers;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.language.PHPVersion;
import org.eclipse.php.internal.core.phpModel.PHPKeywords;
import org.eclipse.php.internal.core.phpModel.PHPKeywords.KeywordData;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

public class PHPCompletionEngine extends ScriptCompletionEngine {

	private static final char TAG_SIGN = '@';
	private static final String NEW = "new"; //$NON-NLS-1$
	private static final String INSTANCEOF = "instanceof"; //$NON-NLS-1$
	private static final String SELF = "self"; //$NON-NLS-1$
	private static final String IMPLEMENTS = "implements"; //$NON-NLS-1$
	private static final String EXTENDS = "extends"; //$NON-NLS-1$
	private static final String PAAMAYIM_NEKUDOTAIM = "::"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String FUNCTION = "function"; //$NON-NLS-1$
	private static final String DESTRUCTOR = "__destruct"; //$NON-NLS-1$
	private static final String CONSTRUCTOR = "__construct"; //$NON-NLS-1$
	private static final String DOLLAR = "$"; //$NON-NLS-1$
	private static final String BRACKETS_SUFFIX = "()"; //$NON-NLS-1$
	private static final String WHITESPACE_SUFFIX = " "; //$NON-NLS-1$
	private static final String EMPTY = ""; //$NON-NLS-1$
	
	private final static int RELEVANCE_KEYWORD = 10000000;
	private final static int RELEVANCE_METHOD = 1000000;
	private final static int RELEVANCE_CLASS = 100000;
	private final static int RELEVANCE_VAR = 10000;
	private final static int RELEVANCE_CONST = 1000;

	protected final static String[] phpVariables = { "$_COOKIE", "$_ENV", "$_FILES", "$_GET", "$_POST", "$_REQUEST", "$_SERVER", "$_SESSION", "$GLOBALS", "$HTTP_COOKIE_VARS", "$HTTP_ENV_VARS", "$HTTP_GET_VARS", "$HTTP_POST_FILES", "$HTTP_POST_VARS", "$HTTP_SERVER_VARS", "$HTTP_SESSION_VARS", };

	protected final static String[] serverVaraibles = { "$DOCUMENT_ROOT", "$GATEWAY_INTERFACE", "$HTTP_ACCEPT", "$HTTP_ACCEPT_ENCODING", "$HTTP_ACCEPT_LANGUAGE", "$HTTP_CONNECTION", "$HTTP_HOST", "$HTTP_USER_AGENT", "$PATH", "$PATH_TRANSLATED", "$PHP_SELF", "$QUERY_STRING", "$REMOTE_ADDR",
		"$REMOTE_PORT", "$REQUEST_METHOD", "$REQUEST_URI", "$SCRIPT_FILENAME", "$SCRIPT_NAME", "$SERVER_ADDR", "$SERVER_ADMIN", "$SERVER_NAME", "$SERVER_PORT", "$SERVER_PROTOCOL", "$SERVER_SIGNATURE", "$SERVER_SOFTWARE", };

	protected final static String[] classVariables = { "$this" };

	protected final static String[] sessionVariables = { "SID" };

	protected final static String[] allVariables = new String[phpVariables.length + serverVaraibles.length];
	static {
		System.arraycopy(phpVariables, 0, allVariables, 0, phpVariables.length);
		System.arraycopy(serverVaraibles, 0, allVariables, phpVariables.length, serverVaraibles.length);
		Arrays.sort(allVariables);
	}

	protected static final String[] magicFunctions = { "__get", "__set", "__call", "__sleep", "__wakeup", };

	protected static final String[] magicFunctionsPhp5 = { "__isset", "__unset", "__toString", "__set_state", "__clone", "__autoload", };

	protected static final String[] phpDocTags = { "abstract", "access", "author", "category", "copyright", "deprecated", "example", "final", "filesource", "global", "ignore", "internal", "license", "link", "method", "name", "package", "param", "property", "return", "see", "since", "static",
		"staticvar", "subpackage", "todo", "tutorial", "uses", "var", "version" };

	protected static final char[] phpDelimiters = new char[] { '?', ':', ';', '|', '^', '&', '<', '>', '+', '-', '.', '*', '/', '%', '!', '~', '[', ']', '(', ')', '{', '}', '@', '\n', '\t', ' ', ',', '$', '\'', '\"' };
	protected static final String CLASS_FUNCTIONS_TRIGGER = PAAMAYIM_NEKUDOTAIM; //$NON-NLS-1$
	protected static final String OBJECT_FUNCTIONS_TRIGGER = "->"; //$NON-NLS-1$
	protected static final char[] DEFAULT_AUTOACTIVATION_TRIGGERS = { '$', ':', '>' };

	private static final Pattern extendsPattern = Pattern.compile("\\Wextends\\W", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final Pattern implementsPattern = Pattern.compile("\\Wimplements", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final Pattern catchPattern = Pattern.compile("catch\\s[^{]*", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$

	protected HashSet<String> completedNames = new HashSet<String>();
	protected WeakHashSet intresting = new WeakHashSet();
	protected boolean isPHP5;
	protected ISourceModule sourceModule;
	private Preferences pluginPreferences;

	enum States {
		CATCH, NEW, INSTANCEOF
	};

	public PHPCompletionEngine() {
		pluginPreferences = PHPCorePlugin.getDefault().getPluginPreferences();
	}

	public char[] getAutoactivationTriggers() {
		if (pluginPreferences.contains(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP)) {
			return pluginPreferences.getString(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP).trim().toCharArray();
		}
		return DEFAULT_AUTOACTIVATION_TRIGGERS;
	}

	protected int getEndOfEmptyToken() {
		return 0;
	}

	protected String processFieldName(IField field, String token) {
		return null;
	}

	protected String processMethodName(IMethod method, String token) {
		return null;
	}

	protected String processTypeName(IType method, String token) {
		return null;
	}

	public IAssistParser getParser() {
		return null;
	}

	public void complete(ISourceModule module, int position, int i) {
		
		IStructuredDocument document = null;
		
		if (requestor instanceof IAdaptable) {
			IDocument d = (IDocument) ((IAdaptable)requestor).getAdapter(IDocument.class);
			if (d instanceof IStructuredDocument) {
				document = (IStructuredDocument) d;
			}
		}
		if (document == null) {
			IStructuredModel structuredModel = null;
			try {
				IFile file = (IFile) module.getModelElement().getResource();
				if (file != null && file.exists()) {
					structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(file);
					if (structuredModel != null) {
						document = structuredModel.getStructuredDocument();
					} else {
						try {
							document = StructuredModelManager.getModelManager().createStructuredDocumentFor(file);
						} catch (Exception e) {
						}
					}
				}
			} finally {
				if (structuredModel != null) {
					structuredModel.releaseFromRead();
				}
			}
		}
		
		if (document != null) {
			try {
				calcCompletionOption(document, position, module);
			} catch (BadLocationException e) {
				Logger.logException(e);
	
			}
		}
	}

	protected void calcCompletionOption(IStructuredDocument document, int offset, ISourceModule sourceModule) throws BadLocationException {

		this.sourceModule = sourceModule;

		String phpVersion = PhpVersionProjectPropertyHandler.getVersion(((SourceModule) sourceModule).getScriptProject().getProject());
		isPHP5 = phpVersion.equals(PHPVersion.PHP5);

		// Find the structured document region:
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
		int lastOffset = offset;
		while (sdRegion == null && lastOffset >= 0) {
			lastOffset--;
			sdRegion = document.getRegionAtCharacterOffset(lastOffset);
		}
		
		if (sdRegion == null) {
			return;
		}

		ITextRegion textRegion = null;
		// 	in case we are at the end of the document, asking for completion
		if (offset == document.getLength()) {
			textRegion = sdRegion.getLastRegion();
		} else {
			textRegion = sdRegion.getRegionAtCharacterOffset(offset);
		}

		if (textRegion == null) {
			return;
		}

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
				if (preTextRegion.getType() == EMPTY) { //$NON-NLS-1$
					// FIXME needs to be fixed. The problem is what to do if the cursor is exatly between problematic regions, e.g. single line comment and quoted string??
				}
			}
			startOffset = sdRegion.getStartOffset(textRegion);
		}

		boolean inPHPDoc = false;
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
			} else if (partitionType == PHPPartitionTypes.PHP_DOC) {
				inPHPDoc = true;
			} else {
				return;
			}
			internalPHPRegion = (ContextRegion) phpScriptRegion.getPhpToken(internalOffset);
		}

		if (phpScriptRegion == null) {
			return;
		}

		TextSequence statementText = PHPTextSequenceUtilities.getStatement(offset, sdRegion, true);
		String type = internalPHPRegion.getType();
		int totalLength = statementText.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, totalLength); // read whitespace
		int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, endPosition, true);
		String lastWord = statementText.subSequence(startPosition, endPosition).toString();
		boolean haveSpacesAtEnd = totalLength != endPosition;

		if (inPHPDoc) {
			if (isInPhpDocCompletion(statementText, offset, lastWord, haveSpacesAtEnd)) {
				// the current position is php doc block.
				return;
			}

			int tagStart = startPosition - 1;
			while (tagStart > 0 && statementText.charAt(tagStart) != TAG_SIGN) {
				--tagStart;
			}
			if (statementText.charAt(tagStart) == TAG_SIGN) {
				++tagStart;
				int tagEnd = PHPTextSequenceUtilities.readIdentifierEndIndex(statementText, tagStart, false);
				String tagName = statementText.subSequence(tagStart, tagEnd).toString();

				if (isVariableCompletion(offset, tagName, lastWord, haveSpacesAtEnd)) {
					// the current position is a variable completion after @param PHP-doc tag
					return;
				}
				if (tagStart == startPosition && isReturnTypeCompletion(offset, tagName, haveSpacesAtEnd)) {
					// the current position is a class completion after @return PHP-doc tag
					return;
				}
			}

		} else { // not inside of PHPDoc

			if (isInArrayOptionQuotes(type, offset, statementText)) {
				// the current position is inside quotes as a parameter for an array.
				return;
			}

			if (isPHPSingleQuote(container, phpScriptRegion, internalPHPRegion, document, offset) || isLineComment(container, phpScriptRegion, offset)) {
				// we dont have code completion inside single quotes.
				return;
			}

			if (isInFunctionDeclaration(statementText, offset)) {
				// the current position is inside function declaration.
				return;
			}

			if (isInCatchStatement(statementText, offset)) {
				// the current position is inside catch statement.
				return;
			}

			if (haveSpacesAtEnd && isNewOrInstanceofStatement(lastWord, EMPTY, offset, type)) { //$NON-NLS-1$
				// the current position is inside new or instanceof statement.
				return;
			}

			int line = document.getLineOfOffset(offset);
			if (isClassFunctionCompletion(statementText, offset, line, lastWord, startPosition, haveSpacesAtEnd)) {
				// the current position is in class function.
				return;
			}

			endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, startPosition); // read whitespace
			startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, endPosition, true);
			String firstWord = statementText.subSequence(startPosition, endPosition).toString();

			if (!haveSpacesAtEnd && isNewOrInstanceofStatement(firstWord, lastWord, offset, type)) {
				// the current position is inside new or instanceof statement.
				if (lastWord.startsWith(DOLLAR)) {
					getRegularCompletion(lastWord, offset, container, phpScriptRegion, internalPHPRegion, document);
				}
				return;
			}

			if (haveSpacesAtEnd && CodeAssistUtils.isFunctionCall(lastWord)) {
				// the current position is between the end of a function call and open bracket.
				return;
			}

			if (isInArrayOption(haveSpacesAtEnd, firstWord, lastWord, startPosition, offset, statementText, type)) {
				// the current position is after '[' sign show special completion.
				return;
			}

			if (isInClassDeclaration(statementText, offset)) {
				// the current position is inside class declaration.
				return;
			}

			getRegularCompletion(lastWord, offset, container, phpScriptRegion, internalPHPRegion, document);
		}
	}

	private boolean isInPhpDocCompletion(CharSequence statementText, int offset, String tagName, boolean hasSpacesAtEnd) {
		if (hasSpacesAtEnd) {
			return false;
		}
		int startPosition = statementText.length() - tagName.length();
		if (startPosition <= 0 || statementText.charAt(startPosition - 1) != TAG_SIGN) {
			return false; // this is not a tag
		}

		startPosition--;
		// verify that only whitespaces and '*' before the tag
		boolean founeX = false;
		for (; startPosition > 0; startPosition--) {
			if (!Character.isWhitespace(statementText.charAt(startPosition - 1))) {
				if (founeX || statementText.charAt(startPosition - 1) != '*') {
					break;
				}
				founeX = true;
			}
		}
		if (!founeX) {
			return false;
		}

		this.setSourceRange(offset - tagName.length(), offset);
		
		int relevanceKeyword = RELEVANCE_KEYWORD;
		for (String phpDocTag : phpDocTags) {
			if (CodeAssistUtils.startsWithIgnoreCase(phpDocTag, tagName)) {
				reportKeyword(phpDocTag, EMPTY, relevanceKeyword--);
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean isVariableCompletion(int offset, String tagName, String varName, boolean haveSpacesAtEnd) {
		if (haveSpacesAtEnd) {
			return false;
		}
		if (!PHPDocTag.PARAM_NAME.equals(tagName)) {
			return false;
		}
		if (varName.startsWith(DOLLAR)) { //$NON-NLS-1$
			// find function arguments
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration((org.eclipse.dltk.core.ISourceModule) sourceModule.getModelElement(), null);
			
			Declaration declaration = ASTUtils.findDeclarationAfterPHPdoc(moduleDeclaration, offset);
			if (declaration instanceof MethodDeclaration) {

				List<String> variables = new LinkedList<String>();
				List<Argument> arguments = ((MethodDeclaration)declaration).getArguments();
				for (Argument arg : arguments) {
					variables.add(arg.getName());
				}
				
				this.setSourceRange(offset - varName.length(), offset);
				
				int relevanceVar = RELEVANCE_VAR;
				String[] paramVars = variables.toArray(new String[variables.size()]);
				reportVariables(paramVars, varName, relevanceVar, false);
				
				return true;
			}
		}
		return false;
	}
	
	private boolean isReturnTypeCompletion(final int offset, String tagName, boolean haveSpacesAtEnd) {
		if (!haveSpacesAtEnd) {
			return false;
		}
		if (!PHPDocTag.RETURN_NAME.equals(tagName)) {
			return false;
		}
		
		// find function return types
		org.eclipse.dltk.core.ISourceModule module = (org.eclipse.dltk.core.ISourceModule) sourceModule.getModelElement();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(module, null);
		
		Declaration declaration = ASTUtils.findDeclarationAfterPHPdoc(moduleDeclaration, offset);
		if (declaration instanceof MethodDeclaration) {
			IMethod method = (IMethod) PHPModelUtils.getModelElementByNode(module, moduleDeclaration, declaration);
			if (method != null) {
				IType[] returnTypes = CodeAssistUtils.getFunctionReturnType(method, true);
				if (returnTypes != null) {
					int relevanceClass = RELEVANCE_CLASS;
					for (IType type : returnTypes) {
						try {
							if ((type.getFlags() & IPHPModifiers.Internal) == 0) {
								reportType(type, relevanceClass--, EMPTY);
							}
						} catch (ModelException e) {
							Logger.logException(e);
						}
					}
				}
			}
		}
		
		return false;
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

	protected void reportVariables(String[] variables, String prefix, int relevance, boolean removeDollar) {
		for (String var : variables) {
			if (var.startsWith(prefix)) {
				reportField(new FakeField((ModelElement) sourceModule, var, 0, 0), relevance--, removeDollar);
			}
		}
	}

	protected void reportArrayVariables(String arrayName, int offset, String prefix) {
		this.setSourceRange(offset - prefix.length(), offset);

		int relevanceVar = RELEVANCE_VAR;
		
		if (arrayName.equals("$_SERVER") || arrayName.equals("$HTTP_SERVER_VARS")) { //$NON-NLS-1$ //$NON-NLS-2$
			reportVariables(serverVaraibles, prefix, relevanceVar, true);
			return;
		}
		if (arrayName.equals("$_SESSION") || arrayName.equals("$HTTP_SESSION_VARS")) { //$NON-NLS-1$ //$NON-NLS-2$
			reportVariables(sessionVariables, prefix, relevanceVar, true);
			return;
		}
		if (arrayName.equals("$GLOBALS")) { //$NON-NLS-1$
			if (prefix.length() == 0) {
				prefix = DOLLAR;
			}
			IModelElement[] elements = CodeAssistUtils.getWorkspaceFields(prefix, false);
			for (IModelElement element : elements) {
				IField field = (IField) element;
				reportField(field, relevanceVar--, true);
			}

			reportVariables(phpVariables, prefix, relevanceVar, true);
			return;
		}
	}

	protected boolean isInArrayOptionQuotes(String type, int offset, TextSequence text) {
		if (!PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}
		int length = text.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, length);
		int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(text, endPosition, false);
		if (endPosition != length && startPosition != endPosition) {
			return false;
		}

		String prefix = text.subSequence(startPosition, endPosition).toString();
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

		reportArrayVariables(variableName, offset, prefix);
		return true;
	}

	protected void getRegularCompletion(String prefix, int offset, ITextRegionCollection sdRegion, ITextRegion tRegion, ContextRegion internalPhpRegion, IStructuredDocument document) {
		if (prefix.length() == 0) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);

		boolean inClass = false;
		try {
			if (((SourceModule) sourceModule.getModelElement()).getElementAt(offset) instanceof IType) {
				inClass = true;
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}

		int relevanceKeyword = RELEVANCE_KEYWORD;
		int relevanceVar = RELEVANCE_VAR;
		int relevanceConst = RELEVANCE_CONST;
		int relevanceClass = RELEVANCE_CLASS;
		int relevanceMethod = RELEVANCE_METHOD;
		
		Collection<KeywordData> keywordsList = PHPKeywords.findByPrefix(((SourceModule) sourceModule).getScriptProject().getProject(), prefix);
		for (KeywordData k : keywordsList) {
			if (inClass == k.isClassKeyword) {
				reportKeyword(k.name, k.suffix, relevanceKeyword--);
			}
		}

		if (internalPhpRegion != null) {
			final String type = internalPhpRegion.getType();

			if (prefix.startsWith(DOLLAR) && !inClass) { //$NON-NLS-1$
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

				
				IModelElement[] variables = CodeAssistUtils.getWorkspaceFields(prefix, false);
				for (IModelElement var : variables) {
					IField field = (IField) var;
					try {
						if ((field.getFlags() & Modifiers.AccConstant) != 0) {
							reportField(field, relevanceConst--, false);
						} else {
							reportField(field, relevanceVar--, false);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
				
				IMethod containerMethodData = CodeAssistUtils.getContainerMethodData(sourceModule, offset);
				if (containerMethodData != null && containerMethodData.getDeclaringType() != null) {
					reportVariables(classVariables, prefix, relevanceVar--, false);
					relevanceVar -= classVariables.length;
				}

				reportVariables(phpVariables, prefix, relevanceVar--, false);
				return;
			}

			if (PHPPartitionTypes.isPHPQuotesState(type) || type.equals(PHPRegionTypes.PHP_HEREDOC_TAG) && sdRegion.getStartOffset(tRegion) + tRegion.getLength() <= offset) {
				return;
			}
		}

		if (!inClass) {
			IModelElement[] functions = CodeAssistUtils.getWorkspaceMethods(prefix, false);
			for (IModelElement function : functions) {
				try {
					if ((((IMethod)function).getFlags() & IPHPModifiers.Internal) == 0) {
						reportMethod((IMethod) function, relevanceMethod--);
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}

			if (showConstantAssist()) {
				IModelElement[] constants = CodeAssistUtils.getWorkspaceFields(prefix, false);
				for (IModelElement constant : constants) {
					try {
						if ((((IField) constant).getFlags() & Modifiers.AccConstant) != 0) {
							reportField((IField) constant, relevanceConst--, false);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
			}
		}

		if (!inClass) {
			if (showClassNamesInGlobalCompletion()) {
				IModelElement[] classes = CodeAssistUtils.getWorkspaceClasses(prefix, false);
				for (IModelElement type : classes) {
					try {
						if ((((IType)type).getFlags() & IPHPModifiers.Internal) == 0) {
							reportType((IType) type, relevanceClass--, PAAMAYIM_NEKUDOTAIM);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
			}
		}
		return;
	}

	protected boolean isClassFunctionCompletion(TextSequence statementText, int offset, int line, String functionName, int startFunctionPosition, boolean haveSpacesAtEnd) {
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

		IType[] types = CodeAssistUtils.getTypesFor(sourceModule, statementText, startFunctionPosition, offset, line, determineObjsFromOtherFiles());

		if (haveSpacesAtEnd && functionName.length() > 0) {
			// check if current position is between the end of a function call and open bracket.
			return CodeAssistUtils.isClassFunctionCall(sourceModule, types, functionName);
		}

		if (isClassTriger) {
			if (isParent) {
				if (types != null) { //$NON-NLS-1$
					showClassStaticCall(offset, types, functionName);
				}
			} else {
				showClassStaticCall(offset, types, functionName);
			}
		} else {
			String parent = statementText.toString().substring(0, statementText.toString().lastIndexOf(OBJECT_FUNCTIONS_TRIGGER)).trim();
			boolean isInstanceOf = !parent.equals("$this"); //$NON-NLS-1$
			//boolean addVariableDollar = parent.endsWith("()");
			boolean addVariableDollar = false;
			showClassCall(offset, types, functionName, isInstanceOf, addVariableDollar);
		}
		return true;
	}

	protected void showClassCall(int offset, IType[] className, String prefix, boolean isInstanceOf, boolean addVariableDollar) {
		if (className == null) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);
		
		int relevanceMethod = RELEVANCE_METHOD;

		for (IType type : className) {
			if (!prefix.startsWith(DOLLAR)) {
				IMethod[] methods = CodeAssistUtils.getClassMethods(type, prefix, false);
				for (IModelElement method : methods) {
					try {
						if ((((IMethod)method).getFlags() & IPHPModifiers.Internal) == 0) {
							reportMethod((IMethod) method, relevanceMethod--);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
			}

			IModelElement[] fields = CodeAssistUtils.getClassFields(type, prefix, false, true);
			int relevanceVar = RELEVANCE_VAR;
			int relevanceConst = RELEVANCE_CONST;
			for (IModelElement element : fields) {
				IField field = (IField) element;
				try {
					if ((field.getFlags() & Modifiers.AccConstant) != 0) {
						reportField(field, relevanceConst--, true);
					} else {
						reportField(field, relevanceVar--, true);
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
		}
	}

	protected void showClassStaticCall(int offset, IType[] className, String prefix) {
		this.setSourceRange(offset - prefix.length(), offset);

		boolean showNonStrictOptions = showNonStrictOptions();

		int relevanceMethod = RELEVANCE_METHOD;
		if (!prefix.startsWith(DOLLAR)) {
			for (IType type : className) {
				IMethod[] classMethods = CodeAssistUtils.getClassMethods(type, prefix, false);

				for (IMethod method : classMethods) {
					try {
						if ((!isPHP5 || showNonStrictOptions || (method.getFlags() & Modifiers.AccStatic) != 0)
								&& (method.getFlags() & IPHPModifiers.Internal) == 0) {
							reportMethod(method, relevanceMethod--);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
			}
		}

		int relevanceConst = RELEVANCE_CONST;
		int relevanceVar = RELEVANCE_VAR;
		for (IType type : className) {
			IField[] classFields = CodeAssistUtils.getClassFields(type, prefix, false, true);
			for (IField field : classFields) {
				try {
					int flags = field.getFlags();
					boolean isConstant = (flags & Modifiers.AccConstant) != 0;
					if (isConstant || !isPHP5 || showNonStrictOptions || (flags & Modifiers.AccStatic) != 0) {
						if (isConstant) {
							reportField(field, relevanceConst--, false);
						} else {
							reportField(field, relevanceVar--, false);
						}
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
		}
	}

	protected boolean isInFunctionDeclaration(TextSequence text, int offset) {
		// are we inside function declaration statement
		int functionStart = PHPTextSequenceUtilities.isInFunctionDeclaration(text);
		if (functionStart == -1) {
			return false;
		}

		// are we inside parameters part in function declaration statement
		for (int i = text.length() - 1; i >= functionStart; i--) {
			if (text.charAt(i) == '(') {
				boolean showClassCompletion = true;
				int j = text.length() - 1;
				for (; j > i; j--) {
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
					String prefix = text.subTextSequence(j + 1, text.length()).toString();
					//remove leading white spaces
					int k = 0;
					for (; k < prefix.length(); k++) {
						if (!Character.isWhitespace(prefix.charAt(k))) {
							break;
						}
					}
					if (k != 0) {
						prefix = prefix.substring(k);
					}

					this.setSourceRange(offset - prefix.length(), offset);

					int relevanceClass = RELEVANCE_CLASS;
					IModelElement[] classes = CodeAssistUtils.getWorkspaceClasses(prefix, false);
					for (IModelElement type : classes) {
						try {
							if ((((IType)type).getFlags() & IPHPModifiers.Internal) == 0) {
								reportType((IType) type, relevanceClass--, WHITESPACE_SUFFIX);
							}
						} catch (ModelException e) {
							Logger.logException(e);
						}
					}
				}
				return true;
			}
		}

		IType classData = CodeAssistUtils.getContainerClassData(sourceModule, text.getOriginalOffset(functionStart));
		// We look for the container class data in function start offset.

		if (classData == null) {
			// We are not inside class function.
			return true;
		}

		int wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(text, text.length());
		int wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(text, wordEnd, false);
		String word = text.subSequence(wordStart, wordEnd).toString();

		String functionNameStart;
		if (word.equals(FUNCTION)) { //$NON-NLS-1$
			functionNameStart = EMPTY;
		} else if (wordEnd == text.length()) {
			functionNameStart = word;
		} else {
			return true;
		}

		this.setSourceRange(offset - functionNameStart.length(), offset);
		
		int relevanceMethod = RELEVANCE_METHOD;

		IMethod[] superClassMethods = CodeAssistUtils.getSuperClassMethods(classData, functionNameStart, false);
		for (IMethod superMethod : superClassMethods) {
			if (classData.getMethod(superMethod.getElementName()).exists()) {
				continue;
			}
			try {
				int flags = superMethod.getFlags();
				if ((flags & Modifiers.AccPrivate) == 0 && (flags & Modifiers.AccStatic) == 0 && (flags & IPHPModifiers.Internal) == 0) {
					reportMethod(superMethod, relevanceMethod--);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}

		List<String> functions = new LinkedList<String>();
		functions.addAll(Arrays.asList(magicFunctions));
		if (isPHP5) {
			functions.addAll(Arrays.asList(magicFunctionsPhp5));
		}
		functions.add(classData.getElementName());
		if (isPHP5) {
			functions.add(CONSTRUCTOR);
			functions.add(DESTRUCTOR);
		}

		for (String function : functions) {
			if (CodeAssistUtils.startsWithIgnoreCase(function, functionNameStart)) {
				FakeMethod fakeMagicMethod = new FakeMethod((ModelElement) classData, function);
				reportMethod(fakeMagicMethod, relevanceMethod--);
			}
		}
		return true;
	}

	protected boolean isInClassDeclaration(TextSequence text, int offset) {
		int classEnd = PHPTextSequenceUtilities.isInClassDeclaration(text);
		if (classEnd == -1) {
			return false;
		}
		boolean isClassDeclaration = true;
		if (classEnd >= 6) {
			String classString = text.subSequence(classEnd - 6, classEnd - 1).toString();
			isClassDeclaration = classString.equals(CLASS); //$NON-NLS-1$
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
			if (isClassDeclaration) {
				showExtendsImplementsList(lastWord, offset);
			} else {
				showExtendsList(lastWord, offset);
			}
			return true;
		}

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, startPosition);
		startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(text, endPosition, true);
		String firstWord = text.subSequence(startPosition, endPosition).toString();

		if (firstWord.equalsIgnoreCase(EXTENDS)) { //$NON-NLS-1$
			showBaseClassList(lastWord, offset, isClassDeclaration);
			return true;
		}

		if (firstWord.equalsIgnoreCase(IMPLEMENTS)) { //$NON-NLS-1$
			showInterfaceList(lastWord, offset);
			return true;
		}

		if (foundExtends && foundImplements) {
			if (extendsMatcher.start() < implementsMatcher.start()) {
				showInterfaceList(lastWord, offset);
			} else {
				showBaseClassList(lastWord, offset, isClassDeclaration);
			}
			return true;
		}

		if (foundImplements || !isClassDeclaration) {
			showInterfaceList(lastWord, offset);
			return true;
		}
		if (isClassDeclaration) {
			showImplementsList(lastWord, offset);
		}
		return true;
	}

	protected void showInterfaceList(String prefix, int offset) {
		IModelElement[] interfaces = CodeAssistUtils.getOnlyInterfaces(prefix, false);
		int relevanceClass = RELEVANCE_CLASS;
		for (IModelElement i : interfaces) {
			try {
				if ((((IType)i).getFlags() & IPHPModifiers.Internal) == 0) {
					reportType((IType) i, relevanceClass--, EMPTY);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
	}

	protected void showExtendsImplementsList(String prefix, int offset) {
		this.setSourceRange(offset - prefix.length(), offset);

		int relevanceKeyword = RELEVANCE_KEYWORD;
		
		if (isPHP5) {
			if (EXTENDS.startsWith(prefix)) {
				reportKeyword(EXTENDS, EMPTY, relevanceKeyword--);
			}
			if (IMPLEMENTS.startsWith(prefix)) {
				reportKeyword(IMPLEMENTS, EMPTY, relevanceKeyword--);
			}
		} else {
			if (EXTENDS.startsWith(prefix)) {
				reportKeyword(EXTENDS, EMPTY, relevanceKeyword--);
			}
		}
	}

	protected void showImplementsList(String prefix, int offset) {
		if (isPHP5) {
			this.setSourceRange(offset - prefix.length(), offset);

			int relevanceKeyword = RELEVANCE_KEYWORD;
			if (IMPLEMENTS.startsWith(prefix)) {
				reportKeyword(IMPLEMENTS, EMPTY, relevanceKeyword--);
			}
		}
	}

	private void showExtendsList(String prefix, int offset) {
		this.setSourceRange(offset - prefix.length(), offset);

		int relevanceKeyword = RELEVANCE_KEYWORD;
		if (EXTENDS.startsWith(prefix)) {
			reportKeyword(EXTENDS, EMPTY, relevanceKeyword--);
		}
	}

	private void showBaseClassList(String prefix, int offset, boolean isClassDecleration) {
		if (!isClassDecleration) {
			showInterfaceList(prefix, offset);
			return;
		}
		
		this.setSourceRange(offset - prefix.length(), offset);
		
		int relevanceClass = RELEVANCE_CLASS; 

		IType[] classes = CodeAssistUtils.getOnlyClasses(prefix, false);
		for (IType type : classes) {
			try {
				if ((type.getFlags() & IPHPModifiers.Internal) == 0) {
					reportType(type, relevanceClass--, EMPTY);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
	}

	protected boolean isInCatchStatement(TextSequence text, int offset) {
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
			showClassList(className, offset, States.CATCH);
		}
		return true;
	}

	protected void showClassList(String prefix, int offset, States state) {
		this.setSourceRange(offset - prefix.length(), offset);

		int relevanceClass = RELEVANCE_CLASS;
		
		switch (state) {
			case NEW:
				IType[] types = CodeAssistUtils.getOnlyClasses(prefix, false);
				for (IType type : types) {
					try {
						if ((type.getFlags() & IPHPModifiers.Internal) == 0) {
							reportType(type, relevanceClass--, BRACKETS_SUFFIX);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
				if (CodeAssistUtils.startsWithIgnoreCase(SELF, prefix)) {
					// get the class data for "self". In case of null, the self function will not be added
					IType selfClassData = CodeAssistUtils.getSelfClassData(sourceModule, offset);
					if (selfClassData != null) {
						addSelfFunctionToProposals(selfClassData);
					}
				}
				break;
			case INSTANCEOF:
				IModelElement[] typeElements = CodeAssistUtils.getWorkspaceClasses(prefix, false);
				for (IModelElement typeElement : typeElements) {
					try {
						if ((((IType)typeElement).getFlags() & IPHPModifiers.Internal) == 0) {
							reportType((IType) typeElement, relevanceClass--, EMPTY);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
				if (CodeAssistUtils.startsWithIgnoreCase(SELF, prefix)) {
					// get the class data for "self". In case of null, the self function will not be added
					IType selfClassData = CodeAssistUtils.getSelfClassData(sourceModule, offset);
					if (selfClassData != null) {
						addSelfFunctionToProposals(selfClassData);
					}
				}
				break;
			case CATCH:
				typeElements = CodeAssistUtils.getWorkspaceClasses(prefix, false);
				for (IModelElement typeElement : typeElements) {
					try {
						if ((((IType)typeElement).getFlags() & IPHPModifiers.Internal) == 0) {
							reportType((IType) typeElement, relevanceClass--, EMPTY);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Adds the self function with the relevant data to the proposals array
	 * @param classData
	 */
	private void addSelfFunctionToProposals(IType type) {
		reportMethod(new FakeMethod((ModelElement) type, SELF), RELEVANCE_METHOD);
	}

	protected boolean isNewOrInstanceofStatement(String keyword, String prefix, int offset, String type) {
		if (PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}

		if (keyword.equalsIgnoreCase(INSTANCEOF)) { //$NON-NLS-1$
			this.setSourceRange(offset - prefix.length(), offset);
			showClassList(prefix, offset, States.INSTANCEOF);
			return true;
		}

		if (keyword.equalsIgnoreCase(NEW)) { //$NON-NLS-1$
			this.setSourceRange(offset - prefix.length(), offset);
			showClassList(prefix, offset, States.NEW);
			return true;
		}

		return false;
	}

	protected boolean isInArrayOption(boolean haveSpacesAtEnd, String firstWord, String lastWord, int startPosition, int offset, TextSequence text, String type) {
		if (PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}
		boolean isArrayOption = false;
		if (startPosition > 0 && !lastWord.startsWith(DOLLAR)) { //$NON-NLS-1$
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

		reportArrayVariables(variableName, offset, lastWord);

		int relevanceMethod = RELEVANCE_METHOD;
		
		IModelElement[] functions = CodeAssistUtils.getWorkspaceMethods(lastWord, false);
		for (IModelElement function : functions) {
			try {
				if ((((IMethod)function).getFlags() & IPHPModifiers.Internal) == 0) {
					reportMethod((IMethod) function, relevanceMethod--);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}

		if (showConstantAssist()) {
			IModelElement[] constants = CodeAssistUtils.getWorkspaceFields(lastWord, false);
			int relevanceConst = RELEVANCE_CONST;
			for (IModelElement constant : constants) {
				IField field = (IField) constant;
				try {
					if ((field.getFlags() & Modifiers.AccConstant) != 0) {
						reportField(field, relevanceConst--, false);
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
		}
		return true;
	}

	private void reportMethod(IMethod method, int relevance) {
		this.intresting.add(method);
		String elementName = method.getElementName();
		if (completedNames.contains(elementName)) {
			return;
		}
		completedNames.add(elementName);
		if (elementName.indexOf('.') != -1) {
			elementName = elementName.substring(elementName.indexOf('.') + 1);
		}
		char[] name = elementName.toCharArray();

		// accept result
		noProposal = false;
		if (!requestor.isIgnored(CompletionProposal.METHOD_DECLARATION)) {
			CompletionProposal proposal = createProposal(CompletionProposal.METHOD_DECLARATION, actualCompletionPosition);

			String[] params = null;
			try {
				params = method.getParameters();
			} catch (ModelException e) {
				Logger.logException(e);
			}

			if (params != null && params.length > 0) {
				char[][] args = new char[params.length][];
				for (int i = 0; i < params.length; ++i) {
					args[i] = params[i].toCharArray();
				}
				proposal.setParameterNames(args);
			}

			proposal.setModelElement(method);
			proposal.setName(name);
			proposal.setCompletion((elementName + BRACKETS_SUFFIX).toCharArray());
			try {
				proposal.setIsConstructor(elementName.equals(CONSTRUCTOR) || method.isConstructor());
				proposal.setFlags(method.getFlags());
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
			proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
			proposal.setRelevance(relevance);
			this.requestor.accept(proposal);
			if (DEBUG) {
				this.printDebug(proposal);
			}
		}

	}

	private void reportType(IType type, int relevance, String suffix) {
		this.intresting.add(type);
		String elementName = type.getElementName();
		if (completedNames.contains(elementName)) {
			return;
		}
		completedNames.add(elementName);
		char[] name = elementName.toCharArray();
		if (name.length == 0) {
			return;
		}

		// accept result
		noProposal = false;
		if (!requestor.isIgnored(CompletionProposal.TYPE_REF)) {
			CompletionProposal proposal = createProposal(CompletionProposal.TYPE_REF, actualCompletionPosition);

			proposal.setModelElement(type);
			proposal.setName(name);
			proposal.setCompletion((elementName + suffix).toCharArray());
			try {
				proposal.setFlags(type.getFlags());
			} catch (ModelException e) {
			}
			proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
			proposal.setRelevance(relevance);
			this.requestor.accept(proposal);
			if (DEBUG) {
				this.printDebug(proposal);
			}
		}

	}

	private void reportField(IField field, int relevance, boolean removeDollar) {
		this.intresting.add(field);
		String elementName = field.getElementName();
		if (completedNames.contains(elementName)) {
			return;
		}
		completedNames.add(elementName);
		char[] name = elementName.toCharArray();
		if (name.length == 0) {
			return;
		}

		// accept result
		noProposal = false;
		if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {
			CompletionProposal proposal = createProposal(CompletionProposal.FIELD_REF, actualCompletionPosition);

			proposal.setModelElement(field);
			proposal.setName(name);

			String completion = elementName;
			if (removeDollar && completion.startsWith(DOLLAR)) {
				completion = completion.substring(1);
			}
			proposal.setCompletion(completion.toCharArray());
			try {
				proposal.setFlags(field.getFlags());
			} catch (ModelException e) {
			}
			proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
			proposal.setRelevance(relevance);
			this.requestor.accept(proposal);
			if (DEBUG) {
				this.printDebug(proposal);
			}
		}

	}

	private void reportKeyword(String name, String suffix, int relevance) {
		// accept result
		noProposal = false;
		if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {
			CompletionProposal proposal = createProposal(CompletionProposal.KEYWORD, actualCompletionPosition);
			proposal.setName(name.toCharArray());
			proposal.setCompletion((name + suffix).toCharArray());
			// proposal.setFlags(Flags.AccDefault);
			proposal.setRelevance(relevance);
			proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
			this.requestor.accept(proposal);
			if (DEBUG) {
				this.printDebug(proposal);
			}
		}

	}

	private boolean showClassNamesInGlobalCompletion() {
		if (pluginPreferences.contains(PHPCoreConstants.CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION)) {
			return pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION);
		}
		return true;
	}

	private boolean determineObjsFromOtherFiles() {
		if (pluginPreferences.contains(PHPCoreConstants.CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES)) {
			pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES);
		}
		return true;
	}

	private boolean showConstantAssist() {
		if (pluginPreferences.contains(PHPCoreConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST)) {
			pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST);
		}
		return true;
	}

	private boolean showNonStrictOptions() {
		if (pluginPreferences.contains(PHPCoreConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS)) {
			pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS);
		}
		return false;
	}
}
