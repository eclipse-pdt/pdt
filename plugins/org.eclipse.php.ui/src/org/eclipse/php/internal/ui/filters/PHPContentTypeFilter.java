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
package org.eclipse.php.internal.ui.filters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filters out all compilation units and class files elements.
 */
public class PHPContentTypeFilter extends ViewerFilter implements
		IExecutableExtension {

	private static final String ORG_ECLIPSE_WST_HTML_CORE_HTMLSOURCE = "org.eclipse.wst.html.core.htmlsource"; //$NON-NLS-1$
	IContentType contentType;

	public PHPContentTypeFilter() {
		super();
		contentType = Platform.getContentTypeManager().getContentType(
				ORG_ECLIPSE_WST_HTML_CORE_HTMLSOURCE);
	}

	/**
	 * Returns the result of this filter, when applied to the given inputs.
	 * 
	 * @return Returns true if element should be included in filtered set
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof IFile) {
			IFile file = (IFile) element;
			if (contentType.isAssociatedWith(file.getName()))
				return false;

		}

		return true;
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		String pattern = config.getAttribute("pattern"); //$NON-NLS-1$
		if (pattern != null) {
			IContentType newcontentType = Platform.getContentTypeManager()
					.getContentType(pattern);
			if (newcontentType != null)
				contentType = newcontentType;
		}
	}

}
