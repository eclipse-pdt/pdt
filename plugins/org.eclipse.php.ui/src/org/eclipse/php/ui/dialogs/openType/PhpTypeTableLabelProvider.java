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
package org.eclipse.php.ui.dialogs.openType;

import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.dialogs.openType.generic.IBasicSelectorLabelProvider;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;


public class PhpTypeTableLabelProvider implements IBasicSelectorLabelProvider {

	private Image classImage;
	private Image constantImage;
	private Image functionImage;

	public PhpTypeTableLabelProvider() {
		classImage = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_OBJS_CLASS);
		functionImage = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_MISC_PUBLIC);
		constantImage = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_CONSTANT);
	}

	public Image getElementImage(Object element) {
		if (element instanceof PHPClassData) {
			return classImage;
		}

		if (element instanceof PHPFunctionData) {
			return functionImage;
		}

		if (element instanceof PHPConstantData | element instanceof PHPClassConstData) {
			return constantImage;
		}

		//assert false;
		return null;
	}

	public String getElementName(Object element) {
		CodeData codeData = (CodeData)element;
		return codeData.getName();
	}

	public String getElementDescription(Object element) {
		return element.toString();
	}
}
