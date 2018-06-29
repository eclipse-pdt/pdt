/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.views.coverage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Code coverage view action group.
 */
public class CodeCoverageViewActionGroup extends ActionGroup {

	private NextEventAction fNextEventAction;
	private PrevEventAction fPrevEventAction;
	private CodeCoverageView fView;

	public CodeCoverageViewActionGroup(CodeCoverageView view) {
		fView = view;
		fNextEventAction = new NextEventAction();
		fPrevEventAction = new PrevEventAction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.
	 * IActionBars )
	 */
	@Override
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
		IToolBarManager toolbarManager = actionBars.getToolBarManager();
		toolbarManager.add(fNextEventAction);
		toolbarManager.add(fPrevEventAction);
		toolbarManager.add(new Separator());
	}

	class NextEventAction extends Action {
		public NextEventAction() {
			super(PHPDebugUIMessages.CodeCoverageViewActionGroup_0);
			setDescription(PHPDebugUIMessages.CodeCoverageViewActionGroup_1);
			setToolTipText(PHPDebugUIMessages.CodeCoverageViewActionGroup_2);
			setImageDescriptor(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_EVIEW_EVENT_NEXT));
		}

		@Override
		public void run() {
			fView.getViewer().goToNextLine();
		}
	}

	class PrevEventAction extends Action {
		public PrevEventAction() {
			super(PHPDebugUIMessages.CodeCoverageViewActionGroup_3);
			setDescription(PHPDebugUIMessages.CodeCoverageViewActionGroup_4);
			setToolTipText(PHPDebugUIMessages.CodeCoverageViewActionGroup_5);
			setImageDescriptor(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_EVIEW_EVENT_PREV));
		}

		@Override
		public void run() {
			fView.getViewer().goToPreviousLine();
		}
	}
}
