/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.breakpoint;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.texteditor.IAnnotationImageProvider;

/**
 * PHP exception breakpoint annotation image provider.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPExceptionBreakpointAnnotationImageProvider implements IAnnotationImageProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.IAnnotationImageProvider#getManagedImage(org.
	 * eclipse.jface.text.source.Annotation)
	 */
	@Override
	public Image getManagedImage(Annotation annotation) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.IAnnotationImageProvider#getImageDescriptorId(
	 * org.eclipse.jface.text.source.Annotation)
	 */
	@Override
	public String getImageDescriptorId(Annotation annotation) {
		switch (((PHPExceptionBreakpointAnnotation) annotation).getBreakpoint().getType()) {
		case ERROR:
			return PHPDebugUIImages.IMG_OBJ_ERROR_ANNOTATION;
		case EXCEPTION:
			return PHPDebugUIImages.IMG_OBJ_EXCEPTION_ANNOTATION;
		default:
			break;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.IAnnotationImageProvider#getImageDescriptor(
	 * java.lang.String)
	 */
	@Override
	public ImageDescriptor getImageDescriptor(String imageDescritporId) {
		return PHPDebugUIImages.getImageDescriptor(imageDescritporId);
	}

}
