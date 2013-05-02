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
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayVariableReference;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
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
 * This is a common utility used by completion and selection engines for PHP
 * elements retrieval.
 * 
 * @author michael
 */
public class CodeAssistUtils {

	/**
	 * Whether to use PHPDoc in type inference
	 */
	public static final int USE_PHPDOC = 1 << 5;

	private static final String DOLLAR = "$"; //$NON-NLS-1$
	private static final String PAAMAYIM_NEKUDOTAIM = "::"; //$NON-NLS-1$
	protected static final String OBJECT_FUNCTIONS_TRIGGER = "->"; //$NON-NLS-1$
	private static final Pattern globalPattern = Pattern
			.compile("\\$GLOBALS[\\s]*\\[[\\s]*[\\'\\\"][\\w]+[\\'\\\"][\\s]*\\]"); //$NON-NLS-1$

	private static final IType[] EMPTY_TYPES = new IType[0];

	public static boolean startsWithIgnoreCase(String word, String prefix) {
		return word.toLowerCase().startsWith(prefix.toLowerCase());
	}

	/**
	 * Returns type of a class field defined by name.
	 * 
	 * @param types
	 * @param propertyName
	 * @param offset
	 * @return
	 */
	public static IType[] getVariableType(IType[] types, String propertyName,
			int offset) {
		if (types != null) {
			for (IType type : types) {
				PHPClassType classType = PHPClassType.fromIType(type);

				ModuleDeclaration moduleDeclaration = SourceParserUtil
						.getModuleDeclaration(type.getSourceModule(), null);
				FileContext fileContext = new FileContext(
						type.getSourceModule(), moduleDeclaration, offset);
				TypeContext typeContext = new TypeContext(fileContext,
						classType);
				PHPTypeInferencer typeInferencer = new PHPTypeInferencer();

				if (!propertyName.startsWith(DOLLAR)) {
					propertyName = DOLLAR + propertyName;
				}
				PHPDocClassVariableGoal phpDocGoal = new PHPDocClassVariableGoal(
						typeContext, propertyName);
				IEvaluatedType evaluatedType = typeInferencer
						.evaluateTypePHPDoc(phpDocGoal, 3000);

				IType[] modelElements = PHPTypeInferenceUtils.getModelElements(
						evaluatedType, fileContext, offset);
				if (modelElements != null) {
					return modelElements;
				}

				ClassVariableDeclarationGoal goal = new ClassVariableDeclarationGoal(
						typeContext, types, propertyName);
				evaluatedType = typeInferencer.evaluateType(goal);

				modelElements = PHPTypeInferenceUtils.getModelElements(
						evaluatedType, fileContext, offset);
				if (modelElements != null) {
					return modelElements;
				}
			}
		}
		return EMPTY_TYPES;
	}

	/**
	 * Returns type of a variable defined by name.
	 * 
	 * @param sourceModule
	 * @param variableName
	 * @param position
	 * @return
	 */
	public static IType[] getArrayVariableType(ISourceModule sourceModule,
			String variableName, int position) {
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);
		IContext context = ASTUtils.findContext(sourceModule,
				moduleDeclaration, position);
		if (context != null) {
			VariableReference varReference = getVariableReference(variableName,
					position);
			ExpressionTypeGoal goal = new ExpressionTypeGoal(context,
					varReference);
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			IEvaluatedType evaluatedType = typeInferencer.evaluateType(goal);
			if (evaluatedType instanceof MultiTypeType
					|| evaluatedType instanceof AmbiguousType) {
				return getTypes(position, context, evaluatedType);
			}

			IType[] modelElements = PHPTypeInferenceUtils.getModelElements(
					evaluatedType, (ISourceModuleContext) context, position);
			if (modelElements != null) {
				return modelElements;
			}
		}
		return EMPTY_TYPES;
	}

	private static IType[] getTypes(int position, IContext context,
			IEvaluatedType evaluatedType) {
		List<IType> tmpList = new LinkedList<IType>();
		List<IEvaluatedType> possibleTypes = new LinkedList<IEvaluatedType>();
		if (evaluatedType instanceof MultiTypeType) {
			possibleTypes = ((MultiTypeType) evaluatedType).getTypes();
		} else if (evaluatedType instanceof AmbiguousType) {
			possibleTypes.addAll(Arrays.asList(((AmbiguousType) evaluatedType)
					.getPossibleTypes()));
		} else {
			possibleTypes.add(evaluatedType);
		}
		for (IEvaluatedType possibleType : possibleTypes) {
			IType[] tmpArray;
			if (possibleType instanceof MultiTypeType
					|| possibleType instanceof AmbiguousType) {
				tmpArray = getTypes(position, context, possibleType);
			} else {
				tmpArray = PHPTypeInferenceUtils.getModelElements(possibleType,
						(ISourceModuleContext) context, position,
						(IModelAccessCache) null);
			}
			if (tmpArray != null) {
				tmpList.addAll(Arrays.asList(tmpArray));
			}
		}
		// the elements are filtered already
		return tmpList.toArray(new IType[tmpList.size()]);
	}

	/**
	 * Returns type of a variable defined by name.
	 * 
	 * @param sourceModule
	 * @param variableName
	 * @param position
	 * @return
	 */
	public static IType[] getVariableType(ISourceModule sourceModule,
			String variableName, int position) {
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);
		IContext context = ASTUtils.findContext(sourceModule,
				moduleDeclaration, position);
		if (context != null) {
			VariableReference varReference = getVariableReference(variableName,
					position);
			ExpressionTypeGoal goal = new ExpressionTypeGoal(context,
					varReference);
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			IEvaluatedType evaluatedType = typeInferencer.evaluateType(goal);

			IType[] modelElements = getTypes(position, context, evaluatedType);
			// IType[] modelElements = PHPTypeInferenceUtils.getModelElements(
			// evaluatedType, (ISourceModuleContext) context, position);
			if (modelElements != null) {
				return modelElements;
			}
		}
		return EMPTY_TYPES;
	}

	private static VariableReference getVariableReference(String variableName,
			int position) {
		String start = ""; //$NON-NLS-1$
		int arrayType = 0;
		if (variableName.endsWith("]")) { //$NON-NLS-1$
			start = "["; //$NON-NLS-1$
			arrayType = ArrayVariableReference.VARIABLE_ARRAY;
		} else if (variableName.endsWith("}")) { //$NON-NLS-1$
			start = "{"; //$NON-NLS-1$
			arrayType = ArrayVariableReference.VARIABLE_HASHTABLE;
		}
		if (!"".equals(start)) { //$NON-NLS-1$
			int startIndex = variableName.indexOf(start);
			String name = variableName.substring(0, startIndex);
			return new ArrayVariableReference(position, position
					+ variableName.length(), name, null, arrayType);
		}
		return new VariableReference(position,
				position + variableName.length(), variableName);
	}

	/**
	 * Determines the return type of the given method element.
	 * 
	 * @param method
	 * @param function
	 * @param offset
	 * @return
	 */
	public static IType[] getFunctionReturnType(IType[] types, String method,
			org.eclipse.dltk.core.ISourceModule sourceModule, int offset) {
		return getFunctionReturnType(types, method, USE_PHPDOC, sourceModule,
				offset);
	}

	/**
	 * Determines the return type of the given method element.
	 * 
	 * @param method
	 * @param mask
	 * @param offset
	 * @return
	 */
	public static IType[] getFunctionReturnType(IType[] types, String method,
			int mask, org.eclipse.dltk.core.ISourceModule sourceModule,
			int offset) {
		PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);
		IContext context = ASTUtils.findContext(sourceModule,
				moduleDeclaration, offset);

		IEvaluatedType evaluatedType;
		IType[] modelElements;
		boolean usePhpDoc = (mask & USE_PHPDOC) != 0;
		if (usePhpDoc) {
			PHPDocMethodReturnTypeGoal phpDocGoal = new PHPDocMethodReturnTypeGoal(
					context, types, method);
			evaluatedType = typeInferencer.evaluateTypePHPDoc(phpDocGoal);

			modelElements = PHPTypeInferenceUtils.getModelElements(
					evaluatedType, (ISourceModuleContext) context, offset);
			if (modelElements != null) {
				return modelElements;
			}
		}

		MethodElementReturnTypeGoal methodGoal = new MethodElementReturnTypeGoal(
				context, types, method);
		evaluatedType = typeInferencer.evaluateType(methodGoal);
		if (evaluatedType instanceof PHPThisClassType
				&& ((PHPThisClassType) evaluatedType).getType() != null) {
			modelElements = new IType[] { ((PHPThisClassType) evaluatedType)
					.getType() };
		} else {
			modelElements = PHPTypeInferenceUtils.getModelElements(
					evaluatedType, (ISourceModuleContext) context, offset);
		}
		if (modelElements != null) {
			return modelElements;
		}
		return EMPTY_TYPES;
	}

	/**
	 * The "self" function needs to be added only if we are in a class method
	 * and it is not an abstract class or an interface
	 * 
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
				if (!PHPFlags.isAbstract(flags) && !PHPFlags.isInterface(flags)
						&& !PHPFlags.isInterface(flags)) {
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
	 * 
	 * @param sourceModule
	 * @param statementText
	 * @param endPosition
	 * @param offset
	 * @return
	 */
	public static IType[] getTypesFor(ISourceModule sourceModule,
			TextSequence statementText, int endPosition, int offset) {
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, endPosition); // read whitespace

		boolean isClassTriger = false;

		if (endPosition < 2) {
			return EMPTY_TYPES;
		}

		String triggerText = statementText.subSequence(endPosition - 2,
				endPosition).toString();
		if (triggerText.equals(OBJECT_FUNCTIONS_TRIGGER)) {
		} else if (triggerText.equals(PAAMAYIM_NEKUDOTAIM)) {
			isClassTriger = true;
		} else {
			return EMPTY_TYPES;
		}

		int propertyEndPosition = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, endPosition - triggerText.length());
		int lastObjectOperator = PHPTextSequenceUtilities
				.getPrivousTriggerIndex(statementText, propertyEndPosition);
		String text = statementText.subSequence(0, propertyEndPosition)
				.toString();
		if (lastObjectOperator == -1
				|| (text.indexOf('>') >= 0 && text.indexOf("->") < 0)) { //$NON-NLS-1$
			// if there is no "->" or "::" in the left sequence then we need to
			// calc the object type
			return innerGetClassName(sourceModule, statementText,
					propertyEndPosition, isClassTriger, offset);
		}

		int propertyStartPosition = PHPTextSequenceUtilities.readForwardSpaces(
				statementText, lastObjectOperator + triggerText.length());
		String propertyName = statementText.subSequence(propertyStartPosition,
				propertyEndPosition).toString();
		IType[] types = getTypesFor(sourceModule, statementText,
				propertyStartPosition, offset);

		int bracketIndex = propertyName.indexOf('(');

		if (bracketIndex == -1) {
			// meaning its a class variable and not a function
			return getVariableType(types, propertyName, offset);
		}

		boolean arrayReference = false;
		PHPVersion version = ProjectOptions.getPhpVersion(sourceModule
				.getScriptProject().getProject());
		if (propertyName.endsWith("]") //$NON-NLS-1$
				&& version.isGreaterThan(PHPVersion.PHP5_3)) {
			int closeBracketIndex = propertyName.lastIndexOf(')');
			if (closeBracketIndex >= 0) {
				if (propertyName.indexOf('[', closeBracketIndex) > closeBracketIndex) {
					arrayReference = true;
				}
			}
		}
		String functionName = propertyName.substring(0, bracketIndex).trim();
		Set<IType> result = new LinkedHashSet<IType>();
		IType[] returnTypes = null;
		if (arrayReference) {
			returnTypes = getFunctionArrayReturnType(types, functionName,
					sourceModule, offset);
		} else {
			returnTypes = getFunctionReturnType(types, functionName,
					sourceModule, offset);
		}
		if (returnTypes != null) {
			result.addAll(Arrays.asList(returnTypes));
		}
		return result.toArray(new IType[result.size()]);
	}

	public static IType[] getTraitsFor(ISourceModule sourceModule,
			TextSequence statementText, int endPosition, int offset) {
		PHPVersion phpVersion = ProjectOptions.getPhpVersion(sourceModule
				.getScriptProject().getProject());
		if (phpVersion.isLessThan(PHPVersion.PHP5_4)) {
			return EMPTY_TYPES;
		}
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, endPosition); // read whitespace

		boolean isClassTriger = false;

		if (endPosition < 2) {
			return EMPTY_TYPES;
		}

		String triggerText = statementText.subSequence(endPosition - 2,
				endPosition).toString();
		if (triggerText.equals(OBJECT_FUNCTIONS_TRIGGER)) {
		} else if (triggerText.equals(PAAMAYIM_NEKUDOTAIM)) {
			isClassTriger = true;
		} else {
			return EMPTY_TYPES;
		}

		int propertyEndPosition = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, endPosition - triggerText.length());
		// int lastObjectOperator = PHPTextSequenceUtilities
		// .getPrivousTriggerIndex(statementText, propertyEndPosition);
		// String text = statementText.subSequence(0, propertyEndPosition)
		// .toString();
		int classNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
				phpVersion, statementText, propertyEndPosition, true);
		String className = statementText.subSequence(classNameStart,
				propertyEndPosition).toString();
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);
		FileContext context = new FileContext(sourceModule, moduleDeclaration,
				offset);
		IEvaluatedType type = PHPClassType.fromTraitName(className,
				sourceModule, offset);
		IType[] modelElements = PHPTypeInferenceUtils.getModelElements(type,
				context, offset);
		if (modelElements != null) {
			return modelElements;
		}
		return EMPTY_TYPES;

	}

	/**
	 * example:(new class1())->avc2()[1][1]->avc1()
	 * 
	 * @param types
	 * @param method
	 * @param mask
	 * @param sourceModule
	 * @param offset
	 * @return
	 */
	private static IType[] getFunctionArrayReturnType(IType[] types,
			String method, ISourceModule sourceModule, int offset) {
		return getFunctionArrayReturnType(types, method, USE_PHPDOC,
				sourceModule, offset);
	}

	/**
	 * example:(new class1())->avc2()[1][1]->avc1()
	 * 
	 * @param types
	 * @param method
	 * @param mask
	 * @param sourceModule
	 * @param offset
	 * @return
	 */
	private static IType[] getFunctionArrayReturnType(IType[] types,
			String method, int mask, ISourceModule sourceModule, int offset) {
		PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);
		IContext context = ASTUtils.findContext(sourceModule,
				moduleDeclaration, offset);

		IEvaluatedType evaluatedType;
		boolean usePhpDoc = (mask & USE_PHPDOC) != 0;
		if (usePhpDoc) {
			PHPDocMethodReturnTypeGoal phpDocGoal = new PHPDocMethodReturnTypeGoal(
					context, types, method);
			evaluatedType = typeInferencer.evaluateTypePHPDoc(phpDocGoal);
			if (evaluatedType instanceof MultiTypeType) {
				List<IType> tmpList = new LinkedList<IType>();
				List<IEvaluatedType> possibleTypes = ((MultiTypeType) evaluatedType)
						.getTypes();
				for (IEvaluatedType possibleType : possibleTypes) {
					IType[] tmpArray = PHPTypeInferenceUtils.getModelElements(
							possibleType, (ISourceModuleContext) context,
							offset, (IModelAccessCache) null);
					if (tmpArray != null) {
						tmpList.addAll(Arrays.asList(tmpArray));
					}
				}
				// the elements are filtered already
				return tmpList.toArray(new IType[tmpList.size()]);
			}

			// modelElements = PHPTypeInferenceUtils.getModelElements(
			// evaluatedType, (ISourceModuleContext) context, offset);
			// if (modelElements != null) {
			// return modelElements;
			// }
		}

		MethodElementReturnTypeGoal methodGoal = new MethodElementReturnTypeGoal(
				context, types, method);
		evaluatedType = typeInferencer.evaluateType(methodGoal);

		if (evaluatedType instanceof MultiTypeType) {
			List<IType> tmpList = new LinkedList<IType>();
			List<IEvaluatedType> possibleTypes = ((MultiTypeType) evaluatedType)
					.getTypes();
			for (IEvaluatedType possibleType : possibleTypes) {
				IType[] tmpArray = PHPTypeInferenceUtils.getModelElements(
						possibleType, (ISourceModuleContext) context, offset,
						(IModelAccessCache) null);
				if (tmpArray != null) {
					tmpList.addAll(Arrays.asList(tmpArray));
				}
			}
			// the elements are filtered already
			return tmpList.toArray(new IType[tmpList.size()]);
		}

		return EMPTY_TYPES;
	}

	/**
	 * Getting an instance and finding its type.
	 */
	private static IType[] innerGetClassName(ISourceModule sourceModule,
			TextSequence statementText, int propertyEndPosition,
			boolean isClassTriger, int offset) {

		PHPVersion phpVersion = ProjectOptions.getPhpVersion(sourceModule
				.getScriptProject().getProject());

		int classNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
				phpVersion, statementText, propertyEndPosition, true);
		String className = statementText.subSequence(classNameStart,
				propertyEndPosition).toString();
		if (isClassTriger && className != null && className.length() != 0) {
			if ("self".equals(className) //$NON-NLS-1$
					|| "parent".equals(className) //$NON-NLS-1$
					|| (phpVersion.isGreaterThan(PHPVersion.PHP5) && "static" //$NON-NLS-1$
							.equals(className))) {
				IType classData = PHPModelUtils.getCurrentType(sourceModule,
						offset - className.length() - 2); // the offset before
				// "self::",
				// "parent::" or
				// "static::"
				if (classData != null) {
					return new IType[] { classData };
				}
			}
			if (className.length() > 0) {
				if (className.startsWith("$") //$NON-NLS-1$
						&& phpVersion.isGreaterThan(PHPVersion.PHP5)) {
					int statementStart = statementText
							.getOriginalOffset(classNameStart);
					return getVariableType(sourceModule, className,
							statementStart);
				} else {
					ModuleDeclaration moduleDeclaration = SourceParserUtil
							.getModuleDeclaration(sourceModule, null);
					FileContext context = new FileContext(sourceModule,
							moduleDeclaration, offset);
					IEvaluatedType type = PHPClassType.fromTypeName(className,
							sourceModule, offset);
					IType[] modelElements = PHPTypeInferenceUtils
							.getModelElements(type, context, offset);
					if (modelElements != null) {
						return modelElements;
					}
					return EMPTY_TYPES;
				}
			}
		}
		if (className.length() == 0) {
			// this can happen if the first char before the property is ']'
			String testedVar = statementText
					.subSequence(0, propertyEndPosition).toString().trim();
			if (testedVar != null && testedVar.length() != 0) {
				// check for $GLOBALS['myVar'] scenario
				Matcher m = globalPattern.matcher(testedVar);
				if (m.matches()) {
					// $GLOBALS['myVar'] => 'myVar'
					String quotedVarName = testedVar.substring(
							testedVar.indexOf('[') + 1, testedVar.indexOf(']'))
							.trim();
					// 'myVar' => $myVar
					className = DOLLAR
							+ quotedVarName.substring(1,
									quotedVarName.length() - 1); //$NON-NLS-1$
					// check for $array[0] scenario
				} else if (testedVar.endsWith("}")) { //$NON-NLS-1$
					className = testedVar;
				} else if (testedVar.endsWith("]")) { //$NON-NLS-1$
					if (statementText.toString().lastIndexOf('[') > 0) {
						int end = statementText.toString().lastIndexOf('[');
						int classNameStart1 = PHPTextSequenceUtilities
								.readIdentifierStartIndex(phpVersion,
										statementText, end, true);
						className = statementText.subSequence(classNameStart1,
								end).toString();
						// if its object call calc the object type.
						if (className.length() > 0
								&& className.charAt(0) == '$') {
							int statementStart = statementText
									.getOriginalOffset(classNameStart);
							return getArrayVariableType(sourceModule,
									className, statementStart);
						}
					}
					className = testedVar;
				}
			}
		}
		// if its object call calc the object type.
		if (className.length() > 0 && className.charAt(0) == '$') {
			int statementStart = statementText
					.getOriginalOffset(classNameStart);
			return getVariableType(sourceModule, className, statementStart);
		}
		boolean arrayReference = false;
		if (statementText.charAt(propertyEndPosition - 1) == ']'
				&& phpVersion.isGreaterThan(PHPVersion.PHP5_3)) {
			int closeBracketIndex = statementText.toString().lastIndexOf(')');
			if (closeBracketIndex >= 0) {
				if (statementText.toString().indexOf('[', closeBracketIndex) > closeBracketIndex) {
					propertyEndPosition = closeBracketIndex + 1;
					arrayReference = true;
				}
			}
		}
		// if its function call calc the return type.
		if (propertyEndPosition > 0
				&& statementText.charAt(propertyEndPosition - 1) == ')') {
			int functionNameEnd = PHPModelUtils.getFunctionNameEndOffset(
					statementText, propertyEndPosition - 1);
			int functionNameStart = PHPTextSequenceUtilities
					.readIdentifierStartIndex(phpVersion, statementText,
							functionNameEnd, false);

			String functionName = statementText.subSequence(functionNameStart,
					functionNameEnd).toString();
			// if its a non class function
			Set<IType> returnTypes = new LinkedHashSet<IType>();
			if (functionNameStart == functionNameEnd
					&& statementText.charAt(functionNameStart) == '('
					&& propertyEndPosition - 1 > functionNameStart + 1
					&& phpVersion.isGreaterThan(PHPVersion.PHP5_3)) {
				TextSequence newClassStatementText = statementText
						.subTextSequence(functionNameStart + 1,
								propertyEndPosition - 1);
				String newClassName = PHPModelUtils
						.getClassNameForNewStatement(newClassStatementText,
								phpVersion);
				try {
					return PHPModelUtils.getTypes(newClassName, sourceModule,
							offset, null, null);
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
				// String newClassName = statementText
				// .subSequence(functionNameStart + 1,
				// propertyEndPosition - 1).toString().trim();
				// if (newClassName.startsWith("new")
				// && newClassName.endsWith(")")) {
				// int newClassNameEnd = PHPModelUtils.getFunctionNameEndOffset(
				// newClassStatementText,
				// newClassStatementText.length() - 1);
				// int newClassNameStart = PHPTextSequenceUtilities
				// .readIdentifierStartIndex(phpVersion,
				// newClassStatementText, newClassNameEnd,
				// false);
				// if (newClassNameStart > 3) {// should have blank chars after
				// // 'new'
				// newClassName = newClassStatementText.subSequence(
				// newClassNameStart, newClassNameEnd).toString();
				//
				// }
				// }

			} else {
				if (arrayReference) {

					IType[] types = getFunctionArrayReturnType(null,
							functionName, sourceModule, offset);
					if (types != null) {
						returnTypes.addAll(Arrays.asList(types));
					}
				} else {
					IType[] types = getFunctionReturnType(null, functionName,
							sourceModule, offset);
					if (types != null) {
						returnTypes.addAll(Arrays.asList(types));
					}
				}
			}
			return returnTypes.toArray(new IType[returnTypes.size()]);
		}
		return EMPTY_TYPES;
	}

	/**
	 * This method checks whether the specified function name refers to existing
	 * method in the given list of classes.
	 * 
	 * @param sourceModule
	 * @param className
	 * @param functionName
	 * @return
	 */
	public static boolean isClassFunctionCall(ISourceModule sourceModule,
			IType[] className, String functionName) {
		for (IType type : className) {
			IMethod[] classMethod;
			try {
				classMethod = PHPModelUtils.getTypeHierarchyMethod(type,
						functionName, true, null);
				if (classMethod != null) {
					return true;
				}
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
			}
		}
		return false;
	}
}
