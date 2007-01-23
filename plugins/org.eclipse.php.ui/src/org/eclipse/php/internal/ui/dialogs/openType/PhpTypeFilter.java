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
package org.eclipse.php.internal.ui.dialogs.openType;

import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.ui.dialogs.openType.filter.IPhpTypeFilterReadModel;
import org.eclipse.php.internal.ui.dialogs.openType.filter.IPhpTypeFilterWriteModel;
import org.eclipse.php.internal.ui.dialogs.openType.generic.filter.ElementSpecificFilter;

public class PhpTypeFilter extends ElementSpecificFilter implements IPhpTypeFilterWriteModel, IPhpTypeFilterReadModel {

	private boolean selectClasses = true;
	private boolean selectFunctions = true;
	private boolean selectConstants = true;
	
	public boolean select(Object element) {
		if (selectClasses && selectFunctions && selectConstants) {
			return true;
		}
		if (element instanceof PHPClassData) {
			return selectClasses;
		}

		if (element instanceof PHPFunctionData) {
			return selectFunctions;
		}

		if (element instanceof PHPConstantData | element instanceof PHPClassConstData) {
			return selectConstants;
		}

		//assert false;
		return false;
	}

	public void setSelectClasses(boolean select) {
		this.selectClasses = select;
		this.notifyFilterChanged();
	}

	public void setSelectFunctions(boolean select) {
		this.selectFunctions = select;
		this.notifyFilterChanged();
	}

	public void setSelectConstants(boolean select) {
		this.selectConstants = select;
		this.notifyFilterChanged();
	}

	public boolean getSelectClasss() {
		return this.selectClasses;
	}

	public boolean getSelectFunctions() {
		return selectFunctions;
	}

	public boolean getSelectConstants() {
		return selectConstants;
	}
}
