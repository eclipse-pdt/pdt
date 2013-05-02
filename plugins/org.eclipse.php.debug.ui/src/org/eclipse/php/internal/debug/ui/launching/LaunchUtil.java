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
package org.eclipse.php.internal.debug.ui.launching;

import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;

public class LaunchUtil {

	public static final String ORG_ECLIPSE_WST_HTML_CORE_HTMLSOURCE = "org.eclipse.wst.html.core.htmlsource"; //$NON-NLS-1$

	public static String[] getRequiredNatures() {
		return new String[] { org.eclipse.php.internal.core.project.PHPNature.ID };
	}

	public static String[] getFileExtensions() {
		ArrayList extensions = new ArrayList();
		IContentTypeManager typeManager = Platform.getContentTypeManager();

		IContentType type = typeManager
				.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		String[] phpExtensions = type
				.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);

		IContentType htmlContentType = typeManager
				.getContentType(ORG_ECLIPSE_WST_HTML_CORE_HTMLSOURCE);
		String[] htmlExtensions = htmlContentType
				.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);

		if (phpExtensions != null)
			for (int i = 0; i < phpExtensions.length; i++)
				extensions.add(phpExtensions[i]);

		if (htmlExtensions != null)
			for (int i = 0; i < htmlExtensions.length; i++)
				extensions.add(htmlExtensions[i]);

		if (extensions.isEmpty())
			return null;

		return (String[]) extensions.toArray(new String[extensions.size()]);
	}
}
