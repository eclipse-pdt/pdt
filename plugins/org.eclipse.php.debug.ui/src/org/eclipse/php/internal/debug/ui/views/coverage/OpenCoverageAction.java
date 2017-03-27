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

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;

/**
 * Open code coverage action.
 */
public class OpenCoverageAction extends Action {
	private Object element;
	private CodeCoverageActionGroup group;

	public OpenCoverageAction(final CodeCoverageActionGroup group) {
		super(PHPDebugUIMessages.OpenCoverageAction_0,
				PHPDebugUIPlugin.getImageDescriptor(CodeCoverageSection.CODE_COVERAGE_ICON_PATH));
		this.group = group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.Action#runWithEvent(org.eclipse.swt.widgets.
	 * Event)
	 */
	public void run() {
		final ISourceModule fileData = PHPToolkitUtil.getSourceModule(element);
		if (fileData != null) {
			group.showCoverage(fileData);
		} else {
			group.showCoverage(element);
		}
	}

	public void updateSelection(final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection sSelection = (IStructuredSelection) selection;
			element = sSelection.getFirstElement();
		} else
			element = null;
	}

}