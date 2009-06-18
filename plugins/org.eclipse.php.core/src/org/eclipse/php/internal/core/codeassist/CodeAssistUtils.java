/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.internal.core.AbstractSourceModule;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.ScriptProject;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.nodes.GlobalStatement;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.mixin.PHPMixinParser;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.*;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
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

	private static final String DOLLAR = "$"; //$NON-NLS-1$
	private static final String WILDCARD = "*"; //$NON-NLS-1$
	private static final String PAAMAYIM_NEKUDOTAIM = "::"; //$NON-NLS-1$
	protected static final String OBJECT_FUNCTIONS_TRIGGER = "->"; //$NON-NLS-1$
	private static final Pattern globalPattern = Pattern.compile("\\$GLOBALS[\\s]*\\[[\\s]*[\\'\\\"][\\w]+[\\'\\\"][\\s]*\\]"); //$NON-NLS-1$

	private static final IModelElement[] EMPTY = new IModelElement[0];
	private static final IType[] EMPTY_TYPES = new IType[0];

	public static boolean startsWithIgnoreCase(String word, String prefix) {
		return word.toLowerCase().startsWith(prefix.toLowerCase());
	}

	/**
	 * This method finds all ancestor methods that match the given prefix.
	 * @param type Type to find methods within
	 * @param prefix Method prefix
	 * @param mask Search mask
	 * @return
	 */
	public static IMethod[] getSuperClassMethods(IType type, String prefix, int mask) {
		return getSuperClassMethods(type, null, prefix, mask);
	}
	
	/**
	 * This method finds all ancestor methods that match the given prefix.
	 * @param type Type to find methods within
	 * @param hierarchy Cached type hierarchy
	 * @param prefix Method prefix
	 * @param mask Search mask
	 * @return
	 */
	public static IMethod[] getSuperClassMethods(IType type, ITypeHierarchy hierarchy, String prefix, int mask) {
		boolean exactName = (mask & EXACT_NAME) != 0;
		final Set<IMethod> methods = new TreeSet<IMethod>(new AlphabeticComparator());
		try {
			if (type.getSuperClasses() != null && type.getSuperClasses().length > 0) {
				if (hierarchy == null) {
					hierarchy = type.newSupertypeHierarchy(null);
				}
				IType[] allSuperclasses = hierarchy.getAllSuperclasses(type);
				for (IType superClass : allSuperclasses) {
					for (IMethod method : superClass.getMethods()) {
						String methodName = method.getElementName();
						if (exactName) {
							if (methodName.equalsIgnoreCase(prefix)) {
								methods.add(method);
								break;
							}
						} else if (startsWithIgnoreCase(methodName, prefix)) {
							methods.add(method);
						}
					}
				}
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * This method finds all class methods that match the given prefix.
	 * @param type Type to find methods within
	 * @param prefix Method prefix
	 * @param mask Search mask
	 * @return
	 */
	public static IMethod[] getTypeMethods(IType type, String prefix, int mask) {
		return getTypeMethods(type, null, prefix, mask);
	}
	
	/**
	 * This method finds all class methods that match the given prefix
	 * @param type Type to find methods within
	 * @param hierarchy Cached type hierarchy
	 * @param prefix Method prefix
	 * @param mask Search mask
	 * @return
	 */
	public static IMethod[] getTypeMethods(IType type, ITypeHierarchy hierarchy, String prefix, int mask) {
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
			PHPCorePlugin.log(e);
		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * This method finds all class fields that match the given prefix.
	 * @param type Type to search fields within
	 * @param prefix Field prefix
	 * @param mask Search mask
	 * @return
	 */
	public static IField[] getTypeFields(IType type, String prefix, int mask) {
		return getTypeFields(type, null, prefix, mask);
	}
	
	/**
	 * This method finds all class fields that match the given prefix
	 * @param type Type to search fields within
	 * @param hierarchy Cached type hierarchy
	 * @param prefix Field prefix
	 * @param mask Search mask
	 * @return
	 */
	public static IField[] getTypeFields(IType type, ITypeHierarchy hierarchy, String prefix, int mask) {
		
		boolean exactName = (mask & EXACT_NAME) != 0;
		boolean searchConstants = (mask & EXCLUDE_CONSTANTS) == 0;

		final Set<IField> fields = new TreeSet<IField>(new AlphabeticComparator());
		try {
			List<IType> searchTypes = new LinkedList<IType>();

			searchTypes.add(type);
			if (type.getSuperClasses() != null && type.getSuperClasses().length > 0) {
				if (hierarchy == null) {
					hierarchy = type.newSupertypeHierarchy(null);
				}
				IType[] allSuperclasses = hierarchy.getAllSuperclasses(type);
				searchTypes.addAll(Arrays.asList(allSuperclasses));
			}
			for (IType searchType : searchTypes) {
				IField[] typeFields = searchType.getFields();

				for (IField typeField : typeFields) {
					String elementName = typeField.getElementName();
					int flags = typeField.getFlags();
					if (PHPFlags.isConstant(flags)) {
						if (!searchConstants) {
							continue;
						}
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
						} else if (startsWithIgnoreCase(elementName, tmp)) {
							fields.add(typeField);
						}
					}
				}
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}
		return fields.toArray(new IField[fields.size()]);
	}

	/**
	 * Returns type of a class field defined by name.
	 * @param types
	 * @param propertyName
	 * @param offset
	 * @return
	 */
	public static IType[] getVariableType(IType[] types, String propertyName, int offset) {
		if (types != null) {
			for (IType type : types) {
				PHPClassType classType = PHPClassType.fromIType(type);
				//				IField[] fields = getTypeFields(type, propertyName, CASE_SENSITIVE | EXCLUDE_CONSTANTS);

				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(type.getSourceModule(), null);
				FileContext fileContext = new FileContext(type.getSourceModule(), moduleDeclaration, offset);
				TypeContext typeContext = new TypeContext(fileContext, classType);
				PHPTypeInferencer typeInferencer = new PHPTypeInferencer();

				//				Set<String> processedFields = new HashSet<String>();
				//				for (IField field : fields) {

				//					String variableName = field.getElementName();
				//					if (processedFields.contains(propertyName)) {
				//						continue;
				//					}
				//					processedFields.add(propertyName);

				if (!propertyName.startsWith(DOLLAR)) {
					propertyName = DOLLAR + propertyName;
				}
				PHPDocClassVariableGoal phpDocGoal = new PHPDocClassVariableGoal(typeContext, propertyName);
				IEvaluatedType evaluatedType = typeInferencer.evaluateTypePHPDoc(phpDocGoal, 3000);

				IType[] modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, fileContext, offset);
				if (modelElements != null) {
					return modelElements;
				}

				ClassVariableDeclarationGoal goal = new ClassVariableDeclarationGoal(typeContext, types, propertyName);
				evaluatedType = typeInferencer.evaluateType(goal);

				modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, fileContext, offset);
				if (modelElements != null) {
					return modelElements;
				}
				//				}
			}
		}
		return EMPTY_TYPES;
	}

	/**
	 * Returns type of a variable defined by name.
	 * @param sourceModule
	 * @param variableName
	 * @param position
	 * @return
	 */
	public static IType[] getVariableType(ISourceModule sourceModule, String variableName, int position) {
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		IContext context = ASTUtils.findContext(sourceModule, moduleDeclaration, position);
		if (context != null) {
			VariableReference varReference = new VariableReference(position, position + variableName.length(), variableName);
			ExpressionTypeGoal goal = new ExpressionTypeGoal(context, varReference);
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			IEvaluatedType evaluatedType = typeInferencer.evaluateType(goal);

			IType[] modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, (ISourceModuleContext) context, position);
			if (modelElements != null) {
				return modelElements;
			}
		}
		return EMPTY_TYPES;
	}

	/**
	 * Determines the return type of the given method element.
	 * @param method
	 * @param function
	 * @param offset
	 * @return
	 */
	public static IType[] getFunctionReturnType(IType[] types, String method, org.eclipse.dltk.core.ISourceModule sourceModule, int offset) {
		return getFunctionReturnType(types, method, USE_PHPDOC, sourceModule, offset);
	}

	/**
	 * Determines the return type of the given method element.
	 * @param method
	 * @param mask
	 * @param offset
	 * @return
	 */
	public static IType[] getFunctionReturnType(IType[] types, String method, int mask, org.eclipse.dltk.core.ISourceModule sourceModule, int offset) {
		PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		IContext context = ASTUtils.findContext(sourceModule, moduleDeclaration, offset);

		IEvaluatedType evaluatedType;
		IType[] modelElements;
		boolean usePhpDoc = (mask & USE_PHPDOC) != 0;
		if (usePhpDoc) {
			PHPDocMethodReturnTypeGoal phpDocGoal = new PHPDocMethodReturnTypeGoal(context, types, method);
			evaluatedType = typeInferencer.evaluateTypePHPDoc(phpDocGoal);

			modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, (ISourceModuleContext) context, offset);
			if (modelElements != null) {
				return modelElements;
			}
		}

		MethodElementReturnTypeGoal methodGoal = new MethodElementReturnTypeGoal(context, types, method);
		evaluatedType = typeInferencer.evaluateType(methodGoal);
		modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, (ISourceModuleContext) context, offset);
		if (modelElements != null) {
			return modelElements;
		}
		return EMPTY_TYPES;
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

		IType type = PHPModelUtils.getCurrentType(sourceModule, offset);
		IMethod method = PHPModelUtils.getCurrentMethod(sourceModule, offset);

		if (type != null && method != null) {
			try {
				int flags = type.getFlags();
				if (!PHPFlags.isAbstract(flags) && !PHPFlags.isInterface(flags) && !PHPFlags.isInterface(flags)) {
					return type;
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}

		return null;
	}

	/**
	 * This method finds types for the receiver in the statement text.
	 * @param sourceModule
	 * @param statementText
	 * @param endPosition
	 * @param offset
	 * @return
	 */
	public static IType[] getTypesFor(ISourceModule sourceModule, TextSequence statementText, int endPosition, int offset) {
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
			return innerGetClassName(sourceModule, statementText, propertyEndPosition, isClassTriger, offset);
		}

		int propertyStartPosition = PHPTextSequenceUtilities.readForwardSpaces(statementText, lastObjectOperator + triggerText.length());
		String propertyName = statementText.subSequence(propertyStartPosition, propertyEndPosition).toString();
		IType[] types = getTypesFor(sourceModule, statementText, propertyStartPosition, offset);

		int bracketIndex = propertyName.indexOf('(');

		if (bracketIndex == -1) {
			// meaning its a class variable and not a function
			return getVariableType(types, propertyName, offset);
		}

		String functionName = propertyName.substring(0, bracketIndex).trim();
		Set<IType> result = new LinkedHashSet<IType>();
		IType[] returnTypes = getFunctionReturnType(types, functionName, sourceModule, offset);
		if (returnTypes != null) {
			result.addAll(Arrays.asList(returnTypes));
		}
		return result.toArray(new IType[result.size()]);
	}

	/**
	 * Getting an instance and finding its type.
	 */
	private static IType[] innerGetClassName(ISourceModule sourceModule, TextSequence statementText, int propertyEndPosition, boolean isClassTriger, int offset) {

		PHPVersion phpVersion = ProjectOptions.getPhpVersion(sourceModule.getScriptProject().getProject());

		int classNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(phpVersion, statementText, propertyEndPosition, true);
		String className = statementText.subSequence(classNameStart, propertyEndPosition).toString();
		if (isClassTriger) {
			if ("self".equals(className) || "parent".equals(className) || (phpVersion.isGreaterThan(PHPVersion.PHP5) && "static".equals(className))) {
				IType classData = PHPModelUtils.getCurrentType(sourceModule, offset - className.length() - 2); //the offset before "self::", "parent::" or "static::"
				if (classData != null) {
					return new IType[] { classData };
				}
			}
			if (className.length() > 0) {
				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
				FileContext context = new FileContext(sourceModule, moduleDeclaration, offset);
				IEvaluatedType type = PHPClassType.fromTypeName(className, sourceModule, offset);
				IType[] modelElements = PHPTypeInferenceUtils.getModelElements(type, context, offset);
				if (modelElements != null) {
					return modelElements;
				}
				return EMPTY_TYPES;
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
			return getVariableType(sourceModule, className, statementStart);
		}
		// if its function call calc the return type.
		if (statementText.charAt(propertyEndPosition - 1) == ')') {
			int functionNameEnd = getFunctionNameEndOffset(statementText, propertyEndPosition - 1);
			int functionNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(phpVersion, statementText, functionNameEnd, false);

			String functionName = statementText.subSequence(functionNameStart, functionNameEnd).toString();
			// if its a non class function
			Set<IType> returnTypes = new LinkedHashSet<IType>();
			IType[] types = getFunctionReturnType(null, functionName, sourceModule, offset);
			if (types != null) {
				returnTypes.addAll(Arrays.asList(types));
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
				classMethod = PHPModelUtils.getTypeHierarchyMethod(type, functionName, null);
				if (classMethod != null) {
					return true;
				}
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
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
				PHPCorePlugin.log(e);
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
		return getGlobalElements(sourceModule, prefix, IDLTKSearchConstants.FIELD, mask);
	}

	/**
	 * This method searches for all fields that where declared in the specified method
	 * 
	 * @param method Method to look at
	 * @param prefix Field name
	 * @param mask
	 */
	public static IModelElement[] getMethodFields(final IMethod method, String prefix, int mask) {

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

		final Set<String> processedVars = new HashSet<String>();

		SearchPattern pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, matchRule, toolkit);
		final Set<IModelElement> elements = new TreeSet<IModelElement>(new AlphabeticComparator());
		try {
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
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
			PHPCorePlugin.log(e);
		}

		// collect global variables
		ModuleDeclaration rootNode = SourceParserUtil.getModuleDeclaration(method.getSourceModule());
		try {
			MethodDeclaration methodDeclaration = PHPModelUtils.getNodeByMethod(rootNode, method);
			final String varPrefix = prefix;
			methodDeclaration.traverse(new ASTVisitor() {
				public boolean visit(Statement s) throws Exception {
					if (s instanceof GlobalStatement) {
						GlobalStatement globalStatement = (GlobalStatement) s;
						for (Expression e : globalStatement.getVariables()) {
							if (e instanceof VariableReference) {
								VariableReference varReference = (VariableReference) e;
								String varName = varReference.getName();
								if (varName.startsWith(varPrefix) && !processedVars.contains(varName)) {
									elements.add(new FakeField((ModelElement) method, varName, e.sourceStart(), e.sourceEnd() - e.sourceStart()));
									processedVars.add(varName);
								}
							}
						}
					}
					return super.visit(s);
				}
			});
		} catch (Exception e) {
			PHPCorePlugin.log(e);
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
			IModelElement[] variables = mixinModel.getVariable(prefix + WILDCARD, scope);
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
	private static IModelElement[] getGlobalElements(final ISourceModule sourceModule, final IDLTKSearchScope scope, String prefix, final int elementType, final int mask) {

		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();
		SearchEngine searchEngine = new SearchEngine();

		boolean exactName = (mask & EXACT_NAME) != 0;
		boolean caseSensitive = (mask & CASE_SENSITIVE) != 0;
		boolean currentFileOnly = (mask & ONLY_CURRENT_FILE) != 0;

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

		boolean showGroupOptions = Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID, PHPCoreConstants.CODEASSIST_GROUP_OPTIONS, false, null);
		if (!exactName && !currentFileOnly && showGroupOptions && !prefix.startsWith("$") && (elementType == IDLTKSearchConstants.TYPE || elementType == IDLTKSearchConstants.METHOD)) {
			return getGroupOptions(sourceModule, prefix, elementType, mask, matchRule, scope);
		}

		SearchPattern pattern = SearchPattern.createPattern(prefix, elementType, IDLTKSearchConstants.DECLARATIONS, matchRule, toolkit);

		final Set<IModelElement> elements = new TreeSet<IModelElement>(new AlphabeticComparator(sourceModule));
		if (pattern != null) {
			try {
				if (elementType == IDLTKSearchConstants.TYPE) {
					elements.addAll(Arrays.asList(PHPModelUtils.getTypes(prefix, pattern.getMatchRule(), scope)));
				} else if (elementType == IDLTKSearchConstants.METHOD) {
					elements.addAll(Arrays.asList(PHPModelUtils.getFunctions(prefix, pattern.getMatchRule(), scope)));
				} else {
					if ((mask & EXCLUDE_CONSTANTS) == 0 && (mask & ONLY_CURRENT_FILE) == 0 && !prefix.startsWith(DOLLAR)) {
						// workaround for fast searching global constants:
						MixinModel mixinModel = PHPMixinModel.getInstance(sourceModule.getScriptProject()).getRawModel();
						if (!prefix.endsWith(WILDCARD)) {
							prefix += WILDCARD;
						}
						String[] keys = mixinModel.findKeys(MixinModel.SEPARATOR + prefix + PHPMixinParser.CONSTANT_SUFFIX);
						for (String key : keys) {
							String name = key.substring(1, key.indexOf(PHPMixinParser.CONSTANT_SUFFIX));
							elements.add(new FakeField((ModelElement) sourceModule, name, Modifiers.AccConstant));
						}
					} else {
						searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
							public void acceptSearchMatch(SearchMatch match) throws CoreException {

								IModelElement element = (IModelElement) match.getElement();
								// sometimes method reference is found instead of declaration (seems to be a bug in search engine):
								if (!(element instanceof IField)) {
									return;
								}
								try {
									int flags = ((IField) element).getFlags();
									if (((mask & EXCLUDE_CONSTANTS) != 0 && PHPFlags.isConstant(flags)) || ((mask & EXCLUDE_VARIABLES) != 0 && !PHPFlags.isConstant(flags))) {
										return;
									}
								} catch (ModelException e) {
									PHPCorePlugin.log(e);
								}

								IModelElement parent = element.getParent();
								if (parent instanceof IType && !PHPFlags.isNamespace(((IType) parent).getFlags())) {
									return; // The element is a class/interface member
								}
								if (parent instanceof IMethod) {
									return; // The element is a local function variable
								}
								elements.add(element);
							}
						}, null);
					}
				}
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
			}
		}

		if (!currentFileOnly) {
			Collection<IModelElement> result = PHPModelUtils.filterElements(sourceModule, elements);
			return (IModelElement[]) result.toArray(new IModelElement[result.size()]);
		}
		return elements.toArray(new IModelElement[elements.size()]);
	}

	private static IModelElement[] getGroupOptions(ISourceModule sourceModule, String prefix, int elementType, int mask, int matchRule, IDLTKSearchScope scope) {
		final Set<IModelElement> elements = new TreeSet<IModelElement>(new AlphabeticComparator());
		Set<String> groups = new HashSet<String>();

		// Build the mixin request key:
		if (elementType == IDLTKSearchConstants.TYPE) {
			IType[] classesAndInterfaces = PHPModelUtils.getClassesAndInterfaces(prefix, matchRule, scope);
			try {
				for (IType type : classesAndInterfaces) {
					int flags = type.getFlags();
					if ((mask & EXCLUDE_CLASSES) == 0 && PHPFlags.isClass(flags)) {
						elements.add(type);
					}
					if ((mask & EXCLUDE_INTERFACES) == 0 && PHPFlags.isInterface(flags)) {
						elements.add(type);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		} else {
			elements.addAll(Arrays.asList(PHPModelUtils.getFunctions(prefix, matchRule, scope)));
		}

		// Calculate minimal namespaces:
		int prefixLength = prefix.length();
		for (IModelElement element : elements) {
			String elementName = element.getElementName();
			int nsIdx = elementName.substring(prefixLength).indexOf('_');
			if ((nsIdx >= 0 && prefixLength > 0 || prefixLength == 0 && nsIdx > 0) && nsIdx < elementName.length() - 1) {
				groups.add(elementName.substring(0, prefixLength + nsIdx));
			}
		}

		Set<IModelElement> outOfGroupsElements = new HashSet<IModelElement>();
		outOfGroupsElements.addAll(elements);

		// Calclulate classes to search:
		List<String> filteredGroups = new LinkedList<String>();
		for (String group : groups) {
			List<String> filteredElements = new LinkedList<String>();
			for (IModelElement element : elements) {
				String elementName = element.getElementName();
				if (elementName.startsWith(group)) {
					int underscore = elementName.lastIndexOf('_');
					if (underscore == group.length()) {
						filteredElements.add(elementName);
					}
					outOfGroupsElements.remove(element);
				}
			}
			if (filteredElements.size() == 1) {
				filteredGroups.add(group);
			}
		}
		for (String filteredGroup : filteredGroups) {
			groups.remove(filteredGroup);
		}

		if (groups.size() > 0) {
			List<IModelElement> groupElements = new LinkedList<IModelElement>();
			for (String group : groups) {
				String fakeElementName = new StringBuilder(group).append("_*").toString();
				if (elementType == IDLTKSearchConstants.TYPE) {
					groupElements.add(new FakeGroupType((ModelElement) sourceModule, fakeElementName));
				} else if (elementType == IDLTKSearchConstants.METHOD) {
					groupElements.add(new FakeGroupMethod((ModelElement) sourceModule, fakeElementName));
				}
			}

			// Add all elements that don't fall into any group
			groupElements.addAll(outOfGroupsElements);
			return (IModelElement[]) groupElements.toArray(new IModelElement[groupElements.size()]);
		}
		return (IModelElement[]) elements.toArray(new IModelElement[elements.size()]);
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
			PHPCorePlugin.log(e);
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
							PHPCorePlugin.log(e);
						}
					}
				}
				return 1;
			}
			return r;
		}
	}
}
