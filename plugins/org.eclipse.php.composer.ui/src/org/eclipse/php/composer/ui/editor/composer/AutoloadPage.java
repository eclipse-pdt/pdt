/*******************************************************************************
 * Copyright (c) 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor.composer;

import org.eclipse.php.composer.api.objects.Autoload;

public class AutoloadPage extends AbstractAutoloadPage {

	public AutoloadPage(ComposerFormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	public final static String ID = "org.eclipse.php.composer.ui.editor.composer.AutoloadPage"; //$NON-NLS-1$

	@Override
	protected Autoload getAutoload() {
		return getComposerEditor().getComposerPackge().getAutoload();
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);

		if (active) {
			editor.getHeaderForm().getForm().setText(Messages.AutoloadPage_Title);
		}
	}

}