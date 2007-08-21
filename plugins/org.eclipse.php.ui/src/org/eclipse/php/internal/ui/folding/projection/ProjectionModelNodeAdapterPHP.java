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

import org.eclipse.jface.text.*;
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

	private IStructuredDocument document;

	// first time we load the adapter - we should adopt the preference auto folding
	private boolean shouldAutoCollapseAnnotations = true;

	public ProjectionModelNodeAdapterPHP(ProjectionModelNodeAdapterFactoryPHP factory) {
		super(factory);
	}

	@Override
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
	@Override
	public void updateAdapter(Node node, ProjectionViewer viewer) {

		final Map<ProjectionAnnotation, Position> addedAnnotations = new HashMap<ProjectionAnnotation, Position>();
		final Map<ProjectionAnnotation, Position> currentAnnotations = new HashMap<ProjectionAnnotation, Position>();

		if (node != null && node instanceof NodeImpl) {
			NodeImpl element = (NodeImpl) node;
			assert element.getModel() instanceof DOMModelForPHP : "Incompatible model";
			DOMModelForPHP phpModel = (DOMModelForPHP) element.getModel();
			document = phpModel.getStructuredDocument();

			// resolve the viewer
			ProjectionViewer modelViewer = getAdapterFactory().findViewer(phpModel);
			if (modelViewer == null) {
				return;
			}

			// ignore editor changes when the php model isn't ready.
			ProjectionViewerInformation information = getAdapterFactory().getInformation(modelViewer);
			if (information.isDocumentChanging()) {
				return;
			}

			PHPFileData fileData = phpModel.getFileData();
			if (fileData == null) {
				return;
			}

			createAnnotationsForChild(addedAnnotations, currentAnnotations, fileData, node.getFirstChild());

			// in the end, want to delete anything leftover in old list, add
			// everything in additions, and update everything in
			// projectionAnnotations
			ProjectionAnnotation[] oldList = null;
			if (!previousAnnotations.isEmpty()) {
				oldList = previousAnnotations.keySet().toArray(new ProjectionAnnotation[0]);
			}
			ProjectionAnnotation[] modifyList = null;
			if (!currentAnnotations.isEmpty()) {
				// modifyList = currentAnnotations.keySet().toArray(new ProjectionAnnotation[0]);
			}

			// specifically add all annotations to viewer
			if (viewer != null && !currentAnnotations.isEmpty()) {
				fAdapterFactory.queueAnnotationModelChanges(node, null, currentAnnotations, null, viewer);
			}

			// only update when there is something to update
			if (oldList != null && oldList.length > 0 || !addedAnnotations.isEmpty() || modifyList != null && modifyList.length > 0) {
				fAdapterFactory.queueAnnotationModelChanges(node, oldList, addedAnnotations, modifyList);
			}

			// next time don't obey preferences rules 
			shouldAutoCollapseAnnotations = false;
		}

		// save new list of annotations
		previousAnnotations = currentAnnotations;

	}

	private final Node createAnnotationsForChild(final Map<ProjectionAnnotation, Position> addedAnnotations, final Map<ProjectionAnnotation, Position> currentAnnotations, PHPFileData fileData, Node childNode) {
		while (childNode != null) {
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				assert childNode instanceof ElementImplForPhp : "Bad element";
				ElementImplForPhp childElement = (ElementImplForPhp) childNode;
				if (childElement.isPhpTag()) {
					int startOffset = childElement.getStartOffset();
					int endOffset = childElement.getEndOffset();

					// adds the annotations for the specific node
					createFileAnnotations(currentAnnotations, addedAnnotations, fileData, startOffset, endOffset);
				}

				// adds the annotations for the child nodes (recursively)
				final Node firstChild = childElement.getFirstChild();
				createAnnotationsForChild(addedAnnotations, currentAnnotations, fileData, childElement.getFirstChild());
			}

			childNode = childNode.getNextSibling();
		}
		return childNode;
	}

	private void createFileAnnotations(Map<ProjectionAnnotation, Position> currentAnnotations, Map<ProjectionAnnotation, Position> addedAnnotations, PHPFileData fileData, int startOffset, int endOffset) {
		final ProjectionModelNodeAdapterFactoryPHP adapterFactory = getAdapterFactory();
		assert adapterFactory != null : "provider can't be null - see setProvider()";

		// set the automatic folding according to preference
		final boolean foldingPhpDoc = adapterFactory.isFoldingPhpDoc();
		final boolean foldingFunctions = adapterFactory.isFoldingFunctions();
		final boolean foldingClasses = adapterFactory.isFoldingClasses();

		// adds the file doc block
		createDocBlockAnnotations(currentAnnotations, addedAnnotations, fileData, startOffset, endOffset, foldingPhpDoc);

		PHPClassData[] classes = fileData.getClasses();
		for (PHPClassData classData : classes) {
			createCodeDataAnnotations(currentAnnotations, addedAnnotations, classData, startOffset, endOffset, foldingClasses);
			createDocBlockAnnotations(currentAnnotations, addedAnnotations, classData, startOffset, endOffset, foldingPhpDoc);
			PHPFunctionData[] methods = classData.getFunctions();
			for (PHPFunctionData methodData : methods) {
				createCodeDataAnnotations(currentAnnotations, addedAnnotations, methodData, startOffset, endOffset, foldingFunctions);
				createDocBlockAnnotations(currentAnnotations, addedAnnotations, methodData, startOffset, endOffset, foldingPhpDoc);
			}
			PHPClassVarData[] variables = classData.getVars();
			for (PHPClassVarData variableData : variables) {
				createDocBlockAnnotations(currentAnnotations, addedAnnotations, variableData, startOffset, endOffset, foldingPhpDoc);
			}

			PHPClassConstData[] constants = classData.getConsts();
			for (PHPClassConstData variableData : constants) {
				createDocBlockAnnotations(currentAnnotations, addedAnnotations, variableData, startOffset, endOffset, foldingPhpDoc);
			}
		}

		PHPFunctionData[] functions = fileData.getFunctions();
		for (PHPFunctionData functionData : functions) {
			createCodeDataAnnotations(currentAnnotations, addedAnnotations, functionData, startOffset, endOffset, foldingFunctions);
			createDocBlockAnnotations(currentAnnotations, addedAnnotations, functionData, startOffset, endOffset, foldingPhpDoc);
		}
	}

	/**
	 * Computes current annotations on given CodeDatas.
	 *
	 * @param codeDatas
	 * @param startOffset
	 * @param endOffset
	 */
	private void createCodeDataAnnotations(Map<ProjectionAnnotation, Position> currentAnnotations, Map<ProjectionAnnotation, Position> addedAnnotations, PHPCodeData codeData, int startOffset, int endOffset, boolean collapse) {
		UserData userData = codeData.getUserData();
		int codeStartOffset = userData.getStartPosition();
		if (codeStartOffset > startOffset && codeStartOffset < endOffset) {
			// element may start in one PHP block and end in another.
			// false - when adding new annotation - don't fold
			ProjectionAnnotation newAnnotation = new ElementProjectionAnnotation(codeData, false, shouldAutoCollapseAnnotations ? collapse : false);
			ProjectionAnnotation existingAnnotation = getExistingAnnotation(newAnnotation);
			Position newPosition = createPosition(codeStartOffset, userData.getEndPosition(), document);

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

	private Position createPosition(int startOffset, int endOffset, IDocument document) {
		assert document != null;

		final IRegion alignRegion = alignRegion(startOffset, endOffset, document);
		return new Position(alignRegion.getOffset(), alignRegion.getLength());
	}

	private Position createCommentPosition(int startOffset, int endOffset, IDocument document) {
		assert document != null;

		final IRegion alignRegion = alignRegion(startOffset, endOffset, document);
		return new CommentPosition(alignRegion.getOffset(), alignRegion.getLength());
	}

	/**
	 * Aligns <code>region</code> to start and end at a line offset. The region's start is
	 * decreased to the next line offset, and the end offset increased to the next line start or the
	 * end of the document. <code>null</code> is returned if <code>region</code> is
	 * <code>null</code> itself or does not comprise at least one line delimiter, as a single line
	 * cannot be folded.
	 * 
	 * @param region the region to align, may be <code>null</code>
	 * @param document the folding context
	 * @return a region equal or greater than <code>region</code> that is aligned with line
	 *         offsets, <code>null</code> if the region is too small to be foldable (e.g. covers
	 *         only one line)
	 */
	protected final IRegion alignRegion(int startOfset, int endOffsetOrg, IDocument document) {
		try {
			final int length = document.getLength();
			int start = document.getLineOfOffset(startOfset);
			int end = document.getLineOfOffset(Math.min(length, endOffsetOrg));

			if (start >= end)
				return new Region(startOfset, 0);

			int offset = document.getLineOffset(start);
			int endOffset;
			if (document.getNumberOfLines() > end + 1)
				endOffset = document.getLineOffset(end + 1);
			else
				endOffset = document.getLineOffset(end) + document.getLineLength(end);

			return new Region(offset, endOffset - offset);

		} catch (BadLocationException x) {
			// concurrent modification
			return new Region(startOfset, endOffsetOrg - startOfset);
		}
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
	 * @param fileData 
	 *
	 * @param codeDatas
	 * @param startOffset
	 * @param endOffset
	 * @param collapse
	 */
	private void createDocBlockAnnotations(Map<ProjectionAnnotation, Position> currentAnnotations, Map<ProjectionAnnotation, Position> addedAnnotations, PHPCodeData codeData, int startOffset, int endOffset, boolean collapse) {
		final PHPDocBlock docBlock = codeData.getDocBlock();

		// no need to add an empty doc block
		if (docBlock == null) {
			return;
		}

		int codeStartOffset = docBlock.getStartPosition();
		if (codeStartOffset > startOffset && codeStartOffset < endOffset) {
			// element may start in one PHP block and end in another.
			// false - when adding new annotation - don't fold
			final Position newPosition = createCommentPosition(codeStartOffset, docBlock.getEndPosition(), document);
			final ProjectionAnnotation newAnnotation = new ElementProjectionAnnotation(codeData, true, shouldAutoCollapseAnnotations ? collapse : false);
			final ProjectionAnnotation existingAnnotation = getExistingAnnotation(newAnnotation);
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
	private ProjectionAnnotation getExistingAnnotation(ProjectionAnnotation projectionAnnotation) {
		assert projectionAnnotation != null : "projectionAnnotation should be not null";
		if (!previousAnnotations.isEmpty()) {
			for (ProjectionAnnotation annotation : previousAnnotations.keySet()) {
				if (projectionAnnotation.equals(annotation)) {
					return annotation;
				}
			}
		}
		return null;
	}

	private ProjectionModelNodeAdapterFactoryPHP getAdapterFactory() {
		assert fAdapterFactory instanceof ProjectionModelNodeAdapterFactoryPHP : "Factory must be ProjectionModelNodeAdapterFactoryPHP";
		return (ProjectionModelNodeAdapterFactoryPHP) fAdapterFactory;
	}
}
