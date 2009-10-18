/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

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

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

public class Resources {

	private Resources() {
	}

	/**
	 * This method is used to generate a list of local locations to be used in
	 * DnD for file transfers.
	 * 
	 * @param resources
	 *            the array of resources to get the local locations for
	 * @return the local locations
	 */
	public static String[] getLocationOSStrings(IResource[] resources) {
		List result = new ArrayList(resources.length);
		for (int i = 0; i < resources.length; i++) {
			IPath location = resources[i].getLocation();
			if (location != null)
				result.add(location.toOSString());
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	/**
	 * Returns the location of the given resource. For local resources this is
	 * the OS path in the local file system. For remote resource this is the
	 * URI.
	 * 
	 * @param resource
	 *            the resource
	 * @return the location string or <code>null</code> if the location URI of
	 *         the resource is <code>null</code>
	 */
	public static String getLocationString(IResource resource) {
		URI uri = resource.getLocationURI();
		if (uri == null)
			return null;
		return EFS.SCHEME_FILE.equalsIgnoreCase(uri.getScheme()) ? new File(uri)
				.getAbsolutePath()
				: uri.toString();
	}

}
