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
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.php.ui.editor.highlighter.PHPLineStyleProvider;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore.OverlayKey;
import org.eclipse.wst.sse.ui.internal.preferences.ui.AbstractColorPage;
import org.eclipse.wst.sse.ui.internal.preferences.ui.StyledTextColorPicker;

public class PHPColorPage extends AbstractColorPage {

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

	public String getSampleText() {
		return PHPUIMessages.ColorPage_CodeExample_0;
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

	protected void setupPicker(StyledTextColorPicker picker) {
		IModelManager mmanager = StructuredModelManager.getModelManager();
		picker.setParser(mmanager.createStructuredDocumentFor(ContentTypeIdForPHP.ContentTypeID_PHP).getParser());

		Dictionary descriptions = new Hashtable();
		initDescriptions(descriptions);

		ArrayList styleList = new ArrayList();
		initStyleList(styleList);

		PHPLineStyleProvider styleProvider = null;
		IEditorPart editor = PHPUiPlugin.getActivePage().getActiveEditor();
		final PHPStructuredEditor phpEditor = EditorUtility.getPHPStructuredEditor(editor);
		if (phpEditor != null) {
			SourceViewerConfiguration viewerConfig = phpEditor.getSourceViwerConfiguration();
			if (viewerConfig != null && viewerConfig instanceof PHPStructuredTextViewerConfiguration) {
				PHPStructuredTextViewerConfiguration phpViewerConfig = (PHPStructuredTextViewerConfiguration) viewerConfig;
				styleProvider = (PHPLineStyleProvider) phpViewerConfig.getLineStyleProvider();
			}
		}
		if (styleProvider == null) {
			styleProvider = new PHPLineStyleProvider();
		}
		Dictionary contextStyleMap = new Hashtable(styleProvider.getColorTypesMap());

		picker.setDescriptions(descriptions);
		picker.setStyleList(styleList);
		picker.setContextStyleMap(contextStyleMap);
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

	protected IPreferenceStore doGetPreferenceStore() {
		return PreferenceConstants.getPreferenceStore();
	}

	protected void savePreferences() {
		PHPUiPlugin.getDefault().savePluginPreferences();
	}
}