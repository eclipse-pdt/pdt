/**
 * Copyright (c) 2007 Zend Technologies
 * 
 */
package org.eclipse.php.internal.core.documentModel.dom;

import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;

/**
 * @author seva
 *
 */
public class Utils {

	// return php model item  at offset
	public static PHPCodeData getPHPCodeData(NodeImpl node, int offset) {
		String location = node.getModel().getBaseLocation();

		PHPFileData fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(location);
		if (fileData == null)
			return null;

		PHPConstantData[] constants = fileData.getConstants();
		if (constants != null)
			for (int i = 0; i < constants.length; i++)
				if (Utils.isInside(offset, constants[i]))
					return constants[i];

		PHPFunctionData[] functions = fileData.getFunctions();
		if (functions != null)
			for (int i = 0; i < functions.length; i++)
				if (Utils.isInside(offset, functions[i]))
					return functions[i];

		PHPClassData[] classes = fileData.getClasses();
		if (classes != null)
			for (int i = 0; i < classes.length; i++)
				if (Utils.isInside(offset, classes[i])) {
					PHPClassVarData[] vars = classes[i].getVars();
					for (int j = 0; j < vars.length; j++)
						if (Utils.isInside(offset, vars[j]))
							return vars[j];
					PHPClassConstData[] consts = classes[i].getConsts();
					for (int j = 0; j < consts.length; j++)
						if (Utils.isInside(offset, consts[j]))
							return consts[j];
					functions = classes[i].getFunctions();
					for (int j = 0; j < functions.length; j++)
						if (Utils.isInside(offset, functions[j]))
							return functions[j];
					return classes[i];
				}
		return fileData;

	}

	public static boolean isInside(int offset, PHPCodeData codeData) {
		UserData userData = codeData.getUserData();
		if (userData == null)
			return false;
		if (offset >= userData.getStartPosition() && offset <= userData.getEndPosition())
			return true;
		return false;
	}

}
