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

public class CompositeFilter extends SimpleFilter {

	private List filters = new ArrayList();

	private IFilterChangeListener myFilterChangeListener = new IFilterChangeListener() {
		public void notifyFilterChanged() {
			CompositeFilter.this.notifyFilterChanged();
		}
	};

	public Object[] filter(Object[] elements) {
		for (Iterator iter = filters.iterator(); iter.hasNext();) {
			IFilter filter = (IFilter) iter.next();
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
