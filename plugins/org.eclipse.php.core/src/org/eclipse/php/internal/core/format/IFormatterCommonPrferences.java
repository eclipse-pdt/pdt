/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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

import org.eclipse.jface.text.IDocument;

public interface IFormatterCommonPrferences {
	public int getIndentationWrappedLineSize(IDocument document);

	public int getIndentationArrayInitSize(IDocument document);

	public int getIndentationSize(IDocument document);

	public char getIndentationChar(IDocument document);

	public int getTabSize(IDocument document);

	public boolean useTab(IDocument document);
}
