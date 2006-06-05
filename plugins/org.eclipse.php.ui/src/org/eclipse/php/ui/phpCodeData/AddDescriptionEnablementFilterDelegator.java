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
package org.eclipse.php.ui.phpCodeData;

import org.eclipse.php.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.core.phpModel.phpElementData.PHPIncludeFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.ui.generic.actionFilter.ActionFilterBooleanDelegator;

public class AddDescriptionEnablementFilterDelegator extends ActionFilterBooleanDelegator {

	protected boolean test(Object target) {
		if (!(target instanceof PHPClassData) && !(target instanceof PHPFileData) && !(target instanceof PHPFunctionData) && !(target instanceof PHPConstantData) && !(target instanceof PHPVariableData) && !(target instanceof PHPClassConstData) && !(target instanceof PHPIncludeFileData)) {
			return false;
		}
		
		PHPCodeData phpCodeData = (PHPCodeData)target;
		return phpCodeData.getUserData() != null;
	}
}
