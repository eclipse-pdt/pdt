/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/**
 * TODO header
 */
package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

/**
 * TODO description
 * 
 * @author nir.c
 * 
 */
public class ProjectOutlineLabelProvider extends ScriptExplorerLabelProvider {

	public ProjectOutlineLabelProvider(ScriptExplorerContentProvider cp, IPreferenceStore store) {
		super(cp, store);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof ProjectOutlineGroups) {
			return ((ProjectOutlineGroups) element).getImage();

		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ProjectOutlineGroups) {
			return ((ProjectOutlineGroups) element).getText();
		}
		return super.getText(element);
	}

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof ProjectOutlineGroups) {
			return new StyledString(((ProjectOutlineGroups) element).getText());
		}
		return super.getStyledText(element);
	}

}
