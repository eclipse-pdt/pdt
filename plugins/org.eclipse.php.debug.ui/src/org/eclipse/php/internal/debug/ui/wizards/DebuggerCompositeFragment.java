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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.core.IUniqueIdentityElement;
import org.eclipse.php.internal.debug.core.PHPExeUtil;
import org.eclipse.php.internal.debug.core.PHPExeUtil.PHPModuleInfo;
import org.eclipse.php.internal.debug.core.debugger.*;
import org.eclipse.php.internal.debug.core.preferences.*;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.IControlHandler.Kind;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * Debugger settings composite fragment.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DebuggerCompositeFragment extends CompositeFragment {

	public static final String ID = "org.eclipse.php.debug.ui.fragments.debuggerCompositeFragment"; //$NON-NLS-1$

	// A class used as a local original IServerWorkingCopy values cache.
	private class ValuesCache {
		String debuggerId;

		public ValuesCache() {
		}

		public ValuesCache(ValuesCache cache) {
			this.debuggerId = cache.debuggerId;
		}
	}

	private class EmptySettingsSection implements IDebuggerSettingsSection {

		private EmptySettingsSection(Composite settingsComposite) {
			Composite empty = new Composite(settingsComposite, SWT.NONE);
			empty.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}

		@Override
		public boolean performOK() {
			return true;
		}

		@Override
		public boolean performCancel() {
			return true;
		}

		@Override
		public void validate() {
			// Reset state
			setMessage(getDescription(), IMessageProvider.NONE);
		}

		@Override
		public boolean canTest() {
			return false;
		}

		@Override
		public void performTest() {
			// ignore
		}

	}

	private class PHPExeListener implements IPHPexeItemListener {

		@Override
		public void phpExeChanged(PHPexeItemEvent event) {
			if (event.getProperty().equals(IPHPexeItemProperties.PROP_EXE_LOCATION)
					|| event.getProperty().equals(IPHPexeItemProperties.PROP_INI_LOCATION)
					|| event.getProperty().equals(IPHPexeItemProperties.PROP_USE_DEFAULT_INI)) {
				detectDebugger = true;
			} else if (!updatingDebuggerId && event.getProperty().equals(IPHPexeItemProperties.PROP_DEBUGGER_ID)) {
				modifiedValuesCache.debuggerId = (String) event.getNewValue();
				Display.getDefault().syncExec(new SelectDebuggerRunnable());
			}
		}

	}

	private class PHPServerListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (!updatingDebuggerId && event.getPropertyName().equals(Server.DEBUGGER)) {
				modifiedValuesCache.debuggerId = (String) event.getNewValue();
				Display.getDefault().syncExec(new SelectDebuggerRunnable());
			}
		}

	}

	private class SelectDebuggerRunnable implements Runnable {

		@Override
		public void run() {
			selectDebugger(modifiedValuesCache.debuggerId);
		}

	}

	private List<String> debuggersIds;
	private Combo debuggerCombo;
	private Button debuggerTest;
	private Link debuggerGlobalSettings;
	private PHPExeListener phpExeListener;
	private PHPServerListener phpServerListener;
	private ValuesCache originalValuesCache = new ValuesCache();
	private ValuesCache modifiedValuesCache;
	private IDebuggerSettingsSection debuggerSettingsSection;
	private IDebuggerSettingsWorkingCopy debuggerSettingsWC;
	private Map<String, IDebuggerSettingsWorkingCopy> settingsWCBuffer = new HashMap<>();
	private String detectedDebuggerId = null;
	private boolean detectDebugger;
	private Composite debuggerSettingsComposite;
	private Composite mainComposite;
	private boolean updatingDebuggerId = false;

	/**
	 * Creates new debugger composite fragment.
	 * 
	 * @param parent
	 * @param handler
	 * @param isForEditing
	 */
	public DebuggerCompositeFragment(Composite parent, IControlHandler handler, boolean isForEditing) {
		super(parent, handler, isForEditing);
		this.detectDebugger = handler.getKind() == Kind.WIZARD;
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				unregisterListeners();
				removeDisposeListener(this);
			}
		});
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
		if (detectDebugger) {
			detectDebugger();
			detectDebugger = false;
		}
		if (debuggerSettingsSection != null) {
			boolean isOK = debuggerSettingsSection.performOK();
			if (!isOK) {
				return isOK;
			}
		}
		if (debuggerSettingsWC != null) {
			if (debuggerSettingsWC.isDirty()) {
				DebuggerSettingsManager.INSTANCE.save(debuggerSettingsWC);
			}
			DebuggerSettingsManager.INSTANCE.dropWorkingCopy(debuggerSettingsWC);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.CompositeFragment#performCancel()
	 */
	@Override
	public boolean performCancel() {
		if (debuggerSettingsSection != null) {
			debuggerSettingsSection.performCancel();
		}
		if (debuggerSettingsWC != null) {
			DebuggerSettingsManager.INSTANCE.dropWorkingCopy(debuggerSettingsWC);
		}
		return super.performCancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.CompositeFragment#validate()
	 */
	@Override
	public void validate() {
		setComplete(true);
		// Delegate validation to settings composite
		if (debuggerSettingsSection != null) {
			debuggerSettingsSection.validate();
		}
	}

	/**
	 * Override the super setData to handle only Server types.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given object is not a {@link Server}
	 */
	@Override
	public void setData(Object debuggerOwner) throws IllegalArgumentException {
		if (debuggerOwner != null && !(debuggerOwner instanceof IUniqueIdentityElement)) {
			throw new IllegalArgumentException("The given object is not a PHP Server or Executable"); //$NON-NLS-1$
		}
		unregisterListeners();
		super.setData(debuggerOwner);
		registerListeners();
		createDescription(debuggerOwner);
		init();
		validate();
	}

	public IUniqueIdentityElement getDebuggerOwner() {
		return (IUniqueIdentityElement) getData();
	}

	void createSettings(String debuggerId) {
		IUniqueIdentityElement debuggerOwner = getDebuggerOwner();
		if (debuggerOwner == null || debuggerId == null) {
			return;
		}
		IDebuggerSettings settings = DebuggerSettingsManager.INSTANCE.findSettings(debuggerOwner.getUniqueId(),
				debuggerId);
		boolean repaint = controlHandler.getKind() == Kind.WIZARD
				&& !PHPDebuggersRegistry.NONE_DEBUGGER_ID.equals(debuggerId);
		if (debuggerSettingsSection != null) {
			debuggerSettingsComposite.dispose();
			repaint = true;
		}
		// Rebuild settings composite
		debuggerSettingsComposite = new Composite(mainComposite, SWT.NONE);
		debuggerSettingsComposite.setLayout(new GridLayout());
		GridData dscData = new GridData(SWT.FILL, SWT.FILL, true, true);
		debuggerSettingsComposite.setLayoutData(dscData);
		if (PHPDebuggersRegistry.NONE_DEBUGGER_ID.equals(debuggerId)) {
			debuggerSettingsSection = new EmptySettingsSection(debuggerSettingsComposite);
			debuggerGlobalSettings.setVisible(false);
		} else if (settings == null) {
			debuggerSettingsSection = new DebuggerUnsupportedSettingsSection(this, debuggerSettingsComposite);
		} else {
			debuggerGlobalSettings
					.setVisible(PHPDebuggersRegistry.getDebuggerConfiguration(debuggerId) != null ? true : false);
			debuggerSettingsWC = getSettingsWC(debuggerId, settings);
			IDebuggerSettingsProvider provider = DebuggerSettingsProviderRegistry.getProvider(debuggerId);
			IDebuggerSettingsSectionBuilder sectionBuilder = DebuggerSettingsSectionBuildersRegistry
					.getBuilder(provider.getId());
			debuggerSettingsSection = sectionBuilder.build(this, debuggerSettingsComposite, debuggerSettingsWC);
		}
		if (!debuggerSettingsSection.canTest()) {
			debuggerTest.setVisible(false);
		} else {
			debuggerTest.setVisible(true);
		}
		this.getParent().layout(true, true);
		if (repaint) {
			repaint();
		}
	}

	IDebuggerSettingsWorkingCopy getSettingsWC(String debuggerId, IDebuggerSettings settings) {
		IDebuggerSettingsWorkingCopy debuggerSettingsWC = settingsWCBuffer.get(debuggerId);
		if (debuggerSettingsWC == null) {
			debuggerSettingsWC = DebuggerSettingsManager.INSTANCE.fetchWorkingCopy(settings);
			settingsWCBuffer.put(debuggerId, debuggerSettingsWC);
		}
		return debuggerSettingsWC;
	}

	protected void createDescription(Object owner) {
		if (owner instanceof PHPexeItem) {
			setImageDescriptor(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_WIZBAN_DEBUG_PHPEXE));
			setDescription(Messages.DebuggerCompositeFragment_Configure_exe_debugger_settings);
		} else if (owner instanceof Server) {
			setImageDescriptor(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_WIZBAN_DEBUG_SERVER));
			setDescription(Messages.DebuggerCompositeFragment_Configure_server_debugger_settings);
		}
		setDisplayName(Messages.DebuggerCompositeFragment_Debugger);
		setTitle(Messages.DebuggerCompositeFragment_Debugger_settings);
		controlHandler.setTitle(getTitle());
		controlHandler.setImageDescriptor(getImageDescriptor());
		controlHandler.setDescription(getDescription());
	}

	@Override
	protected void createContents(Composite parent) {
		debuggersIds = new LinkedList<>(PHPDebuggersRegistry.getDebuggersIds());
		mainComposite = parent;
		Composite debuggerChoice = new Composite(parent, SWT.NONE);
		GridLayout dcLayout = new GridLayout();
		dcLayout.numColumns = 4;
		debuggerChoice.setLayout(dcLayout);
		debuggerChoice.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		Label debuggerLabel = new Label(debuggerChoice, SWT.NONE);
		debuggerLabel.setText("Debugger:"); //$NON-NLS-1$
		debuggerLabel.setLayoutData(new GridData());
		debuggerCombo = new Combo(debuggerChoice, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData dcData = new GridData(SWT.LEFT, SWT.FILL, false, false);
		debuggerCombo.setLayoutData(dcData);
		debuggerCombo.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (getDebuggerOwner() != null) {
					modifiedValuesCache.debuggerId = debuggersIds.get(debuggerCombo.getSelectionIndex());
					updateItem();
					// create settings panel
					createSettings(modifiedValuesCache.debuggerId);
					// Validate debugger data
					validate();
				}
			}
		});
		debuggerTest = SWTFactory.createPushButton(debuggerChoice, Messages.DebuggerCompositeFragment_Test_button,
				null);
		debuggerTest.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (debuggerSettingsSection != null) {
					debuggerSettingsSection.performTest();
				}
			}
		});
		debuggerGlobalSettings = new Link(debuggerChoice, SWT.NONE);
		GridData dgsData = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		debuggerGlobalSettings.setLayoutData(dgsData);
		debuggerGlobalSettings.setText(Messages.DebuggerCompositeFragment_Global_settings_link);
		debuggerGlobalSettings.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				String selectedDebuggerId = debuggersIds.get(debuggerCombo.getSelectionIndex());
				AbstractDebuggerConfiguration globalConfiguration = PHPDebuggersRegistry
						.getDebuggerConfiguration(selectedDebuggerId);
				globalConfiguration
						.openConfigurationDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
		for (int i = 0; i < debuggersIds.size(); ++i) {
			String id = debuggersIds.get(i);
			String debuggerName = PHPDebuggersRegistry.getDebuggerName(id);
			debuggerCombo.add(debuggerName, i);
		}
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		Dialog.applyDialogFont(this);
		// Initialize & validate
		init();
		validate();
		// Set focus on debuggers combo
		debuggerCombo.forceFocus();
	}

	protected void init() {
		IUniqueIdentityElement debuggerOwner = getDebuggerOwner();
		if (debuggerOwner == null) {
			return;
		}
		if (debuggerOwner instanceof Server) {
			originalValuesCache.debuggerId = ((Server) debuggerOwner).getDebuggerId();
		} else if (debuggerOwner instanceof PHPexeItem) {
			originalValuesCache.debuggerId = ((PHPexeItem) debuggerOwner).getDebuggerID();
		}
		// Clone the cache lazily
		if (modifiedValuesCache == null) {
			modifiedValuesCache = new ValuesCache(originalValuesCache);
		}
		setDebugger();
	}

	private void setDebugger() {
		if (detectDebugger) {
			detectDebugger();
			detectDebugger = false;
		} else {
			// Set combo to appropriate debugger ID
			selectDebugger(modifiedValuesCache.debuggerId);
		}
	}

	private void detectDebugger() {
		IUniqueIdentityElement data = (IUniqueIdentityElement) getData();
		// Check if debugger module is installed on top of PHP executable
		if (data instanceof PHPexeItem) {
			PHPexeItem exeItem = (PHPexeItem) data;
			modifiedValuesCache.debuggerId = detectedDebuggerId = fetchDebugger(exeItem);
		}
		if (detectedDebuggerId == null && (modifiedValuesCache.debuggerId == null
				|| modifiedValuesCache.debuggerId.equals(PHPDebuggersRegistry.NONE_DEBUGGER_ID))) {
			detectedDebuggerId = PHPDebuggersRegistry.NONE_DEBUGGER_ID;
		} else if (modifiedValuesCache.debuggerId != null) {
			detectedDebuggerId = modifiedValuesCache.debuggerId;
		}
		// Set combo to appropriate debugger ID
		selectDebugger(detectedDebuggerId);
	}

	private void selectDebugger(String debuggerId) {
		final String name = PHPDebuggersRegistry.getDebuggerName(debuggerId);
		String[] values = debuggerCombo.getItems();
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(name)) {
				debuggerCombo.select(i);
				break;
			}
		}
	}

	private String fetchDebugger(PHPexeItem exeItem) {
		List<PHPModuleInfo> modules = PHPExeUtil.getModules(exeItem);
		AbstractDebuggerConfiguration[] debuggers = PHPDebuggersRegistry.getDebuggersConfigurations();
		for (AbstractDebuggerConfiguration debugger : debuggers) {
			for (PHPModuleInfo module : modules) {
				if (module.getName().equalsIgnoreCase(debugger.getModuleId())) {
					return debugger.getDebuggerId();
				}
			}
		}
		return null;
	}

	private void updateItem() {
		updatingDebuggerId = true;
		IUniqueIdentityElement debuggerOwner = getDebuggerOwner();
		if (debuggerOwner instanceof Server) {
			((Server) debuggerOwner).setDebuggerId(modifiedValuesCache.debuggerId);
		} else if (debuggerOwner instanceof PHPexeItem) {
			((PHPexeItem) debuggerOwner).setDebuggerID(modifiedValuesCache.debuggerId);
		}
		updatingDebuggerId = false;
	}

	private void repaint() {
		Shell shell = this.getShell();
		Point previousSize = new Point(shell.getSize().x, shell.getSize().y);
		Rectangle previousClientArea = shell.getClientArea();
		shell.layout(true, true);
		final Point computedSize = shell.computeSize(previousClientArea.width, SWT.DEFAULT, false);
		boolean resize = computedSize.y > previousSize.y;
		if (resize) {
			shell.setSize(shell.computeSize(previousClientArea.width,
					computedSize.y - IDialogConstants.BUTTON_BAR_HEIGHT, true));
		} else {
			// Workaround for incorrect redrawing in GTK 3
			shell.setRedraw(false);
			shell.setSize(shell.computeSize(previousClientArea.width + 1, previousClientArea.height, true));
			shell.setRedraw(true);
			shell.setSize(shell.computeSize(previousClientArea.width, previousClientArea.height, true));
		}
	}

	private void registerListeners() {
		Object data = getData();

		if (data instanceof PHPexeItem) {
			if (phpExeListener == null) {
				phpExeListener = new PHPExeListener();
			}
			((PHPexeItem) data).addPHPexeListener(phpExeListener);
		}
		if (data instanceof Server) {
			if (phpServerListener == null) {
				phpServerListener = new PHPServerListener();
			}
			((Server) data).addPropertyChangeListener(phpServerListener);
		}
	}

	private void unregisterListeners() {
		Object data = getData();
		if (data == null) {
			return;
		}

		if (phpExeListener != null) {
			((PHPexeItem) data).removePHPexeListener(phpExeListener);
		}
		if (phpServerListener != null) {
			((Server) data).removePropertyChangeListener(phpServerListener);
		}
	}

}
