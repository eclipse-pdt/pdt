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
package org.eclipse.php.ui.wizards;

import java.util.Observer;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;

/**
 * The basic added composite
 */
public interface ICompositeData {

	public Composite getParentComposite();

	public IDialogSettings getSettings();

	public Observer getObserver();

}
