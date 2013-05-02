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
package org.eclipse.php.internal.debug.ui.preferences.phps;

import java.util.*;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * A composite that displays installed PHP's in a combo box, with a 'PHP
 * Executables' page link to modify installed PHPs.
 * <p>
 * This block implements ISelectionProvider - it sends selection change events
 * when the checked PHP in the table changes, or when the "use default" button
 * check state changes.
 * </p>
 */
public class PHPsComboBlock implements ISelectionProvider {

	PHPexes exes = PHPexes.getInstance();

	/*
	 * The main control
	 */
	protected Combo fExecutablesCombo;
	protected Combo fDebuggersCombo;

	/*
	 * This block's control
	 */
	private Composite fControl;

	/*
	 * Default PHP descriptor or <code>null</code> if none.
	 */
	private PHPexeDescriptor fDefaultDescriptor = null;

	/*
	 * Previous selection
	 */
	private ISelection fPrevSelection = new StructuredSelection();

	/*
	 * Selection listeners (checked PHP changes)
	 */
	private final ListenerList fSelectionListeners = new ListenerList();

	/*
	 * The title used for the PHPexe block
	 */
	private String fTitle = null;

	/*
	 * PHP executable being displayed
	 */
	private final List<PHPexeItem> phpExecutables = new ArrayList<PHPexeItem>();
	private boolean isTitled;
	private Link link;
	private Set<String> fDebuggerIds;

	private Timer timer;

	/**
	 * Constructs a new php combo box with or without a titled group that
	 * describes it.
	 * 
	 * @param titleGrouped
	 *            Set a titled group for this composite.
	 */
	public PHPsComboBlock(boolean titleGrouped) {
		this.isTitled = titleGrouped;
		fDefaultDescriptor = new PHPexeDescriptor() {
			public String getDescription() {
				final PHPexeItem def = exes.getDefaultItem(PHPDebugPlugin
						.getCurrentDebuggerId());
				if (def != null)
					return def.getName()
							+ " (" + def.getExecutable().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
				return PHPDebugUIMessages.PHPsComboBlock_2; 
			}
		};
	}

	/**
	 * Constructs a new php combo box wrapped inside a titled group that
	 * describes it.
	 */
	public PHPsComboBlock() {
		this(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(
			final ISelectionChangedListener listener) {
		fSelectionListeners.add(listener);
	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param anscestor
	 *            containing control
	 */
	public void createControl(final Composite ancestor) {
		final Font font = ancestor.getFont();
		Composite composite = null;
		if (isTitled) {
			Group g = new Group(ancestor, SWT.NULL);
			if (fTitle == null)
				fTitle = PHPDebugUIMessages.PHPexesComboBlock_3;
			g.setText(fTitle);
			composite = g;
		} else {
			composite = new Composite(ancestor, SWT.NULL);
		}
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(gridData);
		composite.setFont(font);
		fControl = composite;

		GridData data;
		// Add a top-left composite that will hold the label and the combo of
		// the php debuggers
		Composite topLeft = new Composite(composite, SWT.NONE);
		layout = new GridLayout(2, false);
		topLeft.setLayout(layout);
		topLeft.setFont(font);
		// Add the label
		Label debuggerLabel = new Label(topLeft, SWT.WRAP);
		debuggerLabel
				.setText(PHPDebugUIMessages.PhpDebugPreferencePage_phpDebugger);
		data = new GridData(GridData.BEGINNING);
		data.widthHint = 100;
		debuggerLabel.setLayoutData(data);
		// Add the debuggers combo
		fDebuggersCombo = new Combo(topLeft, SWT.DROP_DOWN | SWT.READ_ONLY);
		fDebuggersCombo.setFont(font);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		fDebuggersCombo.setLayoutData(data);
		fDebuggersCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				String selectedDebuggerID = getSelectedDebuggerId();
				PHPexeItem[] items = exes.getItems(selectedDebuggerID);
				List<PHPexeItem> itemsList;
				if (items != null) {
					itemsList = Arrays.asList(items);
				} else {
					itemsList = new ArrayList<PHPexeItem>(0);
				}
				setPHPexes(itemsList);
				fireSelectionChanged();
				signalExecutablesCombo();
			}
		});

		// add a dummy label to fill in the missing column
		Label dummy = new Label(composite, SWT.NONE);
		dummy.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));

		// Add a bottom-left composite that will hold the label and the combo of
		// the php executables
		Composite bottomLeft = new Composite(composite, SWT.NONE);
		layout = new GridLayout(2, false);
		bottomLeft.setLayout(layout);
		bottomLeft.setFont(font);
		data = gridData;
		bottomLeft.setLayoutData(data);
		// Add the label
		Label executableLabel = new Label(bottomLeft, SWT.WRAP);
		executableLabel.setText(PHPDebugUIMessages.PHPsComboBlock_3);
		data = new GridData(GridData.BEGINNING);
		data.widthHint = 100;
		executableLabel.setLayoutData(data);
		fExecutablesCombo = new Combo(bottomLeft, SWT.DROP_DOWN | SWT.READ_ONLY);
		fExecutablesCombo.setFont(font);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		fExecutablesCombo.setLayoutData(data);
		fExecutablesCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				setPHPexe(getPHPexe());
			}
		});
		// Add the php executables link to the right side of the composite
		link = new Link(composite, SWT.NONE);
		data = new GridData();
		data.horizontalSpan = 1;
		link.setLayoutData(data);
		link.setFont(font);
		link
				.setText(PHPDebugUIMessages.PhpDebugPreferencePage_installedPHPsLink);
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PHPexeItem selected = getPHPexe();
				new ShowPHPsPreferences().run(null);
				fillWithWorkspacePHPexes();
				if (phpExecutables.contains(selected)) {
					String name = selected.getName()
							+ " (" + selected.getExecutable().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
					fExecutablesCombo.select(fExecutablesCombo.indexOf(name));
				}
			}
		});

		checkDeuggers();
		fillDebuggers();
		fillWithWorkspacePHPexes();
	}

	// Check if there are no executables available for debug.
	// If so, display a notification and open the PHP Executables preferences
	// page.
	private void checkDeuggers() {
		if (!exes.hasItems()) {
			MessageDialog.openInformation(getShell(),
					PHPDebugUIMessages.PHPsComboBlock_NoPHPsTitle,
					PHPDebugUIMessages.PHPsComboBlock_noPHPsMessage);
			new ShowPHPsPreferences().run(null);
		}
	}

	/**
	 * Populates the debuggers with the debuggers defined in the workspace.
	 */
	protected void fillDebuggers() {
		fDebuggerIds = PHPDebuggersRegistry.getDebuggersIds();
		for (String id : fDebuggerIds) {
			// Insert the debuggers names
			fDebuggersCombo.add(PHPDebuggersRegistry.getDebuggerName(id));
		}
		// Select the default debugger
		String defaultName = PHPDebuggersRegistry
				.getDebuggerName(PHPDebuggersRegistry.getDefaultDebuggerId());
		int index = fDebuggersCombo.indexOf(defaultName);
		if (index > -1) {
			fDebuggersCombo.select(index);
		} else if (fDebuggersCombo.getItemCount() > 0) {
			fDebuggersCombo.select(0);
		}
	}

	/**
	 * Signal the executable combo about a change in the debugger's selection.
	 * The default signal is by blinking with a color change for 3 times. This
	 * behavior can be overridden.
	 */
	protected void signalExecutablesCombo() {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new BlinkTask(), 0, 300L);
	}

	/**
	 * Populates the PHPexe table with existing PHPexes defined in the
	 * workspace.
	 */
	protected void fillWithWorkspacePHPexes() {
		// fill with PHPexes
		final List<PHPexeItem> standins = new ArrayList<PHPexeItem>();
		final PHPexeItem[] types = exes.getItems(PHPDebugPlugin
				.getCurrentDebuggerId());
		if (types != null) {
			for (int i = 0; i < types.length; i++) {
				final PHPexeItem type = types[i];
				standins.add(type);
			}
		}
		setPHPexes(standins);
	}

	/**
	 * Fire current selection
	 */
	private void fireSelectionChanged() {
		final SelectionChangedEvent event = new SelectionChangedEvent(this,
				getSelection());
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
	 * Enable or disable the inner controls of this combo block.
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		fControl.setEnabled(enabled);
		fExecutablesCombo.setEnabled(enabled);
		fDebuggersCombo.setEnabled(enabled);
		link.setEnabled(enabled);
	}

	/**
	 * Returns the selected PHPexe or <code>null</code> if none.
	 * 
	 * @return the selected PHPexe or <code>null</code> if none
	 */
	public PHPexeItem getPHPexe() {
		final int index = fExecutablesCombo.getSelectionIndex();
		if (index >= 0
				&& !PHPDebugUIMessages.PhpDebugPreferencePage_noExeDefined
						.equals(fExecutablesCombo.getText()))
			return (PHPexeItem) phpExecutables.get(index);
		return null;
	}

	/**
	 * Returns the PHPexes currently being displayed in this block
	 * 
	 * @return PHPexes currently being displayed in this block
	 */
	public PHPexeItem[] getPHPexes() {
		return (PHPexeItem[]) phpExecutables
				.toArray(new PHPexeItem[phpExecutables.size()]);
	}

	/**
	 * Returns the location of the PHP executable that was selected.
	 * 
	 * @return The executable's location.
	 */
	public String getSelectedExecutablePath() {
		final PHPexeItem item = getPHPexe();
		if (item != null)
			return item.getExecutable().toString();
		return ""; //$NON-NLS-1$
	}

	/**
	 * Returns the location of the PHP ini file, or an empty string, if none was
	 * defined.
	 * 
	 * @return The php ini location.
	 */
	public String getSelectedIniPath() {
		final PHPexeItem item = getPHPexe();
		if (item != null) {
			if (item.getINILocation() != null) {
				return item.getINILocation().toString();
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Returns the id of the selected debugger.
	 * 
	 * @return The debugger's id.
	 */
	public String getSelectedDebuggerId() {
		int selectedIndex = fDebuggersCombo.getSelectionIndex();
		String debuggerId = DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID; // default
		if (selectedIndex > -1 && fDebuggerIds.size() > selectedIndex) {
			debuggerId = fDebuggerIds.toArray()[selectedIndex].toString();
		}
		return debuggerId;
	}

	/*
	 * (non-Javadoc)
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(
			final ISelectionChangedListener listener) {
		fSelectionListeners.remove(listener);
	}

	/**
	 * Sets the selected PHPexe, or <code>null</code>
	 * 
	 * @param item
	 *            {@link PHPexeItem} or <code>null</code>
	 */
	public void setPHPexe(final PHPexeItem item) {
		if (item == null)
			// There are no PHP executables for this debugger
			setSelection(new StructuredSelection());
		else
			setSelection(new StructuredSelection(item));
	}

	/**
	 * Sets the selected PHP debugger. The debugger will be selected only if
	 * it's in the installed debuggers list. Also, the executables list will be
	 * updated to the default executables values for this debugger and the
	 * selected exe will be the default exe from this group.
	 * 
	 * @param debuggerID
	 *            The debugger id to set.
	 */
	public void setDebugger(String debuggerID) {
		int index = fDebuggersCombo.indexOf(PHPDebuggersRegistry
				.getDebuggerName(debuggerID));
		if (index > -1) {
			fDebuggersCombo.select(index);
			PHPexeItem[] items = exes.getItems(debuggerID);
			if (items != null) {
				setPHPexes(Arrays.asList(items));
			} else {
				setPHPexes(new ArrayList<PHPexeItem>(0));
			}

		}
	}

	/**
	 * Sets the PHPexes to be displayed in this block
	 * 
	 * @param vms
	 *            PHPexes to be displayed
	 */
	protected void setPHPexes(final List<PHPexeItem> phps) {
		phpExecutables.clear();
		phpExecutables.addAll(phps);
		// sort by name
		Collections.sort(phpExecutables, new Comparator<PHPexeItem>() {
			public int compare(final PHPexeItem o1, final PHPexeItem o2) {
				if (null != o1 && null != o2) {
					String o1Name = o1.getName();
					String o2Name = o2.getName();
					if (null != o1Name && null != o2Name)
						return o1Name.compareToIgnoreCase(o2Name);
				}
				return 0;
			}

			public boolean equals(final Object obj) {
				return obj == this;
			}
		});
		// now make an array of names
		String[] names = new String[phpExecutables.size()];
		final Iterator<PHPexeItem> iter = phpExecutables.iterator();
		int i = 0;
		while (iter.hasNext()) {
			final PHPexeItem item = iter.next();
			names[i] = item.getName()
					+ " (" + item.getExecutable().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			i++;
		}
		if (names.length == 0) {
			names = new String[] { PHPDebugUIMessages.PhpDebugPreferencePage_noExeDefined };
		}
		fExecutablesCombo.setItems(names);
		PHPexeItem defaultExe = exes.getDefaultItem(getSelectedDebuggerId());
		if (defaultExe != null) {
			String defaultName = defaultExe.getName()
					+ " (" + defaultExe.getExecutable().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			fExecutablesCombo.select(fExecutablesCombo.indexOf(defaultName));
		} else {
			fExecutablesCombo.select(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse
	 * .jface.viewers.ISelection)
	 */
	public void setSelection(final ISelection selection) {
		if (selection instanceof IStructuredSelection)
			if (!selection.equals(fPrevSelection)) {
				fPrevSelection = selection;
				if (selection.isEmpty()) {
					fExecutablesCombo.setText(""); //$NON-NLS-1$
					fExecutablesCombo.select(-1);
					// need to do this to clear the old text
					fExecutablesCombo.setItems(new String[] {});
					fillWithWorkspacePHPexes();
				} else {
					final Object phpExe = ((IStructuredSelection) selection)
							.getFirstElement();
					final int index = phpExecutables.indexOf(phpExe);
					if (index >= 0)
						fExecutablesCombo.select(index);
				}
				fireSelectionChanged();
			}
	}

	/**
	 * Sets the title used for this PHPexe block
	 * 
	 * @param title
	 *            title for this PHPexe block
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

	/*
	 * A task that blinks the executables combo.
	 */
	private class BlinkTask extends TimerTask {
		int counter;

		public void run() {
			try {
				if (counter++ < 6) {
					fExecutablesCombo.getDisplay().syncExec(new Runnable() {
						public void run() {
							try {
								if (counter % 2 != 0) {
									fExecutablesCombo.setEnabled(false);
								} else {
									fExecutablesCombo.setEnabled(true);
								}
							} catch (SWTException se) {
								cancel();
							}
						}
					});
				} else {
					cancel();
				}
			} catch (SWTException se) {
				cancel();
			}
		}
	}
}
