/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.format;

import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class IndentationObject {

	private int indentationWrappedLineSize;
	private int indentationArrayInitSize;
	private int indentationSize;
	private char indentationChar;

	public IndentationObject(IStructuredDocument document) {
		IFormatterCommonPrferences preferences = FormatterUtils.getFormatterCommonPrferences();
		this.indentationWrappedLineSize = preferences.getIndentationWrappedLineSize(document);
		this.indentationArrayInitSize = preferences.getIndentationArrayInitSize(document);
		this.indentationSize = preferences.getIndentationSize(document);
		this.indentationChar = preferences.getIndentationChar(document);
	}

	public int getIndentationArrayInitSize() {
		return indentationArrayInitSize;
	}

	public char getIndentationChar() {
		return indentationChar;
	}

	public int getIndentationSize() {
		return indentationSize;
	}

	public int getIndentationWrappedLineSize() {
		return indentationWrappedLineSize;
	}

}