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
package org.eclipse.php.internal.core.phpModel.parser;

import java.util.Stack;

import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocBlock;

public class ContextParserClient implements ParserClient {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private String className;
	private String functionName;
	private PHPCodeContext context;

	private final Stack classes = new Stack();
	private final Stack functions = new Stack();

	public ContextParserClient() {
		className = EMPTY_STRING;
		functionName = EMPTY_STRING;
		updateContext();
	}
	
	public void dispose() {
	}

	protected PHPCodeContext getContext() {
		return context;
	}

	private void updateContext() {
		context = ModelSupport.createContext(className, functionName);
	}

	protected String getCurrentFunctionName() {
		return functionName;
	}

	protected String getCurrentClassName() {
		return className;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////
	// Parser client methods.
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public void handleFunctionDeclaration(String functionName, boolean isClassFunction, int modifier, PHPDocBlock docInfo, int startPosition, int stopPosition, int lineNumber) {
	}

	public void handleFunctionParameter(String classType, String variableName, boolean isReference, boolean isConst, String defaultValue, int startPosition, int endPosition, int stopPosition, int lineNumber) {
	}

	public void handleClassDeclaration(String className, int modifier, String superClassName, String interfacesNames, PHPDocBlock docInfo, int startPosition, int stopPosition, int lineNumber) {
	}

	public void handleClassVariablesDeclaration(String variables, int modifier, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition) {
	}

	public void handleClassConstDeclaration(String constName, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition) {
	}

	public void handleIncludedFile(String includingType, String includeFileName, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition, int lineNumber) {
	}

	public void handleError(String description, int startPosition, int endPosition, int lineNumber) {
	}

	public void handleSyntaxError(int currToken, String currText, short[] rowOfProbe, int startPosition, int endPosition, int lineNumber) {
	}

	public void handleTask(String taskName, String description, int startPosition, int endPosition, int lineNumber) {
	}

	public void haveReturnValue() {
	}

	public void handleFunctionDeclarationStarts(String functionName) {
		this.functionName = functionName;
		this.functions.push(functionName);
		updateContext();
	}

	public void handleFunctionDeclarationEnds(String functionName, boolean isClassFunction, int endPosition) {
		if (!functions.isEmpty()) {
			this.functions.pop();
		}
		this.functionName = (functions.isEmpty()) ? EMPTY_STRING : (String) functions.peek();
		updateContext();
	}

	public void hadleClassDeclarationStarts(String className, int startPosition) {
		this.className = className;
		this.classes.push(className);
		updateContext();
	}

	public void handleClassDeclarationEnds(String className, int endPosition) {
		if (!classes.isEmpty()) {
			this.classes.pop();
		}
		this.className = (classes.isEmpty()) ? EMPTY_STRING : (String) classes.peek();
		updateContext();
	}

	public void handleObjectInstansiation(String variableName, String className, String ctorArrguments, int line, int startPosition, boolean isUserDocumetation) {
	}

	public void handleVariableName(String variableName, int line) {
	}

	public void handleGlobalVar(String variableName) {
	}

	public void handleStaticVar(String variableName) {
	}

	public void startParsing(String fileName) {
		classes.clear();
		functions.clear();
		className = EMPTY_STRING;
		functionName = EMPTY_STRING;
		updateContext();
	}

	public void finishParsing(int lastPosition, int lastLine, long lastModified) {
	}

	public void handlePHPStart(int startOffset, int endOffset) {
	}

	public void handlePHPEnd(int startOffset, int endOffset) {
	}

	public void setFirstDocBlock(PHPDocBlock docBlock) {
	}

	public void handleDefine(String name, String value, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition) {
	}

}