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
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public class PHPContextInfoRendererVisitor extends SimplePHPCodeDataVisitor {

	private String text;
	protected StringBuffer buffer;
	private PHPProjectModel projectModel;

	public PHPContextInfoRendererVisitor() {
		buffer = new StringBuffer();
	}

	public void init(PHPProjectModel projectModel, CodeData codeData) {
		this.projectModel = projectModel;
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
				buffer.append(", "); //$NON-NLS-1$
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
		PHPFunctionData constructor = PHPModelUtil.getRealConstructor(projectModel, codeData.getUserData().getFileName(), codeData);
		if(constructor != null) {
			visit(constructor);
		}
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
			buffer.append(" "); //$NON-NLS-1$
		} else {
			// buffer.append("unknown");
			// buffer.append(" ");
		}
		if (codeData.isConst()) {
			buffer.append("const "); //$NON-NLS-1$
		}
		if (codeData.isReference()) {
			buffer.append("&"); //$NON-NLS-1$
		}
		buffer.append("$"); //$NON-NLS-1$
		buffer.append(codeData.getName());
		String defaultValue = codeData.getDefaultValue();
		if (defaultValue != null && defaultValue.length() != 0) {
			buffer.append(" = "); //$NON-NLS-1$
			buffer.append(defaultValue);
		}
	}
}
