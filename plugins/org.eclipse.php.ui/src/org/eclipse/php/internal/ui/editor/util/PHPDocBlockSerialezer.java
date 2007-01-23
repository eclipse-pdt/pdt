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

package org.eclipse.php.internal.ui.editor.util;

import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocBlock;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocTag;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * @author guy.g
 *
 */
public class PHPDocBlockSerialezer {

	private static PHPDocBlockSerialezer instance;
	private static final String DOCBLOCK_START = "/**";
	private static final String DOCBLOCK_END = " */";
	private static final String CENTERED_ASTERISK = " * ";
	private static final String RIGHT_ASTERISK = " *";
	private static Pattern pattern = Pattern.compile("\n");

	private static String lineDelimiter = "";

	public static PHPDocBlockSerialezer instance() {
		if (instance == null) {
			instance = new PHPDocBlockSerialezer();
		}
		return instance;
	}

	private PHPDocBlockSerialezer() {
	}

	public String createDocBlockText(IStructuredDocument document, PHPDocBlock docBlock, int position, boolean addNewLine) {
		if (!lineDelimiter.equals(document.getLineDelimiter())) {
			lineDelimiter = document.getLineDelimiter();
			pattern = Pattern.compile(lineDelimiter);

		}
		String whiteSpaces = getPreInertionPointWhiteSpaces(document, position);
		String tagsText = createTagsText(docBlock, whiteSpaces);

		StringBuffer rv = new StringBuffer();
		if (docBlock.getType() == PHPDocBlock.FILE_DOCBLOCK) {
			rv.append(lineDelimiter);
			rv.append(whiteSpaces);
		}
		rv.append(DOCBLOCK_START); //$NON-NLS-1$
		rv.append(lineDelimiter);
		if (!docBlock.getShortDescription().equals("")) { //$NON-NLS-1$
			rv.append(whiteSpaces);
			rv.append(CENTERED_ASTERISK);//$NON-NLS-1$
			rv.append(docBlock.getShortDescription());
			rv.append(lineDelimiter);
			rv.append(whiteSpaces);
			rv.append(RIGHT_ASTERISK); 
			rv.append(lineDelimiter);
		}
		String longDesc = docBlock.getLongDescription().replaceAll(pattern.pattern(), lineDelimiter + whiteSpaces + CENTERED_ASTERISK);
		if (!longDesc.equals("")) { //$NON-NLS-1$
			rv.append(whiteSpaces);
			rv.append(CENTERED_ASTERISK);
			rv.append(longDesc);
			rv.append(lineDelimiter);
			rv.append(whiteSpaces);
			rv.append(RIGHT_ASTERISK); 
			rv.append(lineDelimiter);
		}
		rv.append(tagsText);
		rv.append(whiteSpaces);
		rv.append(DOCBLOCK_END);
		if(addNewLine){
			rv.append(lineDelimiter);
			rv.append(whiteSpaces);
		}

		return rv.toString();
	}

	private String createTagsText(PHPDocBlock docBlock, String whiteSpaces) {
		Iterator it = docBlock.getTags();
		String rv = ""; //$NON-NLS-1$
		while (it != null && it.hasNext()) {
			PHPDocTag phpDocTag = (PHPDocTag) it.next();
			rv += whiteSpaces + CENTERED_ASTERISK + phpDocTag.toString() + lineDelimiter; 
		}
		return rv;
	}

	private String getPreInertionPointWhiteSpaces(IDocument document, int position) {
		try {
			IRegion region = document.getLineInformationOfOffset(position);
			int lineStartOffset = region.getOffset();
			int i = lineStartOffset;
			for (; i < position; ++i) {
				if (!Character.isWhitespace(document.getChar(i))) {
					break;
				}
			}
			return document.get(lineStartOffset, i - lineStartOffset);
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return ""; //$NON-NLS-1$
	}

}
