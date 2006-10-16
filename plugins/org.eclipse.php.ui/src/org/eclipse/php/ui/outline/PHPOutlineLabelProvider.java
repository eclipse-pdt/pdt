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
package org.eclipse.php.ui.outline;

import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.ui.outline.PHPOutlineContentProvider.GroupNode;
import org.eclipse.php.ui.treecontent.PHPTreeNode;
import org.eclipse.php.ui.util.PHPElementLabels;
import org.eclipse.php.ui.util.PHPUILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider;

public class PHPOutlineLabelProvider extends JFaceNodeLabelProvider {
	PHPUILabelProvider phpLabelProvider = new PHPUILabelProvider();

	public Image getImage(Object element) {
		if (element instanceof PHPCodeData) {
			return phpLabelProvider.getImage(element);
		} else if (element instanceof GroupNode) {
			return ((GroupNode) element).getImage();
		} else if (element instanceof PHPTreeNode) {
			return ((PHPTreeNode) element).getImage();
		}
		return super.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof PHPCodeData)
			return phpLabelProvider.getText(element);
		else if (element instanceof GroupNode) {
			return ((GroupNode) element).getText();
		} else if (element instanceof PHPTreeNode) {
			return ((PHPTreeNode) element).getText();
		}

		return super.getText(element);

	}

	public String getTooltipText(Object element) {
		return PHPElementLabels.getTooltipTextLabel(element);
	}
}
