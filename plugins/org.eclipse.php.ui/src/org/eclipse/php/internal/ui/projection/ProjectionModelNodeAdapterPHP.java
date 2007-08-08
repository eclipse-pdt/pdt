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
package org.eclipse.php.internal.ui.projection;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.dom.ElementImplForPhp;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;
import org.w3c.dom.Node;

/**
 * Updates projection annotation model with projection annotations for this
 * adapter node's children
 * @author Seva & Roy 2007
 */
public class ProjectionModelNodeAdapterPHP extends ProjectionModelNodeAdapterHTML {

	final private Map<ProjectionAnnotation, Position> addedAnnotations = new HashMap<ProjectionAnnotation, Position>();
	final private Map<ProjectionAnnotation, Position> currentAnnotations = new HashMap<ProjectionAnnotation, Position>();

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
	public void updateAdapter(Node node, ProjectionViewer viewer) {

		addedAnnotations.clear();
		currentAnnotations.clear();

		if (node != null && node instanceof NodeImpl) {
			NodeImpl element = (NodeImpl) node;
			assert element.getModel() instanceof DOMModelForPHP : "Incompatible model";
			DOMModelForPHP phpModel = (DOMModelForPHP) element.getModel();

			ProjectionViewerInformation information = getAdapterFactory().findInformation(phpModel);
			if(information.isDocumentChanging())
				return;

			PHPFileData fileData = phpModel.getFileData();

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

						createFileAnnotations(fileData, startOffset, endOffset);

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
			if (oldList != null && oldList.length > 0 || !addedAnnotations.isEmpty() || modifyList != null && modifyList.length > 0)
				fAdapterFactory.queueAnnotationModelChanges(node, oldList, addedAnnotations, modifyList);
		}

		// save new list of annotations
		previousAnnotations = currentAnnotations;

	}

	private void createFileAnnotations(PHPFileData fileData, int startOffset, int endOffset) {
		assert getAdapterFactory() != null : "provider can't be null - see setProvider()";
		PHPClassData[] classes = fileData.getClasses();
		for (PHPClassData classData : classes) {
			if (getAdapterFactory().isFoldingClasses()) {
				createCodeDataAnnotations(classData, startOffset, endOffset);
				if (getAdapterFactory().isFoldingPhpDoc()) {
					createDocBlockAnnotations(classData.getDocBlock(), startOffset, endOffset);
				}

			}
			PHPFunctionData[] methods = classData.getFunctions();
			for (PHPFunctionData methodData : methods) {
				if (getAdapterFactory().isFoldingFunctions()) {
					createCodeDataAnnotations(methodData, startOffset, endOffset);
				}
				if (getAdapterFactory().isFoldingPhpDoc()) {
					createDocBlockAnnotations(methodData.getDocBlock(), startOffset, endOffset);
				}
			}
			if (getAdapterFactory().isFoldingPhpDoc()) {
				PHPClassVarData[] variables = classData.getVars();
				for (PHPClassVarData variableData : variables) {
					createDocBlockAnnotations(variableData.getDocBlock(), startOffset, endOffset);
				}

				PHPClassConstData[] constants = classData.getConsts();
				for (PHPClassConstData variableData : constants) {
					createDocBlockAnnotations(variableData.getDocBlock(), startOffset, endOffset);
				}
			}
		}

		PHPFunctionData[] functions = fileData.getFunctions();
		for (PHPFunctionData functionData : functions) {
			if (getAdapterFactory().isFoldingFunctions()) {
				createCodeDataAnnotations(functionData, startOffset, endOffset);
			}
			if (getAdapterFactory().isFoldingPhpDoc()) {
				createDocBlockAnnotations(functionData.getDocBlock(), startOffset, endOffset);
			}
		}

		if (getAdapterFactory().isFoldingPhpDoc()) {
			createDocBlockAnnotations(fileData.getDocBlock(), startOffset, endOffset);
		}
	}

	/**
	 * Computes current annotations on given CodeDatas.
	 *
	 * @param codeDatas
	 * @param startOffset
	 * @param endOffset
	 */
	private void createCodeDataAnnotations(CodeData codeData, int startOffset, int endOffset) {
		UserData userData = codeData.getUserData();
		int codeStartOffset = userData.getStartPosition();
		if (codeStartOffset > startOffset && codeStartOffset < endOffset) {
			// element may start in one PHP block and end in another.
			Position newPosition = new Position(codeStartOffset, userData.getEndPosition() - codeStartOffset);
			ProjectionAnnotation newAnnotation = new ProjectionAnnotation(false);
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
	 * Computes current annotations on given CodeDatas.
	 *
	 * @param codeDatas
	 * @param startOffset
	 * @param endOffset
	 */
	private void createDocBlockAnnotations(PHPDocBlock docBlock, int startOffset, int endOffset) {
		if (docBlock == null) {
			return;
		}

		int codeStartOffset = docBlock.getStartPosition();
		if (codeStartOffset > startOffset && codeStartOffset < endOffset) {
			// element may start in one PHP block and end in another.
			Position newPosition = new Position(codeStartOffset, docBlock.getEndPosition() - codeStartOffset);
			ProjectionAnnotation newAnnotation = new ProjectionAnnotation(false);
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
					entry.getKey();
				}
			}
		}
		return existingAnnotation;
	}

	private ProjectionModelNodeAdapterFactoryPHP getAdapterFactory() {
		assert fAdapterFactory instanceof ProjectionModelNodeAdapterFactoryPHP : "Factory must be ProjectionModelNodeAdapterFactoryPHP";
		return (ProjectionModelNodeAdapterFactoryPHP) fAdapterFactory;
	}

	/**
	 * @return the addedAnnotations
	 */
	public Map<ProjectionAnnotation, Position> getAddedAnnotations() {
		return addedAnnotations;
	}
}
