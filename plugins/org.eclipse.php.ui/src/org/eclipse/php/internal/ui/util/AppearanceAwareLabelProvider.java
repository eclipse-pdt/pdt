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
package org.eclipse.php.internal.ui.util;

import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;

;

/**
 * PHPUILabelProvider that respects settings from the Appearance preference
 * page. Triggers a viewer update when a preference changes.
 */
public class AppearanceAwareLabelProvider extends ScriptUILabelProvider
		implements IPropertyChangeListener {

	public final static long DEFAULT_TEXTFLAGS = ScriptElementLabels.ROOT_VARIABLE
			| ScriptElementLabels.M_PARAMETER_TYPES
			| ScriptElementLabels.M_PARAMETER_NAMES
			| ScriptElementLabels.M_APP_RETURNTYPE
			| ScriptElementLabels.REFERENCED_ROOT_POST_QUALIFIED;
	public final static int DEFAULT_IMAGEFLAGS = ScriptElementImageProvider.OVERLAY_ICONS;

	private int fTextFlagMask;
	private int fImageFlagMask;

	/**
	 * Constructor for AppearanceAwareLabelProvider.
	 */
	public AppearanceAwareLabelProvider(long textFlags, int imageFlags) {
		super(textFlags, imageFlags);
		initMasks();
		PreferenceConstants.getPreferenceStore()
				.addPropertyChangeListener(this);
	}

	/**
	 * Creates a labelProvider with DEFAULT_TEXTFLAGS and DEFAULT_IMAGEFLAGS
	 */
	public AppearanceAwareLabelProvider() {
		this(DEFAULT_TEXTFLAGS, DEFAULT_IMAGEFLAGS);
	}

	private void initMasks() {
		IPreferenceStore store = PreferenceConstants.getPreferenceStore();
		fTextFlagMask = -1;
		if (!store.getBoolean(PreferenceConstants.APPEARANCE_METHOD_RETURNTYPE)) {
			fTextFlagMask ^= ScriptElementLabels.M_APP_RETURNTYPE;
		}

		fImageFlagMask = -1;
	}

	/*
	 * @see IPropertyChangeListener#propertyChange(PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if (property.equals(PreferenceConstants.APPEARANCE_METHOD_RETURNTYPE)) {
			initMasks();
			LabelProviderChangedEvent lpEvent = new LabelProviderChangedEvent(
					this, null); // refresh all
			fireLabelProviderChanged(lpEvent);
		}
	}

	/*
	 * @see IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		PreferenceConstants.getPreferenceStore().removePropertyChangeListener(
				this);
		super.dispose();
	}

	/*
	 * @see PHPUILabelProvider#evaluateImageFlags()
	 */
	protected int evaluateImageFlags(Object element) {
		return getImageFlags() & fImageFlagMask;
	}

	/*
	 * @see PHPUILabelProvider#evaluateTextFlags()
	 */
	protected long evaluateTextFlags(Object element) {
		return getTextFlags() & fTextFlagMask;
	}

}
