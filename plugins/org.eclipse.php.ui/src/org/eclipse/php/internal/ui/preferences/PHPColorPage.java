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
package org.eclipse.php.internal.ui.preferences;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore.OverlayKey;

public class PHPColorPage extends org.eclipse.jface.preference.PreferencePage implements org.eclipse.ui.IWorkbenchPreferencePage {

	protected OverlayPreferenceStore fOverlayStore;
	protected PHPStyledTextColorPicker fPicker = null;

	/**
	 * Creates the coloring group used in createContents This method can be
	 * overwritten to set the text of the group or provide an infopop
	 */
	protected Composite createColoringComposite(Composite parent) {
		Composite coloringComposite = createComposite(parent, 1);
		return coloringComposite;
	}

	/**
	 * Creates composite control and sets the default layout data.
	 */
	protected Composite createComposite(Composite parent, int numColumns) {
		Composite composite = new Composite(parent, SWT.NULL);

		//GridLayout
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		//GridData
		GridData data = new GridData(GridData.FILL);
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);
		return composite;
	}

	protected Control createContents(Composite parent) {
		// create scrollbars for this preference page when needed
		final ScrolledComposite sc1 = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		sc1.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite pageComponent = createComposite(sc1, 1);
		sc1.setContent(pageComponent);
		setSize(pageComponent);

		Label descLabel = createDescriptionLabel(pageComponent, SSEUIMessages.AbstractColorPageDescription); //$NON-NLS-1$
		Composite coloringComposite = createColoringComposite(pageComponent);
		createContentsForPicker(coloringComposite);

		GridData gd = (GridData) descLabel.getLayoutData();
		gd.widthHint = (coloringComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT)).x;
		setSize(pageComponent);
		return pageComponent;
	}

	protected void createContentsForPicker(Composite parent) {
		// create the color picker
		fPicker = new PHPStyledTextColorPicker(parent, SWT.NULL);
		GridData data = new GridData(GridData.FILL_BOTH);
		fPicker.setLayoutData(data);

		fPicker.setPreferenceStore(fOverlayStore);
		setupPicker(fPicker);

		fPicker.setText(getSampleText());
	}

	/**
	 * Create description label displayed at top of preference page. This
	 * method/label is used instead of PreferencePage's description label
	 * because the ScrolledComposite contained in this page will not fully
	 * work (horizontal scrolling) with PreferencePage's description label.
	 */
	protected Label createDescriptionLabel(Composite parent, String description) {
		Label label = new Label(parent, SWT.LEFT | SWT.WRAP);
		label.setText(description);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		label.setLayoutData(data);

		return label;
	}

	/**
	 * Creates composite control and sets the default layout data.
	 */

	protected Group createGroup(Composite parent, int numColumns) {
		Group group = new Group(parent, SWT.NULL);

		//GridLayout
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		group.setLayout(layout);

		//GridData
		GridData data = new GridData(GridData.FILL);
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		group.setLayoutData(data);

		return group;
	}

	/**
	 * Utility method that creates a label instance and sets the default
	 * layout data.
	 */
	protected Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);
		GridData data = new GridData(GridData.FILL);
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	/**
	 * Set up all the style preference keys in the overlay store
	 */
	protected OverlayKey[] createOverlayStoreKeys() {
		ArrayList overlayKeys = new ArrayList();

		ArrayList styleList = new ArrayList();
		initStyleList(styleList);
		Iterator i = styleList.iterator();
		while (i.hasNext()) {
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, (String) i.next()));
		}

		OverlayPreferenceStore.OverlayKey[] keys = new OverlayPreferenceStore.OverlayKey[overlayKeys.size()];
		overlayKeys.toArray(keys);
		return keys;
	}

	protected Button createPushButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		GridData data = new GridData(GridData.FILL);
		data.horizontalAlignment = GridData.FILL;
		button.setLayoutData(data);
		return button;
	}

	/**
	 * Utility method that creates a text instance and sets the default layout
	 * data.
	 */
	protected Text createTextField(Composite parent, String text) {
		Text textfield = new Text(parent, SWT.LEFT);
		textfield.setText(text);
		GridData data = new GridData(GridData.FILL);
		data.horizontalAlignment = GridData.FILL;
		textfield.setLayoutData(data);
		return textfield;
	}

	public void dispose() {
		super.dispose();
		if (fPicker != null && !fPicker.isDisposed())
			fPicker.releasePickerResources();
		if (fOverlayStore != null) {
			fOverlayStore.stop();
		}
	}

	protected IPreferenceStore doGetPreferenceStore() {
		return PreferenceConstants.getPreferenceStore();
	}

	public PHPStyledTextColorPicker getPicker() {
		return fPicker;
	}

	public String getSampleText() {
		return PHPUIMessages.ColorPage_CodeExample_0;
	}

	/**
	 * Initializes this preference page for the given workbench.
	 * <p>
	 * This method is called automatically as the preference page is being
	 * created and initialized. Clients must not call this method.
	 * </p>
	 * 
	 * @param workbench
	 *            the workbench
	 */
	public void init(IWorkbench workbench) {
		fOverlayStore = new OverlayPreferenceStore(getPreferenceStore(), createOverlayStoreKeys());
		fOverlayStore.load();
		fOverlayStore.start();
	}

	protected void initDescriptions(Dictionary descriptions) {
		descriptions.put(PreferenceConstants.EDITOR_NORMAL_COLOR, PHPUIMessages.ColorPage_Normal);
		descriptions.put(PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR, PHPUIMessages.ColorPage_BoundryMaker);
		descriptions.put(PreferenceConstants.EDITOR_KEYWORD_COLOR, PHPUIMessages.ColorPage_Keyword);
		descriptions.put(PreferenceConstants.EDITOR_VARIABLE_COLOR, PHPUIMessages.ColorPage_Variable);
		descriptions.put(PreferenceConstants.EDITOR_STRING_COLOR, PHPUIMessages.ColorPage_String);
		descriptions.put(PreferenceConstants.EDITOR_NUMBER_COLOR, PHPUIMessages.ColorPage_Number);
		descriptions.put(PreferenceConstants.EDITOR_HEREDOC_COLOR, PHPUIMessages.ColorPage_Heredoc);
		descriptions.put(PreferenceConstants.EDITOR_COMMENT_COLOR, PHPUIMessages.ColorPage_Comment);
		descriptions.put(PreferenceConstants.EDITOR_PHPDOC_COLOR, PHPUIMessages.ColorPage_Phpdoc);
		descriptions.put(PreferenceConstants.EDITOR_TASK_COLOR, PHPUIMessages.ColorPage_TaskTag);
	}

	protected void initStyleList(ArrayList list) {
		list.add(PreferenceConstants.EDITOR_NORMAL_COLOR);
		list.add(PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);
		list.add(PreferenceConstants.EDITOR_KEYWORD_COLOR);
		list.add(PreferenceConstants.EDITOR_VARIABLE_COLOR);
		list.add(PreferenceConstants.EDITOR_STRING_COLOR);
		list.add(PreferenceConstants.EDITOR_NUMBER_COLOR);
		list.add(PreferenceConstants.EDITOR_HEREDOC_COLOR);
		list.add(PreferenceConstants.EDITOR_COMMENT_COLOR);
		list.add(PreferenceConstants.EDITOR_PHPDOC_COLOR);
		list.add(PreferenceConstants.EDITOR_TASK_COLOR);
	}

	/**
	 * Initializes states of the controls using default values in the
	 * preference store.
	 */
	protected void performDefaults() {
		fOverlayStore.loadDefaults();
		fPicker.refresh();
	}

	public boolean performOk() {
		fOverlayStore.propagate();
		savePreferences();
		return true;
	}

	protected void savePreferences() {
		PHPUiPlugin.getDefault().savePluginPreferences();
	}

	/**
	 * Sets the size of composite to the default value
	 */
	protected void setSize(Composite composite) {
		if (composite != null) {
			// Note: The font is set here in anticipation that the class inheriting
			//       this base class may add widgets to the dialog.   setSize
			//       is assumed to be called just before we go live.
			applyDialogFont(composite);
			Point minSize = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			composite.setSize(minSize);
			// set scrollbar composite's min size so page is expandable but
			// has scrollbars when needed
			if (composite.getParent() instanceof ScrolledComposite) {
				ScrolledComposite sc1 = (ScrolledComposite) composite.getParent();
				sc1.setMinSize(minSize);
				sc1.setExpandHorizontal(true);
				sc1.setExpandVertical(true);
			}
		}
	}

	protected void setupPicker(PHPStyledTextColorPicker picker) {
		IModelManager mmanager = StructuredModelManager.getModelManager();
		picker.setParser(mmanager.createStructuredDocumentFor(ContentTypeIdForPHP.ContentTypeID_PHP).getParser());

		Dictionary descriptions = new Hashtable();
		initDescriptions(descriptions);

		ArrayList styleList = new ArrayList();
		initStyleList(styleList);

		LineStyleProviderForPhp styleProvider = new LineStyleProviderForPhp();
		Dictionary contextStyleMap = new Hashtable(styleProvider.getColorTypesMap());

		picker.setDescriptions(descriptions);
		picker.setStyleList(styleList);
		picker.setContextStyleMap(contextStyleMap);

		picker.setLineStyleProvider(styleProvider);
	}

	public void setVisible(boolean visible) {
		boolean doShrink = false;
		// limiter, for the really huge fonts
		if (visible) {
			getPicker().refresh();
			int x = Math.min(getControl().getShell().getSize().x, getControl().getDisplay().getClientArea().width * 9 / 10);
			int y = Math.min(getControl().getShell().getSize().y, getControl().getDisplay().getClientArea().height * 9 / 10);
			boolean shrinkWidth = (x != getControl().getShell().getSize().x);
			boolean shrinkHeight = (y != getControl().getShell().getSize().y);
			doShrink = shrinkWidth || shrinkHeight;
			if (doShrink) {
				// modify just the height
				if (shrinkHeight && !shrinkWidth)
					getShell().setBounds(getShell().getLocation().x, 0, getShell().getSize().x, getControl().getDisplay().getClientArea().height);
				// modify just the width
				else if (!shrinkHeight && shrinkWidth)
					getShell().setBounds(0, getShell().getLocation().y, getControl().getDisplay().getClientArea().width, getShell().getSize().y);
				// change the entire shell size to only fill the display, and
				// move it to the origin
				else
					getShell().setBounds(0, 0, getControl().getDisplay().getClientArea().width, getControl().getDisplay().getClientArea().height);
			}
		}
		super.setVisible(visible);
		if (doShrink) {
			getControl().getShell().redraw();
			getControl().getShell().update();
		}
	}
}