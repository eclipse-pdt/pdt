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
package org.eclipse.php.internal.debug.ui.preferences.phps;

import java.util.*;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * A composite that displays installed PHP's in a combo box, with a 'manage...'
 * button to modify installed PHPs.
 * <p>
 * This block implements ISelectionProvider - it sends selection change events
 * when the checked PHP in the table changes, or when the "use default" button
 * check state changes.
 * </p>
 */
public class PHPsComboBlock implements ISelectionProvider {

	PHPexes exes;

	/**
	 * The main control
	 */
	private Combo fCombo;

	/**
	 * This block's control
	 */
	private Composite fControl;

	/**
	 * Default PHP descriptor or <code>null</code> if none.
	 */
	private PHPexeDescriptor fDefaultDescriptor = null;

	/**
	 * Previous selection
	 */
	private ISelection fPrevSelection = new StructuredSelection();

	/**
	 * Selection listeners (checked PHP changes)
	 */
	private final ListenerList fSelectionListeners = new ListenerList();

	/**
	 * The title used for the PHPexe block
	 */
	private String fTitle = null;

	/**
	 * VMs being displayed
	 */
	private final List phpExecutables = new ArrayList();

	public PHPsComboBlock() {
		fDefaultDescriptor = new PHPexeDescriptor() {
			public String getDescription() {
				final PHPexeItem def = getPHPs(true).getDefaultItem();
				if (def != null)
					return def.getName() + " (" + def.getPhpEXE().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
				return "No PHP exes defined"; //$NON-NLS-1$
			}
		};
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		fSelectionListeners.add(listener);
	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param anscestor containing control
	 */
	public void createControl(final Composite ancestor) {
		final Font font = ancestor.getFont();
		final Group group = new Group(ancestor, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setFont(font);
		fControl = group;

		GridData data;
		if (fTitle == null)
			fTitle = PHPDebugUIMessages.PHPexesComboBlock_3;
		group.setText(fTitle);

		fCombo = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
		fCombo.setFont(font);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		fCombo.setLayoutData(data);
		fCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				setPHPexe(getPHPexe());
			}
		});

		Link link = new Link(group, SWT.NONE);
		link.setFont(font);
		data = new GridData(SWT.BEGINNING, SWT.BOTTOM, true, false);
		data.horizontalSpan = 1;
		link.setLayoutData(data);
		link.setText(PHPDebugUIMessages.PhpDebugPreferencePage_installedPHPsLink);
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PHPexeItem selected = getPHPexe();
				new ShowPHPsPreferences().run(null);
				fillWithWorkspacePHPexes();
				if (phpExecutables.contains(selected)) {
					String name = selected.getName() + " (" + selected.getPhpEXE().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
					fCombo.select(fCombo.indexOf(name));
				}
			}
		});

		if (getPHPs(true).getDefaultItem() == null) {
			MessageDialog.openInformation(getShell(), PHPDebugUIMessages.PHPsComboBlock_NoPHPsTitle, PHPDebugUIMessages.PHPsComboBlock_noPHPsMessage);
			new ShowPHPsPreferences().run(null);
		}
		fillWithWorkspacePHPexes();
	}

	/**
	 * Populates the PHPexe table with existing PHPexes defined in the workspace.
	 */
	protected void fillWithWorkspacePHPexes() {

		// fill with PHPexes
		final List standins = new ArrayList();
		final PHPexeItem[] types = getPHPs(true).getItems();
		for (int i = 0; i < types.length; i++) {
			final PHPexeItem type = types[i];
			standins.add(type);
		}
		setPHPexes(standins);
	}

	/**
	 * Fire current selection
	 */
	private void fireSelectionChanged() {
		final SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
		final Object[] listeners = fSelectionListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			final ISelectionChangedListener listener = (ISelectionChangedListener) listeners[i];
			listener.selectionChanged(event);
		}
	}

	/**
	 * Returns this block's control
	 * 
	 * @return control
	 */
	public Control getControl() {
		return fControl;
	}

	/**
	 * Returns the selected PHPexe or <code>null</code> if none.
	 * 
	 * @return the selected PHPexe or <code>null</code> if none
	 */
	public PHPexeItem getPHPexe() {
		final int index = fCombo.getSelectionIndex();
		if (index >= 0)
			return (PHPexeItem) phpExecutables.get(index);
		return null;
	}

	/**
	 * Returns the PHPexes currently being displayed in this block
	 * 
	 * @return PHPexes currently being displayed in this block
	 */
	public PHPexeItem[] getPHPexes() {
		return (PHPexeItem[]) phpExecutables.toArray(new PHPexeItem[phpExecutables.size()]);
	}

	public PHPexes getPHPs(final boolean load) {
		if (exes == null || load) {
			exes = new PHPexes();
			exes.load(PHPProjectPreferences.getModelPreferences());
		}
		return exes;
	}

	public String getSelectedLocation() {
		final PHPexeItem item = getPHPexe();
		if (item != null)
			return item.getPhpEXE().toString();
		return ""; //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	public ISelection getSelection() {
		final PHPexeItem vm = getPHPexe();
		if (vm == null)
			return new StructuredSelection();
		return new StructuredSelection(vm);
	}

	protected Shell getShell() {
		return getControl().getShell();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		fSelectionListeners.remove(listener);
	}

	/**
	 * Sets the selected PHPexe, or <code>null</code>
	 * 
	 * @param vm PHPexe or <code>null</code>
	 */
	public void setPHPexe(final PHPexeItem item) {
		if (item == null)
			setSelection(new StructuredSelection());
		else
			setSelection(new StructuredSelection(item));
	}

	/**
	 * Sets the PHPexes to be displayed in this block
	 * 
	 * @param vms PHPexes to be displayed
	 */
	protected void setPHPexes(final List phps) {
		phpExecutables.clear();
		phpExecutables.addAll(phps);
		// sort by name
		Collections.sort(phpExecutables, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				final PHPexeItem left = (PHPexeItem) o1;
				final PHPexeItem right = (PHPexeItem) o2;
				return left.getName().compareToIgnoreCase(right.getName());
			}

			public boolean equals(final Object obj) {
				return obj == this;
			}
		});
		// now make an array of names
		final String[] names = new String[phpExecutables.size()];
		final Iterator iter = phpExecutables.iterator();
		int i = 0;
		while (iter.hasNext()) {
			final PHPexeItem item = (PHPexeItem) iter.next();
			names[i] = item.getName() + " (" + item.getPhpEXE().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$

			i++;
		}
		fCombo.setItems(names);
		PHPexes exes = getPHPs(false);
		if (exes == null) {
			exes = getPHPs(true);
		}
		PHPexeItem defaultExe = exes.getDefaultItem();
		if (defaultExe != null) {
			String defaultName = defaultExe.getName() + " (" + defaultExe.getPhpEXE().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			fCombo.select(fCombo.indexOf(defaultName));
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(final ISelection selection) {
		if (selection instanceof IStructuredSelection)
			if (!selection.equals(fPrevSelection)) {
				fPrevSelection = selection;
				if (selection.isEmpty()) {
					fCombo.setText(""); //$NON-NLS-1$
					fCombo.select(-1);
					// need to do this to clear the old text
					fCombo.setItems(new String[] {});
					fillWithWorkspacePHPexes();
				} else {
					final Object jre = ((IStructuredSelection) selection).getFirstElement();
					final int index = phpExecutables.indexOf(jre);
					if (index >= 0)
						fCombo.select(index);
				}
				fireSelectionChanged();
			}
	}

	/**
	 * Sets the title used for this PHPexe block
	 * 
	 * @param title title for this PHPexe block 
	 */
	public void setTitle(final String title) {
		fTitle = title;
	}

	/**
	 * Sets this control to use the 'default' PHPexe.
	 */
	public void setUseDefaultPHPexe() {
		if (fDefaultDescriptor != null && fControl != null) {
			fPrevSelection = null;
			fireSelectionChanged();
		}
	}

}
