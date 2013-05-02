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

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.dltk.core.AbstractLanguageToolkit;
import org.eclipse.dltk.core.IArchive;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IDLTKLanguageToolkitExtension;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPLanguageToolkit extends AbstractLanguageToolkit implements
		IDLTKLanguageToolkitExtension {

	private static PHPLanguageToolkit toolkit = new PHPLanguageToolkit();

	protected String getCorePluginID() {
		return PHPCorePlugin.ID;
	}

	public String[] getLanguageFileExtensions() {
		IContentType type = Platform.getContentTypeManager().getContentType(
				ContentTypeIdForPHP.ContentTypeID_PHP);
		return type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
	}

	public String getLanguageName() {
		return "PHP"; //$NON-NLS-1$
	}

	public String getNatureId() {
		return PHPNature.ID;
	}

	public String getLanguageContentType() {
		return ContentTypeIdForPHP.ContentTypeID_PHP;
	}

	public static IDLTKLanguageToolkit getDefault() {
		return toolkit;
	}

	// add by zhaozw
	public boolean languageSupportZIPBuildpath() {
		return true;
	}

	@Override
	public IArchive openArchive(File localFile) throws IOException {
		return PHPToolkitUtil.getArchive(localFile);
	}

	public boolean isArchiveFileName(String name) {
		return PHPToolkitUtil.isPharFileName(name);
	}
}
