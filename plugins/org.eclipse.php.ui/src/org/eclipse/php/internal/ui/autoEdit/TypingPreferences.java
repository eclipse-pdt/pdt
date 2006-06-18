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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.PreferenceConstants;

/**
 * @author guy.g
 */

public class TypingPreferences {

	static boolean closeQuotes;
	static boolean closeBrackets;
	static boolean closeCurlyBracket;
	static boolean closePhpdoc;
	static boolean addDocTags;

	static {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();

		closeQuotes = store.getBoolean(PreferenceConstants.EDITOR_CLOSE_STRINGS);
		closeBrackets = store.getBoolean(PreferenceConstants.EDITOR_CLOSE_BRACKETS);
		closeCurlyBracket = store.getBoolean(PreferenceConstants.EDITOR_CLOSE_BRACES);
		closePhpdoc = store.getBoolean(PreferenceConstants.EDITOR_CLOSE_PHPDOCS_AND_COMMENTS);
		addDocTags = store.getBoolean(PreferenceConstants.EDITOR_ADD_PHPDOC_TAGS);

		store.addPropertyChangeListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (property == PreferenceConstants.EDITOR_CLOSE_STRINGS) {
					closeQuotes = Boolean.getBoolean((String)event.getNewValue());
					return;
				}
				if (property == PreferenceConstants.EDITOR_CLOSE_BRACKETS) {
					closeBrackets = Boolean.getBoolean((String)event.getNewValue());
					return;
				}
				if (property == PreferenceConstants.EDITOR_CLOSE_BRACES) {
					closeCurlyBracket = Boolean.getBoolean((String)event.getNewValue());
					return;
				}
				if (property == PreferenceConstants.EDITOR_CLOSE_PHPDOCS_AND_COMMENTS) {
					closePhpdoc = Boolean.getBoolean((String)event.getNewValue());
					return;
				}
				if (property == PreferenceConstants.EDITOR_ADD_PHPDOC_TAGS) {
					addDocTags = Boolean.getBoolean((String)event.getNewValue());
					return;
				}
			}
		});
	}
}
