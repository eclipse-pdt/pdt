/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.format;

import org.eclipse.jface.text.IDocument;

public interface IFormatterCommonPreferences {
	public int getIndentationWrappedLineSize(IDocument document);

	public int getIndentationArrayInitSize(IDocument document);

	public int getIndentationSize(IDocument document);

	public char getIndentationChar(IDocument document);

	public int getTabSize(IDocument document);

	public boolean useTab(IDocument document);
}
