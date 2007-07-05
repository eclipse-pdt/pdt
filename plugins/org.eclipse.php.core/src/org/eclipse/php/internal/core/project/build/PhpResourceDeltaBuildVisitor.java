/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.project.build;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.documentModel.validate.PHPProblemsValidator;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.PHPNature;

public class PhpResourceDeltaBuildVisitor implements IResourceDeltaVisitor {

	// used to examine if a file is php associated
	private static final IContentTypeManager CONTENT_TYPE_MANAGER = Platform.getContentTypeManager();

	private PHPProblemsValidator validator = PHPProblemsValidator.getInstance();

	public boolean visit(IResourceDelta delta) {
		switch (delta.getResource().getType()) {
			//only process files with PHP content type
			case IResource.FILE:
				processFileDelta(delta);
				return false;

				//only process projects with PHP nature
			case IResource.PROJECT:
				return processProjectDelta(delta);

			default:
				return true;
		}
	}

	private void processFileDelta(IResourceDelta fileDelta) {
		IFile file = (IFile) fileDelta.getResource();

		// if it is not an associated php file - don't validate
		final int numSegments = fileDelta.getFullPath().segmentCount();
		final String filename = fileDelta.getFullPath().segment(numSegments - 1);
		final IContentType contentType = CONTENT_TYPE_MANAGER.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		if (!contentType.isAssociatedWith(filename)) {
			return;
		}

		switch (fileDelta.getKind()) {
			case IResourceDelta.ADDED:
			case IResourceDelta.CHANGED:
				PHPWorkspaceModelManager.getInstance().addFileToModel(file);
				validator.validateFile(file);
				break;
			case IResourceDelta.REMOVED:
				PHPWorkspaceModelManager.getInstance().removeFileFromModel(file);
				// removed automatically from the validator, no need to enforce
				break;
		}
	}

	private boolean processProjectDelta(IResourceDelta projectDelta) {
		IProject project = (IProject) projectDelta.getResource();
		try {
			if (!project.hasNature(PHPNature.ID)) {
				return false;
			}
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
			return false;
		}

		return true;
	}
}
