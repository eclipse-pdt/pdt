/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.server.ui;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

/**
 * A closable wizard dialog.
 */
public class ClosableWizardDialog extends WizardDialog {
	/**
	 * Constructor for ClosableWizardDialog.
	 * 
	 * @param shell
	 * @param wizard
	 */
	public ClosableWizardDialog(Shell shell, IWizard wizard) {
		super(shell, wizard);
	}

	/**
	 * The Finish button has been pressed.
	 */
	public void finishPressed() {
		super.finishPressed();
	}

	@Override
	protected Point getInitialSize() {
		Point size = super.getInitialSize();
		// Make some more space between dialog bottom pane
		size.y += 50;
		return size;
	}

}