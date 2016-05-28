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

import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;

public class PHPRefactorQuickMenuAction extends PHPQuickMenuAction {
	public PHPRefactorQuickMenuAction(PHPStructuredEditor editor) {
		super(editor);
		setActionDefinitionId(PHPActionConstants.REFACTOR_QUICK_MENU);
	}

	@Override
	protected PHPQuickMenuCreator getQuickMenuCreator() {
		return new PHPQuickMenuCreator(PHPActionConstants.REFACTOR_QUICK_MENU);
	}
}