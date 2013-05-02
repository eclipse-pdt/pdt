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
package org.eclipse.php.internal.ui.manualView;

import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ManualPart extends ViewPart {

	/**
	 * Availability checking cache.
	 */
	private static boolean fgIsAvailable = false;
	private static boolean fgAvailabilityChecked = false;

	public ManualPart() {
	}

	/**
	 * Tells whether the SWT Browser widget and hence this information control
	 * is available.
	 * 
	 * @param parent
	 *            the parent component used for checking or <code>null</code> if
	 *            none
	 * @return <code>true</code> if this control is available
	 */
	public static boolean isAvailable(Composite parent) {
		if (!fgAvailabilityChecked) {
			try {
				Browser browser = new Browser(parent, SWT.NONE);
				browser.dispose();
				fgIsAvailable = true;
			} catch (SWTError er) {
				fgIsAvailable = false;
			} finally {
				fgAvailabilityChecked = true;
			}
		}

		return fgIsAvailable;
	}

	public void createPartControl(Composite parent) {
		Browser browser = new Browser(parent, SWT.NONE);
		browser.setUrl(PHPUIMessages.ManualPart_URL); 
	}

	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
