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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public interface IIndentationStrategyExtension1 extends IIndentationStrategy {

	void placeMatchingBlanks(IStructuredDocument document, StringBuffer result,
			int lineNumber, int forOffset, Program program)
			throws BadLocationException;

}
