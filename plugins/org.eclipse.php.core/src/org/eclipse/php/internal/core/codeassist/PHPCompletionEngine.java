package org.eclipse.php.core.codeassist;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.codeassist.IAssistParser;
import org.eclipse.dltk.codeassist.ScriptCompletionEngine;
import org.eclipse.dltk.compiler.env.ISourceModule;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.internal.core.util.WeakHashSet;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.phpModel.PHPKeywords;
import org.eclipse.php.internal.core.phpModel.PHPKeywords.KeywordData;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
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
	protected static final String CLASS_FUNCTIONS_TRIGGER = PAAMAYIM_NEKUDOTAIM; //$NON-NLS-1$
	protected static final String OBJECT_FUNCTIONS_TRIGGER = "->"; //$NON-NLS-1$

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
		return pluginPreferences.getString(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP).trim().toCharArray();
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
		IStructuredModel structuredModel = null;
		boolean isUnmanaged = false;
		try {
			IFile file = (IFile) module.getModelElement().getResource();
			structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(file);
			if (structuredModel == null) {
				structuredModel = StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(file);
				isUnmanaged = true;
			}
			if (structuredModel instanceof DOMModelForPHP) {
				DOMModelForPHP domModelForPHP = (DOMModelForPHP) structuredModel;
				calcCompletionOption(domModelForPHP, position, module, true);
			}
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			if (structuredModel != null && !isUnmanaged) {
				structuredModel.releaseFromRead();
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
				getRegularCompletion(lastWord, offset, explicit, container, phpScriptRegion, internalPHPRegion, document);
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

		if (isInClassDeclaration(statementText, offset, explicit)) {
			// the current position is inside class declaration.
			return;
		}

		if (lastWord.length() > 0) {
			getRegularCompletion(lastWord, offset, explicit, container, phpScriptRegion, internalPHPRegion, document);
		}

		return;
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

		reportArrayVariables(variableName, offset, prefix);
		return true;
	}

	protected void getRegularCompletion(String prefix, int offset, boolean explicit, ITextRegionCollection sdRegion, ITextRegion tRegion, ContextRegion internalPhpRegion, IStructuredDocument document) {
		if (!explicit && prefix.length() == 0) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);

		boolean inClass = false;
		try {
			if (((SourceModule)sourceModule.getModelElement()).getElementAt(offset) instanceof IType) {
				inClass = true;
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}

		if (internalPhpRegion != null) {
			final String type = internalPhpRegion.getType();

			if (prefix.startsWith(DOLLAR) && !inClass) { //$NON-NLS-1$
				boolean autoactivateVariables = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_VARIABLES);
				if (!explicit && !autoactivateVariables)
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

		Collection<KeywordData> keywordsList = PHPKeywords.findByPrefix(((SourceModule) sourceModule).getScriptProject().getProject(), prefix);
		if (inClass) {
			keywordsList = filterClassKeywords(keywordsList);
		}
		for (KeywordData k : keywordsList) {
			reportKeyword(k.name, k.suffix);
		}

		boolean autoactivateKeywordsConstants = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS);
		if ((explicit || autoactivateKeywordsConstants) && !inClass) {
			IModelElement[] functions = PHPMixinModel.getInstance().getMethod(null, prefix + WILDCARD);
			for (IModelElement function : functions) {
				reportMethod((IMethod) function, RELEVANCE_METHODS);
			}

			if (pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST)) {
				int relevance = 4242;
				IModelElement[] constants = PHPMixinModel.getInstance().getConstant(prefix + WILDCARD, null);
				for (IModelElement constant : constants) {
					reportField((IField) constant, relevance--, false);
				}
			}
		}

		if (!inClass) {
			boolean showClasses = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION);
			if (showClasses) {
				boolean autoactivateClassNames = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES);
				if (explicit || autoactivateClassNames) {
					int relevance = 424242;
					IModelElement[] classes = PHPMixinModel.getInstance().getClass(prefix + WILDCARD);
					for (IModelElement type : classes) {
						reportType((IType) type, relevance--, PAAMAYIM_NEKUDOTAIM);
					}
				}
			}
		}
		return;
	}

	private static Collection<KeywordData> filterClassKeywords(Collection<KeywordData> keywords) {
		List<KeywordData> filteredKeywords = new LinkedList<KeywordData>();
		for (KeywordData k : keywords) {
			if (k.isClassKeyword) {
				filteredKeywords.add(k);
			}
		}
		return filteredKeywords;
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

		boolean determineObjectTypeFromOtherFile = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES);
		IType[] types = CodeAssistUtils.getTypesFor(sourceModule, statementText, startFunctionPosition, offset, line, determineObjectTypeFromOtherFile);

		if (haveSpacesAtEnd && functionName.length() > 0) {
			// check if current position is between the end of a function call and open bracket.
			return CodeAssistUtils.isClassFunctionCall(sourceModule, types, functionName);
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

	protected void showClassCall(int offset, IType[] className, String prefix, boolean isInstanceOf, boolean addVariableDollar, boolean explicit) {
		if (className == null) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);

		boolean autoactivateKeywordsConstants = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS);
		boolean autoactivateVariables = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_VARIABLES);
		
		for (IType type : className) {
			if (!prefix.startsWith(DOLLAR) && (explicit || autoactivateKeywordsConstants)) {
				IMethod[] methods = CodeAssistUtils.getClassMethods(type, prefix, false);
				for (IModelElement method : methods) {
					reportMethod((IMethod) method, RELEVANCE_METHODS);
				}
			}
			if (explicit || autoactivateVariables) {
				IModelElement[] fields = CodeAssistUtils.getClassFields(type, prefix, false, true);
				int relevance = 424242;
				for (IModelElement element : fields) {
					IField field = (IField) element;
					reportField(field, relevance--, true);
				}

			}
		}
	}

	protected void showClassStaticCall(int offset, IType[] className, String prefix, boolean explicit) {
		this.setSourceRange(offset - prefix.length(), offset);
		
		boolean showNonStrictOptions = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS);
		boolean autoactivateKeywordsConstants = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS);

		if (!prefix.startsWith(DOLLAR) && (explicit || autoactivateKeywordsConstants)) {
			for (IType type : className) {
				IMethod[] classMethods = CodeAssistUtils.getClassMethods(type, prefix, false);

				for (IMethod method : classMethods) {
					try {
						if (!isPHP5 || showNonStrictOptions || (method.getFlags() & Modifiers.AccStatic) != 0) {
							reportMethod(method, RELEVANCE_METHODS);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
			}
		}

		boolean autoactivateVariables = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_VARIABLES);
		if (explicit || autoactivateVariables) {
			int relevance = 4242;
			for (IType type : className) {
				IField[] classFields = CodeAssistUtils.getClassFields(type, prefix, false, true);
				for (IField field : classFields) {
					try {
						int flags = field.getFlags();
						if ((flags & Modifiers.AccConstant) != 0 || !isPHP5 || showNonStrictOptions || (flags & Modifiers.AccStatic) != 0) {
							reportField(field, relevance--, false);
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
			}
		}
	}

	protected void showParentCall(ISourceModule sourceModule, int offset, String className, String prefix, boolean explicit) {
		boolean autoactivateKeywordsConstants = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS);
		if (explicit || autoactivateKeywordsConstants) {
			IModelElement[] methods = PHPMixinModel.getInstance().getMethod(className, prefix + WILDCARD);
			for (IModelElement method : methods) {
				IMethod methodElement = (IMethod) method;
				try {
					int flags = methodElement.getFlags();
					if ((flags & Modifiers.AccPrivate) == 0) {
						reportMethod(methodElement, RELEVANCE_METHODS);
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
		}
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

					IModelElement[] classes = PHPMixinModel.getInstance().getClass(prefix + WILDCARD);

					this.setSourceRange(offset - prefix.length(), offset);

					for (IModelElement type : classes) {
						reportType((IType) type, RELEVANCE_FREE_SPACE, " ");
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

		if (!explicit && functionNameStart.length() == 0) {
			return true;
		}

		this.setSourceRange(offset - functionNameStart.length(), offset);

		IMethod[] superClassMethods = CodeAssistUtils.getSuperClassMethods(classData, functionNameStart, false);
		for (IMethod superMethod : superClassMethods) {
			try {
				int flags = superMethod.getFlags();
				if ((flags & Modifiers.AccPrivate) == 0) {
					reportMethod(superMethod, RELEVANCE_METHODS);
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
				reportMethod(fakeMagicMethod, RELEVANCE_METHODS);
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

		if (firstWord.equalsIgnoreCase(EXTENDS)) { //$NON-NLS-1$
			showBaseClassList(lastWord, offset, isClassDeclaration, explicit);
			return true;
		}

		if (firstWord.equalsIgnoreCase(IMPLEMENTS)) { //$NON-NLS-1$
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
		boolean autoactivateForClassNames = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES);
		if (!explicit && !autoactivateForClassNames) {
			return;
		}
		IModelElement[] interfaces = CodeAssistUtils.getOnlyInterfaces(prefix, false);
		for (IModelElement i : interfaces) {
			reportType((IType)i, RELEVANCE_FREE_SPACE, EMPTY);
		}
	}

	protected void showExtendsImplementsList(String prefix, int offset, boolean explicit) {
		boolean autoactivateForClassNames = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES);
		if (!explicit && !autoactivateForClassNames) {
			return;
		}
		this.setSourceRange(offset - prefix.length(), offset);

		if (isPHP5) {
			if (EXTENDS.startsWith(prefix)) {
				reportKeyword(EXTENDS, EMPTY);
			}
			if (IMPLEMENTS.startsWith(prefix)) {
				reportKeyword(IMPLEMENTS, EMPTY);
			}
		} else {
			if (EXTENDS.startsWith(prefix)) {
				reportKeyword(EXTENDS, EMPTY);
			}
		}
	}

	protected void showImplementsList(String prefix, int offset, boolean explicit) {
		boolean autoactivateForClassNames = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES);
		if (!explicit && !autoactivateForClassNames) {
			return;
		}
		if (isPHP5) {
			this.setSourceRange(offset - prefix.length(), offset);
			
			if (IMPLEMENTS.startsWith(prefix)) {
				reportKeyword(IMPLEMENTS, EMPTY);
			}
		}
	}

	private void showExtendsList(String prefix, int offset, boolean explicit) {
		boolean autoactivateForClassNames = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES);
		if (!explicit && !autoactivateForClassNames) {
			return;
		}
		this.setSourceRange(offset - prefix.length(), offset);
		
		if (EXTENDS.startsWith(prefix)) {
			reportKeyword(EXTENDS, EMPTY);
		}
	}

	private void showBaseClassList(String prefix, int offset, boolean isClassDecleration, boolean explicit) {
		if (!isClassDecleration) {
			showInterfaceList(prefix, offset, explicit);
			return;
		}
		boolean autoactivateForClassNames = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES);
		if (!explicit && !autoactivateForClassNames) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);

		IType[] classes = CodeAssistUtils.getOnlyClasses(prefix, false);
		for (IType type : classes) {
			reportType(type, RELEVANCE_FREE_SPACE, EMPTY);
		}
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
		boolean autoactivateForClassNames = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES);
		if (!explicit && !autoactivateForClassNames) {
			return;
		}

		this.setSourceRange(offset - prefix.length(), offset);

		switch (state) {
			case NEW:
				IType[] types = CodeAssistUtils.getOnlyClasses(prefix, false);
				for (IType type : types) {
					reportType(type, RELEVANCE_FREE_SPACE, BRACKETS_SUFFIX);
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
				IModelElement[] typeElements = PHPMixinModel.getInstance().getClass(prefix + WILDCARD);
				for (IModelElement typeElement : typeElements) {
					reportType((IType) typeElement, RELEVANCE_FREE_SPACE, EMPTY);
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
	 * Adds the self function with the relevant data to the proposals array
	 * @param classData
	 */
	private void addSelfFunctionToProposals(IType type) {
		reportMethod(new FakeMethod((ModelElement) type, SELF), RELEVANCE_METHODS);
	}

	protected boolean isNewOrInstanceofStatement(String keyword, String prefix, int offset, boolean explicit, String type) {
		if (PHPPartitionTypes.isPHPQuotesState(type)) {
			return false;
		}

		if (keyword.equalsIgnoreCase(INSTANCEOF)) { //$NON-NLS-1$
			this.setSourceRange(offset - prefix.length(), offset);
			showClassList(prefix, offset, States.INSTANCEOF, explicit);
			return true;
		}

		if (keyword.equalsIgnoreCase(NEW)) { //$NON-NLS-1$
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

		reportArrayVariables(variableName, offset, lastWord);

		IModelElement[] functions = PHPMixinModel.getInstance().getFunction(lastWord + WILDCARD);
		for (IModelElement function : functions) {
			reportMethod((IMethod) function, RELEVANCE_METHODS);
		}

		boolean showConstants = pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST);
		if (showConstants) {
			IModelElement[] constants = PHPMixinModel.getInstance().getConstant(lastWord + WILDCARD, null);
			int relevance = 4242;
			for (IModelElement constant : constants) {
				IField field = (IField) constant;
				reportField(field, relevance--, false);
			}
		}
		return true;
	}

	private void reportMethod(IMethod method, int rel) {
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

	private void reportKeyword(String name, String suffix) {
		// accept result
		noProposal = false;
		if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {
			CompletionProposal proposal = createProposal(CompletionProposal.KEYWORD, actualCompletionPosition);

			proposal.setName(name.toCharArray());
			proposal.setCompletion((name + suffix).toCharArray());
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
