/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
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

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;

/**
 * @author guy.g
 *
 */
public class PHPDocTool {

	public static PHPDocBlock createPhpDoc(IModelElement modelElem) {
		return createPhpDoc(modelElem, null);
	}

	public static PHPDocBlock createPhpDoc(PHPCodeData modelElem, String shortDescription) {
		PHPDocBlock block = createDocBlock(modelElem);
		if(shortDescription != null){
			block.setShortDescription(shortDescription);
		}
		return block;
	}
	
	public static PHPDocBlock createPhpDoc(IModelElement modelElem, String shortDescription) {
		PHPDocBlock block = createDocBlock(modelElem);
		if(shortDescription != null){
			block.setShortDescription(shortDescription);
		}
		return block;
	}

	private static PHPDocBlock createDocBlock(IModelElement modelElem) {
		if (modelElem instanceof PHPFunctionData) {
			return createFunctionDocBlock((PHPFunctionData) modelElem);
		}
		if (modelElem instanceof PHPClassData) {
			return createClassDocBlock();
		}
		if (modelElem instanceof PHPFileData) {
			return createFileDocBlock();
		}
		if (modelElem instanceof PHPClassVarData) {
			return createClassVarDocBlock();
		}
		if (modelElem instanceof PHPVariableData) {
			return createVarDocBlock((PHPVariableData) modelElem);
		}
		if (modelElem instanceof PHPConstantData) {
			return createConstantDocBlock();
		}
		if (modelElem instanceof PHPIncludeFileData) {
			return createIncludeDocBlock();
		}
		if (modelElem instanceof PHPClassConstData) {
			return createClassConstantDocBlock();
		}
		return null;
	}
	
	private static PHPDocBlock createDocBlock(PHPCodeData modelElem) {
		if (modelElem instanceof PHPFunctionData) {
			return createFunctionDocBlock((PHPFunctionData) modelElem);
		}
		if (modelElem instanceof PHPClassData) {
			return createClassDocBlock();
		}
		if (modelElem instanceof PHPFileData) {
			return createFileDocBlock();
		}
		if (modelElem instanceof PHPClassVarData) {
			return createClassVarDocBlock();
		}
		if (modelElem instanceof PHPVariableData) {
			return createVarDocBlock((PHPVariableData) modelElem);
		}
		if (modelElem instanceof PHPConstantData) {
			return createConstantDocBlock();
		}
		if (modelElem instanceof PHPIncludeFileData) {
			return createIncludeDocBlock();
		}
		if (modelElem instanceof PHPClassConstData) {
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
		return new PHPDocBlockImp(null, null, tags);
	}

	private static PHPDocBlock createClassDocBlock() {
		return new PHPDocBlockImp(null, null, null);
	}

	private static PHPDocBlock createFileDocBlock() {
		PHPDocTag packageTag = new BasicPHPDocTag(PHPDocTag.PACKAGE, "defaultPackage"); //$NON-NLS-1$
		PHPDocTag authorTag = new BasicPHPDocTag(PHPDocTag.AUTHOR, System.getProperty("user.name")); //$NON-NLS-1$
		PHPDocTag[] tags = new PHPDocTag[] { authorTag, packageTag };
		return new PHPDocBlockImp(null, null, tags);
	}

	private static PHPDocBlock createClassVarDocBlock() {
		BasicPHPDocTag varTag = new BasicPHPDocTag(PHPDocTag.VAR, "unknown_type"); //$NON-NLS-1$
		PHPDocTag[] tags = new PHPDocTag[] { varTag };
		return new PHPDocBlockImp(null, null, tags);
	}

	private static PHPDocBlock createVarDocBlock(PHPVariableData codeData) {
		PHPDocTag globalTag = null;
		if (codeData.isGlobal()) {
			globalTag = new BasicPHPDocTag(PHPDocTag.GLOBAL, codeData.getName());
		}
		PHPDocTag[] tags = null;
		if (globalTag != null) {
			tags = new PHPDocTag[] { globalTag };
		}
		return new PHPDocBlockImp(null, null, tags);
	}

	private static PHPDocBlock createConstantDocBlock() {
		return new PHPDocBlockImp(null, null, null);
	}

	private static PHPDocBlock createIncludeDocBlock() {
		return new PHPDocBlockImp(null, null, null);
	}

	private static PHPDocBlock createClassConstantDocBlock() {
		return new PHPDocBlockImp(null, null, null);
	}

}
