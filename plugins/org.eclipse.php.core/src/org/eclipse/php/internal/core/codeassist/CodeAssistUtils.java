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
package org.eclipse.php.internal.core.codeassist;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.core.search.indexing.IIndexConstants;
import org.eclipse.dltk.internal.core.*;
import org.eclipse.dltk.internal.core.util.HandleFactory;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.mixin.PHPMixinParser;
import org.eclipse.php.internal.core.typeinference.*;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This is a common utility used by completion and selection engines for PHP elements retrieval.
 * @author michael
 */
public class CodeAssistUtils {

	/**
	 * Whether to look for exact name or for the prefix
	 */
	public static final int EXACT_NAME = 1 << 0;

	/**
	 * Whether the match will be case-sensitive
	 */
	public static final int CASE_SENSITIVE = 1 << 1;

	/**
	 * Whether to retrieve only current file elements
	 */
	public static final int ONLY_CURRENT_FILE = 1 << 2;

	/**
	 * Exclude classes when looking for types in {@link #getGlobalTypes(ISourceModule, String, int)}
	 */
	public static final int EXCLUDE_CLASSES = 1 << 3;

	/**
	 * Exclude interfaces when looking for types in {@link #getGlobalTypes(ISourceModule, String, int)}
	 */
	public static final int EXCLUDE_INTERFACES = 1 << 4;

	/**
	 * Exclude namespaces when looking for types in {@link #getGlobalTypes(ISourceModule, String, int)} 
	 */
	public static final int EXCLUDE_NAMESPACES = 1 << 5;
	
	/**
	 * Exclude constants when looking for fields in {@link #getGlobalFields(ISourceModule, String, int)}
	 */
	public static final int EXCLUDE_CONSTANTS = 1 << 6;
	
	/**
	 * Exclude variables (retreive only constants) when looking for fields in {@link #getGlobalFields(ISourceModule, String, int)}
	 */
	public static final int EXCLUDE_VARIABLES = 1 << 7;

	/**
	 * Whether to use PHPDoc in type inference
	 */
	public static final int USE_PHPDOC = 1 << 5;

	private static final String SELF = "self"; //$NON-NLS-1$
	private static final String DOLLAR = "$"; //$NON-NLS-1$
	private static final String WILDCARD = "*"; //$NON-NLS-1$
	private static final String PAAMAYIM_NEKUDOTAIM = "::"; //$NON-NLS-1$
	private static final String NS_SEPARATOR = "\\"; //$NON-NLS-1$
	protected static final String OBJECT_FUNCTIONS_TRIGGER = "->"; //$NON-NLS-1$
	private static final Pattern globalPattern = Pattern.compile("\\$GLOBALS[\\s]*\\[[\\s]*[\\'\\\"][\\w]+[\\'\\\"][\\s]*\\]"); //$NON-NLS-1$

	private static final IModelElement[] EMPTY = new IModelElement[0];
	private static final IType[] EMPTY_TYPES = new IType[0];

	public static boolean startsWithIgnoreCase(String word, String prefix) {
		return word.toLowerCase().startsWith(prefix.toLowerCase());
	}

	/**
	 * This method finds all ancestor methods that match the given prefix.
	 * @param type
	 * @param prefix
	 * @param mask
	 * @return
	 */
	public static IMethod[] getSuperClassMethods(IType type, String prefix, int mask) {
		final Set<IMethod> methods = new TreeSet<IMethod>(new AlphabeticComparator());
		try {
			if (type.getSuperClasses() != null) {
				if (prefix.length() == 0) {
					ITypeHierarchy superTypeHierarchy = type.newSupertypeHierarchy(null);
					IType[] allSuperclasses = superTypeHierarchy.getAllSuperclasses(type);
					for (IType superClass : allSuperclasses) {
						for (IMethod method : superClass.getMethods()) {
							methods.add(method);
						}
					}
				} else {
					SearchEngine searchEngine = new SearchEngine();
					IDLTKSearchScope scope = SearchEngine.createSuperHierarchyScope(type);

					int matchRule;
					boolean exactName = (mask & EXACT_NAME) != 0;
					if (prefix.length() == 0 && !exactName) {
						prefix = WILDCARD;
						matchRule = SearchPattern.R_PATTERN_MATCH;
					} else {
						matchRule = exactName ? SearchPattern.R_EXACT_MATCH : SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_PREFIX_MATCH;
					}

					SearchPattern pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, matchRule, PHPLanguageToolkit.getDefault());

					searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
						public void acceptSearchMatch(SearchMatch match) throws CoreException {
							methods.add((IMethod) match.getElement());
						}
					}, null);
				}
			}
		} catch (Exception e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * This method finds all class methods that match the given prefix.
	 * @param type
	 * @param prefix
	 * @param mask
	 * @return
	 */
	public static IMethod[] getTypeMethods(IType type, String prefix, int mask) {
		final Set<IMethod> methods = new TreeSet<IMethod>(new AlphabeticComparator());
		final Set<String> methodNames = new HashSet<String>();
		boolean exactName = (mask & EXACT_NAME) != 0;
		try {
			IMethod[] typeMethods = type.getMethods();
			for (IMethod typeMethod : typeMethods) {
				String methodName = typeMethod.getElementName().toLowerCase();
				if (exactName) {
					if (methodName.equalsIgnoreCase(prefix)) {
						methods.add(typeMethod);
						methodNames.add(methodName);
						break;
					}
				} else if (startsWithIgnoreCase(methodName, prefix)) {
					methods.add(typeMethod);
					methodNames.add(methodName);
				}
			}

			IMethod[] superClassMethods = getSuperClassMethods(type, prefix, mask);
			// Filter overriden methods:
			for (IMethod superClassMethod : superClassMethods) {
				if (type.equals(superClassMethod.getDeclaringType())) {
					continue;
				}
				String methodName = superClassMethod.getElementName().toLowerCase();
				if (!methodNames.contains(methodName)) {
					methods.add(superClassMethod);
					methodNames.add(methodName);
				}
			}

		} catch (Exception e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * This method finds all class fields that match the given prefix.
	 * @param type
	 * @param prefix
	 * @param mask
	 * @return
	 */
	public static IField[] getTypeFields(IType type, String prefix, int mask) {
		boolean exactName = (mask & EXACT_NAME) != 0;
		boolean searchConstants = (mask & EXCLUDE_CONSTANTS) == 0;

		final Set<IField> fields = new TreeSet<IField>(new AlphabeticComparator());
		try {
			List<IType> searchTypes = new LinkedList<IType>();

			if (prefix.length() == 0) {
				searchTypes.add(type);

				ITypeHierarchy superTypeHierarchy = type.newSupertypeHierarchy(null);
				IType[] allSuperclasses = superTypeHierarchy.getAllSuperclasses(type);
				searchTypes.addAll(Arrays.asList(allSuperclasses));

			} else if (type.getSuperClasses() != null) {
				SearchEngine searchEngine = new SearchEngine();
				IDLTKSearchScope scope;
				SearchPattern pattern;

				int matchRule;
				if (prefix.length() == 0 && !exactName) {
					prefix = WILDCARD;
					matchRule = SearchPattern.R_PATTERN_MATCH;
				} else {
					matchRule = exactName ? SearchPattern.R_EXACT_MATCH : SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_PREFIX_MATCH;
				}

				scope = SearchEngine.createSuperHierarchyScope(type);

				if (searchConstants) {
					// search for constants in hierarchy
					pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, matchRule, PHPLanguageToolkit.getDefault());

					searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
						public void acceptSearchMatch(SearchMatch match) throws CoreException {
							fields.add((IField) match.getElement());
						}
					}, null);
				}

				// search for variables in hierarchy
				pattern = SearchPattern.createPattern(prefix.startsWith(DOLLAR) ? prefix : DOLLAR + prefix, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, matchRule, PHPLanguageToolkit.getDefault());

				searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						fields.add((IField) match.getElement());
					}
				}, null);

			} else {
				searchTypes.add(type);
			}

			for (IType searchType : searchTypes) {
				IField[] typeFields = searchType.getFields();

				for (IField typeField : typeFields) {
					String elementName = typeField.getElementName();
					int flags = typeField.getFlags();
					if (PHPFlags.isConstant(flags)) {
						if (exactName) {
							if (elementName.equals(prefix)) {
								fields.add(typeField);
								break;
							}
						} else if (elementName.startsWith(prefix)) {
							fields.add(typeField);
						}
					} else { // variable
						String tmp = prefix;
						if (!tmp.startsWith(DOLLAR)) {
							tmp = DOLLAR + tmp;
						}
						if (exactName) {
							if (elementName.equals(tmp)) {
								fields.add(typeField);
								break;
							}
						} else if (elementName.startsWith(tmp)) {
							fields.add(typeField);
						}
					}
				}
			}
		} catch (Exception e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return fields.toArray(new IField[fields.size()]);
	}

	/**
	 * Returns type of a class field defined by name.
	 * @param types
	 * @param propertyName
	 * @param offset
	 * @param line
	 * @return
	 */
	public static IType[] getVariableType(IType[] types, String propertyName, int offset, int line) {
		if (types != null) {
			for (IType type : types) {
				PHPClassType classType = new PHPClassType(type.getElementName());
				IField[] fields = getTypeFields(type, propertyName, CASE_SENSITIVE | EXCLUDE_CONSTANTS);

				Set<String> processedFields = new HashSet<String>();
				for (IField field : fields) {

					String variableName = field.getElementName();
					if (processedFields.contains(variableName)) {
						continue;
					}
					processedFields.add(variableName);

					ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(field.getSourceModule(), null);
					BasicContext sourceModuleContext = new BasicContext(field.getSourceModule(), moduleDeclaration);
					InstanceContext instanceContext = new InstanceContext(sourceModuleContext, classType);
					PHPTypeInferencer typeInferencer = new PHPTypeInferencer();

					PHPDocClassVariableGoal phpDocGoal = new PHPDocClassVariableGoal(instanceContext, variableName);
					IEvaluatedType evaluatedType = typeInferencer.evaluateTypePHPDoc(phpDocGoal, 3000);

					IModelElement[] modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, sourceModuleContext);
					if (modelElements != null) {
						return modelElementsToTypes(modelElements);
					}

					ClassVariableDeclarationGoal goal = new ClassVariableDeclarationGoal(sourceModuleContext, types, variableName);
					evaluatedType = typeInferencer.evaluateType(goal);

					modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, sourceModuleContext);
					if (modelElements != null) {
						return modelElementsToTypes(modelElements);
					}
				}
			}
		}
		return EMPTY_TYPES;
	}

	/**
	 * Returns type of a variable defined by name.
	 * @param sourceModule
	 * @param variableName
	 * @param position
	 * @param line
	 * @return
	 */
	public static IType[] getVariableType(ISourceModule sourceModule, String variableName, int position, int line) {
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		IContext context = ASTUtils.findContext(sourceModule, moduleDeclaration, position);
		if (context != null) {
			VariableReference varReference = new VariableReference(position, position + variableName.length(), variableName);
			ExpressionTypeGoal goal = new ExpressionTypeGoal(context, varReference);
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			IEvaluatedType evaluatedType = typeInferencer.evaluateType(goal);

			IModelElement[] modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, (ISourceModuleContext) context);
			if (modelElements != null) {
				return modelElementsToTypes(modelElements);
			}
		}
		return EMPTY_TYPES;
	}

	/**
	 * Converts model elements array to IType elements array
	 * @param elements
	 * @return
	 */
	public static IType[] modelElementsToTypes(IModelElement[] elements) {
		List<IType> types = new ArrayList<IType>(elements.length);
		for (IModelElement element : elements) {
			types.add((IType) element);
		}
		return types.toArray(new IType[types.size()]);
	}

	/**
	 * Determines the return type of the method defined by type element and method name.
	 * @param type
	 * @param functionName
	 * @return
	 */
	public static IType[] getFunctionReturnType(IType type, String functionName) {
		IMethod[] classMethod = getTypeMethods(type, functionName, EXACT_NAME);
		if (classMethod.length > 0) {
			return getFunctionReturnType(classMethod[0]);
		}
		return EMPTY_TYPES;
	}

	/**
	 * Determines the return type of the given method element.
	 * @param method
	 * @return
	 */
	public static IType[] getFunctionReturnType(IMethod method) {
		return getFunctionReturnType(method, USE_PHPDOC);
	}

	/**
	 * Determines the return type of the given method element.
	 * @param method
	 * @param mask
	 * @return
	 */
	public static IType[] getFunctionReturnType(IMethod method, int mask) {
		PHPTypeInferencer typeInferencer = new PHPTypeInferencer();

		IEvaluatedType classType = null;
		if (method.getDeclaringType() != null) {
			classType = new PHPClassType(method.getDeclaringType().getElementName());
		}
		org.eclipse.dltk.core.ISourceModule sourceModule = method.getSourceModule();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		BasicContext sourceModuleContext = new BasicContext(sourceModule, moduleDeclaration);

		InstanceContext instanceContext = new InstanceContext(sourceModuleContext, classType);
		IEvaluatedType evaluatedType;
		IModelElement[] modelElements;

		boolean usePhpDoc = (mask & USE_PHPDOC) != 0;
		if (usePhpDoc) {
			PHPDocMethodReturnTypeGoal phpDocGoal = new PHPDocMethodReturnTypeGoal(instanceContext, method.getElementName());
			evaluatedType = typeInferencer.evaluateTypePHPDoc(phpDocGoal, 3000);

			modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, sourceModuleContext);
			if (modelElements != null) {
				return modelElementsToTypes(modelElements);
			}
		}

		MethodElementReturnTypeGoal methodGoal = new MethodElementReturnTypeGoal(instanceContext, method);
		evaluatedType = typeInferencer.evaluateType(methodGoal);
		modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, sourceModuleContext);
		if (modelElements != null) {
			return modelElementsToTypes(modelElements);
		}
		return EMPTY_TYPES;
	}

	/**
	 * Returns enclosing class for the given offset.
	 * @param sourceModule
	 * @param offset
	 * @return
	 */
	public static IType getContainerClassData(ISourceModule sourceModule, int offset) {
		IModelElement type = null;
		try {
			type = sourceModule.getElementAt(offset);
			while (type != null && !(type instanceof IType)) {
				type = type.getParent();
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return (IType) type;
	}

	/**
	 * Returns enclosing function or method for the given offset.
	 * @param sourceModule
	 * @param offset
	 * @return
	 */
	public static IMethod getContainerMethodData(ISourceModule sourceModule, int offset) {
		try {
			IModelElement method = sourceModule.getElementAt(offset);
			if (method instanceof IMethod) {
				return (IMethod) method;
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
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
			try {
				int flags = type.getFlags();
				if (!PHPFlags.isAbstract(flags) && !PHPFlags.isInterface(flags) && !PHPFlags.isInterface(flags)) {
					return type;
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * Checks whether function with given name exists.
	 * @param functionName
	 * @param scriptProject 
	 * @return
	 */
	public static boolean isFunctionCall(String functionName, IScriptProject scriptProject) {
		IModelElement[] functions = scriptProject == null ? PHPMixinModel.getWorkspaceInstance().getFunction(functionName) : PHPMixinModel.getInstance(scriptProject).getFunction(functionName);
		return functions.length > 0;
	}

	/**
	 * This method finds types for the receiver in the statement text.
	 * @param sourceModule
	 * @param statementText
	 * @param endPosition
	 * @param offset
	 * @param line
	 * @return
	 */
	public static IType[] getTypesFor(ISourceModule sourceModule, TextSequence statementText, int endPosition, int offset, int line) {
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, endPosition); // read whitespace

		boolean isClassTriger = false;

		if (endPosition < 2) {
			return EMPTY_TYPES;
		}

		String triggerText = statementText.subSequence(endPosition - 2, endPosition).toString();
		if (triggerText.equals(OBJECT_FUNCTIONS_TRIGGER)) {
		} else if (triggerText.equals(PAAMAYIM_NEKUDOTAIM)) {
			isClassTriger = true;
		} else {
			return EMPTY_TYPES;
		}

		int propertyEndPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, endPosition - triggerText.length());
		int lastObjectOperator = PHPTextSequenceUtilities.getPrivousTriggerIndex(statementText, propertyEndPosition);

		if (lastObjectOperator == -1) {
			// if there is no "->" or "::" in the left sequence then we need to calc the object type
			return innerGetClassName(sourceModule, statementText, propertyEndPosition, isClassTriger, offset, line);
		}

		int propertyStartPosition = PHPTextSequenceUtilities.readForwardSpaces(statementText, lastObjectOperator + triggerText.length());
		String propertyName = statementText.subSequence(propertyStartPosition, propertyEndPosition).toString();
		IType[] types = getTypesFor(sourceModule, statementText, propertyStartPosition, offset, line);

		int bracketIndex = propertyName.indexOf('(');

		if (bracketIndex == -1) {
			// meaning its a class variable and not a function
			return getVariableType(types, propertyName, offset, line);
		}

		String functionName = propertyName.substring(0, bracketIndex).trim();
		Set<IType> result = new LinkedHashSet<IType>();
		for (IType type : types) {
			IType[] returnTypes = getFunctionReturnType(type, functionName);
			if (returnTypes != null) {
				result.addAll(Arrays.asList(returnTypes));
			}
		}
		return result.toArray(new IType[result.size()]);
	}

	/**
	 * Getting an instance and finding its type.
	 */
	private static IType[] innerGetClassName(ISourceModule sourceModule, TextSequence statementText, int propertyEndPosition, boolean isClassTriger, int offset, int line) {

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
				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
				BasicContext context = new BasicContext(sourceModule, moduleDeclaration);
				IEvaluatedType type = new PHPClassType(className);
				return modelElementsToTypes(PHPTypeInferenceUtils.getModelElements(type, context));
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
			return getVariableType(sourceModule, className, statementStart, line);
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
			Set<IType> returnTypes = new LinkedHashSet<IType>();
			IModelElement[] functions = getGlobalMethods(sourceModule, functionName, EXACT_NAME);
			for (IModelElement function : functions) {
				IType[] types = getFunctionReturnType((IMethod) function);
				if (types != null) {
					returnTypes.addAll(Arrays.asList(types));
				}
			}
			return returnTypes.toArray(new IType[returnTypes.size()]);
		}
		return EMPTY_TYPES;
	}

	/**
	 * This method checks whether the specified function name refers to existing method in the given list of classes.
	 * @param sourceModule
	 * @param className
	 * @param functionName
	 * @return
	 */
	public static boolean isClassFunctionCall(ISourceModule sourceModule, IType[] className, String functionName) {
		for (IType type : className) {
			IMethod[] classMethod;
			try {
				classMethod = PHPModelUtils.getClassMethod(type, functionName, null);
				if (classMethod != null) {
					return true;
				}
			} catch (CoreException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * This method searches for all classes in the project scope that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Field name
	 * @param mask
	 */
	public static IType[] getGlobalTypes(ISourceModule sourceModule, String prefix, int mask) {

		if ((mask & EXCLUDE_CLASSES) == 0 || (mask & EXCLUDE_INTERFACES) == 0) {
			// Check whether the type name has a namespace prefix:
			int nsIdx = prefix.lastIndexOf(NS_SEPARATOR);
			if (nsIdx != -1) {
				String nsName = prefix.substring(0, nsIdx);
				prefix = prefix.substring(nsIdx + 1);

				IType[] namespaces = getGlobalTypes(sourceModule, nsName, EXCLUDE_CLASSES | EXCLUDE_INTERFACES);
				List<IType> types = new LinkedList<IType>();

				for (IType ns : namespaces) {
					if (prefix.length() == 0 && ns.getElementName().equalsIgnoreCase(nsName)) {
						continue;
					}
					types.add(ns);
				}
				
				for (IType ns : namespaces) {
					if (!ns.getElementName().equalsIgnoreCase(nsName)) {
						continue;
					}
					try {
						for (IType t : ns.getTypes()) {
							String typeName = t.getElementName();
							if ((mask & EXACT_NAME) != 0) {
								if (typeName.equalsIgnoreCase(prefix)) {
									types.add(t);
									break;
								}
							} else if (startsWithIgnoreCase(typeName, prefix)) {
								types.add(t);
							}
						}
					} catch (ModelException e) {
						if (DLTKCore.DEBUG_COMPLETION) {
							e.printStackTrace();
						}
					}
				}
				return types.toArray(new IType[types.size()]);
			}
		}

		IModelElement[] elements = getGlobalElements(sourceModule, prefix, IDLTKSearchConstants.TYPE, mask);
		List<IType> filteredElements = new LinkedList<IType>();
		for (IModelElement c : elements) {
			IType type = (IType) c;
			try {
				int flags = type.getFlags();
				if ((mask & EXCLUDE_CLASSES) != 0 && PHPFlags.isClass(flags)) {
					continue;
				}
				if ((mask & EXCLUDE_INTERFACES) != 0 && PHPFlags.isInterface(flags)) {
					continue;
				}
				if ((mask & EXCLUDE_NAMESPACES) != 0 && PHPFlags.isNamespace(flags)) {
					continue;
				}
				filteredElements.add(type);
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
		return filteredElements.toArray(new IType[filteredElements.size()]);
	}

	/**
	 * This method searches for all methods in the project scope that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Field name
	 * @param mask
	 */
	public static IModelElement[] getGlobalMethods(ISourceModule sourceModule, String prefix, int mask) {
		// Check whether the method has a namespace prefix:
		int nsIdx = prefix.lastIndexOf(NS_SEPARATOR);
		if (nsIdx != -1) {
			String nsName = prefix.substring(0, nsIdx);
			prefix = prefix.substring(nsIdx + 1);

			IType[] namespaces = getGlobalTypes(sourceModule, nsName, EXACT_NAME | EXCLUDE_CLASSES | EXCLUDE_INTERFACES);
			List<IModelElement> methods = new LinkedList<IModelElement>();
			for (IType ns : namespaces) {
				methods.addAll(Arrays.asList(getTypeMethods(ns, prefix, mask)));
			}
			return methods.toArray(new IModelElement[methods.size()]);
		}
		return getGlobalElements(sourceModule, prefix, IDLTKSearchConstants.METHOD, mask);
	}

	/**
	 * This method searches for all fields in the project scope that match the given prefix.
	 * By default variables is looked only in current file. 
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Field name
	 * @param mask
	 */
	public static IModelElement[] getGlobalFields(ISourceModule sourceModule, String prefix, int mask) {
		// Check whether the field has a namespace prefix:
		int nsIdx = prefix.lastIndexOf(NS_SEPARATOR);
		if (nsIdx != -1) {
			String nsName = prefix.substring(0, nsIdx);
			prefix = prefix.substring(nsIdx + 1);

			IType[] namespaces = getGlobalTypes(sourceModule, nsName, EXACT_NAME | EXCLUDE_CLASSES | EXCLUDE_INTERFACES);
			List<IModelElement> fields = new LinkedList<IModelElement>();
			for (IType ns : namespaces) {
				fields.addAll(Arrays.asList(getTypeFields(ns, prefix, mask)));
			}
			return fields.toArray(new IModelElement[fields.size()]);
		}
		return getGlobalElements(sourceModule, prefix, IDLTKSearchConstants.FIELD, mask);
	}

	/**
	 * Return workspace or method fields depending on current position: whether we are inside method or in global scope.
	 * @param sourceModule
	 * @param offset
	 * @param prefix
	 * @param mask
	 * @return
	 */
	public static IModelElement[] getGlobalOrMethodFields(ISourceModule sourceModule, int offset, String prefix, int mask) {
		try {
			IModelElement enclosingElement = sourceModule.getElementAt(offset);
			if (enclosingElement instanceof IField) {
				enclosingElement = enclosingElement.getParent();
			}
			if (enclosingElement instanceof IMethod) {
				IMethod method = (IMethod) enclosingElement;
				return getMethodFields(method, prefix, mask);
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return getGlobalFields(sourceModule, prefix, mask);
	}

	/**
	 * This method searches for all fields that where declared in the specified method
	 * 
	 * @param method Method to look at
	 * @param prefix Field name
	 * @param mask
	 */
	public static IModelElement[] getMethodFields(IMethod method, String prefix, int mask) {

		SearchEngine searchEngine = new SearchEngine();
		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(new IModelElement[] { method }, toolkit);

		int matchRule;
		boolean exactName = (mask & EXACT_NAME) != 0;
		if (prefix.length() == 0 && !exactName) {
			prefix = WILDCARD;
			matchRule = SearchPattern.R_PATTERN_MATCH;
		} else {
			matchRule = exactName ? SearchPattern.R_EXACT_MATCH : SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_PREFIX_MATCH;
		}

		SearchPattern pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, matchRule, toolkit);

		final Set<IModelElement> elements = new TreeSet<IModelElement>(new AlphabeticComparator());
		try {
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				private Set<String> processedVars = new HashSet<String>();

				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					IModelElement element = (IModelElement) match.getElement();
					String elementName = element.getElementName();
					if (!processedVars.contains(elementName)) {
						processedVars.add(elementName);
						elements.add(element);
					}
				}
			}, null);
		} catch (CoreException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return elements.toArray(new IModelElement[elements.size()]);
	}

	/**
	 * This method searches in the project scope for all elements of specified type that match the given prefix.
	 * If currentFileOnly parameter is <code>true</code>, the search scope for variables will contain only the source module.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Element name or prefix
	 * @param elementType Element type from {@link IDLTKSearchConstants}
	 * @param mask
	 * @return
	 */
	private static IModelElement[] getGlobalElements(ISourceModule sourceModule, String prefix, int elementType, int mask) {

		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();

		boolean isVariable = elementType == IDLTKSearchConstants.FIELD && prefix.startsWith("$"); //$NON-NLS-1$

		IScriptProject scriptProject = sourceModule.getScriptProject();
		if (!ScriptProject.hasScriptNature(scriptProject.getProject())) {
			return getSourceModuleElements(sourceModule, prefix, elementType, mask);
		}

		IDLTKSearchScope scope;
		if ((mask & ONLY_CURRENT_FILE) != 0) {
			scope = SearchEngine.createSearchScope(sourceModule);
		} else {
			if (scriptProject != null) {
				scope = SearchEngine.createSearchScope(scriptProject);
			} else {
				scope = SearchEngine.createWorkspaceScope(toolkit);
			}
		}

		if ((mask & EXACT_NAME) == 0 & (mask & ONLY_CURRENT_FILE) == 0 && isVariable) {
			// search variables using mixin model:
			PHPMixinModel mixinModel = scriptProject == null ? PHPMixinModel.getWorkspaceInstance() : PHPMixinModel.getInstance(scriptProject);
			IModelElement[] variables = mixinModel.getVariable(prefix + WILDCARD, null, null, scope);
			return variables == null ? EMPTY : filterOtherFilesElements(sourceModule, variables);
		}

		return getGlobalElements(sourceModule, scope, prefix, elementType, mask);
	}

	/**
	 * This method searches in the project scope for all elements of specified type that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current file
	 * @param scope Search scope
	 * @param prefix Element name or prefix
	 * @param elementType Element type from {@link IDLTKSearchConstants}
	 * @return
	 */
	private static IModelElement[] getGlobalElements(final ISourceModule sourceModule, final IDLTKSearchScope scope, String prefix, final int elementType, int mask) {

		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();

		SearchEngine searchEngine = new SearchEngine();
		SearchPattern pattern = null;

		boolean exactName = (mask & EXACT_NAME) != 0;
		boolean caseSensitive = (mask & CASE_SENSITIVE) != 0;
		boolean currentFileOnly = (mask & ONLY_CURRENT_FILE) != 0;

		// Group options:
		Set<String> elementsToSearch = new HashSet<String>();
		Set<String> groups = new HashSet<String>();

		boolean showGroupOptions = PHPCorePlugin.getDefault().getPluginPreferences().getBoolean(PHPCoreConstants.CODEASSIST_GROUP_OPTIONS);
		if (!prefix.startsWith("$") && !currentFileOnly && showGroupOptions && (elementType == IDLTKSearchConstants.TYPE || elementType == IDLTKSearchConstants.METHOD)) {
			if (!exactName) {
				MixinModel mixinModel = PHPMixinModel.getInstance(sourceModule.getScriptProject()).getRawModel();

				// Build the mixin request key:
				String[] elementNames;
				if (elementType == IDLTKSearchConstants.TYPE) {
					
					List<String> elementNamesList = new LinkedList<String>();
					if ((mask & EXCLUDE_CLASSES) == 0) {
						elementNamesList.addAll(Arrays.asList(mixinModel.findKeys(new StringBuilder(prefix).append(WILDCARD).append(PHPMixinParser.CLASS_SUFFIX).toString())));
					}
					if ((mask & EXCLUDE_INTERFACES) == 0) {
						elementNamesList.addAll(Arrays.asList(mixinModel.findKeys(new StringBuilder(prefix).append(WILDCARD).append(PHPMixinParser.INTERFACE_SUFFIX).toString())));
					}
					elementNames = elementNamesList.toArray(new String[elementNamesList.size()]);
					
				} else {
					elementNames = mixinModel.findKeys(new StringBuilder(MixinModel.SEPARATOR).append(prefix).append(WILDCARD).toString());
				}

				// Filter Mixin result strings:
				Set<String> elementNamesSet = new HashSet<String>();
				for (String elementName : elementNames) {
					if (elementType == IDLTKSearchConstants.TYPE) {
						elementName = elementName.substring(0, elementName.length() - 1);
					} else {
						if (!Character.isJavaIdentifierPart(elementName.substring(elementName.length() - 1).charAt(0))) {
							continue; // filter non-methods
						}
						elementName = elementName.substring(1);
						if (elementName.indexOf(IIndexConstants.SEPARATOR) != -1) {
							continue; // filter class members
						}
						if (elementName.charAt(0) == '$') {
							continue; // filter variables
						}
					}
					elementNamesSet.add(elementName);
				}
				elementNames = elementNamesSet.toArray(new String[elementNamesSet.size()]);

				// Calculate minimal namespaces:
				int prefixLength = prefix.length();
				for (String elementName : elementNames) {
					int nsIdx = elementName.substring(prefixLength).indexOf('_');
					if ((nsIdx >= 0 && prefixLength > 0 || prefixLength == 0 && nsIdx > 0) && nsIdx < elementName.length() - 1) {
						groups.add(elementName.substring(0, prefixLength + nsIdx));
					}
				}

				// Calclulate classes to search:
				List<String> filteredGroups = new LinkedList<String>();
				for (String group : groups) {
					List<String> filteredElements = new LinkedList<String>();
					for (String elementName : elementNames) {
						if (elementName.startsWith(group)) {
							int underscore = elementName.lastIndexOf('_');
							if (underscore < group.length()) {
								elementsToSearch.add(elementName);
							} else {
								if (elementName.charAt(group.length()) == '_') {
									filteredElements.add(elementName);
								}
							}
						}
					}
					if (filteredElements.size() == 1) {
						elementsToSearch.add(filteredElements.get(0));
						filteredGroups.add(group);
					}
				}
				for (String filteredGroup : filteredGroups) {
					groups.remove(filteredGroup);
				}
			}
		}

		int matchRule;
		if (prefix.length() == 0 && !exactName) {
			prefix = WILDCARD;
			matchRule = SearchPattern.R_PATTERN_MATCH;
			if (caseSensitive) {
				matchRule |= SearchPattern.R_CASE_SENSITIVE;
			}
		} else {
			if (caseSensitive) {
				matchRule = exactName ? SearchPattern.R_EXACT_MATCH : SearchPattern.R_PREFIX_MATCH;
				matchRule |= SearchPattern.R_CASE_SENSITIVE;
			} else {
				matchRule = exactName ? SearchPattern.R_EXACT_MATCH : SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_PREFIX_MATCH;
			}
		}

		if (groups.size() > 0) {
			if (elementsToSearch.size() > 0) {
				StringBuilder buf = new StringBuilder();
				int i = elementsToSearch.size();
				for (String elementName : elementsToSearch) {
					buf.append(elementName);
					if (--i > 0) {
						buf.append('|');
					}
				}
				pattern = SearchPattern.createPattern(buf.toString(), elementType, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_REGEXP_MATCH, toolkit);
			}
		} else {
			pattern = SearchPattern.createPattern(prefix, elementType, IDLTKSearchConstants.DECLARATIONS, matchRule, toolkit);
		}

		final Set<IModelElement> elements = new TreeSet<IModelElement>(new AlphabeticComparator(sourceModule));
		if (pattern != null) {
			try {
				if (elementType == IDLTKSearchConstants.TYPE) {
					final HandleFactory handleFactory = new HandleFactory();
					searchEngine.searchAllTypeNames(null, 0, prefix.toCharArray(), pattern.getMatchRule(), IDLTKSearchConstants.DECLARATIONS, scope, new TypeNameRequestor() {
						public void acceptType(int modifiers, char[] packageName, char[] simpleTypeName, char[][] enclosingTypeNames, String path) {
							Openable openable = handleFactory.createOpenable(path, scope);
							elements.add(new FakeType(openable, new String(simpleTypeName), modifiers));
						}
					}, IDLTKSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, null);
				} else {
					searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
						public void acceptSearchMatch(SearchMatch match) throws CoreException {

							IModelElement element = (IModelElement) match.getElement();
							// sometimes method reference is found instead of declaration (seems to be a bug in search engine):
							if (element instanceof SourceModule) {
								return;
							}
							IModelElement parent = element.getParent();
							// Global scope elements in PHP are those, which are not defined in class body,
							// or it is a variable, and its parent - source module
							if ((element instanceof IField && parent instanceof org.eclipse.dltk.core.ISourceModule) || (!(element instanceof IField) && !(parent instanceof IType))) {
								elements.add(element);
							}
						}
					}, null);
				}
			} catch (CoreException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}

		if (showGroupOptions) {
			for (String group : groups) {
				String fakeElementName = new StringBuilder(group).append("_*").toString();
				if (elementType == IDLTKSearchConstants.TYPE) {
					elements.add(new FakeGroupType((ModelElement) sourceModule, fakeElementName));
				} else if (elementType == IDLTKSearchConstants.METHOD) {
					elements.add(new FakeGroupMethod((ModelElement) sourceModule, fakeElementName));
				}
			}
		}
		IModelElement[] result = elements.toArray(new IModelElement[elements.size()]);
		return currentFileOnly ? result : PHPModelUtils.filterElements(sourceModule, result);
	}

	/**
	 * Filters model elements leaving only elements with same names from current file
	 * @param currentFile
	 * @param elements
	 * @return
	 */
	private static IModelElement[] filterOtherFilesElements(ISourceModule currentFile, IModelElement[] modelElements) {
		List<IModelElement> elements = new ArrayList<IModelElement>(modelElements.length);
		String lastName = null;
		for (IModelElement element : modelElements) {
			if (element.getElementName().equals(lastName)) {
				continue;
			}
			lastName = null;
			if (currentFile.equals(element.getOpenable())) {
				lastName = element.getElementName();
			}
			elements.add(element);
		}
		return elements.toArray(new IModelElement[elements.size()]);
	}

	/**
	 * Returns file global model elements by given prefix
	 */
	public static IModelElement[] getSourceModuleElements(ISourceModule sourceModule, String prefix, int elementType, int mask) {
		List<IModelElement> elements = new LinkedList<IModelElement>();
		try {
			switch (elementType) {
				case IDLTKSearchConstants.TYPE:
					IType[] types = sourceModule.getTypes();
					for (IType type : types) {
						String typeName = type.getElementName();
						if ((mask & EXACT_NAME) != 0) {
							if (typeName.equalsIgnoreCase(prefix)) {
								elements.add(type);
								break;
							}
						} else if (startsWithIgnoreCase(typeName, prefix)) {
							elements.add(type);
						}
					}
					break;
				case IDLTKSearchConstants.METHOD:
					IMethod[] methods = ((AbstractSourceModule) sourceModule).getMethods();
					for (IMethod method : methods) {
						String methodName = method.getElementName();
						if ((mask & EXACT_NAME) != 0) {
							if (methodName.equalsIgnoreCase(prefix)) {
								elements.add(method);
								break;
							}
						} else if (startsWithIgnoreCase(methodName, prefix)) {
							elements.add(method);
						}
					}
					break;
				case IDLTKSearchConstants.FIELD:
					IField[] fields = sourceModule.getFields();
					for (IField field : fields) {
						String fieldName = field.getElementName();
						if ((mask & EXACT_NAME) != 0) {
							if (fieldName.equals(prefix)) {
								elements.add(field);
								break;
							}
						} else if (fieldName.startsWith(prefix)) {
							elements.add(field);
						}
					}
					break;
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return elements.toArray(new IModelElement[elements.size()]);
	}

	/**
	 * This class not only used for sorting elements alphabetically, but it also gives
	 * priority to the elements declared in current file. 
	 */
	public static class AlphabeticComparator implements Comparator<IModelElement> {

		private ISourceModule currentFile;

		public AlphabeticComparator() {
		}

		public AlphabeticComparator(ISourceModule currentFile) {
			this.currentFile = currentFile;
		}

		public int compare(IModelElement o1, IModelElement o2) {
			if (o1 instanceof FakeGroupType) {
				return -1;
			}
			int r = o1.getElementName().compareTo(o2.getElementName());
			if (r == 0) {
				if (currentFile != null && currentFile.equals(o1.getOpenable())) {
					return -1;
				}
				if (o1 instanceof IMember) {
					IType t1 = ((IMember) o1).getDeclaringType();
					//					IType t2 = ((IMember)o2).getDeclaringType();
					if (t1 != null) {
						try {
							if (PHPFlags.isInterface(t1.getFlags())) {
								return -1;
							}
						} catch (Exception e) {
							if (DLTKCore.DEBUG_COMPLETION) {
								e.printStackTrace();
							}
						}
					}
				}
				return 1;
			}
			return r;
		}
	}
}
