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
package org.eclipse.php.internal.ui.util;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.internal.core.phpModel.parser.FolderFilteredUserModel;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class PHPUILabelProvider extends LabelProvider implements IColorProvider {

	protected PHPElementImageProvider fImageLabelProvider;
	protected StorageLabelProvider fStorageLabelProvider;

	private int fImageFlags;
	private int fTextFlags;

	IPHPTreeContentProvider[] treeProviders;

	/**
	 * Creates a new label provider with default flags.
	 */
	public PHPUILabelProvider() {
		this(PHPElementLabels.M_PARAMETER_TYPES | PHPElementLabels.M_PARAMETER_NAMES, PHPElementImageProvider.OVERLAY_ICONS | PHPElementImageProvider.SMALL_ICONS | PHPElementLabels.M_APP_RETURNTYPE);
	}

	/**
	 * @param textFlags Flags defined in <code>PHPElementLabels</code>.
	 * @param imageFlags Flags defined in <code>PHPElementImageProvider</code>.
	 */
	public PHPUILabelProvider(int textFlags, int imageFlags) {
		fImageLabelProvider = new PHPElementImageProvider();

		fStorageLabelProvider = new StorageLabelProvider();
		fImageFlags = imageFlags;
		fTextFlags = textFlags;

	}

	public void setTreeProviders(IPHPTreeContentProvider[] providers) {
		this.treeProviders = providers;
	}

	/**
	 * Sets the textFlags.
	 * @param textFlags The textFlags to set
	 */
	public final void setTextFlags(int textFlags) {
		fTextFlags = textFlags;
	}

	/**
	 * Sets the imageFlags
	 * @param imageFlags The imageFlags to set
	 */
	public final void setImageFlags(int imageFlags) {
		fImageFlags = imageFlags;
	}

	/**
	 * Gets the image flags.
	 * Can be overwriten by super classes.
	 * @return Returns a int
	 */
	public final int getImageFlags() {
		return fImageFlags;
	}

	/**
	 * Gets the text flags.
	 * @return Returns a int
	 */
	public final int getTextFlags() {
		return fTextFlags;
	}

	/**
	 * Evaluates the image flags for a element.
	 * Can be overwriten by super classes.
	 * @return Returns a int
	 */
	protected int evaluateImageFlags(Object element) {
		return getImageFlags();
	}

	/**
	 * Evaluates the text flags for a element. Can be overwriten by super classes.
	 * @return Returns a int
	 */
	protected int evaluateTextFlags(Object element) {
		return getTextFlags();
	}

	public Image getImage(Object element) {
		Image result = fImageLabelProvider.getImageLabel(element, evaluateImageFlags(element));
		if (result == null && treeProviders != null) {
			for (int i = 0; i < treeProviders.length && result == null; i++) {
				result = treeProviders[i].getImage(element);
			}
		}
		if (result == null && element instanceof IStorage) {
			result = fStorageLabelProvider.getImage(element);
		}

		return result;
	}

	public String getText(Object element) {
		String result = PHPElementLabels.getTextLabel(element, evaluateTextFlags(element));
		if (result.length() != 0 && element instanceof FolderFilteredUserModel) {
			result = new Path(result).makeRelative().toString();
		}

		if (result.length() == 0 && treeProviders != null) {
			for (IPHPTreeContentProvider provider : treeProviders) {
				result = provider.getText(element);
				if (result != null && result.length() > 0)
					break;
			}
			if (result == null)
				result = ""; //$NON-NLS-1$
		}
		if (result.length() == 0 && element instanceof IStorage) {
			result = fStorageLabelProvider.getText(element);
		}
		return result;
	}

	public void dispose() {
		fStorageLabelProvider.dispose();
		fImageLabelProvider.dispose();
	}

	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	public Color getForeground(Object element) {
		return null;
	}

	public Color getBackground(Object element) {
		return null;
	}

}
