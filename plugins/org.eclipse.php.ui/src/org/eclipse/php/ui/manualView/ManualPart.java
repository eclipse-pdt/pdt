/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.manualView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ManualPart extends ViewPart {

	public ManualPart() {
	}

	public void createPartControl(Composite parent) {
		Browser browser=new Browser(parent,SWT.NONE);
		browser.setUrl("http://www.eclipse.org");
	}

	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
