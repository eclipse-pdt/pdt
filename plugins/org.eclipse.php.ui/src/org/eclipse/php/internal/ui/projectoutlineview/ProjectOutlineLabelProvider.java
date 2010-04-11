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
/**
 * TODO header
 */
package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Image;

/**
 * TODO description
 * 
 * @author nir.c
 * 
 */
public class ProjectOutlineLabelProvider extends ScriptExplorerLabelProvider {

	public ProjectOutlineLabelProvider(ScriptExplorerContentProvider cp,
			IPreferenceStore store) {
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

}
