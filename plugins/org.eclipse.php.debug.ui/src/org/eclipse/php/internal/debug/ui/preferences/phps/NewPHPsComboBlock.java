/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.phps;

import java.io.File;
import java.util.*;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.PHPRuntime;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.debug.ui.actions.ControlAccessibleListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.ibm.icu.text.MessageFormat;

/**
 * A composite that displays installed PHPs in a combo box, with a 'manage...'
 * button to modify installed PHPs.
 * <p>
 * This block implements ISelectionProvider - it sends selection change events
 * when the checked PHP in the table changes, or when the "use default" button
 * check state changes.
 * </p>
 */
public class NewPHPsComboBlock {

	PHPexes exes = PHPexes.getInstance();
	/*
	 * PHP executable being displayed
	 */
	private final List<PHPexeItem> phpExecutables = new ArrayList<PHPexeItem>();

	public static final String PROPERTY_PHP = "PROPERTY_PHP"; //$NON-NLS-1$

	/**
	 * This block's control
	 */
	private Composite fControl;

	/**
	 * The main control
	 */
	private Combo fExecutablesCombo;

	// Action buttons
	private Button fManageButton;

	/**
	 * PHP change listeners
	 */
	private ListenerList fListeners = new ListenerList();

	/**
	 * Default PHP descriptor or <code>null</code> if none.
	 */
	private PHPexeDescriptor fDefaultDescriptor = null;

	/**
	 * Specific PHP descriptor or <code>null</code> if none.
	 */
	private PHPexeDescriptor fSpecificDescriptor = null;

	/**
	 * Default PHP radio button or <code>null</code> if none
	 */
	private Button fDefaultButton = null;

	/**
	 * Selected PHP radio button
	 */
	private Button fSpecificButton = null;

	/**
	 * The title used for the PHP block
	 */
	private String fTitle = null;

	/**
	 * Selected PHP profile radio button
	 */
	private Button fEnvironmentsButton = null;

	/**
	 * Combo box of PHP profiles
	 */
	private Combo fEnvironmentsCombo = null;

	private Button fManageEnvironmentsButton = null;

	// a path to an unavailable PHP
	private IPath fErrorPath;

	/**
	 * List of execution environments
	 */
	private List fEnvironments = new ArrayList();

	private IStatus fStatus = OK_STATUS;

	private static IStatus OK_STATUS = new Status(IStatus.OK,
			PHPDebugUIPlugin.getID(), 0, "", null); //$NON-NLS-1$
	// private PHPexeItem[] phpItems = exes.getAllItems();

	private IProject project;
	/*
	 * Selection listeners (checked PHP changes)
	 */
	private final ListenerList fSelectionListeners = new ListenerList();

	/**
	 * Creates a PHPs combo block.
	 * 
	 * @param defaultFirst
	 *            whether the default PHP should be in first position (if
	 *            <code>false</code>, it becomes last)
	 */
	public NewPHPsComboBlock() {
		fDefaultDescriptor = new PHPexeDescriptor() {
			public String getDescription() {
				final PHPexeItem def = PHPDebugPlugin.getPHPexeItem(project);
				return getDisplayName(def, true);
			}
		};
		setDefaultPHPexeDescriptor(fDefaultDescriptor);
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		fListeners.add(listener);
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		fListeners.remove(listener);
	}

	private void firePropertyChange() {
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_PHP,
				null, getPHPexe());
		Object[] listeners = fListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			IPropertyChangeListener listener = (IPropertyChangeListener) listeners[i];
			listener.propertyChange(event);
		}
	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param anscestor
	 *            containing control
	 */
	public void createControl(Composite ancestor) {
		fControl = SWTFactory.createComposite(ancestor, 1, 1,
				GridData.FILL_BOTH);
		if (fTitle == null) {
			fTitle = PHPDebugUIMessages.PHPexesComboBlock_3;
		}
		Group group = SWTFactory.createGroup(fControl, fTitle, 1, 1,
				GridData.FILL_HORIZONTAL);
		Composite comp = SWTFactory.createComposite(group, group.getFont(), 3,
				1, GridData.FILL_BOTH, 0, 0);

		createDefaultPHPControls(comp);
		createEEControls(comp);
		createAlternatePHPControls(comp);
		setUseDefaultPHP();
	}

	private void createEEControls(Composite comp) {
		fEnvironmentsButton = SWTFactory.createRadioButton(comp,
				PHPDebugUIMessages.PHPexesComboBlock_4);
		fEnvironmentsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (fEnvironmentsButton.getSelection()) {
					fExecutablesCombo.setEnabled(false);
					if (fEnvironmentsCombo.getText().length() == 0
							&& !fEnvironments.isEmpty()) {
						fEnvironmentsCombo.select(0);
					}
					fEnvironmentsCombo.setEnabled(true);
					if (fEnvironments.isEmpty()) {
						setError(PHPDebugUIMessages.PHPexesComboBlock_5);
					} else {
						setStatus(OK_STATUS);
					}
					firePropertyChange();
				}
			}
		});

		fEnvironmentsCombo = SWTFactory.createCombo(comp, SWT.DROP_DOWN
				| SWT.READ_ONLY, 1, null);
		fEnvironmentsCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// TODO
				setPath(PHPRuntime.newPHPContainerPath(getEnvironment()));
				firePropertyChange();
			}
		});

		fManageEnvironmentsButton = SWTFactory.createPushButton(comp,
				PHPDebugUIMessages.PHPexesComboBlock_14, null);
		fManageEnvironmentsButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				showPrefPage(PHPInterpreterExecutionPreferencePage.PREF_ID);
			}
		});

		fillWithWorkspaceProfiles();
	}

	private void createAlternatePHPControls(Composite comp) {
		String text = PHPDebugUIMessages.PHPexesComboBlock_1;
		if (fSpecificDescriptor != null) {
			text = fSpecificDescriptor.getDescription();
		}
		fSpecificButton = SWTFactory.createRadioButton(comp, text, 1);
		fSpecificButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (fSpecificButton.getSelection()) {
					fExecutablesCombo.setEnabled(true);
					if (fExecutablesCombo.getText().length() == 0
							&& !phpExecutables.isEmpty()) {
						fExecutablesCombo.select(0);
					}
					if (phpExecutables.isEmpty()) {
						setError(PHPDebugUIMessages.PHPexesComboBlock_0);
					} else {
						setStatus(OK_STATUS);
					}
					fEnvironmentsCombo.setEnabled(false);
					firePropertyChange();
				}
			}
		});
		fExecutablesCombo = SWTFactory.createCombo(comp, SWT.DROP_DOWN
				| SWT.READ_ONLY, 1, null);
		ControlAccessibleListener.addListener(fExecutablesCombo,
				fSpecificButton.getText());
		fExecutablesCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setStatus(OK_STATUS);
				firePropertyChange();
			}
		});

		fManageButton = SWTFactory.createPushButton(comp,
				PHPDebugUIMessages.PHPexesComboBlock_2, null);
		fManageButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				showPrefPage(PHPsPreferencePage.ID);
			}
		});
		fillWithWorkspacePHPexes();
	}

	private void createDefaultPHPControls(Composite comp) {
		if (fDefaultDescriptor != null) {
			fDefaultButton = SWTFactory.createRadioButton(comp,
					fDefaultDescriptor.getDescription(), 3);
			fDefaultButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (fDefaultButton.getSelection()) {
						setUseDefaultPHP();
						setStatus(OK_STATUS);
						firePropertyChange();
					}
				}
			});
		}
	}

	/**
	 * Opens the given preference page, and updates when closed.
	 * 
	 * @param id
	 *            pref page id
	 * @param page
	 *            pref page
	 */
	private void showPrefPage(String id/* , IPreferencePage page */) {
		PHPexeItem prevPHP = getPHPexe();
		PHPVersion prevEnv = getEnvironment();
		PreferencesUtil.createPreferenceDialogOn(getShell(), id,
				new String[] { id }, null).open();
		// PHPDebugUIPlugin.showPreferencePage(id);
		fillWithWorkspacePHPexes();
		// fillWithWorkspacePHPs();
		fillWithWorkspaceProfiles();
		restoreCombo(phpExecutables, prevPHP, fExecutablesCombo);
		restoreCombo(fEnvironments, prevEnv, fEnvironmentsCombo);
		// update text
		setDefaultPHPexeDescriptor(fDefaultDescriptor);
		if (isDefaultPHP()) {
			// reset in case default has changed
			setUseDefaultPHP();
		}
		// TODO
		setPath(getPath());
		firePropertyChange();
	}

	private void restoreCombo(List elements, Object element, Combo combo) {
		int index = -1;
		if (element != null) {
			index = elements.indexOf(element);
		}
		if (index >= 0) {
			combo.select(index);
		} else {
			combo.select(0);
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
	 * Sets the PHPs to be displayed in this block
	 * 
	 * @param vms
	 *            PHPs to be displayed
	 */
	// protected void setPHPs(List PHPs) {
	// phpExecutables.clear();
	// phpExecutables.addAll(PHPs);
	// // sort by name
	// Collections.sort(phpExecutables, new Comparator() {
	// public int compare(Object o1, Object o2) {
	// IVMInstall left = (IVMInstall) o1;
	// IVMInstall right = (IVMInstall) o2;
	// return left.getName().compareToIgnoreCase(right.getName());
	// }
	//
	// public boolean equals(Object obj) {
	// return obj == this;
	// }
	// });
	// // now make an array of names
	// String[] names = new String[phpExecutables.size()];
	// Iterator iter = phpExecutables.iterator();
	// int i = 0;
	// while (iter.hasNext()) {
	// IVMInstall vm = (IVMInstall) iter.next();
	// names[i] = vm.getName();
	// i++;
	// }
	// fExecutablesCombo.setItems(names);
	// fExecutablesCombo.setVisibleItemCount(Math.min(names.length, 20));
	// }

	protected Shell getShell() {
		return getControl().getShell();
	}

	/**
	 * Selects a specific PHP based on type/name.
	 * 
	 * @param vm
	 *            PHP or <code>null</code>
	 */
	private void selectPHP(PHPexeItem vm) {
		fSpecificButton.setSelection(true);
		fDefaultButton.setSelection(false);
		fEnvironmentsButton.setSelection(false);
		fExecutablesCombo.setEnabled(true);
		fEnvironmentsCombo.setEnabled(false);
		if (vm != null) {
			int index = phpExecutables.indexOf(vm);
			if (index >= 0) {
				fExecutablesCombo.select(index);
			}
		}
		firePropertyChange();
	}

	/**
	 * Selects a PHP based environment.
	 * 
	 * @param env
	 *            environment or <code>null</code>
	 */
	private void selectEnvironment(PHPVersion env) {
		fSpecificButton.setSelection(false);
		fDefaultButton.setSelection(false);
		fExecutablesCombo.setEnabled(false);
		fEnvironmentsButton.setSelection(true);
		fEnvironmentsCombo.setEnabled(true);
		if (env != null) {
			int index = fEnvironments.indexOf(env);
			if (index >= 0) {
				fEnvironmentsCombo.select(index);
			}
		}
		firePropertyChange();
	}

	/**
	 * Returns the selected PHP or <code>null</code> if none.
	 * 
	 * @return the selected PHP or <code>null</code> if none
	 */
	// public IVMInstall getPHP() {
	// int index = fExecutablesCombo.getSelectionIndex();
	// if (index >= 0) {
	// return (IVMInstall) phpExecutables.get(index);
	// }
	// return null;
	// }

	/**
	 * Returns the selected Environment or <code>null</code> if none.
	 * 
	 * @return the selected Environment or <code>null</code> if none
	 */
	private PHPVersion getEnvironment() {
		int index = fEnvironmentsCombo.getSelectionIndex();
		if (index >= 0) {
			return (PHPVersion) fEnvironments.get(index);
		}
		return null;
	}

	/**
	 * Populates the PHP table with existing PHPs defined in the workspace.
	 */
	// protected void fillWithWorkspacePHPs() {
	// // fill with PHPs
	// List standins = new ArrayList();
	// IVMInstallType[] types = PHPRuntime.getVMInstallTypes();
	// for (int i = 0; i < types.length; i++) {
	// IVMInstallType type = types[i];
	// IVMInstall[] installs = type.getVMInstalls();
	// for (int j = 0; j < installs.length; j++) {
	// IVMInstall install = installs[j];
	// standins.add(new VMStandin(install));
	// }
	// }
	// setPHPs(standins);
	// }

	/**
	 * Populates the PHP profile combo with profiles defined in the workspace.
	 */
	protected void fillWithWorkspaceProfiles() {
		fEnvironments.clear();
		PHPVersion[] environments = PHPVersion.values();
		for (int i = 0; i < environments.length; i++) {
			fEnvironments.add(environments[i]);
		}
		// sort by name
		// Collections.sort(fEnvironments, new Comparator() {
		// public int compare(Object o1, Object o2) {
		// PHPVersion left = (PHPVersion) o1;
		// PHPVersion right = (PHPVersion) o2;
		// return left.getId().compareToIgnoreCase(right.getId());
		// }
		//
		// public boolean equals(Object obj) {
		// return obj == this;
		// }
		// });
		// now make an array of names
		// String[] names = new String[fEnvironments.size()];
		// Iterator iter = fEnvironments.iterator();
		// int i = 0;
		// while (iter.hasNext()) {
		// PHPVersion env = (PHPVersion) iter.next();
		// IPath path = PHPRuntime.newPHPContainerPath(env);
		// IVMInstall install = PHPRuntime.getVMInstall(path);
		// if (install != null) {
		// names[i] = MessageFormat.format(
		// PHPDebugUIMessages.PHPexesComboBlock_15, new String[] {
		// env.getId(), install.getName() });
		// } else {
		// names[i] = MessageFormat.format(
		// PHPDebugUIMessages.PHPexesComboBlock_16,
		// new String[] { env.getId() });
		// }
		// i++;
		// }
		String[] names = new String[fEnvironments.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = environments[i].getAlias();
		}
		fEnvironmentsCombo.setItems(names);
		fEnvironmentsCombo.setVisibleItemCount(Math.min(names.length, 20));
	}

	/**
	 * Sets the Default PHP Descriptor for this block.
	 * 
	 * @param descriptor
	 *            default PHP descriptor
	 */
	public void setDefaultPHPexeDescriptor(PHPexeDescriptor descriptor) {
		fDefaultDescriptor = descriptor;
		setButtonTextFromDescriptor(fDefaultButton, descriptor);
	}

	private void setButtonTextFromDescriptor(Button button,
			PHPexeDescriptor descriptor) {
		if (button != null) {
			// update the description & PHP in case it has changed
			String currentText = button.getText();
			String newText = descriptor.getDescription();
			if (!newText.equals(currentText)) {
				button.setText(newText);
				fControl.layout();
			}
		}
	}

	/**
	 * Sets the specific PHP Descriptor for this block.
	 * 
	 * @param descriptor
	 *            specific PHP descriptor
	 */
	public void setSpecificPHPexeDescriptor(PHPexeDescriptor descriptor) {
		fSpecificDescriptor = descriptor;
		setButtonTextFromDescriptor(fSpecificButton, descriptor);
	}

	/**
	 * Returns whether the 'use default PHP' button is checked.
	 * 
	 * @return whether the 'use default PHP' button is checked
	 */
	public boolean isDefaultPHP() {
		if (fDefaultButton != null) {
			return fDefaultButton.getSelection();
		}
		return false;
	}

	/**
	 * Sets this control to use the 'default' PHP.
	 */
	private void setUseDefaultPHP() {
		if (fDefaultDescriptor != null) {
			fDefaultButton.setSelection(true);
			fSpecificButton.setSelection(false);
			fEnvironmentsButton.setSelection(false);
			fExecutablesCombo.setEnabled(false);
			fEnvironmentsCombo.setEnabled(false);
			firePropertyChange();
		}
	}

	/**
	 * Sets the title used for this PHP block
	 * 
	 * @param title
	 *            title for this PHP block
	 */
	public void setTitle(String title) {
		fTitle = title;
	}

	/**
	 * Refresh the default PHP description.
	 */
	public void refresh() {
		setDefaultPHPexeDescriptor(fDefaultDescriptor);
	}

	/**
	 * Returns a classpath container path identifying the selected PHP.
	 * 
	 * @return classpath container path or <code>null</code>
	 * @since 3.2
	 */
	public IPath getPath() {
		if (!getStatus().isOK() && fErrorPath != null) {
			return fErrorPath;
		}
		if (fEnvironmentsButton.getSelection()) {
			int index = fEnvironmentsCombo.getSelectionIndex();
			if (index >= 0) {
				PHPVersion env = (PHPVersion) fEnvironments.get(index);
				return PHPRuntime.newPHPContainerPath(env);
			}
			return null;
		}
		if (fSpecificButton.getSelection()) {
			int index = fExecutablesCombo.getSelectionIndex();
			if (index >= 0) {
				PHPexeItem vm = phpExecutables.get(index);
				return PHPRuntime.newPHPContainerPath(vm);
			}
			return null;
		}
		return PHPRuntime.newDefaultPHPContainerPath();
	}

	/**
	 * Sets the selection based on the given container path and returns a status
	 * indicating if the selection was successful.
	 * 
	 * @param containerPath
	 * @return status
	 */
	public void setPath(IPath containerPath) {
		fErrorPath = null;
		setStatus(OK_STATUS);
		if (containerPath == null
				|| PHPRuntime.newDefaultPHPContainerPath()
						.equals(containerPath)) {
			setUseDefaultPHP();
		} else {
			PHPVersion version = PHPRuntime.getPHPVersion(containerPath);
			if (version != null) {
				selectEnvironment(version);
				PHPexeItem[] items = PHPexes.getInstance().getCompatibleItems(
						PHPexes.getInstance().getAllItems(), version);
				if (items.length == 0) {
					setError(MessageFormat.format(
							PHPDebugUIMessages.PHPexesComboBlock_7,
							new String[] { version.getAlias() }));
				}
			} else {
				PHPexeItem install = PHPRuntime.getPHPexeItem(containerPath);
				if (install == null) {
					selectPHP(install);
					fErrorPath = containerPath;
					// String installTypeId = PHPRuntime
					// .getVMInstallTypeId(containerPath);
					// if (installTypeId == null) {
					setError(PHPDebugUIMessages.PHPexesComboBlock_8);
					// } else {
					// IVMInstallType installType = PHPRuntime
					// .getVMInstallType(installTypeId);
					// if (installType == null) {
					// setError(MessageFormat.format(
					// PHPDebugUIMessages.PHPexesComboBlock_9,
					// new String[] { installTypeId }));
					// } else {
					// String installName = PHPRuntime
					// .getVMInstallName(containerPath);
					// if (installName == null) {
					// setError(MessageFormat
					// .format(
					// PHPDebugUIMessages.PHPexesComboBlock_10,
					// new String[] { installType
					// .getName() }));
					// } else {
					// setError(MessageFormat
					// .format(
					// PHPDebugUIMessages.PHPexesComboBlock_11,
					// new String[] { installName,
					// installType.getName() }));
					// }
					// }
					// }
				} else {
					selectPHP(install);
					File location = install.getExecutable();
					if (location == null) {
						setError(PHPDebugUIMessages.PHPexesComboBlock_12);
					} else if (!location.exists()) {
						setError(PHPDebugUIMessages.PHPexesComboBlock_13);
					}
				}
			}
		}
	}

	public void setProject(IProject project) {
		if (this.project == null || !this.project.equals(project)) {
			this.project = project;
			setDefaultPHPexeDescriptor(fDefaultDescriptor);
		}

	}

	private void setError(String message) {
		setStatus(new Status(IStatus.ERROR, PHPDebugUIPlugin.getID(), 150,
				message, null));
	}

	/**
	 * Returns the status of the PHP selection.
	 * 
	 * @return status
	 */
	public IStatus getStatus() {
		return fStatus;
	}

	private void setStatus(IStatus status) {
		fStatus = status;
	}

	/**
	 * Populates the PHPexe table with existing PHPexes defined in the
	 * workspace.
	 */
	protected void fillWithWorkspacePHPexes() {
		// fill with PHPexes
		final List<PHPexeItem> standins = new ArrayList<PHPexeItem>();
		PHPexeItem[] phpItems = exes.getAllItems();
		if (phpItems != null) {
			for (int i = 0; i < phpItems.length; i++) {
				final PHPexeItem type = phpItems[i];
				standins.add(type);
			}
		}
		setPHPexes(standins);
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
			names[i] = getDisplayName(item, false);
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

	private String getDisplayName(PHPexeItem item, boolean isDefault) {
		String name = PHPDebugUIMessages.NewPHPsComboBlock_4; 
		if (item != null) {
			String debugger = PHPDebugUIMessages.NewPHPsComboBlock_5;
			if (!"org.eclipse.php.debug.core.zendDebugger".equals(item //$NON-NLS-1$
					.getDebuggerID())) {
				debugger = PHPDebugUIMessages.NewPHPsComboBlock_7;
			}
			name = item.getName()
					+ " (" + debugger + " " + item.getVersion() + " " + item.getSapiType() + " " + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}
		if (isDefault) {
			String defaultPrefix = PHPDebugUIMessages.NewPHPsComboBlock_13;
			if (project != null) {
				defaultPrefix = PHPDebugUIMessages.NewPHPsComboBlock_14;
			}
			return defaultPrefix + PHPDebugUIMessages.NewPHPsComboBlock_15 + name;
		}
		return name;
	}

	/**
	 * Returns the selected PHPexe or <code>null</code> if none.
	 * 
	 * @return the selected PHPexe or <code>null</code> if none
	 */
	public PHPexeItem getPHPexe() {
		return getPHPexe(null);
	}

	private PHPexeItem getPHPexe(IProject project) {
		if (fSpecificButton.getSelection() && !phpExecutables.isEmpty()
				&& fExecutablesCombo.getSelectionIndex() >= 0) {
			return phpExecutables.get(fExecutablesCombo.getSelectionIndex());
		} else if (fEnvironmentsButton.getSelection()) {
			return PHPDebugPlugin.getPHPexeItem(PHPVersion
					.byAlias(fEnvironmentsCombo.getText()));
		}
		return PHPDebugPlugin.getPHPexeItem(project);
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
	 * Returns the id of the selected debugger.
	 * 
	 * @return The debugger's id.
	 */
	public String getSelectedDebuggerId() {
		if (fSpecificButton.getSelection() && !phpExecutables.isEmpty()
				&& fExecutablesCombo.getSelectionIndex() >= 0) {
			return phpExecutables.get(fExecutablesCombo.getSelectionIndex())
					.getDebuggerID();
		} else if (fEnvironmentsButton.getSelection()) {
			return PHPDebugPlugin.getCurrentDebuggerId(PHPVersion
					.byAlias(fEnvironmentsCombo.getText()));
		}
		return PHPDebugPlugin.getCurrentDebuggerId();
	}

	/**
	 * Returns the location of the PHP executable that was selected.
	 * 
	 * @param iProject
	 * 
	 * @return The executable's location.
	 */
	public String getSelectedExecutablePath() {
		final PHPexeItem item = getPHPexe(project);
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

}
