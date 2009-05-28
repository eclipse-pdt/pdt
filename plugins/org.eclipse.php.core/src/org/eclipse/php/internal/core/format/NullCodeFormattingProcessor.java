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
/**
 * 
 */
package org.eclipse.php.internal.core.format;

import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.text.edits.MultiTextEdit;

/**
 * An empty implementation of the {@link ICodeFormattingProcessor} that performs visits on 
 * the ASTNode but does not aggregate any text edits.
 * 
 * @author shalom
 */
public class NullCodeFormattingProcessor extends AbstractVisitor implements ICodeFormattingProcessor {

	public String createIndentationString(int indentationUnits) {
		return ""; //$NON-NLS-1$
	}

	public MultiTextEdit getTextEdits() {
		return new MultiTextEdit();
	}

}
