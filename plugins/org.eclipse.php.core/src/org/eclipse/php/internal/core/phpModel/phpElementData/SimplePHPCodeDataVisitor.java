/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.phpElementData;


public class SimplePHPCodeDataVisitor extends SimpleCodeDataVisitor implements PHPProjectModelVisitor {

	public void visit(PHPCodeData phpCodeData) {
		visit((CodeData) phpCodeData);
	}

	public void visit(PHPFunctionData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPClassData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPClassData.PHPSuperClassNameData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPClassData.PHPInterfaceNameData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPClassVarData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPClassConstData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPIncludeFileData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPKeywordData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPFunctionData.PHPFunctionParameter codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPVariableData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPFileData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPConstantData codeData) {
		visit((CodeData) codeData);
	}

	public void visit(PHPDocTagData codeData) {
		visit((CodeData) codeData);
	}

}