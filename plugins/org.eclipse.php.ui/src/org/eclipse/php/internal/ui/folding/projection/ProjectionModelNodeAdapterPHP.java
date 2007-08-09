/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.php.internal.ui.folding.projection;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.dom.ElementImplForPhp;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;
import org.w3c.dom.Node;

/**
 * Updates projection annotation model with projection annotations for this
 * adapter node's children
 * @author Seva & Roy 2007
 */
public class ProjectionModelNodeAdapterPHP extends ProjectionModelNodeAdapterHTML {

	/**
	 * @author seva, 2007
	 *
	 */
	private static class MyPosition extends Position {

		/** (non-Javadoc)
		 * @see org.eclipse.jface.text.Position#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object other) {
			// TODO Auto-generated method stub
			return super.equals(other);
		}

		/** (non-Javadoc)
		 * @see org.eclipse.jface.text.Position#setLength(int)
		 */
		@Override
		public void setLength(int length) {
			// TODO Auto-generated method stub
			super.setLength(length);
		}

		/** (non-Javadoc)
		 * @see org.eclipse.jface.text.Position#setOffset(int)
		 */
		@Override
		public void setOffset(int offset) {
			// TODO Auto-generated method stub
			super.setOffset(offset);
		}

		/**
		 *
		 */
		public MyPosition() {
			super();
		}

		/**
		 * @param offset
		 * @param length
		 */
		public MyPosition(int offset, int length) {
			super(offset, length);
		}

		/**
		 * @param offset
		 */
		public MyPosition(int offset) {
			super(offset);
		}

	}

	private IStructuredDocument document;

	public ProjectionModelNodeAdapterPHP(ProjectionModelNodeAdapterFactoryPHP factory) {
		super(factory);
	}

	public boolean isAdapterForType(Object type) {
		return type == ProjectionModelNodeAdapterPHP.class;
	}

	/**
	 * Update the projection annotation of all the nodes that are children of
	 * node and adds all projection annotations to viewer (for newly added
	 * viewers)
	 *
	 * We run over the PHP elements and add the relevant annotations,
	 * We don't handle the HTML projections as it is handled in the HTML adapter
	 * hence don't call the super @see ProjectionModelNodeAdapterHTML#updateAdapter(org.w3c.dom.Node, org.eclipse.jface.text.source.projection.ProjectionViewer)
	 */
	public synchronized void updateAdapter(Node node, ProjectionViewer viewer) {

		final Map<ProjectionAnnotation, Position> addedAnnotations = new HashMap<ProjectionAnnotation, Position>();
		final Map<ProjectionAnnotation, Position> currentAnnotations = new HashMap<ProjectionAnnotation, Position>();

		if (node != null && node instanceof NodeImpl) {
			NodeImpl element = (NodeImpl) node;
			assert element.getModel() instanceof DOMModelForPHP : "Incompatible model";
			DOMModelForPHP phpModel = (DOMModelForPHP) element.getModel();
			document = phpModel.getStructuredDocument();

			ProjectionViewer modelViewer = getAdapterFactory().findViewer(phpModel);
			ProjectionViewerInformation information = getAdapterFactory().getInformation(modelViewer);

			// ignore editor changes when the php model isn't ready.
			if (information.isDocumentChanging()) {
				return;
			}

			Object lockObject = modelViewer.getProjectionAnnotationModel().getLockObject();
			modelViewer.getProjectionAnnotationModel().setLockObject(this);

			PHPFileData fileData = phpModel.getFileData();

			if(true)
				return;

			if (fileData == null) {
				return;
			}

			Node childNode = node.getFirstChild();
			while (childNode != null) {

				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					assert childNode instanceof ElementImplForPhp : "Bad element";
					ElementImplForPhp childElement = (ElementImplForPhp) childNode;
					if (childElement.isPhpTag()) {
						int startOffset = childElement.getStartOffset();
						int endOffset = childElement.getEndOffset();

						createFileAnnotations(currentAnnotations, addedAnnotations, fileData, startOffset, endOffset);

					}
				}
				childNode = childNode.getNextSibling();
			}

			// in the end, want to delete anything leftover in old list, add
			// everything in additions, and update everything in
			// projectionAnnotations
			ProjectionAnnotation[] oldList = null;
			if (!previousAnnotations.isEmpty()) {
				oldList = previousAnnotations.keySet().toArray(new ProjectionAnnotation[0]);
			}
			ProjectionAnnotation[] modifyList = null;
			if (!currentAnnotations.isEmpty()) {
				modifyList = currentAnnotations.keySet().toArray(new ProjectionAnnotation[0]);
			}

			// specifically add all annotations to viewer
			if (viewer != null && !currentAnnotations.isEmpty()) {
				fAdapterFactory.queueAnnotationModelChanges(node, null, currentAnnotations, null, viewer);
			}

			// only update when there is something to update
			if (oldList != null && oldList.length > 0 || !addedAnnotations.isEmpty() || modifyList != null && modifyList.length > 0) {
				fAdapterFactory.queueAnnotationModelChanges(node, oldList, addedAnnotations, modifyList);
			}
		}

		// save new list of annotations
		previousAnnotations = currentAnnotations;

	}

	private void createFileAnnotations(Map<ProjectionAnnotation, Position> currentAnnotations, Map<ProjectionAnnotation, Position> addedAnnotations, PHPFileData fileData, int startOffset, int endOffset) {
		assert getAdapterFactory() != null : "provider can't be null - see setProvider()";
		PHPClassData[] classes = fileData.getClasses();

		// set the automatic folding according to preference
		final boolean foldingPhpDoc = getAdapterFactory().isFoldingPhpDoc();
		final boolean foldingFunctions = getAdapterFactory().isFoldingFunctions();
		final boolean foldingClasses = getAdapterFactory().isFoldingClasses();

		for (PHPClassData classData : classes) {
			createCodeDataAnnotations(currentAnnotations, addedAnnotations, classData, startOffset, endOffset, foldingClasses);
			createDocBlockAnnotations(currentAnnotations, addedAnnotations, classData.getDocBlock(), startOffset, endOffset, foldingPhpDoc);
			PHPFunctionData[] methods = classData.getFunctions();
			for (PHPFunctionData methodData : methods) {
				createCodeDataAnnotations(currentAnnotations, addedAnnotations, methodData, startOffset, endOffset, foldingFunctions);
				createDocBlockAnnotations(currentAnnotations, addedAnnotations, methodData.getDocBlock(), startOffset, endOffset, foldingPhpDoc);
			}
			PHPClassVarData[] variables = classData.getVars();
			for (PHPClassVarData variableData : variables) {
				createDocBlockAnnotations(currentAnnotations, addedAnnotations, variableData.getDocBlock(), startOffset, endOffset, foldingPhpDoc);
			}

			PHPClassConstData[] constants = classData.getConsts();
			for (PHPClassConstData variableData : constants) {
				createDocBlockAnnotations(currentAnnotations, addedAnnotations, variableData.getDocBlock(), startOffset, endOffset, foldingPhpDoc);
			}
		}

		PHPFunctionData[] functions = fileData.getFunctions();
		for (PHPFunctionData functionData : functions) {
			createCodeDataAnnotations(currentAnnotations, addedAnnotations, functionData, startOffset, endOffset, foldingFunctions);
			createDocBlockAnnotations(currentAnnotations, addedAnnotations, functionData.getDocBlock(), startOffset, endOffset, foldingPhpDoc);
		}

		createDocBlockAnnotations(currentAnnotations, addedAnnotations, fileData.getDocBlock(), startOffset, endOffset, foldingPhpDoc);
	}

	/**
	 * Computes current annotations on given CodeDatas.
	 *
	 * @param codeDatas
	 * @param startOffset
	 * @param endOffset
	 */
	private void createCodeDataAnnotations(Map<ProjectionAnnotation, Position> currentAnnotations, Map<ProjectionAnnotation, Position> addedAnnotations, CodeData codeData, int startOffset, int endOffset, boolean collapse) {
		UserData userData = codeData.getUserData();
		int codeStartOffset = userData.getStartPosition();
		if (codeStartOffset > startOffset && codeStartOffset < endOffset) {
			// element may start in one PHP block and end in another.
			ProjectionAnnotation newAnnotation = new ProjectionAnnotation(collapse);
			Position newPosition = createPosition(codeStartOffset, userData.getEndPosition());
			ProjectionAnnotation existingAnnotation = getExistingAnnotation(newPosition);
			if (existingAnnotation == null) {
				// add to map containing all annotations for this
				// adapter
				currentAnnotations.put(newAnnotation, newPosition);
				// add to map containing annotations to add
				addedAnnotations.put(newAnnotation, newPosition);
			} else {
				// add to map containing all annotations for this
				// adapter
				currentAnnotations.put(existingAnnotation, newPosition);
				// remove from map containing annotations to delete
				previousAnnotations.remove(existingAnnotation);
			}
		}
	}

	private Position createPosition(int startOffset, int endOffset) {
		return new MyPosition(startOffset, endOffset - startOffset);
	}

	/* TODO think in this direction:
		private Position createPosition(int startOffset, int endOffset) {
			assert endOffset <= document.getLength() : "illegal offset";
			int lastCharOffset = endOffset;
			try {
				char lastChar = document.getChar(lastCharOffset);
				while (Character.isWhitespace(lastChar) && lastChar != '\n' && lastChar != '\r') {
					lastChar = document.getChar(++lastCharOffset);
				}
				if (lastChar == '\n') { // linux or windows
					lastChar = document.getChar(++lastCharOffset);
					if (lastChar == '\r') { // windows
						lastChar = document.getChar(++lastCharOffset);
					}
				} else if (lastChar == '\r') { // mac
					lastChar = document.getChar(++lastCharOffset);
				}
			} catch (BadLocationException e) {
				// the only case here is when we arrived to the end of document
				--lastCharOffset;
			}

			return new Position(startOffset, lastCharOffset - startOffset);
		}
	*/

	/**
	 * Computes current annotations on given CodeDatas.
	 *
	 * @param codeDatas
	 * @param startOffset
	 * @param endOffset
	 * @param collapse
	 */
	private void createDocBlockAnnotations(Map<ProjectionAnnotation, Position> currentAnnotations, Map<ProjectionAnnotation, Position> addedAnnotations, PHPDocBlock docBlock, int startOffset, int endOffset, boolean collapse) {
		if (docBlock == null) {
			return;
		}

		int codeStartOffset = docBlock.getStartPosition();
		if (codeStartOffset > startOffset && codeStartOffset < endOffset) {
			// element may start in one PHP block and end in another.
			Position newPosition = createPosition(codeStartOffset, docBlock.getEndPosition());
			ProjectionAnnotation newAnnotation = new ProjectionAnnotation(collapse);
			ProjectionAnnotation existingAnnotation = getExistingAnnotation(newPosition);
			if (existingAnnotation == null) {
				// add to map containing all annotations for this
				// adapter
				currentAnnotations.put(newAnnotation, newPosition);
				// add to map containing annotations to add
				addedAnnotations.put(newAnnotation, newPosition);
			} else {
				// add to map containing all annotations for this
				// adapter
				currentAnnotations.put(existingAnnotation, newPosition);
				// remove from map containing annotations to delete
				previousAnnotations.remove(existingAnnotation);
			}
		}
	}

	/**
	 * @param classData
	 * @return
	 */
	private ProjectionAnnotation getExistingAnnotation(Position position) {
		assert position != null : "Position should be not null";
		ProjectionAnnotation existingAnnotation = null;
		if (!previousAnnotations.isEmpty()) {
			for (Map.Entry<ProjectionAnnotation, Position> entry : previousAnnotations.entrySet()) {
				if (position.equals(entry.getValue())) {
					return entry.getKey();
				}
			}
		}
		return existingAnnotation;
	}

	private ProjectionModelNodeAdapterFactoryPHP getAdapterFactory() {
		assert fAdapterFactory instanceof ProjectionModelNodeAdapterFactoryPHP : "Factory must be ProjectionModelNodeAdapterFactoryPHP";
		return (ProjectionModelNodeAdapterFactoryPHP) fAdapterFactory;
	}
}
