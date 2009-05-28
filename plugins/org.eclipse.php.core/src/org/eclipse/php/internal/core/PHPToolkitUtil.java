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
package org.eclipse.php.internal.core;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.dltk.core.*;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPToolkitUtil {
	
	public static boolean isPhpElement(final IModelElement modelElement) {
		Assert.isNotNull(modelElement);
		IModelElement sourceModule = modelElement.getAncestor(IModelElement.SOURCE_MODULE);
		if (sourceModule != null) {
			return isPhpFile((ISourceModule) sourceModule);
		}
		return false;
	}

	public static boolean isPhpFile(final ISourceModule sourceModule) {
		try {
			IResource resource = sourceModule.getCorrespondingResource();
			if (resource instanceof IFile) {
				IContentDescription contentDescription = ((IFile)resource).getContentDescription();
				return ContentTypeIdForPHP.ContentTypeID_PHP.equals(contentDescription.getContentType().getId());
			}
		} catch (CoreException e) {
		}
		return hasPhpExtention(sourceModule.getElementName());
	}
	
	public static boolean isPhpFile(final IFile file) {
		IContentDescription contentDescription = null;
		if (!file.exists()) {
			return hasPhpExtention(file);
		}
		try {
			contentDescription = file.getContentDescription();
		} catch (final CoreException e) {
			return hasPhpExtention(file);
		}

		if (contentDescription == null) {
			return hasPhpExtention(file);
		}

		return ContentTypeIdForPHP.ContentTypeID_PHP.equals(contentDescription.getContentType().getId());
	}

	public static boolean hasPhpExtention(final IFile file) {
		final String fileName = file.getName();
		final int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return false;
		}
		String extension = fileName.substring(index + 1);
		final IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		final String[] validExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
		for (String validExtension : validExtensions) {
			if (extension.equalsIgnoreCase(validExtension)) {
				return true;
			}
		}

		return false;
	}

	public static boolean hasPhpExtention(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException();
		}
		
		final int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return false;
		}
		String extension = fileName.substring(index + 1);

		final IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		final String[] validExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
		for (String validExtension : validExtensions) {
			if (extension.equalsIgnoreCase(validExtension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks weather the given project is a php project 
	 * @param project
	 * @return true for php projects
	 * @throws CoreException
	 */
	public static boolean isPhpProject(IProject project) throws CoreException {
		if (project == null || !project.isAccessible()) {
			return false;
		}
		
		final IProjectNature nature = project.getNature(PHPNature.ID);
		return nature != null;
	}	
	
	/**
	 * Retrieves the source module related to the provide model element
	 * @param element
	 * @return the source module related to the provide model element
	 */
	public static final ISourceModule getSourceModule(Object element) {
		
		if (element instanceof IFile) {
			return (ISourceModule) DLTKCore.create((IFile) element);
		}
		
		if (element instanceof IModelElement) {
			return getSourceModule((IModelElement) element);
		}
		
		if (element instanceof String) {
			return getSourceModule((String) element);
		}

		return null;
	}

	/**
	 * retrieves the source module from a model element
	 * @param element
	 * @return source module 
	 */
	public static ISourceModule getSourceModule(IModelElement element) {
		IModelElement mElement = (IModelElement) element;
		
		if (mElement.getElementType() == IModelElement.SOURCE_MODULE) {
			return (ISourceModule) element;
		}
		
		if (element instanceof IMember) {
			return ((IMember) element).getSourceModule();
		}
		
		return null;
	}

	/**
	 * retrieves the source module from a path 
	 * @param element
	 * @return source module   
	 */
	public static ISourceModule getSourceModule(String element) {
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(element));
		if (resource != null && resource instanceof IFile) {
			return (ISourceModule) DLTKCore.create((IFile) resource);
		}
		return null;
	}
	
}
