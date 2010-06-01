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
package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.internal.ui.editor.SourceModuleDocumentProvider.SourceModuleAnnotationModel;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationPresentation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.MarkerAnnotation;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.wst.sse.ui.internal.Logger;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.breakpoint.IBreakpointConstants;
import org.eclipse.wst.sse.ui.internal.reconcile.TemporaryAnnotation;

/**
 * Overrides class
 * org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel until
 * WST will fix the path comparison problem (they don't compare segments but
 * strings) bug #211733 - when this bug is fixed we can remove this class and
 * use the original.
 * 
 * @author yaronm
 * 
 */
public class PHPResourceMarkerAnnotationModel extends
		SourceModuleAnnotationModel {

	public PHPResourceMarkerAnnotationModel(IResource resource) {
		super(resource);
		fMarkerResource = resource;
	}

	public PHPResourceMarkerAnnotationModel(IResource resource,
			String secondaryID) {
		super(resource);
		fMarkerResource = resource;
		fSecondaryMarkerAttributeValue = secondaryID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel#isAcceptable(
	 * org.eclipse.core.resources.IMarker)
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

		String secondaryId = marker.getAttribute(SECONDARY_ID_KEY, ""); //$NON-NLS-1$
		IPath path = Path.fromPortableString(secondaryId);
		path = EnvironmentPathUtils.getLocalPath(path);

		boolean isSameFile = Path.fromPortableString(
				fSecondaryMarkerAttributeValue).equals(path);

		return marker != null && getResource().equals(marker.getResource())
				&& isSameFile;
	}

	public final static String SECONDARY_ID_KEY = IBreakpointConstants.RESOURCE_PATH;

	protected IResource fMarkerResource;
	protected String fSecondaryMarkerAttributeValue;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.texteditor.AbstractMarkerAnnotationModel#
	 * createMarkerAnnotation(org.eclipse.core.resources.IMarker)
	 */
	protected MarkerAnnotation createMarkerAnnotation(IMarker marker) {
		/*
		 * We need to do some special processing if marker is a validation (aka
		 * problem) marker or if marker is a breakpoint marker so create a
		 * special marker annotation for those markers. Otherwise, use default.
		 */
		if (MarkerUtilities.isMarkerType(marker, IMarker.PROBLEM)) {
			return new StructuredMarkerAnnotation(marker);
		}
		return super.createMarkerAnnotation(marker);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel#getMarkerPosition
	 * (org.eclipse.core.resources.IMarker)
	 */
	public Position getMarkerPosition(IMarker marker) {
		Position pos = super.getMarkerPosition(marker);

		// if ((pos == null || pos.getLength() == 0) && marker.getType() ==
		// IInternalDebugUIConstants.ANN_INSTR_POINTER_CURRENT) {
		if (pos == null || pos.getLength() == 0) {
			// We probably should create position from marker if marker
			// attributes specify a valid position
			pos = createPositionFromMarker(marker);
		}

		return pos;
	}

	public class StructuredMarkerAnnotation extends MarkerAnnotation implements
			IAnnotationPresentation {
		// controls if icon should be painted gray
		private boolean fIsGrayed = false;
		String fAnnotationType = null;

		StructuredMarkerAnnotation(IMarker marker) {
			super(marker);
		}

		public final String getAnnotationType() {
			return fAnnotationType;
		}

		/**
		 * Eventually will have to use IAnnotationPresentation &
		 * IAnnotationExtension
		 * 
		 * @see org.eclipse.ui.texteditor.MarkerAnnotation#getImage(org.eclipse.swt.widgets.Display)
		 */
		protected Image getImage(Display display) {
			Image image = null;
			if (fAnnotationType == TemporaryAnnotation.ANNOT_ERROR) {
				image = PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJS_ERROR_TSK);
			} else if (fAnnotationType == TemporaryAnnotation.ANNOT_WARNING) {
				image = PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJS_WARN_TSK);
			} else if (fAnnotationType == TemporaryAnnotation.ANNOT_INFO) {
				image = PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJS_INFO_TSK);
			}

			if (image != null && isGrayed())
				setImage(getGrayImage(display, image));
			else
				setImage(image);

			return super.getImage(display);
		}

		private Image getGrayImage(Display display, Image image) {
			if (image != null) {
				String key = Integer.toString(image.hashCode());
				// make sure we cache the gray image
				Image grayImage = JFaceResources.getImageRegistry().get(key);
				if (grayImage == null) {
					grayImage = new Image(display, image, SWT.IMAGE_GRAY);
					JFaceResources.getImageRegistry().put(key, grayImage);
				}
				image = grayImage;
			}
			return image;
		}

		public final boolean isGrayed() {
			return fIsGrayed;
		}

		public final void setGrayed(boolean grayed) {
			fIsGrayed = grayed;
		}

		/**
		 * Initializes the annotation's icon representation and its drawing
		 * layer based upon the properties of the underlying marker.
		 */
		protected void initAnnotationType() {

			IMarker marker = getMarker();
			fAnnotationType = TemporaryAnnotation.ANNOT_UNKNOWN;
			try {
				if (marker.isSubtypeOf(IMarker.PROBLEM)) {
					int severity = marker.getAttribute(IMarker.SEVERITY, -1);
					switch (severity) {
					case IMarker.SEVERITY_ERROR:
						fAnnotationType = TemporaryAnnotation.ANNOT_ERROR;
						break;
					case IMarker.SEVERITY_WARNING:
						fAnnotationType = TemporaryAnnotation.ANNOT_WARNING;
						break;
					case IMarker.SEVERITY_INFO:
						fAnnotationType = TemporaryAnnotation.ANNOT_INFO;
						break;
					}
				}

			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
	}

}
