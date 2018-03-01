/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.hover;

import org.eclipse.dltk.internal.ui.BrowserInformationControl;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IInformationControlExtension4;
import org.eclipse.jface.text.IInformationControlExtension5;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @deprecated
 */
@Deprecated
public class BrowserInformationControl2 extends BrowserInformationControl
		implements IInformationControlExtension4, IInformationControlExtension5 {
	public BrowserInformationControl2(Shell parent, int shellStyle, int style, String statusFieldText) {
		super(parent, JFaceResources.DIALOG_FONT, statusFieldText);
	}

	@Override
	public Point computeSizeConstraints(int widthInChars, int heightInChars) {
		return null;
	}

	@Override
	public boolean containsControl(Control control) {
		do {
			if (control == getShell()) {
				return true;
			}
			if (control instanceof Shell) {
				return false;
			}
			control = control.getParent();
		} while (control != null);
		return false;
	}

	@Override
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return null;
	}

	@Override
	public boolean isVisible() {
		return getShell() != null && !getShell().isDisposed() && getShell().isVisible();
	}
}
