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
package org.eclipse.php.ui.projectOutline;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.ui.projectOutline.ProjectOutlineContentProvider.OutlineNode;
import org.eclipse.php.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.ui.util.PHPElementLabels;
import org.eclipse.php.ui.util.TreeHierarchyLayoutProblemsDecorator;

public class ProjectOutlineLabelProvider extends AppearanceAwareLabelProvider {

	private TreeHierarchyLayoutProblemsDecorator fProblemDecorator;

	public ProjectOutlineLabelProvider(int textFlags, int imageFlags, ITreeContentProvider cp) {
		super(textFlags, imageFlags);
		fProblemDecorator = new TreeHierarchyLayoutProblemsDecorator();
		addLabelDecorator(fProblemDecorator);
	}

	public String getText(Object element) {
		if (element instanceof OutlineNode) {
			OutlineNode outlineNode = (OutlineNode) element;
			return outlineNode.getText();
		}
		return super.getText(element);
	}

	public String getTooltipText(Object element) {
		String description = "";
		String postfix = "";
		if (element instanceof PHPCodeData) {
			PHPCodeData codeData = (PHPCodeData) element;
			description = codeData.getDescription();
			UserData userData = codeData.getUserData();
			if (userData != null)
				postfix = "(" + userData.getFileName() + ")";
		}
		String text = PHPElementLabels.getTooltipTextLabel(element);
		return description + "" + text + postfix;
	}

}
