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
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.w3c.dom.Node;

/**
 * Updates projection annotation model with projection annotations for this
 * adapter node's children
 */
public class ProjectionModelNodeAdapterPHP extends ProjectionModelNodeAdapterHTML {

	final private Map<DataProjectionAnnotation, Position> addedAnnotations = new HashMap<DataProjectionAnnotation, Position>();
	final private Map<DataProjectionAnnotation, Position> currentAnnotations = new HashMap<DataProjectionAnnotation, Position>();

	/**
		 * @author seva, 2007
		 *
		 */
	public class DataProjectionAnnotation extends ProjectionAnnotation {

		private Object data;

		/**
		 * @param codeData
		 * @param b
		 */
		public DataProjectionAnnotation(Object data, boolean isCollapsed) {
			super(isCollapsed);
			this.data = data;
		}

	}

	private StructuredTextFoldingProviderPHP structuredTextFoldingProviderPHP;

	public ProjectionModelNodeAdapterPHP(ProjectionModelNodeAdapterFactoryPHP factory) {
		super(factory);
	}

	public boolean isAdapterForType(Object type) {
		return type == ProjectionModelNodeAdapterPHP.class;
	}

	void updateAdapter(Node node, ProjectionViewer viewer) {

		addedAnnotations.clear();
		currentAnnotations.clear();

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			assert node instanceof ElementImplForPhp : "Bad element";
			ElementImplForPhp element = (ElementImplForPhp) node;
			if (element.isPhpTag()) {
				IStructuredDocument structuredDocument = element.getStructuredDocument();
				assert structuredDocument != null : "Document doesn't exist";
				IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForRead(structuredDocument);
				assert model != null : "Model doesn't exist";
				DOMModelForPHP phpModel = (DOMModelForPHP) model;
				PHPFileData fileData = phpModel.getFileData();

				int startOffset = element.getStartOffset();
				int endOffset = element.getEndOffset();

				createFileAnnotations(fileData, startOffset, endOffset);

			}
		}
		super.updateAdapter(node, viewer);
	}

	private void createFileAnnotations(PHPFileData fileData, int startOffset, int endOffset) {
		PHPClassData[] classes = fileData.getClasses();
		for (PHPClassData classData : classes) {
			if (structuredTextFoldingProviderPHP.isFoldingClasses()) {
				createCodeDataAnnotations(classData, startOffset, endOffset);
			}
			PHPFunctionData[] methods = classData.getFunctions();
			for (PHPFunctionData methodData : methods) {
				if (structuredTextFoldingProviderPHP.isFoldingFunctions()) {
					createCodeDataAnnotations(methodData, startOffset, endOffset);
				}
				if (structuredTextFoldingProviderPHP.isFoldingPhpDoc()) {
					createDocBlockAnnotations(methodData.getDocBlock(), startOffset, endOffset);
				}
			}
			if (structuredTextFoldingProviderPHP.isFoldingPhpDoc()) {
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
			if (structuredTextFoldingProviderPHP.isFoldingFunctions()) {
				createCodeDataAnnotations(functionData, startOffset, endOffset);
			}
			if (structuredTextFoldingProviderPHP.isFoldingPhpDoc()) {
				createDocBlockAnnotations(functionData.getDocBlock(), startOffset, endOffset);
			}
		}

		if (structuredTextFoldingProviderPHP.isFoldingPhpDoc()) {
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
			DataProjectionAnnotation newAnnotation = new DataProjectionAnnotation(codeData, false);
			DataProjectionAnnotation existingAnnotation = getExistingAnnotation(codeData);
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
		int codeStartOffset = docBlock.getStartPosition();
		if (codeStartOffset > startOffset && codeStartOffset < endOffset) {
			// element may start in one PHP block and end in another.
			Position newPosition = new Position(codeStartOffset, docBlock.getEndPosition() - codeStartOffset);
			DataProjectionAnnotation newAnnotation = new DataProjectionAnnotation(docBlock, false);
			DataProjectionAnnotation existingAnnotation = getExistingAnnotation(docBlock);
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
	private DataProjectionAnnotation getExistingAnnotation(Object data) {
		// XXX think about it!
		return null;
	}

	/**
	 * @param structuredTextFoldingProviderPHP
	 */
	void setProvider(StructuredTextFoldingProviderPHP structuredTextFoldingProviderPHP) {
		this.structuredTextFoldingProviderPHP = structuredTextFoldingProviderPHP;

	}
}
