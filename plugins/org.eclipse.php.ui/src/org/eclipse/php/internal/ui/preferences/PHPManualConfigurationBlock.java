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

import java.util.*;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPManualSiteDescriptor;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.util.StatusUtil;
import org.eclipse.php.internal.ui.wizards.fields.DialogField;
import org.eclipse.php.internal.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.internal.ui.wizards.fields.IListAdapter;
import org.eclipse.php.internal.ui.wizards.fields.ListDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

public class PHPManualConfigurationBlock implements
		IPreferenceConfigurationBlock {

	private class PHPManualLabelProvider extends LabelProvider implements
			ITableLabelProvider, IFontProvider {
		public Font getFont(Object element) {
			if (isDefault((PHPManualConfig) element)) {
				return JFaceResources.getFontRegistry().getBold(
						JFaceResources.DIALOG_FONT);
			}
			return null;
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 0) {
				return ((PHPManualConfig) element).getLabel();
			} else if (columnIndex == 1) {
				return ((PHPManualConfig) element).getUrl();
			} else if (columnIndex == 2) {
				return ((PHPManualConfig) element).getExtension();
			}
			return null;
		}
	}

	private class PHPManualSorter extends ViewerSorter {

		public int compare(Viewer viewer, Object e1, Object e2) {
			return collator.compare(((PHPManualConfig) e1).getLabel(),
					((PHPManualConfig) e2).getLabel());
		}
	}

	private class PHPManualAdapter implements IListAdapter<PHPManualConfig>,
			IDialogFieldListener {

		private boolean canEdit(List<PHPManualConfig> selectedElements) {
			return selectedElements.size() == 1
					&& !selectedElements.get(0).isContributed();
		}

		private boolean canRemove(List<PHPManualConfig> selectedElements) {
			for (PHPManualConfig element : selectedElements) {
				if (element.isContributed()) {
					return false;
				}
			}
			return true;
		}

		private boolean canSetToDefault(List<PHPManualConfig> selectedElements) {
			return selectedElements.size() == 1
					&& !isDefault(selectedElements.get(0));
		}

		public void dialogFieldChanged(DialogField field) {
		}

		public void customButtonPressed(ListDialogField<PHPManualConfig> field,
				int index) {
			sideButtonPressed(index);
		}

		public void doubleClicked(ListDialogField<PHPManualConfig> field) {
			if (canEdit(field.getSelectedElements())) {
				sideButtonPressed(IDX_EDIT);
			}
		}

		public void selectionChanged(ListDialogField<PHPManualConfig> field) {
			List<PHPManualConfig> selectedElements = field
					.getSelectedElements();
			field.enableButton(IDX_EDIT, canEdit(selectedElements));
			field.enableButton(IDX_DEFAULT, canSetToDefault(selectedElements));
			field.enableButton(IDX_REMOVE, canRemove(selectedElements));
		}
	}

	private static final int IDX_ADD = 0;
	private static final int IDX_EDIT = 1;
	private static final int IDX_REMOVE = 2;
	private static final int IDX_DEFAULT = 4;

	public static final String PREFERENCES_DELIMITER = new String(
			new char[] { 5 });

	private IStatus fPHPManualStatus;
	private ListDialogField<PHPManualConfig> fPHPManualButtonsList;
	private PreferencePage fMainPreferencePage;
	private OverlayPreferenceStore fStore;

	private Map<Button, String> fCheckBoxes = new HashMap<Button, String>();
	private SelectionListener fCheckBoxListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
			Button button = (Button) e.widget;
			fStore.setValue(fCheckBoxes.get(button), button.getSelection());
		}

		public void widgetSelected(SelectionEvent e) {
			Button button = (Button) e.widget;
			fStore.setValue(fCheckBoxes.get(button), button.getSelection());
		}
	};

	protected boolean isDefault(PHPManualConfig element) {
		return fPHPManualButtonsList.getIndexOfElement(element) == 0;
	}

	protected void setToDefault(PHPManualConfig element) {
		List<PHPManualConfig> elements = fPHPManualButtonsList.getElements();
		elements.remove(element);
		elements.add(0, element);
		fPHPManualButtonsList.setElements(elements);
		fPHPManualButtonsList.enableButton(IDX_DEFAULT, false);
	}

	public PHPManualConfigurationBlock(PreferencePage mainPreferencePage,
			OverlayPreferenceStore store) {
		Assert.isNotNull(mainPreferencePage);
		Assert.isNotNull(store);
		fMainPreferencePage = mainPreferencePage;
		fStore = store;
		fStore.addKeys(createOverlayStoreKeys());
	}

	private OverlayPreferenceStore.OverlayKey[] createOverlayStoreKeys() {
		ArrayList overlayKeys = new ArrayList();
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.STRING,
				PreferenceConstants.PHP_MANUAL_SITE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.STRING,
				PreferenceConstants.PHP_MANUAL_SITES));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.STRING,
				PreferenceConstants.PHP_MANUAL_OPEN_IN_NEW_BROWSER));

		OverlayPreferenceStore.OverlayKey[] keys = new OverlayPreferenceStore.OverlayKey[overlayKeys
				.size()];
		overlayKeys.toArray(keys);
		return keys;
	}

	public Control createControl(Composite parent) {

		PHPManualAdapter adapter = new PHPManualAdapter();
		String buttons[] = new String[] {
				PHPUIMessages.PHPManualConfigurationBlock_new,
				PHPUIMessages.PHPManualConfigurationBlock_edit,
				PHPUIMessages.PHPManualConfigurationBlock_remove, null,
				PHPUIMessages.PHPManualConfigurationBlock_default };
		fPHPManualButtonsList = new ListDialogField<PHPManualConfig>(adapter,
				buttons, new PHPManualLabelProvider());
		fPHPManualButtonsList.setDialogFieldListener(adapter);
		fPHPManualButtonsList.setRemoveButtonIndex(IDX_REMOVE);

		String[] columnsHeaders = new String[] {
				PHPUIMessages.PHPManualConfigurationBlock_siteName,
				PHPUIMessages.PHPManualConfigurationBlock_url,
				PHPUIMessages.PHPManualConfigurationBlock_fileExtension };
		fPHPManualButtonsList
				.setTableColumns(new ListDialogField.ColumnsDescription(
						columnsHeaders, true));
		fPHPManualButtonsList.setViewerSorter(new PHPManualSorter());

		if (fPHPManualButtonsList.getSize() > 0) {
			fPHPManualButtonsList.selectFirstElement();
		} else {
			fPHPManualButtonsList.enableButton(IDX_EDIT, false);
			fPHPManualButtonsList.enableButton(IDX_DEFAULT, false);
		}

		fPHPManualStatus = new StatusInfo();

		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;

		PixelConverter conv = new PixelConverter(parent);

		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(
				parent, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite composite = new Composite(scrolledCompositeImpl, SWT.NONE);
		composite.setLayout(layout);
		scrolledCompositeImpl.setContent(composite);

		scrolledCompositeImpl.setLayout(layout);
		scrolledCompositeImpl.setFont(parent.getFont());

		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = conv.convertWidthInCharsToPixels(50);
		Control listControl = fPHPManualButtonsList.getListControl(composite);
		listControl.setLayoutData(data);

		Control buttonsControl = fPHPManualButtonsList.getButtonBox(composite);
		buttonsControl.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL
						| GridData.VERTICAL_ALIGN_BEGINNING));

		addCheckBox(composite,
				PHPUIMessages.PHPManualConfigurationBlock_openInNewBrowser,
				PreferenceConstants.PHP_MANUAL_OPEN_IN_NEW_BROWSER, 0); 
		addFiller(composite);

		Point size = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledCompositeImpl.setMinSize(size.x, size.y);

		return scrolledCompositeImpl;
	}

	private Button addCheckBox(Composite parent, String label, String key,
			int indentation) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(label);

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indentation;
		gd.horizontalSpan = 2;
		checkBox.setLayoutData(gd);
		checkBox.addSelectionListener(fCheckBoxListener);

		fCheckBoxes.put(checkBox, key);
		checkBox.setSelection(fStore.getBoolean(key));

		return checkBox;
	}

	private void addFiller(Composite composite) {
		PixelConverter pixelConverter = new PixelConverter(composite);
		Label filler = new Label(composite, SWT.LEFT);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 2;
		gd.heightHint = pixelConverter.convertHeightInCharsToPixels(1) / 2;
		filler.setLayoutData(gd);
	}

	protected void sideButtonPressed(int index) {
		if (index == IDX_ADD) {
			NewPHPManualSiteDialog dialog = new NewPHPManualSiteDialog(
					fMainPreferencePage.getShell(), null, fPHPManualButtonsList
							.getElements());
			if (dialog.open() == Window.OK) {
				fPHPManualButtonsList.addElement(dialog.getResult());
			}
		} else if (index == IDX_EDIT) {
			PHPManualConfig edited = fPHPManualButtonsList
					.getSelectedElements().get(0);
			NewPHPManualSiteDialog dialog = new NewPHPManualSiteDialog(
					fMainPreferencePage.getShell(), edited,
					fPHPManualButtonsList.getElements());
			// in case there was no change, dialog.getResult() will be null and
			// nothing should happen (sending null will throw an exception)
			if (dialog.open() == Window.OK && dialog.getResult() != null) {
				fPHPManualButtonsList
						.replaceElement(edited, dialog.getResult());
			}
		} else if (index == IDX_DEFAULT) {
			setToDefault(fPHPManualButtonsList.getSelectedElements().get(0));
		}
	}

	protected void updateStatus() {
		fMainPreferencePage.setValid(fPHPManualStatus.isOK());
		StatusUtil.applyToStatusLine(fMainPreferencePage, fPHPManualStatus);
	}

	public void initialize() {
		List<PHPManualConfig> configs = new ArrayList<PHPManualConfig>();

		initFromExtensions(configs);
		initFromPreferences(fStore, configs);

		fPHPManualButtonsList.setElements(configs);

		PHPManualConfig defaultConfig = getActiveManualSite(fStore, configs);
		if (defaultConfig != null) {
			setToDefault(defaultConfig);
		}

		for (Map.Entry<Button, String> entry : fCheckBoxes.entrySet()) {
			entry.getKey().setSelection(fStore.getBoolean(entry.getValue()));
		}
	}

	/**
	 * Initializes array of PHPManualConfigs from known phpManualSite extensions
	 * 
	 * @param configs
	 */
	public static void initFromExtensions(List<PHPManualConfig> configs) {
		PHPManualSiteDescriptor[] descs = PHPUiPlugin.getDefault()
				.getPHPManualSiteDescriptors();
		for (int i = 0; i < descs.length; ++i) {
			configs.add(new PHPManualConfig(descs[i].getLabel(), descs[i]
					.getURL(), descs[i].getExtension(), true));
		}
	}

	/**
	 * Initializes array of PHPManualConfigs from preferences
	 * 
	 * @param configs
	 */
	public static void initFromPreferences(IPreferenceStore store,
			List<PHPManualConfig> configs) {
		String storedConfigsString = store
				.getString(PreferenceConstants.PHP_MANUAL_SITES);
		if (storedConfigsString != null && !"".equals(storedConfigsString)) { //$NON-NLS-1$
			StringTokenizer sitesTokenizer = new StringTokenizer(
					storedConfigsString, PREFERENCES_DELIMITER);
			PHPManualConfig config;
			do {
				config = PHPManualConfigSerializer
						.fromStringTokenizer(sitesTokenizer);
				if (config == null)
					break;
				boolean alreadyExists = false;
				for (int i = 0; i < configs.size(); ++i) {
					PHPManualConfig existing = configs.get(i);
					if (existing.equals(config)) {
						alreadyExists = true;
					}
				}
				if (!alreadyExists) {
					configs.add(config);
				}
			} while (true);
		}
	}

	/**
	 * Returns active PHP manual site config
	 * 
	 * @return active PHP manual site config
	 */
	public static PHPManualConfig getActiveManualSite(IPreferenceStore store,
			List<PHPManualConfig> configs) {
		String storedConfigString = store
				.getString(PreferenceConstants.PHP_MANUAL_SITE);
		if (storedConfigString != null && !"".equals(storedConfigString)) { //$NON-NLS-1$
			PHPManualConfig config = PHPManualConfigSerializer
					.fromString(storedConfigString);
			for (PHPManualConfig other : configs) {
				if (config.equals(other)) {
					return other;
				}
			}
			return config;
		}
		return null;
	}

	public void dispose() {
		// nothing to dispose
	}

	public void performDefaults() {
		initialize();
		updateStatus();
	}

	public void performOk() {
		StringBuffer sitesBuffer = new StringBuffer();
		List<PHPManualConfig> elements = fPHPManualButtonsList.getElements();
		if (elements != null && elements.size() > 0) {
			for (PHPManualConfig config : elements) {
				if (!config.isContributed()) {
					if (sitesBuffer.length() != 0) {
						sitesBuffer.append(PREFERENCES_DELIMITER);
					}
					sitesBuffer.append(PHPManualConfigSerializer
							.toString(config));
				}
			}

			fStore.setValue(PreferenceConstants.PHP_MANUAL_SITES, sitesBuffer
					.toString());
			fStore.setValue(PreferenceConstants.PHP_MANUAL_SITE,
					PHPManualConfigSerializer.toString(elements.get(0)));
		}
	}
}
