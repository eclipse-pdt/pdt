/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.builtin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.server.core.builtin.IPHPRuntimeWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.server.core.IRuntimeWorkingCopy;
import org.eclipse.wst.server.core.internal.IInstallableRuntime;
import org.eclipse.wst.server.core.internal.ServerPlugin;
import org.eclipse.wst.server.ui.internal.SWTUtil;
import org.eclipse.wst.server.ui.wizard.IWizardHandle;

/**
 * Wizard page to set the server install directory.
 */

@SuppressWarnings("restriction")
public class PHPRuntimeComposite extends Composite {
	protected IRuntimeWorkingCopy runtimeWC;
	protected IPHPRuntimeWorkingCopy runtime;

	protected IWizardHandle wizard;

	protected Text name;
	protected List<PHPexeItem> installedExecutables;
	protected String[] executableNames;
	protected Combo combo;
	protected IInstallableRuntime ir;
	protected Job installRuntimeJob;
	protected IJobChangeListener jobListener;
	protected Label installLabel;
	protected Button install;

	protected PHPRuntimeComposite(Composite parent, IWizardHandle wizard) {
		super(parent, SWT.NONE);
		this.wizard = wizard;

		wizard.setTitle(Messages.wizardTitle);
		wizard.setDescription(Messages.wizardDescription);
		wizard.setImageDescriptor(PHPServerUIPlugin.getImageDescriptor(PHPServerUIPlugin.IMG_WIZ_PHP_SERVER));

		createControl();
	}

	protected void setRuntime(IRuntimeWorkingCopy newRuntime) {
		if (newRuntime == null) {
			runtimeWC = null;
			runtime = null;
		} else {
			runtimeWC = newRuntime;
			runtime = (IPHPRuntimeWorkingCopy) newRuntime.loadAdapter(IPHPRuntimeWorkingCopy.class, null);
		}

		if (runtimeWC == null) {
			ir = null;
			install.setEnabled(false);
			installLabel.setText(""); //$NON-NLS-1$
		} else {
			ir = ServerPlugin.findInstallableRuntime(runtimeWC.getRuntimeType().getId());
			if (ir != null) {
				install.setEnabled(true);
				installLabel.setText(ir.getName());
			}
		}

		init();
		validate();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (installRuntimeJob != null) {
			installRuntimeJob.removeJobChangeListener(jobListener);
		}
	}

	/**
	 * Provide a wizard page to change the PHP executable installation
	 * directory.
	 */
	protected void createControl() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_BOTH));
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// ContextIds.RUNTIME_COMPOSITE);

		Label label = new Label(this, SWT.NONE);
		label.setText(Messages.runtimeName);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		name = new Text(this, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(data);
		name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				runtimeWC.setName(name.getText());
				validate();
			}
		});

		updateExecutables();

		label = new Label(this, SWT.NONE);
		label.setText(Messages.installedJRE);
		data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		combo = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setItems(executableNames);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		combo.setLayoutData(data);

		combo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int sel = combo.getSelectionIndex();
				PHPexeItem item = (PHPexeItem) installedExecutables.get(sel);
				runtime.setExecutableInstall(item);
				runtimeWC.setLocation(new Path(item.getExecutable().getParent()));
				validate();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		Button button = SWTUtil.createButton(this, Messages.installedJREs);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String currentVM = combo.getText();
				if (showPreferencePage()) {
					updateExecutables();
					combo.setItems(executableNames);
					combo.setText(currentVM);
					if (combo.getSelectionIndex() == -1)
						combo.select(0);
					validate();
				}
			}
		});

		init();
		validate();

		Dialog.applyDialogFont(this);

		name.forceFocus();
	}

	protected void updateExecutables() {
		// get all installed CLI executables
		installedExecutables = new ArrayList<>();
		PHPexeItem[] installedItems = PHPexes.getInstance().getAllItems();
		int size = installedItems.length;
		for (int i = 0; i < size; i++) {
			installedExecutables.add(installedItems[i]);
		}

		// get names
		size = installedExecutables.size();
		executableNames = new String[size];
		for (int i = 0; i < size; i++) {
			PHPexeItem vmInstall = (PHPexeItem) installedExecutables.get(i);
			executableNames[i] = vmInstall.getName();
		}
	}

	protected boolean showPreferencePage() {
		String id = "org.eclipse.php.debug.ui.preferencesphps.PHPsPreferencePage"; //$NON-NLS-1$

		PreferenceManager manager = PlatformUI.getWorkbench().getPreferenceManager();
		IPreferenceNode node = manager.find("org.eclipse.php.ui.preferences.PHPBasePreferencePage").findSubNode(id); //$NON-NLS-1$
		PreferenceManager manager2 = new PreferenceManager();
		manager2.addToRoot(node);
		PreferenceDialog dialog = new PreferenceDialog(getShell(), manager2);
		dialog.create();
		return (dialog.open() == Window.OK);
	}

	protected void init() {
		if (name == null || runtime == null)
			return;

		if (runtimeWC.getName() != null)
			name.setText(runtimeWC.getName());
		else
			name.setText(""); //$NON-NLS-1$

		int size = installedExecutables.size();
		for (int i = 0; i < size; i++) {
			PHPexeItem item = (PHPexeItem) installedExecutables.get(i);
			if (item.equals(runtime.getExecutableInstall())) {
				combo.select(i);
			}
		}
	}

	protected void validate() {
		if (runtime == null) {
			wizard.setMessage("", IMessageProvider.ERROR); //$NON-NLS-1$
			return;
		}

		IStatus status = runtimeWC.validate(null);
		if (status == null || status.isOK())
			wizard.setMessage(null, IMessageProvider.NONE);
		else if (status.getSeverity() == IStatus.WARNING)
			wizard.setMessage(status.getMessage(), IMessageProvider.WARNING);
		else
			wizard.setMessage(status.getMessage(), IMessageProvider.ERROR);
		wizard.update();
	}
}
