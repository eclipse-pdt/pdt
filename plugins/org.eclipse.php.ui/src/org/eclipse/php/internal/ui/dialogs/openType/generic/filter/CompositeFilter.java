/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.dialogs.openType.generic.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompositeFilter extends SimpleFilter {

	private List<IFilter> filters = new ArrayList<>();

	private IFilterChangeListener myFilterChangeListener = new IFilterChangeListener() {
		@Override
		public void notifyFilterChanged() {
			CompositeFilter.this.notifyFilterChanged();
		}
	};

	@Override
	public Object[] filter(Object[] elements) {
		for (Iterator<IFilter> iter = filters.iterator(); iter.hasNext();) {
			IFilter filter = iter.next();
			elements = filter.filter(elements);
		}

		return elements;
	}

	public void addFilter(IFilter filter) {
		filters.add(filter);
		filter.addFilterChangeListener(myFilterChangeListener);
		notifyFilterChanged();
	}
}
