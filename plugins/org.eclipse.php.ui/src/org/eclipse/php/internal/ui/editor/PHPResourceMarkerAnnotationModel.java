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
package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.breakpoint.IBreakpointConstants;

/**
 * Overrides class org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel
 * until WST will fix the path comparison problem (they don't compare segments but strings)
 * bug #211733 - when this bug is fixed we can remove this class and use the original.
 * @author yaronm
 *
 */
public class PHPResourceMarkerAnnotationModel extends StructuredResourceMarkerAnnotationModel {

	public PHPResourceMarkerAnnotationModel(IResource resource) {
		super(resource);
	}

	public PHPResourceMarkerAnnotationModel(IResource resource, String secondaryID) {
		super(resource, secondaryID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel#isAcceptable(org.eclipse.core.resources.IMarker)
	 */
	protected boolean isAcceptable(IMarker marker) {
		try {
			Object attr = marker.getAttribute(IBreakpointConstants.ATTR_HIDDEN);
			if (attr != null && ((Boolean) attr).equals(Boolean.TRUE))
				return false;
		} catch (CoreException e) {
			// ignore
		}

		if (fSecondaryMarkerAttributeValue == null)
			return super.isAcceptable(marker);
		String markerSecondaryMarkerAttributeValue = marker.getAttribute(SECONDARY_ID_KEY, ""); //$NON-NLS-1$
		boolean isSameFile = new Path(fSecondaryMarkerAttributeValue).equals(new Path(markerSecondaryMarkerAttributeValue));

		return marker != null && getResource().equals(marker.getResource()) && isSameFile;
	}
}
