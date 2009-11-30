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

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PlatformUI;

public class SemanticHighlightingStyle {

	private String preferenceKey;
	private boolean boldByDefault = false;
	private boolean italicByDefault = false;
	private boolean strikethroughByDefault = false;
	private boolean underlineByDefault = false;
	private RGB defaultTextColor = new RGB(0, 0, 0);
	private boolean enabledByDefault = true;

	public SemanticHighlightingStyle setDefaultTextColor(int red, int green,
			int blue) {
		return setDefaultTextColor(new RGB(red, green, blue));
	}

	public SemanticHighlightingStyle setDefaultTextColor(RGB defaultTextColor) {
		this.defaultTextColor = defaultTextColor;
		return this;
	}

	public SemanticHighlightingStyle(String preferenceKey) {
		this.preferenceKey = preferenceKey;
		this.defaultTextColor = findRGB(getThemeColorKey(),
				getDefaultTextColor());
	}

	public SemanticHighlightingStyle(String preferenceKey, String displayName,
			boolean boldByDefault, boolean italicByDefault,
			boolean strikethroughByDefault, boolean underlineByDefault,
			RGB defaultTextColor) {
		this.preferenceKey = preferenceKey;
		this.boldByDefault = boldByDefault;
		this.italicByDefault = italicByDefault;
		this.strikethroughByDefault = strikethroughByDefault;
		this.underlineByDefault = underlineByDefault;
		this.defaultTextColor = defaultTextColor;
	}

	public String getPreferenceKey() {
		return preferenceKey;
	}

	/**
	 * @return the default default text color
	 */
	protected RGB getDefaultTextColor() {
		return defaultTextColor;
	}

	private String getThemeColorKey() {
		return PHPUiPlugin.ID + "." + getPreferenceKey() + "Highlighting"; //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Returns the RGB for the given key in the given color registry.
	 * 
	 * @param key
	 *            the key for the constant in the registry
	 * @param defaultRGB
	 *            the default RGB if no entry is found
	 * @return RGB the RGB
	 */
	private static RGB findRGB(String key, RGB defaultRGB) {
		if (!PlatformUI.isWorkbenchRunning())
			return defaultRGB;

		ColorRegistry registry = PlatformUI.getWorkbench().getThemeManager()
				.getCurrentTheme().getColorRegistry();
		RGB rgb = registry.getRGB(key);
		if (rgb != null)
			return rgb;
		return defaultRGB;
	}

	public boolean isBoldByDefault() {
		return boldByDefault;
	}

	public SemanticHighlightingStyle setBoldByDefault(boolean boldByDefault) {
		this.boldByDefault = boldByDefault;
		return this;
	}

	public boolean isItalicByDefault() {
		return italicByDefault;
	}

	public SemanticHighlightingStyle setItalicByDefault(boolean italicByDefault) {
		this.italicByDefault = italicByDefault;
		return this;
	}

	public boolean isStrikethroughByDefault() {
		return strikethroughByDefault;
	}

	public SemanticHighlightingStyle setStrikethroughByDefault(
			boolean strikethroughByDefault) {
		this.strikethroughByDefault = strikethroughByDefault;
		return this;
	}

	public boolean isUnderlineByDefault() {
		return underlineByDefault;
	}

	public SemanticHighlightingStyle setUnderlineByDefault(
			boolean underlineByDefault) {
		this.underlineByDefault = underlineByDefault;
		return this;
	}

	public SemanticHighlightingStyle setPreferenceKey(String preferenceKey) {
		this.preferenceKey = preferenceKey;
		return this;
	}

	public boolean isEnabledByDefault() {
		return enabledByDefault;
	}

	public SemanticHighlightingStyle setEnabledByDefault(
			boolean enabledByDefault) {
		this.enabledByDefault = enabledByDefault;
		return this;
	}
}
