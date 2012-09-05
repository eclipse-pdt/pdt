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
package org.eclipse.php.internal.ui.outline;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * 
 * Provisional API: This class/interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is being made available at this early stage to solicit feedback
 * from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 */
public class PHPOutlineLabelProvider extends XMLLabelProvider {

	private ILabelProvider modelElementLabelProvider;

	public PHPOutlineLabelProvider(ILabelProvider modelElementLabelProvider) {
		this.modelElementLabelProvider = modelElementLabelProvider;
	}

	public Image getImage(Object o) {

		try {
			if (o instanceof IType && PHPFlags.isTrait(((IType) o).getFlags())) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_TRAIT);
			}
		} catch (ModelException e) {
		}
		if (o instanceof IModelElement) {
			return modelElementLabelProvider.getImage(o);
		}
		return super.getImage(o);
	}

	public String getText(Object o) {
		if (o instanceof IModelElement) {
			return modelElementLabelProvider.getText(o);
		}
		return super.getText(o);
	}
}