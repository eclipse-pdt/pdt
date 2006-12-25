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
package org.eclipse.php.debug.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.debug.core.preferences.PHPexeItem;
import org.eclipse.php.debug.core.preferences.PHPexes;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.server.core.Server;
import org.eclipse.php.server.core.manager.ServersManager;
import org.eclipse.php.ui.preferences.AbstractPHPPreferencePageBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.wst.xml.ui.internal.preferences.EncodingSettings;
import org.osgi.service.prefs.BackingStoreException;

public class PHPDebugPreferencesAddon extends AbstractPHPPreferencePageBlock {

	private static final String SERVERS_PAGE_ID = "org.eclipse.php.server.internal.ui.PHPServersPreferencePage";
	private static final String PHP_EXE_PAGE_ID = "org.eclipse.php.debug.ui.preferencesphps.PHPsPreferencePage";
	private Button fStopAtFirstLine;
	private Combo fDefaultServer;
	private Combo fDefaultPHPExe;
	private EncodingSettings fEncodingSettings;
	private PropertyPage propertyPage;

	public void setCompositeAddon(Composite parent) {
		Composite composite = addPageContents(parent);
		addProjectPreferenceSubsection(createSubsection(composite, PHPDebugUIMessages.PhpDebugPreferencePage_6));
	}

	public void initializeValues(PropertyPage propertyPage) {
		this.propertyPage = propertyPage;
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		IScopeContext[] preferenceScopes = createPreferenceScopes(propertyPage);

		boolean stopAtFirstLine = prefs.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE);
		String serverName = ServersManager.getDefaultServer(null).getName();
		String phpExe = prefs.getString(PHPDebugCorePreferenceNames.DEFAULT_PHP);
		String transferEncoding = prefs.getString(PHPDebugCorePreferenceNames.TRANSFER_ENCODING);
		loadServers(fDefaultServer);
		loadPHPExes(fDefaultPHPExe);
		if (preferenceScopes[0] instanceof ProjectScope) {
			IEclipsePreferences node = preferenceScopes[0].getNode(getPreferenceNodeQualifier());
			if (node != null && getProject(propertyPage) != null) {
				String projectServerName = ServersManager.getDefaultServer(getProject(propertyPage)).getName();
				if (!projectServerName.equals("")) {
					serverName = projectServerName;
					stopAtFirstLine = node.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, stopAtFirstLine);
					transferEncoding = node.get(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, "");
					phpExe = node.get(PHPDebugCorePreferenceNames.DEFAULT_PHP, phpExe);
				}
			}
		}
		fStopAtFirstLine.setSelection(stopAtFirstLine);
		fDefaultServer.select(fDefaultServer.indexOf(serverName));
		fDefaultPHPExe.select(fDefaultPHPExe.indexOf(phpExe));
		fEncodingSettings.setIANATag(transferEncoding);
	}

	public boolean performOK(boolean isProjectSpecific) {
		savePreferences(isProjectSpecific);
		return true;
	}

	public void performApply(boolean isProjectSpecific) {
		performOK(isProjectSpecific);
	}

	public boolean performCancel() {
		return true;
	}

	public void performDefaults() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		fStopAtFirstLine.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE));
		loadServers(fDefaultServer);
		loadPHPExes(fDefaultPHPExe);
		fEncodingSettings.setIANATag(prefs.getDefaultString(PHPDebugCorePreferenceNames.TRANSFER_ENCODING));
	}

	protected String getPreferenceNodeQualifier() {
		return PHPProjectPreferences.getPreferenceNodeQualifier();
	}

	private void loadServers(Combo combo) {
		combo.removeAll();
		Server[] servers = ServersManager.getServers();
		if (servers != null) {
			for (int i = 0; i < servers.length; i++) {
				combo.add(servers[i].getName());
			}
			// select first item in list
			if (combo.getItemCount() > 0) {
				combo.select(0);
			}
		}
	}

	private void loadPHPExes(Combo combo) {
		combo.removeAll();
		PHPexes exes = new PHPexes();
		exes.load(PHPProjectPreferences.getModelPreferences());
		PHPexeItem[] phps = exes.getItems();
		if (phps != null) {
			for (int i = 0; i < phps.length; i++) {
				combo.add(phps[i].getName());
			}
			// select first item in list
			if (combo.getItemCount() > 0) {
				combo.select(0);
			}
		}
	}

	private void addProjectPreferenceSubsection(Composite composite) {
		addLabelControl(composite, PHPDebugUIMessages.PhpDebugPreferencePage_9, ServersManager.DEFAULT_SERVER_PREFERENCES_KEY);
		fDefaultServer = addCombo(composite, 2);
		addLink(composite, PHPDebugUIMessages.PhpDebugPreferencePage_serversLink, SERVERS_PAGE_ID);
		addLabelControl(composite, PHPDebugUIMessages.PhpDebugPreferencePage_12, PHPDebugCorePreferenceNames.DEFAULT_PHP);
		fDefaultPHPExe = addCombo(composite, 2);
		addLink(composite, PHPDebugUIMessages.PhpDebugPreferencePage_installedPHPsLink, PHP_EXE_PAGE_ID);
		fEncodingSettings = addEncodingSettings(composite, "Debug Transfer Encoding");
		fStopAtFirstLine = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_1, PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, 0);
	}

	private void addLink(Composite parent, String label, final String propertyPageID) {
		Link link = new Link(parent, SWT.NONE);
		link.setFont(parent.getFont());
		link.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, true, false));
		link.setText(label);
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(PHPDebugPreferencesAddon.this.propertyPage.getShell(), propertyPageID, null, null);
				dialog.setBlockOnOpen(true);
				dialog.addPageChangedListener(new IPageChangedListener() {
					public void pageChanged(PageChangedEvent event) {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								String selectedServer = fDefaultServer.getText();
								String selectedPHP = fDefaultPHPExe.getText();
								loadServers(fDefaultServer);
								loadPHPExes(fDefaultPHPExe);
								selectComboItem(fDefaultServer, fDefaultServer.indexOf(selectedServer));
								selectComboItem(fDefaultPHPExe, fDefaultPHPExe.indexOf(selectedPHP));
							}
						});
					}
				});
				dialog.open();
			}
		});
	}

	private void selectComboItem(Combo combo, int itemIndex) {
		if (itemIndex < 0) {
			if (combo.getItemCount() > 0) {
				combo.select(0);
			}
		} else {
			combo.select(itemIndex);
		}
	}

	private EncodingSettings addEncodingSettings(Composite parent, String label) {
		EncodingSettings encodingSettings = new EncodingSettings(parent, "Debug Transfer Encoding");
		GridData data = (GridData) encodingSettings.getLayoutData();
		data.horizontalSpan = 3;
		data.verticalAlignment = 0;
		data.verticalIndent = -5;
		data.horizontalIndent = -5;
		encodingSettings.setLayoutData(data);
		return encodingSettings;
	}

	private Combo addCombo(Composite parent, int horizontalIndent) {
		Combo combo = new Combo(parent, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = horizontalIndent;
		combo.setLayoutData(gd);
		return combo;
	}

	private void savePreferences(boolean isProjectSpecific) {
		String phpExe = fDefaultPHPExe.getText();
		// Check if it's still valide, since this method can be called after removing a php executable from another preferences page (PHP Executables)
		PHPexes exes = new PHPexes();
		exes.load(PHPProjectPreferences.getModelPreferences());
		if (exes.getItem(phpExe) == null) {
			PHPexeItem item = exes.getDefaultItem();
			if (item != null) {
				phpExe = item.getName();
			} else {
				phpExe = "";
			}
		}
		// TODO - Might do the same for the default server
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		IScopeContext[] preferenceScopes = createPreferenceScopes(propertyPage);
		IEclipsePreferences debugUINode = preferenceScopes[0].getNode(getPreferenceNodeQualifier());
		IProject project = getProject(propertyPage);
		if (isProjectSpecific && debugUINode != null && preferenceScopes[0] instanceof ProjectScope && project != null) {
			debugUINode.putBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, fStopAtFirstLine.getSelection());
			debugUINode.put(PHPDebugCorePreferenceNames.DEFAULT_PHP, phpExe);
			debugUINode.put(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, fEncodingSettings.getIANATag());
			ServersManager.setDefaultServer(project, fDefaultServer.getText());
		} else {
			if (project == null) {
				prefs.setValue(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, fStopAtFirstLine.getSelection());
				prefs.setValue(PHPDebugCorePreferenceNames.DEFAULT_PHP, phpExe);
				prefs.setValue(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, fEncodingSettings.getIANATag());
				ServersManager.setDefaultServer(null, fDefaultServer.getText());
			} else {
				if (debugUINode != null) {
					debugUINode.remove(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE);
					debugUINode.remove(PHPDebugCorePreferenceNames.DEBUG_PORT);
					debugUINode.remove(PHPDebugCorePreferenceNames.DEFAULT_PHP);
					ServersManager.setDefaultServer(project, (Server) null);
					debugUINode.remove(PHPDebugCorePreferenceNames.TRANSFER_ENCODING);
				}
			}
		}
		try {
			debugUINode.flush();
			PHPDebugPlugin.getDefault().savePluginPreferences();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}
}
