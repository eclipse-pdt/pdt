/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class ComboPart {

	protected Control combo;

	public ComboPart() {
	}

	public void addSelectionListener(SelectionListener listener) {
		if (combo instanceof Combo) {
			((Combo) combo).addSelectionListener(listener);
		} else {
			((CCombo) combo).addSelectionListener(listener);
		}
	}

	public int indexOf(String item) {
		if (combo instanceof Combo) {
			return ((Combo) combo).indexOf(item);
		}

		return ((CCombo) combo).indexOf(item);
	}

	public void addModifyListener(ModifyListener listener) {
		if (combo instanceof Combo) {
			((Combo) combo).addModifyListener(listener);
		} else {
			((CCombo) combo).addModifyListener(listener);
		}
	}

	public void createControl(Composite parent, FormToolkit toolkit, int style) {
		if (toolkit.getBorderStyle() == SWT.BORDER) {
			combo = new Combo(parent, style | SWT.BORDER);
		} else {
			combo = new CCombo(parent, style | SWT.FLAT);
		}
		toolkit.adapt(combo, true, false);
	}

	public Control getControl() {
		return combo;
	}

	public int getSelectionIndex() {
		if (combo instanceof Combo) {
			return ((Combo) combo).getSelectionIndex();
		}
		return ((CCombo) combo).getSelectionIndex();
	}

	public void add(String item, int index) {
		if (combo instanceof Combo) {
			((Combo) combo).add(item, index);
		} else {
			((CCombo) combo).add(item, index);
		}
	}

	public void add(String item) {
		if (combo instanceof Combo) {
			((Combo) combo).add(item);
		} else {
			((CCombo) combo).add(item);
		}
	}

	/**
	 * @param index
	 */
	public void remove(int index) {
		// Ensure the index is valid
		if ((index < 0) || (index >= getItemCount())) {
			return;
		}
		// Remove the item from the specified index
		if (combo instanceof Combo) {
			((Combo) combo).remove(index);
		} else {
			((CCombo) combo).remove(index);
		}
	}

	public void select(int index) {
		if (combo instanceof Combo) {
			((Combo) combo).select(index);
		} else {
			((CCombo) combo).select(index);
		}
	}

	public String getSelection() {
		if (combo instanceof Combo) {
			return ((Combo) combo).getText().trim();
		}
		return ((CCombo) combo).getText().trim();
	}

	public void setText(String text) {
		if (combo instanceof Combo) {
			((Combo) combo).setText(text);
		} else {
			((CCombo) combo).setText(text);
		}
	}

	public void setItems(String[] items) {
		if (combo instanceof Combo) {
			((Combo) combo).setItems(items);
		} else {
			((CCombo) combo).setItems(items);
		}
	}

	public void setEnabled(boolean enabled) {
		if (combo instanceof Combo) {
			((Combo) combo).setEnabled(enabled);
		} else {
			((CCombo) combo).setEnabled(enabled);
		}
	}

	public int getItemCount() {
		if (combo instanceof Combo) {
			return ((Combo) combo).getItemCount();
		}
		return ((CCombo) combo).getItemCount();
	}

	public String[] getItems() {
		if (combo instanceof Combo) {
			return ((Combo) combo).getItems();
		}
		return ((CCombo) combo).getItems();
	}

	public void setVisibleItemCount(int count) {
		if (combo instanceof Combo) {
			((Combo) combo).setVisibleItemCount(count);
		} else {
			((CCombo) combo).setVisibleItemCount(count);
		}
	}
}
