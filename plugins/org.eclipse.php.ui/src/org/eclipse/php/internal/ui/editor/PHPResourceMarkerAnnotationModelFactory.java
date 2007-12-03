package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.IAnnotationModelFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModelFactory;

/**
 * This class overrides the factory as an extension for WST extension point
 * in order to call explicitly to org.eclipse.php.internal.ui.editor.PHPResourceMarkerAnnotationModel.PHPResourceMarkerAnnotationModel
 * which overrides a path comparison method.
 * It can be removed (including the plugin.xml extension) after bug #211733 is resolved.
 * @author yaronm
 *
 */
public class PHPResourceMarkerAnnotationModelFactory extends StructuredResourceMarkerAnnotationModelFactory implements IAnnotationModelFactory {
	/*
	 * @see org.eclipse.core.filebuffers.IAnnotationModelFactory#createAnnotationModel(org.eclipse.core.runtime.IPath)
	 */
	public IAnnotationModel createAnnotationModel(IPath location) {
		IAnnotationModel model = null;
		IFile file = FileBuffers.getWorkspaceFileAtLocation(location);
		if (file != null) {
			model = new PHPResourceMarkerAnnotationModel(file);
		} else {
			model = new PHPResourceMarkerAnnotationModel(ResourcesPlugin.getWorkspace().getRoot(), location.toString());
		}
		return model;
	}
}
