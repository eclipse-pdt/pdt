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
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.php.profile.ui.views.AbstractProfilerFunctionsView;
import org.eclipse.php.profile.ui.views.TreeElement;

/**
 * Open function invocation statistics action.
 */
class OpenFunctionInvocationStatisticsAction extends Action {

	private AbstractProfilerFunctionsView fView;

	public OpenFunctionInvocationStatisticsAction(AbstractProfilerFunctionsView view) {
		super(PHPProfileUIMessages.getString("OpenFunctionInvocationStatisticsAction_0")); //$NON-NLS-1$
		setDescription(PHPProfileUIMessages.getString("OpenFunctionInvocationStatisticsAction_0")); //$NON-NLS-1$
		setToolTipText(PHPProfileUIMessages.getString("OpenFunctionInvocationStatisticsAction_0")); //$NON-NLS-1$
		setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_FUNCTION_STATISTICS));
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
			if (data != null && data instanceof ProfilerFunctionData) {
				fView.openFunctionInvocationStatisticsView((ProfilerFunctionData) data);
			}
		}
	}
}