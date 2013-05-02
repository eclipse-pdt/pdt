/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.selectionactions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.action.Action;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.texteditor.IUpdate;

public class StructureSelectHistoryAction extends Action implements IUpdate {
	private PHPStructuredEditor fEditor;
	private SelectionHistory fHistory;

	public StructureSelectHistoryAction(PHPStructuredEditor editor,
			SelectionHistory history) {
		super(Messages.StructureSelectHistoryAction_3);
		setToolTipText(Messages.StructureSelectHistoryAction_4);
		setDescription(Messages.StructureSelectHistoryAction_5);
		Assert.isNotNull(history);
		Assert.isNotNull(editor);
		fHistory = history;
		fEditor = editor;
		update();
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IJavaHelpContextIds.STRUCTURED_SELECTION_HISTORY_ACTION);
	}

	public void update() {
		setEnabled(!fHistory.isEmpty());
	}

	public void run() {
		ISourceRange old = fHistory.getLast();
		if (old != null) {
			try {
				fHistory.ignoreSelectionChanges();
				fEditor.selectAndReveal(old.getOffset(), old.getLength());
			} finally {
				fHistory.listenToSelectionChanges();
			}
		}
	}
}
