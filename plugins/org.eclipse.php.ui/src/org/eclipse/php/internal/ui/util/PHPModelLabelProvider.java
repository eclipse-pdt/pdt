/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
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

import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * 
 * @author Dawid zulus Pakula <zulus@w3des.net>
 * @since 3.3
 */
public class PHPModelLabelProvider extends LabelProvider implements ILabelProvider {

	private ImageDescriptorRegistry fRegistry;
	final private int fImageFlags;
	final private long fTextFlags;
	private ScriptElementImageProvider fImageProvider;

	public static final long DEFAULT_TEXTFLAGS = ScriptElementLabels.ALL_DEFAULT;
	public static final int DEFAULT_IMAGEFLAGS = ScriptElementImageProvider.OVERLAY_ICONS
			| ScriptElementImageProvider.SMALL_ICONS;

	/**
	 * @param textFlags
	 *            Flags defined in {@link ScriptElementLabels}.
	 * @param imageFlags
	 *            Flags defined in {@link ScriptElementImageProvider}.
	 */
	public PHPModelLabelProvider(final long textFlags, final int imageFlags) {
		fImageFlags = imageFlags;
		fTextFlags = textFlags;
		fRegistry = null;
		fImageProvider = new ScriptElementImageProvider();
	}

	public PHPModelLabelProvider() {
		this(DEFAULT_TEXTFLAGS, DEFAULT_IMAGEFLAGS);
	}

	private ImageDescriptorRegistry getRegistry() {
		if (fRegistry == null)
			fRegistry = DLTKUIPlugin.getImageDescriptorRegistry();
		return fRegistry;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof IModelElement) {
			ImageDescriptor baseImage = getImageDescriptor((IModelElement) element, fImageFlags);
			if (baseImage != null) {
				return getRegistry().get(baseImage);
			}

		}
		return null;
	}

	public ImageDescriptor getImageDescriptor(IModelElement element, int imageFlags) {
		if (element == null) {
			return null;
		}
		ImageDescriptor baseImage = getBaseImageDescriptor(element, imageFlags);
		if (baseImage != null) {
			int adornmentFlags = getAdornmentFlags(element);
			Point size = ((imageFlags & ScriptElementImageProvider.SMALL_ICONS) != 0)
					? ScriptElementImageProvider.SMALL_SIZE : ScriptElementImageProvider.BIG_SIZE;
			return new PHPElementImageDescriptor(baseImage, adornmentFlags, size);
		}
		return null;
	}

	private ImageDescriptor getBaseImageDescriptor(IModelElement element, int imageFlags) {

		return fImageProvider.getBaseImageDescriptor(element, imageFlags);
	}

	private int getAdornmentFlags(IModelElement element) {
		int adornments = 0;
		int modifiers = 0;
		if (element instanceof IMember) {
			try {
				modifiers = ((IMember) element).getFlags();
			} catch (ModelException e) {
				if (e.isDoesNotExist()) {
					return modifiers;
				}
				Logger.logException(e);
			}
		}
		try {
			if (element.getElementType() == IModelElement.METHOD && ((IMethod) element).isConstructor())
				adornments |= PHPElementImageDescriptor.CONSTRUCTOR;
		} catch (ModelException e) {
			if (e.isDoesNotExist()) {
				return modifiers;
			}
			Logger.logException(e);
		}
		if (PHPFlags.isAbstract(modifiers))
			adornments |= PHPElementImageDescriptor.ABSTRACT;
		if (PHPFlags.isConstant(modifiers)) {
			adornments |= PHPElementImageDescriptor.CONSTANT;
		} else if (PHPFlags.isFinal(modifiers)) {
			adornments |= PHPElementImageDescriptor.FINAL;
		}
		if (PHPFlags.isStatic(modifiers))
			adornments |= PHPElementImageDescriptor.STATIC;
		if (PHPFlags.isDeprecated(modifiers))
			adornments |= PHPElementImageDescriptor.DEPRECATED;
		return adornments;
	}

	@Override
	public String getText(Object element) {
		// TODOw
		return null;
	}
}
