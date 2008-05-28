package org.eclipse.php.core.codeassist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.compiler.env.ISourceModule;
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
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferencer;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

public class CodeAssistUtils {
	
	private static final String SELF = "self"; //$NON-NLS-1$
	private static final String DOLLAR = "$"; //$NON-NLS-1$
	private static final String WILDCARD = "*"; //$NON-NLS-1$
	private static final String PAAMAYIM_NEKUDOTAIM = "::"; //$NON-NLS-1$
	protected static final String CLASS_FUNCTIONS_TRIGGER = PAAMAYIM_NEKUDOTAIM; //$NON-NLS-1$
	protected static final String OBJECT_FUNCTIONS_TRIGGER = "->"; //$NON-NLS-1$
	private static final Pattern globalPattern = Pattern.compile("\\$GLOBALS[\\s]*\\[[\\s]*[\\'\\\"][\\w]+[\\'\\\"][\\s]*\\]"); //$NON-NLS-1$
	
	public static boolean startsWithIgnoreCase(String word, String prefix) {
		return word.toLowerCase().startsWith(prefix.toLowerCase());
	}
	
	public static IMethod[] getSuperClassMethods(IType type, String prefix, boolean exactName) {
		final Set<IMethod> methods = new HashSet<IMethod>();
		try {
			if (type.getSuperClasses() != null) {
				SearchEngine searchEngine = new SearchEngine();
				IDLTKSearchScope scope = SearchEngine.createHierarchyScope(type);
				SearchPattern pattern;
				if (exactName) {
					pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
				} else {
					pattern = SearchPattern.createPattern(prefix + WILDCARD, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH, PHPLanguageToolkit.getDefault());
				}

				searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						methods.add((IMethod) match.getElement());
					}
				}, null);
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	public static IMethod[] getClassMethods(IType type, String prefix, boolean exactName) {
		final Set<IMethod> methods = new HashSet<IMethod>();
		try {
			methods.addAll(Arrays.asList(getSuperClassMethods(type, prefix, exactName)));

			IMethod[] typeMethods = type.getMethods();
			for (IMethod typeMethod : typeMethods) {
				String methodName = typeMethod.getElementName();
				if ((exactName && methodName.equalsIgnoreCase(prefix)) || startsWithIgnoreCase(methodName, prefix)) {
					methods.add(typeMethod);
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	public static IField[] getClassFields(IType type, String prefix, boolean exactName, boolean searchConstants) {
		final Set<IField> fields = new HashSet<IField>();
		try {
			SearchEngine searchEngine = new SearchEngine();
			IDLTKSearchScope scope;
			SearchPattern pattern;

			if (type.getSuperClasses() != null) {
				scope = SearchEngine.createHierarchyScope(type);
				
				if (searchConstants) {
					// search for constants in hierarchy
					if (exactName) {
						pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
					} else {
						pattern = SearchPattern.createPattern(prefix + WILDCARD, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH, PHPLanguageToolkit.getDefault());
					}
					
					searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
						public void acceptSearchMatch(SearchMatch match) throws CoreException {
							fields.add((IField) match.getElement());
						}
					}, null);
				}

				// search for variables in hierarchy
				if (exactName) {
					pattern = SearchPattern.createPattern(DOLLAR + prefix, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
				} else {
					pattern = SearchPattern.createPattern(DOLLAR + prefix + WILDCARD, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH, PHPLanguageToolkit.getDefault());
				}
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
				
				int flags = typeField.getFlags();
				if ((flags & Modifiers.AccConstant) != 0) {
					if (exactName && elementName.equals(prefix) || elementName.startsWith(prefix)) {
						fields.add (typeField);
					}
				} else { // variable
					String tmp = prefix;
					if (!tmp.startsWith(DOLLAR)) {
						tmp = DOLLAR + tmp;
					}
					if (exactName && elementName.equals(tmp) || elementName.startsWith(tmp)) {
						fields.add (typeField);
					}
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return fields.toArray(new IField[fields.size()]);
	}
	
	public static IType[] getVariableType(IType[] types, String propertyName, int offset, int line) {
		for (IType type : types) {
			IField[] fields = getClassFields(type, propertyName, true, false);

			Set<String> processedFields = new HashSet<String>();
			for (IField field : fields) {
				if (processedFields.contains(field.getElementName())) {
					continue;
				}
				processedFields.add(field.getElementName());

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

	public static IType[] getVariableType(ISourceModule sourceModule, String variableName, int position, int line, boolean showObjectsFromOtherFiles) {
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
	
	public static IType[] modelElementsToTypes(IModelElement[] elements) {
		List<IType> types = new ArrayList<IType>(elements.length);
		for (IModelElement element : elements) {
			types.add((IType) element);
		}
		return types.toArray(new IType[types.size()]);
	}
	
	public static IType[] getFunctionReturnType(IType type, String functionName) {
		IMethod[] classMethod = getClassMethods(type, functionName, true);
		if (classMethod.length > 0) {
			return getFunctionReturnType(classMethod[0]);
		}
		return null;
	}
	
	public static IType[] getFunctionReturnType(IMethod method) {
		MethodElementReturnTypeGoal goal = new MethodElementReturnTypeGoal(method);
		PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
		IEvaluatedType evaluatedType = typeInferencer.evaluateType(goal);
		if (evaluatedType != null) {
			IModelElement[] modelElements = PHPMixinModel.getInstance().getClass(evaluatedType.getTypeName());
			return modelElementsToTypes(modelElements);
		}
		return null;
	}
	
	public static IType getContainerClassData(ISourceModule sourceModule, int offset) {
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

	public static IMethod getContainerMethodData(ISourceModule sourceModule, int offset) {
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
	public static int getFunctionNameEndOffset(TextSequence statementText, int offset) {
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
	 * The "self" function needs to be added only if we are in a class method
	 * and it is not an abstract class or an interface
	 * @param fileData
	 * @param offset 
	 * @return the self class data or null in case not found 
	 */
	public static IType getSelfClassData(ISourceModule sourceModule, int offset) {

		IType type = getContainerClassData(sourceModule, offset);
		IMethod method = getContainerMethodData(sourceModule, offset);

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
	
	public static boolean isFunctionCall(String functionName) {
		IModelElement[] functions = PHPMixinModel.getInstance().getFunction(functionName);
		return functions.length > 0;
	}
	
	public static IType[] getOnlyClasses(String prefix, boolean exactName) {
		IModelElement[] classes = PHPMixinModel.getInstance().getClass(exactName ? prefix : prefix + WILDCARD);
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
	
	public static IType[] getOnlyInterfaces(String prefix, boolean exactName) {
		IModelElement[] interfaces = PHPMixinModel.getInstance().getClass(exactName ? prefix : prefix + WILDCARD);
		List<IType> onlyInterfaces = new LinkedList<IType>();
		for (IModelElement i : interfaces) {
			IType type = (IType) i;
			try {
				if ((type.getFlags() & Modifiers.AccInterface) != 0) {
					onlyInterfaces.add(type);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
		return onlyInterfaces.toArray(new IType[onlyInterfaces.size()]);
	}

	public static IType[] getTypesFor(ISourceModule sourceModule, TextSequence statementText, int endPosition, int offset, int line, boolean determineObjectFromOtherFile) {
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
			return innerGetClassName(sourceModule, statementText, propertyEndPosition, isClassTriger, offset, line, determineObjectFromOtherFile);
		}

		int propertyStartPosition = PHPTextSequenceUtilities.readForwardSpaces(statementText, lastObjectOperator + 2);
		String propertyName = statementText.subSequence(propertyStartPosition, propertyEndPosition).toString();
		IType[] types = getTypesFor(sourceModule, statementText, propertyStartPosition, offset, line, determineObjectFromOtherFile);

		int bracketIndex = propertyName.indexOf('(');

		if (bracketIndex == -1) {
			// meaning its a class variable and not a function
			return getVariableType(types, propertyName, offset, line);
		}

		String functionName = propertyName.substring(0, bracketIndex).trim();
		Set<IType> result = new HashSet<IType>();
		for (IType type : types) {
			IType[] returnTypes = getFunctionReturnType(type, functionName);
			if (returnTypes != null) {
				result.addAll(Arrays.asList(returnTypes));
			}
		}
		return result.toArray(new IType[result.size()]);
	}


	/**
	 * getting an instance and finding its type.
	 */
	public static IType[] innerGetClassName(ISourceModule sourceModule, TextSequence statementText, int propertyEndPosition, boolean isClassTriger, int offset, int line, boolean determineObjectTypeFromOtherFile) {

		int classNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, propertyEndPosition, true);
		String className = statementText.subSequence(classNameStart, propertyEndPosition).toString();
		if (isClassTriger) {
			if (className.equals(SELF)) { //$NON-NLS-1$
				IType classData = getContainerClassData(sourceModule, offset - 6); //the offset before self::
				if (classData != null) {
					return new IType[] { classData };
				}
			} else if (className.equals("parent")) { //$NON-NLS-1$
				IType classData = getContainerClassData(sourceModule, offset - 8); //the offset before parent::
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
			return getVariableType(sourceModule, className, statementStart, line, determineObjectTypeFromOtherFile);
		}
		// if its function call calc the return type.
		if (statementText.charAt(propertyEndPosition - 1) == ')') {
			int functionNameEnd = getFunctionNameEndOffset(statementText, propertyEndPosition - 1);
			int functionNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, functionNameEnd, false);

			String functionName = statementText.subSequence(functionNameStart, functionNameEnd).toString();
			IType classData = getContainerClassData(sourceModule, offset);
			if (classData != null) { //if its a clss function
				return getFunctionReturnType(classData, functionName);
			}
			
			// if its a non class function
			Set<IType> returnTypes = new HashSet<IType>();
			IModelElement[] functions = PHPMixinModel.getInstance().getFunction(functionName);
			for (IModelElement function : functions) {
				IType[] types = getFunctionReturnType((IMethod) function);
				if (types != null) {
					returnTypes.addAll(Arrays.asList(types));
				}
			}
			return returnTypes.toArray(new IType[returnTypes.size()]);
		}
		return null;
	}

	public static boolean isClassFunctionCall(ISourceModule sourceModule, IType[] className, String functionName) {
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
}
