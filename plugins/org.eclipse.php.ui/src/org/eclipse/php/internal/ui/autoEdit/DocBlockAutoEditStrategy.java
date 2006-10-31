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

package org.eclipse.php.internal.ui.autoEdit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.php.Logger;
import org.eclipse.php.core.documentModel.PHPEditorModel;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.core.format.FormatterUtils;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassVarData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPDocBlock;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.ui.editor.util.PHPDocBlockSerialezer;
import org.eclipse.php.internal.ui.editor.util.PHPDocTool;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * 
 * @author guy.g
 *
 */

public class DocBlockAutoEditStrategy implements IAutoEditStrategy {

	private static final String lineStart = "* ";

	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		if (TypingPreferences.closePhpdoc && command.text != null && TextUtilities.endsWith(document.getLegalLineDelimiters(), command.text) != -1) {
			autoEditAfterNewLine((IStructuredDocument) document, command);
		}
	}

	private void autoEditAfterNewLine(IStructuredDocument document, DocumentCommand command) {
		try {
			//the basic assumption is that we are allready in phpDoc state.
			IRegion lineInfo = document.getLineInformationOfOffset(command.offset);

			int startOffset = lineInfo.getOffset();

			String line = document.get(startOffset, lineInfo.getLength());
			String blanks = getBlanks(line);

			String trimedLine = line.trim();

			boolean isFirstLine = false;
			boolean isDocBlock = true;
			// if this is the first line of the docBlock
			int docStart;
			if ((docStart = trimedLine.indexOf("/*")) != -1) {
				if (command.offset + command.length < startOffset + docStart + 2)
					return;
				isFirstLine = true;
				if (trimedLine.indexOf("/**") != docStart) {
					isDocBlock = false;
				}
			}
			Matcher m = Pattern.compile("/\\*.*\\*/").matcher(trimedLine);
			if (m.find())
				return;
			if (isFirstLine) {
				blanks += ' ';
				command.text += blanks;
				int startIndex = line.indexOf("/*");
				final String partitionType = FormatterUtils.getPartitionType(document, startOffset + startIndex - 1);
				if (partitionType == PHPPartitionTypes.PHP_DEFAULT || partitionType == PHPPartitionTypes.PHP_DOC || partitionType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT) {
					int placeCaretAt = handleDocBlockStart(document, command, blanks, isDocBlock);
					if (placeCaretAt != -1) {
						document.replace(command.offset, command.length, command.text);					
						command.offset = placeCaretAt;
						command.length = 0;
						command.text = "";
						document.getUndoManager().disableUndoManagement();
						document.replace(command.offset, command.length, command.text);
						document.getUndoManager().enableUndoManagement();
					}
				}
				return;
			}
			boolean lastLint = document.get(startOffset, command.offset - startOffset).endsWith("*/");
			if (!lastLint) { // only if the line starts with * then we add * to the new line
				if (trimedLine.length() > 0 && trimedLine.charAt(0) == '*') {
					command.text = command.text + blanks + lineStart;
				}
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
	}

	private String getBlanks(String line) {
		int start;
		start = line.indexOf("/*");
		if (start < 0)
			start = line.indexOf("*");
		if (start >= 0) {
			StringBuffer blanks = new StringBuffer(start);
			for (int i = 0; i < start; ++i)
				if (line.charAt(i) == '\t')
					blanks.append('\t');
				else
					blanks.append(' ');
			return blanks.toString();
		} else {
			return " ";
		}

	}

	/**
	 * this function handles the case that the line of the event is the first line of the dockBloc
	 * @return the position in the document the caret should be at, at the end of the command.
	 * -1 if the caret should be at the end of the command.text
	 */
	private int handleDocBlockStart(IStructuredDocument document, DocumentCommand command, String blanks, boolean isDocBlock) {
		int rvPosition = 0;
		try {
			IRegion lineInfo = document.getLineInformationOfOffset(command.offset);
			int lineStart = lineInfo.getOffset();
			String line = document.get(lineStart, command.offset - lineStart);

			Matcher m = Pattern.compile("\\/\\*+").matcher(line);
			m.find();
			String commentStart = line.substring(m.start(), m.end());
			int commentStartLength = commentStart.length();
			command.text += "* ";
			rvPosition = command.offset + command.text.length();
			int selectionEnd = command.offset + command.length;
			if (isInsideExistingDocBlock(document, selectionEnd)) {
				return -1;
			}

			lineStart += line.indexOf(commentStart);
			String lineContent = line.substring(line.indexOf(commentStart) + commentStartLength).trim();
			rvPosition = lineStart + commentStartLength + command.text.length();
			// if there are whiteSpaces after the selection then we remove them too
			int lineEnd = selectionEnd;
			if (selectionEnd < lineInfo.getOffset() + lineInfo.getLength()) {
				lineInfo = document.getLineInformationOfOffset(selectionEnd);
			}

			if (isDocBlock && TypingPreferences.addDocTags) {
				//taking off the /** in order to find the closest codeData
				document.replace(lineStart, lineEnd - lineStart, "");
				command.offset = lineStart;

				// making sure the new fileData will be created after the deletion
				PHPEditorModel editorModel = (PHPEditorModel) StructuredModelManager.getModelManager().getModelForRead(document);
				editorModel.updateFileData();

				if (lineContent.equals("")) { //this is a patch in order to make PHPDescriptionTool add a default shortDescription
					lineContent = null;
				}

				String stub = getDocBlockStub(editorModel, document, lineStart, lineContent);

				editorModel.releaseFromRead();
				
				// putting back the /** that was taken off
				command.offset += commentStart.length();
				document.replace(lineStart, 0, commentStart);

				if (stub != null) {
					command.text = stub.substring(3);
					if (lineContent == null) {
						//this means that we added the default shortDescription to the docBlock
						//now we want to make sure this description will be selected in the editor 
						//at the end of the command
						String fileName = editorModel.getFileData().getName();
						IFile file = PHPUiPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
						IEditorPart editorPart;
						try {
							editorPart = EditorUtility.openInEditor(file, true);
						} catch (PartInitException e) {
							Logger.logException(e);
							command.text = commentStart + command.text;
							return -1;
						}
						ITextEditor textEditor = EditorUtility.getPHPStructuredEditor(editorPart);
						//25 - stands for the shortDescription length
						//E - stands for the first latter in the shortDescription
						Display.getDefault().asyncExec(new SelectText(command.offset + command.text.indexOf("E"), 25, textEditor));
						return -1;
					}
					return rvPosition + lineContent.length();
				}
			} else {
				command.length += (command.offset - lineStart - commentStartLength);
				command.offset = lineStart + commentStartLength;
			}
			lineStart += commentStartLength;
			if (lineContent != null && !lineContent.equals("")) {
				command.text += lineContent;
				rvPosition = lineStart + command.text.length();
			}

		} catch (BadLocationException e) {
			Logger.logException(e);
		} finally {
			document.getUndoManager().enableUndoManagement();
		}
		command.text += document.getLineDelimiter() + blanks + "*/";
		return rvPosition;
	}

	/**
	 * this function determins if the new line is needed within a docBlock or this is a new docBloc
	 *
	 * @param doc
	 * @param offset
	 * @return true - the dockBlock allready exists
	 */
	private boolean isInsideExistingDocBlock(IStructuredDocument document, int offset) {

		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);

		boolean firstRegion = true;

		while (sdRegion != null) {
			String text = sdRegion.getText();
			if (firstRegion) {
				firstRegion = false;
				text = text.substring(offset - sdRegion.getStartOffset());
			}
			int textLength = text.length();
			if (textLength == 0) {
				sdRegion = sdRegion.getNext();
				continue;
			}
			char c1 = text.charAt(0);
			char c2;
			for (int i = 1; i < textLength; i++) {
				c2 = text.charAt(i);
				//if its a docBlock end then we are in a docBlock
				if ((c1 == '*') && (c2 == '/')) {
					return true;
				}
				//if its a docBlock start then we are not in a docBlock
				if ((c1 == '/') && (c2 == '*')) {
					return false;
				}
				c1 = c2;
			}
			sdRegion = sdRegion.getNext();
		}
		return false;
	}

	/**
	 * @return true - if the docBlock was added
	 */
	private String getDocBlockStub(PHPEditorModel editorModel, IStructuredDocument document, int offset, String shortDescription) {

		PHPFileData fileData = editorModel.getFileData();
		if (fileData == null) {
			return null;
		}
		PHPCodeData codeData = getClosestCodeData(document, fileData, offset);
		if (codeData == null) {
			return null;
		}
		PHPDocBlock docBlock = PHPDocTool.createPhpDoc(codeData, shortDescription, document);
		return PHPDocBlockSerialezer.instance().createDocBlockText(document, docBlock, offset, false);
	}

	/**
	 * finds the closest codeData to the offest
	 */
	private PHPCodeData getClosestCodeData(IStructuredDocument document, PHPFileData fileData, int offset) {

		PHPCodeData closestCodeData = null;
		int closestCodeDataOffset = document.getLength();
		PHPClassData classes[] = fileData.getClasses();

		for (int i = 0; i < classes.length; i++) {
			PHPClassData classData = classes[i];
			int startOffset = classData.getUserData().getStartPosition();
			if (startOffset < offset) {
				int endOffset = classData.getUserData().getEndPosition();
				if (endOffset > offset) {
					return getClosestCodeDataFromClass(document, classData, offset);
				}
			} else {
				if (startOffset < closestCodeDataOffset) {
					closestCodeDataOffset = startOffset;
					closestCodeData = classData;
				}
			}
		}

		PHPFunctionData functions[] = fileData.getFunctions();

		for (int i = 0; i < functions.length; i++) {
			PHPFunctionData functionData = functions[i];
			int startOffset = functionData.getUserData().getStartPosition();
			if (startOffset < offset) {
				int endOffset = functionData.getUserData().getEndPosition();
				if (endOffset > offset) {
					return null;
				}
			} else {
				if (startOffset < closestCodeDataOffset) {
					closestCodeDataOffset = startOffset;
					closestCodeData = functionData;
				}
			}
		}

		PHPConstantData constants[] = fileData.getConstants();

		for (int i = 0; i < constants.length; i++) {
			PHPConstantData constant = constants[i];
			int startOffset = constant.getUserData().getStartPosition();
			if (startOffset < offset) {
				int endOffset = constant.getUserData().getEndPosition();
				if (endOffset > offset) {
					return null;
				}
			} else {
				if (startOffset < closestCodeDataOffset) {
					closestCodeDataOffset = startOffset;
					closestCodeData = constant;
				}
			}
		}

		if (closestCodeData != null && isNoCodeBetween(document, offset, closestCodeDataOffset)) {
			return closestCodeData;
		}
		return null;
	}

	private PHPCodeData getClosestCodeDataFromClass(IStructuredDocument document, PHPClassData classData, int offset) {
		PHPCodeData closestCodeData = null;
		int closestCodeDataOffset = document.getLength();

		PHPFunctionData functions[] = classData.getFunctions();

		for (int i = 0; i < functions.length; i++) {
			PHPFunctionData functionData = functions[i];
			int startOffset = functionData.getUserData().getStartPosition();
			if (startOffset < offset) {
				int endOffset = functionData.getUserData().getEndPosition();
				if (endOffset > offset) {
					return null;
				}
			} else {
				if (startOffset < closestCodeDataOffset) {
					closestCodeDataOffset = startOffset;
					closestCodeData = functionData;
				}
			}
		}
		PHPClassConstData constants[] = classData.getConsts();

		for (int i = 0; i < constants.length; i++) {
			PHPClassConstData constant = constants[i];
			int startOffset = constant.getUserData().getStartPosition();
			if (startOffset < offset) {
				int endOffset = constant.getUserData().getEndPosition();
				if (endOffset > offset) {
					return null;
				}
			} else {
				if (startOffset < closestCodeDataOffset) {
					closestCodeDataOffset = startOffset;
					closestCodeData = constant;
				}
			}
		}

		PHPClassVarData vars[] = classData.getVars();

		for (int i = 0; i < vars.length; i++) {
			PHPClassVarData var = vars[i];
			int startOffset = var.getUserData().getStartPosition();
			if (startOffset < offset) {
				int endOffset = var.getUserData().getEndPosition();
				if (endOffset > offset) {
					return null;
				}
			} else {
				if (startOffset < closestCodeDataOffset) {
					closestCodeDataOffset = startOffset;
					closestCodeData = var;
				}
			}
		}

		if (closestCodeData != null && isNoCodeBetween(document, offset, closestCodeDataOffset)) {
			return closestCodeData;
		}
		return null;
	}

	/**
	 * checks if there is any code between the current offset and the codeData start
	 *
	 * @param offset    - the selection end
	 * @param endOffset - the codeData startPosition
	 * @return true if no code between them (whiteSpace or lineComment is allowed)
	 */

	private boolean isNoCodeBetween(IStructuredDocument document, int offset, int endOffset) {
		int index = offset;
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(index);

		while (sdRegion != null && index < endOffset) {
			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(index);
			String regionType = tRegion.getType();
			if (regionType == PHPRegionTypes.PHP_LINE_COMMENT || sdRegion.getStartOffset() + tRegion.getTextEnd() < index) {
				index = sdRegion.getStartOffset() + tRegion.getEnd() + 1;
				if (sdRegion.getEndOffset() < index) {
					sdRegion = sdRegion.getNext();
				}
			} else {
				return false;
			}
		}

		return true;
	}

	/**
	 * the perpose of this class is to select text in the editor
	 * 
	 *
	 */
	private class SelectText implements Runnable {
		int offset;
		int length;
		ITextEditor textEditor;

		public SelectText(int offset, int length, ITextEditor textEditor) {
			this.offset = offset;
			this.length = length;
			this.textEditor = textEditor;
		}

		public void run() {
			EditorUtility.revealInEditor(textEditor, offset, length);
		}
	}
}
