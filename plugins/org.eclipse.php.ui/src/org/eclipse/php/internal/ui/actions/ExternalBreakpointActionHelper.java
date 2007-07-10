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
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;

/**
 * A helper class for all the actions that deals with setting and managing
 * breakpoints on external files.
 * 
 * @author shalom
 * 
 */
public class ExternalBreakpointActionHelper {

	/**
	 * Returns if there are markers which include the ruler's line of activity.
	 * 
	 * @param resource
	 * @param document
	 * @param annotationModel
	 * @param rulerInfo
	 * @return
	 */
	public static boolean hasMarkers(IResource resource, IDocument document, AbstractMarkerAnnotationModel annotationModel, IVerticalRulerInfo rulerInfo) {
		if (resource != null && annotationModel != null) {
			try {
				IMarker[] allMarkers;
				if (resource.exists()) {
					allMarkers = resource.findMarkers(IBreakpoint.LINE_BREAKPOINT_MARKER, true, IResource.DEPTH_ZERO);
					if (allMarkers != null) {
						for (int i = 0; i < allMarkers.length; i++) {
							if (includesRulerLine(annotationModel.getMarkerPosition(allMarkers[i]), document, rulerInfo)) {
								return true;
							}
						}
					}
				} else {
					// get it from the workspace root
					allMarkers = resource.getWorkspace().getRoot().findMarkers(IBreakpoint.LINE_BREAKPOINT_MARKER, true, IResource.DEPTH_ZERO);
					IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
					if (allMarkers != null) {
						for (int i = 0; i < allMarkers.length; i++) {
							if (manager.getBreakpoint(allMarkers[i]) != null && includesRulerLine(annotationModel.getMarkerPosition(allMarkers[i]), document, rulerInfo)) {
								return true;
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
	 * @param resource
	 * @param document
	 * @param annotationModel
	 * @param rulerInfo
	 * @return
	 */
	public static IMarker[] getMarkers(IResource resource, IDocument document, AbstractMarkerAnnotationModel annotationModel, IVerticalRulerInfo rulerInfo) {
		List<IMarker> markers = new ArrayList<IMarker>();
		if (resource != null && annotationModel != null) {
			try {

				IMarker[] allMarkers;
				if (resource.exists()) {
					allMarkers = resource.findMarkers(IBreakpoint.BREAKPOINT_MARKER, true, IResource.DEPTH_ZERO);
					if (allMarkers != null) {
						for (int i = 0; i < allMarkers.length; i++) {
							if (includesRulerLine(annotationModel.getMarkerPosition(allMarkers[i]), document, rulerInfo)) {
								markers.add(allMarkers[i]);
							}
						}
					}
				} else {
					allMarkers = resource.getWorkspace().getRoot().findMarkers(IBreakpoint.BREAKPOINT_MARKER, true, IResource.DEPTH_ZERO);
					if (allMarkers != null) {
						IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
						for (int i = 0; i < allMarkers.length; i++) {
							if (manager.getBreakpoint(allMarkers[i]) != null && includesRulerLine(annotationModel.getMarkerPosition(allMarkers[i]), document, rulerInfo)) {
								markers.add(allMarkers[i]);
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
	private static boolean includesRulerLine(Position position, IDocument document, IVerticalRulerInfo rulerInfo) {
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
