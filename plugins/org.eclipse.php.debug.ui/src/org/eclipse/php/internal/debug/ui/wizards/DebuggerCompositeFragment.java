/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.core.IUniqueIdentityElement;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.PHPExeUtil;
import org.eclipse.php.internal.debug.core.PHPExeUtil.PHPModuleInfo;
import org.eclipse.php.internal.debug.core.debugger.*;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Debugger composite fragment.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class DebuggerCompositeFragment extends CompositeFragment {

	public static final String ID = "org.eclipse.php.internal.ui.fragments.debuggerCompositeFragment"; //$NON-NLS-1$

	// A class used as a local original IServerWorkingCopy values cache.
	private class ValuesCache {
		String debuggerId;

		public ValuesCache() {
		}

		public ValuesCache(ValuesCache cache) {
			this.debuggerId = cache.debuggerId;
		}
	}

	private List<String> debuggersIds;
	private Combo debuggerCombo;
	private ValuesCache originalValuesCache = new ValuesCache();
	private ValuesCache modifiedValuesCache;
	private IDebuggerSettingsSection debuggerSettingsSection;
	private IDebuggerSettingsWorkingCopy debuggerSettingsWC;
	private Map<String, IDebuggerSettingsWorkingCopy> settingsWCBuffer = new HashMap<String, IDebuggerSettingsWorkingCopy>();
	private String detectedDebuggerId = null;

	/**
	 * Creates new debugger composite fragment.
	 * 
	 * @param parent
	 * @param handler
	 * @param isForEditing
	 */
	public DebuggerCompositeFragment(Composite parent, IControlHandler handler,
			boolean isForEditing) {
		super(parent, handler, isForEditing);
		setDisplayName(Messages.DebuggerCompositeFragment_Debugger);
		setTitle(Messages.DebuggerCompositeFragment_Edit_debugger_settings);
		controlHandler.setTitle(getTitle());
		setDescription(Messages.DebuggerCompositeFragment_Configure_server_debugger_settings);
		controlHandler.setDescription(getDescription());
		setImageDescriptor(PHPDebugUIImages
				.getImageDescriptor(PHPDebugUIImages.IMG_WIZBAN_DEBUG_SERVER));
		controlHandler.setImageDescriptor(getImageDescriptor());
		debuggersIds = new LinkedList<String>(
				PHPDebuggersRegistry.getDebuggersIds());
		createControl();
	}

	@Override
	public String getId() {
		return ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.CompositeFragment#performOk()
	 */
	@Override
	public boolean performOk() {
		if (debuggerSettingsSection != null) {
			boolean isOK = debuggerSettingsSection.performOK();
			if (!isOK)
				return isOK;
		}
		if (debuggerSettingsWC != null && debuggerSettingsWC.isDirty())
			DebuggerSettingsManager.INSTANCE.save(debuggerSettingsWC);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.wizards.CompositeFragment#performCancel()
	 */
	@Override
	public boolean performCancel() {
		if (debuggerSettingsSection != null)
			debuggerSettingsSection.performCancel();
		return super.performCancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.CompositeFragment#validate()
	 */
	@Override
	public void validate() {
		// Validation is being done in settings composite
		IUniqueIdentityElement data = (IUniqueIdentityElement) getData();
		if (data instanceof PHPexeItem) {
			PHPexeItem exeItem = (PHPexeItem) data;
			IStatus debuggerStatus = Status.OK_STATUS;
			AbstractDebuggerConfiguration[] debuggers = PHPDebuggersRegistry
					.getDebuggersConfigurations();
			for (AbstractDebuggerConfiguration debugger : debuggers) {
				if (exeItem.getDebuggerID().equals(debugger.getDebuggerId())) {
					debuggerStatus = debugger.validate(exeItem);
				}
			}
			if (debuggerStatus.getSeverity() != IStatus.OK) {
				if (debuggerStatus.getSeverity() == IStatus.ERROR) {
					setMessage(debuggerStatus.getMessage(),
							IMessageProvider.ERROR);
					// set complete ?
					return;
				} else {
					setMessage(debuggerStatus.getMessage(),
							IMessageProvider.WARNING);
				}
			} else {
				setMessage(getDescription(), IMessageProvider.NONE);
			}
		}
		if (debuggerSettingsSection != null)
			debuggerSettingsSection.validate();
	}

	/**
	 * Override the super setData to handle only Server types.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given object is not a {@link Server}
	 */
	public void setData(Object debuggerOwner) throws IllegalArgumentException {
		if (debuggerOwner != null
				&& !(debuggerOwner instanceof IUniqueIdentityElement)) {
			throw new IllegalArgumentException(
					"The given object is not a Server"); //$NON-NLS-1$
		}
		super.setData(debuggerOwner);
		init();
		validate();
	}

	public IUniqueIdentityElement getDebuggerOwner() {
		return (IUniqueIdentityElement) getData();
	}

	void createSettings(String debuggerId) {
		IUniqueIdentityElement debuggerOwner = getDebuggerOwner();
		if (debuggerOwner == null || debuggerId == null)
			return;
		IDebuggerSettings settings = DebuggerSettingsManager.INSTANCE
				.findSettings(debuggerOwner, debuggerId);
		if (settings == null)
			return;
		if (debuggerSettingsSection != null) {
			debuggerSettingsSection.getComposite().dispose();
		}
		debuggerSettingsWC = getSettingsWC(debuggerId, settings);
		IDebuggerSettingsProvider provider = DebuggerSettingsProviderRegistry
				.getProvider(debuggerId);
		IDebuggerSettingsSectionBuilder sectionBuilder = DebuggerSettingsSectionBuildersRegistry
				.getBuilder(provider.getId());
		debuggerSettingsSection = sectionBuilder.build(this, debuggerSettingsWC);
		this.getParent().layout(true, true);
	}

	IDebuggerSettingsWorkingCopy getSettingsWC(String debuggerId,
			IDebuggerSettings settings) {
		IDebuggerSettingsWorkingCopy debuggerSettingsWC = settingsWCBuffer
				.get(debuggerId);
		if (debuggerSettingsWC == null) {
			debuggerSettingsWC = DebuggerSettingsManager.INSTANCE
					.createWorkingCopy(settings);
			settingsWCBuffer.put(debuggerId, debuggerSettingsWC);
		}
		return debuggerSettingsWC;
	}

	protected void createControl() {
		GridLayout layout = new GridLayout(1, true);
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite debuggerChoice = new Composite(this, SWT.NONE);
		GridLayout dcLayout = new GridLayout();
		dcLayout.numColumns = 2;
		debuggerChoice.setLayout(dcLayout);
		debuggerChoice.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label debuggerLabel = new Label(debuggerChoice, SWT.NONE);
		debuggerLabel.setText("Debugger:"); //$NON-NLS-1$
		debuggerLabel.setLayoutData(new GridData());
		debuggerCombo = new Combo(debuggerChoice, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData dcData = new GridData(SWT.LEFT, SWT.FILL, true, false);
		debuggerCombo.setLayoutData(dcData);
		debuggerCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (getDebuggerOwner() != null) {
					modifiedValuesCache.debuggerId = debuggersIds
							.get(debuggerCombo.getSelectionIndex());
					updateItem();
					// create settings panel
					createSettings(modifiedValuesCache.debuggerId);
					// Validate owner debugger data
					validate();
				}
			}
		});
		for (int i = 0; i < debuggersIds.size(); ++i) {
			String id = debuggersIds.get(i);
			String debuggerName = PHPDebuggersRegistry.getDebuggerName(id);
			debuggerCombo.add(debuggerName, i);
		}
		Label separator = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		Dialog.applyDialogFont(this);
		// Initialize & validate
		init();
		validate();
		// Set focus on debuggers combo
		debuggerCombo.forceFocus();
	}

	protected void init() {
		IUniqueIdentityElement debuggerOwner = getDebuggerOwner();
		if (debuggerOwner == null)
			return;
		if (debuggerOwner instanceof Server) {
			originalValuesCache.debuggerId = ((Server) debuggerOwner)
					.getDebuggerId();
		} else if (debuggerOwner instanceof PHPexeItem) {
			originalValuesCache.debuggerId = ((PHPexeItem) debuggerOwner)
					.getDebuggerID();
		}
		// Clone the cache lazily
		if (modifiedValuesCache == null)
			modifiedValuesCache = new ValuesCache(originalValuesCache);
		setDebugger();
	}

	private void setDebugger() {
		String debuggerId = null;
		// Check if owner has debugger ID set already
		if (modifiedValuesCache.debuggerId != null) {
			debuggerId = modifiedValuesCache.debuggerId;
		}
		// If owner doesn't have debugger ID, detect one or set default
		else {
			detectDebugger();
			if (detectedDebuggerId == null)
				debuggerId = PHPDebugPlugin.getCurrentDebuggerId();
			else
				debuggerId = modifiedValuesCache.debuggerId = detectedDebuggerId;
		}
		// Set combo to appropriate debugger ID
		String name = PHPDebuggersRegistry.getDebuggerName(debuggerId);
		String[] values = debuggerCombo.getItems();
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(name)) {
				debuggerCombo.select(i);
				break;
			}
		}

	}

	private void detectDebugger() {
		IUniqueIdentityElement data = (IUniqueIdentityElement) getData();
		// Check if debugger module is installed on top of PHP executable
		if (data instanceof PHPexeItem) {
			PHPexeItem exeItem = (PHPexeItem) data;
			List<PHPModuleInfo> modules = PHPExeUtil.getModules(exeItem);
			AbstractDebuggerConfiguration[] debuggers = PHPDebuggersRegistry
					.getDebuggersConfigurations();
			for (AbstractDebuggerConfiguration debugger : debuggers) {
				for (PHPModuleInfo module : modules)
					if (module.getName().equalsIgnoreCase(
							debugger.getModuleId())) {
						detectedDebuggerId = debugger.getDebuggerId();
						break;
					}
			}
		}
	}

	private void updateItem() {
		IUniqueIdentityElement debuggerOwner = getDebuggerOwner();
		if (debuggerOwner instanceof Server) {
			((Server) debuggerOwner)
					.setDebuggerId(modifiedValuesCache.debuggerId);
		} else if (debuggerOwner instanceof PHPexeItem) {
			((PHPexeItem) debuggerOwner)
					.setDebuggerID(modifiedValuesCache.debuggerId);
		}
	}

}
