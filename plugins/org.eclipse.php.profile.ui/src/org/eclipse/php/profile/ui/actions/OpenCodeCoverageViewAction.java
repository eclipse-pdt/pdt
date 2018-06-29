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
package org.eclipse.php.profile.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.php.profile.ui.views.AbstractProfilerFunctionsView;
import org.eclipse.php.profile.ui.views.TreeElement;

/**
 * Open code coverage view action.
 */
class OpenCodeCoverageViewAction extends Action {

	private AbstractProfilerFunctionsView fView;

	public OpenCodeCoverageViewAction(AbstractProfilerFunctionsView view) {
		super(PHPProfileUIMessages.getString("OpenCodeCoverageViewAction.0")); //$NON-NLS-1$
		setDescription(PHPProfileUIMessages.getString("OpenCodeCoverageViewAction.0")); //$NON-NLS-1$
		setToolTipText(PHPProfileUIMessages.getString("OpenCodeCoverageViewAction.0")); //$NON-NLS-1$
		setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_CODE_COVERAGE));
		fView = view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		ISelection selection = fView.getViewer().getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			TreeElement element = (TreeElement) ((IStructuredSelection) selection).getFirstElement();
			Object data = element.getData();
			if (data != null && data instanceof ProfilerFileData) {
				fView.openCodeCoverageView((ProfilerFileData) data);
			}
		}
	}
}