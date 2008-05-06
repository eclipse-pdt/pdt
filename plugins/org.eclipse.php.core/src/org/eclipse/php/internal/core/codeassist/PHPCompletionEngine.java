package org.eclipse.php.core.codeassist;

import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.codeassist.IAssistParser;
import org.eclipse.dltk.codeassist.ScriptCompletionEngine;
import org.eclipse.dltk.compiler.env.ISourceModule;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchMatch;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.SearchRequestor;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.util.WeakHashSet;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.DLTKTypeInferenceEngine;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.mixin.PHPMixinElementInfo;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.phpModel.PHPKeywords;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;

public class PHPCompletionEngine extends ScriptCompletionEngine {

	private final static int RELEVANCE_FREE_SPACE = 10000000;
	private final static int RELEVANCE_KEYWORD = 1000000;
	private final static int RELEVANCE_METHODS = 100000;

	private final static String[] globalVars = { "$_GET", "$_POST", "$_COOKIE", "$_SESSION", "$_SERVER", "$_ENV", "$_REQUEST", "$_FILES", "$GLOBALS", "$HTTP_GET_VARS", "$HTTP_POST_VARS", "$HTTP_COOKIE_VARS", "$HTTP_SESSION_VARS", "$HTTP_SERVER_VARS", "$HTTP_ENV_VARS", "$HTTP_POST_FILES",
		"$DOCUMENT_ROOT", "$HTTP_ACCEPT", "$HTTP_ACCEPT_ENCODING", "$HTTP_ACCEPT_LANGUAGE", "$HTTP_CONNECTION", "$HTTP_HOST", "$HTTP_USER_AGENT", "$REMOTE_ADDR", "$SCRIPT_FILENAME", "$SERVER_NAME", "$GATEWAY_INTERFACE", "$REQUEST_METHOD", "$QUERY_STRING", "$REQUEST_URI", "$SCRIPT_NAME",
		"$PHP_SELF", "$PATH", "$REMOTE_PORT", "$SERVER_ADDR", "$SERVER_ADMIN", "$SERVER_PORT", "$SERVER_SIGNATURE", "$SERVER_SOFTWARE", "$SERVER_PROTOCOL", "$PATH_TRANSLATED", };

	private DLTKTypeInferenceEngine inferencer;
	private ISourceParser parser = null;
	private MixinModel model;
	private HashSet<String> completedNames = new HashSet<String>();
	private WeakHashSet intresting = new WeakHashSet();
	private IContext context;

	private final Comparator<IModelElement> modelElementComparator = new Comparator<IModelElement>() {

		Collator collator = Collator.getInstance(Locale.ENGLISH);

		public int compare(IModelElement me1, IModelElement me2) {
			int r = -collator.compare(me1.getElementName(), me2.getElementName());
			if (r == 0) {
				// prefer elements from current module
				boolean cur1 = me1.getAncestor(IModelElement.SOURCE_MODULE).equals(currentModule);
				boolean cur2 = me2.getAncestor(IModelElement.SOURCE_MODULE).equals(currentModule);
				if (cur1 == cur2) {
					return 0;
				} else {
					return cur1 ? -1 : 1;
				}
			}
			return r;
		}
	};

	private ISourceModule currentModule;;

	public PHPCompletionEngine() {
		this.inferencer = new DLTKTypeInferenceEngine();
		this.model = PHPMixinModel.getRawInstance();
		this.parser = DLTKLanguageManager.getSourceParser(PHPNature.ID);
	}

	protected int getEndOfEmptyToken() {
		return 0;
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

	private boolean afterColons(String content, int position) {
		if (position < 2) {
			return false;
		}
		if (content.charAt(position - 1) == ':' && content.charAt(position - 2) == ':') {
			return true;
		}
		return false;
	}
	
	private boolean afterObjectOperator(String content, int position) {
		if (position < 2) {
			return false;
		}
		if (content.charAt(position - 1) == '>' && content.charAt(position - 2) == '-') {
			return true;
		}
		return false;
	}

	private boolean afterDollar(String content, int position) {
		if (position < 1) {
			return false;
		}
		if (content.charAt(position - 1) == '$') {
			return true;
		}
		return false;
	}

	private static boolean isIdentifierChar(char ch) {
		return Character.isLetterOrDigit(ch) || ch == '_' || ch == '$';
	}

	private String getWordStarting(String content, int position, int maxLen) {
		int original = position;
		if (position <= 0) {
			return ""; //$NON-NLS-1$
		}
		if (position >= content.length()) {
			position = content.length();
		}
		position--;
		int len = 0;
		while (position >= 0 && len < maxLen && isIdentifierChar(content.charAt(position))) {
			position--;
		}
		if (position + 1 > original) {
			return ""; //$NON-NLS-1$
		}
		if ((position >= 0 && Character.isWhitespace(content.charAt(position))) || position == -1) {
			return content.substring(position + 1, original);
		}
		return null;
	}

	public void complete(ISourceModule module, int position, int i) {
		this.currentModule = module;
		if (Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_BUILD).length > 0) {
			// FIXIT, make more correct awaiting for building
			this.requestor.completionFailure(new DefaultProblem(null, "Please wait until the build is finished", 0, null, IStatus.WARNING, startPosition, endPosition, -1));
			return;
		}

		completedNames.clear();
		this.actualCompletionPosition = position;
		this.requestor.beginReporting();
		org.eclipse.dltk.core.ISourceModule modelModule = (org.eclipse.dltk.core.ISourceModule) module;
		try {
			String content = module.getSourceContents();

			String wordStarting = getWordStarting(content, position, 10);

			if (wordStarting != null) {
				this.setSourceRange(position - wordStarting.length(), position);
				Collection<String> keywords = PHPKeywords.findNamesByPrefix(modelModule.getScriptProject().getProject(), wordStarting);
				for (String keyword : keywords) {
					reportKeyword(keyword);
				}
			}

			ModuleDeclaration moduleDeclaration = parser.parse(module.getFileName(), content.toCharArray(), null);

			if (afterDollar(content, position)) {
				completeGlobalVar((org.eclipse.dltk.core.ISourceModule) module, moduleDeclaration, "$", position); //$NON-NLS-1$
			}
			else if (afterColons(content, position)) {
				ASTNode node = ASTUtils.findMaximalNodeEndingAt(moduleDeclaration, position - 2);
				this.setSourceRange(position, position);
				if (node != null) {
					BasicContext basicContext = new BasicContext(modelModule, moduleDeclaration);
					ExpressionTypeGoal goal = new ExpressionTypeGoal(basicContext, node);
					IEvaluatedType type = inferencer.evaluateType(goal, 3000);
					reportSubElements(modelModule, type, wordStarting);
				} else {
					completeConstant(modelModule, moduleDeclaration, "", position); //$NON-NLS-1$
				}
			} else {
				ASTNode minimalNode = ASTUtils.findMinimalNode(moduleDeclaration, position, position);
				if (minimalNode != null) {

					IContext context = ASTUtils.findContext(modelModule, moduleDeclaration, minimalNode);
					if (context != null) {
						this.context = context;

						if (minimalNode instanceof CallExpression) {
							completeCall(modelModule, moduleDeclaration, (CallExpression) minimalNode, position);
						} else if (minimalNode instanceof ConstantReference) {
							completeConstant(modelModule, moduleDeclaration, (ConstantReference) minimalNode, position);
						} else if (minimalNode instanceof FieldAccess) {
							completeFieldAccess(modelModule, moduleDeclaration, (FieldAccess) minimalNode, position);
						} else if (minimalNode instanceof SimpleReference) {
							completeSimpleRef(modelModule, moduleDeclaration, wordStarting, position);
						} else { // worst case
							if (wordStarting == null || wordStarting.length() == 0) {
								int rel = RELEVANCE_FREE_SPACE;
								try {
									IModelElement[] children = modelModule.getChildren();
									if (children != null) {
										for (int j = 0; j < children.length; j++) {
											if (children[j] instanceof IField) {
												reportField((IField) children[j], rel);
											}
											if (children[j] instanceof IMethod) {
												IMethod method = (IMethod) children[j];
												if ((method.getFlags() & Modifiers.AccStatic) == 0) {
													reportMethod(method, rel);
												}
											}
											if (children[j] instanceof IType && !children[j].getElementName().trim().startsWith("<<")) {
												reportType((IType) children[j], rel);
											}
										}
									}
								} catch (ModelException e) {
									Logger.logException(e);
								}
							}
						}
					}
				}

			}

		} finally {
			this.requestor.endReporting();
		}
	}

	private void completeClassMethods(org.eclipse.dltk.core.ISourceModule modelModule, ModuleDeclaration moduleDeclaration, IType type, String prefix) {
		try {
			SearchEngine engine = new SearchEngine();
			IDLTKSearchScope scope = SearchEngine.createHierarchyScope(type);
			SearchPattern pattern = SearchPattern.createPattern(prefix + "*", IDLTKSearchConstants.DECLARATIONS, IDLTKSearchConstants.METHOD, SearchPattern.R_PATTERN_MATCH | SearchPattern.R_CAMELCASE_MATCH); //$NON-NLS-1$

			engine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					reportMethod((IMethod) match.getElement(), RELEVANCE_METHODS);
				}
			}, new NullProgressMonitor());

		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	private void completeClassMethods(org.eclipse.dltk.core.ISourceModule modelModule, ModuleDeclaration moduleDeclaration, IEvaluatedType type, String prefix) {
		IModelElement[] modelElements = PHPTypeInferenceUtils.getModelElements(type, (BasicContext) context, false);
		if (modelElements != null) {
			for (IModelElement element : modelElements) {
				completeClassMethods(modelModule, moduleDeclaration, (IType) element, prefix);
			}
		}
	}

	private void completeClassMethods(org.eclipse.dltk.core.ISourceModule modelModule, ModuleDeclaration moduleDeclaration, ASTNode receiver, String pattern) {
		ExpressionTypeGoal goal = new ExpressionTypeGoal(new BasicContext(modelModule, moduleDeclaration), receiver);
		IEvaluatedType type = inferencer.evaluateType(goal, 2000);
		completeClassMethods(modelModule, moduleDeclaration, type, pattern);
	}

	private void completeGlobalVar(org.eclipse.dltk.core.ISourceModule module, ModuleDeclaration moduleDeclaration, String prefix, int position) {
		int relevance = 424242;
		this.setSourceRange(position - prefix.length(), position);

		IModelElement[] elements = PHPMixinModel.getInstance().getVariable(prefix + "*", null, null);
		for (IModelElement element : elements) {
			reportField((IField) element, relevance--);
		}

		for (int i = 0; i < globalVars.length; i++) {
			if (globalVars[i].startsWith(prefix)) {
				reportField(new FakeField((ModelElement) module, globalVars[i], 0, 0), relevance--);
			}
		}
	}

	private void completeSimpleRef(org.eclipse.dltk.core.ISourceModule module, ModuleDeclaration moduleDeclaration, String prefix, int position) {
		this.setSourceRange(position - prefix.length(), position);
		
		if (prefix.startsWith("$")) { // globals //$NON-NLS-1$
			completeGlobalVar(module, moduleDeclaration, prefix, position);
		} else {
			try {
				SearchEngine engine = new SearchEngine();
				IDLTKSearchScope scope = SearchEngine.createSearchScope(new IModelElement[] { module.getScriptProject() });
				SearchPattern pattern = SearchPattern.createPattern(prefix + "*", IDLTKSearchConstants.DECLARATIONS, IDLTKSearchConstants.ALL_OCCURRENCES, SearchPattern.R_PATTERN_MATCH | SearchPattern.R_CAMELCASE_MATCH); //$NON-NLS-1$
	
				engine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
					
					int relevance = 424242;
	
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						Object element = match.getElement();
						if (element instanceof IMethod) {
							reportMethod((IMethod) element, RELEVANCE_METHODS);
						} else if (element instanceof IField) {
							reportField((IField) element, relevance--);
						} else if (element instanceof IType) {
							reportType((IType) element, relevance--);
						}
					}
				}, new NullProgressMonitor());
	
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	private void reportSubElements(org.eclipse.dltk.core.ISourceModule module, IEvaluatedType type, String prefix) {
		int relevance = 424242;

		List<IType> types = new LinkedList<IType>();
		List<IMethod> methods = new LinkedList<IMethod>();
		List<IField> fields = new LinkedList<IField>();

		if (type instanceof PHPClassType) {
			PHPClassType rubyClassType = (PHPClassType) type;
			IMixinElement mixinElement = model.get(rubyClassType.getModelKey());
			if (mixinElement != null) {
				IMixinElement[] children = mixinElement.getChildren();
				for (int i = 0; i < children.length; i++) {
					Object[] infos = children[i].getAllObjects();
					for (int j = 0; j < infos.length; j++) {
						PHPMixinElementInfo obj = (PHPMixinElementInfo) infos[j];
						if (obj.getObject() == null) {
							continue;
						}
						if (obj.getKind() == PHPMixinElementInfo.K_CLASS) {
							IType type2 = (IType) obj.getObject();
							if (type2 != null && type2.getElementName().startsWith(prefix)) {
								// reportType(type2, relevance--);
								types.add(type2);
							}
						} else if (obj.getKind() == PHPMixinElementInfo.K_METHOD) {
							IMethod method2 = (IMethod) obj.getObject();
							if (method2 != null && method2.getElementName().startsWith(prefix)) {
								// reportMethod(method2, relevance--);
								methods.add(method2);
							}
						} else if (obj.getKind() == PHPMixinElementInfo.K_VARIABLE || obj.getKind() == PHPMixinElementInfo.K_CONSTANT) {
							IField fff = (IField) obj.getObject();
							if (fff != null && fff.getElementName().startsWith(prefix)) {
								// reportField(fff, relevance--);
								fields.add(fff);
							}
						}
						break;
					}
				}
			}
		} else {
			// never should be here
		}

		Collections.sort(fields, modelElementComparator);
		for (IField t : fields) {
			reportField(t, relevance--);
		}

		Collections.sort(types, modelElementComparator);
		for (IType t : types) {
			reportType(t, relevance--);
		}

		Collections.sort(methods, modelElementComparator);
		for (IMethod t : methods) {
			reportMethod(t, relevance--);
		}

	}

	private void completeFieldAccess(org.eclipse.dltk.core.ISourceModule module, ModuleDeclaration moduleDeclaration, FieldAccess node, int position) {
		String content;
		try {
			content = module.getSource();
		} catch (ModelException e) {
			return;
		}
		int pos = (node.getDispatcher() != null) ? (node.getDispatcher().sourceEnd() + 2) : (node.sourceStart());
		String starting = null;
		try {
			starting = content.substring(pos, position).trim();
		} catch (IndexOutOfBoundsException e) {
			Logger.logException(e);
			return;
		}

		this.setSourceRange(position - starting.length(), position);

		ExpressionTypeGoal goal = new ExpressionTypeGoal(new BasicContext(module, moduleDeclaration), node.getDispatcher());
		IEvaluatedType type = inferencer.evaluateType(goal, 3000);
		reportSubElements(module, type, starting);
	}

	private void completeConstant(org.eclipse.dltk.core.ISourceModule module, ModuleDeclaration moduleDeclaration, String prefix, int position) {
		int relevance = 4242;
		this.setSourceRange(position - prefix.length(), position);

		IModelElement[] elements = PHPMixinModel.getInstance().getConstant(prefix + "*", null);
		for (IModelElement element : elements) {
			reportField((IField) element, relevance--);
		}
	}

	private void completeConstant(org.eclipse.dltk.core.ISourceModule module, ModuleDeclaration moduleDeclaration, ConstantReference node, int position) {
		String content;
		try {
			content = module.getSource();
		} catch (ModelException e) {
			return;
		}

		String prefix = content.substring(node.sourceStart(), position);
		this.setSourceRange(position - prefix.length(), position);
		completeConstant(module, moduleDeclaration, prefix, position);
	}

	private void completeCall(org.eclipse.dltk.core.ISourceModule module, ModuleDeclaration moduleDeclaration, CallExpression node, int position) {
		ASTNode receiver = node.getReceiver();

		String content;
		try {
			content = module.getSource();
		} catch (ModelException e) {
			return;
		}

		int pos = (receiver != null) ? (receiver.sourceEnd() + 1) : (node.sourceStart());

		for (int t = 0; t < 2; t++) { // correct not more 2 chars
			if (pos < position && !isIdentifierChar(content.charAt(pos))) {
				// calls
				pos++;
			}
		}

		String starting = content.substring(pos, position).trim();

		if (receiver == null) {
			completeSimpleRef(module, moduleDeclaration, starting, position);
		}

		this.setSourceRange(position - starting.length(), position);

		if (receiver != null) {
			completeClassMethods(module, moduleDeclaration, receiver, starting);
		}
	}

	protected String processFieldName(IField field, String token) {
		return field.getElementName();
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
		char[] compl = name;

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

	private void reportType(IType type, int rel) {
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
			proposal.setCompletion(elementName.toCharArray());
			// proposal.setFlags(Flags.AccDefault);
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

	private void reportField(IField field, int rel) {
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
			proposal.setCompletion(elementName.toCharArray());
			// proposal.setFlags(Flags.AccDefault);
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