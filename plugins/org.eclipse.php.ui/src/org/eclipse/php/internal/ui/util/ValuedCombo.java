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
package org.eclipse.php.internal.ui.util;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class ValuedCombo extends Combo {

	String[] viewedValues;
	private List<Entry> entryList;

	public ValuedCombo(Composite composite, int style, List<Entry> entryList) {
		super(composite, style);

		this.entryList = entryList;
		retrieveViewedItemsAsArray();
		this.setItems(viewedValues);
		if (viewedValues.length > 0) {
			this.select(0);
		}
	}

	public List<Entry> getEntryList() {
		return entryList;
	}

	private void retrieveViewedItemsAsArray() {
		viewedValues = new String[entryList.size()];
		int i = 0;
		for (Iterator<Entry> entryIterator = entryList.iterator(); entryIterator.hasNext(); ++i) {
			Entry entry = entryIterator.next();
			viewedValues[i] = entry.getViewedValue();
		}
	}

	public String getSelectionValue() {
		return entryList.get(getSelectionIndex()).getValue();
	}

	public boolean selectValue(String Value) {
		for (Iterator<Entry> entryIterator = entryList.iterator(); entryIterator.hasNext();) {
			Entry entry = entryIterator.next();
			if (entry.getValue().equals(Value)) {
				setText(entry.getViewedValue());
				return true;
			}
		}
		return false;
	}

	@Override
	protected void checkSubclass() {
	}

	public static class Entry {
		private String value;
		private String viewedValue;

		public Entry(String value, String viewedValue) {
			this.value = value;
			this.viewedValue = viewedValue;
		}

		public String getValue() {
			return value;
		}

		public String getViewedValue() {
			return viewedValue;
		}
	}
}
