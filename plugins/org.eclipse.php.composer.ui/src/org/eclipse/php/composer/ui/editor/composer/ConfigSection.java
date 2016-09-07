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
package org.eclipse.php.composer.ui.editor.composer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.ComposerSection;
import org.eclipse.php.composer.ui.editor.FormEntryAdapter;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.parts.BooleanFormEntry;
import org.eclipse.php.composer.ui.parts.FormEntry;
import org.eclipse.php.composer.ui.parts.IBooleanFormEntryListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class ConfigSection extends ComposerSection {

	protected FormEntry processTimeoutEntry;
	protected FormEntry vendorDirEntry;
	protected FormEntry binDirEntry;
	protected BooleanFormEntry notifyOnInstallEntry;

	public ConfigSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION);
		createClient(getSection(), page.getManagedForm().getToolkit());
	}

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText("Config");
		section.setDescription("Configure your package.");
		// section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite client = toolkit.createComposite(section);
		client.setLayout(FormLayoutFactory.createSectionClientTableWrapLayout(false, 2));
		section.setClient(client);

		createProcessTimeoutEntry(client, toolkit);
		createVendorDirEntry(client, toolkit);
		createBinDirEntry(client, toolkit);
		createNotifyOnInstallEntry(client, toolkit);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		processTimeoutEntry.setEnabled(enabled);
		vendorDirEntry.setEnabled(enabled);
		binDirEntry.setEnabled(enabled);
		notifyOnInstallEntry.setEnabled(enabled);
	}

	private void createProcessTimeoutEntry(Composite client, FormToolkit toolkit) {
		processTimeoutEntry = new FormEntry(client, toolkit, "process-timeout", null, false);
		Integer processTimeout = composerPackage.getConfig().getProcessTimeout();
		if (processTimeout != null) {
			processTimeoutEntry.setValue("" + processTimeout, true);
		}

		processTimeoutEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				if (entry.getValue().isEmpty()) {
					composerPackage.getConfig().remove("process-timeout");
				} else {
					composerPackage.getConfig().set("process-timeout", Integer.valueOf(entry.getValue()));
				}
			}
		});
		composerPackage.getConfig().addPropertyChangeListener("process-timeout", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				Integer processTimeout = composerPackage.getConfig().getProcessTimeout();
				if (processTimeout == null) {
					processTimeoutEntry.setValue("", true);
				} else {
					processTimeoutEntry.setValue("" + processTimeout, true);
				}
			}
		});
	}

	private void createVendorDirEntry(Composite client, FormToolkit toolkit) {
		vendorDirEntry = new FormEntry(client, toolkit, "vendor-dir", null, false);
		String vendorDir = composerPackage.getConfig().getVendorDir();
		if (vendorDir != null) {
			vendorDirEntry.setValue(vendorDir, true);
		}

		vendorDirEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				if (entry.getValue().isEmpty()) {
					composerPackage.getConfig().remove("vendor-dir");
				} else {
					composerPackage.getConfig().set("vendor-dir", entry.getValue());
				}

			}
		});
		composerPackage.getConfig().addPropertyChangeListener("vendor-dir", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String vendorDir = composerPackage.getConfig().getVendorDir();
				if (vendorDir != null) {
					vendorDirEntry.setValue("", true);
				} else {
					vendorDirEntry.setValue(composerPackage.getConfig().getVendorDir(), true);
				}
			}
		});
	}

	private void createBinDirEntry(Composite client, FormToolkit toolkit) {
		binDirEntry = new FormEntry(client, toolkit, "bin-dir", null, false);
		String binDir = composerPackage.getConfig().getBinDir();
		if (binDir != null) {
			binDirEntry.setValue(binDir, true);
		}

		binDirEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				if (entry.getValue().isEmpty()) {
					composerPackage.getConfig().remove("bin-dir");
				} else {
					composerPackage.getConfig().set("bin-dir", entry.getValue());
				}
			}
		});
		composerPackage.getConfig().addPropertyChangeListener("bin-dir", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String binDir = composerPackage.getConfig().getBinDir();
				if (binDir == null) {
					binDirEntry.setValue("", true);
				} else {
					binDirEntry.setValue(binDir, true);
				}
			}
		});
	}

	private void createNotifyOnInstallEntry(Composite client, FormToolkit toolkit) {
		notifyOnInstallEntry = new BooleanFormEntry(client, toolkit, "notify-on-install");
		notifyOnInstallEntry.setValue(composerPackage.getConfig().getNotifyOnInstall());

		notifyOnInstallEntry.addBooleanFormEntryListener(new IBooleanFormEntryListener() {
			public void selectionChanged(BooleanFormEntry entry) {
				if (entry.getValue()) {
					composerPackage.getConfig().remove("notify-on-install");
				} else {
					composerPackage.getConfig().setNotifyOnInstall(entry.getValue());
				}
			}
		});
		composerPackage.getConfig().addPropertyChangeListener("notify-on-install", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				notifyOnInstallEntry.setValue(composerPackage.getConfig().getNotifyOnInstall(), true);
			}
		});
	}

}
