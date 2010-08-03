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
package org.eclipse.php.internal.ui.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.folding.PHPFoldingStructureProviderDescriptor;
import org.eclipse.php.internal.ui.folding.PHPFoldingStructureProviderRegistry;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.ui.folding.IPHPFoldingPreferenceBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;
import org.eclipse.wst.sse.ui.internal.projection.AbstractStructuredFoldingStrategy;

/**
 * Configures PHP Editor folding preferences.
 * 
 * @since 3.0
 */
class FoldingConfigurationBlock implements IPreferenceConfigurationBlock {

	private static class ErrorPreferences implements IPHPFoldingPreferenceBlock {
		private String fMessage;

		protected ErrorPreferences(String message) {
			fMessage = message;
		}

		/*
		 * @see
		 * org.eclipse.jdt.internal.ui.text.folding.IJavaFoldingPreferences#
		 * createControl(org.eclipse.swt.widgets.Group)
		 */
		public Control createControl(Composite composite) {
			Composite inner = new Composite(composite, SWT.NONE);
			inner.setLayout(new FillLayout(SWT.VERTICAL));

			Label label = new Label(inner, SWT.CENTER);
			label.setText(fMessage);

			return inner;
		}

		public void initialize() {
		}

		public void performOk() {
		}

		public void performDefaults() {
		}

		public void dispose() {
		}

	}

	/** The overlay preference store. */
	private final OverlayPreferenceStore fStore;

	/* The controls */
	private Combo fProviderCombo;
	private Button fFoldingCheckbox;
	private ComboViewer fProviderViewer;
	private Composite fGroup;
	private StackLayout fStackLayout;

	/* the model */
	private final Map fProviderDescriptors;
	private final Map fProviderPreferences;
	private final Map fProviderControls;

	public FoldingConfigurationBlock(OverlayPreferenceStore store) {
		Assert.isNotNull(store);
		fStore = store;
		fStore.addKeys(createOverlayStoreKeys());
		fProviderDescriptors = createListModel();
		fProviderPreferences = new HashMap();
		fProviderControls = new HashMap();
	}

	private Map createListModel() {
		PHPFoldingStructureProviderRegistry reg = PHPUiPlugin.getDefault()
				.getFoldingStructureProviderRegistry();
		reg.reloadExtensions();
		PHPFoldingStructureProviderDescriptor[] descs = reg
				.getFoldingProviderDescriptors();
		Map map = new HashMap();
		for (PHPFoldingStructureProviderDescriptor element : descs) {
			map.put(element.getId(), element);
		}
		return map;
	}

	private OverlayPreferenceStore.OverlayKey[] createOverlayStoreKeys() {

		ArrayList overlayKeys = new ArrayList();

		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.BOOLEAN,
				PreferenceConstants.EDITOR_FOLDING_ENABLED));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.STRING,
				PreferenceConstants.EDITOR_FOLDING_PROVIDER));

		OverlayPreferenceStore.OverlayKey[] keys = new OverlayPreferenceStore.OverlayKey[overlayKeys
				.size()];
		overlayKeys.toArray(keys);
		return keys;
	}

	/**
	 * Creates page for folding preferences.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the control for the preference page
	 */
	public Control createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NULL);
		// assume parent page uses griddata
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER
				| GridData.VERTICAL_ALIGN_FILL);
		composite.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		PixelConverter pc = new PixelConverter(composite);
		layout.verticalSpacing = pc.convertHeightInCharsToPixels(1) / 2;
		composite.setLayout(layout);

		/* check box for new editors */
		fFoldingCheckbox = new Button(composite, SWT.CHECK);
		fFoldingCheckbox
				.setText(PHPUIMessages.FoldingConfigurationBlock_enable);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING
				| GridData.VERTICAL_ALIGN_BEGINNING);
		fFoldingCheckbox.setLayoutData(gd);
		fFoldingCheckbox.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = fFoldingCheckbox.getSelection();
				fStore.setValue(PreferenceConstants.EDITOR_FOLDING_ENABLED,
						enabled);
				updateCheckboxDependencies();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Label label = new Label(composite, SWT.CENTER);
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		label.setLayoutData(gd);

		if (fProviderDescriptors.size() > 1) {
			/* list */
			Composite comboComp = new Composite(composite, SWT.NONE);
			gd = new GridData(GridData.FILL_HORIZONTAL
					| GridData.VERTICAL_ALIGN_BEGINNING);
			GridLayout gridLayout = new GridLayout(2, false);
			gridLayout.marginWidth = 0;
			comboComp.setLayout(gridLayout);

			Label comboLabel = new Label(comboComp, SWT.CENTER);
			gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING
					| GridData.VERTICAL_ALIGN_CENTER);
			comboLabel.setLayoutData(gd);
			comboLabel
					.setText(PHPUIMessages.FoldingConfigurationBlock_combo_caption);

			label = new Label(composite, SWT.CENTER);
			gd = new GridData(GridData.FILL_HORIZONTAL
					| GridData.VERTICAL_ALIGN_BEGINNING);
			label.setLayoutData(gd);

			fProviderCombo = new Combo(comboComp, SWT.READ_ONLY | SWT.DROP_DOWN);
			gd = new GridData(GridData.HORIZONTAL_ALIGN_END
					| GridData.VERTICAL_ALIGN_CENTER);
			fProviderCombo.setLayoutData(gd);

			fProviderViewer = createProviderViewer();
		}

		Composite groupComp = new Composite(composite, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		groupComp.setLayoutData(gd);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 0;
		groupComp.setLayout(gridLayout);

		/* contributed provider preferences. */
		fGroup = new Composite(groupComp, SWT.NONE);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING
				| GridData.VERTICAL_ALIGN_BEGINNING);
		fGroup.setLayoutData(gd);
		fStackLayout = new StackLayout();
		fGroup.setLayout(fStackLayout);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite,
				IPHPHelpContextIds.FOLDING_PREFERENCES);
		return composite;
	}

	private ComboViewer createProviderViewer() {
		/* list viewer */
		ComboViewer viewer = new ComboViewer(fProviderCombo);
		viewer.setContentProvider(new IStructuredContentProvider() {

			/*
			 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
			 */
			public void dispose() {
			}

			/*
			 * @see
			 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
			 * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
			 */
			public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			}

			/*
			 * @see
			 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements
			 * (java.lang.Object)
			 */
			public Object[] getElements(Object inputElement) {
				return fProviderDescriptors.values().toArray();
			}
		});
		viewer.setLabelProvider(new LabelProvider() {
			/*
			 * @see
			 * org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object
			 * )
			 */
			public Image getImage(Object element) {
				return null;
			}

			/*
			 * @see
			 * org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
			 */
			public String getText(Object element) {
				return ((PHPFoldingStructureProviderDescriptor) element)
						.getName();
			}
		});
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event
						.getSelection();
				if (!sel.isEmpty()) {
					fStore.setValue(
							PreferenceConstants.EDITOR_FOLDING_PROVIDER,
							((PHPFoldingStructureProviderDescriptor) sel
									.getFirstElement()).getId());
					updateListDependencies();
				}
			}
		});
		viewer.setInput(fProviderDescriptors);
		viewer.refresh();

		return viewer;
	}

	private void updateCheckboxDependencies() {
	}

	void updateListDependencies() {
		String id = fStore
				.getString(PreferenceConstants.EDITOR_FOLDING_PROVIDER);
		PHPFoldingStructureProviderDescriptor desc = (PHPFoldingStructureProviderDescriptor) fProviderDescriptors
				.get(id);
		IPHPFoldingPreferenceBlock prefs;

		if (desc == null) {
			// safety in case there is no such descriptor
			String message = PHPUIMessages.FoldingConfigurationBlock_error_not_exist;
			PHPUiPlugin.log(new Status(IStatus.WARNING, PHPUiPlugin
					.getPluginId(), IStatus.OK, message, null));
			prefs = new ErrorPreferences(message);
		} else {
			prefs = (IPHPFoldingPreferenceBlock) fProviderPreferences.get(id);
			if (prefs == null) {
				try {
					prefs = desc.createPreferences();
					fProviderPreferences.put(id, prefs);
				} catch (CoreException e) {
					PHPUiPlugin.log(e);
					prefs = new ErrorPreferences(e.getLocalizedMessage());
				}
			}
		}

		Control control = (Control) fProviderControls.get(id);
		if (control == null) {
			control = prefs.createControl(fGroup);
			if (control == null) {
				String message = PHPUIMessages.FoldingConfigurationBlock_info_no_preferences;
				control = new ErrorPreferences(message).createControl(fGroup);
			} else {
				fProviderControls.put(id, control);
			}
		}
		Dialog.applyDialogFont(control);
		fStackLayout.topControl = control;
		control.pack();
		fGroup.layout();
		fGroup.getParent().layout();

		prefs.initialize();
	}

	public void initialize() {
		restoreFromPreferences();
	}

	public void performOk() {
		fStore.propagate();
		for (Iterator it = fProviderPreferences.values().iterator(); it
				.hasNext();) {
			IPHPFoldingPreferenceBlock prefs = (IPHPFoldingPreferenceBlock) it
					.next();
			prefs.performOk();
		}
		// TODO - Might need a fix after the WST will support code folding
		// officially.
		boolean foldingEnabled = fStore
				.getBoolean(PreferenceConstants.EDITOR_FOLDING_ENABLED);
		SSEUIPlugin.getDefault().getPreferenceStore().setValue(
				AbstractStructuredFoldingStrategy.FOLDING_ENABLED,
				foldingEnabled);
	}

	public void performDefaults() {
		restoreFromPreferences();
		for (Iterator it = fProviderPreferences.values().iterator(); it
				.hasNext();) {
			IPHPFoldingPreferenceBlock prefs = (IPHPFoldingPreferenceBlock) it
					.next();
			prefs.performDefaults();
		}
	}

	public void dispose() {
		for (Iterator it = fProviderPreferences.values().iterator(); it
				.hasNext();) {
			IPHPFoldingPreferenceBlock prefs = (IPHPFoldingPreferenceBlock) it
					.next();
			prefs.dispose();
		}
	}

	private void restoreFromPreferences() {
		boolean enabled = fStore
				.getBoolean(PreferenceConstants.EDITOR_FOLDING_ENABLED);
		fFoldingCheckbox.setSelection(enabled);
		updateCheckboxDependencies();

		String id = fStore
				.getString(PreferenceConstants.EDITOR_FOLDING_PROVIDER);
		Object provider = fProviderDescriptors.get(id);
		if (provider != null) {
			if (fProviderViewer == null)
				updateListDependencies();
			else
				fProviderViewer.setSelection(new StructuredSelection(provider),
						true);
		}
	}
}
