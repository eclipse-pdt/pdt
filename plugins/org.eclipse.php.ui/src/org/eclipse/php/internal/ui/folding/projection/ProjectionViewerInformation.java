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

import java.util.*;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

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

	private static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[] {};

	/**
	 * Listens to document to be aware of when to update the projection
	 * annotation model.
	 */
	private static class DocumentListener implements IDocumentListener {
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
			fQueuedAnnotationChanges = new LinkedList<ProjectionAnnotationModelChanges>();
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
		List<ProjectionAnnotationModelChanges> queuedChanges = getQueuedAnnotationChanges();
		// go through all the pending annotation changes and apply:
		while (!queuedChanges.isEmpty()) {
			ProjectionAnnotationModelChanges changes = queuedChanges.remove(0);
			try {
				// 1. Collect annotations and their positions and store collapsed ones:
				Set<Position> collapsedPositions = new HashSet<Position>();
				Map<Position, ProjectionAnnotation> positionAnnotations = new HashMap<Position, ProjectionAnnotation>();
				for (Iterator<ProjectionAnnotation> i = fProjectionAnnotationModel.getAnnotationIterator(); i.hasNext();) {
					ProjectionAnnotation existingAnnotation = i.next();
					Position position = fProjectionAnnotationModel.getPosition(existingAnnotation);
					if (existingAnnotation.isCollapsed() && inScript(position.offset)) {
						collapsedPositions.add(position);
					}
					positionAnnotations.put(position, existingAnnotation);
				}

				// 2. Delete the annotations marked as deleted, if they are not collapsed:
				Annotation[] deletions = changes.getDeletions();
				if (deletions == null) {
					deletions = EMPTY_ANNOTATIONS;
				}
				Set<Position> persistentPositions = new HashSet<Position>(deletions.length);
				for (Annotation deletion : deletions) {
					Position position = fProjectionAnnotationModel.getPosition(deletion);
					if (!collapsedPositions.contains(position)) {
						fProjectionAnnotationModel.removeAnnotation(deletion);
					} else {
						persistentPositions.add(position);
					}
				}

				// 3. Add missing annotations or replace existing ones with same persistent position:
				Map<ProjectionAnnotation, Position> additions = changes.getAdditions();
				for (Map.Entry<ProjectionAnnotation, Position> addition : additions.entrySet()) {
					Position position = addition.getValue();
					ProjectionAnnotation newAnnotation = addition.getKey();
					if (!persistentPositions.contains(position)) {
						fProjectionAnnotationModel.addAnnotation(newAnnotation, position);
					} else {
						ProjectionAnnotation existingAnnotation = positionAnnotations.get(position);
						if (existingAnnotation.isCollapsed()) {
							newAnnotation.markCollapsed();
						} else {
							newAnnotation.markExpanded();
						}
						Map annotationAddition = new HashMap(1);
						annotationAddition.put(newAnnotation, position);
						fProjectionAnnotationModel.replaceAnnotations(new Annotation[] { existingAnnotation }, annotationAddition);
					}
				}

				//4. Replace positions for modified annotations or add if missing and not persistent:
				Map<ProjectionAnnotation, Position> modifications = changes.getModifications();
				if (modifications != null) {
					for (Map.Entry<ProjectionAnnotation, Position> modification : modifications.entrySet()) {
						ProjectionAnnotation modifiedAnnotation = modification.getKey();
						Position modifiedPosition = modification.getValue();
						Position position = fProjectionAnnotationModel.getPosition(modifiedAnnotation);
						if (position == null) {
							if (!persistentPositions.contains(modifiedPosition)) {
								fProjectionAnnotationModel.addAnnotation(modifiedAnnotation, modifiedPosition);
							}
						} else if (!modifiedPosition.equals(position)) {
							fProjectionAnnotationModel.modifyAnnotationPosition(modifiedAnnotation, modifiedPosition);
						}
					}
				}

			} catch (RuntimeException e) {
				Logger.logException(e);
			}
		}
	}

	/**
	 * @param offset
	 * @return true, if the offset is in script region (not string, comment, html or heredoc)
	 */
	private boolean inScript(int offset) {
		IStructuredDocument document = (IStructuredDocument) fDocument;
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
		ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(offset);

		if (textRegion == null)
			return false;

		ITextRegionCollection container = sdRegion;

		if (textRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) textRegion;
			textRegion = container.getRegionAtCharacterOffset(offset);
		}
		if (!(textRegion instanceof PhpScriptRegion)) {
			return false;
		}

		PhpScriptRegion phpScriptRegion = (PhpScriptRegion) textRegion;

		int internalOffset = offset - container.getStartOffset() - phpScriptRegion.getStart();

		try {
			String partitionType = phpScriptRegion.getPartition(internalOffset);
			if (partitionType == PHPPartitionTypes.PHP_DEFAULT) {
				return true;
			}

			if (partitionType == PHPPartitionTypes.PHP_DOC || partitionType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT) {
				ITextRegion phpToken = phpScriptRegion.getPhpToken(internalOffset);
				if (phpToken.getStart() == internalOffset) {
					return true;
				}
			}

			return false;
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return false;
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
