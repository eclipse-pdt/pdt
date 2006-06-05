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
package org.eclipse.php.core.phpModel.phpElementData;


public interface PHPProjectModelVisitor extends CodeDataVisitor {

	public void visit(PHPCodeData phpCodeData);

	public void visit(PHPFunctionData codeData);

	public void visit(PHPClassData codeData);

	public void visit(PHPClassData.PHPSuperClassNameData codeData);

	public void visit(PHPClassData.PHPInterfaceNameData codeData);

	public void visit(PHPClassVarData codeData);

	public void visit(PHPClassConstData codeData);

	public void visit(PHPIncludeFileData codeData);

	public void visit(PHPKeywordData codeData);

	public void visit(PHPFunctionData.PHPFunctionParameter codeData);

	public void visit(PHPVariableData codeData);

	public void visit(PHPFileData codeData);

	public void visit(PHPConstantData codeData);

	public void visit(PHPDocTagData codeData);

}