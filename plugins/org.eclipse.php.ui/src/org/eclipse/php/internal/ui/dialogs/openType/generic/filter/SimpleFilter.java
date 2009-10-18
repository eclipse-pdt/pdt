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

	private ArrayList filterChangeListeners = new ArrayList();

	public Object[] filter(Object[] elements) {
		return elements;
	}

	public void addFilterChangeListener(
			IFilterChangeListener filterChangeListener) {
		filterChangeListeners.add(filterChangeListener);
	}

	public void removeFilterChangeListener(
			IFilterChangeListener filterChangeListener) {
		filterChangeListeners.remove(filterChangeListener);
	}

	public List getFilterChangeListeners() {
		return filterChangeListeners;
	}

	protected void notifyFilterChanged() {
		for (Iterator filterChangeListenerIterator = getFilterChangeListeners()
				.iterator(); filterChangeListenerIterator.hasNext();) {
			IFilterChangeListener filterChangeListener = (IFilterChangeListener) filterChangeListenerIterator
					.next();
			filterChangeListener.notifyFilterChanged();

		}
	}
}
