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
package org.eclipse.php.internal.ui.outline;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.SuperClassLabelProvider;
import org.eclipse.php.internal.ui.outline.PHPOutlineContentProvider.GroupNode;
import org.eclipse.php.internal.ui.treecontent.PHPTreeNode;
import org.eclipse.php.internal.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.internal.ui.util.PHPElementLabels;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider;

public class PHPOutlineLabelProvider extends AppearanceAwareLabelProvider {
	JFaceNodeLabelProvider phpLabelProvider = new JFaceNodeLabelProvider();

	protected ILabelProvider superClassLabelProviderFragment = new SuperClassLabelProvider(this);

	public Image getImage(Object element) {
		if (element instanceof PHPCodeData) {
			Image image = superClassLabelProviderFragment.getImage(element);
			if (image != null) {
				return image;
			}
			return super.getImage(element);
		} else if (element instanceof GroupNode) {
			return ((GroupNode) element).getImage();
		} else if (element instanceof PHPTreeNode) {
			return ((PHPTreeNode) element).getImage();
		}
		return phpLabelProvider.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof PHPCodeData) {
			String text = superClassLabelProviderFragment.getText(element);
			if (text != null) {
				return text;
			}
			return super.getText(element);
		} else if (element instanceof GroupNode) {
			return ((GroupNode) element).getText();
		} else if (element instanceof PHPTreeNode) {
			return ((PHPTreeNode) element).getText();
		}

		return phpLabelProvider.getText(element);

	}

	public String getTooltipText(Object element) {
		return PHPElementLabels.getTooltipTextLabel(element);
	}
}
