package org.eclipse.php.core.codeassist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
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
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchMatch;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.SearchRequestor;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.internal.core.util.WeakHashSet;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.phpModel.PHPKeywords;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferencer;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
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

	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String FUNCTION = "function"; //$NON-NLS-1$
	private static final String DESTRUCTOR = "__destruct"; //$NON-NLS-1$
	private static final String CONSTRUCTOR = "__construct"; //$NON-NLS-1$
	private static final String DOLLAR = "$"; //$NON-NLS-1$
	private static final String BRACKETS_SUFFIX = "()"; //$NON-NLS-1$
	private static final String EMPTY = ""; //$NON-NLS-1$
	private static final String WILDCARD = "*"; //$NON-NLS-1$
	private final static int RELEVANCE_FREE_SPACE = 10000000;
	private final static int RELEVANCE_KEYWORD = 1000000;
	private final static int RELEVANCE_METHODS = 100000;

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

	protected static final char[] phpDelimiters = new char[] { '?', ':', ';', '|', '^', '&', '<', '>', '+', '-', '.', '*', '/', '%', '!', '~', '[', ']', '(', ')', '{', '}', '@', '\n', '\t', ' ', ',', '$', '\'', '\"' };
	protected static final String CLASS_FUNCTIONS_TRIGGER = "::"; //$NON-NLS-1$
	protected static final String OBJECT_FUNCTIONS_TRIGGER = "->"; //$NON-NLS-1$

	private static final Pattern extendsPattern = Pattern.compile("\\Wextends\\W", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final Pattern implementsPattern = Pattern.compile("\\Wimplements", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final Pattern catchPattern = Pattern.compile("catch\\s[^{]*", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final Pattern globalPattern = Pattern.compile("\\$GLOBALS[\\s]*\\[[\\s]*[\\'\\\"][\\w]+[\\'\\\"][\\s]*\\]"); //$NON-NLS-1$

	private static String[] CLASS_KEYWORDS = { "abstract", "const", FUNCTION, "private", "protected", "public", "static", "var" }; // must be ordered!

	protected HashSet<String> completedNames = new HashSet<String>();
	protected WeakHashSet intresting = new WeakHashSet();
	protected boolean showVariablesFromOtherFiles;
	protected boolean groupCompletionOptions;
	protected boolean cutCommonPrefix;
	public boolean determineObjectTypeFromOtherFile;
	protected boolean disableConstants;
	protected boolean showClassNamesInGlobalList = true;
	protected boolean showNonStrictOptions;
	protected boolean constantCaseSensitive;
	protected boolean autoShowVariables;
	protected boolean autoShowFunctionsKeywordsConstants;
	protected boolean autoShowClassNames;
	protected char[] autoActivationTriggers;
	protected boolean isPHP5;
	protected ISourceModule sourceModule;

	enum States {
		CATCH, NEW, INSTANCEOF
	};

	public PHPCompletionEngine() {
	}

	public char[] getAutoactivationTriggers() {
		return autoActivationTriggers;
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
		IStructuredModel existingModelForRead = null;
		try {
			existingModelForRead = StructuredModelManager.getModelManager().getExistingModelForRead((IFile) module.getModelElement().getResource());
			if (existingModelForRead instanceof DOMModelForPHP) {
				DOMModelForPHP domModelForPHP = (DOMModelForPHP) existingModelForRead;
				calcCompletionOption(domModelForPHP, position, module, true);
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		} finally {
			if (existingModelForRead != null) {
				existingModelForRead.releaseFromRead();
			}
		}
	}

	protected void calcCompletionOption(DOMModelForPHP domModelForPHP, int offset, ISourceModule sourceModule, boolean explicit) throws BadLocationException {

		this.sourceModule = sourceModule;

		String phpVersion = PhpVersionProjectPropertyHandler.getVersion(((SourceModule) sourceModule).getScriptProject().getProject());
		isPHP5 = phpVersion.equals(PHPVersion.PHP5);

		// Find the structured document region:
		IStructuredDocument document = (IStructuredDocument) domModelForPHP.getDocument().getStructuredDocument();
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
		int lastOffset = offset;
		while (sdRegion == null && lastOffset >= 0) {
			lastOffset--;
			sdRegion = document.getRegionAtCharacterOffset(lastOffset);
		}

		ITextRegion textRegion = null;
		// 	in case we are at the end of the document, asking for completion
		if (offset == domModelForPHP.getStructuredDocument().getLength()) {
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

		if (phpScriptRegion == null) {
			return;
		}

		TextSequence statementText = PHPTextSequenceUtilities.getStatement(offset, sdRegion, true);

		String type = internalPHPRegion.getType();
		if (isInArrayOptionQuotes(type, offset, statementText)) {
			// the current position is inside quotes as a parameter for an array.
			return;
		}

		if (isPHPSingleQuote(container, phpScriptRegion, internalPHPRegion, document, offset) || isLineComment(container, phpScriptRegion, offset)) {
			// we dont have code completion inside single quotes.
			return;
		}

		if (isInFunctionDeclaration(statementText, offset, explicit)) {
			// the current position is inside function declaration.
			return;
		}

		if (isInCatchStatement(statementText, offset, explicit)) {
			// the current position is inside catch statement.
			return;
		}

		int totalLength = statementText.length();

		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, totalLength); // read whitespace
		int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, endPosition, true);
		String lastWord = statementText.subSequence(startPosition, endPosition).toString();
		boolean haveSpacesAtEnd = totalLength != endPosition;

		if (haveSpacesAtEnd && isNewOrInstanceofStatement(lastWord, EMPTY, offset, explicit, type)) { //$NON-NLS-1$
			// the current position is inside new or instanceof statement.
			return;
		}

		int line = document.getLineOfOffset(offset);
		if (isClassFunctionCompletion(statementText, offset, line, lastWord, startPosition, haveSpacesAtEnd, explicit)) {
			// the current position is in class function.
			return;
		}

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, startPosition); // read whitespace
		startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, endPosition, true);
		String firstWord = statementText.subSequence(startPosition, endPosition).toString();

		if (!haveSpacesAtEnd && isNewOrInstanceofStatement(firstWord, lastWord, offset, explicit, type)) {
			// the current position is inside new or instanceof statement.
			if (lastWord.startsWith(DOLLAR)) {
				if (haveSpacesAtEnd) {
					getRegularCompletion(EMPTY, offset, explicit, container, phpScriptRegion, internalPHPRegion, document); //$NON-NLS-1$
				} else {
					getRegularCompletion(lastWord, offset, explicit, container, phpScriptRegion, internalPHPRegion, document);
				}
			}
			return;
		}

		if (haveSpacesAtEnd && isFunctionCall(lastWord)) {
			// the current position is between the end of a function call and open bracket.
			return;
		}

		if (isInArrayOption(haveSpacesAtEnd, firstWord, lastWord, startPosition, offset, statementText, type)) {
			// the current position is after '[' sign show special completion.
			return;
		}

		if (isInClassDeclaration(statementText, offset, explicit)) {
			// the current position is inside class declaration.
			return;
		}

		if (haveSpacesAtEnd) {
			getRegularCompletion(EMPTY, offset, explicit, container, phpScriptRegion, internalPHPRegion, document); //$NON-NLS-1$
		} else {
			getRegularCompletion(lastWord, offset, explicit, container, phpScriptRegion, internalPHPRegion, document);
		}

		return;
	}

	protected static boolean isFunctionCall(String functionName) {
		IModelElement[] functions = PHPMixinModel.getInstance().getFunction(functionName);
		return functions.length > 0;
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

	protected void reportArrayVariables(String arrayName, int offset, String prefix, boolean showObjectsFromOtherFiles) {
		this.setSourceRange(offset - prefix.length(), offset);

		int relevance = 424242;
		if (arrayName.equals("$_SERVER") || arrayName.equals("$HTTP_SERVER_VARS")) { //$NON-NLS-1$ //$NON-NLS-2$
			reportVariables(serverVaraibles, prefix, relevance, true);
			return;
		}
		if (arrayName.equals("$_SESSION") || arrayName.equals("$HTTP_SESSION_VARS")) { //$NON-NLS-1$ //$NON-NLS-2$
			reportVariables(sessionVariables, prefix, relevance, true);
			return;
		}
		if (arrayName.equals("$GLOBALS")) { //$NON-NLS-1$
			IModelElement[] elements = PHPMixinModel.getInstance().getVariable(prefix + WILDCARD, null, null);
			for (IModelElement element : elements) {
				IField field = (IField) element;
				reportField(field, relevance--, true);
			}

			reportVariables(phpVariables, prefix, relevance, true);
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

		//		if (variableName.startsWith("$")) { //$NON-NLS-1$
		//			variableName = variableName.substring(1);
		//		}

		reportArrayVariables(variableName, offset, prefix, determineObjectTypeFromOtherFile);
		return true;
	}

	protected void getRegularCompletion(String prefix, int offset, boolean explicit, ITextRegionCollection sdRegion, ITextRegion tRegion, ContextRegion internalPhpRegion, IStructuredDocument document) {
		if (!explicit && prefix.length() == 0) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);

		boolean inClass = false;

		if (getContainerClassData(offset) != null) {
			inClass = true;
		}

		if (internalPhpRegion != null) {
			final String type = internalPhpRegion.getType();

			if (prefix.startsWith(DOLLAR) && !inClass) { //$NON-NLS-1$
				if (!explicit && !autoShowVariables)
					return;
				try {
					//if we're right next to a letter, in an implicit scenario, we don't want it to complete the variables name.
					if (!explicit && prefix.equals(DOLLAR) && document.getLength() != offset && Character.isLetter(document.getChar(offset))) { //$NON-NLS-1$
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

				int relevance = 424242;
				reportVariables(phpVariables, prefix, relevance--, false);

				IModelElement[] variables = PHPMixinModel.getInstance().getVariable(prefix + WILDCARD, null, null);
				for (IModelElement var : variables) {
					reportField((IField) var, relevance--, false);
				}
				return;
			}

			if (PHPPartitionTypes.isPHPQuotesState(type) || type.equals(PHPRegionTypes.PHP_HEREDOC_TAG) && sdRegion.getStartOffset(tRegion) + tRegion.getLength() <= offset) {
				return;
			}
		}

		Collection<String> keywordsList = PHPKeywords.findNamesByPrefix(((SourceModule) sourceModule).getScriptProject().getProject(), prefix);
		String[] keywords = keywordsList.toArray(new String[keywordsList.size()]);
		if (inClass) {
			keywords = filterClassKeywords(keywords);
		}
		for (String keyword : keywords) {
			reportKeyword(keyword);
		}

		if ((explicit || autoShowFunctionsKeywordsConstants) && !inClass) {
			IModelElement[] functions = PHPMixinModel.getInstance().getMethod(null, prefix + WILDCARD);
			for (IModelElement function : functions) {
				reportMethod((IMethod) function, RELEVANCE_METHODS, BRACKETS_SUFFIX);
			}

			if (!disableConstants) {
				int relevance = 4242;
				IModelElement[] constants = PHPMixinModel.getInstance().getConstant(prefix + WILDCARD, null);
				for (IModelElement constant : constants) {
					reportField((IField) constant, relevance--, false);
				}
			}
		}

		if (!inClass) {
			if (showClassNamesInGlobalList) {
				if (explicit || autoShowClassNames) {
					int relevance = 424242;
					IModelElement[] classes = PHPMixinModel.getInstance().getClass(prefix + WILDCARD);
					for (IModelElement type : classes) {
						reportType((IType) type, relevance--, EMPTY);
					}
				}
			}
		}
		return;
	}

	private static String[] filterClassKeywords(String[] keywords) {
		List<String> filteredKeywords = new LinkedList<String>();
		for (int i = 0, j = 0; i < keywords.length && j < CLASS_KEYWORDS.length;) {
			int compared = keywords[i].compareTo(CLASS_KEYWORDS[j]);
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
		return filteredKeywords.toArray(new String[filteredKeywords.size()]);
	}

	private static boolean startsWithIgnoreCase(String word, String prefix) {
		return word.toLowerCase().startsWith(prefix.toLowerCase());
	}

	protected boolean isClassFunctionCompletion(TextSequence statementText, int offset, int line, String functionName, int startFunctionPosition, boolean haveSpacesAtEnd, boolean explicit) {
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

		IType[] types = getTypesFor(sourceModule, statementText, startFunctionPosition, offset, line);

		if (haveSpacesAtEnd && functionName.length() > 0) {
			// check if current position is between the end of a function call and open bracket.
			return isClassFunctionCall(sourceModule, types, functionName);
		}

		if (isClassTriger) {
			if (isParent) {
				if (types != null) { //$NON-NLS-1$
					showClassStaticCall(offset, types, functionName, explicit);
				}
			} else {
				showClassStaticCall(offset, types, functionName, explicit);
			}
		} else {
			String parent = statementText.toString().substring(0, statementText.toString().lastIndexOf(OBJECT_FUNCTIONS_TRIGGER)).trim();
			boolean isInstanceOf = !parent.equals("$this"); //$NON-NLS-1$
			//boolean addVariableDollar = parent.endsWith("()");
			boolean addVariableDollar = false;
			showClassCall(offset, types, functionName, isInstanceOf, addVariableDollar, explicit);
		}
		return true;
	}

	protected IType[] getTypesFor(ISourceModule sourceModule, TextSequence statementText, int endPosition, int offset, int line) {
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
			return innerGetClassName(statementText, propertyEndPosition, isClassTriger, offset, line);
		}

		int propertyStartPosition = PHPTextSequenceUtilities.readForwardSpaces(statementText, lastObjectOperator + 2);
		String propertyName = statementText.subSequence(propertyStartPosition, propertyEndPosition).toString();
		IType[] types = getTypesFor(sourceModule, statementText, propertyStartPosition, offset, line);

		int bracketIndex = propertyName.indexOf('(');

		if (bracketIndex == -1) {
			// meaning its a class variable and not a function
			return getVariableType(types, propertyName, offset, line);
		}

		String functionName = propertyName.substring(0, bracketIndex).trim();
		Set<IType> result = new HashSet<IType>();
		for (IType type : types) {
			IType[] returnTypes = getFunctionReturnType(type, functionName);
			result.addAll(Arrays.asList(returnTypes));
		}
		return result.toArray(new IType[result.size()]);
	}

	protected IType[] getVariableType(IType[] types, String propertyName, int offset, int line) {
		for (IType type : types) {
			IField[] fields = getClassProperties(type, propertyName);
			for (IField field : fields) {
				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(field.getSourceModule(), null);
				BasicContext context = new BasicContext(field.getSourceModule(), moduleDeclaration);
				ClassVariableDeclarationGoal goal = new ClassVariableDeclarationGoal(context, types, field.getElementName());
				PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
				IEvaluatedType evaluatedType = typeInferencer.evaluateType(goal);
				if (evaluatedType != null) {
					IModelElement[] modelElements = PHPMixinModel.getInstance().getClass(evaluatedType.getTypeName());
					return modelElementsToTypes(modelElements);
				}
			}
		}
		return null;
	}

	protected IType[] getVariableType(String variableName, int position, int line, boolean showObjectsFromOtherFiles) {
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration((SourceModule) sourceModule, null);
		IContext context = ASTUtils.findContext((SourceModule) sourceModule, moduleDeclaration, position);
		if (context != null) {
			VariableReference varReference = new VariableReference(position, position + variableName.length(), variableName);
			ExpressionTypeGoal goal = new ExpressionTypeGoal(context, varReference);
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			IEvaluatedType evaluatedType = typeInferencer.evaluateType(goal);
			if (evaluatedType != null) {
				IModelElement[] modelElements = PHPMixinModel.getInstance().getClass(evaluatedType.getTypeName());
				return modelElementsToTypes(modelElements);
			}
		}
		return null;
	}

	protected IType[] getFunctionReturnType(IType type, String functionName) {
		try {
			IMethod[] classMethod = PHPModelUtils.getClassMethod(type, functionName, null);
			if (classMethod.length > 0) {
				MethodElementReturnTypeGoal goal = new MethodElementReturnTypeGoal(classMethod[0]);
				PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
				IEvaluatedType evaluatedType = typeInferencer.evaluateType(goal);
				if (evaluatedType != null) {
					IModelElement[] modelElements = PHPMixinModel.getInstance().getClass(evaluatedType.getTypeName());
					return modelElementsToTypes(modelElements);
				}
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return null;
	}

	protected static IType[] modelElementsToTypes(IModelElement[] elements) {
		List<IType> types = new ArrayList<IType>(elements.length);
		for (IModelElement element : elements) {
			types.add((IType) element);
		}
		return types.toArray(new IType[types.size()]);
	}

	/**
	 * getting an instance and finding its type.
	 */
	protected IType[] innerGetClassName(TextSequence statementText, int propertyEndPosition, boolean isClassTriger, int offset, int line) {

		int classNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, propertyEndPosition, true);
		String className = statementText.subSequence(classNameStart, propertyEndPosition).toString();
		if (isClassTriger) {
			if (className.equals("self")) { //$NON-NLS-1$
				IType classData = getContainerClassData(offset - 6); //the offset before self::
				if (classData != null) {
					return new IType[] { classData };
				}
			} else if (className.equals("parent")) { //$NON-NLS-1$
				IType classData = getContainerClassData(offset - 8); //the offset before parent::
				if (classData != null) {
					return new IType[] { classData };
				}
			}

			if (className.length() > 0) {
				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration((SourceModule) sourceModule, null);
				BasicContext context = new BasicContext((SourceModule) sourceModule, moduleDeclaration);
				IEvaluatedType type = new PHPClassType(className);
				return modelElementsToTypes(PHPTypeInferenceUtils.getModelElements(type, context, !determineObjectTypeFromOtherFile));
			}
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
				className = DOLLAR + quotedVarName.substring(1, quotedVarName.length() - 1); //$NON-NLS-1$
			}
		}
		// if its object call calc the object type.
		if (className.length() > 0 && className.charAt(0) == '$') {
			int statementStart = offset - statementText.length();
			return getVariableType(className, statementStart, line, determineObjectTypeFromOtherFile);
		}
		// if its function call calc the return type.
		if (statementText.charAt(propertyEndPosition - 1) == ')') {
			int functionNameEnd = getFunctionNameEndOffset(statementText, propertyEndPosition - 1);
			int functionNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, functionNameEnd, false);

			String functionName = statementText.subSequence(functionNameStart, functionNameEnd).toString();
			IType classData = getContainerClassData(offset);
			if (classData != null) { //if its a clss function
				return getFunctionReturnType(classData, functionName);
			}
			// if its a non class function
			IModelElement[] functions = PHPMixinModel.getInstance().getFunction(functionName);
			for (IModelElement function : functions) {
				reportMethod((IMethod) function, RELEVANCE_METHODS, BRACKETS_SUFFIX);
			}
		}
		return null;
	}

	protected boolean isClassFunctionCall(ISourceModule sourceModule, IType[] className, String functionName) {
		for (IType type : className) {
			IMethod[] classMethod;
			try {
				classMethod = PHPModelUtils.getClassMethod(type, functionName, null);
				if (classMethod != null) {
					return true;
				}
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		return false;
	}

	protected void showClassCall(int offset, IType[] className, String prefix, boolean isInstanceOf, boolean addVariableDollar, boolean explicit) {
		if (className == null) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);

		for (IType type : className) {
			if (explicit || autoShowFunctionsKeywordsConstants) {
				IMethod[] methods = getClassMethods(type, prefix);
				for (IModelElement method : methods) {
					reportMethod((IMethod) method, RELEVANCE_METHODS, BRACKETS_SUFFIX);
				}
			}
			if (explicit || autoShowVariables) {
				IModelElement[] fields = getClassFields(type, prefix);
				int relevance = 424242;
				for (IModelElement element : fields) {
					IField field = (IField) element;
					reportField(field, relevance--, true);
				}

			}
		}
	}

	protected static IMethod[] getClassMethods(IType type, String prefix) {
		final Set<IMethod> methods = new HashSet<IMethod>();
		try {
			if (type.getSuperClasses() != null) {
				SearchEngine searchEngine = new SearchEngine();
				IDLTKSearchScope scope = SearchEngine.createHierarchyScope(type);
				SearchPattern pattern = SearchPattern.createPattern(prefix + WILDCARD, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH, PHPLanguageToolkit.getDefault());
	
				searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						methods.add((IMethod) match.getElement());
					}
				}, null);
			}

			IMethod[] typeMethods = type.getMethods();
			for (IMethod typeMethod : typeMethods) {
				if (startsWithIgnoreCase(typeMethod.getElementName(), prefix)) {
					methods.add(typeMethod);
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	protected static IField[] getClassProperties(IType type, String propertyName) {
		final Set<IField> fields = new HashSet<IField>();
		try {
			SearchEngine searchEngine = new SearchEngine();
			IDLTKSearchScope scope;
			SearchPattern pattern;

			if (type.getSuperClasses() != null) {
				// search in hierarchy
				scope = SearchEngine.createHierarchyScope(type);
				pattern = SearchPattern.createPattern(DOLLAR + propertyName, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
				searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						fields.add((IField) match.getElement());
					}
				}, null);
			}

			// search in class itself
			IField[] typeFields = type.getFields();
			for (IField typeField : typeFields) {
				String elementName = typeField.getElementName();
				if (elementName.equals(DOLLAR + propertyName)) {
					fields.add(typeField);
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return fields.toArray(new IField[fields.size()]);
	}

	protected static IField[] getClassFields(IType type, String prefix) {
		final Set<IField> fields = new HashSet<IField>();
		try {
			SearchEngine searchEngine = new SearchEngine();
			IDLTKSearchScope scope;
			SearchPattern pattern;

			if (type.getSuperClasses() != null) {
				scope = SearchEngine.createHierarchyScope(type);
				// search for constants in hierarchy
				pattern = SearchPattern.createPattern(prefix + WILDCARD, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH, PHPLanguageToolkit.getDefault());
				searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						fields.add((IField) match.getElement());
					}
				}, null);
	
				// search for variables in hierarchy
				pattern = SearchPattern.createPattern(DOLLAR + prefix + WILDCARD, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH, PHPLanguageToolkit.getDefault());
				searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						fields.add((IField) match.getElement());
					}
				}, null);
			}

			// search for all fields in the class itself
			IField[] typeFields = type.getFields();
			for (IField typeField : typeFields) {
				String elementName = typeField.getElementName();
				if (elementName.startsWith(prefix) || (elementName.startsWith(DOLLAR) && elementName.substring(1).startsWith(prefix))) {
					fields.add(typeField);
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return fields.toArray(new IField[fields.size()]);
	}

	protected void showClassStaticCall(int offset, IType[] className, String prefix, boolean explicit) {
		this.setSourceRange(offset - prefix.length(), offset);

		if (explicit || autoShowFunctionsKeywordsConstants) {
			for (IType type : className) {
				IMethod[] classMethods = getClassMethods(type, prefix);

				for (IMethod method : classMethods) {
					try {
						if (!isPHP5 || showNonStrictOptions || (method.getFlags() & Modifiers.AccStatic) != 0) {
							reportMethod(method, RELEVANCE_METHODS, BRACKETS_SUFFIX);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
			}
		}

		if (explicit || autoShowVariables) {
			int relevance = 4242;
			for (IType type : className) {
				IField[] classFields = getClassFields(type, prefix);
				for (IField field : classFields) {
					reportField(field, relevance--, true);
				}
			}
		}
	}

	protected void showParentCall(ISourceModule sourceModule, int offset, String className, String prefix, boolean explicit) {
		if (explicit || autoShowFunctionsKeywordsConstants) {
			IModelElement[] methods = PHPMixinModel.getInstance().getMethod(className, prefix + WILDCARD);
			for (IModelElement method : methods) {
				IMethod methodElement = (IMethod) method;
				try {
					int flags = methodElement.getFlags();
					if ((flags & Modifiers.AccPrivate) == 0) {
						reportMethod(methodElement, RELEVANCE_METHODS, BRACKETS_SUFFIX);
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
		}
	}

	protected IType getContainerClassData(int offset) {
		IModelElement type = null;
		try {
			type = ((SourceModule) sourceModule).getElementAt(offset);
			while (type != null && !(type instanceof IType)) {
				type = type.getParent();
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return (IType) type;
	}

	protected IMethod getContainerMethodData(int offset) {
		try {
			IModelElement method = ((SourceModule) sourceModule).getElementAt(offset);
			if (method instanceof IMethod) {
				return (IMethod) method;
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return null;
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

	protected boolean isInFunctionDeclaration(TextSequence text, int offset, boolean explicit) {
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
					IModelElement[] classes = PHPMixinModel.getInstance().getClass(WILDCARD);

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

					for (IModelElement type : classes) {
						reportType((IType) type, RELEVANCE_FREE_SPACE, BRACKETS_SUFFIX);
					}
				}
				return true;
			}
		}

		IType classData = getContainerClassData(text.getOriginalOffset(functionStart));
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

		if (!explicit && functionNameStart.length() == 0) {
			return true;
		}

		this.setSourceRange(offset - functionNameStart.length(), offset);

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
			if (startsWithIgnoreCase(function, functionNameStart)) {
				FakeMethod fakeMagicMethod = new FakeMethod((ModelElement) classData, function);
				reportMethod(fakeMagicMethod, RELEVANCE_METHODS, BRACKETS_SUFFIX);
			}
		}

		return true;
	}

	protected boolean isInClassDeclaration(TextSequence text, int offset, boolean explicit) {
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
			if (explicit || lastWord.length() > 0) {
				if (isClassDeclaration) {
					showExtendsImplementsList(lastWord, offset, explicit);
				} else {
					showExtendsList(lastWord, offset, explicit);
				}
			}
			return true;
		}

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, startPosition);
		startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(text, endPosition, true);
		String firstWord = text.subSequence(startPosition, endPosition).toString();

		if (firstWord.equalsIgnoreCase("extends")) { //$NON-NLS-1$
			showBaseClassList(lastWord, offset, isClassDeclaration, explicit);
			return true;
		}

		if (firstWord.equalsIgnoreCase("implements")) { //$NON-NLS-1$
			showInterfaceList(lastWord, offset, explicit);
			return true;
		}

		if (foundExtends && foundImplements) {
			if (explicit || lastWord.length() > 0) {
				if (extendsMatcher.start() < implementsMatcher.start()) {
					showInterfaceList(lastWord, offset, explicit);
				} else {
					showBaseClassList(lastWord, offset, isClassDeclaration, explicit);
				}
			}
			return true;
		}

		if (foundImplements || !isClassDeclaration) {
			if (explicit) {
				showInterfaceList(lastWord, offset, explicit);
			}
			return true;
		}
		if ((explicit || lastWord.length() > 0) && isClassDeclaration) {
			showImplementsList(lastWord, offset, explicit);
		}
		return true;
	}

	protected void showInterfaceList(String prefix, int offset, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}
		try {
			IModelElement[] classes = PHPMixinModel.getInstance().getClass(prefix + WILDCARD);
			for (IModelElement c : classes) {
				IType type = (IType) c;
				if ((type.getFlags() & Modifiers.AccInterface) != 0) {
					reportType(type, RELEVANCE_FREE_SPACE, EMPTY);
				}
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}
	}

	protected void showExtendsImplementsList(String prefix, int offset, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}
		this.setSourceRange(offset - prefix.length(), offset);

		if (isPHP5) {
			reportKeyword("extends");
			reportKeyword("implements");
		} else {
			reportKeyword("extends");
		}
	}

	protected void showImplementsList(String prefix, int offset, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}
		if (isPHP5) {
			this.setSourceRange(offset - prefix.length(), offset);
			reportKeyword("implements");
		}
	}

	private void showExtendsList(String prefix, int offset, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}
		this.setSourceRange(offset - prefix.length(), offset);
		reportKeyword("extends");
	}

	private void showBaseClassList(String prefix, int offset, boolean isClassDecleration, boolean explicit) {
		if (!isClassDecleration) {
			showInterfaceList(prefix, offset, explicit);
			return;
		}
		if (!explicit && !autoShowClassNames) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);

		IType[] classes = getOnlyClasses(prefix);
		for (IType type : classes) {
			reportType(type, RELEVANCE_FREE_SPACE, EMPTY);
		}
	}

	private static IType[] getOnlyClasses(String prefix) {
		IModelElement[] classes = PHPMixinModel.getInstance().getClass(prefix + WILDCARD);
		List<IType> onlyClasses = new LinkedList<IType>();
		for (IModelElement c : classes) {
			IType type = (IType) c;
			try {
				if ((type.getFlags() & Modifiers.AccInterface) == 0) {
					onlyClasses.add(type);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
		return onlyClasses.toArray(new IType[onlyClasses.size()]);
	}

	protected boolean isInCatchStatement(TextSequence text, int offset, boolean explicit) {
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
			showClassList(className, offset, States.CATCH, explicit);
		}
		return true;
	}

	protected void showClassList(String prefix, int offset, States state, boolean explicit) {
		if (!explicit && !autoShowClassNames) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);

		// get the class data for "self". In case of null, the self function will not be added
		IType selfClassData = getSelfClassData(offset);

		switch (state) {
			case NEW:
				IType[] types = getOnlyClasses(prefix);
				for (IType type : types) {
					reportType(type, RELEVANCE_FREE_SPACE, BRACKETS_SUFFIX);
				}
				if (selfClassData != null) {
					addSelfFunctionToProposals(selfClassData);
				}
				break;
			case INSTANCEOF:
				IModelElement[] typeElements = PHPMixinModel.getInstance().getClass(prefix + WILDCARD);
				for (IModelElement typeElement : typeElements) {
					reportType((IType) typeElement, RELEVANCE_FREE_SPACE, EMPTY);
				}
				if (selfClassData != null) {
					addSelfFunctionToProposals(selfClassData);
				}
				break;
			case CATCH:
				typeElements = PHPMixinModel.getInstance().getClass(prefix + WILDCARD);
				for (IModelElement typeElement : typeElements) {
					reportType((IType) typeElement, RELEVANCE_FREE_SPACE, EMPTY);
				}
				break;
			default:
				break;
		}
	}

	/**
	 * The "self" function needs to be added only if we are in a class method
	 * and it is not an abstract class or an interface
	 * @param fileData
	 * @param offset 
	 * @return the self class data or null in case not found 
	 */
	private IType getSelfClassData(int offset) {

		IType type = getContainerClassData(offset);
		IMethod method = getContainerMethodData(offset);

		if (type != null && method != null) {
			int modifiers;
			try {
				modifiers = type.getFlags();
				if ((modifiers & Modifiers.AccAbstract) == 0 && (modifiers & Modifiers.AccInterface) == 0) {
					return type;
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}

		return null;
	}

	/**
	 * Adds the self function with the relevant data to the proposals array
	 * @param classData
	 */
	private void addSelfFunctionToProposals(IType type) {
		reportMethod(new FakeMethod((ModelElement) type, "self"), RELEVANCE_METHODS, BRACKETS_SUFFIX);
	}

	protected boolean isNewOrInstanceofStatement(String keyword, String prefix, int offset, boolean explicit, String type) {
		if (PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}

		if (keyword.equalsIgnoreCase("instanceof")) { //$NON-NLS-1$
			this.setSourceRange(offset - prefix.length(), offset);
			showClassList(prefix, offset, States.INSTANCEOF, explicit);
			return true;
		}

		if (keyword.equalsIgnoreCase("new")) { //$NON-NLS-1$
			this.setSourceRange(offset - prefix.length(), offset);
			showClassList(prefix, offset, States.NEW, explicit);
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

		reportArrayVariables(variableName, offset, lastWord, determineObjectTypeFromOtherFile);

		IModelElement[] functions = PHPMixinModel.getInstance().getFunction(lastWord + WILDCARD);
		for (IModelElement function : functions) {
			reportMethod((IMethod) function, RELEVANCE_METHODS, BRACKETS_SUFFIX);
		}

		if (!disableConstants) {
			IModelElement[] constants = PHPMixinModel.getInstance().getConstant(lastWord + WILDCARD, null);
			int relevance = 4242;
			for (IModelElement constant : constants) {
				IField field = (IField) constant;
				reportField(field, relevance--, false);
			}
		}
		return true;
	}

	private void reportMethod(IMethod method, int rel, String suffix) {
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
		char[] compl = (elementName + suffix).toCharArray();

		int relevance = rel;

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
			proposal.setCompletion(compl);
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

	private void reportType(IType type, int rel, String suffix) {
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

		int relevance = rel;

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

	private void reportField(IField field, int rel, boolean removeDollar) {
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

		int relevance = rel;

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

	private void reportKeyword(String name) {
		// accept result
		noProposal = false;
		if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {
			CompletionProposal proposal = createProposal(CompletionProposal.KEYWORD, actualCompletionPosition);

			proposal.setName(name.toCharArray());
			proposal.setCompletion(name.toCharArray());
			// proposal.setFlags(Flags.AccDefault);
			proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
			proposal.setRelevance(RELEVANCE_KEYWORD);
			this.requestor.accept(proposal);
			if (DEBUG) {
				this.printDebug(proposal);
			}
		}

	}
}
