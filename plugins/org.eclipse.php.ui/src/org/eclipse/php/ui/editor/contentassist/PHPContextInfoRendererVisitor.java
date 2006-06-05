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
package org.eclipse.php.ui.editor.contentassist;

import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassVarData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.core.phpModel.phpElementData.PHPKeywordData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.core.phpModel.phpElementData.SimplePHPCodeDataVisitor;


public class PHPContextInfoRendererVisitor extends SimplePHPCodeDataVisitor {

	private String text;
	protected StringBuffer buffer;

	public PHPContextInfoRendererVisitor() {
		buffer = new StringBuffer();
	}

	public void init(CodeData codeData) {
		buffer.setLength(0);
		codeData.accept(this);
		text = buffer.toString();
	}

	public String getDisplayString() {
		return text;
	}

	// ------------------------------------------------------------------------------------------------
	public void visit(CodeData codeData) {
		buffer.append(codeData.getName());
	}

	public void visit(PHPFunctionData codeData) {
		PHPFunctionData.PHPFunctionParameter[] parameters = codeData.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			if (i != 0) {
				buffer.append(", ");
			}
			visit(parameters[i]);
		}
	}

	public void visit(PHPClassVarData codeData) {
		//super.visit(codeData);
	}

	public void visit(PHPClassConstData codeData) {
		//super.visit(codeData);
	}

	public void visit(PHPConstantData codeData) {
		//super.visit(codeData);
	}

	public void visit(PHPClassData codeData) {
		if (codeData.hasConstructor()) {
			visit(codeData.getConstructor());
		}/* else {
			super.visit(codeData);
		}*/
	}

	public void visit(PHPKeywordData codeData) {
		//super.visit(codeData);
	}

	public void visit(PHPVariableData codeData) {
		//super.visit(codeData);
	}

	public void visit(PHPFunctionData.PHPFunctionParameter codeData) {
		String classType = codeData.getClassType();
		if (classType != null && classType.length() != 0) {
			buffer.append(classType);
			buffer.append(" ");
		} else {
			// buffer.append("unknown");
			// buffer.append(" ");
		}
		if (codeData.isConst()) {
			buffer.append("const ");
		}
		if (codeData.isReference()) {
			buffer.append("&");
		}
		buffer.append("$");
		buffer.append(codeData.getName());
		String defaultValue = codeData.getDefaultValue();
		if (defaultValue != null && defaultValue.length() != 0) {
			buffer.append(" = ");
			buffer.append(defaultValue);
		}
	}
}
