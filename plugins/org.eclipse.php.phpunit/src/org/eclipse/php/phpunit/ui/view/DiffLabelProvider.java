/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.php.phpunit.model.elements.PHPUnitTestCase;
import org.eclipse.php.phpunit.model.providers.DiffLine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class DiffLabelProvider extends ColumnLabelProvider {

	private final Color minusBackground = new Color(Display.getDefault(), 255, 255, 165);
	private final Color plusBackground = new Color(Display.getDefault(), 220, 245, 185);
	private final Color minusForeground = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
	private final Color plusForeground = Display.getDefault().getSystemColor(SWT.COLOR_DARK_MAGENTA);
	private final Color sameForeground = Display.getDefault().getSystemColor(SWT.COLOR_DARK_BLUE);

	public DiffLabelProvider() {
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof DiffLine) {
			return element.toString();
		}
		if (element instanceof PHPUnitTestCase) {
			return ((PHPUnitTestCase) element).getException().getMessage();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public Image getImage(final Object element) {
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof DiffLine) {
			final String text = element.toString();
			if (text.contains("=>")) { //$NON-NLS-1$
				if (text.startsWith("+")) { //$NON-NLS-1$
					return plusForeground;
				} else if (text.startsWith("-")) { //$NON-NLS-1$
					return minusForeground;
				} else {
					return sameForeground;
				}
			}
		}
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		if (element instanceof DiffLine) {
			final String text = element.toString();
			if (text.contains("=>")) { //$NON-NLS-1$
				if (text.startsWith("+")) { //$NON-NLS-1$
					return plusBackground;
				} else if (text.startsWith("-")) { //$NON-NLS-1$
					return minusBackground;
				}
			}
		}
		return null;
	}

}
