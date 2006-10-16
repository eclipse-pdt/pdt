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

import java.util.StringTokenizer;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.core.phpModel.phpElementData.PHPModifier;
import org.eclipse.php.ui.preferences.PreferenceConstants;

/**
 */
public class MembersOrderPreferenceCache implements IPropertyChangeListener {

	public static final int INCLUDEFILES_INDEX = 0;
	public static final int CONSTANTS_INDEX = 1;
	public static final int STATIC_VARS_INDEX = 2;
	public static final int CONSTRUCTORS_INDEX = 3;
	public static final int STATIC_FUNCTIONS_INDEX = 4;
	public static final int FUNCTIONS_INDEX = 5;
	public static final int CLASS_INDEX = 6;
	public static final int VARS_INDEX = 7;
	public static final int N_CATEGORIES = 8;

	private static final int PUBLIC_INDEX = 0;
	private static final int DEFAULT_INDEX = 1;
	private static final int PROTECTED_INDEX = 2;
	private static final int PRIVATE_INDEX = 3;
	private static final int N_VISIBILITIES = 4;

	private int[] fCategoryOffsets = null;

	private boolean fSortByVisibility;
	private int[] fVisibilityOffsets = null;

	public MembersOrderPreferenceCache() {
		fCategoryOffsets = null;
		fSortByVisibility = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.APPEARANCE_ENABLE_VISIBILITY_SORT_ORDER);
		fVisibilityOffsets = null;
	}

	public static boolean isMemberOrderProperty(String property) {
		return PreferenceConstants.APPEARANCE_MEMBER_SORT_ORDER.equals(property) || PreferenceConstants.APPEARANCE_VISIBILITY_SORT_ORDER.equals(property) || PreferenceConstants.APPEARANCE_ENABLE_VISIBILITY_SORT_ORDER.equals(property);
	}

	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();

		if (PreferenceConstants.APPEARANCE_MEMBER_SORT_ORDER.equals(property)) {
			fCategoryOffsets = null;
		} else if (PreferenceConstants.APPEARANCE_VISIBILITY_SORT_ORDER.equals(property)) {
			fVisibilityOffsets = null;
		} else if (PreferenceConstants.APPEARANCE_ENABLE_VISIBILITY_SORT_ORDER.equals(property)) {
			fSortByVisibility = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.APPEARANCE_ENABLE_VISIBILITY_SORT_ORDER);
		}
	}

	public int getCategoryIndex(int kind) {
		if (fCategoryOffsets == null) {
			fCategoryOffsets = getCategoryOffsets();
		}
		return fCategoryOffsets[kind];
	}

	private int[] getCategoryOffsets() {
		int[] offsets = new int[N_CATEGORIES];
		IPreferenceStore store = PreferenceConstants.getPreferenceStore();
		String key = PreferenceConstants.APPEARANCE_MEMBER_SORT_ORDER;
		boolean success = fillCategoryOffsetsFromPreferenceString(store.getString(key), offsets);
		if (!success) {
			store.setToDefault(key);
			fillCategoryOffsetsFromPreferenceString(store.getDefaultString(key), offsets);
		}
		return offsets;
	}

	private boolean fillCategoryOffsetsFromPreferenceString(String str, int[] offsets) {
		StringTokenizer tokenizer = new StringTokenizer(str, ","); //$NON-NLS-1$
		int i = 0;

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().trim();
			if ("T".equals(token)) { //$NON-NLS-1$
				offsets[CLASS_INDEX] = i++;
			} else if ("I".equals(token)) { //$NON-NLS-1$
				offsets[INCLUDEFILES_INDEX] = i++;
			} else if ("F".equals(token)) { //$NON-NLS-1$
				offsets[FUNCTIONS_INDEX] = i++;
			} else if ("V".equals(token)) { //$NON-NLS-1$
				offsets[VARS_INDEX] = i++;
			} else if ("SV".equals(token)) { //$NON-NLS-1$
				offsets[STATIC_VARS_INDEX] = i++;
			} else if ("SF".equals(token)) { //$NON-NLS-1$
				offsets[STATIC_FUNCTIONS_INDEX] = i++;
			} else if ("C".equals(token)) { //$NON-NLS-1$
				offsets[CONSTRUCTORS_INDEX] = i++;
			} else if ("S".equals(token)) { //$NON-NLS-1$
				offsets[CONSTANTS_INDEX] = i++;
			}
		}
		return i == N_CATEGORIES;
	}

	public boolean isSortByVisibility() {
		return fSortByVisibility;
	}

	public int getVisibilityIndex(int modifierFlags) {
		if (fVisibilityOffsets == null) {
			fVisibilityOffsets = getVisibilityOffsets();
		}
		int kind = DEFAULT_INDEX;
		if (PHPModifier.isPublic(modifierFlags)) {
			kind = PUBLIC_INDEX;
		} else if (PHPModifier.isProtected(modifierFlags)) {
			kind = PROTECTED_INDEX;
		} else if (PHPModifier.isPrivate(modifierFlags)) {
			kind = PRIVATE_INDEX;
		}

		return fVisibilityOffsets[kind];
	}

	private int[] getVisibilityOffsets() {
		int[] offsets = new int[N_VISIBILITIES];
		IPreferenceStore store = PreferenceConstants.getPreferenceStore();
		String key = PreferenceConstants.APPEARANCE_VISIBILITY_SORT_ORDER;
		boolean success = fillVisibilityOffsetsFromPreferenceString(store.getString(key), offsets);
		if (!success) {
			store.setToDefault(key);
			fillVisibilityOffsetsFromPreferenceString(store.getDefaultString(key), offsets);
		}
		return offsets;
	}

	private boolean fillVisibilityOffsetsFromPreferenceString(String str, int[] offsets) {
		StringTokenizer tokenizer = new StringTokenizer(str, ","); //$NON-NLS-1$
		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().trim();
			if ("B".equals(token)) { //$NON-NLS-1$
				offsets[PUBLIC_INDEX] = i++;
			} else if ("V".equals(token)) { //$NON-NLS-1$
				offsets[PRIVATE_INDEX] = i++;
			} else if ("R".equals(token)) { //$NON-NLS-1$
				offsets[PROTECTED_INDEX] = i++;
			} else if ("D".equals(token)) { //$NON-NLS-1$
				offsets[DEFAULT_INDEX] = i++;
			}
		}
		return i == N_VISIBILITIES;
	}

}
