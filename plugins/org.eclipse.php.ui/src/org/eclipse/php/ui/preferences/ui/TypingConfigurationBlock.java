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

package org.eclipse.php.ui.preferences.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.text.Assert;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.ui.util.StatusInfo;
import org.eclipse.php.ui.util.StatusUtil;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.util.ScrolledPageContent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

/**
 * @author guy.g
 *
 */
public class TypingConfigurationBlock implements IPreferenceConfigurationBlock {

	private OverlayPreferenceStore fStore;

	private PreferencePage fMainPreferencePage;

	public TypingConfigurationBlock(PreferencePage mainPreferencePage, OverlayPreferenceStore store) {
		Assert.isNotNull(mainPreferencePage);
		Assert.isNotNull(store);
		fMainPreferencePage = mainPreferencePage;
		fStore = store;
		fStore.addKeys(createOverlayStoreKeys());
		fStore = store;
	}

	public void initialize() {
		initializeFields();
	}

	private void initializeFields() {

		Iterator iter = fCheckBoxes.keySet().iterator();
		while (iter.hasNext()) {
			Button b = (Button) iter.next();
			String key = (String) fCheckBoxes.get(b);
			b.setSelection(fStore.getBoolean(key));
		}

		iter = fTextFields.keySet().iterator();
		while (iter.hasNext()) {
			Text t = (Text) iter.next();
			String key = (String) fTextFields.get(t);
			t.setText(fStore.getString(key));
		}

		// Update slaves
		iter = fMasterSlaveListeners.iterator();
		while (iter.hasNext()) {
			SelectionListener listener = (SelectionListener) iter.next();
			listener.widgetSelected(null);
		}

		updateStatus(new StatusInfo());
	}

	protected void updateStatus(IStatus status) {
		if (fMainPreferencePage == null)
			return;
		fMainPreferencePage.setValid(status.isOK());
		StatusUtil.applyToStatusLine(fMainPreferencePage, status);
	}

	public void performOk() {
	}

	public void performDefaults() {
		initializeFields();
	}

	public void dispose() {
	}

	private OverlayPreferenceStore.OverlayKey[] createOverlayStoreKeys() {

		ArrayList overlayKeys = new ArrayList();

		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, PreferenceConstants.EDITOR_CLOSE_STRINGS));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, PreferenceConstants.EDITOR_CLOSE_BRACES));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, PreferenceConstants.EDITOR_CLOSE_BRACKETS));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, PreferenceConstants.EDITOR_CLOSE_PHPDOCS_AND_COMMENTS));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, PreferenceConstants.EDITOR_ADD_PHPDOC_TAGS));

		OverlayPreferenceStore.OverlayKey[] keys = new OverlayPreferenceStore.OverlayKey[overlayKeys.size()];
		overlayKeys.toArray(keys);
		return keys;
	}

	public Control createControl(Composite parent) {
		ScrolledPageContent scrolled = new ScrolledPageContent(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolled.setExpandHorizontal(true);
		scrolled.setExpandVertical(true);

		Composite control = new Composite(scrolled, SWT.NONE);
		GridLayout layout = new GridLayout();
		control.setLayout(layout);

		Composite composite;

		composite = createSubsection(control, PHPUIMessages.typingPage_autoClose_title);
		addAutoclosingSection(composite);

		scrolled.setContent(control);
		final Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolled.setMinSize(size.x, size.y);
		return scrolled;

	}

	private Composite createSubsection(Composite parent, String label) {

		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(label);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		group.setLayoutData(data);
		return group;
	}

	private void addAutoclosingSection(Composite composite) {

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);

		String label;
		Button master, slave;

		label = PHPUIMessages.typingPage_autoClose_string;
		addCheckBox(composite, label, PreferenceConstants.EDITOR_CLOSE_STRINGS, 0);

		label = PHPUIMessages.typingPage_autoClose_brackets;
		addCheckBox(composite, label, PreferenceConstants.EDITOR_CLOSE_BRACKETS, 0);

		label = PHPUIMessages.typingPage_autoClose_braces;
		addCheckBox(composite, label, PreferenceConstants.EDITOR_CLOSE_BRACES, 0);

		label = PHPUIMessages.typingPage_autoClose_phpDoc_and_commens;
		master = addCheckBox(composite, label, PreferenceConstants.EDITOR_CLOSE_PHPDOCS_AND_COMMENTS, 0);

		label = PHPUIMessages.typingPage_autoAdd_phpDoc_tags;
		slave = addCheckBox(composite, label, PreferenceConstants.EDITOR_ADD_PHPDOC_TAGS, 0);
		createDependency(master, slave);
	}

	protected Button addCheckBox(Composite parent, String label, String key, int indentation) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(label);

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indentation;
		gd.horizontalSpan = 2;
		checkBox.setLayoutData(gd);
		checkBox.addSelectionListener(fCheckBoxListener);
		makeScrollableCompositeAware(checkBox);

		fCheckBoxes.put(checkBox, key);

		return checkBox;
	}

	private void makeScrollableCompositeAware(Control control) {
		ScrolledPageContent parentScrolledComposite = getParentScrolledComposite(control);
		if (parentScrolledComposite != null) {
			parentScrolledComposite.adaptChild(control);
		}
	}

	protected final ScrolledPageContent getParentScrolledComposite(Control control) {
		Control parent = control.getParent();
		while (!(parent instanceof ScrolledPageContent) && parent != null) {
			parent = parent.getParent();
		}
		if (parent instanceof ScrolledPageContent) {
			return (ScrolledPageContent) parent;
		}
		return null;
	}

	protected void createDependency(final Button master, final Control slave) {
		createDependency(master, new Control[] { slave });
	}

	protected void createDependency(final Button master, final Control[] slaves) {
		Assert.isTrue(slaves.length > 0);
		indent(slaves[0]);
		SelectionListener listener = new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				boolean state = master.getSelection();
				for (int i = 0; i < slaves.length; i++) {
					slaves[i].setEnabled(state);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};
		master.addSelectionListener(listener);
		fMasterSlaveListeners.add(listener);
	}

	protected static void indent(Control control) {
		((GridData) control.getLayoutData()).horizontalIndent += INDENT;
	}

	private Map fCheckBoxes = new HashMap();
	private SelectionListener fCheckBoxListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			Button button = (Button) e.widget;
			fStore.setValue((String) fCheckBoxes.get(button), button.getSelection());
		}
	};

	private ArrayList fMasterSlaveListeners = new ArrayList();
	protected static final int INDENT = 20;

	private Map fTextFields = new HashMap();

}
