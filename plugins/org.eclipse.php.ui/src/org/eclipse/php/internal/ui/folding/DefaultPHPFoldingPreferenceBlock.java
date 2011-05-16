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
package org.eclipse.php.internal.ui.folding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.folding.IPHPFoldingPreferenceBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore.OverlayKey;

/**
 * PHP default folding preferences.
 * 
 * @since 3.0
 */
public class DefaultPHPFoldingPreferenceBlock implements
		IPHPFoldingPreferenceBlock {

	private IPreferenceStore fStore;
	private OverlayPreferenceStore fOverlayStore;
	private OverlayKey[] fKeys;
	private Map fCheckBoxes = new HashMap();
	private SelectionListener fCheckBoxListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			Button button = (Button) e.widget;
			fOverlayStore.setValue((String) fCheckBoxes.get(button),
					button.getSelection());
		}
	};

	public DefaultPHPFoldingPreferenceBlock() {
		fStore = PreferenceConstants.getPreferenceStore();
		fKeys = createKeys();
		fOverlayStore = new OverlayPreferenceStore(fStore, fKeys);
	}

	private OverlayKey[] createKeys() {
		ArrayList overlayKeys = new ArrayList();

		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.BOOLEAN,
				PreferenceConstants.EDITOR_FOLDING_PHPDOC));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.BOOLEAN,
				PreferenceConstants.EDITOR_FOLDING_CLASSES));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.BOOLEAN,
				PreferenceConstants.EDITOR_FOLDING_FUNCTIONS));
		// overlayKeys.add(new
		// OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN,
		// PreferenceConstants.EDITOR_FOLDING_INCLUDES));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.BOOLEAN,
				PreferenceConstants.EDITOR_FOLDING_HEADER_COMMENTS));

		return (OverlayKey[]) overlayKeys.toArray(new OverlayKey[overlayKeys
				.size()]);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.ui.folding.IPHPFoldingPreferenceBlock#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public Control createControl(Composite composite) {
		fOverlayStore.load();
		fOverlayStore.start();

		Composite inner = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.verticalSpacing = 3;
		layout.marginWidth = 0;
		inner.setLayout(layout);

		Label label = new Label(inner, SWT.LEFT);
		label.setText(PHPUIMessages.DefaultPHPFoldingPreferenceBlock_title);

		addCheckBox(inner,
				PHPUIMessages.DefaultPHPFoldingPreferenceBlock_classes,
				PreferenceConstants.EDITOR_FOLDING_CLASSES, 0);
		// addCheckBox(inner,
		// PHPUIMessages.getString("DefaultPHPFoldingPreferenceBlock_includes"),
		// PreferenceConstants.EDITOR_FOLDING_INCLUDES, 0);
		addCheckBox(inner,
				PHPUIMessages.DefaultPHPFoldingPreferenceBlock_functions,
				PreferenceConstants.EDITOR_FOLDING_FUNCTIONS, 0);
		addCheckBox(inner,
				PHPUIMessages.DefaultPHPFoldingPreferenceBlock_PHPdoc,
				PreferenceConstants.EDITOR_FOLDING_PHPDOC, 0);
		addCheckBox(inner,
				PHPUIMessages.DefaultPHPFoldingPreferenceBlock_header_comments,
				PreferenceConstants.EDITOR_FOLDING_HEADER_COMMENTS, 0);

		return inner;
	}

	private Button addCheckBox(Composite parent, String label, String key,
			int indentation) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(label);

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indentation;
		gd.horizontalSpan = 1;
		gd.grabExcessVerticalSpace = false;
		checkBox.setLayoutData(gd);
		checkBox.addSelectionListener(fCheckBoxListener);

		fCheckBoxes.put(checkBox, key);

		return checkBox;
	}

	private void initializeFields() {
		Iterator it = fCheckBoxes.keySet().iterator();
		while (it.hasNext()) {
			Button b = (Button) it.next();
			String key = (String) fCheckBoxes.get(b);
			b.setSelection(fOverlayStore.getBoolean(key));
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.ui.folding.IPHPFoldingPreferenceBlock#performOk()
	 */
	public void performOk() {
		fOverlayStore.propagate();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.ui.folding.IPHPFoldingPreferenceBlock#initialize()
	 */
	public void initialize() {
		initializeFields();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.ui.folding.IPHPFoldingPreferenceBlock#performDefaults()
	 */
	public void performDefaults() {
		fOverlayStore.loadDefaults();
		initializeFields();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.ui.folding.IPHPFoldingPreferenceBlock#dispose()
	 */
	public void dispose() {
		fOverlayStore.stop();
	}
}
