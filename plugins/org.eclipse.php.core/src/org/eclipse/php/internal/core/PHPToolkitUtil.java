package org.eclipse.php.internal.core;

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

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ZipArchiveFile;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.phar.PharArchiveFile;
import org.eclipse.php.internal.core.phar.PharException;
import org.eclipse.php.internal.core.preferences.CorePreferencesSupport;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.tar.TarArchiveFile;
import org.eclipse.php.internal.core.tar.TarException;

public class PHPToolkitUtil {
	public static final String PHAR_EXTENSTION = "phar"; //$NON-NLS-1$
	public static final String TAR_EXTENSTION = "tar"; //$NON-NLS-1$
	public static final String BZ2_EXTENSTION = "bz2"; //$NON-NLS-1$
	public static final String GZ_EXTENSTION = "gz"; //$NON-NLS-1$
	public static final String ZIP_EXTENSTION = "zip"; //$NON-NLS-1$
	public static final String[] PHAR_EXTENSTIONS = new String[] {
			PHAR_EXTENSTION, TAR_EXTENSTION, BZ2_EXTENSTION, GZ_EXTENSTION,
			ZIP_EXTENSTION };

	public static boolean isPhpElement(final IModelElement modelElement) {
		Assert.isNotNull(modelElement);
		IModelElement sourceModule = modelElement
				.getAncestor(IModelElement.SOURCE_MODULE);
		if (sourceModule != null) {
			return isPhpFile((ISourceModule) sourceModule);
		}
		return false;
	}

	public static boolean isPhpFile(final ISourceModule sourceModule) {
		try {
			IResource resource = sourceModule.getCorrespondingResource();
			if (resource instanceof IFile) {
				if (isPhar(resource)) {
					return true;
				}
				IContentDescription contentDescription = ((IFile) resource)
						.getContentDescription();
				if (contentDescription != null) {
					return ContentTypeIdForPHP.ContentTypeID_PHP
							.equals(contentDescription.getContentType().getId());
				}
			}
		} catch (CoreException e) {
		}
		return hasPhpExtention(sourceModule.getElementName());
	}

	public static boolean isPhar(IResource resource) {
		if (resource instanceof IFile) {
			return isPharExtention(((IFile) resource).getFileExtension());
		}
		return false;
	}

	public static boolean isPharExtention(String extension) {
		for (int i = 0; i < PHAR_EXTENSTIONS.length; i++) {
			if (PHAR_EXTENSTIONS[i].equals(extension)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPharFileName(String fileName) {
		String extension = getExtention(fileName);
		return extension != null && isPharExtention(extension);
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

		return ContentTypeIdForPHP.ContentTypeID_PHP.equals(contentDescription
				.getContentType().getId());
	}

	public static boolean hasPhpExtention(final IFile file) {
		final String fileName = file.getName();
		String extension = getExtention(fileName);
		if (extension == null) {
			return false;
		}
		final IContentType type = Platform.getContentTypeManager()
				.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		final String[] validExtensions = type
				.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
		for (String validExtension : validExtensions) {
			if (extension.equalsIgnoreCase(validExtension)) {
				return true;
			}
		}

		return false;
	}

	private static String getExtention(String fileName) {
		final int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return null;
		}
		return fileName.substring(index + 1);
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

		final IContentType type = Platform.getContentTypeManager()
				.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		final String[] validExtensions = type
				.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
		for (String validExtension : validExtensions) {
			if (extension.equalsIgnoreCase(validExtension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks weather the given project is a php project
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @param element
	 * @return source module
	 */
	public static ISourceModule getSourceModule(String element) {
		IResource resource = ResourcesPlugin.getWorkspace().getRoot()
				.getFileForLocation(new Path(element));
		if (resource != null && resource instanceof IFile) {
			return (ISourceModule) DLTKCore.create((IFile) resource);
		}
		return null;
	}

	public static IArchive getArchive(File localFile) throws IOException {
		String extension = getExtension(localFile);
		if (isPharExtention(extension)) {
			IArchive archive = null;
			try {
				if (PHAR_EXTENSTION.equals(extension)) {
					archive = new PharArchiveFile(localFile);
				} else if (ZIP_EXTENSTION.equals(extension)) {
					archive = new ZipArchiveFile(localFile);
				} else if (TAR_EXTENSTION.equals(extension)
						|| GZ_EXTENSTION.equals(extension)
						|| BZ2_EXTENSTION.equals(extension)) {

					archive = new TarArchiveFile(localFile);
				}
			} catch (PharException e) {
				throw new IOException(e.getMessage());
			} catch (TarException e) {
				throw new IOException(e.getMessage());
			}
			return archive;
			// return getArchive(localFile,extension);
		}
		return null;
	}

	private static String getExtension(File localFile) {
		if (localFile.isFile()) {
			int index = localFile.getName().lastIndexOf("."); //$NON-NLS-1$
			if (localFile.getName().length() > index + 1) {
				String extension = localFile.getName().substring(index + 1);
				return extension;
			}

		}
		return null;
	}

	public static void setProjectVersion(IProject project) {
		String versionName = CorePreferencesSupport.getInstance()
				.getWorkspacePreferencesValue(
						PHPCoreConstants.PHP_OPTIONS_PHP_VERSION);

		PHPVersion version = PHPVersion.byAlias(versionName);
		if (version != null && ProjectOptions.getDefaultPhpVersion() != version) {
			ProjectOptions.setPhpVersion(version, project);
		}
	}

}
