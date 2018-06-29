/*******************************************************************************
 * Copyright (c) 2009 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

	@Override
	public Composite getParentComposite() {
		return paretnt;
	}

	@Override
	public IDialogSettings getSettings() {
		return settings;
	}

	public void setParetnt(Composite paretnt) {
		this.paretnt = paretnt;
	}

	public void setSettings(IDialogSettings settings) {
		this.settings = settings;
	}

	@Override
	public Observer getObserver() {
		return observer;
	}

	public void setObserver(Observer observer) {
		this.observer = observer;
	}

}
