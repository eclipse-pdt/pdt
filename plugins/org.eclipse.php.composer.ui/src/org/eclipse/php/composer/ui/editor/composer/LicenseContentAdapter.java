/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor.composer;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class LicenseContentAdapter extends TextContentAdapter {

	@Override
	public String getControlContents(Control control) {
		String text = ((Text) control).getText();
		String[] chunks = text.split(","); //$NON-NLS-1$
		return chunks[chunks.length - 1].trim();
	}

	@Override
	public void setControlContents(Control control, String text, int cursorPosition) {

		String id = text.replaceAll(".+\\((.+)\\)$", "$1"); //$NON-NLS-1$ //$NON-NLS-2$

		String val = ((Text) control).getText();
		String[] chunks = val.split(","); //$NON-NLS-1$
		chunks[chunks.length - 1] = id;
		val = StringUtils.join(chunks, ", "); //$NON-NLS-1$
		cursorPosition = val.length();

		((Text) control).setText(val);
		((Text) control).setSelection(cursorPosition, cursorPosition);
	}
}