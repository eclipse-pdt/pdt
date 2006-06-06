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

import java.util.ArrayList;

import org.eclipse.core.resources.IStorage;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class PHPUILabelProvider extends LabelProvider implements IColorProvider {

	protected PHPElementImageProvider fImageLabelProvider;
	protected StorageLabelProvider fStorageLabelProvider;

	private ArrayList fLabelDecorators;

	private int fImageFlags;
	private int fTextFlags;

	IPHPTreeContentProvider []treeProviders;
	
	/**
	 * Creates a new label provider with default flags.
	 */
	public PHPUILabelProvider() {
		this(PHPElementLabels.M_PARAMETER_TYPES | PHPElementLabels.M_PARAMETER_NAMES, PHPElementImageProvider.OVERLAY_ICONS |  PHPElementImageProvider.SMALL_ICONS);
	}

	/**
	 * @param textFlags Flags defined in <code>PHPElementLabels</code>.
	 * @param imageFlags Flags defined in <code>PHPElementImageProvider</code>.
	 */
	public PHPUILabelProvider(int textFlags, int imageFlags) {
		fImageLabelProvider = new PHPElementImageProvider();
		fLabelDecorators = null;

		fStorageLabelProvider = new StorageLabelProvider();
		fImageFlags = imageFlags;
		fTextFlags = textFlags;
	}

	
	public void setTreeProviders(IPHPTreeContentProvider []providers)
	{
		this.treeProviders=providers;
	}
	
	/**
	 * Adds a decorator to the label provider
	 */
	public void addLabelDecorator(ILabelDecorator decorator) {
		if (fLabelDecorators == null) {
			fLabelDecorators = new ArrayList(2);
		}
		fLabelDecorators.add(decorator);
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

	protected Image decorateImage(Image image, Object element) {
		if (fLabelDecorators != null && image != null) {
			for (int i = 0; i < fLabelDecorators.size(); i++) {
				ILabelDecorator decorator = (ILabelDecorator) fLabelDecorators.get(i);
				image = decorator.decorateImage(image, element);
			}
		}
		return image;
	}

	public Image getImage(Object element) {
		Image result = fImageLabelProvider.getImageLabel(element, evaluateImageFlags(element));
		if (result==null && treeProviders!=null)
		{
			for (int i = 0; i < treeProviders.length&&result==null; i++) {
				result=treeProviders[i].getImage(element);
			}
		}
		if (result == null && (element instanceof IStorage)) {
			result = fStorageLabelProvider.getImage(element);
		}

		return decorateImage(result, element);
	}

	protected String decorateText(String text, Object element) {
		if (fLabelDecorators != null && text.length() > 0) {
			for (int i = 0; i < fLabelDecorators.size(); i++) {
				ILabelDecorator decorator = (ILabelDecorator) fLabelDecorators.get(i);
				text = decorator.decorateText(text, element);
			}
		}
		return text;
	}

	public String getText(Object element) {
		String result = PHPElementLabels.getTextLabel(element, evaluateTextFlags(element));
		if (result.length() == 0 && treeProviders!=null)
		{
			for (int i = 0; i < treeProviders.length; i++) {
				result=treeProviders[i].getText(element);
				if (result!=null && result.length()>0)
					break;
			}
			if (result==null)
				result="";
		}
		if (result.length() == 0 && (element instanceof IStorage)) {
			result = fStorageLabelProvider.getText(element);
		}

		return decorateText(result, element);
	}

	public void dispose() {
		if (fLabelDecorators != null) {
			for (int i = 0; i < fLabelDecorators.size(); i++) {
				ILabelDecorator decorator = (ILabelDecorator) fLabelDecorators.get(i);
				decorator.dispose();
			}
			fLabelDecorators = null;
		}
		fStorageLabelProvider.dispose();
		fImageLabelProvider.dispose();
	}

	public void addListener(ILabelProviderListener listener) {
		if (fLabelDecorators != null) {
			for (int i = 0; i < fLabelDecorators.size(); i++) {
				ILabelDecorator decorator = (ILabelDecorator) fLabelDecorators.get(i);
				decorator.addListener(listener);
			}
		}
		super.addListener(listener);
	}

	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	public void removeListener(ILabelProviderListener listener) {
		if (fLabelDecorators != null) {
			for (int i = 0; i < fLabelDecorators.size(); i++) {
				ILabelDecorator decorator = (ILabelDecorator) fLabelDecorators.get(i);
				decorator.removeListener(listener);
			}
		}
		super.removeListener(listener);
	}

	public static ILabelDecorator[] getDecorators(boolean errortick, ILabelDecorator extra) {
		if (errortick) {
			if (extra == null) {
				return new ILabelDecorator[] {};
			} else {
				return new ILabelDecorator[] { extra };
			}
		}
		if (extra != null) {
			return new ILabelDecorator[] { extra };
		}
		return null;
	}

	public Color getForeground(Object element) {
		return null;
	}

	public Color getBackground(Object element) {
		return null;
	}

}
