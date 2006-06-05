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
package org.eclipse.php.core.format;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.core.Logger;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatContraints;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatPreferences;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;

public class PhpFormatter implements IStructuredFormatter {

	protected PhpFormatConstraints fFormatContraints = null;
	protected IStructuredFormatPreferences fFormatPreferences = null;
	protected IProgressMonitor fProgressMonitor = null;

	private PhpFormatProcessorImpl fProcessoer = null;

	public void format(Node node) {
		format(node, getFormatContraints());
	}

	public void format(Node node, IStructuredFormatContraints formatContraints) {
		if (node instanceof IDOMNode) {
			formatNode((IDOMNode) node, formatContraints);
		}

	}

	private void formatNode(IDOMNode node, IStructuredFormatContraints formatContraints) {
		if (node.hasChildNodes()) { // container
			formatChildNodes(node, formatContraints);
		} else { // leaf
			IStructuredDocumentRegion sdRegion = node.getStartStructuredDocumentRegion();
			if (sdRegion != null && sdRegion.getType() == PHPRegionTypes.PHP_CONTENT) {
				format(sdRegion);
			}
		}

	}

	private void formatChildNodes(IDOMNode node, IStructuredFormatContraints formatContraints) {
		IDOMNode child = (IDOMNode) node.getFirstChild();

		while (child != null) {
			IStructuredDocumentRegion sdRegion = child.getStartStructuredDocumentRegion();
			if (sdRegion.getType() == PHPRegionTypes.PHP_CONTENT) {
				format(sdRegion);
			}
			child = (IDOMNode) child.getNextSibling();
		}

	}

	public IStructuredFormatContraints getFormatContraints() {
		if (fFormatContraints == null) {
			fFormatContraints = new PhpFormatConstraints();
		}
		return fFormatContraints;
	}

	public IStructuredFormatPreferences getFormatPreferences() {
		return fFormatPreferences;
	}

	public void setFormatPreferences(IStructuredFormatPreferences formatPreferences) {
		this.fFormatPreferences = formatPreferences;
	}

	public void setProgressMonitor(IProgressMonitor monitor) {
		this.fProgressMonitor = monitor;
	}

	public void setProcessor(PhpFormatProcessorImpl processor) {
		this.fProcessoer = processor;
	}

	protected int getStart() {
		return (fProcessoer == null) ? 0 : fProcessoer.getStart();
	}

	protected int getLength() {
		return (fProcessoer == null) ? 0 : fProcessoer.getLength();
	}

	private void format(IStructuredDocumentRegion sdRegion) {

		int regionStart = sdRegion.getStartOffset();
		int regionEnd = regionStart + sdRegion.getLength();
		int formatRequestStart = getStart();
		int formatRequestEnd = formatRequestStart + getLength();

		int startFormat = (formatRequestStart < regionStart) ? regionStart : formatRequestStart;
		int endFormat = (formatRequestEnd > regionEnd) ? regionEnd : formatRequestEnd;

		IStructuredDocument document = sdRegion.getParentDocument();
		int lineIndex = document.getLineOfOffset(startFormat);
		int endLineIndex = document.getLineOfOffset(endFormat);

		for (; lineIndex <= endLineIndex; lineIndex++) {
			formatLine(sdRegion, lineIndex);
		}

	}

	private StringBuffer resultBuffer = new StringBuffer();

	private void formatLine(IStructuredDocumentRegion sdRegion, int lineNumber) {

		resultBuffer.setLength(0);

		try {
			IStructuredDocument document = sdRegion.getParentDocument();
			IRegion lineInfo = document.getLineInformation(lineNumber);
			int startOffset = lineInfo.getOffset();
			int lineLength = lineInfo.getLength();

			if (!shouldReformat(document, lineInfo)) {
				return;
			}
			String lineText = document.get(startOffset, lineLength);

			// remove ending spaces.
			int endingWhiteSpaces = getEndingWhiteSpaces(lineText);
			if (endingWhiteSpaces != lineLength) {
				document.replace(startOffset + endingWhiteSpaces, lineLength - endingWhiteSpaces, "");
			}
			if (endingWhiteSpaces == 0) {
				return;
			}

			int startingWhiteSpaces = getStartingWhiteSpaces(lineText);

			IIndentationStrategy insertionStrategy;

			ITextRegion firstTokenInLine = sdRegion.getRegionAtCharacterOffset(lineInfo.getOffset());
			if (firstTokenInLine.getStart() + sdRegion.getStartOffset() < lineInfo.getOffset() || firstTokenInLine.getType() == PHPRegionTypes.WHITESPACE) {
				//meaning we got previos line last token
				firstTokenInLine = sdRegion.getRegionAtCharacterOffset(sdRegion.getStartOffset() + firstTokenInLine.getEnd());
			}
			String firstTokenType = firstTokenInLine.getType();
			if (firstTokenType == PHPRegionTypes.PHP_CASE || firstTokenType == PHPRegionTypes.PHP_DEFAULT) {
				insertionStrategy = caseDefualtIndentationStrategy;
			} else {
				insertionStrategy = getIndentationStrategy(lineText.charAt(startingWhiteSpaces));
			}

			// Fill the buffer with blanks as if we added a "\n" to the end of the prev element.
			//insertionStrategy.placeMatchingBlanks(editor,doc,insertionStrtegyKey,resultBuffer,startOffset-1);
			insertionStrategy.placeMatchingBlanks(document, resultBuffer, lineNumber, document.getLineOffset(lineNumber));

			// replace the starting spaces
			document.replaceText(sdRegion, startOffset, startingWhiteSpaces, resultBuffer.toString());

		} catch (BadLocationException e) {
			Logger.logException(e);
		}
	}

	private boolean shouldReformat(IStructuredDocument document, IRegion lineInfo) {

		if (lineInfo.getLength() == 0) {
			return false;
		}

		int lineStart = lineInfo.getOffset();
		int lineEnd = lineStart + lineInfo.getLength();

		try {
			while (Character.isWhitespace(document.getChar(lineStart)) && lineStart <= lineEnd) {
				lineStart++;
			}
		} catch (BadLocationException e) {
		}

		String checkedLineBeginState = FormatterUtils.getPartitionType(document, lineStart);

		return ((checkedLineBeginState == PHPPartitionTypes.PHP_DEFAULT) || (checkedLineBeginState == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT));
	}

	private static int getStartingWhiteSpaces(String text) {
		int index = 0;
		for (; index < text.length(); index++) {
			char c = text.charAt(index);
			if (c != ' ' && c != '\t') {
				break;
			}
		}
		return index;
	}

	private static int getEndingWhiteSpaces(String text) {
		int index = text.length() - 1;
		for (; index >= 0; index--) {
			char c = text.charAt(index);
			if (c != ' ' && c != '\t') {
				break;
			}
		}
		return index + 1;
	}

	protected IIndentationStrategy getIndentationStrategy(char c) {
		if (c == '}') {
			return curlyCloseIndentationStrategy;
		}
		return getDefaultIndentationStrategy();
	}

	private IIndentationStrategy getDefaultIndentationStrategy() {
		return defualtIndentationStrategy;
	}

	private IIndentationStrategy defualtIndentationStrategy = new DefualtIndentationStrategy();;
	private IIndentationStrategy curlyCloseIndentationStrategy = new CurlyCloseIndentationStrategy();
	private IIndentationStrategy caseDefualtIndentationStrategy = new CaseDefualtIndentationStrategy();

}
