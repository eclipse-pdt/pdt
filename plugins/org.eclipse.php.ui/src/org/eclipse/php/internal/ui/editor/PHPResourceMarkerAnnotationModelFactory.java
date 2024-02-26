/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.IAnnotationModelFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModelFactory;

/**
 * This class overrides the factory as an extension for WST extension point in
 * order to call explicitly to
 * org.eclipse.php.internal.ui.editor.PHPResourceMarkerAnnotationModel
 * .PHPResourceMarkerAnnotationModel which overrides a path comparison method.
 * It can be removed (including the plugin.xml extension) after bug #211733 is
 * resolved.
 * 
 * @author yaronm
 * 
 */
public class PHPResourceMarkerAnnotationModelFactory extends ResourceMarkerAnnotationModelFactory
		implements IAnnotationModelFactory {
	/*
	 * @see org.eclipse.core.filebuffers.IAnnotationModelFactory#
	 * createAnnotationModel (org.eclipse.core.runtime.IPath)
	 */
	@Override
	public IAnnotationModel createAnnotationModel(IPath location) {
		IAnnotationModel model = null;
		IFile file = FileBuffers.getWorkspaceFileAtLocation(location);
		if (file != null) {
			model = new PHPResourceMarkerAnnotationModel(file);
		}
		return model;
	}
}
