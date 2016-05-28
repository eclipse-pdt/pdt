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

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.wst.sse.ui.internal.IStructuredTextEditorActionConstants;

public class PHPSourceQuickMenuAction extends PHPQuickMenuAction {
	public PHPSourceQuickMenuAction(PHPStructuredEditor editor) {
		super(editor);
		setActionDefinitionId(PHPActionConstants.SOURCE_QUICK_MENU);
	}

	@Override
	protected PHPQuickMenuCreator getQuickMenuCreator() {
		return new PHPQuickMenuCreator(PHPActionConstants.SOURCE_QUICK_MENU) {
			@Override
			protected void fillMenu(IMenuManager menu) {
				menu.add(new Separator(IStructuredTextEditorActionConstants.SOURCE_BEGIN));
				menu.add(new Separator(IStructuredTextEditorActionConstants.SOURCE_ADDITIONS));
				menu.add(new Separator(IStructuredTextEditorActionConstants.SOURCE_END));
				super.fillMenu(menu);
			}
		};
	}

}
