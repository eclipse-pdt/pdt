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
package org.eclipse.php.internal.ui.projectOutline;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.SuperClassLabelProvider;
import org.eclipse.php.internal.ui.projectOutline.ProjectOutlineContentProvider.OutlineNode;
import org.eclipse.php.internal.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.internal.ui.util.PHPElementLabels;
import org.eclipse.swt.graphics.Image;

public class ProjectOutlineLabelProvider extends AppearanceAwareLabelProvider {

	protected ILabelProvider superClassLabelProviderFragment = new SuperClassLabelProvider(this);

	public ProjectOutlineLabelProvider(int textFlags, int imageFlags, ITreeContentProvider cp) {
		super(textFlags, imageFlags);
	}

	public String getText(Object element) {
		if (element instanceof PHPCodeData) {
			String text = superClassLabelProviderFragment.getText(element);
			if (text != null) {
				return text;
			}
			return super.getText(element);
		}
		if (element instanceof OutlineNode) {
			OutlineNode outlineNode = (OutlineNode) element;
			return outlineNode.getText();
		}
		return super.getText(element);
	}

	public String getTooltipText(Object element) {
		String postfix = ""; //$NON-NLS-1$
		if (element instanceof PHPCodeData) {
			PHPCodeData codeData = (PHPCodeData) element;
			UserData userData = codeData.getUserData();
			if (userData != null)
				postfix = " (" + userData.getFileName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		String text = PHPElementLabels.getTooltipTextLabel(element);
		return text + postfix;
	}

	public Image getImage(Object element) {
		if (element instanceof PHPCodeData) {
			Image image = superClassLabelProviderFragment.getImage(element);
			if (image != null) {
				return image;
			}
		}
		return super.getImage(element);
	}
}
