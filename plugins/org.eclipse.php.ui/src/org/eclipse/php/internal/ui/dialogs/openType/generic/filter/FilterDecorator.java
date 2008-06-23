/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.dialogs.openType.generic.filter;


public class FilterDecorator extends SimpleFilter {

	private IFilter filter;

	private IFilterChangeListener myFilterChangeListener = new IFilterChangeListener() {
		public void notifyFilterChanged() {
			FilterDecorator.this.notifyFilterChanged();
		}
	};

	public FilterDecorator(IFilter filter) {
		this.filter = filter;
		filter.addFilterChangeListener(myFilterChangeListener);
	}

	public Object[] filter(Object[] elements) {
		return filter.filter(elements);
	}

	protected IFilter getFilter() {
		return filter;
	}
}
