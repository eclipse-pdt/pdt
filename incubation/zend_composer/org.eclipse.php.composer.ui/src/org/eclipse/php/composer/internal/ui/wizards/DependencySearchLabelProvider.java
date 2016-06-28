/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.wizards;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.php.composer.core.model.IPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author Michal Niewrzal, 2014
 * 
 */
public class DependencySearchLabelProvider extends LabelProvider implements IStyledLabelProvider {

	private static final int MAX_DESCRIPTION_LENGTH = 150;

	private static final Styler BOLD_STYLER = new Styler() {
		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.font = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
		}
	};

	private static final Styler GRAY_STYLER = new Styler() {
		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
		}
	};

	@Override
	public String getText(Object element) {
		IPackage p = (IPackage) element;
		return p.getName();
	}

	@Override
	public StyledString getStyledText(Object element) {
		StyledString styledString = new StyledString(getText(element), BOLD_STYLER);
		IPackage p = (IPackage) element;
		if (p.getDescription() != null && !p.getDescription().isEmpty()) {
			String description = p.getDescription();
			if (description.length() > MAX_DESCRIPTION_LENGTH) {
				description = description.substring(0, MAX_DESCRIPTION_LENGTH);
			}
			styledString.append(" " + description, GRAY_STYLER); //$NON-NLS-1$
		}
		return styledString;
	}

}
