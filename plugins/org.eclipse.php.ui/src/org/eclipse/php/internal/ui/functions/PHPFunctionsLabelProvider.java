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
package org.eclipse.php.internal.ui.functions;

import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for the PHP Functions view. Inherits all its behavior from the
 * ScriptUILabelProvider but adds special handling for the Constants Node
 * 
 * @author Eden K., 2008
 * 
 */
public class PHPFunctionsLabelProvider extends ScriptUILabelProvider {

	/**
	 * Creates a new label provider with default flags.
	 */
	public PHPFunctionsLabelProvider() {
		this(ScriptElementLabels.ALL_DEFAULT,
				ScriptElementImageProvider.OVERLAY_ICONS);
	}

	/**
	 * @param textFlags
	 *            Flags defined in <code>ScriptElementLabels</code>.
	 * @param imageFlags
	 *            Flags defined in <code>ScriptElementImageProvider</code>.
	 */
	public PHPFunctionsLabelProvider(long textFlags, int imageFlags) {
		super(textFlags, imageFlags);
		fImageLabelProvider = new ScriptElementImageProvider();
	}

	public String getText(Object element) {
		if (element instanceof ConstantNode) {
			return ((ConstantNode) element).getName();
		}
		return super.getText(element);
	}

	public Image getImage(Object element) {
		if (element instanceof ConstantNode) {
			ImageDescriptor descriptor = new ScriptElementImageDescriptor(
					PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP, 0,
					ScriptElementImageProvider.BIG_SIZE);
			return DLTKUIPlugin.getImageDescriptorRegistry().get(descriptor);
		} else {
			return super.getImage(element);
		}
	}

}
