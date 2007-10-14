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
package org.eclipse.php.internal.ui.phpCodeData;

import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.ui.actions.filters.IActionFilterContributor;

public class AddPHPDocActionFilterContributor implements IActionFilterContributor {

	public boolean testAttribute(Object target, String name, String value) {
		if (!(target instanceof PHPClassData) && !(target instanceof PHPFileData) && !(target instanceof PHPFunctionData) && !(target instanceof PHPConstantData) && !(target instanceof PHPVariableData) && !(target instanceof PHPClassConstData) && !(target instanceof PHPIncludeFileData)) {
			return false;
		}
		
		PHPCodeData phpCodeData = (PHPCodeData)target;
		return phpCodeData.getUserData() != null;
	}
}
