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
package org.eclipse.php.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.Logger;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPBlock;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPDocBlock;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.editor.util.PHPDocBlockSerialezer;
import org.eclipse.php.internal.ui.editor.util.PHPDocTool;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class AddDescriptionAction implements IObjectActionDelegate {

	private PHPCodeData phpCodeData;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {

		UserData userData = phpCodeData.getUserData();
		String fileName = userData.getFileName();
		IFile file = PHPUiPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
		IEditorPart editorPart;
		try {
			editorPart = EditorUtility.openInEditor(file, true);
		} catch (PartInitException e) {
			Logger.logException(e);
			return;
		}
		ITextEditor textEditor = (ITextEditor) editorPart;
		IEditorInput editorInput = editorPart.getEditorInput();
		IDocument document = textEditor.getDocumentProvider().getDocument(editorInput);
		if (phpCodeData instanceof PHPFileData) {
			handleFileDocBlock((PHPFileData)phpCodeData, (IStructuredDocument)document);
		}
		PHPDocBlock docBlock = PHPDocTool.createPhpDoc(this.phpCodeData, document);
		int startPosition = getCodeDataOffset();
		String dockBlockText = insertDocBlock(phpCodeData, (IStructuredDocument)document, startPosition);
		if(dockBlockText == null) {
			return;
		}
		String shortDescription = docBlock.getShortDescription();
		int shortDescriptionInnerOffset = dockBlockText.indexOf(shortDescription);
		int shortDescriptionStartOffset = startPosition + shortDescriptionInnerOffset;

		EditorUtility.revealInEditor(textEditor, shortDescriptionStartOffset, shortDescription.length());
	}

	private int getCodeDataOffset() {
		if (phpCodeData instanceof PHPFileData) {
			PHPBlock[] phpBlocks = ((PHPFileData) phpCodeData).getPHPBlocks();
			return phpBlocks.length > 0 ? phpBlocks[0].getPHPStartTag().getEndPosition() : -1;
		}
		int dataOffset = phpCodeData.getUserData().getStartPosition();
		return dataOffset;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection == null || !(selection instanceof IStructuredSelection)) {
			return;
		}
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		this.phpCodeData = (PHPCodeData) structuredSelection.getFirstElement();
	}

	/*
	 * Handle a situation where a file DocBlock is requested and there is an undocumented
	 * class, function or define at the beginning of the document. 
	 * In this case we auto-document the undocumented element to comply the DocBlock rules.
	 *  
	 * @param data	A PHPFileData that need to be documented
	 * @param document The IStructuredDocument that we are working on
	 */
	private void handleFileDocBlock(PHPFileData data, IStructuredDocument document) {
		IStructuredDocumentRegion sdRegion = document.getFirstStructuredDocumentRegion();
		while (sdRegion != null && sdRegion.getType() != PHPRegionTypes.PHP_CONTENT) {
			sdRegion = sdRegion.getNext();
		}
		if (sdRegion == null) {
			return;
		}
		ITextRegion textRegion = sdRegion.getFirstRegion();
		int regionEnd = textRegion.getEnd();
		textRegion = sdRegion.getRegionAtCharacterOffset(sdRegion.getStartOffset() + regionEnd + 1);
		if (textRegion == null) {
			return;
		}
		if (textRegion.getType() == PHPRegionTypes.PHP_CLASS) {
			// add a class doc at textRegion.getStart() + sdRegion.getStartOffset()
			addClassBlock(document, data, textRegion.getStart() + sdRegion.getStartOffset());
		} else if (textRegion.getType() == PHPRegionTypes.PHP_FUNCTION){
			addFunctionBlock(document, data, textRegion.getStart() + sdRegion.getStartOffset());
		} else if (textRegion.getType() == PHPRegionTypes.PHP_STRING && sdRegion.getFullText(textRegion).trim().equalsIgnoreCase("define")) { //$NON-NLS-1$
			addConstantBlock(document, data, textRegion.getStart() + sdRegion.getStartOffset());
		}
	}

	private void addConstantBlock(IStructuredDocument document, PHPFileData data, int offset) {
		PHPConstantData[] constants = data.getConstants();
		if (constants.length > 0) {
			if (constants[0].getUserData().getStartPosition() == offset) {
				// We need to add a DocBlock to this constant
				insertDocBlock(constants[0], document, offset);
			}
		}
	}

	private void addFunctionBlock(IStructuredDocument document, PHPFileData data, int offset) {
		PHPFunctionData[] functions = data.getFunctions();
		if (functions.length > 0) {
			if (functions[0].getUserData().getStartPosition() == offset) {
				// We need to add a DocBlock to this function
				insertDocBlock(functions[0], document, offset);
			}
		}
	}

	private void addClassBlock(IStructuredDocument document, PHPFileData data, int offset) {
		PHPClassData[] classes = data.getClasses();
		if (classes.length > 0) {
			if (classes[0].getUserData().getStartPosition() == offset) {
				// We need to add a DocBlock to this class
				insertDocBlock(classes[0], document, offset);
			}
		}
	}

	private String insertDocBlock(CodeData codeData, IStructuredDocument document, int offset) {
		PHPDocBlock docBlock = PHPDocTool.createPhpDoc(codeData, document);
		String dockBlockText = PHPDocBlockSerialezer.instance().createDocBlockText(document, docBlock, offset, true);
		try {
			document.replace(offset, 0, dockBlockText);
		} catch (BadLocationException e) {
			Logger.logException(e);
			dockBlockText = null;
		}
		return dockBlockText;
	}
}
