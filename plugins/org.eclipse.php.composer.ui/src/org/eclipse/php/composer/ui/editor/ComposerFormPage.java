/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
