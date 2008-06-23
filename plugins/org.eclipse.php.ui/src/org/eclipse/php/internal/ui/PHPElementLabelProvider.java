/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import org.eclipse.core.resources.IStorage;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.internal.ui.util.PHPElementImageProvider;
import org.eclipse.php.internal.ui.util.PHPElementLabels;
import org.eclipse.php.internal.ui.util.StorageLabelProvider;
import org.eclipse.swt.graphics.Image;

public class PHPElementLabelProvider extends LabelProvider {

	/**
	 * Flag (bit mask) indicating that methods labels include the method return type (appended).
	 */
	public final static int SHOW_RETURN_TYPE = 0x001;

	/**
	 * Flag (bit mask) indicating that method label include parameter types.
	 */
	public final static int SHOW_PARAMETERS = 0x002;

	/**
	 * Flag (bit mask) indicating that the label should include overlay icons
	 * for element type and modifiers.
	 */
	public final static int SHOW_OVERLAY_ICONS = 0x010;

	/**
	 * Flag (bit mask) indicating that a field label should include the declared type.
	 */
	public final static int SHOW_TYPE = 0x020;

	/**
	 * Flag (bit mask) indicating that the label should include the name of the
	 * folder root (appended).
	 */
	public final static int SHOW_ROOT = 0x040;

	/**
	 * Flag (bit mask) indicating that the label should show the icons with no space
	 * reserved for overlays.
	 */
	public final static int SHOW_SMALL_ICONS = 0x100;

	/**
	 * Flag (bit mask) indicating that the package fragment roots from class path variables should
	 * be rendered with the variable in the name
	 */
	public final static int SHOW_VARIABLE = 0x200;

	/**
	 * Flag (bit mask) indicating that  php files, types, declarations and members
	 * should be rendered qualified.
	 * 
	 */
	public final static int SHOW_QUALIFIED = 0x400;

	/**
	 * Flag (bit mask) indicating that php files, types, declarations and members
	 * should be rendered qualified.The qualification is appended.
	 */
	public final static int SHOW_POST_QUALIFIED = 0x800;

	/**
	 * Constant (value <code>0</code>) indicating that the label should show 
	 * the basic images only.
	 */
	public final static int SHOW_BASICS = 0x000;

	/**
	 * Constant indicating the default label rendering.
	 * Currently the default is equivalent to
	 * <code>SHOW_PARAMETERS | SHOW_OVERLAY_ICONS</code>.
	 */
	public final static int SHOW_DEFAULT = SHOW_PARAMETERS | SHOW_OVERLAY_ICONS;

	private PHPElementImageProvider fImageLabelProvider;

	private StorageLabelProvider fStorageLabelProvider;
	private int fFlags;
	private int fImageFlags;
	private long fTextFlags;

	/**
	 * Creates a new label provider with <code>SHOW_DEFAULT</code> flag.
	 *
	 * @see #SHOW_DEFAULT
	 * @since 2.0
	 */
	public PHPElementLabelProvider() {
		this(SHOW_DEFAULT);
	}

	/**
	 * Creates a new label provider.
	 *
	 * @param flags the initial options; a bitwise OR of <code>SHOW_* </code> constants
	 */
	public PHPElementLabelProvider(int flags) {
		fImageLabelProvider = new PHPElementImageProvider();
		fStorageLabelProvider = new StorageLabelProvider();
		fFlags = flags;
		updateImageProviderFlags();
		updateTextProviderFlags();
	}

	private boolean getFlag(int flag) {
		return (fFlags & flag) != 0;
	}

	/**
	 * Turns on the rendering options specified in the given flags.
	 *
	 * @param flags the options; a bitwise OR of <code>SHOW_* </code> constants
	 */
	public void turnOn(int flags) {
		fFlags |= flags;
		updateImageProviderFlags();
		updateTextProviderFlags();
	}

	/**
	 * Turns off the rendering options specified in the given flags.
	 *
	 * @param flags the initial options; a bitwise OR of <code>SHOW_* </code> constants
	 */
	public void turnOff(int flags) {
		fFlags &= (~flags);
		updateImageProviderFlags();
		updateTextProviderFlags();
	}

	private void updateImageProviderFlags() {
		fImageFlags = 0;
		if (getFlag(SHOW_OVERLAY_ICONS)) {
			fImageFlags |= PHPElementImageProvider.OVERLAY_ICONS;
		}
		if (getFlag(SHOW_SMALL_ICONS)) {
			fImageFlags |= PHPElementImageProvider.SMALL_ICONS;
		}
	}

	private void updateTextProviderFlags() {
		fTextFlags = PHPElementLabels.T_TYPE_PARAMETERS;
		if (getFlag(SHOW_RETURN_TYPE)) {
			fTextFlags |= PHPElementLabels.M_APP_RETURNTYPE;
		}
		if (getFlag(SHOW_PARAMETERS)) {
			fTextFlags |= PHPElementLabels.M_PARAMETER_TYPES;
		}
		if (getFlag(SHOW_ROOT)) {
			fTextFlags |= PHPElementLabels.APPEND_ROOT_PATH | PHPElementLabels.P_POST_QUALIFIED | PHPElementLabels.T_POST_QUALIFIED | PHPElementLabels.CF_POST_QUALIFIED | PHPElementLabels.CU_POST_QUALIFIED | PHPElementLabels.M_POST_QUALIFIED | PHPElementLabels.F_POST_QUALIFIED;
		}
		if (getFlag(SHOW_POST_QUALIFIED)) {
			fTextFlags |= (PHPElementLabels.T_POST_QUALIFIED | PHPElementLabels.CF_POST_QUALIFIED | PHPElementLabels.CU_POST_QUALIFIED);
		} else if (getFlag(SHOW_QUALIFIED)) {
			fTextFlags |= (PHPElementLabels.T_FULLY_QUALIFIED | PHPElementLabels.CF_QUALIFIED | PHPElementLabels.CU_QUALIFIED);
		}
		if (getFlag(SHOW_TYPE)) {
			fTextFlags |= PHPElementLabels.F_APP_TYPE_SIGNATURE;
		}
		if (getFlag(SHOW_VARIABLE)) {
			fTextFlags |= PHPElementLabels.ROOT_VARIABLE;
		}
		if (getFlag(SHOW_QUALIFIED)) {
			fTextFlags |= (PHPElementLabels.F_FULLY_QUALIFIED | PHPElementLabels.M_FULLY_QUALIFIED | PHPElementLabels.I_FULLY_QUALIFIED | PHPElementLabels.T_FULLY_QUALIFIED | PHPElementLabels.D_QUALIFIED | PHPElementLabels.CF_QUALIFIED | PHPElementLabels.CU_QUALIFIED);
		}
		if (getFlag(SHOW_POST_QUALIFIED)) {
			fTextFlags |= (PHPElementLabels.F_POST_QUALIFIED | PHPElementLabels.M_POST_QUALIFIED | PHPElementLabels.I_POST_QUALIFIED | PHPElementLabels.T_POST_QUALIFIED | PHPElementLabels.D_POST_QUALIFIED | PHPElementLabels.CF_POST_QUALIFIED | PHPElementLabels.CU_POST_QUALIFIED);
		}
	}

	/* (non-Javadoc)
	 * @see ILabelProvider#getImage
	 */
	public Image getImage(Object element) {
		Image result = fImageLabelProvider.getImageLabel(element, fImageFlags);
		if (result != null) {
			return result;
		}

		if (element instanceof IStorage)
			return fStorageLabelProvider.getImage(element);

		return result;
	}

	/* (non-Javadoc)
	 * @see ILabelProvider#getText
	 */
	public String getText(Object element) {
		String text = PHPElementLabels.getTextLabel(element, fTextFlags);
		if (text.length() > 0) {
			return text;
		}

		if (element instanceof IStorage)
			return fStorageLabelProvider.getText(element);

		return text;
	}

	/* (non-Javadoc)
	 * 
	 * @see IBaseLabelProvider#dispose
	 */
	public void dispose() {
		fStorageLabelProvider.dispose();
		fImageLabelProvider.dispose();
	}
}
