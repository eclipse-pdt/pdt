/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.util.PHPDocBlockSerialezer;
import org.eclipse.php.internal.ui.editor.util.PHPDocTool;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class AddDescriptionAction implements IObjectActionDelegate {

	private PHPCodeData[] phpCodeData;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		if (phpCodeData == null) {
			return;
		}
		
		// Sorting the PHP code elements array by "first-line" position.
		// this will enable "right" order of iteration
		Arrays.sort(phpCodeData, new Comparator<PHPCodeData>() {
			public int compare(PHPCodeData object1, PHPCodeData object2) {

				/* handling null-pointers on both levels (object=null or object1.getUserData()=null)
				   'null' objects will be considered as 'bigger' and will be pushed to the end of the array 
				 */
				if (object1 == null || object1.getUserData() == null){
					if (object2 == null || object2.getUserData() == null) {
						return 0 ;	// both null => equal
					}else return 1;	// only object1 is null => object1 is bigger
				}
				if (object2 == null || object2.getUserData() == null){ 
					return -1;		// only object2 is null => object2 is bigger
				}
				return object1.getUserData().getStartPosition() - object2.getUserData().getStartPosition() ;			
			}	
		});
		
		// iterating the functions that need to add 'PHP Doc' bottoms-up - to eliminate mutual interference
		for (int i = phpCodeData.length-1 ; i >= 0; i--) {
			PHPCodeData codeData = phpCodeData[i];
			if (null == codeData){
				continue ; // if we got to null pointer, skipping it
			}
			IEditorPart editorPart;
			IEditorInput input = EditorUtility.getEditorInput(codeData);
			IWorkbenchPage page = PHPUiPlugin.getActivePage();
			try {
				editorPart = IDE.openEditor(page, input, PHPUiConstants.PHP_EDITOR_ID);
			} catch (PartInitException e) {
				Logger.logException(e);
				return;
			}
			ITextEditor textEditor = EditorUtility.getPHPStructuredEditor(editorPart);
			IEditorInput editorInput = editorPart.getEditorInput();
			IDocument document = textEditor.getDocumentProvider().getDocument(editorInput);
			if (codeData instanceof PHPFileData) {
				handleFileDocBlock((PHPFileData) codeData, (IStructuredDocument) document);
			}
			PHPDocBlock docBlock = PHPDocTool.createPhpDoc(codeData);
			int startPosition = getCodeDataOffset(codeData);
			String dockBlockText = insertDocBlock(codeData, (IStructuredDocument) document, startPosition);
			if (dockBlockText == null) {
				return;
			}
			String shortDescription = docBlock.getShortDescription();
			int shortDescriptionInnerOffset = dockBlockText.indexOf(shortDescription);
			int shortDescriptionStartOffset = startPosition + shortDescriptionInnerOffset;

			EditorUtility.revealInEditor(textEditor, shortDescriptionStartOffset, shortDescription.length());
		}
	}

	private int getCodeDataOffset(PHPCodeData codeData) {
		if (codeData instanceof PHPFileData) {
			PHPBlock[] phpBlocks = ((PHPFileData) codeData).getPHPBlocks();
			return phpBlocks.length > 0 ? phpBlocks[0].getPHPStartTag().getEndPosition() : -1;
		}
		int dataOffset = codeData.getUserData().getStartPosition();
		return dataOffset;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection == null || !(selection instanceof IStructuredSelection)) {
			return;
		}

		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		phpCodeData = new PHPCodeData[structuredSelection.size()];
		Iterator i = structuredSelection.iterator();
		int idx = 0;
		while (i.hasNext()) {
			phpCodeData[idx++] = (PHPCodeData) i.next();
		}
	}

	/**
	 * Handle a situation where a file DocBlock is requested and there is an undocumented
	 * class, function or define at the beginning of the document. 
	 * In this case we auto-document the undocumented element to comply the DocBlock rules.
	 *  
	 * @param data	A PHPFileData that need to be documented
	 * @param document The IStructuredDocument that we are working on
	 */
	private void handleFileDocBlock(PHPFileData data, IStructuredDocument document) {

		// Find the first PHP script region:
		IStructuredDocumentRegion sdRegion = document.getFirstStructuredDocumentRegion();
		IPhpScriptRegion phpScriptRegion = null;
		while (sdRegion != null) {
			ITextRegion region = sdRegion.getFirstRegion();
			if (region.getType() == PHPRegionContext.PHP_OPEN) {
				region = sdRegion.getRegionAtCharacterOffset(region.getEnd());
				if (region != null && region.getType() == PHPRegionContext.PHP_CONTENT) {
					phpScriptRegion = (IPhpScriptRegion) region;
					break;
				}
			}
			sdRegion = sdRegion.getNext();
		}

		if (phpScriptRegion != null) {
			try {
				ITextRegion textRegion = phpScriptRegion.getPhpToken(0);
				int offset = textRegion.getStart() + sdRegion.getStartOffset() + phpScriptRegion.getStart();
				if (textRegion.getType() == PHPRegionTypes.PHP_CLASS) {
					// add a class doc at textRegion.getStart() + sdRegion.getStartOffset()
					addClassBlock(document, data, offset);
				} else if (textRegion.getType() == PHPRegionTypes.PHP_FUNCTION) {
					addFunctionBlock(document, data, offset);
				} else if (textRegion.getType() == PHPRegionTypes.PHP_STRING && document.get(offset, textRegion.getLength()).trim().equalsIgnoreCase("define")) { //$NON-NLS-1$
					addConstantBlock(document, data, offset);
				}
			} catch (BadLocationException e) {
			}
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
		PHPDocBlock docBlock = PHPDocTool.createPhpDoc(codeData);
		String dockBlockText = PHPDocBlockSerialezer.instance().createDocBlockText(document, docBlock, offset, codeData instanceof PHPFileData, true);
		try {
			document.replace(offset, 0, dockBlockText);
		} catch (BadLocationException e) {
			Logger.logException(e);
			dockBlockText = null;
		}
		return dockBlockText;
	}
}
