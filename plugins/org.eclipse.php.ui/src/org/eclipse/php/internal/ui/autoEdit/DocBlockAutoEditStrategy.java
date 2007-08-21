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

import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.core.preferences.TaskPatternsProvider;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.core.project.properties.handlers.UseAspTagsHandler;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.util.PHPDocBlockSerialezer;
import org.eclipse.php.internal.ui.editor.util.PHPDocTool;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;

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
				//taking off the text after the /** in order to find the closest codeData
				document.replace(lineStart + 3, lineEnd - lineStart - 3, "");
				lineEnd = lineStart + 3; //now the end of the line is the end of the comment start
				command.offset = lineEnd;

				if (lineContent.equals("")) { //this is a patch in order to make PHPDescriptionTool add a default shortDescription
					lineContent = null;
				}

				DOMModelForPHP editorModel = (DOMModelForPHP) StructuredModelManager.getModelManager().getModelForRead(document);
				PHPFileData fileData = editorModel.getFileData();
				editorModel.releaseFromRead();

				fileData = updateFileData(document, lineStart, lineEnd, fileData);
				String stub = getDocBlockStub(fileData, document, lineStart, lineContent, lineEnd);

				if (stub != null) {
					command.text = stub.substring(3);
					if (lineContent == null) {
						//this means that we added the default shortDescription to the docBlock
						//now we want to make sure this description will be selected in the editor 
						//at the end of the command
						IEditorPart editorPart = PHPUiPlugin.getActivePage().getActiveEditor();
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

	private static final IPreferenceStore store = PHPCorePlugin.getDefault().getPreferenceStore();
	private static final PreferencesSupport preferencesSupport = new PreferencesSupport(PHPCorePlugin.ID, store);

	private PHPFileData updateFileData(IStructuredDocument document, int commentStart, int commentEnd, PHPFileData fileData) {
		String fileName = fileData.getName();

		Pattern[] tasks = new Pattern[0];

		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
		IProject project = null;
		if (file != null) {
			project = file.getProject();
			tasks = TaskPatternsProvider.getInstance().getPatternsForProject(project);
		}
		boolean useAspTags = UseAspTagsHandler.useAspTagsAsPhp(project);
		String version = preferencesSupport.getPreferencesValue(Keys.PHP_VERSION, PHPVersion.PHP5, project);

		fileData = PHPFileDataUtilities.getFileData(new SkippedTextDocumentReader(document, commentStart, commentEnd - commentStart), fileData.getName(), 0, version, tasks, useAspTags);
		return fileData;
	}

	/**
	 * this function determines if the new line is needed within a docBlock or this is a new docBloc
	 *
	 * @param doc
	 * @param offset
	 * @return true - the dockBlock already exists
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

	private String getDocBlockStub(PHPFileData fileData, IStructuredDocument document, int offset, String shortDescription, int skippedAreaEndoffset) {

		if (fileData == null) {
			return null;
		}
		PHPCodeData codeData = getClosestCodeData(document, fileData, offset, skippedAreaEndoffset);
		if (codeData == null) {
			return null;
		}
		PHPDocBlock docBlock = PHPDocTool.createPhpDoc(codeData, shortDescription);
		return PHPDocBlockSerialezer.instance().createDocBlockText(document, docBlock, offset, false);
	}

	/**
	 * finds the closest codeData to the offset
	 * @param skippedAreaEndoffset 
	 */
	private PHPCodeData getClosestCodeData(IStructuredDocument document, PHPFileData fileData, int offset, int skippedAreaEndoffset) {

		PHPCodeData closestCodeData = null;
		int closestCodeDataOffset = document.getLength();
		PHPClassData classes[] = fileData.getClasses();

		for (int i = 0; i < classes.length; i++) {
			PHPClassData classData = classes[i];
			int startOffset = classData.getUserData().getStartPosition();
			if (startOffset < offset) {
				int endOffset = classData.getUserData().getEndPosition();
				if (endOffset > offset) {
					return getClosestCodeDataFromClass(document, classData, offset, skippedAreaEndoffset);
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

		if (closestCodeData != null && isNoCodeBetween(document, skippedAreaEndoffset, closestCodeDataOffset)) {
			return closestCodeData;
		}
		return null;
	}

	private PHPCodeData getClosestCodeDataFromClass(IStructuredDocument document, PHPClassData classData, int offset, int skippedAreaEndoffset) {
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

		if (closestCodeData != null && isNoCodeBetween(document, skippedAreaEndoffset, closestCodeDataOffset)) {
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
		char[] chars = null;
		try {
			chars = document.get(offset, endOffset - offset).toCharArray();
		} catch (BadLocationException e) {
			return true;
		}
		if (chars == null) {
			return true;
		}
		boolean inLineComment = false;
		for (int index = 0; index < chars.length; index++) {
			char curr = chars[index];
			if (inLineComment) {
				if (curr == '\n' || curr == '\r') {
					inLineComment = false;
				}
			} else {
				if (!Character.isWhitespace(curr)) {
					if (curr == '/' && index + 1 < chars.length && chars[index + 1] == '/') {
						inLineComment = true;
					} else {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * the purpose of this class is to select text in the editor
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

	/**
	 * This class purpose is to read the document and skip a certain part.
	 *
	 */
	private class SkippedTextDocumentReader extends Reader {
		private IDocument document = null;
		private int mark = 0;
		private int position = 0;
		private int skippedPosition;
		private int skippedLength;

		public SkippedTextDocumentReader(IDocument document, int skippedPosition, int skippedLength) {
			super();
			this.document = document;
			this.skippedPosition = skippedPosition;
			this.skippedLength = skippedLength;
		}

		public void close() throws IOException {
			document = null;
		}

		public void mark(int readAheadLimit) throws IOException {
			mark = position;
		}

		public boolean markSupported() {
			return true;
		}

		public int read(char[] cbuf, int off, int len) throws IOException {
			if (document == null)
				return -1;

			char[] readChars = null;
			try {
				if (position >= document.getLength())
					return -1;

				if (position < skippedPosition) {
					if (position + len < skippedPosition) {
						//we try to read before the skipped area - we should do nothing
					} else {
						//we need to split the reading since the requested string includes the skipped area.
						String s1 = document.get(position, skippedPosition - position);
						String s2 = document.get(skippedPosition + skippedLength, len - (skippedPosition - position));
						readChars = (s1 + s2).toCharArray();
					}
				} else if (position <= skippedPosition + skippedLength) {
					//the reading starts in the middle of the skipped area - moving the reading index forward
					position = skippedPosition + skippedLength;
				}
				// the IDocument is likely using a GapTextStore, so we can't
				// retrieve a char[] directly
				if (readChars == null) {
					//meaning it wasn't filled already 
					if (position + len > document.getLength())
						readChars = document.get(position, document.getLength() - position).toCharArray();
					else
						readChars = document.get(position, len).toCharArray();
				}
				System.arraycopy(readChars, 0, cbuf, off, readChars.length);
				position += readChars.length;
				return readChars.length;
			} catch (Exception e) {
				throw new IOException("Exception while reading from IDocument: " + e); //$NON-NLS-1$
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.Reader#reset()
		 */
		public void reset() throws IOException {
			position = mark;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.Reader#reset()
		 */
		public void reset(int pos) throws IOException {
			position = pos;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.Reader#skip(long)
		 */
		public long skip(long n) throws IOException {
			if (document == null)
				return 0;

			long skipped = n;
			if (position + n > document.getLength()) {
				skipped = document.getLength() - position;
				position = document.getLength();
			} else {
				position += n;
			}
			return skipped;
		}

	}
}
