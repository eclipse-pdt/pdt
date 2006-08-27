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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.projection.IProjectionListener;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.core.documentModel.PHPEditorModel;
import org.eclipse.php.core.phpModel.parser.ModelListener;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.*;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelStateListener;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
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
			} else {
				PHPUiPlugin.logErrorMessage("getExistingModelForRead gave a null result");
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
			} else {
				PHPUiPlugin.logErrorMessage("getExistingModelForRead gave a null result");
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
			if (sModel != null && sModel instanceof PHPEditorModel) {
				PHPEditorModel editorModel = (PHPEditorModel) sModel;
				fileData = editorModel.getFileData();
				if (fileData == null) {
					// It's possible that while loading, the model is not yet ready, therefore, we will wait until the 
					// model fires the fileDataAdded event with the currect file data.
					// Fix bug #75
					return;
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
						while (existing.hasNext()) {
							ProjectionAnnotation existingAnnotation = (ProjectionAnnotation) existing.next();
							Position existingPosition = model.getPosition(existingAnnotation);
							exitingHashMap.put(existingPosition, existingAnnotation);
						}

						Iterator additionsIterator = additions.values().iterator();

						while (additionsIterator.hasNext()) {
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
						Iterator annotationsToRemove = exitingHashMap.values().iterator();
						while (annotationsToRemove.hasNext()) {
							toRemove.add(annotationsToRemove.next());
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
				if (fileData != null){
					allowCollapsing = false;
				}
			}
			
		}
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
		for (int k = 0; k < constants.length; k++) {
			data.add(constants[k].getDocBlock());
		}

		// Get the functions docs
		PHPFunctionData[] functionsData = fileData.getFunctions();
		for (int index = 0; index < functionsData.length; index++) {
			data.add(functionsData[index].getDocBlock());
		}

		// For each class, get the functions docs, the variables docs and the constants docs
		PHPClassData[] classData = fileData.getClasses();
		for (int j = 0; j < classData.length; j++) {
			data.add(classData[j].getDocBlock());
			PHPFunctionData[] functions = classData[j].getFunctions();
			for (int k = 0; k < functions.length; k++) {
				data.add(functions[k].getDocBlock());
			}
			PHPClassConstData[] classConstants = classData[j].getConsts();
			for (int k = 0; k < classConstants.length; k++) {
				data.add(classConstants[k].getDocBlock());
			}
			PHPClassVarData[] variables = classData[j].getVars();
			for (int k = 0; k < variables.length; k++) {
				data.add(variables[k].getDocBlock());
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
		for (int i = 0; i < functionsData.length; i++) {
			data.add(functionsData[i]);
		}

		// Get the functions that are inside classes
		PHPClassData[] classData = fileData.getClasses();
		for (int j = 0; j < classData.length; j++) {
			PHPFunctionData[] functions = classData[j].getFunctions();
			for (int k = 0; k < functions.length; k++) {
				data.add(functions[k]);
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
		for (int i = 0; i < classData.length; i++) {
			data.add(classData[i]);
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
			return createAnnotatedPosition(start, end);
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
			if (sModel != null && sModel instanceof PHPEditorModel) {
				PHPEditorModel editorModel = (PHPEditorModel) sModel;
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
