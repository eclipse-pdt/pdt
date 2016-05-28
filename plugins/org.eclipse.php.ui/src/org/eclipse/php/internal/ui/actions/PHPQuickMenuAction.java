/*******************************************************************************
 * Copyright (c) 2016 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
