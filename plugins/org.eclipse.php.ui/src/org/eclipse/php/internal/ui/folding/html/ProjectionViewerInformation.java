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
package org.eclipse.php.internal.ui.folding.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.internal.core.Logger;

/**
 * Contains information about a projection viewer and also manages updating the
 * viewer's projection annotation model
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
			if (document instanceof IDocumentExtension
					&& fInfo.getDocument() == document) {
				if (fInfo.hasChangesQueued())
					((IDocumentExtension) document)
							.registerPostNotificationReplace(this,
									new PostDocumentChangedListener(fInfo));
			}
		}
	}

	/**
	 * Essentially a post document changed listener because it is called after
	 * documentchanged has been fired.
	 */
	private class PostDocumentChangedListener implements
			IDocumentExtension.IReplace {
		private ProjectionViewerInformation fInfo;

		public PostDocumentChangedListener(ProjectionViewerInformation info) {
			fInfo = info;
		}

		public void perform(IDocument document, IDocumentListener owner) {
			IJobManager jobManager = Job.getJobManager();
			if (jobManager.find("Applying annotation model changes").length == 0) { //$NON-NLS-1$
				ApplyAnnotationModelChangesJob job = new ApplyAnnotationModelChangesJob(
						"Applying annotation model changes", fInfo); //$NON-NLS-1$
				job.setPriority(Job.DECORATE);
				job.setSystem(true);
				job.schedule();
			}
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
	private List<ProjectionAnnotationModelChanges> fQueuedAnnotationChanges;

	public ProjectionViewerInformation(ProjectionViewer viewer) {
		fDocument = viewer.getDocument();
		fProjectionAnnotationModel = viewer.getProjectionAnnotationModel();
	}

	IDocument getDocument() {
		return fDocument;
	}

	private List<ProjectionAnnotationModelChanges> getQueuedAnnotationChanges() {
		if (fQueuedAnnotationChanges == null) {
			fQueuedAnnotationChanges = Collections
					.synchronizedList(new ArrayList<ProjectionAnnotationModelChanges>());
		}
		return fQueuedAnnotationChanges;
	}

	void setIsDocumentChanging(boolean changing) {
		fIsDocumentChanging = changing;
	}

	private boolean isDocumentChanging() {
		return fIsDocumentChanging;
	}

	/**
	 * Applies the pending projection annotation model changes to the projection
	 * annotation model.
	 */
	void applyAnnotationModelChanges() {
		List<ProjectionAnnotationModelChanges> changesToApply = new ArrayList<ProjectionAnnotationModelChanges>();

		List<ProjectionAnnotationModelChanges> queuedChanges = getQueuedAnnotationChanges();
		// Copy of changes to apply. Original list is cleared.
		synchronized (queuedChanges) {
			changesToApply.addAll(queuedChanges);
			queuedChanges.clear();
		}
		queuedChanges = null;
		// go through all the pending annotation changes and apply
		// them to
		// the projection annotation model
		while (!changesToApply.isEmpty()) {
			ProjectionAnnotationModelChanges changes = changesToApply.remove(0);
			try {
				fProjectionAnnotationModel.modifyAnnotations(
						changes.getDeletions(), changes.getAdditions(),
						changes.getModifications());
			} catch (Exception e) {
				// if anything goes wrong, log it and continue
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
	public void queueAnnotationModelChanges(
			ProjectionAnnotationModelChanges newChange) {

		List<ProjectionAnnotationModelChanges> changes = getQueuedAnnotationChanges();
		synchronized (changes) {
			int index = changes.indexOf(newChange);
			if (index > -1) {
				changes.get(index).updateChange(newChange);
			} else {
				changes.add(newChange);
			}

		}

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

	private class ApplyAnnotationModelChangesJob extends Job {
		ProjectionViewerInformation fInfo;

		public ApplyAnnotationModelChangesJob(String name,
				ProjectionViewerInformation fInfo) {
			super(name);
			this.fInfo = fInfo;
		}

		public boolean belongsTo(Object family) {
			return getName().equals(family);
		}

		public IStatus run(IProgressMonitor monitor) {
			fInfo.applyAnnotationModelChanges();
			fInfo.setIsDocumentChanging(false);
			return Status.OK_STATUS;
		}
	}
}
