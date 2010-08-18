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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;

/**
 * @author guy.g
 */

public class TypingPreferences {

	static boolean closeQuotes;
	static boolean closeBrackets;
	static boolean closeCurlyBracket;
	static boolean closePhpdoc;
	static boolean addDocTags;
	static boolean addPhpCloseTag;
	static boolean addPhpForPhpStartTags;
	static {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();

		closeQuotes = store
				.getBoolean(PreferenceConstants.EDITOR_CLOSE_STRINGS);
		closeBrackets = store
				.getBoolean(PreferenceConstants.EDITOR_CLOSE_BRACKETS);
		closeCurlyBracket = store
				.getBoolean(PreferenceConstants.EDITOR_CLOSE_BRACES);
		closePhpdoc = store
				.getBoolean(PreferenceConstants.EDITOR_CLOSE_PHPDOCS_AND_COMMENTS);
		addDocTags = store
				.getBoolean(PreferenceConstants.EDITOR_ADD_PHPDOC_TAGS);
		addPhpCloseTag = store
				.getBoolean(PreferenceConstants.EDITOR_ADD_PHPCLOSE_TAGS);
		addPhpForPhpStartTags = store
				.getBoolean(PreferenceConstants.EDITOR_ADD_PHP_FOR_PHPSTART_TAGS);
		store.addPropertyChangeListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (property == PreferenceConstants.EDITOR_CLOSE_STRINGS) {
					closeQuotes = Boolean.valueOf((String) event.getNewValue())
							.booleanValue();
					return;
				}
				if (property == PreferenceConstants.EDITOR_CLOSE_BRACKETS) {
					closeBrackets = Boolean.valueOf(
							(String) event.getNewValue()).booleanValue();
					return;
				}
				if (property == PreferenceConstants.EDITOR_CLOSE_BRACES) {
					closeCurlyBracket = Boolean.valueOf(
							(String) event.getNewValue()).booleanValue();
					return;
				}
				if (property == PreferenceConstants.EDITOR_CLOSE_PHPDOCS_AND_COMMENTS) {
					closePhpdoc = Boolean.valueOf((String) event.getNewValue())
							.booleanValue();
					return;
				}
				if (property == PreferenceConstants.EDITOR_ADD_PHPDOC_TAGS) {
					addDocTags = Boolean.valueOf((String) event.getNewValue())
							.booleanValue();
					return;
				}
				if (property == PreferenceConstants.EDITOR_ADD_PHPCLOSE_TAGS) {
					addPhpCloseTag = Boolean.valueOf(
							(String) event.getNewValue()).booleanValue();
					return;
				}
				if (property == PreferenceConstants.EDITOR_ADD_PHP_FOR_PHPSTART_TAGS) {
					addPhpForPhpStartTags = Boolean.valueOf(
							(String) event.getNewValue()).booleanValue();
					return;
				}
			}
		});
	}
}
