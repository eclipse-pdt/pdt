/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
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
			this.setHelpListener(new HelpListener() {
				public void helpRequested(HelpEvent arg0) {
					// TODO - help context
				}
			});
			setImageDescriptor(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_EVIEW_EVENT_NEXT));
		}

		public void run() {
			fView.getViewer().goToNextLine();
		}
	}

	class PrevEventAction extends Action {
		public PrevEventAction() {
			super(PHPDebugUIMessages.CodeCoverageViewActionGroup_3);
			setDescription(PHPDebugUIMessages.CodeCoverageViewActionGroup_4);
			setToolTipText(PHPDebugUIMessages.CodeCoverageViewActionGroup_5);
			this.setHelpListener(new HelpListener() {
				public void helpRequested(HelpEvent arg0) {
					// TODO - help context
				}
			});
			setImageDescriptor(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_EVIEW_EVENT_PREV));
		}

		public void run() {
			fView.getViewer().goToPreviousLine();
		}
	}
}
