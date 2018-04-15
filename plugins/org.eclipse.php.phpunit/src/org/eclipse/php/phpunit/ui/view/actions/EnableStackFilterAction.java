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
package org.eclipse.php.phpunit.ui.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.PHPUnitPreferenceKeys;
import org.eclipse.php.phpunit.ui.view.FailureTrace;

/**
 * Action to enable/disable stack trace filtering.
 */
public class EnableStackFilterAction extends Action {

	private FailureTrace fView;

	public EnableStackFilterAction(final FailureTrace view) {
		super(PHPUnitMessages.EnableStackFilterAction_Name);
		this.fView = view;

		setDescription(PHPUnitMessages.EnableStackFilterAction_Description);
		setToolTipText(PHPUnitMessages.EnableStackFilterAction_Description);

		setDisabledImageDescriptor(PHPUnitPlugin.getImageDescriptor("dlcl16/cfilter.png")); //$NON-NLS-1$
		setHoverImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/cfilter.png")); //$NON-NLS-1$
		setImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/cfilter.png")); //$NON-NLS-1$

		setChecked(PHPUnitPreferenceKeys.getFilterStack());
	}

	@Override
	public void run() {
		PHPUnitPreferenceKeys.setFilterStack(isChecked());
		fView.refresh();
	}
}
