/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.stepFilter;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.internal.debug.core.preferences.stepFilters.DebugStepFilter;
import org.eclipse.php.internal.debug.core.preferences.stepFilters.IStepFilterTypes;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for Debug Step Filter objects
 * 
 * @author yaronm
 */
public class FilterLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	/**
	 * @see ITableLabelProvider#getColumnText(Object, int)
	 */
	public String getColumnText(Object object, int column) {
		String text = ""; //$NON-NLS-1$
		if (column == 0) {
			DebugStepFilter filter = (DebugStepFilter) object;
			text = filter.getPath();
			if (filter.isReadOnly()) {
				text += PHPDebugUIMessages.FilterLabelProvider_readOnly;
			}
			return text;
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element) {
		String text = ""; //$NON-NLS-1$
		DebugStepFilter filter = (DebugStepFilter) element;
		text = filter.getPath();
		if (filter.isReadOnly()) {
			text += PHPDebugUIMessages.FilterLabelProvider_readOnly;
		}
		return text;
	}

	/**
	 * @see ITableLabelProvider#getColumnImage(Object, int)
	 */
	public Image getColumnImage(Object object, int column) {
		DebugStepFilter filter = (DebugStepFilter) object;
		switch (filter.getType()) {
		case IStepFilterTypes.PHP_PROJECT:
			return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_PROJECT);
		case IStepFilterTypes.PHP_PROJECT_FOLDER:
			return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_FOLDER);
		case IStepFilterTypes.PHP_PROJECT_FILE:
			return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_FILE);

		case IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY:
		case IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY_FILE:
		case IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY_FOLDER:
			return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);

		case IStepFilterTypes.PHP_INCLUDE_PATH_VAR:
		case IStepFilterTypes.PHP_INCLUDE_PATH_VAR_FILE:
		case IStepFilterTypes.PHP_INCLUDE_PATH_VAR_FOLDER:
			return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_ENV_VAR);
		}
		return null;
	}
}
