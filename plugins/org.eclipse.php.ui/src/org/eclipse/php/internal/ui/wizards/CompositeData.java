/*******************************************************************************
 * Copyright (c) 2009 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards;

import java.util.Observer;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.php.ui.wizards.ICompositeData;
import org.eclipse.swt.widgets.Composite;

public class CompositeData implements ICompositeData {

	private Composite paretnt;
	private IDialogSettings settings;
	private Observer observer;

	public Composite getParentComposite() {
		return paretnt;
	}

	public IDialogSettings getSettings() {
		return settings;
	}

	public void setParetnt(Composite paretnt) {
		this.paretnt = paretnt;
	}

	public void setSettings(IDialogSettings settings) {
		this.settings = settings;
	}

	public Observer getObserver() {
		return observer;
	}

	public void setObserver(Observer observer) {
		this.observer = observer;
	}

}
