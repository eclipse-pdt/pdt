/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.explorer;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.composer.core.model.PackagePath;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

public class PackageTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof PackagePath) {
			PackagePath path = (PackagePath) element;
			return path.getPackageName();
		}

		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof PackagePath) {
			return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
		}

		return null;
	}
}
