/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dawid Pakuła - Port two methods from org.eclipse.ui.texteditor.MarkerUtilities
 *******************************************************************************/
package org.eclipse.php.formatter.core;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class MarkerUtilities {
	/**
	 * Returns the marker type of the given marker or <code>null</code> if the
	 * type could not be determined.
	 *
	 * @param marker
	 *            the marker
	 * @return the marker type
	 */
	public static String getMarkerType(IMarker marker) {

		try {
			return marker.getType();
		} catch (CoreException e) {
			Logger.logException(e);
			return null;
		}
	}

	/**
	 * Creates a marker on the given resource with the given type and
	 * attributes.
	 * <p>
	 * This method modifies the workspace (progress is not reported to the
	 * user).
	 * </p>
	 *
	 * @param resource
	 *            the resource
	 * @param attributes
	 *            the attribute map (key type: <code>String</code>, value type:
	 *            <code>Object</code>)
	 * @param markerType
	 *            the type of marker
	 * @throws CoreException
	 *             if this method fails
	 * @see IResource#createMarker(java.lang.String)
	 */
	public static void createMarker(final IResource resource, final Map<String, ? extends Object> attributes,
			final String markerType) throws CoreException {

		IWorkspaceRunnable r = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker = resource.createMarker(markerType);
				marker.setAttributes(attributes);
			}
		};

		resource.getWorkspace().run(r, null, IWorkspace.AVOID_UPDATE, null);
	}
}
