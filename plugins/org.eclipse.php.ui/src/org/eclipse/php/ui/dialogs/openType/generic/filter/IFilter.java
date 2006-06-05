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
package org.eclipse.php.ui.dialogs.openType.generic.filter;

import java.util.List;

public interface IFilter {

	public Object[] filter(Object[] elements);

	public void addFilterChangeListener(IFilterChangeListener filterChangeListener);
	public void removeFilterChangeListener(IFilterChangeListener filterChangeListener);
	public List getFilterChangeListeners();
}
