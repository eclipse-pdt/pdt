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
package org.eclipse.php.internal.ui.dialogs.openType;

import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.dialogs.openType.filter.IPhpTypeFilterReadModel;
import org.eclipse.php.internal.ui.dialogs.openType.filter.IPhpTypeFilterWriteModel;
import org.eclipse.php.internal.ui.dialogs.openType.generic.filter.ElementSpecificFilter;

public class PhpTypeFilter extends ElementSpecificFilter implements IPhpTypeFilterWriteModel, IPhpTypeFilterReadModel {

	private static final int USE_CLASSES = 1 << 1;
	private static final int USE_FUNCTION = 1 << 2;
	private static final int USE_CONSTANTS = 1 << 3;

	private static final String OPEN_PHP_ELEMENT_PREFERENCE = "open_php_element_preference";

	static {
		PHPUiPlugin.getDefault().getPreferenceStore().setDefault(OPEN_PHP_ELEMENT_PREFERENCE, USE_CLASSES | USE_FUNCTION | USE_CONSTANTS);
	}

	private int state = PHPUiPlugin.getDefault().getPreferenceStore().getInt(OPEN_PHP_ELEMENT_PREFERENCE);

	public boolean select(Object element) {
		if ((state & (USE_CLASSES | USE_FUNCTION | USE_CONSTANTS)) != 0) {
			return true;
		}
		if (element instanceof PHPClassData) {
			return ((state & USE_CLASSES) != 0);
		}

		if (element instanceof PHPFunctionData) {
			return ((state & USE_FUNCTION) != 0);
		}

		if (element instanceof PHPConstantData | element instanceof PHPClassConstData) {
			return ((state & USE_CONSTANTS) != 0);
		}

		//assert false;
		return false;
	}

	public void setSelectClasses(boolean select) {
		valueChanged(select, USE_CLASSES);
	}

	public void setSelectFunctions(boolean select) {
		valueChanged(select, USE_FUNCTION);
	}

	public void setSelectConstants(boolean select) {
		valueChanged(select, USE_CONSTANTS);
	}

	private void valueChanged(boolean select, int value) {
		if (select) {
			state |= value;
		} else {
			state &= ~value;
		}
		PHPUiPlugin.getDefault().getPreferenceStore().setValue(OPEN_PHP_ELEMENT_PREFERENCE, state);
		this.notifyFilterChanged();
	}

	public boolean getSelectClasss() {
		return ((state & USE_CLASSES) != 0);
	}

	public boolean getSelectFunctions() {
		return ((state & USE_FUNCTION) != 0);
	}

	public boolean getSelectConstants() {
		return ((state & USE_CONSTANTS) != 0);
	}
}
