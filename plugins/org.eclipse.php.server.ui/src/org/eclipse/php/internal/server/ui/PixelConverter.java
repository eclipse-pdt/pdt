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
package org.eclipse.php.internal.server.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Control;

public class PixelConverter {

	private FontMetrics fFontMetrics;

	public PixelConverter(Control control) {
		GC gc = new GC(control);
		gc.setFont(control.getFont());
		fFontMetrics = gc.getFontMetrics();
		gc.dispose();
	}

	/**
	 * see org.eclipse.jface.dialogs.DialogPage#convertHeightInCharsToPixels(int)
	 */
	public int convertHeightInCharsToPixels(int chars) {
		return Dialog.convertHeightInCharsToPixels(fFontMetrics, chars);
	}

	/**
	 * see org.eclipse.jface.dialogs.DialogPage#convertHorizontalDLUsToPixels(int)
	 */
	public int convertHorizontalDLUsToPixels(int dlus) {
		return Dialog.convertHorizontalDLUsToPixels(fFontMetrics, dlus);
	}

	/**
	 * see org.eclipse.jface.dialogs.DialogPage#convertVerticalDLUsToPixels(int)
	 */
	public int convertVerticalDLUsToPixels(int dlus) {
		return Dialog.convertVerticalDLUsToPixels(fFontMetrics, dlus);
	}

	/**
	 * see org.eclipse.jface.dialogs.DialogPage#convertWidthInCharsToPixels(int)
	 */
	public int convertWidthInCharsToPixels(int chars) {
		return Dialog.convertWidthInCharsToPixels(fFontMetrics, chars);
	}

}
