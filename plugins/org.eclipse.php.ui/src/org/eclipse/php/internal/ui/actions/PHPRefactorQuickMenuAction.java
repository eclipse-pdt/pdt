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