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

package org.eclipse.php.internal.ui.editor.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.phpModel.phpElementData.*;

/**
 * @author guy.g
 *
 */
public class PHPDocTool {

	public static PHPDocBlock createPhpDoc(CodeData codeData, IDocument document) {
		return createPhpDoc(codeData, null, document);
	}

	public static PHPDocBlock createPhpDoc(CodeData codeData, String shortDescription, IDocument document) {
		PHPDocBlock block = createDocBlock(codeData);
		if(shortDescription != null){
			block.setShortDescription(shortDescription);
		}
		return block;
	}

	private static PHPDocBlock createDocBlock(CodeData codeData) {
		if (codeData instanceof PHPFunctionData) {
			return createFunctionDocBlock((PHPFunctionData) codeData);
		}
		if (codeData instanceof PHPClassData) {
			return createClassDocBlock();
		}
		if (codeData instanceof PHPFileData) {
			return createFileDocBlock();
		}
		if (codeData instanceof PHPClassVarData) {
			return createClassVarDocBlock();
		}
		if (codeData instanceof PHPVariableData) {
			return createVarDocBlock((PHPVariableData) codeData);
		}
		if (codeData instanceof PHPConstantData) {
			return createConstantDocBlock();
		}
		if (codeData instanceof PHPIncludeFileData) {
			return createIncludeDocBlock();
		}
		if (codeData instanceof PHPClassConstData) {
			return createClassConstantDocBlock();
		}
		return null;
	}

	private static PHPDocBlock createFunctionDocBlock(PHPFunctionData codeData) {
		PHPFunctionData.PHPFunctionParameter[] parameters = codeData.getParameters();
		List tagList = new ArrayList(parameters.length + 1);
		PHPDocTag paramTag;
		for (int i = 0; i < parameters.length; i++) {
			String param = '$' + parameters[i].getName();
			if (parameters[i].getClassType() != null) {
				param = parameters[i].getClassType() + ' ' + param;
			} else {
				param = "unknown_type " + param; //$NON-NLS-1$
			}
			paramTag = new BasicPHPDocTag(PHPDocTag.PARAM, param);
			tagList.add(paramTag);
		}
		String returnValue = codeData.getReturnType();
		if (returnValue == null) {
			returnValue = "unknown"; //$NON-NLS-1$
		} else if (!returnValue.equals("void")) { //$NON-NLS-1$
			PHPDocTag returnTag = new BasicPHPDocTag(PHPDocTag.RETURN, returnValue);
			tagList.add(returnTag);
		}
		PHPDocTag[] tags = new PHPDocTag[tagList.size()];
		tagList.toArray(tags);
		return new PHPDocBlockImp(null, null, tags, PHPDocBlockImp.FUNCTION_DOCBLOCK);
	}

	private static PHPDocBlock createClassDocBlock() {
		return new PHPDocBlockImp(null, null, null, PHPDocBlockImp.CLASS_DOCBLOCK);
	}

	private static PHPDocBlock createFileDocBlock() {
		PHPDocTag packageTag = new BasicPHPDocTag(PHPDocTag.PACKAGE, "defaultPackage"); //$NON-NLS-1$
		PHPDocTag authorTag = new BasicPHPDocTag(PHPDocTag.AUTHOR, System.getProperty("user.name")); //$NON-NLS-1$
		PHPDocTag[] tags = new PHPDocTag[] { authorTag, packageTag };
		return new PHPDocBlockImp(null, null, tags, PHPDocBlockImp.FILE_DOCBLOCK);
	}

	private static PHPDocBlock createClassVarDocBlock() {
		int id = PHPDocBlock.CLASS_VAR_DOCBLOCK;
		BasicPHPDocTag varTag = new BasicPHPDocTag(PHPDocTag.VAR, "unknown_type"); //$NON-NLS-1$
		PHPDocTag[] tags = new PHPDocTag[] { varTag };
		return new PHPDocBlockImp(null, null, tags, id);
	}

	private static PHPDocBlock createVarDocBlock(PHPVariableData codeData) {
		PHPDocTag globalTag = null;
		int id = PHPDocBlock.VARIABLE_DOCBLOCK;
		if (codeData.isGlobal()) {
			globalTag = new BasicPHPDocTag(PHPDocTag.GLOBAL, codeData.getName());
			id = PHPDocBlock.GLOBAL_VAR_DOCBLOCK;
		}
		PHPDocTag[] tags = null;
		if (globalTag != null) {
			tags = new PHPDocTag[] { globalTag };
		}
		return new PHPDocBlockImp(null, null, tags, id);
	}

	private static PHPDocBlock createConstantDocBlock() {
		return new PHPDocBlockImp(null, null, null, PHPDocBlockImp.DEFINE_DOCBLOCK);
	}

	private static PHPDocBlock createIncludeDocBlock() {
		return new PHPDocBlockImp(null, null, null, PHPDocBlockImp.INCLUDE_FILE_DOCBLOCK);
	}

	private static PHPDocBlock createClassConstantDocBlock() {
		return new PHPDocBlockImp(null, null, null, PHPDocBlockImp.DEFINE_DOCBLOCK);
	}

}
