/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.search.TypeNameMatch;
import org.eclipse.dltk.internal.ui.util.TypeNameMatchLabelProvider;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for {@link TypeNameMatch} instances.
 */
public class PHPTypeNameMatchLabelProvider extends TypeNameMatchLabelProvider {

	private static final Image INTERFACE_ICON = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_INTERFACE);
	private static final Image TRAIT_ICON = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_TRAIT);

	public PHPTypeNameMatchLabelProvider(int flags, IDLTKUILanguageToolkit toolkit) {
		super(flags, toolkit);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof TypeNameMatch) {
			int modifiers = ((TypeNameMatch) element).getModifiers();
			if (Flags.isInterface(modifiers)) {
				return INTERFACE_ICON;
			} else if (PHPFlags.isTrait(modifiers)) {
				return TRAIT_ICON;
			}
		}
		return super.getImage(element);
	}
}
