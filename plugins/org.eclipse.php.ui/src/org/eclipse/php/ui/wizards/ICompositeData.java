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
