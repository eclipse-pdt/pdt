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
package org.eclipse.php.internal.ui.dialogs.openType.generic.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleFilter implements IFilter {

	private List<IFilterChangeListener> filterChangeListeners = new ArrayList<>();

	@Override
	public Object[] filter(Object[] elements) {
		return elements;
	}

	@Override
	public void addFilterChangeListener(IFilterChangeListener filterChangeListener) {
		filterChangeListeners.add(filterChangeListener);
	}

	@Override
	public void removeFilterChangeListener(IFilterChangeListener filterChangeListener) {
		filterChangeListeners.remove(filterChangeListener);
	}

	@Override
	public List<IFilterChangeListener> getFilterChangeListeners() {
		return filterChangeListeners;
	}

	protected void notifyFilterChanged() {
		for (Iterator<IFilterChangeListener> filterChangeListenerIterator = getFilterChangeListeners()
				.iterator(); filterChangeListenerIterator.hasNext();) {
			IFilterChangeListener filterChangeListener = filterChangeListenerIterator.next();
			filterChangeListener.notifyFilterChanged();

		}
	}
}
