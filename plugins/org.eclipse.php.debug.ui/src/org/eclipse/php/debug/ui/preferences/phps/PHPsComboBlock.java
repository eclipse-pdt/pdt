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
package org.eclipse.php.debug.ui.preferences.phps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.internal.ui.SWTUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.debug.core.preferences.PHPexeItem;
import org.eclipse.php.debug.core.preferences.PHPexes;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.debug.ui.actions.ControlAccessibleListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.sse.core.internal.SSECorePlugin;

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

	/**
	 * This block's control
	 */
	private Composite fControl;

	/**
	 * VMs being displayed
	 */
	private List fVMs = new ArrayList();

	/**
	 * The main control
	 */
	private Combo fCombo;

	// Action buttons
	private Button fManageButton;

	/**
	 * Selection listeners (checked PHP changes)
	 */
	private ListenerList fSelectionListeners = new ListenerList();

	/**
	 * Previous selection
	 */
	private ISelection fPrevSelection = new StructuredSelection();

	/**
	 * Default PHP descriptor or <code>null</code> if none.
	 */
	private PHPexeDescriptor fDefaultDescriptor = null;

	/**
	 * Specific PHPexe descriptor or <code>null</code> if none.
	 */
	private PHPexeDescriptor fSpecificDescriptor = null;

	/**
	 * Default PHPexe radio button or <code>null</code> if none
	 */
	private Button fDefaultButton = null;

	/**
	 * Selected PHPexe radio button
	 */
	private Button fSpecificButton = null;

	/**
	 * The title used for the PHPexe block
	 */
	private String fTitle = null;

	PHPexes exes;

	public PHPsComboBlock() {
		fDefaultDescriptor = new PHPexeDescriptor() {

			public String getDescription() {
				PHPexeItem def = getPHPs(true).getDefaultItem();
				if (def != null)
					return def.getName() + " (" + def.getPhpEXE().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
				return "no php exes defined"; //$NON-NLS-1$
			}
		};
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		fSelectionListeners.add(listener);
	}

	public PHPexes getPHPs(boolean load) {
		if (exes == null || load) {
			exes = new PHPexes();
			exes.load(PHPDebugUIPlugin.getDefault().getPluginPreferences());
		}
		return exes;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	public ISelection getSelection() {
		PHPexeItem vm = getPHPexe();
		if (vm == null) {
			return new StructuredSelection();
		}
		return new StructuredSelection(vm);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		fSelectionListeners.remove(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			if (!selection.equals(fPrevSelection)) {
				fPrevSelection = selection;
				if (selection.isEmpty()) {
					fCombo.setText(""); //$NON-NLS-1$
					fCombo.select(-1);
					// need to do this to clear the old text
					fCombo.setItems(new String[] {});
					fillWithWorkspacePHPexes();
				} else {
					Object jre = ((IStructuredSelection) selection).getFirstElement();
					int index = fVMs.indexOf(jre);
					if (index >= 0) {
						fCombo.select(index);
					}
				}
				fireSelectionChanged();
			}
		}
	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param anscestor containing control
	 */
	public void createControl(Composite ancestor) {
		Font font = ancestor.getFont();
		Composite comp = new Composite(ancestor, SWT.NONE);
		GridLayout layout = new GridLayout();
		comp.setLayout(new GridLayout());
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		fControl = comp;
		comp.setFont(font);

		Group group = new Group(comp, SWT.NULL);
		layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setFont(font);

		GridData data;

		if (fTitle == null) {
			fTitle = PHPDebugUIMessages.PHPexesComboBlock_3; //$NON-NLS-1$
		}
		group.setText(fTitle);

		// display a 'use default PHPexe' check box
		if (fDefaultDescriptor != null) {
			fDefaultButton = new Button(group, SWT.RADIO);
			fDefaultButton.setText(fDefaultDescriptor.getDescription());
			fDefaultButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (fDefaultButton.getSelection()) {
						setUseDefaultPHPexe();
					}
				}
			});
			data = new GridData();
			data.horizontalSpan = 3;
			fDefaultButton.setLayoutData(data);
			fDefaultButton.setFont(font);
		}

		fSpecificButton = new Button(group, SWT.RADIO);
		if (fSpecificDescriptor != null) {
			fSpecificButton.setText(fSpecificDescriptor.getDescription());
		} else {
			fSpecificButton.setText(PHPDebugUIMessages.PHPexesComboBlock_1); //$NON-NLS-1$
		}
		fSpecificButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (fSpecificButton.getSelection()) {
					fCombo.setEnabled(true);
					fManageButton.setEnabled(true);
					if (fCombo.getText().length() == 0 && !fVMs.isEmpty()) {
						fCombo.select(0);
					}
					fireSelectionChanged();
				}
			}
		});
		fSpecificButton.setFont(font);
		data = new GridData(GridData.BEGINNING);
		fSpecificButton.setLayoutData(data);

		fCombo = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
		fCombo.setFont(font);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		fCombo.setLayoutData(data);
		ControlAccessibleListener.addListener(fCombo, fSpecificButton.getText());

		fCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setPHPexe(getPHPexe());
			}
		});

		fManageButton = createPushButton(group, PHPDebugUIMessages.PHPexesComboBlock_2); //$NON-NLS-1$
		fManageButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				PHPexeItem oldSelection = getPHPexe();

				int oldIndex = -1;
				if (oldSelection != null) {
					oldIndex = fVMs.indexOf(oldSelection);
				}
				ShowPHPsPreferences showPrefs = new ShowPHPsPreferences();
				showPrefs.run(null);

				fillWithWorkspacePHPexes();
				int newIndex = -1;
				if (oldSelection != null) {
					newIndex = fVMs.indexOf(oldSelection);
				}
				if (newIndex != oldIndex) {
					// clear the old selection so that a selection changed is fired
					fPrevSelection = null;
				}
				// update text
				setDefaultPHPexeDescriptor(fDefaultDescriptor);
				if (isDefaultPHPexe()) {
					// reset in case default has changed
					setUseDefaultPHPexe();
				} else {
					// restore selection
					if (newIndex >= 0) {
						fCombo.select(newIndex);
					} else {
						// select the first PHPexe
						fCombo.select(0);
					}
					setPHPexe(getPHPexe());
				}
			}
		});

		if (getPHPs(true).getDefaultItem() == null) {
			MessageDialog.openInformation(getShell(),PHPDebugUIMessages.PHPsComboBlock_NoPHPsTitle,PHPDebugUIMessages.PHPsComboBlock_noPHPsMessage);
			new ShowPHPsPreferences().run(null);
		}
		fillWithWorkspacePHPexes();
	}

	/**
	 * Fire current selection
	 */
	private void fireSelectionChanged() {
		SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
		Object[] listeners = fSelectionListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			ISelectionChangedListener listener = (ISelectionChangedListener) listeners[i];
			listener.selectionChanged(event);
		}
	}

	protected Button createPushButton(Composite parent, String label) {
		return SWTUtil.createPushButton(parent, label, null);
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
	 * Sets the PHPexes to be displayed in this block
	 * 
	 * @param vms PHPexes to be displayed
	 */
	protected void setPHPexes(List jres) {
		fVMs.clear();
		fVMs.addAll(jres);
		// sort by name
		Collections.sort(fVMs, new Comparator() {
			public int compare(Object o1, Object o2) {
				PHPexeItem left = (PHPexeItem) o1;
				PHPexeItem right = (PHPexeItem) o2;
				return left.getName().compareToIgnoreCase(right.getName());
			}

			public boolean equals(Object obj) {
				return obj == this;
			}
		});
		// now make an array of names
		String[] names = new String[fVMs.size()];
		Iterator iter = fVMs.iterator();
		int i = 0;
		while (iter.hasNext()) {
			PHPexeItem vm = (PHPexeItem) iter.next();
			names[i] = vm.getName() + " (" + vm.getPhpEXE().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			i++;
		}
		fCombo.setItems(names);
	}

	/**
	 * Returns the PHPexes currently being displayed in this block
	 * 
	 * @return PHPexes currently being displayed in this block
	 */
	public PHPexeItem[] getPHPexes() {
		return (PHPexeItem[]) fVMs.toArray(new PHPexeItem[fVMs.size()]);
	}

	protected Shell getShell() {
		return getControl().getShell();
	}

	/**
	 * Sets the selected PHPexe, or <code>null</code>
	 * 
	 * @param vm PHPexe or <code>null</code>
	 */
	public void setPHPexe(PHPexeItem vm) {
		if (vm == null || vm != getPHPs(false).getDefaultItem()) {
			fSpecificButton.setSelection(true);
			fDefaultButton.setSelection(false);
			fCombo.setEnabled(true);
			fManageButton.setEnabled(true);
			if (vm == null) {
				setSelection(new StructuredSelection());
			} else {
				setSelection(new StructuredSelection(vm));
			}
		} else
			setUseDefaultPHPexe();
	}

	/**
	 * Returns the selected PHPexe or <code>null</code> if none.
	 * 
	 * @return the selected PHPexe or <code>null</code> if none
	 */
	public PHPexeItem getPHPexe() {
		int index = fCombo.getSelectionIndex();
		if (index >= 0) {
			return (PHPexeItem) fVMs.get(index);
		}
		return null;
	}

	/**
	 * Populates the PHPexe table with existing PHPexes defined in the workspace.
	 */
	protected void fillWithWorkspacePHPexes() {

		// fill with PHPexes
		List standins = new ArrayList();
		PHPexeItem[] types = getPHPs(true).getItems();
		for (int i = 0; i < types.length; i++) {
			PHPexeItem type = types[i];
			standins.add(type);
		}
		setPHPexes(standins);
	}

	/**
	 * Sets the Default PHPexe Descriptor for this block.
	 * 
	 * @param descriptor default PHPexe descriptor
	 */
	public void setDefaultPHPexeDescriptor(PHPexeDescriptor descriptor) {
		fDefaultDescriptor = descriptor;
		setButtonTextFromDescriptor(fDefaultButton, descriptor);
	}

	private void setButtonTextFromDescriptor(Button button, PHPexeDescriptor descriptor) {
		if (button != null) {
			//update the description & PHPexe in case it has changed
			String currentText = button.getText();
			String newText = descriptor.getDescription();
			if (!newText.equals(currentText)) {
				button.setText(newText);
				fControl.layout();
			}
		}
	}

	/**
	 * Sets the specific PHPexe Descriptor for this block.
	 * 
	 * @param descriptor specific PHPexe descriptor
	 */
	public void setSpecificPHPexeDescriptor(PHPexeDescriptor descriptor) {
		fSpecificDescriptor = descriptor;
		setButtonTextFromDescriptor(fSpecificButton, descriptor);
	}

	/**
	 * Returns whether the 'use default PHPexe' button is checked.
	 * 
	 * @return whether the 'use default PHPexe' button is checked
	 */
	public boolean isDefaultPHPexe() {
		if (fDefaultButton != null) {
			return fDefaultButton.getSelection();
		}
		return false;
	}

	/**
	 * Sets this control to use the 'default' PHPexe.
	 */
	public void setUseDefaultPHPexe() {
		if (fDefaultDescriptor != null) {
			fDefaultButton.setSelection(true);
			fSpecificButton.setSelection(false);
			fCombo.setEnabled(false);
			fManageButton.setEnabled(false);
			fPrevSelection = null;
			fireSelectionChanged();
		}
	}

	/**
	 * Sets the title used for this PHPexe block
	 * 
	 * @param title title for this PHPexe block 
	 */
	public void setTitle(String title) {
		fTitle = title;
	}

	/**
	 * Refresh the default PHPexe description.
	 */
	public void refresh() {
		setDefaultPHPexeDescriptor(fDefaultDescriptor);
	}

	public String getSelectedLocation() {
		if (fDefaultButton.getSelection()) {
			PHPexeItem defaultItem = getPHPs(false).getDefaultItem();
			if (defaultItem==null)
				return null;
			return defaultItem.getPhpEXE().toString();
		}
		PHPexeItem item = getPHPexe();
		if (item != null)
			return item.getPhpEXE().toString();
		return ""; //$NON-NLS-1$
	}

}
