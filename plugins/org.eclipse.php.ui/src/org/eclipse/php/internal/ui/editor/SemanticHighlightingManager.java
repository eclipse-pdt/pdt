/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     William Candillon - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor;

import java.util.*;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;

@SuppressWarnings("restriction")
public class SemanticHighlightingManager {

	private static SemanticHighlightingManager instance;

	private List<AbstractSemanticHighlighting> rules = new LinkedList<AbstractSemanticHighlighting>();

	private Map<String, AbstractSemanticHighlighting> highlightings = new LinkedHashMap<String, AbstractSemanticHighlighting>();

	public synchronized static SemanticHighlightingManager getInstance() {
		if (instance == null) {
			instance = new SemanticHighlightingManager();
		}
		return instance;
	}

	private SemanticHighlightingManager() {
		super();
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(
						"org.eclipse.wst.sse.ui.semanticHighlighting"); //$NON-NLS-1$
		try {
			loadContributor(elements);
			// Sort the contributors according to their priority
			Collections.sort(rules);
			// Add contributors to the map
			for (AbstractSemanticHighlighting rule : rules) {
				highlightings.put(rule.getPreferenceKey(), rule);
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	private SemanticHighlightingManager loadContributor(
			IConfigurationElement[] elements) throws Exception {
		for (IConfigurationElement element : elements) {
			String target = element.getAttribute("target"); //$NON-NLS-1$
			if ("org.eclipse.php.core.phpsource".equals(target)) { //$NON-NLS-1$
				final Object o = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (o instanceof AbstractSemanticHighlighting) {
					AbstractSemanticHighlighting instance = (AbstractSemanticHighlighting) o;
					rules.add(instance);
				}
			}
		}
		return this;
	}

	public Map<String, AbstractSemanticHighlighting> getSemanticHighlightings() {
		return highlightings;
	}

	/**
	 * Initialize default preferences in the given preference store.
	 * 
	 * @param store
	 *            The preference store
	 */
	public SemanticHighlightingManager initDefaults(IPreferenceStore store) {
		Collection<AbstractSemanticHighlighting> semanticHighlightings = highlightings
				.values();
		for (AbstractSemanticHighlighting rule : semanticHighlightings) {
			rule.initDefaultPreferences();
			SemanticHighlightingStyle style = rule.getStyle();
			setDefaultAndFireEvent(store, rule.getColorPreferenceKey(),
					style.getDefaultTextColor());
			// setDefaultAndFireEvent(store,
			// rule.getBackgroundColorPreferenceKey(), style
			// .);
			store.setDefault(rule.getBoldPreferenceKey(),
					style.isBoldByDefault());
			store.setDefault(rule.getItalicPreferenceKey(),
					style.isItalicByDefault());
			store.setDefault(rule.getStrikethroughPreferenceKey(),
					style.isStrikethroughByDefault());
			store.setDefault(rule.getUnderlinePreferenceKey(),
					style.isUnderlineByDefault());
			store.setDefault(rule.getEnabledPreferenceKey(),
					style.isEnabledByDefault());
		}
		return this;
	}

	/**
	 * Sets the default value and fires a property change event if necessary.
	 * 
	 * @param store
	 *            the preference store
	 * @param key
	 *            the preference key
	 * @param newValue
	 *            the new value
	 */
	private static void setDefaultAndFireEvent(IPreferenceStore store,
			String key, RGB newValue) {
		RGB oldValue = null;
		if (store.isDefault(key))
			oldValue = PreferenceConverter.getDefaultColor(store, key);

		PreferenceConverter.setDefault(store, key, newValue);
		store.setDefault(key, ColorHelper.toRGBString(newValue));

		if (oldValue != null && !oldValue.equals(newValue))
			store.firePropertyChangeEvent(key, oldValue, newValue);
	}
}
