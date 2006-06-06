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
package org.eclipse.php.ui.util;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.php.ui.preferences.PreferenceConstants;

;

/**
 * PHPUILabelProvider that respects settings from the Appearance preference page.
 * Triggers a viewer update when a preference changes.
 */
public class AppearanceAwareLabelProvider extends PHPUILabelProvider implements IPropertyChangeListener {

	public final static int DEFAULT_TEXTFLAGS = PHPElementLabels.ROOT_VARIABLE | PHPElementLabels.M_PARAMETER_TYPES  | PHPElementLabels.M_PARAMETER_NAMES| PHPElementLabels.M_APP_RETURNTYPE | PHPElementLabels.REFERENCED_ROOT_POST_QUALIFIED;
	public final static int DEFAULT_IMAGEFLAGS = PHPElementImageProvider.OVERLAY_ICONS;

	private int fTextFlagMask;
	private int fImageFlagMask;

	/**
	 * Constructor for AppearanceAwareLabelProvider.
	 */
	public AppearanceAwareLabelProvider(int textFlags, int imageFlags) {
		super(textFlags, imageFlags);
		initMasks();
		PreferenceConstants.getPreferenceStore().addPropertyChangeListener(this);
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
			fTextFlagMask ^= PHPElementLabels.M_APP_RETURNTYPE;
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
			LabelProviderChangedEvent lpEvent = new LabelProviderChangedEvent(this, null); // refresh all
			fireLabelProviderChanged(lpEvent);
		}
	}

	/*
	 * @see IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		PreferenceConstants.getPreferenceStore().removePropertyChangeListener(this);
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
	protected int evaluateTextFlags(Object element) {
		return getTextFlags() & fTextFlagMask;
	}

}
