/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;

public class PHPModelUtil {

	
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
	
	
}
