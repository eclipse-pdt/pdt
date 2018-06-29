/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.jface.action.Action;

public class ViewAction extends Action {

	private final ViewActionGroup fActionGroup;
	private final int fMode;

	public ViewAction(ViewActionGroup group, int mode) {
		super("", AS_RADIO_BUTTON); //$NON-NLS-1$
		fActionGroup = group;
		fMode = mode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		if (isChecked()) {
			fActionGroup.setMode(fMode);
		}
	}
}
