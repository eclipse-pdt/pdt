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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferencer;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;
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
	private static final IModelElement[] EMPTY = new IModelElement[0];

	public static boolean startsWithIgnoreCase(String word, String prefix) {
		return word.toLowerCase().startsWith(prefix.toLowerCase());
	}

	/**
	 * This method finds all ancestor methods that match the given prefix.
	 * @param type
	 * @param prefix
	 * @param exactName
	 * @return
	 */
	public static IMethod[] getSuperClassMethods(IType type, String prefix, boolean exactName) {
		final Set<IMethod> methods = new LinkedHashSet<IMethod>();
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
	 * @param exactName
	 * @return
	 */
	public static IMethod[] getClassMethods(IType type, String prefix, boolean exactName) {
		final Set<IMethod> methods = new LinkedHashSet<IMethod>();
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
	 * @param exactName
	 * @param searchConstants
	 * @return
	 */
	public static IField[] getClassFields(IType type, String prefix, boolean exactName, boolean searchConstants) {
		final Set<IField> fields = new LinkedHashSet<IField>();
		try {
			List<IType> searchTypes = new LinkedList<IType>();
			searchTypes.add(type);
			
			if (prefix.length() == 0) {
				ITypeHierarchy superTypeHierarchy = type.newSupertypeHierarchy(null);
				IType[] allSuperclasses = superTypeHierarchy.getAllSuperclasses(type);
				searchTypes.addAll(Arrays.asList(allSuperclasses));
			} else {
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

				if (type.getSuperClasses() != null) {
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
					pattern = SearchPattern.createPattern(DOLLAR + prefix, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, matchRule, PHPLanguageToolkit.getDefault());

					searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
						public void acceptSearchMatch(SearchMatch match) throws CoreException {
							fields.add((IField) match.getElement());
						}
					}, null);
				}
			}
			
			for (IType searchType : searchTypes) {
				IField[] typeFields = searchType.getFields();
				for (IField typeField : typeFields) {

					String elementName = typeField.getElementName();

					int flags = typeField.getFlags();
					if ((flags & Modifiers.AccConstant) != 0) {
						if (exactName && elementName.equals(prefix) || elementName.startsWith(prefix)) {
							fields.add(typeField);
						}
					} else { // variable
						String tmp = prefix;
						if (!tmp.startsWith(DOLLAR)) {
							tmp = DOLLAR + tmp;
						}
						if (exactName && elementName.equals(tmp) || elementName.startsWith(tmp)) {
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
	 * @param determineObjectFromOtherFile
	 * @return
	 */
	public static IType[] getVariableType(IType[] types, String propertyName, int offset, int line, boolean determineObjectFromOtherFile) {
		for (IType type : types) {
			
			PHPClassType classType = new PHPClassType(type.getElementName());

			IField[] fields = getClassFields(type, propertyName, true, false);

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
				
				IModelElement[] modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, sourceModuleContext, !determineObjectFromOtherFile);
				if (modelElements != null) {
					return modelElementsToTypes(modelElements);
				}

				ClassVariableDeclarationGoal goal = new ClassVariableDeclarationGoal(sourceModuleContext, types, variableName);
				evaluatedType = typeInferencer.evaluateType(goal);

				modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, sourceModuleContext, !determineObjectFromOtherFile);
				if (modelElements != null) {
					return modelElementsToTypes(modelElements);
				}
			}
		}
		return null;
	}

	/**
	 * Returns type of a variable defined by name.
	 * @param sourceModule
	 * @param variableName
	 * @param position
	 * @param line
	 * @param determineObjectFromOtherFile
	 * @return
	 */
	public static IType[] getVariableType(ISourceModule sourceModule, String variableName, int position, int line, boolean determineObjectFromOtherFile) {
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		IContext context = ASTUtils.findContext(sourceModule, moduleDeclaration, position);
		if (context != null) {
			VariableReference varReference = new VariableReference(position, position + variableName.length(), variableName);
			ExpressionTypeGoal goal = new ExpressionTypeGoal(context, varReference);
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			IEvaluatedType evaluatedType = typeInferencer.evaluateType(goal);

			IModelElement[] modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, (ISourceModuleContext) context, !determineObjectFromOtherFile);
			if (modelElements != null) {
				return modelElementsToTypes(modelElements);
			}
		}
		return null;
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
	 * @param determineObjectFromOtherFile
	 * @return
	 */
	public static IType[] getFunctionReturnType(IType type, String functionName, boolean determineObjectFromOtherFile) {
		IMethod[] classMethod = getClassMethods(type, functionName, true);
		if (classMethod.length > 0) {
			return getFunctionReturnType(classMethod[0], determineObjectFromOtherFile);
		}
		return null;
	}

	/**
	 * Determines the return type of the given method element.
	 * @param method
	 * @param determineObjectFromOtherFile
	 * @return
	 */
	public static IType[] getFunctionReturnType(IMethod method, boolean determineObjectFromOtherFile) {
		PHPTypeInferencer typeInferencer = new PHPTypeInferencer();

		IEvaluatedType classType = null;
		if (method.getDeclaringType() != null) {
			classType = new PHPClassType(method.getDeclaringType().getElementName());
		}
		org.eclipse.dltk.core.ISourceModule sourceModule = method.getSourceModule();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		BasicContext sourceModuleContext = new BasicContext(sourceModule, moduleDeclaration);

		InstanceContext instanceContext = new InstanceContext(sourceModuleContext, classType);
		PHPDocMethodReturnTypeGoal phpDocGoal = new PHPDocMethodReturnTypeGoal(instanceContext, method.getElementName());
		IEvaluatedType evaluatedType = typeInferencer.evaluateTypePHPDoc(phpDocGoal, 3000);

		IModelElement[] modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, sourceModuleContext, !determineObjectFromOtherFile);
		if (modelElements != null) {
			return modelElementsToTypes(modelElements);
		}

		MethodElementReturnTypeGoal methodGoal = new MethodElementReturnTypeGoal(instanceContext, method);
		evaluatedType = typeInferencer.evaluateType(methodGoal);
		modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, sourceModuleContext, !determineObjectFromOtherFile);
		if (modelElements != null) {
			return modelElementsToTypes(modelElements);
		}
		return null;
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
			int modifiers;
			try {
				modifiers = type.getFlags();
				if ((modifiers & Modifiers.AccAbstract) == 0 && (modifiers & Modifiers.AccInterface) == 0) {
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
	 * @return
	 */
	public static boolean isFunctionCall(String functionName) {
		IModelElement[] functions = PHPMixinModel.getInstance().getFunction(functionName);
		return functions.length > 0;
	}

	/**
	 * Retrieves all classes from the global scope by the given prefix.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix
	 * @param exactName
	 * @return
	 */
	public static IType[] getOnlyClasses(ISourceModule sourceModule, String prefix, boolean exactName) {
		IModelElement[] classes = getGlobalClasses(sourceModule, prefix, exactName);
		List<IType> onlyClasses = new LinkedList<IType>();
		for (IModelElement c : classes) {
			IType type = (IType) c;
			try {
				if ((type.getFlags() & Modifiers.AccInterface) == 0) {
					onlyClasses.add(type);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
		return onlyClasses.toArray(new IType[onlyClasses.size()]);
	}

	/**
	 * Retrieves all interfaces from the global scope by the given prefix.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix
	 * @param exactName
	 * @return
	 */
	public static IType[] getOnlyInterfaces(ISourceModule sourceModule, String prefix, boolean exactName) {
		IModelElement[] classes = getGlobalClasses(sourceModule, prefix, exactName);
		List<IType> onlyInterfaces = new LinkedList<IType>();
		for (IModelElement i : classes) {
			IType type = (IType) i;
			try {
				if ((type.getFlags() & Modifiers.AccInterface) != 0) {
					onlyInterfaces.add(type);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
		return onlyInterfaces.toArray(new IType[onlyInterfaces.size()]);
	}

	/**
	 * This method finds types for the receiver in the statement text.
	 * @param sourceModule
	 * @param statementText
	 * @param endPosition
	 * @param offset
	 * @param line
	 * @param determineObjectFromOtherFile
	 * @return
	 */
	public static IType[] getTypesFor(ISourceModule sourceModule, TextSequence statementText, int endPosition, int offset, int line, boolean determineObjectFromOtherFile) {
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, endPosition); // read whitespace

		boolean isClassTriger = false;

		if (endPosition < 2) {
			return null;
		}
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
			return getVariableType(types, propertyName, offset, line, determineObjectFromOtherFile);
		}

		String functionName = propertyName.substring(0, bracketIndex).trim();
		Set<IType> result = new LinkedHashSet<IType>();
		for (IType type : types) {
			IType[] returnTypes = getFunctionReturnType(type, functionName, determineObjectFromOtherFile);
			if (returnTypes != null) {
				result.addAll(Arrays.asList(returnTypes));
			}
		}
		return result.toArray(new IType[result.size()]);
	}

	/**
	 * Getting an instance and finding its type.
	 */
	private static IType[] innerGetClassName(ISourceModule sourceModule, TextSequence statementText, int propertyEndPosition, boolean isClassTriger, int offset, int line, boolean determineObjectFromOtherFile) {

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
				return modelElementsToTypes(PHPTypeInferenceUtils.getModelElements(type, context, !determineObjectFromOtherFile));
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
			return getVariableType(sourceModule, className, statementStart, line, determineObjectFromOtherFile);
		}
		// if its function call calc the return type.
		if (statementText.charAt(propertyEndPosition - 1) == ')') {
			int functionNameEnd = getFunctionNameEndOffset(statementText, propertyEndPosition - 1);
			int functionNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, functionNameEnd, false);

			String functionName = statementText.subSequence(functionNameStart, functionNameEnd).toString();
			IType classData = getContainerClassData(sourceModule, offset);
			if (classData != null) { //if its a clss function
				return getFunctionReturnType(classData, functionName, determineObjectFromOtherFile);
			}

			// if its a non class function
			Set<IType> returnTypes = new LinkedHashSet<IType>();
			IModelElement[] functions = getGlobalMethods(sourceModule, functionName, true);
			for (IModelElement function : functions) {
				IType[] types = getFunctionReturnType((IMethod) function, determineObjectFromOtherFile);
				if (types != null) {
					returnTypes.addAll(Arrays.asList(types));
				}
			}
			return returnTypes.toArray(new IType[returnTypes.size()]);
		}
		return null;
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
	 * @param exactName Whether the prefix is an exact name of a class
	 */
	public static IModelElement[] getGlobalClasses(ISourceModule sourceModule, String prefix, boolean exactName) {
		return getGlobalElements(sourceModule, prefix, exactName, IDLTKSearchConstants.TYPE);
	}

	/**
	 * This method searches for all methods in the project scope that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Field name
	 * @param exactName Whether the prefix is an exact name of a class
	 */
	public static IModelElement[] getGlobalMethods(ISourceModule sourceModule, String prefix, boolean exactName) {
		return getGlobalElements(sourceModule, prefix, exactName, IDLTKSearchConstants.METHOD);
	}

	/**
	 * This method searches for all fields in the project scope that match the given prefix.
	 * By default variables is looked only in current file. 
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Field name
	 * @param exactName Whether the prefix is an exact name of a class
	 */
	public static IModelElement[] getGlobalFields(ISourceModule sourceModule, String prefix, boolean exactName) {
		return getGlobalFields(sourceModule, prefix, exactName, true);
	}
	
	/**
	 * This method searches for all fields in the project scope that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Field name
	 * @param exactName Whether the prefix is an exact name of a class
	 * @param currentFileOnly Whether to search variables only in current file
	 */
	public static IModelElement[] getGlobalFields(ISourceModule sourceModule, String prefix, boolean exactName, boolean currentFileOnly) {
		return getGlobalElements(sourceModule, prefix, exactName, IDLTKSearchConstants.FIELD, currentFileOnly);
	}
	
	/**
	 * Return workspace or method fields depending on current position: whether we are inside method or in global scope.
	 * @param sourceModule
	 * @param offset
	 * @param prefix
	 * @param exactName
	 * @return
	 */
	public static IModelElement[] getGlobalOrMethodFields(ISourceModule sourceModule, int offset, String prefix, boolean exactName) {
		try {
			IModelElement enclosingElement = sourceModule.getElementAt(offset);
			if (enclosingElement instanceof IMethod) {
				IMethod method = (IMethod) enclosingElement;
				return getMethodFields(method, prefix, exactName);
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return getGlobalFields(sourceModule, prefix, exactName);
	}
	
	/**
	 * This method searches for all fields that where declared in the specified method
	 * 
	 * @param method Method to look at
	 * @param prefix Field name
	 * @param exactName Whether the prefix is an exact name of a class
	 */
	public static IModelElement[] getMethodFields(IMethod method, String prefix, boolean exactName) {
		
		SearchEngine searchEngine = new SearchEngine();
		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(new IModelElement[] { method }, toolkit);

		int matchRule;
		if (prefix.length() == 0 && !exactName) {
			prefix = WILDCARD;
			matchRule = SearchPattern.R_PATTERN_MATCH;
		} else {
			matchRule = exactName ? SearchPattern.R_EXACT_MATCH : SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_PREFIX_MATCH;
		}

		SearchPattern pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, matchRule, toolkit);

		final List<IModelElement> elements = new LinkedList<IModelElement>();
		try {
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					IModelElement element = (IModelElement) match.getElement();
					elements.add(element);
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
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Element name or prefix
	 * @param exactName Whether the prefix is an exact name of the element
	 * @param elementType Element type from {@link IDLTKSearchConstants}
	 * @return
	 */
	private static IModelElement[] getGlobalElements(ISourceModule sourceModule, String prefix, boolean exactName, int elementType) {
		return getGlobalElements(sourceModule, prefix, exactName, elementType, false);
	}
	
	/**
	 * This method searches in the project scope for all elements of specified type that match the given prefix.
	 * If currentFileOnly parameter is <code>true</code>, the search scope will contain only the source module.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Element name or prefix
	 * @param exactName Whether the prefix is an exact name of the element
	 * @param elementType Element type from {@link IDLTKSearchConstants}
	 * @param currentFileOnly Whether to search elements in current file only
	 * @return
	 */
	private static IModelElement[] getGlobalElements(ISourceModule sourceModule, String prefix, boolean exactName, int elementType, boolean currentFileOnly) {
		
		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();
		
		IDLTKSearchScope scope;
		if (currentFileOnly) {
			scope = SearchEngine.createSearchScope(sourceModule);
		} else {
			IScriptProject scriptProject = sourceModule.getScriptProject();
			if (scriptProject != null) {
				scope = SearchEngine.createSearchScope(scriptProject);
			} else {
				scope = SearchEngine.createWorkspaceScope(toolkit);
			}
		} 
		
		if (!currentFileOnly && prefix.startsWith("$")) { //$NON-NLS-1$
			// search variables using mixin model:
			IModelElement[] variables = PHPMixinModel.getInstance().getVariable(prefix + WILDCARD, null, null, scope);
			return variables == null ? EMPTY : variables;
		}
		
		return getGlobalElements(scope, prefix, exactName, elementType);
	}
	
	/**
	 * This method searches in the project scope for all elements of specified type that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param scope Search scope
	 * @param prefix Element name or prefix
	 * @param exactName Whether the prefix is an exact name of the element
	 * @param elementType Element type from {@link IDLTKSearchConstants}
	 * @return
	 */
	private static IModelElement[] getGlobalElements(IDLTKSearchScope scope, String prefix, boolean exactName, int elementType) {
		
		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();
		
		int matchRule;
		if (prefix.length() == 0 && !exactName) {
			prefix = WILDCARD;
			matchRule = SearchPattern.R_PATTERN_MATCH;
		} else {
			matchRule = exactName ? SearchPattern.R_EXACT_MATCH : SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_PREFIX_MATCH;
		}
		
		SearchEngine searchEngine = new SearchEngine();
		SearchPattern pattern = SearchPattern.createPattern(prefix, elementType, IDLTKSearchConstants.DECLARATIONS, matchRule, toolkit);

		final List<IModelElement> elements = new LinkedList<IModelElement>();
		try {
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
					if ((element instanceof IField && parent instanceof org.eclipse.dltk.core.ISourceModule)
							|| (!(element instanceof IField) && !(parent instanceof IType))) {
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
}
