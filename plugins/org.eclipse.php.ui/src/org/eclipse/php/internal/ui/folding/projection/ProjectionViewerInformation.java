/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.folding.projection;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.internal.ui.Logger;

/**
 * Contains information about a projection viewer and also manages updating
 * the viewer's projection annotation model
 */
class ProjectionViewerInformation {
	// copies of this class located in:
	// org.eclipse.wst.xml.ui.internal.projection
	// org.eclipse.wst.css.ui.internal.projection
	// org.eclipse.wst.html.ui.internal.projection
	// org.eclipse.jst.jsp.ui.internal.projection

	/**
	 * Listens to document to be aware of when to update the projection
	 * annotation model.
	 */
	private class DocumentListener implements IDocumentListener {
		private ProjectionViewerInformation fInfo;

		public DocumentListener(ProjectionViewerInformation info) {
			fInfo = info;
		}

		public void documentAboutToBeChanged(DocumentEvent event) {
			IDocument document = event.getDocument();
			if (fInfo.getDocument() == document) {
				fInfo.setIsDocumentChanging(true);
			}
		}

		public void documentChanged(DocumentEvent event) {
			// register a post notification replace so that projection
			// annotation model will be updated after all documentChanged
			// listeners have been notified
			IDocument document = event.getDocument();
			if (document instanceof IDocumentExtension && fInfo.getDocument() == document) {
				//	if (fInfo.hasChangesQueued())
				// In WST, when they have no events they don't need to listen to post document change
				// We (PDT) need to listen in order to set document changing to false.
				// Otherwise, when an event from php model arrive, they can't update.
				((IDocumentExtension) document).registerPostNotificationReplace(this, new PostDocumentChangedListener(fInfo));
			}
		}
	}

	/**
	 * Essentially a post document changed listener because it is called after
	 * documentchanged has been fired.
	 */
	private static class PostDocumentChangedListener implements IDocumentExtension.IReplace {
		private ProjectionViewerInformation fInfo;

		public PostDocumentChangedListener(ProjectionViewerInformation info) {
			fInfo = info;
		}

		public void perform(IDocument document, IDocumentListener owner) {
			fInfo.applyAnnotationModelChanges();
			fInfo.setIsDocumentChanging(false);
		}
	}

	/**
	 * Projection annotation model current associated with this projection
	 * viewer
	 */
	private ProjectionAnnotationModel fProjectionAnnotationModel;
	/**
	 * Document currently associated with this projection viewer
	 */
	private IDocument fDocument;
	/**
	 * Listener to fProjectionViewer's document
	 */
	private IDocumentListener fDocumentListener;
	/**
	 * Indicates whether or not document is in the middle of changing
	 */
	private boolean fIsDocumentChanging = false;
	/**
	 * List of projection annotation model changes that need to be applied
	 */
	private List fQueuedAnnotationChanges;

	public ProjectionViewerInformation(ProjectionViewer viewer) {
		fDocument = viewer.getDocument();
		fProjectionAnnotationModel = viewer.getProjectionAnnotationModel();
	}

	IDocument getDocument() {
		return fDocument;
	}

	private List getQueuedAnnotationChanges() {
		if (fQueuedAnnotationChanges == null) {
			fQueuedAnnotationChanges = new LinkedList();
		}
		return fQueuedAnnotationChanges;
	}

	void setIsDocumentChanging(boolean changing) {
		fIsDocumentChanging = changing;
	}

	boolean isDocumentChanging() {
		return fIsDocumentChanging;
	}

	/**
	 * Applies the pending projection annotation model changes to the
	 * projection annotation model.
	 */
	void applyAnnotationModelChanges() {
		List queuedChanges = getQueuedAnnotationChanges();
		// go through all the pending annotation changes and apply
		// them to
		// the projection annotation model
		while (!queuedChanges.isEmpty()) {
			ProjectionAnnotationModelChanges changes = (ProjectionAnnotationModelChanges) queuedChanges.remove(0);
			try {
				fProjectionAnnotationModel.modifyAnnotations(changes.getDeletions(), changes.getAdditions(), changes.getModifications());
			} catch (RuntimeException e) {
				// if anything goes wrong, log it be continue
				Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
			}
		}
	}

	/**
	 * Returns true if there are annotation changes queued up, false otherwise
	 *
	 * @return boolean
	 */
	boolean hasChangesQueued() {
		return !getQueuedAnnotationChanges().isEmpty();
	}

	/**
	 * Updates projection annotation model if document is not in flux.
	 * Otherwise, queues up the changes to be applied when document is ready.
	 */
	public void queueAnnotationModelChanges(ProjectionAnnotationModelChanges newChange) {
		/*
		 * future_TODO: maybe improve by checking if annotation projection
		 * model change already exists for node. if so, throw out old change.
		 */
		getQueuedAnnotationChanges().add(newChange);

		// if document isn't changing, go ahead and apply it
		if (!isDocumentChanging()) {
			applyAnnotationModelChanges();
		}
	}

	public void initialize() {
		// add document listener
		if (fDocumentListener == null) {
			fDocumentListener = new DocumentListener(this);
		}
		getDocument().addDocumentListener(fDocumentListener);
	}

	public void dispose() {
		// remove document listener
		if (fDocumentListener != null) {
			getDocument().removeDocumentListener(fDocumentListener);
		}

		// clear out list of queued changes since it may no longer
		// be accurate
		if (fQueuedAnnotationChanges != null) {
			fQueuedAnnotationChanges.clear();
			fQueuedAnnotationChanges = null;
		}
	}
}
