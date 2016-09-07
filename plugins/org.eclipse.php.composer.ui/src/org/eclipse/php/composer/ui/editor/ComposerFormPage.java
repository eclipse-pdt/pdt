/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.php.composer.ui.editor.composer.ComposerFormEditor;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public class ComposerFormPage extends FormPage {

	protected boolean enabled = true;

	public ComposerFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	public ComposerFormEditor getComposerEditor() {
		return (ComposerFormEditor) getEditor();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void contributeToToolbar(IToolBarManager manager, IManagedForm headerForm) {

	}
}
