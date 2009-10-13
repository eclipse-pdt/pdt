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
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.core.IExternalSourceModule;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

/**
 * A helper class for all the actions that deals with setting and managing
 * breakpoints on external files.
 * 
 * @author shalom
 * 
 */
public class ExternalBreakpointActionHelper {

	private static String getSecondaryId(ITextEditor textEditor) {
		String secondaryId = null;
		if (textEditor instanceof PHPStructuredEditor) {
			IModelElement modelElement = ((PHPStructuredEditor) textEditor)
					.getModelElement();
			if (modelElement instanceof IExternalSourceModule) {
				secondaryId = EnvironmentPathUtils.getFile(modelElement)
						.getFullPath().toString();
			}
		}
		return secondaryId;
	}

	/**
	 * Returns if there are markers which include the ruler's line of activity.
	 * 
	 * @param textEditor
	 * @param resource
	 * @param document
	 * @param annotationModel
	 * @param rulerInfo
	 * @return
	 */
	public static boolean hasMarkers(ITextEditor textEditor,
			IResource resource, IDocument document,
			AbstractMarkerAnnotationModel annotationModel,
			IVerticalRulerInfo rulerInfo) {

		if (resource != null && annotationModel != null) {
			try {
				IMarker[] allMarkers;
				if (resource instanceof IFile && resource.exists()) {
					allMarkers = resource.findMarkers(
							IBreakpoint.LINE_BREAKPOINT_MARKER, true,
							IResource.DEPTH_ZERO);
					if (allMarkers != null) {
						for (IMarker marker : allMarkers) {
							if (includesRulerLine(annotationModel
									.getMarkerPosition(marker), document,
									rulerInfo)) {
								return true;
							}
						}
					}
				} else {
					String secondaryId = getSecondaryId(textEditor);

					// get it from the workspace root
					allMarkers = resource.getWorkspace().getRoot().findMarkers(
							IBreakpoint.LINE_BREAKPOINT_MARKER, true,
							IResource.DEPTH_ZERO);
					IBreakpointManager manager = DebugPlugin.getDefault()
							.getBreakpointManager();
					if (allMarkers != null) {
						for (IMarker marker : allMarkers) {
							if (manager.getBreakpoint(marker) != null) {
								String markerSecondaryId = marker
										.getAttribute(
												StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY,
												null);
								if ((secondaryId == null || secondaryId
										.equals(markerSecondaryId))
										&& includesRulerLine(annotationModel
												.getMarkerPosition(marker),
												document, rulerInfo)) {
									return true;
								}
							}
						}
					}
				}
			} catch (CoreException x) {
			}
		}
		return false;
	}

	/**
	 * Returns all markers which include the ruler's line of activity.
	 * 
	 * @param textEditor
	 * @param resource
	 * @param document
	 * @param annotationModel
	 * @param rulerInfo
	 * @return
	 */
	public static IMarker[] getMarkers(ITextEditor textEditor,
			IResource resource, IDocument document,
			AbstractMarkerAnnotationModel annotationModel,
			IVerticalRulerInfo rulerInfo) {

		List<IMarker> markers = new ArrayList<IMarker>();
		if (resource != null && annotationModel != null) {
			try {
				IMarker[] allMarkers;
				if (resource instanceof IFile && resource.exists()) {
					allMarkers = resource.findMarkers(
							IBreakpoint.BREAKPOINT_MARKER, true,
							IResource.DEPTH_ZERO);
					if (allMarkers != null) {
						for (IMarker marker : allMarkers) {
							if (includesRulerLine(annotationModel
									.getMarkerPosition(marker), document,
									rulerInfo)) {
								markers.add(marker);
							}
						}
					}
				} else {
					String secondaryId = getSecondaryId(textEditor);
					allMarkers = resource.getWorkspace().getRoot().findMarkers(
							IBreakpoint.BREAKPOINT_MARKER, true,
							IResource.DEPTH_ZERO);
					if (allMarkers != null) {
						IBreakpointManager manager = DebugPlugin.getDefault()
								.getBreakpointManager();
						for (IMarker marker : allMarkers) {
							if (manager.getBreakpoint(marker) != null) {
								String markerSecondaryId = marker
										.getAttribute(
												StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY,
												null);
								if ((secondaryId == null || secondaryId
										.equals(markerSecondaryId))
										&& includesRulerLine(annotationModel
												.getMarkerPosition(marker),
												document, rulerInfo)) {
									markers.add(marker);
								}
							}
						}
					}
				}

			} catch (CoreException x) {
			}
		}
		return (IMarker[]) markers.toArray(new IMarker[0]);
	}

	/**
	 * Checks whether a position includes the ruler's line of activity.
	 * 
	 * @param position
	 *            the position to be checked
	 * @param document
	 *            the document the position refers to
	 * @return <code>true</code> if the line is included by the given position
	 */
	private static boolean includesRulerLine(Position position,
			IDocument document, IVerticalRulerInfo rulerInfo) {
		if (position != null && rulerInfo != null) {
			try {
				int markerLine = document.getLineOfOffset(position.getOffset());
				int line = rulerInfo.getLineOfLastMouseButtonActivity();
				if (line == markerLine)
					return true;
				// commented because of "1GEUOZ9: ITPJUI:ALL - Confusing UI
				// for
				// multiline Bookmarks and Tasks"
				// return (markerLine <= line && line <=
				// document.getLineOfOffset(position.getOffset() +
				// position.getLength()));
			} catch (BadLocationException x) {
			}
		}
		return false;
	}
}
