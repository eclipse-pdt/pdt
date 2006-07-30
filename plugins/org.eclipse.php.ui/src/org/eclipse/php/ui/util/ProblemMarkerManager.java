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
package org.eclipse.php.ui.util;

import java.util.HashSet;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.source.AnnotationModelEvent;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelListener;
import org.eclipse.jface.text.source.IAnnotationModelListenerExtension;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Display;

;

/**
 * Listens to resource deltas and filters for marker changes of type IMarker.PROBLEM
 * Viewers showing error ticks should register as listener to
 * this type.
 */
public class ProblemMarkerManager implements IResourceChangeListener, IAnnotationModelListener, IAnnotationModelListenerExtension {

	/**
	 * Visitors used to look if the element change delta containes a marker change.
	 */
	private static class ProjectErrorVisitor implements IResourceDeltaVisitor {

		private HashSet fChangedElements;

		public ProjectErrorVisitor(final HashSet changedElements) {
			fChangedElements = changedElements;
		}

		private void checkInvalidate(final IResourceDelta delta, IResource resource) {
			final int kind = delta.getKind();
			if (kind == IResourceDelta.REMOVED || kind == IResourceDelta.ADDED || kind == IResourceDelta.CHANGED && isErrorDelta(delta))
				// invalidate the resource and all parents
				while (resource.getType() != IResource.ROOT && fChangedElements.add(resource))
					resource = resource.getParent();
		}

		private boolean isErrorDelta(final IResourceDelta delta) {
			if ((delta.getFlags() & IResourceDelta.MARKERS) != 0) {
				final IMarkerDelta[] markerDeltas = delta.getMarkerDeltas();
				for (int i = 0; i < markerDeltas.length; i++)
					if (markerDeltas[i].isSubtypeOf(IMarker.PROBLEM)) {
						final int kind = markerDeltas[i].getKind();
						if (kind == IResourceDelta.ADDED || kind == IResourceDelta.REMOVED)
							return true;
						final int severity = markerDeltas[i].getAttribute(IMarker.SEVERITY, -1);
						final int newSeverity = markerDeltas[i].getMarker().getAttribute(IMarker.SEVERITY, -1);
						if (newSeverity != severity)
							return true;
					}
			}
			return false;
		}

		public boolean visit(final IResourceDelta delta) {
			final IResource res = delta.getResource();
			if (res instanceof IProject && delta.getKind() == IResourceDelta.CHANGED) {
				final IProject project = (IProject) res;
				if (!project.isAccessible())
					// only track open PHP projects
					return false;
			}
			checkInvalidate(delta, res);
			return true;
		}
	}

	private ListenerList fListeners;

	public ProblemMarkerManager() {
		fListeners = new ListenerList(10);
	}

	/**
	 * Adds a listener for problem marker changes.
	 */
	public void addListener(final IProblemChangedListener listener) {
		if (fListeners.isEmpty())
			PHPUiPlugin.getWorkspace().addResourceChangeListener(this);
		//			PHPUiPlugin.getDefault().getCompilationUnitDocumentProvider().addGlobalAnnotationModelListener(this);
		fListeners.add(listener);
	}

	private void fireChanges(final IResource[] changes, final boolean isMarkerChange) {
		final Display display = SWTUtil.getStandardDisplay();
		if (display != null && !display.isDisposed())
			display.asyncExec(new Runnable() {
				public void run() {
					final Object[] listeners = fListeners.getListeners();
					for (int i = 0; i < listeners.length; i++) {
						final IProblemChangedListener curr = (IProblemChangedListener) listeners[i];
						curr.problemsChanged(changes, isMarkerChange);
					}
				}
			});
	}

	/* (non-Javadoc)
	 * @see IAnnotationModelListenerExtension#modelChanged(AnnotationModelEvent)
	 */
	public void modelChanged(final AnnotationModelEvent event) {
	}

	/* (non-Javadoc)
	 * @see IAnnotationModelListener#modelChanged(IAnnotationModel)
	 */
	public void modelChanged(final IAnnotationModel model) {
		// no action
	}

	/**
	 * Removes a <code>IProblemChangedListener</code>.
	 */
	public void removeListener(final IProblemChangedListener listener) {
		fListeners.remove(listener);
		if (fListeners.isEmpty())
			PHPUiPlugin.getWorkspace().removeResourceChangeListener(this);
		//			PHPUiPlugin.getDefault().getCompilationUnitDocumentProvider().removeGlobalAnnotationModelListener(this);
	}

	/*
	 * @see IResourceChangeListener#resourceChanged
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		final HashSet changedElements = new HashSet();

		try {
			final IResourceDelta delta = event.getDelta();
			if (delta != null)
				delta.accept(new ProjectErrorVisitor(changedElements));
		} catch (final CoreException e) {
			PHPUiPlugin.log(e.getStatus());
		}

		if (!changedElements.isEmpty()) {
			final IResource[] changes = (IResource[]) changedElements.toArray(new IResource[changedElements.size()]);
			fireChanges(changes, true);
		}
	}

}
