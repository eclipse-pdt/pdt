/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.swt.graphics.Image;

/**
 * @author Dawid zulus Pakula <zulus@w3des.net>
 * @since 3.3
 */
public class PHPScriptElementLabelProvider extends LabelProvider implements ILabelProvider {
	@Override
	public Image getImage(Object o) {
		try {
			if (o instanceof IType) {
				if (PHPFlags.isTrait(((IType) o).getFlags())) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_TRAIT);
				} else if (PHPFlags.isEnum(((IType) o).getFlags())) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_ENUM);
				}
			}
		} catch (ModelException e) {
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		return null;
	}
}
