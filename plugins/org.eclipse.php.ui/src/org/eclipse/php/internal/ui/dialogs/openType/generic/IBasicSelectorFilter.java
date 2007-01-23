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
package org.eclipse.php.internal.ui.dialogs.openType.generic;

import java.util.List;

import org.eclipse.php.internal.ui.dialogs.openType.generic.filter.IFilterChangeListener;


public interface IBasicSelectorFilter {

	public boolean select(Object element);

	public void addFilterChangeListener(IFilterChangeListener filterChangeListener);
	public void removeFilterChangeListener(IFilterChangeListener filterChangeListener);
	public List getFilterChangeListeners();}
