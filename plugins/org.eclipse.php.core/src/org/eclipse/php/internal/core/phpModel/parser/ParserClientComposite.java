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

import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocBlock;

public class ParserClientComposite extends HashSet implements ParserClient {
	
	public ParserClientComposite() {
	}

	public void dispose() {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.dispose();
		}
	}

	public void handleFunctionDeclarationStarts(String functionName) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleFunctionDeclarationStarts(functionName);
		}
	}

	public void handleFunctionDeclaration(String functionName, boolean isClassFunction, int modifier, PHPDocBlock docInfo, int startPosition, int stopPosition, int lineNumber) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleFunctionDeclaration(functionName, isClassFunction, modifier, docInfo, startPosition, stopPosition, lineNumber);
		}
	}

	public void handleFunctionDeclarationEnds(String functionName, boolean isClassFunction, int endPosition) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleFunctionDeclarationEnds(functionName, isClassFunction, endPosition);
		}
	}

	public void handleFunctionParameter(String classType, String variableName, boolean isReference, boolean isConst, String defaultValue, int startPosition, int endPosition, int stopPosition, int lineNumber) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleFunctionParameter(classType, variableName, isReference, isConst, defaultValue, startPosition, endPosition, stopPosition, lineNumber);
		}
	}

	public void hadleClassDeclarationStarts(String className, int startPosition) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.hadleClassDeclarationStarts(className, startPosition);
		}
	}

	public void handleClassDeclaration(String className, int modifier, String superClassName, String interfacesNames, PHPDocBlock docInfo, int startPosition, int stopPosition, int lineNumber) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleClassDeclaration(className, modifier, superClassName, interfacesNames, docInfo, startPosition, stopPosition, lineNumber);
		}
	}

	public void handleClassDeclarationEnds(String className, int endPosition) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleClassDeclarationEnds(className, endPosition);
		}
	}

	public void handleClassVariablesDeclaration(String variables, int modifier, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleClassVariablesDeclaration(variables, modifier, docInfo, startPosition, endPosition, stopPosition);
		}
	}

	public void handleClassConstDeclaration(String constName, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleClassConstDeclaration(constName, docInfo, startPosition, endPosition, stopPosition);
		}
	}

	public void handleIncludedFile(String includeFileName, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition, int lineNumber) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleIncludedFile(includeFileName, docInfo, startPosition, endPosition, stopPosition, lineNumber);
		}
	}

	public void haveReturnValue() {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.haveReturnValue();
		}
	}

	public void handleObjectInstansiation(String variableName, String className, String ctorArrguments, int line, int startPosition, boolean isUserDocumentation) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleObjectInstansiation(variableName, className, ctorArrguments, line, startPosition, isUserDocumentation);
		}
	}

	public void handleVariableName(String variableName, int line) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleVariableName(variableName, line);
		}
	}

	public void handleGlobalVar(String variableName) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleGlobalVar(variableName);
		}
	}

	public void handleStaticVar(String variableName) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleStaticVar(variableName);
		}
	}

	public void handleDefine(String name, String value, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleDefine(name, value, docInfo, startPosition, endPosition, stopPosition);
		}
	}

	public void handleError(String description, int startPosition, int endPosition, int lineNumber) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleError(description, startPosition, endPosition, lineNumber);
		}
	}

	public void handleSyntaxError(int currToken, String currText, short[] rowOfProbe, int startPosition, int endPosition, int lineNumber) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleSyntaxError(currToken, currText, rowOfProbe, startPosition, endPosition, lineNumber);
		}
	}

	public void handleTask(String taskName, String description, int startPosition, int endPosition, int lineNumber) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handleTask(taskName, description, startPosition, endPosition, lineNumber);
		}
	}

	public void handlePHPStart(int startOffset, int endOffset) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handlePHPStart(startOffset, endOffset);
		}
	}

	public void handlePHPEnd(int startOffset, int endOffset) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.handlePHPEnd(startOffset, endOffset);
		}
	}

	public void setFirstDocBlock(PHPDocBlock docBlock) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.setFirstDocBlock(docBlock);
		}
	}

	public void startParsing(String fileName) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.startParsing(fileName);
		}
	}

	public void finishParsing(int lastPosition, int lastLine, long lastModified) {
		for (Iterator iter = iterator(); iter.hasNext();) {
			ParserClient parserClient = (ParserClient) iter.next();
			parserClient.finishParsing(lastPosition, lastLine, lastModified);
		}
	}

}
