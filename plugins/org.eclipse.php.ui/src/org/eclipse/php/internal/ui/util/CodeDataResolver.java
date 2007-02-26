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
package org.eclipse.php.internal.ui.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.parser.*;
import org.eclipse.php.internal.core.phpModel.parser.PHPCodeDataFactory.PHPFunctionDataImp;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.contentassist.PHPTextSequenceUtilities;
import org.eclipse.php.internal.ui.editor.util.TextSequence;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.sse.ui.internal.contentassist.ContentAssistUtils;

public class CodeDataResolver {

	protected static final char[] phpDelimiters = new char[] { '?', ':', ';', '|', '^', '&', '<', '>', '+', '-', '.', '*', '/', '%', '!', '~', '[', ']', '(', ')', '{', '}', '@', '\n', '\t', ' ', ',', '$', '\'', '\"' };
	private static final String CLASS_FUNCTIONS_TRIGGER = "::";
	private static final String OBJECT_FUNCTIONS_TRIGGER = "->";

	private static final Pattern extendsPattern = Pattern.compile("\\Wextends\\W", Pattern.CASE_INSENSITIVE);
	private static final Pattern implementsPattern = Pattern.compile("\\Wimplements", Pattern.CASE_INSENSITIVE);
	private static final Pattern catchPattern = Pattern.compile("catch\\s[^{]*", Pattern.CASE_INSENSITIVE);
	private static final boolean CONSTANT_CASE_SENSITIVE = false;

	private static final CodeDataResolver instance = new CodeDataResolver();

	private CodeDataResolver() {
	}

	/**
	 * This function tries to search for code data, that corresponds to the specified offset
	 * 
	 * @param textViewer Text viewer
	 * @param offset Real offset of in the document
	 * @return CodeData Code data according to the selected PHP element
	 */
	public static CodeData getCodeData(ITextViewer textViewer, int offset) {
		IStructuredModel sModel = StructuredModelManager.getModelManager().getExistingModelForRead(textViewer.getDocument());
		if (sModel instanceof DOMModelForPHP) {
			DOMModelForPHP editorModel = (DOMModelForPHP) sModel;
			try {
				return instance.getCodeData(textViewer, editorModel, offset);
			} catch (BadLocationException e) {
				Logger.logException(e);
			} finally {
				sModel.releaseFromRead();
			}
		} else if (sModel != null) {
			sModel.releaseFromRead();
		}
		return null;
	}

	private CodeData getCodeData(ITextViewer viewer, DOMModelForPHP editorModel, int offset) throws BadLocationException {
		PHPFileData fileData = editorModel.getFileData();
		if (fileData == null) {
			return null;
		}

		IStructuredDocumentRegion sdRegion = ContentAssistUtils.getStructuredDocumentRegion((StructuredTextViewer) viewer, offset);
		ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(offset);
		if (textRegion == null) {
			return null;
		}

		ITextRegionCollection container = sdRegion;
		if (textRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) textRegion;
			textRegion = container.getRegionAtCharacterOffset(offset);
		}

		PhpScriptRegion phpScriptRegion;
		if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			phpScriptRegion = (PhpScriptRegion) textRegion;
			textRegion = phpScriptRegion.getPhpToken(offset - container.getStartOffset() - phpScriptRegion.getStart());
		} else {
			return null;
		}

		TextSequence statmentText = PHPTextSequenceUtilities.getStatment(container.getStartOffset() + phpScriptRegion.getStart() + textRegion.getEnd(), sdRegion, true);
		if (statmentText == null) {
			return null;
		}

		String fileName = fileData.getName();
		PHPProjectModel projectModel = editorModel.getProjectModel();
		CodeData resultCandidate;
		String type = textRegion.getType();
		if ((resultCandidate = getIfInArrayOptionQuotes(projectModel, fileName, type, offset, statmentText)) != null) {
			// the current position is inside quotes as a parameter for an array.
			return resultCandidate;
		}

		if (isPHPSingleQuote(sdRegion, textRegion) || PHPPartitionTypes.isPHPCommentState(type)) {
			// we dont have code completion inside single quotes.
			return null;
		}

		if ((resultCandidate = getIfInFunctionDeclaretion(projectModel, fileName, statmentText, offset)) != null) {
			// the current position is inside function declaretion.
			return resultCandidate;
		}

		if ((resultCandidate = getIfInClassDeclaretion(projectModel, fileName, statmentText, offset)) != null) {
			// the current position is inside class declaretion.
			return resultCandidate;
		}

		if ((resultCandidate = getIfInCatchStatment(projectModel, statmentText, offset)) != null) {
			// the current position is inside catch statment.
			return resultCandidate;
		}

		int totalLength = statmentText.length();

		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statmentText, totalLength); // read whitespace
		int startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(statmentText, endPosition, true);
		String lastWord = statmentText.subSequence(startPosition, endPosition).toString();
		boolean haveSpacesAtEnd = totalLength != endPosition;

		if (haveSpacesAtEnd && (resultCandidate = getIfNewOrInstanceofStatment(fileName, projectModel, lastWord, "", offset, type)) != null) {
			// the current position is inside new or instanceof statment.
			return resultCandidate;
		}

		int line = sdRegion.getParentDocument().getLineOfOffset(offset);
		if ((resultCandidate = getIfClassFunctionCompletion(projectModel, fileName, statmentText, offset, line, lastWord, startPosition, haveSpacesAtEnd)) != null) {
			// the current position is in class function.
			return resultCandidate;
		}

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statmentText, startPosition); // read whitespace
		startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(statmentText, endPosition, true);
		String firstWord = statmentText.subSequence(startPosition, endPosition).toString();

		if (!haveSpacesAtEnd && (resultCandidate = getIfNewOrInstanceofStatment(fileName, projectModel, firstWord, lastWord, offset, type)) != null) {
			// the current position is inside new or instanceof statment.
			return resultCandidate;
		}

		if ((resultCandidate = getIfInArrayOption(projectModel, fileName, haveSpacesAtEnd, firstWord, lastWord, startPosition, offset, statmentText)) != null) {
			// the current position is after '[' sign show special completion.
			return resultCandidate;
		}

		String elementName = lastWord;
		if (elementName.startsWith("$")) {
			if (PHPPartitionTypes.isPHPQuotesState(type)) {
				IStructuredDocument doc = sdRegion.getParentDocument();
				try {
					char charBefore = doc.get(offset - 2, 1).charAt(0);
					if (charBefore == '\\') {
						return null;
					}
				} catch (BadLocationException badLocationException) {
					Logger.logException(badLocationException);
				}
			}
			PHPCodeContext context = getContext(projectModel, fileName, offset - elementName.length());
			elementName = elementName.substring(1);
			CodeData[] variables = projectModel.getVariables(fileName, context, elementName, true);
			return filterExact(variables, elementName);
		}

		if (PHPPartitionTypes.isPHPQuotesState(type) || (type.equals(PHPRegionTypes.PHP_HEREDOC_TAG) && sdRegion.getStartOffset(textRegion) + textRegion.getLength() <= offset)) {
			return null;
		}
		if ("".equals(elementName)) {
			return null;
		}
		CodeData function = projectModel.getFunction(fileName, elementName);
		CodeData constant = projectModel.getConstant(fileName, elementName);
		CodeData classs = projectModel.getClass(fileName, elementName);
		CodeData[] keywords = projectModel.getKeywordData(elementName);

		CodeData[] mergeData = null;
		if (keywords != null && keywords.length > 0) {
			mergeData = ModelSupport.merge(keywords, mergeData);
		}
		if (classs != null) {
			mergeData = ModelSupport.merge(new CodeData[] { classs }, mergeData);
		}
		if (constant != null) {
			mergeData = ModelSupport.merge(new CodeData[] { constant }, mergeData);
		}
		if (function != null) {
			mergeData = ModelSupport.merge(new CodeData[] { function }, mergeData);
		}

		return filterExact(mergeData, elementName);
	}

	private boolean isPHPSingleQuote(IStructuredDocumentRegion sdRegion, ITextRegion textRegion) {
		if (PHPPartitionTypes.isPHPQuotesState(textRegion.getType())) {
			char firstChar = sdRegion.getText(textRegion).charAt(0);
			return (firstChar == '\'');
		}
		return false;
	}

	private CodeData getIfInArrayOptionQuotes(PHPProjectModel projectModel, String fileName, String type, int offset, TextSequence text) {
		if (!PHPPartitionTypes.isPHPQuotesState(type)) {
			return null;
		}
		int length = text.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, length);
		int startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, false);
		if (endPosition != length && startPosition != endPosition) {
			return null;
		}

		String elementName = text.subSequence(startPosition, endPosition).toString();
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, startPosition);
		if (endPosition == 0) {
			return null;
		}
		char c = text.charAt(endPosition - 1);
		if (c != '\"' && c != '\'') {
			return null;
		}
		endPosition--;
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, endPosition);
		if (endPosition == 0 || text.charAt(endPosition - 1) != '[') {
			return null;
		}
		endPosition--;
		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, endPosition);
		startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, true);
		String variableName = text.subSequence(startPosition, endPosition).toString();

		if (variableName.startsWith("$")) {
			variableName = variableName.substring(1);
		}
		return filterExact(projectModel.getArrayVariables(fileName, variableName, elementName, true), elementName);
	}

	private CodeData getIfClassFunctionCompletion(PHPProjectModel projectModel, String fileName, TextSequence statmentText, int offset, int line, String functionName, int startFunctionPosition, boolean haveSpacesAtEnd) {
		startFunctionPosition = PHPTextSequenceUtilities.readBackwardSpaces(statmentText, startFunctionPosition);
		if (startFunctionPosition <= 2) {
			return null;
		}
		boolean isClassTriger = false;
		String triggerText = statmentText.subSequence(startFunctionPosition - 2, startFunctionPosition).toString();
		if (triggerText.equals(OBJECT_FUNCTIONS_TRIGGER)) {
		} else if (triggerText.equals(CLASS_FUNCTIONS_TRIGGER)) {
			isClassTriger = true;
		} else {
			return null;
		}

		String className = getClassName(projectModel, fileName, statmentText, startFunctionPosition, offset, line);

		if (className == null) {
			className = "";
		}

		if (haveSpacesAtEnd && functionName.length() > 0) {
			// check if current position is between the end of a function call and open bracket.
			return filterExact(getIfClassFunctionCall(projectModel, fileName, className, functionName), functionName);
		}
		boolean isInstanceOf = !isClassTriger && !statmentText.toString().trim().equals("$this->");
		return filterExact(getClassCall(projectModel, fileName, offset, className, functionName, isInstanceOf), functionName);
	}

	/**
	 * returns the type of the variable in the sequence.
	 *
	 * @param statmentText
	 * @param endPosition  - the end offset in the sequence
	 * @param offset       - the offset in the document
	 */

	private String getClassName(PHPProjectModel projectModel, String fileName, TextSequence statmentText, int endPosition, int offset, int line) {
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
	private String innerGetClassName(PHPProjectModel projectModel, String fileName, TextSequence statmentText, int propertyEndPosition, boolean isClassTriger, int offset, int line) {

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
			return PHPFileDataUtilities.getVariableType(fileName, className, statmentStart, line, projectModel.getPHPUserModel(), true);
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

	private CodeData[] getIfClassFunctionCall(PHPProjectModel projectModel, String fileName, String className, String functionName) {
		CodeData classFunctionData = projectModel.getClassFunctionData(fileName, className, functionName);
		if (classFunctionData != null) {
			return new CodeData[] { classFunctionData };
		}
		return null;
	}

	/**
	 * finding the type of the class variable.
	 */
	private String getVarType(PHPProjectModel projectModel, String fileName, String className, String varName, int statmentStart, int line) {
		String tempType = PHPFileDataUtilities.getVariableType(fileName, "this;*" + varName, statmentStart, line, projectModel.getPHPUserModel(), true);
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
	private String getFunctionReturnType(PHPProjectModel projectModel, String fileName, String className, String functionName) {
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

	private CodeData[] getClassCall(PHPProjectModel projectModel, String fileName, int offset, String className, String startWith, boolean isInstanceOf) {
		CodeData function = projectModel.getClassFunctionData(fileName, className, startWith);
		CodeData variable = projectModel.getClassVariablesData(fileName, className, startWith);
		CodeData constant = projectModel.getClassConstsData(fileName, className, startWith);
		CodeData[] result = null;
		if (function != null) {
			result = ModelSupport.merge(new CodeData[] { function }, result);
		}
		if (variable != null) {
			result = ModelSupport.merge(new CodeData[] { variable }, result);
		}
		if (constant != null) {
			result = ModelSupport.merge(new CodeData[] { constant }, result);
		}
		result = ModelSupport.getFilteredCodeData(result, getAccessLevelFilter(projectModel, fileName, className, offset, isInstanceOf));

		return result;
	}

	private PHPClassData getContainerClassData(PHPProjectModel projectModel, String fileName, int offset) {
		PHPFileData fileData = projectModel.getFileData(fileName);
		return PHPFileDataUtilities.getContainerClassDada(fileData, offset);
	}

	/**
	 * this function searches the sequence from the right closing bracket ")" and finding
	 * the position of the left "("
	 * the offset has to be the offset of the "("
	 */
	private int getFunctionNameEndOffset(TextSequence statmentText, int offset) {
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
	private CodeDataFilter getAccessLevelFilter(PHPProjectModel projectModel, String fileName, String className, int offset, boolean isInstanceOf) {
		PHPCodeContext context = getContext(projectModel, fileName, offset);
		String contextClassName = context.getContainerClassName();
		// if the name of the context class is the same as the className itself then we are
		// inside the same class - meaning we can use private methodes. 
		if (contextClassName.equals(className)) {
			return ModelSupport.PIRVATE_ACCESS_LEVEL_FILTER;
		}
		// if we are out side of a class or this is an instance of a class and not $this
		if (contextClassName.equals("") || isInstanceOf) {
			return ModelSupport.PUBLIC_ACCESS_LEVEL_FILTER;
		}
		PHPClassData classData = projectModel.getClass(fileName, contextClassName);
		String superClassName = classData.getSuperClassData().getName();
		while (superClassName != null) {
			if (superClassName.equals(className)) {
				return ModelSupport.PROTECTED_ACCESS_LEVEL_FILTER;
			}
			classData = projectModel.getClass(fileName, superClassName);
			if (classData == null) {
				break;
			}
			superClassName = classData.getSuperClassData().getName();
		}
		return ModelSupport.PUBLIC_ACCESS_LEVEL_FILTER;
	}

	private PHPCodeContext getContext(PHPProjectModel projectModel, String fileName, int offset) {
		PHPFileData fileData = projectModel.getFileData(fileName);
		return ModelSupport.createContext(fileData, offset);
	}

	private CodeData getIfInFunctionDeclaretion(PHPProjectModel projectModel, String fileName, TextSequence text, int offset) {
		// are we inside function declaretion statment
		int functionStart = PHPTextSequenceUtilities.isInFunctionDeclaretion(text);
		if (functionStart == -1) {
			return null;
		}

		// are we inside parameters part in function declaretion statment
		for (int i = text.length() - 1; i >= functionStart; i--) {
			if (text.charAt(i) == '(') {
				return null;
			}
		}

		int wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(text, text.length());
		int wordStart = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, wordEnd, false);
		String functionName = text.subSequence(wordStart, wordEnd).toString();

		PHPClassData classData = getContainerClassData(projectModel, fileName, text.getOriginalOffset(functionStart));
		// We look for the container class data in function start offset.

		if (classData == null) {
			PHPFileData file = projectModel.getFileData(fileName);
			return filterExact(file.getFunctions(), functionName);
		}

		// Try to search for the function in current class:
		CodeData codeData = projectModel.getClassFunctionData(fileName, classData.getName(), functionName);
		if (codeData != null) {
			return codeData;
		}

		CodeData data[];
		String phpVersion = projectModel.getPHPLanguageModel().getPHPVersion();
		boolean isPHP5 = phpVersion.equals(PHPVersion.PHP5);
		if (isPHP5) {
			data = new CodeData[] { PHPCodeDataFactory.createPHPFuctionData(PHPClassData.CONSTRUCTOR, PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null),
				PHPCodeDataFactory.createPHPFuctionData(PHPClassData.DESCRUCTOR, PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null),
				PHPCodeDataFactory.createPHPFuctionData(classData.getName(), PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null), };
		} else {
			return PHPCodeDataFactory.createPHPFuctionData(classData.getName(), PHPModifier.PUBLIC, null, classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null);
		}
		for (int i = 0; i < data.length; i++) {
			((PHPFunctionData) data[i]).setContainer(classData);
			((PHPFunctionDataImp) data[i]).setReturnType(classData.getName());
		}
		return filterExact(data, functionName);
	}

	private CodeData getIfInClassDeclaretion(PHPProjectModel projectModel, String fileName, TextSequence text, int offset) {
		int classEnd = PHPTextSequenceUtilities.isInClassDeclaretion(text);
		if (classEnd == -1) {
			return null;
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
			return projectModel.getClass(fileName, text.toString());
		}
		text = text.subTextSequence(classIdentifierEndPosition, text.length());

		int endPosition = text.length();
		int startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, false);
		String elementName = text.subSequence(startPosition, endPosition).toString();

		Matcher extendsMatcher = extendsPattern.matcher(text);
		Matcher implementsMatcher = implementsPattern.matcher(text);
		boolean foundExtends = extendsMatcher.find();
		boolean foundImplements = implementsMatcher.find();
		if (!foundExtends && !foundImplements) {
			if (isClassDeclaration) {
				return filterExact(getExtendsImplementsCodeData(projectModel, elementName, offset), elementName);
			}
			return filterExact(getExtendsCodeData(projectModel, elementName, offset), elementName);
		}

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, startPosition);
		startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, true);
		String firstWord = text.subSequence(startPosition, endPosition).toString();

		if (firstWord.equalsIgnoreCase("extends")) {
			return filterExact(getBaseClassList(projectModel, elementName, offset, isClassDeclaration), elementName);
		}

		if (firstWord.equalsIgnoreCase("implements")) {
			return filterExact(getInterfaceList(projectModel, elementName, offset), elementName);
		}

		if (foundExtends && foundImplements) {
			if (extendsMatcher.start() < implementsMatcher.start()) {
				return filterExact(getInterfaceList(projectModel, elementName, offset), elementName);
			}
			return filterExact(getBaseClassList(projectModel, elementName, offset, isClassDeclaration), elementName);
		}

		if (foundImplements) {
			return filterExact(getInterfaceList(projectModel, elementName, offset), elementName);
		}
		if (isClassDeclaration) {
			return filterExact(getImplementsCodeData(projectModel, elementName, offset), elementName);
		}
		return null;
	}

	private CodeData[] getInterfaceList(PHPProjectModel projectModel, String startWith, int offset) {
		CodeData[] classes = projectModel.getClasses(startWith);
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
		return interfacesArray;
	}

	private CodeData[] extendedImplementCodeData;

	private CodeData[] getExtendsImplementsCodeData(PHPProjectModel projectModel, String startWith, int offset) {
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

	private static CodeData[] implementCodeData;

	private CodeData[] getImplementsCodeData(PHPProjectModel projectModel, String startWith, int offset) {
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

	private static CodeData[] extendsCodeData;

	private static CodeData[] getExtendsCodeData(PHPProjectModel projectModel, String startWith, int offset) {
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

	private CodeData[] getBaseClassList(PHPProjectModel projectModel, String startWith, int offset, boolean isClassDecleration) {
		if (!isClassDecleration) {
			getInterfaceList(projectModel, startWith, offset);
		}
		CodeData[] classes = getOnlyClasses(projectModel);
		return classes;
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

	private CodeData getIfInCatchStatment(PHPProjectModel projectModel, TextSequence text, int offset) {
		Matcher matcher = catchPattern.matcher(text);
		int catchStart = text.length();
		while (matcher.find()) {
			if (text.length() == matcher.end()) {
				catchStart = matcher.start() + 1; // for the white space before the 'class'
				break;
			}
		}
		if (catchStart == text.length()) {
			return null;
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
			return null;
		}

		startPosition = PHPTextSequenceUtilities.readForwardSpaces(text, startPosition + 1); // + 1 for the '('
		int endPosition = PHPTextSequenceUtilities.readIdentifiarEndIndex(text, startPosition, false);
		String className = text.subSequence(startPosition, endPosition).toString();

		if (endPosition == text.length()) {
			return filterExact(getClassList(projectModel, className, offset, false), className);
		}
		return null;
	}

	private CodeData[] getClassList(PHPProjectModel projectModel, String startWith, int offset, boolean isNewStatment) {
		CodeData[] classes;
		if (isNewStatment) {
			classes = getOnlyClasses(projectModel);
		} else {
			classes = projectModel.getClasses(startWith);
		}
		return classes;
	}

	private CodeData getIfNewOrInstanceofStatment(String fileName, PHPProjectModel projectModel, String keyword, String elementName, int offset, String type) {
		if (PHPPartitionTypes.isPHPQuotesState(type)) {
			return null;
		}

		if (keyword.equalsIgnoreCase("instanceof") || keyword.equalsIgnoreCase("new")) {
			return projectModel.getClass(fileName, elementName);
		}

		return null;
	}

	private CodeData getIfInArrayOption(PHPProjectModel projectModel, String fileName, boolean haveSpacesAtEnd, String firstWord, String elementName, int startPosition, int offset, TextSequence text) {
		boolean isArrayOption = false;
		if (startPosition > 0 && !elementName.startsWith("$")) {
			if (haveSpacesAtEnd) {
				if (elementName.length() == 0 && firstWord.length() == 0) {
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
			return null;
		}
		int endPosition = startPosition - 1;

		endPosition = PHPTextSequenceUtilities.readBackwardSpaces(text, endPosition);
		startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(text, endPosition, true);
		String variableName = text.subSequence(startPosition, endPosition).toString();

		if (variableName.startsWith("$")) {
			variableName = variableName.substring(1);
		}
		CodeData[] arrayResult = projectModel.getArrayVariables(fileName, variableName, elementName, true);
		CodeData[] functions = projectModel.getFunction(elementName);
		if (functions != null && functions.length > 0) {
			arrayResult = ModelSupport.merge(functions, arrayResult);
		}
		CodeData[] constants = projectModel.getConstants(elementName, CONSTANT_CASE_SENSITIVE);
		if (constants != null && constants.length > 0) {
			arrayResult = ModelSupport.merge(constants, arrayResult);
		}
		return filterExact(arrayResult, elementName);
	}

	private CodeData filterExact(CodeData[] sortedArray, String searchName) {
		int index = ModelSupport.getFirstMatch(sortedArray, searchName, true);
		if (index != -1) {
			return sortedArray[index];
		}
		return null;
	}
}
