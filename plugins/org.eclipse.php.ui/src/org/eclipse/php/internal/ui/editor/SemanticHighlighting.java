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
package org.eclipse.php.internal.ui.editor;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PlatformUI;

/**
 * Semantic highlighting
 */
public abstract class SemanticHighlighting {

	/**
	 * @return the preference key, will be augmented by a prefix and a suffix
	 *         for each preference
	 */
	public abstract String getPreferenceKey();

	/**
	 * @return the default default text color
	 * @since 3.3
	 */
	public abstract RGB getDefaultDefaultTextColor();

	/**
	 * @return the default default text color
	 */
	public RGB getDefaultTextColor() {
		return findRGB(getThemeColorKey(), getDefaultDefaultTextColor());
	}

	/**
	 * @return <code>true</code> if the text attribute bold is set by default
	 */
	public abstract boolean isBoldByDefault();

	/**
	 * @return <code>true</code> if the text attribute italic is set by default
	 */
	public abstract boolean isItalicByDefault();

	/**
	 * @return <code>true</code> if the text attribute strikethrough is set by
	 *         default
	 * @since 3.1
	 */
	public boolean isStrikethroughByDefault() {
		return false;
	}

	/**
	 * @return <code>true</code> if the text attribute underline is set by
	 *         default
	 * @since 3.1
	 */
	public boolean isUnderlineByDefault() {
		return false;
	}

	/**
	 * @return <code>true</code> if the text attribute italic is enabled by
	 *         default
	 */
	public abstract boolean isEnabledByDefault();

	/**
	 * @return the display name
	 */
	public abstract String getDisplayName();

	/**
	 * Returns <code>true</code> iff the semantic highlighting consumes the
	 * semantic token.
	 * <p>
	 * NOTE: Implementors are not allowed to keep a reference on the token or on
	 * any object retrieved from the token.
	 * </p>
	 * 
	 * @param token
	 *            the semantic token for a
	 *            {@link org.eclipse.jdt.core.dom.SimpleName}
	 * @return <code>true</code> iff the semantic highlighting consumes the
	 *         semantic token
	 */
	public abstract boolean consumes(SemanticToken token);

	/**
	 * Returns <code>true</code> iff the semantic highlighting consumes the
	 * semantic token.
	 * <p>
	 * NOTE: Implementors are not allowed to keep a reference on the token or on
	 * any object retrieved from the token.
	 * </p>
	 * 
	 * @param token
	 *            the semantic token for a
	 *            {@link org.eclipse.jdt.core.dom.NumberLiteral},
	 *            {@link org.eclipse.jdt.core.dom.BooleanLiteral} or
	 *            {@link org.eclipse.jdt.core.dom.CharacterLiteral}
	 * @return <code>true</code> iff the semantic highlighting consumes the
	 *         semantic token
	 */
	public boolean consumesLiteral(SemanticToken token) {
		return false;
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
	 * @since 3.3
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

}
