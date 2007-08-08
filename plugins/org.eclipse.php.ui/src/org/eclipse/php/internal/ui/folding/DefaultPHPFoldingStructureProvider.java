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
package org.eclipse.php.internal.ui.folding;

import java.util.*;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.projection.*;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.parser.ModelListener;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.document.DocumentReader;
import org.eclipse.wst.sse.core.internal.provisional.IModelStateListener;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.eclipse.wst.sse.ui.internal.projection.IStructuredTextFoldingProvider;

/**
 * A PHPFoldingStructureProvider.
 *
 * @author shalom
 */
public class DefaultPHPFoldingStructureProvider implements IProjectionListener, IStructuredTextFoldingProvider, ModelListener {

	private static final PHPWorkspaceModelManager workspaceModelManagerInstance = PHPWorkspaceModelManager.getInstance();

	private ProjectionViewer viewer;
	private boolean collapseClasses;
	private boolean collapseFunctions;
	private boolean collapsePHPDoc;
	private boolean allowCollapsing;
	private IModelStateListener modelStateListener;
	private ArrayList toRemove;
	private Map newFolds;
	private Timer timer;
	private IDocument document;
	private TimerTask timerTask;

	public DefaultPHPFoldingStructureProvider() {
		super();
		collapseClasses = false;
		collapseFunctions = false;
		collapsePHPDoc = false;
		toRemove = new ArrayList();
		newFolds = new LinkedHashMap();
	}

	static int failCount;
	static final int MAX_RETRY = 3;
	static final int THREAD_DELAY = 5000;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.source.projection.IProjectionListener#projectionEnabled()
	 *
	 * In case getExistingModelForRead returns null, schedule a thread that starts after THREAD_DELAY milliseconds
	 * retry for MAX_RETRY times.
	 */
	public void projectionEnabled() {
		// http://home.ott.oti.com/teams/wswb/anon/out/vms/index.html
		// projectionEnabled messages are not always paired with projectionDisabled
		// i.e. multiple enabled messages may be sent out.
		// we have to make sure that we disable first when getting an enable
		// message.
		projectionDisabled();
		initialize();
		if (document != null) {
			IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForRead(document);
			if (model != null) {
				modelStateListener = new PHPModelStateListener();
				model.addModelStateListener(modelStateListener);
				model.releaseFromRead();
				failCount = 0;
			} else {
				TimerTask thread = new TimerTask() {
					public void run() {
						if (failCount++ < MAX_RETRY) {
							projectionEnabled();
						}
					}
				};
				Timer t = new Timer(false);
				t.schedule(thread, THREAD_DELAY);
				return;
			}
			timer = new Timer(false); // TODO - Remove this timer and listen to the annotation end of drawing instead
			timerTask = new FoldingTimerTask();
		}
	}

	public void projectionDisabled() {
		if (timer != null) {
			timer.cancel();
			timer = null;
			timerTask = null;
		}
		if (document != null) {
			IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForRead(document);
			if (model != null) {
				model.removeModelStateListener(modelStateListener);
				model.releaseFromRead();
			}
			modelStateListener = null;
			document = null;
		}
	}

	public void install(ProjectionViewer viewer) {
		// uninstall before trying to install new viewer
		if (isInstalled()) {
			uninstall();
		}
		this.viewer = viewer;
		viewer.addProjectionListener(this);
		workspaceModelManagerInstance.addModelListener(this);
	}

	public void uninstall() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (isInstalled()) {
			projectionDisabled();
			viewer.removeProjectionListener(this);
			workspaceModelManagerInstance.removeModelListener(this);
			viewer = null;
		}
	}

	protected boolean isInstalled() {
		return viewer != null;
	}

	public void initialize() {
		if (!isInstalled()) {
			return;
		}
		initializePreferences();
		// clear out all annotations
		if (viewer.getProjectionAnnotationModel() != null) {
			viewer.getProjectionAnnotationModel().removeAllAnnotations();
		}
		document = viewer.getDocument();
		allowCollapsing = true;
		if (document != null) {
			updateFolds();
		}
	}

	private void updateFolds() {
		IStructuredModel sModel = null;
		PHPFileData fileData = null;
		try {
			sModel = StructuredModelManager.getModelManager().getExistingModelForRead(document);
			if (sModel != null && sModel instanceof DOMModelForPHP) {
				DOMModelForPHP editorModel = (DOMModelForPHP) sModel;
				fileData = editorModel.getFileData();
				if (editorModel.getProjectModel() == null) {
					return;
				}
				if (fileData == null) {
					// create file data
					IProject project = editorModel.getProjectModel().getProject();
					fileData = PHPFileDataUtilities.getFileData(new DocumentReader(document), project);
				}
				workspaceModelManagerInstance.removeModelListener(this);
				ProjectionAnnotationModel model = viewer.getProjectionAnnotationModel();
				if (model != null) {
					synchronized (model.getLockObject()) {
						// Get the additions map that has a PHPProjectionAnnotation keys and AnnotatedPosition values
						Map additions = computeAdditions(fileData);
						toRemove.clear();
						newFolds.clear();

						// get the existing annotations.
						// In this map we use the AnnotatedPosition as keys
						Iterator existing = model.getAnnotationIterator();
						LinkedHashMap exitingHashMap = new LinkedHashMap();
						while ( existing.hasNext() ) {
							ProjectionAnnotation existingAnnotation = (ProjectionAnnotation) existing.next();
							Position existingPosition = model.getPosition(existingAnnotation);
							exitingHashMap.put(existingPosition, existingAnnotation);
						}

						Iterator additionsIterator = additions.values().iterator();

						while ( additionsIterator.hasNext() ) {
							AnnotatedPosition addedPosition = (AnnotatedPosition) additionsIterator.next();
							// Try to remove the added Position from the existing positions.
							// If the position was found and removed, then it was not new. Otherwise, it's a new
							// one and we add it to the new folds.
							//								addedPosition.hashCode()
							if (exitingHashMap.remove(addedPosition) == null) {
								newFolds.put(addedPosition.getAnnotation(), addedPosition);
							}
						}

						// At this stage we have the added map.
						// All the Annotations that are left in the hash need
						// to be removed from the model.
						boolean removeAnyway = false;
						Iterator annotationsToRemove = exitingHashMap.entrySet().iterator();
						while ( annotationsToRemove.hasNext() ) {
							Entry entry = (Entry) annotationsToRemove.next();
							AnnotatedPosition position = (AnnotatedPosition) entry.getKey();
							PHPProjectionAnnotation projectionToRemove = (PHPProjectionAnnotation) entry.getValue();
							// Check if this annotation should be removed.
							// Any expanded annotation should be removed.
							if (removeAnyway || !projectionToRemove.isCollapsed()) {
								toRemove.add(projectionToRemove);
							} else if (shouldRemoveAnnotation(projectionToRemove, position.offset)) {
								toRemove.add(projectionToRemove);
								// We can assume that any other fold that we have under this fold should be
								// removed since it's probably under a comment.
								removeAnyway = true;
							}
						}
						//						List removals = new LinkedList();
						//						model.getPosition((Annotation)removals.get(0));
						//						Iterator existing = model.getAnnotationIterator();
						//						while (existing.hasNext()) {
						//							removals.add(existing.next());
						//						}

						/*
						 *  Minimize the events being sent out - as this happens in the
						 *  UI thread merge everything into one call.
						 */
						if (toRemove.size() > 0 || newFolds.size() > 0) {
							model.replaceAnnotations((Annotation[]) toRemove.toArray(new Annotation[toRemove.size()]), newFolds);
						}
					}
				}
			}
		} catch (ArrayStoreException ase) {
			ase.printStackTrace();
		} finally {
			if (sModel != null) {
				sModel.releaseFromRead();
				if (fileData != null) {
					allowCollapsing = false;
				}
			}

		}
	}

	/*
	 * Check and return if the given projection-annotation should be removed from the annotations model.
	 * If the fold is collapsed and we are not dealing with a comment that caused it to expand
	 * we keep the fold and do not remove it, even though the model does not hold any data about it.
	 * However, if the annotation is already expanded, we remove it from the model.
	 *
	 * This technique prevents the expanding of the folds when editing the code in a way that the
	 * classes, functions and comments model temporarily drops some of the data.
	 */
	private boolean shouldRemoveAnnotation(PHPProjectionAnnotation projectionToRemove, int annotationOffset) {
		// Check if the caret position in the document is inside a comment. If so, the last edit was a
		// comment that was added and we need to open the folds anyway.
		if (document != null) {
			try {
				return isInComment((IStructuredDocument) document, annotationOffset);
			} catch (BadLocationException e) {
				Logger.logException(e);
			}
		}
		return false;
	}

	/*
	 * Initialize the preferences of the PHP folding.
	 */
	private void initializePreferences() {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		collapseClasses = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_CLASSES);
		collapseFunctions = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_FUNCTIONS);
		collapsePHPDoc = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_PHPDOC);
		//		collapseIncludes = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_INCLUDES);
	}

	/**
	 * Computes and returns the projection annotations additions.
	 * A user can override this method to add more additions.
	 *
	 * @param fileData The PHPFileData to compute.
	 * @return	A Map of projection annotations.
	 */
	protected Map computeAdditions(PHPFileData fileData) {
		Map map = new LinkedHashMap();
		try {
			computeClassesAdditions(fileData, map);
			computeFunctionsAdditions(fileData, map);
			computePHPDocAdditions(fileData, map);
		} catch (Exception x) {
		}
		return map;
	}

	/*
	 * Compute the PHP doc blocks additions.
	 */
	private void computePHPDocAdditions(PHPFileData fileData, Map map) {
		ArrayList data = new ArrayList();
		// Get the file data doc-block
		PHPDocBlock fileDataDocBlock = fileData.getDocBlock();
		data.add(fileDataDocBlock);

		// Get the external consts docs
		PHPConstantData[] constants = fileData.getConstants();
		for (PHPConstantData element : constants) {
			data.add(element.getDocBlock());
		}

		// Get the functions docs
		PHPFunctionData[] functionsData = fileData.getFunctions();
		for (PHPFunctionData element : functionsData) {
			data.add(element.getDocBlock());
		}

		// For each class, get the functions docs, the variables docs and the constants docs
		PHPClassData[] classData = fileData.getClasses();
		for (PHPClassData element : classData) {
			data.add(element.getDocBlock());
			PHPFunctionData[] functions = element.getFunctions();
			for (PHPFunctionData element2 : functions) {
				data.add(element2.getDocBlock());
			}
			PHPClassConstData[] classConstants = element.getConsts();
			for (PHPClassConstData element2 : classConstants) {
				data.add(element2.getDocBlock());
			}
			PHPClassVarData[] variables = element.getVars();
			for (PHPClassVarData element2 : variables) {
				data.add(element2.getDocBlock());
			}
		}

		// Add the collected doc-blocks to the map.
		addAnnotations(data, map, allowCollapsing && collapsePHPDoc, true);
	}

	/*
	 * Compute the PHP functions additions.
	 */
	private void computeFunctionsAdditions(PHPFileData fileData, Map map) {
		ArrayList data = new ArrayList();

		// Get the global functions
		PHPFunctionData[] functionsData = fileData.getFunctions();
		for (PHPFunctionData element : functionsData) {
			data.add(element);
		}

		// Get the functions that are inside classes
		PHPClassData[] classData = fileData.getClasses();
		for (PHPClassData element : classData) {
			PHPFunctionData[] functions = element.getFunctions();
			for (PHPFunctionData element2 : functions) {
				data.add(element2);
			}
		}
		addAnnotations(data, map, allowCollapsing && collapseFunctions, false);
	}

	/*
	 * Compute the PHP classes additions.
	 */
	private void computeClassesAdditions(PHPFileData fileData, Map map) {
		ArrayList data = new ArrayList();

		PHPClassData[] classData = fileData.getClasses();
		for (PHPClassData element : classData) {
			data.add(element);
		}
		addAnnotations(data, map, allowCollapsing && collapseClasses, false);
	}

	/*
	 * Add the given PHPCodeData list to the annotations map as PHPProjectionAnnotation.
	 */
	private void addAnnotations(ArrayList codeData, Map map, boolean collapsed, boolean isComment) {
		for (int i = 0; i < codeData.size(); i++) {
			Object data = codeData.get(i);
			if (data != null) {
				AnnotatedPosition position = null;
				if (isComment) {
					position = createAnnotatedPosition((PHPDocBlock) data);
				} else {
					position = createAnnotatedPosition((PHPCodeData) data);
				}
				if (position != null) {
					position.setAnnotation(new PHPProjectionAnnotation(data, collapsed, isComment));
					map.put(position.getAnnotation(), position);
				}
			}
		}
	}

	/*
	 * Create and return a Position according to the PHPCodeData.
	 */
	private AnnotatedPosition createAnnotatedPosition(PHPCodeData element) {
		if (document == null) {
			return null;
		}
		try {
			int start = document.getLineOfOffset(element.getUserData().getStopPosition());
			int end = document.getLineOfOffset(element.getUserData().getEndPosition());
			return createAnnotatedPosition(start, end);
		} catch (BadLocationException x) {
		}
		return null;
	}

	/*
	 * Create and return a Position according to a PHPDocBlock.
	 */
	private AnnotatedPosition createAnnotatedPosition(PHPDocBlock element) {
		if (document == null) {
			return null;
		}
		try {
			int start = document.getLineOfOffset(element.getStartPosition());
			int end = document.getLineOfOffset(element.getEndPosition() + 1);
			return createCommentAnnotatedPosition(start, end);
		} catch (BadLocationException x) {
		}
		return null;
	}

	/*
	 * Create and return a Position according to the given start and end document positions.
	 */
	private AnnotatedPosition createAnnotatedPosition(int start, int end) throws BadLocationException {
		if (start != end) {
			int offset = document.getLineOffset(start);
			int endOffset;
			if (document.getNumberOfLines() > end + 1) {
				endOffset = document.getLineOffset(end + 1);
			} else if (end > start) {
				endOffset = document.getLineOffset(end) + document.getLineLength(end);
			} else {
				return null;
			}
			return new AnnotatedPosition(offset, endOffset - offset);
		}
		return null;
	}

	/*
	 * Create and return a Position according to the given start and end document positions.
	 */
	private AnnotatedPosition createCommentAnnotatedPosition(int start, int end) throws BadLocationException {
		if (start != end) {
			int offset = document.getLineOffset(start);
			int endOffset;
			if (document.getNumberOfLines() > end + 1) {
				endOffset = document.getLineOffset(end + 1);
			} else if (end > start) {
				endOffset = document.getLineOffset(end) + document.getLineLength(end);
			} else {
				return null;
			}
			return new CommentAnnotatedPosition(offset, endOffset - offset);
		}
		return null;
	}

	/*
	 * Returns true if the given offset is inside a PHPDoc comment.
	 */
	private boolean isInComment(IStructuredDocument document, int offset) throws BadLocationException {
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
		ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(offset);

		if (textRegion == null)
			return false;

		ITextRegionCollection container = sdRegion;

		if (textRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) textRegion;
			textRegion = container.getRegionAtCharacterOffset(offset);
		}

		if (textRegion.getType() == PHPRegionContext.PHP_OPEN) {
			return false;
		}
		if (textRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			if (container.getStartOffset(textRegion) == offset) {
				ITextRegion regionBefore = container.getRegionAtCharacterOffset(offset - 1);
				if (regionBefore instanceof PhpScriptRegion) {
					textRegion = regionBefore;
				}
			} else {
				return false;
			}
		}

		PhpScriptRegion phpScriptRegion = null;
		String partitionType = null;
		int internalOffset = 0;

		if (textRegion instanceof PhpScriptRegion) {
			phpScriptRegion = (PhpScriptRegion) textRegion;
			internalOffset = offset - container.getStartOffset() - phpScriptRegion.getStart();

			partitionType = phpScriptRegion.getPartition(internalOffset);
			//if we are at the beginning of multi-line comment or docBlock then we should get completion.
			if (partitionType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT || partitionType == PHPPartitionTypes.PHP_DOC) {
				String regionType = phpScriptRegion.getPhpToken(internalOffset).getType();
				if (regionType == PHPRegionTypes.PHP_COMMENT_START || regionType == PHPRegionTypes.PHPDOC_COMMENT_START) {
					if (phpScriptRegion.getPhpToken(internalOffset).getStart() == internalOffset) {
						partitionType = phpScriptRegion.getPartition(internalOffset - 1);
					}
				}
			}
			return partitionType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT || partitionType == PHPPartitionTypes.PHP_DOC;
		}
		return false;
	}

	/*
	 * A listener for the PHP model.
	 * This listener starts the folding update on every modelChanged event.
	 */
	private class PHPModelStateListener implements IModelStateListener {
		/**
		 * Update the folds when the model is changed.
		 */
		public void modelChanged(IStructuredModel model) {
			if (timer != null) {
				timer.cancel();
			}
			timer = new Timer(false);
			timer.schedule(new FoldingTimerTask(), 1000);
		}

		// Do nothing on all other methods
		public void modelAboutToBeChanged(IStructuredModel model) {
		}

		public void modelDirtyStateChanged(IStructuredModel model, boolean isDirty) {
		}

		public void modelResourceDeleted(IStructuredModel model) {
		}

		public void modelResourceMoved(IStructuredModel oldModel, IStructuredModel newModel) {
		}

		public void modelAboutToBeReinitialized(IStructuredModel structuredModel) {
		}

		public void modelReinitialized(IStructuredModel structuredModel) {
		}
	}

	/*
	 * An annotated position, which is a Position that holds an annotation that is assigned to it.
	 */
	private class AnnotatedPosition extends Position {

		private ProjectionAnnotation annotation;

		/**
		 * Constructs a new AnnotatedPosition.
		 *
		 * @param offset
		 * @param length
		 */
		public AnnotatedPosition(int offset, int length) {
			super(offset, length);
		}

		/**
		 * Constructs a new AnnotatedPosition.
		 *
		 * @param offset
		 * @param length
		 * @param annotation
		 */
		public AnnotatedPosition(int offset, int length, ProjectionAnnotation annotation) {
			super(offset, length);
			this.annotation = annotation;
		}

		/**
		 * Returns the ProjectionAnnotation that is attached to this position.
		 *
		 * @return
		 */
		public ProjectionAnnotation getAnnotation() {
			return annotation;
		}

		/**
		 * Attach a ProjectionAnnotation to this position.
		 *
		 * @param annotation
		 */
		public void setAnnotation(ProjectionAnnotation annotation) {
			this.annotation = annotation;
		}

		public String toString() {
			return "[AnnotatedPosition (" + getOffset() + ", " + getLength() + ")]";
		}
	}

	private class CommentAnnotatedPosition extends AnnotatedPosition implements IProjectionPosition {
		/**
		 * Constructs a new CommentAnnotatedPosition.
		 *
		 * @param offset
		 * @param length
		 */
		public CommentAnnotatedPosition(int offset, int length) {
			super(offset, length);
		}

		/**
		 * Constructs a new CommentAnnotatedPosition.
		 *
		 * @param offset
		 * @param length
		 * @param annotation
		 */
		public CommentAnnotatedPosition(int offset, int length, ProjectionAnnotation annotation) {
			super(offset, length, annotation);
		}

		/**
		 * Returns the offset of the caption (the anchor region) of this projection
		 * position. The returned offset is relative to the receivers offset into
		 * the document.
		 *
		 * @param document the document that this position is attached to
		 * @return the caption offset relative to the position's offset
		 * @throws BadLocationException if accessing the document fails
		 */
		public int computeCaptionOffset(IDocument document) throws BadLocationException {
			return findFirstContent(document, getOffset(), getLength());
		}

		/**
		 * Returns an array of regions that should be collapsed when the annotation
		 * belonging to this position is collapsed. May return null instead of
		 * an empty array.
		 *
		 * @param document the document that this position is attached to
		 * @return the foldable regions for this position
		 * @throws BadLocationException if accessing the document fails
		 */
		public IRegion[] computeProjectionRegions(IDocument document) throws BadLocationException {
			int contentStart = findFirstContent(document, getOffset(), getLength());

			int firstLine = document.getLineOfOffset(getOffset());
			int captionLine = document.getLineOfOffset(getOffset() + contentStart);
			int lastLine = document.getLineOfOffset(getOffset() + getLength());

			IRegion preRegion;
			if (firstLine < captionLine) {
				int preOffset = document.getLineOffset(firstLine);
				IRegion preEndLineInfo = document.getLineInformation(captionLine);
				int preEnd = preEndLineInfo.getOffset();
				preRegion = new Region(preOffset, preEnd - preOffset);
			} else {
				preRegion = null;
			}

			if (captionLine < lastLine) {
				int postOffset = document.getLineOffset(captionLine + 1);
				IRegion postRegion = new Region(postOffset, getOffset() + getLength() - postOffset);

				if (preRegion == null)
					return new IRegion[] { postRegion };

				return new IRegion[] { preRegion, postRegion };
			}

			if (preRegion != null) {
				return new IRegion[] { preRegion };
			}

			return null;
		}

		/**
		 * Finds the offset of the first identifier part within <code>content</code>.
		 * Returns 0 if none is found.
		 *
		 * @param content the content to search
		 * @param prefixEnd the end of the prefix
		 * @return the first index of a unicode identifier part, or zero if none can
		 *         be found
		 * @throws BadLocationException
		 */
		private int findFirstContent(final IDocument document, int offset, int length) throws BadLocationException {
			for (int index = 0; index < length; index++) {
				char currentChar = document.getChar(offset + index);
				if (Character.isUnicodeIdentifierPart(currentChar))
					return index;
			}
			return 0;
		}
	}

	/*
	 * A PHP ProjectionAnnotation.
	 */
	private static final class PHPProjectionAnnotation extends ProjectionAnnotation {

		private Object phpElement;
		private boolean isComment;

		public PHPProjectionAnnotation(Object phpElement, boolean isCollapsed, boolean isComment) {
			super(isCollapsed);
			this.phpElement = phpElement;
			this.isComment = isComment;
		}

		public Object getElement() {
			return phpElement;
		}

		public void setElement(PHPCodeData phpElement) {
			this.phpElement = phpElement;
		}

		public boolean isComment() {
			return isComment;
		}

		public void setIsComment(boolean isComment) {
			this.isComment = isComment;
		}

		public boolean equals(Object other) {
			if (other == this) {
				return true;
			}
			if (other instanceof PHPProjectionAnnotation) {
				return phpElement.equals(((PHPProjectionAnnotation) other).phpElement);
			}
			return false;
		}

		/*
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "PHPProjectionAnnotation:\n" + //$NON-NLS-1$
				"\telement: \t" + phpElement.toString() + "\n" + //$NON-NLS-1$ //$NON-NLS-2$
				"\tcollapsed: \t" + isCollapsed() + "\n" + //$NON-NLS-1$ //$NON-NLS-2$
				"\tcomment: \t" + isComment + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private class FoldingTimerTask extends TimerTask {
		public void run() {
			updateFolds();
			timer.cancel();
		}
	}

	public void dataCleared() {
		// Do nothing
	}

	public void fileDataAdded(PHPFileData fileData) {
		IStructuredModel sModel = null;
		try {
			sModel = StructuredModelManager.getModelManager().getExistingModelForRead(document);
			if (sModel != null && sModel instanceof DOMModelForPHP) {
				DOMModelForPHP editorModel = (DOMModelForPHP) sModel;
				if (editorModel.getFileData() == fileData) {
					updateFolds();
					//					allowCollapsing = true; // TODO - Need a fix!
				}
			}
		} catch (Throwable t) {
		} finally {
			if (sModel != null) {
				sModel.releaseFromRead();
			}
		}
	}

	public void fileDataChanged(PHPFileData fileData) {
		// Do nothing
	}

	public void fileDataRemoved(PHPFileData fileData) {
		// Do nothing
	}
}
