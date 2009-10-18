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
package org.eclipse.php.internal.ui.util;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class ValuedCombo extends Combo {

	String[] viewedValues;
	private List entryList;

	public ValuedCombo(Composite composite, int style, List entryList) {
		super(composite, style);

		this.entryList = entryList;
		retrieveViewedItemsAsArray();
		this.setItems(viewedValues);
		if (viewedValues.length > 0) {
			this.select(0);
		}
	}

	private void retrieveViewedItemsAsArray() {
		viewedValues = new String[entryList.size()];
		int i = 0;
		for (Iterator entryIterator = entryList.iterator(); entryIterator
				.hasNext(); ++i) {
			Entry entry = (Entry) entryIterator.next();
			viewedValues[i] = entry.getViewedValue();
		}
	}

	public String getSelectionValue() {
		return ((Entry) entryList.get(getSelectionIndex())).getValue();
	}

	public void selectValue(String Value) {
		for (Iterator entryIterator = entryList.iterator(); entryIterator
				.hasNext();) {
			Entry entry = (Entry) entryIterator.next();
			if (entry.getValue().equals(Value)) {
				setText(entry.getViewedValue());
				return;
			}
		}
	}

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
