/*******************************************************************************
 * Copyright (c) 2016 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.jface.action.Action;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;

abstract public class PHPQuickMenuAction extends Action {
	protected @Nullable PHPStructuredEditor editor;

	protected abstract PHPQuickMenuCreator getQuickMenuCreator();

	public PHPQuickMenuAction(PHPStructuredEditor editor) {
		this.editor = editor;
	}

	@Override
	public void run() {
		PHPQuickMenuCreator creator = getQuickMenuCreator();
		if (editor != null) {
			creator.setEditor(editor);
		}
		creator.createMenu();
	}
}
