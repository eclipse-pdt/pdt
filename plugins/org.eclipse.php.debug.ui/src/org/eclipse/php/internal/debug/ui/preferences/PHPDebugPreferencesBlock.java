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
package org.eclipse.php.internal.debug.ui.preferences;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.*;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.preferences.AbstractPHPPreferencePageBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.wst.xml.ui.internal.preferences.EncodingSettings;
import org.osgi.service.prefs.BackingStoreException;

/**
 * PHP debug options preferences add-on.
 * This add-on specifies the default debugger, executable and server for the workspace or the
 * project specific.
 *
 * @author Shalom Gibly
 */
public class PHPDebugPreferencesBlock extends AbstractPHPPreferencePageBlock {

	private static final String DEBUGGERS_PAGE_ID = "org.eclipse.php.debug.ui.installedDebuggersPage"; //$NON-NLS-1$
	private static final String SERVERS_PAGE_ID = "org.eclipse.php.server.internal.ui.PHPServersPreferencePage"; //$NON-NLS-1$
	private static final String PHP_EXE_PAGE_ID = "org.eclipse.php.debug.ui.preferencesphps.PHPsPreferencePage"; //$NON-NLS-1$

	private Button fStopAtFirstLine;
	private Text fClientIP;
	private Label fClientIPLabel;
	private Combo fDefaultDebugger;
	private Combo fDefaultServer;
	private Combo fDefaultPHPExe;
	private Collection<String> debuggersIds;
	private EncodingSettings fDebugEncodingSettings;
	private EncodingSettings fOutputEncodingSettings;
	private PreferencePage propertyPage;

	public void setCompositeAddon(Composite parent) {
		Composite composite = addPageContents(parent);
		addProjectPreferenceSubsection(composite);
	}

	public void initializeValues(PreferencePage propertyPage) {
		this.propertyPage = propertyPage;
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		IScopeContext[] preferenceScopes = createPreferenceScopes(propertyPage);

		boolean stopAtFirstLine = prefs.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE);
		String debuggerName = PHPDebuggersRegistry.getDebuggerName(prefs.getString(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID));
		String serverName = ServersManager.getDefaultServer(null).getName();
		PHPexes exes = PHPexes.getInstance();
		String phpExeName = PHPDebugUIMessages.PhpDebugPreferencePage_noExeDefined;
		if (exes.hasItems(PHPDebugPlugin.getCurrentDebuggerId())) {
			phpExeName = exes.getDefaultItem(PHPDebugPlugin.getCurrentDebuggerId()).getName();
		}
		String transferEncoding = prefs.getString(PHPDebugCorePreferenceNames.TRANSFER_ENCODING);
		String outputEncoding = prefs.getString(PHPDebugCorePreferenceNames.OUTPUT_ENCODING);
		loadDebuggers(fDefaultDebugger);
		loadServers(fDefaultServer);
		boolean exeLoaded = false;
		// Update the values in case we have a project-specific settings.
		if (preferenceScopes[0] instanceof ProjectScope) {
			IEclipsePreferences node = preferenceScopes[0].getNode(getPreferenceNodeQualifier());
			if (node != null && getProject(propertyPage) != null) {
				String projectServerName = ServersManager.getDefaultServer(getProject(propertyPage)).getName();
				if (!projectServerName.equals("")) { //$NON-NLS-1$
					String debuggerId = node.get(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, PHPDebugPlugin.getCurrentDebuggerId());
					debuggerName = PHPDebuggersRegistry.getDebuggerName(debuggerId);
					serverName = projectServerName;
					stopAtFirstLine = node.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, stopAtFirstLine);
					transferEncoding = node.get(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, ""); //$NON-NLS-1$
					outputEncoding = node.get(PHPDebugCorePreferenceNames.OUTPUT_ENCODING, ""); //$NON-NLS-1$
					phpExeName = node.get(PHPDebugCorePreferenceNames.DEFAULT_PHP, phpExeName);
					// Check that if the project had a non-defined exe, and now there is one that is valid. we set
					// it with the new valid default exe.
					if (PHPDebugUIMessages.PhpDebugPreferencePage_noExeDefined.equals(phpExeName)) {
						if (exes.hasItems(debuggerId)) {
							phpExeName = exes.getDefaultItem(debuggerId).getName();
							node.put(PHPDebugCorePreferenceNames.DEFAULT_PHP, phpExeName);
							try {
								node.flush();
							} catch (BackingStoreException e) {
							}
						}
					}
					loadPHPExes(fDefaultPHPExe, exes.getItems(node.get(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, PHPDebugPlugin.getCurrentDebuggerId())));
					exeLoaded = true;
				}
			}
		}
		if (!exeLoaded) {
			loadPHPExes(fDefaultPHPExe, exes.getItems(PHPDebugPlugin.getCurrentDebuggerId()));
		}
		fStopAtFirstLine.setSelection(stopAtFirstLine);
		fClientIP.setText(prefs.getString(PHPDebugCorePreferenceNames.CLIENT_IP)); 
		fDefaultDebugger.select(fDefaultDebugger.indexOf(debuggerName));
		fDefaultServer.select(fDefaultServer.indexOf(serverName));
		fDefaultPHPExe.select(fDefaultPHPExe.indexOf(phpExeName));
		fDebugEncodingSettings.setIANATag(transferEncoding);
		fOutputEncodingSettings.setIANATag(outputEncoding);

		if (getProject(propertyPage) != null) {
			fClientIP.setVisible(false);
			fClientIPLabel.setVisible(false);
		}
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
		fClientIP.setText(prefs.getDefaultString(PHPDebugCorePreferenceNames.CLIENT_IP));
		loadDebuggers(fDefaultDebugger);
		loadServers(fDefaultServer);
		loadPHPExes(fDefaultPHPExe, PHPexes.getInstance().getItems(PHPDebugPlugin.getCurrentDebuggerId()));
		fDebugEncodingSettings.setIANATag(prefs.getDefaultString(PHPDebugCorePreferenceNames.TRANSFER_ENCODING));
		fOutputEncodingSettings.setIANATag(prefs.getDefaultString(PHPDebugCorePreferenceNames.OUTPUT_ENCODING));
	}

	protected String getPreferenceNodeQualifier() {
		return PHPProjectPreferences.getPreferenceNodeQualifier();
	}

	private void addProjectPreferenceSubsection(Composite composite) {

		Composite inner = new Composite(composite, SWT.NONE);
		inner.setFont(composite.getFont());
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 10;
		inner.setLayout(layout);
		inner.setLayoutData(new GridData(GridData.FILL_BOTH));

		addLabelControl(inner, PHPDebugUIMessages.PhpDebugPreferencePage_phpDebugger, PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID);
		fDefaultDebugger = addCombo(inner, 2);
		addLink(inner, "<a>Configure...</a>", DEBUGGERS_PAGE_ID);

		addLabelControl(inner, PHPDebugUIMessages.PhpDebugPreferencePage_9, ServersManager.DEFAULT_SERVER_PREFERENCES_KEY);
		fDefaultServer = addCombo(inner, 2);
		addLink(inner, PHPDebugUIMessages.PhpDebugPreferencePage_serversLink, SERVERS_PAGE_ID);

		addLabelControl(inner, PHPDebugUIMessages.PhpDebugPreferencePage_12, PHPDebugCorePreferenceNames.DEFAULT_PHP);
		fDefaultPHPExe = addCombo(inner, 2);
		addLink(inner, PHPDebugUIMessages.PhpDebugPreferencePage_installedPHPsLink, PHP_EXE_PAGE_ID);

		new Label(composite, SWT.NONE); // dummy label

		addLabelControl(composite, PHPDebugUIMessages.PHPDebugPreferencesAddon_debugTransferEncoding, PHPDebugCorePreferenceNames.TRANSFER_ENCODING);
		inner = new Composite(composite, SWT.NONE);
		inner.setFont(composite.getFont());
		inner.setLayout(new GridLayout(3, false));
		fDebugEncodingSettings = addEncodingSettings(inner, PHPDebugUIMessages.PHPDebugPreferencesAddon_selectedEncoding);

		addLabelControl(composite, PHPDebugUIMessages.PHPDebugPreferencesAddon_debugOutputEncoding, PHPDebugCorePreferenceNames.OUTPUT_ENCODING);
		inner = new Composite(composite, SWT.NONE);
		inner.setFont(composite.getFont());
		inner.setLayout(new GridLayout(3, false));
		fOutputEncodingSettings = addEncodingSettings(inner, PHPDebugUIMessages.PHPDebugPreferencesAddon_selectedEncoding);
		fStopAtFirstLine = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_1, PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, 0);

		fClientIPLabel = addLabelControl(composite, "Client Host/IP:", PHPDebugCorePreferenceNames.CLIENT_IP);
		fClientIP = new Text(composite, SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 2;
		fClientIP.setLayoutData(layoutData);

		// Add a default debugger listener that will update the possible executables
		// and, maybe, servers that can work with this debugger.
		fDefaultDebugger.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String selectedDebugger = getSelectedDebuggerId();
				PHPexeItem[] items = PHPexes.getInstance().getItems(selectedDebugger);
				loadPHPExes(fDefaultPHPExe, items);
			}
		});
	}

	private void loadPHPExes(Combo combo, PHPexeItem[] items) {
		combo.removeAll();
		if (items == null || items.length == 0) {
			combo.add(PHPDebugUIMessages.PhpDebugPreferencePage_noExeDefined);
			combo.select(0);
			return;
		}
		for (PHPexeItem element : items) {
			combo.add(element.getName());
		}
		// select the default item for the current selected debugger
		if (fDefaultDebugger.getItemCount() > 0) {
			PHPexeItem defaultItem = PHPexes.getInstance().getDefaultItem(getSelectedDebuggerId());
			String defaultItemName;
			if (defaultItem != null) {
				defaultItemName = defaultItem.getName();
			} else {
				defaultItemName = PHPDebugUIMessages.PHPDebuggersTable_notDefined;
				if (combo.indexOf(defaultItemName) == -1) {
					combo.add(defaultItemName, 0);
					combo.select(0);
				}
			}
			int index = combo.indexOf(defaultItemName);
			if (index > -1) {
				combo.select(index);
			} else if (combo.getItemCount() > 0) {
				// select first item in list
				combo.select(0);
			}
		}

	}

	private void loadServers(Combo combo) {
		combo.removeAll();
		Server[] servers = ServersManager.getServers();
		if (servers != null) {
			for (Server element : servers) {
				combo.add(element.getName());
			}
			// select first item in list
			if (combo.getItemCount() > 0) {
				combo.select(0);
			}
		}
	}

	private void loadDebuggers(Combo combo) {
		debuggersIds = PHPDebuggersRegistry.getDebuggersIds();
		String defaultDebuggerID = DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID;
		combo.removeAll();
		Iterator<String> debuggers = debuggersIds.iterator();
		int defaultIndex = 0;
		int index = 0;
		while (debuggers.hasNext()) {
			String id = debuggers.next();
			if (defaultDebuggerID.equals(id)) {
				defaultIndex = index;
			} else {
				index++;
			}
			String debuggerName = PHPDebuggersRegistry.getDebuggerName(id);
			combo.add(debuggerName);
		}
		// select the default item in list
		if (combo.getItemCount() > 0) {
			combo.select(defaultIndex);
		}
	}

	private void addLink(Composite parent, String label, final String propertyPageID) {
		Link link = new Link(parent, SWT.NONE);
		link.setFont(parent.getFont());
		link.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, true, false));
		link.setText(label);
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(PHPDebugPreferencesBlock.this.propertyPage.getShell(), propertyPageID, null, null);
				dialog.setBlockOnOpen(true);
				dialog.addPageChangedListener(new IPageChangedListener() {
					public void pageChanged(PageChangedEvent event) {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								String selectedDebugger = fDefaultDebugger.getText();
								String selectedServer = fDefaultServer.getText();
								String selectedPHP = fDefaultPHPExe.getText();
								loadDebuggers(fDefaultDebugger);
								loadServers(fDefaultServer);
								loadPHPExes(fDefaultPHPExe, PHPexes.getInstance().getItems(getSelectedDebuggerId()));
								selectComboItem(fDefaultDebugger, fDefaultDebugger.indexOf(selectedDebugger));
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
		EncodingSettings encodingSettings = new EncodingSettings(parent, label);
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
		// Check if it's still valid, since this method can be called after removing a php executable from another preferences page (PHP Executables)
		PHPexes exes = PHPexes.getInstance();
		if (exes.getItem(getSelectedDebuggerId(), phpExe) == null) {
			PHPexeItem item = exes.getDefaultItem(getSelectedDebuggerId());
			if (item != null) {
				phpExe = item.getName();
			} else {
				phpExe = ""; //$NON-NLS-1$
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
			debugUINode.put(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, fDebugEncodingSettings.getIANATag());
			debugUINode.put(PHPDebugCorePreferenceNames.OUTPUT_ENCODING, fOutputEncodingSettings.getIANATag());
			debugUINode.put(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, getSelectedDebuggerId());
			ServersManager.setDefaultServer(project, fDefaultServer.getText());
		} else {
			if (project == null) {
				// Workspace settings
				prefs.setValue(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, fStopAtFirstLine.getSelection());
				prefs.setValue(PHPDebugCorePreferenceNames.CLIENT_IP, fClientIP.getText());
				prefs.setValue(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, fDebugEncodingSettings.getIANATag());
				prefs.setValue(PHPDebugCorePreferenceNames.OUTPUT_ENCODING, fOutputEncodingSettings.getIANATag());
				prefs.setValue(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, getSelectedDebuggerId());
				exes.setDefaultItem(getSelectedDebuggerId(), phpExe);
				ServersManager.setDefaultServer(null, fDefaultServer.getText());
			} else {
				if (debugUINode != null) {
					// Removed a project specific
					debugUINode.remove(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE);
					//					debugUINode.remove(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT); // No need
					debugUINode.remove(PHPDebugCorePreferenceNames.DEFAULT_PHP);
					ServersManager.setDefaultServer(project, (Server) null);
					debugUINode.remove(PHPDebugCorePreferenceNames.TRANSFER_ENCODING);
					debugUINode.remove(PHPDebugCorePreferenceNames.OUTPUT_ENCODING);
					debugUINode.remove(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID);
				}
			}
		}
		try {
			debugUINode.flush();
			exes.save();
			PHPDebugPlugin.getDefault().savePluginPreferences();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}

	private String getSelectedDebuggerId() {
		int selectedIndex = fDefaultDebugger.getSelectionIndex();
		String debuggerId = DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID; // default
		if (selectedIndex > -1 && debuggersIds.size() > selectedIndex) {
			debuggerId = debuggersIds.toArray()[selectedIndex].toString();
		}
		return debuggerId;
	}
}
