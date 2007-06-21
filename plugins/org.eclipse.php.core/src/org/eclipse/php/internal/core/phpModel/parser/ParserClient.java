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

import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocBlock;

/**
 * This is interface is to be used by the PhpParser as it's client.
 * It's functions are hooks from the parser actions to the implemeting class
 * of this interface.
 */
public interface ParserClient {
	
	public void dispose();

	public void handleFunctionDeclarationStarts(String functionName);

	public void handleFunctionDeclaration(String functionName, boolean isClassFunction, int modifier, PHPDocBlock docInfo, int startPosition, int stopPosition, int lineNumber);

	public void handleFunctionDeclarationEnds(String functionName, boolean isClassFunction, int endPosition);

	public void handleFunctionParameter(String classType, String variableName, boolean isReference, boolean isConst, String defaultValue, int startPosition, int endPosition, int stopPosition, int lineNumber);

	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public void hadleClassDeclarationStarts(String className, int startPosition);

	public void handleClassDeclaration(String className, int modifier, String superClassName, String interfacesNames, PHPDocBlock docInfo, int startPosition, int stopPosition, int lineNumber);

	public void handleClassDeclarationEnds(String className, int endPosition);

	public void handleClassVariablesDeclaration(String variables, int modifier, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition);

	public void handleClassConstDeclaration(String constName, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition);

	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public void handleIncludedFile(String includingType, String includeFileName, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition, int lineNumber);

	public void haveReturnValue();

	public void handleObjectInstansiation(String variableName, String className, String ctorArrguments, int line, int startPosition, boolean isUserDocumentation);

	public void handleVariableName(String variableName, int line);

	public void handleGlobalVar(String variableName);

	public void handleStaticVar(String variableName);

	public void handleDefine(String name, String value, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition);

	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public void handleError(String description, int startPosition, int endPosition, int lineNumber);

	public void handleSyntaxError(int currToken, String currText, short[] rowOfProbe, int startPosition, int endPosition, int lineNumber);

	public void handleTask(String taskName, String description, int startPosition, int endPosition, int lineNumber);

	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public void handlePHPStart(int startOffset, int endOffset);

	public void handlePHPEnd(int startOffset, int endOffset);

	public void setFirstDocBlock(PHPDocBlock docBlock);

	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startParsing(String fileName);

	public void finishParsing(int lastPosition, int lastLine, long lastModified);

	///////////////////////////////////////////////////////////////////////////////////////////////////////

}