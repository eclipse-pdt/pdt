/*******************************************************************************
 * Copyright (c) 2017 PDT Extension Group and others.
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

import org.eclipse.php.composer.api.objects.Autoload;

public class AutoloadDevPage extends AbstractAutoloadPage {

	public AutoloadDevPage(ComposerFormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	public final static String ID = "org.eclipse.php.composer.ui.editor.composer.AutoloadDevPage"; //$NON-NLS-1$

	@Override
	protected Autoload getAutoload() {
		return getComposerEditor().getComposerPackge().getAutoloadDev();
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);

		if (active) {
			editor.getHeaderForm().getForm().setText(Messages.AutoloadDevPage_Title);
		}
	}

}