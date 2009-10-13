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
package org.eclipse.php.internal.debug.core.sourcelookup.containers;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.CompositeSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.DirectorySourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.ExternalArchiveSourceContainer;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;

public class PHPVariableSourceContainer extends CompositeSourceContainer {

	private IPath fPath;
	private String[] validExtensions;

	public PHPVariableSourceContainer(IPath path) {
		fPath = path;
		updateExtentionList();
	}

	protected ISourceContainer[] createSourceContainers() throws CoreException {
		IPath path = DLTKCore.getResolvedVariablePath(fPath);
		File file = path.toFile();
		ISourceContainer[] container = new ISourceContainer[1];
		if (file.isDirectory()) {
			container[0] = new DirectorySourceContainer(file, false);
		} else {
			String fileName = file.getName();
			if (fileName.toLowerCase().endsWith(".zip")) { //$NON-NLS-1$
				container[0] = new ExternalArchiveSourceContainer(file
						.getPath(), false);
			} else if (isPhpFile(fileName)) {
				container[0] = new PHPFileSourceContainer(file);
			}
		}
		return container;
	}

	public String getName() {
		return fPath.toString();
	}

	public ISourceContainerType getType() {
		return null;
	}

	private boolean isPhpFile(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return false;
		}
		String ext = fileName.substring(index + 1);
		for (int i = 0; i < validExtensions.length; i++) {
			if (ext.equals(validExtensions[i])) {
				return true;
			}
		}
		return false;
	}

	private void updateExtentionList() {
		IContentType type = Platform.getContentTypeManager().getContentType(
				ContentTypeIdForPHP.ContentTypeID_PHP);
		validExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
	}
}
