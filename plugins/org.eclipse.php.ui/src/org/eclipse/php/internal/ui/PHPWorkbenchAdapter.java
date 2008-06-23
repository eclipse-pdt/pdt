/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.IPhpModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.util.PHPElementImageProvider;
import org.eclipse.php.internal.ui.util.PHPElementLabels;
import org.eclipse.ui.model.IWorkbenchAdapter;


/**
 * An imlementation of the IWorkbenchAdapter for IJavaElements.
 */
public class PHPWorkbenchAdapter implements IWorkbenchAdapter {

	protected static final Object[] NO_CHILDREN = new Object[0];

	private PHPElementImageProvider fImageProvider;

	public PHPWorkbenchAdapter() {
		fImageProvider = new PHPElementImageProvider();
	}

	public Object[] getChildren(Object element) {
		Object je = getPHPElement(element);
		if (je != null) {
			return new StandardPHPElementContentProvider().getChildren(element);
		}
		return NO_CHILDREN;
	}

	public ImageDescriptor getImageDescriptor(Object element) {
		Object je = getPHPElement(element);
		if (je instanceof PHPCodeData)
			return fImageProvider.getPHPImageDescriptor((PHPCodeData) je, PHPElementImageProvider.OVERLAY_ICONS | PHPElementImageProvider.SMALL_ICONS);

		return null;

	}

	public String getLabel(Object element) {
		return PHPElementLabels.getTextLabel(getPHPElement(element), PHPElementLabels.ALL_DEFAULT);
	}

	public Object getParent(Object element) {
		return PHPModelUtil.getParent(element);
	}

	private Object getPHPElement(Object element) {
		if (element instanceof PHPCodeData || element instanceof PHPProjectModel || element instanceof PHPWorkspaceModelManager || element instanceof IPhpModel)
			return element;
		return null;
	}
}
