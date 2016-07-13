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
			if (o instanceof IType && PHPFlags.isTrait(((IType) o).getFlags())) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_TRAIT);
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
