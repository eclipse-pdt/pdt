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
package org.eclipse.php.internal.ui.actions;

import java.util.ResourceBundle;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;

/**
 * A toolbar action which toggles the
 * {@linkplain org.eclipse.php.internal.ui.preferences.PreferenceConstants#EDITOR_MARK_OCCURRENCES
 * mark occurrences preference}.
 * 
 * @since 3.0
 */
public class ToggleMarkOccurrencesAction extends TextEditorAction implements
		IPropertyChangeListener {

	private IPreferenceStore fStore;

	/**
	 * Constructs and updates the action.
	 */
	public ToggleMarkOccurrencesAction(ResourceBundle resourceBundle) {
		super(resourceBundle,
				"ToggleMarkOccurrencesAction.", null, IAction.AS_CHECK_BOX); //$NON-NLS-1$
		PHPPluginImages.setToolImageDescriptors(this, "mark_occurrences.gif"); //$NON-NLS-1$
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IPHPHelpContextIds.TOGGLE_MARK_OCCURRENCES_ACTION); // TODO - Add
		// Help
		update();
	}

	/*
	 * @see IAction#actionPerformed
	 */
	public void run() {
		fStore.setValue(PreferenceConstants.EDITOR_MARK_OCCURRENCES,
				isChecked());
	}

	/*
	 * @see TextEditorAction#update
	 */
	public void update() {
		ITextEditor editor = getTextEditor();

		boolean checked = false;
		if (editor instanceof PHPStructuredEditor)
			checked = ((PHPStructuredEditor) editor).isMarkingOccurrences();

		setChecked(checked);
		setEnabled(editor != null);
	}

	/*
	 * @see TextEditorAction#setEditor(ITextEditor)
	 */
	public void setEditor(ITextEditor editor) {
		super.setEditor(editor);
		if (editor != null) {
			if (fStore == null) {
				fStore = PHPUiPlugin.getDefault().getPreferenceStore();
				fStore.addPropertyChangeListener(this);
			}
		} else if (fStore != null) {
			fStore.removePropertyChangeListener(this);
			fStore = null;
		}
		update();
	}

	/*
	 * @see IPropertyChangeListener#propertyChange(PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(
				PreferenceConstants.EDITOR_MARK_OCCURRENCES))
			setChecked(Boolean.valueOf(event.getNewValue().toString())
					.booleanValue());
	}
}
